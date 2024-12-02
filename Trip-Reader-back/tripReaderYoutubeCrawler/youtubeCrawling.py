'''
0. 환경설정
conda env create -f tripReader environment.yml
1. sql 로 쿼리 날려서 csv로 저장
select fe.title, ns.news_summary_key from news_summary ns join festival_events fe on ns.contentid=fe.contentid;
2. 파일명 festival_events.csv
3. 실행
'''

import sys
import os
import re
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.options import Options
from bs4 import BeautifulSoup
import time

python_file_path = os.path.dirname(os.path.abspath(__file__))
input_file_name = os.path.join(python_file_path , "festival_events.csv")
output_file_name = os.path.join(python_file_path , "output.sql")

TITLE = 0
NEWS_SUMMARY_KEY = 1
DELEY = 2

chrome_options = Options()
chrome_options.add_experimental_option("detach", True)

# 불필요한 에러 메시지 없애기
chrome_options.add_experimental_option("excludeSwitches", ["enable-logging"])

# 브라우저 생성
browser = webdriver.Chrome(options=chrome_options)
browser.get('https://www.youtube.com/')

time.sleep(DELEY);
def requestQuery(query):
    search = browser.find_element(By.NAME, "search_query") # 이 아이디에 해당하는 걸 찾아
    search.clear() # 
    search.send_keys(query) # 키워드 넣고
    search.send_keys(Keys.ENTER) # 엔터키 쳐주기
    present_url = browser.current_url
    browser.get(present_url) # 작업창을 기본 유튭에서 검색한 창으로 교체
    time.sleep(DELEY);

def getData():
    html_source = browser.page_source
    soup = BeautifulSoup(html_source, 'html.parser')
    results = soup.select("#contents > ytd-video-renderer")
    ret = []
    for element in results :
        title = element.select_one('#video-title')['title']
        href = element.select_one('#video-title')['href'].replace("/watch?v=","")
        if(href.find("shorts")!=-1):
            continue
        href = href[:11]
        ret.append([title, href])
    return ret

def getPureText(input_str):
    return re.sub(r"[^\uAC00-\uD7A30-9a-zA-Z\s]", "", input_str)

def getTitles(input_str):
    ret = []
    for (i,rawRow) in enumerate(input_str.split('\n')):
        if(rawRow == ''):
            continue
        row = rawRow.split(',')
        ret.append(getPureText(row[TITLE]))
    return ret

with open(input_file_name, "r") as input_file, open(output_file_name, "w") as output_file:
    input_data = input_file.read()
    out = "INSERT INTO attraction_video (news_summary_key, url) VALUES\n"
    for (i,rawRow) in enumerate(input_data.split('\n')):
        if(rawRow == ''):
            continue
        row = rawRow.split(',')
        title = getPureText(','.join(row[TITLE:-1]))
        requestQuery(title)
        datas = getData()
        for data in datas:
            out += f"\t({row[-1]}, \"{data[1]}\"),\n"
    output_file.write(out[:-2] + ";")