/*
 * 创建日期 2009-11-26
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author wanghf
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
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
	 * @return 返回 leader3。
	 */
	public Integer getLeader3() {
		return leader3;
	}
	/**
	 * @param leader3 要设置的 leader3。
	 */
	public void setLeader3(Integer leader3) {
		this.leader3 = leader3;
	}
	/**
	 * @return 返回 leader2。
	 */
	public Integer getLeader2() {
		return leader2;
	}
	/**
	 * @param leader2 要设置的 leader2。
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
	 * @return 返回 mark_flag。
	 */
	public Integer getMark_flag() {
		return mark_flag;
	}
	/**
	 * @param mark_flag 要设置的 mark_flag。
	 */
	public void setMark_flag(Integer mark_flag) {
		this.mark_flag = mark_flag;
	}
	/**
	 * @return 返回 parent_id。
	 */
	public Integer getParent_id() {
		return parent_id;
	}
	/**
	 * @param parent_id 要设置的 parent_id。
	 */
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
}
