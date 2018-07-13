package com.zhanglugao.mybatis.interceptor;


import com.zhanglugao.base.PageParameter;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，重写sql语句实现物理分页。
 * 
 * 2017/04/17 zlg针对mybatis 3.4.0以上版本做了修改  注解Intercepts部分  args增加参数
 * 
 */
//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class,Integer.class }) })  
public class PaginationInterceptor implements Interceptor {
	private static final Log logger = LogFactory.getLog(PaginationInterceptor.class);
    protected static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    protected static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    protected static String defaultDialect = "mysql"; // 数据库类型(默认为mysql)
    private static String defaultPageSqlId = ".*Page$"; // 需要拦截的ID(正则匹配)
    private static String dialect = ""; // 数据库类型(默认为mysql)
    private static String pageSqlId = ""; // 需要拦截的ID(正则匹配)

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        }
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        dialect = configuration.getVariables().getProperty("dialect");
        if (null == dialect || "".equals(dialect)) {
            logger.warn("Property dialect is not setted,use default 'mysql' ");
            dialect = defaultDialect;
        }
        pageSqlId = configuration.getVariables().getProperty("pageSqlId");
        if (null == pageSqlId || "".equals(pageSqlId)) {
            logger.warn("Property pageSqlId is not setted,use default '.*Page$' ");
            pageSqlId = defaultPageSqlId;
        }
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的MappedStatement的sql
        if (mappedStatement.getId().matches(pageSqlId)) {
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                throw new NullPointerException("parameterObject is null!");
            } 
            else {
            	Object o=metaStatementHandler.getValue("delegate.boundSql.parameterObject");
            	PageParameter page;
            	if(o instanceof PageParameter){
            		 page = (PageParameter) metaStatementHandler.getValue("delegate.boundSql.parameterObject");
            		 if(page.getSearch()!=null){
            			    Map<String,String> paramMab=page.getSearch();  
            	            for(Entry<String,String> entry:paramMab.entrySet())  
            	            {  
            	                //sql=sql.replace(s, " ? ");  
            	                // #{operUName} -->operUName  
            	                String key=entry.getKey();  
            	                String val =paramMab.get(key);  
            	                if(val!=null &&val instanceof String&&(val.toString().contains("%")||val.toString().contains("_")))  
            	                {  
            	                    val=AntiSqlInterceptor.antiSql(val.toString());
            	                    paramMab.put(key.toString(), val);  
            	                }             
            	            }     
            		 }
            		 if(page.getSelect()!=null){
         			    Map<String,Object> paramMab=page.getSelect();  
         	            for(Entry<String,Object> entry:paramMab.entrySet())  
         	            {  
         	                //sql=sql.replace(s, " ? ");  
         	                // #{operUName} -->operUName  
         	            	String key=entry.getKey();  
         	            	Object val =paramMab.get(key);  
         	                if(val!=null &&val instanceof String&&(val.toString().contains("%")||val.toString().contains("_")))  
         	                {  
         	                	val=AntiSqlInterceptor.antiSql(val.toString());
         	                    paramMab.put(key, val);  
         	                }             
         	            }     
         		 }
            	}else{
            		 page = (PageParameter) ((Map)(metaStatementHandler.getValue("delegate.boundSql.parameterObject"))).get("obj");
            	}
            	//String sql = AntiSqlInterceptor.getSql(configuration, boundSql);
                String sql = boundSql.getSql();
                // 重写sql
                String pageSql = buildPageSql(sql, page);
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                Connection connection = (Connection) invocation.getArgs()[0];
                // 重设分页参数里的总页数等
                setPageParameter(sql, connection, mappedStatement, boundSql, page);
            }
        }
        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }

    /**
     * 从数据库里查询总的记录数并计算总页数，回写进分页参数<code>PageParameter</code>,这样调用者就可用通过 分页参数
     * <code>PageParameter</code>获得相关信息。
     * 
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
            BoundSql boundSql, PageParameter page) {
        // 记录总记录数
        String countSql = "select count(0) from (" + sql + ") as total";
//        int fromSqlIndex=sql.toLowerCase().indexOf("from");
//        String countSql="select count(0) "+sql.substring(fromSqlIndex);
        
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalCount(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setTotalPage(totalPage);

        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                if(rs!=null) rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                if(countStmt!=null) countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }

    }

    /**
     * 对SQL参数(?)设值
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * 根据数据库类型，生成特定的分页sql
     * 
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, PageParameter page) {
        if (page != null) {
            StringBuilder pageSql = new StringBuilder();
            if ("mysql".equals(dialect)) {
                pageSql = buildPageSqlForMysql(sql, page);
            } else if ("oracle".equals(dialect)) {
                pageSql = buildPageSqlForOracle(sql, page);
            } else if ("mssql".equals(dialect)) {
                pageSql = buildPageSqlForMssql(sql, page);
            } else {
                return sql;
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    /**
     * mysql的分页语句
     * 
     * @param sql
     * @param page
     * @return String
     */
    public StringBuilder buildPageSqlForMysql(String sql, PageParameter page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        pageSql.append(sql);
        pageSql.append(" limit " + beginrow + "," + page.getPageSize());
        return pageSql;
    }
    //mssql分页语句
    public StringBuilder buildPageSqlForMssql(String sql, PageParameter page) {
         
        //String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        //String endrow = String.valueOf(page.getCurrentPage() * page.getPageSize());
        
        int beginrow =  (page.getCurrentPage() - 1) * page.getPageSize();
        int endrow =  page.getCurrentPage() * page.getPageSize();
        
        StringBuilder pageSql = new StringBuilder(sql.length() + 100);  
        //select * from (Select ROW_NUMBER() OVER (order by id desc) as pos,* from (select * from BD_Provider) temp) as sp where pos between 1 and 5
        pageSql.append("select * from (Select ROW_NUMBER() OVER (order by "+page.getOrderField()+" "+page.getOrderType()+") as pos,* from (");  
        pageSql.append(sql);  
        pageSql.append(") temp) as sp where pos between "+(beginrow+1)+" and "+endrow+"");  
        return pageSql;  
        
         
    }

    /**
     * 参考hibernate的实现完成oracle的分页
     * 
     * @param sql
     * @param page
     * @return String
     */
    public StringBuilder buildPageSqlForOracle(String sql, PageParameter page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        String endrow = String.valueOf(page.getCurrentPage() * page.getPageSize());

        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp where rownum <= ").append(endrow);
        pageSql.append(") where row_id > ").append(beginrow);
        return pageSql;
    }

    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public void setProperties(Properties properties) {
    }

}
