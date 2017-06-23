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
public class internShipDao {
    @Autowired
    private JdbcTemplate sql;

    public List<Map<String, Object>> getInternShips(String txtValue) throws SQLException {
        if(txtValue==""){
            txtValue=null;
        }
        String query = "select" +
                " to_char(s.INTERNSHIPSTARTDATE,'dd/mm/yyyy') as INTERNSHIPSTARTDATE," +
                " to_char(s.INTERNSHIPENDDATE,'dd/mm/yyyy') as INTERNSHIPENDDATE," +
                " s.INTERNSHIPID," +
                " (select us.STUDENTFIRSTNAME ||' '|| us.STUDENTLASTNAME from ktu.student us where us.studentid = s.internshipstudentid) as STUDENTFIRSTANDLASTNAME," +
                " (select us.STUDENTNO from ktu.student us where us.studentid = s.internshipstudentid) as STUDENTNO," +
                " s.INTERNSHIPSTUDENTID," +
                " s.INTERNSHIPTYPEID," +
                " s.INTERNSHIPSTATUSID," +
                " s.INTERNSHIPPERIODID," +
                //" s.INTERNSHIPTOTALDAY," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 5 and lud.lookUpDetailId = s.INTERNSHIPTYPEID) as INTERNSHIPTYPE," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 6 and lud.lookUpDetailId = s.INTERNSHIPSTATUSID) as INTERNSHIPSTATUS," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 4 and lud.lookUpDetailId = s.INTERNSHIPPERIODID) as INTERNSHIPPERIOD" +
                " from ktu.INTERNSHIP s";
        if (txtValue != null) {
            query += " where (select lower(us.STUDENTFIRSTNAME) ||' '|| us.STUDENTLASTNAME from ktu.student us where us.studentid = s.internshipstudentid) like lower('%" + txtValue + "%')" +
                    " or (select lower(us.STUDENTNO) from ktu.student us where us.studentid = s.internshipstudentid) like lower('%" + txtValue + "%')" +
                    " or (select lower(lud.lookUpDetailName) from ktu.LOOKUPDETAIL lud where lud.lookUpId = 5 and lud.lookUpDetailId = s.INTERNSHIPTYPEID) like lower('%" + txtValue + "%')" +
                    " or (select lower(lud.lookUpDetailName) from ktu.LOOKUPDETAIL lud where lud.lookUpId = 6 and lud.lookUpDetailId = s.INTERNSHIPSTATUSID) like lower('%" + txtValue + "%')" +
                    " or (select lower(lud.lookUpDetailName) from ktu.LOOKUPDETAIL lud where lud.lookUpId = 4 and lud.lookUpDetailId = s.INTERNSHIPPERIODID) like lower('%" + txtValue + "%')";
        }
        return sql.queryForList(query);
    }

    public Boolean saveOrUpdateInternShip(String txtInternShipId,Long cmbInternShipStudent, String dateStartInternShip, String dateEndInternShip, Long cmbInternShipPeriod, Long cmbInternShipType, Long cmbInternShipStatu) {

        String query = "";
        try {
            if (txtInternShipId.equals("")) {
                query = " insert into ktu.INTERNSHIP (" +
                        " INTERNSHIPID," +
                        " INTERNSHIPSTUDENTID," +
                        " INTERNSHIPSTARTDATE," +
                        " INTERNSHIPENDDATE," +
                        " INTERNSHIPPERIODID," +
                        " INTERNSHIPSTATUSID," +
                        " INTERNSHIPTYPEID) values (" +
                        "ktu.internShipSeq.nextval,'"
                        + cmbInternShipStudent + "',"
                        + "to_date('"+dateStartInternShip+"',',dd/MM/yyyy')"+ ","
                        + "to_date('"+dateEndInternShip+"',',dd/MM/yyyy')" + ","
                        + cmbInternShipPeriod + ","
                        + cmbInternShipStatu + ","
                        + cmbInternShipType + ")";
                System.out.println(" row inserted.");
            } else {
                query = "update ktu.INTERNSHIP i set " +
                        " i.INTERNSHIPSTUDENTID ='" + cmbInternShipStudent + "'," +
                        " i.INTERNSHIPSTARTDATE=" + "to_date('"+dateStartInternShip+"','dd/MM/yyyy')" + "," +
                        " i.INTERNSHIPENDDATE=" + "to_date('"+dateEndInternShip+"','dd/MM/yyyy')" + "," +
                        " i.INTERNSHIPPERIODID=" + cmbInternShipPeriod + "," +
                        " i.INTERNSHIPSTATUSID=" + cmbInternShipStatu + "," +
                        " i.INTERNSHIPTYPEID=" + cmbInternShipType +
                        " where i.INTERNSHIPID=" + txtInternShipId;
                System.out.println(" row updated.");
            }
        } catch (Exception e) {
            return false;
        }
        sql.update(query);
        return true;
    }
    public Boolean deleteInternShip(Long internShipId) {
        String query =  "delete ktu.INTERNSHIP u " +
                " where u.internShipId=" + internShipId;
        System.out.println(" row deleted.");
        sql.update(query);
        return true;
    }
}
