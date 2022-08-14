package enfo.crm.intrust;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.IntrustBusiExBean;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ProductCellVO;
import enfo.crm.vo.TreeVO;
import net.sf.json.JSONArray;

@Component(value="productCell")
public class ProductCellBean extends IntrustBusiExBean implements ProductCellLocal {

	   /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductCellLocal#listTree(enfo.crm.vo.ProductCellVO)
	 */
    @Override
	public List listTree(ProductCellVO vo) throws Exception {
        Object[] param = new Object[5];
        param[0] = vo.getBook_code();
        param[1] = Utility.parseInt(vo.getCell_id(), new Integer(0));
        param[2] = vo.getCell_code();
        param[3] = vo.getInput_man();
        param[4] = vo.getCell_name();
        return super.listBySql("{call SP_QUERY_TPRODUCT_CELL_TREE (?,?,?,?,?)}", param);
    }
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductCellLocal#getSubTree(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String)
	 */
    @Override
	public String getSubTree(Integer book_code, Integer cell_id,
            String cell_code, Integer input_man, String cell_name)
            throws Exception {
        StringBuffer sb = new StringBuffer(90000);
        ProductCellVO vo = new ProductCellVO();
        sb
          .append("<TreeNode NodeId=\"msdnlib587\" Href=\"/nhp/default.asp?contentid=28000519\" Title=\".NET Development\" ParentXmlSrc=\"top.xml\" NodeXmlSrc=\"item.jsp\">");
        vo.setCell_id(cell_id);
        vo.setCell_code(cell_code);
        vo.setBook_code(book_code);
        vo.setInput_man(input_man);
        vo.setCell_name(cell_name);
        List list = listTree(vo);
       for(int i=0;i<list.size();i++){
    	   	Map map = (Map)list.get(i);
            if (Utility.parseInt(Utility.trimNull(map.get("CELL_TYPE")),0) == 2) //如果是组合
            {
                sb.append("<TreeNode NodeId=\"1\" Href=\"#");
                sb.append(Utility.parseInt(Utility.trimNull(map.get("CELL_ID")),0));
                sb.append("α");
                sb.append(Utility.trimNull(map.get("CELL_CODE")) + " - " + Utility.trimNull(map.get("CELL_NAME")));
                sb.append("ε\" Title=\"");
                sb.append(Utility.trimNull(map.get("CELL_CODE")) + " - " + Utility.trimNull(map.get("CELL_NAME")));
                sb.append("\" NodeXmlSrc=\"item.jsp?cell=" + Utility.parseInt(Utility.trimNull(map.get("CELL_ID")),0)
                        + "$" + Utility.trimNull(map.get("CELL_CODE")));//这里必须这样写，在item里用数组来处理
                sb.append("\"></TreeNode>");
            } else if (Utility.parseInt(Utility.trimNull(map.get("CELL_TYPE")),0) == 1) {
                sb.append("<TreeNode NodeId=\"1\" Href=\"#");
                sb.append(Utility.parseInt(Utility.trimNull(map.get("CELL_ID")),0));
                sb.append("α");
                sb.append(Utility.trimNull(map.get("CELL_CODE")) + " - " + Utility.trimNull(map.get("CELL_NAME")));
                sb.append("ε\" Title=\"");
                sb.append(Utility.trimNull(map.get("CELL_CODE")) + " - " + Utility.trimNull(map.get("CELL_NAME")));
                sb.append("\"></TreeNode>");/**/

            }
        }
        sb.append("</TreeNode>");
        return sb.toString();
    }
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductCellLocal#querySubJectJosn(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String)
	 */
    @Override
	public String querySubJectJosn(Integer book_code, Integer cell_id,
            String cell_code, Integer input_man, String cell_name) throws Exception{
        List treeList = new ArrayList();
        TreeVO node = null;
        ProductCellVO vo = new ProductCellVO();
        if (cell_id.intValue() == -1){
            node = new TreeVO();
            node.setId(new Integer(0));
            node.setCode("");
            node.setName("选择产品单元");
            node.setParentId(new Integer("-1"));
            node.setIsParent(true);
            treeList.add(node);
        }else if(cell_id.intValue() == 0){
            vo.setCell_id(cell_id);
            vo.setCell_code(cell_code);
            vo.setBook_code(book_code);
            vo.setInput_man(input_man);
            vo.setCell_name(cell_name);
            List list = listTree(vo);
            for (int i=0; i<list.size(); i++) { 
                Map map = (Map)list.get(i);
                if (((Integer)map.get("CELL_TYPE")).intValue() == 2){
                    node = new TreeVO();
                    node.setId((Integer)map.get("CELL_ID"));
                    node.setCode((String)map.get("CELL_CODE"));
                    node.setName(map.get("CELL_CODE")+" - "+map.get("CELL_NAME"));
                    node.setParentId(cell_id);
                    node.setIsParent(true);
                    treeList.add(node);
                }
            }
        }else{       
            vo.setCell_id(cell_id);
            vo.setCell_code(cell_code);
            vo.setBook_code(book_code);
            vo.setInput_man(input_man);
            vo.setCell_name(cell_name);
            List list = listTree(vo);
            for (int i=0; i<list.size(); i++) { 
                Map map = (Map)list.get(i);
                node = new TreeVO();
                node.setId((Integer)map.get("CELL_ID"));
                node.setCode((String)map.get("CELL_CODE"));
                node.setName(map.get("CELL_CODE")+" - "+map.get("CELL_NAME"));
                node.setParentId(cell_id);
                node.setIsParent(((Integer)map.get("CELL_TYPE")).intValue() == 2);                   
                treeList.add(node);                
            }
        }
        String json = JSONArray.fromObject(treeList).toString();
        return json;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductCellLocal#querySubJectJosn2(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String)
	 */
    @Override
	public String querySubJectJosn2(Integer book_code, Integer cell_id, String cell_code, Integer input_man, String cell_name) throws Exception{
        List treeList = new ArrayList();
        TreeVO node = null;        
        if (cell_id.intValue() == -1){
            node = new TreeVO();
            node.setId(new Integer(0));
            node.setCode("");
            node.setName("选择产品");
            node.setParentId(new Integer("-1"));
            node.setIsParent(true);
            treeList.add(node);
        }else{
            ProductCellVO vo = new ProductCellVO();
            vo.setCell_id(new Integer(0));
            vo.setCell_code(cell_code);
            vo.setBook_code(book_code);
            vo.setInput_man(input_man);
            vo.setCell_name(cell_name);
            List list = listTree(vo);
            for (int i=0; i<list.size(); i++) {
                Map map = (Map)list.get(i);
                node = new TreeVO();
                node.setId((Integer)map.get("CELL_ID"));
                node.setCode((String)map.get("CELL_CODE"));
                node.setName(map.get("CELL_CODE")+" - "+map.get("CELL_NAME"));
                node.setParentId(cell_id);
                if (((Integer)map.get("CELL_TYPE")).intValue() == 1){
                    node.setIsParent(false);
                    treeList.add(node);
                }
            }
        }
        String json = JSONArray.fromObject(treeList).toString();
        return json;
    }

}
