package enfo.crm.vo;

import java.math.BigDecimal;

public class ChannelVO {
	private Integer bookCode;//帐套
	private Integer channelID;
	private String channelCode;
	private String channelName;
	private String address;
	private String link_man;//联系人姓名
	private String zip;//邮编
	private String phone;
	private String fax;
	private String summary;
	private String bankId;
	private Integer sellFlag;
	private Integer inputMan;//输入人
	private String channel_type;
	private String channel_type_name;
	private Integer parent_channel;
	private Integer cell_id;
	private Integer channel_id;
	private Integer begin_date;
	private Integer end_date;
	private Integer group_type;
	private Integer group_product;
	private Integer serial_no;
	private String plan_time;
	private String plan_contnet;
	private Integer plan_man;
	private String service_time;
	private String service_content;
	private Integer service_man;
	private Integer input_man;
	private Integer sale_man;
	
	private Integer list_product;
	private Integer list_channel;
	private Integer list_saleman;
	private Integer list_serviceman;
	private Integer intrust_flag1;
	private Integer product_id;
	
	private String product_code;
	private String product_name;
	private String channel_memo;
	private Integer cust_id;
	private String cust_name;
	private Integer start_date;
	private BigDecimal rg_money;
	
	private Integer list_cust;
	private Integer List_channel_coopertype;
	private String channel_coopertype;
	
