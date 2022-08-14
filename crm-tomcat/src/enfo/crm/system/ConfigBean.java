package enfo.crm.system;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.ConfigUtil;

@Component(value="config")
public class ConfigBean extends CrmBusiExBean implements ConfigLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listElementOrderNO(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List listElementOrderNO(String catalog_code,Integer record_id,String queryFlag)throws BusiException{
		List list = null;
		String sql ="";
		String visibledStr="";
		if(record_id.intValue() == 0){//新增
			visibledStr="1";
		}else{//修改
			visibledStr="2";
		}
		if(queryFlag.equals("1")){//所有元素
			sql = "SELECT * FROM CONFIG_ELEMENT WHERE CATALOG_CODE = '"+catalog_code+"'  ORDER BY ORDER_NO";
		}else if(queryFlag.equals("2")){//所有页面显示的元素包括分区
			sql = "SELECT * FROM CONFIG_ELEMENT WHERE CATALOG_CODE = '"+catalog_code+"' AND charindex('"+visibledStr+"',isnull(VISIBLED_FLAG,''))!=0 ORDER BY ORDER_NO";
		}else if(queryFlag.equals("3")){//所有页面隐藏元素不包括分区
			sql = "SELECT * FROM CONFIG_ELEMENT WHERE CATALOG_CODE = '"+catalog_code+"' AND charindex('"+visibledStr+"',isnull(VISIBLED_FLAG,''))=0 AND EDIT_TYPE!='8' ORDER BY ORDER_NO";
		}
		
		
		list = super.listBySql(sql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listRegionParams(java.lang.String)
	 */
	@Override
	public List listRegionParams(String region_code)throws BusiException{
		List list = null;
		String sql ="SELECT * FROM CONFIG_REGION WHERE REGION_CODE ='" + region_code + "' and Use_state='1' ORDER BY ORDER_NO";
		list = super.listBySql(sql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listBySql(java.lang.String)
	 */
	@Override
	public List listBySql(String catalog_code)throws BusiException{
		List list = null;
		String sql ="SELECT * FROM CONFIG_CATALOG WHERE 1=1 order by catalog_code";
		list = super.listBySql(sql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listRegionParamName(java.lang.String, java.lang.String)
	 */
	@Override
	public List listRegionParamName(String region_code,String item_code)throws BusiException{
		List list = null;
		String sql ="SELECT * FROM CONFIG_REGION WHERE REGION_CODE ='" + region_code + "' and ITEM_CODE='"+item_code+"' and Use_state='1'";
		list = super.listBySql(sql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#addConfigRecord(java.lang.String, java.lang.String)
	 */
	@Override
	public void addConfigRecord(String addField,String addValue) throws BusiException{
		String addSql="insert into config_record("+addField+") values("+addValue+")";
		super.executeSql(addSql);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#addConfigData(java.lang.String, java.lang.String)
	 */
	@Override
	public void addConfigData(String addField,String addValue) throws BusiException{
		String addSql="insert into config_data("+addField+") values("+addValue+")";
		super.executeSql(addSql);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listDataValues(java.lang.String, java.lang.String)
	 */
	@Override
	public List listDataValues(String relation_id,String query_flag) throws BusiException{
		List list = null;
		String querySql = "";
		if(query_flag.equals("1")){//显示元素数据
			querySql = " select b.* from config_element a,config_data b " +
					   "where a.element_code=b.element_code and b.relation_id='"+relation_id+"' " +
					   "and a.catalog_code = b.catalog_code and charindex('2',a.visibled_flag)!=0 " +
					   "order by a.order_no";
		}else if(query_flag.equals("2")){//隐藏元素数据
			querySql = " select b.* from config_element a,config_data b " +
					   "where a.element_code=b.element_code and b.relation_id='"+relation_id+"' " +
					   "and a.catalog_code = b.catalog_code and charindex('2',a.visibled_flag)=0 " +
					   "order by a.order_no";
		}
		list = super.listBySql(querySql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listTableIdentity(java.lang.String)
	 */
	@Override
	public List listTableIdentity(String interfacetype_code) throws BusiException{
		List list = null;
		String querySql = " select interfacefield_code from interface_field" +
						  " where interfacetype_code='"+interfacetype_code+"' and key_flag='1'";
		list = super.listBySql(querySql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#getConfigInterface(java.lang.String)
	 */
	@Override
	public List getConfigInterface(String interfacetype_code) throws BusiException{
		List list = null;
		String querySql = " select IC.*,IFD.* from INTERFACE_CATALOG IC,INTERFACE_FIELD IFD" +
						  " where IC.Interfacetype_Code=IFD.Interfacetype_Code "+
						  " and IC.Interfacetype_Code='"+interfacetype_code+"' and IFD.Use_State='1'"+
						  " order by IFD.Order_No";
		list = super.listBySql(querySql);
		return list;		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#getConfigButton(java.lang.String)
	 */
	@Override
	public List getConfigButton(String interfacetype_code) throws BusiException{
		List list = null;
		String querySql = " select IBN.* from INTERFACE_BUTTON IBN" +
						  " where IBN.Interfacetype_Code='"+interfacetype_code+"' and IBN.Use_State='1'"+
						  " order by IBN.Order_No";
		list = super.listBySql(querySql);
		return list;			
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listTableName(java.lang.String)
	 */
	@Override
	public List listTableName(String interfacetype_code) throws BusiException{
		List list = null;
		String querySql = " select * from interface_catalog" +
						  " where interfacetype_code='"+interfacetype_code+"' and use_state='1'";
		list = super.listBySql(querySql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#modConfigData(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public void modConfigData(String updateValue,String queryWhere,Integer record_id) throws BusiException{
		String addSql="update config_data set "+updateValue+" where " + queryWhere;
		super.executeSql(addSql);//修改配置表数据
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#modConfigRecord(java.lang.Integer)
	 */
	@Override
	public void modConfigRecord(Integer record_id) throws BusiException{
		String recordModSql = "update config_record set update_time='"+new Timestamp(System.currentTimeMillis())+"' where record_id="+record_id;
		super.executeSql(recordModSql);//修改应用表数据
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listExistDataValues(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List listExistDataValues(String relation_id,String query_flag,String catalog_code) throws BusiException{
		List list = null;
		String querySql ="";
		if(query_flag.equals("1")){//显示字段
			querySql = " select d.OLDELEMENT_NAME,c.* from "
				+" (select * from config_element where catalog_code='"+catalog_code+"' and charindex('2',visibled_flag)!=0) c LEFT OUTER JOIN "
				+" (select b.element_name as OLDELEMENT_NAME,a.element_code " 
				+" from config_element a,config_data b "
				+" where a.element_code=b.element_code and a.catalog_code=b.catalog_code and a.element_name=b.element_name and charindex('2',a.visibled_flag)!=0 and "
				+" b.relation_id='"+relation_id+"') d on c.element_code=d.element_code "
				+" order by ORDER_NO";
		}else if(query_flag.equals("2")){//隐藏字段
			querySql = " select d.OLDELEMENT_NAME,c.* from "
				+" (select * from config_element where catalog_code='"+catalog_code+"' and charindex('2',visibled_flag)=0) c LEFT OUTER JOIN "
				+" (select b.element_name as OLDELEMENT_NAME,a.element_code " 
				+" from config_element a,config_data b "
				+" where a.element_code=b.element_code and a.catalog_code=b.catalog_code and a.element_name=b.element_name and charindex('2',a.visibled_flag)=0 and "
				+" b.relation_id='"+relation_id+"') d on c.element_code=d.element_code "
				+" order by ORDER_NO";
		}
		list = super.listBySql(querySql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#tableFieldShowList(java.lang.String)
	 */
	@Override
	public List tableFieldShowList(String interfaceType_code)throws BusiException{
		List list = null;
		String querySql = " SELECT * FROM INTERFACE_FIELD WHERE TABLESHOW_FLAG='1' AND USE_STATE='1' "+
	   			" AND INTERFACETYPE_CODE ='"+interfaceType_code+"' ORDER BY ORDER_NO";
		list = super.listBySql(querySql);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#tableNoShowList(java.lang.String)
	 */
	@Override
	public List tableNoShowList(String interfaceType_code)throws BusiException{
		List list = null;
		String querySql = " SELECT * FROM INTERFACE_FIELD WHERE TABLESHOW_FLAG <> '1' AND USE_STATE='1' "+
	   			" AND INTERFACETYPE_CODE ='"+interfaceType_code+"' ORDER BY ORDER_NO";
		list = super.listBySql(querySql);
		return list;
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#queryShowList(java.lang.String)
	 */
	@Override
	public List queryShowList(String interfaceType_code)throws BusiException{
		List list = null;
		String querySql = " SELECT * FROM INTERFACE_FIELD WHERE QUERY_FLAG='1' AND USE_STATE='1' "+
	   			" AND INTERFACETYPE_CODE ='"+interfaceType_code+"' ORDER BY ORDER_NO";
		list = super.listBySql(querySql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#showPageDataOther(java.lang.String, java.lang.String, java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String, int, int)
	 */
	@Override
	public IPageList showPageDataOther(String interfaceType_code,String tableName,String[] queryFieldCol,String[] queryFieldValue,String[] conditionType,
								String otherQueryStr,int pageIndex, int pageSize) throws Exception{
		IPageList rsList = null;
		String querySql=ConfigUtil.getShowFieldSql(interfaceType_code,queryFieldCol,queryFieldValue,conditionType,otherQueryStr);
		rsList = super.listSqlPage(querySql, pageIndex, pageSize);
		return rsList;
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#getListPageData(java.lang.String, int, int)
	 */
	@Override
	public IPageList getListPageData(String showSql,int pageIndex, int pageSize) throws BusiException{
		IPageList rsList = null;
		rsList = super.listSqlPage(showSql, pageIndex, pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#previewPageData(java.lang.String, int, int)
	 */
	@Override
	public IPageList previewPageData(String tableName,int pageIndex, int pageSize) throws BusiException{
		IPageList rsList = null;
		String querySql = " select * from " + tableName;
		rsList = super.listSqlPage(querySql, pageIndex, pageSize);
		return rsList;
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listVaildFieldOrderNO(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List listVaildFieldOrderNO(String interfaceType_code,String edit_flag,String query_flag)throws BusiException{
		List list = null;
		String querySql =" SELECT * FROM INTERFACE_FIELD WHERE INTERFACETYPE_CODE = '"+interfaceType_code+"' AND USE_STATE = '1' AND SHOW_TYPE !='6'  ";
		if(query_flag.equals("1"))
			querySql =querySql + " AND EDIT_TYPE!='8' ";
		else if(query_flag.equals("2"))
			querySql =querySql + " AND CHARINDEX('"+edit_flag+"',isnull(VISIBLED_FLAG,'')) != 0";//显示字段集合中包含分区元素
		else if(query_flag.equals("3"))
			querySql =querySql + " AND EDIT_TYPE!='8' AND CHARINDEX('"+edit_flag+"',isnull(VISIBLED_FLAG,'')) = 0";
		querySql = querySql + " ORDER BY ORDER_NO";
		list = super.listBySql(querySql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listVaildViewFieldOrderNO(java.lang.String)
	 */
	@Override
	public List listVaildViewFieldOrderNO(String interfaceType_code)throws BusiException{
		List list = null;
		String querySql = " SELECT * FROM INTERFACE_FIELD WHERE INTERFACETYPE_CODE = '"+interfaceType_code+"' "+
					" AND CHARINDEX('3',isnull(VISIBLED_FLAG,''))!=0 AND USE_STATE = '1' ORDER BY ORDER_NO";
		list = super.listBySql(querySql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#addInterfaceField(java.lang.String, java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String[])
	 */
	@Override
	public void addInterfaceField(String table_name,String[] showTypeStrs,String[] addFieldName,String[] addFieldValue,String[] updateFlagStrs) throws BusiException{
		String addSql="insert into "+table_name+"(";
		for(int i=0;i<addFieldName.length;i++){
			if(!(showTypeStrs[i].equals("6") || updateFlagStrs[i].equals("2"))){
				addSql =addSql  + addFieldName[i] + ",";
			}
		}
		addSql = addSql.substring(0, addSql.length()-1) + ") values(";//去掉最后的逗号
		for(int i=0;i<addFieldValue.length;i++){
			if(!(showTypeStrs[i].equals("6") || updateFlagStrs[i].equals("2"))){
				if(showTypeStrs[i].equals("3") || showTypeStrs[i].equals("4") || showTypeStrs[i].equals("5")){
					if(addFieldValue[i]==null || addFieldValue[i].equals("")){
						addSql = addSql + "null"+",";
					}else{ 
						addSql = addSql + "'" + addFieldValue[i].replaceAll("'", "''") + "'"+",";
					}
				}else{
					if(addFieldValue[i]==null || addFieldValue[i].equals("")){
						addSql = addSql + "null"+",";
					}else{ 
						addSql = addSql + "'" + addFieldValue[i].replaceAll("'", "''") + "'"+",";
					}					
				}				
			}
		}
		addSql = addSql.substring(0, addSql.length()-1) + ")";
		super.executeSql(addSql);
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#modInterfaceField(java.lang.String, java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public void modInterfaceField(String table_name,String[] showTypeStrs,String[] modFieldName,String[] modFieldValue,String identyityCol,String identityValue,String[] updateFlagStr) throws BusiException{
		String modSql="update "+table_name+" set ";
		for(int i=0;i<modFieldName.length;i++){
			if((updateFlagStr[i].equals("1"))&& (!showTypeStrs[i].equals("6"))){//更新标识为是并且不为图标类型的进行更新
					if(showTypeStrs[i].equals("3") || showTypeStrs[i].equals("4") || showTypeStrs[i].equals("5")){
						if(modFieldValue[i]==null || modFieldValue[i].equals("")){
							modSql =modSql + modFieldName[i] + "=null"+",";
						}else{ 
							modSql =modSql + modFieldName[i] + "='" + modFieldValue[i].replaceAll("'", "''")+"'"+ ",";
						}
					}else{
						if(modFieldValue[i]==null || modFieldValue[i].equals("")){
							modSql =modSql + modFieldName[i] + "=null"+",";
						}else{ 
							modSql =modSql + modFieldName[i] + "='" + modFieldValue[i].replaceAll("'", "''")+"'"+ ",";
						}
					}
			}
		}
		modSql = modSql.substring(0, modSql.length()-1) + " where " +identyityCol+"='"+identityValue+"'";//去掉最后逗号
		super.executeSql(modSql);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#getInfoPageData(java.lang.String)
	 */
	@Override
	public List getInfoPageData(String querySql) throws BusiException{
		List list = null;
		list = super.listBySql(querySql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#modPageShowData(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List modPageShowData(String table_name,String identity_code,String identity_codeValue) throws BusiException{
		List list = null;
		String querySql = " select * from "+ table_name
						  + " where "+identity_code + "='"+identity_codeValue+"'";
		list = super.listBySql(querySql);
		return list;
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#delInfo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void delInfo(String table_code,String identity_code,String identity_value) throws BusiException{
		String deleteSql="delete from "+table_code+" where "+identity_code+"="+identity_value;
		super.executeSql(deleteSql);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#delRelationInfo(java.lang.String)
	 */
	@Override
	public void delRelationInfo(String identity_value) throws BusiException{
		String deleteSql="delete from config_data where Relation_ID in (select Relation_ID from config_record where record_id='"+identity_value+"')";
		super.executeSql(deleteSql);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#modLogInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void modLogInfo(String catalog_Code,String table_code,String identityCode,String identity_code,String fieldColName,String fieldName,String beforeValue,String afterValue,String userID,String deptID) throws BusiException{
		String addSql=" insert into MODIFY_LOGINFO(INTERFACE_CODE,TABLE_NAME,Key_Code,Key_Value,FIELD_Code,FIELD_NAME,Before_value,After_value,UPDATE_TIME,UPDATE_USER,UPDATE_DEPT)"+
					  " values('"+catalog_Code+"','"+table_code+"','"+identityCode+"','"+identity_code+"','"+fieldColName+"','"+fieldName+"','"+beforeValue+"','"+afterValue+"','"+new Timestamp(System.currentTimeMillis())+"','"+userID+"','"+deptID+"')";
		super.executeSql(addSql);		
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#interfaceFieldList(java.lang.String)
	 */
	@Override
	public List interfaceFieldList(String interfacetype_code) throws BusiException{
		List list = null;
		String sql ="SELECT * FROM INTERFACE_FIELD WHERE INTERFACETYPE_CODE ='" + interfacetype_code + "'";
		list = super.listBySql(sql);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#interfaceRegionList(java.lang.String)
	 */
	@Override
	public List interfaceRegionList(String region_code) throws BusiException{
		List list = null;
		String sql ="SELECT * FROM CONFIG_REGION WHERE REGION_CODE ='" + region_code + "'";
		list = super.listBySql(sql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#configElementList(java.lang.String)
	 */
	@Override
	public List configElementList(String catalog_code) throws BusiException{
		List list = null;
		String sql ="SELECT * FROM CONFIG_ELEMENT WHERE CATALOG_CODE ='" + catalog_code + "'";
		list = super.listBySql(sql);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#configDataList(java.lang.String, java.lang.String)
	 */
	@Override
	public List configDataList(String catalog_code,String relation_id) throws BusiException{
		List list = null;
		String sql ="SELECT * FROM CONFIG_DATA WHERE CATALOG_CODE ='" + catalog_code + "' AND RELATION_ID='"+ relation_id +"'";
		list = super.listBySql(sql);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#interfaceFieldNums(java.lang.String, java.lang.String)
	 */
	@Override
	public List interfaceFieldNums(String codeName,String interfaceType_code) throws BusiException{
		List list = null;
		String sql = "SELECT * FROM INTERFACE_FIELD WHERE INTERFACETYPE_CODE='"+codeName+"' AND INTERFACEFIELD_CODE='"+interfaceType_code+"'";
		list = super.listBySql(sql);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#configElementNums(java.lang.String, java.lang.String)
	 */
	@Override
	public List configElementNums(String elementCode,String catalogCode) throws BusiException{
		List list = null;
		String sql = "SELECT * FROM CONFIG_ELEMENT WHERE ELEMENT_CODE='"+elementCode+"' AND CATALOG_CODE='"+catalogCode+"'";
		list = super.listBySql(sql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#showSelectInfo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List showSelectInfo(String showType,String codeName,String showSql) throws BusiException{
		List list = null;
		String querySql="";
		//取模板配置所有的字段
		if(showType.equals("code"))
			querySql = " select Item_Code,Item_Name,Relative_Name,Attribute_Name,Control_Condition1,Control_Condition2"+
						  " from CONFIG_REGION " +
						  " where Region_Code='"+codeName+"' and Use_State='1'"+
						  " order by Order_No";
		else if(showType.equals("sql"))
			querySql=showSql;
		list = super.listBySql(querySql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#showMenuInfo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List showMenuInfo(String MenuID,String opCode,String product_type) throws BusiException{
		List list = null;
		String querySql="";
		querySql =  " select * from CONFIG_MENU "+
					" where Menu_ID like '"+MenuID+"%' and use_state='1' "+
				//	" and product_type='"+product_type+"'"+
					" and (menu_type='2' and (Show_Pic is not null and len(Show_Pic)>0))  "+
					" and exists (select 1 from toprole where op_code ='"+opCode+"' and CONFIG_MENU.relation_role like '%'+cast(role_id as nvarchar)+'%')"+
					" order by order_no";
		list = super.listBySql(querySql);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#listAllMenu(java.lang.String, java.lang.String)
	 */
	@Override
	public List listAllMenu(String roleID,String existFlag) throws BusiException{
		List list = null;
		String querySql="";
		if("NoExist".equals(existFlag))
			querySql =  " select * from CONFIG_MENU where relation_role not like '%"+roleID+"%' and use_state='1' order by order_no";
		else if("YesExist".equals(existFlag))
			querySql =  " select * from CONFIG_MENU where relation_role like '%"+roleID+"%' and use_state='1' order by order_no";		
		list = super.listBySql(querySql);
		return list;
	}		
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#queryPrintContentList(java.lang.String)
	 */
	@Override
	public List queryPrintContentList(String formCode) throws BusiException{
		List list = null;
		String querySql="";
		querySql = " select FC.*,FD.*"+
				   " from FORMPRINT_CATALOG FC,FORMPRINT_DETAIL FD " +
				   " where FC.Form_code=FD.Form_code"+
				   " and FC.Form_code='"+formCode+"' and FD.Use_state='1'" +
				   " order by FD.Order_No";
		list = super.listBySql(querySql);
		return list;
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#getMenuInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List getMenuInfo(String MenuID,String MenuType,String opCode,String productType) throws BusiException{
		List list = null;
		String querySql="";
		//取系统主菜单所有的信息
		if(MenuType.equals("1"))
			querySql = " select * from CONFIG_MENU where Menu_Type='1' and Use_State='1' "+
				//	   " and product_type='"+productType+"'"+
					   " and exists (select 1 from toprole where op_code ='"+opCode+"' and CONFIG_MENU.relation_role like '%'+cast(role_id as nvarchar)+'%')"+
					   " order by Order_No";
		else if(MenuType.equals("2"))
			querySql = " select * from CONFIG_MENU where ((Menu_ID like '"+MenuID+"%' and Menu_Type='2') or Menu_ID='"+MenuID+"')"+
					   " and exists (select 1 from toprole where op_code ='"+opCode+"' and CONFIG_MENU.relation_role like '%'+cast(role_id as nvarchar)+'%')"+
					   " and Use_State='1' "+
				//	   " and product_type='"+productType+"' "+
					   " order by Order_No";
		list = super.listBySql(querySql);
		return list;
	}			
	
	//@Override
	/* (non-Javadoc)
	 * @see enfo.crm.system.ConfigLocal#remove()
	 */
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
