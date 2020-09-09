package com.laibo.data;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class POIUtil {
    public static List<String> readWord(String filePath) throws Exception{

        List<String> linList = new ArrayList<String>();
        String buffer = "";
        try {
            if (filePath.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(filePath));
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
                if(buffer.length() > 0){
                    //使用回车换行符分割字符串
                    String [] arry = buffer.split("\\r\\n");
                    for (String string : arry) {
                        linList.add(string.trim());
                    }
                }
            } else if (filePath.endsWith(".docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();

                if(buffer.length() > 0){
                    //使用换行符分割字符串
                    String [] arry = buffer.split("\\n");
                    for (String string : arry) {
                        linList.add(string.trim());
                    }
                }
            } else {
                return null;
            }

            return linList;
        } catch (Exception e) {
            System.err.print("error---->"+filePath);
            e.printStackTrace();
            return null;
        }
    }


    public static void docs2Txts(String filePath,String outPath) throws Exception {
        File file = new File(filePath);
        File outFile = new File(outPath);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filePath + "\\" + filelist[i]);
                doc2Txt(readfile,outPath);
            }
        }
    }

    public static void doc2Txt(File file,String outPath) throws Exception{
        String buffer = "";
        try {
            if (file.toString().endsWith(".doc")) {
                InputStream is = new FileInputStream(file);
                String name = file.getName();
                FileOutputStream out = new FileOutputStream(outPath+"/"+name+".txt");
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
                is.close();
                if(buffer.length() > 0){
                    //使用回车换行符分割字符串
                    String [] arry = buffer.split("\\r\\n");
                    for (String string : arry) {
                        byte[] bytes = string.trim().getBytes();
                        for(int i=0; i<bytes.length; i++) {
                            out.write(bytes[i]);
                        }
                    }
                }
                out.close();
            } else if (file.toString().endsWith(".docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(file.toString());
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
                String name = file.getName().substring(0, file.getName().indexOf("."));
                FileOutputStream out = new FileOutputStream(outPath+"/"+name+".txt");

                if(buffer.length() > 0){
                    //使用换行符分割字符串
                    String [] arry = buffer.split("\\n");
                    for (String string : arry) {
                        byte[] bytes = string.trim().getBytes();
                        for(int i=0; i<bytes.length; i++) {
                            out.write(bytes[i]);
                        }
                    }
                }
                out.close();
            }
        } catch (Exception e) {
            System.err.print("error---->"+file.toString());
            e.printStackTrace();
        }
    }

}