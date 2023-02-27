package com.softeem.util;

import org.apache.commons.dbutils.*;

/**
 * BaseDao的目的就是去优化dao实现类
 * @param <T>
 */
public abstract class BaseDao<T> {
    public int pageSize = 4;
    public QueryRunner queryRunner;

    public BaseDao() {
        queryRunner = new QueryRunner(MyDataSource.getDataSource());
    }

    public RowProcessor getProcessor(){
        //开启下划线->驼峰转换所用 - strat
        BeanProcessor bean = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(bean);
        //开启下划线->驼峰转换所用 - end
        return processor;
    }

}
