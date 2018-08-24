/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.dao.confpackage;

import com.osp.model.ConfPackage;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ConfPackageDao {
    List<ConfPackage> getListPackage();
    
    int editFee(long id, long fee);
}
