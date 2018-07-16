
package com.feathernet.jayfit.rest.pojos.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationPOJO {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private Integer data;
    @SerializedName("new_token")
    @Expose
    private Object newToken;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public Object getNewToken() {
        return newToken;
    }

    public void setNewToken(Object newToken) {
        this.newToken = newToken;
    }

}
