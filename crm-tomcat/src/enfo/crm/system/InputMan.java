package enfo.crm.system;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Utility;

public class InputMan
{
	private java.lang.Integer op_code;
	private java.lang.String op_name;
	private java.lang.String password;
	private java.lang.Integer reg_date;
	private java.lang.Integer book_code;
	private java.lang.String ip_address;
	private java.lang.Integer cancel_date;
	private java.sql.Timestamp login_time;
	private java.sql.Timestamp logout_time;
	private java.lang.Integer status;
	private java.lang.Integer depart_id;
	private java.lang.Integer role_id;
	private java.lang.String summary;
	private java.lang.String depart_name;
	private java.lang.String role_name;
	private java.lang.String book_name;
	private java.lang.String status_name;
	//caiyuan 20061225
	private Integer product_id;
	
	private String o_tel;
	private String mobile;

	private static final String listOpRightSql = "{call SP_QUERY_TOPRIGHT(?)}";
	private Hashtable menuTable = new Hashtable();
	private Hashtable funcTable = new Hashtable();
	private String login_user;
	private Integer is_intrust;
	private Integer tree_table_flag; //选择产品的显示方式 1树形显示 2表格形式显示 默认1

	public void logout() throws Exception
	{
		OperatorLocal operator = EJBFactory.getOperator();
		operator.logOut(op_code);
		operator.remove();
	}

	public void changePass(String oldPass, String newPass1, String newPass2) throws Exception
	{
		if (!newPass1.equals(newPass2)) 
		{
			throw new BusiException("两次输入新密码不符，请重新输入！");
		}
		OperatorLocal operator = EJBFactory.getOperator();
		operator.modiPassWord(op_code, oldPass, newPass1);
		operator.remove();
	}

	public void listRights() throws Exception
	{
		String menu_id, parent_id;
		Integer func_id = new Integer(0);
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt =
			conn.prepareCall(listOpRightSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, op_code.intValue());
		ResultSet rslist = stmt.executeQuery();

		menuTable.clear();
		funcTable.clear();
		try
		{
			while (rslist.next())
			{
				menu_id = rslist.getString("MENU_ID");
				func_id = new Integer(rslist.getInt("FUNC_ID"));
				parent_id = rslist.getString("PARENT_ID");
				menuTable.put(menu_id, parent_id);
				if(!"".equals(parent_id)) {
				    menuTable.put("_" + menu_id, parent_id.substring(0, 3));
				}
				String key2 = menu_id + func_id.toString();
				funcTable.put(key2, key2);
			}
		}
		catch (Exception ex)
		{	
			menuTable.clear();
			funcTable.clear();
			new BusiException("获取用户菜单权限失败"+ex.getMessage());
		}
		finally
		{
			rslist.close();
			stmt.close();
			conn.close();
		}
	}

	/**
	 * @param menu_id 菜单id
	 * @return 根据输入参数判断是否存在权限
	 */
	public boolean hasMenu(String menu_id)
	{
		//if(menu_id.equals("201"))
		//{
		//	Utility.debugln("menuTable.containsKey(menu_id): " + menuTable.containsKey(menu_id));
		//	Utility.debugln("menuTable.containsValue(menu_id): " + menuTable.containsValue(menu_id));
		//}
		return menuTable.containsKey(menu_id) || menuTable.containsValue(menu_id);
	}

	public String getMenuLabel(String menu_id)
	{
		if (!hasMenu(menu_id))
			return "style='display:none'";
		else
			return "";
	}
	
	/**
	 * @param menu_id 菜单id
	 * @param func_id 功能id
	 * @return 根据输入参数判断是否存在权限
	 */
	public boolean hasFunc(String menu_id, Integer func_id)
	{

		String key = menu_id + func_id.toString();
		return funcTable.containsKey(key);
	}

	/**
	 * @param menu_id 菜单id
	 * @param func_id 功能id
	 * @return 根据输入参数判断是否存在权限
	 */
	public boolean hasFunc(String menu_id, int func_id)
	{
		return hasFunc(menu_id, new Integer(func_id));
	}

	public String getFuncLabel(String menu_id, int func_id)
	{
		if (!hasFunc(menu_id, func_id))
			return "style='display:none'";
		else
			return "";
	}

	/**
	 * @return
	 */
	public Integer getBook_code()
	{
		return book_code;
	}

