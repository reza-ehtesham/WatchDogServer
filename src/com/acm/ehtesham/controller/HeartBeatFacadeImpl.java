package com.acm.ehtesham.controller;

import com.acm.ehtesham.entity.HeartBeatEntity;
import com.acm.ehtesham.entity.IHeartBeat;
import com.acm.ehtesham.util.HeartBeatFacade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

/**
 * Created by Euphoria on 6/27/2017.
 */
public class HeartBeatFacadeImpl implements HeartBeatFacade {
    private EntityManager em;

    public HeartBeatFacadeImpl() {
        Properties props = new Properties();
        props.put("eclipselink.persistencexml", "com/acm/ehtesham/META-INF/persistence.xml");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPA", props);
        this.em = emf.createEntityManager();
    }

    /**
     * Save Heart Beat Object On Database
     * @param heartBeat
     * @param ip
     */
    public synchronized void saveHeartBeat(IHeartBeat heartBeat, String ip) {
        HeartBeatEntity heartBeatEntity = translateXmlToEntity(heartBeat, ip);
        em.getTransaction().begin();
        em.persist(heartBeatEntity);
        em.getTransaction().commit();
    }

    /**
     * Translate Xml To Entity
     * @param heartBeat
     * @param ip
     * @return
     */
    public synchronized HeartBeatEntity translateXmlToEntity(IHeartBeat heartBeat, String ip) {
        HeartBeatEntity heartBeatEntity = new HeartBeatEntity();
        heartBeatEntity.setTotalMemory(heartBeat.getTotalMemory());
        heartBeatEntity.setFreeMemory(heartBeat.getFreeMemory());
        heartBeatEntity.setProcessName(heartBeat.getProcessName());
        heartBeatEntity.setSystemCpuLoad(heartBeat.getSystemCpuLoad());
        heartBeatEntity.setProcessorsCount(heartBeat.getProcessoresCount());
        heartBeatEntity.setTime(heartBeat.getTime());
        heartBeatEntity.setAlive(heartBeat.isAlive());
        heartBeatEntity.setClientIp(ip);
        return heartBeatEntity;
    }
}
