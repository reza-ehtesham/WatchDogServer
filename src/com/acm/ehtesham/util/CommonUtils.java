package com.acm.ehtesham.util;

import com.acm.ehtesham.controller.HeartBeatFacadeImpl;
import com.acm.ehtesham.controller.WatchDogServer;
import com.acm.ehtesham.entity.HeartBeat;
import com.acm.ehtesham.entity.IHeartBeat;
import com.acm.ehtesham.view.WatchDogMainForm;
import com.sun.istack.internal.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Created by ehtesham on 03/12/2017.
 */
public class CommonUtils {
    public static final Logger logger = Logger.getLogger(WatchDogServer.class);

    public static String getProperty(String keyProperty) throws Exception {
        Properties configProperties = new Properties();
        configProperties.load(new FileInputStream("NetworkConfig.properties"));
        return configProperties.getProperty(keyProperty);
    }

    public static void setProperty(String keyProperty, String valueProperty) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream("NetworkConfig.properties"));
        props.setProperty(keyProperty, valueProperty);
        props.store(new FileOutputStream("NetworkConfig.properties"), null);
    }

    /**
     * Unmarshaling XML & Send Object On Heart Beat FAcade
     * @param inputStream
     * @param clientIp
     * @throws FileNotFoundException
     */
    public synchronized static void unmarshalling(InputStream inputStream, String clientIp) throws FileNotFoundException {
        IHeartBeat heartBeat;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(HeartBeat.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            heartBeat = (HeartBeat) unmarshaller.unmarshal(inputStream);
            WatchDogMainForm.receiveDataClient(heartBeat, clientIp);
            logger.log(Level.INFO, "Save Heart Beat Entity");
            HeartBeatFacade facade = new HeartBeatFacadeImpl();
            facade.saveHeartBeat(heartBeat, clientIp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

