package com.osp.security;

import com.osp.model.Customer;
import com.osp.web.service.customer.LogAccessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * Created by Admin on 1/8/2018.
 */
@Component
public class SessionEndedListener implements ApplicationListener<SessionDestroyedEvent> {
    @Autowired
    LogAccessService logAccessService;
    private Logger logger= LogManager.getLogger(SessionEndedListener.class);

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
        for (SecurityContext securityContext : event.getSecurityContexts())
        {
            try{
                Authentication authentication = securityContext.getAuthentication();
                Customer customer = (Customer) authentication.getPrincipal();
                WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
                String remoteIpAddress = webAuthenticationDetails.getRemoteAddress();
                logAccessService.addLogWithUserId(customer.getId(),"Đăng xuất sessionTimeout","Hệ thống",remoteIpAddress);
            }catch (Exception e){
                logger.error("Have an error when end session Timeout:"+e.getMessage());
            }

        }
    }
}
