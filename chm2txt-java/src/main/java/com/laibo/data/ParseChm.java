package com.laibo.data;
import java.io.*;


import org.chm4j.*;
//
//import cn.lswe.baseframe.validator.Conf;

public class ParseChm {

    public static void main(String[] args) throws IOException {
        ChmHandle.parseAll(new File("D:\\IdeaProjects\\book2txt\\chm2txt\\in"),new File("D:\\IdeaProjects\\book2txt\\chm2txt-java\\src\\main\\resources"));

        File file = new File("D:\\IdeaProjects\\book2txt\\chm2txt-java\\src\\main\\resources\\1");

    }
}