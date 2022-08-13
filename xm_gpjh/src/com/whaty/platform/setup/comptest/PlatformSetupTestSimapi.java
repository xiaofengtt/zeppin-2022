/*
 * File   : $Source: H:/cvs/whatymanager4/src/com/whaty/platform/setup/comptest/PlatformSetupTestSimapi.java,v $
 * Date   : $Date: 2006/07/05 08:17:08 $
 * Version: $Revision: 1.3 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (c) 2005 Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.whaty.platform.setup.comptest;

import com.alkacon.simapi.RenderSettings;
import com.alkacon.simapi.Simapi;
import com.alkacon.simapi.filter.ImageMath;
import com.alkacon.simapi.filter.RotateFilter;
import com.whaty.platform.setup.PlatformSetupBean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;


/**
 * Tests the image processing capabilities.<p>
 * 
 * @author Michael Moossen
 * 
 * @version $Revision: 1.3 $ 
 * 
 * @since 6.1.8 
 */
public class PlatformSetupTestSimapi implements I_PlatformSetupTest {

    /** The test name. */
    public static final String TEST_NAME = "Image Processing";

    /**
     * @see com.whaty.platform.setup.comptest.I_PlatformSetupTest#getName()
     */
    public String getName() {

        return TEST_NAME;
    }
    
    /**
     * @see com.whaty.platform.setup.comptest.I_PlatformSetupTest#execute(org.opencms.setup.CmsSetupBean)
     */
    public PlatformSetupTestResult execute(PlatformSetupBean setupBean) {
        
        PlatformSetupTestResult testResult = new PlatformSetupTestResult(this);
        boolean ok = true;
        Throwable ex = null;
        try {
            RenderSettings settings = new RenderSettings(Simapi.RENDER_QUALITY);
            settings.setCompressionQuality(0.85f);
            Simapi simapi = new Simapi(settings);

            ImageIO.scanForPlugins();
            Iterator pngReaders = ImageIO.getImageReadersByFormatName(Simapi.TYPE_PNG);
            if (!pngReaders.hasNext()) {
                throw (new Exception("No Java ImageIO readers for the PNG format are available."));
            }
            Iterator pngWriters = ImageIO.getImageWritersByFormatName(Simapi.TYPE_PNG);
            if (!pngWriters.hasNext()) {
                throw (new Exception("No Java ImageIO writers for the PNG format are available."));
            }

            String basePath = setupBean.getConfig().getPlatformWebAppAbsPath();
            if (!basePath.endsWith(File.separator)) {
                basePath += File.separator;
            }
            basePath += "setup" + File.separator + "resources" + File.separator;

            BufferedImage img1 = Simapi.read(basePath + "test1.png");
            BufferedImage img3 = simapi.applyFilter(img1, new RotateFilter(ImageMath.PI));
            simapi.write(img3, basePath + "test3.png", Simapi.TYPE_PNG);
            BufferedImage img2 = Simapi.read(basePath + "test2.png");

            ok = Arrays.equals(simapi.getBytes(img2, Simapi.TYPE_PNG), simapi.getBytes(img3, Simapi.TYPE_PNG));
        } catch (Throwable e) {
            ok = false;
            ex = e;
        }

        if (ok) {
            testResult.setResult(RESULT_PASSED);
            testResult.setGreen();
        } else {
            testResult.setYellow();
            if (ex != null) {
                testResult.setResult(RESULT_FAILED);
                testResult.setHelp(ex.toString());
                testResult.setInfo("<p><code>-Djava.awt.headless=true</code> JVM parameter or X-Server may be missing.<br>"
                    + "<b>You can continue the setup, but image processing will be disabled.</b></p>");
            } else {
                testResult.setResult(RESULT_WARNING);
                testResult.setHelp("Image processing works but result does not exactly match.");
                StringBuffer info = new StringBuffer();
                info.append("<p>Please check the following images for visible differences:</p>");
                info.append("<table width='100%'>");
                info.append("<tr><th>Expected</th><th>Result</th></tr>");
                info.append("<tr><td align='center' width='50%'><img src='resources/test2.png'></td>");
                info.append("<td align='center' width='50%'><img src='resources/test3.png'></td></table>");
                info.append("<p><b>You can continue the setup, but image processing may not always work as expected.</b></p>");
                testResult.setInfo(info.toString());
            }
        }
        return testResult;
    }
}
