
package enfo.crm.intrust;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ChannelVO;

@Component(value="channel")
public class ChannelBean extends enfo.crm.dao.IntrustBusiExBean implements ChannelLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#append(enfo.crm.vo.ChannelVO)
	 */
	@Override
	public void append(ChannelVO vo) throws BusiException{
		String addSql = "{?=call SP_ADD_TCHANNEL(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[14];
		
		params[0] = vo.getBookCode();
		params[1] = vo.getChannelCode();
		params[2] = vo.getChannelName();
		params[3] = vo.getAddress();
		params[4] = vo.getLink_man();
		params[5] = vo.getZip();
		params[6] = vo.getPhone();
		params[7] = vo.getFax();
		params[8] = vo.getSellFlag();
		params[9] = vo.getSummary();
		params[10] = vo.getInputMan();
		params[11] = vo.getChannel_type();
		params[12] = vo.getParent_channel();
		params[13] = vo.getService_man();
		
		super.cudProc(addSql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#modi(enfo.crm.vo.ChannelVO)
	 */
	@Override
	public void modi(ChannelVO vo) throws BusiException{
		String modiSql = "{?=call SP_MODI_TCHANNEL(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[14];
		
		params[0] = vo.getChannelID();
		params[1] = vo.getChannelCode();
		params[2] = vo.getChannelName();
		params[3] = vo.getAddress();
		params[4] = vo.getLink_man();
		params[5] = vo.getZip();
		params[6] = vo.getPhone();
		params[7] = vo.getFax();
		params[8] = vo.getSellFlag();
		params[9] = vo.getSummary();
		params[10] = vo.getInputMan();
		params[11] = vo.getChannel_type();
		params[12] = vo.getParent_channel();
		params[13] = vo.getService_man();
		
		super.cudProc(modiSql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#del(enfo.crm.vo.ChannelVO)
	 */
	@Override
	public void del(ChannelVO vo) throws BusiException{
		String delSql = "{?=call SP_DEL_TCHANNEL(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = vo.getChannelID();
		params[1] = vo.getInputMan();
		
		super.cudProc(delSql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#query(enfo.crm.vo.ChannelVO)
	 */
	@Override
	public List query(ChannelVO vo) throws BusiException{
		String procSql = "{call SP_QUERY_TCHANNEL(?,?,?,?)}";
		Object[] params = new Object[4];
		List rsList = new ArrayList();
		
		params[0] = vo.getBookCode();
		params[1] = vo.getChannelID();
		params[2] = vo.getChannelCode();
		params[3] = vo.getChannelName();
		
		rsList= super.listProcAll(procSql, params);
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#queryPage(enfo.crm.vo.ChannelVO, int, int)
	 */
	@Override
	public IPageList queryPage(ChannelVO vo,int pageIndex,int pageSize) throws BusiException{
		String procSql = "{call SP_QUERY_TCHANNEL(?,?,?,?,?,?,?)}";
		Object[] params = new Object[7];
		IPageList rsList = null;
		
		params[0] = vo.getBookCode();
		params[1] = vo.getChannelID();
		params[2] = vo.getChannelCode();
		params[3] = vo.getChannelName();
		params[4] = vo.getChannel_type();
		params[5] = new Integer(0);
		params[6] = vo.getService_man();
		
		rsList= super.listProcPage(procSql, params, pageIndex, pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#queryServicePlanPage(enfo.crm.vo.ChannelVO, int, int)
	 */
	@Override
	public IPageList queryServicePlanPage(ChannelVO vo,int pageIndex,int pageSize) throws BusiException{
		String procSql = "{call SP_QUERY_TCHANNELSERVICE(?,?,?,?,?)}";
		Object[] params = new Object[5];
		IPageList rsList = null;
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getChannel_id();
		params[2] = vo.getPlan_contnet();
		params[3] = vo.getPlan_man();
		params[4] = Utility.parseInt(Utility.trimNull(vo.getInput_man()), new Integer(0));
		
		rsList= super.listProcPage(procSql, params, pageIndex, pageSize);
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#queryServicePlan(enfo.crm.vo.ChannelVO)
	 */
	@Override
	public List queryServicePlan(ChannelVO vo) throws BusiException{
		String procSql = "{call SP_QUERY_TCHANNELSERVICE(?,?,?,?,?)}";
		Object[] params = new Object[5];
		List rsList = new ArrayList();
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getChannelID();
		params[2] = vo.getPlan_contnet();
		params[3] = vo.getPlan_man();
		params[4] = Utility.parseInt(Utility.trimNull(vo.getInput_man()), new Integer(0));
		
		rsList= super.listProcAll(procSql, params);
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#appendServicePlans(enfo.crm.vo.ChannelVO)
	 */
	@Override
	public void appendServicePlans(ChannelVO vo) throws BusiException{
		String addSql = "{?=call SP_ADD_TCHANNELSERVICE(?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[8];
		
		params[0] = vo.getChannel_id();
		params[1] = vo.getPlan_time();
		params[2] = vo.getPlan_contnet();
		params[3] = vo.getPlan_man();
		params[4] = vo.getService_time();
		params[5] = vo.getService_content();
		params[6] = vo.getService_man();
		params[7] = Utility.parseInt(Utility.trimNull(vo.getInput_man()), new Integer(0));
		
		super.cudProc(addSql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#modiServicePlans(enfo.crm.vo.ChannelVO)
	 */
	@Override
	public void modiServicePlans(ChannelVO vo) throws BusiException{
		String modiSql = "{?=call SP_MODI_TCHANNELSERVICE(?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[9];
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getChannel_id();
		params[2] = vo.getPlan_time();
		params[3] = vo.getPlan_contnet();
		params[4] = vo.getPlan_man();
		params[5] = vo.getService_time();
		params[6] = vo.getService_content();
		params[7] = vo.getService_man();
		params[8] = Utility.parseInt(Utility.trimNull(vo.getInput_man()), new Integer(0));
		
		super.cudProc(modiSql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#delServicePlans(enfo.crm.vo.ChannelVO)
	 */
	@Override
	public void delServicePlans(ChannelVO vo) throws BusiException{
		String delSql = "{?=call SP_DEL_TCHANNELSERVICE(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = vo.getSerial_no();
		params[1] = Utility.parseInt(Utility.trimNull(vo.getInput_man()), new Integer(0));
		
		super.cudProc(delSql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#queryPageAnalysis(enfo.crm.vo.ChannelVO, java.lang.String[], int, int, int)
	 */
	@Override
	public IPageList queryPageAnalysis(ChannelVO vo,String[] totalColumn,int pageIndex,int pageSize,int flag)
	throws BusiException{
		String sql = "";
		if(flag==1)
			sql = "{call SP_STAT_COMPREHENSIVE_ANALYSIS(?,?,?,?,?,?,?,?,?)}";
		else
			sql = "{call SP_STAT_COMPREHENSIVE_ANALYSIS_2(?,?,?,?,?,?,?,?,?)}";	
		IPageList pageList = null;
		Object[] params = new Object[9];
		params[0] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getStart_date(),new Integer(0));
		params[2] = Utility.parseInt(vo.getEnd_date(),null);
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[4] = Utility.trimNull(vo.getProduct_status());
		params[5] = Utility.parseInt(vo.getSellFlag(),new Integer(0));
		params[6] = Utility.parseInt(vo.getIntrust_flag(),new Integer(0));
		params[7] = Utility.trimNull(vo.getProduct_name());
		params[8] = Utility.trimNull(vo.getChannel_type());
		pageList = CrmDBManager.listProcPage(sql,params,totalColumn,pageIndex,pageSize);
		return pageList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ChannelLocal#queryByAllAnalysis(enfo.crm.vo.ChannelVO, int)
	 */
	@Override
	public List queryByAllAnalysis(ChannelVO vo,int flag)throws BusiException{
		String sql = "";
		if(flag==1)
			sql = "{call SP_STAT_COMPREHENSIVE_ANALYSIS(?,?,?,?,?,?,?,?,?)}";
		else
			sql = "{call SP_STAT_COMPREHENSIVE_ANALYSIS_2(?,?,?,?,?,?,?,?,?)}";
		
		List list = null;
		Object[] params = new Object[9];
		params[0] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getStart_date(),new Integer(0));
		params[2] = Utility.parseInt(vo.getEnd_date(),null);
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[4] = Utility.trimNull(vo.getProduct_status());
		params[5] = Utility.parseInt(vo.getSellFlag(),new Integer(0));
		params[6] = Utility.parseInt(vo.getIntrust_flag(),new Integer(0));
		params[7] = Utility.trimNull(vo.getProduct_name());
		params[8] = Utility.trimNull(vo.getChannel_type());
		list = CrmDBManager.listBySql(sql,params);
		return list;
	}
}