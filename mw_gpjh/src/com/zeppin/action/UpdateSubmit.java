/**  
 * @Title: UpdateSubmit.java
 * @Package com.zeppin.action
 * @author jiangfei  
 */
package com.zeppin.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.zeppin.service.SubmitService;

/**
 * @author Administrator
 * 
 */
public class UpdateSubmit implements Runnable {

	private HttpSession session;
	private SubmitService submitService;

	public UpdateSubmit(HttpSession session, SubmitService submitService) {
		this.session = session;
		this.submitService = submitService;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {

		// ================================================
		// 1.先查询出 所有 问卷
		// 2.根据问卷 查询出需要升级的学生
		// ================================================

		String psqSql = "select id from PSQ t where t.PROJECTTYPE=1 and t.status<>0";
		List psqList = submitService.executeSQL(psqSql);

		for (Object obj : psqList) {

			int psqId = Integer.valueOf(obj.toString());

			StringBuilder sb = new StringBuilder();
			sb.append(" select ").append(" t.id as tid ,te.fk_province,apply.parentid,apply.id as pid ,")
				.append(" te.fk_subject,te.fk_training_unit ")
				.append(" from SUBMIT t,LOGINKEY l,TRAINEE te,PROCHILDAPPLYNO apply ")
				.append(" where t.loginkey = l.id and l.traineeid = te.id and te.fk_applyno= apply.id ")
				.append(" and t.psq =").append(psqId);

			List sbList = submitService.executeSQL(sb.toString());

			for (Object sbObject : sbList) {

				Object[] arrObj = (Object[]) sbObject;
				
				StringBuilder sbUpdate = new StringBuilder();
				sbUpdate.append("update Submit t set ")
				.append(" t.province ='").append(arrObj[1].toString()).append("'")
				.append(" , t.project='").append(arrObj[2].toString()).append("'")
				.append(" , t.childproject='").append(arrObj[3].toString()).append("'")
				.append(" , t.subject='").append(arrObj[4].toString()).append("'")
				.append(" , t.trainingunit='").append(arrObj[5].toString()).append("' ")
				.append(" where t.id=").append(arrObj[0]);
				
				System.out.println(sbUpdate.toString());
				
				submitService.executeSQLUpdate(sbUpdate.toString());

			}

		}
		
		session.setAttribute("updateSubmint", "ok");
		System.out.println("process ok");
		
	}

}
