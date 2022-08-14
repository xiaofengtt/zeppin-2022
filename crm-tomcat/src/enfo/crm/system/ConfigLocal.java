package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;

public interface ConfigLocal extends IBusiExLocal {

	/**
	 * 查询某个类别名称下的要素名称按order_no排序
	 * @param catalog_code 类型编码
	 * @param record_id 判断新增修改标识
	 * @param queryFlag 查询类型
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listElementOrderNO(String catalog_code, Integer record_id, String queryFlag) throws BusiException;

	/**
	 * 查询对应的所有要素域
	 * @ejb.interface-method view-type = "local"
	 */
	List listRegionParams(String region_code) throws BusiException;

	/**
	 * 查询对应的所有类别
	 * @ejb.interface-method view-type = "local"
	 */
	List listBySql(String catalog_code) throws BusiException;

	/**
	 * 查询对应的要素域名称
	 * @param region_code 域编码
	 * @param item_code 项目编码
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listRegionParamName(String region_code, String item_code) throws BusiException;

	/**
	 * 新增要素应用表数据
	 * @param addField 新增字段
	 * @param addValue 新增字段值
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void addConfigRecord(String addField, String addValue) throws BusiException;

	/**
	 *  新增配置表数据
	 * @ejb.interface-method view-type = "local"
	 */
	void addConfigData(String addField, String addValue) throws BusiException;

	/**
	 * 查询配置表数据
	 * @param relation_id config_record关联ID
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listDataValues(String relation_id, String query_flag) throws BusiException;

	/**
	 * 获取某个表的主键值集
	 * @param interfacetype_code 界面类别编码
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listTableIdentity(String interfacetype_code) throws BusiException;

	/**
	 * 获取当前模板号下界面配置信息
	 * @param interfacetype_code 界面类别编码
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List getConfigInterface(String interfacetype_code) throws BusiException;

	/**
	 * 获取当前模板号下界面按钮信息
	 * @param interfacetype_code 界面类别编码
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List getConfigButton(String interfacetype_code) throws BusiException;

	/**
	 * 获取某个表的表名
	 * @param interfacetype_code 界面类别编码
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listTableName(String interfacetype_code) throws BusiException;

	/**
	 * 修改配置表数据
	 * @param updateValue 修改字段
	 * @param queryWhere where条件
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void modConfigData(String updateValue, String queryWhere, Integer record_id) throws BusiException;

	/**
	 * 修改应用表日期
	 * @param record_id
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void modConfigRecord(Integer record_id) throws BusiException;

	/**
	 * 修改时查询已经存在的配置数据
	 * @param relation_id
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listExistDataValues(String relation_id, String query_flag, String catalog_code) throws BusiException;

	/**
	 * 显示所有有效的表格显示字段
	 * @param interfaceType_Id
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List tableFieldShowList(String interfaceType_code) throws BusiException;

	/**
	 * 显示所有不显示的表格显示字段
	 * @param interfaceType_Id
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List tableNoShowList(String interfaceType_code) throws BusiException;

	/**
	 * 查询所有有效的查询字段
	 * @ejb.interface-method view-type = "local"
	 */
	List queryShowList(String interfaceType_code) throws BusiException;

	/**
	 * 查询页面列表数据包括查询块之外的查询条件
	 * @param interfaceType_code 模板编号
	 * @param tableName 表名
	 * @param queryFieldCol 查询块查询字段列集
	 * @param queryFieldValue 查询块查询字段列值集
	 * @param conditionType 需要显示的字段的查询类型集
	 * @param otherQueryStr 其他查询查询条件语句
	 * @return
	 * @throws Exception 
	 * @ejb.interface-method view-type = "local"
	 */
	IPageList showPageDataOther(String interfaceType_code, String tableName, String[] queryFieldCol,
			String[] queryFieldValue, String[] conditionType, String otherQueryStr, int pageIndex, int pageSize)
			throws Exception;

