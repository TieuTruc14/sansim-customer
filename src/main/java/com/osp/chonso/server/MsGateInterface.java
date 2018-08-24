package com.osp.chonso.server;

import com.osp.chonso.common.RequestInfo;
import com.osp.chonso.common.ResponseInfo;
import java.rmi.*;

public interface MsGateInterface extends Remote {

    public ResponseInfo sendRequest(RequestInfo request) throws RemoteException;
}
