package enfo.crm.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.system.ConfigLocal;
import enfo.crm.tools.EJBFactory;

public class ConfigCheck {

	/**
	 * 获取界面自定义页面符合业务逻辑的行（某个界面类型编码在界面字段表中对应的记录数大于0）
	 * @param pageList
	 * @return
	 * @throws BusiException
	 */
	public static String getInterfaceCollectCheck(IPageList pageList) throws BusiException,Exception{
		List repList = pageList.getRsList();
		String checkStr = "";
		for(int i=0;i<repList.size();i++	){
			Map repMap = (Map)repList.get(i);
			String repCode = Utility.trimNull(repMap.get("INTERFACETYPE_CODE"));
			if(ConfigCheck.getInterfaceField(repCode).intValue()>0){
				checkStr = checkStr + i + ",";
			}
		}
		return checkStr;
	}
	
	/**
	 * 获取界面自定义页面符合业务逻辑的行（某个要素域编码在界面字段表中对应的记录数大于0）
	 * @param pageList
	 * @return
	 * @throws BusiException
	 */
	public static String getInterfaceCheck(IPageList pageList,String checkTable,String checkField) throws BusiException,Exception{
		ConfigLocal configLocal = EJBFactory.getConfig();
		List repList = pageList.getRsList();
		List list=new ArrayList();
		String checkStr = "",showSql="",repCode="",orderNo="",checkCount="";
		for(int i=0;i<repList.size();i++){
			Map repMap = (Map)repList.get(i);
			repCode = Utility.trimNull(repMap.get(checkField));
			if("".equals(showSql)){ 
				showSql="select count(1) AS CHECK_COUNT from "+checkTable+" WHERE "+checkField+" ='"+repCode+"'";
			}else{ 
				showSql=showSql+" union all "+"select count(1) AS CHECK_COUNT from "+checkTable+" WHERE "+checkField+" ='"+repCode+"'";
			}
		}
		if(!"".equals(showSql)) list=configLocal.getInfoPageData(showSql);
		for(int i=0;i<repList.size();i++){
			Map repMap = (Map)repList.get(i);
			Map Map = (Map)list.get(i);
			repCode = Utility.trimNull(repMap.get(checkField));
			checkCount = Utility.trimNull(Map.get("CHECK_COUNT"));
			if(!"0".equals(checkCount)){
				checkStr = checkStr + i + ",";
			}
		}
		return checkStr;
	}
	
	/**
	 * 界面字段表中某个界面类型编码对应的记录数
	 * @param interfacetype_code 界面类型编码
	 * @return
	 * @throws BusiException
	 */
	public static Integer getInterfaceField(String interfacetype_code) throws BusiException,Exception{
		ConfigLocal configLocal = EJBFactory.getConfig();
		List fieldList = configLocal.interfaceFieldList(interfacetype_code);
		return  new Integer(fieldList.size());
	}
	
	/**
	 * 界面要素域表中某个要素域编码对应的记录数
	 * @param region_code 要素域编码
	 * @return
	 * @throws BusiException
	 */
	public static Integer getInterfaceRegion(String region_code) throws BusiException,Exception{
		ConfigLocal configLocal = EJBFactory.getConfig();
		List fieldList = configLocal.interfaceRegionList(region_code);
		return new Integer(fieldList.size());
	}
	
	/**
	 * 获取界面自定义辅助页面符合业务逻辑的行（某个界面辅助类型编码在界面元素表中对应的记录数大于0）
	 * @param pageList
	 * @return
	 * @throws BusiException
	 */
	public static String getInterfaceTypeCheck(IPageList pageList) throws BusiException,Exception{
		List repList = pageList.getRsList();
		String checkStr = "";
		for(int i=0;i<repList.size();i++	){
			Map repMap = (Map)repList.get(i);
			String repCode = Utility.trimNull(repMap.get("CATALOG_CODE"));
			if(ConfigCheck.getConfigElement(repCode).intValue()>0){
				checkStr = checkStr + i + ",";
			}
		}
		return checkStr;
	}
	
	/**
	 * 界面元素表中某个界面辅助类型编码集合的个数
	 * @param catalog_code 界面辅助类型编码
	 * @return
	 * @throws BusiException
	 */
	public static Integer getConfigElement(String catalog_code) throws BusiException,Exception{
		ConfigLocal configLocal = EJBFactory.getConfig();
		List elementList = configLocal.configElementList(catalog_code);
		return new Integer(elementList.size());
	}
	/**
	 * 获取界面应用页面符合业务逻辑的行(界面应用存在对应的记录数据)
	 * @param pageList
	 * @return
	 * @throws BusiException
	 */
	public static String getConfigRecord(IPageList pageList) throws BusiException,Exception{
		List repList = pageList.getRsList();
		String checkStr = "";
		for(int i=0;i<repList.size();i++	){
			Map repMap = (Map)repList.get(i);
			String repCode = Utility.trimNull(repMap.get("CATALOG_CODE"));
			String relation_id = Utility.trimNull(repMap.get("RELATION_ID"));
			if(ConfigCheck.getConfigData(repCode,relation_id).intValue()>0){
				checkStr = checkStr + i + ",";
			}
		}
		return checkStr;
	}
	
	/**
	 * 界面应用表中某个界面类型编码数据集合的个数
	 * @param catalog_code
	 * @param relation_id
	 * @return
	 * @throws BusiException
	 */
	public static Integer getConfigData(String catalog_code,String relation_id) throws BusiException,Exception{
		ConfigLocal configLocal = EJBFactory.getConfig();
		List elementList = configLocal.configDataList(catalog_code,relation_id);
		return new Integer(elementList.size());
	}
	
	/**
	 * 对显示字段增加特殊逻辑控制,设置后缀名不显示
	 * @param ControlList 页面显示的数据集
	 * @param fieldStrs 字段集
	 * @param controlFlag 控制标志
	 * @throws BusiException
	 */
	public static List setFieldValue(List ControlList,String fieldCode,String configField,String configValue) throws BusiException{
		String fieldName="";
		for(int i=0;i<ControlList.size();i++){
			Map rowMap = (Map)ControlList.get(i);
			fieldName = Utility.trimNull(rowMap.get("INTERFACEFIELD_CODE"));
			//变更内容
			if(fieldName.equals(fieldCode)){
				if("DelRow".equals(configValue))
					ControlList.remove(i);
				else
					rowMap.put(configField, configValue);
			}
		}
		return ControlList;
	}
	
	/**
	 * 对显示字段中特殊值进行替换
	 * @param ControlList 页面显示的数据集
	 * @param fieldStrs 字段集
	 * @param controlFlag 控制标志
	 * @throws BusiException
	 */
	public static List replaceFieldValue(List ControlList,String fieldCode,String oldValue,String newValue) throws BusiException{
		String fieldName="";
		for(int i=0;i<ControlList.size();i++){
			Map rowMap = (Map)ControlList.get(i);
			fieldName = Utility.trimNull(rowMap.get(fieldCode));
			rowMap.put(fieldCode, fieldName.replaceAll(oldValue, newValue));
		}
		return ControlList;
	}
	
	/**
	 * 对显示字段中特殊值进行替换
	 * @param ControlList 页面显示的数据集
	 * @param fieldStrs 字段集
	 * @param controlFlag 控制标志
	 * @throws BusiException
	 */
	public static List setAllField(List ControlList,String fieldCode,String configValue) throws BusiException{
		String fieldName="";
		for(int i=0;i<ControlList.size();i++){
			Map rowMap = (Map)ControlList.get(i);
			rowMap.put(fieldCode, configValue);
		}
		return ControlList;
	}
	
	
}
