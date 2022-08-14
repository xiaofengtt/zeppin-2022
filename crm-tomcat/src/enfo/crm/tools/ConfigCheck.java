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
	 * ��ȡ�����Զ���ҳ�����ҵ���߼����У�ĳ���������ͱ����ڽ����ֶα��ж�Ӧ�ļ�¼������0��
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
	 * ��ȡ�����Զ���ҳ�����ҵ���߼����У�ĳ��Ҫ��������ڽ����ֶα��ж�Ӧ�ļ�¼������0��
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
	 * �����ֶα���ĳ���������ͱ����Ӧ�ļ�¼��
	 * @param interfacetype_code �������ͱ���
	 * @return
	 * @throws BusiException
	 */
	public static Integer getInterfaceField(String interfacetype_code) throws BusiException,Exception{
		ConfigLocal configLocal = EJBFactory.getConfig();
		List fieldList = configLocal.interfaceFieldList(interfacetype_code);
		return  new Integer(fieldList.size());
	}
	
	/**
	 * ����Ҫ�������ĳ��Ҫ��������Ӧ�ļ�¼��
	 * @param region_code Ҫ�������
	 * @return
	 * @throws BusiException
	 */
	public static Integer getInterfaceRegion(String region_code) throws BusiException,Exception{
		ConfigLocal configLocal = EJBFactory.getConfig();
		List fieldList = configLocal.interfaceRegionList(region_code);
		return new Integer(fieldList.size());
	}
	
	/**
	 * ��ȡ�����Զ��帨��ҳ�����ҵ���߼����У�ĳ�����渨�����ͱ����ڽ���Ԫ�ر��ж�Ӧ�ļ�¼������0��
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
	 * ����Ԫ�ر���ĳ�����渨�����ͱ��뼯�ϵĸ���
	 * @param catalog_code ���渨�����ͱ���
	 * @return
	 * @throws BusiException
	 */
	public static Integer getConfigElement(String catalog_code) throws BusiException,Exception{
		ConfigLocal configLocal = EJBFactory.getConfig();
		List elementList = configLocal.configElementList(catalog_code);
		return new Integer(elementList.size());
	}
	/**
	 * ��ȡ����Ӧ��ҳ�����ҵ���߼�����(����Ӧ�ô��ڶ�Ӧ�ļ�¼����)
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
	 * ����Ӧ�ñ���ĳ���������ͱ������ݼ��ϵĸ���
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
	 * ����ʾ�ֶ����������߼�����,���ú�׺������ʾ
	 * @param ControlList ҳ����ʾ�����ݼ�
	 * @param fieldStrs �ֶμ�
	 * @param controlFlag ���Ʊ�־
	 * @throws BusiException
	 */
	public static List setFieldValue(List ControlList,String fieldCode,String configField,String configValue) throws BusiException{
		String fieldName="";
		for(int i=0;i<ControlList.size();i++){
			Map rowMap = (Map)ControlList.get(i);
			fieldName = Utility.trimNull(rowMap.get("INTERFACEFIELD_CODE"));
			//�������
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
	 * ����ʾ�ֶ�������ֵ�����滻
	 * @param ControlList ҳ����ʾ�����ݼ�
	 * @param fieldStrs �ֶμ�
	 * @param controlFlag ���Ʊ�־
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
	 * ����ʾ�ֶ�������ֵ�����滻
	 * @param ControlList ҳ����ʾ�����ݼ�
	 * @param fieldStrs �ֶμ�
	 * @param controlFlag ���Ʊ�־
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
