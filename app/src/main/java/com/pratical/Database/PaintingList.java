package com.pratical.Database;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class PaintingList implements Serializable {
    private Integer paintingId;
    private String paintingImage;
    private String likeId;
    private Integer painterId;

    public Integer getPaintingId() {
        return paintingId;
    }

    public void setPaintingId(Integer paintingId) {
        this.paintingId = paintingId;
    }

    public String getPaintingImage() {
        return paintingImage;
    }

    public void setPaintingImage(String paintingImage) {
        this.paintingImage = paintingImage;
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public Integer getPainterId() {
        return painterId;
    }

    public void setPainterId(Integer painterId) {
        this.painterId = painterId;
    }

}
