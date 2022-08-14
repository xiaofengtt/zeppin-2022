/**
 * 
 */
package cn.zeppin.product.ntb.web.controller;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.Resource.ResourceStatus;

/**
 * @author hehe
 *
 * web上传文件
 */

@Controller
@RequestMapping(value = "/web/resource")
public class WebResourceController extends BaseController {

	@Autowired
	private IResourceService resourceService;
	
	/**
	 * 添加一个资源
	 * @param name
	 * @param logo
	 * @return
	 */
	@RequestMapping(value="/add", method = RequestMethod.POST) 
	@ResponseBody
	public Result add(HttpServletRequest request) {
		if (request instanceof MultipartHttpServletRequest) {  
			String beanPath = Resource.class.getResource("").getPath();
			String serverPath = beanPath.substring(0,beanPath.indexOf("WEB-INF"));
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFiles("file").get(0);  
            if (!file.isEmpty()) { 
            	String uuid = UUID.randomUUID().toString();
	            String[] dir = uuid.split("-");
				String basePath = "/upload/";
				for (String sPath : dir) {
					basePath += sPath + "/";
					File tfFile = new File(serverPath + "/" + basePath);
					if (!tfFile.exists()) {
						tfFile.mkdir();
					}
				}
				Resource res = new Resource();
				res.setUuid(uuid);
				String name = dir[dir.length-1];res.setFilename(name);
				res.setFiletype(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
				res.setSize(BigInteger.valueOf(file.getSize()));
				res.setStatus(ResourceStatus.NORMAL);
				res.setUrl(basePath + name + "." + res.getFiletype());
				File file2 = new File(serverPath + res.getUrl());
	            try {
	            	file.transferTo(file2);
	            	BufferedImage bufferedimage = ImageIO.read(file2);
	            	if(bufferedimage.getWidth()<bufferedimage.getHeight()){
	            		bufferedimage = Rotate(bufferedimage, 270);
		            	ImageIO.write(bufferedimage, res.getFiletype(), file2);
	            	}
	            	
				} catch (IOException e) {
					e.printStackTrace();
					return ResultManager.createFailResult("文件上传失败");
				}
				if(res.getFiletype().toLowerCase().equals("jpg")||res.getFiletype().toLowerCase().equals("jpeg")||res.getFiletype().toLowerCase().equals("png")
						||res.getFiletype().toLowerCase().equals("bmp")||res.getFiletype().toLowerCase().equals("tiff")||res.getFiletype().toLowerCase().equals("gif")
						||res.getFiletype().toLowerCase().equals("psd")){
					res.setType("2");
				}else if(res.getFiletype().toLowerCase().equals("mp4")||res.getFiletype().toLowerCase().equals("rmvb")||res.getFiletype().toLowerCase().equals("avi")||res.getFiletype().toLowerCase().equals("mov")
						||res.getFiletype().toLowerCase().equals("3gp")||res.getFiletype().toLowerCase().equals("flv")||res.getFiletype().toLowerCase().equals("wmv")){
					res.setType("1");
				}else if(res.getFiletype().toLowerCase().equals("doc")||res.getFiletype().toLowerCase().equals("docx")||res.getFiletype().toLowerCase().equals("pdf")){
					res.setType("3");///文本文档
				}else{
					return ResultManager.createFailResult("文件格式错误");
				}

	            res = resourceService.insert(res);
	            return ResultManager.createDataResult(res, "文件上传成功");
            }
		}
		return ResultManager.createFailResult("文件上传失败");
	}

	/**
	 * 旋转图片
	 * @param src
	 * @param angel
	 * @return
	 */
	public static BufferedImage Rotate(Image src, int angel) {  
        int src_width = src.getWidth(null);  
        int src_height = src.getHeight(null);  
        // calculate the new image size  
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(  
                src_width, src_height)), angel);  
  
        BufferedImage res = null;  
        res = new BufferedImage(rect_des.width, rect_des.height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2 = res.createGraphics();  
        // transform  
        g2.translate((rect_des.width - src_width) / 2,  
                (rect_des.height - src_height) / 2);  
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);  
  
        g2.drawImage(src, null, null);  
        return res;  
    } 
	
    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {  
        // if angel is greater than 90 degree, we need to do some conversion  
        if (angel >= 90) {  
            if(angel / 90 % 2 == 1){  
                int temp = src.height;  
                src.height = src.width;  
                src.width = temp;  
            }  
            angel = angel % 90;  
        }  
  
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;  
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;  
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;  
        double angel_dalta_width = Math.atan((double) src.height / src.width);  
        double angel_dalta_height = Math.atan((double) src.width / src.height);  
  
        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha  
                - angel_dalta_width));  
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha  
                - angel_dalta_height));  
        int des_width = src.width + len_dalta_width * 2;  
        int des_height = src.height + len_dalta_height * 2;  
        return new java.awt.Rectangle(new Dimension(des_width, des_height));  
    }
	
}
