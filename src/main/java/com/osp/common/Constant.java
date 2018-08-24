/**
 *
 */
package com.osp.common;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

/**
 * @author SONY
 *
 */
public class Constant {

    public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    //Danh sách biểu thức bất quy tắc
    public static final String REGEX_NUMBER = "^[0-9]*$";
    public static final String REGEX_NUMBER_ALIAS = "^[0-9\\.]*$";
    public static final String REGEX_SEARCH_NUMBER = "^[0-9*]*$";
    public static final String REGEX_TEXT_NUMBER = "^[a-zA-Z0-9]+$";
    public static final String REGEX_DATE = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateMsisdn(String msi) {
        if (msi.startsWith("0")) {
            if ((msi.startsWith("09") && msi.length() == 10) || (msi.startsWith("01") && msi.length() == 11) || (msi.startsWith("08") && msi.length() == 10)
                    || (msi.startsWith("03") && msi.length() == 10) || (msi.startsWith("07") && msi.length() == 10)) {
                return true;
            }
        } else if (msi.startsWith("84")) {
            if ((msi.startsWith("849") && msi.length() == 11) || (msi.startsWith("841") && msi.length() == 12) || (msi.startsWith("848") && msi.length() == 11)
                    || (msi.startsWith("843") && msi.length() == 11) || (msi.startsWith("847") && msi.length() == 11)) {
                return true;
            }
        } else {
            if ((msi.startsWith("9") && msi.length() == 9) || (msi.startsWith("1") && msi.length() == 10) || (msi.startsWith("8") && msi.length() == 9)
                    || (msi.startsWith("7") && msi.length() == 9) || (msi.startsWith("3") && msi.length() == 9)) {
                return true;
            }
        }
        return false;
    }

    public static String convertMsisdn(String msi) {

        String msisdn = msi.replace(" ", "").replace(".", "").trim();

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

    public static boolean validateMsisdn1(String msi) {
        if (msi.contains(" .")) {
            return false;
        }
        if (msi.contains(". ")) {
            return false;
        }
        return Utils.checkRegex(msi, REGEX_NUMBER_ALIAS);
    }

    public static String convertMsisdnAlias(String msi) {
        String msisdn = msi.replace("  ", " ").replace("..", ".").trim();

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

}
