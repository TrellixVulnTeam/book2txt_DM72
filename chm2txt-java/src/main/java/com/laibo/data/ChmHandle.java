package com.laibo.data;

import com.sun.deploy.util.ArrayUtil;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class ChmHandle {

    public static void parseOne(File inFile,File outFile) {
        try {
            Process exec = Runtime.getRuntime().exec("hh.exe -decompile " + outFile.getAbsolutePath() + " " + inFile.getAbsolutePath());
            exec.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void parseAll(File inFile, File outFile) throws IOException {
        //判断是否存在总的输出文件夹
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        String baseInPath = inFile.getPath();
        if (!inFile.isDirectory()) {
            System.out.println("需要输入文件夹");
            return;
        } else {
            //遍历输入文件
            for (String file : inFile.list()) {
                //解析单个文件成html
                if (file.endsWith(".chm") || file.endsWith(".CHM")) {
                    File file1 = new File(baseInPath+"/"+file);
                    String name = file1.getName();
                    String oneOutStringName = name.substring(0, name.indexOf("."));
                    //创建单个文件的解析临时输出文件夹
                    String tempHtmlDir = outFile.getAbsolutePath() + "/" + oneOutStringName;
                    File file2 = new File(tempHtmlDir);
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                    parseOne(file1,file2);

                    //html按顺序合并成txt写入总的文件夹
                    new ChmHandle().MergeHTML(file2);
                    //删除临时文件夹
                }
            }
        }
    }

    private void MergeHTML(File needMergeFile) throws IOException {

        String[] list = needMergeFile.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".htm")||name.endsWith(".html");
            }
        });

        String root = needMergeFile.getPath();
        ArrayList<String> tempHtmlNames = new ArrayList<String>();
        for (String s : list) {
            if (s.endsWith(".htm") || s.endsWith(".html")) {
                tempHtmlNames.add(s);
            }
        }

        //升序
        tempHtmlNames.sort((String o1, String o2)-> {
            return o1.compareTo(o2);
        });

        StringBuffer sb = new StringBuffer();

        if (tempHtmlNames.contains("index.htm")) {
            FileInputStream fis = new FileInputStream(new File(root + "/" + "index.htm"));
            InputStreamReader reader = new InputStreamReader(fis,"GB2312");
            int len;
            char[] arr = new char[1024*1024];						//创建字符数组
            while((len = reader.read(arr)) != -1) {					//将数据读到字符数组中
                sb.append(Jsoup.parse(new String(arr)).text());
            }
            fis.close();
            reader.close();
        }
        for (String tempHtmlName : tempHtmlNames) {
            if ("index.htm".endsWith(tempHtmlName)||"about.htm".endsWith(tempHtmlName)) {
                continue;
            }
            FileInputStream fis = new FileInputStream(new File(root + "/"+tempHtmlName));
            InputStreamReader reader = new InputStreamReader(fis,"GB2312");
            int len;
            char[] arr = new char[1024*1024];						//创建字符数组
            while((len = reader.read(arr)) != -1) {					//将数据读到字符数组中
                sb.append(Jsoup.parse(new String(arr)).text());
            }
            fis.close();
            reader.close();
        }


        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(new File(needMergeFile.getParent() + "/" + needMergeFile.getName() + ".txt")), "utf-8");
        BufferedWriter bw = new BufferedWriter(os);
        bw.write(sb.toString(),0,sb.length());
        bw.close();
        os.close();
    }
}
