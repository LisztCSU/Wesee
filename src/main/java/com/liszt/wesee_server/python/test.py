#coding=utf-8
import requests
from lxml import etree
import sys



headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36",
    "Referer": "https://www.douban.com/"
}
douban_url = "https://movie.douban.com/cinema/nowplaying/"+sys.argv[0]+"/"
response = requests.get(douban_url, headers=headers)
douban_text = response.text

html_element = etree.HTML(douban_text)
ul = html_element.xpath('//ul[@class="lists"]')[0]
lis = ul.xpath('./li')
movies = []
for li in lis:
    id = li.xpath('./@data-subject')[0]
    title = li.xpath('./@data-title')[0]
    score = li.xpath('./@data-score')[0]
    star = li.xpath('./@data-star')[0]
    duration = li.xpath('./@data-duration')[0]
    votecount = li.xpath('./@data-votecount')[0]
    region = li.xpath('./@data-region')[0]
    director = li.xpath('./@data-director')[0]
    actors = li.xpath('./@data-actors')[0]
    post = li.xpath('.//img/@src')[0]
    movie = (
         id,
         title,
         score,
         star,
         duration,
         votecount,
         region,
         director,
         actors+" ",
         post
    )
    movies.append(movie)
with open('E:\\IDEA\\android\\wesee_server\\src\\main\\java\\com\\youxiang\\wesee_server\\python\\movies.txt', 'w+',encoding='utf-8') as fp:
    fp.write('\n'.join('%s,%s,%s,%s,%s,%s,%s,%s,%s,%s' % x for x in movies))

  
