package com.osp.web.service.msisdn;

import com.osp.common.PagingResult;
import com.osp.model.ConfPackage;
import com.osp.model.CustService;
import com.osp.model.StockMsisdn;
import com.osp.model.view.SearchIndex;
import java.util.Date;
import java.util.List;

import java.util.Optional;

/**
 * Created by Admin on 12/21/2017.
 */
public interface StockMsisdnService {

    Optional<PagingResult> searchIndex(PagingResult page,Long id, SearchIndex searchIndex);
    Optional<PagingResult> searchDetail(PagingResult page,Long id);
    Optional<PagingResult> searchByGroupPrice(PagingResult page,Long customerId,Integer id);
    Optional<PagingResult> searchByGroupMsisdn(PagingResult page,Long customerId,Long id);
    Optional<PagingResult> searchByGroupYear(PagingResult page,Long customerId,Integer id);
    Optional<PagingResult> searchByGroup(PagingResult page,Long customerId,Integer gpriceId,Long gmsisdnId,Integer gyearId,Byte sortPrice,Byte telco);

    Optional<Object[]> simDetailById(Long id);

    int countTotalMsisdn(String msisdn, String tel, String confirmExpired, Date fromDate, Date toDate, Long userId, String confStatus, String[] telco);

    List<StockMsisdn> getPagingMsisdn(int page, int limit, String msisdn, String tel, String confirmExpired, Date fromDate, Date toDate, Long userId, String confStatus, String[] telco);

    boolean checkExitsMsisdn(String msisdn, Long userId);

    CustService getServiceRegister(Long userId);

    int countTotalMsisdnUpload(Long userId);

    boolean insertMsisdn(StockMsisdn msiUpload);
    
    StockMsisdn getMsisdnById(Long id,Long userId);

    boolean updateMsisdn(StockMsisdn msi);

    boolean removeListMsisdn(List<StockMsisdn> listMsisdn);

    ConfPackage getPackageById(Long id);

    List<ConfPackage> getListPackage();

    StockMsisdn getMsisdn(String msisdn, Long id);
    
    int editFee(long id, long price);
    
    int insertBatchMsisdn(List<StockMsisdn> msiUploads);

}
