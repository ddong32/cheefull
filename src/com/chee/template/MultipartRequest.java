package com.chee.template;

import com.jfinal.upload.UploadFile;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MultipartRequest extends HttpServletRequestWrapper {
    private static String baseUploadPath;
    private static int maxPostSize;
    private static String encoding;
    static FileRenamePolicy fileRenamePolicy = new DefaultFileRenamePolicy();
    private List<UploadFile> uploadFiles;
    private com.oreilly.servlet.MultipartRequest multipartRequest;

    public static void init(String baseUploadPath, int maxPostSize, String encoding) {
        baseUploadPath = baseUploadPath;
        maxPostSize = maxPostSize;
        encoding = encoding;
    }

    public MultipartRequest(HttpServletRequest request, String uploadPath, int maxPostSize, String encoding) {
        super(request);
        wrapMultipartRequest(request, getFinalPath(uploadPath), maxPostSize, encoding);
    }

    public MultipartRequest(HttpServletRequest request, String uploadPath, int maxPostSize) {
        super(request);
        wrapMultipartRequest(request, getFinalPath(uploadPath), maxPostSize, encoding);
    }

    public MultipartRequest(HttpServletRequest request, String uploadPath) {
        super(request);
        wrapMultipartRequest(request, getFinalPath(uploadPath), maxPostSize, encoding);
    }

    public MultipartRequest(HttpServletRequest request) {
        super(request);
        wrapMultipartRequest(request, baseUploadPath, maxPostSize, encoding);
    }

    private String getFinalPath(String uploadPath) {
        if (uploadPath == null) {
            throw new IllegalArgumentException("uploadPath can not be null.");
        }
        uploadPath = uploadPath.trim();
        if ((uploadPath.startsWith("/")) || (uploadPath.startsWith("\\"))) {
            if (baseUploadPath.equals("/")) {
                return uploadPath;
            }
            return baseUploadPath + uploadPath;
        }
        return baseUploadPath + File.separator + uploadPath;
    }

    private void wrapMultipartRequest(HttpServletRequest request, String uploadPath, int maxPostSize, String encoding) {
        File dir = new File(uploadPath);
        if ((!dir.exists()) && (!dir.mkdirs())) {
            throw new RuntimeException("Directory " + uploadPath + " not exists and can not create directory.");
        }
        String content_type = request.getContentType();
        if ((content_type == null) || (content_type.indexOf("multipart/form-data") == -1)) {
            throw new RuntimeException("Not multipart request, enctype=\"multipart/form-data\" is not found of form.");
        }
        this.uploadFiles = new ArrayList();
        try {
            this.multipartRequest = new com.oreilly.servlet.MultipartRequest(request, uploadPath, maxPostSize, encoding, fileRenamePolicy);
            Enumeration files = this.multipartRequest.getFileNames();
            while (files.hasMoreElements()) {
                String name = (String) files.nextElement();
                String filesystemName = this.multipartRequest.getFilesystemName(name);
                if (filesystemName != null) {
                    String originalFileName = this.multipartRequest.getOriginalFileName(name);
                    String contentType = this.multipartRequest.getContentType(name);
                    UploadFile uploadFile = new UploadFile(name, uploadPath, filesystemName, originalFileName, contentType);
                    if (isSafeFile(uploadFile)) {
                        this.uploadFiles.add(uploadFile);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isSafeFile(UploadFile uploadFile) {
        String fileName = uploadFile.getFileName().trim().toLowerCase();
        if ((fileName.endsWith(".jsp")) || (fileName.endsWith(".jspx"))) {
            uploadFile.getFile().delete();
            return false;
        }
        return true;
    }

    public List<UploadFile> getFiles() {
        return this.uploadFiles;
    }

    public Enumeration getParameterNames() {
        return this.multipartRequest.getParameterNames();
    }

    public String getParameter(String name) {
        return this.multipartRequest.getParameter(name);
    }

    public String[] getParameterValues(String name) {
        return this.multipartRequest.getParameterValues(name);
    }

    public Map getParameterMap() {
        Map map = new HashMap();
        Enumeration enumm = getParameterNames();
        while (enumm.hasMoreElements()) {
            String name = (String) enumm.nextElement();
            map.put(name, this.multipartRequest.getParameterValues(name));
        }
        return map;
    }
}
