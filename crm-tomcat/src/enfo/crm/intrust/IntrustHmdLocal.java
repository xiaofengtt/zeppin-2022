package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.HmdVO;

public interface IntrustHmdLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询黑名单——分页 CREATE PROCEDURE SP_AML_QUERY_TBLACKLIST
	 * 
	 * @IN_FULL_NAME_C NVARCHAR (60), --中文全称
	 * @IN_FOR_SHORT_C NVARCHAR (30), --中文简称
	 * @IN_FULL_NAME_E NVARCHAR (80), --英文全称
	 * @IN_FOR_SHORT_E NVARCHAR (30), --英文简称
	 * @IN_OTHER_LANG_NAME NVARCHAR (80), --其他语言名称
	 * @IN_CLASSIFICATION_NO INTEGER , --分类编号,1恐怖分子、2防止武器扩散、3其他制裁等
	 * @IN_CATEGORY_NO INTEGER , --类别编号,1个人、2组织
	 * @IN_CARD_TYPE NVARCHAR (40), --证件类型
	 * @IN_CARD_NO NVARCHAR (40), --证件编号
	 * @IN_COUNTRY VARCHAR(30), --所属国家
	 * @IN_EXPLANATION VARCHAR(200) --简要说明
	 * 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList queryBlack(HmdVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询公司基本信息——列表显示
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_hmd(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询别名信息——列表显示 CREATE PROCEDURE SP_AML_QUERY_TALIAS
	 * 
	 * @IN_BIRTH_NAME_ID INTEGER --对应TBLACKLIST中的SERIAL_NO
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_bm(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 新增黑名单信息 CREATE PROCEDURE SP_ADD_TBLACKLIST
	 * 
	 * @IN_FULL_NAME_C NVARCHAR (60), --中文全称
	 * @IN_FOR_SHORT_C NVARCHAR (30), --中文简称
	 * @IN_FULL_NAME_E NVARCHAR (80), --英文全称
	 * @IN_FOR_SHORT_E NVARCHAR (30), --英文简称
	 * @IN_OTHER_LANG_NAME NVARCHAR (80), --其他语言名称
	 * @IN_CLASSIFICATION_NO INTEGER , --分类编号,1恐怖分子、2防止武器扩散、3其他制裁等
	 * @IN_CATEGORY_NO INTEGER , --类别编号
	 * @IN_CARD_TYPE NVARCHAR (40), --证件类型
	 * @IN_CARD_NO NVARCHAR (40), --证件编号
	 * @IN_COUNTRY VARCHAR(30), --所属国家
	 * @IN_EXPLANATION VARCHAR(200), --简要说明
	 * @IN_INPUT_MAN INT --操作员
	 */
	Integer addBlack(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 修改黑名单 CREATE PROCEDURE SP_AML_MODI_TBLACKLIST
	 * 
	 * @IN_SERIAL_NO INTEGER, --流水号
	 * @IN_FULL_NAME_C NVARCHAR (60), --中文全称
	 * @IN_FOR_SHORT_C NVARCHAR (30), --中文简称
	 * @IN_FULL_NAME_E NVARCHAR (80), --英文全称
	 * @IN_FOR_SHORT_E NVARCHAR (30), --英文简称
	 * @IN_OTHER_LANG_NAME NVARCHAR (80), --其他语言名称
	 * @IN_CLASSIFICATION_NO INTEGER , --分类编号,1恐怖分子、2防止武器扩散、3其他制裁等
	 * @IN_CATEGORY_NO INTEGER , --类别编号,1个人、2组织
	 * @IN_CARD_TYPE NVARCHAR (40), --证件类型
	 * @IN_CARD_NO NVARCHAR (40), --证件编号
	 * @IN_COUNTRY VARCHAR(30), --所属国家
	 * @IN_EXPLANATION VARCHAR(200), --简要说明
	 * @IN_INPUT_MAN INTEGER --操作员
	 * @param vo
	 * @throws BusiException
	 */

	void modiBlack(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 增加别名 CREATE PROCEDURE SP_ADD_TALIAS
	 * 
	 * @IN_FULL_NAME_C NVARCHAR (60), --中文全称
	 * @IN_FOR_SHORT_C NVARCHAR (30), --中文简称
	 * @IN_FULL_NAME_E NVARCHAR (80), --英文全称
	 * @IN_FOR_SHORT_E NVARCHAR (30), --英文简称
	 * @IN_OTHER_LANG_NAME NVARCHAR (80), --其他语言名称
	 * @IN_BIRTH_NAME_ID INTEGER, --对应TBLACKLIST中的SERIAL_NO
	 * @IN_CLASSIFICATION_NO NVARCHAR (10),
	 *                       --分类编号,511701恐怖分子、511702防止武器扩散、511703其他制裁等
	 * @IN_CATEGORY_NO NVARCHAR (10), --类别编号,511801个人、511802组织
	 * @IN_INPUT_MAN INT --操作员
	 * @param vo
	 * @throws BusiException
	 */

	void addTalias(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 删除黑名单 CREATE PROCEDURE SP_AML_DEL_TBLACKLIST
	 * 
	 * @IN_SERIAL_NO INT, --序号
	 * @IN_INPUT_MAN INT --操作员
	 * @param vo
	 * @throws BusiException
	 */

	void removeBlack(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 删除化名
	 * 
	 * @param vo
	 * @throws BusiException
	 */
	void removeTalis(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询公司基本信息——列表显示
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryAll(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 根据条件检查现有客户中与黑名单信息匹配的记录
	 * 
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList checkCustList(Object params[], int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询黑名单文档——分页 CREATE PROCEDURE SP_AML_QUERY_TBLACKLIST_FILE
	 * 
	 * @IN_SERIAL_NO INTEGER,
	 * @IN_FILE_NAME NVARCHAR (60),--文档名称
	 * @IN_CLASSIFICATION_NO NVARCHAR
	 *                       (10),--分类编号,511701恐怖分子名单、511702防止武器扩散名单、511703其他制裁名单
	 * @IN_FILE_DATE NVARCHAR (8) --日期
	 * 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList queryBlackFile(HmdVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 新增制裁名单信息 CREATE PROCEDURE SP_ADD_TBLACKLIST_FILE
	 * 
	 * @IN_SERIAL_NO INT, --主键
	 * @IN_FILE_NAME NVARCHAR (60), --文档名称
	 * @IN_CLASSIFICATION_NO NVARCHAR (10),
	 *                       --分类编号,511701恐怖分子名单、511702防止武器扩散名单、511703其他制裁名单
	 * @IN_INPUT_MAN INT, --操作员
	 * @OUT_ID INT OUTPUT
	 */
	Integer addBlackListFile(HmdVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 删除黑名单 CREATE PROCEDURE SP_AML_DEL_TBLACKLIST_FILE
	 * 
	 * @IN_SERIAL_NO INT, --序号
	 * @IN_INPUT_MAN INT --操作员
	 * @param vo
	 * @throws BusiException
	 */

	void removeBlackFile(HmdVO vo) throws BusiException;

}