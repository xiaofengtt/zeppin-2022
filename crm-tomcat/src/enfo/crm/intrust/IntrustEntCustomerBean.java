package enfo.crm.intrust;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustBusiBean;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.EntRelationVO;

@Component(value="intrustEntCustomer")
@Scope("prototype")
public class IntrustEntCustomerBean extends IntrustBusiBean implements IntrustEntCustomerLocal {

	private java.lang.Integer cust_id;

    private java.lang.String cust_name;

    private java.lang.String cust_code;

    private java.lang.String card_id;

    private java.lang.String bank_id;

    private java.lang.String bank_name;

    private java.lang.String bank_acct;

    private java.lang.String reg_address;

    private java.lang.String reg_postcode;

    private java.lang.String link_man;

    private java.lang.String address;

    private java.lang.String postcode;

    private java.lang.String telphone;

    private java.lang.String fax;

    private java.lang.String email;

    private java.lang.String ent_type;

    private java.lang.String ent_type_name;

    private java.lang.String summary;

    private java.lang.Integer input_man;

    private java.sql.Timestamp input_time;

    private java.lang.Integer modi_man;

    private java.sql.Timestamp last_modi_time;

    private Integer product_id;

    private String product_name;

    private java.lang.String bank_sub_name;

    private Integer cust_type;

    private Integer sex;

    private java.lang.String card_type;

    private java.lang.String card_type_name;

    private java.lang.String credit_level;

    private java.lang.String credit_level_name;

    private java.lang.String card_code;

    private Integer jt_flag; //1是集团，2不是

    //add by jinxr 2007/4/26
    private Integer is_link; //1 关联方 2 非关联方

    //add by jinxr 2007/8/7
    private String busi_name;

    private String r_type_name;

    private Integer contract_id;

    private String contract_sub_bh;

    private Integer start_date;

    private Integer end_date;

    private java.math.BigDecimal ht_money;

    private java.math.BigDecimal cw_money;

    private java.lang.Integer serial_no;

    private java.lang.Integer book_code;

    private java.lang.String field_name;

    private java.lang.String field_cn_name;

    private java.lang.String old_field_info;

    private java.lang.String new_field_info;

    private java.lang.Integer jt_cust_id;

    //2008-09-25
    private Integer from_cust_id;//源客户ID

    private Integer to_cust_id;//目的客户ID

    /**
     * add by tsg 2007-09-30 关联类型
     *  
     */
    private java.lang.Integer link_type;

    /**
     * add by tsg 2007-09-30 股东投资入股信托公司金额
     *  
     */

    private BigDecimal link_gd_money;

    /**
     * add by tsg 2001-11-02 客户类型
     */

    private java.lang.String voc_type;//行业类型

    private java.lang.String voc_type_name;

    //add by zhangmy 20100304 增加企业信息字段
    private String workaddress;//目前办公地点

    private String legal_person;//法定代表人

    private BigDecimal contri_capital;//实收资本

    private BigDecimal regist_capital;//注册资本

    private BigDecimal total_seset;//资产总额

    private String incorporation_date;//成立时间

    private String tax_registration_no;//税务登记号

    private BigDecimal net_asset;//净资产

    private Integer annual_inspection;//是否年检:1是,2否

    private Integer forms_type;//财务报表类型:1审计类,2非审计类

    private BigDecimal sales_income;//上年销售收入

    private String funding_source;//项目来源

    private String strong_stockholder;//企业实际控制人

    private BigDecimal ratio;//占股比例

    private Integer require;//企业需求 SELECT TYPE_VALUE FROM TDICTPARAM WHERE

    // TYPE_ID = 5116

    private String item_location;//项目所在区域

    private String scale_term;//项目规模和期限

    private BigDecimal financial_cost;//财务成本

    private String business_scope;//主营业务

    private String guarantee_mode;//企业拟提供的担保方式
    
    private String gov_prov_regioal;//
    private String gov_prov_regioal_name;//
    private String gov_regioal_name;
    private String gov_regioal;//
    private String type_code;//
    private String type_name;//
    //20101026新增字段
    private String jkr_type;//借款人特征(5102)
    private String jkr_type_name;
    private String country = "5105CHN";//国别（5105）
    private String country_name;
    private String jkr_type2;//借款人性质(5104)
    private String jkr_type2_name;
	private String reg_no;
	private Integer reg_date;
	private Integer license_end_date;
	private String land_tax_no;
	private String country_tax_no;
	private Integer valid_period; //期限

	private Integer period_unit; //期限单位
	
	private Integer public_flag;//1.上市公司 2.非上市公司
	private String seid_id; //上市市场
	private String stock_code; //上市代码
	private String stock_name;//上市名称
	private Integer complete_flag; //资料完整
	
