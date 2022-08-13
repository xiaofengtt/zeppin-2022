package cn.zeppin.dao.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.IPsqDao;
import cn.zeppin.entity.Psq;

public class PsqDaoImpl extends BaseDaoImpl<Psq, Integer> implements IPsqDao {

	@Override
	public List<Psq> getPsqByType(short type) {
		String hql = "from Psq t where t.type=" + type;
		return this.getListByHSQL(hql);
	}

	@Override
	public List<Psq> getPsqByTypePage(short type, int offset, int length) {
		String hql = "from Psq t where t.type=" + type +" order by t.creattime desc ";
		return this.getListForPage(hql, offset, length);
	}

	@Override
	public int getPsqByTypeCount(short type) {
		String hql = "select count(*) from Psq t where t.type=" + type;
		Object result = this.getObjectByHql(hql, null);
		return Integer.parseInt(result.toString());
	}

	public int getSubmitCount(int psqid,int projectId,int trainingSubjectId,int trainingCollegeId){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Submit s where s.psq=");
		sb.append(psqid);
		sb.append(" and s.project=");
		sb.append(projectId);
		sb.append(" and s.trainingSubject=");
		sb.append(trainingSubjectId);
		sb.append(" and s.trainingCollege=");
		sb.append(trainingCollegeId);
		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}
	
	
	/**
	 * 每题统计
	 */
	@Override
	public HashMap<String, String[]> getPsqPaper(HashMap<String, String> map) {
		DecimalFormat df = new DecimalFormat("0.0");
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT r.question,r.answer,COUNT(*) AS SUM ");
		sb.append("FROM submit s ,result r,teacher_training_records ttr ");
		sb.append("WHERE r.submit = s.id and s.uuid = ttr.uuid ");

		if (map.containsKey("paperid")) {
			if (map.get("paperid") != null) {
				sb.append(" AND s.psq=" + map.get("paperid"));
			}
		}
		
		if (map.containsKey("pid")) {
			if (map.get("pid") != null && !map.get("pid").equals("0")) {
				sb.append(" AND s.project=" + map.get("pid"));
			}
		}

		if (map.containsKey("stid")) {
			if (map.get("stid") != null && !map.get("stid").equals("0")) {
				sb.append(" AND s.subject=" + map.get("stid"));
			}
		}

		if (map.containsKey("tcid")) {
			if (map.get("tcid") != null && !map.get("tcid").equals("0")) {
				sb.append(" AND s.training_college=" + map.get("tcid"));
			}
		}
		if(map.containsKey("classes")){
			if (map.get("classes") != null && !map.get("classes").equals("0")) {
				sb.append(" AND ttr.classes=" + map.get("classes"));
			}
		}

		sb.append(" GROUP BY r.question,r.answer ");

		List li = this.getListBySQL(sb.toString(), null);

		HashMap<String, String[]> retMap = new HashMap<>();
		HashMap<String, Integer> countMap = new HashMap<>();
		
		if (li != null && li.size() > 0) {
			for (int i = 0; i < li.size(); i++) {
				Object[] objects = (Object[]) li.get(i);
				String key = objects[0].toString();
				Integer old = countMap.get(key) ==null? 0 : countMap.get(key);
				Integer count = old + Integer.valueOf(objects[2].toString());
				countMap.put(key, count);
			}
			for (int i = 0; i < li.size(); i++) {
				Object[] objects = (Object[]) li.get(i);
				String key = objects[0] + "_" + objects[1];
				String count = objects[2].toString();
				Integer totalCount = countMap.get(objects[0].toString()) ==null? 0 : countMap.get(objects[0].toString());
				String percent = df.format((Double.valueOf(objects[2].toString()) / Double.valueOf(totalCount+"")*100))+"";
				String[] info = {count,percent};
				retMap.put(key, info);
			}

		}
		return retMap;
	}

