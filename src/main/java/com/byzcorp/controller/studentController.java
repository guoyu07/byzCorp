package com.byzcorp.controller;

import com.byzcorp.service.studentService;
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
@RequestMapping(value = "/student/*")
public class studentController {

    @Autowired
    private studentService service;

    @RequestMapping(value = "/getStudents")
    public List<Map<String,Object>> getStudents(String txtValue){
        List<Map<String,Object>> result = null;
        try {
            result = service.getStudents(txtValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/saveOrUpdateStudent")
    public @ResponseBody void saveOrUpdateStudent(HttpServletResponse response, HttpServletRequest request) throws IOException {
        JSONObject formData = studentService.reqGetJsonObject(request.getParameter("data"));
        JSONObject sendJSON = new JSONObject();
        Boolean success = service.saveOrUpdateStudent(formData);
        sendJSON.put("success", success);
        response.getWriter().write(sendJSON.toString());
    }

    @RequestMapping(value= "studentPDF")
    public @ResponseBody void studentPdf(HttpServletRequest request,HttpServletResponse response) throws JRException, SQLException, IOException {
        String txtValue = request.getParameter("txtValue");
        if(txtValue==""){
            txtValue = null;
        }
        InputStream inputStream = this.getClass().getResourceAsStream("/reports/ogrenciListesi.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String,Object> parameterMap = new HashMap();
        List<Map<String,Object>> students = service.getStudents(txtValue);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(students);
        parameterMap.put("datasource", jrDataSource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameterMap,jrDataSource);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "inline ; filename = ogrenciListesi.pdf");
        final OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
    }
}