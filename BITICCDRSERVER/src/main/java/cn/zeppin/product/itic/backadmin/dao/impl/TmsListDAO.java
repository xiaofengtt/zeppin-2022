/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITmsListDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TmsList;
import cn.zeppin.product.itic.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class TmsListDAO extends BaseDAO<TmsList, Integer> implements ITmsListDAO {
	
	/**
	 * 新增Controller信息
	 */
	@Override
	public TmsList insert(TmsList msList){
		return super.insert(msList);
	}
	
	/**
	 * 删除Controller信息
	 */
	@Override
	public TmsList delete(TmsList msList){
		return super.delete(msList);
	}
	
	/**
	 * 修改Controller信息
	 */
	@Override
	public TmsList update(TmsList msList){
		return super.update(msList);
	}
	
	@Override
	public TmsList get(Integer id) {
		
		return super.get(id);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.ID as id,tp.STATUS as status,tp.CREATETIME as createtime,tp.CALLID as callid, tp.SERVICENAME as servicename, tp.MESSAGEID as messageid, "
				+ " tp.SERVICEKEY as servicekey, "
				+ " tp.CALLERNUM as callernum, tp.CALLEDNUM as callednum, tp.MIDDLENUMBER as middlenumber, tp.CALLERDISPLAYNUMBER as callerdisplaynumber, "
				+ " tp.CALLEDDISPLAYNUMBER as calleddisplaynumber, tp.CALLERSTREAMNO as callerstreamno, tp.STARTCALLERTIME as startcallertime,"
				+ " tp.ABSTARTCALLTIME as abstartcalltime, tp.ABSTOPCALLTIME as abstopcalltime, tp.CALLERDURATION as callerduration,"
				+ " tp.CALLERCOST as callercost, tp.CALLERRELCAUSE as callerrelcause, tp.CALLERORIRESCODE as callerorirescode, tp.CALLEDSTREAMNO as calledstreamno,"
				+ " tp.STARTCALLEDTIME as startcalledtime, tp.CALLEDDURATION as calledduration, tp.CALLEDCOST as calledcost, tp.CALLEDRELCAUSE as calledrelcause,"
				+ " tp.CALLEDORIRESCODE as calledorirescode, tp.SRFMSGID as srfmsgid, tp.CHARGENUMBER as chargenumber, tp.CALLERRELREASON as callerrelreason,"
				+ " tp.CALLEDRELREASON as calledrelreason, tp.MSSERVER as msserver, tp.MIDDLESTARTTIME as middlestarttime, tp.MIDDLECALLTIME as middlecalltime,"
				+ " tp.DURATION as duration, tp.COSTCOUNT as costcount, tp.REALPATH as realpath, tp.FK_TOPERATOR as fkToperator, tp.FK_TCUSTOMERS as fkTcustomers "
				+ " from TMS_LIST tp where 1=1 ");
		
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID != " + inputParams.get("id"));
		}
		
		//
		if (inputParams.get("callid") != null && !"".equals(inputParams.get("callid"))) {
			builder.append(" and tp.CALLID = '" + inputParams.get("callid") + "'");
		}
		
		//
		if (inputParams.get("callernum") != null && !"".equals(inputParams.get("callernum"))) {
			builder.append(" and tp.CALLERNUM like '%" + inputParams.get("callernum") + "%'");
		}
		//
		if (inputParams.get("callednum") != null && !"".equals(inputParams.get("callednum"))) {
			builder.append(" and tp.CALLEDNUM like '%" + inputParams.get("callednum") + "%'");
		}
		//
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.STATUS = '" + inputParams.get("status") + "'");
		}
		//
		if (inputParams.get("middlenumber") != null && !"".equals(inputParams.get("middlenumber"))) {
			builder.append(" and tp.MIDDLENUMBER like '%" + inputParams.get("middlenumber") + "%'");
		}
		
		//
		if (inputParams.get("callerdisplaynumber") != null && !"".equals(inputParams.get("callerdisplaynumber"))) {
			builder.append(" and tp.CALLERDISPLAYNUMBER like '%" + inputParams.get("callerdisplaynumber") + "%'");
		}
		//
		if (inputParams.get("calleddisplaynumber") != null && !"".equals(inputParams.get("calleddisplaynumber"))) {
			builder.append(" and tp.CALLEDDISPLAYNUMBER like '%" + inputParams.get("calleddisplaynumber") + "%'");
		}
		//
		if (inputParams.get("calleddisplaynumber") != null && !"".equals(inputParams.get("calleddisplaynumber"))) {
			builder.append(" and tp.CALLEDDISPLAYNUMBER like '%" + inputParams.get("calleddisplaynumber") + "%'");
		}
		//
		if (inputParams.get("callerstreamno") != null && !"".equals(inputParams.get("callerstreamno"))) {
			builder.append(" and tp.CALLERSTREAMNO = '" + inputParams.get("callerstreamno") + "'");
		}
		
		//
		if (inputParams.get("callerrelcause") != null && !"".equals(inputParams.get("callerrelcause"))) {
			builder.append(" and tp.CALLERRELCAUSE = '" + inputParams.get("callerrelcause") + "'");
		}
		
		//
		if (inputParams.get("realpath") != null && !"".equals(inputParams.get("realpath"))) {
			builder.append(" and tp.REALPATH is null");
		}
		
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.CREATETIME desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) "
				+ " from TMS_LIST tp where 1=1 ");
		
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID != " + inputParams.get("id"));
		}
		
		//
		if (inputParams.get("callid") != null && !"".equals(inputParams.get("callid"))) {
			builder.append(" and tp.CALLID = '" + inputParams.get("callid") + "'");
		}
		
		//
		if (inputParams.get("callernum") != null && !"".equals(inputParams.get("callernum"))) {
			builder.append(" and tp.CALLERNUM like '%" + inputParams.get("callernum") + "%'");
		}
		//
		if (inputParams.get("callednum") != null && !"".equals(inputParams.get("callednum"))) {
			builder.append(" and tp.CALLEDNUM like '%" + inputParams.get("callednum") + "%'");
		}
		//
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.STATUS = '" + inputParams.get("status") + "'");
		}
		//
		if (inputParams.get("middlenumber") != null && !"".equals(inputParams.get("middlenumber"))) {
			builder.append(" and tp.MIDDLENUMBER like '%" + inputParams.get("middlenumber") + "%'");
		}
		
		//
		if (inputParams.get("callerdisplaynumber") != null && !"".equals(inputParams.get("callerdisplaynumber"))) {
			builder.append(" and tp.CALLERDISPLAYNUMBER like '%" + inputParams.get("callerdisplaynumber") + "%'");
		}
		//
		if (inputParams.get("calleddisplaynumber") != null && !"".equals(inputParams.get("calleddisplaynumber"))) {
			builder.append(" and tp.CALLEDDISPLAYNUMBER like '%" + inputParams.get("calleddisplaynumber") + "%'");
		}
		//
		if (inputParams.get("calleddisplaynumber") != null && !"".equals(inputParams.get("calleddisplaynumber"))) {
			builder.append(" and tp.CALLEDDISPLAYNUMBER like '%" + inputParams.get("calleddisplaynumber") + "%'");
		}
		//
		if (inputParams.get("callerstreamno") != null && !"".equals(inputParams.get("callerstreamno"))) {
			builder.append(" and tp.CALLERSTREAMNO = '" + inputParams.get("callerstreamno") + "'");
		}
		
		//
		if (inputParams.get("callerrelcause") != null && !"".equals(inputParams.get("callerrelcause"))) {
			builder.append(" and tp.CALLERRELCAUSE = '" + inputParams.get("callerrelcause") + "'");
		}
		
		//
		if (inputParams.get("realpath") != null && !"".equals(inputParams.get("realpath"))) {
			builder.append(" and tp.REALPATH is null");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<TmsList> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		StringBuilder builder = new StringBuilder();
		builder.append("select tp from TmsList tp where 1=1 ");
		//名称
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.id != " + inputParams.get("id"));
		}
		
		//
		if (inputParams.get("callid") != null && !"".equals(inputParams.get("callid"))) {
			builder.append(" and tp.callid = '" + inputParams.get("callid") + "'");
		}
		
		//
		if (inputParams.get("callernum") != null && !"".equals(inputParams.get("callernum"))) {
			builder.append(" and tp.callernum like '%" + inputParams.get("callernum") + "%'");
		}
		//
		if (inputParams.get("callednum") != null && !"".equals(inputParams.get("callednum"))) {
			builder.append(" and tp.callednum like '%" + inputParams.get("callednum") + "%'");
		}
		//
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.status = '" + inputParams.get("status") + "'");
		}
		//
		if (inputParams.get("middlenumber") != null && !"".equals(inputParams.get("middlenumber"))) {
			builder.append(" and tp.middlenumber like '%" + inputParams.get("middlenumber") + "%'");
		}
		
		//
		if (inputParams.get("callerdisplaynumber") != null && !"".equals(inputParams.get("callerdisplaynumber"))) {
			builder.append(" and tp.callerdisplaynumber like '%" + inputParams.get("callerdisplaynumber") + "%'");
		}
		//
		if (inputParams.get("calleddisplaynumber") != null && !"".equals(inputParams.get("calleddisplaynumber"))) {
			builder.append(" and tp.calleddisplaynumber like '%" + inputParams.get("calleddisplaynumber") + "%'");
		}
		//
		if (inputParams.get("calleddisplaynumber") != null && !"".equals(inputParams.get("calleddisplaynumber"))) {
			builder.append(" and tp.calleddisplaynumber like '%" + inputParams.get("calleddisplaynumber") + "%'");
		}
		//
		if (inputParams.get("callerstreamno") != null && !"".equals(inputParams.get("callerstreamno"))) {
			builder.append(" and tp.callerstreamno = '" + inputParams.get("callerstreamno") + "'");
		}
		
		//
		if (inputParams.get("callerrelcause") != null && !"".equals(inputParams.get("callerrelcause"))) {
			builder.append(" and tp.callerrelcause = '" + inputParams.get("callerrelcause") + "'");
		}
		//其他搜索条件
		//
		if (inputParams.get("realpath") != null && !"".equals(inputParams.get("realpath"))) {
			builder.append(" and tp.realpath is null");
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.createtime desc ");
		}
		return super.getByHQL(builder.toString(), pageNum, pageSize);
	}

	@Override
	public List<Entity> getListForSearchPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.ID as id,tp.STATUS as status,tp.CREATETIME as createtime,tp.CALLID as callid, tp.SERVICENAME as servicename, tp.MESSAGEID as messageid, "
				+ " tp.SERVICEKEY as servicekey, "
				+ " tp.CALLERNUM as callernum, tp.CALLEDNUM as callednum, tp.MIDDLENUMBER as middlenumber, tp.CALLERDISPLAYNUMBER as callerdisplaynumber, "
				+ " tp.CALLEDDISPLAYNUMBER as calleddisplaynumber, tp.CALLERSTREAMNO as callerstreamno, tp.STARTCALLERTIME as startcallertime,"
				+ " tp.ABSTARTCALLTIME as abstartcalltime, tp.ABSTOPCALLTIME as abstopcalltime, tp.CALLERDURATION as callerduration,"
				+ " tp.CALLERCOST as callercost, tp.CALLERRELCAUSE as callerrelcause, tp.CALLERORIRESCODE as callerorirescode, tp.CALLEDSTREAMNO as calledstreamno,"
				+ " tp.STARTCALLEDTIME as startcalledtime, tp.CALLEDDURATION as calledduration, tp.CALLEDCOST as calledcost, tp.CALLEDRELCAUSE as calledrelcause,"
				+ " tp.CALLEDORIRESCODE as calledorirescode, tp.SRFMSGID as srfmsgid, tp.CHARGENUMBER as chargenumber, tp.CALLERRELREASON as callerrelreason,"
				+ " tp.CALLEDRELREASON as calledrelreason, tp.MSSERVER as msserver, tp.MIDDLESTARTTIME as middlestarttime, tp.MIDDLECALLTIME as middlecalltime,"
				+ " tp.DURATION as duration, tp.COSTCOUNT as costcount, tp.REALPATH as realpath, tp.FK_TOPERATOR as fkToperator, tp.FK_TCUSTOMERS as fkTcustomers "
				+ " from TMS_LIST tp left join TOPERATOR op on tp.FK_TOPERATOR=op.OP_CODE "
				+ " left join TCustomers tc on tp.FK_TCUSTOMERS=tc.CUST_ID where 1=1 ");
		
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID != " + inputParams.get("id"));
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tc.CUST_TEL like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.PinYinSimple like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.PinYinWhole like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.CUST_NAME like '%" + inputParams.get("name") + "%') ");
		}
		
		//名称fkToperator
		if (inputParams.get("oname") != null && !"".equals(inputParams.get("oname"))) {
			builder.append(" and (op.MOBILE like '%" + inputParams.get("oname") + "%' ");
			builder.append(" or op.OP_NAME like '%" + inputParams.get("oname") + "%') ");
		}
		
		//
		if (inputParams.get("callid") != null && !"".equals(inputParams.get("callid"))) {
			builder.append(" and tp.CALLID = '" + inputParams.get("callid") + "'");
		}
		
		//
		if (inputParams.get("callernum") != null && !"".equals(inputParams.get("callernum"))) {
			builder.append(" and tp.CALLERNUM like '%" + inputParams.get("callernum") + "%'");
		}
		//
		if (inputParams.get("callednum") != null && !"".equals(inputParams.get("callednum"))) {
			builder.append(" and tp.CALLEDNUM like '%" + inputParams.get("callednum") + "%'");
		}
		//
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.STATUS = '" + inputParams.get("status") + "'");
		}
		//
		if (inputParams.get("middlenumber") != null && !"".equals(inputParams.get("middlenumber"))) {
			builder.append(" and tp.MIDDLENUMBER like '%" + inputParams.get("middlenumber") + "%'");
		}
		
		//
		if (inputParams.get("callerdisplaynumber") != null && !"".equals(inputParams.get("callerdisplaynumber"))) {
			builder.append(" and tp.CALLERDISPLAYNUMBER like '%" + inputParams.get("callerdisplaynumber") + "%'");
		}
		//
		if (inputParams.get("calleddisplaynumber") != null && !"".equals(inputParams.get("calleddisplaynumber"))) {
			builder.append(" and tp.CALLEDDISPLAYNUMBER like '%" + inputParams.get("calleddisplaynumber") + "%'");
		}
		//
		if (inputParams.get("calleddisplaynumber") != null && !"".equals(inputParams.get("calleddisplaynumber"))) {
			builder.append(" and tp.CALLEDDISPLAYNUMBER like '%" + inputParams.get("calleddisplaynumber") + "%'");
		}
		//
		if (inputParams.get("callerstreamno") != null && !"".equals(inputParams.get("callerstreamno"))) {
			builder.append(" and tp.CALLERSTREAMNO = '" + inputParams.get("callerstreamno") + "'");
		}
		
		//
		if (inputParams.get("callerrelcause") != null && !"".equals(inputParams.get("callerrelcause"))) {
			builder.append(" and tp.CALLERRELCAUSE = '" + inputParams.get("callerrelcause") + "'");
		}
		
		//
		if (inputParams.get("realpath") != null && !"".equals(inputParams.get("realpath"))) {
			builder.append(" and tp.REALPATH is null");
		}
		//其他搜索条件
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split("-");
			builder.append(" order by tp.");
			builder.append(sortArray[0] + " " + sortArray[1]);
		}
		else {
			builder.append(" order by tp.CREATETIME desc ");
		}
		return super.getBySQL(builder.toString(), pageNum, pageSize, resultClass);
	}

	@Override
	public Integer getCountForSearchPage(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) from TMS_LIST tp left join TOPERATOR op on tp.FK_TOPERATOR=op.OP_CODE"
				+ " left join TCustomers tc on tp.FK_TCUSTOMERS=tc.CUST_ID where 1=1 ");
		
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID != " + inputParams.get("id"));
		}
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tc.CUST_TEL like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.PinYinSimple like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.PinYinWhole like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.CUST_NAME like '%" + inputParams.get("name") + "%') ");
		}
		
		//名称fkToperator
		if (inputParams.get("oname") != null && !"".equals(inputParams.get("oname"))) {
			builder.append(" and (op.MOBILE like '%" + inputParams.get("oname") + "%' ");
			builder.append(" or op.OP_NAME like '%" + inputParams.get("oname") + "%') ");
		}
		
		//
		if (inputParams.get("callid") != null && !"".equals(inputParams.get("callid"))) {
			builder.append(" and tp.CALLID = '" + inputParams.get("callid") + "'");
		}
		
		//
		if (inputParams.get("callernum") != null && !"".equals(inputParams.get("callernum"))) {
			builder.append(" and tp.CALLERNUM like '%" + inputParams.get("callernum") + "%'");
		}
		//
		if (inputParams.get("callednum") != null && !"".equals(inputParams.get("callednum"))) {
			builder.append(" and tp.CALLEDNUM like '%" + inputParams.get("callednum") + "%'");
		}
		//
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.STATUS = '" + inputParams.get("status") + "'");
		}
		//
		if (inputParams.get("middlenumber") != null && !"".equals(inputParams.get("middlenumber"))) {
			builder.append(" and tp.MIDDLENUMBER like '%" + inputParams.get("middlenumber") + "%'");
		}
		
		//
		if (inputParams.get("callerdisplaynumber") != null && !"".equals(inputParams.get("callerdisplaynumber"))) {
			builder.append(" and tp.CALLERDISPLAYNUMBER like '%" + inputParams.get("callerdisplaynumber") + "%'");
		}
		//
		if (inputParams.get("calleddisplaynumber") != null && !"".equals(inputParams.get("calleddisplaynumber"))) {
			builder.append(" and tp.CALLEDDISPLAYNUMBER like '%" + inputParams.get("calleddisplaynumber") + "%'");
		}
		//
		if (inputParams.get("calleddisplaynumber") != null && !"".equals(inputParams.get("calleddisplaynumber"))) {
			builder.append(" and tp.CALLEDDISPLAYNUMBER like '%" + inputParams.get("calleddisplaynumber") + "%'");
		}
		//
		if (inputParams.get("callerstreamno") != null && !"".equals(inputParams.get("callerstreamno"))) {
			builder.append(" and tp.CALLERSTREAMNO = '" + inputParams.get("callerstreamno") + "'");
		}
		
		//
		if (inputParams.get("callerrelcause") != null && !"".equals(inputParams.get("callerrelcause"))) {
			builder.append(" and tp.CALLERRELCAUSE = '" + inputParams.get("callerrelcause") + "'");
		}
		
		//
		if (inputParams.get("realpath") != null && !"".equals(inputParams.get("realpath"))) {
			builder.append(" and tp.REALPATH is null");
		}
		
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}
}
