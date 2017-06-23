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

    public List<Map<String,Object>> getLookUpDetails (Long lookUpId, String query) throws SQLException {
        return dao.getLookUpDetails(lookUpId, query);
    }

    public List<Map<String,Object>> getLudTitles (String query) throws SQLException {
        return dao.getLudTitles(query);
    }
    public List<Map<String,Object>> getLudPeriods (String query) throws SQLException {
        return dao.getLudPeriods(query);
    }
    public List<Map<String,Object>> getLudDepartments (String query) throws SQLException {
        return dao.getLudDepartments(query);
    }
    public List<Map<String,Object>> getLudClasses (String query) throws SQLException {
        return dao.getLudClasses(query);
    }

    public List<Map<String,Object>> getLudStatus (String query) throws SQLException {
        return dao.getLudStatus(query);
    }

    public List<Map<String,Object>> getLudInternShipStatus (String query) throws SQLException {
        return dao.getLudInternShipStatus(query);
    }

    public List<Map<String,Object>> getLudInternShipTypes (String query) throws SQLException {
        return dao.getLudInternShipTypes(query);
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
