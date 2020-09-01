package com.laibo.data;

import com.sun.deploy.util.ArrayUtil;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ChmHandle {
    public static void parseOne(File inFile,File outFile) {
        try {
            //hh.exe是Windows系统中允许chm文件的程序
            Runtime.getRuntime().exec("hh.exe -decompile "+outFile.getAbsolutePath()+" "+inFile.getAbsolutePath());
        } catch (IOException e) {
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
                    parseOne(file1,file2);
                    //html按顺序合并成txt写入总的文件夹
                    new ChmHandle().MergeHTML(file2);
                    //删除临时文件夹
                }
            }
        }
    }

    private  void MergeHTML(File needMergeFile) throws IOException {

        String[] list = needMergeFile.list();

        String root = needMergeFile.getPath();
        ArrayList<String> tempHtmlNames = new ArrayList<String>();
        for (String s : list) {
            if (s.endsWith(".htm") || s.endsWith(".html")) {
                tempHtmlNames.add(s);
            }
        }

        tempHtmlNames.sort((String o1, String o2)-> {
            return 0;
        });

        for (String tempHtmlName : tempHtmlNames) {
            System.out.println(tempHtmlName);
        }

        StringBuffer sb = new StringBuffer();
        if (tempHtmlNames.contains("index.htm")) {
            FileInputStream fis = new FileInputStream(new File(root + "/" + "index.htm"));
            InputStreamReader reader = new InputStreamReader(fis,"GB2312");
            int len;
            char[] arr = new char[1024*1024];						//创建字符数组
            while((len = reader.read(arr)) != -1) {					//将数据读到字符数组中
                sb.append(Jsoup.parse(arr.toString()).text());
            }
            fis.close();
            reader.close();
        }
        for (String tempHtmlName : tempHtmlNames) {
            if ("index.htm".equals(tempHtmlName)) {
                continue;
            }
            FileInputStream fis = new FileInputStream(new File(root + tempHtmlName));
            InputStreamReader reader = new InputStreamReader(fis,"GB2312");
            int len;
            char[] arr = new char[1024*1024];						//创建字符数组
            while((len = reader.read(arr)) != -1) {					//将数据读到字符数组中
                System.out.println(Jsoup.parse(arr.toString()).text());
                sb.append(Jsoup.parse(arr.toString()).text());
                System.out.println(sb);
            }
        }


    }
}
