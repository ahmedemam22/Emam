package com.example.monte.ondb;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by monte on 4/8/2019.
 */

public class Send_Data extends StringRequest {
    private static final String SENDURL="http://192.168.43.95:8080/myproject/send.php";
    private   Map<String,String> map;

    public Send_Data(String name, String email, String password,Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, SENDURL, listener, errorListener);
        map=new HashMap<>();
        map.put("name",name);
        map.put("email",email);
        map.put("password",password);

    }





    public Map<String,String> getparams(){
        return map;
    }
}
