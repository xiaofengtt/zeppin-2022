package cn.zeppin.project.chinamobile.media.web.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zeppin.project.chinamobile.media.core.entity.VideoPublish;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.IVideoPublishDAO;
import cn.zeppin.project.chinamobile.media.web.dao.base.BaseDAO;

/**
 * @author Clark.R 2016年4月30日
 *
 */

@Repository
public class VideoPublishDAO extends BaseDAO<VideoPublish, String> implements IVideoPublishDAO {
	
	@Override
	public Integer getCountByParams(VideoPublish videoinfo) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from video_publish vp,category cat,resource re,sys_user su where 1=1 and vp.category=cat.id and vp.cover=re.id and vp.creator=su.id");
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			sb.append(" and vp.id='"+videoinfo.getId()+"'");
		}
		if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
			sb.append(" and vp.title like'%"+videoinfo.getTitle()+"%'");
		}
		if(videoinfo.getShortTitle() != null && !"".equals(videoinfo.getShortTitle())){
			sb.append(" and vp.short_title like'%"+videoinfo.getShortTitle()+"%'");
		}
		if(videoinfo.getCategory() != null && !"".equals(videoinfo.getCategory())){
			sb.append(" and vp.category ='"+videoinfo.getCategory()+"'");
		}
		if(videoinfo.getStatus() != null && !"".equals(videoinfo.getStatus())){
			sb.append(" and vp.status ='"+videoinfo.getStatus()+"'");
		}
		
		return Integer.valueOf(this.getResultBySQL(sb.toString()).toString());
	}
	
	@Override
	public List<Entity> getListByParams(VideoPublish videoinfo, String sorts,
			Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select vp.id as id,vp.title as title,vp.short_title as shortTitle,vp.cover as cover,cat.name as categoryName,su.name as creator,vp.createtime as createtime,vp.status as status ");
		sb.append(" from video_publish vp,category cat,sys_user su where 1=1 and vp.category=cat.id and vp.creator=su.id");
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			sb.append(" and vp.id='"+videoinfo.getId()+"'");
		}
		if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
			sb.append(" and vp.title like'%"+videoinfo.getTitle()+"%'");
		}
		if(videoinfo.getShortTitle() != null && !"".equals(videoinfo.getShortTitle())){
			sb.append(" and vp.short_title like'%"+videoinfo.getShortTitle()+"%'");
		}
		if(videoinfo.getCategory() != null && !"".equals(videoinfo.getCategory())){
			sb.append(" and vp.category ='"+videoinfo.getCategory()+"'");
		}
		if(videoinfo.getStatus() != null && !"".equals(videoinfo.getStatus())){
			sb.append(" and vp.status ='"+videoinfo.getStatus()+"'");
		}
		
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append(" vp.").append(sort);
				comma = ",";
			}
		}
		
		if(offset!=null && length!=null){
			return this.getBySQL(sb.toString(), offset, length, null, resultClass);
		}else{
			return this.getBySQL(sb.toString(), resultClass);
		}
	}
	
	public List<Object[]> getStatusCount(VideoPublish videoinfo){
		StringBuilder sb = new StringBuilder();
		sb.append("select status , count(status) from video_publish vp where 1=1 ");
		if(videoinfo.getCategory() != null && !"".equals(videoinfo.getCategory())){
			sb.append(" and vp.category='"+videoinfo.getCategory()+"'");
		}
		sb.append(" group by status");
		return this.getBySQL(sb.toString());
	}

	@Override
	public List<Entity> getListByParams(VideoPublish videoinfo, String scode, String sort, Integer offset,
			Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select vp.video as id,vp.title as title,re.url as coverURL,vi.video as videoURL");
		sb.append(" from video_publish vp,category cat,resource re,videoinfo vi where 1=1 and vp.category=cat.id and vp.cover=re.id and vp.video=vi.id");
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			sb.append(" and vp.id='"+videoinfo.getId()+"'");
		}
		if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
			sb.append(" and vp.title like'%"+videoinfo.getTitle()+"%'");
		}
		if(videoinfo.getShortTitle() != null && !"".equals(videoinfo.getShortTitle())){
			sb.append(" and vp.short_title like'%"+videoinfo.getShortTitle()+"%'");
		}
		if(videoinfo.getCategory() != null && !"".equals(videoinfo.getCategory())){
			sb.append(" and vp.category ='"+videoinfo.getCategory()+"'");
		}
		if(videoinfo.getStatus() != null && !"".equals(videoinfo.getStatus())){
			sb.append(" and vp.status ='"+videoinfo.getStatus()+"'");
		}
		
		if(scode != null && !"".equals(scode)){
			sb.append(" and cat.scode like '"+scode+"%'");
		}
		
		sb.append(" order by vp.createtime desc");
		
		if(offset!=null && length!=null){
			return this.getBySQL(sb.toString(), offset, length, null, resultClass);
		}else{
			return this.getBySQL(sb.toString(), resultClass);
		}
	}

	@Override
	public Integer getCountByParams(VideoPublish videoinfo, String scode) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from video_publish vp,category cat,resource re,sys_user su where 1=1 and vp.category=cat.id and vp.cover=re.id and vp.creator=su.id");
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			sb.append(" and vp.id='"+videoinfo.getId()+"'");
		}
		if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
			sb.append(" and vp.title like'%"+videoinfo.getTitle()+"%'");
		}
		if(videoinfo.getShortTitle() != null && !"".equals(videoinfo.getShortTitle())){
			sb.append(" and vp.short_title like'%"+videoinfo.getShortTitle()+"%'");
		}
		if(videoinfo.getCategory() != null && !"".equals(videoinfo.getCategory())){
			sb.append(" and vp.category ='"+videoinfo.getCategory()+"'");
		}
		if(videoinfo.getStatus() != null && !"".equals(videoinfo.getStatus())){
			sb.append(" and vp.status ='"+videoinfo.getStatus()+"'");
		}
		
		if(scode != null && !"".equals(scode)){
			sb.append(" and cat.scode like '"+scode+"%'");
		}
		return Integer.valueOf(this.getResultBySQL(sb.toString()).toString());
	}

	@Override
	public List<VideoPublish> getListByParams(String video) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from VideoPublish vp where 1=1");
		if(video != null && !"".equals(video)){
			sb.append(" and vp.video='"+video+"'");
		}
		return this.getByHQL(sb.toString());
	}
}
