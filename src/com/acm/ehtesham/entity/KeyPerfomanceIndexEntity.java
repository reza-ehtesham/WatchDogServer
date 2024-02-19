package com.acm.ehtesham.entity;

import javax.persistence.*;

/**
 * Created by ehtesham on 6/28/2017.
 */
@Entity
@Table(name = "key_performance_index")
public class KeyPerfomanceIndexEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_memory")
    private Long totalMemory;

    @Column(name = "free_memory")
    private Long freeMemory;

    @Column(name = "system_cpu_load")
    private Double systemCpuLoad;

    @Column(name = "processors_count")
    private Integer processorsCount;

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

    public Integer getProcessorsCount() {
        return processorsCount;
    }

    public void setProcessorsCount(Integer processorsCount) {
        this.processorsCount = processorsCount;
    }
}
