package com.enfo.intrust.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import enfo.crm.dao.CrmDBManager;

public class BlackListMatchingSource {
	
	public static String getname(String pkey) throws Exception {
	    String name = "";
	   	Connection connection = null;
	    
	    try{
	    	connection = CrmDBManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("select * from heimingdan where pkey=?");
			preparedStatement.setString(1, pkey);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				name = resultSet.getString("name");
		    }
			return name;
	    }finally{
	    	
	    	if(connection != null)
	    		CrmDBManager.closeConnection();
	    }
	}
  
	public static String getisoCode(String threeLetters) throws Exception {
		String isoCode = "";
		Connection connection = null;
	    
	    try{
	    	connection = CrmDBManager.getConnection();
		
			PreparedStatement preparedStatement = connection.prepareStatement("select * from iso  where threeLetters=?");
			preparedStatement.setString(1, threeLetters);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
			  isoCode = resultSet.getString("isoCode");
			}
	    }finally{
	    	if(connection != null)
	    		CrmDBManager.closeConnection();
	    }
		return isoCode;
	}
  
	public static String getisoCodeForChineseIdioms(String chineseIdioms) throws Exception {
		String isoCode = "";
		Connection connection = null;
	    
	    try{
	    	connection = CrmDBManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("select * from iso  where chineseIdioms like ?");
			preparedStatement.setString(1, "%" + chineseIdioms + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				isoCode = resultSet.getString("isoCode");
			}
	    }finally{
	    	if(connection != null)
	    		CrmDBManager.closeConnection();
	    }
	    return isoCode;
	}
  
	public static String getChineseIdioms(String iso) throws Exception {
		String chinese = "";
		
		Connection connection = null;
	    try{
	    	connection = CrmDBManager.getConnection();
	    	PreparedStatement preparedStatement = connection.prepareStatement("select * from iso  where isoCode = ?");
			preparedStatement.setString(1, iso);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				chinese = resultSet.getString("chineseIdioms");
			}
	    }finally{
	    	if(connection != null)
	    		CrmDBManager.closeConnection();
	    }
	    return chinese;
	}
  
	public static String removeallEnglish(String chineseIdioms) {
	    chineseIdioms = chineseIdioms.replaceAll("A", "");
	    chineseIdioms = chineseIdioms.replaceAll("B", "");
	    chineseIdioms = chineseIdioms.replaceAll("C", "");
	    chineseIdioms = chineseIdioms.replaceAll("D", "");
	    chineseIdioms = chineseIdioms.replaceAll("E", "");
	    chineseIdioms = chineseIdioms.replaceAll("F", "");
	    chineseIdioms = chineseIdioms.replaceAll("G", "");
	    chineseIdioms = chineseIdioms.replaceAll("H", "");
	    chineseIdioms = chineseIdioms.replaceAll("I", "");
	    chineseIdioms = chineseIdioms.replaceAll("J", "");
	    chineseIdioms = chineseIdioms.replaceAll("K", "");
	    chineseIdioms = chineseIdioms.replaceAll("L", "");
	    chineseIdioms = chineseIdioms.replaceAll("M", "");
	    chineseIdioms = chineseIdioms.replaceAll("N", "");
	    chineseIdioms = chineseIdioms.replaceAll("O", "");
	    chineseIdioms = chineseIdioms.replaceAll("P", "");
	    chineseIdioms = chineseIdioms.replaceAll("Q", "");
	    chineseIdioms = chineseIdioms.replaceAll("R", "");
	    chineseIdioms = chineseIdioms.replaceAll("S", "");
	    chineseIdioms = chineseIdioms.replaceAll("T", "");
	    chineseIdioms = chineseIdioms.replaceAll("U", "");
	    chineseIdioms = chineseIdioms.replaceAll("V", "");
	    chineseIdioms = chineseIdioms.replaceAll("W", "");
	    chineseIdioms = chineseIdioms.replaceAll("X", "");
	    chineseIdioms = chineseIdioms.replaceAll("Y", "");
	    chineseIdioms = chineseIdioms.replaceAll("Z", "");
	    chineseIdioms = chineseIdioms.replaceAll("a", "");
	    chineseIdioms = chineseIdioms.replaceAll("b", "");
	    chineseIdioms = chineseIdioms.replaceAll("c", "");
	    chineseIdioms = chineseIdioms.replaceAll("d", "");
	    chineseIdioms = chineseIdioms.replaceAll("e", "");
	    chineseIdioms = chineseIdioms.replaceAll("f", "");
	    chineseIdioms = chineseIdioms.replaceAll("g", "");
	    chineseIdioms = chineseIdioms.replaceAll("h", "");
	    chineseIdioms = chineseIdioms.replaceAll("i", "");
	    chineseIdioms = chineseIdioms.replaceAll("j", "");
	    chineseIdioms = chineseIdioms.replaceAll("k", "");
	    chineseIdioms = chineseIdioms.replaceAll("l", "");
	    chineseIdioms = chineseIdioms.replaceAll("m", "");
	    chineseIdioms = chineseIdioms.replaceAll("n", "");
	    chineseIdioms = chineseIdioms.replaceAll("o", "");
	    chineseIdioms = chineseIdioms.replaceAll("p", "");
	    chineseIdioms = chineseIdioms.replaceAll("q", "");
	    chineseIdioms = chineseIdioms.replaceAll("r", "");
	    chineseIdioms = chineseIdioms.replaceAll("s", "");
	    chineseIdioms = chineseIdioms.replaceAll("t", "");
	    chineseIdioms = chineseIdioms.replaceAll("u", "");
	    chineseIdioms = chineseIdioms.replaceAll("v", "");
	    chineseIdioms = chineseIdioms.replaceAll("w", "");
	    chineseIdioms = chineseIdioms.replaceAll("x", "");
	    chineseIdioms = chineseIdioms.replaceAll("y", "");
	    chineseIdioms = chineseIdioms.replaceAll("z", "");
	    chineseIdioms = chineseIdioms.replaceAll("(", "");
	    chineseIdioms = chineseIdioms.replaceAll(")", "");
	    return chineseIdioms;
	}
  
	@SuppressWarnings("unused")
	public static void blackListScreen(Integer inputman, String customerName) throws Exception {
		Date curDate = new Date();
		Connection connection = null;
	    try{
	    	connection = CrmDBManager.getConnection();
			String sql = "insert into TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,TRADE_TIME,SUMMARY) values(20169,'黑名单筛查'," + inputman + ",?,'黑名单筛查，员工编号:" + inputman + "筛查了" + customerName + "客户')";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setTimestamp(1, new Timestamp(Calendar.getInstance().getTime().getTime()));
			preparedStatement.execute();
	    }finally{
	    	if(connection != null)
	    		CrmDBManager.closeConnection();
	    }
	}
  
	public static void main(String[] args) throws Exception {
		blackListScreen(Integer.valueOf("666"), "小姚客户");
	}
}
