 
package enfo.crm.system;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.DictparamVO;

@Component(value="dictparam")
public class DictparamBean extends enfo.crm.dao.CrmBusiExBean implements DictparamLocal {
	private static final String listSysContrl =
		"{call SP_QUERY_TSYSCONTROL(?,?,?,?)}";
	//查询系统控制信息
	private static final String modiSysContrl =
		"{?=call SP_MODI_TSYSCONTROL(?,?,?,?,?)}";
	//修改系统控制信息
	private static final String listSysControlValue =
		"{call SP_QUERY_TSYSCONTROL_VALUE(?)}";
	//查询系统开关

	private static final String listDictparam =
		"{call SP_QUERY_TDICTPARAM(?,?,?)}";
	//查询参数	
	private static final String delDictparam =
		"{?= call SP_DEL_TDICTPARAM(?,?)}";
	//删除参数
	private static final String modiDictparam =
		"{?= call SP_MODI_TDICTPARAM(?,?,?,?,?,?,?,?)}";
	//修改参数
	private static final String addDictparam =
		"{?= call SP_ADD_TDICTPARAM(?,?,?,?,?,?,?)}";
	//新增参数
	
	private static final String listDict =
		"{call SP_QUERY_TDICTPARAM(?,?,?,?,?)}";
	//查询外部链接
	private static final String addDict =
		"{?= call SP_ADD_TDICTPARAM(?,?,?,?,?,?)}";
	//新增外部链接

	private static final String listReportparam =
		"{call SP_QUERY_TINIPARAM(?,?)}";
	//查询报表参数
	private static final String addReportparam =
		"{?=call SP_ADD_TINIPARAM(?,?,?,?)}";
	//增加报表参数
	private static final String delReportparam =
		"{?=call SP_DEL_TINIPARAM(?,?,?)}"; 
	//删除报表参数

