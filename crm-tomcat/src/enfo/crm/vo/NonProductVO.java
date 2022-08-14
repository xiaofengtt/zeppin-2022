/*
 * �������� 2011-5-12
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author my
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class NonProductVO {
	
	private Integer nonproduct_id;				//����
	private String nonproduct_name;				//�����в�Ʒ����
	private String investment_direction;		//Ͷ���ֵ�1155��
	private String investment_direction_name;	//Ͷ������
	private Integer valid_priod_unit;			//���޵�λ��1��2��3��4��9��
	private Integer valid_priod;				//����
	private Integer start_date;					//��ʼ����
	private Integer end_date;					//��������
	private Integer fact_end_date;				//ʵ�ʽ�������
	private BigDecimal expect_money;			//Ԥ�ڲ�Ʒ���
	private BigDecimal expect_rate1;			//Ԥ������1
	private BigDecimal expect_rate2;			//Ԥ������2
	private BigDecimal face_moeny;				//ʵ�ʲ�Ʒ���
	private String investment_manager;			//Ͷ�ʹ�����
	private Integer partner_cust_id;			//�ϻ�����ҵID��TCustomers.CUST_ID��
	private String status; 						//״̬��ʹ�����в�Ʒ״̬���ֵ�1102��
	private Integer operator;					//¼�����Ա
	private java.sql.Timestamp input_time;		//¼��ʱ��
	private Integer check_flag;					//��˱�־1δ���2�����
	private Integer check_man;					//��˲���Ա
	private java.sql.Timestamp check_time;		//���ʱ��
	private Integer out_nonproduct_id;
	private Integer input_man;
	private String partner_cust_name;			//�ϻ���
	private String description;                //���
	

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPartner_cust_name() {
		return partner_cust_name;
	}
	public void setPartner_cust_name(String partnerCustName) {
		partner_cust_name = partnerCustName;
	}
	public Integer getOut_nonproduct_id() {
		return out_nonproduct_id;
	}
	public Integer getInput_man() {
		return input_man;
	}
	public void setInput_man(Integer inputMan) {
		input_man = inputMan;
	}
	public void setOut_nonproduct_id(Integer outNonproductId) {
		out_nonproduct_id = outNonproductId;
	}
	public Integer getNonproduct_id() {
		return nonproduct_id;
	}
	public void setNonproduct_id(Integer nonproductId) {
		nonproduct_id = nonproductId;
	}
	public String getNonproduct_name() {
		return nonproduct_name;
	}
	public void setNonproduct_name(String nonproductName) {
		nonproduct_name = nonproductName;
	}
	public String getInvestment_direction() {
		return investment_direction;
	}
	public void setInvestment_direction(String investmentDirection) {
		investment_direction = investmentDirection;
	}
	public String getInvestment_direction_name() {
		return investment_direction_name;
	}
	public void setInvestment_direction_name(String investmentDirectionName) {
		investment_direction_name = investmentDirectionName;
	}
	public Integer getValid_priod_unit() {
		return valid_priod_unit;
	}
	public void setValid_priod_unit(Integer validPriodUnit) {
		valid_priod_unit = validPriodUnit;
	}
	public Integer getValid_priod() {
		return valid_priod;
	}
	public void setValid_priod(Integer validPriod) {
		valid_priod = validPriod;
	}
	public Integer getStart_date() {
		return start_date;
	}
	public void setStart_date(Integer startDate) {
		start_date = startDate;
	}
	public Integer getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Integer endDate) {
		end_date = endDate;
	}
	public Integer getFact_end_date() {
		return fact_end_date;
	}
	public void setFact_end_date(Integer factEndDate) {
		fact_end_date = factEndDate;
	}
	public BigDecimal getExpect_money() {
		return expect_money;
	}
	public void setExpect_money(BigDecimal expectMoney) {
		expect_money = expectMoney;
	}
	public BigDecimal getExpect_rate1() {
		return expect_rate1;
	}
	public void setExpect_rate1(BigDecimal expectRate1) {
		expect_rate1 = expectRate1;
	}
	public BigDecimal getExpect_rate2() {
		return expect_rate2;
	}
	public void setExpect_rate2(BigDecimal expectRate2) {
		expect_rate2 = expectRate2;
	}
	public BigDecimal getFace_moeny() {
		return face_moeny;
	}
	public void setFace_moeny(BigDecimal faceMoeny) {
		face_moeny = faceMoeny;
	}
	public String getInvestment_manager() {
		return investment_manager;
	}
	public void setInvestment_manager(String investmentManager) {
		investment_manager = investmentManager;
	}
	public Integer getPartner_cust_id() {
		return partner_cust_id;
	}
	public void setPartner_cust_id(Integer partnerCustId) {
		partner_cust_id = partnerCustId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public java.sql.Timestamp getInput_time() {
		return input_time;
	}
	public void setInput_time(java.sql.Timestamp inputTime) {
		input_time = inputTime;
	}
	public Integer getCheck_flag() {
		return check_flag;
	}
	public void setCheck_flag(Integer checkFlag) {
		check_flag = checkFlag;
	}
	public Integer getCheck_man() {
		return check_man;
	}
	public void setCheck_man(Integer checkMan) {
		check_man = checkMan;
	}
	public java.sql.Timestamp getCheck_time() {
		return check_time;
	}
	public void setCheck_time(java.sql.Timestamp checkTime) {
		check_time = checkTime;
	}
	
	
	
}
