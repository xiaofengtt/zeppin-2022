package cn.product.score.util;


import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import cn.product.score.entity.Admin;



public class PasswordHelper {
	static String algorithmName = "md5";
	static int hashIterations = 2;

	public static void encryptPassword(Admin admin) {
		String newPassword = new SimpleHash(algorithmName, admin.getPassword(),  ByteSource.Util.bytes(admin.getUsername()), hashIterations).toHex();
		admin.setPassword(newPassword);
	}
}
