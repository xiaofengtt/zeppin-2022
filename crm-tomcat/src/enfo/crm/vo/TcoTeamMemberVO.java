/*
 * �������� 2011-8-11
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class TcoTeamMemberVO {
	private Integer serial_no;
	private Integer team_id;
	private String team_name;
	private Integer team_member;
	private String team_member_name;
	private String team_position;
    private Integer input_man;
	private Integer input_time;
	public Integer getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(Integer serialNo) {
		serial_no = serialNo;
	}
	public Integer getTeam_id() {
		return team_id;
	}
	public void setTeam_id(Integer teamId) {
		team_id = teamId;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String teamName) {
		team_name = teamName;
	}
	public Integer getTeam_member() {
		return team_member;
	}
	public void setTeam_member(Integer teamMember) {
		team_member = teamMember;
	}
	public String getTeam_member_name() {
		return team_member_name;
	}
	public void setTeam_member_name(String teamMemberName) {
		team_member_name = teamMemberName;
	}
	public String getTeam_position() {
		return team_position;
	}
	public void setTeam_position(String teamPosition) {
		team_position = teamPosition;
	}
	public Integer getInput_man() {
		return input_man;
	}
	public void setInput_man(Integer inputMan) {
		input_man = inputMan;
	}
	public Integer getInput_time() {
		return input_time;
	}
	public void setInput_time(Integer inputTime) {
		input_time = inputTime;
	}
    
}
