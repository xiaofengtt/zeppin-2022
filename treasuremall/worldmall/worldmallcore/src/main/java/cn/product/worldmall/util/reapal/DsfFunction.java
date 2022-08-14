package cn.product.worldmall.util.reapal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.ui.Model;

public class DsfFunction {



    /**
     * 普通TXT格式
     * @param fileName
     * @return
     */
    public static String readFileByLines(String fileName) {
        StringBuffer sb=new StringBuffer();

        FileInputStream  file = null;
        BufferedReader reader = null;
        try {
            file = new FileInputStream (fileName);
            InputStreamReader isr = new InputStreamReader(file, "GBK");
            reader = new BufferedReader(isr);
            String tempString = null;
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
                //过滤空行
                if (tempString.trim().length()==0) {
                    continue;
                }
                line++;
                //过滤第一行
                if(line>1){
                    sb.append(tempString+"|");
                }

            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e1) {
                }
            }
        }
        //System.out.println(sb.toStrieng());
        return sb.toString();
    }
    /**
     * xls格式上传
     * @param excelFileName
     * @return
     * @throws BiffException
     * @throws IOException
     */
    public static String readExcel(String excelFileName,Model model) throws BiffException, IOException{
        Workbook rwb = null;
        Cell cell = null;
        InputStream stream = new FileInputStream(excelFileName);
        Sheet sheet = null;
        try{
            rwb = Workbook.getWorkbook(stream);   //过不去
        }catch(BiffException e){
        	model.addAttribute("msg", "请选择文件后上传");
    		return "test/payByFile";
        }catch(IOException i){
        	
        }catch(Exception e){
        	
        }
    	sheet = rwb.getSheet(0);
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < sheet.getRows(); i++) {
            if(sheet.getCell(0,i).getContents()==""){break;}
            String[] str = new String[sheet.getColumns()];

            for (int j = 0; j < sheet.getColumns(); j++) {
                cell = sheet.getCell(j, i);
                str[j] = cell.getContents();
//					if (str[j] == "") {break;}
                sb.append(str[j]+",");

            }
            sb.append("|");
        }
        String re=sb.toString().replace(",|", "|");

        return re;
    }
   
}
