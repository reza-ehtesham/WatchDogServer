package com.acm.ehtesham.entity;

/**
 * Created by ehtesham on 8/10/2017.
 */
public interface KPIFacade {

    void saveKPI(KeyPerfomanceIndexEntity kpiEntity);

    String checkKPI(IHeartBeat heartBeat);
}
