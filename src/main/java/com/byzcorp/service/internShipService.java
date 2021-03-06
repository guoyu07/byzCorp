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
    @Autowired
    private lookUpService lookUpService;
    @Autowired
    private studentService studentService;

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

    public List<Map<String,Object>> getInternShips (String txtValue, Long userId, Long cmbPeriod) throws SQLException {
        return dao.getInternShips(txtValue, userId, cmbPeriod);
    }

    public List<Map<String,Object>> internShipDeliveryPDF (String txtValue) throws SQLException {
        return dao.internShipDeliveryPDF(txtValue);
    }

    public List<Map<String,Object>> getInternShipsDetails (Long internShipId, String query) throws SQLException {
        return dao.getInternShipsDetails(internShipId, query);
    }

    public List<Map<String,Object>> getInternShipCount (Long cmbInternShipPeriod,Long cmbInternShipStudent, Long internShipId) throws SQLException {
        return dao.getInternShipCount(cmbInternShipPeriod,cmbInternShipStudent,internShipId);
    }

    public List<Map<String,Object>> getInternShipDetailsCount (Long internShipId) throws SQLException {
        return dao.getInternShipDetailsCount(internShipId);
    }

    @Transactional(readOnly = false)
    public JSONObject saveOrUpdateInternShip(JSONObject formData,Long userId) throws ParseException, SQLException {
        JSONObject sendJSON = new JSONObject();
        String txtInternShipId = formData.getString("txtInternShipId-inputEl");
        String txtInternShipDay = formData.getString("txtInternShipDay-inputEl");
        Long cmbInternShipPlace = Long.parseLong(formData.getString("cmbInternShipPlace-inputEl"));
        String txtInternShipDesc = formData.getString("txtInternShipDesc-inputEl");
        Long cmbInternShipStudent = Long.parseLong(formData.getString("cmbInternShipStudent-inputEl"));
        String dateStartInternShip = formData.getString("dateStartInternShip-inputEl");
        String dateEndInternShip = formData.getString("dateEndInternShip-inputEl");
        Long cmbInternShipPeriod = Long.parseLong(formData.getString("cmbInternShipPeriod-inputEl"));
        Long cmbInternShipType = Long.parseLong(formData.getString("cmbInternShipType-inputEl"));
        Long cmbInternShipAcceptStatus = Long.parseLong(formData.getString("cmbInternShipAcceptStatus-inputEl"));
        List<Map<String,Object>> getInternShipCount = getInternShipCount(cmbInternShipPeriod,cmbInternShipStudent, null);
        List<Map<String,Object>> params = lookUpService.getLookUpDetail(28L);
        List<Map<String,Object>> periodList = lookUpService.getLookUpDetail(cmbInternShipPeriod);
        String period = periodList.get(0).get("LOOKUPDETAILNAME").toString();
        Long paramValue = Long.parseLong(params.get(0).get("LOOKUPDETAILVALUE").toString());
        Boolean save = true;
        List<Map<String,Object>> student = studentService.getStudent(cmbInternShipStudent);
        String studentIdandFirstandLastName = student.get(0).get("studentIdandFirstandLastName").toString();
        Long oldDay = null;
        if(getInternShipCount.size()==0){
            oldDay = 0L;
        }else{
            oldDay = Long.parseLong(getInternShipCount.get(0).get("TOTALINTERNSHIPDAY").toString());
        }
        Long newDay = Long.parseLong(txtInternShipDay.toString());
        Long saveDay = paramValue-oldDay;
        if(getInternShipCount.size()>0 && txtInternShipId.equals("")){
            if(newDay>saveDay) {
                sendJSON.put("data",new SQLException("HATA", studentIdandFirstandLastName+" ogrenicinin " +
                        period+" icin " +oldDay+" gun staj kaydi bulunmaktadir. Staj maksimum gunu "+paramValue+" oldugundan "+newDay+" staj gunu ekleyemezsiniz.."));
                sendJSON.put("success", false);
                save = false;
            }else{
                save = true;
            }
        }
        if(save) {
            if(newDay<=paramValue) {
                sendJSON.put("success", dao.saveOrUpdateInternShip(txtInternShipId, txtInternShipDay, cmbInternShipPlace, txtInternShipDesc, cmbInternShipStudent, dateStartInternShip, dateEndInternShip, cmbInternShipPeriod, cmbInternShipType,cmbInternShipAcceptStatus, userId));
            }else{
                sendJSON.put("data",new SQLException("HATA", "Staj maksimum gunu "+paramValue+" oldugundan "+newDay+" staj gunu ekleyemezsiniz.."));
                sendJSON.put("success", false);
            }
        }
        return sendJSON;
    }

    @Transactional(readOnly = false)
    public JSONObject saveOrUpdateInternShipRequest(JSONObject requestData,Long userId) throws ParseException, SQLException {
        JSONObject sendJSON = new JSONObject();
        String txtInternShipId = requestData.getString("txtInternShipIdReq-inputEl");
        //String txtInternShipDay = requestData.getString("txtInternShipDayReq-inputEl");
        Long cmbInternShipPlace = Long.parseLong(requestData.getString("cmbInternShipPlaceReq-inputEl"));
        String txtInternShipDesc = requestData.getString("txtInternShipDescReq-inputEl");
        Long cmbInternShipStudent = userId;
        String dateStartInternShip = requestData.getString("dateStartInternShipReq-inputEl");
        String dateEndInternShip = requestData.getString("dateEndInternShipReq-inputEl");
        Long cmbInternShipPeriod = Long.parseLong(requestData.getString("cmbInternShipPeriodReq-inputEl"));
        Long cmbInternShipType = Long.parseLong(requestData.getString("cmbInternShipTypeReq-inputEl"));
        List<Map<String,Object>> getInternShipCount = getInternShipCount(cmbInternShipPeriod,cmbInternShipStudent, null);
        List<Map<String,Object>> params = lookUpService.getLookUpDetail(28L);
        List<Map<String,Object>> periodList = lookUpService.getLookUpDetail(cmbInternShipPeriod);
        String period = periodList.get(0).get("LOOKUPDETAILNAME").toString();
        Long paramValue = Long.parseLong(params.get(0).get("LOOKUPDETAILVALUE").toString());
        Long cmbInternShipAcceptStatus = 39L;
        Boolean save = true;
        List<Map<String,Object>> student = studentService.getStudent(cmbInternShipStudent);
        String studentIdandFirstandLastName = student.get(0).get("studentIdandFirstandLastName").toString();
        Long oldDay = null;
        if(getInternShipCount.size()==0){
            oldDay = 0L;
        }else{
            oldDay = Long.parseLong(getInternShipCount.get(0).get("TOTALINTERNSHIPDAY").toString());
        }
        Long newDay = paramValue;
        Long saveDay = paramValue-oldDay;
        if(getInternShipCount.size()>0 && txtInternShipId.equals("")){
            String acceptStatus = getInternShipCount.get(0).get("INTERNSHIPACCEPTSTATUS").toString();
            if(acceptStatus=="Onaylandı"){
                sendJSON.put("data",new SQLException("HATA", " Seçtiğiniz dönemde daha önce oluşturulmuş bir kaydınız bulunmaktadır. Aynı döneme yeni kayıt ekleyemez, onaylanmış kayıtlar üzerinde güncelleme yapamazsınız."));
                sendJSON.put("success", false);
            }else{}
            if(newDay>saveDay) {
                sendJSON.put("data",new SQLException("HATA", studentIdandFirstandLastName+" ogrenicinin " +
                        period+" icin " +oldDay+" gun staj kaydi bulunmaktadir. Staj maksimum gunu "+paramValue+" oldugundan "+newDay+" staj gunu ekleyemezsiniz.."));
                sendJSON.put("success", false);
                save = false;
            }else{
                save = true;
            }
        }
        if(save) {
            if(newDay<=paramValue) {
                sendJSON.put("success", dao.saveOrUpdateInternShip(txtInternShipId, paramValue.toString(), cmbInternShipPlace, txtInternShipDesc, cmbInternShipStudent, dateStartInternShip, dateEndInternShip, cmbInternShipPeriod, cmbInternShipType,cmbInternShipAcceptStatus, userId));
            }else{
                sendJSON.put("data",new SQLException("HATA", "Staj maksimum gunu "+paramValue+" oldugundan "+newDay+" staj gunu ekleyemezsiniz.."));
                sendJSON.put("success", false);
            }
        }
        return sendJSON;
    }

    @Transactional(readOnly = false)
    public JSONObject saveOrUpdateInternShipsDetail(JSONObject formData, JSONObject selected) throws ParseException, SQLException {

        JSONObject sendJSON = new JSONObject();
        String txtInternShipDetailId = formData.getString("txtInternShipDetailId-inputEl");
        Long internShipId = Long.parseLong(selected.getString("INTERNSHIPID"));
        Long internShipDay = Long.parseLong(selected.getString("INTERNSHIPDAY"));
        String studentNo = selected.getString("STUDENTNO");
        String studentFirstandLastName = selected.getString("STUDENTFIRSTANDLASTNAME");
        String internShipPeriod = selected.getString("INTERNSHIPPERIOD");
        Long txtInternShipDetailCompDate = Long.parseLong(formData.getString("txtInternShipDetailCompDate-inputEl"));
        String dateDeliveryInternShip = formData.getString("dateStartInternShip1-inputEl");
        String txtInternShipDetailDesc = formData.getString("txtInternShipDetailDesc-inputEl");
        Long cmbInternShipDetailPlace = Long.parseLong(formData.getString("cmbInternShipDetailPlace-inputEl"));
        Boolean save = true;
        List<Map<String,Object>> getInternShipDetailCount = getInternShipDetailsCount(internShipId);
        Long totalDetailDay = Long.parseLong(getInternShipDetailCount.get(0).get("TOTALINTERNSHIPDETAILCOMPDAY").toString());
        if(txtInternShipDetailCompDate>internShipDay){
            sendJSON.put("data",new SQLException("HATA", studentNo+"-"+studentFirstandLastName+" ogrenicinin " +
                    internShipPeriod+" icin " +internShipDay+" gun staj plani bulunmaktadir. Planlanan gunden daha fazla staj gunu ekleyemezsiniz.."));
            save = false;
        }else{
            save = true;
        }
        if((internShipDay-totalDetailDay)<txtInternShipDetailCompDate && txtInternShipDetailId.equals("")){
            sendJSON.put("data",new SQLException("HATA", studentNo+"-"+studentFirstandLastName+" ogrenicinin " +
                    internShipPeriod+" icin " +totalDetailDay+" gun staj detay kaydi bulunmaktadir. Planlanan gunden daha fazla staj gunu ekleyemezsiniz.."));
            save = false;
        }
        if(save) {
            sendJSON.put("success", dao.saveOrUpdateInternShipsDetail(txtInternShipDetailId, internShipId, txtInternShipDetailCompDate, dateDeliveryInternShip, txtInternShipDetailDesc, cmbInternShipDetailPlace));
        }
        return sendJSON;
    }

    @Transactional(readOnly = false)
    public Boolean deleteInternShip(Long internShipId){
        return dao.deleteInternShip(internShipId);
    }

    @Transactional(readOnly = false)
    public Boolean deleteInternShipDetail(Long internShipDetailId){
        return dao.deleteInternShipDetail(internShipDetailId);
    }
}
