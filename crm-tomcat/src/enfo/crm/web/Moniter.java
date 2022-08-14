package enfo.crm.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>Title: Servlet Moniter</p>
 * <p>Description: Moniter for setting GB2312 charset of jsp</p>
 * <p>Copyright: Copyright (c) Singlee Software 2003</p>
 * <p>Company: Singlee Software</p>
 * @author <a href="mailto:zhou_bin@singlee.com">Zhou bin</a>
 * @version 1.0
 */

public class Moniter implements Filter
{
	/**
	* @see javax.servlet.Filter#void ()
	*/
	public void destroy()
	{

	}

	/**
	* @see javax.servlet.Filter#void (javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	*/
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws ServletException, IOException
	{
		req.setCharacterEncoding("GBK");
		resp.setContentType ("text/html;charset=GBK");
		chain.doFilter(req, resp);
		
	}

	/**
	* Method init.
	* @param config
	* @throws javax.servlet.ServletException
	*/
	public void init(FilterConfig config) throws ServletException
	{
		
	}

}
