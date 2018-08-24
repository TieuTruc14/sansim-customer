package com.osp.shedule;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Created by Admin on 6/12/2018.
 */
@Component
public class CleanImageShareFB {
    @Autowired
    ServletContext servletContext;
    @Value("${image_sim_gen}")
    private String imageSimGenFolder;
    private Logger logger = LogManager.getLogger(CleanImageShareFB.class);

    @Scheduled(fixedDelay = 36000000)
    public void scheduleFixedDelayTaskClearImageShare() throws Exception {
        String path = servletContext.getRealPath(imageSimGenFolder);
        try{
            File file = new File(path);
            FileUtils.cleanDirectory(file);
        }catch (Exception e){
            logger.error("have error scheduleFixedDelayTaskClearImageShare: "+e.getMessage());
        }
    }
}
