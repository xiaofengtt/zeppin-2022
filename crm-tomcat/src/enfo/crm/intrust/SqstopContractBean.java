 
package enfo.crm.intrust;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.SqstopContractVO;

@Component(value="sqstopContract")
public class SqstopContractBean extends enfo.crm.dao.CrmBusiExBean implements SqstopContractLocal {

	/**
	 * 发行期退款申请的查询
	 */
	private static final String listSql =
		"{call SP_QUERY_TSQSTOPCONTRACT_CRM(?,?,?,?,?,?,?,?)}";

	/**
	 * 添加发行期退款申请
	 */
	private static final String appendSql =
		"{?= call SP_ADD_TSQSTOPCONTRACT_CRM(?,?,?,?,?,?,?,?,?)}";

	/**
	 * 修改发行期退款申请
	 */
	private static final String modiSql =
		"{?= call SP_MODI_TSQSTOPCONTRACT_CRM(?,?,?,?,?,?,?,?)}";

	/**
	 * 删除发行期退款申请
	 */
	private static final String delSql =
		"{?= call SP_DEL_TSQSTOPCONTRACT_CRM(?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.SqstopContractLocal#listAll(enfo.crm.vo.SqstopContractVO, int, int)
	 */
	@Override
	public IPageList listAll(SqstopContractVO vo, int pageIndex, int pageSize)
		throws BusiException {
		IPageList pageList = null;
		Object[] params = new Object[8];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = vo.getReason();
		params[4] = Utility.parseInt(vo.getSq_date(), null);
		params[5] = Utility.parseInt(vo.getCheck_flag(), null);
		params[6] = Utility.parseInt(vo.getRebate_flag(), null);
		params[7] = Utility.parseInt(vo.getSub_product_id(), null);

		pageList = super.listProcPage(listSql, params, pageIndex, pageSize);
		return pageList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.SqstopContractLocal#listAll(enfo.crm.vo.SqstopContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listAll(SqstopContractVO vo, String[] totalColumn,int pageIndex, int pageSize)
		throws BusiException {
		IPageList pageList = null;
		Object[] params = new Object[8];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = vo.getReason();
		params[4] = Utility.parseInt(vo.getSq_date(), null);
		params[5] = Utility.parseInt(vo.getCheck_flag(), null);
		params[6] = Utility.parseInt(vo.getRebate_flag(), null);
		params[7] = Utility.parseInt(vo.getSub_product_id(), null);

		pageList = super.listProcPage(listSql, params,totalColumn, pageIndex, pageSize);
		return pageList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.SqstopContractLocal#listBySql(enfo.crm.vo.SqstopContractVO)
	 */
	@Override
	public List listBySql(SqstopContractVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[8];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = vo.getReason();
		params[4] = Utility.parseInt(vo.getSq_date(), null);
		params[5] = Utility.parseInt(vo.getCheck_flag(), null);
		params[6] = Utility.parseInt(vo.getRebate_flag(), null);
		params[7] = Utility.parseInt(vo.getSub_product_id(), null);

		list = super.listBySql(listSql, params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.SqstopContractLocal#append(enfo.crm.vo.SqstopContractVO)
	 */
	@Override
	public void append(SqstopContractVO vo) throws BusiException {
		Object[] params = new Object[9];
		params[0] = Utility.parseInt(vo.getHt_serial_no(), null);
		params[1] = vo.getReason();
		params[2] = Utility.parseInt(vo.getSq_date(), null);
		params[3] = Utility.parseBigDecimal(vo.getFee(), null);
		params[4] = Utility.parseInt(vo.getInput_man(), null);
		params[5] = Utility.parseInt(vo.getT_rg_fee(), null);
		params[6] = Utility.parseBigDecimal(vo.getSq_money(), null);
		params[7] = Utility.parseInt(vo.getRebate_flag(), null);
		params[8] = Utility.parseBigDecimal(vo.getHt_fee(), null);

		super.cudProc(appendSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.SqstopContractLocal#modi(enfo.crm.vo.SqstopContractVO)
	 */
	@Override
	public void modi(SqstopContractVO vo) throws BusiException {
		Object[] params = new Object[8];
		params[0] = Utility.parseInt(vo.getSerial_no(), null);
		params[1] = vo.getReason();
		params[2] = Utility.parseInt(vo.getSq_date(), null);
		params[3] = Utility.parseBigDecimal(vo.getFee(), null);
		params[4] = Utility.parseInt(vo.getInput_man(), null);
		params[5] = Utility.parseInt(vo.getT_rg_fee(), null);
		params[6] = Utility.parseBigDecimal(vo.getSq_money(), null);
		params[7] = Utility.parseBigDecimal(vo.getHt_fee(), null);

		super.cudProc(modiSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.SqstopContractLocal#delete(enfo.crm.vo.SqstopContractVO)
	 */
	@Override
	public void delete(SqstopContractVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), null);
		params[1] = Utility.parseInt(vo.getInput_man(), null);
		super.cudProc(delSql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.SqstopContractLocal#check(enfo.crm.vo.SqstopContractVO)
	 */
    @Override
	public void check(SqstopContractVO vo) throws BusiException {
        Object[] params = new Object[2];
        params[0] = Utility.parseInt(vo.getSerial_no(), null);
        params[1] = Utility.parseInt(vo.getInput_man(), null);
        super.cudProc("{?= call SP_CHECK_TSQSTOPCONTRACT_CRM(?,?)}",params);
    }
}