package com.chee.util;

import com.jfinal.config.Constants;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtils {
    public static String getSuffix(String fileName) {
        if ((fileName != null) && (fileName.contains("."))) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return null;
    }

    public static String removePrefix(String src, String prefix) {
        if ((src != null) && (src.startsWith(prefix))) {
            return src.substring(prefix.length());
        }
        return src;
    }

    public static String removeRootPath(String src) {
        return removePrefix(src, PathKit.getWebRootPath());
    }

    public static String readString(File file) {
        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int len = 0; (len = fis.read(buffer)) > 0;) {
                baos.write(buffer, 0, len);
            }
            return new String(baos.toByteArray(), JFinal.me().getConstants().getEncoding());
        } catch (Exception localException) {
        } finally {
            close(fis, baos);
        }
        return null;
    }

    public static void writeString(File file, String string) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            fos.write(string.getBytes(JFinal.me().getConstants().getEncoding()));
        } catch (Exception localException) {
        } finally {
            close(null, fos);
        }
    }

    private static void close(InputStream is, OutputStream os) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException localIOException) {
            }
        }
        if (os != null) {
            try {
                os.close();
            } catch (IOException localIOException1) {
            }
        }
    }

    public static void unzip(String zipFilePath) throws IOException {
        String targetPath = zipFilePath.substring(0, zipFilePath.lastIndexOf("."));
        unzip(zipFilePath, targetPath);
    }

    public static void unzip(String zipFilePath, String targetPath) throws IOException {
        ZipFile zipFile = new ZipFile(zipFilePath);
        try {
            Enumeration<?> entryEnum = zipFile.entries();
            if (entryEnum != null) {
                while (entryEnum.hasMoreElements()) {
                    OutputStream os = null;
                    InputStream is = null;
                    try {
                        ZipEntry zipEntry = (ZipEntry) entryEnum.nextElement();
                        if (!zipEntry.isDirectory()) {
                            File targetFile = new File(targetPath + File.separator + zipEntry.getName());
                            if (!targetFile.getParentFile().exists()) {
                                targetFile.getParentFile().mkdirs();
                            }
                            os = new BufferedOutputStream(new FileOutputStream(targetFile));
                            is = zipFile.getInputStream(zipEntry);
                            byte[] buffer = new byte[4096];
                            int readLen = 0;
                            while ((readLen = is.read(buffer, 0, 4096)) > 0) {
                                os.write(buffer, 0, readLen);
                            }
                        }
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                        if (os != null) {
                            os.close();
                        }
                    }
                }
            }
        } finally {
            zipFile.close();
        }
    }
}
