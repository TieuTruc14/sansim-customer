package com.osp.web.service.msisdn;

import com.osp.common.PagingResult;
import com.osp.model.*;
import com.osp.model.view.SearchIndex;
import com.osp.web.dao.group.GroupTaskDao;
import com.osp.web.dao.msisdn.StockMsisdnDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Admin on 12/21/2017.
 */
@Service
public class StockMsisdnServiceImpl implements StockMsisdnService {

    @Autowired
    StockMsisdnDao msisdnDao;
    @Autowired
    GroupTaskDao groupTaskDao;

    @Override
    public Optional<PagingResult> searchIndex(PagingResult page,Long id, SearchIndex searchIndex) {
        return msisdnDao.searchIndex(page,id, searchIndex);
    }

    @Override
    public Optional<PagingResult> searchDetail(PagingResult page, Long id) {
        return msisdnDao.searchDetail(page,id);
    }

    @Override
    public Optional<PagingResult> searchByGroupPrice(PagingResult page,Long customerId, Integer id) {
        GroupPrice item=groupTaskDao.getGroupPrice(id).orElse(null);
        if(item==null) return Optional.ofNullable(new PagingResult());
        return msisdnDao.searchByGroupPrice(page,customerId,item.getMin(),item.getMax());
    }

    @Override
    public Optional<PagingResult> searchByGroupMsisdn(PagingResult page,Long customerId, Long id) {
        return msisdnDao.searchByGroupMsisdn(page,customerId,id);
    }

    @Override
    public Optional<PagingResult> searchByGroupYear(PagingResult page,Long customerId, Integer id) {
        GroupYear item=groupTaskDao.getGroupYear(id).orElse(null);
        if(item==null) return Optional.ofNullable(new PagingResult());
        return msisdnDao.searchByGroupYear(page,customerId,item.getYear());
    }

    @Override
    public Optional<PagingResult> searchByGroup(PagingResult page, Long customerId, Integer gpriceId, Long gmsisdnId, Integer gyearId,Byte sortPrice,Byte telco) {
        GroupPrice itemPrice=null;
        if(gpriceId!=null && gpriceId.intValue()>0){
            itemPrice=groupTaskDao.getGroupPrice(gpriceId).orElse(null);
        }
        GroupYear itemYear=null;
        if(gyearId!=null && gyearId.intValue()>0){
            itemYear=groupTaskDao.getGroupYear(gyearId).orElse(null);
        }
        if(gmsisdnId!=null && gmsisdnId.longValue()==0){
            gmsisdnId=null;
        }
        if(telco!=null && telco.intValue()==9){
            telco=null;
        }
        return msisdnDao.searchByGroup(page,customerId,itemPrice,gmsisdnId,itemYear,sortPrice,telco);
    }

    @Override
    public Optional<Object[]> simDetailById(Long id) {
        return msisdnDao.simDetailById(id);
    }

    @Override
    public int countTotalMsisdn(String msisdn, String tel, String confirmExpired, Date fromDate, Date toDate, Long userId, String confStatus, String[] telco) {
        int total = 0;
        total = msisdnDao.countTotalMsisdn(msisdn, tel, confirmExpired, fromDate, toDate, userId, confStatus, telco);
        return total;
    }

    @Override
    public List<StockMsisdn> getPagingMsisdn(int page, int limit, String msisdn, String tel, String confirmExpired, Date fromDate, Date toDate, Long userId, String confStatus, String[] telco) {
        List<StockMsisdn> listResult = new ArrayList<StockMsisdn>();
        listResult = msisdnDao.getPagingMsisdn(page, limit, msisdn, tel, confirmExpired, fromDate, toDate, userId, confStatus, telco);

        return listResult;

    }

    @Override
    public boolean checkExitsMsisdn(String msisdn, Long userId) {
        boolean result = false;
        result = msisdnDao.checkExitsMsisdn(msisdn, userId);
        return result;

    }

    @Override
    public CustService getServiceRegister(Long userId) {
        CustService custService = new CustService();
        custService = msisdnDao.getServiceRegister(userId);
        return custService;
    }

    @Override
    public int countTotalMsisdnUpload(Long userId) {
        int total = 0;
        total = msisdnDao.countTotalMsisdnUpload(userId);
        return total;
    }

    @Override
    public boolean insertMsisdn(StockMsisdn msiUpload) {
        boolean result = false;
        result = msisdnDao.insertMsisdn(msiUpload);
        return result;
    }

    @Override
    public StockMsisdn getMsisdnById(Long id, Long userId) {
        StockMsisdn msi = new StockMsisdn();
        msi = msisdnDao.getMsisdnById(id, userId);
        return msi;
    }

    @Override
    public boolean updateMsisdn(StockMsisdn msi) {
        boolean result = false;
        result = msisdnDao.updateMsisdn(msi);
        return result;
    }

    @Override
    public boolean removeListMsisdn(List<StockMsisdn> listMsisdn) {
        boolean result = false;
        result = msisdnDao.removeListMsisdn(listMsisdn);
        return result;
    }

    @Override
    public ConfPackage getPackageById(Long id) {
        ConfPackage confPackage = new ConfPackage();
        confPackage = msisdnDao.getPackageById(id);
        return confPackage;
    }

    @Override
    public List<ConfPackage> getListPackage() {
        List<ConfPackage> confPackages = new ArrayList<>();
        confPackages = msisdnDao.getListPackage();
        return confPackages;
    }

    @Override
    public StockMsisdn getMsisdn(String msisdn, Long userId) {
        StockMsisdn msi = new StockMsisdn();
        msi = msisdnDao.getMsisdn(msisdn, userId);
        return msi;
    }

    @Override
    public int editFee(long id, long price) {
        return msisdnDao.editFee(id, price);
    }

    @Override
    public int insertBatchMsisdn(List<StockMsisdn> msiUploads) {
        int result = 0;
        result = msisdnDao.insertBatchMsisdn(msiUploads);
        return result;
    }
    
    
    
    

}
