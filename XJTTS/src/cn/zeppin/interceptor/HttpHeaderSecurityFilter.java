/**
 * 
 */
package cn.zeppin.interceptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.slf4j.LoggerFactory;

/**
 * @author elegantclack
 *
 */
public class HttpHeaderSecurityFilter extends StrutsPrepareAndExecuteFilter {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpHeaderSecurityFilter.class);

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final MutableHttpServletRequest mutableHttpServletRequest = new MutableHttpServletRequest(request);
		mutableHttpServletRequest.putHeader("X-Frame-Options", "SAMEORIGIN");
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		response.setHeader("X-Content-Type-Options", "nosniff");
		final boolean isTrue = sqlInjection(request);
		if (isTrue) {
			final RequestDispatcher dispatcher = request.getRequestDispatcher("/400.jsp");
			dispatcher.forward(request, response);
			return;
		}
//		super.doFilter(new HttpServletRequestWrapper(request), response, chain);
		chain.doFilter(new StringFilterRequest((HttpServletRequest) request), response);
	}

	@SuppressWarnings("rawtypes")
	private Boolean sqlInjection(final HttpServletRequest httpRequest) {
		boolean isIngect = false;
		// 获取上下文的请求参数
		final Map valueTreeMap = httpRequest.getParameterMap();
		// 获得请求参数集合的迭代器
		final Iterator iterator = valueTreeMap.entrySet().iterator();
		// 遍历组装请求参数
		while (iterator.hasNext()) {
			// 获得迭代的键值对
			final Entry entry = (Entry) iterator.next();
			// 获得键值对中的键值
			final String key = (String) entry.getKey();
			if ("title".equals(key)) {
				System.err.println(key);
			}
			// 原请求参数，因为有可能一键对多值所以这里用的String[]
			String[] oldValues = null;
			// 对参数值转换成String类型的
			if (entry.getValue() instanceof String) {
				oldValues = new String[] { entry.getValue().toString() };
			} else {
				oldValues = (String[]) entry.getValue();
			}
			for (int i = 0; i < oldValues.length; i++) {
				if (StringUtils.isNotBlank(oldValues[i])) {
					if (HasInjectionData(oldValues[i])) {
						isIngect = true;
						break;
					}
				}

			}
			if (isIngect) {
				return isIngect;
			}
		}
		return isIngect;
	}

	/// <summary>
	/// 验证是否存在注入代码(条件语句）
	/// </summary>
	/// <param name="inputData"></param>
	public boolean HasInjectionData(final String inputData) {
		// 里面定义恶意字符集合
		// 验证inputData是否包含恶意集合
		if (StringUtils.isBlank(inputData)) {
			return false;
		}

		final Pattern pattern = Pattern.compile(GetRegexString());
		final Matcher matcher = pattern.matcher(inputData.trim().toLowerCase());
		final boolean b = matcher.matches();
		if (b) {
			LOGGER.info(String.format("检测出SQL注入的恶意数据, "+inputData));
			return true;
		} else {
			return false;
		}
	}

	/// <summary>
	/// 获取正则表达式
	/// </summary>
	/// <returns></returns>
	private String GetRegexString() {
		// 构造SQL的注入关键字符
		final String[] strBadChar = { "select\\s", "from\\s", "or\\s", "insert\\s", "delete\\s", "update\\s", "drop\\s", "show\\s", "use\\s", "create\\s",
				"truncate\\s", "exec\\s", "count\\(", "declare\\s", "asc\\(", "mid\\(", "char\\(", "net user",
				"xp_cmdshell", "/add\\s", "exec master.dbo.xp_cmdshell", "net localgroup administrators", "and\\s",
				"=\\s", "where\\s" };

		// 构造正则表达式
		String str_Regex = ".*(";
		for (int i = 0; i < strBadChar.length - 1; i++) {
			str_Regex += strBadChar[i] + "|";
		}
		str_Regex += strBadChar[strBadChar.length - 1] + ").*";

		return str_Regex;
	}

}