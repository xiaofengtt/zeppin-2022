package enfo.crm.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.system.ConfigLocal;

public class ConfigUtil {

	/**
	 * 获取下拉框、radio框值名称
	 * @param valueType 下拉框类型
	 * @param propertyValue 下拉框值
	 * @param value_content 下拉框内容
	 * @return
	 * @throws Exception 
	 */
	public static String getParamName(String valueType,String propertyValue,String value_content) throws Exception{
		String paramValueName = "";
		if(valueType.equals("1")){//在config_region中获取
			String[] queryId ={"item_code","region_code"};
			String[] queryValue ={propertyValue,value_content};
			paramValueName = getPropertyNameByIdValue("config_region","item_name",queryId,queryValue);
		}else if(valueType.equals("2")){//通过SQL语句取
			String queryName="";
			String valueName="";
			String tableName="";
			if(!value_content.equals("")){
				value_content=value_content.toLowerCase();
				int selectIndex =value_content.indexOf("select")+"select".length()+1;
				int dotIndex = value_content.indexOf(",");
				int fromIndex = value_content.indexOf("from");
				int whereIndex =value_content.indexOf("where");
				if(whereIndex == -1){
					whereIndex = value_content.length();
				}
				queryName = value_content.substring(selectIndex,dotIndex).trim();
				valueName = value_content.substring(dotIndex+1,fromIndex).trim();
				tableName = value_content.substring(fromIndex+4,whereIndex).trim();
				paramValueName =getPropertyNameById(tableName,valueName,queryName,propertyValue);
			}
		}else if("3".equals(valueType)){//在TDICTPARAM中获取
			String[] queryId ={"TYPE_VALUE","TYPE_ID"};
			String[] queryValue ={propertyValue,value_content};
			paramValueName = getPropertyNameByIdValue("TDICTPARAM","TYPE_CONTENT",queryId,queryValue);
		}
		return paramValueName;
	}
	
	/**
	 * 组合checkbox名称
	 * @param valueType 数据查询类型
	 * @param propertyValue  value值
	 * @param value_content 查询内容
	 * @return
	 * @throws Exception
	 */
	public static String getCheckboxParamName(String valueType,String propertyValue,String value_content) throws Exception{
		String paramValueName = "";
		String paramValueReturnName="";
		if(!propertyValue.equals("")){
		String[] strs = propertyValue.split(",");	
		for(int j =0;j<strs.length;j++){
				String queryName="";
				String valueName="";
				String tableName="";
				if(valueType.equals("1")){
					String[] queryId ={"item_code","region_code"};
					String[] queryValue ={strs[j],value_content};
					paramValueName = ConfigUtil.getPropertyNameByIdValue("config_region","item_name",queryId,queryValue);
				}else{
					if(!value_content.equals("")){
						int selectIndex =value_content.indexOf("select")+"select".length()+1;
						int dotIndex = value_content.indexOf(",");
						int fromIndex = value_content.indexOf("from");
						int whereIndex =value_content.indexOf("where");
						if(whereIndex == -1){
							whereIndex = value_content.length();
						}
						queryName = value_content.substring(selectIndex,dotIndex).trim();
						valueName = value_content.substring(dotIndex+1,fromIndex).trim();
						tableName = value_content.substring(fromIndex+4,whereIndex).trim();
						paramValueName =ConfigUtil.getPropertyNameById(tableName,valueName,queryName,strs[j]);
					}
				}
			if(j == strs.length-1){
				paramValueReturnName =paramValueReturnName + paramValueName;
			}else{
				paramValueReturnName =paramValueReturnName + paramValueName + ",";
			}
		}
	}
		return paramValueReturnName;
	}
	
	/**
	 * 查询页面显示的有效元素集合
	 * @param identity_code 主键ID值
	 * @param interfaceType_code 界面类型编码
	 * @return
	 * @throws BusiException
	 */
	public static List getFieldShowList(String interfaceType_code,Integer identity_code,String query_flag) throws BusiException{
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		List fieldTdList = null;
		String edit_flag="";
		if(identity_code.intValue() == 0){//新增
			edit_flag ="1";
		}else{//修改
			edit_flag = "2";
		}
		fieldTdList = listVaildFieldOrderNO(interfaceType_code,edit_flag,query_flag);//查询各种类型的有效字段
		
		return fieldTdList;
	}
	
	/**
	 * 查看页面需要显示的字段
	 * @param configLocal
	 * @param interfaceType_code 界面类型编码
	 * @return
	 * @throws BusiException
	 */
	public static List getFieldViewList(ConfigLocal configLocal,String interfaceType_code) throws BusiException{
		List fieldTdList = null;
		fieldTdList = configLocal.listVaildViewFieldOrderNO(interfaceType_code);//查询所有查看页面需要显示的字段
		
		return fieldTdList;
	}
	
	/**
	 * 新增页面信息(主键值自动生成)
	 * @param table_code 表名
	 * @param fieldTdList 页面显示的元素集合
	 * @param request
	 * @param addType 1：主键不保存；2：主键保存
	 * @throws Exception 
	 */
	public static void addInfo(String table_code,List fieldTdList,HttpServletRequest request) throws Exception{
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		String[] addFieldColStrs = new String[fieldTdList.size()];//新增字段数组
		String[] addFieldNameStrs = new String[fieldTdList.size()];//新增字段名称数组
		String[] showTypeStrs = new String[fieldTdList.size()];//显示格式数组
		String[] updateFlagStrs = new String[fieldTdList.size()];//是否更新数组
		for(int i=0;i<fieldTdList.size();i++){
			Map addFieldMap = (Map)fieldTdList.get(i);
			updateFlagStrs[i] = Utility.trimNull(addFieldMap.get("UPDATE_FLAG"));
			String fieldColName = Utility.trimNull(addFieldMap.get("INTERFACEFIELD_CODE"));
			String showType = Utility.trimNull(addFieldMap.get("SHOW_TYPE"));
			String editType = Utility.trimNull(addFieldMap.get("EDIT_TYPE"));
			showTypeStrs[i] = showType;
			addFieldColStrs[i]=fieldColName;//字段编码
			if(showType.equals("5")&&editType.equals("1")){//货币金额去掉逗号
				addFieldNameStrs[i]=request.getParameter(fieldColName).replaceAll(",","");//字段编码值
			}else if(showType.equals("1")&&editType.equals("4")){//复选框组合
					if(request.getParameterValues(fieldColName)!=null){
						String[] values =request.getParameterValues(fieldColName);
						String paramValue="";
						for(int j=0;j<values.length;j++){
							paramValue = paramValue + values[j] + ",";
						}
						addFieldNameStrs[i] = paramValue.substring(0,paramValue.length()-1);
					}else{
						addFieldNameStrs[i]="";
					}
			}else{
					addFieldNameStrs[i]=request.getParameter(fieldColName);
			}
		}
		addInterfaceField(table_code,showTypeStrs,addFieldColStrs,addFieldNameStrs,updateFlagStrs);//新增方法
	}
	
	/**
	 * 新增页面信息(主键值自动生成)
	 * @param configInterfaceList 页面显示的元素集合
	 * @param request
	 * @param addType 1：主键不保存；2：主键保存
	 * @throws Exception 
	 */
	public static void addInfo(List configInterfaceList,HttpServletRequest request) throws Exception{
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		List fieldTdList = ConfigUtil.getInterfaceValueList(configInterfaceList,"QueryAllInfoShow");//获取修改页面所有的有效元素集合
		String table_code = ConfigUtil.getInterfaceValue(configInterfaceList,"TableName");
		String[] addFieldColStrs = new String[fieldTdList.size()];//新增字段数组
		String[] addFieldNameStrs = new String[fieldTdList.size()];//新增字段名称数组
		String[] showTypeStrs = new String[fieldTdList.size()];//显示格式数组
		String[] updateFlagStrs = new String[fieldTdList.size()];//是否更新数组
		for(int i=0;i<fieldTdList.size();i++){
			Map addFieldMap = (Map)fieldTdList.get(i);
			updateFlagStrs[i] = Utility.trimNull(addFieldMap.get("UPDATE_FLAG"));
			String fieldColName = Utility.trimNull(addFieldMap.get("INTERFACEFIELD_CODE"));
			String showType = Utility.trimNull(addFieldMap.get("SHOW_TYPE"));
			String editType = Utility.trimNull(addFieldMap.get("EDIT_TYPE"));
			showTypeStrs[i] = showType;
			addFieldColStrs[i]=fieldColName;//字段编码
			if(showType.equals("5")&&editType.equals("1")){//货币金额去掉逗号
				addFieldNameStrs[i]=Utility.trimNull(request.getParameter(fieldColName)).replaceAll(",","");//字段编码值
			}else if(showType.equals("1")&&editType.equals("4")){//复选框组合
					if(request.getParameterValues(fieldColName)!=null){
						String[] values =request.getParameterValues(fieldColName);
						String paramValue="";
						for(int j=0;j<values.length;j++){
							paramValue = paramValue + values[j] + ",";
						}
						addFieldNameStrs[i] = paramValue.substring(0,paramValue.length()-1);
					}else{
						addFieldNameStrs[i]="";
					}
			}else{
					addFieldNameStrs[i]=Utility.trimNull(request.getParameter(fieldColName));
			}
		}
		addInterfaceField(table_code,showTypeStrs,addFieldColStrs,addFieldNameStrs,updateFlagStrs);//新增方法
	}
		
	/**
	 * 修改页面信息(主键值不修改)
	 * @param table_code 表名
	 * @param fieldTdList 页面有效显示元素集合
	 * @param request
	 * @param identityCode
	 * @param identity_code
	 * @throws Exception 
	 */
	public static void modInfo(String table_code,List fieldTdList,HttpServletRequest request,String identityCode,Integer identity_code) throws Exception{
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		String[] modFieldColStrs = new String[fieldTdList.size()];//修改字段数组
		String[] modFieldNameStrs = new String[fieldTdList.size()];//修改字段值数组
		String[] showTypeStrs = new String[fieldTdList.size()];//显示格式数组
		String[] updateFlagStr = new String[fieldTdList.size()];
		
		for(int i=0;i<fieldTdList.size();i++){
			Map modFieldMap = (Map)fieldTdList.get(i);
			String fieldColName = Utility.trimNull(modFieldMap.get("INTERFACEFIELD_CODE"));
			String showType = Utility.trimNull(modFieldMap.get("SHOW_TYPE"));
			String editType = Utility.trimNull(modFieldMap.get("EDIT_TYPE"));
			String update_flag = Utility.trimNull(modFieldMap.get("UPDATE_FLAG"));
				
			modFieldColStrs[i] = fieldColName;
			showTypeStrs[i] = showType;
			updateFlagStr[i] = update_flag;
			
			if(showType.equals("5")&&editType.equals("1")){// 货币类型
				modFieldNameStrs[i]=request.getParameter(fieldColName).replaceAll(",","");
			}else if(showType.equals("1")&&editType.equals("4")){//checkbox时组装
				if(request.getParameterValues(fieldColName)!=null){
					String[] values =request.getParameterValues(fieldColName);
					String paramValue="";
					for(int j=0;j<values.length;j++){
						paramValue = paramValue + values[j] + ",";
					}
					modFieldNameStrs[i] = paramValue.substring(0,paramValue.length()-1);
				}else{//没选任何任何值为“”
					modFieldNameStrs[i]="";
				}
			}else{
					modFieldNameStrs[i]=request.getParameter(fieldColName);
			}
		}
		modInterfaceField(table_code,showTypeStrs,modFieldColStrs,modFieldNameStrs,identityCode,String.valueOf(identity_code),updateFlagStr);//修改方法
	}

	/**
	 * 修改页面信息(主键值不修改)
	 * @param table_code 表名
	 * @param fieldTdList 页面有效显示元素集合
	 * @param request
	 * @param identityCode
	 * @param identity_code
	 * @throws Exception 
	 */
	public static void modInfo(List configInterfaceList,HttpServletRequest request,String identity_code) throws Exception{
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		List fieldTdList = ConfigUtil.getInterfaceValueList(configInterfaceList,"QueryAllInfoShow");//获取修改页面所有的有效元素集合
		String table_code = ConfigUtil.getInterfaceValue(configInterfaceList,"TableName");	
		String identityCode = ConfigUtil.getInterfaceValue(configInterfaceList,"TableKey");
		String[] modFieldColStrs = new String[fieldTdList.size()];//修改字段数组
		String[] modFieldNameStrs = new String[fieldTdList.size()];//修改字段值数组
		String[] showTypeStrs = new String[fieldTdList.size()];//显示格式数组
		String[] updateFlagStr = new String[fieldTdList.size()];
		
		for(int i=0;i<fieldTdList.size();i++){
			Map modFieldMap = (Map)fieldTdList.get(i);
			String fieldColName = Utility.trimNull(modFieldMap.get("INTERFACEFIELD_CODE"));
			String showType = Utility.trimNull(modFieldMap.get("SHOW_TYPE"));
			String editType = Utility.trimNull(modFieldMap.get("EDIT_TYPE"));
			String update_flag = Utility.trimNull(modFieldMap.get("UPDATE_FLAG"));
				
			modFieldColStrs[i] = fieldColName;
			showTypeStrs[i] = showType;
			updateFlagStr[i] = update_flag;
			
			if(showType.equals("5")&&editType.equals("1")){// 货币类型
				modFieldNameStrs[i]=Utility.trimNull(request.getParameter(fieldColName)).replaceAll(",","");
			}else if(showType.equals("1")&&editType.equals("4")){//checkbox时组装
				if(request.getParameterValues(fieldColName)!=null){
					String[] values =request.getParameterValues(fieldColName);
					String paramValue="";
					for(int j=0;j<values.length;j++){
						paramValue = paramValue + values[j] + ",";
					}
					modFieldNameStrs[i] = paramValue.substring(0,paramValue.length()-1);
				}else{//没选任何任何值为“”
					modFieldNameStrs[i]="";
				}
			}else {
					modFieldNameStrs[i]=Utility.trimNull(request.getParameter(fieldColName));
			}
		}
		modInterfaceField(table_code,showTypeStrs,modFieldColStrs,modFieldNameStrs,identityCode,identity_code,updateFlagStr);//修改方法
	}
	
