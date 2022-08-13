package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ExamInformation;

/**
 * ClassName: IExamInformationService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public interface IExamInformationService {

	/**
	 * 通过id获取资源
	 * 
	 * @param id
	 * @return
	 */
	ExamInformation getById(int id);

	/**
	 * 添加资源
	 * 
	 * @param ExamInformation
	 * @return
	 */
	ExamInformation add(ExamInformation ExamInformation);

	/**
	 * @param ExamInformation
	 */
	void update(ExamInformation ExamInformation);

	/**
	 * @param id
	 */
	void delById(int id);
	
	/**
	 * 根据指定条件获取列表
	 * @param currentUser
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param length
	 * @param role
	 * @return
	 */
	public List<ExamInformation> searchExamInformation(Map<String, Object> searchMap, String sorts, int offset, int length);

	/**
	 * 根据指定条件获取数目
	 * @param searchMap
	 * @return
	 */
	public int searchExamInformationCount(Map<String, Object> searchMap);

}
