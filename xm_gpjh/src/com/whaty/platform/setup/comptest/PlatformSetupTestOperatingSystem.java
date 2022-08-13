/*
 * File   : $Source: H:/cvs/whatymanager4/src/com/whaty/platform/setup/comptest/PlatformSetupTestOperatingSystem.java,v $
 * Date   : $Date: 2006/07/05 08:17:07 $
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
import com.whaty.platform.setup.PlatformSetupBean;

/**
 * Tests the operating system.<p>
 * 
 * @author Michael Moossen
 * 
 * @version $Revision: 1.3 $ 
 * 
 * @since 6.1.8 
 */
public class PlatformSetupTestOperatingSystem implements I_PlatformSetupTest {

    /** The test name. */
    public static final String TEST_NAME = "Operating System";

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

        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");

        testResult.setResult(osName + " " + osVersion);
        testResult.setHelp("Your operating system version");

        // there is still no handling to test the operating system
        testResult.setGreen();

        return testResult;
    }
}