	private String fz_total; //负债总额
    protected void validate() throws BusiException { 
        super.validate();
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#deleteEntCustomer()
	 */
    @Override
	public void deleteEntCustomer() throws BusiException {
        Object[] oparams = new Object[2];
        oparams[0] = Utility.parseInt(cust_id, new Integer(0));
        oparams[1] = Utility.parseInt(input_man, new Integer(0));
        try {
            super.delete("{?=call SP_DEL_TENTCUSTINFO(?,?)}", oparams);
        } catch (Exception e) {
            throw new BusiException("企业客户删除失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#updateEntCustomer()
	 */
    @Override
	public void updateEntCustomer() throws BusiException {
        Object[] params = new Object[65];
        params[0] = Utility.parseInt(cust_id, new Integer(0));
        params[1] = cust_name;
        params[2] = cust_code;
        params[3] = card_id;
        params[4] = bank_id;
        params[5] = bank_sub_name;
        params[6] = bank_acct;
        params[7] = reg_address;
        params[8] = link_man;
        params[9] = address;
        params[10] = telphone;
        params[11] = fax;
        params[12] = email;
        params[13] = ent_type;
        params[14] = summary;
        params[15] = reg_postcode;
        params[16] = postcode;
        params[17] = Utility.parseInt(sex, new Integer(0));
        params[18] = Utility.parseInt(cust_type, new Integer(0));
        params[19] = card_code;
        params[20] = card_type;
        params[21] = credit_level;
        params[22] = Utility.parseInt(jt_flag, new Integer(0));
        params[23] = Utility.parseInt(is_link, new Integer(2));
        params[24] = Utility.parseInt(link_type, new Integer(0));
        params[25] = Utility.parseBigDecimal(link_gd_money, new BigDecimal(0));
        params[26] = Utility.parseInt(input_man, new Integer(0));
        params[27] = Utility.trimNull(voc_type);
        params[28] = Utility.parseInt(jt_cust_id, new Integer(0));

        params[29] = workaddress;//目前办公地点
        params[30] = legal_person;//法定代表人
        params[31] = contri_capital;//实收资本
        params[32] = regist_capital;//注册资本
        params[33] = total_seset;//资产总额
        params[34] = incorporation_date;//成立时间
        params[35] = tax_registration_no;//税务登记号
        params[36] = net_asset;//净资产
        params[37] = annual_inspection;//是否年检:1是,2否
        params[38] = forms_type;//财务报表类型:1审计类
        params[39] = sales_income;//上年销售收入
        params[40] = funding_source;//项目来源
        params[41] = strong_stockholder;//企业实际控制人
        params[42] = ratio;//占股比例
        params[43] = require;//企业需求 SELECT TYPE
        params[44] = item_location;//项目所在区域
        params[45] = scale_term;//项目规模和期限
        params[46] = financial_cost;//财务成本
        params[47] = business_scope;//主营业务
        params[48] = guarantee_mode;//企业拟提供的担保方式
        params[49] = gov_prov_regioal;//
        params[50] = gov_regioal;//
        params[51] = type_code;//
        params[52] = jkr_type;//
        params[53] = jkr_type_name;//
        params[54] = country;//
        params[55] = country_name;//
        params[56] = jkr_type2;//
        params[57] = jkr_type2_name;//
        params[58] = Utility.parseInt(valid_period,null);
        params[59] = Utility.parseInt(period_unit,null);
        
        params[60] = Utility.parseInt(public_flag,null);
        params[61] = seid_id;
        params[62] = stock_code;
        params[63] = stock_name;
        params[64] = Utility.parseInt(complete_flag, new Integer(2));
        try {
            super.update("{?=call SP_MODI_TENTCUSTINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                            params);
        } catch (Exception e) {
            throw new BusiException("企业客户保存失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#addNewEntCustomer()
	 */
    @Override
	public void addNewEntCustomer() throws BusiException {
        Object[] params = new Object[64];
        params[0] = Utility.parseInt(book, new Integer(0));
        params[1] = cust_name;
        params[2] = Utility.trimNull(cust_code);
        params[3] = card_id;
        params[4] = bank_id;
        params[5] = bank_sub_name;
        params[6] = bank_acct;
        params[7] = reg_address;
        params[8] = link_man;
        params[9] = address;
        params[10] = telphone;
        params[11] = fax;
        params[12] = email;
        params[13] = ent_type;
        params[14] = summary;
        params[15] = reg_postcode;
        params[16] = postcode;
        params[17] = Utility.parseInt(sex, new Integer(0));
        params[18] = cust_type;
        params[19] = card_code;
        params[20] = card_type;
        params[21] = credit_level;
        params[22] = Utility.parseInt(jt_flag, new Integer(0));
        params[23] = Utility.parseInt(is_link, new Integer(2));
        params[24] = Utility.parseInt(link_type, new Integer(0));
        params[25] = Utility.parseBigDecimal(link_gd_money, new BigDecimal(0));
        params[26] = Utility.parseInt(input_man, new Integer(0));
        params[27] = Utility.trimNull(voc_type);

        params[28] = workaddress;//目前办公地点
        params[29] = legal_person;//法定代表人
        params[30] = contri_capital;//实收资本
        params[31] = regist_capital;//注册资本
        params[32] = total_seset;//资产总额
        params[33] = incorporation_date;//成立时间
        params[34] = tax_registration_no;//税务登记号
        params[35] = net_asset;//净资产
        params[36] = annual_inspection;//是否年检:1是,2否
        params[37] = forms_type;//财务报表类型:1审计类,2非审计类
        params[38] = sales_income;//上年销售收入
        params[39] = funding_source;//项目来源
        params[40] = strong_stockholder;//企业实际控制人
        params[41] = ratio;//占股比例
        params[42] = require;//企业需求 SELECT TYPE_VALUE FROM TDICTPARAM WHERE
        // TYPE_ID = 5116
        params[43] = item_location;//项目所在区域
        params[44] = scale_term;//项目规模和期限
        params[45] = financial_cost;//财务成本
        params[46] = business_scope;//主营业务
        params[47] = guarantee_mode;//企业拟提供的担保方式
        params[48] = gov_prov_regioal;//
        params[49] = gov_regioal;//
        params[50] = type_code;//
        params[51] = jkr_type;//
        params[52] = jkr_type_name;//
        params[53] = country;//
        params[54] = country_name;//
        params[55] = jkr_type2;//
        params[56] = jkr_type2_name;//
        params[57] = Utility.parseInt(valid_period,null);
        params[58] = Utility.parseInt(period_unit,null);
        
        params[59] = Utility.parseInt(public_flag,null);
        params[60] = seid_id;
        params[61] = stock_code;
        params[62] = stock_name;
        params[63] = Utility.parseInt(complete_flag, new Integer(2));
        try {
            cust_id = (Integer) super
                    .cudEx(
                            "{?=call SP_ADD_TENTCUSTINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                            params, 49, java.sql.Types.INTEGER);
        } catch (Exception e) {
            throw new BusiException("企业客户创建失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#list()
	 */
    @Override
	public void list() throws Exception {
        Object[] params = new Object[9];
        params[0] = Utility.parseInt(book, new Integer(0));
        params[1] = Utility.parseInt(cust_id, new Integer(0));
        params[2] = Utility.parseInt(cust_type, new Integer(0));
        ;
        params[3] = cust_name;
        params[4] = cust_code;
        params[5] = Utility.parseInt(is_link, new Integer(0));
        params[6] = Utility.parseInt(jt_flag, new Integer(0));
        params[7] = card_code;
        params[8] = complete_flag;
        
        super.query("{call SP_QUERY_TENTCUSTINFO (?,?,?,?,?,?,?,?,?)}", params);

    }    
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#load()
	 */
    @Override
	public void load() throws Exception {
        if (cust_id != null && cust_id.intValue() > 0) {
            list();
            getNext();
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getNext()
	 */
    @Override
	public boolean getNext() throws Exception {
        boolean b = super.getNext();
        if (b) {
            this.book = (Integer) rowset.getObject("BOOK_CODE");
            this.cust_id = new Integer(rowset.getInt("CUST_ID"));
            this.cust_name = rowset.getString("CUST_NAME");
            this.cust_code = rowset.getString("CUST_CODE");
            this.card_id = rowset.getString("CARD_ID");
            this.bank_id = rowset.getString("BANK_ID");
            this.bank_name = rowset.getString("BANK_NAME");
            this.bank_sub_name = rowset.getString("BANK_SUB_NAME");
            this.bank_acct = rowset.getString("BANK_ACCT");
            this.reg_address = rowset.getString("REG_ADDRESS");
            this.link_man = rowset.getString("LINK_MAN");
            this.address = rowset.getString("ADDRESS");
            this.telphone = rowset.getString("TELPHONE");
            this.fax = rowset.getString("FAX");
            this.email = rowset.getString("EMAIL");
            this.ent_type = rowset.getString("ENT_TYPE");
            this.ent_type_name = rowset.getString("ENT_TYPE_NAME");
            this.summary = rowset.getString("SUMMARY");
            this.input_man = (Integer) rowset.getObject("INPUT_MAN");
            this.input_time = rowset.getTimestamp("INPUT_TIME");
            this.last_modi_time = rowset.getTimestamp("LAST_MODI_TIME");
            this.modi_man = (Integer) rowset.getObject("MODI_MAN");
            this.reg_postcode = rowset.getString("REG_POSTCODE");
            this.postcode = rowset.getString("POSTCODE");
            this.sex = (Integer) rowset.getObject("SEX");
            this.cust_type = (Integer) rowset.getObject("CUST_TYPE");
            this.card_code = rowset.getString("CARD_CODE");
            this.card_type = rowset.getString("CARD_TYPE");
            this.card_type_name = rowset.getString("CARD_TYPE_NAME");
            this.credit_level = rowset.getString("CREDIT_LEVEL");
            this.credit_level_name = rowset.getString("CREDIT_LEVEL_NAME");
            this.jt_flag = (Integer) rowset.getObject("JT_FLAG");
            this.is_link = (Integer) rowset.getObject("IS_LINK");
            this.link_type = new Integer(rowset.getInt("LINK_TYPE"));
            this.link_gd_money = rowset.getBigDecimal("LINK_GD_MONEY");
            this.voc_type = rowset.getString("VOC_TYPE");
            this.voc_type_name = rowset.getString("VOC_TYPE_NAME");
            this.jt_cust_id = new Integer(rowset.getInt("JT_CUST_ID"));

            this.workaddress = rowset.getString("WORKADDRESS");//目前办公地点
            this.legal_person = rowset.getString("LEGAL_PERSON");//法定代表人
            this.contri_capital = rowset.getBigDecimal("CONTRI_CAPITAL");//实收资本
            this.regist_capital = rowset.getBigDecimal("REGIST_CAPITAL");//注册资本
            this.total_seset = rowset.getBigDecimal("TOTAL_SESET");//资产总额
            this.incorporation_date = rowset.getString("INCORPORATION_DATE");//成立时间
            this.tax_registration_no = rowset.getString("TAX_REGISTRATION_NO");//税务登记号
            this.net_asset = rowset.getBigDecimal("NET_ASSET");//净资产
            this.annual_inspection = (Integer) rowset
                    .getObject("ANNUAL_INSPECTION");//是否年检:1是,2否
            this.forms_type = (Integer) rowset.getObject("FORMS_TYPE");//财务报表类型:1审计类,2非审计类
            this.sales_income = rowset.getBigDecimal("SALES_INCOME");//上年销售收入
            this.funding_source = rowset.getString("FUNDING_SOURCE");//项目来源
            this.strong_stockholder = rowset.getString("STRONG_STOCKHOLDER");//企业实际控制人
            this.ratio = rowset.getBigDecimal("RATIO");//占股比例
            this.require = (Integer) rowset.getObject("REQUIRE");//企业需求 SELECT
            // TYPE_VALUE
            // FROM
            // TDICTPARAM
            // WHERE
            // TYPE_ID =
            // 5116
            this.item_location = rowset.getString("ITEM_LOCATION");//项目所在区域
            this.scale_term = rowset.getString("SCALE_TERM");//项目规模和期限
            this.financial_cost = rowset.getBigDecimal("FINANCIAL_COST");//财务成本
            this.business_scope = rowset.getString("BUSINESS_SCOPE");//主营业务
            this.guarantee_mode = rowset.getString("GUARANTEE_MODE");//企业拟提供的担保方式
            
            this.gov_prov_regioal = rowset.getString("GOV_PROV_REGIONAL");
            this.gov_regioal = rowset.getString("GOV_REGIONAL");
            this.type_code = rowset.getString("TYPE_CODE");
            this.type_name = rowset.getString("TYPE_NAME");
            this.gov_prov_regioal_name = rowset.getString("GOV_PROV_REGIONAL_NAME");
            this.gov_regioal_name = rowset.getString("GOV_REGIONAL_NAME");
            this.jkr_type_name = rowset.getString("JKR_TYPE_NAME");
            this.jkr_type = rowset.getString("JKR_TYPE");
            this.jkr_type2_name = rowset.getString("JKR_TYPE2_NAME");
            this.jkr_type2 = rowset.getString("JKR_TYPE2");
            this.country_name = rowset.getString("COUNTRY_NAME");
            this.country = rowset.getString("COUNTRY");
            this.valid_period = (Integer) rowset.getObject("VALID_PERIOD");
            this.period_unit = (Integer) rowset.getObject("PERIOD_UNIT");
            
            this.public_flag = (Integer) rowset.getObject("PUBLIC_FLAG");
			this.seid_id = rowset.getString("SEID_ID");
			this.stock_code = rowset.getString("STOCK_CODE");
			this.stock_name = rowset.getString("STOCK_NAME");
			this.complete_flag = Utility.parseInt(rowset.getString("COMPLETE_FLAG"), new Integer(2));
        }
        return b;
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getNextAllist()
	 */
    @Override
	public List getNextAllist() throws Exception {
        List list = new ArrayList();
        while (super.getNext()) {
            Map map = new HashMap();
            map.put("BOOK_CODE", rowset.getObject("BOOK_CODE"));
            map.put("CUST_ID", rowset.getObject("CUST_ID"));
            map.put("CUST_NAME", rowset.getObject("CUST_NAME"));
            map.put("CUST_CODE", rowset.getObject("CUST_CODE"));
            map.put("CARD_ID", rowset.getObject("CARD_ID"));
            map.put("BANK_ID", rowset.getObject("BANK_ID"));
            map.put("BANK_NAME", rowset.getObject("BANK_NAME"));
            map.put("BANK_SUB_NAME", rowset.getObject("BANK_SUB_NAME"));
            map.put("BANK_ACCT", rowset.getObject("BANK_ACCT"));
            map.put("REG_ADDRESS", rowset.getObject("REG_ADDRESS"));
            map.put("LINK_MAN", rowset.getObject("LINK_MAN"));
            map.put("ADDRESS", rowset.getObject("ADDRESS"));
            map.put("TELPHONE", rowset.getObject("TELPHONE"));
            map.put("FAX", rowset.getObject("FAX"));
            map.put("EMAIL", rowset.getObject("EMAIL"));
            map.put("ENT_TYPE", rowset.getObject("ENT_TYPE"));
            map.put("ENT_TYPE_NAME", rowset.getObject("ENT_TYPE_NAME"));
            map.put("SUMMARY", rowset.getObject("SUMMARY"));
            map.put("INPUT_MAN", rowset.getObject("INPUT_MAN"));
            map.put("INPUT_TIME", rowset.getObject("INPUT_TIME"));
            map.put("LAST_MODI_TIME", rowset.getObject("LAST_MODI_TIME"));
            map.put("MODI_MAN", rowset.getObject("MODI_MAN"));
            map.put("REG_POSTCODE", rowset.getObject("REG_POSTCODE"));
            map.put("POSTCODE", rowset.getObject("POSTCODE"));
            map.put("SEX", rowset.getObject("SEX"));
            map.put("CUST_TYPE", rowset.getObject("CUST_TYPE"));
            map.put("CARD_CODE", rowset.getObject("CARD_CODE"));
            map.put("CARD_TYPE", rowset.getObject("CARD_TYPE"));
            map.put("CARD_TYPE_NAME", rowset.getObject("CARD_TYPE_NAME"));
            map.put("CREDIT_LEVEL", rowset.getObject("CREDIT_LEVEL"));
            map.put("CREDIT_LEVEL_NAME", rowset.getObject("CREDIT_LEVEL_NAME"));
            map.put("JT_FLAG", rowset.getObject("JT_FLAG"));
            map.put("IS_LINK", rowset.getObject("IS_LINK"));
            map.put("LINK_TYPE", rowset.getObject("LINK_TYPE"));
            map.put("LINK_GD_MONEY", rowset.getObject("LINK_GD_MONEY"));
            map.put("VOC_TYPE", rowset.getObject("VOC_TYPE"));
            map.put("VOC_TYPE_NAME", rowset.getObject("VOC_TYPE_NAME"));
            map.put("JT_CUST_ID", rowset.getObject("JT_CUST_ID"));
            map.put("WORKADDRESS", rowset.getObject("WORKADDRESS"));
            map.put("LEGAL_PERSON", rowset.getObject("LEGAL_PERSON"));
            map.put("CONTRI_CAPITAL", rowset.getObject("CONTRI_CAPITAL"));
            map.put("REGIST_CAPITAL", rowset.getObject("REGIST_CAPITAL"));
            map.put("TOTAL_SESET", rowset.getObject("TOTAL_SESET"));
            map.put("INCORPORATION_DATE", rowset.getObject("INCORPORATION_DATE"));
            map.put("TAX_REGISTRATION_NO", rowset.getObject("TAX_REGISTRATION_NO"));
            map.put("NET_ASSET", rowset.getObject("NET_ASSET"));
            map.put("ANNUAL_INSPECTION", rowset.getObject("ANNUAL_INSPECTION"));
            map.put("FORMS_TYPE", rowset.getObject("FORMS_TYPE"));
            map.put("SALES_INCOME", rowset.getObject("SALES_INCOME"));
            map.put("FUNDING_SOURCE", rowset.getObject("FUNDING_SOURCE"));
            map.put("STRONG_STOCKHOLDER", rowset.getObject("STRONG_STOCKHOLDER"));
            map.put("RATIO", rowset.getObject("RATIO"));
            map.put("REQUIRE", rowset.getObject("REQUIRE"));
            map.put("ITEM_LOCATION", rowset.getObject("ITEM_LOCATION"));
            map.put("SCALE_TERM", rowset.getObject("SCALE_TERM"));
            map.put("FINANCIAL_COST", rowset.getObject("FINANCIAL_COST"));
            map.put("BUSINESS_SCOPE", rowset.getObject("BUSINESS_SCOPE"));
            map.put("GUARANTEE_MODE", rowset.getObject("GUARANTEE_MODE"));
            map.put("COMPLETE_FLAG", rowset.getObject("COMPLETE_FLAG"));
            list.add(map);
        }
        return list;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#fixLogic()
	 */
    @Override
	public void fixLogic() throws Exception {
        if ((cust_id != null) && (cust_code == null))
            this.cust_id = null; //delete(); do not remove it!
        else if ((cust_id == null) && (cust_code != null))
            append();
        else if ((cust_id != null) && (cust_code != null))
            save();
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#syncCrmEntCust()
	 */
    @Override
	public void syncCrmEntCust() throws BusiException {

        Object[] params = new Object[8];
        params[0] = book_code;
        params[1] = cust_code;
        params[2] = cust_name;
        params[3] = card_code;
        params[4] = card_type;
        params[5] = card_id;
        params[6] = cust_type;
        params[7] = input_man;
        try {
            super.update("{?=call SP_SYNC_CRM_TENTCUSTINFO(?,?,?,?,?,?,?,?)}",
                    params);
        } catch (Exception e) {
            throw new BusiException("同步客户信息失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#unite()
	 */
    @Override
	public void unite() throws BusiException {
        Object[] params = new Object[3];
        params[0] = Utility.parseInt(from_cust_id, null);
        params[1] = Utility.parseInt(to_cust_id, null);
        params[2] = Utility.parseInt(input_man, null);
        try {
            super.update("{?= call SP_HB_TENTCUSTINFO(?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("客户信息合并失败：" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#queryTentCustInfoDetail()
	 */
    @Override
	public void queryTentCustInfoDetail() throws Exception {
        super.list();
        String queryTentCustInfoDetailSql = "{call SP_QUERY_TENTCUSTINFODETAIL(?)}";
        Connection conn = IntrustDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(queryTentCustInfoDetailSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, cust_id.intValue());
        ResultSet rslist = stmt.executeQuery();
        try {
            rowset.close();
            rowset.populate(rslist);
        } finally {
            rslist.close();
            stmt.close();
            conn.close();
        }
        countRows();
        countPages();

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getNextTentCustInfoDetail()
	 */
    @Override
	public boolean getNextTentCustInfoDetail() throws Exception {
        boolean b = super.getNext();
        if (b) {
            this.serial_no = new Integer(rowset.getInt("SERIAL_NO"));
            this.book_code = new Integer(rowset.getInt("BOOK_CODE"));
            this.cust_id = new Integer(rowset.getInt("CUST_ID"));
            this.field_name = rowset.getString("FIELD_NAME");
            this.field_cn_name = rowset.getString("FIELD_CN_NAME");
            this.old_field_info = rowset.getString("OLD_FIELD_INFO");
            this.new_field_info = rowset.getString("NEW_FIELD_INFO");
            this.input_man = new Integer(rowset.getInt("INPUT_MAN"));
            this.input_time = rowset.getTimestamp("INPUT_TIME");

        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#queryentcustht()
	 */
    @Override
	public void queryentcustht() throws Exception {
        super.list();
        String queryTentCustInfoDetailSql = "{call SP_QUERY_TENTCUSTHT(?)}";
        Connection conn = IntrustDBManager.getConnection();
        CallableStatement stmt = conn.prepareCall(queryTentCustInfoDetailSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, cust_id.intValue());
        ResultSet rslist = stmt.executeQuery();
        try {
            rowset.close();
            rowset.populate(rslist);
        } finally {
            rslist.close();
            stmt.close();
            conn.close();
        }
        countRows();
        countPages();

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getNextentcustht()
	 */
    @Override
	public boolean getNextentcustht() throws Exception {
        boolean b = super.getNext();
        if (b) {
            this.busi_name = rowset.getString("BUSI_NAME");
            this.r_type_name = rowset.getString("R_TYPE_NAME");
            this.contract_id = new Integer(rowset.getInt("CONTRACT_ID"));
            this.contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
            this.start_date = new Integer(rowset.getInt("START_DATE"));
            this.end_date = new Integer(rowset.getInt("END_DATE"));
            this.ht_money = rowset.getBigDecimal("HT_MONEY");
            this.cw_money = rowset.getBigDecimal("CW_MONEY");

        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setProperties(javax.servlet.ServletRequest, java.lang.String)
	 */
    @Override
	public void setProperties(javax.servlet.ServletRequest request,
            String prefix) throws Exception {
        cust_name = request.getParameter(prefix + "_cust_name");
        cust_code = request.getParameter(prefix + "_cust_code");
        ent_type = request.getParameter(prefix + "_ent_type");
        card_id = request.getParameter(prefix + "_card_id");
        bank_id = request.getParameter(prefix + "_bank_id");
        bank_acct = request.getParameter(prefix + "_bank_acct");
        reg_address = request.getParameter(prefix + "_reg_address");
        reg_postcode = request.getParameter(prefix + "_reg_postcode");
        link_man = request.getParameter(prefix + "_link_man");
        address = request.getParameter(prefix + "_address");
        postcode = request.getParameter(prefix + "_postcode");
        telphone = request.getParameter(prefix + "_telphone");
        fax = request.getParameter(prefix + "_fax");
        email = request.getParameter(prefix + "_email");
        ent_type = request.getParameter(prefix + "_ent_type");
        summary = request.getParameter(prefix + "_summary");
        bank_sub_name = request.getParameter(prefix + "_bank_sub_name");

        sex = Utility.parseInt(request.getParameter(prefix + "_sex"),
                new Integer(0));
        cust_type = Utility.parseInt(request
                .getParameter(prefix + "_cust_type"), new Integer(1));
        card_type = request.getParameter(prefix + "_card_type");
        card_type_name = request.getParameter(prefix + "_card_type_name");
        credit_level = request.getParameter(prefix + "_credit_level");
        credit_level_name = request.getParameter(prefix + "_credit_level_name");
        card_code = request.getParameter(prefix + "_card_code");

        jt_flag = Utility.parseInt(request.getParameter(prefix + "_jt_flag"),
                new Integer(2));

        is_link = Utility.parseInt(request.getParameter(prefix + "_is_link"),
                new Integer(2));

        if ("".equals(cust_name))
            cust_name = null;
        if ("".equals(cust_code))
            cust_code = null;

        workaddress = request.getParameter("workaddress");//目前办公地点
        legal_person = request.getParameter("legal_person");//法定代表人
        contri_capital = Utility.parseDecimal(request
                .getParameter("contri_capital"), new BigDecimal(0));//实收资本
        regist_capital = Utility.parseDecimal(request
                .getParameter("regist_capital"), new BigDecimal(0));//注册资本
        total_seset = Utility.parseDecimal(request.getParameter("total_seset"),
                new BigDecimal(0));//资产总额
        incorporation_date = request.getParameter("incorporation_date");//成立时间
        tax_registration_no = request.getParameter("tax_registration_no");//税务登记号
        net_asset = Utility.parseDecimal(request.getParameter("net_asset"),
                new BigDecimal(0));//净资产
        annual_inspection = Utility.parseInt(request
                .getParameter("annual_inspection"), new Integer(0));//是否年检:1是,2否
        forms_type = Utility.parseInt(request.getParameter("forms_type"),
                new Integer(0));//财务报表类型:1审计类,2非审计类
        sales_income = Utility.parseDecimal(request
                .getParameter("sales_income"), new BigDecimal(0));//上年销售收入
        funding_source = request.getParameter("funding_source");//项目来源
        strong_stockholder = request.getParameter("strong_stockholder");//企业实际控制人
        ratio = Utility.parseDecimal(request.getParameter("ratio"), null);
        require = Utility.parseInt(request.getParameter("require"),
                new Integer(0));//企业需求 SELECT TYPE_VALUE FROM TDICTPARAM WHERE
        // TYPE_ID = 5116
        item_location = request.getParameter("item_location");//项目所在区域
        scale_term = request.getParameter("scale_term");//项目规模和期限
        financial_cost = Utility.parseDecimal(request
                .getParameter("financial_cost"), new BigDecimal(0));//财务成本
        business_scope = request.getParameter("business_scope");//主营业务
        guarantee_mode = request.getParameter("guarantee_mode");//企业拟提供的担保方式
        voc_type = Utility.trimNull(request.getParameter("voc_type"));//行业类型
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#listTreeBySql(java.lang.String, java.lang.String)
	 */
    @Override
	public String listTreeBySql(String type_code,String value)throws Exception {
		List list = null;
		List arrAyList = new ArrayList();
		Boolean leaf = new Boolean(false);
		Object[] param = new Object[2];
		param[0] = Utility.trimNull(type_code);
		param[1] = Utility.trimNull(value);

		super.query("{call SP_QUERY_TINDUSTRYCATEGORY_TREE(?,?)}",param);
		while(super.getNext()){
			HashMap hmap = new HashMap();
			if(Utility.parseInt(Utility.trimNull(rowset.getString("BOTTOM_FLAG")),2)==2)
				leaf = new Boolean(false);
			else
				leaf = new Boolean(true);
			hmap.put("id",Utility.trimNull(rowset.getString("TYPE_CODE")));
			hmap.put("text",Utility.trimNull(rowset.getString("TYPE_NAME")));
			hmap.put("leaf",leaf);
			arrAyList.add(hmap);
		}
		
		String ret = enfo.crm.util.JsonUtil.object2json(arrAyList);
		
		ret = Utility.replaceAll(ret, "\"false\"", "false");
		ret = Utility.replaceAll(ret, "\"true\"", "true");
		return ret;
	}
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#addEntRelation(enfo.crm.vo.EntRelationVO)
	 */
	@Override
	public void addEntRelation(EntRelationVO vo) throws Exception {
		Object[] params = new Object[7];
		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		params[1] = Utility.parseInt(vo.getEnt_cust_id(),new Integer(0));
		params[2] = Utility.trimNull(vo.getRelation_type());
		params[3] = Utility.parseInt(vo.getRelation_cust_id(),new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[5] = Utility.parseInt(vo.getKg_flag(), new Integer(0));
		params[6] = Utility.parseBigDecimal(vo.getCg_rate(), new BigDecimal(0));

		super.cud("{?=call SP_ADD_TENTRELATION(?,?,?,?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#delEntRelation(enfo.crm.vo.EntRelationVO)
	 */
	@Override
	public void delEntRelation(EntRelationVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		
		try {
			super.cud("{?=call SP_DEL_TENTRELATION(?,?)}", params);
		} catch (Exception e) {
			throw new BusiException("删除客户股东关系失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#queryEntRelation(enfo.crm.vo.EntRelationVO, int, int)
	 */
	@Override
	public IPageList queryEntRelation(EntRelationVO vo, int pageIndex, int pageSize)
			throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getEnt_cust_id(), new Integer(0));
		params[2] = Utility.trimNull(vo.getRelation_type());

		try {

			rsList = super.listProcPage("{call SP_QUERY_TENTRELATION(?,?,?)}", params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询客户股东关系失败: " + e.getMessage());
		}
		return rsList;
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getAddress()
	 */
    @Override
	public java.lang.String getAddress() {
        return address;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setAddress(java.lang.String)
	 */
    @Override
	public void setAddress(java.lang.String address) {
        this.address = address;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getAnnual_inspection()
	 */
    @Override
	public Integer getAnnual_inspection() {
        return annual_inspection;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setAnnual_inspection(java.lang.Integer)
	 */
    @Override
	public void setAnnual_inspection(Integer annual_inspection) {
        this.annual_inspection = annual_inspection;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getBank_acct()
	 */
    @Override
	public java.lang.String getBank_acct() {
        return bank_acct;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setBank_acct(java.lang.String)
	 */
    @Override
	public void setBank_acct(java.lang.String bank_acct) {
        this.bank_acct = bank_acct;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getBank_id()
	 */
    @Override
	public java.lang.String getBank_id() {
        return bank_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setBank_id(java.lang.String)
	 */
    @Override
	public void setBank_id(java.lang.String bank_id) {
        this.bank_id = bank_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getBank_name()
	 */
    @Override
	public java.lang.String getBank_name() {
        return bank_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setBank_name(java.lang.String)
	 */
    @Override
	public void setBank_name(java.lang.String bank_name) {
        this.bank_name = bank_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getBank_sub_name()
	 */
    @Override
	public java.lang.String getBank_sub_name() {
        return bank_sub_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setBank_sub_name(java.lang.String)
	 */
    @Override
	public void setBank_sub_name(java.lang.String bank_sub_name) {
        this.bank_sub_name = bank_sub_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getBook_code()
	 */
    @Override
	public java.lang.Integer getBook_code() {
        return book_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setBook_code(java.lang.Integer)
	 */
    @Override
	public void setBook_code(java.lang.Integer book_code) {
        this.book_code = book_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getBusiness_scope()
	 */
    @Override
	public String getBusiness_scope() {
        return business_scope;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setBusiness_scope(java.lang.String)
	 */
    @Override
	public void setBusiness_scope(String business_scope) {
        this.business_scope = business_scope;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCard_code()
	 */
    @Override
	public java.lang.String getCard_code() {
        return card_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCard_code(java.lang.String)
	 */
    @Override
	public void setCard_code(java.lang.String card_code) {
        this.card_code = card_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCard_id()
	 */
    @Override
	public java.lang.String getCard_id() {
        return card_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCard_id(java.lang.String)
	 */
    @Override
	public void setCard_id(java.lang.String card_id) {
        this.card_id = card_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCard_type()
	 */
    @Override
	public java.lang.String getCard_type() {
        return card_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCard_type(java.lang.String)
	 */
    @Override
	public void setCard_type(java.lang.String card_type) {
        this.card_type = card_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCard_type_name()
	 */
    @Override
	public java.lang.String getCard_type_name() {
        return card_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCard_type_name(java.lang.String)
	 */
    @Override
	public void setCard_type_name(java.lang.String card_type_name) {
        this.card_type_name = card_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getContri_capital()
	 */
    @Override
	public BigDecimal getContri_capital() {
        return contri_capital;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setContri_capital(java.math.BigDecimal)
	 */
    @Override
	public void setContri_capital(BigDecimal contri_capital) {
        this.contri_capital = contri_capital;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCredit_level()
	 */
    @Override
	public java.lang.String getCredit_level() {
        return credit_level;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCredit_level(java.lang.String)
	 */
    @Override
	public void setCredit_level(java.lang.String credit_level) {
        this.credit_level = credit_level;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCredit_level_name()
	 */
    @Override
	public java.lang.String getCredit_level_name() {
        return credit_level_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCredit_level_name(java.lang.String)
	 */
    @Override
	public void setCredit_level_name(java.lang.String credit_level_name) {
        this.credit_level_name = credit_level_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCust_code()
	 */
    @Override
	public java.lang.String getCust_code() {
        return cust_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCust_code(java.lang.String)
	 */
    @Override
	public void setCust_code(java.lang.String cust_code) {
        this.cust_code = cust_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCust_id()
	 */
    @Override
	public java.lang.Integer getCust_id() {
        return cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCust_id(java.lang.Integer)
	 */
    @Override
	public void setCust_id(java.lang.Integer cust_id) {
        this.cust_id = cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCust_name()
	 */
    @Override
	public java.lang.String getCust_name() {
        return cust_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCust_name(java.lang.String)
	 */
    @Override
	public void setCust_name(java.lang.String cust_name) {
        this.cust_name = cust_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCust_type()
	 */
    @Override
	public Integer getCust_type() {
        return cust_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCust_type(java.lang.Integer)
	 */
    @Override
	public void setCust_type(Integer cust_type) {
        this.cust_type = cust_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getEmail()
	 */
    @Override
	public java.lang.String getEmail() {
        return email;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setEmail(java.lang.String)
	 */
    @Override
	public void setEmail(java.lang.String email) {
        this.email = email;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getEnt_type()
	 */
    @Override
	public java.lang.String getEnt_type() {
        return ent_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setEnt_type(java.lang.String)
	 */
    @Override
	public void setEnt_type(java.lang.String ent_type) {
        this.ent_type = ent_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getEnt_type_name()
	 */
    @Override
	public java.lang.String getEnt_type_name() {
        return ent_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setEnt_type_name(java.lang.String)
	 */
    @Override
	public void setEnt_type_name(java.lang.String ent_type_name) {
        this.ent_type_name = ent_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getFax()
	 */
    @Override
	public java.lang.String getFax() {
        return fax;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setFax(java.lang.String)
	 */
    @Override
	public void setFax(java.lang.String fax) {
        this.fax = fax;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getField_cn_name()
	 */
    @Override
	public java.lang.String getField_cn_name() {
        return field_cn_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setField_cn_name(java.lang.String)
	 */
    @Override
	public void setField_cn_name(java.lang.String field_cn_name) {
        this.field_cn_name = field_cn_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getField_name()
	 */
    @Override
	public java.lang.String getField_name() {
        return field_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setField_name(java.lang.String)
	 */
    @Override
	public void setField_name(java.lang.String field_name) {
        this.field_name = field_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getFinancial_cost()
	 */
    @Override
	public BigDecimal getFinancial_cost() {
        return financial_cost;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setFinancial_cost(java.math.BigDecimal)
	 */
    @Override
	public void setFinancial_cost(BigDecimal financial_cost) {
        this.financial_cost = financial_cost;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getForms_type()
	 */
    @Override
	public Integer getForms_type() {
        return forms_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setForms_type(java.lang.Integer)
	 */
    @Override
	public void setForms_type(Integer forms_type) {
        this.forms_type = forms_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getFrom_cust_id()
	 */
    @Override
	public Integer getFrom_cust_id() {
        return from_cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setFrom_cust_id(java.lang.Integer)
	 */
    @Override
	public void setFrom_cust_id(Integer from_cust_id) {
        this.from_cust_id = from_cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getFunding_source()
	 */
    @Override
	public String getFunding_source() {
        return funding_source;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setFunding_source(java.lang.String)
	 */
    @Override
	public void setFunding_source(String funding_source) {
        this.funding_source = funding_source;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getGuarantee_mode()
	 */
    @Override
	public String getGuarantee_mode() {
        return guarantee_mode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setGuarantee_mode(java.lang.String)
	 */
    @Override
	public void setGuarantee_mode(String guarantee_mode) {
        this.guarantee_mode = guarantee_mode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getIncorporation_date()
	 */
    @Override
	public String getIncorporation_date() {
        return incorporation_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setIncorporation_date(java.lang.String)
	 */
    @Override
	public void setIncorporation_date(String incorporation_date) {
        this.incorporation_date = incorporation_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getInput_man()
	 */
    @Override
	public java.lang.Integer getInput_man() {
        return input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setInput_man(java.lang.Integer)
	 */
    @Override
	public void setInput_man(java.lang.Integer input_man) {
        this.input_man = input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getInput_time()
	 */
    @Override
	public java.sql.Timestamp getInput_time() {
        return input_time;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setInput_time(java.sql.Timestamp)
	 */
    @Override
	public void setInput_time(java.sql.Timestamp input_time) {
        this.input_time = input_time;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getIs_link()
	 */
    @Override
	public Integer getIs_link() {
        return is_link;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setIs_link(java.lang.Integer)
	 */
    @Override
	public void setIs_link(Integer is_link) {
        this.is_link = is_link;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getItem_location()
	 */
    @Override
	public String getItem_location() {
        return item_location;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setItem_location(java.lang.String)
	 */
    @Override
	public void setItem_location(String item_location) {
        this.item_location = item_location;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getJt_cust_id()
	 */
    @Override
	public java.lang.Integer getJt_cust_id() {
        return jt_cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setJt_cust_id(java.lang.Integer)
	 */
    @Override
	public void setJt_cust_id(java.lang.Integer jt_cust_id) {
        this.jt_cust_id = jt_cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getJt_flag()
	 */
    @Override
	public Integer getJt_flag() {
        return jt_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setJt_flag(java.lang.Integer)
	 */
    @Override
	public void setJt_flag(Integer jt_flag) {
        this.jt_flag = jt_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getLast_modi_time()
	 */
    @Override
	public java.sql.Timestamp getLast_modi_time() {
        return last_modi_time;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setLast_modi_time(java.sql.Timestamp)
	 */
    @Override
	public void setLast_modi_time(java.sql.Timestamp last_modi_time) {
        this.last_modi_time = last_modi_time;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getLegal_person()
	 */
    @Override
	public String getLegal_person() {
        return legal_person;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setLegal_person(java.lang.String)
	 */
    @Override
	public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getLink_gd_money()
	 */
    @Override
	public BigDecimal getLink_gd_money() {
        return link_gd_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setLink_gd_money(java.math.BigDecimal)
	 */
    @Override
	public void setLink_gd_money(BigDecimal link_gd_money) {
        this.link_gd_money = link_gd_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getLink_man()
	 */
    @Override
	public java.lang.String getLink_man() {
        return link_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setLink_man(java.lang.String)
	 */
    @Override
	public void setLink_man(java.lang.String link_man) {
        this.link_man = link_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getLink_type()
	 */
    @Override
	public java.lang.Integer getLink_type() {
        return link_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setLink_type(java.lang.Integer)
	 */
    @Override
	public void setLink_type(java.lang.Integer link_type) {
        this.link_type = link_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getModi_man()
	 */
    @Override
	public java.lang.Integer getModi_man() {
        return modi_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setModi_man(java.lang.Integer)
	 */
    @Override
	public void setModi_man(java.lang.Integer modi_man) {
        this.modi_man = modi_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getNet_asset()
	 */
    @Override
	public BigDecimal getNet_asset() {
        return net_asset;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setNet_asset(java.math.BigDecimal)
	 */
    @Override
	public void setNet_asset(BigDecimal net_asset) {
        this.net_asset = net_asset;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getNew_field_info()
	 */
    @Override
	public java.lang.String getNew_field_info() {
        return new_field_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setNew_field_info(java.lang.String)
	 */
    @Override
	public void setNew_field_info(java.lang.String new_field_info) {
        this.new_field_info = new_field_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getOld_field_info()
	 */
    @Override
	public java.lang.String getOld_field_info() {
        return old_field_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setOld_field_info(java.lang.String)
	 */
    @Override
	public void setOld_field_info(java.lang.String old_field_info) {
        this.old_field_info = old_field_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getPostcode()
	 */
    @Override
	public java.lang.String getPostcode() {
        return postcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setPostcode(java.lang.String)
	 */
    @Override
	public void setPostcode(java.lang.String postcode) {
        this.postcode = postcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getProduct_id()
	 */
    @Override
	public Integer getProduct_id() {
        return product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setProduct_id(java.lang.Integer)
	 */
    @Override
	public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getProduct_name()
	 */
    @Override
	public String getProduct_name() {
        return product_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setProduct_name(java.lang.String)
	 */
    @Override
	public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getRatio()
	 */
    @Override
	public BigDecimal getRatio() {
        return ratio;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setRatio(java.math.BigDecimal)
	 */
    @Override
	public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getReg_address()
	 */
    @Override
	public java.lang.String getReg_address() {
        return reg_address;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setReg_address(java.lang.String)
	 */
    @Override
	public void setReg_address(java.lang.String reg_address) {
        this.reg_address = reg_address;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getReg_postcode()
	 */
    @Override
	public java.lang.String getReg_postcode() {
        return reg_postcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setReg_postcode(java.lang.String)
	 */
    @Override
	public void setReg_postcode(java.lang.String reg_postcode) {
        this.reg_postcode = reg_postcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getRegist_capital()
	 */
    @Override
	public BigDecimal getRegist_capital() {
        return regist_capital;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setRegist_capital(java.math.BigDecimal)
	 */
    @Override
	public void setRegist_capital(BigDecimal regist_capital) {
        this.regist_capital = regist_capital;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getRequire()
	 */
    @Override
	public Integer getRequire() {
        return require;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setRequire(java.lang.Integer)
	 */
    @Override
	public void setRequire(Integer require) {
        this.require = require;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getSales_income()
	 */
    @Override
	public BigDecimal getSales_income() {
        return sales_income;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setSales_income(java.math.BigDecimal)
	 */
    @Override
	public void setSales_income(BigDecimal sales_income) {
        this.sales_income = sales_income;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getScale_term()
	 */
    @Override
	public String getScale_term() {
        return scale_term;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setScale_term(java.lang.String)
	 */
    @Override
	public void setScale_term(String scale_term) {
        this.scale_term = scale_term;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getSerial_no()
	 */
    @Override
	public java.lang.Integer getSerial_no() {
        return serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setSerial_no(java.lang.Integer)
	 */
    @Override
	public void setSerial_no(java.lang.Integer serial_no) {
        this.serial_no = serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getSex()
	 */
    @Override
	public Integer getSex() {
        return sex;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setSex(java.lang.Integer)
	 */
    @Override
	public void setSex(Integer sex) {
        this.sex = sex;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getStrong_stockholder()
	 */
    @Override
	public String getStrong_stockholder() {
        return strong_stockholder;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setStrong_stockholder(java.lang.String)
	 */
    @Override
	public void setStrong_stockholder(String strong_stockholder) {
        this.strong_stockholder = strong_stockholder;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getSummary()
	 */
    @Override
	public java.lang.String getSummary() {
        return summary;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setSummary(java.lang.String)
	 */
    @Override
	public void setSummary(java.lang.String summary) {
        this.summary = summary;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getTax_registration_no()
	 */
    @Override
	public String getTax_registration_no() {
        return tax_registration_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setTax_registration_no(java.lang.String)
	 */
    @Override
	public void setTax_registration_no(String tax_registration_no) {
        this.tax_registration_no = tax_registration_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getTelphone()
	 */
    @Override
	public java.lang.String getTelphone() {
        return telphone;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setTelphone(java.lang.String)
	 */
    @Override
	public void setTelphone(java.lang.String telphone) {
        this.telphone = telphone;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getTo_cust_id()
	 */
    @Override
	public Integer getTo_cust_id() {
        return to_cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setTo_cust_id(java.lang.Integer)
	 */
    @Override
	public void setTo_cust_id(Integer to_cust_id) {
        this.to_cust_id = to_cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getTotal_seset()
	 */
    @Override
	public BigDecimal getTotal_seset() {
        return total_seset;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setTotal_seset(java.math.BigDecimal)
	 */
    @Override
	public void setTotal_seset(BigDecimal total_seset) {
        this.total_seset = total_seset;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getVoc_type()
	 */
    @Override
	public java.lang.String getVoc_type() {
        return voc_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setVoc_type(java.lang.String)
	 */
    @Override
	public void setVoc_type(java.lang.String voc_type) {
        this.voc_type = voc_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getVoc_type_name()
	 */
    @Override
	public java.lang.String getVoc_type_name() {
        return voc_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setVoc_type_name(java.lang.String)
	 */
    @Override
	public void setVoc_type_name(java.lang.String voc_type_name) {
        this.voc_type_name = voc_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getWorkaddress()
	 */
    @Override
	public String getWorkaddress() {
        return workaddress;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setWorkaddress(java.lang.String)
	 */
    @Override
	public void setWorkaddress(String workaddress) {
        this.workaddress = workaddress;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getBusi_name()
	 */
    @Override
	public String getBusi_name() {
        return busi_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getContract_id()
	 */
    @Override
	public Integer getContract_id() {
        return contract_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getContract_sub_bh()
	 */
    @Override
	public String getContract_sub_bh() {
        return contract_sub_bh;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCw_money()
	 */
    @Override
	public java.math.BigDecimal getCw_money() {
        return cw_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getEnd_date()
	 */
    @Override
	public Integer getEnd_date() {
        return end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getHt_money()
	 */
    @Override
	public java.math.BigDecimal getHt_money() {
        return ht_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getR_type_name()
	 */
    @Override
	public String getR_type_name() {
        return r_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getStart_date()
	 */
    @Override
	public Integer getStart_date() {
        return start_date;
    }
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getGov_prov_regioal()
	 */
	@Override
	public String getGov_prov_regioal() {
		return gov_prov_regioal;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setGov_prov_regioal(java.lang.String)
	 */
	@Override
	public void setGov_prov_regioal(String gov_prov_regioal) {
		this.gov_prov_regioal = gov_prov_regioal;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getGov_regioal()
	 */
	@Override
	public String getGov_regioal() {
		return gov_regioal;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setGov_regioal(java.lang.String)
	 */
	@Override
	public void setGov_regioal(String gov_regioal) {
		this.gov_regioal = gov_regioal;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getType_code()
	 */
	@Override
	public String getType_code() {
		return type_code;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setType_code(java.lang.String)
	 */
	@Override
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getType_name()
	 */
	@Override
	public String getType_name() {
		return type_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setType_name(java.lang.String)
	 */
	@Override
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getGov_prov_regioal_name()
	 */
	@Override
	public String getGov_prov_regioal_name() {
		return gov_prov_regioal_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setGov_prov_regioal_name(java.lang.String)
	 */
	@Override
	public void setGov_prov_regioal_name(String gov_prov_regioal_name) {
		this.gov_prov_regioal_name = gov_prov_regioal_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getGov_regioal_name()
	 */
	@Override
	public String getGov_regioal_name() {
		return gov_regioal_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setGov_regioal_name(java.lang.String)
	 */
	@Override
	public void setGov_regioal_name(String gov_regioal_name) {
		this.gov_regioal_name = gov_regioal_name;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCountry()
	 */
	@Override
	public String getCountry() {
		return country;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCountry(java.lang.String)
	 */
	@Override
	public void setCountry(String country) {
		this.country = country;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCountry_name()
	 */
	@Override
	public String getCountry_name() {
		return country_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCountry_name(java.lang.String)
	 */
	@Override
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getJkr_type()
	 */
	@Override
	public String getJkr_type() {
		return jkr_type;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setJkr_type(java.lang.String)
	 */
	@Override
	public void setJkr_type(String jkr_type) {
		this.jkr_type = jkr_type;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getJkr_type_name()
	 */
	@Override
	public String getJkr_type_name() {
		return jkr_type_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setJkr_type_name(java.lang.String)
	 */
	@Override
	public void setJkr_type_name(String jkr_type_name) {
		this.jkr_type_name = jkr_type_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getJkr_type2()
	 */
	@Override
	public String getJkr_type2() {
		return jkr_type2;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setJkr_type2(java.lang.String)
	 */
	@Override
	public void setJkr_type2(String jkr_type2) {
		this.jkr_type2 = jkr_type2;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getJkr_type2_name()
	 */
	@Override
	public String getJkr_type2_name() {
		return jkr_type2_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setJkr_type2_name(java.lang.String)
	 */
	@Override
	public void setJkr_type2_name(String jkr_type2_name) {
		this.jkr_type2_name = jkr_type2_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getCountry_tax_no()
	 */
	@Override
	public String getCountry_tax_no() {
		return country_tax_no;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setCountry_tax_no(java.lang.String)
	 */
	@Override
	public void setCountry_tax_no(String country_tax_no) {
		this.country_tax_no = country_tax_no;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getLand_tax_no()
	 */
	@Override
	public String getLand_tax_no() {
		return land_tax_no;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setLand_tax_no(java.lang.String)
	 */
	@Override
	public void setLand_tax_no(String land_tax_no) {
		this.land_tax_no = land_tax_no;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getLicense_end_date()
	 */
	@Override
	public Integer getLicense_end_date() {
		return license_end_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setLicense_end_date(java.lang.Integer)
	 */
	@Override
	public void setLicense_end_date(Integer license_end_date) {
		this.license_end_date = license_end_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getReg_date()
	 */
	@Override
	public Integer getReg_date() {
		return reg_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setReg_date(java.lang.Integer)
	 */
	@Override
	public void setReg_date(Integer reg_date) {
		this.reg_date = reg_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getReg_no()
	 */
	@Override
	public String getReg_no() {
		return reg_no;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setReg_no(java.lang.String)
	 */
	@Override
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getValid_period()
	 */
	@Override
	public Integer getValid_period() {
		return valid_period;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setValid_period(java.lang.Integer)
	 */
	@Override
	public void setValid_period(Integer valid_period) {
		this.valid_period = valid_period;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getPeriod_unit()
	 */
	@Override
	public Integer getPeriod_unit() {
		return period_unit;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setPeriod_unit(java.lang.Integer)
	 */
	@Override
	public void setPeriod_unit(Integer period_unit) {
		this.period_unit = period_unit;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getPublic_flag()
	 */
	@Override
	public Integer getPublic_flag() {
		return public_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setPublic_flag(java.lang.Integer)
	 */
	@Override
	public void setPublic_flag(Integer public_flag) {
		this.public_flag = public_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getSeid_id()
	 */
	@Override
	public String getSeid_id() {
		return seid_id;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setSeid_id(java.lang.String)
	 */
	@Override
	public void setSeid_id(String seid_id) {
		this.seid_id = seid_id;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getStock_code()
	 */
	@Override
	public String getStock_code() {
		return stock_code;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setStock_code(java.lang.String)
	 */
	@Override
	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getStock_name()
	 */
	@Override
	public String getStock_name() {
		return stock_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setStock_name(java.lang.String)
	 */
	@Override
	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getComplete_flag()
	 */
	@Override
	public Integer getComplete_flag() {
		return complete_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setComplete_flag(java.lang.Integer)
	 */
	@Override
	public void setComplete_flag(Integer complete_flag) {
		this.complete_flag = complete_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#getFz_total()
	 */
	@Override
	public String getFz_total() {
		return fz_total;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustomerLocal#setFz_total(java.lang.String)
	 */
	@Override
	public void setFz_total(String fz_total) {
		this.fz_total = fz_total;
	}
}