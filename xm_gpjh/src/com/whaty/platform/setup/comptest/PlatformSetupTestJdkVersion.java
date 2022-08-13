
package com.whaty.platform.setup.comptest;

import com.whaty.platform.setup.PlatformSetupBean;


/**
 * Tests the version of the JDK.<p>
 * 
 * @author Michael Moossen
 * 
 * @version $Revision: 1.3 $ 
 * 
 * @since 6.1.8 
 */
public class PlatformSetupTestJdkVersion implements I_PlatformSetupTest {

    /** The test name. */
    public static final String TEST_NAME = "JDK Version";
    
    private String  requiredJDK="1.4.1";

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

        String requiredJDK = this.getRequiredJDK();
        String JDKVersion = System.getProperty("java.version");

        testResult.setResult(JDKVersion);

        boolean supportedJDK = compareJDKVersions(JDKVersion, requiredJDK);

        if (!supportedJDK) {
            testResult.setRed();
            testResult.setHelp("whaty platform requires at least Java version "
                + requiredJDK
                + " to run. Please update your JDK");
        } else {
            testResult.setGreen();
        }
        return testResult;
    }

    /**
     * Checks if the used JDK is a higher version than the required JDK.<p>
     * 
     * @param usedJDK The JDK version in use
     * @param requiredJDK The required JDK version
     * 
     * @return true if used JDK version is equal or higher than required JDK version, false otherwise
     */
    private boolean compareJDKVersions(String usedJDK, String requiredJDK) {

        int compare = usedJDK.compareTo(requiredJDK);
        return (!(compare < 0));
    }

	public String getRequiredJDK() {
		return requiredJDK;
	}

	public void setRequiredJDK(String requiredJDK) {
		this.requiredJDK = requiredJDK;
	}
}
