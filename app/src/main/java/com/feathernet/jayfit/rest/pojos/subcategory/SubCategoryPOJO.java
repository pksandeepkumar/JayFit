
package com.feathernet.jayfit.rest.pojos.subcategory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryPOJO {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("new_token")
    @Expose
    private Object newToken;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Object getNewToken() {
        return newToken;
    }

    public void setNewToken(Object newToken) {
        this.newToken = newToken;
    }

}