	/**
	 * 修改页面信息(主键值不修改)
	 * @param table_code 表名
	 * @param fieldTdList 页面有效显示元素集合
	 * @param request
	 * @param identityCode
	 * @param identity_code
	 * @throws Exception 
	 */
	public static void addLog(String table_code,List fieldTdList,List fieldModifyList,HttpServletRequest request,String identityCode,Integer identity_code,String catalog_Code,String userID,String deptID) throws Exception{
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		String[] modFieldColStrs = new String[fieldTdList.size()];//修改字段数组
		String[] modFieldNameStrs = new String[fieldTdList.size()];//修改字段值数组
		String[] showTypeStrs = new String[fieldTdList.size()];//显示格式数组
		String[] updateFlagStr = new String[fieldTdList.size()];
		
		//取保存后的值
		for(int i=0;i<fieldTdList.size();i++){
			Map modFieldMap = (Map)fieldTdList.get(i);
			String fieldColName = Utility.trimNull(modFieldMap.get("INTERFACEFIELD_CODE"));
			String fieldName = Utility.trimNull(modFieldMap.get("INTERFACEFIELD_NAME"));
			String showType = Utility.trimNull(modFieldMap.get("SHOW_TYPE"));
			String editType = Utility.trimNull(modFieldMap.get("EDIT_TYPE"));
			String update_flag = Utility.trimNull(modFieldMap.get("UPDATE_FLAG"));
				
			modFieldColStrs[i] = fieldColName;
			showTypeStrs[i] = showType;
			updateFlagStr[i] = update_flag;
			String afterValue="",beforeValue="";
			
			if(showType.equals("5")&&editType.equals("1")){// 货币类型
				afterValue=Utility.trimNull(request.getParameter(fieldColName)).replaceAll(",","");
				
			}else if(showType.equals("1")&&editType.equals("4")){//checkbox时组装
				if(request.getParameterValues(fieldColName)!=null){
					String[] values =request.getParameterValues(fieldColName);
					String paramValue="";
					for(int j=0;j<values.length;j++){
						paramValue = paramValue + values[j] + ",";
					}
					afterValue = paramValue.substring(0,paramValue.length()-1);
				}else{//没选任何任何值为“”
					afterValue="";
				}
			}else {
				afterValue=Utility.trimNull(request.getParameter(fieldColName));
			}
			//对比保存前的值
			Map modShowData = (Map)fieldModifyList.get(0);
			beforeValue = Utility.trimNull(modShowData.get(fieldColName));
			if(!beforeValue.equals(afterValue))
				modLogInfo(catalog_Code,table_code,identityCode,String.valueOf(identity_code),fieldColName,fieldName,beforeValue,afterValue,userID,deptID);//修改方法
		}
	}
	
	/**
	 * 判断文本框的类型
	 * @param show_type 显示格式
	 * @param edit_type 编辑类型
	 * @return
	 */
	public static String getInputType(String show_type,String edit_type){
		String inputType = "text";
		if(show_type.equals("1")&&edit_type.equals("2")){
			inputType = "selectbox";//下拉框
		}else if(show_type.equals("1")&&edit_type.equals("3")){
			inputType = "radio";//单选框
		}else if (show_type.equals("1")&&edit_type.equals("4")){
			inputType = "checkbox";//多选框
		}else if (show_type.equals("2")&&edit_type.equals("5")){
			inputType = "date";//日期框
		}else if (show_type.equals("1")&&edit_type.equals("7")){
			inputType = "textarea";//备注框
		}else if(show_type.equals("5")&&edit_type.equals("1")){
			inputType ="money";//货币框
		}else if(show_type.equals("7")&&edit_type.equals("8")){
			inputType = "subarea";//分区
		}else if(show_type.equals("3")&&edit_type.equals("1")){
			inputType = "number";//数字
		}else if(show_type.equals("4")&&edit_type.equals("1")){
			inputType = "integer";//整形
		}else if(show_type.equals("6")&&edit_type.equals("1")){
			inputType = "icon";//图标
		}
		return inputType;
	}
	
	/**
	 * 判断文本框是否只读 
	 * @param identity_code 主键值
 	 * @param readonly_flag 只读标识值
	 * @return
	 */
	public static boolean getReadonlyFlag(Integer identity_code,String readonly_flag){
		boolean readonlyFlag = false;
		if(!readonly_flag.equals("")){
			if(identity_code.intValue() == 0 && readonly_flag.indexOf("1")!=-1){//新增只读
				readonlyFlag = true;
			}else if(identity_code.intValue() != 0 && readonly_flag.indexOf("2")!=-1){//修改只读
				readonlyFlag = true;
			}
		}
		return readonlyFlag;
	}
	
	/**
	 * 获取对齐方式类型
	 * @param align_type
	 * @return
	 */
	public static String getAlignType(String align_type){
		if(align_type.equals("2")){
			align_type="style='text-align:center'";
		}else if(align_type.equals("3")){
			align_type="style='text-align:right'";
		}else{
			align_type="style='text-align:left'";
		}
		return align_type;
	}
	
	/**
	 * 获取修改页面元素值
	 * @param modShowDataList 修改记录集合
	 * @param inputType 元素框类型
	 * @param interfaceField_code 字段编码
	 * @return
	 */
	public static String getTableValue(List modShowDataList,String inputType,String interfaceField_code){
		String tableValue="";
		if(modShowDataList.size()>0){
			Map modShowData = (Map)modShowDataList.get(0);
			tableValue = Utility.trimNull(modShowData.get(interfaceField_code.toUpperCase()));
			if(inputType.equals("money")){
				if(!"".equals(tableValue)){
					tableValue = ConfigUtil.MoneyTransform(tableValue);//货币转化
				}
			}
		}
		return tableValue;
	}
	
	/**
	 * 获取元素值对应的名称
	 * @param inputType
	 * @param tableValue
	 * @return
	 * @throws Exception 
	 */
	public static String getTableValueName(String inputType,String value_type,String tableValue,String value_content) throws Exception{
		String tableValueName = "";
		if(inputType.equals("selectbox")||inputType.equals("radio")){
			tableValueName =getParamName(value_type,tableValue,value_content);
			return tableValueName;
		}else if(inputType.equals("checkbox")){
			tableValueName = getCheckboxParamName(value_type,tableValue,value_content);
			return tableValueName;
		}
		
		return tableValue;
	}
	
	/**
	 * 判断是否必输
	 * @param identity_code 主键值
	 * @param required_flag 必输值
	 * @return
	 */
	public static String getRequiredFlag(String required_flag){
		String tdRequiredStyle1= "";
		if(!"".equals(required_flag)){
			if(required_flag.equals("1")){//是
				tdRequiredStyle1="color: red;";
			}
		}
		return tdRequiredStyle1;
	}
	
	/**
	 * 判断标签的名称是否可见
	 * @param identity_code 主键值
	 * @param visibled_flag 可见标识值
	 * @return
	 */
	public static String getVisibledFlag(Integer identity_code,String visibled_flag){
		String tdVisibledStyle="";
		if(!"".equals(visibled_flag)){
			if(identity_code.intValue()==0 &&(visibled_flag.indexOf("1")==-1)){//新增可见
				tdVisibledStyle ="style='display=none;'";
			}else if(identity_code.intValue()!=0&&(visibled_flag.indexOf("2")==-1)){//修改可见
				tdVisibledStyle ="style='display=none;'";
			}
		}else{
			tdVisibledStyle ="style='display=none;'";
		}
		return tdVisibledStyle;
	}
	
	/**
	 * 组合样式
	 * @param styleStr 样式数组集
	 * @return
	 */
	public static String getCombinationStyle(String[] styleStr){
		String styleTd="";
		for(int i=0;i<styleStr.length;i++){
			if(!"".equals(styleStr[i])){
				if(styleTd.equals("")){
					styleTd="style='";
				}
				styleTd = styleTd + styleStr[i];
			}
		}
		if(styleTd.length()>0){
			styleTd = styleTd +"'";
		}
		return styleTd;
	}
	
	/**
	 * 获取文本框高度、宽度样式
	 * @param show_width
	 * @param show_height
	 * @return
	 */
	public static String getInputStyle(String show_width,String show_height){
		String inputStyle="";
		if(!"".equals(show_width)){
			inputStyle = "style='width:"+ show_width +"px;'";
		}
		if(!"".equals(show_height)){
			if(!"".equals(inputStyle)){
				inputStyle = inputStyle.substring(0,inputStyle.length()-1)+"height:"+show_height+"px;'";
			}else{
				inputStyle = "style='height:"+ show_height +"px;'";
			}
		}
		return inputStyle;
	}
	
	/**
	 * 获取字体样式
	 * @param colName 字段名称
	 * @param rowMapTd map集
	 * @return
	 */
	public static String getFontStyle(String colName,Map rowMapTd){
		String fontStyle="";
		String colValue="";
		if(colName.equals("OVERSTRIKING_FLAG")){//字体加粗
			colValue = Utility.trimNull(rowMapTd.get(colName));
			if(!colValue.equals("")){
				if(colValue.equals("1")){
					fontStyle = "bold";//加粗
				}
			}
			return fontStyle;
		}else if(colName.equals("FONT_COLOR")){//字体颜色
			colValue = Utility.trimNull(rowMapTd.get(colName));
			fontStyle=colValue;
			return fontStyle;
		}else if(colName.equals("FONT_SIZE")){//字体大小
			colValue = Utility.trimNull(rowMapTd.get(colName));
			if(colValue.equals("")||colValue.equals("1")){
				fontStyle ="100%";
			}else if(colValue.equals("2")){
				fontStyle ="120%";
			}else if(colValue.equals("3")){
				fontStyle ="140%";
			}else if(colValue.equals("4")){
				fontStyle ="160%";
			}
			return fontStyle;
		}else if(colName.equals("FONT_FAMILY")){//字体类型
			colValue = Utility.trimNull(rowMapTd.get(colName));
			if(colValue.equals("")){//空时设为宋体
				fontStyle ="宋体";
			}else{
				fontStyle=colValue;
			}
			return fontStyle;
		}
		return "";
	}
	
	
	/**
	 * 查询条件处理块
	 * @param request 请求
	 * @param queryFieldList 查询字段数据集
	 * @param queryFieldColStr 字段集
	 * @param queryFieldNameStr 字段名称集
	 * @param queryInputTypeStr 数据框类型集
	 * @param queryValueTypeStr 下拉框数据源类型集
	 * @param queryValueContentStr 数据查询语句集
	 * @param queryConditionTypeStr 查询类型集
	 * @param queryValueStr 查询字段页面值
	 * @param queryConditionValueStr 需要真正查询的类别值
	 */
	public static void queryConditionBlock(HttpServletRequest request,List queryFieldList,String[] queryFieldColStr,String[] queryFieldNameStr,String[] queryInputTypeStr,
											String[] queryValueTypeStr,String[] queryValueContentStr,String[] queryConditionTypeStr,String[] queryValueStr,String[] queryConditionValueStr){
		for(int i=0;i<queryFieldColStr.length;i++){
			Map  queryMap = (Map)queryFieldList.get(i);
			String queryField_code =Utility.trimNull(queryMap.get("INTERFACEFIELD_CODE"));
			String queryField_name =Utility.trimNull(queryMap.get("INTERFACEFIELD_NAME"));
			String show_type =Utility.trimNull(queryMap.get("SHOW_TYPE"));
			String edit_type =Utility.trimNull(queryMap.get("EDIT_TYPE"));
			String value_type =Utility.trimNull(queryMap.get("VALUE_TYPE"));
			String value_content =Utility.trimNull(queryMap.get("VALUE_CONTENT"));
			String condition_Type = Utility.trimNull(queryMap.get("CONDITION_TYPE"));
			queryFieldColStr[i] = queryField_code;
			queryFieldNameStr[i] = queryField_name;
			queryInputTypeStr[i] = ConfigUtil.getInputType(show_type,edit_type);
			queryValueTypeStr[i] = value_type;
			queryValueContentStr[i] =value_content;
			queryConditionTypeStr[i] = condition_Type;
		}
		for(int i=0;i<queryValueStr.length;i++){
			queryConditionValueStr[i] = Utility.trimNull(request.getParameter(queryFieldColStr[i]+"type"));
			if(queryConditionValueStr[i].equals("7")){//如果为两者之间时把两个框中的值用逗号组装
				String condition1=Utility.trimNull(request.getParameter(queryFieldColStr[i]+"one"+i));
				String condition2=Utility.trimNull(request.getParameter(queryFieldColStr[i]+"two"+i));
				queryValueStr[i]=condition1 + "," + condition2;
			}else if(queryInputTypeStr[i].equals("checkbox")){
				if(request.getParameterValues(queryFieldColStr[i])!=null){
					String[] values =request.getParameterValues(queryFieldColStr[i]);
					String paramValue="";
					for(int j=0;j<values.length;j++){
						paramValue = paramValue + values[j] + ",";
					}
					queryValueStr[i] = paramValue.substring(0,paramValue.length()-1);
				}else{
					queryValueStr[i]="";
				}
			}else{
				queryValueStr[i] = Utility.trimNull(request.getParameter(queryFieldColStr[i]));
			}
			
		}
	}
	
	/**
	 * 获取查询字段和字段名称
	 * @param tableFieldList 页面显示的数据集
	 * @param fieldStrs 字段集
	 * @param fieldNameStrs 字段名称集
	 * @throws BusiException
	 */
	public static void pageValidColShowBlock(List tableFieldList,String[] fieldStrs,String[] fieldNameStrs) throws BusiException{
		List list= new ArrayList();
		for(int i=0;i<tableFieldList.size();i++){
			Map  rowMap = (Map)tableFieldList.get(i);
			fieldStrs[i] = Utility.trimNull(rowMap.get("INTERFACEFIELD_CODE"));
			fieldNameStrs[i] = Utility.trimNull(rowMap.get("INTERFACEFIELD_NAME"));
			list.add(tableFieldList.get(i));
		}
	}
	
