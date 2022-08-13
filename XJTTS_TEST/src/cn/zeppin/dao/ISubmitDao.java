package cn.zeppin.dao;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Submit;

public interface ISubmitDao extends IBaseDao<Submit, Integer> {

	public Submit getSubmitByAll(int valuator, int psq, int project, short subject, int trainingCollege, int creater);
	
	public int getPsqSubmitCount(HashMap<String, String> map);
	
	/**
	 * 根据条件查询评审专家问卷数目
	 * @param map
	 * @return
	 */
	public int getReportPsqSubmitCount(HashMap<String, String> map);

	public List<Submit> getPsqSubmit(HashMap<String, String> map, int start, int length);
	
	public List<Submit> getSubmitByGroup(int paperid);
	
	public List<Submit> getSubmitByParams(HashMap<String, String> mapParams);
	/**
	 * 根据问卷和项目来获取提交的任务
	 * @param psq
	 * @param projectId
	 * @return
	 */
	public int getSubmitCountByProjectPsq(int psq,int projectId);

}
