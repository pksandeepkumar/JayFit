
package com.feathernet.jayfit.rest.pojos.videosall;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllVideosPOJO {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("video")
    @Expose
    private List<Video> video = null;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("SubCategory")
    @Expose
    private List<SubCategory> subCategory = null;
    @SerializedName("new_token")
    @Expose
    private Object newToken;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

    public Object getNewToken() {
        return newToken;
    }

    public void setNewToken(Object newToken) {
        this.newToken = newToken;
    }

}
