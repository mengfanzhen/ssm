package com.mfz.dynamicdb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Intercepts({
	@Signature(type = Executor.class, method = "update", args = {
			MappedStatement.class, Object.class }),
	@Signature(type = Executor.class, method = "query", args = {
			MappedStatement.class, Object.class, RowBounds.class,
			ResultHandler.class }) })
public class DynamicInterceptor implements Interceptor {

	private static Log logger = LogFactory.getLog(DynamicInterceptor.class);


	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = null;
		if (invocation.getArgs().length > 1) {
			parameter = invocation.getArgs()[1];
		}
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Configuration configuration = mappedStatement.getConfiguration();
		String sql = DynamicInterceptor.showSql(configuration, boundSql);

		if(sql.toUpperCase().startsWith("SELECT")){
			DynamicDataSourceHolder.setDsKeySlave();
		}else{
			DynamicDataSourceHolder.setDsKeyMaster();
		}

		return invocation.proceed();
	}
	
	


	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
	    return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}
	public static String showSql(Configuration configuration, BoundSql boundSql) {
		String sql = "---------生成监控用SQL失败，不影响程序执行-------------";
		try {
			Object parameterObject = boundSql.getParameterObject();
			List<ParameterMapping> parameterMappings = boundSql
					.getParameterMappings();
			sql = boundSql.getSql().replaceAll("[\\s]+", " ");
			if (parameterMappings.size() > 0 && parameterObject != null) {
				TypeHandlerRegistry typeHandlerRegistry = configuration
						.getTypeHandlerRegistry();
				if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
					sql = sql.replaceFirst("\\?",
							getParameterValue(parameterObject));
				} else {
					MetaObject metaObject = configuration
							.newMetaObject(parameterObject);
					for (ParameterMapping parameterMapping : parameterMappings) {
						String propertyName = parameterMapping.getProperty();
						if (metaObject.hasGetter(propertyName)) {
							Object obj = metaObject.getValue(propertyName);
							sql = sql.replaceFirst("\\?", getParameterValue(obj));
						} else if (boundSql.hasAdditionalParameter(propertyName)) {
							Object obj = boundSql
									.getAdditionalParameter(propertyName);
							sql = sql.replaceFirst("\\?", getParameterValue(obj));
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return sql;
	}
 
	private static String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(
					DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(new Date()) + "'";
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "";
			}
		}
		return value;
	}
}
