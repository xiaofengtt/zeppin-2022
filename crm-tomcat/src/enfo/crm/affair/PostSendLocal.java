package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PostSendVO;

public interface PostSendLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增
	 * @param vo
	    @IN_INPUT_DATE       INTEGER,          --邮寄日期
	    @IN_PRODUCT_ID       INTEGER,          --产品ID
	    @IN_CONTRACT_SUB_BH  NVARCHAR(200),    --合同编号
	    @IN_POST_NO          NVARCHAR(30),     --邮寄单号
	    @IN_POST_CONTENT     NVARCHAR(30),     --邮寄内容（1成立公告2确认单3管理报告4临时信息披露5终止公告6其他。可能有多个，则用|间隔）
	    @IN_SUMMARY          NVARCHAR(500),    --备注
	 */
	void append(PostSendVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改
	 * @param vo
	   @IN_INPUT_DATE       INTEGER,           --邮寄日期
	   @IN_PRODUCT_ID       INTEGER,           --产品ID
	   @IN_CONTRACT_SUB_BH  NVARCHAR(200),    --合同编号
	   @IN_POST_NO           NVARCHAR(30),     --邮寄单号
	   @IN_POST_CONTENT  NVARCHAR(30),     --邮寄内容（1成立公告2确认单3管理报告4临时信息披露5终止公告6其他。可能有多个，则用|间隔）
	   @IN_SUMMARY           NVARCHAR(500),    --备注
	   @IN_INPUT_MAN        INTEGER           --操作员
	 */
	void modi(PostSendVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询
	    @IN_INPUT_DATE  INTEGER,           --邮寄日期
	    @IN_PRODUCT_ID       INTEGER,          --产品ID
	    @IN_CONTRACT_SUB_BH  NVARCHAR(200),    --合同编号
	    @IN_POST_NO          NVARCHAR(30),     --邮寄单号
	    @IN_POST_CONTENT     NVARCHAR(30)      --邮寄内容（1成立公告2确认单3管理报告4临时信息披露5终止公告6其他。可能有多个，则用|间隔）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(PostSendVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询
	    @IN_INPUT_DATE  INTEGER,           --邮寄日期
	    @IN_PRODUCT_ID       INTEGER,          --产品ID
	    @IN_CONTRACT_SUB_BH  NVARCHAR(200),    --合同编号
	    @IN_POST_NO          NVARCHAR(30),     --邮寄单号
	    @IN_POST_CONTENT     NVARCHAR(30)      --邮寄内容（1成立公告2确认单3管理报告4临时信息披露5终止公告6其他。可能有多个，则用|间隔）
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_query(PostSendVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询
	    @IN_PRODUCT_ID       INTEGER,          --产品ID
	    @IN_CUST_NAME         NVARCHAR(50)='', --客户名称
		@IN_CONTRACT_SUB_BH	 VARCHAR(80)=''	   --合同编号
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList query(PostSendVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改
	 * @param vo
	   @IN_INPUT_DATE       INTEGER,           --邮寄日期
	   @IN_PRODUCT_ID       INTEGER,           --产品ID
	   @IN_CONTRACT_SUB_BH  NVARCHAR(200),    --合同编号
	   @IN_POST_NO           NVARCHAR(30),     --邮寄单号
	   @IN_POST_CONTENT  NVARCHAR(30),     --邮寄内容（1成立公告2确认单3管理报告4临时信息披露5终止公告6其他。可能有多个，则用|间隔）
	   @IN_SUMMARY           NVARCHAR(500),    --备注
	   @IN_INPUT_MAN        INTEGER           --操作员
	   @IN_SERIAL_NO        INTEGER           --记录序号
	 */
	void batchModi(PostSendVO vo) throws BusiException;

}