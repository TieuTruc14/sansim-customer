package com.osp.shedule;

import com.osp.model.GroupMsisdn;
import com.osp.model.GroupPrice;
import com.osp.model.GroupYear;
import com.osp.web.service.group.GroupTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 2/7/2018.
 */
@Component
public class GroupTask {
    private List<GroupMsisdn> listGroupMsisdn;
    private List<GroupPrice> listGroupPrice;
    private List<GroupYear> listGroupYear;

    @Autowired
    GroupTaskService groupTaskService;

    @Scheduled(fixedDelay = 3600000)
    public void scheduleFixedDelayTask() throws Exception {
        listGroupMsisdn=groupTaskService.listGroupMsisdn().orElse(new ArrayList<>());
        listGroupPrice=groupTaskService.listGroupPrice().orElse(new ArrayList<>());
        listGroupYear=groupTaskService.listGroupYear().orElse(new ArrayList<>());
    }


    public List<GroupMsisdn> getListGroupMsisdn() {
        return listGroupMsisdn;
    }

    public void setListGroupMsisdn(List<GroupMsisdn> listGroupMsisdn) {
        this.listGroupMsisdn = listGroupMsisdn;
    }

    public List<GroupPrice> getListGroupPrice() {
        return listGroupPrice;
    }

    public void setListGroupPrice(List<GroupPrice> listGroupPrice) {
        this.listGroupPrice = listGroupPrice;
    }

    public List<GroupYear> getListGroupYear() {
        return listGroupYear;
    }

    public void setListGroupYear(List<GroupYear> listGroupYear) {
        this.listGroupYear = listGroupYear;
    }


}
