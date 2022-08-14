/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITnumberRelationDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TnumberRelation;
import cn.zeppin.product.itic.core.entity.ToperatorMobile;
import cn.zeppin.product.itic.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class TnumberRelationDAO extends BaseDAO<TnumberRelation, Integer> implements ITnumberRelationDAO {
	
	/**
	 * 新增Controller信息
	 */
	@Override
//	@Caching(put={@CachePut(value = "numberRelations", key = "'numberRelations:' + #numberRelation.uuid")}, evict={@CacheEvict(value = "allnumberRelations", allEntries = true)})
	public TnumberRelation insert(TnumberRelation numberRelation){
		return super.insert(numberRelation);
	}
	
	/**
	 * 删除Controller信息
	 */
	@Override
//	@Caching(evict={@CacheEvict(value = "numberRelations", key = "'numberRelations:' + #numberRelation.uuid"), @CacheEvict(value = "allnumberRelations", allEntries = true)})
	public TnumberRelation delete(TnumberRelation numberRelation){
		return super.delete(numberRelation);
	}
	
	/**
	 * 修改Controller信息
	 */
	@Override
//	@Caching(put={@CachePut(value = "numberRelations", key = "'numberRelations:' + #numberRelation.uuid")}, evict={@CacheEvict(value = "allnumberRelations", allEntries = true)})
	public TnumberRelation update(TnumberRelation numberRelation){
		return super.update(numberRelation);
	}
	
	@Override
//	@Cacheable(value = "numberRelations", key = "'numberRelations:' + #uuid")
	public TnumberRelation get(Integer id) {
		
		return super.get(id);
	}
	
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(" select tp.ID as id,tp.STATUS as status,tp.CREATETIME as createtime,tp.FK_TCUSTOMERS as fkTcustomers, "
				+ " tp.FK_TOPERATOR as fkToperator, tp.TO_MOBILE as toMobile, tp.TO_TEL as toTel, tp.TC_PHONE as tcPhone, "
				+ " tp.TC_TEL as tcTel, tp.PROCESS_STATUS as processStatus, tp.FK_OP_MOBILE as fkOpMobile, tp.EXPIRY_DATE as expiryDate, "
				+ " tp.MAXDURATION as maxduration, tp.ISRECORD as isrecord, tp.WAYBILLNUM as waybillnum "
				+ " from TNUMBER_RELATION tp "
				+ " left join Tcustomers tc on tp.FK_TCUSTOMERS = tc.CUST_ID "
				+ " left join TOPERATOR op on tp.FK_TOPERATOR = op.OP_CODE where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tc.MOBILE like '%" + inputParams.get("name") + "%' ");
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
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID <> " + inputParams.get("id"));
		}
		
		//
		if (inputParams.get("fkTcustomers") != null && !"".equals(inputParams.get("fkTcustomers"))) {
			builder.append(" and tp.FK_TCUSTOMERS = '" + inputParams.get("fkTcustomers") + "'");
		}
		
		//
		if (inputParams.get("fkToperator") != null && !"".equals(inputParams.get("fkToperator"))) {
			builder.append(" and tp.FK_TOPERATOR = '" + inputParams.get("fkToperator") + "'");
		}
		
		//
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.[STATUS] = '" + inputParams.get("status") + "'");
		}
		//
		if (inputParams.get("custName") != null && !"".equals(inputParams.get("custName"))) {
			builder.append(" and tc.CUST_NAME like '%" + inputParams.get("custName") + "%'");
		}
		
		//
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and op.OP_NAME like '%" + inputParams.get("opName") + "%'");
		}
		
		if (inputParams.get("pinYinSimple") != null && !"".equals(inputParams.get("pinYinSimple"))) {
			builder.append(" and tc.PinYinSimple like '%" + inputParams.get("pinYinSimple") + "%'");
		}
		
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.TO_MOBILE = '" + inputParams.get("toMobile") + "'");
		}
		
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.TO_TEL = '" + inputParams.get("toTel") + "'");
		}
		
		if (inputParams.get("tcPhone") != null && !"".equals(inputParams.get("tcPhone"))) {
			builder.append(" and tp.TC_PHONE = '" + inputParams.get("tcPhone") + "'");
		}
		
		if (inputParams.get("tcTel") != null && !"".equals(inputParams.get("tcTel"))) {
			builder.append(" and tp.TC_TEL = '" + inputParams.get("tcTel") + "'");
		}
		
		if (inputParams.get("processing") != null && "1".equals(inputParams.get("processing"))) {
			builder.append(" and tp.PROCESS_STATUS > -1 ");
		}
		
		if (inputParams.get("processStatus") != null && !"".equals(inputParams.get("processStatus"))) {
			builder.append(" and tp.PROCESS_STATUS = " + inputParams.get("processStatus"));
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
				+ " from TNUMBER_RELATION tp "
				+ " left join Tcustomers tc on tp.FK_TCUSTOMERS = tc.CUST_ID "
				+ " left join TOPERATOR op on tp.FK_TOPERATOR = op.OP_CODE where 1=1 ");
		
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tc.MOBILE like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.PinYinSimple like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.PinYinWhole like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.CUST_NAME like '%" + inputParams.get("name") + "%') ");
		}
		
		//名称fkToperator
		if (inputParams.get("oname") != null && !"".equals(inputParams.get("oname"))) {
			builder.append(" and (op.MOBILE like '%" + inputParams.get("oname") + "%' ");
			builder.append(" or op.OP_NAME like '%" + inputParams.get("oname") + "%') ");
		}
		
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.ID <> " + inputParams.get("id"));
		}
		
		//
		if (inputParams.get("fkTcustomers") != null && !"".equals(inputParams.get("fkTcustomers"))) {
			builder.append(" and tp.FK_TCUSTOMERS = '" + inputParams.get("fkTcustomers") + "'");
		}
		
		//
		if (inputParams.get("fkToperator") != null && !"".equals(inputParams.get("fkToperator"))) {
			builder.append(" and tp.FK_TOPERATOR = '" + inputParams.get("fkToperator") + "'");
		}
		
		//
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.[STATUS] = '" + inputParams.get("status") + "'");
		}
		//
		if (inputParams.get("custName") != null && !"".equals(inputParams.get("custName"))) {
			builder.append(" and tc.CUST_NAME like '%" + inputParams.get("custName") + "%'");
		}
		
		//
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and op.OP_NAME like '%" + inputParams.get("opName") + "%'");
		}
		
		if (inputParams.get("pinYinSimple") != null && !"".equals(inputParams.get("pinYinSimple"))) {
			builder.append(" and tc.PinYinSimple like '%" + inputParams.get("pinYinSimple") + "%'");
		}
		
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.TO_MOBILE = '" + inputParams.get("toMobile") + "'");
		}
		
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.TO_TEL = '" + inputParams.get("toTel") + "'");
		}
		
		if (inputParams.get("tcPhone") != null && !"".equals(inputParams.get("tcPhone"))) {
			builder.append(" and tp.TC_PHONE = '" + inputParams.get("tcPhone") + "'");
		}
		
		if (inputParams.get("tcTel") != null && !"".equals(inputParams.get("tcTel"))) {
			builder.append(" and tp.TC_TEL = '" + inputParams.get("tcTel") + "'");
		}
		
		if (inputParams.get("processing") != null && "1".equals(inputParams.get("processing"))) {
			builder.append(" and tp.PROCESS_STATUS > -1 ");
		}
		
		if (inputParams.get("processStatus") != null && !"".equals(inputParams.get("processStatus"))) {
			builder.append(" and tp.PROCESS_STATUS = " + inputParams.get("processStatus"));
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

	@Override
	public List<TnumberRelation> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		StringBuilder builder = new StringBuilder();
		builder.append("select tp from TnumberRelation tp "
		+ " left join tp.fkTcustomers "
		+ " left join tp.fkToperator where 1=1 ");
		//名称
		if (inputParams.get("name") != null && !"".equals(inputParams.get("name"))) {
			builder.append(" and (tc.custTel like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.pinYinSimple like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.pinYinWhole like '%" + inputParams.get("name") + "%' ");
			builder.append(" or tc.custName like '%" + inputParams.get("name") + "%') ");
		}
		//名称fkToperator
		if (inputParams.get("oname") != null && !"".equals(inputParams.get("oname"))) {
			builder.append(" and (op.MOBILE like '%" + inputParams.get("oname") + "%' ");
			builder.append(" or op.OP_NAME like '%" + inputParams.get("oname") + "%') ");
		}
		if (inputParams.get("id") != null && !"".equals(inputParams.get("id"))) {
			builder.append(" and tp.id <> " + inputParams.get("id"));
		}
		
		//
		if (inputParams.get("fkTcustomers") != null && !"".equals(inputParams.get("fkTcustomers"))) {
			builder.append(" and tc.fkTcustomers = '" + inputParams.get("fkTcustomers") + "'");
		}
		
		//
		if (inputParams.get("fkToperator") != null && !"".equals(inputParams.get("fkToperator"))) {
			builder.append(" and tc.fkToperator = '" + inputParams.get("fkToperator") + "'");
		}
		//
		if (inputParams.get("status") != null && !"".equals(inputParams.get("status"))) {
			builder.append(" and tp.status = '" + inputParams.get("status") + "'");
		}
		//
		if (inputParams.get("custName") != null && !"".equals(inputParams.get("custName"))) {
			builder.append(" and tc.custName like '%" + inputParams.get("custName") + "%'");
		}
		
		//
		if (inputParams.get("opName") != null && !"".equals(inputParams.get("opName"))) {
			builder.append(" and to.opName like '%" + inputParams.get("opName") + "%'");
		}
		
		if (inputParams.get("pinYinSimple") != null && !"".equals(inputParams.get("pinYinSimple"))) {
			builder.append(" and tc.pinYinSimple like '%" + inputParams.get("pinYinSimple") + "%'");
		}
		
		if (inputParams.get("toMobile") != null && !"".equals(inputParams.get("toMobile"))) {
			builder.append(" and tp.toMobile = '" + inputParams.get("toMobile") + "'");
		}
		
		if (inputParams.get("toTel") != null && !"".equals(inputParams.get("toTel"))) {
			builder.append(" and tp.toTel = '" + inputParams.get("toTel") + "'");
		}
		
		if (inputParams.get("tcPhone") != null && !"".equals(inputParams.get("tcPhone"))) {
			builder.append(" and tp.tcPhone = '" + inputParams.get("tcPhone") + "'");
		}
		
		if (inputParams.get("tcTel") != null && !"".equals(inputParams.get("tcTel"))) {
			builder.append(" and tp.tcTel = '" + inputParams.get("tcTel") + "'");
		}
		
		if (inputParams.get("processing") != null && "1".equals(inputParams.get("processing"))) {
			builder.append(" and tp.processStatus > -1 ");
		}
		
		if (inputParams.get("processStatus") != null && !"".equals(inputParams.get("processStatus"))) {
			builder.append(" and tp.PROCESS_STATUS = " + inputParams.get("processStatus"));
		}
		//其他搜索条件
		
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
	
	/**
	  * 日结删除绑定关系--客户修改手机号
	  * 1
	  */
	public void dailyDelete(){
		String sql = "  insert into TNUMBER_RELATION_LOG " + 
				"  select tr.FK_TCUSTOMERS,tr.FK_TOPERATOR,'normal' as [STATUS],GETDATE() as CREATETIME,tr.TC_PHONE,'delete' as [TYPE],tr.TC_TEL,TR.ID AS FK_TNUMBER_RELATION , " + 
				"  tr.TO_MOBILE as TO_MOBILE, tr.TO_TEL as TO_TEL " + 
				"  from TNUMBER_RELATION tr " + 
				"  left join TCustomers tc on tr.FK_TCUSTOMERS=tc.CUST_ID and tc.STATUS != '112805' " +
				"  where tr.TC_PHONE<>tc.MOBILE and tr.STATUS='normal'";
		super.executeSQL(sql);
		
		
		sql = "  insert into TNUMBER_RELATION_LOG" + 
				"  select tr.FK_TCUSTOMERS,tr.FK_TOPERATOR,'normal' as [STATUS],GETDATE() as CREATETIME,tc.MOBILE as TC_PHONE,'insert' as [TYPE],tr.TC_TEL,TR.ID AS FK_TNUMBER_RELATION , " + 
				"  tr.TO_MOBILE as TO_MOBILE, tr.TO_TEL as TO_TEL " + 
				"  from TNUMBER_RELATION tr " + 
				"  left join TCustomers tc on tr.FK_TCUSTOMERS=tc.CUST_ID and tc.STATUS != '112805'" + 
				"  where tr.TC_PHONE<>tc.MOBILE and LEN(tc.MOBILE)=11";
		super.executeSQL(sql);
	}
	

	/**
	 * 日结删除绑定关系--非客户修改手机号
	 * 2
	 */
	@Override
	public void dailyDeleteOther() {
		String sql = "  insert into TNUMBER_RELATION_LOG " + 
				"  select tr.FK_TCUSTOMERS,tr.FK_TOPERATOR,'normal' as [STATUS],GETDATE() as CREATETIME,tr.TC_PHONE,'delete' as [TYPE],tr.TC_TEL,TR.ID AS FK_TNUMBER_RELATION , " + 
				"  tr.TO_MOBILE as TO_MOBILE, tr.TO_TEL as TO_TEL " + 
				"  from TNUMBER_RELATION tr left join  " + 
				"	(select tr.ID from TNUMBER_RELATION tr left join TOPERATOR t on tr.FK_TOPERATOR=t.OP_CODE and t.STATUS = 1  " + 
				"		left join TCustomers tc on tr.FK_TCUSTOMERS=tc.CUST_ID and tc.STATUS != '112805'  " + 
				"		where t.OP_CODE is null or tc.CUST_ID is null or tc.SERVICE_MAN != tr.FK_TOPERATOR) ids  " + 
				"    on tr.id=ids.id  " + 
				"    where ids.ID is not null and tr.STATUS='normal'";
		super.executeSQL(sql);
	}
	
	/**
	  * 日结增加绑定关系--非客户修改手机号
	  * 4
	  */
	public void dailyInsert(){
		String sql = "with  " + 
				" a as(select s.*,ROW_NUMBER() over(partition by s.FK_TOPERATOR order by s.CREATETIME) as rownum from ( " + 
				"select tc.CUST_ID FK_TCUSTOMERS, t.OP_CODE FK_TOPERATOR,'normal' as [STATUS],GETDATE() as CREATETIME,tc.MOBILE,'insert' as [TYPE],tr.TC_TEL,TR.ID AS FK_TNUMBER_RELATION  " + 
				" from TCustomers tc  " + 
				" left join TOPERATOR t on tc.SERVICE_MAN=t.OP_CODE  " + 
				" left join TOPERATOR_MOBILE tm on tc.SERVICE_MAN=tm.FK_TOPERATOR  " + 
				" left join TNUMBER_RELATION tr  " + 
				"	on tc.CUST_ID=tr.FK_TCUSTOMERS and tc.SERVICE_MAN=tr.FK_TOPERATOR AND TR.FK_TCUSTOMERS > 0 " + 
				" where tc.STATUS != '112805' and tr.ID is null and t.OP_CODE is not null and t.OP_CODE <>2720 and tm.TO_MOBILE is not null and len(tc.MOBILE)=11) s " + 
				"  ), " + 
				" b as ( " + 
				"	select * , ROW_NUMBER() over(partition by FK_TOPERATOR order by id) as rownum from TNUMBER_RELATION where STATUS='empty' " + 
				" ) " + 
				" insert into TNUMBER_RELATION_LOG " + 
				" select a.FK_TCUSTOMERS,a.FK_TOPERATOR, a.STATUS,a.CREATETIME,a.MOBILE,a.TYPE,b.TC_TEL, b.ID as FK_TNUMBER_RELATION , " + 
				"  b.TO_MOBILE as TO_MOBILE, b.TO_TEL as TO_TEL " + 
				" from a  " + 
				" left join b on a.FK_TOPERATOR = b.FK_TOPERATOR and a.rownum = b.rownum where b.ID is not null " + 
				" ";
		super.executeSQL(sql);
		
//		sql = " with  " + 
//				" a as( " + 
//				"	select *  ,ROW_NUMBER() over(partition by FK_TOPERATOR order by id) as rownum from TNUMBER_RELATION_LOG where FK_TNUMBER_RELATION is null and TYPE='insert' " + 
//				" ), " + 
//				" b as ( " + 
//				"	select * , ROW_NUMBER() over(partition by FK_TOPERATOR order by id) as rownum from TNUMBER_RELATION where STATUS='empty' " + 
//				" ) " + 
//				" update TNUMBER_RELATION_LOG set TC_TEL=b.TC_TEL,FK_TNUMBER_RELATION=b.ID " + 
//				" from a  " + 
//				" left join b on a.FK_TOPERATOR = b.FK_TOPERATOR and a.rownum = b.rownum  " + 
//				" where TNUMBER_RELATION_LOG.FK_TNUMBER_RELATION is null and  TNUMBER_RELATION_LOG.TYPE='insert'";
//		super.executeSQL(sql);
	}

	@Override
	public void dailyInsert(ToperatorMobile tm) {
		//新增绑定关系
		String sql = "insert into TNUMBER_RELATION select 'empty' as [STATUS],getdate() as CREATETIME, NULL as FK_TCUSTOMERS, tm.FK_TOPERATOR1 as FK_TOPERATOR,  " + 
				"tm.TO_MOBILE1 AS TO_MOBILE, tm.TO_TEL1 as TO_TEL, NULL as TC_PHONE, tm.TC_TEL1 as TC_TEL, '-1' as PROCESS_STATUS,  " + 
				"tm.ID1 as FK_OP_MOBILE, null as EXPIRY_DATE, 3600 as MAXDURATION, 1 as ISRECORD, null as WAYBILLNUM " + 
				"from ( " + 
				"	select ts.*,row_number() over (partition by FK_TOPERATOR,TO_MOBILE order by TC_TEL) as num  " + 
				"		from (select t.ID as ID1, t.FK_TOPERATOR as FK_TOPERATOR1,t.TO_MOBILE as TO_MOBILE1,t.TO_TEL as TO_TEL1,tt.TC_TEL as TC_TEL1  " + 
				"			from TOPERATOR_MOBILE t , TIN_NUMBER tt where t.FK_TOPERATOR="+ tm.getFkToperator() +" ) as ts  " + 
				"		left join TNUMBER_RELATION tr  " + 
				"		on ts.tc_tel1 =tr.tc_tel and ts.FK_TOPERATOR1=tr.FK_TOPERATOR and ts.TO_MOBILE1=tr.TO_MOBILE where tr.TC_TEL is null " + 
				") as tm order by tm.FK_TOPERATOR1 asc";
		super.executeSQL(sql);
	}

	@Override
	public void dailyUpdate(Integer opCode) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("update TNUMBER_RELATION set TC_TEL=tn.TC_TEL from ");
//		sb.append(" (select *,row_number() over (partition by FK_TOPERATOR order by ID desc) as num from TNUMBER_RELATION ");
//		sb.append(" where FK_TOPERATOR="+opCode + " and TC_TEL is null and STATUS='normal') tr ");
//		sb.append(" left join(select *,row_number() over (order by ID) as num from TIN_NUMBER) tn ");
//		sb.append(" on tr.num = tn.num ");
//		sb.append(" where TNUMBER_RELATION.ID = tr.ID and tn.ID is not null");
//		super.executeSQL(sb.toString());
		
		String sql = " with  " + 
				" a as( " + 
				"	select *  ,ROW_NUMBER() over(partition by FK_TOPERATOR order by id) as rownum from TNUMBER_RELATION_LOG where FK_TNUMBER_RELATION is null and TYPE='insert' AND FK_TOPERATOR= " + opCode + 
				" ), " + 
				" b as ( " + 
				"	select * , ROW_NUMBER() over(partition by FK_TOPERATOR order by id) as rownum from TNUMBER_RELATION where STATUS='empty' AND FK_TOPERATOR= " + opCode +
				" ) " + 
				" update TNUMBER_RELATION_LOG set TC_TEL=b.TC_TEL,FK_TNUMBER_RELATION=b.ID " + 
				" from a  " + 
				" left join b on a.FK_TOPERATOR = b.FK_TOPERATOR and a.rownum = b.rownum  " + 
				" where TNUMBER_RELATION_LOG.FK_TNUMBER_RELATION is null and  TNUMBER_RELATION_LOG.TYPE='insert'";
		super.executeSQL(sql);
	}
}
