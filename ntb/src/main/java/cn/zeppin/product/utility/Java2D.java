package cn.zeppin.product.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/** 
 * ClassName: Java2D <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 */
public class Java2D {
	
	/**
	 * 生成图片
	 * @param value
	 * @return
	 */
	public static BufferedImage drawNewAuthCodeImg(String value) {
		int width = 720, height = 1280;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		ImageIcon imgIcon = new ImageIcon("D:\\actives.png");
		Image img = imgIcon.getImage();
		width = img.getWidth(null);
		height = img.getHeight(null);
		
		Graphics g = image.getGraphics();
		
		// 准备在图片中绘制内容
		g.setColor(new Color(221, 221, 221));
		g.fillRect(0, 0, 185, 57);
		g.setFont(new Font("PingFangSC", Font.PLAIN, 95));
		g.drawImage(img, 0, 0, null);
		
		// 数字并加入到图片中
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			g.setColor(new Color(223, 68, 58)); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
//			if(c == ".".charAt(0)){
//				System.out.println(true);
//				g.drawString(String.valueOf(c), 55 * i + 230, 630);
//				continue;
//			}
			if(i == (value.indexOf(".")+1)){
				g.drawString(String.valueOf(c), 42 * i + 230, 630);
				continue;
			}
			if(i == (value.indexOf(".")+2)){
				g.drawString(String.valueOf(c), 46 * i + 230, 630);
				continue;
			}
			g.drawString(String.valueOf(c), 51 * i + 230, 630);
		}
		try {
            FileOutputStream out = new FileOutputStream("D:\\1122.png");
            ImageIO.write(image, "png", out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return image;
	}
	/**
	 * 画验证码
	 * @author Clark
	 * @param image
	 * @param content 
	 * @param height 
	 * @param width 
	 */
	public static BufferedImage drawAuthCodeImg(String value) {
		// TODO Auto-generated method stub+
		int width = 90, height = 25;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		
		// 准备在图片中绘制内容
		g.setColor(getRandColor(214, 225));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.setColor(getRandColor(190, 191));
		
		// 生成随机线条
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		
		// 生成随机的数字并加入到图片中
		for (int i = 0; i < 4; i++) {
			char c = value.charAt(i);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(String.valueOf(c), 13 * i + 14, 21);
		}
		return image;
	}

	/**
	 * 生成随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	public static void main(String[] args) {
		System.out.println("start");
		String str = "18.88";
		System.out.println(str.indexOf("."));
		drawNewAuthCodeImg("1.11");
		System.out.println("end");
	}
}
