package com.fxyh.mybatis.interceptor;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by chenboge on 2017/5/14.
 * <p>
 * Email:baigegechen@gmail.com
 * <p>
 * description:插件分页
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class PageInterceptor implements Interceptor {

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    //    在配置插件的时候配置默认参数
    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = getActuralHandlerObject(invocation);
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        String sql = statementHandler.getBoundSql().getSql();
        if (!checkIsSelectFalg(sql)) {
            //检测未通过，不是select语句
            return invocation.proceed();
        }
        BoundSql boundSql = statementHandler.getBoundSql();
        Object paramObject = boundSql.getParameterObject();
        MyPage myPage = getMyPage(paramObject);
        if (myPage == null){
            return invocation.proceed();
        }
        //不使用分页功能
        if (!myPage.isUseFlag()) {
            return invocation.proceed();
        }
        Long total = new Long(getTotal(invocation, metaStatementHandler, boundSql));
        myPage.setTotal(total);
        //检查当前页码的有效性
        checkPage(myPage.isCheckFlag(), myPage.getPageNo(), myPage.getPages());
        //修改sql
        return updateSql2Limit(invocation, metaStatementHandler, myPage.getPageSize(), myPage.getPageStart());
    }

    /**
     * @Author fengzhaoquan
     * @Description 从代理对象中分离出真实statementHandler对象,非代理对象
     * @return
     **/
    private StatementHandler getActuralHandlerObject(Invocation invocation) {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        Object object = null;
        //分离代理对象链，目标可能被多个拦截器拦截，分离出最原始的目标类
        while (metaStatementHandler.hasGetter("h")) {
            object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }

        if (object == null) {
            return statementHandler;
        }
        return (StatementHandler) object;
    }

    /**
     * @Author fengzhaoquan
     * @Description 判断是否是select语句，只有select语句，才会用到分页
     * @return
     **/
    private boolean checkIsSelectFalg(String sql) {
        String trimSql = sql.trim();
        int index = trimSql.toLowerCase().indexOf("select");
        return index == 0;
    }

    /*
     * @Author fengzhaoquan
     * @Description 获取分页的参数参数可以通过map，@param注解进行参数传递。或者请求pojo继承自MyPage  将MyPage中的分页数据放进去
     * @return
     **/
    private MyPage getMyPage(Object paramerObject) {
        if (paramerObject == null) {
            return null;
        }

        MyPage myPage = null;
        //首先处理传递进来的是map对象和通过注解方式传值的情况，从中提取出myPage,循环获取map中的键值对，取出myPage对象
        if (paramerObject instanceof Map) {
            Map<String, Object> params = (Map<String, Object>) paramerObject;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof MyPage) {
                    return (MyPage) entry.getValue();
                }
            }
        } else if (paramerObject instanceof MyPage) {
            //继承方式 pojo继承自MyPage 只取出我们希望得到的分页参数
            myPage = (MyPage) paramerObject;

        }
        return myPage;
    }

    /**
     * @Author fengzhaoquan
     * @Description 获取当前sql查询的记录总数
     * @return
     **/
    private int getTotal(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql) {
        //获取mapper文件中当前查询语句的配置信息
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        //获取所有配置Configuration
        org.apache.ibatis.session.Configuration configuration = mappedStatement.getConfiguration();
        // 获取当前查询语句的sql
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        //将sql改写成统计记录数的sql语句,这里是mysql的改写语句,将第一次查询结果作为第二次查询的表
        String countSql = "select count(*) as total from (" + sql + ") $_paging";
        //获取connection连接对象，用于执行countsql语句
        Connection conn = (Connection) invocation.getArgs()[0];
        PreparedStatement ps = null;
        int total = 0;
        try {
            //预编译统计总记录数的sql
            ps = conn.prepareStatement(countSql);
            //构建统计总记录数的BoundSql
            BoundSql countBoundSql = new BoundSql(configuration, countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            //构建ParameterHandler，用于设置统计sql的参数
            ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBoundSql);
            //设置总数sql的参数
            parameterHandler.setParameters(ps);
            //执行查询语句
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //与countSql中设置的别名对应
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return total;
    }

    /**
     * @Author fengzhaoquan
     * @Description 修改原始sql语句为分页sql语句
     * @return
     **/
    private Object updateSql2Limit(Invocation invocation, MetaObject metaStatementHandler, int pageSize, int pageStart) throws InvocationTargetException, IllegalAccessException, SQLException {
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        //构建新的分页sql语句
        String limitSql = "select * from (" + sql + ") $_paging_table limit ? offset ?";
        //修改当前要执行的sql语句
        metaStatementHandler.setValue("delegate.boundSql.sql", limitSql);
        //相当于调用prepare方法，预编译sql并且加入参数，但是少了分页的两个参数，它返回一个PreparedStatement对象
        PreparedStatement ps = (PreparedStatement) invocation.proceed();
        //获取sql总的参数总数
        int count = ps.getParameterMetaData().getParameterCount();
        //设置与分页相关的两个参数
        ps.setInt(count, pageStart);
        ps.setInt(count - 1, pageSize);
        return ps;
    }

    /**
     * @Author fengzhaoquan
     * @Description 验证当前页码的有效性
     * @return
     **/
    private void checkPage(boolean checkFlag, Integer pageNumber, Long pageTotal) throws Exception {
        if (checkFlag) {
            if (pageNumber > pageTotal) {
                throw new Exception("查询失败，查询页码" + pageNumber + "大于总页数" + pageTotal);
            }
        }
    }
}
