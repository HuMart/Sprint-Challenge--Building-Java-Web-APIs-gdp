package com.lambda.countries.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class GDP
{
    private static final AtomicLong counter = new AtomicLong();
    private long id;
    private String cname;
    private long gdp;


    public GDP(String cname, String gdp)
    {
        this.id = counter.incrementAndGet();
        this.cname = cname;
        this.gdp = Long.parseLong(gdp);

    }

    public GDP(String cname, long gdp)
    {
        this.id = counter.incrementAndGet();
        this.cname = cname;
        this.gdp = gdp;

    }

    public GDP(GDP toClone)
    {
        this.id = toClone.getId();
        this.cname = toClone.getCname();
        this.gdp = toClone.getGdp();
    }

    public long getId()
    {
        return id;
    }

    public String getCname()
    {
        return cname;
    }

    public void setCname(String cname)
    {
        this.cname = cname;
    }

    public long getGdp()
    {
        return gdp;
    }

    public void setGdp(long gdp)
    {
        this.gdp = gdp;
    }

    @Override
    public String toString()
    {
        return "GDP{" + "id=" + id + ", cname='" + cname + '\'' + ", gdp=" + gdp + '}';
    }
}
