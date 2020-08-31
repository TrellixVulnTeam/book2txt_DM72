package com.laibo.data;
/**
 * <p>Description:POIUtil 工具类</p>
 * <p>Copyright: Copyright (c)2019</p>
 * <p>Company: Tope</p>
 * <P>@version 1.0</P>
 */
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

    public static void doc2Txt(String filePath) throws Exception{

//        List<String> linList = new ArrayList<String>();
        String buffer = "";
        try {
            if (filePath.endsWith(".doc")) {
                File file = new File(filePath);
                InputStream is = new FileInputStream(file);
                String parent = file.getParent();
                String name = file.getName().substring(0, file.getName().indexOf("."));
                FileOutputStream out = new FileOutputStream(parent+"/"+name+".txt");
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
                if(buffer.length() > 0){
                    //使用回车换行符分割字符串
                    String [] arry = buffer.split("\\r\\n");
                    for (String string : arry) {
//                        linList.add(string.trim());
                        byte[] bytes = string.trim().getBytes();
                        for(int i=0; i<bytes.length; i++) {
                            out.write(bytes[i]);
                        }
                    }
                }
                out.close();
            } else if (filePath.endsWith(".docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();

                File file = new File(filePath);
                String parent = file.getParent();
                String name = file.getName().substring(0, file.getName().indexOf("."));
                FileOutputStream out = new FileOutputStream(parent+"/"+name+".txt");

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
            }
        } catch (Exception e) {
            System.err.print("error---->"+filePath);
            e.printStackTrace();
        }
    }

}