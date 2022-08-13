package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IInformationDAO;
import cn.zeppin.entity.Information;
import cn.zeppin.service.api.IInformationService;
import cn.zeppin.utility.Dictionary;

public class InformationService implements IInformationService {

	private IInformationDAO informationDAO;

	public IInformationDAO getInformationDAO() {
		return informationDAO;
	}

	public void setInformationDAO(IInformationDAO informationDAO) {
		this.informationDAO = informationDAO;
	}

	/**
	 * 获取by Id
	 * 
	 * @param id
	 * @return
	 */
	public Information getInformationById(Integer id) {
		return this.getInformationDAO().get(id);
	}

	/**
	 * 添加资讯
	 * 
	 * @param information
	 */
	public void addInformation(Information information) {
		this.getInformationDAO().save(information);
	}

	/**
	 * 删除
	 * 
	 * @param information
	 */
	public void deleteInformation(Information information) {
		information.setStatus(Dictionary.INFORMATION_STATUS_CLOSED);
		this.getInformationDAO().update(information);
	}

	/**
	 * 更新
	 * 
	 * @param information
	 */
	public void updateInformation(Information information) {
		this.getInformationDAO().update(information);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public int getInformationCount(Map<String, Object> map) {
		return this.getInformationDAO().getInformationCount(map);
	}

	/**
	 * 
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Information> getInformations(Map<String, Object> map, String sort, int offset, int length) {
		return this.getInformationDAO().getInformations(map, sort, offset, length);
	}

}
