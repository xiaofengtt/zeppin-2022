/**
 * 
 */
package com.whaty.platform.util;

/**
 * @author wq
 *
 */
public class SystemInfoUtil {
	public static String getRuntimeName() {
		return System.getProperty("java.runtime.name");
	}
	
	public static String getVmVersion() {
		return System.getProperty("java.vm.version");
	}
	
	public static String getRuntimeVersion() {
		return System.getProperty("java.runtime.version");
	}
	
	public static String getVmVendor() {
		return System.getProperty("java.vm.vendor");
	}
	
	public static String getVmInfo() {
		return System.getProperty("java.vm.info");
	}
	
	public static String getJavaCompiler() {
		return System.getProperty("java.compiler");
	}
	
	public static String getAppEnviromentRoot() {
		return System.getProperty("user.dir");
	}
	
	public static String getOsArch() {
		return System.getProperty("os.arch");
	}
	
	public static String getOsName() {
		return System.getProperty("os.name");
	}
	
	public static String getLibraryPath() {
		return System.getProperty("java.library.path");
	}
	
	public static String getClassPth() {
		return System.getProperty("java.class.path");
	}
	
	public static String getTimeZone() {
		return System.getProperty("user.timezone");
	}
	
	public static String getFileEncoding() {
		return System.getProperty("file.encoding");
	}
	
	public static String getLang() {
		return System.getProperty("user.language");
	}
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.compiler"));
	}
}
