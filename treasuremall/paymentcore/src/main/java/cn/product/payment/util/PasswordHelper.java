package cn.product.payment.util;


import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import cn.product.payment.entity.Admin;
import cn.product.payment.entity.CompanyAdmin;

public class PasswordHelper {
	static String algorithmName = "md5";
	static int hashIterations = 2;

	public static void encryptPassword(Admin admin) {
		String newPassword = new SimpleHash(algorithmName, admin.getPassword(),  ByteSource.Util.bytes(admin.getUsername()), hashIterations).toHex();
		admin.setPassword(newPassword);
	}
	
	public static String getEncryptPassword(Admin admin, String password){
		return new SimpleHash(algorithmName, password,  ByteSource.Util.bytes(admin.getUsername()), hashIterations).toHex();
	}
	
	public static void encryptPassword(CompanyAdmin companyAdmin) {
		String newPassword = new SimpleHash(algorithmName, companyAdmin.getPassword(),  ByteSource.Util.bytes(companyAdmin.getMobile()), hashIterations).toHex();
		companyAdmin.setPassword(newPassword);
	}
	
	public static String getEncryptPassword(CompanyAdmin companyAdmin, String password){
		return new SimpleHash(algorithmName, password,  ByteSource.Util.bytes(companyAdmin.getMobile()), hashIterations).toHex();
	}
}
