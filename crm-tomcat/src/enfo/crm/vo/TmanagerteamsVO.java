/*
 * �������� 2009-11-26
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @author wanghf
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class TmanagerteamsVO {
	private Integer team_id;
	private String team_no;
	private String team_name;
	private Integer create_date;
	private Integer leader;
	private Integer leader2;
	private Integer leader3;
	private String leader_name;
	private String description;
	private Integer input_man;
	private Integer mark_flag;
	
	private Integer begin_date;
	private Integer end_date;
	private Integer parent_id;
	
	
	
	
	/**
	 * @return ���� leader3��
	 */
	public Integer getLeader3() {
		return leader3;
	}
	/**
	 * @param leader3 Ҫ���õ� leader3��
	 */
	public void setLeader3(Integer leader3) {
		this.leader3 = leader3;
	}
	/**
	 * @return ���� leader2��
	 */
	public Integer getLeader2() {
		return leader2;
	}
	/**
	 * @param leader2 Ҫ���õ� leader2��
	 */
	public void setLeader2(Integer leader2) {
		this.leader2 = leader2;
	}
	/**
	 * @return
	 */
	public Integer getCreate_date() {
		return create_date;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
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
	public Integer getLeader() {
		return leader;
	}

	/**
	 * @return
	 */
	public String getLeader_name() {
		return leader_name;
	}

	/**
	 * @return
	 */
	public Integer getTeam_id() {
		return team_id;
	}

	/**
	 * @return
	 */
	public String getTeam_name() {
		return team_name;
	}

	/**
	 * @return
	 */
	public String getTeam_no() {
		return team_no;
	}

	/**
	 * @param integer
	 */
	public void setCreate_date(Integer integer) {
		create_date = integer;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
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
	public void setLeader(Integer integer) {
		leader = integer;
	}

	/**
	 * @param string
	 */
	public void setLeader_name(String string) {
		leader_name = string;
	}

	/**
	 * @param integer
	 */
	public void setTeam_id(Integer integer) {
		team_id = integer;
	}

	/**
	 * @param string
	 */
	public void setTeam_name(String string) {
		team_name = string;
	}

	/**
	 * @param string
	 */
	public void setTeam_no(String string) {
		team_no = string;
	}

	/**
	 * @return
	 */
	public Integer getBegin_date() {
		return begin_date;
	}

	/**
	 * @return
	 */
	public Integer getEnd_date() {
		return end_date;
	}

	/**
	 * @param integer
	 */
	public void setBegin_date(Integer integer) {
		begin_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(Integer integer) {
		end_date = integer;
	}

	/**
	 * @return ���� mark_flag��
	 */
	public Integer getMark_flag() {
		return mark_flag;
	}
	/**
	 * @param mark_flag Ҫ���õ� mark_flag��
	 */
	public void setMark_flag(Integer mark_flag) {
		this.mark_flag = mark_flag;
	}
	/**
	 * @return ���� parent_id��
	 */
	public Integer getParent_id() {
		return parent_id;
	}
	/**
	 * @param parent_id Ҫ���õ� parent_id��
	 */
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
}
