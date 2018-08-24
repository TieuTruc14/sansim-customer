package com.osp.web.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonNode;
import org.springframework.ui.Model;

//import com.osp.chonso.common.ConfigProperties;
public class BaseController {

    private final String START_ITEM_INDEX_KEY = "start";
    private final String LIMIT_KEY = "rowPerPage";
    private final String PRICE_STEP_CONFIG = "priceStepConfig";
    private final String MAX_PAGE_LINE_KEY = "maxPageLine";
    private final String TOTAL_ITEM_KEY = "total";
    private final String TOTAL_PAGE_KEY = "totalPage";
    private final String START_PAGE = "startPage";
    private final String END_PAGE = "endPage";
    protected final String START_TIME = " 00:00:00";
    protected final String END_TIME = " 23:59:59";
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * @return 2
     */
    protected static final String FILE_TYPE_NOTIFY = "Thông báo";
    /**
     * @return 1
     */
    protected static final String FILE_TYPE_CONTRACT = "Hợp đồng";
    /**
     * @return 3
     */
    protected static final String FILE_TYPE_CONTRACT_KIND = "Mẫu hợp đồng";
    /**
     * @return 4
     */
    protected static final String FILE_TYPE_DOCUMENT = "Tài liệu";
    /**
     * @return 5
     */
    protected static final String FILE_TYPE_MANUAL = "Hướng dẫn sử dụng";
    /**
     * @return 6
     */
    protected static final String FILE_TYPE_PREVENT = "Thông tin ngăn chặn";
    /**
     * @return 7
     */
    protected static final String FILE_TYPE_PREVENT_RELEASE = "Thông tin ngăn chặn giải tỏa";
    protected static final HashMap<String, Integer> fileTypes;

    static {
        fileTypes = new HashMap<String, Integer>();
        fileTypes.put(FILE_TYPE_CONTRACT, 1);
        fileTypes.put(FILE_TYPE_NOTIFY, 2);
        fileTypes.put(FILE_TYPE_CONTRACT_KIND, 3);
        fileTypes.put(FILE_TYPE_DOCUMENT, 4);
        fileTypes.put(FILE_TYPE_MANUAL, 5);
        fileTypes.put(FILE_TYPE_PREVENT, 6);
        fileTypes.put(FILE_TYPE_PREVENT_RELEASE, 7);
    }
    ;
    protected static final HashMap<String, String> dateFilterTypes;

    static {
        dateFilterTypes = new HashMap<String, String>();
        dateFilterTypes.put("1", "1");
        dateFilterTypes.put("2", "2");
        dateFilterTypes.put("3", "3");
        dateFilterTypes.put("4", "4");
        dateFilterTypes.put("5", "5");
    }
    ;
    /**
	 * @return 1
	 */
    protected static final String DATA_PREVENT_STATUS_DATIEPNHAN = "Thông tin ngăn chặn đã tiếp nhận";
    /**
     * @return 2
     */
    protected static final String DATA_PREVENT_STATUS_DATRINHLANHDAO = "Thông tin ngăn chặn đã trình lãnh đạo";
    /**
     * @return 3
     */
    protected static final String DATA_PREVENT_STATUS_DANGXULY = "Thông tin ngăn chặn đang xử lý";
    /**
     * @return 4
     */
    protected static final String DATA_PREVENT_STATUS_DADUYET = "Thông tin ngăn chặn đã duyệt";
    protected static final HashMap<String, Byte> DataPreventStatus;

    static {
        DataPreventStatus = new HashMap<String, Byte>();
        DataPreventStatus.put(DATA_PREVENT_STATUS_DATIEPNHAN, (byte) 1);
        DataPreventStatus.put(DATA_PREVENT_STATUS_DATRINHLANHDAO, (byte) 2);
        DataPreventStatus.put(DATA_PREVENT_STATUS_DANGXULY, (byte) 3);
        DataPreventStatus.put(DATA_PREVENT_STATUS_DADUYET, (byte) 4);
    }

    ;

    public BaseController() {
    }

