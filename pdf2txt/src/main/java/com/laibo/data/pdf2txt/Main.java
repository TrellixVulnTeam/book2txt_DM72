package com.laibo.data.pdf2txt;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String inPath = "D:\\IdeaProjects\\book2txt\\pdf2txt\\src\\main\\resources\\in";
        String outPath = "D:\\IdeaProjects\\book2txt\\pdf2txt\\src\\main\\resources\\out";
        pdfs2Txts(inPath,outPath);
        System.out.println(System.currentTimeMillis()-start);
    }


    private static void pdfs2Txts(String inPath, String outPath) throws IOException {
        File inFile = new File(inPath);
        File outFile = new File(outPath);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        if (inFile.isDirectory()) {
            String[] filelist = inFile.list();
            for (int i = 0; i < filelist.length; i++) {
                try {
                    File readfile = new File(inPath + "/" + filelist[i]);
                    String content = getTextFormPDF(FileUtils.readFileToByteArray(readfile));
                    FileUtils.write(new File(outPath + "/" + readfile.getName() + ".txt"), content, "utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    private static String getTextFormPDF(byte[] file) {
        String text = "";
        PDDocument pdfdoc = null;
        InputStream is;
        try {
            is = new ByteArrayInputStream(file);
            pdfdoc = PDDocument.load(is);
            PDFTextStripper stripper = new PDFTextStripper();
            text = stripper.getText(pdfdoc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pdfdoc != null) {
                    pdfdoc.close();
                }
            } catch (Exception e) {
            }
        }
        return text;
    }
}