	private static final String listSummarySql =
		"{call SP_QUERY_TSUMMARY(?,?)}";
	//查询备注
	 
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#getPrintCatalog(enfo.crm.vo.DictparamVO, int, int)
	 */
	@Override
	public IPageList getPrintCatalog(DictparamVO vo,int pageIndex,int pageSize) throws Exception{
		Object[] params = new Object[3];
        params[0] = vo.getCatalog_id();
        params[1] = vo.getCatalog_code();
        params[2] = vo.getCatalog_name();
        
        IPageList pageList = null;
        try { 
            pageList = super.listProcPage("{call SP_QUERY_TPRINTCATALOG (?,?,?)}",params,pageIndex,pageSize);
        } catch (BusiException e) {
            throw new BusiException("打印模板类型查询失败"+e.getMessage());
        }
        return pageList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#getPrintTemplate(enfo.crm.vo.DictparamVO, int, int)
	 */
	@Override
	public IPageList getPrintTemplate(DictparamVO vo,int pageIndex,int pageSize) throws Exception{
		Object[] params = new Object[4];
        params[0] = vo.getTemplate_id();
        params[1] = vo.getCatalog_code();
        params[2] = vo.getTemplate_code();
        params[3] = vo.getTemplate_name();
        
        IPageList pageList = null;
        try {
            pageList = super.listProcPage("{call SP_QUERY_TPRINTTEMPLATE (?,?,?,?)}",params,pageIndex,pageSize);
        } catch (BusiException e) {
            throw new BusiException("打印模板查询失败"+e.getMessage());
        }
        return pageList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#modiPrintTemplateEnable(enfo.crm.vo.DictparamVO)
	 */
    @Override
	public void modiPrintTemplateEnable(DictparamVO vo) throws Exception{
        
        Object[] params = new Object[3];
        params[0] = vo.getTemplate_id();
        params[1] = vo.getFlag();
        params[2] = vo.getInput_man();
        try {
            super.cudProc("{?=call SP_MODI_TPRINTTEMPLATE_ENABLE(?,?,?)}",params);
        } catch (BusiException e) {
            throw new BusiException("启用标志修改失败"+e.getMessage());
        }
    }
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#modiPrintTemplate(enfo.crm.vo.DictparamVO)
	 */
    @Override
	public void modiPrintTemplate(DictparamVO vo) throws Exception{
        
        Object[] params = new Object[6];
        params[0] = vo.getTemplate_id();
        params[1] = vo.getTemplate_code();
        params[2] = vo.getTemplate_name(); 
        params[3] = vo.getTemplate_content();
        params[4] = vo.getSummary(); 
        params[5] = vo.getInput_man();
        try {
            super.cudProc("{?=call SP_MODI_TPRINTTEMPLATE(?,?,?,?,?,?)}",params);
        } catch (BusiException e) {
            throw new BusiException("打印模板修改失败"+e.getMessage());
        } 
    }
    /* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#getPrintElement(enfo.crm.vo.DictparamVO, int, int)
	 */
    @Override
	public IPageList getPrintElement(DictparamVO vo,int pageIndex,int pageSize) throws Exception{
    	
		Object[] params = new Object[4];
        params[0] = vo.getElement_id();
        params[1] = vo.getCatalog_code();
        params[2] = vo.getElement_code();
        params[3] = vo.getElement_name();
        
        IPageList pageList = null;
        try {
            pageList = super.listProcPage("{call SP_QUERY_TPRINTELEMENT (?,?,?,?)}",params,pageIndex,pageSize);
        } catch (BusiException e) {
            throw new BusiException("打印要素查询失败"+e.getMessage());
        }
        return pageList;
	} 
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#addPrintTemplate(enfo.crm.vo.DictparamVO)
	 */
    @Override
	public void addPrintTemplate(DictparamVO vo) throws Exception{
         
        Object[] params = new Object[6];
        params[0] = vo.getTemplate_code();
        params[1] = vo.getTemplate_name();
        params[2] = vo.getCatalog_code();
        params[3] = vo.getTemplate_content();
        params[4] = vo.getSummary();
        params[5] = vo.getInput_man();
        
        try {
            super.cudProc("{?=call SP_ADD_TPRINTTEMPLATE(?,?,?,?,?,?)}",params);
        } catch (BusiException e) {
            throw new BusiException("新建打印模板失败"+e.getMessage());
        }
    }
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listSysControl(enfo.crm.vo.DictparamVO)
	 */
	@Override
	public List listSysControl(DictparamVO vo) throws BusiException {

		List rsList = null;
		Object[] params = new Object[4];
		params[0] = Utility.trimNull(vo.getFlag_type());
		params[1] = Utility.trimNull(vo.getType_name());
		params[2] = Utility.parseInt(vo.getIs_modi(), null);
		params[3] = Utility.trimNull(vo.getSummary());
		rsList = super.listProcAll(listSysContrl, params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#modiSysControl(enfo.crm.vo.DictparamVO, java.lang.Integer)
	 */
	@Override
	public void modiSysControl(DictparamVO vo, Integer input_operatorCode)
		throws BusiException {
		Object[] params = new Object[5];
		params[0] = Utility.trimNull(vo.getFlag_type());
		params[1] = Utility.trimNull(vo.getType_name());
		params[2] = Utility.parseInt(vo.getValue(), null);
		params[3] = Utility.trimNull(vo.getSummary());
		params[4] = Utility.parseInt(input_operatorCode, null);
		super.cudProc(modiSysContrl, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listDictparamAll(enfo.crm.vo.DictparamVO)
	 */
	@Override
	public List listDictparamAll(DictparamVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getType_id(), null);
		params[1] = Utility.trimNull(vo.getType_name());
		params[2] = Utility.parseInt(vo.getSerial_no(), null);
		//Utility.debugln("vo.getSerial_no():"+vo.getSerial_no());
		rsList = super.listProcAll(listDictparam, params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listDictparamAllIntrust(enfo.crm.vo.DictparamVO)
	 */
	@Override
	public List listDictparamAllIntrust(DictparamVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getType_id(), null);
		params[1] = Utility.trimNull(vo.getType_name());
		params[2] = Utility.parseInt(vo.getSerial_no(), null);
		//Utility.debugln("vo.getSerial_no():"+vo.getSerial_no());
		rsList = IntrustDBManager.listProcAll(listDictparam, params);
		return rsList;
	}
	

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listDictparamAllIntrustProvince(enfo.crm.vo.DictparamVO)
	 */
	@Override
	public List listDictparamAllIntrustProvince(DictparamVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getType_id(), null);
		params[1] = Utility.trimNull(vo.getType_value());
		params[2] = Utility.parseInt(vo.getSerial_no(), null);
		//Utility.debugln("vo.getSerial_no():"+vo.getSerial_no());
		rsList = IntrustDBManager.listProcAll("{call SP_QUERY_TDICTPARAM_9999(?,?,?)}", params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#delDictparam(enfo.crm.vo.DictparamVO, java.lang.Integer)
	 */
	@Override
	public void delDictparam(DictparamVO vo, Integer input_operatorCode)
		throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.trimNull(vo.getSerial_no());
		params[1] = Utility.parseInt(input_operatorCode, null);
		super.cudProc(delDictparam, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#modiDictparam(enfo.crm.vo.DictparamVO, java.lang.Integer)
	 */
	@Override
	public void modiDictparam(DictparamVO vo, Integer input_operatorCode)
		throws BusiException {
		Object[] params = new Object[8];
		params[0] = Utility.parseInt(vo.getSerial_no(), null);
		params[1] = Utility.parseInt(vo.getType_id(), null);
		params[2] = Utility.trimNull(vo.getType_name());
		params[3] = Utility.trimNull(vo.getType_value());
		params[4] = Utility.trimNull(vo.getType_content());
		params[5] = Utility.parseInt(input_operatorCode, null);
		params[6] = Utility.trimNull(vo.getAdditive_value());
		params[7] = Utility.trimNull(vo.getAml_value());
		super.cudProc(modiDictparam, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#modiDictparam1(enfo.crm.vo.DictparamVO, java.lang.Integer)
	 */
	@Override
	public void modiDictparam1(DictparamVO vo, Integer input_operatorCode)
		throws BusiException {
		Object[] params = new Object[8];
		
		params[0] = Utility.parseInt(vo.getSerial_no(), null);
		params[1] = Utility.parseInt(vo.getType_id(), null);
		params[2] = Utility.trimNull(vo.getType_name());
		params[3] = Utility.trimNull(vo.getType_value());
		params[4] = Utility.trimNull(vo.getType_content());
		params[5] = Utility.parseInt(input_operatorCode, null);
		params[6] = Utility.trimNull(vo.getAdditive_value());
		params[7] = Utility.trimNull(vo.getAml_value());
		super.cudProc("{?= call SP_MODI_TDICTPARAM(?,?,?,?,?,?,?,?)}", params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#addDictparam(enfo.crm.vo.DictparamVO, java.lang.Integer)
	 */
	@Override
	public void addDictparam(DictparamVO vo, Integer input_operatorCode)
		throws BusiException {
		Object[] params = new Object[7];
		params[0] = Utility.parseInt(vo.getType_id(), null);
		params[1] = Utility.trimNull(vo.getType_name());
		params[2] = Utility.trimNull(vo.getType_value());
		params[3] = Utility.trimNull(vo.getType_content());
		params[4] = Utility.parseInt(input_operatorCode, null);
		params[5] = Utility.trimNull(vo.getAdditive_value());
		params[6] = Utility.trimNull(vo.getAml_value());
		super.cudProc(addDictparam, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listSysControlValue(enfo.crm.vo.DictparamVO)
	 */
	@Override
	public List listSysControlValue(DictparamVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[1];
		Utility.debugln("flag_type is " + vo.getFlag_type().toString());
		params[0] = Utility.trimNull(vo.getFlag_type());
		Utility.debugln("flag_type2 is " + params[0].toString());
		rsList = super.listProcAll(listSysControlValue, params);
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listByMul(enfo.crm.vo.DictparamVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listByMul(
		DictparamVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[4];
		params[0] = Utility.trimNull(vo.getFlag_type());
		params[1] = Utility.trimNull(vo.getType_name());
		params[2] = Utility.parseInt(vo.getIs_modi(), null);
		params[3] = Utility.trimNull(vo.getSummary());
		rsList =
			super.listProcPage(
				listSysContrl,
				params,
				totalColumn,
				pageIndex,
				pageSize);
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listDictparamAll(enfo.crm.vo.DictparamVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listDictparamAll(
		DictparamVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getType_id(), null);
		params[1] = Utility.trimNull(vo.getType_name());
		params[2] = Utility.parseInt(vo.getSerial_no(), null);
		params[3] = Utility.trimNull(vo.getType_content());
		rsList =
			super.listProcPage(
				"{call SP_QUERY_TDICTPARAM(?,?,?,?)}",
				params,
				totalColumn,
				pageIndex,
				pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listReportAll(enfo.crm.vo.DictparamVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listReportAll(
		DictparamVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getType_id(), null);
		params[1] = Utility.trimNull(vo.getType_value());
		rsList =
			super.listProcPage(
				listReportparam,
				params,
				totalColumn,
				pageIndex,
				pageSize);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#addReportparam(enfo.crm.vo.DictparamVO, java.lang.Integer)
	 */
	@Override
	public void addReportparam(DictparamVO vo, Integer input_operatorCode)
		throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getType_id(), null);
		params[1] = Utility.trimNull(vo.getType_value());
		params[2] = Utility.trimNull(vo.getType_content());
		params[3] = Utility.parseInt(input_operatorCode, null);
		super.cudProc(addReportparam, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#delReportparam(enfo.crm.vo.DictparamVO, java.lang.Integer)
	 */
	@Override
	public void delReportparam(DictparamVO vo, Integer input_operatorCode)
		throws BusiException {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getType_id(), null);
		params[1] = Utility.trimNull(vo.getType_value());
		params[2] = Utility.parseInt(input_operatorCode, null);
		super.cudProc(delReportparam, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listSummaryByMul(enfo.crm.vo.DictparamVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listSummaryByMul(
		DictparamVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.trimNull(vo.getSum_code());
		params[1] = Utility.trimNull(vo.getSum_name());
		rsList =
			super.listProcPage(
				listSummarySql,
				params,
				totalColumn,
				pageIndex,
				pageSize);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listFzType(enfo.crm.vo.DictparamVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listFzType(
		DictparamVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize) {
		return null;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#listTotherbusiFlag(enfo.crm.vo.DictparamVO)
	 */
	@Override
	public List listTotherbusiFlag(DictparamVO vo) {
		return null;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#updateTotherbusiFlag(enfo.crm.vo.DictparamVO)
	 */
	@Override
	public void updateTotherbusiFlag(DictparamVO vo) throws Exception {

	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#pagelistDictparamAll(enfo.crm.vo.DictparamVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelistDictparamAll(DictparamVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getType_id(), null);
		params[1] = Utility.trimNull(vo.getType_name());
		params[2] = Utility.parseInt(vo.getSerial_no(), null);
		params[3] = Utility.trimNull(vo.getType_content());
		params[4] = Utility.trimNull(vo.getAdditive_value());
		rsList =
			super.listProcPage(listDict, params, totalColumn, pageIndex, pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.DictparamLocal#addWebLinks(enfo.crm.vo.DictparamVO, java.lang.Integer)
	 */
	@Override
	public void addWebLinks(DictparamVO vo, Integer input_operatorCode)
		throws BusiException {
		Object[] params = new Object[6];
		params[0] = Utility.parseInt(vo.getType_id(), null);
		params[1] = Utility.trimNull(vo.getType_name());
		params[2] = Utility.trimNull(vo.getType_value());
		params[3] = Utility.trimNull(vo.getType_content());
		params[4] = Utility.parseInt(input_operatorCode, null);
		params[5] = Utility.trimNull(vo.getAdditive_value());
		super.cudProc(addDict, params);
	}
}