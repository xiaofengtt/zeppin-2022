/*
 * 创建日期 2007-5-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.util;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * @author caiyuan
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public interface CallableStatementCallback {
	
	Object doInCallableStatement(CallableStatement cs) throws SQLException;
}
