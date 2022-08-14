
/*
 * �������� 2009-11-19
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.io.InputStream;
import java.math.BigDecimal;

/**
 * �ͻ�������Ϣ�����ӦCustomerVO����
 * @author dingyj
 * @since 2009-11-18
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CustomerVO {

	private java.lang.Integer cust_id;
	private java.lang.Integer book_code;
	private java.lang.String cust_no;
	private java.lang.String cust_name;
	private java.lang.String card_type;
	private java.lang.String card_type_name;
	private java.lang.String card_id;
	private java.lang.String card_address;
	private java.lang.Integer cust_type;
	private java.lang.String cust_type_name;
	private java.lang.Integer wt_flag;
	private java.lang.String wt_flag_name;
	private java.lang.String cust_level;
	private java.lang.String cust_level_name;
	private java.lang.String touch_type;
	private java.lang.String touch_type_name;
	private java.lang.String cust_source;
	private java.lang.String cust_source_name;
	private java.lang.Integer input_man;
	private java.sql.Timestamp input_time;
	private java.lang.Integer check_flag;
	private java.lang.String status;
	private java.lang.String status_name;
	private String cust_tel;
	private String post_address;
	private String post_code;
	private Integer age;
	private Integer sex;
	private String sex_name;
	private String o_tel;
	private String h_tel;
	private String mobile;
	private String bp;
	private String fax;
	private String e_mail;
	private Integer rg_times;
	private java.math.BigDecimal total_money;
	private java.math.BigDecimal current_money;
	private java.math.BigDecimal ben_amount;
	private Integer last_rg_date;
	private Integer check_man;
	private Integer modi_man;
	private Integer modi_time;
	private Integer cancel_man;
	private Integer cancel_time;
	private String summary;
	private String contract_bh;
	private Integer product_id;
	private java.math.BigDecimal curr_to_amount;

	private String legal_man; //��������
	private String legal_address; //���˵�ַ
	private String prov_level;
	private Integer birthday;
	private int birthday_temp; //��ʱ����,��ΪNULLֵû��.intValue()	
	private String post_address2 = "";
	private String post_code2 = "";
	private Integer print_deploy_bill; //--�Ƿ��ӡ�ͻ����ʵ������������ 
	private Integer print_post_info; // --�Ƿ��ӡ��¶��Ϣ
	private Integer is_link = new Integer(0); //�Ƿ������
	private String last_product_name; //--��������Ʒ����
	private java.math.BigDecimal exchange_amount; //-ת�ý��
	private java.math.BigDecimal back_amount; //-�Ҹ����
	private Integer from_cust_id; //�ϲ��ͻ���ʼID
	private Integer to_cust_id; //�ϲ��ͻ���ID
	private Integer service_man;
	private String vip_card_id;
	private Integer vip_date;
	private String hgtzr_bh;
	private Integer tzr_flag;
	private String voc_type;
	private String voc_type_name;
	private int modi_flag;
	private String contact_man; //��ϵ��
	private Integer card_valid_date; //�ͻ����֤ ��Ч���� 8λ���ڱ�ʾ
	private String country; //�ͻ�������9901��
	private String jg_cust_type; //�������9921��������cust_type=2 ����ʱ��Ч)
	//MONEY_SOURCE �ʽ���Դ
	private String money_source;
	private String money_source_name;
	
	private String recommended;
	
	private String accountManager;
	
	private String classEs;
	
	private String productTo;
	private String telephone;
	private Integer start_age;
	private Integer end_age;
	private Integer cell_id;
	private Integer birthday_end;
	private Integer rg_begin_date;
	private Integer rg_end_date;
	//20111212
	private String region;
	private String channel;
	
	private String head_office_cust_id;
	private Integer stature;
	private Integer weight;
	private String spouse_name;
	private String spouse_info;
	private String children_info;
	private String children_name;
	private Integer employee_num;
	private String holding;
	private BigDecimal personal_income;
	private BigDecimal household_income;
	private Integer feeding_num_people;
	private String main_source;
	private String other_source;
	private String house_position;
	private Integer house_property;
	private Integer house_area;
	private String plat_envaluate;
	private BigDecimal market_appraisal;
	private Integer vehicle_num;
	private String vehicle_make;
	private String vehicle_type;
	private String credit_type;
	private String credit_type_name;
	private Integer credit_num;
	private Integer credit_start_date;
	private Integer credit_end_date;
	private String other_investment_status;
	private String other_inve_status_name;
	private String type_pref;
	private String type_pref_name;
	private String time_limit_pref;
	private String time_limit_pref_name;
	private String invest_pref;
	private String invest_pref_name;
	private String hobby_pref;
	private String hobby_pref_name;
	private String service_pref;
	private String service_pref_name;
	private String contact_pref;
	private String contact_pref_name;
	private String risk_pref;
	private String nation;
	private String nation_name;
	private Integer marital;
	private String health;
	private String health_name;
	private String education;
	private String education_name;
	private String post;
	private String holderofanoffice;
	private String company_character;
	private String company_character_name;
	private Integer company_staff;
	private Integer bocom_staff;
	private String client_quale;
	private String client_quale_name;
	private String registered_place;
	private BigDecimal registered_capital;
	private Integer company_family;
	private Integer start_current_score;
	private Integer end_current_score;
	private BigDecimal trade_money;
    private String team_name;
    private Integer query_type;
    private String customer_messager;
    private Integer begin_date;
	
	private java.math.BigDecimal activity_fee;//Ӫ������
	
	private BigDecimal burend_of_debt;
	private BigDecimal free_cash_flow;
	
	private Integer complete_flag; //�������� 1���� 2������ Ĭ��2
	
	private Integer prov_flag;
	private Integer sub_product_id;
	private String product_name;
	
	private int change_flag = -1;// 1 ��ʾ��Ͷ�޸���Ҫ�ύ��� ��
	private String company_unit;
	private String company_depart;
	private String company_position;
	
	private Integer start_date;
	private Integer end_date;
	
	private String cust_aum;
	private String cust_age_group;
	private String inves_experinc; 
	private String cust_source_explain;
	
	private String city_name;//��������
	private String service_info;//������Ŀ
	private Integer executor;//ִ����
	private Integer serial_no;
	private String data_flag;//��������
	
	private Integer verify_date;//�˲�����
	private Integer verify_state;//�˲�״̬
	
	private Integer issued_date;//ǩ������
	private Integer valid_date;//��Ч�� TCUSTCARDINFO���е��ֶΣ�ͬʱTCustomersҲ������ֶΣ������������Ƶģ�����ϵͳ���õ���cad_validate_date;
	private String issued_org;//ǩ����
	
	private String east_jg_type; //--��������2109������EASTί��������WTRLX��
	private String east_jg_type_name;//�����������ƣ�����EASTί��������WTRLX��
	private String jg_wtrlx2; //������Ч��1�����Թ�˾ 2���� 3�ǽ����Թ�˾ 4��������Թ�˾
	
	//�ͻ�Ͷ��
	private Integer input_date;//Ͷ������
	private Integer comp_type;//Ͷ�߷�ʽ
	private String content;//Ͷ������
	private String check_content;//�˲����
	private Integer reply_type;//�ظ���ʽ
	private Integer reply_date;//�ظ�����
	private Integer do_status;//����״̬
	private Integer forward_type;//ת����ʽ
	private String do_result;//������
	private Integer relpy_man;//�ط���
	
	private String invest_field;//CRMͶ������
	
	//�ͻ���Ϣ�޸����
	private Integer change_check_flag;
	
	private String must_contain;
	private String loosely_match;
	private Integer max_diff;
	
	private Integer link_type; //��������(1302)
	private BigDecimal link_gd_money;//�ɶ�Ͷ��������й�˾���
	
	private String legal_tel;//���˹̶��绰
    private String legal_mobile;//�����ֻ�
    
    private String industry_post;//��ҵ��λ
    private String industry_post_name;//��ҵ��λ����

    private String cust_industry;

    private String cust_corp_nature;

    private String corp_branch;

    private String service_man_tel;

    private String grade_score;

    private String fc_card_type;

    private String fc_card_id;

    private Integer fc_card_valid_date;

    private String summary2;

    private String summary1;
    
    private String legal_post_code;
    
    private String trans_name;
    
    private String trans_tel;
    
    private String trans_mobile;
    
    private String trans_address;
    
    private String trans_post_code;
    
    private String register_address;
    
    private String register_post_code;
	
    private String business_code;
    private String organ_code;
    private String tax_reg_code;
    private String legal_name;
    private String legal_card_id;
    private String agent_name;
    private String agent_card_id;
    private String partner_card_id;
    private String managing_scope;  // ��Ӫ��Χ
    private String legal_card_type; // ����
    private Integer legal_card_valid_date;
    private String agent_card_type; // ������
    private Integer agent_card_valid_date;
    private String agent_tel;
    
    private Integer true_flag; //�ͻ���ʵ�Ա�� Ĭ��Ϊ1δ�˲�
    
    private Integer export_flag; // �ͻ���Ϣ���������ã�0�ǵ�����ѯ 1�����ͻ���Ϣ 2�����ֻ� 3����ͨѶ¼
    private String export_summary;//--������ע
    

    private Integer list_id;
    private String tel;
    private String province;
    private String city;
    private String district;
    private String town_detail;
    
    private String cust_flag; /*  --�ͻ���ʶ����1λ���ͻ�����ʵ�Ժ˲�[1δ�˲�(Ĭ��)��2��ʵ��3���˲�(�Ѿ���ʵ������ȷ�����)];
    	��2λ��֤���������Ψһ��У��[2��У�飬1У��(Ĭ��)];                 ��3λ���Ƿ���ʵ�ͻ�[2Ǳ�ڿͻ�(Ĭ��)��1��ʵ�ͻ�]
		��4λ:�Ƿ�����ͻ�[1��0��] */
    
    
    private Integer task_type ;         //  --����־��102 ����������� 103 ��Ϣ��¶����
    
    private Integer date_begin;   // --�ƻ��������ڣ���
    private Integer date_end; //      --�ƻ��������ڣ�ֹ��
    private BigDecimal ben_money; //������
    private Integer task_date;// �����������
    
    
    private String key_word;//-�����ؼ���
    
    private Integer flag; //��ѯ��ʶ
    
    
    private Integer sPage; 
    private Integer sPagesize;
    
    
    private String ip;
    private String mac;
    
    
    private Integer gift_id;
	
    private String gift_name;   //		    NVARCHAR(100)   NOT NULL,
	private BigDecimal gift_price ;//         DECIMAL(16,3),  --����۸�
	private Integer gift_number;//			INTEGER,      --��������
	private Integer purchase_time ;//      DATETIME NOT NULL,  --����ʱ�� 
	private String gift_summary;//       NVARCHAR(500),       --���ﱸע
	private Integer purchase_start_date;
	private Integer purchase_end_date;
    private String detail_info;
	
	private int ifGifiput;
	
	private Integer printFlag;
	
	private Integer move_out_id;
	
	private String provide; //���ŷ�ʽ
	
	private Integer provide_dade;//��������
	
	
	private Integer check_flag1;
	
	private Integer check_flag2;
	
	 
	private Integer check_man1  ;       // INTEGER NOT NULL DEFAULT 0,  --���������
	private String check_man1_explain;  //NVARCHAR(200),    
	
	
	private Integer check_man2  ;       // INTEGER NOT NULL DEFAULT 0,  --���������
	private String check_man2_explain;
	
	private Integer gift_modi_flag;
	
	
	private Integer laundering_item;//��ϴǮ������ID:0���ѯ����Ҫ����
	private String base_item_name;//����Ҫ��������
	private Integer laundering_para;//��ϴǮ������	
	private Integer value; //��ϴǮ�������������ֵ

	private Integer sl_status;
	
	
	
	
	
	
	/**
	 * @return ���� laundering_para��
	 */
	public Integer getLaundering_para() {
		return laundering_para;
	}
	/**
	 * @param laundering_para Ҫ���õ� laundering_para��
	 */
	public void setLaundering_para(Integer laundering_para) {
		this.laundering_para = laundering_para;
	}
	/**
	 * @return ���� value��
	 */
	public Integer getValue() {
		return value;
	}
	/**
	 * @param value Ҫ���õ� value��
	 */
	public void setValue(Integer value) {
		this.value = value;
	}
	/**
	 * @return ���� base_item_name��
	 */
	public String getBase_item_name() {
		return base_item_name;
	}
	/**
	 * @param base_item_name Ҫ���õ� base_item_name��
	 */
	public void setBase_item_name(String base_item_name) {
		this.base_item_name = base_item_name;
	}
	/**
	 * @return ���� laundering_item��
	 */
	public Integer getLaundering_item() {
		return laundering_item;
	}
	/**
	 * @param laundering_item Ҫ���õ� laundering_item��
	 */
	public void setLaundering_item(Integer laundering_item) {
		this.laundering_item = laundering_item;
	}
	/**
	 * @return ���� gift_modi_flag��
	 */
	public Integer getGift_modi_flag() {
		return gift_modi_flag;
	}
	/**
	 * @param gift_modi_flag Ҫ���õ� gift_modi_flag��
	 */
	public void setGift_modi_flag(Integer gift_modi_flag) {
		this.gift_modi_flag = gift_modi_flag;
	}
	/**
	 * @return ���� check_man1��
	 */
	public Integer getCheck_man1() {
		return check_man1;
	}
	/**
	 * @param check_man1 Ҫ���õ� check_man1��
	 */
	public void setCheck_man1(Integer check_man1) {
		this.check_man1 = check_man1;
	}
	/**
	 * @return ���� check_man1_explain��
	 */
	public String getCheck_man1_explain() {
		return check_man1_explain;
	}
	/**
	 * @param check_man1_explain Ҫ���õ� check_man1_explain��
	 */
	public void setCheck_man1_explain(String check_man1_explain) {
		this.check_man1_explain = check_man1_explain;
	}
	/**
	 * @return ���� check_man2��
	 */
	public Integer getCheck_man2() {
		return check_man2;
	}
	/**
	 * @param check_man2 Ҫ���õ� check_man2��
	 */
	public void setCheck_man2(Integer check_man2) {
		this.check_man2 = check_man2;
	}
	/**
	 * @return ���� check_man2_explain��
	 */
	public String getCheck_man2_explain() {
		return check_man2_explain;
	}
	/**
	 * @param check_man2_explain Ҫ���õ� check_man2_explain��
	 */
	public void setCheck_man2_explain(String check_man2_explain) {
		this.check_man2_explain = check_man2_explain;
	}
	/**
	 * @return ���� check_flag1��
	 */
	public Integer getCheck_flag1() {
		return check_flag1;
	}
	/**
	 * @param check_flag1 Ҫ���õ� check_flag1��
	 */
	public void setCheck_flag1(Integer check_flag1) {
		this.check_flag1 = check_flag1;
	}
	/**
	 * @return ���� check_flag2��
	 */
	public Integer getCheck_flag2() {
		return check_flag2;
	}
	/**
	 * @param check_flag2 Ҫ���õ� check_flag2��
	 */
	public void setCheck_flag2(Integer check_flag2) {
		this.check_flag2 = check_flag2;
	}
	/**
	 * @return ���� provide_dade��
	 */
	public Integer getProvide_dade() {
		return provide_dade;
	}
	/**
	 * @param provide_dade Ҫ���õ� provide_dade��
	 */
	public void setProvide_dade(Integer provide_dade) {
		this.provide_dade = provide_dade;
	}
	/**
	 * @return ���� provide��
	 */
	public String getProvide() {
		return provide;
	}
	/**
	 * @param provide Ҫ���õ� provide��
	 */
	public void setProvide(String provide) {
		this.provide = provide;
	}
	/**
	 * @return ���� move_out_id��
	 */
	public Integer getMove_out_id() {
		return move_out_id;
	}
	/**
	 * @param move_out_id Ҫ���õ� move_out_id��
	 */
	public void setMove_out_id(Integer move_out_id) {
		this.move_out_id = move_out_id;
	}
	/**
	 * @return ���� printFlag��
	 */
	public Integer getPrintFlag() {
		return printFlag;
	}
	/**
	 * @param printFlag Ҫ���õ� printFlag��
	 */
	public void setPrintFlag(Integer printFlag) {
		this.printFlag = printFlag;
	}
	/**
	 * @return ���� gift_id��
	 */
	public Integer getGift_id() {
		return gift_id;
	}
	/**
	 * @param gift_id Ҫ���õ� gift_id��
	 */
	public void setGift_id(Integer gift_id) {
		this.gift_id = gift_id;
	}
	
	/**
	 * @return ���� detail_info��
	 */
	public String getDetail_info() {
		return detail_info;
	}
	/**
	 * @param detail_info Ҫ���õ� detail_info��
	 */
	public void setDetail_info(String detail_info) {
		this.detail_info = detail_info;
	}
	/**
	 * @return ���� ifGifiput��
	 */
	public int getIfGifiput() {
		return ifGifiput;
	}
	/**
	 * @param ifGifiput Ҫ���õ� ifGifiput��
	 */
	public void setIfGifiput(int ifGifiput) {
		this.ifGifiput = ifGifiput;
	}
	/**
	 * @return ���� purchase_end_date��
	 */
	public Integer getPurchase_end_date() {
		return purchase_end_date;
	}
	/**
	 * @param purchase_end_date Ҫ���õ� purchase_end_date��
	 */
	public void setPurchase_end_date(Integer purchase_end_date) {
		this.purchase_end_date = purchase_end_date;
	}
	/**
	 * @return ���� purchase_start_date��
	 */
	public Integer getPurchase_start_date() {
		return purchase_start_date;
	}
	/**
	 * @param purchase_start_date Ҫ���õ� purchase_start_date��
	 */
	public void setPurchase_start_date(Integer purchase_start_date) {
		this.purchase_start_date = purchase_start_date;
	}
	/**
	 * @return ���� gift_name��
	 */
	public String getGift_name() {
		return gift_name;
	}
	/**
	 * @param gift_name Ҫ���õ� gift_name��
	 */
	public void setGift_name(String gift_name) {
		this.gift_name = gift_name;
	}
	/**
	 * @return ���� gift_number��
	 */
	public Integer getGift_number() {
		return gift_number;
	}
	/**
	 * @param gift_number Ҫ���õ� gift_number��
	 */
	public void setGift_number(Integer gift_number) {
		this.gift_number = gift_number;
	}
	/**
	 * @return ���� gift_price��
	 */
	public BigDecimal getGift_price() {
		return gift_price;
	}
	/**
	 * @param gift_price Ҫ���õ� gift_price��
	 */
	public void setGift_price(BigDecimal gift_price) {
		this.gift_price = gift_price;
	}
	/**
	 * @return ���� gift_summary��
	 */
	public String getGift_summary() {
		return gift_summary;
	}
	/**
	 * @param gift_summary Ҫ���õ� gift_summary��
	 */
	public void setGift_summary(String gift_summary) {
		this.gift_summary = gift_summary;
	}
	/**
	 * @return ���� purchase_time��
	 */
	public Integer getPurchase_time() {
		return purchase_time;
	}
	/**
	 * @param purchase_time Ҫ���õ� purchase_time��
	 */
	public void setPurchase_time(Integer purchase_time) {
		this.purchase_time = purchase_time;
	}
	/**
	 * @return ���� ip��
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip Ҫ���õ� ip��
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return ���� mac��
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * @param mac Ҫ���õ� mac��
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * @return ���� export_summary��
	 */
	public String getExport_summary() {
		return export_summary;
	}
	/**
	 * @param export_summary Ҫ���õ� export_summary��
	 */
	public void setExport_summary(String export_summary) {
		this.export_summary = export_summary;
	}
  
	/**
	 * @return ���� sPage��
	 */
	public Integer getSPage() {
		return sPage;
	}
	/**
	 * @param page Ҫ���õ� sPage��
	 */
	public void setSPage(Integer page) {
		sPage = page;
	}
	/**
	 * @return ���� sPagesize��
	 */
	public Integer getSPagesize() {
		return sPagesize;
	}
	/**
	 * @param pagesize Ҫ���õ� sPagesize��
	 */
	public void setSPagesize(Integer pagesize) {
		sPagesize = pagesize;
	}
	/**
	 * @return ���� flag��
	 */
	public Integer getFlag() {
		return flag;
	}
	/**
	 * @param flag Ҫ���õ� flag��
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/**
	 * @return ���� key_word��
	 */
	public String getKey_word() {
		return key_word;
	}
	/**
	 * @param key_word Ҫ���õ� key_word��
	 */
	public void setKey_word(String key_word) {
		this.key_word = key_word;
	}
	/**
	 * @return ���� ben_money��
	 */
	public BigDecimal getBen_money() {
		return ben_money;
	}
	/**
	 * @param ben_money Ҫ���õ� ben_money��
	 */
	public void setBen_money(BigDecimal ben_money) {
		this.ben_money = ben_money;
	}
	/**
	 * @return ���� task_date��
	 */
	public Integer getTask_date() {
		return task_date;
	}
	/**
	 * @param task_date Ҫ���õ� task_date��
	 */
	public void setTask_date(Integer task_date) {
		this.task_date = task_date;
	}
	/**
	 * @return ���� date_begin��
	 */
	public Integer getDate_begin() {
		return date_begin;
	}
	/**
	 * @param date_begin Ҫ���õ� date_begin��
	 */
	public void setDate_begin(Integer date_begin) {
		this.date_begin = date_begin;
	}
	
	/**
	 * @return ���� date_end��
	 */
	public Integer getDate_end() {
		return date_end;
	}
	/**
	 * @param date_end Ҫ���õ� date_end��
	 */
	public void setDate_end(Integer date_end) {
		this.date_end = date_end;
	}
	/**
	 * @return ���� cust_flag��
	 */
	public String getCust_flag() {
		return cust_flag;
	}
	/**
	 * @param cust_flag Ҫ���õ� cust_flag��
	 */
	public void setCust_flag(String cust_flag) {
		this.cust_flag = cust_flag;
	}
	/**
	 * @return ���� city��
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city Ҫ���õ� city��
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return ���� district��
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * @param district Ҫ���õ� district��
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	/**
	 * @return ���� list_id��
	 */
	public Integer getList_id() {
		return list_id;
	}
	/**
	 * @param list_id Ҫ���õ� list_id��
	 */
	public void setList_id(Integer list_id) {
		this.list_id = list_id;
	}
	/**
	 * @return ���� province��
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province Ҫ���õ� province��
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return ���� tel��
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel Ҫ���õ� tel��
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return ���� town_detail��
	 */
	public String getTown_detail() {
		return town_detail;
	}
	/**
	 * @param town_detail Ҫ���õ� town_detail��
	 */
	public void setTown_detail(String town_detail) {
		this.town_detail = town_detail;
	}
	/**
	 * @return ���� agent_card_type��
	 */
	public String getAgent_card_type() {
		return agent_card_type;
	}
	/**
	 * @param agent_card_type Ҫ���õ� agent_card_type��
	 */
	public void setAgent_card_type(String agent_card_type) {
		this.agent_card_type = agent_card_type;
	}
	/**
	 * @return ���� agent_card_valid_date��
	 */
	public Integer getAgent_card_valid_date() {
		return agent_card_valid_date;
	}
	/**
	 * @param agent_card_valid_date Ҫ���õ� agent_card_valid_date��
	 */
	public void setAgent_card_valid_date(Integer agent_card_valid_date) {
		this.agent_card_valid_date = agent_card_valid_date;
	}
	/**
	 * @return ���� agent_tel��
	 */
	public String getAgent_tel() {
		return agent_tel;
	}
	/**
	 * @param agent_tel Ҫ���õ� agent_tel��
	 */
	public void setAgent_tel(String agent_tel) {
		this.agent_tel = agent_tel;
	}
	/**
	 * @return ���� legal_card_type��
	 */
	public String getLegal_card_type() {
		return legal_card_type;
	}
	/**
	 * @param legal_card_type Ҫ���õ� legal_card_type��
	 */
	public void setLegal_card_type(String legal_card_type) {
		this.legal_card_type = legal_card_type;
	}
	/**
	 * @return ���� legal_card_valid_date��
	 */
	public Integer getLegal_card_valid_date() {
		return legal_card_valid_date;
	}
	/**
	 * @param legal_card_valid_date Ҫ���õ� legal_card_valid_date��
	 */
	public void setLegal_card_valid_date(Integer legal_card_valid_date) {
		this.legal_card_valid_date = legal_card_valid_date;
	}
	/**
	 * @return ���� managing_scope��
	 */
	public String getManaging_scope() {
		return managing_scope;
	}
	/**
	 * @param managing_scope Ҫ���õ� managing_scope��
	 */
	public void setManaging_scope(String managing_scope) {
		this.managing_scope = managing_scope;
	}
	/**
	 * @return ���� export_flag��
	 */
	public Integer getExport_flag() {
		return export_flag;
	}
	/**
	 * @param export_flag Ҫ���õ� export_flag��
	 */
	public void setExport_flag(Integer export_flag) {
		this.export_flag = export_flag;
	}
	/**
	 * @return ���� true_flag��
	 */
	public Integer getTrue_flag() {
		return true_flag;
	}
	/**
	 * @param true_flag Ҫ���õ� true_flag��
	 */
	public void setTrue_flag(Integer true_flag) {
		this.true_flag = true_flag;
	}
	/**
	 * @return ���� agent_card_id��
	 */
	public String getAgent_card_id() {
		return agent_card_id;
	}
	/**
	 * @param agent_card_id Ҫ���õ� agent_card_id��
	 */
	public void setAgent_card_id(String agent_card_id) {
		this.agent_card_id = agent_card_id;
	}
	/**
	 * @return ���� agent_name��
	 */
	public String getAgent_name() {
		return agent_name;
	}
	/**
	 * @param agent_name Ҫ���õ� agent_name��
	 */
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	/**
	 * @return ���� business_code��
	 */
	public String getBusiness_code() {
		return business_code;
	}
	/**
	 * @param business_code Ҫ���õ� business_code��
	 */
	public void setBusiness_code(String business_code) {
		this.business_code = business_code;
	}
	/**
	 * @return ���� legal_card_id��
	 */
	public String getLegal_card_id() {
		return legal_card_id;
	}
	/**
	 * @param legal_card_id Ҫ���õ� legal_card_id��
	 */
	public void setLegal_card_id(String legal_card_id) {
		this.legal_card_id = legal_card_id;
	}
	/**
	 * @return ���� legal_name��
	 */
	public String getLegal_name() {
		return legal_name;
	}
	/**
	 * @param legal_name Ҫ���õ� legal_name��
	 */
	public void setLegal_name(String legal_name) {
		this.legal_name = legal_name;
	}
	/**
	 * @return ���� organ_code��
	 */
	public String getOrgan_code() {
		return organ_code;
	}
	/**
	 * @param organ_code Ҫ���õ� organ_code��
	 */
	public void setOrgan_code(String organ_code) {
		this.organ_code = organ_code;
	}
	/**
	 * @return ���� partner_card_id��
	 */
	public String getPartner_card_id() {
		return partner_card_id;
	}
	/**
	 * @param partner_card_id Ҫ���õ� partner_card_id��
	 */
	public void setPartner_card_id(String partner_card_id) {
		this.partner_card_id = partner_card_id;
	}
	/**
	 * @return ���� tax_reg_code��
	 */
	public String getTax_reg_code() {
		return tax_reg_code;
	}
	/**
	 * @param tax_reg_code Ҫ���õ� tax_reg_code��
	 */
	public void setTax_reg_code(String tax_reg_code) {
		this.tax_reg_code = tax_reg_code;
	}
	/**
	 * @return ���� corp_branch��
	 */
	public String getCorp_branch() {
		return corp_branch;
	}
	/**
	 * @param corp_branch Ҫ���õ� corp_branch��
	 */
	public void setCorp_branch(String corp_branch) {
		this.corp_branch = corp_branch;
	}
	/**
	 * @return ���� cust_corp_nature��
	 */
	public String getCust_corp_nature() {
		return cust_corp_nature;
	}
	/**
	 * @param cust_corp_nature Ҫ���õ� cust_corp_nature��
	 */
	public void setCust_corp_nature(String cust_corp_nature) {
		this.cust_corp_nature = cust_corp_nature;
	}
	/**
	 * @return ���� cust_industry��
	 */
	public String getCust_industry() {
		return cust_industry;
	}
	/**
	 * @param cust_industry Ҫ���õ� cust_industry��
	 */
	public void setCust_industry(String cust_industry) {
		this.cust_industry = cust_industry;
	}
	/**
	 * @return ���� fc_card_id��
	 */
	public String getFc_card_id() {
		return fc_card_id;
	}
	/**
	 * @param fc_card_id Ҫ���õ� fc_card_id��
	 */
	public void setFc_card_id(String fc_card_id) {
		this.fc_card_id = fc_card_id;
	}
	/**
	 * @return ���� fc_card_type��
	 */
	public String getFc_card_type() {
		return fc_card_type;
	}
	/**
	 * @param fc_card_type Ҫ���õ� fc_card_type��
	 */
	public void setFc_card_type(String fc_card_type) {
		this.fc_card_type = fc_card_type;
	}
	/**
	 * @return ���� fc_card_valid_date��
	 */
	public Integer getFc_card_valid_date() {
		return fc_card_valid_date;
	}
	/**
	 * @param fc_card_valid_date Ҫ���õ� fc_card_valid_date��
	 */
	public void setFc_card_valid_date(Integer fc_card_valid_date) {
		this.fc_card_valid_date = fc_card_valid_date;
	}
	/**
	 * @return ���� grade_score��
	 */
	public String getGrade_score() {
		return grade_score;
	}
	/**
	 * @param grade_score Ҫ���õ� grade_score��
	 */
	public void setGrade_score(String grade_score) {
		this.grade_score = grade_score;
	}
	/**
	 * @return ���� industry_post��
	 */
	public String getIndustry_post() {
		return industry_post;
	}
	/**
	 * @param industry_post Ҫ���õ� industry_post��
	 */
	public void setIndustry_post(String industry_post) {
		this.industry_post = industry_post;
	}
	/**
	 * @return ���� legal_mobile��
	 */
	public String getLegal_mobile() {
		return legal_mobile;
	}
	/**
	 * @param legal_mobile Ҫ���õ� legal_mobile��
	 */
	public void setLegal_mobile(String legal_mobile) {
		this.legal_mobile = legal_mobile;
	}
	/**
	 * @return ���� legal_post_code��
	 */
	public String getLegal_post_code() {
		return legal_post_code;
	}
	/**
	 * @param legal_post_code Ҫ���õ� legal_post_code��
	 */
	public void setLegal_post_code(String legal_post_code) {
		this.legal_post_code = legal_post_code;
	}
	/**
	 * @return ���� legal_tel��
	 */
	public String getLegal_tel() {
		return legal_tel;
	}
	/**
	 * @param legal_tel Ҫ���õ� legal_tel��
	 */
	public void setLegal_tel(String legal_tel) {
		this.legal_tel = legal_tel;
	}
	/**
	 * @return ���� link_gd_money��
	 */
	public BigDecimal getLink_gd_money() {
		return link_gd_money;
	}
	/**
	 * @param link_gd_money Ҫ���õ� link_gd_money��
	 */
	public void setLink_gd_money(BigDecimal link_gd_money) {
		this.link_gd_money = link_gd_money;
	}
	/**
	 * @return ���� link_type��
	 */
	public Integer getLink_type() {
		return link_type;
	}
	/**
	 * @param link_type Ҫ���õ� link_type��
	 */
	public void setLink_type(Integer link_type) {
		this.link_type = link_type;
	}
	/**
	 * @return ���� register_address��
	 */
	public String getRegister_address() {
		return register_address;
	}
	/**
	 * @param register_address Ҫ���õ� register_address��
	 */
	public void setRegister_address(String register_address) {
		this.register_address = register_address;
	}
	/**
	 * @return ���� register_post_code��
	 */
	public String getRegister_post_code() {
		return register_post_code;
	}
	/**
	 * @param register_post_code Ҫ���õ� register_post_code��
	 */
	public void setRegister_post_code(String register_post_code) {
		this.register_post_code = register_post_code;
	}
	/**
	 * @return ���� service_man_tel��
	 */
	public String getService_man_tel() {
		return service_man_tel;
	}
	/**
	 * @param service_man_tel Ҫ���õ� service_man_tel��
	 */
	public void setService_man_tel(String service_man_tel) {
		this.service_man_tel = service_man_tel;
	}
	/**
	 * @return ���� summary1��
	 */
	public String getSummary1() {
		return summary1;
	}
	/**
	 * @param summary1 Ҫ���õ� summary1��
	 */
	public void setSummary1(String summary1) {
		this.summary1 = summary1;
	}
	/**
	 * @return ���� summary2��
	 */
	public String getSummary2() {
		return summary2;
	}
	/**
	 * @param summary2 Ҫ���õ� summary2��
	 */
	public void setSummary2(String summary2) {
		this.summary2 = summary2;
	}
	/**
	 * @return ���� trans_address��
	 */
	public String getTrans_address() {
		return trans_address;
	}
	/**
	 * @param trans_address Ҫ���õ� trans_address��
	 */
	public void setTrans_address(String trans_address) {
		this.trans_address = trans_address;
	}
	/**
	 * @return ���� trans_mobile��
	 */
	public String getTrans_mobile() {
		return trans_mobile;
	}
	/**
	 * @param trans_mobile Ҫ���õ� trans_mobile��
	 */
	public void setTrans_mobile(String trans_mobile) {
		this.trans_mobile = trans_mobile;
	}
	/**
	 * @return ���� trans_name��
	 */
	public String getTrans_name() {
		return trans_name;
	}
	/**
	 * @param trans_name Ҫ���õ� trans_name��
	 */
	public void setTrans_name(String trans_name) {
		this.trans_name = trans_name;
	}
	/**
	 * @return ���� trans_post_code��
	 */
	public String getTrans_post_code() {
		return trans_post_code;
	}
	/**
	 * @param trans_post_code Ҫ���õ� trans_post_code��
	 */
	public void setTrans_post_code(String trans_post_code) {
		this.trans_post_code = trans_post_code;
	}
	/**
	 * @return ���� trans_tel��
	 */
	public String getTrans_tel() {
		return trans_tel;
	}
	/**
	 * @param trans_tel Ҫ���õ� trans_tel��
	 */
	public void setTrans_tel(String trans_tel) {
		this.trans_tel = trans_tel;
	}
	/**
	 * @return ���� change_check_flag��
	 */
	public Integer getChange_check_flag() {
		return change_check_flag;
	}
	/**
	 * @param change_check_flag Ҫ���õ� change_check_flag��
	 */
	public void setChange_check_flag(Integer change_check_flag) {
		this.change_check_flag = change_check_flag;
	}
	/**
	 * @return ���� cust_age_group��
	 */
	public String getCust_age_group() {
		return cust_age_group;
	}
	/**
	 * @param cust_age_group Ҫ���õ� cust_age_group��
	 */
	public void setCust_age_group(String cust_age_group) {
		this.cust_age_group = cust_age_group;
	}
	/**
	 * @return ���� cust_num��
	 */
	public String getCust_aum() {
		return cust_aum;
	}
	/**
	 * @param cust_num Ҫ���õ� cust_num��
	 */
	public void setCust_aum(String cust_aum) {
		this.cust_aum = cust_aum;
	}
	/**
	 * @return ���� cust_source_explain��
	 */
	public String getCust_source_explain() {
		return cust_source_explain;
	}
	/**
	 * @param cust_source_explain Ҫ���õ� cust_source_explain��
	 */
	public void setCust_source_explain(String cust_source_explain) {
		this.cust_source_explain = cust_source_explain;
	}
	/**
	 * @return ���� inves_experinc��
	 */
	public String getInves_experinc() {
		return inves_experinc;
	}
	/**
	 * @param inves_experinc Ҫ���õ� inves_experinc��
	 */
	public void setInves_experinc(String inves_experinc) {
		this.inves_experinc = inves_experinc;
	}
	/**
	 * @return ���� end_date��
	 */
	public Integer getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date Ҫ���õ� end_date��
	 */
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return ���� start_date��
	 */
	public Integer getStart_date() {
		return start_date;
	}
	/**
	 * @param start_date Ҫ���õ� start_date��
	 */
	public void setStart_date(Integer start_date) {
		this.start_date = start_date;
	}
	/**
	 * @return ���� company_depart��
	 */
	public String getCompany_depart() {
		return company_depart;
	}
	/**
	 * @param company_depart Ҫ���õ� company_depart��
	 */
	public void setCompany_depart(String company_depart) {
		this.company_depart = company_depart;
	}
	/**
	 * @return ���� company_position��
	 */
	public String getCompany_position() {
		return company_position;
	}
	/**
	 * @param company_position Ҫ���õ� company_position��
	 */
	public void setCompany_position(String company_position) {
		this.company_position = company_position;
	}
	/**
	 * @return ���� company_unit��
	 */
	public String getCompany_unit() {
		return company_unit;
	}
	/**
	 * @param company_unit Ҫ���õ� company_unit��
	 */
	public void setCompany_unit(String company_unit) {
		this.company_unit = company_unit;
	}
	/**
	 * @return ���� product_name��
	 */
	public String getProduct_name() {
		return product_name;
	}
	/**
	 * @param product_name Ҫ���õ� product_name��
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getProv_flag() {
		return prov_flag;
	}
	public void setProv_flag(Integer provFlag) {
		prov_flag = provFlag;
	}
	public Integer getSub_product_id() {
		return sub_product_id;
	}
	public void setSub_product_id(Integer subProductId) {
		sub_product_id = subProductId;
	}
	public Integer getStart_current_score() {
		return start_current_score;
	}
	public void setStart_current_score(Integer startCurrentScore) {
		start_current_score = startCurrentScore;
	}
	public Integer getEnd_current_score() {
		return end_current_score;
	}
	public void setEnd_current_score(Integer endCurrentScore) {
		end_current_score = endCurrentScore;
	}
	public BigDecimal getBurend_of_debt() {
		return burend_of_debt;
	}
	public void setBurend_of_debt(BigDecimal burendOfDebt) {
		burend_of_debt = burendOfDebt;
	}
	public BigDecimal getFree_cash_flow() {
		return free_cash_flow;
	}
	public void setFree_cash_flow(BigDecimal freeCashFlow) {
		free_cash_flow = freeCashFlow;
	}
	public String getProductTo() {
		return productTo;
	}
	public void setProductTo(String productTo) {
		this.productTo = productTo;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getStart_age() {
		return start_age;
	}
	public void setStart_age(Integer startAge) {
		start_age = startAge;
	}
	public Integer getEnd_age() {
		return end_age;
	}
	public void setEnd_age(Integer endAge) {
		end_age = endAge;
	}
	public Integer getCell_id() {
		return cell_id;
	}
	public void setCell_id(Integer cellId) {
		cell_id = cellId;
	}
	public String getClassEs() {
		return classEs;
	}
	public void setClassEs(String classEs) {
		this.classEs = classEs;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getRecommended() {
		return recommended;
	}
	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}
	/**
	 * @return ���� money_source_name��
	 */
	public String getMoney_source_name() {
		return money_source_name;
	}
	/**
	 * @param money_source_name Ҫ���õ� money_source_name��
	 */
	public void setMoney_source_name(String money_source_name) {
		this.money_source_name = money_source_name;
	}
	private String grade_level;
	private String fact_controller; //ʵ�ʿ�����
	//����ȴ���
	private Integer min_times;
	private Integer max_times;
	private String family_name; //��������
	private Integer onlyemail;
	private BigDecimal min_total_money; //�����
	private BigDecimal max_total_money; //��С���
	private Integer only_thisproduct; // ��һ��Ʒ
	private String orderby;
	private BigDecimal ben_amount_min;
	private BigDecimal ben_amount_max;
	private Integer form_cust_id;
	private Integer repaeat_flag; //�ظ�����־
	private Integer contactID;
	private InputStream inputStream; //�ϴ�ͼƬ��ת���ɶ�������-֤��1
	private InputStream inputStream1; //�ϴ�ͼƬ��ת���ɶ�������-֤��2
	private String isClient;//�Ƿ�ί����
	private String service_man_name;//�ͻ���������
	private String area;
	private BigDecimal potenital_money;
	private Integer is_deal;
	private String gov_prov_regional;//ʡ����������(9999)
	private String gov_prov_regional_name;//ʡ����������˵��
	private String gov_regional;//��������
	private String gov_regional_name;//��������˵��
	private Integer groupID;
	private String mobile2;
	private Integer query_flag;
	
	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}
	/**
	 * @return ���� gov_prov_regional��
	 */
	public String getGov_prov_regional() {
		return gov_prov_regional;
	}
	/**
	 * @param gov_prov_regional Ҫ���õ� gov_prov_regional��
	 */
	public void setGov_prov_regional(String gov_prov_regional) {
		this.gov_prov_regional = gov_prov_regional;
	}
	/**
	 * @return ���� gov_prov_regional_name��
	 */
	public String getGov_prov_regional_name() {
		return gov_prov_regional_name;
	}
	/**
	 * @param gov_prov_regional_name Ҫ���õ� gov_prov_regional_name��
	 */
	public void setGov_prov_regional_name(String gov_prov_regional_name) {
		this.gov_prov_regional_name = gov_prov_regional_name;
	}
	/**
	 * @return ���� gov_regional��
	 */
	public String getGov_regional() {
		return gov_regional;
	}
	/**
	 * @param gov_regional Ҫ���õ� gov_regional��
	 */
	public void setGov_regional(String gov_regional) {
		this.gov_regional = gov_regional;
	}
	/**
	 * @return ���� gov_regional_name��
	 */
	public String getGov_regional_name() {
		return gov_regional_name;
	}
	/**
	 * @param gov_regional_name Ҫ���õ� gov_regional_name��
	 */
	public void setGov_regional_name(String gov_regional_name) {
		this.gov_regional_name = gov_regional_name;
	}
	/**
	 * @return
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getBack_amount() {
		return back_amount;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getBen_amount() {
		return ben_amount;
	}

	/**
	 * @return
	 */
	public Integer getBirthday() {
		return birthday;
	}

	/**
	 * @return
	 */
	public int getBirthday_temp() {
		return birthday_temp;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBook_code() {
		return book_code;
	}

	/**
	 * @return
	 */
	public String getBp() {
		return bp;
	}

	/**
	 * @return
	 */
	public Integer getCancel_man() {
		return cancel_man;
	}

	/**
	 * @return
	 */
	public Integer getCancel_time() {
		return cancel_time;
	}

	/**
	 * @return
	 */
	public java.lang.String getCard_id() {
		return card_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getCard_type() {
		return card_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getCard_type_name() {
		return card_type_name;
	}

	/**
	 * @return
	 */
	public Integer getCard_valid_date() {
		return card_valid_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCheck_flag() {
		return check_flag;
	}

	/**
	 * @return
	 */
	public Integer getCheck_man() {
		return check_man;
	}

	/**
	 * @return
	 */
	public String getContact_man() {
		return contact_man;
	}

	/**
	 * @return
	 */
	public String getContract_bh() {
		return contract_bh;
	}

	/**
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getCurr_to_amount() {
		return curr_to_amount;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getCurrent_money() {
		return current_money;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCust_id() {
		return cust_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_level() {
		return cust_level;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_level_name() {
		return cust_level_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_name() {
		return cust_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_no() {
		return cust_no;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_source() {
		return cust_source;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_source_name() {
		return cust_source_name;
	}

	/**
	 * @return
	 */
	public String getCust_tel() {
		return cust_tel;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCust_type() {
		return cust_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_type_name() {
		return cust_type_name;
	}

	/**
	 * @return
	 */
	public String getE_mail() {
		return e_mail;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getExchange_amount() {
		return exchange_amount;
	}

	/**
	 * @return
	 */
	public String getFact_controller() {
		return fact_controller;
	}

	/**
	 * @return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @return
	 */
	public Integer getFrom_cust_id() {
		return from_cust_id;
	}

	/**
	 * @return
	 */
	public String getGrade_level() {
		return grade_level;
	}

	/**
	 * @return
	 */
	public String getH_tel() {
		return h_tel;
	}

	/**
	 * @return
	 */
	public String getHgtzr_bh() {
		return hgtzr_bh;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public java.sql.Timestamp getInput_time() {
		return input_time;
	}

	/**
	 * @return
	 */
	public Integer getIs_link() {
		return is_link;
	}

	/**
	 * @return
	 */
	public String getJg_cust_type() {
		return jg_cust_type;
	}

	/**
	 * @return
	 */
	public String getLast_product_name() {
		return last_product_name;
	}

	/**
	 * @return
	 */
	public Integer getLast_rg_date() {
		return last_rg_date;
	}

	/**
	 * @return
	 */
	public String getLegal_address() {
		return legal_address;
	}

	/**
	 * @return
	 */
	public String getLegal_man() {
		return legal_man;
	}

	/**
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return
	 */
	public int getModi_flag() {
		return modi_flag;
	}

	/**
	 * @return
	 */
	public Integer getModi_man() {
		return modi_man;
	}

	/**
	 * @return
	 */
	public Integer getModi_time() {
		return modi_time;
	}

	/**
	 * @return
	 */
	public String getMoney_source() {
		return money_source;
	}

	/**
	 * @return
	 */
	public String getO_tel() {
		return o_tel;
	}

	/**
	 * @return
	 */
	public String getPost_address() {
		return post_address;
	}

	/**
	 * @return
	 */
	public String getPost_address2() {
		return post_address2;
	}

	/**
	 * @return
	 */
	public String getPost_code() {
		return post_code;
	}

	/**
	 * @return
	 */
	public String getPost_code2() {
		return post_code2;
	}

	/**
	 * @return
	 */
	public Integer getPrint_deploy_bill() {
		return print_deploy_bill;
	}

	/**
	 * @return
	 */
	public Integer getPrint_post_info() {
		return print_post_info;
	}

	/**
	 * @return
	 */
	public Integer getProduct_id() {
		return product_id;
	}

	/**
	 * @return
	 */
	public String getProv_level() {
		return prov_level;
	}

	/**
	 * @return
	 */
	public Integer getRg_times() {
		return rg_times;
	}

	/**
	 * @return
	 */
	public Integer getService_man() {
		return service_man;
	}

	/**
	 * @return
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @return
	 */
	public String getSex_name() {
		return sex_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getStatus() {
		return status;
	}

	/**
	 * @return
	 */
	public java.lang.String getStatus_name() {
		return status_name;
	}

	/**
	 * @return
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @return
	 */
	public Integer getTo_cust_id() {
		return to_cust_id;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getTotal_money() {
		return total_money;
	}

	/**
	 * @return
	 */
	public java.lang.String getTouch_type() {
		return touch_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getTouch_type_name() {
		return touch_type_name;
	}

	/**
	 * @return
	 */
	public Integer getTzr_flag() {
		return tzr_flag;
	}

	/**
	 * @return
	 */
	public String getVip_card_id() {
		return vip_card_id;
	}

	/**
	 * @return
	 */
	public Integer getVip_date() {
		return vip_date;
	}

	/**
	 * @return
	 */
	public String getVoc_type() {
		return voc_type;
	}

	/**
	 * @return
	 */
	public String getVoc_type_name() {
		return voc_type_name;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getWt_flag() {
		return wt_flag;
	}

	/**
	 * @return
	 */
	public java.lang.String getWt_flag_name() {
		return wt_flag_name;
	}

	/**
	 * @param integer
	 */
	public void setAge(Integer integer) {
		age = integer;
	}

	/**
	 * @param decimal
	 */
	public void setBack_amount(java.math.BigDecimal decimal) {
		back_amount = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setBen_amount(java.math.BigDecimal decimal) {
		ben_amount = decimal;
	}

	/**
	 * @param integer
	 */
	public void setBirthday(Integer integer) {
		birthday = integer;
	}

	/**
	 * @param i
	 */
	public void setBirthday_temp(int i) {
		birthday_temp = i;
	}

	/**
	 * @param integer
	 */
	public void setBook_code(java.lang.Integer integer) {
		book_code = integer;
	}

	/**
	 * @param string
	 */
	public void setBp(String string) {
		bp = string;
	}

	/**
	 * @param integer
	 */
	public void setCancel_man(Integer integer) {
		cancel_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setCancel_time(Integer integer) {
		cancel_time = integer;
	}

	/**
	 * @param string
	 */
	public void setCard_id(java.lang.String string) {
		card_id = string;
	}

	/**
	 * @param string
	 */
	public void setCard_type(java.lang.String string) {
		card_type = string;
	}

	/**
	 * @param string
	 */
	public void setCard_type_name(java.lang.String string) {
		card_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setCard_valid_date(Integer integer) {
		card_valid_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_flag(java.lang.Integer integer) {
		check_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_man(Integer integer) {
		check_man = integer;
	}

	/**
	 * @param string
	 */
	public void setContact_man(String string) {
		contact_man = string;
	}

	/**
	 * @param string
	 */
	public void setContract_bh(String string) {
		contract_bh = string;
	}

	/**
	 * @param string
	 */
	public void setCountry(String string) {
		country = string;
	}

	/**
	 * @param decimal
	 */
	public void setCurr_to_amount(java.math.BigDecimal decimal) {
		curr_to_amount = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setCurrent_money(java.math.BigDecimal decimal) {
		current_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setCust_id(java.lang.Integer integer) {
		cust_id = integer;
	}

	/**
	 * @param string
	 */
	public void setCust_level(java.lang.String string) {
		cust_level = string;
	}

	/**
	 * @param string
	 */
	public void setCust_level_name(java.lang.String string) {
		cust_level_name = string;
	}

	/**
	 * @param string
	 */
	public void setCust_name(java.lang.String string) {
		cust_name = string;
	}

	/**
	 * @param string
	 */
	public void setCust_no(java.lang.String string) {
		cust_no = string;
	}

	/**
	 * @param string
	 */
	public void setCust_source(java.lang.String string) {
		cust_source = string;
	}

	/**
	 * @param string
	 */
	public void setCust_source_name(java.lang.String string) {
		cust_source_name = string;
	}

	/**
	 * @param string
	 */
	public void setCust_tel(String string) {
		cust_tel = string;
	}

	/**
	 * @param integer
	 */
	public void setCust_type(java.lang.Integer integer) {
		cust_type = integer;
	}

	/**
	 * @param string
	 */
	public void setCust_type_name(java.lang.String string) {
		cust_type_name = string;
	}

	/**
	 * @param string
	 */
	public void setE_mail(String string) {
		e_mail = string;
	}

	/**
	 * @param decimal
	 */
	public void setExchange_amount(java.math.BigDecimal decimal) {
		exchange_amount = decimal;
	}

	/**
	 * @param string
	 */
	public void setFact_controller(String string) {
		fact_controller = string;
	}

	/**
	 * @param string
	 */
	public void setFax(String string) {
		fax = string;
	}

	/**
	 * @param integer
	 */
	public void setFrom_cust_id(Integer integer) {
		from_cust_id = integer;
	}

	/**
	 * @param string
	 */
	public void setGrade_level(String string) {
		grade_level = string;
	}

	/**
	 * @param string
	 */
	public void setH_tel(String string) {
		h_tel = string;
	}

	/**
	 * @param string
	 */
	public void setHgtzr_bh(String string) {
		hgtzr_bh = string;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(java.lang.Integer integer) {
		input_man = integer;
	}

	/**
	 * @param timestamp
	 */
	public void setInput_time(java.sql.Timestamp timestamp) {
		input_time = timestamp;
	}

	/**
	 * @param integer
	 */
	public void setIs_link(Integer integer) {
		is_link = integer;
	}

	/**
	 * @param string
	 */
	public void setJg_cust_type(String string) {
		jg_cust_type = string;
	}

	/**
	 * @param string
	 */
	public void setLast_product_name(String string) {
		last_product_name = string;
	}

	/**
	 * @param integer
	 */
	public void setLast_rg_date(Integer integer) {
		last_rg_date = integer;
	}

	/**
	 * @param string
	 */
	public void setLegal_address(String string) {
		legal_address = string;
	}

	/**
	 * @param string
	 */
	public void setLegal_man(String string) {
		legal_man = string;
	}

	/**
	 * @param string
	 */
	public void setMobile(String string) {
		mobile = string;
	}

	/**
	 * @param i
	 */
	public void setModi_flag(int i) {
		modi_flag = i;
	}

	/**
	 * @param integer
	 */
	public void setModi_man(Integer integer) {
		modi_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setModi_time(Integer integer) {
		modi_time = integer;
	}

	/**
	 * @param string
	 */
	public void setMoney_source(String string) {
		money_source = string;
	}

	/**
	 * @param string
	 */
	public void setO_tel(String string) {
		o_tel = string;
	}

	/**
	 * @param string
	 */
	public void setPost_address(String string) {
		post_address = string;
	}

	/**
	 * @param string
	 */
	public void setPost_address2(String string) {
		post_address2 = string;
	}

	/**
	 * @param string
	 */
	public void setPost_code(String string) {
		post_code = string;
	}

	/**
	 * @param string
	 */
	public void setPost_code2(String string) {
		post_code2 = string;
	}

	/**
	 * @param integer
	 */
	public void setPrint_deploy_bill(Integer integer) {
		print_deploy_bill = integer;
	}

	/**
	 * @param integer
	 */
	public void setPrint_post_info(Integer integer) {
		print_post_info = integer;
	}

	/**
	 * @param integer
	 */
	public void setProduct_id(Integer integer) {
		product_id = integer;
	}

	/**
	 * @param string
	 */
	public void setProv_level(String string) {
		prov_level = string;
	}

	/**
	 * @param integer
	 */
	public void setRg_times(Integer integer) {
		rg_times = integer;
	}

	/**
	 * @param integer
	 */
	public void setService_man(Integer integer) {
		service_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setSex(Integer integer) {
		sex = integer;
	}

	/**
	 * @param string
	 */
	public void setSex_name(String string) {
		sex_name = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(java.lang.String string) {
		status = string;
	}

	/**
	 * @param string
	 */
	public void setStatus_name(java.lang.String string) {
		status_name = string;
	}

	/**
	 * @param string
	 */
	public void setSummary(String string) {
		summary = string;
	}

	/**
	 * @param integer
	 */
	public void setTo_cust_id(Integer integer) {
		to_cust_id = integer;
	}

	/**
	 * @param decimal
	 */
	public void setTotal_money(java.math.BigDecimal decimal) {
		total_money = decimal;
	}

	/**
	 * @param string
	 */
	public void setTouch_type(java.lang.String string) {
		touch_type = string;
	}

	/**
	 * @param string
	 */
	public void setTouch_type_name(java.lang.String string) {
		touch_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setTzr_flag(Integer integer) {
		tzr_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setVip_card_id(String string) {
		vip_card_id = string;
	}

	/**
	 * @param integer
	 */
	public void setVip_date(Integer integer) {
		vip_date = integer;
	}

	/**
	 * @param string
	 */
	public void setVoc_type(String string) {
		voc_type = string;
	}

	/**
	 * @param string
	 */
	public void setVoc_type_name(String string) {
		voc_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setWt_flag(java.lang.Integer integer) {
		wt_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setWt_flag_name(java.lang.String string) {
		wt_flag_name = string;
	}

	/**
	 * @return
	 */
	public Integer getMax_times() {
		return max_times;
	}

	/**
	 * @return
	 */
	public Integer getMin_times() {
		return min_times;
	}

	/**
	 * @param integer
	 */
	public void setMax_times(Integer integer) {
		max_times = integer;
	}

	/**
	 * @param integer
	 */
	public void setMin_times(Integer integer) {
		min_times = integer;
	}

	/**
	 * @return
	 */
	public String getFamily_name() {
		return family_name;
	}

	/**
	 * @param string
	 */
	public void setFamily_name(String string) {
		family_name = string;
	}

	/**
	 * @return
	 */
	public Integer getOnlyemail() {
		return onlyemail;
	}

	/**
	 * @param integer
	 */
	public void setOnlyemail(Integer integer) {
		onlyemail = integer;
	}

	/**
	 * @return
	 */
	public BigDecimal getMax_total_money() {
		return max_total_money;
	}

	/**
	 * @return
	 */
	public BigDecimal getMin_total_money() {
		return min_total_money;
	}

	/**
	 * @param double1
	 */
	public void setMax_total_money(BigDecimal double1) {
		max_total_money = double1;
	}

	/**
	 * @param double1
	 */
	public void setMin_total_money(BigDecimal double1) {
		min_total_money = double1;
	}

	/**
	 * @return
	 */
	public Integer getOnly_thisproduct() {
		return only_thisproduct;
	}

	/**
	 * @param integer
	 */
	public void setOnly_thisproduct(Integer integer) {
		only_thisproduct = integer;
	}

	/**
	 * @return
	 */
	public String getOrderby() {
		return orderby;
	}

	/**
	 * @param string
	 */
	public void setOrderby(String string) {
		orderby = string;
	}

	/**
	 * @return
	 */
	public BigDecimal getBen_amount_max() {
		return ben_amount_max;
	}

	/**
	 * @return
	 */
	public BigDecimal getBen_amount_min() {
		return ben_amount_min;
	}

	/**
	 * @param decimal
	 */
	public void setBen_amount_max(BigDecimal decimal) {
		ben_amount_max = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setBen_amount_min(BigDecimal decimal) {
		ben_amount_min = decimal;
	}
	/**
	 * @return
	 */
	public Integer getForm_cust_id() {
		return form_cust_id;
	}

	/**
	 * @return
	 */
	public Integer getRepaeat_flag() {
		return repaeat_flag;
	}

	/**
	 * @param integer
	 */
	public void setForm_cust_id(Integer integer) {
		form_cust_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setRepaeat_flag(Integer integer) {
		repaeat_flag = integer;
	}

	/**
	 * @return
	 */
	public Integer getContactID() {
		return contactID;
	}

	/**
	 * @param integer
	 */
	public void setContactID(Integer integer) {
		contactID = integer;
	}

	/**
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param stream
	 */
	public void setInputStream(InputStream stream) {
		inputStream = stream;
	}


	/**
	 * @return
	 */
	public String getIsClient() {
		return isClient;
	}

	/**
	 * @param string
	 */
	public void setIsClient(String string) {
		isClient = string;
	}

	/**
	 * @return
	 */
	public String getService_man_name() {
		return service_man_name;
	}

	/**
	 * @param string
	 */
	public void setService_man_name(String string) {
		service_man_name = string;
	}

	/**
	 * @return
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @return
	 */
	public BigDecimal getPotenital_money() {
		return potenital_money;
	}

	/**
	 * @param string
	 */
	public void setArea(String string) {
		area = string;
	}

	/**
	 * @param decimal
	 */
	public void setPotenital_money(BigDecimal decimal) {
		potenital_money = decimal;
	}

	/**
	 * @return
	 */
	public Integer getIs_deal() {
		return is_deal;
	}

	/**
	 * @param integer
	 */
	public void setIs_deal(Integer integer) {
		is_deal = integer;
	}
	/**
	 * @return ���� birthday_end��
	 */
	public Integer getBirthday_end() {
		return birthday_end;
	}
	/**
	 * @param birthday_end Ҫ���õ� birthday_end��
	 */
	public void setBirthday_end(Integer birthday_end) {
		this.birthday_end = birthday_end;
	}
	/**
	 * @return ���� rg_begin_date��
	 */
	public Integer getRg_begin_date() {
		return rg_begin_date;
	}
	/**
	 * @param rg_begin_date Ҫ���õ� rg_begin_date��
	 */
	public void setRg_begin_date(Integer rg_begin_date) {
		this.rg_begin_date = rg_begin_date;
	}
	/**
	 * @return ���� rg_end_date��
	 */
	public Integer getRg_end_date() {
		return rg_end_date;
	}
	/**
	 * @param rg_end_date Ҫ���õ� rg_end_date��
	 */
	public void setRg_end_date(Integer rg_end_date) {
		this.rg_end_date = rg_end_date;
	}
	public String getHead_office_cust_id() {
		return head_office_cust_id;
	}
	public void setHead_office_cust_id(String headOfficeCustId) {
		head_office_cust_id = headOfficeCustId;
	}
	public Integer getStature() {
		return stature;
	}
	public void setStature(Integer stature) {
		this.stature = stature;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getSpouse_name() {
		return spouse_name;
	}
	public void setSpouse_name(String spouseName) {
		spouse_name = spouseName;
	}
	public String getSpouse_info() {
		return spouse_info;
	}
	public void setSpouse_info(String spouseInfo) {
		spouse_info = spouseInfo;
	}
	public String getChildren_info() {
		return children_info;
	}
	public void setChildren_info(String childrenInfo) {
		children_info = childrenInfo;
	}
	public String getChildren_name() {
		return children_name;
	}
	public void setChildren_name(String childrenName) {
		children_name = childrenName;
	}
	public Integer getEmployee_num() {
		return employee_num;
	}
	public void setEmployee_num(Integer employeeNum) {
		employee_num = employeeNum;
	}
	public String getHolding() {
		return holding;
	}
	public void setHolding(String holding) {
		this.holding = holding;
	}
	public BigDecimal getPersonal_income() {
		return personal_income;
	}
	public void setPersonal_income(BigDecimal personalIncome) {
		personal_income = personalIncome;
	}
	public BigDecimal getHousehold_income() {
		return household_income;
	}
	public void setHousehold_income(BigDecimal householdIncome) {
		household_income = householdIncome;
	}
	public Integer getFeeding_num_people() {
		return feeding_num_people;
	}
	public void setFeeding_num_people(Integer feedingNumPeople) {
		feeding_num_people = feedingNumPeople;
	}
	public String getMain_source() {
		return main_source;
	}
	public void setMain_source(String mainSource) {
		main_source = mainSource;
	}

	public String getOther_source() {
		return other_source;
	}
	public void setOther_source(String otherSource) {
		other_source = otherSource;
	}
	public String getHouse_position() {
		return house_position;
	}
	public void setHouse_position(String housePosition) {
		house_position = housePosition;
	}
	public Integer getHouse_property() {
		return house_property;
	}
	public void setHouse_property(Integer houseProperty) {
		house_property = houseProperty;
	}
	public Integer getHouse_area() {
		return house_area;
	}
	public void setHouse_area(Integer houseArea) {
		house_area = houseArea;
	}
	public String getPlat_envaluate() {
		return plat_envaluate;
	}
	public void setPlat_envaluate(String platEnvaluate) {
		plat_envaluate = platEnvaluate;
	}
	public BigDecimal getMarket_appraisal() {
		return market_appraisal;
	}
	public void setMarket_appraisal(BigDecimal marketAppraisal) {
		market_appraisal = marketAppraisal;
	}
	public Integer getVehicle_num() {
		return vehicle_num;
	}
	public void setVehicle_num(Integer vehicleNum) {
		vehicle_num = vehicleNum;
	}
	public String getVehicle_make() {
		return vehicle_make;
	}
	public void setVehicle_make(String vehicleMake) {
		vehicle_make = vehicleMake;
	}
	public String getVehicle_type() {
		return vehicle_type;
	}
	public void setVehicle_type(String vehicleType) {
		vehicle_type = vehicleType;
	}
	public String getCredit_type() {
		return credit_type;
	}
	public void setCredit_type(String creditType) {
		credit_type = creditType;
	}
	public String getCredit_type_name() {
		return credit_type_name;
	}
	public void setCredit_type_name(String creditTypeName) {
		credit_type_name = creditTypeName;
	}
	public Integer getCredit_num() {
		return credit_num;
	}
	public void setCredit_num(Integer creditNum) {
		credit_num = creditNum;
	}
	public Integer getCredit_start_date() {
		return credit_start_date;
	}
	public void setCredit_start_date(Integer creditStartDate) {
		credit_start_date = creditStartDate;
	}
	public Integer getCredit_end_date() {
		return credit_end_date;
	}
	public void setCredit_end_date(Integer creditEndDate) {
		credit_end_date = creditEndDate;
	}
	public String getOther_investment_status() {
		return other_investment_status;
	}
	public void setOther_investment_status(String otherInvestmentStatus) {
		other_investment_status = otherInvestmentStatus;
	}
	public String getOther_inve_status_name() {
		return other_inve_status_name;
	}
	public void setOther_inve_status_name(String otherInveStatusName) {
		other_inve_status_name = otherInveStatusName;
	}
	public String getType_pref() {
		return type_pref;
	}
	public void setType_pref(String typePref) {
		type_pref = typePref;
	}
	public String getType_pref_name() {
		return type_pref_name;
	}
	public void setType_pref_name(String typePrefName) {
		type_pref_name = typePrefName;
	}
	public String getTime_limit_pref() {
		return time_limit_pref;
	}
	public void setTime_limit_pref(String timeLimitPref) {
		time_limit_pref = timeLimitPref;
	}
	public String getTime_limit_pref_name() {
		return time_limit_pref_name;
	}
	public void setTime_limit_pref_name(String timeLimitPrefName) {
		time_limit_pref_name = timeLimitPrefName;
	}
	public String getInvest_pref() {
		return invest_pref;
	}
	public void setInvest_pref(String investPref) {
		invest_pref = investPref;
	}
	public String getInvest_pref_name() {
		return invest_pref_name;
	}
	public void setInvest_pref_name(String investPrefName) {
		invest_pref_name = investPrefName;
	}
	public String getHobby_pref() {
		return hobby_pref;
	}
	public void setHobby_pref(String hobbyPref) {
		hobby_pref = hobbyPref;
	}
	public String getHobby_pref_name() {
		return hobby_pref_name;
	}
	public void setHobby_pref_name(String hobbyPrefName) {
		hobby_pref_name = hobbyPrefName;
	}
	public String getService_pref() {
		return service_pref;
	}
	public void setService_pref(String servicePref) {
		service_pref = servicePref;
	}
	public String getService_pref_name() {
		return service_pref_name;
	}
	public void setService_pref_name(String servicePrefName) {
		service_pref_name = servicePrefName;
	}
	public String getContact_pref() {
		return contact_pref;
	}
	public void setContact_pref(String contactPref) {
		contact_pref = contactPref;
	}
	public String getContact_pref_name() {
		return contact_pref_name;
	}
	public void setContact_pref_name(String contactPrefName) {
		contact_pref_name = contactPrefName;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getNation_name() {
		return nation_name;
	}
	public void setNation_name(String nationName) {
		nation_name = nationName;
	}
	public Integer getMarital() {
		return marital;
	}
	public void setMarital(Integer marital) {
		this.marital = marital;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getHealth_name() {
		return health_name;
	}
	public void setHealth_name(String healthName) {
		health_name = healthName;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getEducation_name() {
		return education_name;
	}
	public void setEducation_name(String educationName) {
		education_name = educationName;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getHolderofanoffice() {
		return holderofanoffice;
	}
	public void setHolderofanoffice(String holderofanoffice) {
		this.holderofanoffice = holderofanoffice;
	}
	public String getCompany_character() {
		return company_character;
	}
	public void setCompany_character(String companyCharacter) {
		company_character = companyCharacter;
	}
	public String getCompany_character_name() {
		return company_character_name;
	}
	public void setCompany_character_name(String companyCharacterName) {
		company_character_name = companyCharacterName;
	}
	public Integer getCompany_staff() {
		return company_staff;
	}
	public void setCompany_staff(Integer companyStaff) {
		company_staff = companyStaff;
	}
	public Integer getBocom_staff() {
		return bocom_staff;
	}
	public void setBocom_staff(Integer bocomStaff) {
		bocom_staff = bocomStaff;
	}
	public String getClient_quale() {
		return client_quale;
	}
	public void setClient_quale(String clientQuale) {
		client_quale = clientQuale;
	}
	public String getClient_quale_name() {
		return client_quale_name;
	}
	public void setClient_quale_name(String clientQualeName) {
		client_quale_name = clientQualeName;
	}
	public String getRegistered_place() {
		return registered_place;
	}
	public void setRegistered_place(String registeredPlace) {
		registered_place = registeredPlace;
	}

	public BigDecimal getRegistered_capital() {
		return registered_capital;
	}
	public void setRegistered_capital(BigDecimal registeredCapital) {
		registered_capital = registeredCapital;
	}
	public Integer getCompany_family() {
		return company_family;
	}
	public void setCompany_family(Integer companyFamily) {
		company_family = companyFamily;
	}
	public String getRisk_pref() {
		return risk_pref;
	}
	public void setRisk_pref(String riskPref) {
		risk_pref = riskPref;
	}
	public java.math.BigDecimal getActivity_fee() {
		return activity_fee;
	}
	public void setActivity_fee(java.math.BigDecimal activityFee) {
		activity_fee = activityFee;
	}
	
	

	/**
	 * @return ���� channel��
	 */
	public String getChannel() {
		return channel;
	}
	/**
	 * @param channel Ҫ���õ� channel��
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	/**
	 * @return ���� region��
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region Ҫ���õ� region��
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return ���� mobile2��
	 */
	public String getMobile2() {
		return mobile2;
	}
	/**
	 * @param mobile2 Ҫ���õ� mobile2��
	 */
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	/**
	 * @return ���� complete_flag��
	 */
	public Integer getComplete_flag() {
		return complete_flag;
	}
	/**
	 * @param complete_flag Ҫ���õ� complete_flag��
	 */
	public void setComplete_flag(Integer complete_flag) {
		this.complete_flag = complete_flag;
	}
	
	
	/**
	 * @return ���� change_flag��
	 */
	public int getChange_flag() {
		return change_flag;
	}
	/**
	 * @param change_flag Ҫ���õ� change_flag��
	 */
	public void setChange_flag(int change_flag) {
		this.change_flag = change_flag;
	}
	/**
	 * @return ���� city_name��
	 */
	public String getCity_name() {
		return city_name;
	}
	/**
	 * @param city_name Ҫ���õ� city_name��
	 */
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	/**
	 * @return ���� service_info��
	 */
	public String getService_info() {
		return service_info;
	}
	/**
	 * @param service_info Ҫ���õ� service_info��
	 */
	public void setService_info(String service_info) {
		this.service_info = service_info;
	}
	/**
	 * @return ���� executor��
	 */
	public Integer getExecutor() {
		return executor;
	}
	/**
	 * @param executor Ҫ���õ� executor��
	 */
	public void setExecutor(Integer executor) {
		this.executor = executor;
	}
	/**
	 * @return ���� serial_no��
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no Ҫ���õ� serial_no��
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return ���� data_flag��
	 */
	public String getData_flag() {
		return data_flag;
	}
	/**
	 * @param data_flag Ҫ���õ� data_flag��
	 */
	public void setData_flag(String data_flag) {
		this.data_flag = data_flag;
	}
	/**
	 * @return ���� card_address��
	 */
	public java.lang.String getCard_address() {
		return card_address;
	}
	/**
	 * @param card_address Ҫ���õ� card_address��
	 */
	public void setCard_address(java.lang.String card_address) {
		this.card_address = card_address;
	}
	/**
	 * @return ���� verify_date��
	 */
	public Integer getVerify_date() {
		return verify_date;
	}
	/**
	 * @param verify_date Ҫ���õ� verify_date��
	 */
	public void setVerify_date(Integer verify_date) {
		this.verify_date = verify_date;
	}
	/**
	 * @return ���� verify_state��
	 */
	public Integer getVerify_state() {
		return verify_state;
	}
	/**
	 * @param verify_state Ҫ���õ� verify_state��
	 */
	public void setVerify_state(Integer verify_state) {
		this.verify_state = verify_state;
	} 
	/**
	 * @return ���� issued_date��
	 */
	public Integer getIssued_date() {
		return issued_date;
	}
	/**
	 * @param issued_date Ҫ���õ� issued_date��
	 */
	public void setIssued_date(Integer issued_date) {
		this.issued_date = issued_date;
	}
	/**
	 * @return ���� issued_org��
	 */
	public String getIssued_org() {
		return issued_org;
	}
	/**
	 * @param issued_org Ҫ���õ� issued_org��
	 */
	public void setIssued_org(String issued_org) {
		this.issued_org = issued_org;
	}
	/**
	 * @return ���� valid_date��
	 */
	public Integer getValid_date() {
		return valid_date;
	}
	/**
	 * @param valid_date Ҫ���õ� valid_date��
	 */
	public void setValid_date(Integer valid_date) {
		this.valid_date = valid_date;
	}
	/**
	 * @return ���� inputStream1��
	 */
	public InputStream getInputStream1() {
		return inputStream1;
	}
	/**
	 * @param inputStream1 Ҫ���õ� inputStream1��
	 */
	public void setInputStream1(InputStream inputStream1) {
		this.inputStream1 = inputStream1;
	}
	/**
	 * @return ���� east_jg_type��
	 */
	public String getEast_jg_type() {
		return east_jg_type;
	}
	/**
	 * @param east_jg_type Ҫ���õ� east_jg_type��
	 */
	public void setEast_jg_type(String east_jg_type) {
		this.east_jg_type = east_jg_type;
	}
	/**
	 * @return ���� east_jg_type_name��
	 */
	public String getEast_jg_type_name() {
		return east_jg_type_name;
	}
	/**
	 * @param east_jg_type_name Ҫ���õ� east_jg_type_name��
	 */
	public void setEast_jg_type_name(String east_jg_type_name) {
		this.east_jg_type_name = east_jg_type_name;
	}
	/**
	 * @return ���� jg_wtrlx2��
	 */
	public String getJg_wtrlx2() {
		return jg_wtrlx2;
	}
	/**
	 * @param jg_wtrlx2 Ҫ���õ� jg_wtrlx2��
	 */
	public void setJg_wtrlx2(String jg_wtrlx2) {
		this.jg_wtrlx2 = jg_wtrlx2;
	}
	
	/**
	 * @return ���� check_content��
	 */
	public String getCheck_content() {
		return check_content;
	}
	/**
	 * @param check_content Ҫ���õ� check_content��
	 */
	public void setCheck_content(String check_content) {
		this.check_content = check_content;
	}
	/**
	 * @return ���� comp_type��
	 */
	public Integer getComp_type() {
		return comp_type;
	}
	/**
	 * @param comp_type Ҫ���õ� comp_type��
	 */
	public void setComp_type(Integer comp_type) {
		this.comp_type = comp_type;
	}
	/**
	 * @return ���� content��
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content Ҫ���õ� content��
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return ���� do_result��
	 */
	public String getDo_result() {
		return do_result;
	}
	/**
	 * @param do_result Ҫ���õ� do_result��
	 */
	public void setDo_result(String do_result) {
		this.do_result = do_result;
	}
	/**
	 * @return ���� do_status��
	 */
	public Integer getDo_status() {
		return do_status;
	}
	/**
	 * @param do_status Ҫ���õ� do_status��
	 */
	public void setDo_status(Integer do_status) {
		this.do_status = do_status;
	}
	/**
	 * @return ���� forward_type��
	 */
	public Integer getForward_type() {
		return forward_type;
	}
	/**
	 * @param forward_type Ҫ���õ� forward_type��
	 */
	public void setForward_type(Integer forward_type) {
		this.forward_type = forward_type;
	}
	/**
	 * @return ���� input_date��
	 */
	public Integer getInput_date() {
		return input_date;
	}
	/**
	 * @param input_date Ҫ���õ� input_date��
	 */
	public void setInput_date(Integer input_date) {
		this.input_date = input_date;
	}
	/**
	 * @return ���� relpy_man��
	 */
	public Integer getRelpy_man() {
		return relpy_man;
	}
	/**
	 * @param relpy_man Ҫ���õ� relpy_man��
	 */
	public void setRelpy_man(Integer relpy_man) {
		this.relpy_man = relpy_man;
	}
	/**
	 * @return ���� reply_date��
	 */
	public Integer getReply_date() {
		return reply_date;
	}
	/**
	 * @param reply_date Ҫ���õ� reply_date��
	 */
	public void setReply_date(Integer reply_date) {
		this.reply_date = reply_date;
	}
	/**
	 * @return ���� reply_type��
	 */
	public Integer getReply_type() {
		return reply_type;
	}
	/**
	 * @param reply_type Ҫ���õ� reply_type��
	 */
	public void setReply_type(Integer reply_type) {
		this.reply_type = reply_type;
	}
	
	/**
	 * @return ���� invest_field��
	 */
	public String getInvest_field() {
		return invest_field;
	}
	/**
	 * @param invest_field Ҫ���õ� invest_field��
	 */
	public void setInvest_field(String invest_field) {
		this.invest_field = invest_field;
	}
	
	
	/**
	 * @return ���� loosely_match��
	 */
	public String getLoosely_match() {
		return loosely_match;
	}
	/**
	 * @param loosely_match Ҫ���õ� loosely_match��
	 */
	public void setLoosely_match(String loosely_match) {
		this.loosely_match = loosely_match;
	}
	/**
	 * @return ���� max_diff��
	 */
	public Integer getMax_diff() {
		return max_diff;
	}
	/**
	 * @param max_diff Ҫ���õ� max_diff��
	 */
	public void setMax_diff(Integer max_diff) {
		this.max_diff = max_diff;
	}
	/**
	 * @return ���� must_contain��
	 */
	public String getMust_contain() {
		return must_contain;
	}
	/**
	 * @param must_contain Ҫ���õ� must_contain��
	 */
	public void setMust_contain(String must_contain) {
		this.must_contain = must_contain;
	}
	/**
	 * @return ���� query_flag��
	 */
	public Integer getQuery_flag() {
		return query_flag;
	}
	/**
	 * @param query_flag Ҫ���õ� query_flag��
	 */
	public void setQuery_flag(Integer query_flag) {
		this.query_flag = query_flag;
	}
	public void setTrade_money(BigDecimal trade_money) {
		this.trade_money = trade_money;
	}
	public BigDecimal getTrade_money() {
		return trade_money;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setQuery_type(Integer query_type) {
		this.query_type = query_type;
	}
	public Integer getQuery_type() {
		return query_type;
	}
	public void setCustomer_messager(String customer_messager) {
		this.customer_messager = customer_messager;
	}
	public String getCustomer_messager() {
		return customer_messager;
	}
	public void setBegin_date(Integer begin_date) {
		this.begin_date = begin_date;
	}
	public Integer getBegin_date() {
		return begin_date;
	}
	/**
	 * @return ���� industry_post_name��
	 */
	public String getIndustry_post_name() {
		return industry_post_name;
	}
	/**
	 * @param industry_post_name Ҫ���õ� industry_post_name��
	 */
	public void setIndustry_post_name(String industry_post_name) {
		this.industry_post_name = industry_post_name;
	}
	
	
	
	/**
	 * @return ���� task_type��
	 */
	public Integer getTask_type() {
		return task_type;
	}
	/**
	 * @param task_type Ҫ���õ� task_type��
	 */
	public void setTask_type(Integer task_type) {
		this.task_type = task_type;
	}
	public Integer getSl_status() {
		return sl_status;
	}
	public void setSl_status(Integer sl_status) {
		this.sl_status = sl_status;
	}
}
