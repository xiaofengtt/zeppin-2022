package cn.zeppin.service;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Submit;

public interface ISubmitService extends IBaseService<Submit, Integer> {

	public Submit getSubmitByAll(int valuator, int psq, int project, short subject, int trainingCollege, int creater);

	public int getPsqSubmitCount(HashMap<String, String> map);
	
	/**
	 * 通过条件获取评审专家问卷数目
	 * @param map 条件
	 * @return count 条目数
	 */
	public int getReportPsqSubmitCount(HashMap<String, String> map);
	
	public List<Submit> getSubmitByGroup(int paperid);
	
	public List<Submit> getSubmitByParams(HashMap<String, String> mapParams);
	
	public List<Submit> getPsqSubmit(HashMap<String, String> map, int start, int length);
	
	public int getSubmitCountByProjectPsq(int psq, int projectId);

}
