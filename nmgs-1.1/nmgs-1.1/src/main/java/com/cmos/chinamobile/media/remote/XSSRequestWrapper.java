package com.cmos.chinamobile.media.remote;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
public class XSSRequestWrapper extends HttpServletRequestWrapper {
	private static final Logger logger = LoggerFactory.getActionLog(XSSRequestWrapper.class);
    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }
    
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }
        return encodedValues;
    }
    

	public String getRequestURI() {
		String uri = super.getRequestURI();
		try {
			uri = URLDecoder.decode(uri,"UTF-8");
		} catch (Exception e) {
			logger.info("����", e);
		}
		return  stripXSS(uri);
	}

    
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return stripXSS(value);
    }
    
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    }

    private String stripXSS(String value) {
    	String values = null;
        if (value != null) {
        	values = StringEscapeUtils.escapeHtml4(value); 
        }
        return values;
    }
}