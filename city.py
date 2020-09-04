import time
import pymysql
import requests
import threading
from queue import Queue
from bs4 import BeautifulSoup

CRAWL_EXIT = False
PARSE_EXIT = False
count = 0
path = "C:/Users/17604/Desktop/URL.txt"
cities = ["济南市", "青岛市", "淄博市", "枣庄市", "潍坊市", "烟台市", "临沂市", "东营市", "德州市",
        "济宁市", "菏泽市", "聊城市", "滨州市", "泰安市", "日照市", "威海市"]
# db = {"ip": 'localhost', "user": 'root', "pwd": 'account4FTQ', "database": 'tender', "table": 'city'}
db = {"ip": '47.104.188.61', "user": 'root', "pwd": 'Root@123!', "database": 'zb', "table": 'city'}
keys = {}
'''
keys = {"教育": ["大学", "学院", "学校", "研究院", "幼儿园", "中学", "高中"],
        "公安": ["公安", "监狱"],
        "法院": ["法院", "检察院"],
        "消防": ["消防"],
        "智能": ["机器人"]
        }
'''



'''
    采集线程类
'''
class ThreadCrawl(threading.Thread):
    def __init__(self, threadName, urlQueue, dataQueue):
        threading.Thread.__init__(self)
        self.threadName = threadName
        self.urlQueue = urlQueue
        self.dataQueue = dataQueue
        self.headers = {"User-Agent": "Mozilla/5.0","Accept":"text/html,application/xhtml+xml,"
                     "application/xml;q=0.9,image/webp,*/*;q=0.8"}

    def run(self):
        # print(self.threadName + "启动")
        while not CRAWL_EXIT:
            try:
                url = self.urlQueue.get(False)
                html = requests.get(url, headers=self.headers)
                self.dataQueue.put(html)
                print(self.threadName + "正在处理%s" % (url))
            except:
                pass
        # print(self.threadName + "终止")


'''
    解析线程类
'''
class ThreadParse(threading.Thread):
    def __init__(self, threadName, dataQueue, writeLock):
        threading.Thread.__init__(self)
        self.threadName = threadName
        self.dataQueue = dataQueue
        self.writeLock = writeLock

    def run(self):
        # print(self.threadName + "启动")
        while not PARSE_EXIT:
            try:
                html = self.dataQueue.get(False)
                self.parse(html)
            except:
                pass
        # print(self.threadName + "终止")

    def parse(self, html):
        data = {}
        if html.status_code == 200:
            soup = BeautifulSoup(html.text, 'html.parser')
            content = soup.find(attrs={"class": "content"})
            tr = content.find_all("tr")
            data["item"] = tr[1].text  # 采购项目
            data["releaseDate"] = getDate(find(soup.text, "发布时间：", "\xa0")[0])      # 发布时间
            data["location"] = getCity(content.text)  # 市区
            data["purchaser"] = find(content.text, "采购人：", "\xa0")[0]
            data["category"] = getCategory(data["item"], keys)
            data["method"] = "竞争性磋商" if data["item"].find("竞争性磋商") else "公开招标"
            data["phoneNumber"] = find(content.text, "联系方式：", "\xa0")[0]
            data["budget"] = getBudget(content.find(attrs={"id": "NoticeDetail"}))        # 预算总金额
            data["downloadDate"] = find(content.text, "1.时间：", "\xa0")[0]               # 获取文件时间
            data["submitDate"] = find(content.text, "1.时间：", "\xa0")[1]                 # 递交文件时间
            data["bidDate"] = find(content.text, "1.时间：", "\xa0")[2]                    # 开标/磋商时间
        if data["category"] is not None:
            global count
            count += 1
            print(self.threadName + "正在写入第%d条数据, 其项目名称为%s" % (count, data["item"]))
            to_sql(data, db["ip"], db["user"], db["pwd"], db["database"], db["table"])
        else:
            pass


'''
    功能: 从包含"xx年xx月xx日"的字符串中提取日期
    str: 待处理的字符串
'''
def getDate(str):
    p1 = str.find("年")
    year = str[p1 - 4 : p1]
    p2 = str.find("月")
    month = str[p2 - 2: p2]
    p3 = str.find("日")
    day = str[p3 - 2: p3]
    return "%s-%s-%s" %(year, month, day)


'''
    功能: 从包含"xx市"的字符串中提取市区名称
    str: 待处理的字符串
'''
def getCity(str):
    citySet = set(cities)
    pos = str.find("市")
    city = str[pos - 2: pos + 1]
    while (city not in citySet) & (str.find("市") != -1):
        str = str.partition("市")[2]
        pos = str.find("市")
        city = str[pos - 2: pos + 1]
    return city


'''
    功能: 判断目标字符串中是否含有关键字,若含有则返回其类型, 反之返回
    str : 目标字符串
    keys: 关键字字典
'''
def getCategory(str, keys):
    for k in keys:
        for v in keys[k]:
            if v in str:
                return k
    return None


'''
    功能: 从html中提取并计算预算总金额(budget)
    html: 包含多个预算金额的html页面
'''
def getBudget(html):
    trs = html.find_all("tr")
    budget = 0.0
    for i in range(1, len(trs)):
        budget += float(trs[i].text.split("\xa0")[-2])
    return budget


