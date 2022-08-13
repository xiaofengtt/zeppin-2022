package cn.zeppin.utility;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.fasterxml.jackson.core.JsonGenerator;  
import com.fasterxml.jackson.core.JsonParser;   
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;  
import com.fasterxml.jackson.databind.ObjectMapper;   
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JSONUtils {
    private static SerializeConfig mapping = new SerializeConfig();  
    
    static{  
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));  
    }  
      
    public static String obj2json(Object obj){  
        return JSON.toJSONString(obj, mapping);  
    }  
 
    public static <T> T json2obj(String jsonStr,Class<T> clazz){  
        return JSON.parseObject(jsonStr, clazz);  
    }  
      
    public static <T> List<T> json2list(String jsonArrayStr,Class<T> clazz){  
        return JSON.parseArray(jsonArrayStr, clazz);  
    }  
      
    @SuppressWarnings("unchecked")
	public static <T> Map<String,Object> json2map(String jsonStr){  
        return json2obj(jsonStr, Map.class);  
    }  
      
    public static <T> Map<String,T> json2map(String jsonStr,Class<T> clazz){  
        Map<String,T> map = JSON.parseObject(jsonStr, new TypeReference<Map<String, T>>() {});  
        for (Entry<String, T> entry : map.entrySet()) {  
            JSONObject obj = (JSONObject) entry.getValue();  
            map.put(entry.getKey(), JSONObject.toJavaObject(obj, clazz));  
        }  
        return map;  
    }  	
    
    public static String JSON2XML(String json) throws JsonProcessingException, IOException{  
    	  ObjectMapper objectMapper = new ObjectMapper();
    	  XmlMapper xmlMapper = new XmlMapper();
    	  JsonNode root = objectMapper.readTree(json);  
          String xml = xmlMapper.writeValueAsString(root);  
          return xml;  
    }
    
    public static String XML2JSON(String xml) throws IOException{  
	  	  ObjectMapper objectMapper = new ObjectMapper();
	  	  XmlMapper xmlMapper = new XmlMapper();
    	  StringWriter w = new StringWriter();  
          JsonParser jp = xmlMapper.getFactory().createParser(xml);  
          JsonGenerator jg = objectMapper.getFactory().createGenerator(w);  
          while (jp.nextToken() != null) {  
              jg.copyCurrentEvent(jp);  
          }  
          jp.close();  
          jg.close();  
          return w.toString();  
    }  
}
