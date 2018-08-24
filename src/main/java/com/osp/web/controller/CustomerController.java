package com.osp.web.controller;

import com.osp.common.Constant;
import com.osp.common.RmiUtils;
import com.osp.common.Utils;
import com.osp.model.ConfPackage;
import com.osp.model.CustService;
import com.osp.model.Customer;
import com.osp.model.ForwardLog;
import com.osp.model.MOLog;
import com.osp.model.StockMsisdn;
import com.osp.model.TranspayLog;
import com.osp.web.service.confPackage.ConfPackageService;
import com.osp.web.service.customer.CustomerService;
import com.osp.web.service.customer.LogAccessService;
import com.osp.web.service.msisdn.StockMsisdnService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Admin on 12/14/2017.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    CustomerService customerService;
    @Autowired
    StockMsisdnService msisdnService;
    @Autowired
    LogAccessService logAccessService;
    @Autowired
    BCryptPasswordEncoder encoder;

    /////
    @Autowired
    StockMsisdnService stockMsisdnService;
    @Autowired
    ConfPackageService confPackageService;

    private RmiUtils rmiUtils = new RmiUtils();

    @GetMapping("/index")
    public String Index() {
        return "redirect:/customer/msisdn";
    }

    @RequestMapping(value = "/msisdn", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewMsisdnList(
            Model model,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            @RequestParam(value = "p", required = false, defaultValue = "1") int page,
            @RequestParam(value = "typeAction", required = false, defaultValue = "1") int typeAction, HttpServletResponse response,
            HttpServletRequest request) {
        try {
            Customer customer = new Customer();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
                customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String msisdn = null, tel = null, confirmExpired = null, confStatus = null;
                String telco = null;
                Date fromDate = null, toDate = null;
                Long userId = 0L;
                msisdn = request.getParameter("msisdn");

                if (!filter.equals("")) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode actualObj = mapper.readTree(filter);
                    JsonNode msisdnFilter = actualObj
                            .findValue("msisdn");
                    if (msisdnFilter != null
                            && !isNullOrEmpty(msisdnFilter.getTextValue())) {
                        msisdn = msisdnFilter.getTextValue();
                    }
                    JsonNode firstNumberFilter = actualObj.findValue("firstNumber");
                    if (firstNumberFilter != null
                            && !isNullOrEmpty(firstNumberFilter.getTextValue())) {
                        tel = firstNumberFilter.getTextValue();
                    }
                    JsonNode confirmExpiredFilter = actualObj.findValue("confirmExpired");
                    if (confirmExpiredFilter != null
                            && !isNullOrEmpty(confirmExpiredFilter.getTextValue())
                            && !confirmExpiredFilter.getTextValue().equals("-1")) {
                        confirmExpired = confirmExpiredFilter.getTextValue();
                    }

                    SimpleDateFormat fm = new SimpleDateFormat(
                            "dd/MM/yyyy HH:mm:ss");

                    JsonNode fromDateFilter = actualObj.findValue("fromDate");
                    if (fromDateFilter != null
                            && !isNullOrEmpty(fromDateFilter.getTextValue())) {
                        fromDate = fm.parse(fromDateFilter.getTextValue()
                                + " 00:00:00");
                    }

                    JsonNode toDateFilter = actualObj.findValue("toDate");
                    if (toDateFilter != null
                            && !isNullOrEmpty(toDateFilter.getTextValue())) {
                        toDate = fm.parse(toDateFilter.getTextValue()
                                + " 23:59:59");
                    }

                    JsonNode confStatusStr = actualObj.findValue("confstatus");
                    if (confStatusStr != null
                            && !isNullOrEmpty(confStatusStr.getTextValue())
                            && !confStatusStr.getTextValue().equals("-1")) {
                        confStatus = confStatusStr.getTextValue();
                    }
                    JsonNode telcoStr = actualObj.findValue("telco");
                    if (telcoStr != null && !isNullOrEmpty(telcoStr.getTextValue()) && !telcoStr.getTextValue().equals(";")) {
                        telco = telcoStr.getTextValue();
                    }
                }
                String[] listTelco = null;
                if (telco != null && telco.length() > 1 && telco.startsWith(";")) {
                    listTelco = telco.split(";");
                }

                int limit = getLimit();
                userId = customer.getId();

                if (typeAction == 2) {

                    int total = msisdnService.countTotalMsisdn(msisdn, tel, confirmExpired,
                            fromDate, toDate, userId, confStatus, listTelco);

                    List<StockMsisdn> list = msisdnService.getPagingMsisdn(page, total,
                            msisdn, tel, confirmExpired, fromDate, toDate,
                            userId, confStatus, listTelco);

                    String filePath = request.getSession().getServletContext().getRealPath("/") + "assets/filedemo/listMsisdn.xls";
                    HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
                    HSSFSheet sheet = wb.getSheetAt(0);

                    //set value content
                    DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
                    int rowContent = 2;
                    int index = 1;
                    CustService custService = msisdnService.getServiceRegister(customer.getId());
                    for (StockMsisdn item : list) {
                        Row row = sheet.createRow(rowContent++);
                        setCellValue(wb, row.createCell(0), Font.COLOR_NORMAL, String.valueOf(index++), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        setCellValue(wb, row.createCell(1), Font.COLOR_NORMAL, item.getMsisdn() == null ? "" : item.getMsisdn(), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        if (item.getTelco() == 1) {
                            setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Vietel", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        } else if (item.getTelco() == 2) {
                            setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Vinaphone", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        } else if (item.getTelco() == 3) {
                            setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Mobifone", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        } else if (item.getTelco() == 4) {
                            setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Gmobile", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        } else if (item.getTelco() == 5) {
                            setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Vietnamobile", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        } else {
                            setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Nhà mạng khác", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        }
                        setCellValue(wb, row.createCell(3), Font.COLOR_NORMAL, item.getPrice().toString() == null ? "Thương lượng" : item.getPrice().toString(), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        setCellValue(wb, row.createCell(4), Font.COLOR_NORMAL, item.getGenDate() == null ? "" : df.format(item.getGenDate()), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        setCellValue(wb, row.createCell(5), Font.COLOR_NORMAL, item.getStatus() == 1 ? "Hiển thị" : "Không hiển thị", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        if (item.getStatus() == 1 && custService != null) {
                            setCellValue(wb, row.createCell(6), Font.COLOR_NORMAL, df.format(custService.getExpiredDate()), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        } else {
                            setCellValue(wb, row.createCell(6), Font.COLOR_NORMAL, "", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        }
                        setCellValue(wb, row.createCell(7), Font.COLOR_NORMAL, item.getConfirmStatus() == 1 ? "Xác thực" : "Chưa xác thực", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        if (item.getConfirmExpired() == null) {
                            setCellValue(wb, row.createCell(8), Font.COLOR_NORMAL, "", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        } else {
                            setCellValue(wb, row.createCell(8), Font.COLOR_NORMAL, df.format(item.getConfirmExpired()), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                        }
                        setCellValue(wb, row.createCell(9), Font.COLOR_NORMAL, item.getDescription() == null ? "" : item.getDescription(), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
                    }
                    //set autosize content
                    for (int i = 0; i < 9; i++) {
                        sheet.autoSizeColumn(i, true);
                    }

                    // write it as an excel attachment
                    ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
                    wb.write(outByteStream);
                    byte[] outArray = outByteStream.toByteArray();
                    response.setContentType("application/ms-excel");
                    response.setContentLength(outArray.length);
                    response.setHeader("Expires:", "0"); // eliminates browser caching
                    response.setHeader("Content-Disposition", "attachment; filename=Danh_sach_so.xls");
                    OutputStream outStream = response.getOutputStream();
                    outStream.write(outArray);
                    outStream.flush();
                    return null;

                } else {

                    int total = msisdnService.countTotalMsisdn(msisdn, tel, confirmExpired,
                            fromDate, toDate, userId, confStatus, listTelco);
                    if (total > 0) {
                        List<StockMsisdn> list = msisdnService.getPagingMsisdn(page, limit,
                                msisdn, tel, confirmExpired, fromDate, toDate,
                                userId, confStatus, listTelco);
                        model.addAttribute("listresult", list);
                        CustService custService = msisdnService.getServiceRegister(customer.getId());
                        model.addAttribute("custService", custService);
                        int totalPage = total / limit;
                        if (total % limit > 0) {
                            totalPage++;
                        }
                        setPagingProperty(model, page, total, totalPage, limit);
                    } else {
                        setPagingProperty(model, page, 0, 1, limit);
                    }
                }

                model.addAttribute("telco", telco);

            } else {
                return "403";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("deleteURL", "removeMsisdn");
        return "viewMsisdn";
    }

    @RequestMapping(value = "/uploadMsisdn", method = RequestMethod.GET)
    public String viewUpload(Model model, HttpServletRequest request) {
        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "403";
        }
        model.addAttribute("uploadtype",
                request.getParameter("type") == null ? "single" : request
                .getParameter("type").toString());
        return "uploadMsisdn";
    }

    @RequestMapping(value = "/forwardMsisdn", method = RequestMethod.GET)
    public String forwardMsisdn(Model model, HttpServletRequest request) {
        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "403";
        }
        String id = request.getParameter("id");

        if (id == null || id.length() == 0) {
            return "404";
        }

        try {
            new Long(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }

        StockMsisdn msi = new StockMsisdn();
        customer = customerService.getUserInfo(auth.getName(), "username");
        if (customer == null) {
            return "403";
        }
        msi = msisdnService.getMsisdnById(new Long(id), customer.getId());
        if (msi == null) {
            return "404";
        }
        model.addAttribute("msi", msi);

        CustService custService = msisdnService.getServiceRegister(customer.getId());
        model.addAttribute("custService", custService);
        return "forwardMsisdn";
    }

    ////////////////////////////////////////
    //    Hien  thi thong tin goi cuoc   //
    //////////////////////////////////////
    @RequestMapping(value = "/viewInfoPackage", method = RequestMethod.GET)
    public ModelAndView viewInfoPackage() {
        List<ConfPackage> listConfPackage = confPackageService.getListConfPackage();
        return new ModelAndView("viewInfoPackage", "list", listConfPackage);
    }

    @RequestMapping(value = "/forwardMsisdn", method = RequestMethod.POST)
    public String forwardMsisdnP(Model model, HttpServletRequest request, RedirectAttributes modelRe) {
        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "403";
        }
        String id = request.getParameter("id");
        String price = request.getParameter("price");
        String description = request.getParameter("description");
        String username = request.getParameter("userName");

        if (id == null || id.length() == 0 || price == null || price.length() == 0) {
            return "404";
        }
        price = price.replaceAll(",", "");
        try {
            new Long(id);
            new Long(price);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }

        StockMsisdn msi = new StockMsisdn();
        customer = customerService.getUserInfo(auth.getName(), "username");
        msi = msisdnService.getMsisdnById(new Long(id), customer.getId());

        if (msi == null) {
            return "404";
        }

        msi.setPrice(new Long(price));
        msi.setDescription(description);
        model.addAttribute("msi", msi);
        Customer customerNew = customerService.getUserInfo(username, "username");

        if (customerNew == null) {
            model.addAttribute("msg", "Người bán không tồn tại. Vui lòng kiểm tra lại!");
            return "forwardMsisdn";
        }

        StockMsisdn msiNew = msisdnService.getMsisdn(msi.getMsisdn(), customerNew.getId());

        if (msiNew != null) {
            model.addAttribute("msg", "Số thuê bao trên đã có trong kho của người bán. Không thể chuyển số!");
            return "forwardMsisdn";
        }

        CustService custService = msisdnService.getServiceRegister(customerNew.getId());
        int totalMsisdn = msisdnService.countTotalMsisdnUpload(customerNew.getId());
        ConfPackage confPackage = new ConfPackage();
        confPackage = msisdnService.getPackageById(1L); // default package : id = 1
        Long maxQual = (custService != null) ? custService.getConfPackage().getMaxQuantity() : (confPackage != null) ? confPackage.getMaxQuantity() : 100L;

        if ((totalMsisdn >= maxQual) || (totalMsisdn + 1 > maxQual)) {
            model.addAttribute("msg",
                    "Người bán " + customerNew.getUsername() + " hiện đã sửa dụng hết số lượng số được phép đăng. Không thể chuyển số! ");
            return "forwardMsisdn";
        }

        Customer custNew = new Customer();
        custNew.setId(customerNew.getId());
        msi.setCustomer(customerNew);

        if (msisdnService.updateMsisdn(msi)) {
            modelRe.addFlashAttribute("msg",
                    "Chuyển số thành công !");
            ForwardLog forwardLog = new ForwardLog();

            forwardLog.setMsisdn(msi.getMsisdn());
            forwardLog.setCustomerReceiver(customerNew);
            forwardLog.setCustomerSender(customer);
            customerService.insertForwardLog(forwardLog);

        } else {
            modelRe.addFlashAttribute("msg",
                    "Chuyển số thất bại. Liên hệ với quản trị hệ thống để được trợ giúp !");
        }
        String ip = Utils.getIpClient(request);
        logAccessService.addLogWithUserId(customer.getId(), "Cập nhật thông tin thuê bao + Chuyển số", msi.getMsisdn(), ip);

        return "redirect:/customer/msisdn";
    }

    @RequestMapping(value = "/editMsisdn", method = RequestMethod.GET)
    public String editMsisdn(Model model, HttpServletRequest request) {
        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "403";
        }
        String id = request.getParameter("id");

        if (id == null || id.length() == 0) {
            return "404";
        }

        try {
            new Long(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }

        StockMsisdn msi = new StockMsisdn();
        customer = customerService.getUserInfo(auth.getName(), "username");
        if (customer == null) {
            return "403";
        }
        msi = msisdnService.getMsisdnById(new Long(id), customer.getId());
        if (msi == null) {
            return "404";
        }
        model.addAttribute("msi", msi);

        CustService custService = msisdnService.getServiceRegister(customer.getId());
        model.addAttribute("custService", custService);
        return "editMsisdn";
    }

    @RequestMapping(value = "/editMsisdn", method = RequestMethod.POST)
    public String editMsisdnP(Model model, HttpServletRequest request, RedirectAttributes modelRe) {
        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "403";
        }
        String id = request.getParameter("id");
        String price = request.getParameter("price");
        String description = request.getParameter("description");
        String msiAlias = request.getParameter("msisdnAlias");

        if (id == null || id.length() == 0 || price == null || price.length() == 0 || msiAlias == null || msiAlias.length() == 0) {
            return "404";
        }

        price = price.replaceAll(",", "");
        try {
            new Long(id);
            new Long(price);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }

        StockMsisdn msi = new StockMsisdn();
        customer = customerService.getUserInfo(auth.getName(), "username");
        msi = msisdnService.getMsisdnById(new Long(id), customer.getId());

        if (msi == null) {
            return "404";
        }

        if (Constant.validateMsisdn1(msiAlias) && Constant.validateMsisdn(Constant.convertMsisdn(msiAlias))) {
            if (msi.getMsisdn().equals(Constant.convertMsisdn(msiAlias))) {
                msi.setMsisdnAlias(Constant.convertMsisdnAlias(msiAlias));

            } else {
                model.addAttribute("msgError", "Số hiển thị trên không khớp với số thuê bao!");
                return "editMsisdn";
            }

        } else {
            model.addAttribute("msgError", "Số hiển thị không đúng định dạng. Vui lòng kiểm tra lại");
            return "editMsisdn";
        }

        msi.setPrice(new Long(price));
        msi.setDescription(description);

        if (msisdnService.updateMsisdn(msi)) {
            modelRe.addFlashAttribute("msg",
                    "Cập nhật thành công !");

        } else {
            modelRe.addFlashAttribute("msg",
                    "Cập nhật thất bại. Liên hệ với quản trị hệ thống để được trợ giúp !");
        }
        String ip = Utils.getIpClient(request);
        logAccessService.addLogWithUserId(customer.getId(), "Cập nhật thông tin thuê bao", msi.getMsisdn(), ip);

        return "redirect:/customer/msisdn";
    }

    @RequestMapping(value = "/activeMsisdn", method = RequestMethod.GET)
    public String activeMsisdn(Model model, HttpServletRequest request, RedirectAttributes modelRe) {
        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "403";
        }
        String id = request.getParameter("id");

        if (id == null || id.length() == 0) {
            return "404";
        }
        try {
            new Long(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }

        StockMsisdn msi = new StockMsisdn();
        customer = customerService.getUserInfo(auth.getName(), "username");
        msi = msisdnService.getMsisdnById(new Long(id), customer.getId());

        if (msi == null) {
            return "404";
        }

        msi.setStatus(new Short("1"));

        if (msisdnService.updateMsisdn(msi)) {
            modelRe.addFlashAttribute("msg",
                    "Khôi phục đăng số thành công !");

        } else {
            modelRe.addFlashAttribute("msg",
                    "Khôi phục đăng số thất bại. Liên hệ với quản trị hệ thống để được trợ giúp !");
        }
        String ip = Utils.getIpClient(request);
        logAccessService.addLogWithUserId(customer.getId(), "Khôi phục đăng số", msi.getMsisdn(), ip);

        return "redirect:/customer/msisdn";
    }

    @RequestMapping(value = "/detailMsisdn", method = RequestMethod.GET)
    public String detailMsisdn(Model model, HttpServletRequest request) {
        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "403";
        }
        String id = request.getParameter("id");

        if (id == null || id.length() == 0) {
            return "404";
        }

        try {
            new Long(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        StockMsisdn msi = new StockMsisdn();
        customer = customerService.getUserInfo(auth.getName(), "username");
        if (customer == null) {
            return "403";
        }
        msi = msisdnService.getMsisdnById(new Long(id), customer.getId());
        if (msi == null) {
            return "404";
        }
        model.addAttribute("msi", msi);

        if (customer == null) {
            return "403";
        }
        CustService custService = msisdnService.getServiceRegister(customer.getId());
        model.addAttribute("custService", custService);

        List<MOLog> list = customerService.getListConfirm(msi.getMsisdn(), customer.getId());
        model.addAttribute("listConfirm", list);

        return "detailMsisdn";
    }

    @RequestMapping(value = "/uploadMsisdn", method = RequestMethod.POST)
    public String upload(Model model, RedirectAttributes reModel,
            MultipartFile uploadfile, HttpServletRequest request) {
        try {
            Customer customer = new Customer();
            DataFormatter formatter = new DataFormatter();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
                return "403";
            }
            boolean uploadType = request.getParameter("uploadtype") != null && request.getParameter("uploadtype").toString().equals("multi");
            model.addAttribute("uploadtype",
                    request.getParameter("uploadtype") == null ? "single"
                    : request.getParameter("uploadtype").toString());
            customer = customerService.getUserInfo(auth.getName(), "username");
            if (customer == null) {
                return "403";
            }
            List<StockMsisdn> msiList = new ArrayList<StockMsisdn>();
            List<String> msiListInFile = new ArrayList<String>();
            List<String> listError = new ArrayList<String>();
            int countError = 0;
            int countSuccess = 0;
            int total = 0;
            String error = "";

            CustService custService = msisdnService.getServiceRegister(customer.getId());
            int totalMsisdn = msisdnService.countTotalMsisdnUpload(customer.getId());
            ConfPackage confPackage = new ConfPackage();
            confPackage = msisdnService.getPackageById(1L); // default package : id = 1
            Long maxQual = (custService != null) ? custService.getConfPackage().getMaxQuantity() : (confPackage != null) ? confPackage.getMaxQuantity() : 100L;
            if (uploadType) {

                if (uploadfile.getSize() == 0) {
//                    model.addAttribute("msgError",
//                            "Phải chọn file trước khi đăng số!");
                    error = "errorFile";
                    model.addAttribute("error", error);
                    model.addAttribute("total", total);
                    model.addAttribute("countSuccess", countSuccess);
                    model.addAttribute("countError", countError);
                    model.addAttribute("listError", listError);
                    return "uploadMsisdn";
                }
                // upload danh sÃ¡ch
                String msg = "";
                StockMsisdn msiUpload = new StockMsisdn();

                // gen random code
//                String payCode = "";
//                boolean exist = true;
//                do {
//                    payCode = Utils.randomCode(6);
//                    exist = srv.checkExistCode(payCode);
//                } while (exist);
                // thoi gian hieu luc giu so
                Date createTime = new Date();

                if (uploadfile.getOriginalFilename().endsWith(".xls")) {
                    InputStream stream;
                    try {
                        stream = uploadfile.getInputStream();
                        // Create Workbook instance holding reference to .xls
                        // file
                        HSSFWorkbook workbook = new HSSFWorkbook(stream);
                        // Get first/desired sheet from the workbook
                        Sheet sheet = workbook.getSheetAt(0);
                        // Iterate through each rows one by one
                        Iterator<Row> rowIterator = sheet.iterator();

                        // Update by list
                        rowIterator = sheet.iterator();
                        while (rowIterator.hasNext()) {

                            Row row = rowIterator.next();
                            if (row.getRowNum() > 0) {
                                total++;
                                StockMsisdn msi = new StockMsisdn();
                                // Check msisdn
                                Cell cellMsisdn = row.getCell(0);
                                if (cellMsisdn != null
                                        && !isNullOrEmpty(cellMsisdn.toString())) {
                                    String msiAlias = "";
                                    if (cellMsisdn.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                        long i = (long) cellMsisdn.getNumericCellValue();
                                        msiAlias = Constant.convertMsisdnAlias(String.valueOf(i));
                                    } else {
                                        msiAlias = Constant.convertMsisdnAlias(cellMsisdn.toString());
                                    }

                                    String msisdn = Constant.convertMsisdn(msiAlias);
                                    if (Constant.validateMsisdn1(msiAlias) && Constant.validateMsisdn(msisdn)) {

                                        if (!msiListInFile.contains(msisdn)) {

                                            msiListInFile.add(msisdn);
                                            msi.setMsisdnAlias(msiAlias);
                                            msi.setMsisdn(msisdn);
                                            msi.setTelco(checkTelNumber(msisdn));

                                            if (msisdnService.checkExitsMsisdn(msi.getMsisdn(), customer.getId())) {
                                                countError++;
                                                listError
                                                        .add("Dòng "
                                                                + (row.getRowNum() + 1)
                                                                + ": Số thuê bao "
                                                                + msi.getMsisdn()
                                                                + " Đã tồn tại trong danh sách số !");
                                                continue;
                                            }
//                                        DwMsisdn dwMsisdn = rmiUtils.getDwMsisdnInfo(msi.getMsisdn());
//                                        if (dwMsisdn != null && dwMsisdn.getStatus() != new Short("4")) {
//                                            countError++;
//                                            listError
//                                                    .add("Dòng "
//                                                            + (row.getRowNum() + 1)
//                                                            + ": Số thuê bao "
//                                                            + msi.getMsisdn()
//                                                            + " Đang thuộc số chưa bán của kho chọn số !");
//                                            continue;
//                                        }
                                        } else {
                                            continue;
                                        }

                                    } else {
                                        countError++;
                                        listError.add("Dòng "
                                                + (row.getRowNum() + 1)
                                                + ": Số thuê bao "
                                                + msi.getMsisdn()
                                                + " không hợp lệ !");
                                        continue;
                                    }

                                } else {
                                    countError++;
                                    listError.add("Dòng " + (row.getRowNum() + 1)
                                            + ": Không tìm thấy số thuê bao !");
                                    continue;
                                }
                                // check price
                                Cell cellPrice = row.getCell(1);
                                if (cellPrice != null
                                        && !isNullOrEmpty(cellPrice.toString())) {
                                    try {
                                        cellPrice.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                        msi.setPrice(new Long(formatter.formatCellValue(cellPrice)));
                                    } catch (Exception e) {
                                        countError++;
                                        listError.add("Dòng " + (row.getRowNum() + 1)
                                                + ": Giá bán không hợp lệ !");
                                        continue;
                                    }
                                } else {
                                    countError++;
                                    listError.add("Dòng " + (row.getRowNum() + 1)
                                            + ": Giá bán không hợp lệ !");
                                    continue;
                                }

                                // check description
                                Cell cellDes = row.getCell(2);
                                if (cellDes != null && !isNullOrEmpty(cellDes.toString())) {
                                    cellDes.setCellType(XSSFCell.CELL_TYPE_STRING);
                                    msi.setDescription(cellDes.toString());
                                }
                                msi.setConfirmStatus(new Short("0"));
                                msi.setCustomer(customer);
                                msi.setStatus(new Short("1"));
                                msi.setApprove(new Short("1"));
                                msi.setGenDate(new Date());
                                msi.setGroupId(phanLoaiSim(msi.getMsisdn()));
                                msiList.add(msi);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        model.addAttribute("error", "systemBusy");
                        model.addAttribute("total", 0);
                        model.addAttribute("countSuccess", 0);
                        model.addAttribute("countError", countError);
                        model.addAttribute("listError", listError);
                    }
//                }else if (uploadfile.getOriginalFilename().endsWith(".txt")) {
////                    File convFile = new File(uploadfile.getOriginalFilename());
////                    uploadfile.transferTo(convFile);
//                    InputStream fis = null;
//
//                    fis = uploadfile.getInputStream();
//                    // Get the object of DataInputStream
//                    DataInputStream in = new DataInputStream(fis);
//                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
////                    BufferedReader br = new BufferedReader(new FileReader(
////                            convFile));
//                    int lineCount = 0;
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        lineCount++;
//                        String[] split = line.split("-");
//                        if (split.length > 1) {
//                            String msisdn = split[0];
//                            if (!msiListInFile.contains(split[0])) {
//                                StockMsisdn msi = new StockMsisdn();
//                                msi.setMsisdn(msisdn);
//                                try {
//                                    BigDecimal paste = new BigDecimal(
//                                            msi.getMsisdn());
//                                } catch (Exception e) {
//                                    countError++;
//                                    listError.add("Dòng " + lineCount
//                                            + ": Số thuê bao "
//                                            + msi.getMsisdn()
//                                            + " không hợp lệ !");
//                                    continue;
//                                }
//                                if (!Constant.validateMsisdn(msi.getMsisdn())) {
//                                    countError++;
//                                    listError.add("Dòng " + lineCount
//                                            + ": Số thuê bao "
//                                            + msi.getMsisdn()
//                                            + " không hợp lệ !");
//                                    continue;
//                                }
//
//                                if (msi.getMsisdn().startsWith("84")) {
//                                    msi.setMsisdn("0"
//                                            + msi.getMsisdn().substring(2));
//                                }
//
//                                if (!checkTelNumber(msi.getMsisdn())) {
//                                    listError.add("Dòng " + lineCount
//                                            + ": Đầu số thuê bao "
//                                            + msi.getMsisdn()
//                                            + " không hợp lệ !");
//                                    continue;
//
//                                }
//
//                                if (msisdnService.checkExitsMsisdn(msi.getMsisdn(), customer.getId())) {
//                                    countError++;
//                                    listError
//                                            .add("Dòng "
//                                                    + lineCount
//                                                    + ": Số thuê bao "
//                                                    + msi.getMsisdn()
//                                                    + " Đã tồn tại trong danh sách số !");
//                                    continue;
//                                }
//                                try {
//                                    msi.setPrice(new Long(split[1]));
//                                } catch (Exception e) {
//                                    countError++;
//                                    listError.add("Dòng " + lineCount
//                                            + ": Giá bán không hợp lệ !");
//                                    continue;
//                                }
//                                try {
//                                    msi.setDescription(split[2]);
//                                } catch (Exception e) {
//                                    countError++;
//                                    listError.add("Dòng " + lineCount
//                                            + ": Dữ liệu không đúng cấu trúc !");
//                                    continue;
//                                }
//
//                                msi.setConfirmStatus(new Short("0"));
//                                msi.setCustomer(customer);
//                                msi.setStatus(new Short("1"));
//                                msi.setApprove(new Short("1"));
//                                msi.setGenDate(new Date());
//
//                                msiList.add(msi);
//                            } else {
////                                countError++;
////                                    listError.add("Dòng " + lineCount
////                                            + ": Số thuê bao "
////                                            + split[0]
////                                            + " đã có trong file !");
//                                continue;
//                            }
//                        } else {
//                            countError++;
//                            listError.add("Dòng " + lineCount
//                                    + ": Dữ liệu không đúng cấu trúc !");
//                            continue;
//                        }
//                    }
//                    total = lineCount;
                } else {
                    model.addAttribute("error", "errorFormat");
                    model.addAttribute("total", 0);
                    model.addAttribute("countSuccess", 0);
                    model.addAttribute("countError", countError);
                    model.addAttribute("listError", listError);
                    return "uploadMsisdn";

                }
            } else {

                // upload 1 so
//                if (request.getParameter("msisdn") == null
//                        || request.getParameter("msisdn").toString().isEmpty()) {
//                    model.addAttribute("msgError",
//                            "Số thuê bao không được để trống !");
//                    return "uploadMsisdn";
//                }
//
//                if (request.getParameter("price") == null
//                        || request.getParameter("price").toString().isEmpty()) {
//                    model.addAttribute("msgError",
//                            "Giá bán không được để trống !");
//                    return "uploadMsisdn";
//                }
//
//                String msiAlias = Constant.convertMsisdnAlias(request.getParameter("msisdn"));
//                String msisdn = Constant.convertMsisdn(msiAlias);
//                if (!Constant.validateMsisdn1(msiAlias) || !Constant.validateMsisdn(msisdn)) {
//                    model.addAttribute("msgError",
//                            "Số thuê bao không hợp lệ !");
//                    return "uploadMsisdn";
//
//                }
//
//                Long price = 0L;
//                try {
//                    price = new Long(request.getParameter("price").toString().replace(",", ""));
//                } catch (Exception e) {
//                    model.addAttribute("msgError",
//                            "Giá bán không hợp lệ !");
//                    return "uploadMsisdn";
//                }
//
//                String description = request.getParameter("description");
//                if (msisdnService.checkExitsMsisdn(msisdn, customer.getId())) {
//                    model.addAttribute("msgError",
//                            "Số thuê bao đã tồn tại trong danh sách số !");
//                    return "uploadMsisdn";
//                }
//
//                if (custService == null && totalMsisdn >= maxQual) {
//                    model.addAttribute("msgError1",
//                            "Bạn đã sử dụng " + maxQual + "/" + maxQual + " số thuê bao được phép đăng.");
//                    model.addAttribute("listPackage", msisdnService.getListPackage());
//                    return "uploadMsisdn";
//                } else if (custService != null && totalMsisdn >= maxQual) {
//                    model.addAttribute("msgError1",
//                            "Bạn đã sử dụng " + maxQual + "/" + maxQual + " số thuê bao được phép đăng.");
//                    model.addAttribute("listPackage", msisdnService.getListPackage());
//                    return "uploadMsisdn";
//                } else {
////                    DwMsisdn dwMsisdn = rmiUtils.getDwMsisdnInfo(msi);
////                    if (dwMsisdn != null && !Objects.equals(dwMsisdn.getStatus(), new Short("4"))) {
////                        model.addAttribute("msgError",
////                                "Đăng số thất bại. Số thuê bao này nằm trong kho chọn số !");
////                        return "uploadMsisdn";
////                    }
//
//                    // them moi thue bao
//                    StockMsisdn msiUpload = new StockMsisdn();
//                    msiUpload.setMsisdn(msisdn);
//                    msiUpload.setMsisdnAlias(msiAlias);
//                    msiUpload.setConfirmStatus(new Short("0"));
//                    msiUpload.setCustomer(customer);
//                    msiUpload.setStatus(new Short("1"));
//                    msiUpload.setPrice(price);
//                    msiUpload.setApprove(new Short("1"));
//                    msiUpload.setDescription(description);
//                    msiUpload.setGenDate(new Date());
//                    msiUpload.setGroupId(phanLoaiSim(msisdn));
//                    msiUpload.setTelco(checkTelNumber(msisdn));
//                    if (msisdnService.insertMsisdn(msiUpload)) {
//                        reModel.addFlashAttribute("isUpload", true);
//                        model.addAttribute("msisdn", msisdn);
//                        model.addAttribute("countSuccess1", 1);
//                        model.addAttribute("msg", null);
//
//                        String ip = Utils.getIpClient(request);
//                        logAccessService.addLogWithUserId(customer.getId(), "Upload số đơn lẻ", msisdn, ip);
//                        return "uploadMsisdn";
//                    } else {
//                        model.addAttribute("msgError",
//                                "Đăng số thất bại. Liên hệ quản trị hệ thống để được hỗ trợ !");
//                        return "uploadMsisdn";
//                    }     
                String msisdnUpload = request.getParameter("msisdnUpload");
                String typeShow1 = request.getParameter("typeShow1");
                String typeShow2 = request.getParameter("typeShow2");
                String typeShow3 = request.getParameter("typeShow3");

                if (msisdnUpload == null || msisdnUpload.trim().length() == 0) {
                    model.addAttribute("msgError", "Danh sách số thuê bao không được để trống !");
                    return "uploadMsisdn";

                }
                String[] listMsisdnUpload = msisdnUpload.split("\\r?\\n");
                total = (listMsisdnUpload != null && listMsisdnUpload.length >0) ?  listMsisdnUpload.length: 0;
                for (String msiUpload : listMsisdnUpload) {
                    String[] msiItems = msiUpload.split("\\t?\\s");
                    if (msiItems != null && msiItems.length == 2) {
                        String msiAlias = Constant.convertMsisdnAlias(msiItems[0]);
                        String msisdn = Constant.convertMsisdn(msiAlias);
                        if (!Constant.validateMsisdn1(msiAlias) || !Constant.validateMsisdn(msisdn)) {
                            countError++;
                            listError.add("Số thuê bao " + msiItems[0] + " không hợp lệ!");
                            continue;
                        }
                        Long price = 0L;
                        try {
                            price = new Long(msiItems[1].replace(",", "").replace(".", ""));
                        } catch (Exception e) { 
                            countError++;
                            listError.add("Số thuê bao " + msiItems[0] + " có giá bán không hợp lệ!");
                            continue;
                        }
                        if (typeShow2 != null && typeShow2.equals("typeShow2")) {
                            price = price * 1000;
                        } else if (typeShow3 != null && typeShow3.equals("typeShow3")) {
                            price = price * 1000000;
                        }
                        
                        if (msisdnService.checkExitsMsisdn(msisdn, customer.getId())) {
                                                countError++;
                                                listError
                                                        .add(": Số thuê bao "
                                                                + msisdn
                                                                + " Đã tồn tại trong danh sách số !");
                                                continue;
                        }
                        
                        

                        StockMsisdn stockMsisdn = new StockMsisdn();
                        stockMsisdn.setMsisdn(msisdn);
                        stockMsisdn.setMsisdnAlias(msiAlias);

                        stockMsisdn.setPrice(price);

                        stockMsisdn.setConfirmStatus(new Short("0"));
                        stockMsisdn.setCustomer(customer);
                        stockMsisdn.setStatus(new Short("1"));
                        stockMsisdn.setApprove(new Short("1"));
                        stockMsisdn.setGenDate(new Date());
                        stockMsisdn.setGroupId(phanLoaiSim(msisdn));
                        stockMsisdn.setTelco(checkTelNumber(msisdn));
                        msiList.add(stockMsisdn); 
                    }else{
                        countError++;
                        listError.add("Thông tin số thuê bao " + msiUpload + " không hợp lệ!");
                        continue;
                    }

                }

            }

            if (msiList.size() > 0) {
                if (totalMsisdn >= maxQual) {
                    model.addAttribute("error", "maxQual");
                    model.addAttribute("msgErrorStr",
                            "Bạn đã sử dụng " + maxQual + "/" + maxQual + " số thuê bao được phép đăng. ");
                    model.addAttribute("total", 0);
                    model.addAttribute("countSuccess", 0);
                    model.addAttribute("countError", countError);
                    model.addAttribute("listError", listError);
                    model.addAttribute("listPackage", msisdnService.getListPackage());
                    return "uploadMsisdn";
                } else if ((totalMsisdn + msiList.size()) > maxQual) {
                    model.addAttribute("error", "maxQual");
                    model.addAttribute("msgErrorStr",
                            "Bạn còn được đăng " + (maxQual - totalMsisdn) + " số. Số lượng thuê bao trong danh sách đã vượt quá lượng số có thể đăng.");// + msiList.size() + " số. ");
                    model.addAttribute("total", 0);
                    model.addAttribute("countSuccess", 0);
                    model.addAttribute("countError", countError);
                    model.addAttribute("listError", listError);
                    model.addAttribute("listPackage", msisdnService.getListPackage());
                    return "uploadMsisdn";
                }

//                for (StockMsisdn stockMsisdn : msiList) {
//                    if (msisdnService.insertMsisdn(stockMsisdn)) {
//                        countSuccess++;
//                    } else {
//                        countError++;
//                        listError.add(stockMsisdn.getMsisdn() + " - Update thất bại!");
//                    }
//
//                }
                    countSuccess = msisdnService.insertBatchMsisdn(msiList);

            } else {
                model.addAttribute("error", "systemBusy");
                model.addAttribute("msgErrorStr",
                        "Đăng số thất bại. Vui lòng kiểm tra lại danh sách hoặc liên hệ với quản trị hệ thống !");
                model.addAttribute("total", 0);
                model.addAttribute("countSuccess", 0);
                model.addAttribute("countError", countError);
                model.addAttribute("listError", listError);
                return "uploadMsisdn";
            }

            reModel.addFlashAttribute("isUpload", true);
            model.addAttribute("error", error);
            model.addAttribute("total", total);
            model.addAttribute("countSuccess", countSuccess);

            if (countSuccess > 0) {
                if (custService == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.getInstance().get(Calendar.YEAR) + 1, 0, 1, 0, 0, 0);
                    if (confPackage != null) {
                        CustService custService1 = new CustService();
                        custService1.setExpiredDate(calendar.getTime());
                        custService1.setConfPackage(confPackage);
                        custService = custService1;
                        model.addAttribute("custService", custService);
                        model.addAttribute("totalUpload", custService.getConfPackage().getMaxQuantity() - (countSuccess + totalMsisdn));
                    } else {
                        model.addAttribute("custService", null);
                    }

                } else {
                    model.addAttribute("custService", custService);
                    model.addAttribute("totalUpload", custService.getConfPackage().getMaxQuantity() - (countSuccess + totalMsisdn));
                }
                String ip = Utils.getIpClient(request);
                logAccessService.addLogWithUserId(customer.getId(), "Upload danh sách số", "", ip);
            }

            model.addAttribute("countError", countError);
            model.addAttribute("listError", listError);
            return "uploadMsisdn";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError",
                    "Đăng số thất bại. Liên hệ quản trị hệ thống để được hỗ trợ !");
            return "uploadMsisdn";
        }
    }

    @RequestMapping(value = "/removeMsisdn", method = RequestMethod.POST)
    public String removesingle(Model model, HttpServletRequest request,
            RedirectAttributes rModel) {
        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "403";
        }
        String result = "Hủy đăng số thất bại !";
        String ids = request.getParameter("ids");
        String pageRemoveMsisdn = !Objects.equals(request.getParameter("pageRemoveMsisdn"), null) ? request.getParameter("pageRemoveMsisdn") : "1";
        String filter = request.getParameter("filterRemove");

        int page = 1;

        try {
            page = Integer.parseInt(pageRemoveMsisdn);
        } catch (Exception e) {
        }

        if (ids == null) {
            return "404";
        }
        customer = customerService.getUserInfo(auth.getName(), "username");
        List<StockMsisdn> listMsisdn = new ArrayList<>();
        if (!ids.contains(";") && !ids.equalsIgnoreCase("all")) {
            try {
                new Long(ids);
            } catch (Exception e) {
            }
            StockMsisdn msi = msisdnService.getMsisdnById(new Long(ids), customer.getId());
            if (msi == null) {
                return "404";
            }

            try {
//                msi.setStatus(new Short("0"));
//                msi.setLastUpdate(new Date());
                listMsisdn.add(msi);
                if (msisdnService.removeListMsisdn(listMsisdn)) {
                    result = "Hủy đăng số thành công !";
                    String ip = Utils.getIpClient(request);
                    logAccessService.addLogWithUserId(customer.getId(), "Hủy đăng số - Xóa số khỏi kho số", msi.getMsisdn(), ip);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (!ids.contains(";") && ids.equalsIgnoreCase("all")) {
            String msisdn = null, tel = null, confirmExpired = null, confStatus = null;
            String telco = null;
            Date fromDate = null, toDate = null;
            Long userId = 0L;
            msisdn = request.getParameter("msisdn");
            try {
                if (!filter.equals("")) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode actualObj = mapper.readTree(filter);
                    JsonNode msisdnFilter = actualObj
                            .findValue("msisdn");
                    if (msisdnFilter != null
                            && !isNullOrEmpty(msisdnFilter.getTextValue())) {
                        msisdn = msisdnFilter.getTextValue();
                    }
                    JsonNode firstNumberFilter = actualObj.findValue("firstNumber");
                    if (firstNumberFilter != null
                            && !isNullOrEmpty(firstNumberFilter.getTextValue())) {
                        tel = firstNumberFilter.getTextValue();
                    }
                    JsonNode confirmExpiredFilter = actualObj.findValue("confirmExpired");
                    if (confirmExpiredFilter != null
                            && !isNullOrEmpty(confirmExpiredFilter.getTextValue())
                            && !confirmExpiredFilter.getTextValue().equals("-1")) {
                        confirmExpired = confirmExpiredFilter.getTextValue();
                    }

                    SimpleDateFormat fm = new SimpleDateFormat(
                            "dd/MM/yyyy HH:mm:ss");

                    JsonNode fromDateFilter = actualObj.findValue("fromDate");
                    if (fromDateFilter != null
                            && !isNullOrEmpty(fromDateFilter.getTextValue())) {
                        fromDate = fm.parse(fromDateFilter.getTextValue()
                                + " 00:00:00");
                    }

                    JsonNode toDateFilter = actualObj.findValue("toDate");
                    if (toDateFilter != null
                            && !isNullOrEmpty(toDateFilter.getTextValue())) {
                        toDate = fm.parse(toDateFilter.getTextValue()
                                + " 23:59:59");
                    }

                    JsonNode confStatusStr = actualObj.findValue("confstatus");
                    if (confStatusStr != null
                            && !isNullOrEmpty(confStatusStr.getTextValue())
                            && !confStatusStr.getTextValue().equals("-1")) {
                        confStatus = confStatusStr.getTextValue();
                    }
                    JsonNode telcoStr = actualObj.findValue("telco");
                    if (telcoStr != null && !isNullOrEmpty(telcoStr.getTextValue()) && !telcoStr.getTextValue().equals(";")) {
                        telco = telcoStr.getTextValue();
                    }
                }

                String[] listTelco = null;
                if (telco != null && telco.length() > 1 && telco.startsWith(";")) {
                    listTelco = telco.split(";");
                }

                int limit = getLimit();
                userId = customer.getId();
                List<StockMsisdn> list = msisdnService.getPagingMsisdn(page, limit,
                        msisdn, tel, confirmExpired, fromDate, toDate,
                        userId, confStatus, listTelco);
                model.addAttribute("telco", telco);
                if (list != null && list.size() > 0) {
                    if (msisdnService.removeListMsisdn(list)) {
                        result = "Hủy đăng số thành công !";
                        for (StockMsisdn msi : list) {
                            String ip = Utils.getIpClient(request);
                            logAccessService.addLogWithUserId(customer.getId(), "Hủy đăng số - Xóa số khỏi kho số", msi.getMsisdn(), ip);
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (!ids.equals(";")) {
            try {
                String[] listId = ids.substring(1).split(";");

                for (String id : listId) {
                    StockMsisdn msi = msisdnService.getMsisdnById(new Long(id), customer.getId());
                    if (msi == null) {
                        listMsisdn = null;
                        break;
                    }
//                    msi.setStatus(new Short("0"));
//                    msi.setLastUpdate(new Date());
                    listMsisdn.add(msi);
                }
                if (listMsisdn != null && listMsisdn.size() > 0) {
                    if (msisdnService.removeListMsisdn(listMsisdn)) {
                        result = "Hủy đăng số thành công !";
                        for (StockMsisdn msi : listMsisdn) {
                            String ip = Utils.getIpClient(request);
                            logAccessService.addLogWithUserId(customer.getId(), "Hủy đăng số - Xóa số khỏi kho số", msi.getMsisdn(), ip);
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rModel.addFlashAttribute("msg", result);
        return "redirect:/customer/msisdn";
    }

    @RequestMapping(value = "/downloadtemplate", method = RequestMethod.GET)
    public String downloadtemplatemsisdnupload(HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            Customer customer = new Customer();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
                return "403";
            }

            if (request.getParameter("filetype") == null) {
                response.sendRedirect(request.getContextPath() + "/404.html");
            }
            String extencion = "txt";
            if (request.getParameter("filetype").toString().equals("xls")) {
                extencion = "xls";
            }
            // get absolute path of the application
            ServletContext context = request.getSession().getServletContext();
            String fullPath = context.getRealPath("")
                    + "/assets/filedemo/uploadtemplate." + extencion;
            // construct the complete absolute path of the file
            File downloadFile = new File(fullPath);
            if (downloadFile.exists()) {
                FileInputStream inputStream = new FileInputStream(downloadFile);
                // set content attributes for the response
                response.setContentType("application/octet-stream");
                response.setContentLength((int) downloadFile.length());

                // set headers for the response
                String headerKey = "Content-Disposition";
                String headerValue = String.format(
                        "attachment; filename=\"%s\"", "Danh sach so mau."
                        + extencion);
                response.setHeader(headerKey, headerValue);

                // get output stream of the response
                OutputStream outStream = response.getOutputStream();

                byte[] buffer = new byte[10096];
                int bytesRead = -1;

                // write bytes read from the input stream into the output stream
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outStream.close();
            } else {
                response.sendRedirect(request.getContextPath() + "/404.html");
            }
        } catch (Exception e) {
        }
        return null;
    }

    @GetMapping("/profile")
    public ModelAndView editProfile() {
        ModelAndView model = new ModelAndView();
        model.setViewName("editProfile");

        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            model.setViewName("403");
        }
        customer = customerService.getUserInfo(auth.getName(), "username");

        model.addObject("user", customer);
        return model;
    }

    @RequestMapping(value = "/changePass", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    @ResponseBody
    public String changePass(Model model, HttpServletRequest request) {
        String result = "error";
        try {
            String newPass = request.getParameter("password");
            String confirmNewPass = request.getParameter("confirmpassword");
            String curPass = request.getParameter("curPass");

            if (newPass == null || newPass.isEmpty()) {
                result = "newPassEmpty";
            } else if (confirmNewPass == null || confirmNewPass.isEmpty()) {
                result = "confirmPassEmpty";
            } else if (!confirmNewPass.equals(newPass)) {
                result = "notMatch";
            } else {
                Authentication auth = SecurityContextHolder.getContext()
                        .getAuthentication();
                Customer user = customerService.getUserInfo(auth.getName(), "username");
                //md5
//                MessageDigest md = MessageDigest.getInstance("MD5");
//                md.update(curPass.getBytes());
//                byte byteData[] = md.digest();
//                StringBuffer sb = new StringBuffer();
//
//                for (int i = 0; i < byteData.length; i++) {
//                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
//                }
//                curPass = sb.toString();

                PasswordEncoder passwordEnocder = new BCryptPasswordEncoder();
                if (!passwordEnocder.matches(curPass, user.getPassword())) {
                    result = "curPassWrong";
                } else {
                    //md5
//                    md.update(newPass.getBytes());
//                    byte byteData2[] = md.digest();
//                    sb = new StringBuffer();
//
//                    for (int i = 0; i < byteData2.length; i++) {
//                        sb.append(Integer.toString((byteData2[i] & 0xff) + 0x100, 16).substring(1));
//                    }
                    newPass = encoder.encode(newPass);
                    user.setPassword(newPass);
                    boolean check = customerService.updateCustomer(user);
                    if (check) {
                        result = "success";
                    }
                    String ip = Utils.getIpClient(request);
                    logAccessService.addLogWithUserId(user.getId(), "Thay đổi mật khẩu", "", ip);
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    @GetMapping("/shopManager")
    public ModelAndView shopManager() {
        ModelAndView model = new ModelAndView();
        model.setViewName("shopManager");

        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            model.setViewName("403");
        }
        customer = customerService.getUserInfo(auth.getName(), "username");
        model.addObject("user", customer);
        return model;
    }

    @RequestMapping(value = "/changeShopInfo", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    @ResponseBody
    public String changeShopInfo(Model model, HttpServletRequest request) {
        String result = "error";
        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "403";
        }

        try {
            String typedomain1 = request.getParameter("typedomain1");
            String typedomain2 = request.getParameter("typedomain2");
            String typedomain3 = request.getParameter("typedomain3");
            String textdomain3 = request.getParameter("textdomain3");
            String introduce = request.getParameter("introduce");
            String linkMap = request.getParameter("linkMap");

            customer = customerService.getUserInfo(auth.getName(), "username");

            customer.setIntroduce(introduce);
//            customer.setLinkMap(StringEscapeUtils.escapeHtml4(linkMap));
            customer.setLinkMap(linkMap);

            if (typedomain1 != null && typedomain2 == null && typedomain3 == null) {
                customer.setDomain(customer.getUsername() + ".sansim.vn");
            } else if (typedomain1 == null && typedomain2 != null && typedomain3 == null) {
                customer.setDomain("sansim.vn/" + customer.getUsername());
            } else if (typedomain1 == null && typedomain2 == null && typedomain3 != null) {
                if (textdomain3.contains(".sansim.vn") || textdomain3.startsWith("sansim.vn")) {
                    return "typedomain3Systax";
                } else if (customerService.isExitDomain(textdomain3, customer.getId())) {
                    return "domainIsExits";
                } else {
                    customer.setDomain(textdomain3);
                }
            }
            boolean check = customerService.updateCustomer(customer);
            if (check) {
                result = "success";
            }

        } catch (Exception e) {
        }
        return result;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    @ResponseBody
    public String changeInfo(Model model, HttpServletRequest request) {
        String result = "error";
        try {
            String name = request.getParameter("fullname");
            String details = request.getParameter("description");
            String address = request.getParameter("address");
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            Customer user = customerService.getUserInfo(auth.getName(), "username");
            user.setFull_name(name);
            user.setDescription(details);
            user.setAddress(address);
            boolean check = customerService.updateCustomer(user);
            if (check) {
                result = "success";
            }
            String ip = Utils.getIpClient(request);
            logAccessService.addLogWithUserId(user.getId(), "Thay đổi thông tin cá nhân", "", ip);
        } catch (Exception e) {
        }
        return result;
    }

    @RequestMapping(value = "/transpay", method = {RequestMethod.GET, RequestMethod.POST})
    public String transpayLog(Model model,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            @RequestParam(value = "p", required = false, defaultValue = "1") int page,
            HttpServletRequest request) {
        try {
            Customer customer = new Customer();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
                customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String packageName = null, type = null;
                Date fromDate = null, toDate = null;
                Long userId = 0L;
                if (!filter.equals("")) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode actualObj = mapper.readTree(filter);
                    JsonNode packageFilter = actualObj
                            .findValue("packageName");
                    if (packageFilter != null
                            && !isNullOrEmpty(packageFilter.getTextValue())) {
                        packageName = packageFilter.getTextValue();
                    }
                    JsonNode typeFilter = actualObj.findValue("type");
                    if (typeFilter != null
                            && !isNullOrEmpty(typeFilter.getTextValue())) {
                        type = typeFilter.getTextValue();
                    }
                    SimpleDateFormat fm = new SimpleDateFormat(
                            "dd/MM/yyyy HH:mm:ss");

                    JsonNode fromDateFilter = actualObj.findValue("fromDate");
                    if (fromDateFilter != null
                            && !isNullOrEmpty(fromDateFilter.getTextValue())) {
                        fromDate = fm.parse(fromDateFilter.getTextValue()
                                + " 00:00:00");
                    }

                    JsonNode toDateFilter = actualObj.findValue("toDate");
                    if (toDateFilter != null
                            && !isNullOrEmpty(toDateFilter.getTextValue())) {
                        toDate = fm.parse(toDateFilter.getTextValue()
                                + " 23:59:59");
                    }
                }

                int limit = getLimit();
                userId = customer.getId();

                int total = customerService.countTotalTranspayLog(packageName, type,
                        fromDate, toDate, userId);
                if (total > 0) {
                    List<TranspayLog> list = customerService.getPagingTranspayLog(page, limit,
                            packageName, type, fromDate, toDate,
                            userId);
                    model.addAttribute("listresult", list);
                    int totalPage = total / limit;
                    if (total % limit > 0) {
                        totalPage++;
                    }
                    setPagingProperty(model, page, total, totalPage, limit);
                } else {
                    setPagingProperty(model, page, 0, 1, limit);
                }
            } else {
                return "403";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "transpayLog";
    }

    @RequestMapping(value = "/forwardLog", method = {RequestMethod.GET, RequestMethod.POST})
    public String forwardLog(Model model,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            @RequestParam(value = "p", required = false, defaultValue = "1") int page,
            HttpServletRequest request) {
        try {
            Customer customer = new Customer();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
                customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String msisdn = null, userName = null;
                Date fromDate = null, toDate = null;
                Long userId = 0L;
                if (!filter.equals("")) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode actualObj = mapper.readTree(filter);
                    JsonNode msisdnFilter = actualObj
                            .findValue("msisdn");
                    if (msisdnFilter != null
                            && !isNullOrEmpty(msisdnFilter.getTextValue())) {
                        msisdn = msisdnFilter.getTextValue();
                    }
                    JsonNode userNameFilter = actualObj.findValue("userName");
                    if (userNameFilter != null
                            && !isNullOrEmpty(userNameFilter.getTextValue())) {
                        userName = userNameFilter.getTextValue();
                    }
                    SimpleDateFormat fm = new SimpleDateFormat(
                            "dd/MM/yyyy HH:mm:ss");

                    JsonNode fromDateFilter = actualObj.findValue("fromDate");
                    if (fromDateFilter != null
                            && !isNullOrEmpty(fromDateFilter.getTextValue())) {
                        fromDate = fm.parse(fromDateFilter.getTextValue()
                                + " 00:00:00");
                    }

                    JsonNode toDateFilter = actualObj.findValue("toDate");
                    if (toDateFilter != null
                            && !isNullOrEmpty(toDateFilter.getTextValue())) {
                        toDate = fm.parse(toDateFilter.getTextValue()
                                + " 23:59:59");
                    }
                }

                int limit = getLimit();
                userId = customer.getId();

                Customer cusReceiver = null;
                if (userName != null && userName.length() > 0) {
                    cusReceiver = customerService.getUserInfo(userName, "username");
                    if (cusReceiver == null) {
                        setPagingProperty(model, page, 0, 0, limit);
                        model.addAttribute("listresult", null);
                        return "forwardLog";
                    }
                }

                int total = customerService.countTotalForwardLog(msisdn, cusReceiver,
                        fromDate, toDate, userId);
                if (total > 0) {
                    List<ForwardLog> list = customerService.getPagingForwardLog(page, limit,
                            msisdn, cusReceiver, fromDate, toDate,
                            userId);
                    model.addAttribute("listresult", list);
                    int totalPage = total / limit;
                    if (total % limit > 0) {
                        totalPage++;
                    }
                    setPagingProperty(model, page, total, totalPage, limit);
                } else {
                    setPagingProperty(model, page, 0, 1, limit);
                }
            } else {
                return "403";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forwardLog";
    }

    @RequestMapping(value = "/receiverLog", method = {RequestMethod.GET, RequestMethod.POST})
    public String receiverLog(Model model,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            @RequestParam(value = "p", required = false, defaultValue = "1") int page,
            HttpServletRequest request) {
        try {
            Customer customer = new Customer();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
                customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String msisdn = null, userName = null;
                Date fromDate = null, toDate = null;
                Long userId = 0L;
                if (!filter.equals("")) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode actualObj = mapper.readTree(filter);
                    JsonNode msisdnFilter = actualObj
                            .findValue("msisdn");
                    if (msisdnFilter != null
                            && !isNullOrEmpty(msisdnFilter.getTextValue())) {
                        msisdn = msisdnFilter.getTextValue();
                    }
                    JsonNode userNameFilter = actualObj.findValue("userName");
                    if (userNameFilter != null
                            && !isNullOrEmpty(userNameFilter.getTextValue())) {
                        userName = userNameFilter.getTextValue();
                    }
                    SimpleDateFormat fm = new SimpleDateFormat(
                            "dd/MM/yyyy HH:mm:ss");

                    JsonNode fromDateFilter = actualObj.findValue("fromDate");
                    if (fromDateFilter != null
                            && !isNullOrEmpty(fromDateFilter.getTextValue())) {
                        fromDate = fm.parse(fromDateFilter.getTextValue()
                                + " 00:00:00");
                    }

                    JsonNode toDateFilter = actualObj.findValue("toDate");
                    if (toDateFilter != null
                            && !isNullOrEmpty(toDateFilter.getTextValue())) {
                        toDate = fm.parse(toDateFilter.getTextValue()
                                + " 23:59:59");
                    }
                }

                int limit = getLimit();
                userId = customer.getId();

                Customer cusSender = null;
                if (userName != null && userName.length() > 0) {
                    cusSender = customerService.getUserInfo(userName, "username");
                    if (cusSender == null) {
                        setPagingProperty(model, page, 0, 0, limit);
                        model.addAttribute("listresult", null);
                        return "receiverLog";
                    }
                }

                int total = customerService.countTotalReceiverLog(msisdn, cusSender,
                        fromDate, toDate, userId);
                if (total > 0) {
                    List<ForwardLog> list = customerService.getPagingReceiverLog(page, limit,
                            msisdn, cusSender, fromDate, toDate,
                            userId);
                    model.addAttribute("listresult", list);
                    int totalPage = total / limit;
                    if (total % limit > 0) {
                        totalPage++;
                    }
                    setPagingProperty(model, page, total, totalPage, limit);
                } else {
                    setPagingProperty(model, page, 0, 1, limit);
                }
            } else {
                return "403";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "receiverLog";
    }

    @RequestMapping(value = "/smslog", method = {RequestMethod.GET, RequestMethod.POST})
    public String smsLog(Model model,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            @RequestParam(value = "p", required = false, defaultValue = "1") int page,
            HttpServletRequest request) {
        try {
            Customer customer = new Customer();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
                customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String msisdn = null;
                Date fromDate = null, toDate = null;
                Long userId = 0L;
                if (!filter.equals("")) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode actualObj = mapper.readTree(filter);
                    JsonNode msisdnFilter = actualObj
                            .findValue("msisdn");
                    if (msisdnFilter != null
                            && !isNullOrEmpty(msisdnFilter.getTextValue())) {
                        msisdn = msisdnFilter.getTextValue();
                    }
                    SimpleDateFormat fm = new SimpleDateFormat(
                            "dd/MM/yyyy HH:mm:ss");

                    JsonNode fromDateFilter = actualObj.findValue("fromDate");
                    if (fromDateFilter != null
                            && !isNullOrEmpty(fromDateFilter.getTextValue())) {
                        fromDate = fm.parse(fromDateFilter.getTextValue()
                                + " 00:00:00");
                    }

                    JsonNode toDateFilter = actualObj.findValue("toDate");
                    if (toDateFilter != null
                            && !isNullOrEmpty(toDateFilter.getTextValue())) {
                        toDate = fm.parse(toDateFilter.getTextValue()
                                + " 23:59:59");
                    }
                }

                int limit = getLimit();
                userId = customer.getId();

                int total = customerService.countTotalSMSLog(msisdn,
                        fromDate, toDate, userId);
                if (total > 0) {
                    List<MOLog> list = customerService.getPagingSMSLog(page, limit, msisdn, fromDate, toDate,
                            userId);
                    model.addAttribute("listresult", list);
                    int totalPage = total / limit;
                    if (total % limit > 0) {
                        totalPage++;
                    }
                    setPagingProperty(model, page, total, totalPage, limit);
                } else {
                    setPagingProperty(model, page, 0, 1, limit);
                }
            } else {
                return "403";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "smsLog";
    }

    @RequestMapping(value = "/getUserNames", method = RequestMethod.GET)
    public @ResponseBody
    List<Customer> getUserNames(@RequestParam String userName) {

        return customerService.getCustomerWithUserName(userName);

    }

    private boolean checkCaptcha(HttpServletRequest request, String input) {
        boolean result = false;
        if (request.getSession().getAttribute("captchaToken") == null) {
            result = true;
        } else {
            String captcha = request.getSession().getAttribute("captchaToken").toString();
            if (input.equals(captcha)) {
                result = true;
            }
        }
        return result;
    }

    private Byte checkTelNumber(String msisdn) {
        String firstNb3 = msisdn.substring(0, 3);
        String firstNb4 = msisdn.substring(0, 4);
        if (firstNb3.equals("090") || firstNb3.equals("093") || firstNb3.equals("089")
                || firstNb4.equals("0120") || firstNb4.equals("0121")
                || firstNb4.equals("0122") || firstNb4.equals("0126")
                || firstNb4.equals("0128")) {
            return 3;
        } else if (firstNb3.equals("096") || firstNb3.equals("097") || firstNb3.equals("098") || firstNb3.equals("086")
                || firstNb4.equals("0162") || firstNb4.equals("0163")
                || firstNb4.equals("0164") || firstNb4.equals("0165")
                || firstNb4.equals("0166") || firstNb4.equals("0167") || firstNb4.equals("0168") || firstNb4.equals("0169")) {
            return 1;
        } else if (firstNb3.equals("091") || firstNb3.equals("094")
                || firstNb3.equals("088")
                || firstNb4.equals("0123") || firstNb4.equals("0124") || firstNb4.equals("0125") || firstNb4.equals("0127") || firstNb4.equals("0129")) {
            return 2;
        } else if (firstNb3.equals("099") || firstNb4.equals("0199")) {
            return 4;
        } else if (firstNb3.equals("092") || firstNb4.equals("0188") || firstNb4.equals("0186")) {
            return 5;
        }
        return 0;
    }

    private String convertMsisdn(String msisdn) {
        if (msisdn.startsWith("84")) {
            return "0" + msisdn.substring(2);
        } else if (!msisdn.startsWith("84") && !msisdn.startsWith("0") && !msisdn.startsWith("+84")) {
            return "0" + msisdn;
        } else if (msisdn.startsWith("+84")) {
            return "0" + msisdn.substring(3);
        } else {
            return msisdn;
        }
    }

    public long phanLoaiSim(String sim) {
        long rs = 0;

        try {
            sim = sim.substring(sim.length() - 9);
            byte[] b = sim.getBytes();
            //012345678
            for (int i = 0; i < 9; i++) {
                b[i] = (byte) (b[i] - 48);
            }

            if (b[3] == b[4] && b[4] == b[5] && b[5] == b[6] && b[6] == b[7] && b[7] == b[8]) {
                //AAAAAA
                rs = rs + 1;
            }
            if (b[3] != b[4] && b[4] == b[5] && b[5] == b[6] && b[6] == b[7] && b[7] == b[8]) {
                //AAAAA
                rs = rs + 2;
            }
            if (b[4] != b[5] && b[5] == b[6] && b[6] == b[7] && b[7] == b[8]) {
                //AAAA
                rs = rs + 4;
            }
            if (b[3] == b[4] && b[4] == b[5] && b[5] != b[6] && b[6] == b[7] && b[7] == b[8]) {
                //AAABBB
//                return "Tam Hoa Kép";
                rs = rs + 8;
            }
            if (b[5] != b[6] && b[6] == b[7] && b[7] == b[8]) {
                //aaa
//                return "Tam Hoa";
                rs = rs + 16;
            }
            if (b[7] == 6 && b[8] == 8) {
                //68
//                return "Lộc Phát";
                rs = rs + 32;
            }
            if ((b[3] == b[6] && b[4] == b[7] && b[5] == b[8] && b[5] != b[6]) || (b[5] == b[7] && b[6] == b[8] && b[6] != b[7])) {

                //abc.abc abab
//                return "Số Lặp";
                rs = rs + 64;
            }
            if ((b[5] + 1 == b[6]) && (b[5] + 2 == b[7]) && (b[5] + 3 == b[8])) {
                //abcd 
//                return "Số tiến 4";
                rs = rs + 128;
            }
            if ((b[5] + 1 != b[6]) && (b[6] + 1 == b[7]) && (b[6] + 2 == b[8])) {
                //abc 
//                return "Số tiến 3";
                rs = rs + 256;
            }
            if (b[5] == b[8] && b[6] == b[7] && b[5] != b[6]) {
                //abba
//                return "Đảo 2";
                rs = rs + 2048;
            }
            if (b[3] == b[8] && b[4] == b[7] && b[5] == b[6] && (b[3] != b[4] || b[3] != b[5])) {
                //abccba
//                return "Đảo 3";
                rs = rs + 1024;
            }
            if ((b[7] == 3 || b[7] == 7) && b[8] == 9) {
//                return "Thần tài";
                rs = rs + 4096;
            }
            if ((b[7] == 3 || b[7] == 7) && b[8] == 8) {
//                return "Ông địa";
                rs = rs + 8192;
            }
            if (b[3] == b[4] && b[5] == b[6] && b[7] == b[8] && (!(b[3] == b[5] && b[7] == b[5]))) {
                //aa.bb.cc a>=b>=c khong ap dung a=b=c
//                return "Kép 3";
                rs = rs + 16384;
            }
            if (b[5] == b[6] && b[7] == b[8] && b[5] != b[7] && b[3] != b[4]) {
                //aabb
//                return "Kép đôi";
                rs = rs + 32768;
            }
            if (b[5] == 1 && b[6] == 9) {	//196x
//                return "Năm sinh";
                rs = rs + 65536;
            }
            if (b[5] == 2 && b[6] == 0) {	//200x
//                return "Năm sinh";
                rs = rs + 65536;
            }

            if (b[5] < b[6] && b[7] < b[8] && b[6] < b[7]) {
                // sim tăng dần
                rs = rs + 512;
            }

            if ((b[0] == 9 && b[1] == 0) || (b[0] == 9 && b[1] == 1) || (b[0] == 9 && b[1] == 7) || (b[0] == 9 && b[1] == 8) || (b[0] == 9 && b[1] == 3 && b[2] == 4)) {
                // đầu số cổ

                rs = rs + 131072;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    @PostMapping("/editFee")
    public String editFee(HttpServletRequest request, Model model,
            @RequestParam(value = "filterModal", required = false, defaultValue = "") String filter,
            @RequestParam(value = "pModal", required = false, defaultValue = "1") int page) {

        stockMsisdnService.editFee(Long.parseLong(request.getParameter("idModal")),
                Long.parseLong(request.getParameter("price").replaceAll(",", "") == "" ? "0" : request.getParameter("price").replaceAll(",", "")));
        try {
            Customer customer = new Customer();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
                customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String msisdn = null, tel = null, confirmExpired = null, confStatus = null;
                String telco = null;
                Date fromDate = null, toDate = null;
                Long userId = 0L;
                msisdn = request.getParameter("msisdn");

                if (!filter.equals("")) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode actualObj = mapper.readTree(filter);
                    JsonNode msisdnFilter = actualObj
                            .findValue("msisdn");
                    if (msisdnFilter != null
                            && !isNullOrEmpty(msisdnFilter.getTextValue())) {
                        msisdn = msisdnFilter.getTextValue();
                    }
                    JsonNode firstNumberFilter = actualObj.findValue("firstNumber");
                    if (firstNumberFilter != null
                            && !isNullOrEmpty(firstNumberFilter.getTextValue())) {
                        tel = firstNumberFilter.getTextValue();
                    }
                    JsonNode confirmExpiredFilter = actualObj.findValue("confirmExpired");
                    if (confirmExpiredFilter != null
                            && !isNullOrEmpty(confirmExpiredFilter.getTextValue())
                            && !confirmExpiredFilter.getTextValue().equals("-1")) {
                        confirmExpired = confirmExpiredFilter.getTextValue();
                    }

                    SimpleDateFormat fm = new SimpleDateFormat(
                            "dd/MM/yyyy HH:mm:ss");

                    JsonNode fromDateFilter = actualObj.findValue("fromDate");
                    if (fromDateFilter != null
                            && !isNullOrEmpty(fromDateFilter.getTextValue())) {
                        fromDate = fm.parse(fromDateFilter.getTextValue()
                                + " 00:00:00");
                    }

                    JsonNode toDateFilter = actualObj.findValue("toDate");
                    if (toDateFilter != null
                            && !isNullOrEmpty(toDateFilter.getTextValue())) {
                        toDate = fm.parse(toDateFilter.getTextValue()
                                + " 23:59:59");
                    }

                    JsonNode confStatusStr = actualObj.findValue("confstatus");
                    if (confStatusStr != null
                            && !isNullOrEmpty(confStatusStr.getTextValue())
                            && !confStatusStr.getTextValue().equals("-1")) {
                        confStatus = confStatusStr.getTextValue();
                    }
                    JsonNode telcoStr = actualObj.findValue("telco");
                    if (telcoStr != null && !isNullOrEmpty(telcoStr.getTextValue()) && !telcoStr.getTextValue().equals(";")) {
                        telco = telcoStr.getTextValue();
                    }
                }

                String[] listTelco = null;
                if (telco != null && telco.length() > 1 && telco.startsWith(";")) {
                    listTelco = telco.split(";");
                }

                int limit = getLimit();
                userId = customer.getId();

                int total = msisdnService.countTotalMsisdn(msisdn, tel, confirmExpired,
                        fromDate, toDate, userId, confStatus, listTelco);
                if (total > 0) {
                    List<StockMsisdn> list = msisdnService.getPagingMsisdn(page, limit,
                            msisdn, tel, confirmExpired, fromDate, toDate,
                            userId, confStatus, listTelco);
                    model.addAttribute("listresult", list);
                    CustService custService = msisdnService.getServiceRegister(customer.getId());
                    model.addAttribute("custService", custService);
                    int totalPage = total / limit;
                    if (total % limit > 0) {
                        totalPage++;
                    }
                    setPagingProperty(model, page, total, totalPage, limit);
                } else {
                    setPagingProperty(model, page, 0, 1, limit);
                }
                model.addAttribute("telco", telco);
            } else {
                return "403";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("deleteURL", "removeMsisdn");
        return "viewMsisdn";
    }

    @PostMapping("/editStatus")
    @ResponseBody
    public String editStatus(long id, Short valSTT) {
        int check = customerService.editStatus(id, valSTT.equals(new Short("1")) ? new Short("0") : new Short("1"));
        return check == 1 ? "success" : "error";
    }

    @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
    public String downloadExcel(HttpServletRequest request,
            HttpServletResponse response, Model model,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter) throws IOException, ParseException {

        //fill data to excel
        Customer customer = new Customer();
        customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String msisdn = null, tel = null, confirmExpired = null, confStatus = null;
        String telco = null;
        Date fromDate = null, toDate = null;
        Long userId = 0L;
        msisdn = request.getParameter("msisdn");

        if (!filter.equals("")) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(filter);
            JsonNode msisdnFilter = actualObj
                    .findValue("msisdn");
            if (msisdnFilter != null
                    && !isNullOrEmpty(msisdnFilter.getTextValue())) {
                msisdn = msisdnFilter.getTextValue();
            }
            JsonNode firstNumberFilter = actualObj.findValue("firstNumber");
            if (firstNumberFilter != null
                    && !isNullOrEmpty(firstNumberFilter.getTextValue())) {
                tel = firstNumberFilter.getTextValue();
            }
            JsonNode confirmExpiredFilter = actualObj.findValue("confirmExpired");
            if (confirmExpiredFilter != null
                    && !isNullOrEmpty(confirmExpiredFilter.getTextValue())
                    && !confirmExpiredFilter.getTextValue().equals("-1")) {
                confirmExpired = confirmExpiredFilter.getTextValue();
            }

            SimpleDateFormat fm = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm:ss");

            JsonNode fromDateFilter = actualObj.findValue("fromDate");
            if (fromDateFilter != null
                    && !isNullOrEmpty(fromDateFilter.getTextValue())) {
                fromDate = fm.parse(fromDateFilter.getTextValue()
                        + " 00:00:00");
            }

            JsonNode toDateFilter = actualObj.findValue("toDate");
            if (toDateFilter != null
                    && !isNullOrEmpty(toDateFilter.getTextValue())) {
                toDate = fm.parse(toDateFilter.getTextValue()
                        + " 23:59:59");
            }

            JsonNode confStatusStr = actualObj.findValue("confstatus");
            if (confStatusStr != null
                    && !isNullOrEmpty(confStatusStr.getTextValue())
                    && !confStatusStr.getTextValue().equals("-1")) {
                confStatus = confStatusStr.getTextValue();
            }
            JsonNode telcoStr = actualObj.findValue("telco");
            if (telcoStr != null && !isNullOrEmpty(telcoStr.getTextValue()) && !telcoStr.getTextValue().equals(";")) {
                telco = telcoStr.getTextValue();
            }
        }

        String[] listTelco = null;
        if (telco != null && telco.length() > 1 && telco.startsWith(";")) {
            listTelco = telco.split(";");
        }

        int pageDefault = 1;
        userId = customer.getId();

        int total = msisdnService.countTotalMsisdn(msisdn, tel, confirmExpired,
                fromDate, toDate, userId, confStatus, listTelco);

        List<StockMsisdn> list = msisdnService.getPagingMsisdn(pageDefault, total,
                msisdn, tel, confirmExpired, fromDate, toDate,
                userId, confStatus, listTelco);

        model.addAttribute("telco", telco);

        // create a small spreadsheet
        String filePath = request.getSession().getServletContext().getRealPath("/") + "assets/filedemo/listMsisdn.xls";
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
        HSSFSheet sheet = wb.getSheetAt(0);

        //set value content
        DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
        int rowContent = 2;
        int index = 1;
        CustService custService = msisdnService.getServiceRegister(customer.getId());
        for (StockMsisdn item : list) {
            Row row = sheet.createRow(rowContent++);
            setCellValue(wb, row.createCell(0), Font.COLOR_NORMAL, String.valueOf(index++), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            setCellValue(wb, row.createCell(1), Font.COLOR_NORMAL, item.getMsisdn() == null ? "" : item.getMsisdn(), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            if (item.getTelco() == 1) {
                setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Vietel", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            } else if (item.getTelco() == 2) {
                setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Vinaphone", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            } else if (item.getTelco() == 3) {
                setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Mobifone", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            } else if (item.getTelco() == 4) {
                setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Gmobile", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            } else if (item.getTelco() == 5) {
                setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Vietnamobile", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            } else {
                setCellValue(wb, row.createCell(2), Font.COLOR_NORMAL, "Nhà mạng khác", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            }
            setCellValue(wb, row.createCell(3), Font.COLOR_NORMAL, item.getPrice().toString() == null ? "Thương lượng" : item.getPrice().toString(), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            setCellValue(wb, row.createCell(4), Font.COLOR_NORMAL, item.getGenDate() == null ? "" : df.format(item.getGenDate()), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            setCellValue(wb, row.createCell(5), Font.COLOR_NORMAL, item.getStatus() == 1 ? "Hiển thị" : "Không hiển thị", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            if (item.getStatus() == 1 && custService != null) {
                setCellValue(wb, row.createCell(6), Font.COLOR_NORMAL, df.format(custService.getExpiredDate()), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            } else {
                setCellValue(wb, row.createCell(6), Font.COLOR_NORMAL, "", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            }
            setCellValue(wb, row.createCell(7), Font.COLOR_NORMAL, item.getConfirmStatus() == 1 ? "Xác thực" : "Chưa xác thực", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            if (item.getConfirmExpired() == null) {
                setCellValue(wb, row.createCell(8), Font.COLOR_NORMAL, "", Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            } else {
                setCellValue(wb, row.createCell(8), Font.COLOR_NORMAL, df.format(item.getConfirmExpired()), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
            }
            setCellValue(wb, row.createCell(9), Font.COLOR_NORMAL, item.getDescription() == null ? "" : item.getDescription(), Font.BOLDWEIGHT_NORMAL, false, true, true, CellStyle.VERTICAL_CENTER, CellStyle.ALIGN_CENTER);
        }
        //set autosize content
        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i, true);
        }

        // write it as an excel attachment
        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        wb.write(outByteStream);
        byte[] outArray = outByteStream.toByteArray();
        response.setContentType("application/ms-excel");
        response.setContentLength(outArray.length);
        response.setHeader("Expires:", "0"); // eliminates browser caching
        response.setHeader("Content-Disposition", "attachment; filename=Danh_sach_so.xls");
        OutputStream outStream = response.getOutputStream();
        outStream.write(outArray);
        outStream.flush();
//        response.getOutputStream().close(); 
//        return new ByteArrayInputStream(outArray);
        return "viewMsisdn";

    }

    @RequestMapping(value = "/upMsisdnGuide", method = RequestMethod.GET)
    public String viewUploadGuide(Model model, HttpServletRequest request) {
        Customer customer = new Customer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return "403";
        }
//        model.addAttribute("uploadtype",
//                request.getParameter("type") == null ? "single" : request
//                .getParameter("type").toString());
        return "uploadGuide";
    }

    private void setCellValue(Workbook workbook, Cell cell, short color, String value, Short bold, boolean isItalic,
            boolean isBorder, boolean isWrapText, Short verticalAlignment, Short horizontalAlignment) {
        cell.setCellValue(value);
        Font font = setFont(workbook, bold, isItalic, color);
        cell.setCellStyle(setCellStyle(workbook, value, font, isWrapText, isBorder, verticalAlignment, horizontalAlignment));
    }

    private Font setFont(Workbook workbook, Short bold, boolean isItalic, short color) {
        Font font = workbook.createFont();
        if (bold != null) {
            font.setBoldweight(bold);
        }
        font.setColor(color);
        font.setFontName("Times New Roman");
        font.setItalic(isItalic);
        font.setFontHeight((short) (12 * 20));
        return font;
    }

    private CellStyle setCellStyle(Workbook workbook, String value, Font font, boolean isWrapText, boolean isBorder, Short verticalAlignment, Short horizontalAlignment) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setWrapText(isWrapText);

        CreationHelper createHelper = workbook.getCreationHelper();
        if (StringUtils.isNumeric(value)) {
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("######.###"));
        } else {
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("@"));
        }

        if (verticalAlignment != null) {
            cellStyle.setVerticalAlignment(verticalAlignment);
        }
        if (horizontalAlignment != null) {
            cellStyle.setAlignment(horizontalAlignment);
        }

        if (isBorder) {
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        }
        return cellStyle;
    }

}

////////////////////////////////////////////////////////////////////////////

