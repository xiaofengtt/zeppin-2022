package enfo.crm.util;

import java.util.List;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;

/**
 * <pre>
 *  Title:全局常量
 *  Description:全局常量
 * </pre>
 * 
 * @author <a href="mailto:littcai@hotmail.com">空心大白菜</a>
 * @since 2007-10-11
 * @version 1.0
 */
public class Constants {
	
	public static final String SEPARATOR = java.io.File.separator;	//文件分隔符	
	
	public static String smsAddress;
	//取得短信平台的IP地址
	static{
		String sqlStr = "SELECT TYPE_CONTENT " +
						"FROM TDICTPARAM " +
						"WHERE TYPE_VALUE = '800205'";
		List rsList = null;
		try {
			rsList = enfo.crm.dao.CrmDBManager.listBySql(sqlStr);
		} catch (BusiException e) {
			System.err.println(e.getMessage());
		}
		
		if(rsList.size()>0){
			Map map = (Map)rsList.get(0);
			
			if(map!=null){
				smsAddress = Utility.trimNull(map.get("TYPE_CONTENT")).trim();				
			}
		}		
	}
}
