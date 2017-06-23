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
public class studentDao {
    @Autowired
    private JdbcTemplate sql;

    public List<Map<String, Object>> getStudents(String txtValue) throws SQLException {
        if(txtValue==""){
            txtValue=null;
        }
        String query = "select" +
                " s.studentNo ||'-'||s.studentFirstName ||' '|| s.studentLastName as studentIdandFirstandLastName," +
                " s.studentFirstName ||' '|| s.studentLastName as studentFirstandLastName," +
                " s.studentFirstName," +
                " s.studentLastName," +
                " s.studentCountryId," +
                " s.studentId," +
                " s.studentNo," +
                " s.STUDENTSTATUS," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 7 and lud.lookUpDetailId = s.studentStatus) as studentStatusName," +
                //" case s.studentStatus when 1 then 'Aktif' else 'Pasif' end as studentStatusName," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 4 and lud.lookUpDetailId = s.studentPeriodId) as studentPeriod," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 3 and lud.lookUpDetailId = s.studentClassId) as studentClass," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 2 and lud.lookUpDetailId = s.studentDepartmentId) as studentDepartment," +
                " (select lud.lookUpDetailId from ktu.LOOKUPDETAIL lud where lud.lookUpId = 4 and lud.lookUpDetailId = s.studentPeriodId) as studentPeriodId," +
                " (select lud.lookUpDetailId from ktu.LOOKUPDETAIL lud where lud.lookUpId = 3 and lud.lookUpDetailId = s.studentClassId) as studentClassId," +
                " (select lud.lookUpDetailId from ktu.LOOKUPDETAIL lud where lud.lookUpId = 2 and lud.lookUpDetailId = s.studentDepartmentId) as studentDepartmentId" +
                " from ktu.STUDENT s";
        if (txtValue != null) {
            query += " where lower(s.studentFirstName) like lower('%" + txtValue + "%')" +
                    " or lower(s.studentLastName) like lower('%" + txtValue + "%')" +
                    " or s.studentCountryId like lower('%" + txtValue + "%')" +
                    " or s.studentNo like lower('%" + txtValue + "%')" +
                    " or (select lower(lud.lookUpDetailName) from ktu.LOOKUPDETAIL lud where lud.lookUpId = 7 and lud.lookUpDetailId = s.studentStatus) like lower('%" + txtValue + "%')" +
                    " or (select lower(lud.lookUpDetailName) from ktu.LOOKUPDETAIL lud where lud.lookUpId = 4 and lud.lookUpDetailId = s.studentPeriodId) like lower('%" + txtValue + "%')" +
                    " or (select lower(lud.lookUpDetailName) from ktu.LOOKUPDETAIL lud where lud.lookUpId = 3 and lud.lookUpDetailId = s.studentClassId) like lower('%" + txtValue + "%')" +
                    " or (select lower(lud.lookUpDetailName) from ktu.LOOKUPDETAIL lud where lud.lookUpId = 2 and lud.lookUpDetailId = s.studentDepartmentId) like lower('%" + txtValue + "%')";
        }
        return sql.queryForList(query);
    }

    public Boolean saveOrUpdateStudent(String txtStudentId, String txtStudentNo, String txtStudentFirstName, String txtStudentLastName, String txtStudentCountryId, String txtStudentPeriodId, Long cmbStudentClassId, Long cmbStudentDepartmentId, Long cmbStudentStatus) {
        String query = "";
        try {
            if (txtStudentId.equals("")) {
                query = " insert into ktu.STUDENT (" +
                        " studentId," +
                        " studentNo," +
                        " studentFirstName," +
                        " studentLastName," +
                        " STUDENTCOUNTRYID," +
                        " STUDENTPERIODID," +
                        " STUDENTCLASSID," +
                        " STUDENTDEPARTMENTID," +
                        " studentStatus) values (" +
                        "ktu.studentsSeq.nextval,'"
                        + txtStudentNo + "','"
                        + txtStudentFirstName + "','"
                        + txtStudentLastName + "','"
                        + txtStudentCountryId + "','"
                        + txtStudentPeriodId + "',"
                        + cmbStudentClassId + ","
                        + cmbStudentDepartmentId + ","
                        + cmbStudentStatus + ")";
                System.out.println(" row inserted.");

            } else {
                query = "update ktu.STUDENT u set " +
                        " u.studentFirstName ='" + txtStudentFirstName + "'," +
                        " u.studentLastName='" + txtStudentLastName + "'," +
                        " u.STUDENTCOUNTRYID='" + txtStudentCountryId + "'," +
                        " u.studentNo='" + txtStudentNo + "'," +
                        " u.STUDENTPERIODID='" + txtStudentPeriodId + "'," +
                        " u.STUDENTCLASSID=" + cmbStudentClassId + "," +
                        " u.STUDENTDEPARTMENTID=" + cmbStudentDepartmentId + "," +
                        " u.studentStatus=" + cmbStudentStatus +
                        " where u.studentId=" + txtStudentId;
                System.out.println(" row updated.");
            }
        } catch (Exception e) {
            return false;
        }
        sql.update(query);
        return true;
    }
}
