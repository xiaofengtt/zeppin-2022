package com.whaty.platform.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;

public class Counter {

	private static void count(String countFilePath) {
		String record;
		long orecord;
		long nrecord;

		try {
			BufferedReader br = new BufferedReader(
					new FileReader(countFilePath));
			record = br.readLine();
			if (record == null)
				record = "0";
			File f = new File(countFilePath);
			orecord = Long.parseLong(record);
			nrecord = orecord + 1;
			PrintWriter pw = new PrintWriter(new FileWriter(f));
			if (nrecord - 1 != orecord)
				System.out.println("counter error: nrecord=" + nrecord
						+ " ,orecord=" + orecord + "!");
			pw.print(Long.toString(nrecord));
			pw.close();
		} catch (Exception e) {
			
		}
	}

	public static String showCount(ServletRequest req, String countFilePath,
			String imgUriPath, int defaultNumLen) {
		String record;
		String htmCode = "";
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		if (session.isNew()) {
			count(countFilePath);
		}
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(countFilePath));
			record = br.readLine();
			if (record == null)
				record = "0";
			int i = 0;
			int k = defaultNumLen - record.length();
			if (k > 0) {
				for (i = 0; i < k; i++) {
					htmCode += "<img src='" + imgUriPath + "/0.gif'>";
				}
				for (i = 0; i < record.length(); i++) {
					htmCode += "<img src='" + imgUriPath + "/"
							+ record.charAt(i) + ".gif'>";
				}
			} else {
				for (i = 0; i < record.length(); i++) {
					htmCode += "<img src='" + imgUriPath + "/"
							+ record.charAt(i) + ".gif'>";
				}
			}
		} catch (Exception e) {
			
		}
		return htmCode;
	}

	public static String showCount(ServletRequest req, String countFilePath,
			int defaultNumLen) {

		String record = "";
		String s = "";
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		if (session.getAttribute("oldSession") == null) {
			count(countFilePath);
			session.setAttribute("oldSession", "OK");
		}
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(countFilePath));
			record = br.readLine();
			if (record == null) {
				for (int i = 0; i < defaultNumLen; i++) {
					record += "0";
				}
			} else if (record.length() < defaultNumLen) {
				for (int i = 0; i < defaultNumLen - record.length(); i++) {
					s += "0";
				}
				record = s + record;
			}
		} catch (Exception e) {
			
		}
		return record;
	}
	
	public static String showDbCount(ServletRequest req, String seqName,
			int defaultNumLen) {

		String record = "";
		String s = "";
		String args ="";
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		
		try {
			
			if(seqName!=null&&("PLATFROM").equals(seqName.trim())){
				args = "s_platform_counter";
			}else if(seqName!=null&&("COLLEGE").equals(seqName.trim())){
				args = "s_college_counter";
			}
			
			
			if (session.getAttribute(seqName) == null) {
				sql = "select "+args+".nextval as counter from dual";
				rs = db.executeQuery(sql);
				if(rs!=null&&rs.next()){
					record = rs.getString("counter");
				}
				session.setAttribute(seqName,record);
			}else{
				record =(String)session.getAttribute(seqName);
			}
		
			if (record == null) {
				for (int i = 0; i < defaultNumLen; i++) {
					record += "0";
				}
			} else if (record.length() < defaultNumLen) {
				for (int i = 0; i < defaultNumLen - record.length(); i++) {
					s += "0";
				}
				record = s + record;
			}
		} catch (Exception e) {
			
		}finally{
			db.close(rs);
		}
		
		return record;
	}

	public static String showDbCount(ServletRequest req, int defaultNumLen) {

		String record = "";
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		dbpool db = new dbpool();
		String s = "";
		MyResultSet rs = null;
		if (session.getAttribute("oldSession") == null) {
			String sql = "select * from counter";
			String countSql = "";

			if (db.countselect(sql) < 1)
				countSql = "insert into counter (num) values ('1')";
			else
				countSql = "update counter set num=num+1";

			db.executeUpdate(countSql);

			session.setAttribute("oldSession", "OK");
		}
		String selSql = "select num from counter";
		try {
			rs = db.executeQuery(selSql);
			if (rs != null && rs.next()) {
				record = rs.getString("num");
			}
		} catch (Exception e) {
			
		}
		db.close(rs);
		if (record == null) {
			for (int i = 0; i < defaultNumLen; i++) {
				record += "0";
			}
		} else if (record.length() < defaultNumLen) {
			for (int i = 0; i < defaultNumLen - record.length(); i++) {
				s += "0";
			}
			record = s + record;
		}
		return record;
	}
}
