package com.bbl.async;

import com.bbl.business.good.entity.LitemallGoods;
import com.bbl.business.good.service.ILitemallGoodsService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class AsyncImg {

    @Autowired
    ILitemallGoodsService iLitemallGoodsService;

    @Async
    public void parlist( List<LitemallGoods> lists) throws Exception{
//        String savePath="E://imgdown";
        String savePath="/root//redbagbuy";


        for (LitemallGoods litemallGoods:lists ) {
            int status=0;
            String imgname= litemallGoods.getPicUrl ();
            imgname=imgname.substring (imgname.lastIndexOf ("/")+1,imgname.length ());
            status=status+download(litemallGoods.getPicUrl (),imgname,savePath,status);



            if(status==0){
                String[] split = litemallGoods.getBrief ().split (",");
                List<String> strings = Arrays.asList (split);
                List<List<String>> partition = Lists.partition (strings, 5);
                for (List<String> spl: partition ) {
                    status=status+downimg (spl,savePath,status);

                }
            }
            if(status==0){
                litemallGoods.setUpdateTime (DateFormatUtils.format (new Date (),"yyyy-MM-dd HH:mm:ss"));
                iLitemallGoodsService.updateById (litemallGoods);
            }

        }

    }

    @Async
    public Integer downimg(List<String> split,String savePath, Integer status) throws Exception{
        for (String spl: split ) {
            String imgname1= spl;
            imgname1=imgname1.substring (imgname1.lastIndexOf ("/")+1,imgname1.length ());

             status = status+download (spl, imgname1, savePath, status);
             if(status>0)
                 break;

        }
        return status;
    }


    public  Integer download(String urlString, String filename,String savePath, Integer status)  {

    log.info ("down="+urlString);
        try {

            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(15*1000);
            // 输入流
            InputStream is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf=new File(savePath);
            if(!sf.exists()){
                sf.mkdirs();
            }
            OutputStream os = new FileOutputStream (sf.getPath()+"//"+filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }catch (Exception e){

            e.printStackTrace ();
            status=1;
        }

        return status;
    }
//
}
