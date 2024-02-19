package com.acm.ehtesham.entity;

/**
 * Created by ehtesham on 6/23/2017.
 */
public interface IHeartBeat {
    String time = null;
    long totalMemory = 0;
    long freeMemory = 0;
    double systemCpuLoad = 0;
    String processName = null;
    boolean isAlive = false;
    int processoresCount = 0;

    public String getTime();

    public void setTime(String time);

    public long getTotalMemory();

    public void setTotalMemory(long totalMemory);

    public long getFreeMemory();

    public void setFreeMemory(long freeMemory);

    public double getSystemCpuLoad();

    public void setSystemCpuLoad(double systemCpuLoad);

    public String getProcessName();

    public void setProcessName(String processName);

    public boolean isAlive();

    public void setAlive(boolean alive);

    public int getProcessoresCount();

    public void setProcessoresCount(int processoresCount);
}
