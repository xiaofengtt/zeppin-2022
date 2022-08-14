 
package enfo.crm.intrust;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ToMoneyAccountVO;

@Component(value="toMoneyAccount")
public class ToMoneyAccountBean extends enfo.crm.dao.IntrustBusiExBean implements ToMoneyAccountLocal {
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ToMoneyAccountLocal#query_page(enfo.crm.vo.ToMoneyAccountVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_page( ToMoneyAccountVO vo ,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[14];
		String sqlStr = "{call  SP_QUERY_TMONEYDETAIL (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
	    params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = vo.getContract_bh();
        params[3] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
        params[4] = vo.getInput_man();
        params[5] = vo.getDz_date();
        params[6] = vo.getCust_name();
        params[7] = "";
        params[8] = vo.getMin_to_money();
        params[9] = vo.getMax_to_money();
        params[10] = vo.getEnd_date();
        params[11] = new Integer(0);
        params[12] = new Integer(0);
        params[13] = vo.getContract_sub_bh();
        
        try {
			rsList =  super.listProcPage(sqlStr,params,totalColumn,pageIndex,pageSize);
		} catch (BusiException e) {
			throw new BusiException("资金到账信息查询失败:" + e.getMessage());
		}
		
		return rsList;			
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ToMoneyAccountLocal#check(enfo.crm.vo.ToMoneyAccountVO)
	 */
	@Override
	public void check(ToMoneyAccountVO vo) throws BusiException{
        Object[] params = new Object[4];        
        params[0] = vo.getSerial_no();
        params[1] = vo.getTo_bank_date();
        params[2] = vo.getInput_man();
        params[3] = vo.getSbf_serial_no();
        try {
			CrmDBManager.cudProc("{?=call SP_CHECK_TMONEYDETAIL_CRM (?,?,?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("资金到账信息审核失败:" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ToMoneyAccountLocal#listRestoreCheck(enfo.crm.vo.ToMoneyAccountVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listRestoreCheck(ToMoneyAccountVO vo ,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
			IPageList rsList = null;		
			Object[] params = new Object[8];
			String sqlStr = "{call  SP_QUERY_TMONEYDETAIL_BACK(?,?,?,?,?,?,?,?)}";
			
			params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
	        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
	        params[2] = vo.getContract_bh();
	        params[3] = vo.getInput_man();
	        params[4] = vo.getProduct_name();
	        params[5] = vo.getMin_to_money();
	        params[6] = vo.getMax_to_money();
	        params[7] = vo.getContract_sub_bh();
	        
	        try {
				rsList =  CrmDBManager.listProcPage(sqlStr,params,totalColumn,pageIndex,pageSize);
			} catch (BusiException e) {
				throw new BusiException("资金到账审核恢复信息查询失败:" + e.getMessage());
			}
			
			return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ToMoneyAccountLocal#restoreCheck(enfo.crm.vo.ToMoneyAccountVO)
	 */
	@Override
	public void restoreCheck(ToMoneyAccountVO vo) throws BusiException {
		 Object[] params = new Object[2];
	     params[0] = vo.getSerial_no();
	     params[1] = vo.getInput_man();
	     
	     try {
			CrmDBManager.cudProc("{?=call SP_CHECK_TMONEYDETAIL_BACK_CRM (?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("资金到账信息恢复失败:" + e.getMessage());
		}
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ToMoneyAccountLocal#checkByManage(enfo.crm.vo.ToMoneyAccountVO)
	 */
    @Override
	public void checkByManage(ToMoneyAccountVO vo) throws Exception {
        Object[] params = new Object[2];
        params[0] = vo.getSerial_no();
        params[1] = vo.getInput_man();
        try{
            super.cudProc("{?=call SP_MANCHECK_TMONEYDETAIL(?,?)}",params);
        } catch (Exception e) {
            throw new BusiException("到账数据审核失败: " + e.getMessage());
        }
    }
}