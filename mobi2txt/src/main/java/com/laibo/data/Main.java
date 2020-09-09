package com.laibo.data;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStreamReader is = new InputStreamReader(new FileInputStream(new File("")),"UTF-8");
        BufferedReader br = new BufferedReader(is);

        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(new File("")), "GBK");
        BufferedWriter bw = new BufferedWriter(os);

        int len;
        char[] arr = new char[1024*1024];						//创建字符数组
        while((len = br.read(arr)) != -1) {					//将数据读到字符数组中
            bw.write(arr);					//从字符数组将数据写到文件上
        }

        br.close();
        os.close();
        bw.close();
        is.close();
    }
}
