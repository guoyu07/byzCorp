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

    public List<Map<String,Object>> getLookUpDetails (Long lookUpId) throws SQLException {
        String query = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " (select lu.lookUpDetailName from ktu.LOOKUPDETAIL lu where lu.lookUpId = 7 and lu.lookUpDetailId = lud.lookUpDetailStatus) as lookUpDetailStatusName," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud";
        if(lookUpId!=null){
            query +=" where lud.lookUpId = '"+lookUpId+"'";
        }
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getLudTitles() throws SQLException {
        String query = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=1";//Ünvanlar.
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getLudPeriods() throws SQLException {
        String query = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=4";//Aktif Dönemler.
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getLudDepartments() throws SQLException {
        String query = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=2";//Bölümler.
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getLudClasses() throws SQLException {
        String query = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=3";//Sınıflar.
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getLudStatus() throws SQLException {
        String query = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=7";//Statüler.
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getLudInternShipStatus() throws SQLException {
        String query = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=6";//Staj Statüleri.
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getLudInternShipTypes() throws SQLException {
        String query = "select" +
                " lud.lookUpDetailId," +
                " lud.lookUpId," +
                " lud.lookUpDetailName," +
                " lud.lookUpDetailStatus," +
                " lud.lookUpDetailValue" +
                " from ktu.LOOKUPDETAIL lud" +
                " where lud.LOOKUPID=5";//Staj Tipleri.
        return sql.queryForList(query);
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
