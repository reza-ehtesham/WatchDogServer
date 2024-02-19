package com.acm.ehtesham.controller;

import com.acm.ehtesham.entity.IHeartBeat;
import com.acm.ehtesham.entity.KPIFacade;
import com.acm.ehtesham.entity.KeyPerfomanceIndexEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Properties;

/**
 * Created by ehtesham on 05/03/2017.
 */
public class KPIFacadeImpl implements KPIFacade {
    private EntityManager em;

    public KPIFacadeImpl() {
        Properties props = new Properties();
        props.put("eclipselink.persistencexml", "com/acm/ehtesham/META-INF/persistence.xml");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPA", props);
        this.em = emf.createEntityManager();
    }

    /**
     * Save Key Performance Index On Database
     * @param kpiEntity
     */
    public void saveKPI(KeyPerfomanceIndexEntity kpiEntity) {
        em.getTransaction().begin();
        em.persist(kpiEntity);
        em.getTransaction().commit();
    }

    /**
     * Check Client Information With Key Performance Index
     * @param heartBeat
     * @return
     */
    public synchronized String checkKPI(IHeartBeat heartBeat) {
        Query query = em.createNativeQuery("SELECT * FROM key_performance_index WHERE" +
                " id=(SELECT MAX(id) FROM key_performance_index)", KeyPerfomanceIndexEntity.class);
        KeyPerfomanceIndexEntity kpiEntity = (KeyPerfomanceIndexEntity) query.getSingleResult();
        if (heartBeat.getTotalMemory() > kpiEntity.getTotalMemory())
            if (heartBeat.getFreeMemory() > kpiEntity.getFreeMemory())
                if (heartBeat.getProcessoresCount() > kpiEntity.getProcessorsCount())
                    if (heartBeat.getSystemCpuLoad() > kpiEntity.getSystemCpuLoad())
                        return "Trust";
        return "Critical";
    }
}
