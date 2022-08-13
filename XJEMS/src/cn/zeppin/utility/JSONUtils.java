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

/** 
 * ClassName: JsonUtils <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 */
public class JSONUtils {
    private static SerializeConfig mapping = new SerializeConfig();  
    
    static{  
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));  
    }  
      
    /** 
     * javaBean、list、map convert to json string 
     */  
    public static String obj2json(Object obj){  
//      return JSON.toJSONString(obj,SerializerFeature.UseSingleQuotes);//使用单引号  
//      return JSON.toJSONString(obj,true);//格式化数据，方便阅读  
        return JSON.toJSONString(obj, mapping);  
    }  
      
    /** 
     * json string convert to javaBean、map 
     */  
    public static <T> T json2obj(String jsonStr,Class<T> clazz){  
        return JSON.parseObject(jsonStr, clazz);  
    }  
      
    /** 
     * json array string convert to list with javaBean 
     */  
    public static <T> List<T> json2list(String jsonArrayStr,Class<T> clazz){  
        return JSON.parseArray(jsonArrayStr, clazz);  
    }  
      
    /** 
     * json string convert to map 
     */  
    @SuppressWarnings("unchecked")
	public static <T> Map<String,Object> json2map(String jsonStr){  
        return json2obj(jsonStr, Map.class);  
    }  
      
    /** 
     * json string convert to map with javaBean 
     */  
    public static <T> Map<String,T> json2map(String jsonStr,Class<T> clazz){  
        Map<String,T> map = JSON.parseObject(jsonStr, new TypeReference<Map<String, T>>() {});  
        for (Entry<String, T> entry : map.entrySet()) {  
            JSONObject obj = (JSONObject) entry.getValue();  
            map.put(entry.getKey(), JSONObject.toJavaObject(obj, clazz));  
        }  
        return map;  
    }  	
    
    /** 
     * json string convert to xml string 
     * @throws IOException 
     * @throws JsonProcessingException 
     */  
    public static String JSON2XML(String json) throws JsonProcessingException, IOException{  
    	  ObjectMapper objectMapper = new ObjectMapper();
    	  XmlMapper xmlMapper = new XmlMapper();
    	  JsonNode root = objectMapper.readTree(json);  
          String xml = xmlMapper.writeValueAsString(root);  
          return xml;  
    }  
      
    /** 
     * xml string convert to json string 
     * @throws IOException 
     */  
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
