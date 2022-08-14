package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.GradeIndexVO;

public interface GradeIndexLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ���ֱ�׼-��ҳ��ʾ
	 * 
	 * <pre>
	 * SP_QUERY_TGRADEINDE
	 * </pre>
	 * 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return pageList
	 */
	IPageList queryGradeIndex(GradeIndexVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ���ֱ�׼-������¼
	 * 
	 * <pre>
	 * SP_QUERY_TGRADEINDE
	 * </pre>
	 * 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return list
	 */
	List queryGradeIndex(GradeIndexVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ������ֱ�׼
	 * 
	 * <pre>
	 * SP_ADD_TGRADEINDEX
	 * &lt;pre&gt;
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	void appendGradeIndex(GradeIndexVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �޸����ֱ�׼
	 * 
	 * <pre>
	 * SP_ADD_TGRADEINDEX
	 * &lt;pre&gt;
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	void modiGradeIndex(GradeIndexVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" ɾ�����ֱ�׼
	 * 
	 * <pre>
	 * SP_DEL_TGRADEINDEX
	 * &lt;pre&gt;
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	void delGradeIndex(GradeIndexVO vo) throws BusiException;

}