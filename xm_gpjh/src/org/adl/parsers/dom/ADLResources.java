// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLResources.java

package org.adl.parsers.dom;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Vector;
import org.adl.parsers.util.MessageClassification;
import org.adl.parsers.util.MessageHandler;
import org.adl.parsers.util.adlrules.manifest.ResourcesRules;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement, ADLResource

public class ADLResources extends ADLElement
    implements Serializable
{

    private Vector resourceList;
    private String xmlBase;
    private int messageClass;
    private String messageLocation;

    public ADLResources()
    {
        super("resources");
        resourceList = new Vector();
        xmlBase = new String("");
        messageClass = MessageClassification.MINIMUM;
        messageLocation = "ADLResources::";
    }

    public boolean fillResources(Node node)
    {
        String s = messageLocation + "fillResources(Node)";
        if(DebugIndicator.ON)
            System.out.println("******  " + s + "  *********");
        boolean flag = true;
        xmlBase = getAttribute(node, "base");
        if(xmlBase.length() > 0)
        {
            xmlBase = xmlBase.replace('\\', '/');
            if(xmlBase.charAt(xmlBase.length() - 1) != '/')
                xmlBase = xmlBase + '/';
        }
        multiplicity = getMultiplicityUsed(node.getParentNode(), elemName);
        NodeList nodelist = node.getChildNodes();
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1 && node1.getLocalName().equalsIgnoreCase("resource"))
            {
                ADLResource adlresource = new ADLResource();
                flag = adlresource.fillResource(node1);
                resourceList.addElement(adlresource);
            }
        }

        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting " + s + " ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }

    public boolean checkConformance(String s, String s1)
    {
        if(DebugIndicator.ON)
        {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("###   resources");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        }
        String s2 = messageLocation + "checkConformance(String)";
        if(DebugIndicator.ON)
            System.out.println("******  " + s2 + "  *********");
        boolean flag = true;
        min = ResourcesRules.MIN;
        max = ResourcesRules.MAX;
        spm = ResourcesRules.SPM;
        int i = messageClass;
        int j = MessageType.INFO;
        String s3 = "";
        String s4 = "";
        s3 = "Testing element <" + elemName + "> for minimum conformance";
        messageHandler.addMessage(i, j, s3, s2, s4);
        flag = checkMultiplicity(i, s2);
        String s5 = new String();
        s5 = s1 + xmlBase;
        int k = resourceList.size();
        for(int l = 0; l < k; l++)
        {
            flag = ((ADLResource)resourceList.elementAt(l)).checkConformance(s, s5) && flag;
            messageHandler.appendMessage(i, ((ADLResource)resourceList.elementAt(l)).getMessage(i));
        }

        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting " + s2 + " ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }

    public boolean setIdExists(Vector vector)
    {
        boolean flag = true;
        int i = resourceList.size();
        for(int j = 0; j < i; j++)
            flag = ((ADLResource)resourceList.elementAt(j)).setIdExists(vector) && flag;

        return flag;
    }

    public Vector getResourceID()
    {
        String s = messageLocation + "getResourceID()";
        if(DebugIndicator.ON)
            System.out.println("******  " + s + "  *********");
        int i = resourceList.size();
        Vector vector = new Vector(i);
        for(int j = 0; j < i; j++)
            vector.add(((ADLResource)resourceList.elementAt(j)).getIdentifier());

        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting " + s + " ***");
            System.out.println("*** Returning: " + vector);
        }
        return vector;
    }

    public Vector getResourceList()
    {
        return resourceList;
    }

    public Vector getLaunchLines()
    {
        Vector vector = new Vector();
        int i = resourceList.size();
        for(int j = 0; j < i; j++)
        {
            ADLResource adlresource = (ADLResource)resourceList.elementAt(j);
            String s = adlresource.getFullLaunchLocation();
            if(adlresource.getADLCPScormtype().equalsIgnoreCase("sco"))
                vector.add(s);
        }

        return vector;
    }

    public Vector getMetadata()
    {
        String s = messageLocation + "getMetadata()";
        if(DebugIndicator.ON)
            System.out.println("******  " + s + "  *********");
        Vector vector = new Vector();
        int i = resourceList.size();
        for(int j = 0; j < i; j++)
            vector.addAll(((ADLResource)resourceList.elementAt(j)).getMetadata());

        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting " + s + " ***");
            System.out.println("*** Returning: " + vector);
        }
        return vector;
    }
}
