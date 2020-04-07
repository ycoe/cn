package com.duoec.api.w.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("post_attachment")
public class PostAttachmentEntity {
    private Integer id;

    private Integer postId;

    private String type;

    private Integer attachmentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }
}