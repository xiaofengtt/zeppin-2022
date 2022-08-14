 
package enfo.crm.callcenter;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.SeatVO;

@Component(value="seat")
public class SeatBean extends enfo.crm.dao.CrmBusiExBean implements SeatLocal {
	private final String statCCSeatActivitySql ="{call SP_STAT_CC_SeatActivity(?,?,?,?,?)}";//坐席活动事件报表
	private final String statCCSeatCallDetailSql = "{call SP_STAT_CC_SeatCallDetail(?,?,?,?,?)}";//坐席电话事件报表
	private final String statCCSeatAbandonSql = "{call SP_STAT_CC_SeatAbandon(?,?,?,?,?)}";//坐席放弃振铃报表
	private final String statCCSeatWorkloadSql = "{call SP_STAT_CC_SeatWorkload(?,?,?,?,?,?)}";//坐席工作量报表
	private final String statCCSeatCallinSql = "{call SP_STAT_CC_SeatCallin(?,?,?,?,?,?)}";//坐席呼入电话汇总
	private final String statCCSeatCalloutSql = "{call SP_STAT_CC_SeatCallout(?,?,?,?,?,?)}";//坐席呼出电话汇总
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_seatActivity(enfo.crm.vo.SeatVO)
	 */
	@Override
	public List query_seatActivity(SeatVO vo) throws BusiException{		
		List rsList = null;
		Object[] params = new Object[5];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getInputman();
		
		rsList = super.listProcAll(statCCSeatActivitySql,params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#qyery_page_seatActivity(enfo.crm.vo.SeatVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList qyery_page_seatActivity(SeatVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[5];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getInputman();
		
		try {
			return super.listProcPage(statCCSeatActivitySql,params,totalColumn,pageIndex,pageSize);
		} 
		catch (BusiException e) {
			throw new BusiException("查询坐席活动事件报表失败："+e.getMessage());
		}
	} 
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_seatCallDetail(enfo.crm.vo.SeatVO)
	 */
	@Override
	public List query_seatCallDetail(SeatVO vo) throws BusiException{
		List rsList = null;
		Object[] params = new Object[5];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getInputman();
		
		rsList = super.listProcAll(statCCSeatCallDetailSql,params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_page_seatCallDetail(enfo.crm.vo.SeatVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_page_seatCallDetail(SeatVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[5];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getInputman();
		
		try {
			return super.listProcPage(statCCSeatCallDetailSql,params,totalColumn,pageIndex,pageSize);
		} 
		catch (BusiException e) {
			throw new BusiException("查询坐席电话事件报表失败："+e.getMessage());
		}
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_seatAbandon(enfo.crm.vo.SeatVO)
	 */
	@Override
	public List query_seatAbandon(SeatVO vo) throws BusiException{
		List rsList = null;
		Object[] params = new Object[5];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getInputman();
		
		rsList = super.listProcAll(statCCSeatAbandonSql,params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_page_seatAbandon(enfo.crm.vo.SeatVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_page_seatAbandon(SeatVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[5];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getInputman();
		
		try {
			return super.listProcPage(statCCSeatAbandonSql,params,totalColumn,pageIndex,pageSize);
		} 
		catch (BusiException e) {
			throw new BusiException("查询坐席放弃振铃报表失败："+e.getMessage());
		}
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_seatWorkload(enfo.crm.vo.SeatVO)
	 */
	@Override
	public List query_seatWorkload(SeatVO vo) throws BusiException{
		List rsList = null;
		Object[] params = new Object[6];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getStatFlag();
		params[5] = vo.getInputman();
		
		rsList = super.listProcAll(statCCSeatWorkloadSql,params);
		return rsList;		
	}
		
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_page_seatWorkload(enfo.crm.vo.SeatVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_page_seatWorkload(SeatVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[6];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getStatFlag();
		params[5] = vo.getInputman();
		
		try {
			return super.listProcPage(statCCSeatWorkloadSql,params,totalColumn,pageIndex,pageSize);
		} 
		catch (BusiException e) {
			throw new BusiException("查询坐席放弃振铃报表失败："+e.getMessage());
		}
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_seatCallin(enfo.crm.vo.SeatVO)
	 */
	@Override
	public List query_seatCallin(SeatVO vo) throws BusiException{
		List rsList = null;
		Object[] params = new Object[6];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getStatFlag();
		params[5] = vo.getInputman();
		
		rsList = super.listProcAll(statCCSeatCallinSql,params);
		return rsList;	
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_page_seatCallin(enfo.crm.vo.SeatVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_page_seatCallin(SeatVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[6];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getStatFlag();
		params[5] = vo.getInputman();
		
		try {
			return super.listProcPage(statCCSeatCallinSql,params,totalColumn,pageIndex,pageSize);
		} 
		catch (BusiException e) {
			throw new BusiException("查询坐席呼入电话汇总失败："+e.getMessage());
		}
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_seatCallout(enfo.crm.vo.SeatVO)
	 */
	@Override
	public List query_seatCallout(SeatVO vo) throws BusiException{
		List rsList = null;
		Object[] params = new Object[6];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getStatFlag();
		params[5] = vo.getInputman();
		
		rsList = super.listProcAll(statCCSeatCalloutSql,params);
		return rsList;	
	}
		
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SeatLocal#query_page_seatCallout(enfo.crm.vo.SeatVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList query_page_seatCallout(SeatVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[6];
		
		params[0] = vo.getManagerID();
		params[1] = vo.getExtension();
		params[2] = vo.getStartDate();
		params[3] = vo.getEndDate();
		params[4] = vo.getStatFlag();
		params[5] = vo.getInputman();
		
		try {
			return super.listProcPage(statCCSeatCalloutSql,params,totalColumn,pageIndex,pageSize);
		} 
		catch (BusiException e) {
			throw new BusiException("查询坐席呼出电话汇总失败："+e.getMessage());
		}
	}	
}