    public int getLimit() {
        int limit = 10;
        try {
//            limit = Integer.parseInt(ConfigProperties
//                    .getConfigProperties(LIMIT_KEY));
            limit = 25;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return limit;
    }

    public int getMaxPageToDisplay() {
        int maxPage = 5;
//        try {
//            maxPage = Integer.parseInt(ConfigProperties
//                    .getConfigProperties(MAX_PAGE_LINE_KEY));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return maxPage;
    }

    public void setPagingProperty(Model model, int page, int total, int totalPage, int limit) {
        model.addAttribute(START_ITEM_INDEX_KEY, page * limit - limit);
        model.addAttribute(TOTAL_ITEM_KEY, total);
        model.addAttribute(TOTAL_PAGE_KEY, totalPage);
        int maxPage = getMaxPageToDisplay();
        int div = maxPage / 2;
        int start = page - div > 0 ? page - div : 1;
        if (start > 1 && page + div >= totalPage) {
            start = (totalPage - maxPage + 1) > 0 ? (totalPage - maxPage + 1) : 1;
        }
        model.addAttribute(START_PAGE, start);
        model.addAttribute(END_PAGE, start + maxPage - 1 < totalPage ? start + maxPage - 1 : totalPage);
    }

    /**
     * Method total page
     *
     * @param totalCount
     * @param limit
     * @return
     */
    public int pageCalculation(int totalCount, int limit) {
        int totalPage = totalCount / limit;
        if (0 < (totalCount % limit)) {
            ++totalPage;
        }
        return totalPage;
    }

    /**
     * <P>
     * Operate page transition
     * </P>
     *
     * @param direction page transition direction
     * @param page current page
     * @param totalPage total page
     * @return page number
     */
    public int pageTransition(String direction, int page, int totalPage) {

        if (direction == null) {
            direction = "";
        }

        if (direction.equalsIgnoreCase("first")) {
            page = 1;
        } else if (direction.equalsIgnoreCase("ahead")) {
            if (1 > (page - 1)) {
                page = totalPage;
            } else {
                --page;
            }
        } else if (direction.equalsIgnoreCase("next")) {
            if (totalPage < (page + 1)) {
                page = 1;
            } else {
                ++page;
            }
        } else if (direction.equalsIgnoreCase("last")) {
            page = totalPage;
        } else {
            if (page > totalPage) {
                page = totalPage;
            }
        }
        return page;
    }

    /**
     *
     * @param totalCount
     * @param limit
     * @return
     */
    public int fistPageCalculation(int maxPageLine, int totalPage, int page) {
        int firstPage;
        if (totalPage <= maxPageLine) {
            firstPage = 1;
        } else {
            int firstLinePage, lastLinePage;
            int middlePage = maxPageLine / 2;
            if (maxPageLine % 2 != 0) {
                middlePage = middlePage + 1;
            }
            firstLinePage = middlePage - 1;
            lastLinePage = maxPageLine - middlePage;
            if ((page + lastLinePage) >= totalPage) {
                firstPage = totalPage - maxPageLine + 1;
            } else {
                if ((page - firstLinePage) > 1) {
                    firstPage = page - firstLinePage;
                } else {
                    firstPage = 1;
                }
            }
        }
        return firstPage;
    }

    /**
     * Last page calculation
     *
     * @param userForm
     * @param maxPageLine
     * @param totalPage
     * @param page
     */
    public int lastPageCalculation(int maxPageLine, int totalPage, int page,
            int firstPage) {
        int lastPage;
        if (totalPage <= maxPageLine) {
            lastPage = totalPage;
        } else {
            if ((firstPage + maxPageLine) > totalPage) {
                lastPage = totalPage;
            } else {
                lastPage = firstPage + maxPageLine - 1;
            }
        }
        return lastPage;
    }

    public String getDateTimeFilter(String type, String dateStart, String dateEnd, JsonNode actualObj) {
        if (type.equals(dateFilterTypes.get("1"))) {
            dateStart = formatter.format(new Date()) + START_TIME;
            dateEnd = formatter.format(new Date()) + END_TIME;
        } else if (type.equals(dateFilterTypes.get("2"))) {
            dateStart = getFirstDayOfWeek() + START_TIME;
            dateEnd = getLastDayOfWeek() + END_TIME;
        } else if (type.equals(dateFilterTypes.get("3"))) {
            dateStart = "01/" + getCurrentMonth() + "/" + getCurrentYear() + START_TIME;
            dateEnd = getLastDayOfMonth() + END_TIME;
        } else if (type.equals(dateFilterTypes.get("4"))) {
            dateStart = "01/01/" + getCurrentYear() + START_TIME;
            dateEnd = "31/12/" + getCurrentYear() + END_TIME;
        }
        return dateStart + ";" + dateEnd;
    }

    public int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    public String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month > 9 ? "" + month : "0" + month;
    }

    public int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return (day > 9 ? day : "0" + day) + "/" + (month > 9 ? month : "0" + month) + "/" + year;
    }

    public String getCurrentDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return (day > 9 ? day : "0" + day) + "/" + (month > 9 ? month : "0" + month) + "/" + year;
    }

    public String getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();
        return formatter.format(lastDayOfMonth);
    }

    public String getLastDayOfWeek() {
        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        c.add(Calendar.DATE, 6);
        return df.format(c.getTime());
    }

    public String getFirstDayOfWeek() {
        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(c.getTime());
    }

    public boolean isDate(Object obj) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.format(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param str
     * @return true if String is null or empty
     */
    public boolean isNullOrEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public String getClientIpAdress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

}
