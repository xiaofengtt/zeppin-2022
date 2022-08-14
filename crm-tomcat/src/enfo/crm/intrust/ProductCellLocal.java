package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.ProductCellVO;

public interface ProductCellLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRODUCT_CELL_TREE INT,
	 * @IN_CELL_ID INT = 0,
	 * @IN_CELL_CODE VARCHAR(10) = '',
	 * @IN_INPUT_MAN INT = NULL
	 * @IN_CELL_NAME NVARCHAR(60)
	 */
	List listTree(ProductCellVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param book_code
	 * @param cell_id
	 * @param cell_code
	 * @param input_man
	 * @param cell_name
	 * @return
	 * @throws Exception
	 */
	String getSubTree(Integer book_code, Integer cell_id, String cell_code, Integer input_man, String cell_name)
			throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 获得json数据(说明：选择节点时再查询该节点下的所有子节点)
	 * @return
	 * @throws Exception
	 */
	String querySubJectJosn(Integer book_code, Integer cell_id, String cell_code, Integer input_man, String cell_name)
			throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 获得json数据(说明：选择节点时再查询该节点下的所有子节点)
	 * @return
	 * @throws Exception
	 */
	String querySubJectJosn2(Integer book_code, Integer cell_id, String cell_code, Integer input_man, String cell_name)
			throws Exception;

}