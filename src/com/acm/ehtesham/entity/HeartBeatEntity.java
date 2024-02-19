package com.acm.ehtesham.entity;

import javax.persistence.*;

/**
 * Created by ehtesham on 2016/12/18.
 */
@Entity
@Table(name = "heart_beat")
public class HeartBeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time")
    private String time;

    @Column(name = "total_memory")
    private Long totalMemory;

    @Column(name = "free_memory")
    private Long freeMemory;

    @Column(name = "system_cpu_load")
    private Double systemCpuLoad;

    @Column(name = "process_name")
    private String processName;

    @Column(name = "is_alive")
    private Boolean isAlive;

    @Column(name = "processors_count")
    private Integer processorsCount;

    @Column(name = "client_ip")
    private String clientIp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public Long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(Long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public Double getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public void setSystemCpuLoad(Double systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public Integer getProcessorsCount() {
        return processorsCount;
    }

    public void setProcessorsCount(Integer processorsCount) {
        this.processorsCount = processorsCount;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
