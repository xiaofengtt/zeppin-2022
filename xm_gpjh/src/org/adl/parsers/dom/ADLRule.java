// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLRule.java

package org.adl.parsers.dom;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Vector;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement, ADLResult

public class ADLRule extends ADLElement
    implements Serializable
{

    private String condition;
    private String identifier;
    private Vector resultList;

    public ADLRule()
    {
        super("rule");
        condition = new String("");
        identifier = new String("");
        resultList = new Vector();
    }

    public boolean fillRule(Node node)
    {
        boolean flag = true;
        if(DebugIndicator.ON)
            System.out.println("******  ADLRule:fillRule()  *********");
        String s = getAttribute(node, "condition");
        if(s != null)
            condition = s;
        String s1 = getAttribute(node, "identifier");
        if(s1 != null)
            identifier = s1;
        NodeList nodelist = node.getChildNodes();
        if(DebugIndicator.ON)
        {
            System.out.println("*** NODE: " + node.getNodeName());
            System.out.println("*** Children - " + nodelist.getLength() + " ***");
        }
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1 && node1.getLocalName().equalsIgnoreCase("results"))
            {
                if(DebugIndicator.ON)
                    System.out.println("*** Found Current Node: " + node1.getNodeName());
                fillResults(node1);
            }
        }

        return flag;
    }

    public boolean fillResults(Node node)
    {
        boolean flag = true;
        if(DebugIndicator.ON)
            System.out.println("******  ADLRule:fillResults()  *********");
        NodeList nodelist = node.getChildNodes();
        if(DebugIndicator.ON)
        {
            System.out.println("*** NODE: " + node.getNodeName());
            System.out.println("*** Children - " + nodelist.getLength() + " ***");
        }
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1 && node1.getLocalName().equalsIgnoreCase("result"))
            {
                if(DebugIndicator.ON)
                    System.out.println("*** Found Current Node: " + node1.getNodeName());
                ADLResult adlresult = new ADLResult();
                adlresult.fillResult(node1);
                resultList.addElement(adlresult);
            }
        }

        return flag;
    }
}
