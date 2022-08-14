/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITGgGdxxbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITGgJgxxbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITGgYgxxbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITGyGydbgxbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITGyGydbhtbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITGyGydzywbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITGyGyzhxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITGyGyzjyyjylDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITGyGyzzyyhtxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITJyQjglxxfzqDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITJyQjglxxzqDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITJyXtsypzDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITJyXtsyqzrxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITJyXtzjmjjfplDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITJyXtzjyyjylDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKhDsfhzjgxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKhJydsgrDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKhJydsjgDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKhJydsjggdxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKhTzgwhtbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKhXtkhgrDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKhXtkhjgDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKjGynbkmdzbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKjGyzcfzkmtjbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKjGyzzkjqkmbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKjXtnbkmdzbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKjXtxmzcfztjbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITKjXtxmzzkjqkmbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmFdcjsxmxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmFfdcjsxmxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtdbgxbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtdbhtbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtdzywbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtxmqsxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtxmsyqbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtxmxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtxmyjhklypgbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtzhxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtzjmjhtxxDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtzjyyhtxxDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSubmitService;
import cn.zeppin.product.itic.core.entity.TGgGdxxb;
import cn.zeppin.product.itic.core.entity.TGgJgxxb;
import cn.zeppin.product.itic.core.entity.TGgYgxxb;
import cn.zeppin.product.itic.core.entity.TGyGydbgxb;
import cn.zeppin.product.itic.core.entity.TGyGydbhtb;
import cn.zeppin.product.itic.core.entity.TGyGydzywb;
import cn.zeppin.product.itic.core.entity.TGyGyzhxx;
import cn.zeppin.product.itic.core.entity.TGyGyzjyyjyl;
import cn.zeppin.product.itic.core.entity.TGyGyzzyyhtxx;
import cn.zeppin.product.itic.core.entity.TJyQjglxxfzq;
import cn.zeppin.product.itic.core.entity.TJyQjglxxzq;
import cn.zeppin.product.itic.core.entity.TJyXtsypz;
import cn.zeppin.product.itic.core.entity.TJyXtsyqzrxx;
import cn.zeppin.product.itic.core.entity.TJyXtzjmjjfpl;
import cn.zeppin.product.itic.core.entity.TJyXtzjyyjyl;
import cn.zeppin.product.itic.core.entity.TKhDsfhzjgxx;
import cn.zeppin.product.itic.core.entity.TKhJydsgr;
import cn.zeppin.product.itic.core.entity.TKhJydsjg;
import cn.zeppin.product.itic.core.entity.TKhJydsjggdxx;
import cn.zeppin.product.itic.core.entity.TKhTzgwhtb;
import cn.zeppin.product.itic.core.entity.TKhXtkhgr;
import cn.zeppin.product.itic.core.entity.TKhXtkhjg;
import cn.zeppin.product.itic.core.entity.TKjGynbkmdzb;
import cn.zeppin.product.itic.core.entity.TKjGyzcfzkmtjb;
import cn.zeppin.product.itic.core.entity.TKjGyzzkjqkmb;
import cn.zeppin.product.itic.core.entity.TKjXtnbkmdzb;
import cn.zeppin.product.itic.core.entity.TKjXtxmzcfztjb;
import cn.zeppin.product.itic.core.entity.TKjXtxmzzkjqkmb;
import cn.zeppin.product.itic.core.entity.TXmFdcjsxmxx;
import cn.zeppin.product.itic.core.entity.TXmFfdcjsxmxx;
import cn.zeppin.product.itic.core.entity.TXmXtdbgxb;
import cn.zeppin.product.itic.core.entity.TXmXtdbhtb;
import cn.zeppin.product.itic.core.entity.TXmXtdzywb;
import cn.zeppin.product.itic.core.entity.TXmXtxmqsxx;
import cn.zeppin.product.itic.core.entity.TXmXtxmsyqb;
import cn.zeppin.product.itic.core.entity.TXmXtxmxx;
import cn.zeppin.product.itic.core.entity.TXmXtxmyjhklypgb;
import cn.zeppin.product.itic.core.entity.TXmXtzhxx;
import cn.zeppin.product.itic.core.entity.TXmXtzjmjhtxx;
import cn.zeppin.product.itic.core.entity.TXmXtzjyyhtxx;
import cn.zeppin.product.itic.core.service.base.BaseService;
import cn.zeppin.product.utility.CheckBigDecimalValues;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.TableColumn;

