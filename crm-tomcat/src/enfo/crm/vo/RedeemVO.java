/*
 * �������� 2009-12-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author enfo
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class RedeemVO {
	private Integer serial_no;
	private Integer ben_serail_no;//��ӦTBENIFITOR���е�SERIAL_NO
	private Integer product_id;
	private String contract_bh;
	private Integer list;
	private BigDecimal redeem_amout;//������طݶ�
	private Integer sq_date;
	private BigDecimal fee;
	private BigDecimal gs_fee;//���õĹ�˾ռ�Ȳ���
	private BigDecimal nav_price;//��ص�λ��ֵ
	private BigDecimal redeem_money;//��ؽ��
	private Integer trans_date;//��ش������ڣ������գ�
	private Integer input_man;
	private Integer input_time;
	private Integer check_flag; // 1δ��� 2����� 3 �Ѳ������ 4�ѶҸ�
	private Integer check_man;
	private Integer check_time;
	private Integer check_man2;
	private Integer check_time2;

	private Integer list_id;//������ID
	private Integer start_date;
	private Integer end_date;
	private Integer flag;

	private BigDecimal redeem_rate;//��ر���
	private BigDecimal share_money;//�������б��� add by lk 20091023
	private BigDecimal fdyj_share_money;//����ҵ������ add by lk 20091023
	
	private String cust_name;
	
	private Integer transfer_product_id;
	private Integer transfer_sub_product_id;
	private BigDecimal transfer_money;
	
	/**
	 * @return ���� cust_name��
	 */
	public String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name Ҫ���õ� cust_name��
	 */
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	/**
	 * @return
	 */
	public Integer getBen_serail_no() {
		return ben_serail_no;
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
	public Integer getCheck_man() {
		return check_man;
	}

	/**
	 * @return
	 */
	public Integer getCheck_man2() {
		return check_man2;
	}

	/**
	 * @return
	 */
	public Integer getCheck_time() {
		return check_time;
	}

	/**
	 * @return
	 */
	public Integer getCheck_time2() {
		return check_time2;
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
	public Integer getEnd_date() {
		return end_date;
	}

	/**
	 * @return
	 */
	public BigDecimal getFdyj_share_money() {
		return fdyj_share_money;
	}

	/**
	 * @return
	 */
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * @return
	 */
	public Integer getFlag() {
		return flag;
	}

	/**
	 * @return
	 */
	public BigDecimal getGs_fee() {
		return gs_fee;
	}

	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public Integer getInput_time() {
		return input_time;
	}

	/**
	 * @return
	 */
	public Integer getList() {
		return list;
	}

	/**
	 * @return
	 */
	public Integer getList_id() {
		return list_id;
	}

	/**
	 * @return
	 */
	public BigDecimal getNav_price() {
		return nav_price;
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
	public BigDecimal getRedeem_amout() {
		return redeem_amout;
	}

	/**
	 * @return
	 */
	public BigDecimal getRedeem_money() {
		return redeem_money;
	}

	/**
	 * @return
	 */
	public BigDecimal getRedeem_rate() {
		return redeem_rate;
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
	public BigDecimal getShare_money() {
		return share_money;
	}

	/**
	 * @return
	 */
	public Integer getSq_date() {
		return sq_date;
	}

	/**
	 * @return
	 */
	public Integer getStart_date() {
		return start_date;
	}

	/**
	 * @return
	 */
	public Integer getTrans_date() {
		return trans_date;
	}

	/**
	 * @param integer
	 */
	public void setBen_serail_no(Integer integer) {
		ben_serail_no = integer;
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
	public void setCheck_man(Integer integer) {
		check_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_man2(Integer integer) {
		check_man2 = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_time(Integer integer) {
		check_time = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_time2(Integer integer) {
		check_time2 = integer;
	}

	/**
	 * @param string
	 */
	public void setContract_bh(String string) {
		contract_bh = string;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(Integer integer) {
		end_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setFdyj_share_money(BigDecimal decimal) {
		fdyj_share_money = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setFee(BigDecimal decimal) {
		fee = decimal;
	}

	/**
	 * @param integer
	 */
	public void setFlag(Integer integer) {
		flag = integer;
	}

	/**
	 * @param decimal
	 */
	public void setGs_fee(BigDecimal decimal) {
		gs_fee = decimal;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setInput_time(Integer integer) {
		input_time = integer;
	}

	/**
	 * @param integer
	 */
	public void setList(Integer integer) {
		list = integer;
	}

	/**
	 * @param integer
	 */
	public void setList_id(Integer integer) {
		list_id = integer;
	}

	/**
	 * @param decimal
	 */
	public void setNav_price(BigDecimal decimal) {
		nav_price = decimal;
	}

	/**
	 * @param integer
	 */
	public void setProduct_id(Integer integer) {
		product_id = integer;
	}

	/**
	 * @param decimal
	 */
	public void setRedeem_amout(BigDecimal decimal) {
		redeem_amout = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setRedeem_money(BigDecimal decimal) {
		redeem_money = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setRedeem_rate(BigDecimal decimal) {
		redeem_rate = decimal;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param decimal
	 */
	public void setShare_money(BigDecimal decimal) {
		share_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setSq_date(Integer integer) {
		sq_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setStart_date(Integer integer) {
		start_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setTrans_date(Integer integer) {
		trans_date = integer;
	}

	/**
	 * @return ���� transfer_money��
	 */
	public BigDecimal getTransfer_money() {
		return transfer_money;
	}
	/**
	 * @param transfer_money Ҫ���õ� transfer_money��
	 */
	public void setTransfer_money(BigDecimal transfer_money) {
		this.transfer_money = transfer_money;
	}
	/**
	 * @return ���� transfer_product_id��
	 */
	public Integer getTransfer_product_id() {
		return transfer_product_id;
	}
	/**
	 * @param transfer_product_id Ҫ���õ� transfer_product_id��
	 */
	public void setTransfer_product_id(Integer transfer_product_id) {
		this.transfer_product_id = transfer_product_id;
	}
	/**
	 * @return ���� transfer_sub_product_id��
	 */
	public Integer getTransfer_sub_product_id() {
		return transfer_sub_product_id;
	}
	/**
	 * @param transfer_sub_product_id Ҫ���õ� transfer_sub_product_id��
	 */
	public void setTransfer_sub_product_id(Integer transfer_sub_product_id) {
		this.transfer_sub_product_id = transfer_sub_product_id;
	}
}
