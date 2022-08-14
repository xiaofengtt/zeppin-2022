package cn.zeppin.product.itic.core.dao.base;

import java.sql.Types;

import org.hibernate.dialect.SQLServer2008Dialect;

public class SqlServer2008Dialect extends SQLServer2008Dialect {
	public SqlServer2008Dialect() {
		// TODO Auto-generated constructor stub
		super();
		registerHibernateType(Types.CHAR, "string");
		registerHibernateType(Types.NCHAR, "string");
		registerHibernateType(Types.NVARCHAR, "string");
		registerHibernateType(Types.LONGNVARCHAR, "string");
		registerHibernateType(Types.DECIMAL, "double");
	}
}