@Service
public class TSubmitService extends BaseService implements ITSubmitService {
	
	@Autowired
	private ITGgGdxxbDAO TGgGdxxbDAO;
	
	@Autowired
	private ITGgJgxxbDAO TGgJgxxbDAO;
	
	@Autowired
	private ITGgYgxxbDAO TGgYgxxbDAO;
	
	@Autowired
	private ITGyGydbhtbDAO TGyGydbhtbDAO;
	
	@Autowired
	private ITGyGydbgxbDAO TGyGydbgxbDAO;
	
	@Autowired
	private ITGyGydzywbDAO TGyGydzywbDAO;
	
	@Autowired
	private ITGyGyzhxxDAO TGyGyzhxxDAO;
	
	@Autowired
	private ITGyGyzjyyjylDAO TGyGyzjyyjylDAO;
	
	@Autowired
	private ITGyGyzzyyhtxxDAO TGyGyzzyyhtxxDAO;
	
	@Autowired
	private ITJyQjglxxfzqDAO TJyQjglxxfzqDAO;
	
	@Autowired
	private ITJyQjglxxzqDAO TJyQjglxxzqDAO;
	
	@Autowired
	private ITJyXtsypzDAO TJyXtsypzDAO;
	
	@Autowired
	private ITJyXtsyqzrxxDAO TJyXtsyqzrxxDAO;
	
	@Autowired
	private ITJyXtzjmjjfplDAO TJyXtzjmjjfplDAO;
	
	@Autowired
	private ITJyXtzjyyjylDAO TJyXtzjyyjylDAO;
	
	@Autowired
	private ITKhDsfhzjgxxDAO TKhDsfhzjgxxDAO;
	
	@Autowired
	private ITKhJydsgrDAO TKhJydsgrDAO;
	
	@Autowired
	private ITKhJydsjgDAO TKhJydsjgDAO;
	
	@Autowired
	private ITKhJydsjggdxxDAO TKhJydsjggdxxDAO;
	
	@Autowired
	private ITKhTzgwhtbDAO TKhTzgwhtbDAO;
	
	@Autowired
	private ITKhXtkhgrDAO TKhXtkhgrDAO;
	
	@Autowired
	private ITKhXtkhjgDAO TKhXtkhjgDAO;
	
	@Autowired
	private ITKjGynbkmdzbDAO TKjGynbkmdzbDAO;
	
	@Autowired
	private ITKjGyzcfzkmtjbDAO TKjGyzcfzkmtjbDAO;
	
	@Autowired
	private ITKjGyzzkjqkmbDAO TKjGyzzkjqkmbDAO;
	
	@Autowired
	private ITKjXtnbkmdzbDAO TKjXtnbkmdzbDAO;
	
	@Autowired
	private ITKjXtxmzcfztjbDAO TKjXtxmzcfztjbDAO;
	
	@Autowired
	private ITKjXtxmzzkjqkmbDAO TKjXtxmzzkjqkmbDAO;
	
	@Autowired
	private ITXmFdcjsxmxxDAO TXmFdcjsxmxxDAO;
	
	@Autowired
	private ITXmFfdcjsxmxxDAO TXmFfdcjsxmxxDAO;
	
	@Autowired
	private ITXmXtdbgxbDAO TXmXtdbgxbDAO;
	
