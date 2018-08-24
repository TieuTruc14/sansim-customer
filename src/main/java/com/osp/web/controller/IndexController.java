package com.osp.web.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cage.Cage;
import com.github.cage.GCage;
import com.osp.common.Constant;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.Article;
import com.osp.model.Customer;
import com.osp.model.view.ResultSearchIndex;
import com.osp.model.view.SearchIndex;
import com.osp.web.service.article.ArticleService;
import com.osp.web.service.customer.CustomerService;
import com.osp.web.service.msisdn.StockMsisdnService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.Normalizer;
import java.util.Set;

import java.util.*;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Admin on 12/14/2017.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    private Logger logger = LogManager.getLogger(IndexController.class);
    @Autowired
    StockMsisdnService msisdnService;
    @Autowired
    CustomerService customerService;
    private static final Cage cage = new GCage();

    @Autowired
    ArticleService articleService;
    @Value("${image_folder_get}")
    private String imageFolderGet;
    @Value("${image_sim_gen}")
    private String imageSimGenFolder;



    protected String PAGE_TITLE_PARAM = "PAGE_TITLE";
    protected String PAGE_META_TITLE_PARAM = "PAGE_META_TITLE";
    protected String PAGE_META_URL_PARAM = "PAGE_META_URL";
    protected String PAGE_META_DESCRIPTION_PARAM = "PAGE_META_DESCRIPTION";
    protected String PAGE_META_IMAGE_PARAM = "PAGE_META_IMAGE";

    @GetMapping("/")
    public String Index(Model model, HttpServletRequest request) {
        String path = request.getRequestURI().substring(1);
        String url = request.getRequestURL().toString().substring(7);

        Customer shopManager = new Customer();
        if (url != null) {
//            String[] urlList = url.split("/");
//            if(urlList != null && urlList.length >=2){
//                
//            }else {
//                
//            }
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
            shopManager = customerService.getShopManagerAccess(url);
        }
        if (shopManager != null) {
            request.getSession().setAttribute("userShop", shopManager);

        } else {
            request.getSession().setAttribute("userShop", shopManager);
        }

        model.addAttribute("urlImage", imageFolderGet);
        return "index";
    }

    @RequestMapping("*")
