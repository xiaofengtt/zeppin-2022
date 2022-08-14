package enfo.crm.intrust;
  
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AmlVO;

@Component(value="amShareHolder")
public class AmShareHolderBean extends enfo.crm.dao.IntrustBusiExBean implements AmShareHolderLocal {
    private String querySql = "{call SP_AML_QUERY_TAMSHAREHOLDER(?,?,?)}";//查询客户股东信息

    private String addSql = "{?=call SP_AML_ADD_TAMSHAREHOLDER(?,?,?,?,?,?)}";//新增客户股东信息

    private String modiSql = "{?=call SP_AML_MODI_TAMSHAREHOLDER(?,?,?,?,?,?)}";//修改客户股东信息

    private String delSql = "{?=call SP_AML_DEL_TAMSHAREHOLDER(?,?)}";//删除客户股东信息

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.AmShareHolderLocal#listAll(enfo.crm.vo.AmlVO, int, int)
	 */
    @Override
	public IPageList listAll(AmlVO vo, int pageIndex, int pageSize)
            throws BusiException {
        IPageList rsList = null;
        Object[] params = new Object[3];
        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[1] = Utility.parseInt(vo.getCust_id(), new Integer(0));
        params[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        rsList = super.listProcPage(querySql, params, pageIndex, pageSize);
        return rsList;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.AmShareHolderLocal#list(enfo.crm.vo.AmlVO)
	 */
    @Override
	public List list(AmlVO vo) throws BusiException {
        List list = null;
        Object[] params = new Object[3];
        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[1] = Utility.parseInt(vo.getCust_id(), new Integer(0));
        params[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        list = super.listProcAll(querySql, params);
        return list;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.AmShareHolderLocal#append(enfo.crm.vo.AmlVO)
	 */
    @Override
	public void append(AmlVO vo) throws BusiException {
        Object[] params = new Object[6];
        params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
        params[1] = Utility.trimNull(vo.getHdnm());
        params[2] = Utility.trimNull(vo.getHitp());
        params[3] = Utility.trimNull(vo.getHdid());
        params[4] = Utility.parseInt(vo.getHivt(), new Integer(0));
        params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        super.cudProc(addSql, params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.AmShareHolderLocal#modi(enfo.crm.vo.AmlVO)
	 */
    @Override
	public void modi(AmlVO vo) throws BusiException {
        Object[] params = new Object[6];
        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[1] = Utility.trimNull(vo.getHdnm());
        params[2] = Utility.trimNull(vo.getHitp());
        params[3] = Utility.trimNull(vo.getHdid());
        params[4] = Utility.parseInt(vo.getHivt(), new Integer(0));
        params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        super.cudProc(modiSql, params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.AmShareHolderLocal#delete(enfo.crm.vo.AmlVO)
	 */
    @Override
	public void delete(AmlVO vo) throws BusiException {
        Object[] params = new Object[2];
        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        super.cudProc(delSql, params);
    }
}