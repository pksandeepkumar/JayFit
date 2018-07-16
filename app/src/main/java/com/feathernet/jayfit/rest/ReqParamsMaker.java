package com.feathernet.jayfit.rest;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;

public class ReqParamsMaker {


    public static RequestBody getRegisterParams(String email, String tocken, String name, String imageURL) {

        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("email",email);
        hashMap.put("token",tocken);
        hashMap.put("fname",name);
        hashMap.put("lname","");
        hashMap.put("dob","");
        hashMap.put("gender","");
        hashMap.put("image",imageURL);
        hashMap.put("pstatus","0");
        hashMap.put("status","1");

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(hashMap)).toString());
        return  body;
    }
}
