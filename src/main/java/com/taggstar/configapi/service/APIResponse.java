package com.taggstar.configapi.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * API response returned by an API endpoint method.
 */
public class APIResponse {

    private String responseBody;
    private int httpStatusCode;
    private Date lastModified;
    private String moduleRunId;
    private boolean success;
    
    // Date format pattern used to parse HTTP date headers in RFC 1123 format.
    private static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

    public APIResponse() {
    }

    public String getResponseBody() {
        return responseBody;
    }

    public APIResponse withModuleRunId(String moduleRunId) {
        this.moduleRunId = moduleRunId;
        return this;
    }

    public APIResponse withResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public APIResponse withStatusCode(int statusCode) {
        this.httpStatusCode = statusCode;
        return this;
    }

    public APIResponse withLastModified(Date date) {
        this.lastModified = date;
        return this;
    }
    
    public APIResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getLastModifiedHeader() {

        if (lastModified != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_RFC1123, Locale.ENGLISH);
            return formatter.format(lastModified);
        } else {
            return null;
        }

    }

    public String getModuleRunId() {
        return moduleRunId;
    }
    
    
    public  boolean isSuccess(){
    	return success;
    }

}