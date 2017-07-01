package com.byzcorp.controller;

import com.byzcorp.service.userService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by byz on 01.06.2017.
 */

@RestController
@RequestMapping(value = "/user/*")
public class userController {

    @Autowired
    private userService service;

    @RequestMapping(value = "/getUser")
    public List<Map<String,Object>> getUser(String userName, String password){
        List<Map<String,Object>> result = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            result = service.getUser(userName,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(result!=null){
            if(result.size()>0){
                map.put("success",true);
                result.add(map);
            }else{
                map.put("success",false);
                result.add(map);
            }
        }
        return result;
    }

    @RequestMapping(value = "/getUsers")
    public List<Map<String,Object>> getAllUsers(String txtValue){
        List<Map<String,Object>> result = null;
        try {
            result = service.getUsers(txtValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getRoles")
    public List<Map<String,Object>> getRoles(String txtValue){
        List<Map<String,Object>> result = null;
        try {
            result = service.getRoles(txtValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/saveOrUpdateUser")
    public @ResponseBody void saveOrUpdateUser(HttpServletResponse response, HttpServletRequest request) throws IOException {
        JSONObject formData = userService.reqGetJsonObject(request.getParameter("data"));
        JSONObject sendJSON = new JSONObject();
        Boolean success = service.saveOrUpdateUser(formData);
        sendJSON.put("success", success);
        response.getWriter().write(sendJSON.toString());
    }

    @RequestMapping(value = "/updatePassword")
    public @ResponseBody void updatePassword(HttpServletResponse response,Long userId, String password) throws IOException {
        JSONObject sendJSON = new JSONObject();
        Boolean success = service.updatePassword(userId,password);
        sendJSON.put("success", success);
        response.getWriter().write(sendJSON.toString());
    }

    @RequestMapping(value= "userPDF")
    public @ResponseBody void userPdf(HttpServletRequest request,HttpServletResponse response) throws JRException, SQLException, IOException {
        String txtValue = request.getParameter("txtValue");
        if(txtValue==""){
            txtValue = null;
       }
        InputStream inputStream = this.getClass().getResourceAsStream("/reports/kullaniciListesi.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String,Object> parameterMap = new HashMap();
        List<Map<String,Object>> users = service.getUsers(txtValue);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(users);
        parameterMap.put("datasource", jrDataSource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameterMap,jrDataSource);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "inline ; filename = kullaniciListesi.pdf");
        final OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
    }
}