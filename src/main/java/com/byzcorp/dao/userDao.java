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
public class userDao {
    @Autowired
    private JdbcTemplate sql;

//byrmbyz branch test 1
    public List<Map<String, Object>> getUser(String userName, String password) throws SQLException {
        String query = "select" +
                " u.userFirstName," +
                " u.userLastName," +
                " u.userName," +
                " u.userPassword," +
                " u.userEmail," +

                " (select" +
                " lud.LOOKUPDETAILNAME" +
                " from ktu.LOOKUPDETAIL lud, ktu.LOOKUP lu" +
                " where lu.LOOKUPID=lud.LOOKUPID" +
                " and lud.LOOKUPDETAILID=u.USERTITLEID and lud.LOOKUPID=1) as userTitle" +

                " from ktu.USERS u" +
                " where u.userName = " + "'" + userName + "'" +
                " and u.userPassword = " + "'" + password + "'";
        return sql.queryForList(query);
    }

    public List<Map<String, Object>> getUsers(String txtValue) throws SQLException {
        String query = "select" +
                " u.userFirstName ||' '|| u.userLastName as userFirstandLastName," +
                " u.userFirstName," +
                " u.userLastName," +
                " u.userName," +
                " u.userPassword," +
                " u.userEmail," +
                " ur.userRoleName," +
                " u.userId," +
                " u.userStatus," +
                " (select lud.lookUpDetailName from ktu.LOOKUPDETAIL lud where lud.lookUpId = 7 and lud.lookUpDetailId = u.userStatus) as userStatusName," +
                //" case u.userStatus when 1 then 'Aktif' else 'Pasif' end as userStatusName," +
                " u.userRoleId," +
                " u.userTitleId," +
                " lud.lookUpDetailName as userTitle" +
                " from ktu.USERS u, ktu.USERROLE ur, ktu.LOOKUPDETAIL lud" +
                " where u.userRoleId = ur.userRoleId and lud.lookupid=1 and lud.lookupdetailId = u.usertitleid";
        if (txtValue !=null) {
            query += " and (lower(u.userFirstName) like lower('%" + txtValue + "%')" +
                    " or lower(u.userLastName) like lower('%" + txtValue + "%')" +
                    " or lower(u.userName) like lower('%" + txtValue + "%')" +
                    " or lower(u.userEmail) like lower('%" + txtValue + "%')" +
                    " or lower(lud.lookUpDetailName) like lower('%" + txtValue + "%')" +
                    " or (select lower(ur2.userRoleName)" +
                    " from ktu.userRole ur2" +
                    " where ur2.userRoleId = u.userRoleId) like lower('%" + txtValue + "%')" +
                    " )";
        }
        return sql.queryForList(query);
    }

    public List<Map<String, Object>> getRoles(String txtValue) throws SQLException {
        String query = "select" +
                " ur.userRoleId," +
                " ur.userRoleName," +
                " ur.userRoleStatus" +

                " from ktu.USERROLE ur";
        if (txtValue != null) {
            query += " where lower(u.userRoleName) like lower('%" + txtValue + "%')";
        }
        return sql.queryForList(query);
    }

    public Boolean saveOrUpdateUser(String txtUserId, String txtUserFirstName, String txtUserLastName, String txtUserName, String txtUserEmail, Long cmbUserRole, Long cmbUserTitle, Long cmbUserStatus) {
        String query = "";
        try {
            if (txtUserId.equals("")) {
                query = " insert into ktu.USERS (" +
                        " userId," +
                        " userFirstName," +
                        " userLastName," +
                        " userName," +
                        " userEmail," +
                        " userRoleId," +
                        " userTitleId," +
                        " userStatus) values (" +
                        "ktu.usersSeq.nextval,'"
                        + txtUserFirstName + "','"
                        + txtUserLastName + "','"
                        + txtUserName + "','"
                        + txtUserEmail + "',"
                        + cmbUserRole + ","
                        + cmbUserTitle + ","
                        + cmbUserStatus + ")";

            } else {
                query = "update ktu.USERS u set " +
                        " u.userFirstName ='" + txtUserFirstName + "'," +
                        " u.userLastName='" + txtUserLastName + "'," +
                        " u.userName='" + txtUserName + "'," +
                        " u.userEmail='" + txtUserEmail + "'," +
                        " u.userRoleId=" + cmbUserRole + "," +
                        " u.userTitleId=" + cmbUserTitle + "," +
                        " u.userStatus=" + cmbUserStatus +
                        " where u.userId=" + txtUserId;
            }
        } catch (Exception e) {
            return false;
        }
        sql.update(query);
        System.out.println(" row inserted.");
        return true;
    }
}
