package cn.zeppin.project.chinamobile.media.web.dao.api;

import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.base.IBaseDAO;

/**
 * @author Clark.R 2016年4月30日
 *
 */

public interface IVideoinfoDAO extends IBaseDAO<Videoinfo, String> {
	
	public Integer getCountByParams(Videoinfo videoinfo);
	
	public List<Entity> getListByParams(Videoinfo videoinfo, String sorts, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);

	public List<Object[]> getStatusCount();
}
