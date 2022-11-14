package com.rx.models.generators;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

@SuppressWarnings({"unused"})
public class CustomSequenceIdGenerator extends SequenceStyleGenerator {
	public static final String VALUE_PREFIX_PARAMETER="customPrefix";
	public static final String VALUE_PREFIX_DEFAULT="";
	private String customPrefix;
	
	public static final String NUMBER_FORMAT_PARAMETER="numberFormat";
	public static final String NUMBER_FORMAT_DEFAULT="%d";
	private String numberFormat;
	
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		super.configure(LongType.INSTANCE, params, serviceRegistry);
		
		customPrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER, params,VALUE_PREFIX_DEFAULT);
		
		numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params,NUMBER_FORMAT_DEFAULT);
		
	}
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		
		
		return customPrefix.concat(
				String.format(numberFormat, super.generate(session, object))
				);
	}
}