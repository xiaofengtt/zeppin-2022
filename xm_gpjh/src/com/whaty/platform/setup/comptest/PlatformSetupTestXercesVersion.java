/*
 * File   : $Source: H:/cvs/whatymanager4/src/com/whaty/platform/setup/comptest/PlatformSetupTestXercesVersion.java,v $
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


import java.io.ByteArrayInputStream;

import org.apache.xerces.impl.Version;
import org.apache.xerces.parsers.DOMParser;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.whaty.platform.setup.PlatformSetupBean;

/**
 * Test for the Xerces version.<p>
 * 
 * @author Michael Moossen
 * 
 * @version $Revision: 1.3 $ 
 * 
 * @since 6.1.8 
 */
public class PlatformSetupTestXercesVersion implements I_PlatformSetupTest {

    /** The test name. */
    public static final String TEST_NAME = "XML Parser";

    /**
     * @see com.whaty.platform.setup.comptest.I_PlatformSetupTest#getName()
     */
    public String getName() {

        return TEST_NAME;
    }

    /**
     * @see com.whaty.platform.setup.comptest.I_PlatformSetupTest#execute(org.opencms.setup.CmsSetupBean)
     */
    public PlatformSetupTestResult execute(PlatformSetupBean setupBean) throws Exception {

        PlatformSetupTestResult testResult = new PlatformSetupTestResult(this);

        DOMParser parser = new DOMParser();
        String document = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<test>test</test>\n";
        parser.parse(new InputSource(new ByteArrayInputStream(document.getBytes("UTF-8"))));
        Document doc = parser.getDocument();

        // Xerces 1 and 2 APIs are different, let's see what we have...
        String versionStr = null;
        int xercesVersion = 0;

        try {
            doc.getClass().getMethod("getXmlEncoding", new Class[] {}).invoke(doc, new Object[] {});
            versionStr = Version.getVersion();
            xercesVersion = 2;
        } catch (Throwable t) {
            // noop
        }
        if (versionStr == null) {
            try {
                doc.getClass().getMethod("getEncoding", new Class[] {}).invoke(doc, new Object[] {});
                versionStr = "Xerces version 1";
                xercesVersion = 1;
            } catch (Throwable t) {
                // noop
            }
        }

        switch (xercesVersion) {
            case 2:
                testResult.setResult(versionStr);
                testResult.setHelp("OpenCms 6.0 requires Xerces version 2 to run. Usually this should be available as part of the servlet environment.");
                testResult.setGreen();
                break;
            case 1:
                testResult.setResult(versionStr);
                testResult.setRed();
                testResult.setInfo("OpenCms 6.0 requires Xerces version 2 to run, your Xerces version is 1. "
                    + "Usually Xerces 2 should be installed by default as part of the servlet environment.");
                testResult.setHelp(testResult.getInfo());
                break;
            default:
                if (versionStr == null) {
                    versionStr = "Unknown version";
                }
                testResult.setResult(versionStr);
                testResult.setRed();
                testResult.setInfo("OpenCms 6.0 requires Xerces version 2 to run. "
                    + "Usually Xerces 2 should be installed by default as part of the servlet environment.");
                testResult.setHelp(testResult.getInfo());
        }
        return testResult;
    }
}