	@Autowired
	private ITXmXtdbhtbDAO TXmXtdbhtbDAO;
	
	@Autowired
	private ITXmXtdzywbDAO TXmXtdzywbDAO;
	
	@Autowired
	private ITXmXtxmqsxxDAO TXmXtxmqsxxDAO;
	
	@Autowired
	private ITXmXtxmsyqbDAO TXmXtxmsyqbDAO;
	
	@Autowired
	private ITXmXtxmxxDAO TXmXtxmxxDAO;
	
	@Autowired
	private ITXmXtxmyjhklypgbDAO TXmXtxmyjhklypgbDAO;
	
	@Autowired
	private ITXmXtzhxxDAO TXmXtzhxxDAO;
	
	@Autowired
	private ITXmXtzjmjhtxxDAO TXmXtzjmjhtxxDAO;
	
	@Autowired
	private ITXmXtzjyyhtxxDAO TXmXtzjyyhtxxDAO;
	
	@Override
	public Map<String, String> submitDatas(String[] types, String starttime, String endtime, String time) {
		Map<String, String> resultMap = new HashMap<String, String>();
		Integer count = 0;
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("starttime", starttime + " 00:00:00");
		inputParams.put("endtime", endtime + " 23:59:59");
		try{
			Map<String, List<Object>> dataMap = new HashMap<String, List<Object>>();
			for(String type : types){
				Integer dataCount = 0;
				if("TGgGdxxb".equals(type)){
					List<TGgGdxxb> datas = this.TGgGdxxbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TGgGdxxb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TGgGdxxbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TGgJgxxb".equals(type)){
					List<TGgJgxxb> datas = this.TGgJgxxbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TGgJgxxb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TGgJgxxbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TGgYgxxb".equals(type)){
					List<TGgYgxxb> datas = this.TGgYgxxbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TGgYgxxb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TGgYgxxbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TGyGydbgxb".equals(type)){
					List<TGyGydbgxb> datas = this.TGyGydbgxbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TGyGydbgxb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TGyGydbgxbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TGyGydbhtb".equals(type)){
					List<TGyGydbhtb> datas = this.TGyGydbhtbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TGyGydbhtb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TGyGydbhtbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TGyGydzywb".equals(type)){
					List<TGyGydzywb> datas = this.TGyGydzywbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TGyGydzywb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TGyGydzywbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TGyGyzhxx".equals(type)){
					List<TGyGyzhxx> datas = this.TGyGyzhxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TGyGyzhxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TGyGyzhxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TGyGyzjyyjyl".equals(type)){
					List<TGyGyzjyyjyl> datas = this.TGyGyzjyyjylDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TGyGyzjyyjyl data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TGyGyzjyyjylDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TGyGyzzyyhtxx".equals(type)){
					List<TGyGyzzyyhtxx> datas = this.TGyGyzzyyhtxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TGyGyzzyyhtxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TGyGyzzyyhtxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TJyQjglxxfzq".equals(type)){
					List<TJyQjglxxfzq> datas = this.TJyQjglxxfzqDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TJyQjglxxfzq data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TJyQjglxxfzqDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TJyQjglxxzq".equals(type)){
					List<TJyQjglxxzq> datas = this.TJyQjglxxzqDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TJyQjglxxzq data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TJyQjglxxzqDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TJyXtsypz".equals(type)){
					List<TJyXtsypz> datas = this.TJyXtsypzDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TJyXtsypz data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TJyXtsypzDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TJyXtsyqzrxx".equals(type)){
					List<TJyXtsyqzrxx> datas = this.TJyXtsyqzrxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TJyXtsyqzrxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TJyXtsyqzrxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TJyXtzjmjjfpl".equals(type)){
					List<TJyXtzjmjjfpl> datas = this.TJyXtzjmjjfplDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TJyXtzjmjjfpl data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TJyXtzjmjjfplDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TJyXtzjyyjyl".equals(type)){
					List<TJyXtzjyyjyl> datas = this.TJyXtzjyyjylDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TJyXtzjyyjyl data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TJyXtzjyyjylDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKhDsfhzjgxx".equals(type)){
					List<TKhDsfhzjgxx> datas = this.TKhDsfhzjgxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKhDsfhzjgxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKhDsfhzjgxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKhJydsgr".equals(type)){
					List<TKhJydsgr> datas = this.TKhJydsgrDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKhJydsgr data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKhJydsgrDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKhJydsjg".equals(type)){
					List<TKhJydsjg> datas = this.TKhJydsjgDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKhJydsjg data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKhJydsjgDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKhJydsjggdxx".equals(type)){
					List<TKhJydsjggdxx> datas = this.TKhJydsjggdxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKhJydsjggdxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKhJydsjggdxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKhTzgwhtb".equals(type)){
					List<TKhTzgwhtb> datas = this.TKhTzgwhtbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKhTzgwhtb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKhTzgwhtbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKhXtkhgr".equals(type)){
					List<TKhXtkhgr> datas = this.TKhXtkhgrDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKhXtkhgr data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKhXtkhgrDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKhXtkhjg".equals(type)){
					List<TKhXtkhjg> datas = this.TKhXtkhjgDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKhXtkhjg data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKhXtkhjgDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKjGynbkmdzb".equals(type)){
					List<TKjGynbkmdzb> datas = this.TKjGynbkmdzbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKjGynbkmdzb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKjGynbkmdzbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKjGyzcfzkmtjb".equals(type)){
					List<TKjGyzcfzkmtjb> datas = this.TKjGyzcfzkmtjbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKjGyzcfzkmtjb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKjGyzcfzkmtjbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKjGyzzkjqkmb".equals(type)){
					List<TKjGyzzkjqkmb> datas = this.TKjGyzzkjqkmbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKjGyzzkjqkmb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKjGyzzkjqkmbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKjXtnbkmdzb".equals(type)){
					List<TKjXtnbkmdzb> datas = this.TKjXtnbkmdzbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKjXtnbkmdzb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKjXtnbkmdzbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKjXtxmzcfztjb".equals(type)){
					List<TKjXtxmzcfztjb> datas = this.TKjXtxmzcfztjbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKjXtxmzcfztjb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKjXtxmzcfztjbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TKjXtxmzzkjqkmb".equals(type)){
					List<TKjXtxmzzkjqkmb> datas = this.TKjXtxmzzkjqkmbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TKjXtxmzzkjqkmb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TKjXtxmzzkjqkmbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmFdcjsxmxx".equals(type)){
					List<TXmFdcjsxmxx> datas = this.TXmFdcjsxmxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmFdcjsxmxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmFdcjsxmxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmFfdcjsxmxx".equals(type)){
					List<TXmFfdcjsxmxx> datas = this.TXmFfdcjsxmxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmFfdcjsxmxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmFfdcjsxmxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmXtdbgxb".equals(type)){
					List<TXmXtdbgxb> datas = this.TXmXtdbgxbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmXtdbgxb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmXtdbgxbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmXtdbhtb".equals(type)){
					List<TXmXtdbhtb> datas = this.TXmXtdbhtbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmXtdbhtb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmXtdbhtbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmXtdzywb".equals(type)){
					List<TXmXtdzywb> datas = this.TXmXtdzywbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmXtdzywb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmXtdzywbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmXtxmqsxx".equals(type)){
					List<TXmXtxmqsxx> datas = this.TXmXtxmqsxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmXtxmqsxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmXtxmqsxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmXtxmsyqb".equals(type)){
					List<TXmXtxmsyqb> datas = this.TXmXtxmsyqbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmXtxmsyqb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmXtxmsyqbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmXtxmxx".equals(type)){
					List<TXmXtxmxx> datas = this.TXmXtxmxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmXtxmxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmXtxmxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmXtxmyjhklypgb".equals(type)){
					List<TXmXtxmyjhklypgb> datas = this.TXmXtxmyjhklypgbDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmXtxmyjhklypgb data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmXtxmyjhklypgbDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmXtzhxx".equals(type)){
					List<TXmXtzhxx> datas = this.TXmXtzhxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmXtzhxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmXtzhxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmXtzjmjhtxx".equals(type)){
					List<TXmXtzjmjhtxx> datas = this.TXmXtzjmjhtxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmXtzjmjhtxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmXtzjmjhtxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				} else if("TXmXtzjyyhtxx".equals(type)){
					List<TXmXtzjyyhtxx> datas = this.TXmXtzjyyhtxxDAO.getListForPage(inputParams, -1, -1, null);dataMap.put(type, new ArrayList<Object>());
					for(TXmXtzjyyhtxx data :datas){
						count++;dataCount++;data.setCjrq(time.replace("-", ""));
						dataMap.get(type).add(data);
					}
					this.TXmXtzjyyhtxxDAO.submitData(inputParams,time.replace("-", ""));resultMap.put(type+"Count", dataCount+"");
				}
			}
			for(String type : dataMap.keySet()){
				List<Object> dataList = dataMap.get(type);
				if(dataList != null && dataList.size() > 0){
					String[] columnList = (String[]) ReflectUtlity.getConst(TableColumn.class, type);
					StringBuilder dataStr = new StringBuilder();
					for(Object data : dataList){
						for(String column : columnList){
							if(ReflectUtlity.invokeGet(data, column) != null){
								if(("TKhXtkhgr".equals(type) && "khqc".equals(column)) || ("TKhJydsgr".equals(type) && "syrqc".equals(column))){
									String value = ReflectUtlity.invokeGet(data, column).toString();
									String str = "";
									for(int x=0;x<value.length()-1;x++){
										str = str+"*";
									}
									str = str + value.substring(value.length()-1, value.length());
									dataStr.append(str).append("");
								}else if(column.indexOf("zjhm") > -1){
									String value = ReflectUtlity.invokeGet(data, column).toString();
									dataStr.append(MD5.getMD5(value.toUpperCase())).append("");
								}else if("lxdh".equals(column) || "sj".equals(column) || "dh".equals(column) || "cz".equals(column) || "czdh".equals(column)){
									String value = ReflectUtlity.invokeGet(data, column).toString();
									if(value.length() == 11 && value.indexOf("-") == -1){
										dataStr.append(value.substring(0, 3) + "0000" + value.substring(7 ,value.length())).append("");
									}else if(value.length() > 5){
										dataStr.append(value.substring(0, value.length()-4)+"0000").append("");
									}else{
										dataStr.append(value).append("");
									}
								}else if(CheckBigDecimalValues.bigdecimal2p.containsKey(column)) {//判断两位小数
									dataStr.append(String.format("%.2f", ReflectUtlity.invokeGet(data, column))).append("");
								}else if(CheckBigDecimalValues.bigdecimal3p.containsKey(column)){
									dataStr.append(String.format("%.3f", ReflectUtlity.invokeGet(data, column))).append("");
								}else if(CheckBigDecimalValues.bigdecimal4p.containsKey(column)){
									dataStr.append(String.format("%.4f", ReflectUtlity.invokeGet(data, column))).append("");
								}else{
									dataStr.append(ReflectUtlity.invokeGet(data, column)).append("");
								}
							}else{
								dataStr.append("").append("");
							}
						}
						dataStr.delete(dataStr.length() - 1, dataStr.length());
						dataStr.append("\r\n");
					}
					if(dataStr.length() > 0){
						dataStr.delete(dataStr.length()-2, dataStr.length());
					}
					resultMap.put(type, dataStr.toString());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		resultMap.put("count", count+"");
		return resultMap;
	}


}
