/**
 * 
 */
package cn.product.payment.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 */
public class IllegalCharacterFilter extends OncePerRequestFilter {

	/**
	 * 
	 */
	public IllegalCharacterFilter() {
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		filterChain.doFilter(new StringFilterRequest((HttpServletRequest) request), response);
	}
}


class StringFilterRequest extends HttpServletRequestWrapper {

	public StringFilterRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		// 返回值之前 先进行过滤
		return filterDangerString(super.getParameter(name));
	}

	@Override
	public String[] getParameterValues(String name) {
		// 返回值之前 先进行过滤
		String[] values = super.getParameterValues(name);
		if(values == null){
			return null;
		}
		for (int i = 0; i < values.length; i++) {
			values[i] = filterDangerString(values[i]);
		}

		return values;
	}

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> keys = new HashMap<String, String[]>(super.getParameterMap());
        Set<Map.Entry<String, String[]>> set = keys.entrySet();
        Iterator<Map.Entry<String, String[]>> iters = set.iterator();
        while (iters.hasNext()) {
        	Map.Entry<String, String[]> entry = iters.next();
        	String[] values = filterDangerString(entry.getValue());
            keys.put(entry.getKey(), values);
        }
        return keys;
    }

    public String filterDangerString(String value) {
        if (value == null) {
            return null;
        }
        
        // Avoid null characters
        value = value.replaceAll("", "");
        
        // Avoid anything between script tags
        Pattern scriptPattern = Pattern.compile("(.*?)", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        
        // Remove any lonesome  tag
        scriptPattern = Pattern.compile("", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        scriptPattern = Pattern.compile("", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        
        // Avoid eval(...) e­xpressions
        scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        
        // Avoid e­xpression(...) e­xpressions
        scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        
        // Avoid javascript:... e­xpressions
        scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        
        // Avoid vbscript:... e­xpressions
        scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        
        // Avoid onload= e­xpressions
        scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        
        value = value.replaceAll("\\{", "{");
//        value = value.replaceAll("&", "&amp;");

//        value = value.replaceAll("<", "&lt;");
//        value = value.replaceAll(">", "&gt;");
//        value = value.replaceAll("\t", "    ");
//        value = value.replaceAll("\r\n", "\n");
//        value = value.replaceAll("\n", "<br/>");
//        value = value.replaceAll("'", "&#39;");
//        value = value.replaceAll("\\(", "&#40;");
//        value = value.replaceAll("\\)", "&#41;");
//        value = value.replaceAll("\\\\", "&#92;");
//        value = value.replaceAll("\"", "&quot;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = value.replaceAll("\\}", "}").trim();
        return value;
    }
   
    public String[] filterDangerString(String[] values) {
        if (values == null) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
        	values[i] = filterDangerString(values[i]);
        }

        return values;
    }
	  
}