	/**
	 * 获取字体颜色
	 * @param font_color 字体颜色
	 * @param required_flag 是否必输
	 * @return
	 */
	public static String getFontColor(String font_color,String required_flag){
		if(font_color.equals("")){
			if(required_flag.equals("1")){
				font_color = "red";
			}else{
				font_color ="#333";
			}
		}
		return font_color;
	}
	
	
	/**
	 * 查询元素域信息
	 * @param dict 域编码
	 * @param value 默认值
	 * @return
	 * @throws Exception
	 */
	public static String getDictParamConfig(String dict, String value)
	throws Exception {
		StringBuffer sb = new StringBuffer(200);
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		List list = listRegionParams(dict);
		sb.append("<option value=\"\">请选择</option>");
		for(int i =0;i<list.size();i++){
				Map mapRow =(Map)list.get(i);
				appendConfigParam(
				sb,
				Utility.trimNull(mapRow.get("ITEM_CODE")),
				Utility.trimNull(mapRow.get("ITEM_NAME")),
				value);
		}
		System.out.println("-----------------------------------"+sb.toString());
		return sb.toString();
	}
	
	/**
	 * 通过sql语句显示下拉框信息
	 * @param sql sql语句
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getParamSelectSql(String sql, String value)
	throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		sb.append("<option value=\"\">请选择</option>");
		try {
			while (rs.next())
				appendConfigParam(
					sb,
					rs.getString(1),
					rs.getString(2),
					value);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		System.out.println(new Timestamp(System.currentTimeMillis())+":"+sql);
		return sb.toString();
	}

	/**
	 * 查询框时的下拉框组装
	 * @param conditionType 查询类型值
	 * @param defaultValue 默认值
	 * @return
	 * @throws Exception
	 */
	public static String getQuerySelectSql(String conditionType,String defaultValue)
	throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql ="select item_code,item_name from config_region where region_code='QueryType010' and charindex(item_code,'"+conditionType+"')!=0";
		ResultSet rs = stmt.executeQuery(sql);
		try {
			while (rs.next())
				appendConfigParam(
					sb,
					rs.getString(1),
					rs.getString(2),
					defaultValue);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		System.out.println(new Timestamp(System.currentTimeMillis())+":"+sql);
		return sb.toString();
	}
	
	/**
	 * 下拉框语句组装
	 * @param sb
	 * @param value
	 * @param text
	 * @param defvalue
	 */
	public static void appendConfigParam(
			StringBuffer sb,
			String value,
			String text,
			String defvalue) {
			sb.append("<option");
			if (defvalue != null)
				sb.append(
					(value.trim().compareTo(defvalue.trim()) == 0)
						? " selected"
						: "");
			sb.append(" value=\"" + value + "\">");
			sb.append(text);
			sb.append("</option>");
		}
	
	/**
	 * 生成Radio
	 * @param dict 域编码
	 * @param value 默认值
	 * @param name name属性
	 * @param defaultValue 没设默认值时默认显示第几个
	 * @return
	 * @throws Exception
	 */
	public static String getDictParamRadios(String dict, String value,String name,String defaultValue)
	throws Exception {
		StringBuffer sb = new StringBuffer(200);
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		List list = listRegionParams(dict);
		for(int i =0;i<list.size();i++){
				Map mapRow =(Map)list.get(i);
				if(Utility.trimNull(value).equals("")){//没值时设置默认值
						value = defaultValue;
				}
				appendRadios(
				sb,
				Utility.trimNull(mapRow.get("ITEM_CODE")),
				Utility.trimNull(mapRow.get("ITEM_NAME")),
				value,
				name);
		}
		
		return sb.toString();
	}
	/**
	 * 生成Radio
	 * @param dict 域编码
	 * @param value 默认值
	 * @param name name属性
	 * @param defaultValue 没设默认值时默认显示第几个
	 * @return
	 * @throws Exception
	 */
	public static String getDictParamDate(String dict, String value,String name,String defaultValue)
	throws Exception {
		StringBuffer sb = new StringBuffer(200);
		sb.append("<INPUT TYPE='button' value='▼' class=selectbtn onclick='javascript:CalendarWebControl.show(theform.DATE,theform.DATE.value,this);'> ");
		return sb.toString();
	}
	
	/**
	 * 通过SQL语句组装radio
	 * @param sql
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getParamRadioSql(String sql, String value,String name,String defaultValue)
	throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(Utility.trimNull(value).equals("")){//没值时设置默认值
			value = defaultValue;
		}
		try {
			while (rs.next())
				appendRadios(
					sb,
					rs.getString(1),
					rs.getString(2),
					value,
					name);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		System.out.println(new Timestamp(System.currentTimeMillis())+":"+sql);
		return sb.toString();
	}
	
	/**
	 * radio语句组装
	 * @param sb
	 * @param value
	 * @param text
	 * @param defvalue
	 * @param length
	 * @param name
	 */
	public static void appendRadios(
			StringBuffer sb,
			String value,
			String text,
			String defvalue,
			String name) {
			sb.append("<input");
			sb.append(" type=\"radio\" class=\"flatcheckbox\"");
			sb.append(" name=\"" + name + "\"");
			sb.append(" value=\"" + value + "\"");
			if (defvalue != null)
			{
				sb.append(
					(value.trim().compareTo(defvalue.trim()) == 0)
						? " checked />"
						: " />");
			}
			
			sb.append(text);
		}
	
	/**
	 * 生成多选框(checkBox)
	 * @param dict
	 * @param value
	 * @param name
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	public static String getDictParamCheckBoxs(String dict, String value,String name,String defaultValue)
	throws Exception {
		StringBuffer sb = new StringBuffer(300);
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		List list = listRegionParams(dict);
		for(int i =0;i<list.size();i++){
				Map mapRow =(Map)list.get(i);
				if(Utility.trimNull(value).equals("")){//没值时设置默认值
						value = defaultValue;
				}
				appendCheckBoxs(
				sb,
				Utility.trimNull(mapRow.get("ITEM_CODE")),
				Utility.trimNull(mapRow.get("ITEM_NAME")),
				value,
				name);
		}
		
		return sb.toString();
	}
	
	/**
	 * 通过SQL语句组装多选框
	 * @param sql 查询语句
	 * @param value
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String getParamCheckboxSql(String sql, String value,String name)
	throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		try {
			while (rs.next())
				appendCheckBoxs(
					sb,
					rs.getString(1),
					rs.getString(2),
					value,
					name);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		System.out.println(new Timestamp(System.currentTimeMillis())+":"+sql);
		return sb.toString();
	}
	
	
	public static void appendCheckBoxs(StringBuffer sb,String value,String text,String defvalue,String name) {
			sb.append("<input");
			sb.append(" type=\"checkbox\" class=\"flatcheckbox\"");
			sb.append(" name=\"" + name + "\"");
			sb.append(" value=\"" + value + "\"");
			if (defvalue != null)
			{
				String[] strs =defvalue.split(","); 
				for(int i=0;i<strs.length;i++){
					sb.append(
							(value.trim().compareTo(strs[i].trim()) == 0)
								? " checked"
								: " ");
				}
			}
			sb.append(" />");
			sb.append(text);
		}
	

	
	/**
	 * 从要素域表获取有项目名称
	 * @param region_code
	 * @param item_code
	 * @return
	 * @throws Exception
	 */
	public static String getValueOfConfigRegion(String region_code,String item_code)
	throws Exception {
		String item_name="";
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		List list = listRegionParamName(region_code,item_code);
		if(list.size()>0)
		{
			Map mapRow =(Map)list.get(0);
			item_name = mapRow.get("ITEM_NAME").toString();
		}
		
			return item_name;
	}
	
	/**
	 * 通过唯一字段queryId查找某个表需要的valueName
	 * @param tableName 表名
	 * @param valueName 需要查询的表属性名
	 * @param queryId 查询条件的表属性名
	 * @param queryValue 查询条件值
	 * @return
	 * @throws Exception
	 */
	public static String getPropertyNameById(String tableName, String valueName,String queryId,String queryValue)
	throws Exception {
		String sql ="select " + valueName + " from " + tableName + " where " + queryId + " ='" + queryValue +"'";
		String returnValue=getSqlResult(sql);
		return returnValue;
	}

	/**
	 * 通过where条件查找某个表需要的valueName
	 * @param tableName 表名
	 * @param valueName 需要查询的表属性名
	 * @param wherecondition 查询条件值
	 * @return
	 * @throws Exception
	 */
	public static String getDataBaseValue(String valueName,String tableName,String wherecondition)
	throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String returnValue="",value="";
		String sql ="select " + valueName + " from " + tableName + " where " + wherecondition;
		System.out.println(new Timestamp(System.currentTimeMillis())+":"+sql);
		ResultSet rs = stmt.executeQuery(sql);
		try {
			if (rs.next()){
				String[] s=valueName.split(",");
				for(int i=0;i<s.length;i++){
					if(i==0){
						value=Utility.trimNull(rs.getString(i+1));
						if(value.equals("")) returnValue="0";
						else 	returnValue=value;
					}else{
						value=Utility.trimNull(rs.getString(i+1));
						if(value.equals("")) value="0";					
						returnValue=returnValue+"@@"+value;
					}
				}
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return returnValue;
	}
	
	/**
	 * 通过多个查询条件查找某个字段值
	 * @param tableName
	 * @param valueName
	 * @param queryId
	 * @param queryValue
	 * @return
	 * @throws Exception
	 */
	public static String getPropertyNameByIdValue(String tableName, String valueName,String[] queryId,String[] queryValue) throws Exception {
		String returnValue="";
		if(queryId.length > 0){
			String sql ="select " + valueName + " from " + tableName + " where ";
			if(queryId.length == queryValue.length){
				for(int i=0;i<queryId.length;i++){
					sql = sql + queryId[i] +"='" + queryValue[i] + "'";
					if(i != queryId.length - 1){
						sql = sql + " and ";
					}
				}
				returnValue=getSqlResult(sql);
			}
		}
		return returnValue;
	}

	/**
	 * 通过sql语句获得结果
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static String getSqlResult(String sql)throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		System.out.println(new Timestamp(System.currentTimeMillis())+":"+sql);
		ResultSet rs = stmt.executeQuery(sql);
		try {
			if (rs.next() && rs.getString(1)!=null)
				sb.append(rs.getString(1));
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		
		return sb.toString();
	}
	
	
	
	/**
	 * 货币金额转化
	 * @param money 需要转化的金额
	 * @return
	 */
	public static String MoneyTransform(String money){
		String resultMoney = "";
		String IntegerMoney = "";
		String dotMoney = "";
		int dotIndex = money.indexOf(".");
		if(dotIndex == -1){
			dotIndex =money.length();
			dotMoney = ".00";
		}else{
			dotMoney = money.substring(dotIndex);
		}
		IntegerMoney = money.substring(0, dotIndex);
		
		resultMoney = moneyFormat(IntegerMoney);
		resultMoney = resultMoney + dotMoney;
		
		
		return resultMoney;
	}
	
	/**
	 * 增加逗号
	 * @param IntMoney 小数点之前的金额
	 * @return
	 */
	public static String moneyFormat(String IntMoney){
		String resultIntMoney = "";
		int num1 = IntMoney.length()/3;
		int num2 = IntMoney.length()%3;
		if(num2>0){
			num1=num1+1;
		}
		if(num2==0){
			num2= 3;
		}
		for(int i=0;i<num1;i++){
			if(i==0){
				if(num1 == 1){
					resultIntMoney = resultIntMoney + IntMoney.substring(0,num2);
				}else{
					resultIntMoney = resultIntMoney + IntMoney.substring(0,num2)+",";
				}
				IntMoney = IntMoney.substring(num2);
			}else{
				if(i==num1-1){//最后一组不加逗号
					resultIntMoney = resultIntMoney + IntMoney.substring(0, 3);
				}else{
					resultIntMoney = resultIntMoney + IntMoney.substring(0, 3) + ",";
					IntMoney = IntMoney.substring(3);
				}
			}
		}
		
		return resultIntMoney;
	}
	
	/**
	 * 查询页面类型表信息
	 * @param configLocal
	 * @param interfaceType_code 页面类别编码
	 * @return
	 * @throws BusiException
	 */
	public static String getIdentityCode(ConfigLocal configLocal,String interfaceType_code) throws BusiException{
		String identityCode = "";
		List list = configLocal.listTableIdentity(interfaceType_code);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			identityCode = Utility.trimNull(map.get("interfacefield_code"));
		}
		return identityCode;
	}
	
	/**
	 * 获得当前模板号下指定信息
	 * @param configInterfaceList 配置值
	 * @param valueType 获得值方法
	 * @return
	 * @throws BusiException
	 */
	public static String getInterfaceValue(List configInterfaceList,String valueType) throws BusiException{
		String returnValue = "",tempValue="";
		String tableCode = "",orderbyClause = "",groupbyClause = "",whereClause = "",whereParam = "",gridName = "",freeformName = "";
		String fromTable="",interfaceMode="",interfaceWidth="",interfaceHeight="",keyTable="",belongTable="";
		String[] fieldCode = new String[configInterfaceList.size()];
		String[] showType = new String[configInterfaceList.size()];
		String[] keyFlag = new String[configInterfaceList.size()];
		for(int i=0;i<configInterfaceList.size();i++){
			Map map = (Map)configInterfaceList.get(i);		
			tableCode = Utility.trimNull(map.get("TABLE_CODE"));
			fromTable = Utility.trimNull(map.get("FROM_TABLE"));
			orderbyClause = Utility.trimNull(map.get("ORDER_FIELD"));
			groupbyClause = Utility.trimNull(map.get("GROUPBY_FIELD"));
			whereClause = Utility.trimNull(map.get("WHERE_CONDITION"));
			whereParam = Utility.trimNull(map.get("WHERE_PARAM"));
			gridName = Utility.trimNull(map.get("GRID_NAME"));
			freeformName = Utility.trimNull(map.get("FREEFORM_NAME"));
			interfaceMode = Utility.trimNull(map.get("INTERFACE_MODE"));
			interfaceWidth = Utility.trimNull(map.get("INTERFACE_WIDTH"));
			interfaceHeight = Utility.trimNull(map.get("INTERFACE_HEIGHT"));
			belongTable= Utility.trimNull(map.get("BELONG_TABLE"));
			fieldCode[i] = Utility.trimNull(map.get("INTERFACEFIELD_CODE"));
			showType[i] = Utility.trimNull(map.get("SHOW_TYPE"));
			keyFlag[i] = Utility.trimNull(map.get("KEY_FLAG"));
			if("1".equals(keyFlag[i])) 
				if("".equals(belongTable)) keyTable=tableCode;
				else					   keyTable=belongTable;
		}
		//获得对应表结果主键
		if("TableKey".equals(valueType)){
			for(int i=0;i<keyFlag.length;i++){
				if("1".equals(keyFlag[i])){
					if("".equals(returnValue)){
						returnValue=fieldCode[i];
					}else{
						returnValue=returnValue+"@@"+fieldCode;
					}
				}
			}
		}else if("TableName".equals(valueType)){
			returnValue=tableCode;
		}else if("GridName".equals(valueType)){
			returnValue=gridName;
		}else if("FreeformName".equals(valueType)){
			returnValue=freeformName;
		}else if("WhereCondition".equals(valueType)){
			returnValue=whereClause;
		}else if("WhereParam".equals(valueType)){
			returnValue=whereParam;
		}else if("OrderBy".equals(valueType)){
			returnValue=orderbyClause;
		}else if("GroupBy".equals(valueType)){
			returnValue=groupbyClause;
		}else if("InterfaceMode".equals(valueType)){
			returnValue=interfaceMode;
		}else if("InterfaceWidth".equals(valueType)){
			returnValue=interfaceWidth;
		}else if("InterfaceHeight".equals(valueType)){
			returnValue=interfaceHeight;
		}else if("FromTable".equals(valueType)){
			returnValue=fromTable;
		}else if("KeyTable".equals(valueType)){
			returnValue=keyTable;
		}
		
		return returnValue;
	}	

