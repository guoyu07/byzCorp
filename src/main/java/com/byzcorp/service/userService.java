package com.byzcorp.service;

import com.byzcorp.dao.userDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by byz on 05.06.2017.
 */
@Service
public class userService {

    @Autowired
    public userDao dao;

    public static JSONObject reqGetJsonObject(String reqStr) {
        if (reqStr != null && !reqStr.equals("") && reqStr.charAt(0) == '{') {
            try {
                return JSONObject.fromObject(reqStr);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return null;
    }

    public List<Map<String,Object>> getUser (String userName, String password) throws SQLException {
        return dao.getUser(userName,password);
    }

    public List<Map<String,Object>> getUsers (String txtValue) throws SQLException {
        return dao.getUsers(txtValue);
    }

    public List<Map<String,Object>> getRoles (String txtValue) throws SQLException {
        return dao.getRoles(txtValue);
    }

    @Transactional(readOnly = false)
    public Boolean saveOrUpdateUser(JSONObject formData){

        String txtUserId = formData.getString("txtUserId-inputEl");
        String txtUserFirstName = formData.getString("txtUserFirstName-inputEl");
        String txtUserLastName = formData.getString("txtUserLastName-inputEl");
        String txtUserName = formData.getString("txtUserName-inputEl");
        String txtUserEmail = formData.getString("txtUserEmail-inputEl");
        Long cmbUserRole = Long.parseLong(formData.getString("cmbUserRole-inputEl"));
        Long cmbUserTitle = Long.parseLong(formData.getString("cmbUserTitle-inputEl"));
        Long cmbUserStatus = Long.parseLong(formData.getString("cmbUserStatus-inputEl"));

        return dao.saveOrUpdateUser(txtUserId,txtUserFirstName,txtUserLastName,txtUserName,txtUserEmail,cmbUserRole,cmbUserTitle,cmbUserStatus);
    }

    @Transactional(readOnly = false)
    public Boolean updatePassword(Long userId, String password){
        return dao.updatePassword(userId,password);
    }
}
