/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.service.confPackage;

import com.osp.model.ConfPackage;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ConfPackageService {
    List<ConfPackage> getListConfPackage();
    
    int editFee(long id, long fee);
}