	/**
	 * 获取界面配置查询信息
	 * @param request 对象
	 * @param configInterfaceList 配置值
	 * @param valueType 获得值方法
	 * @return
	 * @throws BusiException
	 */
	public static String[] getQueryList(HttpServletRequest request,List configInterfaceList,String valueType) throws BusiException{
		String queryFlag = "";
		int iCount=0;
		for(int i=0;i<configInterfaceList.size();i++){
			Map map = (Map)configInterfaceList.get(i);		
			queryFlag = Utility.trimNull(map.get("QUERY_FLAG"));
			if("1".equals(queryFlag)){
				iCount =iCount +1;
			}
		}
		String[] queryFieldColStr =new String[iCount];//字段集
		String[] queryFieldNameStr =new String[iCount];//字段名称集
		String[] queryValueStr = new String[iCount];//查询字段页面值
		String[] queryConditionTypeStr = new String[iCount];//查询类型集
		String[] queryConditionValueStr = new String[iCount];//真正查询的类别值
		String[] queryInputTypeStr = new String[iCount];//元素框类型集
		String[] queryValueTypeStr = new String[iCount];//下拉框数据源类型集
		String[] queryValueContentStr =new String[iCount];//数据查询语句集
		String[] queryBelongTable =new String[iCount];//字段所属表集合
		String[] queryOtherName =new String[iCount];//字段别名集合
		String[] returnValue =new String[iCount];//返回值
		iCount=0;
		for(int i=0;i<configInterfaceList.size();i++){
			Map  queryMap = (Map)configInterfaceList.get(i);
			String queryField_code =Utility.trimNull(queryMap.get("INTERFACEFIELD_CODE"));
			String queryField_name =Utility.trimNull(queryMap.get("INTERFACEFIELD_NAME"));
			String show_type =Utility.trimNull(queryMap.get("SHOW_TYPE"));
			String edit_type =Utility.trimNull(queryMap.get("EDIT_TYPE"));
			String value_type =Utility.trimNull(queryMap.get("VALUE_TYPE"));
			String value_content =Utility.trimNull(queryMap.get("VALUE_CONTENT"));
			String condition_Type = Utility.trimNull(queryMap.get("CONDITION_TYPE"));
			String belongTable = Utility.trimNull(queryMap.get("BELONG_TABLE"));
			String otherName = Utility.trimNull(queryMap.get("OTHER_NAME"));
			queryFlag = Utility.trimNull(queryMap.get("QUERY_FLAG"));
			if("1".equals(queryFlag)){
				queryFieldColStr[iCount] = queryField_code;
				queryBelongTable[iCount] = belongTable;
				queryOtherName[iCount] = otherName;
				queryFieldNameStr[iCount] = queryField_name;
				queryInputTypeStr[iCount] = ConfigUtil.getInputType(show_type,edit_type);
				queryValueTypeStr[iCount] = value_type;
				queryValueContentStr[iCount] =value_content;
				queryConditionTypeStr[iCount] = condition_Type;
				iCount = iCount + 1;
			}
		}
		for(int i=0;i<queryValueStr.length;i++){
			queryConditionValueStr[i] = Utility.trimNull(request.getParameter(queryFieldColStr[i]+"type"));
			if(queryConditionValueStr[i].equals("7")){//如果为两者之间时把两个框中的值用逗号组装
				String condition1=Utility.trimNull(request.getParameter(queryFieldColStr[i]+"one"+i));
				String condition2=Utility.trimNull(request.getParameter(queryFieldColStr[i]+"two"+i));
				queryValueStr[i]=condition1 + "," + condition2;
			}else if(queryInputTypeStr[i].equals("checkbox")){
				if(request.getParameterValues(queryFieldColStr[i])!=null){
					String[] values =request.getParameterValues(queryFieldColStr[i]);
					String paramValue="";
					for(int j=0;j<values.length;j++){
						paramValue = paramValue + values[j] + ",";
					}
					queryValueStr[i] = paramValue.substring(0,paramValue.length()-1);
				}else{
					queryValueStr[i]="";
				}
			}else{
				queryValueStr[i] = Utility.trimNull(request.getParameter(queryFieldColStr[i]));
			}
		}		
		if("QueryFieldCol".equals(valueType)){
			for(int i=0;i<queryFieldColStr.length;i++)
				returnValue[i]=queryFieldColStr[i];
		}else if("QueryFieldName".equals(valueType)){
			for(int i=0;i<queryFieldNameStr.length;i++)
				returnValue[i]=queryFieldNameStr[i];			
		}else if("QueryValue".equals(valueType)){
			for(int i=0;i<queryValueStr.length;i++)
				returnValue[i]=queryValueStr[i];				
		}else if("QueryConditionType".equals(valueType)){
			for(int i=0;i<queryConditionTypeStr.length;i++)
				returnValue[i]=queryConditionTypeStr[i];			
		}else if("QueryConditionValue".equals(valueType)){
			for(int i=0;i<queryConditionValueStr.length;i++)
				returnValue[i]=queryConditionValueStr[i];			
		}else if("QueryInputType".equals(valueType)){
			for(int i=0;i<queryInputTypeStr.length;i++)
				returnValue[i]=queryInputTypeStr[i];			
		}else if("QueryValueType".equals(valueType)){
			for(int i=0;i<queryValueTypeStr.length;i++)
				returnValue[i]=queryValueTypeStr[i];			
		}else if("QueryValueContent".equals(valueType)){
			for(int i=0;i<queryValueContentStr.length;i++)
				returnValue[i]=queryValueContentStr[i];			
		}else if("QueryBelongTable".equals(valueType)){
			for(int i=0;i<queryBelongTable.length;i++)
				returnValue[i]=queryBelongTable[i];			
		}else if("QueryOtherName".equals(valueType)){
			for(int i=0;i<queryOtherName.length;i++)
				returnValue[i]=queryOtherName[i];			
		}
		return returnValue;
	}	
	
	/**
	 * 获得当前模板号下指定信息
	 * @param configInterfaceList 配置值
	 * @param valueType 获得值方法
	 * @return
	 * @throws BusiException
	 */
	public static List getInterfaceValueList(List configInterfaceList,String valueType) throws BusiException{
		String tableShowFlag = "",showType = "",editType = "",visibledFlag = "";
		List list = new ArrayList();
		for(int i=0;i<configInterfaceList.size();i++){
			Map map = (Map)configInterfaceList.get(i);		
			tableShowFlag = Utility.trimNull(map.get("TABLESHOW_FLAG"));
			showType = Utility.trimNull(map.get("SHOW_TYPE"));
			editType = Utility.trimNull(map.get("EDIT_TYPE"));
			visibledFlag = Utility.trimNull(map.get("VISIBLED_FLAG"));
			
			if("QueryAllListShow".equals(valueType)){
				if("1".equals(tableShowFlag)){
					list.add(configInterfaceList.get(i));
				}
			}else if("QueryAllInfoShow".equals(valueType)){
				if(!("6".equals(showType) || "8".equals(editType))){
					list.add(configInterfaceList.get(i));
				}				
			}else if("QueryLookInfoNew".equals(valueType)){
				if(!("6".equals(showType) || visibledFlag.indexOf("1")==-1)){
					list.add(configInterfaceList.get(i));
				}				
			}else if("QueryHiddenInfoNew".equals(valueType)){
				if(!("6".equals(showType) || "8".equals(editType) || visibledFlag.indexOf("1")>=0)){
					list.add(configInterfaceList.get(i));
				}				
			}else if("QueryLookInfoMod".equals(valueType)){
				if(!("6".equals(showType) || visibledFlag.indexOf("2")==-1)){
					list.add(configInterfaceList.get(i));
				}				
			}else if("QueryHiddenInfoMod".equals(valueType)){
				if(!("6".equals(showType) || "8".equals(editType) || visibledFlag.indexOf("2")>=0)){
					list.add(configInterfaceList.get(i));
				}				
			}
		}
		
		return list;
	}	
	
	/**
	 * 获得当前模板号下指定信息
	 * @param configInterfaceList 配置值
	 * @param valueType 获得值方法
	 * @return
	 * @throws BusiException
	 */
	public static String[] getShowTableList(List configInterfaceList,String valueType) throws BusiException{
		String[] fieldStrs = new String[configInterfaceList.size()];//显示字段编码
		String[] fieldNameStrs = new String[configInterfaceList.size()];//显示字段名称		
		String[] returnValue = new String[configInterfaceList.size()];//返回值
		for(int i=0;i<configInterfaceList.size();i++){
			Map  rowMap = (Map)configInterfaceList.get(i);
			fieldStrs[i] = Utility.trimNull(rowMap.get("INTERFACEFIELD_CODE"));
			fieldNameStrs[i] = Utility.trimNull(rowMap.get("INTERFACEFIELD_NAME"));
			if("QueryFieldCode".equals(valueType))
				returnValue[i]=fieldStrs[i];
			else if("QueryFieldName".equals(valueType))
				returnValue[i]=fieldNameStrs[i];
		}
		return returnValue;
	}
	
	/**
	 * 获得当前模板号下指定信息
	 * @param configInterfaceList 配置值
	 * @param otherWhere 替换参数值
	 * @return
	 * @throws BusiException
	 */
	public static String getQueryWhere(List configInterfaceList,String otherWhere) throws BusiException{
		String sqlContent =Utility.trimNull(getInterfaceValue(configInterfaceList,"WhereCondition"));//其他查询字段语句
		String replaceStr =Utility.trimNull(getInterfaceValue(configInterfaceList,"WhereParam"));//替换参数
		if(!("".equals(sqlContent) || "".equals(replaceStr) || "".equals(otherWhere)))
		{
				String[] otherWhereValue =otherWhere.split(",");  //需要替换参数值
				String[] replaceStrValue =replaceStr.split(",");  //需要替换参数
				if(otherWhereValue.length==replaceStrValue.length)
				{
					for(int i=0;i<replaceStrValue.length;i++)
					{
						String[] replaceType=replaceStrValue[i].split(" ");  //需要替换方式
						if("String".equals(replaceType[0]))
							sqlContent =sqlContent.replaceAll("#"+replaceType[1]+"#","'"+otherWhereValue[i]+"'");
						else if("Number".equals(replaceType[0])){
							sqlContent =sqlContent.replaceAll("#"+replaceType[1]+"#",""+otherWhereValue[i]+"");
						}
					}
				}
		}
		return sqlContent;
	}		

