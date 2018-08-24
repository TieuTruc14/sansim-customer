/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.common;

import com.osp.chonso.common.RequestInfo;
import com.osp.chonso.common.ResponseInfo;
import com.osp.chonso.server.MsGateInterface;
import com.osp.model.DwMsisdn;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class RmiUtils {

//    private static final String HOST = "192.168.1.16";
    private static final String HOST = "192.168.1.111";
    private static final int PORT = 1099;
    private static Registry registry = null;
    private int state = -1;
//    public List<DwGroupMsisdn> groupMsisdns = new ArrayList<DwGroupMsisdn>();
//    public HashMap<String, DwGroupMsisdn> hashGroupMsisdns = new HashMap<>();
//    public HashMap<String, String> hashParam = new HashMap<>();
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Logger logger = Logger.getLogger(RmiUtils.class.getName());

    private void connectToRmi() throws Exception {
        if (state != 1) {
            registry = null;
//            System.setProperty("java.rmi.server.hostname", "192.168.1.16");
            registry = LocateRegistry.getRegistry(HOST, PORT);
            logger.info("Connected to " + HOST + " : " + PORT);
            state = 1;
        }

    }

    private void disconnect() {
        state = 0;
        registry = null;
    }

    public ResponseInfo sendRequest(RequestInfo requestInfo) {

        ResponseInfo responseInfo = new ResponseInfo();
        try {
            connectToRmi();
            MsGateInterface gate = (MsGateInterface) registry.lookup("MsGate");
            responseInfo = gate.sendRequest(requestInfo);
            logger.info("sendRequest with command : " + requestInfo.getCommand() + ", param: " + requestInfo.getParam());
        } catch (Exception e) {
            logger.info("sendRequest fail: " + e.getMessage());
        }
        return responseInfo;
    }

    public DwMsisdn getDwMsisdnInfo(String msisdn) {
        DwMsisdn num_detail = null;
        RequestInfo rq = new RequestInfo();
        rq.setCommand("GET_MSISDN_DETAIL");
        String requestParamTotal[] = {msisdn};
        rq.setParam(requestParamTotal);
        ResponseInfo riDwMsisdn = sendRequest(rq);

        if (riDwMsisdn != null && riDwMsisdn.getResponseCode() == 0) {
            if (riDwMsisdn.getContent() != null) {
                String[] strings = (String[]) riDwMsisdn.getContent();
                    num_detail = new DwMsisdn(msisdn,new Short(strings[1]));
            }
        }
        return num_detail;
    }

}
