<%! 
   String fixnull(String str){
      if(null == str || str.equals("") || str.equals("null")){
        return "";
      }  else
       return str;
   }
     String getString(String str){
      if(null == str || str.equals("") || str.equals("null")){
        return "未记录";
      }  else
       return str;
   }
   String standing(String type)
   {
    int i=Integer.parseInt(type);
    switch(i)
    {case 1: return "铁路职工";
     case 2:return "在校学生";
     case 3:return "铁路干部";
     case 4:return  "社会零散";
     default: return "其它";
    }
   }
   String marriage(String type)
   {
    int i=Integer.parseInt(type);
    switch(i)
    {case 0: return "未婚";
     case 1:return "已婚";
     default: return "不详";
    }
   }
   String parseGender(String type)
   {
  try{ int i=Integer.parseInt(type);
    switch(i)
    {case 0: return "女";
     case 1:return "男";
     default: return "不详";
    }
   }catch(Exception e) {
      if("女".equals(type)) return "女";
      if("男".equals(type)) return "男";
      else return "未记录";
     
   }
   
   }
%>