	/**
	 * 生成显示结果集的sql语句
	 * @param interfaceType_code 查询语句 
	 * @param queryFieldCol 查询字段
	 * @param queryFieldValue 查询值
	 * @param conditionType 查询条件
	 * @param sqlContent 其他sql条件
	 * @return
	 */
	public static String getShowFieldSql(String interfaceType_code,String[] queryFieldCol,String[] queryFieldValue,String[] conditionType,String sqlContent) throws BusiException{
		//增加扩展支持，以后版本升级取消改功能
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		List configInterfaceList=getConfigInterface(interfaceType_code);
		String[] queryBelongTable =new String[configInterfaceList.size()];//字段所属表集合
		String[] queryOtherName =new String[configInterfaceList.size()];//字段别名集合		
		String orderbyClause = getInterfaceValue(configInterfaceList,"OrderBy");
		String groupbyClause = getInterfaceValue(configInterfaceList,"GroupBy");
		String fromTable = getInterfaceValue(configInterfaceList,"FromTable");
		String tableName = getInterfaceValue(configInterfaceList,"TableName");
		if("".equals(fromTable)) fromTable=tableName;
		
		String fieldCode="",showType="",belongTable="",showField="",queryField="",otherName="",queryCondtion="",queryFlag="",showSql="";
		int iCount=0;
		for(int i=0;i<configInterfaceList.size();i++){
			Map  rowMap = (Map)configInterfaceList.get(i);
			fieldCode = Utility.trimNull(rowMap.get("INTERFACEFIELD_CODE"));
			showType = Utility.trimNull(rowMap.get("SHOW_TYPE"));
			belongTable = Utility.trimNull(rowMap.get("BELONG_TABLE"));
			otherName = Utility.trimNull(rowMap.get("OTHER_NAME"));
			queryFlag = Utility.trimNull(rowMap.get("QUERY_FLAG"));
			if("1".equals(showType) || "2".equals(showType) || "3".equals(showType) || "4".equals(showType) || "5".equals(showType))
			{
				if("1".equals(queryFlag)){
					queryBelongTable[iCount] = belongTable;
					queryOtherName[iCount] = otherName;
					iCount=iCount+1;
				}
				if("".equals(showField)){
					if("".equals(otherName) && "".equals(belongTable))
						showField=fieldCode;
					else if(!"".equals(otherName) && "".equals(belongTable))
						showField=otherName+" AS "+fieldCode;
					else if("".equals(otherName) && !"".equals(belongTable))
						showField=belongTable+"."+fieldCode;
					else
						showField=belongTable+"."+otherName+" AS "+fieldCode;
				}else{
					if("".equals(otherName) && "".equals(belongTable))
						showField=showField+","+fieldCode;
					else if(!"".equals(otherName) && "".equals(belongTable))
						showField=showField+","+otherName+" AS "+fieldCode;
					else if("".equals(otherName) && !"".equals(belongTable))
						showField=showField+","+belongTable+"."+fieldCode;
					else
						showField=showField+","+belongTable+"."+otherName+" AS "+fieldCode;					
				}
			}
		}		
		if(queryFieldCol.length>0){//查询块查询语句拼接(值为空的不进行拼接)
			for(int i=0;i<queryFieldCol.length;i++){
				if("".equals(queryOtherName[i]) && "".equals(queryBelongTable[i]))
					queryField=queryFieldCol[i];
				else if(!"".equals(queryOtherName[i]) && "".equals(queryBelongTable[i]))
					queryField=queryOtherName[i];
				else if("".equals(queryOtherName[i]) && !"".equals(queryBelongTable[i]))
					queryField=queryBelongTable[i]+"."+queryFieldCol[i];
				else
					queryField=queryBelongTable[i]+"."+queryOtherName[i];					
				if(conditionType[i].equals("2") && !("".equals(queryFieldValue[i]))){//包含
					
					queryCondtion = queryCondtion + " and ("+queryField+" like '%"+queryFieldValue[i]+"%' or isnull('"+queryFieldValue[i]+"','')='')";
				}else if(conditionType[i].equals("3") && !("".equals(queryFieldValue[i]))){//大于等于
					
					queryCondtion = queryCondtion + " and ("+queryField+" >= '"+queryFieldValue[i]+"' or isnull('"+queryFieldValue[i]+"','')='')";
				}else if(conditionType[i].equals("4") && !("".equals(queryFieldValue[i]))){//小于等于
					
					queryCondtion = queryCondtion + " and ("+queryField+" <= '"+queryFieldValue[i]+"' or isnull('"+queryFieldValue[i]+"','')='')";
				}else if(conditionType[i].equals("5") && !("".equals(queryFieldValue[i]))){//大于
					
					queryCondtion = queryCondtion + " and ("+queryField+" > '"+queryFieldValue[i]+"' or isnull('"+queryFieldValue[i]+"','')='')";
				}else if(conditionType[i].equals("6") && !("".equals(queryFieldValue[i]))){//小于
					
					queryCondtion = queryCondtion + " and ("+queryField+" < '"+queryFieldValue[i]+"' or isnull('"+queryFieldValue[i]+"','')='')";
				}else if(conditionType[i].equals("7")){//两者之间
					String condition = queryFieldValue[i];
					String condition1 = condition.substring(0, condition.indexOf(","));
					String condition2 = condition.substring(condition.indexOf(",")+1,condition.length());
					if(!"".equals(condition1))
						queryCondtion = queryCondtion + " and "+queryField+" >= '"+condition1+"'";
					if(!"".equals(condition2))
						queryCondtion = queryCondtion + " and "+queryField+"<= '"+condition2+"'";					
				}else if(conditionType[i].equals("8") && !("".equals(queryFieldValue[i]))){//匹配开头
					
					queryCondtion = queryCondtion + " and ("+queryField+" like '%"+queryFieldValue[i]+"' or isnull('"+queryFieldValue[i]+"','')='')";
				}else if(conditionType[i].equals("9") && !("".equals(queryFieldValue[i]))){//匹配结尾
				
					queryCondtion = queryCondtion + " and ("+queryField+" like '"+queryFieldValue[i]+"%' or isnull('"+queryFieldValue[i]+"','')='')";
				
				}else if(conditionType[i].equals("1") && !("".equals(queryFieldValue[i]))){
					queryCondtion = queryCondtion + " and ("+queryField+"='"+queryFieldValue[i]+"' or isnull('"+queryFieldValue[i]+"','')='')";
				}
			}
		}
		showSql = "select " +showField + " from "+ fromTable + " where 1=1 "+queryCondtion+ " " +sqlContent+ " " +groupbyClause+ " " +orderbyClause;
		
		return showSql;
	}
	
	/**
	 * 生成显示结果集的sql语句
	 * @param sqlContent 查询语句 
	 * @param request 对象
	 * @param configInterfaceList 配置值
	 * @return
	 */
	public static String getShowListSql(HttpServletRequest request,List configInterfaceList,String sqlContent) throws BusiException{
		//增加扩展支持，以后版本升级取消改功能
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		if(configInterfaceList.size()==0) 
			configInterfaceList=getConfigInterface(sqlContent);
		
		String[] queryFieldColStr =getQueryList(request,configInterfaceList,"QueryFieldCol");//字段集	
		String[] queryValueStr = getQueryList(request,configInterfaceList,"QueryValue");//查询字段页面值
		String[] queryConditionTypeStr = getQueryList(request,configInterfaceList,"QueryConditionValue");//查询类型集
		String[] queryBelongTable =getQueryList(request,configInterfaceList,"QueryBelongTable");//字段所属表集合
		String[] queryOtherName =getQueryList(request,configInterfaceList,"QueryOtherName");//字段别名集合
		String orderbyClause = getInterfaceValue(configInterfaceList,"OrderBy");
		String groupbyClause = getInterfaceValue(configInterfaceList,"GroupBy");
		String fromTable = getInterfaceValue(configInterfaceList,"FromTable");
		String tableName = getInterfaceValue(configInterfaceList,"TableName");
		if("".equals(fromTable)) fromTable=tableName;
		
		String fieldCode="",showType="",belongTable="",showField="",queryField="",otherName="",queryCondtion="",showSql="";
		
		for(int i=0;i<configInterfaceList.size();i++){
			Map  rowMap = (Map)configInterfaceList.get(i);
			fieldCode = Utility.trimNull(rowMap.get("INTERFACEFIELD_CODE"));
			showType = Utility.trimNull(rowMap.get("SHOW_TYPE"));
			belongTable = Utility.trimNull(rowMap.get("BELONG_TABLE"));
			otherName = Utility.trimNull(rowMap.get("OTHER_NAME"));
			if("1".equals(showType) || "2".equals(showType) || "3".equals(showType) || "4".equals(showType) || "5".equals(showType))
			{
				if("".equals(showField)){
					if("".equals(otherName) && "".equals(belongTable))
						showField=fieldCode;
					else if(!"".equals(otherName) && "".equals(belongTable))
						showField=otherName+" AS "+fieldCode;
					else if("".equals(otherName) && !"".equals(belongTable))
						showField=belongTable+"."+fieldCode;
					else
						showField=belongTable+"."+otherName+" AS "+fieldCode;
				}else{
					if("".equals(otherName) && "".equals(belongTable))
						showField=showField+","+fieldCode;
					else if(!"".equals(otherName) && "".equals(belongTable))
						showField=showField+","+otherName+" AS "+fieldCode;
					else if("".equals(otherName) && !"".equals(belongTable))
						showField=showField+","+belongTable+"."+fieldCode;
					else
						showField=showField+","+belongTable+"."+otherName+" AS "+fieldCode;					
				}
			}
		}		
		if(queryFieldColStr.length>0){//查询块查询语句拼接(值为空的不进行拼接)
			for(int i=0;i<queryFieldColStr.length;i++){
				if("".equals(queryOtherName[i]) && "".equals(queryBelongTable[i]))
					queryField=queryFieldColStr[i];
				else if(!"".equals(queryOtherName[i]) && "".equals(queryBelongTable[i]))
					queryField=queryOtherName[i];
				else if("".equals(queryOtherName[i]) && !"".equals(queryBelongTable[i]))
					queryField=queryBelongTable[i]+"."+queryFieldColStr[i];
				else
					queryField=queryBelongTable[i]+"."+queryOtherName[i];				
				if(queryConditionTypeStr[i].equals("2") && !("".equals(queryValueStr[i]))){//包含
					
					queryCondtion = queryCondtion + " and ("+queryField+" like '%"+queryValueStr[i]+"%' or isnull('"+queryValueStr[i]+"','')='')";
				}else if(queryConditionTypeStr[i].equals("3") && !("".equals(queryValueStr[i]))){//大于等于
					
					queryCondtion = queryCondtion + " and ("+queryField+" >= '"+queryValueStr[i]+"' or isnull('"+queryValueStr[i]+"','')='')";
				}else if(queryConditionTypeStr[i].equals("4") && !("".equals(queryValueStr[i]))){//小于等于
					
					queryCondtion = queryCondtion + " and ("+queryField+" <= '"+queryValueStr[i]+"' or isnull('"+queryValueStr[i]+"','')='')";
				}else if(queryConditionTypeStr[i].equals("5") && !("".equals(queryValueStr[i]))){//大于
					
					queryCondtion = queryCondtion + " and ("+queryField+" > '"+queryValueStr[i]+"' or isnull('"+queryValueStr[i]+"','')='')";
				}else if(queryConditionTypeStr[i].equals("6") && !("".equals(queryValueStr[i]))){//小于
					
					queryCondtion = queryCondtion + " and ("+queryField+" < '"+queryValueStr[i]+"' or isnull('"+queryValueStr[i]+"','')='')";
				}else if(queryConditionTypeStr[i].equals("7")){//两者之间
					String condition = queryValueStr[i];
					String condition1 = condition.substring(0, condition.indexOf(","));
					String condition2 = condition.substring(condition.indexOf(",")+1,condition.length());
					if(!"".equals(condition1))
						queryCondtion = queryCondtion + " and "+queryField+" >= '"+condition1+"'";
					if(!"".equals(condition2))
						queryCondtion = queryCondtion + " and "+queryField+"<= '"+condition2+"'";
				}else if(queryConditionTypeStr[i].equals("8") && !("".equals(queryValueStr[i]))){//匹配开头
					queryCondtion = queryCondtion + " and ("+queryField+" like '%"+queryValueStr[i]+"' or isnull('"+queryValueStr[i]+"','')='')";
				}else if(queryConditionTypeStr[i].equals("9") && !("".equals(queryValueStr[i]))){//匹配结尾
					queryCondtion = queryCondtion + " and ("+queryField+" like '"+queryValueStr[i]+"%' or isnull('"+queryValueStr[i]+"','')='')";
				}else if(queryConditionTypeStr[i].equals("1") && !("".equals(queryValueStr[i]))){
					queryCondtion = queryCondtion + " and ("+queryField+"='"+queryValueStr[i]+"' or isnull('"+queryValueStr[i]+"','')='')";
				}
			}
		}
		showSql = "select " +showField + " from "+ fromTable + " where 1=1 "+queryCondtion+ " " +sqlContent+ " " +orderbyClause+ " " +groupbyClause;
		
		return showSql;
	}
	
	
	
	/**
	 * 拼接SUrl
	 * @param sUrl 
	 * @param request 对象
	 * @param configInterfaceList 配置值
	 * @return
	 */
	public static String getPageUrl(HttpServletRequest request,List configInterfaceList,String sUrl) throws BusiException{
		String[] queryFieldColStr =getQueryList(request,configInterfaceList,"QueryFieldCol");//字段集	
		String[] queryValueStr = getQueryList(request,configInterfaceList,"QueryValue");//查询字段页面值
		String[] queryConditionTypeStr = getQueryList(request,configInterfaceList,"QueryConditionType");//查询类型集
		String[] queryConditionValueStr = getQueryList(request,configInterfaceList,"QueryConditionValue");//真正查询的类别值
		
		for(int i=0;i<queryFieldColStr.length;i++){
			sUrl =sUrl + "&"+queryFieldColStr[i]+"=" + queryValueStr[i]+"&"+queryFieldColStr[i]+"type=" + queryConditionValueStr[i];
			if(queryConditionTypeStr[i].indexOf("7")!=-1){//存在查询类型为两者之间是的处理
				String[] str = queryValueStr[i].split(",");
				if(str.length == 2){
					sUrl =sUrl + "&"+queryFieldColStr[i]+"one"+i+"=" + str[0] + "&"+queryFieldColStr[i]+"two"+i+"=" + str[1];
				}
			}
		}
		return sUrl;
	}
	
	/**
	 * 查询框时的下拉框组装
	 * @param conditionType 查询类型值
	 * @param defaultValue 默认值
	 * @return
	 * @throws Exception
	 */
	public static String getQuerySelectValue(String conditionType,String defaultValue)
	throws Exception {
		StringBuffer sb = new StringBuffer();
		String[] queryCondition=conditionType.split(",");
		for(int i=0;i<queryCondition.length;i++)
		{
			if("1".equals(queryCondition[i]))
				appendConfigParam(sb,queryCondition[i],"等于",defaultValue);
			else if("2".equals(queryCondition[i]))
				appendConfigParam(sb,queryCondition[i],"包含",defaultValue);
			else if("3".equals(queryCondition[i]))
				appendConfigParam(sb,queryCondition[i],"大于等于",defaultValue);		
			else if("4".equals(queryCondition[i]))
				appendConfigParam(sb,queryCondition[i],"小于等于",defaultValue);
			else if("5".equals(queryCondition[i]))
				appendConfigParam(sb,queryCondition[i],"大于",defaultValue);
			else if("6".equals(queryCondition[i]))
				appendConfigParam(sb,queryCondition[i],"小于",defaultValue);
			else if("7".equals(queryCondition[i]))
				appendConfigParam(sb,queryCondition[i],"两者之间",defaultValue);		
			else if("8".equals(queryCondition[i]))
				appendConfigParam(sb,queryCondition[i],"匹配开头",defaultValue);
			else if("9".equals(queryCondition[i]))
				appendConfigParam(sb,queryCondition[i],"匹配结尾",defaultValue);
		}
		return sb.toString();
	}
		
	
	/**
	 * 生成显示结果集的sql语句
	 * @param sqlContent 查询语句 
	 * @param configInterfaceList 配置值
	 * @return
	 */
	public static String getShowInfoSql(List configInterfaceList,String sqlContent) throws BusiException{
		String fieldCode="",showType="",showField="",belongTable="",otherName="",showSql="",showCondition="";
		String identityCode = getInterfaceValue(configInterfaceList,"TableKey");
		String keyTable = getInterfaceValue(configInterfaceList,"KeyTable");
		String fromTable = getInterfaceValue(configInterfaceList,"FromTable");
		String tableName = getInterfaceValue(configInterfaceList,"TableName");
		if("".equals(fromTable)) fromTable=tableName;
		if(sqlContent.indexOf("@@")>=0)
			showCondition = ConfigUtil.getQueryWhere(configInterfaceList,sqlContent.substring(sqlContent.indexOf("@@")+2, sqlContent.length()));
		else
			showCondition = ConfigUtil.getQueryWhere(configInterfaceList,"");
		for(int i=0;i<configInterfaceList.size();i++){
			Map  rowMap = (Map)configInterfaceList.get(i);
			fieldCode = Utility.trimNull(rowMap.get("INTERFACEFIELD_CODE"));
			showType = Utility.trimNull(rowMap.get("SHOW_TYPE"));
			belongTable = Utility.trimNull(rowMap.get("BELONG_TABLE"));
			otherName = Utility.trimNull(rowMap.get("OTHER_NAME"));
			
			if("1".equals(showType) || "2".equals(showType) || "3".equals(showType) || "4".equals(showType) || "5".equals(showType))
			{
				if("".equals(showField)){
					if("".equals(otherName) && "".equals(belongTable))
						showField=fieldCode;
					else if(!"".equals(otherName) && "".equals(belongTable))
						showField=otherName+" AS "+fieldCode;
					else if("".equals(otherName) && !"".equals(belongTable))
						showField=belongTable+"."+fieldCode;
					else
						showField=belongTable+"."+otherName+" AS "+fieldCode;
				}else{
					if("".equals(otherName) && "".equals(belongTable))
						showField=showField+","+fieldCode;
					else if(!"".equals(otherName) && "".equals(belongTable))
						showField=showField+","+otherName+" AS "+fieldCode;
					else if("".equals(otherName) && !"".equals(belongTable))
						showField=showField+","+belongTable+"."+fieldCode;
					else
						showField=showField+","+belongTable+"."+otherName+" AS "+fieldCode;	
				}
			}
		}				
		showSql = "select " +showField + " from "+ fromTable + " where "+keyTable+"."+identityCode+"='"+sqlContent+"' "+showCondition;
		
		return showSql;
	}	

