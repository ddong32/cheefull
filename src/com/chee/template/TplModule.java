package com.chee.template;

import com.jfinal.kit.PathKit;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class TplModule {
    private String title;
    private String name;
    private String listTitle;
    private String addTitle;
    private String commentTitle;
    private String iconClass;
    private String orders;
    private List<TplTaxonomyType> taxonomyTypes;
    private List<TplMetadata> metadatas;

    public List<TplTaxonomyType> getTaxonomyTypes() {
        return this.taxonomyTypes;
    }

    public void setTaxonomyTypes(List<TplTaxonomyType> taxonomys) {
        this.taxonomyTypes = taxonomys;
    }

    public List<TplMetadata> getMetadatas() {
        return this.metadatas;
    }

    public void setMetadatas(List<TplMetadata> metadatas) {
        this.metadatas = metadatas;
    }

    public List<String> getStyles() {
        List<String> moduleStyles = null;
        File f = new File(PathKit.getWebRootPath(), TemplateManager.me().currentTemplatePath());
        String[] fileNames = f.list(new FilenameFilter() {
            public boolean accept(File dir, String fileName) {
                return (fileName.startsWith("content_" + TplModule.this.name + "_")) && (!fileName.contains("for$"));
            }
        });
        if ((fileNames != null) && (fileNames.length > 0)) {
            moduleStyles = new ArrayList();
            int start = ("content_" + this.name + "_").length();
            for (String fileName : fileNames) {
                moduleStyles.add(fileName.substring(start, fileName.lastIndexOf(".")));
            }
        }
        return moduleStyles;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListTitle() {
        return this.listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getAddTitle() {
        return this.addTitle;
    }

    public void setAddTitle(String addTitle) {
        this.addTitle = addTitle;
    }

    public String getCommentTitle() {
        return this.commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconClass() {
        return this.iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getOrders() {
        return this.orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public TplTaxonomyType getTaxonomyTypeByType(String name) {
        List<TplTaxonomyType> tts = this.taxonomyTypes;
        if ((tts != null) && (tts.size() > 0)) {
            for (TplTaxonomyType type : tts) {
                if (type.getName().equals(name)) {
                    return type;
                }
            }
        }
        return null;
    }

    public String toString() {
        return "TplModule [title=" + this.title + ", name=" + this.name + ", listTitle=" + this.listTitle + ", addTitle=" + this.addTitle + ", commentTitle=" + this.commentTitle + ", orders="
                + this.orders + ", taxonomyTypes=" + this.taxonomyTypes + ", metadatas=" + this.metadatas + "]";
    }
}
