package com.byzcorp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by byz on 05.06.2017.
 */
@Repository
public class lookUpDao {
    @Autowired
    private JdbcTemplate sql;

    public List<Map<String,Object>> getLookUp (Long lookUpId) throws SQLException {
        String query = "select" +
                " lu.lookUpId," +
                " lu.lookUpName," +
                //" lu.lookUpStatus" +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 7 and lud.lookUpDetailId = lu.lookUpStatus) as lookUpStatus" +
                " from ktu.LOOKUP lu" +
                " where lu.lookUpId = "+"'"+lookUpId+"'";
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getLookUpDetail (Long lookUpDetailId) throws SQLException {
        String query = "select" +
                " lud.LOOKUPDETAILID," +
                " lud.LOOKUPDETAILNAME," +
                " lud.LOOKUPDETAILVALUE" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.lookUpDetailId = "+"'"+lookUpDetailId+"'";
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getActiveLookUps (String lookUpName) throws SQLException {
        String query = "select" +
                " lu.lookUpId," +
                " lu.lookUpName," +
                //" lu.lookUpStatus" +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 7 and lud.lookUpDetailId = lu.lookUpStatus) as lookUpStatus" +
                " from ktu.LOOKUP lu";
        if(lookUpName!=null){
            query +=" where lower(lu.lookUpName) like lower('%"+lookUpName+"%')";
        }
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getLookUpDetails (Long lookUpId, String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " (select lu.lookUpDetailName from ktu.LOOKUPDETAIL lu where lu.lookUpId = 7 and lu.lookUpDetailId = lud.lookUpDetailStatus) as lookUpDetailStatusName," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud";
        if(lookUpId!=null){
            q +=" where lud.lookUpId = '"+lookUpId+"'";
        }
        return sql.queryForList(q);
    }

    public List<Map<String,Object>> getLudTitles(String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=1";//Ünvanlar.
        if(query!=null){
            q +=" and lower(lud.lookUpDetailName) like lower('%"+query+"%')";
        }
        return sql.queryForList(q);
    }

    public List<Map<String,Object>> getLudPeriods(String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=4";//Aktif Dönemler.
        if(query!=null){
            q +=" and lower(lud.lookUpDetailName) like lower('%"+query+"%')";
        }
        return sql.queryForList(q);
    }

    public List<Map<String,Object>> getLudDepartments(String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=2";//Bölümler.
        if(query!=null){
            q +=" and lower(lud.lookUpDetailName) like lower('%"+query+"%')";
        }
        return sql.queryForList(q);
    }

    public List<Map<String,Object>> getLudClasses(String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=3";//Sınıflar.
        if(query!=null){
            q +=" and lower(lud.lookUpDetailName) like lower('%"+query+"%')";
        }
        return sql.queryForList(q);
    }

    public List<Map<String,Object>> getLudStatus(String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=7";//Statüler.
        if(query!=null){
            q +=" and lower(lud.lookUpDetailName) like lower('%"+query+"%')";
        }
        return sql.queryForList(q);
    }

    public List<Map<String,Object>> getLudInternShipStatus(String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=6";//Staj Statüleri.
        if(query!=null){
            q +=" and lower(lud.lookUpDetailName) like lower('%"+query+"%')";
        }
        return sql.queryForList(q);
    }

    public List<Map<String,Object>> getLudInternShipPlace(String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=9";//Staj Yerleri.
        if(query!=null){
            q +=" and lower(lud.lookUpDetailName) like lower('%"+query+"%')";
        }
        return sql.queryForList(q);
    }

    public List<Map<String,Object>> getLudInternShipTypes(String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=5";//Staj Tipleri.
        if(query!=null){
            q +=" and lower(lud.lookUpDetailName) like lower('%"+query+"%')";
        }
        return sql.queryForList(q);
    }

    public List<Map<String,Object>> getLudInternShipAcceptStatus(String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=10";//Staj Onay Durumları.
        if(query!=null){
            q +=" and lower(lud.lookUpDetailName) like lower('%"+query+"%')";
        }
        return sql.queryForList(q);
    }

    public Boolean saveOrUpdateLookUpDetail(String txtLookUpDetailId,String txtLookUpDetailName,String txtLookUpDetailValue,Long cmbLookUpDetailStatus, Long lookUpId) {
        String query = "";
        try {
            if (txtLookUpDetailId.equals("")) {
                query = " insert into ktu.LOOKUPDETAIL (" +
                        " lookUpDetailId," +
                        " LOOKUPDETAILNAME," +
                        " LOOKUPDETAILVALUE," +
                        " LOOKUPDETAILSTATUS," +
                        " LOOKUPID) values (" +
                        "ktu.lookUpDetailSeq.nextval,'"
                        + txtLookUpDetailName + "','"
                        + txtLookUpDetailValue + "',"
                        + cmbLookUpDetailStatus + ","
                        + lookUpId + ")";

            } else {
                query = "update ktu.LOOKUPDETAIL u set " +
                        " u.LOOKUPDETAILNAME ='" + txtLookUpDetailName + "'," +
                        " u.LOOKUPDETAILVALUE='" + txtLookUpDetailValue + "'," +
                        " u.LOOKUPDETAILSTATUS=" + cmbLookUpDetailStatus +
                        " where u.lookUpDetailId=" + txtLookUpDetailId;
            }
        } catch (Exception e) {
            return false;
        }
        sql.update(query);
        System.out.println(" row inserted.");
        return true;
    }
}