	/**
	 * 获取详情页面对应查询代码值信息
	 * @param fieldShowList 
	 * @param queryType 值类别
	 * @return
	 * @throws BusiException
	 */
	public static List getSelectValue(List fieldShowList,String queryType) throws BusiException{
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		String fieldCode="",editType="",valueType="",valueContent="";
		String orderByField="",itemCode="",itemName="",selectField="",tableName="",whereClause="",descClause="";
		String showSql="",tempSql="";
		List list = new ArrayList();
		for(int i=0;i<fieldShowList.size();i++){
			Map rowMap = (Map)fieldShowList.get(i);
			fieldCode = Utility.trimNull(rowMap.get("INTERFACEFIELD_CODE"));
			editType = Utility.trimNull(rowMap.get("EDIT_TYPE"));	
			valueType = Utility.trimNull(rowMap.get("VALUE_TYPE"));	
			valueContent = Utility.trimNull(rowMap.get("VALUE_CONTENT"));
			if("2".equals(editType) || "3".equals(editType) || "4".equals(editType))
			{
				if("1".equals(valueType)){
					tempSql="select '"+fieldCode+"' AS REGION_CODE,ITEM_CODE,ITEM_NAME,ORDER_NO from Config_region where region_code='"+valueContent+"' and Use_State='1'";
				}else if("2".equals(valueType)){
					valueContent=valueContent.toLowerCase();
					//select product_id,PRODUCT_CODE+'-'+product_name from INTRUST..TPRODUCT where START_DATE>20120101 ORDER BY PRODUCT_ID 
					int selectIndex =valueContent.indexOf("select")+"select".length()+1;
					int dotIndex = valueContent.indexOf(",");
					int fromIndex = valueContent.indexOf("from");
					int whereIndex =valueContent.indexOf("where");
					int orderbyIndex =valueContent.indexOf("order by");
					int descIndex = valueContent.indexOf("desc");
					if(whereIndex==-1) whereIndex=valueContent.length();
					if(orderbyIndex==-1) {
						orderbyIndex=valueContent.length();
						orderByField = "1";
					}else{
						if(descIndex!=-1){
							orderByField = valueContent.substring(orderbyIndex+8,descIndex).trim();
							descClause="desc";
						}else{
							orderByField = valueContent.substring(orderbyIndex+8,valueContent.length()).trim();
						}
						if(whereIndex == valueContent.length()){
							whereIndex = orderbyIndex;
						}
					}
					itemCode = valueContent.substring(selectIndex,dotIndex).trim();
					selectField = valueContent.substring(selectIndex,fromIndex).trim();
					itemName = selectField.substring(selectField.indexOf(",")+1,selectField.length()).trim();
					tableName = valueContent.substring(fromIndex+4,whereIndex).trim();
					if(whereIndex==valueContent.length() || whereIndex == orderbyIndex){
						whereClause = " 1=1 ";
					}else{
						whereClause = valueContent.substring(whereIndex+5,orderbyIndex).trim();
					}
					tempSql="select '"+fieldCode+"' AS REGION_CODE,"+itemCode+" AS ITEM_CODE,"+itemName+" AS ITEM_NAME,"+orderByField+" AS ORDER_NO from "+tableName+" where "+whereClause;
				}else if("3".equals(valueType)){
					tempSql="select '"+fieldCode+"' AS REGION_CODE,TYPE_VALUE AS ITEM_CODE,TYPE_CONTENT  AS ITEM_NAME,TYPE_VALUE AS ORDER_NO "
					+ "from TDICTPARAM where TYPE_ID='"+valueContent+"'";
				}
				if("".equals(showSql)) showSql=tempSql;
				else showSql=showSql+" union all "+tempSql;
			}
		}
		if(!"".equals(showSql)) list = getInfoPageData(showSql+" order by 1,4 ");
		return list;
	}

	/**
	 * 通过sql语句显示下拉框信息
	 * @param selectValuelist
	 * @param fieldCode
	 * @param value
	 * @param showType
	 * @return
	 * @throws Exception
	 */
	public static String getSelectContent(List selectValuelist,String fieldCode,String value,String showType)
	throws Exception {
		StringBuffer sb = new StringBuffer();
		if("SelectBox".equals(showType)) sb.append("<option value=\"\">请选择</option>");
		String regionCode="",itemCode="",itemName="",valueContent="";
		for(int i=0;i<selectValuelist.size();i++){
			Map rowMap = (Map)selectValuelist.get(i);
			regionCode = Utility.trimNull(rowMap.get("REGION_CODE"));
			itemCode = Utility.trimNull(rowMap.get("ITEM_CODE"));	
			itemName = Utility.trimNull(rowMap.get("ITEM_NAME"));
			if(fieldCode.equals(regionCode)){
				if("SelectBox".equals(showType)) appendConfigParam(sb,itemCode,itemName,value);
				else if("CheckBox".equals(showType)) appendCheckBoxs(sb,itemCode,itemName,value,fieldCode);	
				else if("CheckRadio".equals(showType)) appendRadios(sb,itemCode,itemName,value,fieldCode);
				else if("SelectName".equals(showType) && itemCode.equals(value)) sb.append(itemName);
			}
		}
		return sb.toString();
	}

	/**
	 * 获取页面按钮信息
	 * @param configButtonList
	 * @param showType
	 * @return
	 * @throws BusiException
	 */
	public static String[][] getShowButton(List configButtonList,String showType,String paramValue) throws BusiException{
		String[] replaceValue=paramValue.split(","); 
		String buttonType="";
		int iCount=0,iNumber=0,iRow=0;
		for(int i=0;i<configButtonList.size();i++){
			Map map = (Map)configButtonList.get(i);
			buttonType = Utility.trimNull(map.get("BUTTON_TYPE"));
			if(showType.equals(buttonType)) iCount = iCount+1;
		}
		String showButtons[][]=new String[iCount][9];
		for(int i=0;i<configButtonList.size();i++){
			Map map = (Map)configButtonList.get(i);
			buttonType = Utility.trimNull(map.get("BUTTON_TYPE"));
			if(showType.equals(buttonType)){ 
				showButtons[iNumber][0] = Utility.trimNull(map.get("SHOW_STATE"));
				showButtons[iNumber][1] = Utility.trimNull(map.get("BUTTON_CLASS"));
				showButtons[iNumber][2] = Utility.trimNull(map.get("ACCESS_KEY"));
				showButtons[iNumber][3] = Utility.trimNull(map.get("BUTTON_CODE"));
				showButtons[iNumber][4] = Utility.trimNull(map.get("BUTTON_NAME"));
				showButtons[iNumber][5] = Utility.trimNull(map.get("BUTTON_TITLE"));
				showButtons[iNumber][6] = Utility.trimNull(map.get("SHOW_NAME"));
				showButtons[iNumber][7] = Utility.trimNull(map.get("BUTTON_JSFUNC"));
				showButtons[iNumber][8] = Utility.trimNull(map.get("SHOW_TYPE"));
				if("HiddenParam".equals(showType) && replaceValue.length>iRow){
					if(replaceValue[iRow].indexOf("#")>=0){
						showButtons[iNumber][7]=replaceValue[iRow];
						iRow=iRow+1;
					}
				}
				iNumber = iNumber+1;
			}
		}		
		return showButtons;
	}
		
	
	/**
	 * 获取页面类型信息
	 * @param configLocal
	 * @param interfaceType_code 页面类别编码
	 * @return
	 * @throws BusiException
	 */
	public static List getTableName(ConfigLocal configLocal,String interfaceType_code) throws BusiException{
		List list = configLocal.listTableName(interfaceType_code);
		List listInfo = new ArrayList();
		if(list.size()>0){
			Map map = (Map)list.get(0);
			listInfo.add(Utility.trimNull(map.get("TABLE_CODE")));
			listInfo.add(Utility.trimNull(map.get("GRID_NAME")));
			listInfo.add(Utility.trimNull(map.get("FREEFORM_NAME")));
		}
		return listInfo;
	}
	
	/**
	 * 获取页面类型信息
	 * @param configLocal
	 * @param interfaceType_code 页面类别编码
	 * @return
	 * @throws BusiException
	 */
	public static String[][] getShowMain(List menuList,String showType,String showValue) throws BusiException{
		String[][] ShowContent = new String[menuList.size()][6];
		String[] ShowParamValue=showValue.split("@@"); 
		String showmenu_id="",menu_name="",tempValue="";
		int iIndex=0,iNextIndex=0;
		//初始化显示菜单信息
		for(int i=0;i<menuList.size();i++)
		{
			Map menuMap = (Map)menuList.get(i);
			showmenu_id=Utility.trimNull(menuMap.get("Item_Code"));
			ShowContent[i][0]=showmenu_id;
			ShowContent[i][1]=Utility.trimNull(menuMap.get("Item_Name"));
			ShowContent[i][4]=Utility.trimNull(menuMap.get("Relative_Name"));
			tempValue=Utility.trimNull(menuMap.get("Attribute_Name"));
			//初始化赋值
			for(int j=0;j<ShowParamValue.length;j++)
			{
				iIndex=tempValue.indexOf("#");
				tempValue=tempValue.replaceFirst("#","");
				iNextIndex=tempValue.indexOf("#");
				tempValue=tempValue.replaceFirst("#","");
				tempValue=tempValue.replaceAll(tempValue.substring(iIndex,iNextIndex),ShowParamValue[j]);
			}
			ShowContent[i][5]=tempValue;
			if(showmenu_id.length()==3){
				ShowContent[i][3]="root";     //继承上级节点编号
			}else if(showmenu_id.length()==6){
				ShowContent[i][3]="treeNode"+showmenu_id.substring(0,3);
			}else if(showmenu_id.length()==9){
				ShowContent[i][3]="treeNode"+showmenu_id.substring(0,6);
			}else if(showmenu_id.length()==12){
				ShowContent[i][3]="treeNode"+showmenu_id.substring(0,9);
			}else if(showmenu_id.length()==15){
				ShowContent[i][3]="treeNode"+showmenu_id.substring(0,12);
			}
		}
		//判断节点是否为叶子节点
		for(int i=0;i<ShowContent.length;i++){
			for(int j=0;j<ShowContent.length;j++){
				if(("treeNode"+ShowContent[i][0]).equals(ShowContent[j][3])){
					ShowContent[i][2]="branch";
					break;
				}else{
					ShowContent[i][2]="leaf";
				}
					
			}
		}
		return ShowContent;
	}

	/**
	 * 获取页面类型信息
	 * @param configLocal
	 * @param interfaceType_code 页面类别编码
	 * @return
	 * @throws BusiException
	 */
	public static String[][] getShowTab(List menuList,String showType,String showValue) throws BusiException{
		String[][] ShowContent = new String[menuList.size()][7];
		String[] ShowParamValue=showValue.split("@@"); 
		String tempValue="";
		int iIndex=0,iNextIndex=0;
		//初始化显示菜单信息
		for(int i=0;i<menuList.size();i++)
		{
			Map menuMap = (Map)menuList.get(i);
			ShowContent[i][0]=Utility.trimNull(menuMap.get("Item_Code"));
			ShowContent[i][1]=Utility.trimNull(menuMap.get("Item_Name"));
			ShowContent[i][2]=Utility.trimNull(menuMap.get("Control_Condition1"));
			ShowContent[i][3]=Utility.trimNull(menuMap.get("Control_Condition2"));
			ShowContent[i][6]=Utility.trimNull(menuMap.get("Control_Condition3"));
			ShowContent[i][4]=Utility.trimNull(menuMap.get("Relative_Name"));
			tempValue=Utility.trimNull(menuMap.get("Attribute_Name"));			
			//初始化赋值
			for(int j=0;j<ShowParamValue.length;j++)
			{
				iIndex=tempValue.indexOf("#");
				tempValue=tempValue.replaceFirst("#","");
				iNextIndex=tempValue.indexOf("#");
				tempValue=tempValue.replaceFirst("#","");
				tempValue=tempValue.replaceAll(tempValue.substring(iIndex,iNextIndex),ShowParamValue[j]);
			}
			ShowContent[i][5]=tempValue;
		}
		return ShowContent;
	}	
	/**
	 * 删除页面信息
	 * @param str 删除信息主键值
	 * @param table_code 表名
	 * @throws BusiException 
	 */
	public static void delInfo(ConfigLocal configLocal,String[] str,String table_code,String identity_code) throws BusiException{
		int identity_value;
		if (str != null)
		{
			for(int i = 0;i < str.length; i++)
			{
				identity_value = Utility.parseInt(str[i], 0);
				if(identity_value != 0)
				{
					//增加关联删除信息
					if(table_code.equals("CONFIG_RECORD")) configLocal.delRelationInfo(String.valueOf(identity_value));
					//删除共用信息
					configLocal.delInfo(table_code,identity_code,String.valueOf(identity_value));
					
				}
			}
		}
	}

