package com.laibo.data;
import java.io.*;


import org.chm4j.*;
//
//import cn.lswe.baseframe.validator.Conf;

public class ParseChm {

    public static void main(String[] args) throws IOException {
//      ChmHandle.parse(new File("D:\\IdeaProjects\\book2txt\\chm2txt\\in\\婚姻之痒 - 副本.CHM"),new File("D:\\IdeaProjects\\book2txt\\chm2txt\\out"));
        ChmHandle.parseAll(new File("D:\\IdeaProjects\\book2txt\\chm2txt\\in"),new File("D:\\IdeaProjects\\book2txt\\chm2txt\\out"));
    }
}