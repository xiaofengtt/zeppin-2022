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
public class TmanagerteammembersVO {
	private Integer serial_no;
	private Integer team_id;
	private String team_no;
	private String team_name;
	private Integer managerid;
	private String managername;
	private String description;
	private Integer input_man;
	
	private Integer leader_query_auth;
	
	private Integer flag;
	private Integer queryAll;
	
	/**
	 * @return ���� leader_query_auth��
	 */
	public Integer getLeader_query_auth() {
		return leader_query_auth;
	}
	/**
	 * @param leader_query_auth Ҫ���õ� leader_query_auth��
	 */
	public void setLeader_query_auth(Integer leader_query_auth) {
		this.leader_query_auth = leader_query_auth;
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
	public Integer getManagerid() {
		return managerid;
	}

	/**
	 * @return
	 */
	public String getManagername() {
		return managername;
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
	public void setManagerid(Integer integer) {
		managerid = integer;
	}

	/**
	 * @param string
	 */
	public void setManagername(String string) {
		managername = string;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
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
	 * @return ���� queryAll��
	 */
	public Integer getQueryAll() {
		return queryAll;
	}
	/**
	 * @param queryAll Ҫ���õ� queryAll��
	 */
	public void setQueryAll(Integer queryAll) {
		this.queryAll = queryAll;
	}
}
