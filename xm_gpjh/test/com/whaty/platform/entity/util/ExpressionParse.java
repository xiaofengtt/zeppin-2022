package com.whaty.platform.entity.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;


public class ExpressionParse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("0123456789".charAt(0));
		//System.out.println("0123456789".substring(0,8));
		List l;
		try {
			//l = strToList("5 or ((<20 and >10)or (not 25 or 30) or (>40 and <50)) or (>100 or not 105)",String.class);
			l = strToList("100 or 105",String.class);
			while(l!=null&&l.size()>0){
				System.out.println(l.remove(0));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DetachedCriteria myfunction = parseExpression(DetachedCriteria.forClass(ExpressionParse.class),"score","100 or 105",java.lang.String.class);
		
//		DetachedCriteria myfunction = myfunction(DetachedCriteria.forClass(Test.class),"score","5 or ((<20 and >10)or (not 25 or 30) or (>=40 and <50)) or (>100 or not 105)");
		
	//	DetachedCriteria myfunction = parseExpression(DetachedCriteria.forClass(ExpressionParse.class),"score","(>=1988-02-29 12:34:56  or <1900-03) and not >2008",java.util.Date.class);
		
		return;
	}
	/**
	 * 解析不同类型的字符串为相应的DetachedCriteria关系
	 * @param dc
	 * @param propertyName
	 * @param exp
	 * @param clazz
	 * @return
	 */
	public static DetachedCriteria parseExpression(DetachedCriteria dc,String propertyName,String exp,Class clazz){
		exp = exp.trim();
		DetachedCriteria dc0 = dc;
		try {
			List list = strToList(exp,clazz);
			Stack stack = new Stack();
			Stack stack_temp = new Stack();
			Stack stack_temp2 = new Stack();
			while(!list.isEmpty()){			
				Object temp = list.remove(0);
				if(temp.toString().equals("("))
					stack.push(temp);
				else if(temp.toString().equals(")")){
					Criterion criterion = null;
					Object otemp = null;
					while(!(otemp = stack.pop()).toString().equals("(")){
						if(otemp.getClass().getName().equals("java.lang.String")){	
							String o  = (String)otemp;
							if(o.equals("not")){
								criterion = Restrictions.not((Criterion)stack_temp.pop());
								stack_temp.push(criterion);
								criterion = null;
								o="";
							}else
								stack_temp.push(o);
						}else{
							stack_temp.push((Criterion)otemp);
						}
					}
					while(!stack_temp.isEmpty()){
						Object o_temp = stack_temp.pop();
						if(o_temp.getClass().getName().equals("java.lang.String")){
							if(o_temp.toString().equals("and")){
								criterion = Restrictions.and((Criterion)stack_temp2.pop(),(Criterion)stack_temp.pop());
								stack_temp2.push(criterion);	
								criterion = null;
							}else{
								stack_temp2.push(o_temp);
							}
						}else{
							stack_temp2.push(o_temp);							
						}
					}
					while(!stack_temp2.isEmpty()){
						Criterion criterion1 = null;
						Criterion criterion2 = null;
						criterion1 =(Criterion)stack_temp2.pop();
						if(!stack_temp2.isEmpty()){
							String o=(String)stack_temp2.pop();
							criterion2 =(Criterion)stack_temp2.pop();
							if(o.equals("or")){
								criterion = Restrictions.or(criterion1,criterion2);
								stack_temp2.push(criterion);	
								criterion = null;
							}else
								throw new Exception("表达式解析失败："+exp);
						}else{
							stack.push(criterion1);
						}
					}
				}else if(isOperate(temp.toString())){
					Object s = list.remove(0);
					if(isOperate(s.toString())||isLogicOperate(s.toString())||(")").equals(s.toString()))
						throw new Exception("表达式解析失败："+exp);
					else if(("(").equals(s)){
						stack.push(temp);
						stack.push(s);
					}else{
						Criterion criterion = null;
						if(temp.equals("="))
							criterion = Restrictions.eq(propertyName, s);
						else if(temp.equals(">="))
							criterion = Restrictions.ge(propertyName, s);
						else if(temp.equals("<="))
							criterion = Restrictions.le(propertyName, s);
						else if(temp.equals("<"))
							criterion = Restrictions.lt(propertyName, s);
						else if(temp.equals(">"))
							criterion = Restrictions.gt(propertyName, s);
						else 
							throw new Exception("表达式解析失败："+exp);
						stack.push(criterion);
					}					
				}else if(isLogicOperate(temp.toString())){				
					stack.push(temp);
				}else{
					if(stack.isEmpty()){
						Criterion criterion  = null;
						if(clazz.getName().equals("java.lang.String"))
							criterion = Restrictions.like(propertyName, temp.toString(),MatchMode.ANYWHERE);
						else							
							criterion = Restrictions.eq(propertyName, temp);
						stack.push(criterion);
					}else{
						Object o = stack.peek();
						if(isLogicOperate(o.toString())||o.toString().equals("(")){
							Criterion criterion  = null;
							if(clazz.getName().equals("java.lang.String"))
								criterion = Restrictions.like(propertyName, temp.toString(),MatchMode.ANYWHERE);
							else							
								criterion = Restrictions.eq(propertyName, temp);
							stack.push(criterion);
						}else
							throw new Exception("表达式解析失败："+exp);						
					}
				}
					
			}
			Criterion criterion = null;
			while(!stack.isEmpty()){
				Object otemp = stack.pop();
				if(otemp.getClass().getName().endsWith("String")){
					String o = (String)otemp;
					if(o.equals("not")){
						criterion = Restrictions.not((Criterion)stack_temp.pop());
						stack_temp.push(criterion);
						criterion = null;
						o="";
					}else 
						stack_temp.push(o);					
				}else{
					stack_temp.push((Criterion)otemp);
				}
			}
			while(!stack_temp.isEmpty()){
				Object o_temp = stack_temp.pop();
				if(o_temp.getClass().getName().equals("java.lang.String")){
					if(o_temp.toString().equals("and")){
						criterion = Restrictions.and((Criterion)stack_temp2.pop(),(Criterion)stack_temp.pop());
						stack_temp2.push(criterion);	
						criterion = null;
					}else
						stack_temp2.push(o_temp);
				}else{
					stack_temp2.push(o_temp);							
				}
			}
			while(!stack_temp2.isEmpty()){
				Criterion criterion1 = null;
				Criterion criterion2 = null;
				criterion1 =(Criterion)stack_temp2.pop();
				if(!stack_temp2.isEmpty()){
					String o=(String)stack_temp2.pop();
					criterion2 =(Criterion)stack_temp2.pop();
					if(o.equals("or")){
						criterion = Restrictions.or(criterion1,criterion2);
						stack_temp2.push(criterion);	
						criterion = null;
					}else
						throw new Exception("表达式解析失败："+exp);
				}else{
					stack.push(criterion1);
				}
			}
			dc0.add((Criterion)stack.pop());
		}catch(Throwable e){
			System.out.println("表达式解析失败："+exp);
			e.printStackTrace();
			dc0 = dc;
		}
		return dc0;
	}
	public static boolean isOperate(String o){
		boolean b = false;
		String[] s = {">",">=","<","<=","="};
		if(o!=null)
			for(int i=0;i<s.length;i++){
				if(s[i].equals(o)){
					b=true;
					break;
				}
			}
		return b;
	}
	public static boolean isLogicOperate(String o){
		boolean b = false;
		String[] s = {"and","or","not"};
		if(o!=null)
			for(int i=0;i<s.length;i++){
				if(s[i].equals(o)){
					b=true;
					break;
				}
			}
		return b;
	}
	public static boolean isStart(char c){
		if(c=='='||c=='>'||c=='<'||c=='('||c==')')
			return true;
		else
			return false;
	}
	public static String dateformate = "yyyy-MM-dd HH:mm:ss";
	public static Object toObject(String s,Class clazz) throws Exception{
		Object o = null;
		if(clazz.getName().equals("java.lang.String"))
			o=s;
		else if(clazz.getName().equals("java.util.Date")){
			String f = dateformate.substring(0, s.length());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(f);
			o = sdf.parse(s);
		}else if(clazz.getName().equals("java.lang.Double")){
			o = java.lang.Double.parseDouble(s);
		}else if(clazz.getName().equals("java.lang.Long")){
			o = java.lang.Long.parseLong(s);
		}else 
			throw new Exception("类型不支持表达式搜索");
//		System.out.println(o.toString());
		return o;
		
	}
	public static List strToList(String exp,Class clazz) throws Exception{
		List list = new ArrayList();
		int e_length = exp.length();
		boolean b = false;
		for(int i=0;i<e_length;i++){
			char c = exp.charAt(i);
			if(c==' ') continue;
			if(c=='='||c=='('||c==')'){
				list.add(""+c);
				continue;
			}			
			if(c=='>'){
				if(i<e_length-1){
					if(exp.charAt(i+1)=='='){
						list.add(">=");
						i++;
					}else{
						list.add(">");
					}	
				}else{
					list.add(">");
				}
				continue;
			}
			if(c=='<'){
				if(i<e_length-1){
					if(exp.charAt(i+1)=='='){
						list.add("<=");
						i++;
					}else{
						list.add("<");
					}
				}else{
					list.add("<");
				}
				continue;				
			}
			if(c=='o'){
				if(i<e_length-1){
					char c_temp = exp.charAt(i+1);
					if(c_temp=='r'){
						if(i<e_length-2){
							if(exp.charAt(i+2)==' '||isStart(exp.charAt(i+2))){
								list.add("or");
								i=i+2;
							}
						}else{
							list.add("or");
							i=i+1;
						}
					}else{
						String stemp=""+c;
						boolean bb=true;
						while(++i<e_length){
							c_temp =  exp.charAt(i);
							if(isStart(c_temp)){break;}
							stemp += c_temp;
							if(stemp.indexOf(" and ")>0){
								bb=false;
								list.add(toObject(stemp.split(" and ")[0].trim(),clazz));
								list.add("and");
								break;
							}
							if(stemp.indexOf(" not ")>0){
								bb=false;
								list.add(toObject(stemp.split(" not ")[0].trim(),clazz));
								list.add("not");
								break;
							}
							if(stemp.indexOf(" or ")>0){
								bb=false;
								list.add(toObject(stemp.split(" or ")[0].trim(),clazz));
								list.add("or");
								break;
							}
						}
						i--;
						if(bb){
							if(stemp.endsWith(" not")){
								list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
								list.add("not");					
							}else if(stemp.endsWith(" and")){
								list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
								list.add("and");					
							}else if(stemp.endsWith(" or")){
								list.add(toObject(stemp.substring(0, stemp.length()-2).trim(),clazz));
								list.add("or");					
							}else
								list.add(toObject(stemp.trim(),clazz));
						}
					}
				}else{
					list.add(c+"");
				}
				continue;
			}
			if(c=='a'){
				if(i<e_length-2){
					String stemp=""+c+exp.charAt(i+1)+exp.charAt(i+2);
					if(stemp.equals("and")){
						i=i+2;
						if(i<e_length){
							char c_temp = exp.charAt(i+1);
							if(isStart(c_temp)||c_temp==' '){
								list.add("and");								
							}else{
								boolean bb=true;
								while(++i<e_length){
									c_temp =  exp.charAt(i);
									if(isStart(c_temp)){break;}
									stemp += c_temp;
									if(stemp.indexOf(" and ")>0){
										bb=false;
										list.add(toObject(stemp.split(" and ")[0].trim(),clazz));
										list.add("and");
										break;
									}
									if(stemp.indexOf(" not ")>0){
										bb=false;
										list.add(toObject(stemp.split(" not ")[0].trim(),clazz));
										list.add("not");
										break;
									}
									if(stemp.indexOf(" or ")>0){
										bb=false;
										list.add(toObject(stemp.split(" or ")[0].trim(),clazz));
										list.add("or");
										break;
									}
								}
								i--;
								if(bb){
									if(stemp.endsWith(" not")){
										list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
										list.add("not");					
									}else if(stemp.endsWith(" and")){
										list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
										list.add("and");					
									}else if(stemp.endsWith(" or")){
										list.add(toObject(stemp.substring(0, stemp.length()-2).trim(),clazz));
										list.add("or");					
									}else
										list.add(toObject(stemp.trim(),clazz));
								}			
							}
						}else{
							list.add("and");
						}
					}else{
						stemp=""+c;
						boolean bb=true;
						while(++i<e_length){
							char c_temp =  exp.charAt(i);
							if(isStart(c_temp)){break;}
							stemp += c_temp;
							if(stemp.indexOf(" and ")>0){
								bb=false;
								list.add(toObject(stemp.split(" and ")[0].trim(),clazz));
								list.add("and");
								break;
							}
							if(stemp.indexOf(" not ")>0){
								bb=false;
								list.add(toObject(stemp.split(" not ")[0].trim(),clazz));
								list.add("not");
								break;
							}
							if(stemp.indexOf(" or ")>0){
								bb=false;
								list.add(toObject(stemp.split(" or ")[0].trim(),clazz));
								list.add("or");
								break;
							}
						}
						i--;
						if(bb){
							if(stemp.endsWith(" not")){
								list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
								list.add("not");					
							}else if(stemp.endsWith(" and")){
								list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
								list.add("and");					
							}else if(stemp.endsWith(" or")){
								list.add(toObject(stemp.substring(0, stemp.length()-2).trim(),clazz));
								list.add("or");					
							}else
								list.add(toObject(stemp.trim(),clazz));
						}
					}
				}else{
					String stemp=""+c;
					if(++i>e_length){
						char c_temp = exp.charAt(i);
						if(!isStart(c_temp)&&c_temp!=' '){
							stemp+=c_temp;
							i++;
						}
					}
					list.add(toObject(stemp.trim(),clazz));
				}
				continue;
			}
			if(c=='n'){
				if(i<e_length-2){
					String stemp=""+c+exp.charAt(i+1)+exp.charAt(i+2);
					if(stemp.equals("not")){
						i=i+2;
						if(i<e_length){
							char c_temp = exp.charAt(i+1);
							if(isStart(c_temp)||c_temp==' '){
								list.add("not");								
							}else{
								boolean bb=true;
								while(++i<e_length){
									c_temp =  exp.charAt(i);
									if(isStart(c_temp)){break;}
									stemp += c_temp;
									if(stemp.indexOf(" and ")>0){
										bb=false;
										list.add(toObject(stemp.split(" and ")[0].trim(),clazz));
										list.add("and");
										break;
									}
									if(stemp.indexOf(" not ")>0){
										bb=false;
										list.add(toObject(stemp.split(" not ")[0].trim(),clazz));
										list.add("not");
										break;
									}
									if(stemp.indexOf(" or ")>0){
										bb=false;
										list.add(toObject(stemp.split(" or ")[0].trim(),clazz));
										list.add("or");
										break;
									}
								}
								i--;
								if(bb){
									if(stemp.endsWith(" not")){
										list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
										list.add("not");					
									}else if(stemp.endsWith(" and")){
										list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
										list.add("and");					
									}else if(stemp.endsWith(" or")){
										list.add(toObject(stemp.substring(0, stemp.length()-2).trim(),clazz));
										list.add("or");					
									}else
										list.add(toObject(stemp.trim(),clazz));
								}			
							}
						}else{
							list.add("not");
						}
					}else{
						stemp=""+c;
						boolean bb=true;
						while(++i<e_length){
							char c_temp =  exp.charAt(i);
							if(isStart(c_temp)){break;}
							stemp += c_temp;
							if(stemp.indexOf(" and ")>0){
								bb=false;
								list.add(toObject(stemp.split(" and ")[0].trim(),clazz));
								list.add("and");
								break;
							}
							if(stemp.indexOf(" not ")>0){
								bb=false;
								list.add(toObject(stemp.split(" not ")[0].trim(),clazz));
								list.add("not");
								break;
							}
							if(stemp.indexOf(" or ")>0){
								bb=false;
								list.add(toObject(stemp.split(" or ")[0].trim(),clazz));
								list.add("or");
								break;
							}
						}
						i--;
						if(bb){
							if(stemp.endsWith(" not")){
								list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
								list.add("not");					
							}else if(stemp.endsWith(" and")){
								list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
								list.add("and");					
							}else if(stemp.endsWith(" or")){
								list.add(toObject(stemp.substring(0, stemp.length()-2).trim(),clazz));
								list.add("or");					
							}else
								list.add(toObject(stemp.trim(),clazz));
						}
					}
				}else{
					String stemp=""+c;
					if(++i>e_length){
						char c_temp = exp.charAt(i);
						if(!isStart(c_temp)&&c_temp!=' '){
							stemp+=c_temp;
							i++;
						}
					}
					list.add(toObject(stemp.trim(),clazz));
				}
				continue;
			}
			String stemp=""+c;
			boolean bb=true;
			while(++i<e_length){
				char c_temp =  exp.charAt(i);
				if(isStart(c_temp)){break;}
				stemp += c_temp;
				if(stemp.indexOf(" and ")>0){
					bb=false;
					list.add(toObject(stemp.split(" and ")[0].trim(),clazz));
					list.add("and");
					break;
				}
				if(stemp.indexOf(" not ")>0){
					bb=false;
					list.add(toObject(stemp.split(" not ")[0].trim(),clazz));
					list.add("not");
					break;
				}
				if(stemp.indexOf(" or ")>0){
					bb=false;
					list.add(toObject(stemp.split(" or ")[0].trim(),clazz));
					list.add("or");
					break;
				}
			}
			i--;
			if(bb){
				if(stemp.endsWith(" not")){
					list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
					list.add("not");					
				}else if(stemp.endsWith(" and")){
					list.add(toObject(stemp.substring(0, stemp.length()-3).trim(),clazz));
					list.add("and");					
				}else if(stemp.endsWith(" or")){
					list.add(toObject(stemp.substring(0, stemp.length()-2).trim(),clazz));
					list.add("or");					
				}else
					list.add(toObject(stemp.trim(),clazz));
			}
		}
		return list;
	}
}
