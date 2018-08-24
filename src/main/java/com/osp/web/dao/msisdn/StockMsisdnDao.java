package com.osp.web.dao.msisdn;

import com.osp.common.PagingResult;
import com.osp.model.*;
import com.osp.model.view.SearchIndex;
import java.util.Date;
import java.util.List;

import java.util.Optional;

/**
 * Created by Admin on 12/21/2017.
 */
public interface StockMsisdnDao {

    Optional<PagingResult> searchIndex(PagingResult page,Long id, SearchIndex searchIndex);
    Optional<PagingResult> searchDetail(PagingResult page,Long id);
    Optional<PagingResult> searchByGroupPrice(PagingResult page,Long id,Long from,Long to);
    Optional<PagingResult> searchByGroupMsisdn(PagingResult page,Long id,Long groupId);
    Optional<PagingResult> searchByGroupYear(PagingResult page,Long id,Integer year);
    Optional<PagingResult> searchByGroup(PagingResult page, Long cusId, GroupPrice groupPrice, Long gmsisdnId, GroupYear groupYear,Byte sortPrice,Byte telco);

    Optional<Object[]> simDetailById(Long id);

    int countTotalMsisdn(String msisdn, String tel, String confirmExpired, Date fromDate, Date toDate, Long userId, String confStatus, String[] telco);

    List<StockMsisdn> getPagingMsisdn(int page, int limit, String msisdn, String tel, String confirmExpired, Date fromDate, Date toDate, Long userId, String confStatus, String[] telco);

    boolean checkExitsMsisdn(String msisdn, Long userId);

    CustService getServiceRegister(Long userId);

    int countTotalMsisdnUpload(Long userId);

    boolean insertMsisdn(StockMsisdn msiUpload);

    StockMsisdn getMsisdnById(Long id, Long userId);

    boolean updateMsisdn(StockMsisdn msi);

    boolean removeListMsisdn(List<StockMsisdn> listMsisdn);

    ConfPackage getPackageById(Long id);

    List<ConfPackage> getListPackage();

    StockMsisdn getMsisdn(String msisdn, Long userId);
    
    int editFee(long id, long price);

    int insertBatchMsisdn(List<StockMsisdn> msiUploads);
}
