package com.github.walker.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by HuQingmiao on 2016/5/14 0014.
 */
public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);


    /**
     * 复制文件
     *
     * @param sorceFilename
     * @param targetFilename
     * @author HuQingmiao
     */
    public static void copyFile(String sorceFilename, String targetFilename) throws IOException {
        sorceFilename = sorceFilename.replace("/", File.separator);
        targetFilename = targetFilename.replace("/", File.separator);
        FileInputStream source = null;
        FileOutputStream target = null;
        try {
            File f1 = new File(sorceFilename);
            File f2 = new File(targetFilename);

            if (targetFilename.indexOf(File.separator) > 0) {
                File dirc = new File(targetFilename.substring(0, targetFilename
                        .lastIndexOf(File.separator)));
                if (!dirc.exists()) {
                    dirc.mkdirs();
                }
            }

            source = new FileInputStream(f1);
            target = new FileOutputStream(f2);
            //	      BufferedInputStream bin = new BufferedInputStream(source);
            //	      BufferedOutputStream bout = new BufferedOutputStream(target);
            byte[] b = new byte[1024 * 5];
            int data;
            while ((data = source.read(b)) != -1) {
                target.write(b, 0, data);
                //	        bout.write(data,0,data);
            }
            source.close();
            target.close();
        } catch (IOException e) {
            //log.error(e.getMessage(),e);
            throw e;
        } finally {
            if (source != null) {
                try {
                    source.close();
                } catch (Exception e) {
                }
            }
            if (target != null) {
                try {
                    target.close();
                } catch (Exception e) {
                }
            }
        }
    }


    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(File file) throws IOException {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            //log.error(e.getMessage(),e);
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            //log.error(e.getMessage(),e);
            throw e;
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static File getFile(byte[] bfile, String absFilename) throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = new File(absFilename);
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (IOException e) {
            //e.printStackTrace();
            throw e;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }


    /**
     * 将指定的文件移至出错目录, 丢弃目录, 备份目录.
     * <p>
     * 当参数 type为'error'时, 表示移入出错目录;
     * 当参数 type为'abandon'时, 表示移入丢弃目录;
     * 当参数 type为'bak'时, 表示移入备份目录;
     *
     * @param file
     * @param type error, abandon, bak
     */
    public static void move(File file, String type) {

        // 取得本报文所在的目录
        File dirc = file.getParentFile();

        // 取得报文错误备份目录
        File toDirc = new File(dirc + "_" + type);

        if (!toDirc.exists()) {
            toDirc.mkdir();
        }
        File bakFile = new File(toDirc.getAbsolutePath() + File.separator
                + file.getName());

        // 如果错误备份目录中已存在该文件, 则将其删除
        if (bakFile.exists()) {
            bakFile.delete();
        }

        // 将报文移入错误备份目录
        file.renameTo(bakFile);// 改名,相当于剪切
    }

    /**
     * 列出指定目录及其子目录下的文件名符合正则表达式的文件
     *
     * @param dirc
     * @param regex
     * @param filesOfDirc
     */
    public static void listAllFiles(File dirc, String regex, ArrayList<File> filesOfDirc) {
        if (!dirc.isDirectory()) {
            return;
        }
        File[] files = dirc.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                if (f.getName().matches(regex)) {
                    filesOfDirc.add(f);
                }
            } else {
                listAllFiles(f, regex, filesOfDirc);
            }
        }
    }

    /**
     * 获取文件所有的根目录名
     * @param file
     * @return
     * @throws IOException
     */
    public static String getRootPath(File file) throws IOException {
        File root = file;
        while (root != null) {
            root = root.getParentFile();
        }
        return root.getCanonicalPath();
    }

    public static void main(String args[]) {
        ArrayList<File> files = new ArrayList<File>();
        listAllFiles(new File("d:/projects/srctra"), ".*", files);

        log.info(">> ");
        for (File f : files) {
            log.info(f.getName());
        }
        log.info(">> ");

        File f = new File("d:/a.txt");
        log.info(">>" + f.getParentFile().getParent());
    }


}
