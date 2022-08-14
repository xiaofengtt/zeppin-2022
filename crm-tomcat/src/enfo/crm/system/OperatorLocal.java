package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustLevelAuthVO;
import enfo.crm.vo.OperatorVO;
import enfo.crm.vo.TOperatorVO;
import enfo.crm.vo.TcustmanagersVO;

public interface OperatorLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 基础信息-新增员工
	 * @author 
	 * @param vo
	 * @param inputOperatorCode
	 * @throws BusiException
	 *	@IN_OP_CODE     INT,                                      
		@IN_OP_NAME     VARCHAR(20),                              
		@IN_DEPART_ID   INT,                                      
		@IN_ROLE_ID     INT,                                      
		@IN_SUMMARY     VARCHAR(200),                             
		@IN_INPUT_MAN   INT,                                      
		@IN_ADDRESS     NVARCHAR (60) ,                           
		@IN_EMAIL       NVARCHAR (40) ,                           
		@IN_O_TEL       NVARCHAR (40) ,                           
		@IN_MOBILE      NVARCHAR (100),                           
		@IN_LOGIN_USER  NVARCHAR(20), -- 登录用户名               
		@IN_CARD_TYPE   VARCHAR(10), --身份证件/证明文件类型(1108)
		@IN_CARD_ID     VARCHAR(30), --身份证件/证明文件号码      
		@IN_CARD_VALID_DATE  INT     --身份证件/证明文件有效期限  
	
	
	 */
	void appendOperator(TOperatorVO vo, Integer inputOperatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 基础信息-查询员工信息
	 * @author 鲁大深
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listOpAll(TOperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 基础信息-查询INTRUST操作员的列表
	 * @author
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getToperIntrust(TOperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 基础信息-修改员工信息
	 * @author 
	 * @param vo
	 * @param inputOperatorCode
	 * @throws BusiException
	 *  @IN_OP_CODE     INT,                                      
		@IN_OP_NAME     VARCHAR(20),                              
		@IN_DEPART_ID   INT,                                      
		@IN_ROLE_ID     INT,                                      
		@IN_SUMMARY     VARCHAR(200),                             
		@IN_INPUT_MAN   INT,                                      
		@IN_ADDRESS     NVARCHAR (60) ,                           
		@IN_EMAIL       NVARCHAR (40) ,                           
		@IN_O_TEL       NVARCHAR (40) ,                           
		@IN_MOBILE      NVARCHAR (100),                           
		@IN_INIT_FLAG   INT = 0,  -- 1 初始化密码                 
		@IN_LOGIN_USER  NVARCHAR(20),  -- 登录用户名              
		@IN_CARD_TYPE   VARCHAR(10), --身份证件/证明文件类型(1108)
		@IN_CARD_ID     VARCHAR(30), --身份证件/证明文件号码      
		@IN_CARD_VALID_DATE  INT     --身份证件/证明文件有效期限  
	 */
	void modiOperator(TOperatorVO vo, Integer inputOperatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 基础信息-修改操作员映射关系表
	 * @author 
	 * @param vo
	 * @param inputOperatorCode
	 * @throws BusiException
	 * @IN_CRM_Operator INT, --对应CRM系统中TOperator.OP_CODE
	   @IN_INTRUST_Operator INT, --对应Intrust系统中TOperator.OP_CODE
	   @IN_INTRUST_BOOKCODE INT, --操作员登录后，并进入了Intrust系统后，该字段保存进入了Intrust系统的哪个帐套
	   @IN_ETRUST_Operator INT, --对应eTrust系统中TOperator.OP_CODE
	   @IN_INPUT_MAN   INT限  
	 */
	void modiTopertorMap(TOperatorVO vo, Integer inputOperatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 基础信息-删除员工信息
	 * @author 鲁大深
	 * @param vo
	 * @param input_man
	 * @throws BusiException
	 */
	void delete(TOperatorVO vo, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 基础信息-分页查询
	 * @author 鲁大深
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listProcPage(TOperatorVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询该用户的角色
	 * 
	 * @author 鲁大深
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listSerno(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 增加员工角色
	 * @param vo
	 * @param input_man
	 * @throws BusiException
	 */
	void appendOperator2(OperatorVO vo, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改登录密码
	 * @author TSG 
	 * 
	 * SP_MODI_PASSWORD @IN_OP_CODE INT,
						@IN_OLD_PASSWORD VARCHAR(40),
						@IN_NEW_PASSWORD VARCHAR(40),
						@OUT_RET_CODE INT OUTPUT
	 * */
	void changePass(Integer opcode, String old_password, String new_pass1, String new_pass2) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改登录别名
	 * @author TSG
	 * 
	 * SP_MODI_LOGIN_USER @IN_LOGIN_USER     NVARCHAR(20),
						  @IN_NEW_LOGIN_USER NVARCHAR(20),
						 -- @OUT_RET_CODE INT OUTPUT
	 * */
	void changeLoginUser(String old_login_user, String new_login_user) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询当前的在线用户，并以分页的形式显示
	 * @author 丁亚军
	 * @param pageIndex 当前的第几页
	 * @param pageSize 每页显示的记录数
	 * @return
	 * @throws BusiException
	*/
	IPageList listOnLineAllOp(int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 初始化报警提示
	 * @param op_code
	 * @param book_code
	 * @throws Exception
	 */
	void updateBookCode(Integer op_code) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 退出登陆
	 * @param op_code
	 * @throws Exception
	 */
	void logOut(Integer op_code) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 根据login_user查询登陆员工的Op_Code
	 */
	List listOpCodeByUser(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_LOGIN @IN_OP_CODE SMALLINT,
	            @IN_PASSWORD VARCHAR(40),
	            @IN_IP_ADDRESS VARCHAR(15),
				--@OUT_BOOK_CODE TINYINT OUTPUT,
	            @OUT_RET_CODE INT OUTPUT
	            
			    @OUT_RET_CODE = 100
			    @OUT_RET_CODE =  @V_RET_CODE-1  -- 操作员不存在
			    @OUT_RET_CODE = @V_RET_CODE-2  -- 密码不对           
	 * */
	Integer loginSystem(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 单点登录 登录方法
	 * @param vo
	 * @throws BusiException 
	 */
	void loginSystem2(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_MAIN_MENURIGHT  @IN_OP_CODE  INT,
								@IN_MAIN_ID  CHAR(1),
								@OUT_FLAG    INT OUTPUT
	 */
	boolean checkMainMenu(String menu_id, String op_code) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改密码
	 */
	Integer modiPassWord(Integer op_code, String oldPassWord, String newPassWord) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改密码
	 */
	String modiPassWord(OperatorVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改密码
	 */
	String modiLoginUser(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 *	2008-10-29 YZJ
	 * 通过“员工号”，来查询该员工可操作的账套列表（账套号、账套名称)个数
	 * 存储过程：SP_QUERY_OP_BOOKRIGHT  IN_OP_CODE	SMALL	员工号
	 * @param opcode
	 * @return
	 * @throws BusiException
	 */
	List listCodeAcount(Integer opcode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listOpAll(OperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询待处理的内部事务
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList listInsideServiceTasks(OperatorVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询待处理的外部事务
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList listOutsideServiceTasks(OperatorVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询当天日程事务
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listScheDule(OperatorVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询发给当前操作员的系统消息
	 * @param vo
	 *     @IN_SERIAL_NO           INT,              --序号
	 *     @IN_TITLE               NVARCHAR(200),    --消息主题
	 *     @IN_FROM_OPCODE         INT,              --消息发起人
	 *     @IN_TO_OPCODE           INT,              --消息接收人   
	 *     @IN_DATE_START          INT,              --消息日期（起）
	 *     @IN_DATE_END            INT,              --消息日期（止）
	 *     @IN_IS_READ             INT,              --是否已阅读 1否2是3已反馈
	 *     @IN_INPUT_MAN           INT               --操作员
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listSysMessage(OperatorVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void addToperator_map(TOperatorVO vo, Integer inputOperatorCode) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void addSysMessage(OperatorVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void addCustLevelAuth(CustLevelAuthVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	void delCustLevelAuth(CustLevelAuthVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * IN_OP_CODE
		IN_PRODUCT_ID
		IN_LEVEL_ID
		IN_LEVEL_VALUE_ID
		IN_LEVEL_VALUE_NAME
		IN_INPUT_MAN
	
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList listCustLevelAuth(CustLevelAuthVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param menu_id
	 * @param op_code
	 * @param vierStr
	 * @throws Exception
	 */
	void addMenuView(String menu_id, Integer op_code, String vierStr) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param menu_id
	 * @param op_code
	 * @return
	 * @throws Exception
	 */
	String listMenuView(String menu_id, Integer op_code) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询常用工作台模块
	 * @param opCode
	 * @return
	 * @throws Exception
	 */
	String getWorkBench(Integer opCode) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改常用工作台模块
	 * @param opCode
	 * @param workbench
	 * @throws Exception
	 */
	void modiWorkBench(Integer opCode, String workbench) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 同步操作员
	 * @param opCode
	 * @throws BusiException
	 */
	void syschroOperator(Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryProductMarketing(TcustmanagersVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List queryProductMarketing1(TcustmanagersVO vo) throws BusiException;

	/**
	 * 查询即将到期客户
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustDue(Integer opCode, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询存量客户
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List listEfficientCust(Integer opCode) throws BusiException;

	/**
	 * 客户经理业绩排名
	 * @ejb.interface-method view-type = "local"
	 * @param opCode
	 * @return
	 * @throws BusiException
	 */
	List listCustManagersRank(Integer opCode) throws BusiException;

	/**
	 * 销售团队统计
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List StatTeamRank(TOperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 修改用户选择的产品
	 * 
	 * @throws BusiException
	 */
	void changeProduct(TOperatorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 修改初始页面显示行数
	 * 
	 * @throws BusiException
	 */
	void changePagesize(Integer op_code, String pagesize) throws BusiException;

}