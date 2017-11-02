package com.chee.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileUtil {
    public static Logger logger = Logger.getLogger(FileUtil.class);

    public static float getRound(int dSource, int n) {
        BigDecimal deSource = new BigDecimal(dSource / 1024.0D);
        float fRound = deSource.setScale(n, 3).floatValue();
        return fRound;
    }

    public static boolean saveUploadFile(byte[] data, int size, String saveFilePath) {
        BufferedOutputStream bout = null;
        boolean bresult;
        try {
            bout = new BufferedOutputStream(new FileOutputStream(saveFilePath));
            bout.write(data, 0, size);
            bout.close();
            bout = null;
            bresult = true;
        } catch (Exception e) {
            logger.error("保存文件失败");
            e.printStackTrace();
            if (bout != null) {
                try {
                    bout.close();
                    bout = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            bresult = false;
        }
        return bresult;
    }

    public static byte[] toBytes(Object[] params) throws IOException {
        byte[] data = (byte[]) null;
        if (params == null) {
            return data;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            Object[] arrayOfObject = params;
            int j = params.length;
            for (int i = 0; i < j; i++) {
                Object obj = arrayOfObject[i];
                if ((obj instanceof Integer)) {
                    dos.writeInt(((Integer) obj).intValue());
                } else if ((obj instanceof String)) {
                    dos.writeUTF(obj.toString());
                } else if ((obj instanceof Double)) {
                    dos.writeDouble(((Double) obj).doubleValue());
                } else if ((obj instanceof Float)) {
                    dos.writeFloat(((Float) obj).floatValue());
                } else if ((obj instanceof Long)) {
                    dos.writeLong(((Long) obj).longValue());
                } else if ((obj instanceof Boolean)) {
                    dos.writeBoolean(((Boolean) obj).booleanValue());
                } else if ((obj instanceof Byte)) {
                    dos.writeByte(((Byte) obj).byteValue());
                } else if ((obj instanceof Short)) {
                    dos.writeShort(Short.parseShort(obj.toString()));
                } else if ((obj instanceof byte[])) {
                    dos.write((byte[]) obj);
                } else if ((obj instanceof Byte[])) {
                    dos.write((byte[]) obj);
                }
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("toBytes fail: " + e.getMessage());
        }
    }


    public static boolean saveFile(ByteArrayOutputStream baos, String savePath) throws IOException {
        BufferedOutputStream bos = null;
        boolean bResult;
        try {
            File outputFile = new File(savePath);
            bos = new BufferedOutputStream(new FileOutputStream(outputFile));
            bos.write(baos.toByteArray());
            bResult = true;
        } catch (IOException e) {
            e.printStackTrace();
            bResult = false;
        } finally {
            if (bos != null) {
                bos.close();
            }
        }
        return bResult;
    }

    public static String getRootPath() {
        String rootPath = "";
        String strClassesPath = FileUtil.class.getResource("/").toString().replace("file:/", "");
        if (File.separator.equals("\\")) {
            rootPath = strClassesPath.substring(0, strClassesPath.indexOf("WEB-INF")).replace('/', '\\');
        } else {
            rootPath = File.separator + strClassesPath.substring(0, strClassesPath.indexOf("WEB-INF"));
        }
        return rootPath;
    }

    public static String buildUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18);
    }

    public static boolean createFileByByte(byte[] buff, String filenamepath) {
        try {
            FileOutputStream fos = new FileOutputStream(filenamepath);
            InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(buff));

            byte[] rbuff = new byte[1024];
            int ri;
            while ((ri = in.read(rbuff)) > 0) {
                fos.write(rbuff, 0, ri);
            }
            in.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Create File Error!");
        }
        return false;
    }

    public static byte[] readFileBytes(String filenamepath) {
        byte[] rbuff = (byte[]) null;
        try {
            if (StringUtils.isNotEmpty(filenamepath)) {
                FileInputStream fis = new FileInputStream(filenamepath);
                byte[] buff = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DeflaterOutputStream out = new DeflaterOutputStream(baos);
                int rednum;
                while ((rednum = fis.read(buff)) > 0) {
                    out.write(buff, 0, rednum);
                }
                out.finish();
                out.close();
                rbuff = baos.toByteArray();
                baos.reset();
                baos.close();
                fis.close();
            }
            return rbuff;
        } catch (Exception e) {
            logger.error("Read File Error!");
        }
        return new byte[0];
    }

    public static String getFilename(String filenamepath) {
        File file = new File(filenamepath);
        return file.getName();
    }

    public static byte[] readFile(String path) {
        File file = new File(path);
        if (file.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buff = new byte[1024];
                int len;
                while ((len = fis.read(buff)) > 0) {
                    baos.write(buff, 0, len);
                }
                buff = baos.toByteArray();
                baos.reset();
                fis.close();
                baos.close();
                return buff;
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[1024];
            }
        }
        return new byte[1024];
    }

    public static void deflaterFile(String filenamepath, OutputStream os) {
        try {
            FileInputStream fis = new FileInputStream(filenamepath);
            byte[] buff = new byte[1024];
            DeflaterOutputStream out = new DeflaterOutputStream(os);
            int rednum;
            while ((rednum = fis.read(buff)) > 0) {
                out.write(buff, 0, rednum);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean inflaterFile(InputStream is, String filenamepath) {
        try {
            FileOutputStream fos = new FileOutputStream(filenamepath);
            InflaterInputStream iis = new InflaterInputStream(is);
            byte[] buff = new byte[1024];
            int rnum;
            while ((rnum = iis.read(buff)) > 0) {
                fos.write(buff, 0, rnum);
            }
            fos.close();
            iis.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteFile(String path) {
        try {
            File file = new File(path);
            if ((!file.exists()) || (!file.isDirectory())) {
                file.mkdirs();
            }
            String[] tempList = file.list();
            for (String aTempList : tempList) {
                File temp;
                if (path.endsWith(File.separator)) {
                    temp = new File(path + aTempList);
                } else {
                    temp = new File(path + File.separator + aTempList);
                }
                if (temp.isFile()) {
                    temp.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean createImageFileByByte(byte[] buff, String filenamepath) {
        try {
            File file = new File(filenamepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filenamepath);
            ByteArrayInputStream in = new ByteArrayInputStream(buff);

            byte[] rbuff = new byte[1024];
            int ri;
            while ((ri = in.read(rbuff)) > 0) {
                fos.write(rbuff, 0, ri);
            }
            in.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Create File Error!");
        }
        return false;
    }

    public static String getFileBase64Str(String urlString) {
        String urlStr = EncodeUtils.Utf8URLencode(urlString);
        if (StringUtils.isEmpty(urlString)) {
            logger.info("读取URL文件图片地址为空: " + urlString);
            return null;
        }
        if (urlStr.indexOf(":") == -1) {
            logger.info("读取URL文件图片地址不正确: " + urlString);
            return null;
        }
        String requestType = urlStr.substring(0, urlStr.indexOf(":"));
        if ((!org.apache.commons.lang.StringUtils.equalsIgnoreCase("ftp", requestType)) && (!org.apache.commons.lang.StringUtils.equalsIgnoreCase("http", requestType))) {
            logger.info("读取URL文件请求方式，支持HTTP和FTP方式: " + urlString);
            return null;
        }
        byte[] data = (byte[]) null;
        if (org.apache.commons.lang.StringUtils.equalsIgnoreCase("http", requestType)) {
            data = getFileBase64StrForHttp(urlString, urlStr);
        } else if (org.apache.commons.lang.StringUtils.equalsIgnoreCase("ftp", requestType)) {
            data = getFileBase64StrForFtp(urlString, urlStr);
        }
        if ((data != null) && (data.length > 0)) {
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data);
        }
        return null;
    }

    private static byte[] getFileBase64StrForHttp(String urlString, String urlStr) {
        URL url = null;
        HttpURLConnection con = null;
        InputStream in = null;
        byte[] data = (byte[]) null;
        int count = 0;
        int readCount = 0;
        try {
            url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type", "image/jpeg");
            con.setRequestProperty("Accept-Charset", "utf-8");
            con.setRequestProperty("contentType", "utf-8");
            in = con.getInputStream();
            while (count == 0) {
                count = con.getContentLength();
            }
            data = new byte[count];
            do {
                readCount += in.read(data, readCount, count - readCount);
                if (readCount > count) {
                    break;
                }
            } while (readCount != count);
        } catch (Exception e) {
            logger.error("上传HTTP图片拉取字节编码流时出现异常：" + e);
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException localIOException) {
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException localIOException1) {
            }
        }
        if (data == null) {
            logger.error("获取HTTP图片文件数据包为空");
        }
        return data;
    }

    private static byte[] getFileBase64StrForFtp(String urlString, String urlStr) {
        URL url = null;
        URLConnection con = null;
        InputStream in = null;
        byte[] data = (byte[]) null;

        ByteArrayOutputStream bytestream = null;
        try {
            url = new URL(urlStr);
            con = url.openConnection();
            con.setUseCaches(false);
            con.setRequestProperty("Content-type", "image/jpeg");
            con.setRequestProperty("Accept-Charset", "utf-8");
            con.setRequestProperty("contentType", "utf-8");
            in = con.getInputStream();
            bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                bytestream.write(ch);
            }
            data = bytestream.toByteArray();
        } catch (Exception e) {
            logger.error("上传FTP图片拉取字节编码流时出现异常：" + e);
            try {
                if (bytestream != null) {
                    bytestream.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException localIOException) {
            }
        } finally {
            try {
                if (bytestream != null) {
                    bytestream.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException localIOException1) {
            }
        }
        if (data == null) {
            logger.error("获取FTP图片文件数据包为空");
        }
        return data;
    }

    public static String getImageStr(String imgFilePath) {
        byte[] data = (byte[]) null;
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static boolean generateImage(String imgBase64Str, String path) {
        if (imgBase64Str == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imgBase64Str);
            return createImageFileByByte(b, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String testImage64BaseStr(String imgFilePath) {
        URL url = null;
        URLConnection con = null;
        InputStream in = null;
        byte[] data = (byte[]) null;
        try {
            url = new URL(imgFilePath);
            con = url.openConnection();
            in = con.getInputStream();
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            logger.error("读取URL文件转化成base64字符串时出错: " + imgFilePath);
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static void smbGet(String remoteUrl, String localDir) {
        InputStream in = null;
        OutputStream out = null;
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl);
            if (remoteFile != null) {
                String fileName = remoteFile.getName();
                File localFile = new File(localDir + File.separator + fileName);
                in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
                out = new BufferedOutputStream(new FileOutputStream(localFile));
                byte[] buffer = new byte[1024];
                while (in.read(buffer) != -1) {
                    out.write(buffer);
                    buffer = new byte[1024];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                out.close();
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void smbPut(String remoteUrl, String localFilePath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            File localFile = new File(localFilePath);
            String fileName = localFile.getName();
            SmbFile remoteFile = new SmbFile(remoteUrl + "/" + fileName);
            in = new BufferedInputStream(new FileInputStream(localFile));
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
                buffer = new byte[1024];
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                out.close();
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean smbImage(String imgBase64Str, String filePath) {
        if (imgBase64Str == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            byte[] buff = decoder.decodeBuffer(imgBase64Str);
            SmbFile remoteFile = new SmbFile(filePath);
            if (!remoteFile.exists()) {
                SmbFile smbDir = new SmbFile(remoteFile.getParent());
                if (!smbDir.isDirectory()) {
                    smbDir.mkdirs();
                }
            }
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
            ByteArrayInputStream in = new ByteArrayInputStream(buff);
            int ri = 0;
            byte[] rbuff = new byte[1024];
            while ((ri = in.read(rbuff)) > 0) {
                out.write(rbuff, 0, ri);
            }
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String GenerateFileName() {
        String fileName = "";
        Date timeCur = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyMMddHHmmssSSSS");
        fileName = fmt.format(timeCur);
        return fileName;
    }

    public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
    }
}
