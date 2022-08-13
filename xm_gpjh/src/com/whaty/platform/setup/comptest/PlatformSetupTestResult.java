
package com.whaty.platform.setup.comptest;




public class PlatformSetupTestResult  implements Cloneable {

    /** Test passed flag. */
    private boolean m_green;

    /** A string offering some help in case a test failed.<p> */
    private String m_help;

    /** A info string for the test result.<p> */
    private String m_info;

    /** The clear text name of the test.<p> */
    private String m_name;

    /** Test failed flag. */
    private boolean m_red;

    /** A string describing the result of the test.<p> */
    private String m_result;
    
    /** Test warning flag. */
    private boolean m_yellow;

    /**
     * Creates a new setup test result.<p>
     * 
     * @param test the test to keep track of 
     */
    public PlatformSetupTestResult(I_PlatformSetupTest test) {

        super();

        setGreen();
        setName(test.getName());
        setInfo("");
        setResult("");
        setHelp("");
    }

    /**
     * Returns the help string what to do if a test failed.<p>
     * This string will be displayed in a help bubble.<p>
     * 
     * @return the help string what to do if a test failed
     */
    public String getHelp() {

        return m_help;
    }

    /**
     * Returns the description of the test, e.g. "Test xy failed due to...".<p>
     * 
     * @return the description of the test
     */
    public String getInfo() {

        return m_info;
    }

    /**
     * Returns the name of the test, e.g. "Operating system test".<p>
     * 
     * @return the name of the test
     */
    public String getName() {

        return m_name;
    }

    /**
     * Returns the result of the test, e.g. "Detected Apache Tomcat/4.1.24...".<p>
     * 
     * @return the result of the test
     */
    public String getResult() {

        return m_result;
    }

    /**
     * Returns true, if the conditions the test were fulfilled.<p>
     * 
     * @return true, if the conditions the test were fulfilled
     */
    public boolean isGreen() {

        return m_green;
    }

    /**
     * Returns true if the test found a violated condition.
     * It is assumed that it will be impossible to run OpenCms.<p>
     * 
     * @return true if the test found a violated a condition
     */
    public boolean isRed() {

        return m_red;
    }

    /**
     * Returns true if the test found a questionable condition.
     * It is possible that OpenCms will not run.<p>
     * 
     * @return true if the test found a questionable condition
     */
    public boolean isYellow() {

        return m_yellow;
    }

    /**
     * Sets if the conditions in the test were fulfilled.<p>
     */
    protected void setGreen() {

        m_green = true;
        m_red = false;
        m_yellow = false;
    }

    /**
     * Sets the help string what to do if a test failed.<p>
     * This string will be displayed in a help bubble.<p>
     * 
     * @param help the help string what to do if a test failed
     */
    protected void setHelp(String help) {

        m_help = help;
    }

    /**
     * Sets the description of the test, e.g. "Test xy failed due to...".<p>
     * 
     * @param info the description of the test 
     */
    protected void setInfo(String info) {

        m_info = info;
    }

    /**
     * Sets the name of the test, e.g. "Operating system test".<p>
     * 
     * @param name the name of the test
     */
    protected void setName(String name) {

        m_name = name;
    }

    /**
     * Sets if the test found a violated condition.<p>
     */
    protected void setRed() {

        m_green = false;
        m_red = true;
        m_yellow = false;
    }

    /**
     * Sets the result of the test, e.g. "Detected Apache Tomcat/4.1.24...".<p>
     * 
     * @param result the result of the test
     */
    protected void setResult(String result) {

        this.m_result = result;
    }

    /**
     * Sets if the test found a questionable condition.<p>
     */
    protected void setYellow() {

        m_green = false;
        m_red = false;
        m_yellow = true;
    }

}
