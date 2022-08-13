package com.whaty.util;

import java.util.*;
/**
 * @param
 * @version 创建时间：2009-10-29 上午11:15:28
 * @return
 * @throws PlatformException
 * 类说明
 */
public class IP {
		
		public IP() {}
		
//		将127.0.0.1 形式的IP地址转换成10进制整数，这里没有进行任何错误处理
		public static long ipToLong(String strIP)
		{
			int j=0;
			int i=0;
			long [] ip=new long[4];
			int position1=strIP.indexOf(".");
			int position2=strIP.indexOf(".",position1+1);
			int position3=strIP.indexOf(".",position2+1); 
			ip[0]=Long.parseLong(strIP.substring(0,position1));
			ip[1]=Long.parseLong(strIP.substring(position1+1,position2));
			ip[2]=Long.parseLong(strIP.substring(position2+1,position3));
			ip[3]=Long.parseLong(strIP.substring(position3+1));
			return (ip[0]<<24)+(ip[1]<<16)+(ip[2]<<8)+ip[3];
		}
//		将10进制整数形式转换成127.0.0.1形式的IP地址，在命令提示符下输入ping 3396362403L
		public static String longToIP(long longIP)
		{
			StringBuffer sb=new StringBuffer("");
			sb.append(String.valueOf(longIP>>>24));//直接右移24位
			sb.append(".");
			sb.append(String.valueOf((longIP&0x00FFFFFF)>>>16)); //将高8位置0，然后右移16位
			sb.append(".");
			sb.append(String.valueOf((longIP&0x0000FFFF)>>>8));
			sb.append(".");
			sb.append(String.valueOf(longIP&0x000000FF));
			sb.append(".");
			return sb.toString();
		}
}