	/**
	 * 查看页面数据
	 * @param showSql 查询语句
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	IPageList getListPageData(String showSql, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查看页面数据
	 * @param tableName 表名
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	IPageList previewPageData(String tableName, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 显示某个页面下所有有效的字段
	 * @param vo
	 * @param edit_flag 新增、修改标识；1：新增；2：修改
	 * @param query_flag 查找字段类型标识;1：所有字段；2：未隐藏字段；3：隐藏字段
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listVaildFieldOrderNO(String interfaceType_code, String edit_flag, String query_flag) throws BusiException;

	/**
	 * 查询查看页面需要显示的字段
	 * @param vo 查询VO
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listVaildViewFieldOrderNO(String interfaceType_code) throws BusiException;

	/**
	 * 新增页面元素
	 * @param table_name 表面
	 * @param addFieldName 新增字段集合
	 * @param addFieldValue 新增字段值集合
	 * @param addType 新增时主键是否自动生成标识 1：更新；2:不更新
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void addInterfaceField(String table_name, String[] showTypeStrs, String[] addFieldName, String[] addFieldValue,
			String[] updateFlagStrs) throws BusiException;

	/**
	 * 修改页面数据
	 * @param table_name
	 * @param addFieldName
	 * @param addFieldValue
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void modInterfaceField(String table_name, String[] showTypeStrs, String[] modFieldName, String[] modFieldValue,
			String identyityCol, String identityValue, String[] updateFlagStr) throws BusiException;

	/**
	 * 查询修改页面显示数据
	 * @param sql 查询语句
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List getInfoPageData(String querySql) throws BusiException;

	/**
	 * 查询修改页面显示数据
	 * @param table_name 表面
	 * @param identity_code 唯一字段名
	 * @param identity_codeValue 唯一字段值
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List modPageShowData(String table_name, String identity_code, String identity_codeValue) throws BusiException;

	/**
	 * 删除页面数据
	 * @param table_code 表名
	 * @param identity_code 主键编码
	 * @param identity_value 主键值
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void delInfo(String table_code, String identity_code, String identity_value) throws BusiException;

	/**
	 * 删除关联数据
	 * @param identity_value 主键值
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void delRelationInfo(String identity_value) throws BusiException;

	/**
	 * 增加修改日志
	 * @param catalog_Code 配置界面编码
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void modLogInfo(String catalog_Code, String table_code, String identityCode, String identity_code,
			String fieldColName, String fieldName, String beforeValue, String afterValue, String userID, String deptID)
			throws BusiException;

	/**
	 * 界面字段表中存在某个界面类型编码的集合
	 * @param interfacetype_code 界面类型编码
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List interfaceFieldList(String interfacetype_code) throws BusiException;

	/**
	 * 界面字段表中存在某个界面类型编码的集合
	 * @param region_code 界面类型编码
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List interfaceRegionList(String region_code) throws BusiException;

	/**
	 * 界面元素表中存在界面辅助类型编码的集合
	 * @param catalog_code
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List configElementList(String catalog_code) throws BusiException;

	/**
	 * 数据配置表中某个界面类型编码和关联ID对应数据集合
	 * @param catalog_code 界面类型编码
	 * @param relation_id 关联ID
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List configDataList(String catalog_code, String relation_id) throws BusiException;

	/**
	 * 某个界面类型中某个字段编码的个数
	 * @param codeName 字段编码
	 * @param interfaceType_code 界面类型
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List interfaceFieldNums(String codeName, String interfaceType_code) throws BusiException;

	/**
	 * 某个辅助界面类型中某个要素编码的个数
	 * @param elementCode 要素编码
	 * @param catalogCode 辅助界面类型
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List configElementNums(String elementCode, String catalogCode) throws BusiException;

	/**
	 * 取模板配置所有的信息
	 * @param showType
	 * @param codeName
	 * @param showSql
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List showSelectInfo(String showType, String codeName, String showSql) throws BusiException;

	/**
	 * 取主菜单对应的信息
	 * @param MenuID
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List showMenuInfo(String MenuID, String opCode, String product_type) throws BusiException;

	/**
	 * 取主菜单所有的信息
	 * @param roleID
	 * @param existFlag
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listAllMenu(String roleID, String existFlag) throws BusiException;

	/**
	 * 取打印配置所有的信息
	 * @param formCode
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List queryPrintContentList(String formCode) throws BusiException;

	/**
	 * 取系统主菜单所有的信息
	 * @param MenuID
	 * @param MenuType
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List getMenuInfo(String MenuID, String MenuType, String opCode, String productType) throws BusiException;

	//@Override
	void remove();

}