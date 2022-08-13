package cn.zeppin.project.chinamobile.media.web.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.IVideoinfoDAO;
import cn.zeppin.project.chinamobile.media.web.dao.base.BaseDAO;

/**
 * @author Clark.R 2016年4月30日
 *
 */

@Repository
public class VideoinfoDAO extends BaseDAO<Videoinfo, String> implements IVideoinfoDAO {
	
	@Override
	public Integer getCountByParams(Videoinfo videoinfo) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from videoinfo where 1=1");
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			sb.append(" and id='"+videoinfo.getId()+"'");
		}
		
		if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
			sb.append(" and title like '%"+videoinfo.getTitle()+"%'");
		}
		
		if(videoinfo.getStatus() != null && !"".equals(videoinfo.getStatus())){
			sb.append(" and status='"+videoinfo.getStatus()+"'");
		}
		
		if(videoinfo.getTranscodingFlag() != null){
			sb.append(" and v.transcoding_flag=" + (videoinfo.getTranscodingFlag() ? 1 : 0) );
		}
		
		return Integer.valueOf(this.getResultBySQL(sb.toString()).toString());
	}
	
	@Override
	public List<Entity> getListByParams(Videoinfo videoinfo, String sorts,
			Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select v.id, v.title, v.context, v.tag, v.status, v.thumbnail, v.video,");
		sb.append(" v.time_length as timeLength, v.source, v.copyright, v.author, u.name as creatorName, v.createtime,");
		sb.append(" r.path as originalVideoPath, r.url as originalVideoUrl, r.dpi as originalVideoDpi");
		sb.append(" from videoinfo v , sys_user u , resource r");
		sb.append(" where v.creator=u.id and v.original_video=r.id");
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			sb.append(" and v.id='"+videoinfo.getId()+"'");
		}
		
		if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
			sb.append(" and v.title like '%"+videoinfo.getTitle()+"%'");
		}
		
		if(videoinfo.getStatus() != null && !"".equals(videoinfo.getStatus())){
			sb.append(" and v.status='"+videoinfo.getStatus()+"'");
		}
		
		if(videoinfo.getTranscodingFlag() != null){
			sb.append(" and v.transcoding_flag=" + (videoinfo.getTranscodingFlag() ? 1 : 0) );
		}
		
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append(" v.").append(sort);
				comma = ",";
			}
		}
		
		if(offset!=null && length!=null){
			return this.getBySQL(sb.toString(), offset, length, null, resultClass);
		}else{
			return this.getBySQL(sb.toString(), resultClass);
		}
	}
	
	public List<Object[]> getStatusCount(){
		StringBuilder sb = new StringBuilder();
		sb.append("select status , count(status) from videoinfo group by status");
		return this.getBySQL(sb.toString());
	}
}
