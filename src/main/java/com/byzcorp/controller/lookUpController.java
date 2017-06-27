package com.byzcorp.controller;

import com.byzcorp.service.lookUpService;
import com.byzcorp.service.userService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by byz on 05.06.2017.
 */
@RestController
@RequestMapping(value = "/lookUp/*")
public class lookUpController {

    @Autowired
    private lookUpService service;

    @RequestMapping(value = "/getLookUp")
    public List<Map<String, Object>> getLookUp(Long lookUpId) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLookUp(lookUpId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLookUpDetail")
    public List<Map<String, Object>> getLookUpDetail(Long lookUpDetailId) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLookUpDetail(lookUpDetailId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLookUps")
    public List<Map<String, Object>> getLookUps(String lookUpName) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getActiveLookUps(lookUpName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLookUpDetails")
    public List<Map<String, Object>> getLookUpDetails(Long lookUpId, String query) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLookUpDetails(lookUpId, query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLudTitles")
    public List<Map<String, Object>> getLudTitles(String query) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLudTitles(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLudPeriods")
    public List<Map<String, Object>> getLudPeriods(String query) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLudPeriods(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLudDepartments")
    public List<Map<String, Object>> getLudDepartments(String query) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLudDepartments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLudClasses")
    public List<Map<String, Object>> getLudClasses(String query) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLudClasses(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLudStatus")
    public List<Map<String, Object>> getLudStatus(String query) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLudStatus(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLudInternShipStatus")
    public List<Map<String, Object>> getLudInternShipStatus(String query) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLudInternShipStatus(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLudInternShipPlace")
    public List<Map<String, Object>> getLudInternShipPlace(String query) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLudInternShipPlace(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLudInternShipTypes")
    public List<Map<String, Object>> getLudInternShipTypes(String query) {
        List<Map<String, Object>> result = null;
        try {
            result = service.getLudInternShipTypes(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value = "/saveOrUpdateLookUpDetail")
    public @ResponseBody void saveOrUpdateLookUpDetail(HttpServletResponse response, HttpServletRequest request, Long lookUpId) throws IOException {
        JSONObject formData = userService.reqGetJsonObject(request.getParameter("data"));
        JSONObject sendJSON = new JSONObject();
        Boolean success = service.saveOrUpdateLookUpDetail(formData, lookUpId);
        sendJSON.put("success", success);
        response.getWriter().write(sendJSON.toString());
    }
}
