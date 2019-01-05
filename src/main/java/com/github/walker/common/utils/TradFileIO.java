package com.github.walker.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 随机访问文件类
 *
 * @author HuQingmiao
 */
public class TradFileIO {

    private final static Logger log = LoggerFactory.getLogger(TradFileIO.class);

    /**
     * @param file     目标文件
     * @param append   true:追加方式写入 false:新建文件写入
     * @param lineList
     * @throws Exception
     */
    public static void writeOut(File file, boolean append, List<String> lineList) throws IOException {
        if (lineList.isEmpty()) {
            return;
        }
        File dirc = file.getParentFile();
        if (!dirc.exists()) {
            dirc.mkdirs();
        }

        if (!append) {
            if (file.exists()) {
                file.delete();
            }
        }
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, append);
            bw = new BufferedWriter(fw);

            for (String line : lineList) {
                bw.append(line);
                bw.append('\n');
                bw.flush();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
            if (fw != null) {
                fw.close();
            }
        }
    }

    /**
     * @param file   目标文件
     * @param append true:追加方式写入 false:新建文件写入
     * @param str
     * @throws Exception
     */
    public static void writeOut(File file, boolean append, String str) throws IOException {
        if (str == null || "".equals(str)) {
            return;
        }
        File dirc = file.getParentFile();
        if (!dirc.exists()) {
            dirc.mkdirs();
        }
        if (!append) {
            if (file.exists()) {
                file.delete();
            }
        }
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, append);
            bw = new BufferedWriter(fw);
            bw.append(str);
            bw.flush();
        } finally {
            if (bw != null) {
                bw.close();
            }
            if (fw != null) {
                fw.close();
            }
        }
    }


    public static File writeOut(String filename, String content) throws IOException {
        if (content == null || "".equals(content)) {
            content = "";
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(filename);
            osw = new OutputStreamWriter(fos, "UTF-8");
//            bw = new BufferedWriter(osw);
//            bw.append(content);
//            bw.flush();
            osw.write(content);
            osw.flush();

            return new File(filename);

        } finally {
//            if (bw != null) {
//                bw.close();
//            }
            if (osw != null) {
                osw.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * @param file 目标文件
     * @throws Exception
     */
    public static String readIn(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file.getCanonicalPath());
            isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return sb.toString();
    }

    public static String readIn(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isReader = null;
        BufferedReader br = null;
        try {
            isReader = new InputStreamReader(in, "UTF-8");
            br = new BufferedReader(isReader);
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
        } finally {
            if (br != null) {
                br.close();
            }
            if (isReader != null) {
                isReader.close();
            }
        }
        return sb.toString();
    }


    public static void main(String args[]) {
        try {
            File f = new File("d:/b/SrcRecvApp.txt");

            System.out.println(f.getParentFile().getAbsoluteFile());

            List<String> outList = new ArrayList<String>();
            outList.add("13228219126|110000001925073|||");
            outList.add("13188457889|110000003471309|810001892906|闫泓旭|");

            writeOut(f, false, outList);

            String pubKeyStr = readIn(new File("d:/pub.txt"));
            System.out.println(">>pubKeyStr: \n" + pubKeyStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
