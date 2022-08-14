package enfo.crm.intrust;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustBusiBean;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.LocalUtilis;
import enfo.crm.tools.Utility;
import enfo.crm.vo.TreeVO;
import net.sf.json.JSONArray;

@Component(value="intrustCapitalType")
@Scope("prototype")
public class IntrustCapitalTypeBean extends IntrustBusiBean implements IntrustCapitalTypeLocal {

	 //增加、修改、删除 输入set方法
    private String caption;

    private Integer serial_no;

    private Integer bottom_flag;

    private Integer parent_id;

    private Integer input_man;

    private Integer bookcode;

    private Integer zc_flag;

    //private Integer book_code; //上面已经有一个
    private String zclb_bh;

    private String parent_caption;
    
    private String zywlb;
    
    private String dywlb;

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#save(java.lang.Integer)
	 */
    @Override
	public void save(Integer input_man) throws Exception {
        validate();
        Object[] params = new Object[5];
        params[0] = serial_no;
        params[1] = caption;
        params[2] = input_man;
        params[3] = zywlb;
        params[4] = dywlb;
        try {
            super.update("{?=call SP_MODI_TCAPITALTYPE(?,?,?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("资产类别添加失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#append(java.lang.Integer)
	 */
    @Override
	public void append(Integer input_man) throws Exception {
        validate();
        Object[] params = new Object[8];
        params[0] = Utility.parseInt(bookcode, new Integer(1));
        params[1] = Utility.parseInt(zc_flag, new Integer(1));
        params[2] = zclb_bh;
        params[3] = caption;
        params[4] = Utility.parseInt(parent_id, new Integer(0));
        params[5] = input_man;
        params[6] = zywlb;
        params[7] = dywlb;
        try {
        	serial_no = (Integer)super.cudEx("{?=call SP_ADD_TCAPITALTYPE(?,?,?,?,?,?,?,?,?)}", params,10,Types.INTEGER);
        } catch (Exception e) {
            throw new BusiException("资产类别修改失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#delete(java.lang.Integer)
	 */
    @Override
	public void delete(Integer input_man) throws Exception {
        validate();

        Object[] params = new Object[2];
        params[0] = serial_no;
        params[1] = input_man;
        try {
            super.update("{?=call SP_DEL_TCAPITALTYPE(?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("资产类别删除失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#load(java.lang.Integer)
	 */
    @Override
	public void load(Integer input_man) throws Exception {
        Object[] params = new Object[6];
        params[0] = bookcode;
        params[1] = serial_no;
        params[2] = caption;
        params[3] = Utility.parseInt(parent_id, new Integer(0));
        params[4] = Utility.parseInt(bottom_flag, new Integer(0));
        params[5] = input_man;
        super.query("{call SP_QUERY_TCAPITALTYPE(?,?,?,?,?,?)}", params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getNext()
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
            zclb_bh = rowset.getString("ZCLB_BH");
            zc_flag = (Integer) rowset.getObject("ZC_FLAG");
            zywlb = rowset.getString("ZYWLB");
            dywlb = rowset.getString("DYWLB");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#listTree(java.lang.Integer)
	 */
    @Override
	public void listTree(Integer serial_no) throws Exception {
        String strSQLtemp = "{call SP_QUERY_TCAPITALTYPE_TREE(?,?,?,?)}";

        Connection conn = IntrustDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(strSQLtemp,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        if (bookcode != null)
            stmt.setInt(1, bookcode.intValue());
        else
            stmt.setInt(1, 1);
        if (serial_no != null)
            stmt.setInt(2, serial_no.intValue());
        else
            stmt.setInt(2, 0);
        if (zc_flag != null)
            stmt.setInt(3, zc_flag.intValue());
        else
            stmt.setInt(3, 0);

        if (input_man != null)
            stmt.setInt(4, input_man.intValue());
        else
            stmt.setInt(4, 0);

        ResultSet rslist = stmt.executeQuery();
        try {
            rowset.close();
            rowset.populate(rslist);
        } finally {
            rslist.close();
            stmt.close();
            conn.close();
        }
    }
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#queryCapitaltypeJosn(java.lang.Integer, java.util.Locale)
	 */
	@Override
	public String queryCapitaltypeJosn(Integer serial_no,java.util.Locale clientLocale) throws Exception{
		List rsList = new ArrayList();
		
		TreeVO nodep = new TreeVO();
		//主节点设置
		nodep.setId(new Integer(0));
		nodep.setName(LocalUtilis.language("intrsut.home.assetclassess",clientLocale));//资产类别
		nodep.setParentId(new Integer(0));
		nodep.setOpen(true);
		nodep.setZclb_bh("");
		nodep.setZc_flag(new Integer(0));
		nodep.setBottom_flag(new Integer(0));
		nodep.setCaption(LocalUtilis.language("intrsut.home.assetclassess",clientLocale));
		rsList.add(nodep);
		
		recursive(nodep.getNodes(), new Integer(0));
		
		String json  = JSONArray.fromObject(rsList).toString();
		System.out.println(">>"+json);
		return json;
	}

	//递归List集合组合节点信息
	private void recursive(List dataList,Integer parentId) throws Exception{
		this.listTree(parentId);
		TreeVO tnode = null;
		while(super.getNext()){
			tnode = new TreeVO();
			tnode.setId(Utility.parseInt(new Integer(rowset.getInt("SERIAL_NO")),new Integer(0)));
			tnode.setName(Utility.trimNull(Utility.trimNull(rowset.getObject("ZCLB_BH"))+"-"+rowset.getString("CAPTION")));
			tnode.setParentId(Utility.parseInt(new Integer(rowset.getInt("PARENT_ID")),new Integer(0)));
			tnode.setOpen(false);
			tnode.setZclb_bh(Utility.trimNull(rowset.getString("ZCLB_BH")));
			tnode.setZc_flag(Utility.parseInt(new Integer(rowset.getInt("ZC_FLAG")),new Integer(0)));
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
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getBookcode()
	 */
    @Override
	public Integer getBookcode() {
        return bookcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setBookcode(java.lang.Integer)
	 */
    @Override
	public void setBookcode(Integer bookcode) {
        this.bookcode = bookcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getBottom_flag()
	 */
    @Override
	public Integer getBottom_flag() {
        return bottom_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setBottom_flag(java.lang.Integer)
	 */
    @Override
	public void setBottom_flag(Integer bottom_flag) {
        this.bottom_flag = bottom_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getCaption()
	 */
    @Override
	public String getCaption() {
        return caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setCaption(java.lang.String)
	 */
    @Override
	public void setCaption(String caption) {
        this.caption = caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getInput_man()
	 */
    @Override
	public Integer getInput_man() {
        return input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setInput_man(java.lang.Integer)
	 */
    @Override
	public void setInput_man(Integer input_man) {
        this.input_man = input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getParent_caption()
	 */
    @Override
	public String getParent_caption() {
        return parent_caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setParent_caption(java.lang.String)
	 */
    @Override
	public void setParent_caption(String parent_caption) {
        this.parent_caption = parent_caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getParent_id()
	 */
    @Override
	public Integer getParent_id() {
        return parent_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setParent_id(java.lang.Integer)
	 */
    @Override
	public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getSerial_no()
	 */
    @Override
	public Integer getSerial_no() {
        return serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setSerial_no(java.lang.Integer)
	 */
    @Override
	public void setSerial_no(Integer serial_no) {
        this.serial_no = serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getZc_flag()
	 */
    @Override
	public Integer getZc_flag() {
        return zc_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setZc_flag(java.lang.Integer)
	 */
    @Override
	public void setZc_flag(Integer zc_flag) {
        this.zc_flag = zc_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getZclb_bh()
	 */
    @Override
	public String getZclb_bh() {
        return zclb_bh;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setZclb_bh(java.lang.String)
	 */
    @Override
	public void setZclb_bh(String zclb_bh) {
        this.zclb_bh = zclb_bh;
    }
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getDywlb()
	 */
	@Override
	public String getDywlb() {
		return dywlb;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setDywlb(java.lang.String)
	 */
	@Override
	public void setDywlb(String dywlb) {
		this.dywlb = dywlb;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#getZywlb()
	 */
	@Override
	public String getZywlb() {
		return zywlb;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalTypeLocal#setZywlb(java.lang.String)
	 */
	@Override
	public void setZywlb(String zywlb) {
		this.zywlb = zywlb;
	}
}