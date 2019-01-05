package com.github.walker.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * 大文件读取示例类
 *
 * Created by HuQingmiao on 2015-8-20.
 */
public class BigFileReader {

    private static Logger log = LoggerFactory.getLogger(TradFileIO.class);

    public static void main(String args[]) {
        File inFile = new File("D:/CHK_106000_CES_20150718.dat");
        //File inFile = new File("D:/b.txt");

        try {
            read2(inFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("OK!!!");
    }


    public static void read1(File inFile) throws IOException {
        FileInputStream is = null;
        Scanner scanner = null;
        try {
            is = new FileInputStream(inFile);
            scanner = new Scanner(is, "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                //读一行，处理一行
                System.out.println(line);
            }
        } catch (IOException e) {
            log.error("", e);
            throw e;
        } finally {
            try {
                if (scanner != null) {
                    scanner.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }


    public static void read2(File inFile) throws IOException {
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(inFile, "UTF-8");
            while (it.hasNext()) {
                String line = it.nextLine();

                //读一行，处理一行
                System.out.println(line);
            }
        } catch (IOException e) {
            log.error("", e);
            throw e;
        } finally {
            LineIterator.closeQuietly(it);
        }
    }
}

