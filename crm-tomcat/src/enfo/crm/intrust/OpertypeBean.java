 
package enfo.crm.intrust;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.LocalUtilis;
import enfo.crm.tools.Utility;
import enfo.crm.vo.TreeVO;
import net.sf.json.JSONArray;

@Component(value="opertype")
@Scope("prototype")
public class OpertypeBean extends enfo.crm.dao.IntrustBusiBean implements OpertypeLocal {
	private static final String AddSQL = "{?=call SP_ADD_TCAPITALOPERTYPE(?,?,?,?,?)}";

    private static final String ModiSQL = "{?=call SP_MODI_TCAPITALOPERTYPE(?,?,?)}";

    private static final String DelSQL = "{?=call SP_DEL_TCAPITALOPERTYPE(?,?)}";

    private static final String QuerySQL = "{call SP_QUERY_TCAPITALOPERTYPE(?,?,?,?,?,?)}";

    //增加、修改、删除 输入set方法
    private String caption;

    private Integer serial_no;

    private Integer bottom_flag;

    private Integer parent_id;

    private Integer input_man;

    private Integer bookcode;

    private String parent_caption;

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#save(java.lang.Integer)
	 */
    @Override
	public void save(Integer input_man) throws Exception {
        Object[] params = new Object[3];
        params[0] = Utility.parseInt(serial_no, new Integer(0));
        params[1] = caption;
        params[2] = Utility.parseInt(input_man, new Integer(0));
        try {
            super.update(ModiSQL, params);
        } catch (Exception e) {
            throw new BusiException("业务类别修改失败:" + e.getMessage());
        }
    } 

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#append(java.lang.Integer)
	 */
    @Override
	public void append(Integer input_man) throws Exception {
        Object[] params = new Object[4];
        params[0] = bookcode;
        params[1] = caption;
        params[2] = Utility.parseInt(parent_id, new Integer(0));
        params[3] = Utility.parseInt(input_man, new Integer(0));
        try {
        	serial_no = (Integer)super.cudEx(AddSQL, params,6,Types.INTEGER);
        } catch (Exception e) {
            throw new BusiException("业务类别添加失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#delete(java.lang.Integer)
	 */
    @Override
	public void delete(Integer input_man) throws Exception {
        Object[] params = new Object[2];
        params[0] = Utility.parseInt(serial_no, new Integer(0));
        params[1] = Utility.parseInt(input_man, new Integer(0));
        try {
            super.update(DelSQL, params);
        } catch (Exception e) {
            throw new BusiException("业务类别删除失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#load(java.lang.Integer)
	 */
    @Override
	public void load(Integer input_man) throws Exception {
        Object[] params = new Object[6];
        params[0] = bookcode;
        params[1] = Utility.parseInt(serial_no, new Integer(0));
        params[2] = caption;
        params[3] = Utility.parseInt(parent_id, new Integer(0));
        params[4] = Utility.parseInt(bottom_flag, new Integer(0));
        params[5] = Utility.parseInt(input_man, new Integer(0));
        super.query(QuerySQL, params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#getNext()
	 */
    @Override
	public boolean getNext() throws Exception {
        boolean b = super.getNext();
        if (b) {
            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
            caption = rowset.getString("CAPTION");
            bottom_flag = (Integer) rowset.getObject("BOTTOM_FLAG");
            parent_id = new Integer(rowset.getInt("PARENT_ID"));
            parent_caption = rowset.getString("PARENT_CAPTION");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#listTree(java.lang.Integer)
	 */
    @Override
	public void listTree(Integer serial_no) throws Exception {
        Object[] params = new Object[1];
        params[0] = serial_no;
        super.query("{call SP_QUERY_TCAPITALOPERTYPE_TREE (?)}", params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#queryCapitaltypeJosn(java.lang.Integer, java.util.Locale)
	 */
	@Override
	public String queryCapitaltypeJosn(Integer serial_no,java.util.Locale clientLocale) throws Exception{
		List rsList = new ArrayList();
		
		TreeVO nodep = new TreeVO();
		//主节点设置
		nodep.setId(new Integer(0));
		nodep.setName(LocalUtilis.language("class.busiName",clientLocale));//资产类别
		nodep.setParentId(new Integer(0));
		nodep.setOpen(true);
		nodep.setBottom_flag(new Integer(0));
		nodep.setCaption(LocalUtilis.language("class.busiName",clientLocale));
		rsList.add(nodep);
		
		recursive(nodep.getNodes(), new Integer(0));
		
		String json  = JSONArray.fromObject(rsList).toString();
		return json;
	}

	//递归List集合组合节点信息
	private void recursive(List dataList,Integer parentId) throws Exception{
		this.listTree(parentId);
		TreeVO tnode = null;
		while(super.getNext()){
			tnode = new TreeVO();
			tnode.setId(Utility.parseInt(new Integer(rowset.getInt("SERIAL_NO")),new Integer(0)));
			tnode.setName(Utility.trimNull(rowset.getString("CAPTION")));
			tnode.setParentId(Utility.parseInt(new Integer(rowset.getInt("PARENT_ID")),new Integer(0)));
			tnode.setOpen(false);
			tnode.setBottom_flag(Utility.parseInt(new Integer(rowset.getInt("BOTTOM_FLAG")),new Integer(0)));
			tnode.setCaption(Utility.trimNull(rowset.getString("CAPTION")));
			dataList.add(tnode);
		}
		
		for (int i = 0; i < dataList.size(); i++) {
			TreeVO node = (TreeVO) dataList.get(i);
			recursive(node.getNodes(), node.getId());
		}
	}

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#getBottom_flag()
	 */
    @Override
	public Integer getBottom_flag() {
        return bottom_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#setBottom_flag(java.lang.Integer)
	 */
    @Override
	public void setBottom_flag(Integer bottom_flag) {
        this.bottom_flag = bottom_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#getCaption()
	 */
    @Override
	public String getCaption() {
        return caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#setCaption(java.lang.String)
	 */
    @Override
	public void setCaption(String caption) {
        this.caption = caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#getParent_caption()
	 */
    @Override
	public String getParent_caption() {
        return parent_caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#setParent_caption(java.lang.String)
	 */
    @Override
	public void setParent_caption(String parent_caption) {
        this.parent_caption = parent_caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#getParent_id()
	 */
    @Override
	public Integer getParent_id() {
        return parent_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#setParent_id(java.lang.Integer)
	 */
    @Override
	public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#getSerial_no()
	 */
    @Override
	public Integer getSerial_no() {
        return serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#setSerial_no(java.lang.Integer)
	 */
    @Override
	public void setSerial_no(Integer serial_no) { 
        this.serial_no = serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#setBookcode(java.lang.Integer)
	 */
    @Override
	public void setBookcode(Integer bookcode) {
        this.bookcode = bookcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.OpertypeLocal#setInput_man(java.lang.Integer)
	 */
    @Override
	public void setInput_man(Integer input_man) {
        this.input_man = input_man;
    }
}