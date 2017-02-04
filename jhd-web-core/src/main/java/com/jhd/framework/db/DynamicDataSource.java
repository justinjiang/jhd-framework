/**
 * Copyright (c) 2016 Jinbanshou,Inc.All Rights Reserved.
 * <p>
 * 上海金扳手信息科技股份有限公司
 */
package com.jhd.framework.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DynamicDataSource
 *
 * @author justin.jiang
 * @date 2016/12/25
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final Log log = LogFactory.getLog(getClass());

    private AtomicInteger counter = new AtomicInteger();

    private DataSource master; //写数据源

    private List<DataSource> slaves; //读数据源

    @Override
    public void afterPropertiesSet() {

    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    @Override
    protected DataSource determineTargetDataSource() {
        DataSource returnDataSource = null;
        if(DynamicDataSourceHolder.isMaster()){
            returnDataSource = master;
        }else if(DynamicDataSourceHolder.isSlave()){
            int count = counter.incrementAndGet();
            if(count>1000000){
                counter.set(0);
            }
            int n = slaves.size();
            int index = count%n;
            returnDataSource = slaves.get(index);
        }else{
            returnDataSource = master;
        }
//        if(returnDataSource instanceof ComboPooledDataSource){
//            ComboPooledDataSource source = (ComboPooledDataSource)returnDataSource;
//            String jdbcUrl = source.getJdbcUrl();
//            log.debug("jdbcUrl:" + jdbcUrl);
//        }
        if(returnDataSource instanceof BasicDataSource){
            BasicDataSource source = (BasicDataSource)returnDataSource;
            String jdbcUrl = source.getUrl();
            log.debug("jdbcUrl:" + jdbcUrl);
        }
        return returnDataSource;
    }

    public void setMaster(DataSource master) {
        this.master = master;
    }

    public DataSource getMaster() {
        return master;
    }

    public List<DataSource> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<DataSource> slaves) {
        this.slaves = slaves;
    }
}
