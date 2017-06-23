package com.byzcorp.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by byz on 23.06.2017.
 */
@Service
public class baseService {

    public static JSONObject reqGetJsonObject(String reqStr) {
        if (reqStr != null && !reqStr.equals("") && reqStr.charAt(0) == '{') {
            try {
                return JSONObject.fromObject(reqStr);
            } catch (Exception e) {
            }
        }
        return null;
    }

}
