package com.saurabh.demo.utils.glide;

import java.io.File;

/**
 * Created by SaurabhA on 03,October,2020
 */
public class GlideImageModel {

    private String id;
    private File localPath;
    private String bucketName;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GlideImageModel imageFid = (GlideImageModel) o;

        return id.equals(imageFid.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File getLocalPath() {
        return localPath;
    }

    public void setLocalPath(File localPath) {
        this.localPath = localPath;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
