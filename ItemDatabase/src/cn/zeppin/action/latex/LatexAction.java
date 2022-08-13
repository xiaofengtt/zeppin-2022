/** 
 * Project Name:ItemDatabase 
 * File Name:LatexAction.java 
 * Package Name:cn.zeppin.action.latex 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.latex;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.imageio.ImageIO;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: LatexAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年8月13日 下午12:28:16 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class LatexAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8329172001757250582L;

	/**
	 * 公式生成图片
	 * 
	 * @author Administrator
	 * @date: 2014年8月13日 下午2:53:35 <br/>
	 * @throws IOException
	 */
	public void getLatexImg() throws IOException {

		if (request.getQueryString() != null && request.getQueryString().trim().length() > 0) {

			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("image/png");
			response.addHeader("expires", "0");

			String strLatex = request.getQueryString();
			strLatex = strLatex.replaceAll("[+]", "%2B");

			String latex = URLDecoder.decode(strLatex, "UTF-8");

			TeXFormula formula = new TeXFormula(latex);

			//TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 55);

			TeXIcon icon = formula.new TeXIconBuilder()
					.setStyle(TeXConstants.STYLE_DISPLAY)
					.setSize(15)
					.setWidth(TeXConstants.STYLE_DISPLAY, 0, TeXConstants.ALIGN_LEFT)
					//.setInterLineSpacing(TeXConstants.UNIT_CM, 20f)
					.build();

			OutputStream os = response.getOutputStream();

			BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = image.createGraphics();

			// g2.setColor(Color.white);
			// g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());

			icon.paintIcon(null, g2, 0, 0);
			g2.dispose();

			ImageIO.write(image, "png", os);

		} else {
			ActionResult result = new ActionResult();
			String dataType = request.getParameter("datatype");

			result.init(FAIL_STATUS, "公式表达式有问题", null);

			Utlity.ResponseWrite(result, dataType, response);
		}

	}

}
