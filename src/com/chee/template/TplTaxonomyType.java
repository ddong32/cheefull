package com.chee.template;

import java.util.List;

public class TplTaxonomyType {
    public static final String TYPE_INPUT = "input";
    public static final String TYPE_SELECT = "select";
    private String title;
    private String name;
    private String formType = "select";
    private TplModule module;
    private List<TplMetadata> metadatas;

    public List<TplMetadata> getMetadatas() {
        return this.metadatas;
    }

    public void setMetadatas(List<TplMetadata> metadatas) {
        this.metadatas = metadatas;
    }

    public TplTaxonomyType(TplModule module) {
        this.module = module;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TplModule getModule() {
        return this.module;
    }

    public String getFormType() {
        return this.formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public boolean isInputType() {
        return "input".equals(getFormType());
    }

    public boolean isSelectType() {
        return "select".equals(getFormType());
    }

    public String toString() {
        return "TplTaxonomyType [title=" + this.title + ", name=" + this.name + ", formType=" + this.formType + ", metadatas=" + this.metadatas + "]";
    }
}
