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
                " (select lu.lookUpDetailName ||'-'|| u.USERFIRSTNAME ||' '|| u.USERLASTNAME from ktu.users u, ktu.LOOKUPDETAIL lu where lu.LOOKUPDETAILID = u.USERTITLEID and u.USERID = s.INTERNSHIPUPDATEUSERID) as USERTITLEFIRSTANDLASTNAME," +
                " (select us.STUDENTNO from ktu.student us where us.studentid = s.internshipstudentid) as STUDENTNO," +
                " s.INTERNSHIPSTUDENTID," +
                " s.INTERNSHIPTYPEID," +
                " s.INTERNSHIPSTATUSID," +
                " s.INTERNSHIPPERIODID," +
                " s.INTERNSHIPDAY," +
                " (select sum(ind.INTERNSHIPDETAILCOMPDAY) from ktu.INTERNSHIPDETAIL ind where ind.INTERNSHIPID = s.INTERNSHIPID) as INTERNSHIPDONEDAY," +
                " s.INTERNSHIPPLACEID," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 9 and lud.lookUpDetailId = s.INTERNSHIPPLACEID) as INTERNSHIPPLACE," +
                " s.INTERNSHIPDESC," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 5 and lud.lookUpDetailId = s.INTERNSHIPTYPEID) as INTERNSHIPTYPE," +
                " case when s.INTERNSHIPDAY-(select sum(ind.INTERNSHIPDETAILCOMPDAY) from ktu.INTERNSHIPDETAIL ind where ind.INTERNSHIPID = s.INTERNSHIPID)<=0 then 'Tamamlandı' else 'Tamamlanmadı' end as INTERNSHIPSTATUS," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 10 and lud.lookUpDetailId = s.INTERNSHIPACCEPTID) as INTERNSHIPACCEPTSTATUS," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 4 and lud.lookUpDetailId = s.INTERNSHIPPERIODID) as INTERNSHIPPERIOD" +
                " from ktu.INTERNSHIP s";
        if (txtValue != null) {
            query += " where (select lower(us.STUDENTFIRSTNAME) ||' '|| us.STUDENTLASTNAME from ktu.student us where us.studentid = s.internshipstudentid) like lower('%" + txtValue + "%')" +
                    " or (select lower(us.STUDENTNO) from ktu.student us where us.studentid = s.internshipstudentid) like lower('%" + txtValue + "%')" +
                    " or (select lower(lud.lookUpDetailName) from ktu.LOOKUPDETAIL lud where lud.lookUpId = 5 and lud.lookUpDetailId = s.INTERNSHIPTYPEID) like lower('%" + txtValue + "%')" +
                    " or lower(case when s.INTERNSHIPDAY-(select sum(ind.INTERNSHIPDETAILCOMPDAY) from ktu.INTERNSHIPDETAIL ind where ind.INTERNSHIPID = s.INTERNSHIPID)<=0 then 'Tamamlandı' else 'Tamamlanmadı' end) like lower('%" + txtValue + "%')" +
                    " or (select lower(lud.lookUpDetailName) from ktu.LOOKUPDETAIL lud where lud.lookUpId = 10 and lud.lookUpDetailId = s.INTERNSHIPACCEPTID) like lower('%" + txtValue + "%')" +
                    " or (select lower(lud.lookUpDetailName) from ktu.LOOKUPDETAIL lud where lud.lookUpId = 4 and lud.lookUpDetailId = s.INTERNSHIPPERIODID) like lower('%" + txtValue + "%')";
        }
        return sql.queryForList(query);
    }

    public List<Map<String,Object>> getInternShipsDetails (Long internShipId, String query) throws SQLException {
        if(query==""){
            query=null;
        }
        String q = "select" +
                " ind.INTERNSHIPDETAILID," +
                " ind.INTERNSHIPID," +
                " ind.INTERNSHIPDETAILPLACEID," +
                " to_char(ind.INTERNSHIPDETAILCOMPDATE,'dd/mm/yyyy') as INTERNSHIPDETAILCOMPDATE," +
                " ind.INTERNSHIPDETAILCOMPDAY," +
                " ind.INTERNSHIPDETAILDESC," +
                " (select lu.lookUpDetailName from ktu.LOOKUPDETAIL lu where lu.lookUpId = 9 and lu.lookUpDetailId = ind.internShipDetailPlaceId) as internShipDetailPlace" +
                " from ktu.INTERNSHIPDETAIL ind";
        if(internShipId!=null){
            q +=" where ind.internShipId = '"+internShipId+"'";
        }
        return sql.queryForList(q);
    }

    public List<Map<String, Object>> getInternShipCount(Long cmbInternShipPeriod,Long cmbInternShipStudent, Long internShipId) throws SQLException {

        String query = "select" +
                " s.INTERNSHIPSTUDENTID," +
                " s.INTERNSHIPSTATUSID," +
                " s.INTERNSHIPPERIODID," +
                " (select l.lookUpDetailName from ktu.lookUpDetail l where l.lookUpDetailId = s.INTERNSHIPACCEPTID) as INTERNSHIPACCEPTSTATUS," +
                " nvl(sum(s.INTERNSHIPDAY),0) totalInternShipDay" +
                " from ktu.INTERNSHIP s where 1=1";
        if (cmbInternShipPeriod != null) {
            query += "and s.INTERNSHIPPERIODID = "+ cmbInternShipPeriod;
        }
        if (cmbInternShipStudent != null) {
            query += "and s.INTERNSHIPSTUDENTID = "+ cmbInternShipStudent;
        }

        if (internShipId != null) {
            query += "and s.INTERNSHIPID = "+ internShipId;
        }
        query +=" group by s.INTERNSHIPSTUDENTID,s.INTERNSHIPSTATUSID,s.INTERNSHIPPERIODID,s.INTERNSHIPACCEPTID";
        return sql.queryForList(query);
    }

    public List<Map<String, Object>> getInternShipDetailsCount(Long internShipId) throws SQLException {

        String query = "select" +
                " nvl(sum(s.INTERNSHIPDETAILCOMPDAY),0) totalInternShipDetailCompDay" +
                " from ktu.INTERNSHIPDETAIL s where 1=1";

        if (internShipId != null) {
            query += "and s.INTERNSHIPID = "+ internShipId;
        }
        //query +=" group by s.INTERNSHIPSTUDENTID,s.INTERNSHIPSTATUSID,s.INTERNSHIPPERIODID";
        return sql.queryForList(query);
    }

    public Boolean saveOrUpdateInternShip(String txtInternShipId,String txtInternShipDay,Long cmbInternShipPlace,String txtInternShipDesc, Long cmbInternShipStudent, String dateStartInternShip, String dateEndInternShip, Long cmbInternShipPeriod, Long cmbInternShipType,Long cmbInternShipAcceptStatus, Long userId) {

        String query = "";
        try {
            if (txtInternShipId.equals("")) {
                query = " insert into ktu.INTERNSHIP (" +
                        " INTERNSHIPID," +
                        " INTERNSHIPSTUDENTID," +
                        " INTERNSHIPSTARTDATE," +
                        " INTERNSHIPENDDATE," +
                        " INTERNSHIPPERIODID," +
                        " INTERNSHIPTYPEID," +
                        " INTERNSHIPDAY," +
                        " INTERNSHIPPLACEID," +
                        " INTERNSHIPUPDATEUSERID," +
                        " INTERNSHIPACCEPTID," +
                        " INTERNSHIPDESC) values (" +
                        "ktu.internShipSeq.nextval,'"
                        + cmbInternShipStudent + "',"
                        + "to_date('"+dateStartInternShip+"',',dd/MM/yyyy')"+ ","
                        + "to_date('"+dateEndInternShip+"',',dd/MM/yyyy')" + ","
                        + cmbInternShipPeriod + ","
                        + cmbInternShipType + ","
                        + txtInternShipDay + ","
                        + cmbInternShipPlace + ","
                        + userId + ","
                        + cmbInternShipAcceptStatus + ",'"
                        + txtInternShipDesc + "')";
                System.out.println(" row inserted.");
            } else {
                query = "update ktu.INTERNSHIP i set " +
                        " i.INTERNSHIPSTUDENTID ='" + cmbInternShipStudent + "'," +
                        " i.INTERNSHIPSTARTDATE=" + "to_date('"+dateStartInternShip+"','dd/MM/yyyy')" + "," +
                        " i.INTERNSHIPENDDATE=" + "to_date('"+dateEndInternShip+"','dd/MM/yyyy')" + "," +
                        " i.INTERNSHIPPERIODID=" + cmbInternShipPeriod + "," +
                        " i.INTERNSHIPTYPEID=" + cmbInternShipType + "," +
                        " i.INTERNSHIPDAY=" + txtInternShipDay + "," +
                        " i.INTERNSHIPUPDATEUSERID=" + userId + "," +
                        " i.INTERNSHIPPLACEID=" + cmbInternShipPlace + "," +
                        " i.INTERNSHIPACCEPTID=" + cmbInternShipAcceptStatus + "," +
                        " i.INTERNSHIPDESC='" + txtInternShipDesc +"'"+
                        " where i.INTERNSHIPID=" + txtInternShipId;
                System.out.println(" row updated.");
            }
        } catch (Exception e) {
            return false;
        }
        sql.update(query);
        return true;
    }

    public Boolean saveOrUpdateInternShipsDetail(String txtInternShipDetailId, Long internShipId, Long txtInternShipDetailCompDate, String dateDeliveryInternShip, String txtInternShipDetailDesc, Long cmbInternShipDetailPlace) {

        String query = "";
        try {
            if (txtInternShipDetailId.equals("")) {
                query = " insert into ktu.INTERNSHIPDETAIL (" +
                        " INTERNSHIPDETAILID," +
                        " INTERNSHIPID," +
                        " INTERNSHIPDETAILPLACEID," +
                        " INTERNSHIPDETAILCOMPDATE," +
                        " INTERNSHIPDETAILCOMPDAY," +
                        " INTERNSHIPDETAILDESC)" +
                        " values (" +
                        "ktu.INTERNSHIPDETAILSEQ.nextval,"
                        + internShipId + ","
                        + cmbInternShipDetailPlace + ","
                        + "to_date('"+dateDeliveryInternShip+"',',dd/MM/yyyy')"+ ","
                        + txtInternShipDetailCompDate + ",'"
                        + txtInternShipDetailDesc + "')";
                System.out.println(" row inserted.");
            } else {
                query = "update ktu.INTERNSHIPDETAIL i set " +
                        " i.INTERNSHIPDETAILID =" + txtInternShipDetailId + "," +
                        " i.INTERNSHIPID =" + internShipId + "," +
                        " i.INTERNSHIPDETAILPLACEID =" + cmbInternShipDetailPlace + "," +
                        " i.INTERNSHIPDETAILCOMPDATE=" + "to_date('"+dateDeliveryInternShip+"','dd/MM/yyyy')" + "," +
                        " i.INTERNSHIPDETAILCOMPDAY=" + txtInternShipDetailCompDate + "," +
                        " i.INTERNSHIPDETAILDESC='" + txtInternShipDetailDesc + "'" +
                        " where i.INTERNSHIPDETAILID=" + txtInternShipDetailId;
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
        try {
            sql.update(query);
        }catch (Exception e){
            new SQLException("Hata",e.getMessage().toString());
        }

        return true;
    }

    public Boolean deleteInternShipDetail(Long internShipDetailId) {
        String query =  "delete ktu.INTERNSHIPDETAIL u " +
                " where u.internShipDetailId=" + internShipDetailId;
        System.out.println(" row deleted.");
        sql.update(query);
        return true;
    }
}