# '''
#     功能：从txt文件中获取所要爬取数据的URL
# '''
# def getURL(path):
#     urlList = []
#     with open(path, "r") as f:
#         for line in f.readlines():
#             urlList.append(line.strip('\n'))
#     return urlList

'''
    功能：翻页获取所有链接
    list：储存获取的链接
    name：'province':省,'city':市县
'''
def getURL(name):
    list = []
    curpage = 0
    flag = True     # 标识器，标识url是否已被解析
    lastId = None
    file = './lmm/city.txt'
    with open(file, 'r') as f:
        lastId = f.read().strip()

    # 构建url
    url = 'http://www.ccgp-shandong.gov.cn/sdgp2017/site/channelall.jsp?colcode=0303'
    # url = baseUrl + '0301' if name == 'province' else baseUrl + '0303'

    while flag:
        curpage += 1
        formdata = {'curpage': curpage}
        response = requests.post(url, data=formdata)
        bsObj = BeautifulSoup(response.content.decode(), 'html.parser')

        for a in bsObj.findAll("a", "aa"):
            href = a.attrs["href"]
            id = href[-9:]
            # print("%s %s %s" %(id, lastId, (id == lastId)))
            if id == lastId:
                flag = False
                break

            bidding_url = "http://www.ccgp-shandong.gov.cn/sdgp2017/site/listcontnew.jsp?" + href[24:]
            list.append(bidding_url)
    if len(list) == 0:
        return list
    with open(file, 'w') as f:
        f.write(list[0][-9:])

    return list


'''
    功能： 找出字符串中拥有指定前继（pre)和后继（post)的子串
    str： 待处理的字符串
    pre、post：所需提取字串的前驱与后继
'''
def find(str, pre, post):
    ansList = []
    while str.find(pre) != -1:
        tmp = str.partition(pre)[2].partition(post)
        ansList.append(tmp[0])
        str = tmp[2]
    return ansList


'''
    功能：将字典数据转换为sql插入语句
    dict：待转化的字典数据
    table：待插入数据的表
'''
def dict2sql(dict, table):
    ls = [(k, v) for k, v in dict.items() if v is not None]
    sql = 'INSERT INTO %s (' % table + ','.join([i[0] for i in ls]) + \
               ') VALUES (' + ','.join(repr(i[1]) for i in ls) + ');'
    return sql


'''
    功能：将字典数据写入数据库
    data：待写入数据库的数据，格式为字典列表
    address,user,pwd,db,table：待插入数据的数据库服务器地址，用户，密码，数据库，数据表
'''
def to_sql(data, address, user, pwd, db, table):
    connection = pymysql.connect(address, user, pwd, db)
    try:
        with connection.cursor() as cursor:
            sql = dict2sql(data, table)
            cursor.execute(sql)
        connection.commit()
    finally:
        connection.close()
        
        
'''
    功能: 读取keywords表，构建关键字字典
    result: 表中所有数据
    mainKey: 大类
    keys：关键字字典
'''
def getKeywords():
    connection = pymysql.connect(db["ip"], db["user"], db["pwd"], db["database"], cursorclass=pymysql.cursors.DictCursor)
    cursor = connection.cursor()
    cursor.execute("select * from keywords")
    result = cursor.fetchall()

    mainKey = []
    for key in result:
        if(key['pid'] == 0):
            mainKey.append(key)

    keywords = {}
    for key in mainKey:
        keywords[key['keyword']] = [key['keyword']]
        for i in result:
            if(i['pid'] == key['id']):
                keywords[key['keyword']].append(i['keyword'])
    
    return keywords


'''
    主函数
'''
def main():
    global keys
    keys = getKeywords()
    print("-------------------------\n" +
          "  正在准备新一轮数据更新   \n" +
          time.strftime("%Y-%m-%d %H:%M:%S") + "\n")
    urls = getURL('city')
    urlQueue = Queue(len(urls))
    for i in range(len(urls)):
        urlQueue.put(urls[i])
    dataQueue = Queue()

    writeLock = threading.Lock()

    # 三个采集线程的名字
    crawlList = ["采集线程%d号" % i for i in range(10)]
    # 存储三个采集线程
    threadcrawl = []
    for threadName in crawlList:
        thread = ThreadCrawl(threadName, urlQueue, dataQueue)
        thread.start()
        threadcrawl.append(thread)

    # 三个解析线程的名字
    parseList = ["解析线程%d号" % i for i in range(10)]
    # 存储三个解析线程
    threadparse = []
    for threadName in parseList:
        thread = ThreadParse(threadName, dataQueue, writeLock)
        thread.start()
        threadparse.append(thread)

    while not urlQueue.empty():
        pass

    # 如果urlQueue为空，采集线程退出循环
    global CRAWL_EXIT
    CRAWL_EXIT = True

    for thread in threadcrawl:
        thread.join()

    while not dataQueue.empty():
        pass

    # 如果dataQueue为空，采集线程退出循环
    global PARSE_EXIT
    PARSE_EXIT = True

    for thread in threadparse:
        thread.join()

    global count
    print("新一轮数据更新完毕，本次新增%d条数据\n" % count +
          time.strftime("%Y-%m-%d %H:%M:%S") +
          "\n--------------------------\n")


main()


# if __name__ == "__main__":
#     main()