	private Integer recommend_flag;
	private Integer normal_flag;
	private Integer end_flag;
	private Integer sub_product_id;
	private String product_status;
	private Integer intrust_flag;
	
	
	/**
	 * @return 返回 intrust_flag。
	 */
	public Integer getIntrust_flag() {
		return intrust_flag;
	}
	/**
	 * @param intrust_flag 要设置的 intrust_flag。
	 */
	public void setIntrust_flag(Integer intrust_flag) {
		this.intrust_flag = intrust_flag;
	}
	/**
	 * @return 返回 product_status。
	 */
	public String getProduct_status() {
		return product_status;
	}
	/**
	 * @param product_status 要设置的 product_status。
	 */
	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}
	/**
	 * @return 返回 sub_product_id。
	 */
	public Integer getSub_product_id() {
		return sub_product_id;
	}
	/**
	 * @param sub_product_id 要设置的 sub_product_id。
	 */
	public void setSub_product_id(Integer sub_product_id) {
		this.sub_product_id = sub_product_id;
	}
	public Integer getRecommend_flag() {
		return recommend_flag;
	}
	public void setRecommend_flag(Integer recommendFlag) {
		recommend_flag = recommendFlag;
	}
	public Integer getNormal_flag() {
		return normal_flag;
	}
	public void setNormal_flag(Integer normalFlag) {
		normal_flag = normalFlag;
	}
	public Integer getEnd_flag() {
		return end_flag;
	}
	public void setEnd_flag(Integer endFlag) {
		end_flag = endFlag;
	}
	public Integer getList_cust() {
		return list_cust;
	}
	public void setList_cust(Integer listCust) {
		list_cust = listCust;
	}
	public Integer getList_channel_coopertype() {
		return List_channel_coopertype;
	}
	public void setList_channel_coopertype(Integer listChannelCoopertype) {
		List_channel_coopertype = listChannelCoopertype;
	}
	public String getChannel_coopertype() {
		return channel_coopertype;
	}
	public void setChannel_coopertype(String channelCoopertype) {
		channel_coopertype = channelCoopertype;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer productId) {
		product_id = productId;
	}
	public Integer getIntrust_flag1() {
		return intrust_flag1;
	}
	public void setIntrust_flag1(Integer intrustFlag1) {
		intrust_flag1 = intrustFlag1;
	}
	public Integer getList_product() {
		return list_product;
	}
	public void setList_product(Integer listProduct) {
		list_product = listProduct;
	}
	public Integer getList_channel() {
		return list_channel;
	}
	public void setList_channel(Integer listChannel) {
		list_channel = listChannel;
	}
	public Integer getList_saleman() {
		return list_saleman;
	}
	public void setList_saleman(Integer listSaleman) {
		list_saleman = listSaleman;
	}
	public Integer getList_serviceman() {
		return list_serviceman;
	}
	public void setList_serviceman(Integer listServiceman) {
		list_serviceman = listServiceman;
	}
	public Integer getSale_man() {
		return sale_man;
	}
	public void setSale_man(Integer saleMan) {
		sale_man = saleMan;
	}
	public Integer getService_man() {
		return service_man;
	}
	public void setService_man(Integer serviceMan) {
		service_man = serviceMan;
	}
	public Integer getGroup_type() {
		return group_type;
	}
	public void setGroup_type(Integer groupType) {
		group_type = groupType;
	}
	public Integer getGroup_product() {
		return group_product;
	}
	public void setGroup_product(Integer groupProduct) {
		group_product = groupProduct;
	}
	public Integer getCell_id() {
		return cell_id;
	}
	public void setCell_id(Integer cellId) {
		cell_id = cellId;
	}
	public Integer getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(Integer channelId) {
		channel_id = channelId;
	}
	public Integer getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(Integer beginDate) {
		begin_date = beginDate;
	}
	public Integer getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Integer endDate) {
		end_date = endDate;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String productCode) {
		product_code = productCode;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String productName) {
		product_name = productName;
	}
	public String getChannel_memo() {
		return channel_memo;
	}
	public void setChannel_memo(String channelMemo) {
		channel_memo = channelMemo;
	}
	public Integer getCust_id() {
		return cust_id;
	}
	public void setCust_id(Integer custId) {
		cust_id = custId;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String custName) {
		cust_name = custName;
	}
	public Integer getStart_date() {
		return start_date;
	}
	public void setStart_date(Integer startDate) {
		start_date = startDate;
	}
	public BigDecimal getRg_money() {
		return rg_money;
	}
	public void setRg_money(BigDecimal rgMoney) {
		rg_money = rgMoney;
	}
	
	public String getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String channelType) {
		channel_type = channelType;
	}
	public String getChannel_type_name() {
		return channel_type_name;
	}
	public void setChannel_type_name(String channelTypeName) {
		channel_type_name = channelTypeName;
	}
	public Integer getParent_channel() {
		return parent_channel;
	}
	public void setParent_channel(Integer parentChannel) {
		parent_channel = parentChannel;
	}
	public Integer getBookCode() {
		return bookCode;
	}
	public void setBookCode(Integer bookCode) {
		this.bookCode = bookCode;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLink_man() {
		return link_man;
	}
	public void setLink_man(String link_man) {
		this.link_man = link_man;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public Integer getInputMan() {
		return inputMan;
	}
	public void setInputMan(Integer inputMan) {
		this.inputMan = inputMan;
	}
	public Integer getChannelID() {
		return channelID;
	}
	public void setChannelID(Integer channelID) {
		this.channelID = channelID;
	}
	public Integer getSellFlag() {
		return sellFlag;
	}
	public void setSellFlag(Integer sellFlag) {
		this.sellFlag = sellFlag;
	}
	public Integer getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(Integer serialNo) {
		serial_no = serialNo;
	}

	public String getPlan_contnet() {
		return plan_contnet;
	}
	public void setPlan_contnet(String planContnet) {
		plan_contnet = planContnet;
	}

	public String getService_content() {
		return service_content;
	}
	public void setService_content(String serviceContent) {
		service_content = serviceContent;
	}
	public Integer getInput_man() {
		return input_man;
	}
	public void setInput_man(Integer inputMan) {
		input_man = inputMan;
	}
	public Integer getPlan_man() {
		return plan_man;
	}
	public void setPlan_man(Integer planMan) {
		plan_man = planMan;
	}
	public String getPlan_time() {
		return plan_time;
	}
	public void setPlan_time(String planTime) {
		plan_time = planTime;
	}
	public String getService_time() {
		return service_time;
	}
	public void setService_time(String serviceTime) {
		service_time = serviceTime;
	}
}
