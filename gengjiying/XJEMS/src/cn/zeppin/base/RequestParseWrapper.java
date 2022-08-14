/**
 * 
 */
package cn.zeppin.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;

/**
 * @author sj
 * 
 */
public class RequestParseWrapper extends JakartaMultiPartRequest {

	public void parse(HttpServletRequest servletRequest, String saveDir) throws IOException {

	}
}
