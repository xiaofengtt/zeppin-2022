package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Information;

public interface IInformationService {

	/**
	 * 获取by Id
	 * @param id
	 * @return
	 */
	public Information getInformationById(Integer id);
	
	/**
	 * 添加资讯
	 * 
	 * @param information
	 */
	public void addInformation(Information information);

	/**
	 * 删除
	 * 
	 * @param information
	 */
	public void deleteInformation(Information information);

	/**
	 * 更新
	 * 
	 * @param information
	 */
	public void updateInformation(Information information);

	/**
	 * 
	 * @param map
	 * @return
	 */
	public int getInformationCount(Map<String, Object> map);

	/**
	 * 
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Information> getInformations(Map<String, Object> map, String sort, int offset, int length);

}
