import traceback

from bs4 import BeautifulSoup
from os.path import join
import mobi
import shutil
import sys
import os



def parse(dir,outPath):
    count = 0
    for root,dirs,files in os.walk(dir):

        for file in files:
            try:
                #获取文件路径
                path = os.path.join(root,file)
                # print("文件路径为"+path)
                if path.endswith(".mobi"):
                    tempdir, filepath = mobi.extract(path)

                    # filepath里面是一个html
                    book = open(filepath,'r',encoding='utf-8')
                    soup = BeautifulSoup(book.read(), "html.parser")

                    # 解析好的文件路径
                    filename=path.split("/")[-1]
                    currentPath = os.getcwd()
                    # print("当前路径为"+currentPath)
                    # savePath = join(currentPath,"out",filename+".txt")
                    savePath = join(outPath,filename+".txt")
                    # print("解析好的文件路径"+savePath)
                    if os.path.exists(outPath):
                        pass
                    else:
                        os.makedirs(join(currentPath,"out"))
                    a = open(savePath,'w',encoding='utf-8')
                    a.write(soup.text)
                    a.close()
                    # print("删除临时目录"+tempdir)
                    shutil.rmtree(tempdir)
                    count = count+1

                    if count%10==0:
                        print("处理了"+str(count))
            except Exception as a:
                print(a)
                traceback.print_exc()
                continue

# python mobi2pdf.py inPath outPath
if __name__ == "__main__":

    unzip_file_path=sys.argv[1]
    outPath = sys.argv[2]
    if os.path.isdir(unzip_file_path):
        print("it's a directory")
        parse(unzip_file_path,outPath)
    elif os.path.isfile(unzip_file_path):
        print("it's a normal file")



