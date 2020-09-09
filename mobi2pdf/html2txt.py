# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
book = open('D:\\IdeaProjects\\book2txt\\mobi2pdf\\book.html','r',encoding='utf-8')
# print(book.read())
soup = BeautifulSoup(book.read(), "html.parser")
print(soup.text)

a = open('book.txt','w',encoding='utf-8')
a.write(soup.text)
