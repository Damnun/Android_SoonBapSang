package com.example.midtermproj;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import java.lang.reflect.Method;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class ValidateRequest extends StringRequest{
    final static private String URL = "http://sch20185119.dothome.co.kr/UserValidate.php";
    private Map<String, String> map;

    public ValidateRequest(String UserId, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("UserId", UserId);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
