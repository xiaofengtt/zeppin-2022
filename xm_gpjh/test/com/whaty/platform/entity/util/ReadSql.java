package com.whaty.platform.entity.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 读取sql生成唯一性约束文件。
 * @author 李冰
 *
 */
public class ReadSql {
	private String intoSql = "e:\\hnsd.sql";
	public static void main(String[] args) {
		ReadSql read = new ReadSql();
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					read.intoSql));

			String name = "";
			String column = "";
			String table = "";
			String colInfo = "";
			List<String> columnInfo = new ArrayList<String>(); //保存有列的备注信息。
			List<String> columnList = new ArrayList<String>(); //临时保存一个表中的列值
			Set<String> columnSet = new HashSet<String>(); //保存所有不重复的列值
			String str;
			int i = 0;
			while ((str = in.readLine()) != null) {
				//取得表名
				if(str.indexOf("create table ")>=0){
					columnList = new ArrayList<String>();
					i=1;
					str = str.substring(13);
					table = str.substring(0,str.indexOf(" "));
					continue;
				}
				if(str.equals(");")){
					i=2;
					continue;
				}
				//解析唯一性约束字段
				if(str.indexOf("constraint")>0&&str.indexOf("unique")>0){
					str = str.substring(str.indexOf("constraint"));
					str = str.substring(11);
					//取得约束名称
					name = "   <alternateKey name=\"HNSD." + str.substring(0, str.indexOf(" "))+ "\">";
					read.writeXml(name);
					System.out.println(name);
					//所在的表
					String table2 = "        <table>"+table+"</table>";
					read.writeXml(table2);
					System.out.println(table2);
					str = str.substring(str.indexOf(" ")).trim();
					str = str.substring(8, str.length()-1).replace(")", "");
					String[] s = str.split(" ");
					read.writeXml("        <columns>");
					//约束相关的列
					for (String string : s) {
						column = string.replace(",", "");
						columnList.add(column);
						columnSet.add(column);
						column = "        <column>" + column + "</column>";
						System.out.println(column);
						read.writeXml(column);
					}
					read.writeXml("        </columns>");
					String end = "   </alternateKey>";
					System.out.println(end);
					read.writeXml(end);
					continue;
				}
				if(i==2){
					for (String string2 : columnList) {
						//唯一性约束列是否有备注
						if(str.indexOf(" "+table+"."+string2+" ")>=0){
							i=3;
							colInfo=string2;
							break;
						}
					}
					continue;
				}
				//取得列所对应的备注
				if(i==3){
					String info = str.substring(1, str.length()-2);
					System.out.println(colInfo+"="+info);
					columnInfo.add(colInfo+"="+info);
					i=2;
				}
				
			}
			in.close();
			for (String string3 : columnSet) {
				read.writeColumns(string3);
			}
			for (String string4 : columnInfo) {
				read.writeInfo(string4);
			}
		} catch (IOException e) {
			System.out.println("读取信息失败");
		}
	}
	
	
	public void writeXml(String content){
		this.WriteLine("e:", "unique.txt", content); //约束关系文件
	}
	
	public void writeColumns(String content){
		this.WriteLine("e:", "column.txt", content);  //约束关系包含的列
	}
	
	public void writeInfo(String content){
		this.WriteLine("e:", "info.txt", content); //列和注释
	}
	/**
	 * 写一行内容最后换行不覆盖原文件
	 * 
	 * @param lujing文件路径
	 * @param name文件名
	 * @param content文件内容
	 */
	public void WriteLine(String lujing, String name, String content) {
		String fileName = lujing + "\\" + name;
		File file = new File(lujing);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName,
					true));// 不覆盖原文件内容
			String write = content + "\r\n";// 最后换行
			out.write(write);
			out.close();
		} catch (IOException e) {
			System.out.println("写文件 " + fileName + " 失败!");
		}
	}
}
