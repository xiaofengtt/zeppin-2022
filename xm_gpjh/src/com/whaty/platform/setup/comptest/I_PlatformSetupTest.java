
package com.whaty.platform.setup.comptest;

import com.whaty.platform.setup.PlatformSetupBean;



public interface I_PlatformSetupTest {

    /** Test failed display text. */
    String RESULT_FAILED = "failed!";

    /** Test passed display text. */
    String RESULT_PASSED = "passed";

    /** Test warning display text. */
    String RESULT_WARNING = "warning!";

    /**
     * Returns the nice name for the test.<p>
     * 
     * @return the nice name
     */
    String getName();

    /**
     * Returns the test results.<p>
     * 
     * @param setupBean the setup bean
     * 
     * @return the test results
     * 
     * @throws Exception if something goes wrong 
     */
    PlatformSetupTestResult execute(PlatformSetupBean setupBean) throws Exception;
}