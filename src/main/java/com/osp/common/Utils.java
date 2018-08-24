package com.osp.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Utils {

    //Tranfer object to object
    public static Object objTransfer(Object objFrom, Object objTo, Class cls) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(cls);
        for (PropertyDescriptor propertyDesc : beanInfo
                .getPropertyDescriptors()) {
            Object value = propertyDesc.getReadMethod().invoke(objFrom);
            if (value == null) {
                propertyDesc.getWriteMethod().invoke(objFrom,
                        propertyDesc.getReadMethod().invoke(objTo));
            }
        }
        objTo = objFrom;
        return objTo;
    }

    //Check Regular expression
    public static boolean checkRegex(String input, String exp) {
        boolean result = false;
        if (!input.isEmpty()) {
            try {
                Pattern pattern;
                pattern = Pattern.compile(exp);
                Matcher matcher;
                matcher = pattern.matcher(input);
                result = matcher.matches();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            result = true;
        }
        return result;
    }

    //Convert String to Date
    public static Date str2date(String input, String format) throws java.text.ParseException {
        Date result = null;
        if (!input.isEmpty()) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                result = formatter.parse(input);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    //Convert Date to String
    public static String date2str(Date input, String oFormat) {
        String result = "";
        if (input != null) {
            try {
                DateFormat df = new SimpleDateFormat(oFormat);
                result = df.format(input);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    //Add Date
    public static Date addDate(Date date, int type, int input) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(type, input);
        date = c.getTime();
        return date;
    }

    //Generate random code by DateTime
    public static String createRandomCode(String prefix) {
        String result = "";
        Date date = new Date();
        String time_code = date2str(date, "yyMM");
        String num_code = randomNumber(4);
        result = prefix + time_code + num_code;
        return result;
    }

    //Generate random number
    public static String randomNumber(int length) {
        String result = "";
        try {
            String pattern = "0123456789";
            for (int i = 0; i < length; i++) {
                Random rand = new Random();
                int pos = rand.nextInt((9 - 0) + 1) + 0;
                result += pattern.charAt(pos);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    // Generate random number
    public static String randomCode(int length) {
        String result = "";
        try {
            String pattern = "123456789qwertyupasdfghjkzxcvbnm";
            for (int i = 0; i < length; i++) {
                Random rand = new Random();
                int pos = rand.nextInt((31 - 0) + 1) + 0;
                result += pattern.charAt(pos);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    //Convert to UTF-8
    public static String toUtf8(String input) {
        String result = "";
        try {
            byte ptext[] = input.getBytes("ISO-8859-1");
            result = new String(ptext, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String md5(String input) throws NoSuchAlgorithmException {
        String result = "";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        result = hexString.toString();
        return result;
    }

    public static String Encrypt(String key, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("TripleDES");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(key.getBytes(), 0, key.length());
        String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(
                0, 24);
        SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(),
                "TripleDES");
        cipher.init(Cipher.ENCRYPT_MODE, keyspec);
        byte[] stringBytes = data.getBytes();
        byte[] raw = cipher.doFinal(stringBytes);
        BASE64Encoder encoder = new BASE64Encoder();
        String base64 = encoder.encode(raw);
        return base64;
    }

    public static String Decrypt(String key, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("TripleDES");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(key.getBytes(), 0, key.length());
        String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(
                0, 24);
        SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(),
                "TripleDES");
        cipher.init(Cipher.DECRYPT_MODE, keyspec);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] raw = decoder.decodeBuffer(data);
        byte[] stringBytes = cipher.doFinal(raw);
        String result = new String(stringBytes);
        return result;
    }

    public static String getIpClient(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

}
