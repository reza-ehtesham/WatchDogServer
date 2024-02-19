package com.acm.ehtesham.util;

import com.acm.ehtesham.entity.HeartBeatEntity;
import com.acm.ehtesham.entity.IHeartBeat;

/**
 * Created by Euphoria on 8/10/2017.
 */
public interface HeartBeatFacade {

    void saveHeartBeat(IHeartBeat heartBeat, String ip);

    HeartBeatEntity translateXmlToEntity(IHeartBeat heartBeat, String ip);
}
