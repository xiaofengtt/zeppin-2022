package cn.zeppin.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.zeppin.dao.ICountTeacherYearDao;
import cn.zeppin.entity.CountTeacherYear;

public class CountTeacherYearDaoImpl extends BaseDaoImpl<CountTeacherYear, Integer> implements ICountTeacherYearDao {
	
	@Override
	public List<List<Object[]>> getPoorCountList(String organizationScode, Integer organizationLevel, String years, String endYear, String projectType) {
		StringBuilder sb = new StringBuilder();
		List<List<Object[]>> objList = new ArrayList<>();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		
		sb.append(" CREATE TEMPORARY TABLE tmp_poor_training_records_").append(uuid);
		sb.append(" SELECT ttr.id ,ttr.project, p.year , ttr.teacher, ttr.TRAINING_STATUS ,oo.is_poor");
		sb.append(" from teacher_training_records ttr");
		sb.append(" join project p on ttr.project=p.id and p.year in(").append(years).append(") ");
//		sb.append(" join project_type pt on p.type=pt.id");
		if(projectType!=null && !"".equals(projectType)){//20161207 修改筛选条件 由“项目类型”改为“项目ID（多选）”
//			sb.append(" and (p.type=").append(projectType).append(" or pt.pid=").append(projectType).append(")");
			sb.append(" and p.id in(");
			sb.append(projectType);
			sb.append(") ");
		}
		sb.append(" join organization o on ttr.TEACHER_ORGANIZATION=o.id join organization oo on o.PID=oo.ID and o.SCODE like '").append(organizationScode).append("%'");
		sb.append(" where ttr.final_status=2;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" SELECT ttr.year ,count(*) as count from tmp_poor_training_records_").append(uuid).append(" ttr where ttr.IS_POOR=1 GROUP BY ttr.year;");
		@SuppressWarnings("unchecked")
		List<Object[]> yearObj = this.getListBySQL(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_poor_count_").append(uuid);
		sb.append(" SELECT SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, 0 as training_count,0 as poor_training_count,sum(cty.teacher_count)  as teacher_count,0 as poor_teacher_count,0 as poor_training_teacher_count");
		sb.append(" from count_teacher_year cty, organization o");
		sb.append(" where cty.ORGANIZATION=o.ID and cty.status=1 and o.scode like '").append(organizationScode).append("%' and cty.year=").append(endYear);
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_poor_training_count_").append(uuid);
		sb.append(" SELECT SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, count(distinct t.id) as training_teacher_count ,count(*) as training_count");
		sb.append(" from tmp_poor_training_records_").append(uuid).append(" ttr, teacher t, organization o,organization oo");
		sb.append(" where ttr.TEACHER = t.id and t.ORGANIZATION=o.ID and o.pid=oo.id and oo.is_poor=1 ");
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_poor_count_").append(uuid).append(" tpc, tmp_poor_training_count_").append(uuid).append(" tmp ");
		sb.append(" set tpc.poor_training_teacher_count=tmp.training_teacher_count ,tpc.poor_training_count=tmp.training_count");
		sb.append(" where tpc.scode=tmp.scode;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_poor_training_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_poor_teacher_count_").append(uuid);
		sb.append(" SELECT SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, count(*) as count");
		sb.append(" from tmp_poor_training_records_").append(uuid).append(" ttr, teacher t, project p ,organization o");
		sb.append(" where ttr.TEACHER = t.id and ttr.PROJECT = p.id and t.ORGANIZATION=o.id");
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_poor_count_").append(uuid).append(" tpc, tmp_poor_teacher_count_").append(uuid).append(" tmp ");
		sb.append(" set tpc.training_count=tmp.count");
		sb.append(" where tpc.scode=tmp.scode;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_poor_teacher_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_poor_poor_teacher_count_").append(uuid);
		sb.append(" SELECT SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, sum(cty.teacher_count) as count");
		sb.append(" from count_teacher_year cty, organization o,organization oo");
		sb.append(" where cty.ORGANIZATION=o.ID and o.pid=oo.id and cty.status=1 and o.scode like '").append(organizationScode).append("%' and cty.year=").append(endYear).append(" and oo.is_poor=1");
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_poor_count_").append(uuid).append(" tpc, tmp_poor_poor_teacher_count_").append(uuid).append(" tmp ");
		sb.append(" set tpc.poor_teacher_count=tmp.count");
		sb.append(" where tpc.scode=tmp.scode;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_poor_poor_teacher_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_poor_training_records_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("select o.id,o.NAME,tpc.* from tmp_poor_count_").append(uuid).append(" tpc ,organization o where tpc.scode=o.scode;");
		@SuppressWarnings("unchecked")
		List<Object[]> obj = this.getListBySQL(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_poor_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		objList.add(yearObj);
		objList.add(obj);
		return objList;
	}
	
	@Override
	public List<List<Object[]>> getVillageCountList(String organizationScode, Integer organizationLevel, String years, String endYear, String projectType) {
		StringBuilder sb = new StringBuilder();
		List<List<Object[]>> objList = new ArrayList<>();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		
		sb.append("CREATE TEMPORARY TABLE tmp_village_training_records_").append(uuid);
		sb.append(" SELECT ttr.id ,ttr.project, p.year , ttr.teacher, ttr.TRAINING_STATUS, o.FTYPE");
		sb.append(" from teacher_training_records ttr");
		sb.append(" join project p on ttr.project=p.id and p.year in(").append(years).append(") ");
//		sb.append(" join project_type pt on p.type=pt.id");
//		if(projectType!=null && !"".equals(projectType)){
//			sb.append(" and (p.type=").append(projectType).append(" or pt.pid=").append(projectType).append(")");
//		}
		if(projectType!=null && !"".equals(projectType)){//20161207 修改筛选条件 由“项目类型”改为“项目ID（多选）”
			sb.append(" and p.id in(");
			sb.append(projectType);
			sb.append(") ");
		}
		sb.append(" join organization o on ttr.TEACHER_ORGANIZATION=o.id and o.SCODE like '").append(organizationScode).append("%'");
		sb.append(" where ttr.final_status=2;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" SELECT ttr.year ,count(*) as count from tmp_village_training_records_").append(uuid).append(" ttr where ttr.FTYPE in('乡中心区','城乡结合区','村庄','乡村','镇乡结合区') GROUP BY ttr.year;");
		@SuppressWarnings("unchecked")
		List<Object[]> yearObj = this.getListBySQL(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_village_count_").append(uuid);
		sb.append(" SELECT SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, 0 as training_count,0 as village_training_count,sum(cty.teacher_count)  as teacher_count,0 as village_teacher_count,0 as village_training_teacher_count");
		sb.append(" from count_teacher_year cty, organization o");
		sb.append(" where cty.ORGANIZATION=o.ID and cty.status=1 and o.scode like '").append(organizationScode).append("%' and cty.year=").append(endYear);
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_village_training_count_").append(uuid);
		sb.append(" SELECT SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, count(distinct t.id) as training_teacher_count ,count(*) as training_count");
		sb.append(" from tmp_village_training_records_").append(uuid).append(" ttr, teacher t, organization o");
		sb.append(" where ttr.TEACHER = t.id and t.ORGANIZATION=o.ID and o.FTYPE in('乡中心区','城乡结合区','村庄','乡村','镇乡结合区')");
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_village_count_").append(uuid).append(" tpc, tmp_village_training_count_").append(uuid).append(" tmp ");
		sb.append(" set tpc.village_training_teacher_count=tmp.training_teacher_count ,tpc.village_training_count=tmp.training_count");
		sb.append(" where tpc.scode=tmp.scode;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_village_training_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_village_teacher_count_").append(uuid);
		sb.append(" SELECT SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, count(*) as count");
		sb.append(" from tmp_village_training_records_").append(uuid).append(" ttr, teacher t, project p ,organization o");
		sb.append(" where ttr.TEACHER = t.id and ttr.PROJECT = p.id and t.ORGANIZATION=o.id");
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_village_count_").append(uuid).append(" tpc, tmp_village_teacher_count_").append(uuid).append(" tmp ");
		sb.append(" set tpc.training_count=tmp.count");
		sb.append(" where tpc.scode=tmp.scode;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_village_teacher_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_villa_villa_teacher_count_").append(uuid);
		sb.append(" SELECT SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, sum(cty.teacher_count) as count");
		sb.append(" from count_teacher_year cty, organization o");
		sb.append(" where cty.ORGANIZATION=o.ID and cty.status=1 and o.scode like '").append(organizationScode).append("%' and cty.year=").append(endYear).append(" and o.FTYPE in('乡中心区','城乡结合区','村庄','乡村','镇乡结合区')");
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_village_count_").append(uuid).append(" tpc, tmp_villa_villa_teacher_count_").append(uuid).append(" tmp ");
		sb.append(" set tpc.village_teacher_count=tmp.count");
		sb.append(" where tpc.scode=tmp.scode;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_villa_villa_teacher_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_village_training_records_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("select o.id,o.NAME,tpc.* from tmp_village_count_").append(uuid).append(" tpc ,organization o where tpc.scode=o.scode;");
		@SuppressWarnings("unchecked")
		List<Object[]> obj = this.getListBySQL(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_village_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		objList.add(yearObj);
		objList.add(obj);
		return objList;
	}

	@Override
	public List<Object[]> getYearCountList(String organizationScode, String years) {
		StringBuilder sb = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		sb.append(" CREATE TEMPORARY TABLE tmp_teacher_training_year_").append(uuid);
		sb.append(" select ttr.id,p.year , ttr.teacher ,ttr.training_status ");
		sb.append(" from teacher_training_records ttr");
		sb.append(" join project p on ttr.project=p.id and p.year in(").append(years).append(") ");
		sb.append(" join organization o on ttr.TEACHER_ORGANIZATION=o.id and o.SCODE like '").append(organizationScode).append("%'");
		sb.append(" where ttr.final_status=2;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_year_count_").append(uuid);
		sb.append(" select cty.year, sum(cty.TEACHER_COUNT) as teacher_count, 0 as training_count, 0 as training_teacher_count, 0 as training_complete_count, 0 as total_training_teacher_count");
		sb.append(" from count_teacher_year cty ,organization o ");
		sb.append(" where cty.ORGANIZATION=o.ID and cty.STATUS = 1");
		sb.append(" and cty.year in(").append(years).append(") and o.SCODE like '").append(organizationScode).append("%'");
		sb.append(" GROUP BY cty.year ;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_year_training_count_").append(uuid);
		sb.append(" select ttr.year , count(*) as training_count, count(DISTINCT ttr.teacher) as training_teacher_count");
		sb.append(" from tmp_teacher_training_year_").append(uuid).append(" ttr");
		sb.append(" group by ttr.year;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_year_count_").append(uuid).append(" tyc , tmp_year_training_count_").append(uuid).append(" tmp");
		sb.append(" set tyc.training_count = tmp.training_count , tyc.training_teacher_count=tmp.training_teacher_count");
		sb.append(" where tyc.year=tmp.year;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_year_training_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_year_train_complete_count_").append(uuid);
		sb.append(" select ttr.year , count(*) as count");
		sb.append(" from tmp_teacher_training_year_").append(uuid).append(" ttr");
		sb.append(" where ttr.training_status in(3,5,6)");
		sb.append(" group by ttr.year;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_year_count_").append(uuid).append(" tyc , tmp_year_train_complete_count_").append(uuid).append(" tmp");
		sb.append(" set tyc.training_complete_count = tmp.count");
		sb.append(" where tyc.year=tmp.year;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_year_train_complete_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_year_total_count_").append(uuid);
		sb.append(" select count(DISTINCT ttr.teacher) as count ");
		sb.append(" from tmp_teacher_training_year_").append(uuid).append(" ttr");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_year_count_").append(uuid).append(" tlc , tmp_year_total_count_").append(uuid).append(" tmp");
		sb.append(" set tlc.total_training_teacher_count = tmp.count;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_year_total_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_teacher_training_year_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("SELECT * from tmp_year_count_").append(uuid).append(";");
		@SuppressWarnings("unchecked")
		List<Object[]> obj = this.getListBySQL(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_year_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		return obj;
	}

	@Override
	public List<Object[]> getLevelCountList(String organizationScode, String years, String endYear) {
		StringBuilder sb = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		sb.append(" CREATE TEMPORARY TABLE tmp_teacher_training_level_").append(uuid);
		sb.append(" select ttr.id,p.type , ttr.teacher ,ttr.training_status ");
		sb.append(" from teacher_training_records ttr");
		sb.append(" join project p on ttr.project=p.id and p.year in(").append(years).append(") ");
		sb.append(" join organization o on ttr.TEACHER_ORGANIZATION=o.id and o.SCODE like '").append(organizationScode).append("%'");
		sb.append(" where ttr.final_status=2;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_level_count_").append(uuid);
		sb.append(" select pt.ID, 0 as teacher_count, count(*) as training_count, count(DISTINCT ttr.teacher) as training_teacher_count, 0 as training_complete_count, 0 as total_training_teacher_count");
		sb.append(" from tmp_teacher_training_level_").append(uuid).append(" ttr").append(", project_type pt ");
		sb.append(" where ttr.type=pt.id");
		sb.append(" group by pt.ID;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_level_complete_count_").append(uuid);
		sb.append(" select pt.ID, count(*) as count");
		sb.append(" from tmp_teacher_training_level_").append(uuid).append(" ttr").append(", project_type pt ");
		sb.append(" where ttr.type=pt.id and ttr.training_status in(3,5,6)");
		sb.append(" group by pt.ID;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_level_count_").append(uuid).append(" tlc , tmp_level_complete_count_").append(uuid).append(" tmp");
		sb.append(" set tlc.training_complete_count = tmp.count");
		sb.append(" where tlc.ID=tmp.ID;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_level_complete_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_level_teacher_count_").append(uuid);
		sb.append(" select sum(teacher_count) as count ");
		sb.append(" from count_teacher_year");
		sb.append(" where status=1 and year=").append(endYear);
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_level_count_").append(uuid).append(" tlc , tmp_level_teacher_count_").append(uuid).append(" tmp");
		sb.append(" set tlc.teacher_count = tmp.count;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_level_teacher_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_level_total_count_").append(uuid);
		sb.append(" select count(DISTINCT ttr.teacher) as count ");
		sb.append(" from tmp_teacher_training_level_").append(uuid).append(" ttr");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_level_count_").append(uuid).append(" tlc , tmp_level_total_count_").append(uuid).append(" tmp");
		sb.append(" set tlc.total_training_teacher_count = tmp.count;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_level_total_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_teacher_training_level_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("SELECT * from tmp_level_count_").append(uuid).append(";");
		@SuppressWarnings("unchecked")
		List<Object[]> obj = this.getListBySQL(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_level_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		return obj;
	}

	@Override
	public List<Object[]> getSubjectCountList(String organizationScode, String years, String endYear) {
		StringBuilder sb = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		sb.append(" CREATE TEMPORARY TABLE tmp_teacher_train_subject_").append(uuid);
		sb.append(" select ttr.id, s.id as subject, ttr.teacher ,ttr.training_status , s.name");
		sb.append(" from teacher_training_records ttr");
		sb.append(" join project p on ttr.project=p.id and p.year in(").append(years).append(") ");
		sb.append(" join organization o on ttr.TEACHER_ORGANIZATION=o.id and o.SCODE like '").append(organizationScode).append("%'");
		sb.append(" left outer join subject s on ttr.TEACHER_SUBJECT=s.id");
		sb.append(" where ttr.final_status=2;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_subject_count_").append(uuid);
		sb.append(" select ctr.subject, sum(ctr.teacher_count) as teacher_count, 0 as training_count, 0 as training_teacher_count, 0 as training_complete_count,s.name");
		sb.append(" from count_teacher_year ctr left join subject s on ctr.subject=s.id");
		sb.append(" join organization o on ctr.organization=o.id and o.SCODE like '").append(organizationScode).append("%'");
		sb.append(" where ctr.status=1 and ctr.year=").append(endYear);
		sb.append(" group by ctr.subject;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("UPDATE tmp_subject_count_").append(uuid).append(" set subject=-1,name='其他' where subject is null;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_subject_complete_count_").append(uuid);
		sb.append(" select ttr.subject, count(*) as count");
		sb.append(" from tmp_teacher_train_subject_").append(uuid).append(" ttr");
		sb.append(" where ttr.training_status in(3,5,6)");
		sb.append(" group by ttr.subject;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("UPDATE tmp_subject_complete_count_").append(uuid).append(" set subject=-1 where subject is null;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_subject_count_").append(uuid).append(" tsc , tmp_subject_complete_count_").append(uuid).append(" tmp");
		sb.append(" set tsc.training_complete_count = tmp.count");
		sb.append(" where tsc.subject=tmp.subject;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_subject_complete_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_subject_teacher_count_").append(uuid);
		sb.append(" select ttr.subject, count(*) as training_count, count(DISTINCT ttr.teacher) as training_teacher_count");
		sb.append(" from tmp_teacher_train_subject_").append(uuid).append(" ttr");
		sb.append(" group by ttr.subject;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("UPDATE tmp_subject_teacher_count_").append(uuid).append(" set subject=-1 where subject is null;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_subject_count_").append(uuid).append(" tsc , tmp_subject_teacher_count_").append(uuid).append(" tmp");
		sb.append(" set tsc.training_count = tmp.training_count, tsc.training_teacher_count = tmp.training_teacher_count");
		sb.append(" where tsc.subject=tmp.subject;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_subject_teacher_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_teacher_train_subject_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);

		sb = new StringBuilder();
		sb.append("SELECT * from tmp_subject_count_").append(uuid).append(";");
		@SuppressWarnings("unchecked")
		List<Object[]> obj = this.getListBySQL(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_subject_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		return obj;
	}
	
	@Override
	public List<Object[]> getGradeCountList(String organizationScode, String years, String endYear) {
		StringBuilder sb = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		sb.append(" CREATE TEMPORARY TABLE tmp_teacher_train_grade_").append(uuid);
		sb.append(" select ttr.id, g.id as grade, ttr.teacher ,ttr.training_status , g.name");
		sb.append(" from teacher_training_records ttr");
		sb.append(" join project p on ttr.project=p.id and p.year in(").append(years).append(") ");
		sb.append(" join organization o on ttr.TEACHER_ORGANIZATION=o.id and o.SCODE like '").append(organizationScode).append("%'");
		sb.append(" left outer join grade g on ttr.grade=g.id");
		sb.append(" where ttr.final_status=2;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_grade_count_").append(uuid);
		sb.append(" select ctr.grade, sum(ctr.teacher_count) as teacher_count, 0 as training_count, 0 as training_teacher_count, 0 as training_complete_count,g.name");
		sb.append(" from count_teacher_year ctr left outer join grade g on ctr.grade=g.id");
		sb.append(" join organization o on ctr.organization=o.id and o.SCODE like '").append(organizationScode).append("%'");
		sb.append(" where ctr.status=1 and ctr.year=").append(endYear);
		sb.append(" group by ctr.grade;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("UPDATE tmp_grade_count_").append(uuid).append(" set grade=-1,name='其他' where grade is null;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_grade_complete_count_").append(uuid);
		sb.append(" select ttr.grade, count(*) as count");
		sb.append(" from tmp_teacher_train_grade_").append(uuid).append(" ttr");
		sb.append(" where ttr.training_status in(3,5,6)");
		sb.append(" group by ttr.grade;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("UPDATE tmp_grade_complete_count_").append(uuid).append(" set grade=-1 where grade is null;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_grade_count_").append(uuid).append(" tsc , tmp_grade_complete_count_").append(uuid).append(" tmp");
		sb.append(" set tsc.training_complete_count = tmp.count");
		sb.append(" where tsc.grade=tmp.grade;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_grade_complete_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_grade_teacher_count_").append(uuid);
		sb.append(" select ttr.grade, count(*) as training_count, count(DISTINCT ttr.teacher) as training_teacher_count");
		sb.append(" from tmp_teacher_train_grade_").append(uuid).append(" ttr");
		sb.append(" group by ttr.grade;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("UPDATE tmp_grade_teacher_count_").append(uuid).append(" set grade=-1 where grade is null;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_grade_count_").append(uuid).append(" tgc , tmp_grade_teacher_count_").append(uuid).append(" tmp");
		sb.append(" set tgc.training_count = tmp.training_count, tgc.training_teacher_count = tmp.training_teacher_count");
		sb.append(" where tgc.grade=tmp.grade;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_grade_teacher_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_teacher_train_grade_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);

		sb = new StringBuilder();
		sb.append("SELECT * from tmp_grade_count_").append(uuid).append(";");
		@SuppressWarnings("unchecked")
		List<Object[]> obj = this.getListBySQL(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_grade_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		return obj;
	}
	
	@Override
	public List<Object[]> getOrganizationCountList(String organizationScode, Integer organizationLevel, String years, String endYear) {
		StringBuilder sb = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		sb.append(" CREATE TEMPORARY TABLE tmp_teacher_train_org_").append(uuid);
		sb.append(" select ttr.id , o.id as organization, ttr.teacher ,ttr.training_status ");
		sb.append(" from teacher_training_records ttr");
		sb.append(" join project p on ttr.project=p.id and p.year in(").append(years).append(") ");
		sb.append(" join organization o on ttr.TEACHER_ORGANIZATION=o.id and o.SCODE like '").append(organizationScode).append("%'");
		sb.append(" where ttr.final_status=2;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_org_count_").append(uuid);
		sb.append(" SELECT SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, sum(cty.teacher_count) as teacher_count, 0 as training_count, 0 as training_teacher_count, 0 as training_complete_count");
		sb.append(" from count_teacher_year cty, organization o");
		sb.append(" where cty.ORGANIZATION=o.ID and cty.status=1 and o.scode like '").append(organizationScode).append("%' and cty.year=").append(endYear);
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_org_teacher_count_").append(uuid);
		sb.append(" select SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, count(*) as training_count, count(DISTINCT ttr.teacher) as training_teacher_count");
		sb.append(" from tmp_teacher_train_org_").append(uuid).append(" ttr");
		sb.append(" join organization o on ttr.organization=o.id");
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_org_count_").append(uuid).append(" toc, tmp_org_teacher_count_").append(uuid).append(" tmp ");
		sb.append(" set toc.training_count=tmp.training_count ,toc.training_teacher_count=tmp.training_teacher_count");
		sb.append(" where toc.scode=tmp.scode;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" DROP TABLE tmp_org_teacher_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" CREATE TEMPORARY TABLE tmp_org_complete_count_").append(uuid);
		sb.append(" select SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(") as scode, count(*) as count");
		sb.append(" from tmp_teacher_train_org_").append(uuid).append(" ttr").append(", organization o ");
		sb.append(" where ttr.organization=o.id and ttr.training_status in(3,5,6)");
		sb.append(" group by SUBSTR(o.scode FROM 1 FOR ").append((organizationLevel+1)*10).append(");");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" update tmp_org_count_").append(uuid).append(" toc , tmp_org_complete_count_").append(uuid).append(" tmp");
		sb.append(" set toc.training_complete_count = tmp.count");
		sb.append(" where toc.scode=tmp.scode;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_org_complete_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_teacher_train_org_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("alter table tmp_org_count_").append(uuid).append(" add name varchar(100);");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("update tmp_org_count_").append(uuid).append(" toc join  organization o on toc.scode=o.scode set toc.name=o.name;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("alter table tmp_org_count_").append(uuid).append(" add level int(11);");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("update tmp_org_count_").append(uuid).append(" toc join  organization o on toc.scode=o.scode set toc.level=o.level;");
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("SELECT * from tmp_org_count_").append(uuid).append(";");
		@SuppressWarnings("unchecked")
		List<Object[]> obj = this.getListBySQL(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append("DROP TABLE tmp_org_count_").append(uuid).append(";");
		this.executeSQLUpdate(sb.toString(), null);
		
		return obj;
	}
}
