/*
 * �������� 2011-8-26
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class TcoTeamInfoVO {
	private Integer team_id;
	private String team_name;
	private String team_summary;
	private Integer team_admin;
	private String team_admin_name;
	private Integer input_man;
	private Integer input_time;
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
	public String getTeam_summary() {
		return team_summary;
	}
	public void setTeam_summary(String teamSummary) {
		team_summary = teamSummary;
	}
	public Integer getTeam_admin() {
		return team_admin;
	}
	public void setTeam_admin(Integer teamAdmin) {
		team_admin = teamAdmin;
	}
	
	public String getTeam_admin_name() {
		return team_admin_name;
	}
	public void setTeam_admin_name(String teamAdminName) {
		team_admin_name = teamAdminName;
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
