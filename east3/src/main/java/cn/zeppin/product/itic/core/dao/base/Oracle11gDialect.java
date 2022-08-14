package cn.zeppin.product.itic.core.dao.base;

import java.sql.Types;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.type.StandardBasicTypes;

public class Oracle11gDialect extends Oracle10gDialect {
	public Oracle11gDialect() {
		// TODO Auto-generated constructor stub
		super();
		registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());          
	    registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());        
	    registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());  
	    registerHibernateType(Types.DECIMAL, StandardBasicTypes.DOUBLE.getName());        
	    registerHibernateType(Types.NCLOB, StandardBasicTypes.STRING.getName()); 
	}
}
