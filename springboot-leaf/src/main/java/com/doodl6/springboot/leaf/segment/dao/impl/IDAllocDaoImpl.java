package com.doodl6.springboot.leaf.segment.dao.impl;

import com.doodl6.springboot.leaf.segment.dao.IDAllocDao;
import com.doodl6.springboot.leaf.segment.dao.IDAllocMapper;
import com.doodl6.springboot.leaf.segment.model.LeafAlloc;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.List;

public class IDAllocDaoImpl implements IDAllocDao {

    private final SqlSessionFactory sqlSessionFactory;

    private final String idAllocMapperName;

    public IDAllocDaoImpl(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(IDAllocMapper.class);
        idAllocMapperName = IDAllocMapper.class.getName();
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    @Override
    public List<LeafAlloc> getAllLeafAllocs() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(false)) {
            return sqlSession.selectList(idAllocMapperName + ".getAllLeafAllocs");
        }
    }

    @Override
    public LeafAlloc updateMaxIdAndGetLeafAlloc(String tag) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.update(idAllocMapperName + ".updateMaxId", tag);
            LeafAlloc result = sqlSession.selectOne(idAllocMapperName + ".getLeafAlloc", tag);
            sqlSession.commit();
            return result;
        }
    }

    @Override
    public LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.update(idAllocMapperName + ".updateMaxIdByCustomStep", leafAlloc);
            LeafAlloc result = sqlSession.selectOne(idAllocMapperName + ".getLeafAlloc", leafAlloc.getKey());
            sqlSession.commit();
            return result;
        }
    }

    @Override
    public List<String> getAllTags() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(false)) {
            return sqlSession.selectList(idAllocMapperName + ".getAllTags");
        }
    }
}