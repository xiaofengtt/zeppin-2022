/**
 * 
 */
package cn.zeppin.product.itic.core.controller.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import cn.zeppin.product.utility.CheckDateValues;
import cn.zeppin.product.utility.CheckUtil;
import cn.zeppin.product.utility.CheckValues;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.TableColumn;
import cn.zeppin.product.utility.TableColumnCN;
import cn.zeppin.product.utility.TableValues;
import cn.zeppin.product.utility.Utlity;


public class BaseController implements Controller{

	/**
	 * 处理导入数据
	 */
	protected Map<String, Object> getInputResult(Sheet s, String type,Map<String,String> coloumTypeMap) {
		Row row;
		row = s.getRow(0);
		int t = s.getLastRowNum();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int lastColoum = s.getRow(0).getLastCellNum();
		String[] columnList = (String[]) ReflectUtlity.getConst(TableColumn.class, type);
		String[] columnCNList = (String[]) ReflectUtlity.getConst(TableColumnCN.class, type);
		
		if(lastColoum < columnList.length - 1){
			resultMap.put("result", "different");
			return resultMap;
		}
		
		List<Map<String, Object>> datasList = new ArrayList<Map<String, Object>>();
		List<WarningData> errorList = new ArrayList<WarningData>();					
		
		for(int i = 3; i <= t; i++){
			row = s.getRow(i);
			if(row == null){
				continue;
			}
			Map<String, Object> datas = new HashMap<String, Object>();
			Boolean flagAble = false;
			for(int j =0; j<columnList.length-1;j++){
				if (row.getCell(j) != null) {
					row.getCell(j).setCellType(CellType.STRING);
					String value = row.getCell(j).getStringCellValue().trim();
					
					String typeString = coloumTypeMap.get(columnList[j]);
					if(typeString != null && !"".equals(value)){
						if(typeString.contains("BigDecimal")){
							if(Utlity.isNumeric(value)){
								datas.put(columnList[j], new BigDecimal(value));
							}else{
								datas.put(columnList[j], value);
								errorList.add(new WarningData((i-2)+"", columnCNList[j] + "应填写数字类型数据！"));
							}
						}else{
							if(!CheckUtil.checkValue(columnList[j], value)){
								errorList.add(new WarningData((i-2)+"", columnCNList[j] + "应填写枚举值类型！("+ ReflectUtlity.getConst(CheckValues.class, columnList[j]) +")"));
							}
							if(!CheckUtil.checkDateValue(columnList[j], value)){
								Object obj = ReflectUtlity.getConst(CheckDateValues.class, columnList[j]);
								errorList.add(new WarningData((i-2)+"", columnCNList[j] + "格式如："+obj.toString()));
							}
							datas.put(columnList[j], value);
						}
						flagAble = true;
					}else{
						if(!CheckUtil.checkNullable(type+"_"+columnList[j], null)) {
							errorList.add(new WarningData((i-2)+"", columnCNList[j] + "必填"));
						}
					}
				}
			}
			if(flagAble){
				datasList.add(datas);
			}
		}
		
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("type", TableValues.submitTables.get(type));
		totalMap.put("cloumn", Arrays.copyOf(columnList, columnList.length-1));
		totalMap.put("cloumnCN", Arrays.copyOf(columnCNList, columnCNList.length-1));
		
		Map<String, Object> totalDataMap = new HashMap<String, Object>();
		for(int i=0;i<datasList.size();i++){
			totalDataMap.put(i+1 + "", datasList.get(i));
		}
		totalMap.put("datasMap", totalDataMap);
		
		resultMap.put("result", "success");
		resultMap.put("totalMap", totalMap);
		resultMap.put("errorList", errorList);
		resultMap.put("datasList", datasList);
		return resultMap;
	}

}
