/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.service.confPackage;

import com.osp.model.ConfPackage;
import com.osp.web.dao.confpackage.ConfPackageDao;
import com.osp.web.dao.msisdn.StockMsisdnDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class ConfPackageServiceImpl implements ConfPackageService {

    @Autowired
    StockMsisdnDao msisdnDao;
    @Autowired
    ConfPackageDao confDao;

    @Override
    public List<ConfPackage> getListConfPackage() {
        List<ConfPackage> confPackages = new ArrayList<>();
        confPackages = msisdnDao.getListPackage();
        return confPackages;
    }

    @Override
    public int editFee(long id, long fee) {
        return confDao.editFee(id, fee);
    }
}
