package com.byzcorp.service;

import com.byzcorp.dao.internShipDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by byz on 05.06.2017.
 */
@Service
public class internShipService {

    @Autowired
    public internShipDao dao;

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

    public List<Map<String,Object>> getInternShips (String txtValue) throws SQLException {
        return dao.getInternShips(txtValue);
    }

    @Transactional(readOnly = false)
    public Boolean saveOrUpdateInternShip(JSONObject formData) throws ParseException {

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");//when use hibernate then is active..
        String txtInternShipId = formData.getString("txtInternShipId-inputEl");
        Long cmbInternShipStudent = Long.parseLong(formData.getString("cmbInternShipStudent-inputEl"));
        String dateStartInternShip = formData.getString("dateStartInternShip-inputEl");
        String dateEndInternShip = formData.getString("dateEndInternShip-inputEl");
        Long cmbInternShipPeriod = Long.parseLong(formData.getString("cmbInternShipPeriod-inputEl"));
        Long cmbInternShipType = Long.parseLong(formData.getString("cmbInternShipType-inputEl"));
        Long cmbInternShipStatu = Long.parseLong(formData.getString("cmbInternShipStatu-inputEl"));
        return dao.saveOrUpdateInternShip(txtInternShipId,cmbInternShipStudent,dateStartInternShip,dateEndInternShip,cmbInternShipPeriod,cmbInternShipType,cmbInternShipStatu);
    }

    @Transactional(readOnly = false)
    public Boolean deleteInternShip(Long internShipId){
        return dao.deleteInternShip(internShipId);
    }

}
