package com.dazzlzy.springbootseed.utils;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbc工具类，可直接执行sql
 *
 * @author dazzlzy
 * @date 2018/4/11
 */
@Slf4j
public class JdbcUtil {

    private static JdbcUtil jdbcUtil;

    private static DruidDataSource dataSource = new DruidDataSource();

    private static ThreadLocal<Connection> pool = new ThreadLocal<>();

    private JdbcUtil(DruidDataSource dataSource) {
        JdbcUtil.dataSource = dataSource;
    }

    public static JdbcUtil initDataSource(DruidDataSource dataSource) {
        if (jdbcUtil != null) {
            return jdbcUtil;
        }
        jdbcUtil = new JdbcUtil(dataSource);
        return jdbcUtil;
    }

    /**
     * 获取数据连接
     *
     * @return 数据库连接
     */
    private static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            log.info(Thread.currentThread().getName() + "连接已经开启......");
            pool.set(conn);
        } catch (Exception e) {
            log.error("连接获取失败!", e);
        }
        return conn;
    }

    /**
     * 获取当前线程上的连接开启事务
     */
    private static void startTransaction() {
        //首先获取当前线程的连接
        Connection conn = pool.get();
        //如果连接为空，从连接池中获取连接,将此连接放在当前线程池上
        if (conn == null) {
            conn = getConnection();
            pool.set(conn);
            log.info(Thread.currentThread().getName() + "空连接从dataSource获取连接");
        } else {
            log.info(Thread.currentThread().getName() + "从缓存中获取连接");
        }
        try {
            //开启事务
            conn.setAutoCommit(false);
        } catch (Exception e) {
            log.error("开启事务异常!", e);
        }
    }

    /**
     * 提交事务
     */
    private static void commit() {
        try {
            //从当前线程上获取连接,如果连接为空，则不做处理
            Connection conn = pool.get();
            if (null != conn) {
                conn.commit();//提交事务
                log.info(Thread.currentThread().getName() + "事务已经提交......");
            }
        } catch (Exception e) {
            log.error("事务提交异常!", e);
        }
    }


    /**
     * 回滚事务
     */
    private static void rollback() {
        try {
            //检查当前线程是否存在连接
            Connection conn = pool.get();
            if (conn != null) {
                conn.rollback();//回滚事务
                pool.remove();//如果回滚了，就移除这个连接
            }
        } catch (Exception e) {
            log.error("事务回滚异常!", e);
        }
    }

    /**
     * 关闭连接
     */
    private static void close() {
        try {
            Connection conn = pool.get();
            if (conn != null) {
                conn.close();
                log.info(Thread.currentThread().getName() + "连接关闭");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                pool.remove();//从当前线程移除连接切记
            } catch (Exception e2) {
                log.error("移除数据连接异常！", e2);
            }
        }
    }

    /**
     * 执行更新sql
     *
     * @param sql    需要执行的sql
     * @param params sql参数
     * @return 是否成功更新
     */
    public boolean updateByParams(String sql, List params) {
        int result = -1;
        Connection connection = JdbcUtil.getConnection();
        try {
            JdbcUtil.startTransaction();
            PreparedStatement ps = connection.prepareStatement(sql);
            int index = 1;
            if (null != params && !params.isEmpty()) {
                for (Object param : params) {
                    ps.setObject(index++, param);
                }
            }
            result = ps.executeUpdate();
            JdbcUtil.commit();
            log.info("成功查询SQL 【SQL: {}】", sql);
            log.info("成功查询SQL 【param: {}】", params);
        } catch (SQLException se) {
            JdbcUtil.rollback();
            log.error("更新SQL异常 【SQL: {}】", sql);
            log.error("更新SQL异常 【param： {}】", params);
            log.error("更新SQL异常！", se);
        } finally {
            JdbcUtil.close();
        }
        return result > 0;
    }

    /**
     * 执行查询sql
     *
     * @param sql    需要执行的sql
     * @param params sql参数
     * @return 查询结果
     */
    public List<Map<String, Object>> selecByParams(String sql, List<?> params) {
        List<Map<String, Object>> list = new ArrayList<>();
        int index = 1;
        Connection connection = JdbcUtil.getConnection();
        try {
            JdbcUtil.startTransaction();
            PreparedStatement ps = connection.prepareStatement(sql);
            if (null != params && !params.isEmpty()) {
                for (Object param : params) {
                    ps.setObject(index++, param);
                }
            }
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int colsLen = metaData.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>(colsLen);
                for (int i = 0; i < colsLen; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    Object columnValue = rs.getObject(columnName);
                    if (null == columnValue) {
                        columnValue = "";
                    }
                    map.put(columnName, columnValue);
                }
                list.add(map);
            }
            JdbcUtil.commit();
            log.info("成功查询SQL 【SQL: {}】", sql);
            log.info("成功查询SQL 【param: {}】", params);
        } catch (SQLException se) {
            JdbcUtil.rollback();
            log.error("查询SQL异常 【SQL: {}】", sql);
            log.error("查询SQL异常 【param： {}】", params);
            log.error("查询SQL异常！", se);
        } finally {
            JdbcUtil.close();
        }
        return list;
    }

    /**
     * 初始化占位符?.
     *
     * @param size ?的个数
     * @return sql中?占位符
     */
    public static String initPlaceholder(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("?");
            if (i < size - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
