package com.doodl6.springboot.leaf.segment;

import com.alibaba.druid.pool.DruidDataSource;
import com.doodl6.springboot.leaf.Constants;
import com.doodl6.springboot.leaf.IDGen;
import com.doodl6.springboot.leaf.common.PropertyFactory;
import com.doodl6.springboot.leaf.common.Result;
import com.doodl6.springboot.leaf.common.ZeroIDGen;
import com.doodl6.springboot.leaf.exception.InitException;
import com.doodl6.springboot.leaf.segment.dao.IDAllocDao;
import com.doodl6.springboot.leaf.segment.dao.impl.IDAllocDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Properties;

/**
 * 段模式获取ID
 */
@Slf4j
@Service
public class SegmentService {

    private final IDGen idGen;

    public SegmentService() throws SQLException, InitException {
        Properties properties = PropertyFactory.getProperties();
        boolean flag = Boolean.parseBoolean(properties.getProperty(Constants.LEAF_SEGMENT_ENABLE, "true"));
        if (flag) {
            // Config dataSource
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(properties.getProperty(Constants.LEAF_JDBC_URL));
            dataSource.setUsername(properties.getProperty(Constants.LEAF_JDBC_USERNAME));
            dataSource.setPassword(properties.getProperty(Constants.LEAF_JDBC_PASSWORD));
            dataSource.init();

            // Config Dao
            IDAllocDao dao = new IDAllocDaoImpl(dataSource);

            // Config ID Gen
            idGen = new SegmentIDGenImpl();
            ((SegmentIDGenImpl) idGen).setDao(dao);
            if (idGen.init()) {
                log.info("Segment Service Init Successfully");
            } else {
                throw new InitException("Segment Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            log.info("Zero ID Gen Service Init Successfully");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }

    public SegmentIDGenImpl getIdGen() {
        if (idGen instanceof SegmentIDGenImpl) {
            return (SegmentIDGenImpl) idGen;
        }
        return null;
    }
}