	/**
	 * 删除页面信息
	 * @param str 删除信息主键值
	 * @param table_code 表名
	 * @throws BusiException 
	 */
	public static void delInfo(List configInterfaceList,String[] str,String delFlag) throws BusiException{
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		String table_code = ConfigUtil.getInterfaceValue(configInterfaceList,"TableName");	
		String identity_code = ConfigUtil.getInterfaceValue(configInterfaceList,"TableKey");
		int identity_value=0;
		if (str != null)
		{
			for(int i = 0;i < str.length; i++)
			{
				identity_value = Utility.parseInt(str[i], 0);
				if(identity_value != 0)
				{
					//删除共用信息
					delInfo(table_code,identity_code,String.valueOf(identity_value));
					//增加关联删除信息
					if("delRetionRecord".equals(delFlag)){
						delRelationInfo(String.valueOf(identity_value));
					}
				}
			}
		}
	}
	
	/**
	 * 新增修改时获取页面元素值
	 * @param identity_code 主键值
	 * @param default_flag 是否有默认值
	 * @param default_value 默认值
	 * @param inputType 元素框类型
	 * @param interfaceField_code 字段名称
	 * @param modShowDataList 修改页面的数据集合
	 * @return
	 * @throws Exception
	 */
	public static String getPageElementValue(Integer identity_code,String default_flag,String default_value,String inputType,
											String interfaceField_code,List modShowDataList) throws Exception{
		String tableValue="";
		if(identity_code.intValue()==0){//新增页面的元素框值
			if(default_flag.equals("1")){//有默认值时的处理
				tableValue= default_value;
			}
		}else{//修改页面的元素框值
			tableValue = ConfigUtil.getTableValue(modShowDataList,inputType,interfaceField_code);
		}
		return tableValue;
	}
	
	/**
	 * 获取所有的参数集
	 * @param map
	 * @param paramValue 所有的参数值集
	 * @param funcParams 函数参数字符集
	 * @return
	 */
	public static String[] iconParamDeal(Map map,String[] paramValue,String funcParams){
		String[] funcParam = funcParams.split(",");//未处理过的函数参数名称
		paramValue = new String[funcParam.length];//函数参数值集
		for(int m=0;m<paramValue.length;m++){
			String resultValue="";
			String funcParamName=funcParam[m];
			if(funcParamName.indexOf("1_")!=-1){//从数据库中获取动态值
				resultValue =Utility.trimNull(map.get((funcParamName.substring(2,funcParamName.length())).toUpperCase()));
			}else{
				if(funcParamName.indexOf("2_")!=-1){//获取静态值
					resultValue =funcParamName.substring(2,funcParamName.length());
				}else{//不正规写法全部赋值
					resultValue = funcParamName;
				}
			}
			paramValue[m]=resultValue;
		}
		return paramValue;
	}
	/**
	 * 加入其他查询字段
	 * @param interfaceType_code 界面类型编码
	 * @param replaceColValue 查询字段替换值
	 * @throws Exception
	 */
	public static String getOtherQuerySql(String interfaceType_code,List replaceColValue) throws Exception{
		String sqlContent =Utility.trimNull(ConfigUtil.getPropertyNameById("interface_catalog","where_condition","interfacetype_code",interfaceType_code));//其他查询字段语句
		String replaceStr =Utility.trimNull(ConfigUtil.getPropertyNameById("interface_catalog","where_param","interfacetype_code",interfaceType_code));
		if(!"".equals(sqlContent)){
			if(!replaceStr.equals("")){
				String[] replaceCol =replaceStr.split(",");  //需要替换的字段
				if(replaceCol.length>0 && (replaceCol.length==replaceColValue.size())){
					for(int i=0;i<replaceCol.length;i++){
						sqlContent =sqlContent.replaceAll(replaceCol[i],"'"+replaceColValue.get(i)+"'");
					}
					return sqlContent;
				}
			}else{//参数为空查询语句不做处理
				return sqlContent;
			}
		}
		return "";
	}
	
	/**
	 * 拼接SUrl
	 * @param sUrl 
	 * @param queryFieldColStr 需要查询的字段集
	 * @param queryConditionTypeStr 查询类型集
	 * @param queryValueStr 查询值集
	 * @return
	 */
	public static String getSUrl(String sUrl,String[] queryFieldColStr,String[] queryConditionTypeStr,String[] queryValueStr,String[] queryConditionValueStr){
		for(int i=0;i<queryFieldColStr.length;i++){
			sUrl =sUrl + "&"+queryFieldColStr[i]+"=" + queryValueStr[i]+"&"+queryFieldColStr[i]+"type=" + queryConditionValueStr[i];
			if(queryConditionTypeStr[i].indexOf("7")!=-1){//存在查询类型为两者之间是的处理
				String[] str = queryValueStr[i].split(",");
				if(str.length == 2){
					sUrl =sUrl + "&"+queryFieldColStr[i]+"one"+i+"=" + str[0] + "&"+queryFieldColStr[i]+"two"+i+"=" + str[1];
				}
			}
		}
		return sUrl;
	}
	
	/**
	 * 保存辅助界面记录信息
	 * @param configLocal
	 * @param catalog_code 辅助界面编码
	 * @param elementTdList 辅助界面元素集合
	 * @param input_operatorCode 操作员
	 * @param guid 自动生成的uuid码
	 * @throws Exception
	 */
	public static void applyRecordAdd(ConfigLocal configLocal,String catalog_code,List elementTdList,Integer input_operatorCode,String guid) throws Exception{
		String[] s = new String[2];
		s = catalog_code.split(","); 
		String Object_id = s[1];
		String Object_type=s[2];
		String catalog_name = ConfigUtil.getPropertyNameById("config_catalog","catalog_name","catalog_code",s[0]);
		String Input_user=String.valueOf(input_operatorCode);
		String input_dept =ConfigUtil.getPropertyNameById("toperator","depart_id","op_code",String.valueOf(input_operatorCode));
		String recordAddField = "object_type,object_id,catalog_code,catalog_name,item_number,input_time,input_user,input_dept,relation_id,update_time";
		String recordAddValue = "'"+Object_type+"','"+Object_id+"','"+s[0]+"','"+catalog_name+"',"+String.valueOf(elementTdList.size())+",'"
			+new Timestamp(System.currentTimeMillis())+"',"+Input_user+","+input_dept+",'"+guid+"'"+",'"+new Timestamp(System.currentTimeMillis())+"'";
		configLocal.addConfigRecord(recordAddField,recordAddValue);//保存要素应用表
	}
	
	/**
	 * 保存辅助界面记录中所有元素信息
	 * @param configLocal
	 * @param elementTdList
	 * @param catalog_code
	 * @param guid
	 * @param request
	 * @throws Exception
	 */
	public static void applyDataAdd(ConfigLocal configLocal,List elementTdList,String catalog_code,String guid,HttpServletRequest request) throws Exception{
		String Result_value="",Value_name="";
		for(int i=0;i<elementTdList.size();i++){
			Map elementMap = (Map)elementTdList.get(i);
			String element_code = Utility.trimNull(elementMap.get("ELEMENT_CODE"));
			String element_name = Utility.trimNull(elementMap.get("ELEMENT_NAME"));
			String show_type = Utility.trimNull(elementMap.get("SHOW_TYPE"));
			String value_type = Utility.trimNull(elementMap.get("VALUE_TYPE"));
			String value_content = Utility.trimNull(elementMap.get("VALUE_CONTENT"));
			String edit_type = Utility.trimNull(elementMap.get("EDIT_TYPE")); 
			
			String propertyName = element_code;
			String value_name = "";
			
			String propertyValue=Utility.trimNull(request.getParameter(propertyName));//页面元素值
			if(show_type.equals("5")){//货币形式
				if(!"".equals(propertyValue)){
					Result_value=(propertyValue.replaceAll(",",""));
					Value_name="";
				}
			}else if(show_type.equals("1")&&edit_type.equals("4")){//多选框
				String checkboxResultValue="";
				if(request.getParameterValues(propertyName) != null){
					String[] strs = request.getParameterValues(propertyName);
					for(int j =0;j<strs.length;j++){
							String queryName="";
							String valueName="";
							String tableName="";
							if(!value_content.equals("")){
								int selectIndex =value_content.indexOf("select")+"select".length()+1;
								int dotIndex = value_content.indexOf(",");
								int fromIndex = value_content.indexOf("from");
								int whereIndex =value_content.indexOf("where");
								if(whereIndex == -1){
									whereIndex = value_content.length();
								}
								queryName = value_content.substring(selectIndex,dotIndex).trim();
								valueName = value_content.substring(dotIndex+1,fromIndex).trim();
								tableName = value_content.substring(fromIndex+4,whereIndex).trim();
								if(j == strs.length-1){//最后一个时不加逗号
									value_name =value_name + ConfigUtil.getPropertyNameById(tableName,valueName,queryName,strs[j]);
									checkboxResultValue =checkboxResultValue + strs[j];
								}else{
									value_name =value_name + ConfigUtil.getPropertyNameById(tableName,valueName,queryName,strs[j]) + ",";
									checkboxResultValue = checkboxResultValue + strs[j] + ",";
								}
							}
						}
				}
				
				Result_value=checkboxResultValue;
				Value_name=value_name;
			}else if(edit_type.equals("8")){
				Result_value="";
				Value_name="";
			}else{
				if("".equals(value_type)){
					Result_value=propertyValue;
					Value_name="";
				}else {
					if(value_type.equals("1")){//在config_region中获取
						Result_value=propertyValue;
						String[] queryId ={"item_code","region_code"};
						String[] queryValue ={Result_value,value_content};
						value_name = ConfigUtil.getPropertyNameByIdValue("config_region","item_name",queryId,queryValue);
						Value_name=value_name;
					}else if(value_type.equals("2")){//通过SQL语句取
						String queryName="";
						String valueName="";
						String tableName="";
						if(!value_content.equals("")){
							value_content = value_content.toUpperCase();
							int selectIndex =value_content.indexOf("SELECT")+"SELECT".length()+1;
							int dotIndex = value_content.indexOf(",");
							int fromIndex = value_content.indexOf("FROM");
							int whereIndex =value_content.indexOf("WHERE");
							if(whereIndex == -1){
								whereIndex = value_content.length();
							}
							queryName = value_content.substring(selectIndex,dotIndex).trim();
							valueName = value_content.substring(dotIndex+1,fromIndex).trim();
							tableName = value_content.substring(fromIndex+4,whereIndex).trim();
							Result_value=propertyValue;
							value_name =ConfigUtil.getPropertyNameById(tableName,valueName,queryName,Result_value);
							Value_name=value_name;
						}
					}
					
				}
			}
		String dataAddField = "relation_id,catalog_code,element_code,element_name,result_type,result_value,value_name";
		String dataAddValue = "'"+guid+"','"+catalog_code+"','"+element_code+"','"+element_name+"','"+show_type+"','"+Result_value+"','"+Value_name+"'";
		configLocal.addConfigData(dataAddField,dataAddValue);//保存配置数据表
		}
	}
	
	/**
	 * 辅助界面中所有元素信息修改
	 * @param configLocal
	 * @param elementTdList 辅助界面元素集合
	 * @param catalog_code 辅助界面编码
	 * @param request 请求
	 * @param record_id 界面元素ID
	 * @throws Exception
	 */
	public static void applyDataModify(ConfigLocal configLocal,List elementTdList,String catalog_code,HttpServletRequest request,Integer record_id) throws Exception{
		String Result_value="",Value_name="";
		String relation_id = ConfigUtil.getPropertyNameById("config_record","relation_id","record_id",String.valueOf(record_id));
		for(int i=0;i<elementTdList.size();i++){
			Map elementModMap = (Map)elementTdList.get(i);
			String element_code = Utility.trimNull(elementModMap.get("ELEMENT_CODE"));
			catalog_code = Utility.trimNull(elementModMap.get("CATALOG_CODE"));
			String show_type = Utility.trimNull(elementModMap.get("SHOW_TYPE"));
			String value_type = Utility.trimNull(elementModMap.get("VALUE_TYPE"));
			String value_content = Utility.trimNull(elementModMap.get("VALUE_CONTENT"));
			String edit_type = Utility.trimNull(elementModMap.get("EDIT_TYPE")); 
			String update_flag = Utility.trimNull(elementModMap.get("UPDATE_FLAG"));
			if(update_flag.equals("1")){//更新标识为是时更新
			String value_name = "";
			String propertyName = element_code;
			String propertyValue=Utility.trimNull(request.getParameter(propertyName));//页面元素值
			if(show_type.equals("5")){//货币形式
				if(!"".equals(propertyValue)){
					Result_value=propertyValue.replaceAll(",","");
				}
			}else if(show_type.equals("1")&&edit_type.equals("4")){//多选框
				String[] strs = request.getParameterValues(propertyName);
				String checkboxResultValue="";
				for(int j =0;j<strs.length;j++){
						String queryName="";
						String valueName="";
						String tableName="";
						if(!value_content.equals("")){
							int selectIndex =value_content.indexOf("select")+"select".length()+1;
							int dotIndex = value_content.indexOf(",");
							int fromIndex = value_content.indexOf("from");
							int whereIndex =value_content.indexOf("where");
							if(whereIndex == -1){
								whereIndex = value_content.length();
							}
							queryName = value_content.substring(selectIndex,dotIndex).trim();
							valueName = value_content.substring(dotIndex+1,fromIndex).trim();
							tableName = value_content.substring(fromIndex+4,whereIndex).trim();
							if(j == strs.length-1){
								value_name =value_name + ConfigUtil.getPropertyNameById(tableName,valueName,queryName,strs[j]);
								checkboxResultValue =checkboxResultValue + strs[j];
							}else{
								value_name =value_name + ConfigUtil.getPropertyNameById(tableName,valueName,queryName,strs[j]) + ",";
								checkboxResultValue = checkboxResultValue + strs[j] + ",";
							}
						}
					}
				Result_value=checkboxResultValue;
				Value_name=value_name;
			}else{//下拉框和单选框
				if("".equals(value_type)){
					Result_value=propertyValue;
					Value_name="";
				}else {
					if(value_type.equals("1")){//在config_region中获取
						Result_value=propertyValue;
						String[] queryId ={"item_code","region_code"};
						String[] queryValue ={Result_value,value_content};
						value_name = ConfigUtil.getPropertyNameByIdValue("config_region","item_name",queryId,queryValue);
						Value_name=value_name;
					}else if(value_type.equals("2")){//通过SQL语句取
						String queryName="";
						String valueName="";
						String tableName="";
						if(!value_content.equals("")){
							value_content = value_content.toUpperCase();
							int selectIndex =value_content.indexOf("SELECT")+"SELECT".length()+1;
							int dotIndex = value_content.indexOf(",");
							int fromIndex = value_content.indexOf("FROM");
							int whereIndex =value_content.indexOf("WHERE");
							if(whereIndex == -1){
								whereIndex = value_content.length();
							}
							queryName = value_content.substring(selectIndex,dotIndex).trim();
							valueName = value_content.substring(dotIndex+1,fromIndex).trim();
							tableName = value_content.substring(fromIndex+4,whereIndex).trim();
							Result_value=propertyValue;
							value_name =ConfigUtil.getPropertyNameById(tableName,valueName,queryName,Result_value);
							Value_name=value_name;
						}
					}
					
				}
			}
		if(!edit_type.equals("8")){
			String dataModValue = " result_value='"+Result_value+"',value_name='"+Value_name+"'";
			String dataModWhere = " relation_id='"+relation_id+"' and element_code='"+element_code+"'";
			configLocal.modConfigData(dataModValue,dataModWhere,record_id);//修改配置数据表
		}
		}
		}
		configLocal.modConfigRecord(record_id);//修改应用表
	}
	/**
	 * 某个界面类型中某个字段编码的个数
	 * @param configLocal
	 * @param codeName
	 * @param interfaceType_code
	 * @return
	 * @throws BusiException
	 */
	public static boolean isFieldExistContent(ConfigLocal configLocal,String codeName,String interfaceType_code) throws BusiException{
		List list =configLocal.interfaceFieldNums(codeName,interfaceType_code);
		if(list.size()>0){//已存在
			return false;
		}
		return true;
	}
	
