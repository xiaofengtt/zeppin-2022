package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.MessageVO;

public interface MessageLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * 查询自定义工作提示----分页显示---返回pagelist
	 * SP_QUERY_TTASKINFO 
						 @IN_INPUT_MAN	INT	操作员
						 @IN_OP_CODE	INT	发起人
						 @IN_TITLE	VARCHAR(100)	标题
						 @IN_SERIAL_NO	INT	序号
	
	 *
	*/
	IPageList listPrivateMessage(MessageVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * 查询自定义工作提示----
	 * SP_QUERY_TTASKINFO
						 @IN_INPUT_MAN	INT	操作员
						 @IN_OP_CODE	INT	发起人
						 @IN_TITLE	VARCHAR(100)	标题
						 @IN_SERIAL_NO	INT	序号
	
	 *
	*/
	List listPrivateMessage(MessageVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * 查询阅读人信息---
	 * SP_QUERY_TTASKLIST @IN_REC_NO INT
	 *
	 */
	List listReaders(Integer rec_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-08
	 * 查询通知的回复信息
	 * SP_QUERY_TTASKREPLY  @IN_REC_NO INT
	 *
	*/
	List listReply(Integer rec_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-08
	 * 添加工作提示的回复信息
	 *
	 * SP_ADD_TTASKREPLY @IN_REC_NO	INT	对应工作提示信息表记录号SERIAL_NO
						@IN_REPLY_STR	NTEXT	内容
						@IN_INPUT_MAN	INT	操作员
	
	 *
	 */
	void appendReply(Integer rec_no, String reply_str, Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * 添加工作提示
	 *
	 * SP_ADD_TTASKINFO   @IN_BOOK_CODE	TINYINT	帐套
						 @IN_TITLE	VARCHAR(100)	标题
						 @IN_INFO_STR	NTEXT	内容
						 @IN_END_DATE	INT	通知结束日期
						 @IN_INPUT_MAN	INT	操作员
						 @OUT_SERIAL_NO	INT	序号
	
	 *
	 */
	Integer appendMessage(MessageVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * 修改工作提示
	 *
	 * SP_MODI_TTASKINFO_COMM
	                @IN_SERIAL_NO  INT,
	                @IN_TITLE      VARCHAR(100),
	                @IN_INFO_STR   NTEXT,
	                @IN_END_DATE   INT,
	                @IN_INPUT_MAN  INT
	 *
	 */
	void modiMessage(MessageVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * 删除工作提示阅读人信息
	 *
	 * SP_DEL_TTASKLIST_ALL @IN_REC_NO INT
	 *
	 */
	void delReaders(Integer rec_no) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * 添加工作提示的阅读人信息
	 *
	 * SP_ADD_TTASKLIST @IN_REC_NO INT,
	                   @IN_OP_CODE INT,
	                   @IN_INPUT_MAN INT
	 *
	 */
	void appendReaders(Integer rec_no, Integer op_code, Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * 删除工作提示信息
	 *
	 * SP_DEL_TTASKINFO  @IN_SERIAL_NO INT,
	                    @IN_INPUT_MAN INT,
	                    @IN_FLAG      INT = 1 -- 标志：1 删除  2 关闭 3 重开
	 *
	 */
	void delMessage(Integer serial_no, Integer input_man, Integer flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-06
	 * 查询由系统发出的工作提示---
	 * SP_QUERY_TTASKINFO_COMM   
								@IN_OP_CODE	INT	操作员
								@IN_TASK_TYPE	INT	90合同到期提醒、91计息提醒
								@IN_BUSI_ID	VARCHAR(10)	合同类别：120301贷款，120302租赁120305投资
								@IN_TITLE	VARCHAR(100)	标题
								@IN_READ_FLAG	INT	1未读2已读0全部
								@IN_SERIAL_NO	INT	TTASKLIST表中的序号
					
	 *
	*/
	List listSystemMessage(MessageVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ADD BY TSG 2008-08-08
	 * 修改工作提示的阅读标志
	 *
	 * SP_MODI_TTASKINFO  @IN_SERIAL_NO	INT	序号
						 @IN_READ_FLAG	INT	阅读标志：1未读，2已读3不再提示
						 @IN_INPUT_MAN	INT	员工号
	
	 *
	 */
	void modiMessageReadFlag(Integer serial_no, Integer read_flag, Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ADD BY TSG 2008-08-11 后台调用
	 * 查询自定义工作提示----分页显示---返回pagelist
	 * SP_QUERY_TTASKINFO_1 @IN_BOOK_CODE 	INT	帐套
							   @IN_OP_CODE	INT	发起人
							   @IN_TITLE	VARCHAR(100)	标题
							   @IN_SERIAL_NO	INT	序号
	
	 *
	*/
	IPageList listPrivateMessage1(MessageVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ADD BY TSG 2008-08-11 后台调用
	 * 查询自定义工作提示----分页显示---返回list
	 * SP_QUERY_TTASKINFO_1 @IN_BOOK_CODE 	INT	帐套
						   @IN_OP_CODE	INT	发起人
						   @IN_TITLE	VARCHAR(100)	标题
						   @IN_SERIAL_NO	INT	序号
	
	 *
	*/
	List listPrivateMessage1(MessageVO vo) throws BusiException;

}