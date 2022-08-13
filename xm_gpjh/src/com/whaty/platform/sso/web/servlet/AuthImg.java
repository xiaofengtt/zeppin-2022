package com.whaty.platform.sso.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 生成4位数字验证码
 * 
 * @author 李冰
 * 
 */
public class AuthImg extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7099312208878086233L;

	// private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	// 设置图形验证码中的字符串的字体的大小
	private Font mFont = new Font("Times New Roman", Font.PLAIN, 18);

	// Initialize global variables
	public void init() throws ServletException {
	}

	// Process the HTTP Get request
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 阻止生成的页面内容被缓存，保证每次重新生成随机验证码
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("image/jpeg");
		response.addHeader("expires", "0");

		ServletOutputStream out = response.getOutputStream();
		// 指定图形验证码图片的大小
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 准备在图片中绘制内容
		Graphics g = image.getGraphics();
		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		g.setFont(mFont);

		g.setColor(getRandColor(160, 200));
		// 生成随机线条
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		String rand = RandomStringUtils.randomNumeric(4);
		char c;
		// 生成随机的数字并加入到图片中
		for (int i = 0; i < 4; i++) {
			c = rand.charAt(i);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110))); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(String.valueOf(c), 13 * i + 6, 16);
		}
		// 将验证码保存到seesion中
		HttpSession seesion = request.getSession();
		seesion.setAttribute("authCode", rand);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// Clean up resources
	public void destroy() {
	}

	/**
	 * 生成随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
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

}
