// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLSequence.java

package org.adl.parsers.dom;

import java.io.PrintStream;
import java.io.Serializable;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement, ADLDeliveryInfo

public class ADLSequence extends ADLElement
    implements Serializable
{

    private ADLDeliveryInfo deliveryInfo;
    private String isTracked;
    private String isRolledUp;
    private String rollUpInfo;
    private String rollUpMastery;
    private String rollUpTotalTime;
    private String rollUpScore;
    private String control;

    public ADLSequence()
    {
        super("sequencing");
        deliveryInfo = null;
        rollUpInfo = new String();
        isTracked = new String();
        isRolledUp = new String();
        rollUpMastery = new String();
        rollUpTotalTime = new String();
        rollUpScore = new String();
        control = new String();
    }

    public String getControl()
    {
        return control;
    }

    public String getRollUpInfo()
    {
        return rollUpInfo;
    }

    public String getIsTracked()
    {
        return isTracked;
    }

    public String getIsRolledUp()
    {
        return isRolledUp;
    }

    public String getRollUpMastery()
    {
        return rollUpMastery;
    }

    public String getRollUpTotalTime()
    {
        return rollUpTotalTime;
    }

    public String getRollUpScore()
    {
        return rollUpScore;
    }

    public void setControl(String s)
    {
        control = s;
    }

    public void setIsTracked(String s)
    {
        isTracked = s;
    }

    public void setRollUpInfo(String s)
    {
        rollUpInfo = s;
    }

    public void setIsRolledUp(String s)
    {
        isRolledUp = s;
    }

    public void setRollUpMastery(String s)
    {
        rollUpMastery = s;
    }

    public void setRollUpTotalTime(String s)
    {
        rollUpTotalTime = s;
    }

    public void setRollUpScore(String s)
    {
        rollUpScore = s;
    }

    public boolean fillSequence(Node node)
    {
        boolean flag = true;
        if(DebugIndicator.ON)
            System.out.println("******  ADLSequence:fillSequence  *********");
        String s = getAttribute(node, "control");
        if(s != null)
        {
            System.out.println("Found control attribute");
            control = s;
        }
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
                if(node1.getLocalName().equalsIgnoreCase("deliveryinfo"))
                {
                    if(DebugIndicator.ON)
                        System.out.println("*** Found Current Node: " + node1.getNodeName());
                } else
                if(node1.getLocalName().equalsIgnoreCase("rollupinfo"))
                {
                    if(DebugIndicator.ON)
                        System.out.println("*** Found Current Node: " + node1.getNodeName());
                    String s1 = getText(node1);
                    if(s1 != null)
                        rollUpInfo = s1;
                    String s2 = getAttribute(node1, "istracked");
                    if(s2 != null)
                        isTracked = s2;
                    String s3 = getAttribute(node1, "isrolledup");
                    if(s3 != null)
                        isRolledUp = s3;
                    String s4 = getAttribute(node1, "rollupmastery");
                    if(s4 != null)
                        rollUpMastery = s4;
                    String s5 = getAttribute(node1, "rolluptotaltime");
                    if(s5 != null)
                        rollUpTotalTime = s5;
                    String s6 = getAttribute(node1, "rollupscore");
                    if(s2 != null)
                        rollUpScore = s6;
                }
        }

        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting Item::fillSequence() ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }
}
