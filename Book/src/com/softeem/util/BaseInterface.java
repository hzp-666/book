package com.softeem.util;

import com.softeem.bean.User;

import java.sql.SQLException;
import java.util.List;

/**
 * 优化Dao接口
 * @param <T>
 */
public interface BaseInterface<T> {
    /**
     *	根据用户名查询用户信息
     *	@param username 用户名
     *	@return 如果返回null,说明没有这个用户。反之亦然
     */
    public User queryUserByUsername(String username)throws SQLException;

    /**
     *	根据 用户名和密码查询用户信息
     *	@param username
     *	@param password
     *	@return 如果返回null,说明用户名或密码错误,反之亦然
     */
    public User queryUserByUsernameAndPassword(String username,String password)throws SQLException;



    /**
     * 查询表中所有数据
     * @return list集合
     */
    List<T> findALL() throws SQLException;

    /**
     * 添加数据
     * @param t
     */
    void save(T t) throws SQLException;

    /**
     * 根据主键更新数据
     * @param t
     */
    void updateById(T t) throws SQLException;

    /**
     * 根据主键删除
     * @param id
     */
    void deleteById(Integer id) throws SQLException;

    /**
     * 根据主键查找
     * @param id
     */
    T findById(Integer id) throws SQLException;

    /**
     * 无条件分页查询
     * @param pageNumber 页码
     * @return
     */
    List<T> page (Integer pageNumber) throws SQLException;

    /**
     * 求表中的记录数
     * @return
     */
    Integer pageRecord() throws SQLException;

}
