package com.byzcorp.service;

import com.byzcorp.dao.studentDao;
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
public class studentService {

    @Autowired
    public studentDao dao;

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

    public List<Map<String,Object>> getStudents (String txtValue) throws SQLException {
        return dao.getStudents(txtValue);
    }

    @Transactional(readOnly = false)
    public Boolean saveOrUpdateStudent(JSONObject formData){

        String txtStudentId = formData.getString("txtStudentId-inputEl");
        String txtStudentNo = formData.getString("txtStudentNo-inputEl");
        String txtStudentFirstName = formData.getString("txtStudentFirstName-inputEl");
        String txtStudentLastName = formData.getString("txtStudentLastName-inputEl");
        String txtStudentCountryId = formData.getString("txtStudentCountryId-inputEl");
        String txtStudentPeriodId = formData.getString("cmbStudentPeriod-inputEl");
        Long cmbStudentClassId = Long.parseLong(formData.getString("cmbStudentClass-inputEl"));
        Long cmbStudentDepartmentId = Long.parseLong(formData.getString("cmbStudentDepartment-inputEl"));
        Long cmbStudentStatus = Long.parseLong(formData.getString("cmbStudentStatu-inputEl"));

        return dao.saveOrUpdateStudent(txtStudentId,txtStudentNo,txtStudentFirstName,txtStudentLastName,txtStudentCountryId,txtStudentPeriodId,cmbStudentClassId,cmbStudentDepartmentId,cmbStudentStatus);
    }
}
