package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ProductVO;
import enfo.crm.vo.WikiSearchVO;

public interface ProductInfoReposLocal extends IBusiExLocal {

	/**
	 *  @ejb.interface-method view-type = "local"
	 * 
	 * 查询产品记录
	 * 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList productInforeposList(ProductVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * 
	 * 查询产品记录
	 * 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList preProductList(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 知识库产品查询
	 *  
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList searchAll(WikiSearchVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	* @ejb.interface-method view-type = "local"
	* 
	* 搜索产品
	* 
	* @param vo
	* @return
	* @throws BusiException
	*/
	List productList(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询产品信息
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List listBySql(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 添加编辑产品
	 * 
	 * @param vo
	 * @throws Exception
	 */
	Integer setPreProduct(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除产品信息
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void delete(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 绑定产品
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void bindProduct(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 取关键字云
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getCloudKeyword() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询相关产品
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List relatedList(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 审核信息
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void checkQuota(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 录入产品推介资料
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void editIntroMaterial(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询产品的推介资料
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryIntroMaterial(ProductVO vo) throws BusiException;

}