//    @ResponseBody
    public String hello(HttpServletRequest request, Model model) {
        String path = request.getRequestURI().substring(1);
        String url = request.getRequestURL().toString().substring(7);

        Customer shopManager = new Customer();
        if (url != null) {
            shopManager = customerService.getShopManagerAccess(url);
        }
        if (shopManager != null) {
            request.getSession().setAttribute("userShop", shopManager);
        } else {
            request.getSession().setAttribute("userShop", shopManager);
        }
        return "index";
    }

    @PostMapping("/image-save")
    public ResponseEntity<String> saveImage(@RequestParam("image") String image, HttpServletRequest request) {
        byte[] imageByte = Base64.decodeBase64(image);
        String filePath = request.getServletContext().getRealPath("/") + "assets/upload/shareImage/";
        String fileName = "";
        try {
            String img_dir = filePath;
            File dirs = new java.io.File(img_dir);
            if (!dirs.exists()) {
                dirs.mkdirs();
            }
            File file = null;
            int count = 0;
            do {
                count++;
                fileName = UUID.randomUUID().toString() + ".png";
                file = new java.io.File(img_dir, fileName);
            } while (file.exists() && count < 10);
            if (file != null) {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(imageByte);
                outputStream.close();
//                Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
//                perms.add(PosixFilePermission.OWNER_READ);
//                perms.add(PosixFilePermission.OWNER_WRITE);
//                perms.add(PosixFilePermission.OWNER_EXECUTE);
//                perms.add(PosixFilePermission.GROUP_READ);
//                perms.add(PosixFilePermission.GROUP_WRITE);
//                perms.add(PosixFilePermission.GROUP_EXECUTE);
//                perms.add(PosixFilePermission.OTHERS_READ);
//                perms.add(PosixFilePermission.OTHERS_WRITE);
//                perms.add(PosixFilePermission.OTHERS_EXECUTE);
//                Files.setPosixFilePermissions(file.toPath(), perms);

            }
        } catch (Exception e) {
            logger.error("have error method saveImage: " + e.getMessage());
            return new ResponseEntity<String>("error", HttpStatus.OK);
        }
        return new ResponseEntity<String>(imageSimGenFolder + fileName, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            if (error.equals("true")) {
                model.addObject("error", "1");
            } else if (error.equals("disable")) {
                model.addObject("error", "0");
            }

        }
        if (logout != null) {
            model.addObject("msg", "logoutSucess");
        }
        model.setViewName("login");
        return model;
    }

    @GetMapping("/search")
    public ResponseEntity<ResultSearchIndex> search(@RequestParam String search, @RequestParam(name = "p", defaultValue = "1") int pageNumber,
            HttpServletRequest request, String captcha, Model model) {
        ResultSearchIndex result = new ResultSearchIndex();
        int count = getAndSetCaptchaCount(request);
        if (count > 50) {
            if (!checkCaptcha(request, captcha)) {
                result.setErrorCaptcha(true);
                return new ResponseEntity<ResultSearchIndex>(result, HttpStatus.OK);
            }
        }
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Customer shopManager = null;
        Long cusId = null;
        try {
            shopManager = (Customer) request.getSession().getAttribute("userShop");
            cusId = shopManager.getId();
        } catch (Exception e) {
        }

//        ShopManager shopManager = model.
        try {
            SearchIndex searchIndex = objectMapper.readValue(search, SearchIndex.class);
            page = msisdnService.searchIndex(page, cusId, searchIndex).orElse(new PagingResult());
            result.setPage(page);
            /*refresh*/
            refreshCaptcha(request);
        } catch (Exception e) {
            logger.error("have an error method IndexController.search():" + e.getMessage());
            return new ResponseEntity<ResultSearchIndex>(new ResultSearchIndex(), HttpStatus.OK);
        }
        return new ResponseEntity<ResultSearchIndex>(result, HttpStatus.OK);
    }

    @GetMapping("/tim-theo-loai/{link}")
    public String searchTheoLoai(@PathVariable("link") String link,Model model){
        if(StringUtils.isBlank(link) || link.indexOf("pr=")==-1 || link.indexOf("gr=")==-1 || link.indexOf("y=")==-1 || link.indexOf("tel=")==-1) return "404";
        List<String> myList = new ArrayList<String>(Arrays.asList(link.split("-")));
        if(myList==null || myList.size()<4) return "404";
        String price=myList.get(myList.size()-4);
        String msisdn=myList.get(myList.size()-3);
        String year=myList.get(myList.size()-2);
        String telco=myList.get(myList.size()-1);
        if(price.indexOf("pr=")==-1 || msisdn.indexOf("gr=")==-1 || year.indexOf("y=")==-1 || telco.indexOf("tel=")==-1){return "404";}
        try{
            Integer gprice;
            Long gmsisdn;
            Integer gyear;
            Integer gtelco;
            List<String> listPrice=new ArrayList<String>(Arrays.asList(price.split("=")));
            List<String> listMsisdn=new ArrayList<String>(Arrays.asList(msisdn.split("=")));
            List<String> listYear=new ArrayList<String>(Arrays.asList(year.split("=")));
            List<String> listTelco=new ArrayList<String>(Arrays.asList(telco.split("=")));
            if(listPrice.size()!=2 || listMsisdn.size()!=2 || listYear.size()!=2 || listTelco.size()!=2) return "404";
            gprice=Integer.parseInt(listPrice.get(1));
            gmsisdn=Long.parseLong(listMsisdn.get(1));
            gyear=Integer.parseInt(listYear.get(1));
            gtelco=Integer.parseInt(listTelco.get(1));
            model.addAttribute("gprice",gprice);
            model.addAttribute("gmsisdn",gmsisdn);
            model.addAttribute("gyear",gyear);
            model.addAttribute("gtelco",gtelco);
        }catch (Exception e){
            return "404";
        }
        return "index.link";
    }

    @GetMapping("/captcha-count")
    public ResponseEntity<Integer> captchaCount(HttpServletRequest request) {
        Integer count = new Integer(1);
        if (request.getSession().getAttribute("captchaCount") != null) {
            count = Integer.valueOf(request.getSession().getAttribute("captchaCount").toString());
        }
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }

    @GetMapping("/sim/{linkDetail}")
    public String simDetail(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, @PathVariable("linkDetail") String linkDetail) {
        if (linkDetail == null) {
            return "404";
        }

        String[] linkDetails = linkDetail.split("-");
        Long id = 0L;

        try {
            id = Long.parseLong(linkDetails[linkDetails.length - 1]);
        } catch (Exception e) {
            return "404";
        }
        Object[] item = msisdnService.simDetailById(id).orElse(null);
        if (item == null) {
            return "404";
        }
        Long cusId = Long.valueOf(item[4].toString());
        PagingResult page = new PagingResult();
        page.setNumberPerPage(15);
        page.setPageNumber(pageNumber);
        try {
            page = msisdnService.searchDetail(page, cusId).orElse(new PagingResult());
        } catch (Exception e) {
            logger.error("have an error method simDetail:" + e.getMessage());
        }
        model.addAttribute("cusId", cusId);
        model.addAttribute("id", id);
        model.addAttribute("item", item);
        model.addAttribute("page", page);
        return "sim.detail";
    }

    @GetMapping("/detailsim")
    public ResponseEntity<Object[]> simPopupDetail(Model model, @RequestParam(value = "msisdnId", required = false, defaultValue = "0") Long msisdnId) {
        Object[] item = msisdnService.simDetailById(msisdnId).orElse(null);
//        model.addAttribute("item", item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/customer-list/{id}")
    public String customerListSim(Model model, @PathVariable("id") Long id) {
        if (id == null) {
            return "404";
        }
        Customer item = customerService.get(id).orElse(null);
        if (item == null) {
            return "404";
        }
        model.addAttribute("customerId", id);
        model.addAttribute("item", item);
        return "customer.sims";
    }

    @GetMapping("/customer-list/search")
    public ResponseEntity<ResultSearchIndex> customerListSearch(@RequestParam Long id, @RequestParam String search,
            @RequestParam(name = "p", defaultValue = "1") int pageNumber, HttpServletRequest request, String captcha) {
        ResultSearchIndex result = new ResultSearchIndex();
        if (id == null) {
            return new ResponseEntity<ResultSearchIndex>(result, HttpStatus.OK);
        }
        int count = getAndSetCaptchaCount(request);
        if (count > 50) {
            if (!checkCaptcha(request, captcha)) {
                result.setErrorCaptcha(true);
                return new ResponseEntity<ResultSearchIndex>(result, HttpStatus.OK);
            }
        }

        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SearchIndex searchIndex = new SearchIndex();
        try {
            searchIndex = objectMapper.readValue(search, SearchIndex.class);
            page = msisdnService.searchIndex(page, id, searchIndex).orElse(new PagingResult());
            result.setPage(page);
            /*refresh*/
            refreshCaptcha(request);
        } catch (Exception e) {
            logger.error("have an error method IndexController.search():" + e.getMessage());
            return new ResponseEntity<ResultSearchIndex>(new ResultSearchIndex(), HttpStatus.OK);
        }
        return new ResponseEntity<ResultSearchIndex>(result, HttpStatus.OK);
    }

    /*Begin Tin tuc, gioi thieu, huong dan, lien he*/
    @GetMapping("/tin-tuc/list")
    public ResponseEntity<PagingResult> listNewsIndex(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        page.setNumberPerPage(2);
        try {
            page = articleService.listByCate(page, Integer.valueOf(6050)).orElse(new PagingResult());
        } catch (Exception e) {
            logger.error("Have an error on method listNewsIndex:" + e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/tin-tuc")
    public String listNews(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        page.setNumberPerPage(6);
        try {
            page = articleService.listByCate(page, Integer.valueOf(6050)).orElse(new PagingResult());
        } catch (Exception e) {
            logger.error("Have an error on method listNews:" + e.getMessage());
        }
        model.addAttribute("page", page);
        model.addAttribute("urlImage", imageFolderGet);
        seosupport(model, "tin-tuc");
        return "news.list";
    }

    @GetMapping("/tin-tuc/{formaturl}")
    public String newsDetail(Model model, @PathVariable("formaturl") String formaturl) {
        String[] arrString = formaturl.split("-");
        int id = 0;
        try {
            id = Integer.parseInt(arrString[arrString.length - 1]);
        } catch (NumberFormatException e) {
            return "redirect:/tin-tuc.html";
        }
        model.addAttribute("urlImage", imageFolderGet);
        PagingResult page = new PagingResult();
        page.setPageNumber(1);
        page.setNumberPerPage(4);
        Article item = null;
        try {
            item = articleService.getArticleOfCate(id, 6050).orElse(null);
            page = articleService.listByCate(page, Integer.valueOf(6050)).orElse(new PagingResult());
        } catch (Exception e) {
            logger.error("Have an error on method newsDetail:" + e.getMessage());
        }
        if (item == null) {
            return "redirect:/tin-tuc.html";
        }
        model.addAttribute("item", item);
        model.addAttribute(PAGE_META_TITLE_PARAM, item.getMetaTitle());
        model.addAttribute(PAGE_META_DESCRIPTION_PARAM, item.getMetaDescription());
        //model.addAttribute(PAGE_META_URL_PARAM, news.getme());
        model.addAttribute(PAGE_META_IMAGE_PARAM, item.getImageUrl());
        if (page.getItems().size() > 0) {
            int count = 0;
            if (page.getItems().size() == 4) {
                for (int i = 0; i < 4; i++) {
                    Object[] itemT = (Object[]) page.getItems().get(i);
                    if (Integer.valueOf(itemT[0].toString()).intValue() == id) {
                        page.getItems().remove(itemT);
                        count++;
                        break;
                    }
                }
                if (count == 0) {
                    page.getItems().remove(page.getItems().size() - 1);
                }
            }

            model.addAttribute("page", page);

        }
        return "news.detail";
    }

    @GetMapping("/gioi-thieu")
    public String Introduce(Model model) {
        Article item = null;
        try {
            item = articleService.getFirstArticleOfCategory(Integer.valueOf(6250)).orElse(null);
        } catch (Exception e) {
            logger.error("Have an error on method Introduce:" + e.getMessage());
        }
        if (item == null) {
            return "404";
        }
        model.addAttribute("item", item);
        seosupport(model, "gioi-thieu");
        return "introduce";
    }

    @GetMapping("/huong-dan")
    public String guide(Model model) {
        List<Article> guides = null;
        List<Article> questions = null;
        try {
            guides = articleService.listAllOfCate(Integer.valueOf(6350)).orElse(null);
            questions = articleService.listAllOfCate(Integer.valueOf(9040)).orElse(null);
        } catch (Exception e) {
            logger.error("Have an error on method guide:" + e.getMessage());
        }

//        for (Article guide : guides) {
//            guide.setFormaturl(seoUrl(guide));
//        }
//        for (Article question : questions) {
//            question.setFormaturl(seoUrl(question));
//        }

        model.addAttribute("guides", guides);
        model.addAttribute("questions", questions);
        seosupport(model, "huong-dan");
        return "guide";
    }

    @GetMapping("/huong-dan/{formaturl}")
    public String guideDetail(Model model, @PathVariable("formaturl") String formaturl) {
        String[] arrString = formaturl.split("-");
        int id = 0;
        try {
            id = Integer.parseInt(arrString[arrString.length - 1]);
        } catch (NumberFormatException e) {
            return "redirect:/huong-dan.html";
        }
        Article item = null;
        try {
            item = articleService.getArticleOfCate(id, 6350).orElse(null);
        } catch (Exception e) {
            logger.error("Have an error on method guideDetail:" + e.getMessage());
        }
        if (item == null) {
            return "redirect:/huong-dan.html";
        }
//        item.setFormaturl(seoUrl(item));

        model.addAttribute("item", item);
        model.addAttribute(PAGE_META_TITLE_PARAM, item.getMetaTitle());
        model.addAttribute(PAGE_META_DESCRIPTION_PARAM, item.getMetaDescription());
        //model.addAttribute(PAGE_META_URL_PARAM, news.getme());
        model.addAttribute(PAGE_META_IMAGE_PARAM, item.getImageUrl());
        return "guide.detail";
    }

    @GetMapping("/lien-he")
    public String contact(Model model) {
//        Article item=null;
//        try{
//            item=articleService.getFirstArticleOfCategory(Integer.valueOf(6250)).orElse(null);
//        }catch (Exception e){
//            logger.error("Have an error on method Introduce:"+e.getMessage());
//        }
//        if(item==null) return "404";
//        model.addAttribute("item",item);
        seosupport(model, "lien-he");
        return "contact";
    }

    @GetMapping("/register")
    public ModelAndView Register() {
        ModelAndView model = new ModelAndView();
        model.setViewName("register");
        model.addObject("userRegister", new Customer());
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public @ResponseBody
    String register(Customer model,
            @ModelAttribute(value = "userRegister") Customer user, HttpServletRequest request
    ) {
        String result = "";
        if (user.getMsisdn() != null) {
            if (user.getMsisdn().startsWith("84")) {
                user.setMsisdn("0" + user.getMsisdn().substring(2));
            } else if (user.getMsisdn().startsWith("0")) {
                user.setMsisdn(user.getMsisdn());
            } else {
                user.setMsisdn("0" + user.getMsisdn());
            }
        }
        try {
            String captcha = request.getParameter("captcha");
            if (checkCaptcha(request, captcha)) {
                String confirmPassword = request.getParameter("confirmPassword");
                if (request.getSession().getAttribute("user_info") != null) {
                    result = "alreadyLogin";
                } else if (!checkRequired(user, "register") || confirmPassword.isEmpty()) {
                    result = "requiredFail";
                } else if (!Utils.checkRegex(user.getUsername(), Constant.REGEX_TEXT_NUMBER) || user.getUsername().length() < 5 || user.getUsername().length() > 20) {
                    result = "userNameSystax";
                } else if (!Utils.checkRegex(user.getMsisdn(), Constant.REGEX_NUMBER) || user.getMsisdn().length() < 10 || user.getMsisdn().length() > 20) {
                    result = "msisdnSystax";
                } else if (user.getEmail() != null && !Utils.checkRegex(user.getEmail(), Constant.REGEX_EMAIL)) {
                    result = "emailSystax";
//                } else if (user.getBirthday() != null && !Utils.checkRegex(user.getStr_birthday(), Constant.REGEX_DATE)) {
//                    result = "birthDaySystax";
                } else if (!confirmPassword.equals(user.getPassword())) {
                    result = "confirmPassSystax";
                } else if (customerService.isBlackList(user.getUsername())) {
                    result = "isBlackList";
                } else if (customerService.isExits(user.getMsisdn(), "msisdn")) {
                    result = "msisdnAlready";
                } else if (customerService.isExits(user.getUsername(), "username")) {
                    result = "userNameAlready";
                } else if (customerService.insertCustomer(user)) {
                    result = "ok";
                } else {
                    result = "registerFail";
                }
            } else {
                result = "captcha";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result = "error";
        }
        return result;
    }

    private boolean checkRequired(Customer user, String func) {
        boolean result = false;
        try {
            if (func.equals("register")) {
                if (user.getMsisdn() == null || user.getMsisdn().isEmpty()) {
                    result = false;
                } else if (user.getPassword() == null || user.getPassword().isEmpty()) {
                    result = false;
                } else if (user.getFull_name() == null || user.getFull_name().isEmpty()) {
                    result = false;
                } else {
                    result = true;
                }
            } else if (func.equals("edit")) {
                if (user.getFull_name() == null || user.getFull_name().isEmpty()) {
                    result = false;
                } else {
                    result = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /*End Tin tuc, gioi thieu, huong dan, lien he*/
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    @ResponseBody
    public String captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        generateToken(request.getSession());
        HttpSession session = request.getSession(false);
        String token = session != null ? getToken(session) : null;
        if (token == null || isTokenUsed(session)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Captcha not found.");
            System.out.println("Captcha not found.");
        }
        setResponseHeaders(response);
        markTokenUsed(session, true);
        cage.draw(token, response.getOutputStream());
        return "forward:/Captcha";
    }

    public static void generateToken(HttpSession session) {
        String token = Utils.randomCode(3);
        session.setAttribute("captchaToken", token);
        markTokenUsed(session, false);
    }

    public static String getToken(HttpSession session) {
        Object val = session.getAttribute("captchaToken");
        return val != null ? val.toString() : null;
    }

    protected static void markTokenUsed(HttpSession session, boolean used) {
        session.setAttribute("captchaTokenUsed", used);
    }

    protected static boolean isTokenUsed(HttpSession session) {
        return !Boolean.FALSE.equals(session.getAttribute("captchaTokenUsed"));
    }

    protected void setResponseHeaders(HttpServletResponse resp) {
        resp.setContentType("image/" + cage.getFormat());
        resp.setHeader("Cache-Control", "no-cache, no-store");
        resp.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        resp.setDateHeader("Last-Modified", time);
        resp.setDateHeader("Date", time);
        resp.setDateHeader("Expires", time);
    }

    private boolean checkCaptcha(HttpServletRequest request, String input) {
        boolean result = false;
        if (StringUtils.isNotBlank(input)) {
            if (request.getSession().getAttribute("captchaToken") == null || input.equals(request.getSession().getAttribute("captchaToken").toString())) {
                result = true;
            }
        }
        return result;
    }

    private int getAndSetCaptchaCount(HttpServletRequest request) {
        if (request.getSession().getAttribute("captchaCount") == null) {
            request.getSession().setAttribute("captchaCount", 0);
        }
        Integer count = Integer.valueOf(request.getSession().getAttribute("captchaCount").toString());
        if (count != null) {
            request.getSession().setAttribute("captchaCount", count.intValue() + 1);
        }
        return count.intValue();
    }

    private void refreshCaptcha(HttpServletRequest request) throws IOException {
        generateToken(request.getSession());
        HttpSession session = request.getSession(false);
        markTokenUsed(session, true);
    }

    public void seosupport(Model model, String url) {
        if (url.indexOf("tin-tuc") != -1) {
            model.addAttribute("meta_title", "Tin tức khuyến mãi - tin công nghệ sim số mới nhất");
            model.addAttribute("meta_description", "sansim.vn cập nhật thường xuyên các tin tức về sim ,tin tức khuyến mãi, tin tức công nghệ của các mạng Viettel, Mobifone, Vinaphone, VietNam Mobile, GMobile. ");
            model.addAttribute("meta_keyword", "mua sim số đẹp,bán sim mobifone,sim mobifone khuyen mai,sim mobifone trả trước,sim so dep mobifone 10 so");
        } else if (url.indexOf("huong-dan") != -1) {
            model.addAttribute("meta_title", "Hướng dẫn cách sử dụng sàn sim, mua bán sim số đẹp");
            model.addAttribute("meta_description", "Đội ngũ chăm sóc khách hàng của chúng tôi luôn sẵn sàng hỗ trợ tư vấn mọi thắc mắc cho các bạn về sim số.");
            model.addAttribute("meta_keyword", "sim trả sau số đẹp,mua sim 10 số giá rẻ,sim số đẹp trả sau,sim số đẹp,trả sau giá rẻ,sim the");
        } else if (url.indexOf("lien-he") != -1) {
            model.addAttribute("meta_title", "Liên hệ");
            model.addAttribute("meta_description", "Sàn sim số - Môi trường lí tưởng cho người dùng sim số muốn mua, bán hoặc trao đổi quyền sử dụng một cách dễ dàng.");
            model.addAttribute("meta_keyword", "sim so dep, san sim so, sim mobifone dep, sim viettel dep, so vinaphone dep");
        } else if (url.indexOf("gioi-thieu") != -1) {
            model.addAttribute("meta_title", "Giới thiệu về chúng tôi");
            model.addAttribute("meta_description", "Dịch vụ sàn sim - đơn vị cung cấp sàn sim số đẹp hàng đầu Việt Nam hiện nay .");
            model.addAttribute("meta_keyword", "sim số đẹp giá rẻ,sim so dep,sim số ,sim trả sau, sim trả trước");
        }
    }
}
