package com.example.managementapi.controller.impl;

import com.example.managementapi.constants.ManagementConstants;
import com.example.managementapi.controller.BillRest;
import com.example.managementapi.model.Bill;
import com.example.managementapi.service.BillService;
import com.example.managementapi.utils.ManagementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class BillRestImpl implements BillRest {

    @Autowired
    BillService billService;


    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
            return billService.generateReport(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
       return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try{
            return billService.getBills();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try{
            return billService.getPdf(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
        return billService.deleteBill(id);
        }catch(Exception ex){
            ex.printStackTrace();

        }
        return ManagementUtils.getResponseEntity(ManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
