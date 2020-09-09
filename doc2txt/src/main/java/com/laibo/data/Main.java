package com.laibo.data;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        String s = "D:\\IdeaProjects\\book2txt\\doc2txt\\src\\main\\resources";
        String out = "D:\\IdeaProjects\\book2txt\\doc2txt\\src\\main\\resources\\out";
        POIUtil.docs2Txts(s,out);
    }
}
