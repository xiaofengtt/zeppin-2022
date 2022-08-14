package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author LZHD
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class ProductVO { 
	 
	private Integer product_id;//��ƷID
	private String product_code;//��Ʒcode
	private String product_name;//��Ʒname 
	private String product_status;//��Ʒ״̬ 
	private Integer start_date;//��ʼ����
	private Integer end_date;//��������
	private Integer open_flag;//����ʽ��־
	private Integer check_flag;//��˱�־
	private java.lang.Integer intrust_flag1; 
	private Integer period_unit;
	private Integer task_type;	
	private String contract_bh;
	private Integer book_code;	
	private Integer input_man;
	private Integer trans_date_begin;
	private Integer trans_date_end;
	private String from_cust_name;
	private String to_cust_name;
	private String trans_type; //ת����� ������ת�ã��̳�
	private Integer serial_no;
	private String contract_sub_bh;// ��ͬ��ţ�ԭ����ʵ�ʺ�ͬ���
	private java.lang.String city_name;	
	private java.lang.Integer trade_date; //ʱ��
	private java.lang.String trade_type; //����
	private java.lang.String trade_type_name;
	private java.lang.String description;
	private BigDecimal trade_rate;	
	private java.math.BigDecimal begin_price;
	private java.math.BigDecimal end_price;
	private java.math.BigDecimal div_rate;
	private java.lang.Integer sub_product_id; //
	private Integer list_id;
	private String list_name;
	private Integer status;
	private String task_name;
	private String summary; 
	private Integer service_man;

    private java.lang.String product_jc;
    private java.lang.String currency_id;
    private java.lang.Integer pre_num;
    private java.math.BigDecimal pre_money;
    private java.lang.Integer pre_start_date;
    private java.lang.Integer pre_end_date;
    private java.math.BigDecimal pre_max_rate;
    private java.lang.Integer pre_max_num;
    private java.math.BigDecimal pre_max_money;
    private java.lang.Integer start_date2;
    private java.lang.Integer end_date2;
    private java.lang.String pre_code;
    private java.lang.String product_status_name;
    private java.lang.Integer admin_manager;
    private java.lang.Integer info_period;
    private java.lang.String open_flag_name;
    private java.lang.String tg_bank_id;
    private java.lang.String tg_bank_name;
    private java.lang.String tg_bank_acct;
    private java.lang.Integer extend_flag;
    private java.lang.String intrust_type;
    private java.lang.String intrust_type_name;
    private java.math.BigDecimal fx_fee;
    private java.lang.Integer depart_id;
    private java.lang.String depart_name;
    private java.lang.String fpfs;
    private java.lang.String fpfs_name;
    private java.math.BigDecimal manage_fee;
    private java.math.BigDecimal manage_rate;
    private java.math.BigDecimal exp_rate1;
    private java.math.BigDecimal exp_rate2;
    private java.lang.Integer fact_pre_num;
    private java.math.BigDecimal fact_pre_money;
    private java.lang.Integer fact_num;

    private java.lang.Integer fact_person_num;
    private java.math.BigDecimal fact_money;
    private java.math.BigDecimal total_money;
    private java.math.BigDecimal total_amount;
    private java.math.BigDecimal nav_price;
    private java.math.BigDecimal zjye;
    private java.sql.Timestamp input_time;
    private java.lang.String item_code;
    private java.math.BigDecimal gain_money;
    private java.lang.Integer intrust_flag2;
    private java.lang.String intrust_type1;
    private java.lang.String intrust_type1_name;
    private java.lang.String intrust_type2;
    private java.lang.String intrust_type2_name;
    private java.math.BigDecimal min_money;
    private BigDecimal tax_rate;
    private java.lang.Integer gr_count;
    private java.math.BigDecimal gr_money;
    private java.lang.Integer jg_count;
    private java.math.BigDecimal jg_money;
    private java.lang.String tg_bank_sub_name;
    private java.lang.String tg_bank_sub_id;
    private java.lang.Integer sub_check_flag;
    
    private java.lang.Integer sale_status;
    private java.lang.String risk_level;
    private Integer risk_level_score;//����ֵ
    private java.lang.String contract_terms;
    
    private java.lang.String class_type1; //��Ʒ����
    private java.lang.String class_type1_name;//��Ʒ��������
    private java.lang.String announce_url; //��Ϣ��¶��ַ
    private java.math.BigDecimal fact_money2; //��Ʒ��ģ����
    private java.lang.String ensure_method; //���ϴ�ʩ
    private java.lang.String kfq; //������
    private java.lang.String shsqtjq;//��������ύ��
    private java.lang.String syzh; //�����˻�
    private java.lang.String dxjg; //��������
    private java.lang.String tzgw; //Ͷ�ʹ���/	�����˴���
    private java.lang.String jjjl; //������
    private java.lang.String kfr; //������
    
    private java.lang.String period; //����
    private Integer expre_start_date; //��ʼԤԼ����
    private java.lang.String profit_date; //�������ʱ��
    private java.lang.String pre_rate; //Ԥ��������
    private java.lang.String cash_usetype;//�ʽ����÷�ʽ
    private java.lang.String sortfield; //����
    private java.lang.String preproduct_name; //Ԥ���в�Ʒ����
    private java.lang.String xszt; //����״̬
    private Integer direct_sale; // ����1 ֱ��2
    private Integer preproduct_id; //Ԥ���в�ƷID
    private java.lang.String searchkey; //�����ؼ���
    private Integer product_status1;//��Ʒ״̬��ȫ��2�ƽ���3������4������
    private java.lang.String admin_manager3; //��Ŀ������
    private java.lang.String pre_status; //ԤԼ��Ʒ����״̬
    
    private Integer quotacheck_flag;//�����˱�־
    private String channel_type;
     
    private java.math.BigDecimal commission_rate;//������ɱ���,CRM��Ʒ������
    
    private String pre_start_time;//ÿ�տ�ԤԼ��ʼʱ��
    private String pre_end_time;//ÿ�տ�ԤԼ��ֹʱ��
    
    private Integer daysBeforeDue; // ��Ʒ����������ǰ����
    private String invest_field;//crmͶ������
    private Integer preStatus;//��Ʒ�Ƿ��ԤԼ

    private String product_desc;	// ��Ʒ˵����
    private String feasstudy;		// ���б���(PPT��ʽ)�ϴ��ļ���·��
    private String feasstudy_easy; // ���б���(PPT��ʽ)�򻯰��ϴ��ļ���·��
    private String notices; 		// �ƽ�֪ͨ��
    private String study_voice; 	// ��ǰ��ѵ��¼�� �ϴ��ļ���·��
    private Integer redeem_freeze;
    private BigDecimal min_redeem_vol2;
    private Integer lot_decimal_flag;
    
    private java.math.BigDecimal lower_limit;
    private java.math.BigDecimal upper_limit;
    private java.math.BigDecimal exp_rate;
    private Integer rate_serial_no;
    
    private Integer pre_valid_days; // ԤԼ��Ч����,����ԤԼʱ������Ч���������ܴ��ڴ˴����õ�ֵ��������Ҫ����ʱ����Ϊ0
    private String expre_start_time;  // ԤԼ��ʼʱ�䣬��'2013-10-08 17:00'��ʽ���룬ͬʱexpre_start_date������
    private String expre_end_time;  // ԤԼ����ʱ�䣬��'2013-10-08 17:00'��ʽ����
    
    private Integer contract_single_flag;
    private Integer template_id; //��ӡģ��ID
    private Integer pre_min_amount;//ԤԼ��С���
    private Integer up_to_show; //�Ϲ���ʾ��ֵ
    
    private BigDecimal product_cost;
    private BigDecimal channel_cost;
    
    private Integer default_prevaliddays;   //��Ч����
    
    private Integer use_tproduct; //���۲��������Ƿ�ʹ�ø���Ʒ������(�������)��1��2��
    
    private BigDecimal proportion;//���۱���
    
    
    
	/**
	 * @return ���� proportion��
	 */
	public BigDecimal getProportion() {
		return proportion;
	}
	/**
	 * @param proportion Ҫ���õ� proportion��
	 */
	public void setProportion(BigDecimal proportion) {
		this.proportion = proportion;
	}
	/**
	 * @return ���� use_tproduct��
	 */
	public Integer getUse_tproduct() {
		return use_tproduct;
	}
	/**
	 * @param use_tproduct Ҫ���õ� use_tproduct��
	 */
	public void setUse_tproduct(Integer use_tproduct) {
		this.use_tproduct = use_tproduct;
	}
	/**
	 * @return ���� default_prevaliddays��
	 */
	public Integer getDefault_prevaliddays() {
		return default_prevaliddays;
	}
	/**
	 * @param default_prevaliddays Ҫ���õ� default_prevaliddays��
	 */
	public void setDefault_prevaliddays(Integer default_prevaliddays) {
		this.default_prevaliddays = default_prevaliddays;
	}
    /**
	 * @return ���� feasstudy��
	 */
	public String getFeasstudy() {
		return feasstudy;
	}
	/**
	 * @param feasstudy Ҫ���õ� feasstudy��
	 */
	public void setFeasstudy(String feasstudy) {
		this.feasstudy = feasstudy;
	}
	/**
	 * @return ���� feasstudy_easy��
	 */
	public String getFeasstudy_easy() {
		return feasstudy_easy;
	}
	/**
	 * @param feasstudy_easy Ҫ���õ� feasstudy_easy��
	 */
	public void setFeasstudy_easy(String feasstudy_easy) {
		this.feasstudy_easy = feasstudy_easy;
	}
	/**
	 * @return ���� notices��
	 */
	public String getNotices() {
		return notices;
	}
	/**
	 * @param notices Ҫ���õ� notices��
	 */
	public void setNotices(String notices) {
		this.notices = notices;
	}
	/**
	 * @return ���� product_desc��
	 */
	public String getProduct_desc() {
		return product_desc;
	}
	/**
	 * @param product_desc Ҫ���õ� product_desc��
	 */
	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}
	/**
	 * @return ���� study_voice��
	 */
	public String getStudy_voice() {
		return study_voice;
	}
	/**
	 * @param study_voice Ҫ���õ� study_voice��
	 */
	public void setStudy_voice(String study_voice) {
		this.study_voice = study_voice;
	}
	/**
	 * @return ���� quotacheck_flag��
	 */
	public Integer getQuotacheck_flag() {
		return quotacheck_flag;
	}
	/**
	 * @param quotacheck_flag Ҫ���õ� quotacheck_flag��
	 */
	public void setQuotacheck_flag(Integer quotacheck_flag) {
		this.quotacheck_flag = quotacheck_flag;
	}
    /**
     * @return ���� pre_status��
     */
    public java.lang.String getPre_status() {
        return pre_status;
    }
    /**
     * @param pre_status Ҫ���õ� pre_status��
     */
    public void setPre_status(java.lang.String pre_status) {
        this.pre_status = pre_status;
    }
    /**
     * @return ���� admin_manager3��
     */
    public java.lang.String getAdmin_manager3() {
        return admin_manager3;
    }
    /**
     * @param admin_manager3 Ҫ���õ� admin_manager3��
     */
    public void setAdmin_manager3(java.lang.String admin_manager3) {
        this.admin_manager3 = admin_manager3;
    }
    /**
     * @return ���� expre_start_date��
     */
    public Integer getExpre_start_date() {
        return expre_start_date;
    }
    /**
     * @param expre_start_date Ҫ���õ� expre_start_date��
     */
    public void setExpre_start_date(Integer expre_start_date) {
        this.expre_start_date = expre_start_date;
    }
	/**
	 * @return ���� product_status1��
	 */
	public Integer getProduct_status1() {
		return product_status1;
	}
	/**
	 * @param product_status1 Ҫ���õ� product_status1��
	 */
	public void setProduct_status1(Integer product_status1) {
		this.product_status1 = product_status1;
	}
	/**
	 * @return ���� searchkey��
	 */
	public java.lang.String getSearchkey() {
		return searchkey;
	}
	/**
	 * @param searchkey Ҫ���õ� searchkey��
	 */
	public void setSearchkey(java.lang.String searchkey) {
		this.searchkey = searchkey;
	}
	/**
	 * @return ���� preproduct_id��
	 */
	public Integer getPreproduct_id() {
		return preproduct_id;
	}
	/**
	 * @param preproduct_id Ҫ���õ� preproduct_id��
	 */
	public void setPreproduct_id(Integer preproduct_id) {
		this.preproduct_id = preproduct_id;
	}
	/**
	 * @return ���� direct_sale��
	 */
	public Integer getDirect_sale() {
		return direct_sale;
	}
	/**
	 * @param direct_sale Ҫ���õ� direct_sale��
	 */
	public void setDirect_sale(Integer direct_sale) {
		this.direct_sale = direct_sale;
	}
	/**
	 * @return ���� xszt��
	 */
	public java.lang.String getXszt() {
		return xszt;
	}
	/**
	 * @param xszt Ҫ���õ� xszt��
	 */
	public void setXszt(java.lang.String xszt) {
		this.xszt = xszt;
	}
	/**
	 * @return ���� preproduct_name��
	 */
	public java.lang.String getPreproduct_name() {
		return preproduct_name;
	}
	/**
	 * @param preproduct_name Ҫ���õ� preproduct_name��
	 */
	public void setPreproduct_name(java.lang.String preproduct_name) {
		this.preproduct_name = preproduct_name;
	}
	/**
	 * @return ���� sortfield��
	 */
	public java.lang.String getSortfield() {
		return sortfield;
	}
	/**
	 * @param sortfield Ҫ���õ� sortfield��
	 */
	public void setSortfield(java.lang.String sortfield) {
		this.sortfield = sortfield;
	}
	/**
	 * @return ���� cash_usetype��
	 */
	public java.lang.String getCash_usetype() {
		return cash_usetype;
	}
	/**
	 * @param cash_usetype Ҫ���õ� cash_usetype��
	 */
	public void setCash_usetype(java.lang.String cash_usetype) {
		this.cash_usetype = cash_usetype;
	}
	/**
	 * @return ���� pre_rate��
	 */
	public java.lang.String getPre_rate() {
		return pre_rate;
	}
	/**
	 * @param pre_rate Ҫ���õ� pre_rate��
	 */
	public void setPre_rate(java.lang.String pre_rate) {
		this.pre_rate = pre_rate;
	}
	/**
	 * @return ���� profit_date��
	 */
	public java.lang.String getProfit_date() {
		return profit_date;
	}
	/**
	 * @param profit_date Ҫ���õ� profit_date��
	 */
	public void setProfit_date(java.lang.String profit_date) {
		this.profit_date = profit_date;
	}
	
	/**
	 * @return ���� period��
	 */
	public java.lang.String getPeriod() {
		return period;
	}
	/**
	 * @param period Ҫ���õ� period��
	 */
	public void setPeriod(java.lang.String period) {
		this.period = period;
	}
	/**
	 * @return ���� dxjg��
	 */
	public java.lang.String getDxjg() {
		return dxjg;
	}
	/**
	 * @param dxjg Ҫ���õ� dxjg��
	 */
	public void setDxjg(java.lang.String dxjg) {
		this.dxjg = dxjg;
	}
	/**
	 * @return ���� jjjl��
	 */
	public java.lang.String getJjjl() {
		return jjjl;
	}
	/**
	 * @param jjjl Ҫ���õ� jjjl��
	 */
	public void setJjjl(java.lang.String jjjl) {
		this.jjjl = jjjl;
	}
	/**
	 * @return ���� kfq��
	 */
	public java.lang.String getKfq() {
		return kfq;
	}
	/**
	 * @param kfq Ҫ���õ� kfq��
	 */
	public void setKfq(java.lang.String kfq) {
		this.kfq = kfq;
	}
	/**
	 * @return ���� kfr��
	 */
	public java.lang.String getKfr() {
		return kfr;
	}
	/**
	 * @param kfr Ҫ���õ� kfr��
	 */
	public void setKfr(java.lang.String kfr) {
		this.kfr = kfr;
	}
	/**
	 * @return ���� shsqtjq��
	 */
	public java.lang.String getShsqtjq() {
		return shsqtjq;
	}
	/**
	 * @param shsqtjq Ҫ���õ� shsqtjq��
	 */
	public void setShsqtjq(java.lang.String shsqtjq) {
		this.shsqtjq = shsqtjq;
	}
	/**
	 * @return ���� syzh��
	 */
	public java.lang.String getSyzh() {
		return syzh;
	}
	/**
	 * @param syzh Ҫ���õ� syzh��
	 */
	public void setSyzh(java.lang.String syzh) {
		this.syzh = syzh;
	}
	/**
	 * @return ���� tzgw��
	 */
	public java.lang.String getTzgw() {
		return tzgw;
	}
	/**
	 * @param tzgw Ҫ���õ� tzgw��
	 */
	public void setTzgw(java.lang.String tzgw) {
		this.tzgw = tzgw;
	}
    
    
	/**
	 * @return ���� announce_url��
	 */
	public java.lang.String getAnnounce_url() {
		return announce_url;
	}
	/**
	 * @param announce_url Ҫ���õ� announce_url��
	 */
	public void setAnnounce_url(java.lang.String announce_url) {
		this.announce_url = announce_url;
	}
	/**
	 * @return ���� class_type1��
	 */
	public java.lang.String getClass_type1() {
		return class_type1;
	}
	/**
	 * @param class_type1 Ҫ���õ� class_type1��
	 */
	public void setClass_type1(java.lang.String class_type1) {
		this.class_type1 = class_type1;
	}
	/**
	 * @return ���� class_type1_name��
	 */
	public java.lang.String getClass_type1_name() {
		return class_type1_name;
	}
	/**
	 * @param class_type1_name Ҫ���õ� class_type1_name��
	 */
	public void setClass_type1_name(java.lang.String class_type1_name) {
		this.class_type1_name = class_type1_name;
	}
    public Integer getRisk_level_score() {
		return risk_level_score;
	}
	public void setRisk_level_score(Integer riskLevelScore) {
		risk_level_score = riskLevelScore;
	}
	/**
     * ��Ʒ��������
     */
    private java.lang.Integer r_channel_id;
    
    /**
     * �������۷���
     */
    private java.math.BigDecimal r_chanel_rate;
    
    public java.lang.String getProduct_jc() {
		return product_jc;
	}
	public void setProduct_jc(java.lang.String productJc) {
		product_jc = productJc;
	}
	public java.lang.String getCurrency_id() {
		return currency_id;
	}
	public void setCurrency_id(java.lang.String currencyId) {
		currency_id = currencyId;
	}
	public java.lang.Integer getPre_num() {
		return pre_num;
	}
	public void setPre_num(java.lang.Integer preNum) {
		pre_num = preNum;
	}
	public java.math.BigDecimal getPre_money() {
		return pre_money;
	}
	public void setPre_money(java.math.BigDecimal preMoney) {
		pre_money = preMoney;
	}
	public java.lang.Integer getPre_start_date() {
		return pre_start_date;
	}
	public void setPre_start_date(java.lang.Integer preStartDate) {
		pre_start_date = preStartDate;
	}
	public java.lang.Integer getPre_end_date() {
		return pre_end_date;
	}
	public void setPre_end_date(java.lang.Integer preEndDate) {
		pre_end_date = preEndDate;
	}
	public java.math.BigDecimal getPre_max_rate() {
		return pre_max_rate;
	}
	public void setPre_max_rate(java.math.BigDecimal preMaxRate) {
		pre_max_rate = preMaxRate;
	}
	public java.lang.Integer getPre_max_num() {
		return pre_max_num;
	}
	public void setPre_max_num(java.lang.Integer preMaxNum) {
		pre_max_num = preMaxNum;
	}
	public java.math.BigDecimal getPre_max_money() {
		return pre_max_money;
	}
	public void setPre_max_money(java.math.BigDecimal preMaxMoney) {
		pre_max_money = preMaxMoney;
	}
	public java.lang.Integer getStart_date2() {
		return start_date2;
	}
	public void setStart_date2(java.lang.Integer startDate2) {
		start_date2 = startDate2;
	}
	public java.lang.Integer getEnd_date2() {
		return end_date2;
	}
	public void setEnd_date2(java.lang.Integer endDate2) {
		end_date2 = endDate2;
	}
	public java.lang.String getPre_code() {
		return pre_code;
	}
	public void setPre_code(java.lang.String preCode) {
		pre_code = preCode;
	}
	public java.lang.String getProduct_status_name() {
		return product_status_name;
	}
	public void setProduct_status_name(java.lang.String productStatusName) {
		product_status_name = productStatusName;
	}
	public java.lang.Integer getAdmin_manager() {
		return admin_manager;
	}
	public void setAdmin_manager(java.lang.Integer adminManager) {
		admin_manager = adminManager;
	}
	public java.lang.Integer getInfo_period() {
		return info_period;
	}
	public void setInfo_period(java.lang.Integer infoPeriod) {
		info_period = infoPeriod;
	}
	public java.lang.String getOpen_flag_name() {
		return open_flag_name;
	}
	public void setOpen_flag_name(java.lang.String openFlagName) {
		open_flag_name = openFlagName;
	}
	public java.lang.String getTg_bank_id() {
		return tg_bank_id;
	}
	public void setTg_bank_id(java.lang.String tgBankId) {
		tg_bank_id = tgBankId;
	}
	public java.lang.String getTg_bank_name() {
		return tg_bank_name;
	}
	public void setTg_bank_name(java.lang.String tgBankName) {
		tg_bank_name = tgBankName;
	}
	public java.lang.String getTg_bank_acct() {
		return tg_bank_acct;
	}
	public void setTg_bank_acct(java.lang.String tgBankAcct) {
		tg_bank_acct = tgBankAcct;
	}
	public java.lang.Integer getExtend_flag() {
		return extend_flag;
	}
	public void setExtend_flag(java.lang.Integer extendFlag) {
		extend_flag = extendFlag;
	}
	public java.lang.String getIntrust_type() {
		return intrust_type;
	}
	public void setIntrust_type(java.lang.String intrustType) {
		intrust_type = intrustType;
	}
	public java.lang.String getIntrust_type_name() {
		return intrust_type_name;
	}
	public void setIntrust_type_name(java.lang.String intrustTypeName) {
		intrust_type_name = intrustTypeName;
	}
	public java.math.BigDecimal getFx_fee() {
		return fx_fee;
	}
	public void setFx_fee(java.math.BigDecimal fxFee) {
		fx_fee = fxFee;
	}
	public java.lang.Integer getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(java.lang.Integer departId) {
		depart_id = departId;
	}
	public java.lang.String getDepart_name() {
		return depart_name;
	}
	public void setDepart_name(java.lang.String departName) {
		depart_name = departName;
	}
	public java.lang.String getFpfs() {
		return fpfs;
	}
	public void setFpfs(java.lang.String fpfs) {
		this.fpfs = fpfs;
	}
	public java.lang.String getFpfs_name() {
		return fpfs_name;
	}
	public void setFpfs_name(java.lang.String fpfsName) {
		fpfs_name = fpfsName;
	}
	public java.math.BigDecimal getManage_fee() {
		return manage_fee;
	}
	public void setManage_fee(java.math.BigDecimal manageFee) {
		manage_fee = manageFee;
	}
	public java.math.BigDecimal getManage_rate() {
		return manage_rate;
	}
	public void setManage_rate(java.math.BigDecimal manageRate) {
		manage_rate = manageRate;
	}
	public java.math.BigDecimal getExp_rate1() {
		return exp_rate1;
	}
	public void setExp_rate1(java.math.BigDecimal expRate1) {
		exp_rate1 = expRate1;
	}
	public java.math.BigDecimal getExp_rate2() {
		return exp_rate2;
	}
	public void setExp_rate2(java.math.BigDecimal expRate2) {
		exp_rate2 = expRate2;
	}
	public java.lang.Integer getFact_pre_num() {
		return fact_pre_num;
	}
	public void setFact_pre_num(java.lang.Integer factPreNum) {
		fact_pre_num = factPreNum;
	}
	public java.math.BigDecimal getFact_pre_money() {
		return fact_pre_money;
	}
	public void setFact_pre_money(java.math.BigDecimal factPreMoney) {
		fact_pre_money = factPreMoney;
	}
	public java.lang.Integer getFact_num() {
		return fact_num;
	}
	public void setFact_num(java.lang.Integer factNum) {
		fact_num = factNum;
	}
	public java.lang.Integer getFact_person_num() {
		return fact_person_num;
	}
	public void setFact_person_num(java.lang.Integer factPersonNum) {
		fact_person_num = factPersonNum;
	}
	public java.math.BigDecimal getFact_money() {
		return fact_money;
	}
	public void setFact_money(java.math.BigDecimal factMoney) {
		fact_money = factMoney;
	}
	public java.math.BigDecimal getTotal_money() {
		return total_money;
	}
	public void setTotal_money(java.math.BigDecimal totalMoney) {
		total_money = totalMoney;
	}
	public java.math.BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(java.math.BigDecimal totalAmount) {
		total_amount = totalAmount;
	}
	public java.math.BigDecimal getNav_price() {
		return nav_price;
	}
	public void setNav_price(java.math.BigDecimal navPrice) {
		nav_price = navPrice;
	}
	public java.math.BigDecimal getZjye() {
		return zjye;
	}
	public void setZjye(java.math.BigDecimal zjye) {
		this.zjye = zjye;
	}
	public java.sql.Timestamp getInput_time() {
		return input_time;
	}
	public void setInput_time(java.sql.Timestamp inputTime) {
		input_time = inputTime;
	}
	public java.lang.String getItem_code() {
		return item_code;
	}
	public void setItem_code(java.lang.String itemCode) {
		item_code = itemCode;
	}
	public java.math.BigDecimal getGain_money() {
		return gain_money;
	}
	public void setGain_money(java.math.BigDecimal gainMoney) {
		gain_money = gainMoney;
	}
	public java.lang.Integer getIntrust_flag2() {
		return intrust_flag2;
	}
	public void setIntrust_flag2(java.lang.Integer intrustFlag2) {
		intrust_flag2 = intrustFlag2;
	}
	public java.lang.String getIntrust_type1() {
		return intrust_type1;
	}
	public void setIntrust_type1(java.lang.String intrustType1) {
		intrust_type1 = intrustType1;
	}
	public java.lang.String getIntrust_type1_name() {
		return intrust_type1_name;
	}
	public void setIntrust_type1_name(java.lang.String intrustType1Name) {
		intrust_type1_name = intrustType1Name;
	}
	public java.lang.String getIntrust_type2() {
		return intrust_type2;
	}
	public void setIntrust_type2(java.lang.String intrustType2) {
		intrust_type2 = intrustType2;
	}
	public java.lang.String getIntrust_type2_name() {
		return intrust_type2_name;
	}
	public void setIntrust_type2_name(java.lang.String intrustType2Name) {
		intrust_type2_name = intrustType2Name;
	}
	public java.math.BigDecimal getMin_money() {
		return min_money;
	}
	public void setMin_money(java.math.BigDecimal minMoney) {
		min_money = minMoney;
	}
	public BigDecimal getTax_rate() {
		return tax_rate;
	}
	public void setTax_rate(BigDecimal taxRate) {
		tax_rate = taxRate;
	}
	public java.lang.Integer getGr_count() {
		return gr_count;
	}
	public void setGr_count(java.lang.Integer grCount) {
		gr_count = grCount;
	}
	public java.math.BigDecimal getGr_money() {
		return gr_money;
	}
	public void setGr_money(java.math.BigDecimal grMoney) {
		gr_money = grMoney;
	}
	public java.lang.Integer getJg_count() {
		return jg_count;
	}
	public void setJg_count(java.lang.Integer jgCount) {
		jg_count = jgCount;
	}
	public java.math.BigDecimal getJg_money() {
		return jg_money;
	}
	public void setJg_money(java.math.BigDecimal jgMoney) {
		jg_money = jgMoney;
	}
	public java.lang.String getTg_bank_sub_name() {
		return tg_bank_sub_name;
	}
	public void setTg_bank_sub_name(java.lang.String tgBankSubName) {
		tg_bank_sub_name = tgBankSubName;
	}
	public java.lang.String getTg_bank_sub_id() {
		return tg_bank_sub_id;
	}
	public void setTg_bank_sub_id(java.lang.String tgBankSubId) {
		tg_bank_sub_id = tgBankSubId;
	}
	public java.lang.Integer getSub_check_flag() {
		return sub_check_flag;
	}
	public void setSub_check_flag(java.lang.Integer subCheckFlag) {
		sub_check_flag = subCheckFlag;
	}
	public java.lang.Integer getBen_period() {
		return ben_period;
	}
	public void setBen_period(java.lang.Integer benPeriod) {
		ben_period = benPeriod;
	}
	public java.lang.String getProv_level() {
		return prov_level;
	}
	public void setProv_level(java.lang.String provLevel) {
		prov_level = provLevel;
	}
	public java.lang.String getProv_level_name() {
		return prov_level_name;
	}
	public void setProv_level_name(java.lang.String provLevelName) {
		prov_level_name = provLevelName;
	}
	public java.math.BigDecimal getBen_amount() {
		return ben_amount;
	}
	public void setBen_amount(java.math.BigDecimal benAmount) {
		ben_amount = benAmount;
	}
	public java.lang.Integer getContract_num() {
		return contract_num;
	}
	public void setContract_num(java.lang.Integer contractNum) {
		contract_num = contractNum;
	}
	public java.lang.Integer getBen_num() {
		return ben_num;
	}
	public void setBen_num(java.lang.Integer benNum) {
		ben_num = benNum;
	}
	public int getBen_period_temp() {
		return ben_period_temp;
	}
	public void setBen_period_temp(int benPeriodTemp) {
		ben_period_temp = benPeriodTemp;
	}
	public Integer getEnd_flag() {
		return end_flag;
	}
	public void setEnd_flag(Integer endFlag) {
		end_flag = endFlag;
	}
	public java.lang.Integer getProduct_from() {
		return product_from;
	}
	public void setProduct_from(java.lang.Integer productFrom) {
		product_from = productFrom;
	}
	public java.lang.Integer getLast_post_date() {
		return last_post_date;
	}
	public void setLast_post_date(java.lang.Integer lastPostDate) {
		last_post_date = lastPostDate;
	}
	public java.lang.Integer getSl_flag() {
		return sl_flag;
	}
	public void setSl_flag(java.lang.Integer slFlag) {
		sl_flag = slFlag;
	}
	public java.lang.Integer getAll_flag() {
		return all_flag;
	}
	public void setAll_flag(java.lang.Integer allFlag) {
		all_flag = allFlag;
	}
	public java.lang.Integer getFlag() {
		return flag;
	}
	public void setFlag(java.lang.Integer flag) {
		this.flag = flag;
	}
	public java.math.BigDecimal getAmount_min() {
		return amount_min;
	}
	public void setAmount_min(java.math.BigDecimal amountMin) {
		amount_min = amountMin;
	}
	public java.math.BigDecimal getAmount_max() {
		return amount_max;
	}
	public void setAmount_max(java.math.BigDecimal amountMax) {
		amount_max = amountMax;
	}
	public java.lang.Integer getAdmin_manager2() {
		return admin_manager2;
	}
	public void setAdmin_manager2(java.lang.Integer adminManager2) {
		admin_manager2 = adminManager2;
	}
	public java.lang.Integer getMatain_manager() {
		return matain_manager;
	}
	public void setMatain_manager(java.lang.Integer matainManager) {
		matain_manager = matainManager;
	}
	public java.lang.Integer getChange_wt_flag() {
		return change_wt_flag;
	}
	public void setChange_wt_flag(java.lang.Integer changeWtFlag) {
		change_wt_flag = changeWtFlag;
	}
	public java.lang.Integer getIntrust_flag3() {
		return intrust_flag3;
	}
	public void setIntrust_flag3(java.lang.Integer intrustFlag3) {
		intrust_flag3 = intrustFlag3;
	}
	public java.lang.Integer getIntrust_flag4() {
		return intrust_flag4;
	}
	public void setIntrust_flag4(java.lang.Integer intrustFlag4) {
		intrust_flag4 = intrustFlag4;
	}
	public java.lang.String getEntity_type() {
		return entity_type;
	}
	public void setEntity_type(java.lang.String entityType) {
		entity_type = entityType;
	}
	public java.lang.String getEntity_type_name() {
		return entity_type_name;
	}
	public void setEntity_type_name(java.lang.String entityTypeName) {
		entity_type_name = entityTypeName;
	}
	public java.lang.String getDeal_type() {
		return deal_type;
	}
	public void setDeal_type(java.lang.String dealType) {
		deal_type = dealType;
	}
	public java.lang.String getDeal_type_name() {
		return deal_type_name;
	}
	public void setDeal_type_name(java.lang.String dealTypeName) {
		deal_type_name = dealTypeName;
	}
	public java.lang.String getField_name() {
		return field_name;
	}
	public void setField_name(java.lang.String fieldName) {
		field_name = fieldName;
	}
	public java.lang.String getField_cn_name() {
		return field_cn_name;
	}
	public void setField_cn_name(java.lang.String fieldCnName) {
		field_cn_name = fieldCnName;
	}
	public java.lang.String getOld_field_info() {
		return old_field_info;
	}
	public void setOld_field_info(java.lang.String oldFieldInfo) {
		old_field_info = oldFieldInfo;
	}
	public java.lang.String getNew_field_info() {
		return new_field_info;
	}
	public void setNew_field_info(java.lang.String newFieldInfo) {
		new_field_info = newFieldInfo;
	}
	public java.lang.String getQuality_level() {
		return quality_level;
	}
	public void setQuality_level(java.lang.String qualityLevel) {
		quality_level = qualityLevel;
	}
	public java.lang.String getQuality_level_name() {
		return quality_level_name;
	}
	public void setQuality_level_name(java.lang.String qualityLevelName) {
		quality_level_name = qualityLevelName;
	}
	public java.lang.String getProduct_info() {
		return product_info;
	}
	public void setProduct_info(java.lang.String productInfo) {
		product_info = productInfo;
	}
	public String getProductstatusName() {
		return productstatusName;
	}
	public void setProductstatusName(String productstatusName) {
		this.productstatusName = productstatusName;
	}
	public String getBusi_name() {
		return busi_name;
	}
	public void setBusi_name(String busiName) {
		busi_name = busiName;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String custName) {
		cust_name = custName;
	}
	public java.math.BigDecimal getHt_money() {
		return ht_money;
	}
	public void setHt_money(java.math.BigDecimal htMoney) {
		ht_money = htMoney;
	}
	public java.math.BigDecimal getCw_money() {
		return cw_money;
	}
	public void setCw_money(java.math.BigDecimal cwMoney) {
		cw_money = cwMoney;
	}
	public java.math.BigDecimal getBen_money() {
		return ben_money;
	}
	public void setBen_money(java.math.BigDecimal benMoney) {
		ben_money = benMoney;
	}
	public java.math.BigDecimal getGr_amount() {
		return gr_amount;
	}
	public void setGr_amount(java.math.BigDecimal grAmount) {
		gr_amount = grAmount;
	}
	public java.math.BigDecimal getJg_amount() {
		return jg_amount;
	}
	public void setJg_amount(java.math.BigDecimal jgAmount) {
		jg_amount = jgAmount;
	}
	public Integer getValid_period2() {
		return valid_period2;
	}
	public void setValid_period2(Integer validPeriod2) {
		valid_period2 = validPeriod2;
	}
	public Integer getSub_flag() {
		return sub_flag;
	}
	public void setSub_flag(Integer subFlag) {
		sub_flag = subFlag;
	}
	public String getDBdriver() {
		return DBdriver;
	}
	public void setDBdriver(String dBdriver) {
		DBdriver = dBdriver;
	}
	public String getBg_bank_id() {
		return bg_bank_id;
	}
	public void setBg_bank_id(String bgBankId) {
		bg_bank_id = bgBankId;
	}
	public String getBg_bank_name() {
		return bg_bank_name;
	}
	public void setBg_bank_name(String bgBankName) {
		bg_bank_name = bgBankName;
	}
	public Integer getOld_end_date() {
		return old_end_date;
	}
	public void setOld_end_date(Integer oldEndDate) {
		old_end_date = oldEndDate;
	}
	public Integer getNew_end_date() {
		return new_end_date;
	}
	public void setNew_end_date(Integer newEndDate) {
		new_end_date = newEndDate;
	}
	public Integer getBusiness_end_flag() {
		return business_end_flag;
	}
	public void setBusiness_end_flag(Integer businessEndFlag) {
		business_end_flag = businessEndFlag;
	}
	public Integer getBusiness_end_date() {
		return business_end_date;
	}
	public void setBusiness_end_date(Integer businessEndDate) {
		business_end_date = businessEndDate;
	}
	public Integer getFact_end_date() {
		return fact_end_date;
	}
	public void setFact_end_date(Integer factEndDate) {
		fact_end_date = factEndDate;
	}
	public Integer getTask_date() {
		return task_date;
	}
	public void setTask_date(Integer taskDate) {
		task_date = taskDate;
	}
	public String getInvest_type() {
		return invest_type;
	}
	public void setInvest_type(String investType) {
		invest_type = investType;
	}
	public String getsAdmin_man() {
		return sAdmin_man;
	}
	public void setsAdmin_man(String sAdminMan) {
		sAdmin_man = sAdminMan;
	}
	public String getsAdmin_man2() {
		return sAdmin_man2;
	}
	public void setsAdmin_man2(String sAdminMan2) {
		sAdmin_man2 = sAdminMan2;
	}
	public String getsMatain_man() {
		return sMatain_man;
	}
	public void setsMatain_man(String sMatainMan) {
		sMatain_man = sMatainMan;
	}
	public BigDecimal getBalance_3101() {
		return balance_3101;
	}
	public void setBalance_3101(BigDecimal balance_3101) {
		this.balance_3101 = balance_3101;
	}
	public BigDecimal getBalance_all() {
		return balance_all;
	}
	public void setBalance_all(BigDecimal balanceAll) {
		balance_all = balanceAll;
	}
	public BigDecimal getBalance_lr() {
		return balance_lr;
	}
	public void setBalance_lr(BigDecimal balanceLr) {
		balance_lr = balanceLr;
	}
	public Integer getGr_num() {
		return gr_num;
	}
	public void setGr_num(Integer grNum) {
		gr_num = grNum;
	}
	public Integer getJg_num() {
		return jg_num;
	}
	public void setJg_num(Integer jgNum) {
		jg_num = jgNum;
	}
	public Integer getTime_flag() {
		return time_flag;
	}
	public void setTime_flag(Integer timeFlag) {
		time_flag = timeFlag;
	}
	public Integer getSub_man() {
		return sub_man;
	}
	public void setSub_man(Integer subMan) {
		sub_man = subMan;
	}
	public String getSub_man_name() {
		return sub_man_name;
	}
	public void setSub_man_name(String subManName) {
		sub_man_name = subManName;
	}
	public Integer getBusi_flag() {
		return busi_flag;
	}
	public void setBusi_flag(Integer busiFlag) {
		busi_flag = busiFlag;
	}
	public Integer getOp_code() {
		return op_code;
	}
	public void setOp_code(Integer opCode) {
		op_code = opCode;
	}
	public Integer getCheck_man() {
		return check_man;
	}
	public void setCheck_man(Integer checkMan) {
		check_man = checkMan;
	}
	public Integer getCurrent_month() {
		return current_month;
	}
	public void setCurrent_month(Integer currentMonth) {
		current_month = currentMonth;
	}
	public Integer getHq_date() {
		return hq_date;
	}
	public void setHq_date(Integer hqDate) {
		hq_date = hqDate;
	}
	public BigDecimal getNav_price1() {
		return nav_price1;
	}
	public void setNav_price1(BigDecimal navPrice1) {
		nav_price1 = navPrice1;
	}
	public BigDecimal getNav_price2() {
		return nav_price2;
	}
	public void setNav_price2(BigDecimal navPrice2) {
		nav_price2 = navPrice2;
	}
	public String getTg_acct_name() {
		return tg_acct_name;
	}
	public void setTg_acct_name(String tgAcctName) {
		tg_acct_name = tgAcctName;
	}
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String opName) {
		op_name = opName;
	}
	public BigDecimal getCurr_fact_money() {
		return curr_fact_money;
	}
	public void setCurr_fact_money(BigDecimal currFactMoney) {
		curr_fact_money = currFactMoney;
	}
	public String getIntrust_flag1_name() {
		return intrust_flag1_name;
	}
	public void setIntrust_flag1_name(String intrustFlag1Name) {
		intrust_flag1_name = intrustFlag1Name;
	}
	public String[] getTotal_fact_money() {
		return total_fact_money;
	}
	public void setTotal_fact_money(String[] totalFactMoney) {
		total_fact_money = totalFactMoney;
	}
	public java.math.BigDecimal getBusi_nav_price() {
		return busi_nav_price;
	}
	public void setBusi_nav_price(java.math.BigDecimal busiNavPrice) {
		busi_nav_price = busiNavPrice;
	}
	public java.math.BigDecimal getProv_level_a_money() {
		return prov_level_a_money;
	}
	public void setProv_level_a_money(java.math.BigDecimal provLevelAMoney) {
		prov_level_a_money = provLevelAMoney;
	}
	public java.math.BigDecimal getProv_level_b_money() {
		return prov_level_b_money;
	}
	public void setProv_level_b_money(java.math.BigDecimal provLevelBMoney) {
		prov_level_b_money = provLevelBMoney;
	}
	public Integer getOrg_count() {
		return org_count;
	}
	public void setOrg_count(Integer orgCount) {
		org_count = orgCount;
	}
	public Integer getPerson_count() {
		return person_count;
	}
	public void setPerson_count(Integer personCount) {
		person_count = personCount;
	}
	public java.math.BigDecimal getPerson_money() {
		return person_money;
	}
	public void setPerson_money(java.math.BigDecimal personMoney) {
		person_money = personMoney;
	}
	public java.math.BigDecimal getOrg_money() {
		return org_money;
	}
	public void setOrg_money(java.math.BigDecimal orgMoney) {
		org_money = orgMoney;
	}
	public java.math.BigDecimal getOriginal_money() {
		return original_money;
	}
	public void setOriginal_money(java.math.BigDecimal originalMoney) {
		original_money = originalMoney;
	}
	public Integer getWith_bank_flag() {
		return with_bank_flag;
	}
	public void setWith_bank_flag(Integer withBankFlag) {
		with_bank_flag = withBankFlag;
	}
	public java.math.BigDecimal getQualified_count() {
		return qualified_count;
	}
	public void setQualified_count(java.math.BigDecimal qualifiedCount) {
		qualified_count = qualifiedCount;
	}
	public java.math.BigDecimal getQualified_money() {
		return qualified_money;
	}
	public void setQualified_money(java.math.BigDecimal qualifiedMoney) {
		qualified_money = qualifiedMoney;
	}
	public java.math.BigDecimal getQualified_amount() {
		return qualified_amount;
	}
	public void setQualified_amount(java.math.BigDecimal qualifiedAmount) {
		qualified_amount = qualifiedAmount;
	}
	public java.math.BigDecimal getMin_buy_limit() {
		return min_buy_limit;
	}
	public void setMin_buy_limit(java.math.BigDecimal minBuyLimit) {
		min_buy_limit = minBuyLimit;
	}
	public java.math.BigDecimal getMax_buy_limit() {
		return max_buy_limit;
	}
	public void setMax_buy_limit(java.math.BigDecimal maxBuyLimit) {
		max_buy_limit = maxBuyLimit;
	}
	public Integer getTrust_fee_period() {
		return trust_fee_period;
	}
	public void setTrust_fee_period(Integer trustFeePeriod) {
		trust_fee_period = trustFeePeriod;
	}
	public int getPermission_flag() {
		return permission_flag;
	}
	public void setPermission_flag(int permissionFlag) {
		permission_flag = permissionFlag;
	}
	public BigDecimal getExt_rate1() {
		return ext_rate1;
	}
	public void setExt_rate1(BigDecimal extRate1) {
		ext_rate1 = extRate1;
	}
	public BigDecimal getExt_rate2() {
		return ext_rate2;
	}
	public void setExt_rate2(BigDecimal extRate2) {
		ext_rate2 = extRate2;
	}
	public Integer getNav_float_num() {
		return nav_float_num;
	}
	public void setNav_float_num(Integer navFloatNum) {
		nav_float_num = navFloatNum;
	}
	public BigDecimal getTrade_tax_rate() {
		return trade_tax_rate;
	}
	public void setTrade_tax_rate(BigDecimal tradeTaxRate) {
		trade_tax_rate = tradeTaxRate;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public String getManagetype_name() {
		return managetype_name;
	}
	public void setManagetype_name(String managetypeName) {
		managetype_name = managetypeName;
	}
	public String getTrust_contract_name() {
		return trust_contract_name;
	}
	public void setTrust_contract_name(String trustContractName) {
		trust_contract_name = trustContractName;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bankName) {
		bank_name = bankName;
	}
	public String getAcct_name() {
		return acct_name;
	}
	public void setAcct_name(String acctName) {
		acct_name = acctName;
	}
	public String getBank_acct() {
		return bank_acct;
	}
	public void setBank_acct(String bankAcct) {
		bank_acct = bankAcct;
	}
	public Integer getManager_type() {
		return manager_type;
	}
	public void setManager_type(Integer managerType) {
		manager_type = managerType;
	}
	public BigDecimal getResult_standard() {
		return result_standard;
	}
	public void setResult_standard(BigDecimal resultStandard) {
		result_standard = resultStandard;
	}
	public Integer getShare_flag() {
		return share_flag;
	}
	public void setShare_flag(Integer shareFlag) {
		share_flag = shareFlag;
	}
	public Integer getCoperate_type() {
		return coperate_type;
	}
	public void setCoperate_type(Integer coperateType) {
		coperate_type = coperateType;
	}
	public String getGov_prov_pegional() {
		return gov_prov_pegional;
	}
	public void setGov_prov_pegional(String govProvPegional) {
		gov_prov_pegional = govProvPegional;
	}
	public String getGov_pegional() {
		return gov_pegional;
	}
	public void setGov_pegional(String govPegional) {
		gov_pegional = govPegional;
	}
	public String getSub_product_code() {
		return sub_product_code;
	}
	public void setSub_product_code(String subProductCode) {
		sub_product_code = subProductCode;
	}
	public Integer getSub_fund_flag() {
		return sub_fund_flag;
	}
	public void setSub_fund_flag(Integer subFundFlag) {
		sub_fund_flag = subFundFlag;
	}
	public Integer getProv_flag() {
		return prov_flag;
	}
	public void setProv_flag(Integer provFlag) {
		prov_flag = provFlag;
	}
	public Integer getDeal_flag() {
		return deal_flag;
	}
	public void setDeal_flag(Integer dealFlag) {
		deal_flag = dealFlag;
	}
	public Integer getQualified_flag() {
		return qualified_flag;
	}
	public void setQualified_flag(Integer qualifiedFlag) {
		qualified_flag = qualifiedFlag;
	}
	public Integer getQualified_num() {
		return qualified_num;
	}
	public void setQualified_num(Integer qualifiedNum) {
		qualified_num = qualifiedNum;
	}
	public Integer getAsfund_flag() {
		return asfund_flag;
	}
	public void setAsfund_flag(Integer asfundFlag) {
		asfund_flag = asfundFlag;
	}
	public Integer getGain_flag() {
		return gain_flag;
	}
	public void setGain_flag(Integer gainFlag) {
		gain_flag = gainFlag;
	}
	public Integer getCity_serial_no() {
		return city_serial_no;
	}
	public void setCity_serial_no(Integer citySerialNo) {
		city_serial_no = citySerialNo;
	}
	public Integer getDq_open_flag() {
		return dq_open_flag;
	}
	public void setDq_open_flag(Integer dqOpenFlag) {
		dq_open_flag = dqOpenFlag;
	}
	public Integer getDq_open_period() {
		return dq_open_period;
	}
	public void setDq_open_period(Integer dqOpenPeriod) {
		dq_open_period = dqOpenPeriod;
	}
	public Integer getDq_open_unit() {
		return dq_open_unit;
	}
	public void setDq_open_unit(Integer dqOpenUnit) {
		dq_open_unit = dqOpenUnit;
	}
	public Integer getHolidays_delay_flag() {
		return holidays_delay_flag;
	}
	public void setHolidays_delay_flag(Integer holidaysDelayFlag) {
		holidays_delay_flag = holidaysDelayFlag;
	}
	public Integer getOpen_gain_flag() {
		return open_gain_flag;
	}
	public void setOpen_gain_flag(Integer openGainFlag) {
		open_gain_flag = openGainFlag;
	}
	public Integer getIs_bank_consign() {
		return is_bank_consign;
	}
	public void setIs_bank_consign(Integer isBankConsign) {
		is_bank_consign = isBankConsign;
	}
	public BigDecimal getJg_min_subamount() {
		return jg_min_subamount;
	}
	public void setJg_min_subamount(BigDecimal jgMinSubamount) {
		jg_min_subamount = jgMinSubamount;
	}
	public BigDecimal getGr_min_subamount() {
		return gr_min_subamount;
	}
	public void setGr_min_subamount(BigDecimal grMinSubamount) {
		gr_min_subamount = grMinSubamount;
	}
	public BigDecimal getJg_min_bidsamount() {
		return jg_min_bidsamount;
	}
	public void setJg_min_bidsamount(BigDecimal jgMinBidsamount) {
		jg_min_bidsamount = jgMinBidsamount;
	}
	public BigDecimal getGr_min_bidsamount() {
		return gr_min_bidsamount;
	}
	public void setGr_min_bidsamount(BigDecimal grMinBidsamount) {
		gr_min_bidsamount = grMinBidsamount;
	}
	public BigDecimal getGr_min_appbidsamount() {
		return gr_min_appbidsamount;
	}
	public void setGr_min_appbidsamount(BigDecimal grMinAppbidsamount) {
		gr_min_appbidsamount = grMinAppbidsamount;
	}
	public BigDecimal getJg_min_appbidsamount() {
		return jg_min_appbidsamount;
	}
	public void setJg_min_appbidsamount(BigDecimal jgMinAppbidsamount) {
		jg_min_appbidsamount = jgMinAppbidsamount;
	}
	public BigDecimal getMin_redeem_vol() {
		return min_redeem_vol;
	}
	public void setMin_redeem_vol(BigDecimal minRedeemVol) {
		min_redeem_vol = minRedeemVol;
	}
	public Integer getCoerce_redeem_flag() {
		return coerce_redeem_flag;
	}
	public void setCoerce_redeem_flag(Integer coerceRedeemFlag) {
		coerce_redeem_flag = coerceRedeemFlag;
	}
	public Integer getLarge_redeem_flag() {
		return large_redeem_flag;
	}
	public void setLarge_redeem_flag(Integer largeRedeemFlag) {
		large_redeem_flag = largeRedeemFlag;
	}
	public Integer getLarge_redeem_condition() {
		return large_redeem_condition;
	}
	public void setLarge_redeem_condition(Integer largeRedeemCondition) {
		large_redeem_condition = largeRedeemCondition;
	}
	public BigDecimal getLarge_redeem_percent() {
		return large_redeem_percent;
	}
	public void setLarge_redeem_percent(BigDecimal largeRedeemPercent) {
		large_redeem_percent = largeRedeemPercent;
	}
	public Integer getLarge_redeem_deal() {
		return large_redeem_deal;
	}
	public void setLarge_redeem_deal(Integer largeRedeemDeal) {
		large_redeem_deal = largeRedeemDeal;
	}
	//����Ͷ20050111
    private java.lang.Integer ben_period;

    private java.lang.String prov_level;

    private java.lang.String prov_level_name;

    private java.math.BigDecimal ben_amount;

    private java.lang.Integer contract_num;

    private java.lang.Integer ben_num;

    private int ben_period_temp;

    private Integer end_flag;

    //�»�
    private java.lang.Integer product_from;
    private java.lang.Integer last_post_date;
    private java.lang.Integer sl_flag;
    private java.lang.Integer all_flag;
    private java.lang.Integer flag; //1����2������

    //	�»�����20051012
    private java.math.BigDecimal amount_min; //��Ʒ��ģ��

    private java.math.BigDecimal amount_max; //��Ʒ��ģ��

    private java.lang.Integer admin_manager2; //ʱ��

    private java.lang.Integer matain_manager; //ʱ��

    private java.lang.Integer change_wt_flag; //ʱ��

    private java.lang.Integer intrust_flag3; //ʱ��

    private java.lang.Integer intrust_flag4; //ʱ��

    private java.lang.String entity_type;

    private java.lang.String entity_type_name;

    private java.lang.String deal_type;

    private java.lang.String deal_type_name;

    //�޸���Ϣ
    private java.lang.String field_name;

    private java.lang.String field_cn_name;

    private java.lang.String old_field_info;

    private java.lang.String new_field_info;

    private java.lang.String quality_level;

    private java.lang.String quality_level_name;

    private java.lang.String product_info;

    private String productstatusName;

    //	  add by jinxr 2007/4/26
    private String busi_name;
    private String cust_name;

    private java.math.BigDecimal ht_money;

    private java.math.BigDecimal cw_money;

    private java.math.BigDecimal ben_money;

    private java.math.BigDecimal gr_amount;

    private java.math.BigDecimal jg_amount;

    private Integer valid_period2;

    private Integer sub_flag;

    /**
     * add by tsg 2007-11-13
     */

    private String DBdriver;

    //2008-07-30 YZJ
    private String bg_bank_id; //������ID

    private String bg_bank_name; //����������

    private Integer old_end_date;

    private Integer new_end_date;

    private Integer business_end_flag;

    private Integer business_end_date;

    private Integer fact_end_date;

    //20090324 add by nizh Ԥ���������ʱ��
    private Integer task_date;

    //	20090331 add by nizh Ͷ������ ���Ͷ�ʡ����� ��ͬ¼���ʱ��ʹ��
    private String invest_type;

    //
    private String sAdmin_man;

    private String sAdmin_man2;

    private String sMatain_man;

    private BigDecimal balance_3101; //���й�ģ

    private BigDecimal balance_all; //�ʲ�

    private BigDecimal balance_lr; //����

    private Integer gr_num; //������

    private Integer jg_num; //������

    private Integer time_flag; //�������: 1201(TINEEGERPARAM ����)

    private Integer sub_man; //������

    private String sub_man_name;

    private Integer busi_flag;

    private Integer op_code;
    
    private Integer check_man;

    private Integer current_month; //��ǰ�������

    private Integer hq_date;

    private BigDecimal nav_price1;

    private BigDecimal nav_price2;

    private String tg_acct_name;

    private String op_name;

    private BigDecimal curr_fact_money;

    private String intrust_flag1_name;

    private String[] total_fact_money;

    private java.math.BigDecimal busi_nav_price;

    private java.math.BigDecimal prov_level_a_money;//����������

    private java.math.BigDecimal prov_level_b_money;//һ��������

    private Integer org_count;//��������

    private Integer person_count;//��������
    
    private java.math.BigDecimal person_money;//�����Ϲ���� add by liug 20100817

    private java.math.BigDecimal org_money;//�����Ϲ���� add by liug 20100817
    
    private java.math.BigDecimal original_money; //��Ʒ��ʼļ����� 
    
    private Integer with_bank_flag;
    
    private java.math.BigDecimal qualified_count;//�ϸ�Ͷ��������
    
    private java.math.BigDecimal qualified_money;//�ϸ�Ͷ���˽��
    
    private java.math.BigDecimal qualified_amount;//�ϸ�Ͷ���˷ݶ�
	
    private java.math.BigDecimal min_buy_limit;//��ͬ��ͽ��
    private java.math.BigDecimal max_buy_limit;//��ͬ��߽��
    
    private Integer trust_fee_period;
    private int permission_flag;//PERMISSION_FLAG ����@IN_OP_CODE �Դ˲�Ʒ�Ƿ���Ȩ�� 0ûȨ�� 1��Ȩ��

    /**
     * ��ʼ������
     */
    private BigDecimal ext_rate1;

    /**
     * ��ֹ������
     */
    private BigDecimal ext_rate2;

    /**
     * ��ֵ����
     */
    private Integer nav_float_num;

    private BigDecimal trade_tax_rate;//2009-12-09 ADD YZJ

    private BigDecimal fee;//2009-12-09 ADD YZJ

    /**
     * ��������
     */
    private String managetype_name;

    //���� ��ͬ����
    private String trust_contract_name;

    //20100308 lym
    private String bank_name;

    private String acct_name;

    private String bank_acct;

    private Integer manager_type;

    private BigDecimal result_standard;
    
    private Integer share_flag;
    
    private Integer coperate_type;// ������ʽ��1:���ź���;2:֤�ź���;3:˽ļ�������;4:��������
    
    private String gov_prov_pegional;
    private String gov_pegional;

    private String sub_product_code;
    
    private Integer sub_fund_flag;//�ʽ�������ñ�־ 0��1��
    
    private Integer prov_flag;//���������־:1���� 2һ�� 3�Ӻ�
    
    private Integer deal_flag;
    
    //-------- 2011-01-20 qmh add----
    
	private Integer qualified_flag ;
	
	private Integer qualified_num ;//��ͬ����
	
	private Integer asfund_flag ;
	
	private Integer gain_flag;
	private Integer city_serial_no;//�ƽ�س�������
	
	private Integer dq_open_flag;  		//�Ӳ�Ʒ���ڿ��ű�־
	private Integer dq_open_period;		//�Ӳ�Ʒ��������
	private Integer dq_open_unit;			//�Ӳ�Ʒ���ŵ�λ
	private Integer holidays_delay_flag;	//�ڼ���˳�ӱ�־
	
	private Integer open_gain_flag;		//�Ƿ���Ὺ��������
	
	private Integer is_bank_consign; //�Ƿ����д���
	
	private BigDecimal jg_min_subamount;//�����Ϲ����ٽ��

	private BigDecimal gr_min_subamount;//�����Ϲ����ٽ��

	private BigDecimal jg_min_bidsamount;//�����״��깺���ٽ��

	private BigDecimal gr_min_bidsamount;//�����״��깺���ٽ��

	private BigDecimal gr_min_appbidsamount;//����׷���깺��ͽ��

	private BigDecimal jg_min_appbidsamount;//����׷���깺��ͽ��

	private BigDecimal min_redeem_vol;//���ٱ����ݶ�

	private Integer coerce_redeem_flag;//��غ���зݶ�С����С�����ݶ�ʱ�Ƿ��ȡǿ����أ�1.�ǣ�2.��

	private Integer large_redeem_flag;//�Ƿ���޶����:1.�ǣ�2.��

	private Integer large_redeem_condition;//�޶�����ж�������1.������طݶ��жϣ�2.������طݶ��ж�

	private BigDecimal large_redeem_percent;//�����ܷݶ�İٷֱ�

	private Integer large_redeem_deal;//�޶���ش���ʽ��1.˳�ӣ�2.���ݿͻ��������ݴ���
	
	private Integer rg_bond_flag;
	/**
	 * @return ���� service_man��
	 */
	public Integer getService_man() {
		return service_man;
	}
	/**
	 * @param service_man Ҫ���õ� service_man��
	 */
	public void setService_man(Integer service_man) {
		this.service_man = service_man;
	}
	/**
	 * @return ���� summary��
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary Ҫ���õ� summary��
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return ���� status��
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status Ҫ���õ� status��
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return ���� task_name��
	 */
	public String getTask_name() {
		return task_name;
	}
	/**
	 * @param task_name Ҫ���õ� task_name��
	 */
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	private java.lang.Integer valid_period;
	 private java.lang.Integer item_id;
	
	public java.lang.Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(java.lang.Integer itemId) {
		item_id = itemId;
	}

	public java.lang.Integer getValid_period() {
		return valid_period;
	}

	public void setValid_period(java.lang.Integer validPeriod) {
		valid_period = validPeriod;
	}

	public String getList_name() {
		return list_name;
	}

	public void setList_name(String list_name) {
		this.list_name = list_name;
	}

	public Integer getList_id() {
		return list_id;
	}

	public void setList_id(Integer list_id) {
		this.list_id = list_id;
	}

	/**
	 * @return
	 */
	public String getProduct_code() {
		return product_code;
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
	public String getProduct_name() {
		return product_name;
	}

	/**
	 * @param string
	 */
	public void setProduct_code(String string) {
		product_code = string;
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
	public void setProduct_name(String string) {
		product_name = string;
	}

	/**
	 * @return
	 */
	public String getProduct_status() {
		return product_status;
	}

	/**
	 * @param string
	 */
	public void setProduct_status(String string) {
		product_status = string;
	}

	/**
	 * @return
	 */
	public Integer getEnd_date() {
		return end_date;
	}

	/**
	 * @return
	 */
	public Integer getStart_date() {
		return start_date;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(Integer integer) {
		end_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setStart_date(Integer integer) {
		start_date = integer;
	}

	/**
	 * @return
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}

	/**
	 * @return
	 */
	public Integer getOpen_flag() {
		return open_flag;
	}

	/**
	 * @param integer
	 */
	public void setCheck_flag(Integer integer) {
		check_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setOpen_flag(Integer integer) {
		open_flag = integer;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getIntrust_flag1() {
		return intrust_flag1;
	}

	/**
	 * @param integer
	 */
	public void setIntrust_flag1(java.lang.Integer integer) {
		intrust_flag1 = integer;
	}

	/**
	 * @return
	 */
	public Integer getPeriod_unit() {
		return period_unit;
	}

	/**
	 * @param integer
	 */
	public void setPeriod_unit(Integer integer) {
		period_unit = integer;
	}

	/**
	 * @return
	 */
	public Integer getTask_type() {
		return task_type;
	}

	/**
	 * @param integer
	 */
	public void setTask_type(Integer integer) {
		task_type = integer;
	}

	/**
	 * @return
	 */
	public Integer getBook_code() {
		return book_code;
	}

	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @param integer
	 */
	public void setBook_code(Integer integer) {
		book_code = integer;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @return
	 */
	public String getContract_bh() {
		return contract_bh;
	}

	/**
	 * @param string
	 */
	public void setContract_bh(String string) {
		contract_bh = string;
	}

	/**
	 * @return
	 */
	public String getContract_sub_bh() {
		return contract_sub_bh;
	}

	/**
	 * @return
	 */
	public String getFrom_cust_name() {
		return from_cust_name;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @return
	 */
	public String getTo_cust_name() {
		return to_cust_name;
	}

	/**
	 * @return
	 */
	public Integer getTrans_date_begin() {
		return trans_date_begin;
	}

	/**
	 * @return
	 */
	public Integer getTrans_date_end() {
		return trans_date_end;
	}

	/**
	 * @return
	 */
	public String getTrans_type() {
		return trans_type;
	}

	/**
	 * @param string
	 */
	public void setContract_sub_bh(String string) {
		contract_sub_bh = string;
	}

	/**
	 * @param string
	 */
	public void setFrom_cust_name(String string) {
		from_cust_name = string;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param string
	 */
	public void setTo_cust_name(String string) {
		to_cust_name = string;
	}

	/**
	 * @param integer
	 */
	public void setTrans_date_begin(Integer integer) {
		trans_date_begin = integer;
	}

	/**
	 * @param integer
	 */
	public void setTrans_date_end(Integer integer) {
		trans_date_end = integer;
	}

	/**
	 * @param string
	 */
	public void setTrans_type(String string) {
		trans_type = string;
	}

	/**
	 * @return
	 */
	public java.lang.String getCity_name() {
		return city_name;
	}

	/**
	 * @param string
	 */
	public void setCity_name(java.lang.String string) {
		city_name = string;
	}

	/**
	 * @return
	 */
	public java.lang.String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getTrade_date() {
		return trade_date;
	}

	/**
	 * @return
	 */
	public BigDecimal getTrade_rate() {
		return trade_rate;
	}

	/**
	 * @return
	 */
	public java.lang.String getTrade_type() {
		return trade_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getTrade_type_name() {
		return trade_type_name;
	}

	/**
	 * @param string
	 */
	public void setDescription(java.lang.String string) {
		description = string;
	}

	/**
	 * @param integer
	 */
	public void setTrade_date(java.lang.Integer integer) {
		trade_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setTrade_rate(BigDecimal decimal) {
		trade_rate = decimal;
	}

	/**
	 * @param string
	 */
	public void setTrade_type(java.lang.String string) {
		trade_type = string;
	}

	/**
	 * @param string
	 */
	public void setTrade_type_name(java.lang.String string) {
		trade_type_name = string;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getBegin_price() {
		return begin_price;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getDiv_rate() {
		return div_rate;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getEnd_price() {
		return end_price;
	}

	/**
	 * @param decimal
	 */
	public void setBegin_price(java.math.BigDecimal decimal) {
		begin_price = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setDiv_rate(java.math.BigDecimal decimal) {
		div_rate = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setEnd_price(java.math.BigDecimal decimal) {
		end_price = decimal;
	}

	public java.lang.Integer getSub_product_id() {
		return sub_product_id;
	}

	public void setSub_product_id(java.lang.Integer sub_product_id) {
		this.sub_product_id = sub_product_id;
	}
	public java.lang.Integer getSale_status() {
		return sale_status;
	}
	public void setSale_status(java.lang.Integer saleStatus) {
		sale_status = saleStatus;
	}
	public java.lang.String getRisk_level() {
		return risk_level;
	}
	public void setRisk_level(java.lang.String riskLevel) {
		risk_level = riskLevel;
	}
	
	

	/**
	 * @return ���� r_chanel_rate��
	 */
	public java.math.BigDecimal getR_chanel_rate() {
		return r_chanel_rate;
	}
	/**
	 * @param r_chanel_rate Ҫ���õ� r_chanel_rate��
	 */
	public void setR_chanel_rate(java.math.BigDecimal r_chanel_rate) {
		this.r_chanel_rate = r_chanel_rate;
	}
	/**
	 * @return ���� r_channel_id��
	 */
	public java.lang.Integer getR_channel_id() {
		return r_channel_id;
	}
	/**
	 * @param r_channel_id Ҫ���õ� r_channel_id��
	 */
	public void setR_channel_id(java.lang.Integer r_channel_id) {
		this.r_channel_id = r_channel_id;
	}
	public java.lang.String getContract_terms() {
		return contract_terms;
	}
	public void setContract_terms(java.lang.String contract_terms) {
		this.contract_terms = contract_terms;
	}
	/**
	 * @return ���� fact_money2��
	 */
	public java.math.BigDecimal getFact_money2() {
		return fact_money2;
	}
	/**
	 * @param fact_money2 Ҫ���õ� fact_money2��
	 */
	public void setFact_money2(java.math.BigDecimal fact_money2) {
		this.fact_money2 = fact_money2;
	}
	/**
	 * @return ���� ensure_method��
	 */
	public java.lang.String getEnsure_method() {
		return ensure_method;
	}
	/**
	 * @param ensure_method Ҫ���õ� ensure_method��
	 */
	public void setEnsure_method(java.lang.String ensure_method) {
		this.ensure_method = ensure_method;
	}
	/**
	 * @return ���� channel_type��
	 */
	public String getChannel_type() {
		return channel_type;
	}
	/**
	 * @param channel_type Ҫ���õ� channel_type��
	 */
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	
	/**
	 * @return ���� commission_rate��
	 */
	public java.math.BigDecimal getCommission_rate() {
		return commission_rate;
	}
	/**
	 * @param commission_rate Ҫ���õ� commission_rate��
	 */
	public void setCommission_rate(java.math.BigDecimal commission_rate) {
		this.commission_rate = commission_rate;
	}
	/**
	 * @return ���� pre_end_time��
	 */
	public String getPre_end_time() {
		return pre_end_time;
	}
	/**
	 * @param pre_end_time Ҫ���õ� pre_end_time��
	 */
	public void setPre_end_time(String pre_end_time) {
		this.pre_end_time = pre_end_time;
	}
	/**
	 * @return ���� pre_start_time��
	 */
	public String getPre_start_time() {
		return pre_start_time;
	}
	/**
	 * @param pre_start_time Ҫ���õ� pre_start_time��
	 */
	public void setPre_start_time(String pre_start_time) {
		this.pre_start_time = pre_start_time;
	}
    
    
	/**
	 * @return ���� daysBeforeDue��
	 */
	public Integer getDaysBeforeDue() {
		return daysBeforeDue;
	}
	/**
	 * @param daysBeforeDue Ҫ���õ� daysBeforeDue��
	 */
	public void setDaysBeforeDue(Integer daysBeforeDue) {
		this.daysBeforeDue = daysBeforeDue;
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
	 * @return ���� preStatus��
	 */
	public Integer getPreStatus() {
		return preStatus;
	}
	/**
	 * @param preStatus Ҫ���õ� preStatus��
	 */
	public void setPreStatus(Integer preStatus) {
		this.preStatus = preStatus;
	}
	/**
	 * @return ���� redeem_freeze��
	 */
	public Integer getRedeem_freeze() {
		return redeem_freeze;
	}
	/**
	 * @param redeem_freeze Ҫ���õ� redeem_freeze��
	 */
	public void setRedeem_freeze(Integer redeem_freeze) {
		this.redeem_freeze = redeem_freeze;
	}
	/**
	 * @return ���� min_redeem_vol2��
	 */
	public BigDecimal getMin_redeem_vol2() {
		return min_redeem_vol2;
	}
	/**
	 * @param min_redeem_vol2 Ҫ���õ� min_redeem_vol2��
	 */
	public void setMin_redeem_vol2(BigDecimal min_redeem_vol2) {
		this.min_redeem_vol2 = min_redeem_vol2;
	}
	/**
	 * @return ���� lot_decimal_flag��
	 */
	public Integer getLot_decimal_flag() {
		return lot_decimal_flag;
	}
	/**
	 * @param lot_decimal_flag Ҫ���õ� lot_decimal_flag��
	 */
	public void setLot_decimal_flag(Integer lot_decimal_flag) {
		this.lot_decimal_flag = lot_decimal_flag;
	}
	/**
	 * @return ���� rg_bond_flag��
	 */
	public Integer getRg_bond_flag() {
		return rg_bond_flag;
	}
	/**
	 * @param rg_bond_flag Ҫ���õ� rg_bond_flag��
	 */
	public void setRg_bond_flag(Integer rg_bond_flag) {
		this.rg_bond_flag = rg_bond_flag;
	}
	
	
	/**
	 * @return ���� lower_limit��
	 */
	public java.math.BigDecimal getLower_limit() {
		return lower_limit;
	}
	/**
	 * @param lower_limit Ҫ���õ� lower_limit��
	 */
	public void setLower_limit(java.math.BigDecimal lower_limit) {
		this.lower_limit = lower_limit;
	}
	/**
	 * @return ���� upper_limit��
	 */
	public java.math.BigDecimal getUpper_limit() {
		return upper_limit;
	}
	/**
	 * @param upper_limit Ҫ���õ� upper_limit��
	 */
	public void setUpper_limit(java.math.BigDecimal upper_limit) {
		this.upper_limit = upper_limit;
	}
	
	
	/**
	 * @return ���� exp_rate��
	 */
	public java.math.BigDecimal getExp_rate() {
		return exp_rate;
	}
	/**
	 * @param exp_rate Ҫ���õ� exp_rate��
	 */
	public void setExp_rate(java.math.BigDecimal exp_rate) {
		this.exp_rate = exp_rate;
	}	

	/**
	 * @return ���� rate_serial_no��
	 */
	public Integer getRate_serial_no() {
		return rate_serial_no;
	}
	/**
	 * @param rate_serial_no Ҫ���õ� rate_serial_no��
	 */
	public void setRate_serial_no(Integer rate_serial_no) {
		this.rate_serial_no = rate_serial_no;
	}
	
	
	/**
	 * @return ���� expre_start_time��
	 */
	public String getExpre_start_time() {
		return expre_start_time;
	}
	/**
	 * @param expre_start_time Ҫ���õ� expre_start_time��
	 */
	public void setExpre_start_time(String expre_start_time) {
		this.expre_start_time = expre_start_time;
	}
	/**
	 * @return ���� expre_end_time��
	 */
	public String getExpre_end_time() {
		return expre_end_time;
	}
	/**
	 * @param expre_end_time Ҫ���õ� expre_end_time��
	 */
	public void setExpre_end_time(String expre_end_time) {
		this.expre_end_time = expre_end_time;
	}
	/**
	 * @return ���� pre_valid_days��
	 */
	public Integer getPre_valid_days() {
		return pre_valid_days;
	}
	/**
	 * @param pre_valid_days Ҫ���õ� pre_valid_days��
	 */
	public void setPre_valid_days(Integer pre_valid_days) {
		this.pre_valid_days = pre_valid_days;
	}
	/**
	 * @return ���� contract_single_flag��
	 */
	public Integer getContract_single_flag() {
		return contract_single_flag;
	}
	/**
	 * @param contract_single_flag Ҫ���õ� contract_single_flag��
	 */
	public void setContract_single_flag(Integer contract_single_flag) {
		this.contract_single_flag = contract_single_flag;
	}
	
	/**
	 * @return ���� pre_min_amount��
	 */
	public Integer getPre_min_amount() {
		return pre_min_amount;
	}
	/**
	 * @param pre_min_amount Ҫ���õ� pre_min_amount��
	 */
	public void setPre_min_amount(Integer pre_min_amount) {
		this.pre_min_amount = pre_min_amount;
	}
	/**
	 * @return ���� template_id��
	 */
	public Integer getTemplate_id() {
		return template_id;
	}
	/**
	 * @param template_id Ҫ���õ� template_id��
	 */
	public void setTemplate_id(Integer template_id) {
		this.template_id = template_id;
	}
	/**
	 * @return ���� up_to_show��
	 */
	public Integer getUp_to_show() {
		return up_to_show;
	}
	/**
	 * @param up_to_show Ҫ���õ� up_to_show��
	 */
	public void setUp_to_show(Integer up_to_show) {
		this.up_to_show = up_to_show;
	}
	
	
	
	
	
	
	/**
	 * @return ���� channel_cost��
	 */
	public BigDecimal getChannel_cost() {
		return channel_cost;
	}
	/**
	 * @param channel_cost Ҫ���õ� channel_cost��
	 */
	public void setChannel_cost(BigDecimal channel_cost) {
		this.channel_cost = channel_cost;
	}
	/**
	 * @return ���� product_cost��
	 */
	public BigDecimal getProduct_cost() {
		return product_cost;
	}
	/**
	 * @param product_cost Ҫ���õ� product_cost��
	 */
	public void setProduct_cost(BigDecimal product_cost) {
		this.product_cost = product_cost;
	}
	
	
	/**
	 * @return ���� sAdmin_man��
	 */
	public String getSAdmin_man() {
		return sAdmin_man;
	}
	/**
	 * @param admin_man Ҫ���õ� sAdmin_man��
	 */
	public void setSAdmin_man(String admin_man) {
		sAdmin_man = admin_man;
	}
	/**
	 * @return ���� sAdmin_man2��
	 */
	public String getSAdmin_man2() {
		return sAdmin_man2;
	}
	/**
	 * @param admin_man2 Ҫ���õ� sAdmin_man2��
	 */
	public void setSAdmin_man2(String admin_man2) {
		sAdmin_man2 = admin_man2;
	}
	/**
	 * @return ���� sMatain_man��
	 */
	public String getSMatain_man() {
		return sMatain_man;
	}
	/**
	 * @param matain_man Ҫ���õ� sMatain_man��
	 */
	public void setSMatain_man(String matain_man) {
		sMatain_man = matain_man;
	}
}