	@Override
	public List getPsqSummary(HashMap<String, String> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT p.name as projectName,ts.name AS subjectName,tc.name AS tcName,SUM(r.score) AS score,p.id as pid,ts.id as tsid,tc.id as tcid,s.psq as psqid ");
		sb.append("FROM submit s,result r,project p,training_subject ts,training_college tc,question q ");
		sb.append("WHERE s.id = r.submit AND s.project = p.id AND s.subject = ts.id AND s.training_college = tc.id AND r.question=q.id AND q.is_count=1 AND q.type=2 ");

		if (map.containsKey("paperid")) {
			if (map.get("paperid") != null) {
				sb.append(" AND s.psq=" + map.get("paperid"));
			}
		}
		
		if (map.containsKey("pid")) {
			if (map.get("pid") != null && !map.get("pid").equals("0")) {
				sb.append(" AND s.project=" + map.get("pid"));
			}
		}

		if (map.containsKey("stid")) {
			if (map.get("stid") != null && !map.get("stid").equals("0")) {
				sb.append(" AND s.subject=" + map.get("stid"));
			}
		}

		if (map.containsKey("tcid")) {
			if (map.get("tcid") != null && !map.get("tcid").equals("0")) {
				sb.append(" AND s.training_college=" + map.get("tcid"));
			}
		}

		sb.append(" GROUP BY p.name,ts.name,tc.name order by score desc");

		List li = this.getListBySQL(sb.toString(), null);

		return li;
	}

	@Override
	public List getPsqContrast(HashMap<String, String> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT q.name,q.inx,p.name as projectName,ts.name AS subjectName,tc.name AS tcName,AVG(r.score) AS score ");
		sb.append("FROM submit s,result r,project p,training_subject ts,training_college tc,question q ");
		sb.append("WHERE s.id = r.submit AND s.project = p.id AND s.subject = ts.id AND s.training_college = tc.id AND r.question=q.id AND q.is_count=1 AND q.type=2 ");

		if (map.containsKey("paperid")) {
			if (map.get("paperid") != null) {
				sb.append(" AND s.psq=" + map.get("paperid"));
			}
		}

		if (map.containsKey("pid")) {
			if (map.get("pid") != null && !map.get("pid").equals("0")) {
				sb.append(" AND s.project=" + map.get("pid"));
			}
		}
		
		if (map.containsKey("stid")) {
			if (map.get("stid") != null && !map.get("stid").equals("0")) {
				sb.append(" AND s.subject=" + map.get("stid"));
			}
		}

		if (map.containsKey("tcid")) {
			if (map.get("tcid") != null && !map.get("tcid").equals("0")) {
				sb.append(" AND s.training_college=" + map.get("tcid"));
			}
		}

		if (map.containsKey("qid")) {
			if (map.get("qid") != null && !map.get("qid").equals("0")) {
				sb.append(" AND q.id=" + map.get("qid"));
			}
		}

		sb.append(" GROUP BY q.name,q.inx,ts.name,tc.name ");
		sb.append(" order by score desc");

		List li = this.getListBySQL(sb.toString(), null);

		return li;
	}

