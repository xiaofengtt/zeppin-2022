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
	 * ��ѯ��Ʒ��¼
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
	 * ��ѯ��Ʒ��¼
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
	 * ֪ʶ���Ʒ��ѯ
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
	* ������Ʒ
	* 
	* @param vo
	* @return
	* @throws BusiException
	*/
	List productList(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��Ʒ��Ϣ
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List listBySql(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ӱ༭��Ʒ
	 * 
	 * @param vo
	 * @throws Exception
	 */
	Integer setPreProduct(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ɾ����Ʒ��Ϣ
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void delete(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �󶨲�Ʒ
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void bindProduct(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ȡ�ؼ�����
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getCloudKeyword() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��ز�Ʒ
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List relatedList(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �����Ϣ
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void checkQuota(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ¼���Ʒ�ƽ�����
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void editIntroMaterial(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��Ʒ���ƽ�����
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryIntroMaterial(ProductVO vo) throws BusiException;

}