	/**
	 * @return
	 */
	public java.lang.String getBook_name()
	{
		return book_name;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCancel_date()
	{
		return cancel_date;
	}

	/**
	 * @return
	 */
	public Integer getDepart_id()
	{
		return depart_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getDepart_name()
	{
		return depart_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getIp_address()
	{
		return ip_address;
	}

	/**
	 * @return
	 */
	public java.sql.Timestamp getLogin_time()
	{
		return login_time;
	}

	/**
	 * @return
	 */
	public java.sql.Timestamp getLogout_time()
	{
		return logout_time;
	}

	/**
	 * @return
	 */
	public Integer getOp_code()
	{
		return op_code;
	}

	/**
	 * @return
	 */
	public String getOp_name()
	{
		return op_name;
	}

	/**
	 * @return
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getReg_date()
	{
		return reg_date;
	}

	/**
	 * @return
	 */
	public Integer getRole_id()
	{
		return role_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getRole_name()
	{
		return role_name;
	}

	/**
	 * @return
	 */
	public Integer getStatus()
	{
		return status;
	}

	/**
	 * @return
	 */
	public java.lang.String getStatus_name()
	{
		return status_name;
	}

	/**
	 * @return
	 */
	public String getSummary()
	{
		return summary;
	}

	/**
	 * @param integer
	 */
	public void setBook_code(Integer integer)
	{
		book_code = integer;
	}

	/**
	 * @param string
	 */
	public void setBook_name(java.lang.String string)
	{
		book_name = string;
	}

	/**
	 * @param date
	 */
	public void setCancel_date(java.lang.Integer integer)
	{
		cancel_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setDepart_id(Integer integer)
	{
		depart_id = integer;
	}

	/**
	 * @param string
	 */
	public void setDepart_name(java.lang.String string)
	{
		depart_name = string;
	}

	/**
	 * @param string
	 */
	public void setIp_address(java.lang.String string)
	{
		ip_address = string;
	}

	/**
	 * @param time
	 */
	public void setLogin_time(java.sql.Timestamp timestamp)
	{
		login_time = timestamp;
	}

	/**
	 * @param time
	 */
	public void setLogout_time(java.sql.Timestamp timestamp)
	{
		logout_time = timestamp;
	}

	/**
	 * @param integer
	 */
	public void setOp_code(Integer integer)
	{
		op_code = integer;
	}

	/**
	 * @param string
	 */
	public void setOp_name(String string)
	{
		op_name = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string)
	{
		password = string;
	}

	/**
	 * @param date
	 */
	public void setReg_date(java.lang.Integer integer)
	{
		reg_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setRole_id(Integer integer)
	{
		role_id = integer;
	}

	/**
	 * @param string
	 */
	public void setRole_name(java.lang.String string)
	{
		role_name = string;
	}

	/**
	 * @param integer
	 */
	public void setStatus(Integer integer)
	{
		status = integer;
	}

	/**
	 * @param string
	 */
	public void setStatus_name(java.lang.String string)
	{
		status_name = string;
	}

	/**
	 * @param string
	 */
	public void setSummary(String string)
	{
		summary = string;
	}

	/**
	 * @return
	 */
	public Integer getProduct_id() {
		return product_id;
	}

	/**
	 * @param integer
	 */
	public void setProduct_id(Integer integer) {
		product_id = integer;
	}

	/**
	 * @return 返回 login_user。
	 */
	public String getLogin_user() {
		return login_user;
	}
	/**
	 * @param login_user 要设置的 login_user。
	 */
	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}
	/**
	 * @return 返回 is_intrust。
	 */
	public Integer getIs_intrust() {
		return is_intrust;
	}
	/**
	 * @param is_intrust 要设置的 is_intrust。
	 */
	public void setIs_intrust(Integer is_intrust) {
		this.is_intrust = is_intrust;
	}
	public Integer getTree_table_flag() {
		return tree_table_flag;
	}
	/**
	 * @param tree_table_flag 要设置的 tree_table_flag。
	 */
	public void setTree_table_flag(Integer tree_table_flag) {
		this.tree_table_flag = tree_table_flag;
	}
    
    /**
     * 
     * 获取用户选择的产品
     * @return 返回 product_id
     * @throws Exception
     */
    
    public Integer getProductidByOpcode(Integer op_code)throws Exception{
    	List list=CrmDBManager.listBySql("SELECT PRODUCT_ID FROM TOPERATOR WHERE OP_CODE ="+Utility.trimNull(op_code,"0"));
    	Map map=null;
    	
    	if(list.size()>0){
    		map = (Map)list.get(0);
    		product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
    	}
    	
    	return product_id;
    }
    
     
	/**
	 * @return 返回 mobile。
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile 要设置的 mobile。
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return 返回 o_tel。
	 */
	public String getO_tel() {
		return o_tel;
	}
	/**
	 * @param o_tel 要设置的 o_tel。
	 */
	public void setO_tel(String o_tel) {
		this.o_tel = o_tel;
	}
}
