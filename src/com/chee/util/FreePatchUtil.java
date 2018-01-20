package com.chee.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class FreePatchUtil {
    public static String patchFile = "D:/updaate/patch.txt";
    public static String projectPath = "F:/workspace/myeclipse/study/jeewx-os/cheerfull";
    public static String webContent = "WebRoot";
    public static String classPath = "F:/workspace/myeclipse/study/jeewx-os/cheerfull/WebRoot/WEB-INF/classes";
    public static String desPath = "d:/updaate/full";
    public static String version = "20180120";

    public static void main(String[] args) throws Exception {
        copyFiles(getPatchFileList());
    }

    public static List<String> getPatchFileList() throws Exception {
        List<String> fileList = new ArrayList();
        FileInputStream f = new FileInputStream(patchFile);
        BufferedReader dr = new BufferedReader(new InputStreamReader(f, "utf-8"));
        String line;
        while ((line = dr.readLine()) != null) {
            if (line.indexOf("Index:") != -1) {
                line = line.replaceAll(" ", "");
                line = line.substring(line.indexOf(":") + 1, line.length());
                fileList.add(line);
            }
        }
        return fileList;
    }

    public static void copyFiles(List<String> list) {
        for (String fullFileName : list) {
            if (fullFileName.indexOf("src/") != -1) {
                String fileName = fullFileName.replace("src", "");
                fullFileName = classPath + fileName;
                if (fileName.endsWith(".java")) {
                    fileName = fileName.replace(".java", ".class");
                    fullFileName = fullFileName.replace(".java", ".class");
                }
                String tempDesPath = fileName.substring(0, fileName.lastIndexOf("/"));
                String desFilePathStr = desPath + "/" + version + "/WEB-INF/classes" + tempDesPath;
                String desFileNameStr = desPath + "/" + version + "/WEB-INF/classes" + fileName;
                File desFilePath = new File(desFilePathStr);
                if (!desFilePath.exists()) {
                    desFilePath.mkdirs();
                }
                copyFile(fullFileName, desFileNameStr);
                System.out.println(fullFileName + "复制完成");
            } else {
                String desFileName = fullFileName.replaceAll(webContent, "");
                fullFileName = projectPath + "/" + fullFileName;
                String fullDesFileNameStr = desPath + "/" + version + desFileName;
                String desFilePathStr = fullDesFileNameStr.substring(0, fullDesFileNameStr.lastIndexOf("/"));
                File desFilePath = new File(desFilePathStr);
                if (!desFilePath.exists()) {
                    desFilePath.mkdirs();
                }
                copyFile(fullFileName, fullDesFileNameStr);
                System.out.println(fullDesFileNameStr + "复制完成");
            }
        }
    }

    private static void copyFile(String sourceFileNameStr, String desFileNameStr) {
        File srcFile = new File(sourceFileNameStr);
        File desFile = new File(desFileNameStr);
        try {
            copyFile(srcFile, desFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            byte[] b = new byte[5120];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } finally {
            if (inBuff != null) {
                inBuff.close();
            }
            if (outBuff != null) {
                outBuff.close();
            }
        }
    }
}
