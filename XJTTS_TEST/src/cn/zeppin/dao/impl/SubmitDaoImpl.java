package cn.zeppin.dao.impl;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.ISubmitDao;
import cn.zeppin.entity.Submit;

public class SubmitDaoImpl extends BaseDaoImpl<Submit, Integer> implements ISubmitDao {

	@Override
	public Submit getSubmitByAll(int valuator, int psq, int project, short subject, int trainingCollege, int creater) {

		StringBuilder sb = new StringBuilder();
		sb.append("from Submit t");
		sb.append(" where 1=1 ");
		sb.append(" and t.valuator=" + valuator);
		sb.append(" and t.psq=" + psq);
		sb.append(" and t.project=" + project);
		sb.append(" and t.trainingSubject=" + subject);
		sb.append(" and t.trainingCollege=" + trainingCollege);
		sb.append(" and t.creater=" + creater);

		Object obj = this.getObjectByHql(sb.toString(), null);

		if (obj != null) {

			Submit submit = (Submit) obj;
			return submit;

		} else {
			return null;
		}
	}

	public List<Submit> getSubmitByGroup(int paperid) {
		String hql = "FROM Submit s WHERE s.psq=" + paperid + " GROUP BY s.project,s.trainingSubject,s.trainingCollege";
		return (List<Submit>) this.getListByHSQL(hql);
	}

	public List<Submit> getSubmitByParams(HashMap<String, String> mapParams) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Submit s WHERE s.psq=").append(mapParams.get("paperid"));
		if (mapParams.get("pid") != null && !mapParams.get("pid").equals("0")) {
			sb.append(" and s.project=").append(mapParams.get("pid"));
		}
		if (mapParams.get("stid") != null && !mapParams.get("stid").equals("0")) {
			sb.append(" and s.trainingSubject=").append(mapParams.get("stid"));
		}
		if (mapParams.get("tcid") != null && !mapParams.get("tcid").equals("0")) {
			sb.append(" and s.trainingCollege=").append(mapParams.get("tcid"));
		}
		sb.append(" GROUP BY s.project,s.trainingSubject,s.trainingCollege");
		return (List<Submit>) this.getListByHSQL(sb.toString());
	}

	@Override
	public int getPsqSubmitCount(HashMap<String, String> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from submit s,teacher_training_records ttr where 1=1 and s.uuid=ttr.uuid ");

		if (map.containsKey("paperid")) {
			if (map.get("paperid") != null) {
				sb.append(" AND s.psq=" + map.get("paperid"));
			}
		}

		if (map.containsKey("pid")) {
			if (map.get("pid") != null && !map.get("pid").equals("0") && !map.get("pid").trim().equals("")) {
				sb.append(" AND s.project=" + map.get("pid"));
			}
		}

		if (map.containsKey("stid")) {
			if (map.get("stid") != null && !map.get("stid").equals("0") && !map.get("stid").trim().equals("")) {
				sb.append(" AND s.subject=" + map.get("stid"));
			}
		}

		if (map.containsKey("tcid")) {
			if (map.get("tcid") != null && !map.get("tcid").equals("0") && !map.get("tcid").trim().equals("")) {
				sb.append(" AND s.training_college=" + map.get("tcid"));
			}
		}

		if (map.containsKey("classes")) {
			sb.append(" and ttr.classes=").append(map.get("classes"));
		}

		Object obj = this.getObjectBySql(sb.toString(), null);

		return Integer.parseInt(obj.toString());
	}

	@Override
	public List<Submit> getPsqSubmit(HashMap<String, String> map, int offset, int length) {
		StringBuilder sb = new StringBuilder();

		sb.append("from Submit s where 1=1 ");

		if (map.containsKey("paperid")) {
			if (map.get("paperid") != null) {
				sb.append(" AND s.psq=" + map.get("paperid"));
			}
		}

		if (map.containsKey("pid")) {
			if (map.get("pid") != null && !map.get("pid").equals("0") && !map.get("pid").trim().equals("")) {
				sb.append(" AND s.project=" + map.get("pid"));
			}
		}

		if (map.containsKey("stid")) {
			if (map.get("stid") != null && !map.get("stid").equals("0") && !map.get("stid").trim().equals("")) {
				sb.append(" AND s.trainingSubject=" + map.get("stid"));
			}
		}

		if (map.containsKey("tcid")) {
			if (map.get("tcid") != null && !map.get("tcid").equals("0") && !map.get("tcid").trim().equals("")) {
				sb.append(" AND s.trainingCollege=" + map.get("tcid"));
			}
		}

		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getSubmitCountByProjectPsq(int psq, int projectId) {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) ").append(" from submit t ").append("where 1=1 ").append(" and t.project=" + projectId).append(" and t.psq=" + psq);

		Object obj = this.getObjectBySql(sb.toString(), null);

		return Integer.parseInt(obj.toString());
	}

	@Override
	public int getReportPsqSubmitCount(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from submit s where 1=1 ");

		if (map.containsKey("paperid")) {
			if (map.get("paperid") != null) {
				sb.append(" AND s.psq=" + map.get("paperid"));
			}
		}

		if (map.containsKey("pid")) {
			if (map.get("pid") != null && !map.get("pid").equals("0") && !map.get("pid").trim().equals("")) {
				sb.append(" AND s.project=" + map.get("pid"));
			}
		}

		if (map.containsKey("stid")) {
			if (map.get("stid") != null && !map.get("stid").equals("0") && !map.get("stid").trim().equals("")) {
				sb.append(" AND s.subject=" + map.get("stid"));
			}
		}

		if (map.containsKey("tcid")) {
			if (map.get("tcid") != null && !map.get("tcid").equals("0") && !map.get("tcid").trim().equals("")) {
				sb.append(" AND s.training_college=" + map.get("tcid"));
			}
		}


		Object obj = this.getObjectBySql(sb.toString(), null);

		return Integer.parseInt(obj.toString());
	}

}