	/**
	 * 某个辅助界面中某个元素编码的个数
	 * @param configLocal
	 * @param codeName
	 * @param interfaceType_code
	 * @return
	 * @throws BusiException
	 */
	public static boolean isFieldExistElement(ConfigLocal configLocal,String codeName,String interfaceType_code) throws BusiException{
		List list =configLocal.configElementNums(codeName,interfaceType_code);
		if(list.size()>0){//已存在
			return false;
		}
		return true;
	}

	/**
	 * 查询表单打印中的配置不定内容
	 * @param sql 查询sql
	 * @param fieldCount 查询字段个数
	 * @return
	 * @throws Exception
	 */
	public static String getQueryPrintSql(String sql,int fieldCount)
	throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		try {
			while (rs.next()){
				for(int i=0;i<fieldCount;i++){
					if(rs.getString(i+1)==null || "".equals(rs.getString(i+1))){
						sb.append(" "+"@@");
					}else{
						sb.append(rs.getString(i+1)+"@@");
					}
				}
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		System.out.println(new Timestamp(System.currentTimeMillis())+":"+sql);
		return sb.toString();
	}	

	/** 更新角色对应菜单信息
	 * @param roleID
	 * @param menuID
	 * @return
	 * @throws BusiException
	 */
	public static void dealRoleMenu(String roleID,String menuID) throws BusiException{
		//LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] sql = new String[4];
		//去掉不存在的角色下的菜单
		sql[0]="update CONFIG_MENU set relation_role=replace(relation_role,',"+roleID+"','') where menu_id not in ('"+menuID.replaceAll(",", "','")+"')";
		sql[1]="update CONFIG_MENU set relation_role=replace(relation_role,'"+roleID+"','') where menu_id not in ('"+menuID.replaceAll(",", "','")+"')";
		//更新选中的角色下的菜单
		sql[2]="update CONFIG_MENU set relation_role=replace(relation_role,',"+roleID+"','')+',"+roleID+"' where menu_id in ('"+menuID.replaceAll(",", "','")+"')";
		sql[3]="update CONFIG_MENU set relation_role=replace(relation_role,'"+roleID+"','')+'"+roleID+"' where menu_id in ('"+menuID.replaceAll(",", "','")+"')";
		initStartFlow(sql);
	}
	
	/** 获得查询sql
	 * @param queryMonth
	 * @param queryFlag
	 * @return
	 * @throws BusiException
	 */
	public static String getAnalyseSql(List checkList,String queryMonth,String queryFlag) throws BusiException{
		//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
		String dimensionsCode="",whereCondition="",sql="",tempSql="",orderNo="",typeID="",occurMonth="";
		for(int i=0;i<checkList.size();i++){ 
		    Map checkMap = (Map)checkList.get(i);
		    dimensionsCode=Utility.trimNull(checkMap.get("DIMENSIONS_CODE"));
		    whereCondition=Utility.trimNull(checkMap.get("WHERE_CONDITION"));
		    typeID=Utility.trimNull(checkMap.get("TYPE_ID"));
		    orderNo=Utility.trimNull(checkMap.get("ORDER_NO"));
		    for(int j=0;j<=11;j++){
		    	occurMonth=getRelativeMonth(queryMonth,-j);
			    if("3".equals(typeID))
			    	tempSql="(select convert(numeric(18, 2),sum("+dimensionsCode+")/10000) from T_REPORT_DATA "+whereCondition+" and Input_Date="+occurMonth+") AS D"+orderNo+occurMonth+" ";
			    else if("4".equals(typeID))
			    	tempSql="(select count("+dimensionsCode+") from T_REPORT_DATA "+whereCondition+" and Input_Date="+occurMonth+") AS D"+orderNo+occurMonth+" ";
			    if("".equals(sql)) sql=tempSql;
			    else  sql= sql + "," +tempSql;
		    }
		}
		sql= "select " + sql;
		return sql;
	}
	
	
	/** 获得当天指定格式日期
	 * @param type
	 * @param format
	 * @return
	 * @throws BusiException
	 */
	public static String getToday(String type,String format) throws BusiException{
		
		Timestamp time=new Timestamp(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat(format);
		String date = df.format(time);
		return date;
	}	
	
	/** 获得指定格式数字
	 * @param type
	 * @param format
	 * @return
	 * @throws BusiException
	 */
	public static String getMoney(String type,String format) throws BusiException{
		
		DecimalFormat   ddf=new   DecimalFormat( "###,###,##0.00 ");
		String   returnValue   =   ddf.format(Double.parseDouble(type)); 
		return returnValue;
	}	
	/**获得月份之间的累加值
	 * @param d1
	 * @param i
	 * @return
	 * @throws BusiException
	 */
	public static String getRelativeMonth(String dayMonth, int i) {
		String newYearMonth="";
		String s1 =dayMonth.substring(0,4) ;
		String s2 = dayMonth.substring(4,6);
		int i1 = Integer.parseInt(s1);
		int i2 = Integer.parseInt(s2);
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.YEAR, i1);
		calendar.set(Calendar.MONTH, i2-1);
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH)+1;
		
		calendar.add(Calendar.MONTH, i); 
		
		y = calendar.get(Calendar.YEAR);
		m = calendar.get(Calendar.MONTH)+1;
		
		int year = calendar.get(Calendar.YEAR); 
		int month = calendar.get(Calendar.MONTH)+1;// 这里月要加1
		
		if(month<10){
			newYearMonth = year + "0" + month ; 
		}else{
			newYearMonth = year + "" + month ;
		}
		
		//System.out.println("newYearMonth:"+newYearMonth);
		return newYearMonth;
	}
	
	//----------------以下几个方法是从ConfigBean移植过来----------------------------
	/**
	 * 显示某个页面下所有有效的字段
	 * @param vo
	 * @param edit_flag 新增、修改标识；1：新增；2：修改
	 * @param query_flag 查找字段类型标识;1：所有字段；2：未隐藏字段；3：隐藏字段
	 * @return
	 * @throws BusiException
	 */
	public static List listVaildFieldOrderNO(String interfaceType_code,String edit_flag,String query_flag)throws BusiException{
		List list = null;
		String querySql =" SELECT * FROM INTERFACE_FIELD WHERE INTERFACETYPE_CODE = '"+interfaceType_code+"' AND USE_STATE = '1' AND SHOW_TYPE !='6'  ";
		if(query_flag.equals("1"))
			querySql =querySql + " AND EDIT_TYPE!='8' ";
		else if(query_flag.equals("2"))
			querySql =querySql + " AND CHARINDEX('"+edit_flag+"',isnull(VISIBLED_FLAG,'')) != 0";//显示字段集合中包含分区元素
		else if(query_flag.equals("3"))
			querySql =querySql + " AND EDIT_TYPE!='8' AND CHARINDEX('"+edit_flag+"',isnull(VISIBLED_FLAG,'')) = 0";
		querySql = querySql + " ORDER BY ORDER_NO";
		list = CrmDBManager.listBySql(querySql);
		return list;
	}
	
	
	/**
	 * 新增页面元素
	 * @param table_name 表面
	 * @param addFieldName 新增字段集合
	 * @param addFieldValue 新增字段值集合
	 * @param addType 新增时主键是否自动生成标识 1：更新；2:不更新
	 * @throws BusiException
	 */
	public static void addInterfaceField(String table_name,String[] showTypeStrs,String[] addFieldName,String[] addFieldValue,String[] updateFlagStrs) throws BusiException{
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
		CrmDBManager.executeSql(addSql);
	}
	
	/**
	 * 修改页面数据
	 * @param table_name
	 * @param addFieldName
	 * @param addFieldValue
	 * @throws BusiException
	 */
	public static void modInterfaceField(String table_name,String[] showTypeStrs,String[] modFieldName,String[] modFieldValue,String identyityCol,String identityValue,String[] updateFlagStr) throws BusiException{
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
		CrmDBManager.executeSql(modSql);
	}
	
	/**
	 * 增加修改日志
	 * @param catalog_Code 配置界面编码
	 * @throws BusiException
	 */
	public static void modLogInfo(String catalog_Code,String table_code,String identityCode,String identity_code,String fieldColName,String fieldName,String beforeValue,String afterValue,String userID,String deptID) throws BusiException{
		String addSql=" insert into MODIFY_LOGINFO(INTERFACE_CODE,TABLE_NAME,Key_Code,Key_Value,FIELD_Code,FIELD_NAME,Before_value,After_value,UPDATE_TIME,UPDATE_USER,UPDATE_DEPT)"+
					  " values('"+catalog_Code+"','"+table_code+"','"+identityCode+"','"+identity_code+"','"+fieldColName+"','"+fieldName+"','"+beforeValue+"','"+afterValue+"','"+new Timestamp(System.currentTimeMillis())+"','"+userID+"','"+deptID+"')";
		CrmDBManager.executeSql(addSql);		
	}
	
	/**
	 * 查询对应的所有要素域
	 */
	public static List listRegionParams(String region_code)throws BusiException{
		List list = null;
		String sql ="SELECT * FROM CONFIG_REGION WHERE REGION_CODE ='" + region_code + "' and Use_state='1' ORDER BY ORDER_NO";
		list = CrmDBManager.listBySql(sql);
		return list;
	}
	
	/**
	 * 查询对应的要素域名称
	 * @param region_code 域编码
	 * @param item_code 项目编码
	 * @return
	 * @throws BusiException
	 */
	public static List listRegionParamName(String region_code,String item_code)throws BusiException{
		List list = null;
		String sql ="SELECT * FROM CONFIG_REGION WHERE REGION_CODE ='" + region_code + "' and ITEM_CODE='"+item_code+"' and Use_state='1'";
		list = CrmDBManager.listBySql(sql);
		return list;
	}
	
	/**
	 * 获取当前模板号下界面配置信息
	 * @param interfacetype_code 界面类别编码
	 * @return
	 * @throws BusiException
	 */
	public static List getConfigInterface(String interfacetype_code) throws BusiException{
		List list = null;
		String querySql = " select IC.*,IFD.* from INTERFACE_CATALOG IC,INTERFACE_FIELD IFD" +
						  " where IC.Interfacetype_Code=IFD.Interfacetype_Code "+
						  " and IC.Interfacetype_Code='"+interfacetype_code+"' and IFD.Use_State='1'"+
						  " order by IFD.Order_No";
		list = CrmDBManager.listBySql(querySql);
		return list;		
	}
	
	/**
	 * 查询修改页面显示数据
	 * @param sql 查询语句
	 * @return
	 * @throws BusiException
	 */
	public static List getInfoPageData(String querySql) throws BusiException{
		List list = null;
		list = CrmDBManager.listBySql(querySql);
		return list;
	}
	
	/**
	 * 删除页面数据
	 * @param table_code 表名
	 * @param identity_code 主键编码
	 * @param identity_value 主键值
	 * @throws BusiException
	 */
	public static void delInfo(String table_code,String identity_code,String identity_value) throws BusiException{
		String deleteSql="delete from "+table_code+" where "+identity_code+"="+identity_value;
		CrmDBManager.executeSql(deleteSql);
	}
	
	/**
	 * 删除关联数据
	 * @param identity_value 主键值
	 * @throws BusiException
	 */
	public static void delRelationInfo(String identity_value) throws BusiException{
		String deleteSql="delete from config_data where Relation_ID in (select Relation_ID from config_record where record_id='"+identity_value+"')";
		CrmDBManager.executeSql(deleteSql);
	}
	
	/**
	 * 初始化流程信息
	 * @param sql
	 * @throws BusiException
	 */
	public static void initStartFlow(String[] sql) throws BusiException{
		CrmDBManager.batchUpdate(sql);
	}
	//----------------以上几个方法是从ConfigBean移植过来----------------------------
}


