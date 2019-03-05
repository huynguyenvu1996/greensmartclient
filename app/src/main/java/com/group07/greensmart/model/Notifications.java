package com.group07.greensmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by nguyenvuhuy on 3/28/18.
 */

public class Notifications implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("read")
    @Expose
    private boolean read;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("_rev")
    @Expose
    private String rev;

    public Notifications(String id, String title, String subject, String content, boolean read, String createdAt, String rev) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.content = content;
        this.read = read;
        this.createdAt = createdAt;
        this.rev = rev;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }
}