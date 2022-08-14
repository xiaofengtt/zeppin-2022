package enfo.crm.intrust;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustBusiExBean;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.IntrustManagerManOpVO;

@Component(value="intrustManagerManOper")
public class IntrustManagerManOperBean extends IntrustBusiExBean implements IntrustManagerManOperLocal {

	/**
	 * 新增责任人成员信息
	 */
	private final static String addSql = "{?=call SP_ADD_TMANAGERMANOP(?,?,?,?)}";
	
	/**
	 * 删除责任人成员信息
	 */
	private final static String delSql = "{?=call SP_DEL_TMANAGERMANOP(?,?)}";
	
	/**
	 * 修改责任人成员信息
	 */
	private final static String modiSql = "{?=call SP_MODI_TMANAGERMANOP(?,?,?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustManagerManOperLocal#addManagerOp(enfo.crm.vo.IntrustManagerManOpVO)
	 */
	@Override
	public void addManagerOp(IntrustManagerManOpVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getManager_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getOp_code());
		params[2] = Utility.trimNull(vo.getOp_name());
		params[3] = Utility.parseInt(vo.getInput_man(), new Integer(888));

		try {
			super.cudProc(addSql, params);
		} catch (Exception e) {
			throw new BusiException("新增责任人成员信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustManagerManOperLocal#delManagerManOp(enfo.crm.vo.IntrustManagerManOpVO)
	 */
	@Override
	public void delManagerManOp(IntrustManagerManOpVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		
		try {
			super.cudProc(delSql, params);
		} catch (Exception e) {
			throw new BusiException("删除责任人成员信息息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustManagerManOperLocal#modiManagerOp(enfo.crm.vo.IntrustManagerManOpVO)
	 */
	@Override
	public void modiManagerOp(IntrustManagerManOpVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.trimNull(vo.getOp_code());
		params[2] = Utility.trimNull(vo.getOp_name());
		params[3] = Utility.parseInt(vo.getInput_man(), new Integer(888));

		try {
			super.cudProc(modiSql, params);
		} catch (Exception e) {
			throw new BusiException("修改责任人成员信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustManagerManOperLocal#queryManagerOp(enfo.crm.vo.IntrustManagerManOpVO, int, int)
	 */
	@Override
	public IPageList queryManagerOp(IntrustManagerManOpVO vo, int pageIndex, int pageSize) throws BusiException {
		IPageList rsList = null;
		String querySql = "SELECT * FROM TMANAGERMANOP WHERE MANAGER_ID = "+vo.getManager_id();
		
		try {
			rsList = super.listSqlPage(querySql, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询责任人成员信息失败: " + e.getMessage());
		}
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustManagerManOperLocal#queryManagerOp(enfo.crm.vo.IntrustManagerManOpVO)
	 */
	@Override
	public List queryManagerOp(IntrustManagerManOpVO vo) throws BusiException {
		List list = null;
		String querySql = "SELECT * FROM TMANAGERMANOP WHERE MANAGER_ID = "+vo.getManager_id();
		
		try {
			list = super.listBySql(querySql);
		} catch (Exception e) {
			throw new BusiException("查询责任人成员信息失败: " + e.getMessage());
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustManagerManOperLocal#queryManagerOp(java.lang.Integer)
	 */
	@Override
	public List queryManagerOp(Integer op_code) throws Exception {
		List list = new ArrayList();    
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM TMANAGERMANOP WHERE OP_CODE = "+op_code);
		try {
			while (rs.next()) {
				list.add(rs.getString("MANAGER_ID"));
			}
		} catch(Exception e){
			throw new BusiException("获得该员工所属的责任人失败: " + e.getMessage());
		}finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return list;
	}
}