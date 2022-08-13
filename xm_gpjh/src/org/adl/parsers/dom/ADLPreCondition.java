// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLPreCondition.java

package org.adl.parsers.dom;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Vector;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement, ADLRule

public class ADLPreCondition extends ADLElement
    implements Serializable
{

    private String evaluation;
    private Vector ruleList;

    public ADLPreCondition()
    {
        super("precondition");
        evaluation = new String();
        ruleList = new Vector();
    }

    public boolean fillPreCondition(Node node)
    {
        boolean flag = true;
        if(DebugIndicator.ON)
            System.out.println("******  ADLPreCondition:fillPreCondition()  *********");
        String s = getAttribute(node, "eval");
        if(s != null)
            evaluation = s;
        NodeList nodelist = node.getChildNodes();
        if(DebugIndicator.ON)
        {
            System.out.println("*** NODE: " + node.getNodeName());
            System.out.println("*** Children - " + nodelist.getLength() + " ***");
        }
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1 && node1.getLocalName().equalsIgnoreCase("rule"))
            {
                if(DebugIndicator.ON)
                    System.out.println("*** Found Current Node: " + node1.getNodeName());
                ADLRule adlrule = new ADLRule();
                adlrule.fillRule(node1);
                ruleList.addElement(adlrule);
            }
        }

        return flag;
    }
}