	/**
	 * 满意率分析
	 */
	@Override
	public List getPsqManyiLv(HashMap<String, String> map, String tableName) {

		/**
		 * 创建临时表
		 */
		List result = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		try {
			
			sb.append("CREATE TEMPORARY TABLE ")
			.append(tableName)
			.append(" SELECT p.id,p.name as projectName, pa.subject as tsid,ts.name AS tsname,pa.training_college AS tcid ,tc.name AS tcname ,pa.approve_number,0 AS factNumber, 0 AS voteNumber ,0.000 AS score ")
			.append(" FROM project_apply pa,project p,training_subject ts,training_college tc ")
			.append(" WHERE pa.proejct=p.id AND pa.subject = ts.id AND pa.training_college = tc.id ");
			
			if (map.containsKey("pid")) {
				if (map.get("pid") != null) {
					if (map.get("pid").indexOf(",") > 0) {
						sb.append(" AND p.id in (" + map.get("pid") + ") ");
					} else {
						sb.append(" AND p.id=" + map.get("pid"));
					}
				}
			}

			if (map.containsKey("stid")) {
				if (map.get("stid") != null && !map.get("stid").equals("0")) {
					sb.append(" AND ts.id=" + map.get("stid"));
				}
			}

			if (map.containsKey("tcid")) {
				if (map.get("tcid") != null && !map.get("tcid").equals("0")) {
					sb.append(" AND tc.id=" + map.get("tcid"));
				}
			}
			// 执行创建 临时表脚本
			this.executeSQLUpdate(sb.toString(), null);

			// //////////sql
			sb.delete(0, sb.length());

			// 跟新 voteNumber
			sb.append("UPDATE ").append(tableName).append(" av ")
			.append("SET voteNumber=").append("(")
			.append(" SELECT COUNT(*) FROM submit s WHERE s.project=av.id AND s.subject=av.tsid AND av.tcid=s.training_college AND s.uuid IS NOT NULL ");

			if (map.containsKey("pid")) {
				if (map.get("pid") != null ) {
					if (map.get("pid").indexOf(",") > 0) {
						sb.append(" AND s.project in (" + map.get("pid") + ") ");
					} else {
						sb.append(" AND s.project=" + map.get("pid"));
					}
				}
			}

			if (map.containsKey("stid")) {
				if (map.get("stid") != null && !map.get("stid").equals("0")) {
					sb.append(" AND s.subject=" + map.get("stid"));
				}
			}

			if (map.containsKey("tcid")) {
				if (map.get("tcid") != null && !map.get("tcid").equals("0")) {
					sb.append(" AND s.training_college=" + map.get("tcid"));
				}
			}

			sb.append(")");

			this.executeSQLUpdate(sb.toString(), null);

			// ////////////////////

			sb.delete(0, sb.length());

			// 跟新 factNumber

			sb.append("UPDATE ").append(tableName).append(" av ").
			append("SET factNumber=").append("(")
			.append(" SELECT COUNT(*) FROM teacher_training_records s WHERE s.project=av.id AND s.subject=av.tsid AND av.tcid=s.training_college AND s.final_status=2 ");

			if (map.containsKey("pid")) {
				if (map.get("pid") != null) {
					if (map.get("pid").indexOf(",") > 0) {
						sb.append(" AND s.project in (" + map.get("pid") + ") ");
					} else {
						sb.append(" AND s.project=" + map.get("pid"));
					}
				}
			}

			if (map.containsKey("stid")) {
				if (map.get("stid") != null && !map.get("stid").equals("0")) {
					sb.append(" AND s.subject=" + map.get("stid"));
				}
			}

			if (map.containsKey("tcid")) {
				if (map.get("tcid") != null && !map.get("tcid").equals("0")) {
					sb.append(" AND s.training_college=" + map.get("tcid"));
				}
			}

			sb.append(")");

			this.executeSQLUpdate(sb.toString(), null);

			// //////////////////////////
			sb.delete(0, sb.length());

			// 跟新score
			sb.append("UPDATE ").append(tableName)
			.append(" av SET av.score=av.voteNumber/av.factNumber where av.voteNumber>0 and av.factNumber>0 ");
			this.executeSQLUpdate(sb.toString(), null);

			//
			sb.delete(0, sb.length());

			sb.append("SELECT * FROM ").append(tableName).append(" t ORDER BY t.score DESC");

			result = this.getListBySQL(sb.toString(), null);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sb.delete(0, sb.length());
			sb.append(" DROP TABLE ");
			sb.append(tableName);
			this.executeSQLUpdate(sb.toString(), null);
		}

		return result;
	}

