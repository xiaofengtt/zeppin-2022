package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ExamInformation;

/**
 * ClassName: IExamInforamtionDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public interface IExamInformationDAO extends IBaseDAO<ExamInformation, Integer> {

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
