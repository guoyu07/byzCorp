package com.byzcorp.service;

import com.byzcorp.dao.lookUpDao;
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
public class lookUpService {
    @Autowired
    public lookUpDao dao;

    public List<Map<String,Object>> getLookUp (Long lookUpId) throws SQLException {
        return dao.getLookUp(lookUpId);
    }

    public List<Map<String,Object>> getActiveLookUps (String lookUpName) throws SQLException {
        return dao.getActiveLookUps(lookUpName);
    }

    public List<Map<String,Object>> getLookUpDetails (Long lookUpId) throws SQLException {
        return dao.getLookUpDetails(lookUpId);
    }

    public List<Map<String,Object>> getLudTitles () throws SQLException {
        return dao.getLudTitles();
    }
    public List<Map<String,Object>> getLudPeriods () throws SQLException {
        return dao.getLudPeriods();
    }
    public List<Map<String,Object>> getLudDepartments () throws SQLException {
        return dao.getLudDepartments();
    }
    public List<Map<String,Object>> getLudClasses () throws SQLException {
        return dao.getLudClasses();
    }

    public List<Map<String,Object>> getLudStatus () throws SQLException {
        return dao.getLudStatus();
    }

    public List<Map<String,Object>> getLudInternShipStatus () throws SQLException {
        return dao.getLudInternShipStatus();
    }

    public List<Map<String,Object>> getLudInternShipTypes () throws SQLException {
        return dao.getLudInternShipTypes();
    }
    @Transactional(readOnly = false)
    public Boolean saveOrUpdateLookUpDetail(JSONObject formData, Long lookUpId){

        String txtLookUpDetailId = formData.getString("txtLookUpDetailId-inputEl");
        String txtLookUpDetailName = formData.getString("txtLookUpDetailDesc-inputEl");
        String txtLookUpDetailValue = formData.getString("txtLookUpDetailValue-inputEl");
        Long cmbLookUpDetailStatus = Long.parseLong(formData.getString("cmbLookUpDetailStatus-inputEl"));
        return dao.saveOrUpdateLookUpDetail(txtLookUpDetailId,txtLookUpDetailName,txtLookUpDetailValue,cmbLookUpDetailStatus,lookUpId);
    }
}
