package com.justice.justcode.dto;

public class CodeRequest {
    String language;
    String code;
    String extension;

    public void setCode(String code){ this.code = code;}
    public void setLanguage(String language){this.language = language;}
    public void setExtension(String extension){this.extension = extension;}

    public String getCode(){return this.code;}
    public String getLanguage(){return this.language;}
    public String getExtension(){return this.extension;}
}
