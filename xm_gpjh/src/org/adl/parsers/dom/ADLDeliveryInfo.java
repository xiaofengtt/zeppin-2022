// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLDeliveryInfo.java

package org.adl.parsers.dom;

import java.io.PrintStream;
import java.io.Serializable;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement, ADLPreCondition, ADLPostCondition, ADLAggregationStatus

public class ADLDeliveryInfo extends ADLElement
    implements Serializable
{

    private String sequencingMode;
    private String navigation;
    private String nextRule;
    private String exitCondition;
    private String purpose;
    private String ordering;
    private String approach;
    private String visibleWhen;
    private String enabledWhen;
    private String identifier;
    private String number;
    private String limitcondition;
    private String maxAttempts;
    private String maxSessions;
    private String maxAttemptDuration;
    private String maxTotalDuration;
    private ADLPreCondition precondition;
    private ADLPostCondition postcondition;
    private ADLAggregationStatus aggStatus;

    public ADLDeliveryInfo()
    {
        super("deliveryinfo");
        sequencingMode = new String();
        navigation = new String();
        nextRule = new String();
        exitCondition = new String();
        purpose = new String();
        ordering = new String();
        approach = new String();
        visibleWhen = new String();
        enabledWhen = new String();
        identifier = new String();
        number = new String();
        limitcondition = new String();
        maxAttempts = new String();
        maxSessions = new String();
        maxAttemptDuration = new String();
        maxTotalDuration = new String();
        precondition = null;
        postcondition = null;
        aggStatus = null;
    }

    public String getOrdering()
    {
        return ordering;
    }

    public String getLimitCondition()
    {
        return limitcondition;
    }

    public String getSequenceMode()
    {
        return sequencingMode;
    }

    public String getNavigation()
    {
        return navigation;
    }

    public String getNextRule()
    {
        return nextRule;
    }

    public String getExitCondition()
    {
        return exitCondition;
    }

    public String getPurpose()
    {
        return purpose;
    }

    public String getApproach()
    {
        return approach;
    }

    public String getVisibleWhen()
    {
        return visibleWhen;
    }

    public String getEnabledWhen()
    {
        return enabledWhen;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public String getNumber()
    {
        return number;
    }

    public String getMaxAttempts()
    {
        return maxAttempts;
    }

    public String getMaxSessions()
    {
        return maxSessions;
    }

    public String getMaxAttemptDuration()
    {
        return maxAttemptDuration;
    }

    public String getMaxTotalDuration()
    {
        return maxTotalDuration;
    }

    public void setOrdering(String s)
    {
        ordering = s;
    }

    public void setLimitCondition(String s)
    {
        limitcondition = s;
    }

    public void setSequenceMode(String s)
    {
        sequencingMode = s;
    }

    public void setNavigation(String s)
    {
        navigation = s;
    }

    public void setNextRule(String s)
    {
        nextRule = s;
    }

    public void setExitCondition(String s)
    {
        exitCondition = s;
    }

    public void setPurpose(String s)
    {
        purpose = s;
    }

    public void setApproach(String s)
    {
        approach = s;
    }

    public void setVisibleWhen(String s)
    {
        visibleWhen = s;
    }

    public void setEnabledWhen(String s)
    {
        enabledWhen = s;
    }

    public void setIdentifier(String s)
    {
        identifier = s;
    }

    public void setNumber(String s)
    {
        number = s;
    }

    public void setMaxAttempts(String s)
    {
        maxAttempts = s;
    }

    public void setMaxSessions(String s)
    {
        maxSessions = s;
    }

    public void setMaxAttemptDuration(String s)
    {
        maxAttemptDuration = s;
    }

    public void setMaxTotalDuration(String s)
    {
        maxTotalDuration = s;
    }

    public boolean fillDeliveryInfo(Node node)
    {
        boolean flag = true;
        if(DebugIndicator.ON)
            System.out.println("******  ADLDeliveryInfo:fillDeliveryInfo  *********");
        String s = getAttribute(node, "sequencingmode");
        if(s != null)
            sequencingMode = s;
        String s1 = getAttribute(node, "navigation");
        if(s1 != null)
            navigation = s1;
        String s2 = getAttribute(node, "nextrule");
        if(s2 != null)
            nextRule = s2;
        String s3 = getAttribute(node, "exitcondition");
        if(s3 != null)
            exitCondition = s3;
        String s4 = getAttribute(node, "purpose");
        if(s4 != null)
            purpose = s4;
        String s5 = getSubElement(node, "ordering");
        if(s5 != null)
            ordering = s5;
        NodeList nodelist = node.getChildNodes();
        if(DebugIndicator.ON)
        {
            System.out.println("*** NODE: " + node.getNodeName());
            System.out.println("*** Children - " + nodelist.getLength() + " ***");
        }
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
                if(node1.getLocalName().equalsIgnoreCase("ordering"))
                {
                    if(DebugIndicator.ON)
                        System.out.println("*** Found Current Node: " + node1.getNodeName());
                    flag = flag && fillOrdering(node1);
                } else
                if(node1.getLocalName().equalsIgnoreCase("precondition"))
                {
                    if(DebugIndicator.ON)
                        System.out.println("*** Found Current Node: " + node1.getNodeName());
                    precondition = new ADLPreCondition();
                    flag = flag && precondition.fillPreCondition(node1);
                } else
                if(node1.getLocalName().equalsIgnoreCase("postcondition"))
                {
                    if(DebugIndicator.ON)
                        System.out.println("*** Found Current Node: " + node1.getNodeName());
                    postcondition = new ADLPostCondition();
                    flag = flag && postcondition.fillPostCondition(node1);
                } else
                if(node1.getLocalName().equalsIgnoreCase("aggregationstatus"))
                {
                    if(DebugIndicator.ON)
                        System.out.println("*** Found Current Node: " + node1.getNodeName());
                    aggStatus = new ADLAggregationStatus();
                    flag = flag && aggStatus.fillAggregationStatus(node1);
                } else
                if(node1.getLocalName().equalsIgnoreCase("limitcondition"))
                {
                    if(DebugIndicator.ON)
                        System.out.println("*** Found Current Node: " + node1.getNodeName());
                    String s6 = getText(node1);
                    if(s6 != null)
                        limitcondition = s6;
                    String s7 = getAttribute(node1, "maxattempts");
                    if(s7 != null)
                        maxAttempts = s7;
                    String s8 = getAttribute(node1, "maxsessions");
                    if(s8 != null)
                        maxSessions = s8;
                    String s9 = getAttribute(node1, "maxattemptduration");
                    if(s9 != null)
                        maxAttemptDuration = s9;
                    String s10 = getAttribute(node1, "maxtotalduration");
                    if(s10 != null)
                        maxTotalDuration = s10;
                }
        }

        return true;
    }

    public boolean fillOrdering(Node node)
    {
        boolean flag = true;
        if(DebugIndicator.ON)
            System.out.println("******  ADLDeliveryInfo:fillOrdering  *********");
        String s = getAttribute(node, "approach");
        if(s != null)
            approach = s;
        String s1 = getAttribute(node, "visiblewhen");
        if(s1 != null)
            visibleWhen = s1;
        String s2 = getAttribute(node, "enabledwhen");
        if(s2 != null)
            enabledWhen = s2;
        String s3 = getAttribute(node, "number");
        if(s3 != null)
            number = s3;
        String s4 = getAttribute(node, "identifier");
        if(s4 != null)
            identifier = s4;
        return flag;
    }
}
