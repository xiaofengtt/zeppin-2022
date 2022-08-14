package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IExamInformationDAO;
import cn.zeppin.entity.ExamInformation;
import cn.zeppin.service.api.IExamInformationService;

/**
 * ClassName: ExamInformationService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class ExamInformationService implements IExamInformationService {

	IExamInformationDAO iExamInformationDAO;

	/**
	 * @return the iExamInformationDAO
	 */
	public IExamInformationDAO getiExamInformationDAO() {
		return iExamInformationDAO;
	}

	/**
	 * @param iExamInformationDAO
	 *            the iExamInformationDAO to set
	 */
	public void setiExamInformationDAO(IExamInformationDAO iExamInformationDAO) {
		this.iExamInformationDAO = iExamInformationDAO;
	}

	@Override
	public ExamInformation getById(int id) {

		return this.getiExamInformationDAO().get(id);
	}

	@Override
	public ExamInformation add(ExamInformation ExamInformation) {

		return this.getiExamInformationDAO().save(ExamInformation);
	}

	@Override
	public void update(ExamInformation ExamInformation) {

		this.getiExamInformationDAO().update(ExamInformation);
	}

	@Override
	public void delById(int id) {

		ExamInformation ExamInformation = this.getiExamInformationDAO().get(id);
		ExamInformation.setStatus((short) 0);
		this.getiExamInformationDAO().update(ExamInformation);
	}

	@Override
	public List<ExamInformation> searchExamInformation(
			Map<String, Object> searchMap, String sorts, int offset, int length) {
		// TODO Auto-generated method stub
		return this.getiExamInformationDAO().searchExamInformation(searchMap, sorts, offset, length);
	}

	@Override
	public int searchExamInformationCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return this.getiExamInformationDAO().searchExamInformationCount(searchMap);
	}

}
