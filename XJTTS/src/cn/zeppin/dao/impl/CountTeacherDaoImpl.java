package cn.zeppin.dao.impl;

import java.util.List;

import cn.zeppin.dao.ICountTeachterDao;

public class CountTeacherDaoImpl extends BaseDaoImpl<Object[], Integer>
		implements ICountTeachterDao {

	/**
	 * 在岗 status=1
	 */
	@Override
	public List<Object[]> countBySex(String scode, String currentYear) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t.sex, count(distinct t.id) from organization o left join teacher t on t.organization=o.id");
		sb.append(" where (t.status=1 or t.status is null)");
		if (scode != null) {
			sb.append(" and o.SCODE like '" + scode + "%'");
		}
		sb.append(" and t.sex is not null");
		sb.append(" group by t.sex");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List countByAge(String scode, String currentYear) {
		StringBuilder sb = new StringBuilder();
		sb.append("select date_format(t.birthday,'%Y'), count(distinct t.id) from organization o left join teacher t on t.organization=o.id");
		sb.append(" where (t.status=1 or t.status is null)");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%'");
		}
		sb.append(" and t.birthday is not null");
		sb.append(" group by date_format(t.birthday,'%Y') order by t.birthday desc");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List countByTeachingAge(String scode, String currentYear) {
		StringBuilder sb = new StringBuilder();
		sb.append("select date_format(t.teaching_age,'%Y'), count(distinct t.id) from organization o left join teacher t on t.organization=o.id");
		sb.append(" where (t.status=1 or t.status is null)");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%'");
		}
		sb.append(" and t.teaching_age is not null");
		sb.append(" group by date_format(t.teaching_age,'%Y') order by t.teaching_age desc");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countBySchoolType(String scode, String currentYear) {
		StringBuilder sb = new StringBuilder();
		sb.append("select o.disctype,count(distinct o.id),count(distinct t.id)");
		sb.append(" from organization o left join teacher t on t.organization=o.id");
		sb.append(" where (t.status=1 or t.status is null) and o.isschool=1");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%'");
		}
		sb.append(" group by o.disctype");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countByJopTitle(String scode, String currentYear) {
		StringBuilder sb = new StringBuilder();
		sb.append("select j.name, count(distinct t.id) from organization o left join teacher t on t.organization=o.id,job_title j");
		sb.append(" where t.job_title = j.id");
		sb.append(" and (t.status=1 or t.status is null)");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%'");
		}
		sb.append(" group by t.job_title");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countByPolitice(String scode, String currentYear) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p.name, count(distinct t.id) from organization o left join teacher t on t.organization=o.id,Politics p");
		sb.append(" where t.politics = p.id");
		sb.append(" and (t.status=1 or t.status is null)");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%'");
		}

		sb.append(" group by t.politics");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countByMutiLanguage(String scode, String currentYear) {
		// 是双语教学的
		StringBuilder sb = new StringBuilder();
		sb.append("select count(distinct t.id) from organization o left join teacher t on t.organization=o.id");
		sb.append(" where (t.status=1 or t.status is null)");
		sb.append(" and t.multi_language = true");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%'");
		}
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countByChinese(String scode, String currentYear) {
		StringBuilder sb = new StringBuilder();

		sb.append("select l.name, count(distinct t.id) from");
		sb.append(" organization o left join teacher t on t.organization=o.id ");
		sb.append(" left join teaching_language tl on tl.teacher = t.id");
		sb.append(" left join Language l on tl.language = l.id");
		sb.append(" where (t.status=1 or t.status is null)");
		sb.append(" and (tl.isprime = 1 or tl.ISPRIME is null)");
		sb.append(" and t.multi_language = false");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%'");
		}
		sb.append(" group by tl.language");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countByTeachingLanguage(String scode,
			String currentYear) {
		StringBuilder sb = new StringBuilder();

		sb.append("select l.name, count(distinct t.id) from");
		sb.append(" organization o left join teacher t on t.organization=o.id ");
		sb.append(" left join teaching_language tl on tl.teacher = t.id");
		sb.append(" left join Language l on tl.language = l.id");
		sb.append(" where (t.status=1 or t.status is null)");
		sb.append(" and (tl.isprime = 1 or tl.ISPRIME is null)");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%'");
		}

		sb.append(" group by tl.language");

		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countByTeachingGrade(String scode, String currentYear) {
		StringBuilder sb = new StringBuilder();
		sb.append("select g.name, count(distinct t.id) from");
		sb.append(" organization o left join teacher t on t.organization=o.id");
		sb.append(" left join teaching_grade tg on tg.teacher=t.id");
		sb.append(" left join Grade g on tg.grade=g.id");
		sb.append(" where (t.status=1 or t.status is null)");
		sb.append(" and (tg.isprime = 1 or tg.isprime is null)");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%'");
		}
		sb.append(" group by tg.grade");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countByTeachingSubject(String scode,
			String currentYear) {
		StringBuilder sb = new StringBuilder();
		sb.append("select s.name, count(distinct t.id) from");
		sb.append(" organization o left join teacher t on t.organization=o.id");
		sb.append(" left join teaching_subject ts on ts.teacher=t.id");
		sb.append(" left join Subject s on ts.subject=s.id");
		sb.append(" where (t.status=1 or t.status is null)");
		sb.append(" and (ts.isprime = 1 or ts.isprime is null)");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%'");
		}
		sb.append(" group by ts.subject");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countByTeacherAddress(String scode, String currentYear) {
		int substrLength = 10;
		if (scode != null) {
			if (scode.length() != 40) {// 是最后一级，则取最后一级的数据，否则取下一级单位的地区数据
				substrLength = scode.length() + 10;
			} else {
				substrLength = scode.length();
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append("select oo.name,t.* from (");
		sb.append(" select sum(cty.teacher_count) as count, substr(o.scode from 1 for "
				+ substrLength + ") as scode");
		sb.append(" from organization o left join count_teacher_year cty on cty.organization=o.ID");
		sb.append(" where cty.year='" + currentYear + "' ");
		sb.append(" and cty.status=1");
		if (scode != null) {
			sb.append(" and o.scode like '" + scode + "%' ");
			sb.append(" group by substr(o.scode from 1 for " + substrLength
					+ ")");
		}
		sb.append(") t left join organization oo on t.scode=oo.scode");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countByLastYear(String scode, int year) {
		StringBuilder sb = new StringBuilder();

		sb.append("select sum(cty.teacher_count) from");
		sb.append(" organization o left join count_teacher_year cty on cty.organization=o.id");
		if (scode != null) {
			sb.append(" and o.SCODE like '" + scode + "%'");
		}
		sb.append(" and cty.year='" + year + "'");
		sb.append(" and cty.status=1");
		return this.getListBySQL(sb.toString(), null);
	}
	
	

	@Override
	public List<Object[]> countByAttribute(String scode, String currentYear) {
		StringBuilder sb = new StringBuilder();
		sb.append("select o.ftype, count(distinct t.id) from teacher t left join organization o on t.organization=o.id");
		sb.append(" where (t.status=1 or t.status is null)");
		if (scode != null) {
			sb.append(" and o.SCODE like '" + scode + "%'");
		}
		sb.append(" group by o.ftype");
		return this.getListBySQL(sb.toString(), null);
	}

	@Override
	public List<Object[]> countByCurrentYear(String scode, int year) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(t.id) from");
		sb.append(" organization o left join teacher t on t.organization=o.id");
		if (scode != null) {
			sb.append(" and o.SCODE like '" + scode + "%'");
		}
		sb.append(" and t.status=1");
		return this.getListBySQL(sb.toString(), null);
	}

}
