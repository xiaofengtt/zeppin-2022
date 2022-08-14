package enfo.crm.tools;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import enfo.crm.dao.CrmDBManager;

public class PJArgument {

	public static StringBuffer newStringBuffer() {
		StringBuffer sb = new StringBuffer(200);
		sb.append("<option value=\"\">请选择..</option>");
		return sb;
	}

	public static void appendOptions(StringBuffer sb, int value, String text,
			int defvalue) {
		sb.append("<option");
		sb.append((defvalue == value) ? " selected" : "");
		sb.append(" value=\"" + value + "\">");
		sb.append(text);
		sb.append("</option>");
	}

	public static void appendOptions(StringBuffer sb, Integer value,
			String text, Integer defvalue) {
		sb.append("<option ");

		if ((defvalue != null) && (defvalue.intValue() == value.intValue())) {
			sb.append(" selected");
		}
		sb.append(" value=\"" + value + "\">");
		sb.append(text);
		sb.append("</option>");
	}

	public static void appendOptions(StringBuffer sb, int value, String text,
			Integer defvalue) {
		sb.append("<option");
		if ((defvalue != null) && (defvalue.intValue() == value))
			sb.append(" selected");
		sb.append(" value=\"" + value + "\">");
		sb.append(text);
		sb.append("</option>");
	}

	public static void appendOptions(StringBuffer sb, String value,
			String text, String defvalue) {
		sb.append("<option");
		if (defvalue != null)
			sb
					.append((value.trim().compareTo(defvalue.trim()) == 0) ? " selected"
							: "");
		sb.append(" value=\"" + value + "\">");
		sb.append(text);
		sb.append("</option>");
	}

	public static void appendOptions(StringBuffer sb, int value, String text,
			int contrastValue, int defvalue) {
		sb.append("<option");
		sb.append((defvalue == contrastValue) ? " selected" : "");
		sb.append(" value=\"" + value + "\">");
		sb.append(text);
		sb.append("</option>");
	}

	/**
	 * 获取审批类别
	 * 
	 * @param problems
	 * @param intrust_flag
	 * @param book_code
	 * @return
	 * @throws Exception
	 */
	public static String getProjects(Integer problems, Integer intrust_flag,
			Integer book_code) throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call  SP_QUERY_TPROJECTS_BYFLAG(?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, intrust_flag.intValue());
		stmt.setInt(2, problems == null ? 0 : problems.intValue());
		stmt.setInt(3, book_code.intValue());
		ResultSet rs = stmt.executeQuery();
		appendOptions(sb, 0, "---选择项目---", problems);
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("PROJECTID"), rs.getString("NAME"),
						rs.getInt("PROBLEMS_TYPE"), problems.intValue());
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}

	/**
	 * 获取审批类别
	 * 
	 * @param problems
	 * @param intrust_flag
	 * @param book_code
	 * @param project_id
	 * @return
	 * @throws Exception
	 */
	public static String getProjects(Integer problems, Integer intrust_flag,
			Integer book_code, Integer project_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call  SP_QUERY_TPROJECTS_BYFLAG(?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, intrust_flag.intValue());
		stmt.setInt(2, problems == null ? 0 : problems.intValue());
		stmt.setInt(3, book_code.intValue());
		ResultSet rs = stmt.executeQuery();
		appendOptions(sb, 0, "---选择项目---", project_id);
		try {
			while (rs.next())
				appendOptions(sb, rs.getInt("PROJECTID"), rs.getString("NAME"),
						project_id);
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
}