
package com.whaty.platform.setup.comptest;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.whaty.platform.setup.PlatformSetupBean;

public class PlatformSetupTests {

    private boolean m_green;
    private boolean m_red;
    private List m_testResults;
    private boolean m_yellow;

    /**
     * Creates a new setup test suite.<p>
     */
    public PlatformSetupTests() {

        super();
    }

    /**
     * Returns a list of all available tests.<p>
     * 
     * @return a list of all available tests
     */
    public List getAllTests() {

        List tests = new ArrayList();
        //tests.add(new PlatformSetupTestFolderPermissions());
        tests.add(new PlatformSetupTestJdkVersion());
        //tests.add(new PlatformSetupTestOperatingSystem());
        //tests.add(new PlatformSetupTestServletEngine());
        //tests.add(new PlatformSetupTestSimapi());
        //tests.add(new PlatformSetupTestWarFileUnpacked());
        //tests.add(new PlatformSetupTestXercesVersion());
        return tests;
    }

    /**
     * Returns the test results.<p>
     * 
     * @return the test results
     */
    public List getTestResults() {

        return m_testResults;
    }

    /**
     * Returns true, if the conditions in all testes were fulfilled.<p>
     * 
     * @return true, if the conditions in all testes were fulfilled
     */
    public boolean isGreen() {

        return m_green;
    }

    /**
     * Returns true if one of the tests found a violated condition.
     * It is assumed that it will be impossible to run OpenCms.<p>
     * 
     * @return true if one of the tests violates a condition
     */
    public boolean isRed() {

        return m_red;
    }

    /**
     * Returns true if one of the tests found a questionable condition.
     * It is possible that OpenCms will not run.<p>
     * 
     * @return true if one of the tests found a questionable condition
     */
    public boolean isYellow() {

        return m_yellow;
    }

    /**
     * Runs all tests.<p>
     * 
     * @param setupBean the CmsSetup bean of the setup wizard
     */
    public void runTests(PlatformSetupBean setupBean) {

        boolean hasRed = false;
        boolean hasYellow = false;

        // reset everything back to an initial state
        m_testResults = new ArrayList();
        setGreen();

        Iterator it = getAllTests().iterator();
        while (it.hasNext()) {
            I_PlatformSetupTest test = (I_PlatformSetupTest)it.next();
            PlatformSetupTestResult testResult = null;
            try {
                testResult = test.execute(setupBean);
                m_testResults.add(testResult);
            } catch (Throwable e) {
                if (testResult != null) {
                    testResult.setRed();
                    testResult.setResult(I_PlatformSetupTest.RESULT_FAILED);
                    testResult.setHelp("Unable to test " + test.getName());
                    testResult.setInfo(e.toString());
                }
            }
        }

        // check whether a test found violated or questionable conditions
        for (int i = 0; i < m_testResults.size(); i++) {
            PlatformSetupTestResult testResult = (PlatformSetupTestResult)m_testResults.get(i);
            if (testResult.isRed()) {
                hasRed = true;
            } else if (testResult.isYellow()) {
                hasYellow = true;
            }
        }

        // set the global result of all tests
        if (hasRed) {
            setRed();
        } else if (!hasRed && hasYellow) {
            setYellow();
        } else {
            setGreen();
        }

       
    }

    /**
     * Sets if the conditions in all testes were fulfilled.<p>
     */
    protected void setGreen() {

        m_green = true;
        m_red = false;
        m_yellow = false;
    }

    /**
     * Sets if one of the tests found a violated condition.<p>
     */
    protected void setRed() {

        m_green = false;
        m_red = true;
        m_yellow = false;
    }

    /**
     * Sets if one of the tests found a questionable condition.<p>
     */
    protected void setYellow() {

        m_green = false;
        m_red = false;
        m_yellow = true;
    }

   

}
