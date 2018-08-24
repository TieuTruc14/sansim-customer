package com.osp.web.controller;

import com.osp.common.PagingResult;
import com.osp.model.Customer;
import com.osp.model.GroupMsisdn;
import com.osp.model.GroupPrice;
import com.osp.model.GroupYear;
import com.osp.shedule.GroupTask;
import com.osp.web.service.msisdn.StockMsisdnService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 2/7/2018.
 */
@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupTask groupTask;
    private Logger logger = LogManager.getLogger(IndexController.class);
    @Autowired
    StockMsisdnService msisdnService;

    @GetMapping("/group-msisdn/list")
    public ResponseEntity<List<GroupMsisdn>> listGroupMsisnd() {
        List<GroupMsisdn> items = groupTask.getListGroupMsisdn();
        return new ResponseEntity<List<GroupMsisdn>>(items, HttpStatus.OK);
    }

    @GetMapping("/group-price/list")
    public ResponseEntity<List<GroupPrice>> listGroupPrice() {
        List<GroupPrice> items = groupTask.getListGroupPrice();
        return new ResponseEntity<List<GroupPrice>>(items, HttpStatus.OK);
    }

    @GetMapping("/group-year/list")
    public ResponseEntity<List<GroupYear>> listGroupYear() {
        List<GroupYear> items = groupTask.getListGroupYear();
        return new ResponseEntity<List<GroupYear>>(items, HttpStatus.OK);
    }

    @GetMapping("/search-by-group")
    public ResponseEntity<PagingResult> searchByGroup(Byte telco,Byte sortPrice,Integer gpriceId, Long gmsisdnId, Integer gyearId, @RequestParam(name = "cusId", defaultValue = "0") Long cusId, @RequestParam(name = "p", defaultValue = "1") int pageNumber, HttpServletRequest request) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        if(cusId >0){
            page.setNumberPerPage(15);
        }
        
        if (cusId == 0) {
            cusId = getCusId(request);
        }
        try {
            page = msisdnService.searchByGroup(page, cusId, gpriceId, gmsisdnId, gyearId,sortPrice,telco).orElse(new PagingResult());
        } catch (Exception e) {
            logger.error("have an error method searchByGroup():" + e.getMessage());
            return new ResponseEntity<PagingResult>(new PagingResult(), HttpStatus.OK);
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    private Long getCusId(HttpServletRequest request) {
        try {
            Customer shopManager = (Customer) request.getSession().getAttribute("userShop");
            Long cusId = shopManager.getId();
            return cusId;
        } catch (Exception e) {
        }
        return null;
    }
}
