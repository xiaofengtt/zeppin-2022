package cn.zeppin.project.chinamobile.media.web.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zeppin.project.chinamobile.media.core.entity.VideoCommodityPoint;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.IVideoCommodityPointDAO;
import cn.zeppin.project.chinamobile.media.web.dao.base.BaseDAO;

@Repository
public class VideoCommodityPointDAO extends BaseDAO<VideoCommodityPoint, String> implements IVideoCommodityPointDAO {
	
	@Override
	public Integer getCountByParams(VideoCommodityPoint videoCommodityPoint) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from video_commodity_point where 1=1");
		if(videoCommodityPoint.getVideo() != null && !"".equals(videoCommodityPoint.getVideo())){
			sb.append(" and video='"+videoCommodityPoint.getVideo()+"'");
		}
		
		return Integer.valueOf(this.getResultBySQL(sb.toString()).toString());
	}
	
	@Override
	public List<Entity> getListByParams(VideoCommodityPoint videoCommodityPoint, String sorts,
			Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select vcp.id, vcp.video, vcp.iframe, vcp.show_message as showMessage, vi.path as iframePath,");
		sb.append(" vcp.show_banner as showBanner, r.url as showBannerUrl, vcp.commodity, c.name as commodityName, ");
		sb.append(" vcp.timepoint, vcp.creator, su.name as creatorName ,vcp.createtime");
		sb.append(" from video_commodity_point vcp, video_iframe vi,commodity c, sys_user su, resource r");
		sb.append(" where vcp.iframe=vi.id and vcp.commodity=c.id and vcp.creator=su.id and vcp.show_banner=r.id and vcp.status='normal'");
		if(videoCommodityPoint.getId() != null && !"".equals(videoCommodityPoint.getId())){
			sb.append(" and vcp.id='"+videoCommodityPoint.getId()+"'");
		}
		
		if(videoCommodityPoint.getVideo() != null && !"".equals(videoCommodityPoint.getVideo())){
			sb.append(" and vcp.video='"+videoCommodityPoint.getVideo()+"'");
		}
		
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append(" vcp.").append(sort);
				comma = ",";
			}
		}
		
		if(offset!=null && length!=null){
			return this.getBySQL(sb.toString(), offset, length, null, resultClass);
		}else{
			return this.getBySQL(sb.toString(), resultClass);
		}
	}

	@Override
	public List<Entity> getListByParam(
			VideoCommodityPoint videoCommodityPoint, @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select vcp.timepoint as timepoint,vcp.show_message as showMessage,vcp.show_banner as showBanner,");
		sb.append(" vcp.commodity, c.cover as commodityCover ");
		sb.append(" from video_commodity_point vcp,commodity c");
		sb.append(" where vcp.commodity=c.id and vcp.status='normal'");
		if(videoCommodityPoint.getVideo() != null && !"".equals(videoCommodityPoint.getVideo())){
			sb.append(" and vcp.video='"+videoCommodityPoint.getVideo()+"'");
		}
		
		sb.append(" order by vcp.createtime desc");
		
		return this.getBySQL(sb.toString(), resultClass);
	}
}