	/**
	 * 评审率分析
	 */
	@Override
	public List getExpertPsqManyiLv(HashMap<String, String> map, String tableName) {

		/**
		 * 创建临时表
		 */
		List result = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		try {
			
			sb.append("CREATE TEMPORARY TABLE ")
			.append(tableName)
			.append(" SELECT p.id,p.name as projectName, pa.subject as tsid,ts.name AS tsname,pa.training_college AS tcid ,tc.name AS tcname ,0 AS planNumber,0 AS voteNumber ,0.000 AS score ")
			.append(" FROM project_apply pa,project p,training_subject ts,training_college tc ")
			.append(" WHERE pa.proejct=p.id AND pa.subject = ts.id AND pa.training_college = tc.id ");
			
			if (map.containsKey("pid")) {
				if (map.get("pid") != null) {
					if (map.get("pid").indexOf(",") > 0) {
						sb.append(" AND p.id in (" + map.get("pid") + ") ");
					} else {
						sb.append(" AND p.id=" + map.get("pid"));
					}
				}
			}

			if (map.containsKey("stid")) {
				if (map.get("stid") != null && !map.get("stid").equals("0")) {
					sb.append(" AND ts.id=" + map.get("stid"));
				}
			}

			if (map.containsKey("tcid")) {
				if (map.get("tcid") != null && !map.get("tcid").equals("0")) {
					sb.append(" AND tc.id=" + map.get("tcid"));
				}
			}
			// 执行创建 临时表脚本
			this.executeSQLUpdate(sb.toString(), null);

			// //////////sql
			sb.delete(0, sb.length());

			// 跟新 voteNumber
			sb.append("UPDATE ").append(tableName).append(" av ")
			.append("SET voteNumber=").append("(")
			.append(" SELECT COUNT(*) FROM submit s WHERE s.project=av.id AND s.subject=av.tsid AND av.tcid=s.training_college ");

			if (map.containsKey("pid")) {
				if (map.get("pid") != null ) {
					if (map.get("pid").indexOf(",") > 0) {
						sb.append(" AND s.project in (" + map.get("pid") + ") ");
					} else {
						sb.append(" AND s.project=" + map.get("pid"));
					}
				}
			}

			if (map.containsKey("stid")) {
				if (map.get("stid") != null && !map.get("stid").equals("0")) {
					sb.append(" AND s.subject=" + map.get("stid"));
				}
			}

			if (map.containsKey("tcid")) {
				if (map.get("tcid") != null && !map.get("tcid").equals("0")) {
					sb.append(" AND s.training_college=" + map.get("tcid"));
				}
			}

			if (map.containsKey("psqid")) {
				if (map.get("psqid") != null && !map.get("psqid").equals("0")) {
					sb.append(" AND s.psq=" + map.get("psqid"));
				}
			}
			sb.append(")");

			this.executeSQLUpdate(sb.toString(), null);

			// ////////////////////

			sb.delete(0, sb.length());

			// 跟新 planNumber

			sb.append("UPDATE ").append(tableName).append(" av ").
			append("SET planNumber=").append("(")
			.append(" SELECT COUNT(*) FROM project_apply_expert pae, project_apply pa  WHERE pa.proejct=av.id AND pa.subject=av.tsid AND av.tcid=pa.training_college AND pa.id=pae.project_apply");

			if (map.containsKey("pid")) {
				if (map.get("pid") != null) {
					if (map.get("pid").indexOf(",") > 0) {
						sb.append(" AND pa.proejct in (" + map.get("pid") + ") ");
					} else {
						sb.append(" AND pa.proejct=" + map.get("pid"));
					}
				}
			}

			if (map.containsKey("stid")) {
				if (map.get("stid") != null && !map.get("stid").equals("0")) {
					sb.append(" AND pa.subject=" + map.get("stid"));
				}
			}

			if (map.containsKey("tcid")) {
				if (map.get("tcid") != null && !map.get("tcid").equals("0")) {
					sb.append(" AND pa.training_college=" + map.get("tcid"));
				}
			}

			sb.append(")");

			this.executeSQLUpdate(sb.toString(), null);

			// //////////////////////////
			sb.delete(0, sb.length());

			// 跟新score
			sb.append("UPDATE ").append(tableName).append(" av SET av.score=av.voteNumber/av.planNumber where av.voteNumber>0 and av.planNumber>0 ");
			this.executeSQLUpdate(sb.toString(), null);

			//
			sb.delete(0, sb.length());

			sb.append("SELECT * FROM ").append(tableName).append(" t ORDER BY t.score DESC");

			result = this.getListBySQL(sb.toString(), null);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sb.delete(0, sb.length());
			sb.append(" DROP TABLE ");
			sb.append(tableName);
			this.executeSQLUpdate(sb.toString(), null);
		}

		return result;
	}
	
	@Override
	public List getPsqSearchTraining(String projectId) {

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT pa.subject as tsid,ts.name AS tsname,pa.training_college AS tcid ,tc.name AS tcname ").append(" FROM project_apply pa,project p,training_subject ts,training_college tc ").append(" WHERE pa.proejct=p.id AND pa.subject = ts.id AND pa.training_college = tc.id ");
		sb.append(" and p.id=" + projectId);

		return this.getListBySQL(sb.toString(), null);
	}

}
