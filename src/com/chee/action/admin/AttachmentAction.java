package com.chee.action.admin;

import com.chee.entity.Attachment;
import com.chee.entity.User;
import com.chee.service.AttachmentService;
import com.chee.template.Template;
import com.chee.template.TemplateManager;
import com.chee.template.Thumbnail;
import com.chee.util.AttachmentUtils;
import com.chee.util.DateUtil;
import com.chee.util.FileUtils;
import com.chee.util.ImageUtils;
import com.chee.util.StringUtils;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Log;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import com.jfinal.upload.UploadFile;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "Attachment!list.action", type = "redirect") })
public class AttachmentAction extends BaseAction<Attachment, Integer> {
    private static final long serialVersionUID = 4984599292597512018L;
    private static final Log log = Log.getLog(AttachmentAction.class);
    @Resource
    private AttachmentService attachmentService;
    private Attachment attachment;

    public String list() {
        if (this.attachment == null) {
            this.attachment = new Attachment();
            if (StringUtils.isEmpty(this.attachment.getBeginDate())) {
                this.attachment.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
            }
            if (StringUtils.isEmpty(this.attachment.getEndDate())) {
                this.attachment.setEndDate(DateUtil.afterNDay(new Date(), 100, "yyyy-MM-dd"));
            }
            this.attachment.setMime("image");
        }
        this.page = this.attachmentService.findAttachmentPage(this.page, this.attachment);
        return "list";
    }

    public String detail_layer() {
        Integer id = this.attachment.getId();
        Attachment attachment = (Attachment) this.attachmentService.get(id);

        File attachmentFile = new File(PathKit.getWebRootPath(), attachment.getPath());
        attachment.setName(attachmentFile.getName());

        long fileLen = attachmentFile.length();
        String fileLenUnit = "Byte";
        if (fileLen > 1024L) {
            fileLen /= 1024L;
            fileLenUnit = "KB";
        }
        if (fileLen > 1024L) {
            fileLen /= 1024L;
            fileLenUnit = "MB";
        }
        attachment.setSize(fileLen + fileLenUnit);
        try {
            if (AttachmentUtils.isImage(attachment.getPath())) {
                String ratio = ImageUtils.ratioAsString(attachmentFile.getAbsolutePath());
                attachment.setRatio(ratio == null ? "unknow" : ratio);
            }
        } catch (Throwable e) {
            log.error("detail_layer ratioAsString error", e);
        }
        return "detail_layer";
    }

    public String choose_layer() {
        this.page = this.attachmentService.findAttachmentPage(this.page, this.attachment);
        return "choose_layer";
    }

    public void doUpload() {
        JSONObject json = new JSONObject();
        try {
            UploadFile uploadFile = getFile();
            if (uploadFile != null) {
                String newPath = AttachmentUtils.moveFile(uploadFile);
                User user = getLoginUser();

                Attachment attachment = new Attachment();
                attachment.setUser_id(user.getId());
                attachment.setCreated(new Date());
                attachment.setTitle(uploadFile.getOriginalFileName());
                attachment.setPath(newPath.replace("\\", "/"));
                attachment.setSuffix(FileUtils.getSuffix(uploadFile.getFileName()));
                attachment.setMime_type(uploadFile.getContentType());
                this.attachmentService.save(attachment);

                processImage(newPath);

                json.put("success", Boolean.valueOf(true));
            } else {
                json.put("success", Boolean.valueOf(false));
                json.put("message", "请选择要上传的文件！");
            }
        } catch (Exception e) {
            json.put("success", Boolean.valueOf(false));
            json.put("message", e.toString());
        }
        ajaxJson(json.toString());
    }

    public void proxy() {
        String url = getPara("url");
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setInstanceFollowRedirects(true);
            conn.setUseCaches(true);
            InputStream is = conn.getInputStream();

            setHeader("Content-Type", conn.getContentType());
        } catch (Exception localException) {
        }
    }

    public String upload() {
        return "upload";
    }

    private void processImage(String newPath) {
        if (!AttachmentUtils.isImage(newPath)) {
            return;
        }
        if (".gif".equalsIgnoreCase(FileUtils.getSuffix(newPath))) {
        }
    }

    private void processThumbnail(String newPath) {
        List<Thumbnail> tbs = TemplateManager.me().currentTemplate().getThumbnails();
        if ((tbs != null) && (tbs.size() > 0)) {
            for (Thumbnail tb : tbs) {
                try {
                    String newSrc = ImageUtils.scale(PathKit.getWebRootPath() + newPath, tb.getWidth(), tb.getHeight());
                    processWatermark(FileUtils.removeRootPath(newSrc));
                } catch (IOException e) {
                    log.error("processWatermark error", e);
                }
            }
        }
    }

    public void processWatermark(String newPath) {
    }

    public class StreamRender extends Render {
        final InputStream stream;

        public StreamRender(InputStream is) {
            this.stream = is;
        }

        public void render() {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = new BufferedInputStream(this.stream);
                outputStream = this.response.getOutputStream();
                byte[] buffer = new byte[1024];
                for (int len = -1; (len = inputStream.read(buffer)) != -1;) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
            } catch (IOException e) {
                if (getDevMode()) {
                    throw new RenderException(e);
                }
            } catch (Exception e) {
                throw new RenderException(e);
            } finally {
                close(inputStream, outputStream);
            }
        }

        private void close(InputStream inputStream, OutputStream outputStream) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogKit.error(e.getMessage(), e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LogKit.error(e.getMessage(), e);
                }
            }
        }
    }

    public Attachment getAttachment() {
        return this.attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
