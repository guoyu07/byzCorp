package com.byzcorp.controller;

import com.byzcorp.service.internShipService;
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
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by byz on 01.06.2017.
 */

@RestController
@RequestMapping(value = "/internShip/*")
public class internShipController {

    @Autowired
    private internShipService service;

    @RequestMapping(value = "/getInternShips")
    public List<Map<String,Object>> getInternShips(String txtValue){
        List<Map<String,Object>> result = null;
        try {
            result = service.getInternShips(txtValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getInternShipsDetails")
    public List<Map<String, Object>> getInternShipsDetails(Long internShipId, String query) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getInternShipsDetails(internShipId, query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getInternShipDetailsCount")
    public List<Map<String, Object>> getInternShipDetailsCount(Long internShipId) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getInternShipDetailsCount(internShipId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/saveOrUpdateInternShip")
    public @ResponseBody void saveOrUpdateInternShip(HttpServletResponse response, HttpServletRequest request, Long userId) throws IOException, ParseException, SQLException {
        JSONObject formData = studentService.reqGetJsonObject(request.getParameter("data"));
        JSONObject requestData = studentService.reqGetJsonObject(request.getParameter("requestData"));
        JSONObject sendJSON = new JSONObject();
        if(formData!=null){
            sendJSON = service.saveOrUpdateInternShip(formData, userId);
        }else{
            sendJSON = service.saveOrUpdateInternShipRequest(requestData,userId);
        }
        response.getWriter().write(sendJSON.toString());
    }

    @RequestMapping(value = "/saveOrUpdateInternShipsDetail")
    public @ResponseBody void saveOrUpdateInternShipsDetail(HttpServletResponse response, HttpServletRequest request) throws IOException, ParseException, SQLException {
        JSONObject formData = studentService.reqGetJsonObject(request.getParameter("data"));
        JSONObject selected = studentService.reqGetJsonObject(request.getParameter("selected"));
        JSONObject sendJSON = new JSONObject();
        sendJSON = service.saveOrUpdateInternShipsDetail(formData, selected);
        response.getWriter().write(sendJSON.toString());
    }

    @RequestMapping(value= "internShipPDF")
    public @ResponseBody void internShipPdf(HttpServletRequest request,HttpServletResponse response) throws JRException, SQLException, IOException {
        String txtValue = request.getParameter("txtValue");
        if(txtValue==""){
            txtValue = null;
        }
        InputStream inputStream = this.getClass().getResourceAsStream("/reports/internShipList.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String,Object> parameterMap = new HashMap();
        List<Map<String,Object>> internShips = service.getInternShips(txtValue);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(internShips);
        parameterMap.put("datasource", jrDataSource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameterMap,jrDataSource);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "inline ; filename = internShipList.pdf");
        final OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
    }

    @RequestMapping(value = "/deleteInternShip")
    public @ResponseBody void deleteInternShip(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Long internShipId = Long.parseLong(request.getParameter("internShipId"));
        JSONObject sendJSON = new JSONObject();
        Boolean success = service.deleteInternShip(internShipId);
        sendJSON.put("success", success);
        response.getWriter().write(sendJSON.toString());
    }

    @RequestMapping(value = "/deleteInternShipDetail")
    public @ResponseBody void deleteInternShipDetail(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Long internShipDetailId = Long.parseLong(request.getParameter("internShipDetailId"));
        JSONObject sendJSON = new JSONObject();
        Boolean success = service.deleteInternShipDetail(internShipDetailId);
        sendJSON.put("success", success);
        response.getWriter().write(sendJSON.toString());
    }
}