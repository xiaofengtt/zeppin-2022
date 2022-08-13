// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLOrganizations.java

package org.adl.parsers.dom;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Vector;
import org.adl.parsers.util.MessageClassification;
import org.adl.parsers.util.MessageHandler;
import org.adl.parsers.util.adlrules.manifest.DefaultRules;
import org.adl.parsers.util.adlrules.manifest.OrganizationsRules;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement, ADLOrganization

public class ADLOrganizations extends ADLElement
    implements Serializable
{

    private Vector organizationList;
    private String defaultAttr;
    private int messageClass;
    private String messageLocation;

    public ADLOrganizations()
    {
        super("organizations");
        organizationList = new Vector();
        defaultAttr = new String();
        messageClass = MessageClassification.MINIMUM;
        messageLocation = "ADLOrganizations::";
    }

    public boolean fillOrgs(Node node, Vector vector)
    {
        boolean flag = true;
        boolean flag1 = true;
        multiplicity = getMultiplicityUsed(node.getParentNode(), elemName);
        if(DebugIndicator.ON)
            System.out.println("******  ADLOrganizations:fillOrgs  *********");
        defaultAttr = getAttribute(node, "default");
        NodeList nodelist = node.getChildNodes();
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1 && node1.getLocalName().equalsIgnoreCase("organization"))
            {
                ADLOrganization adlorganization = new ADLOrganization();
                flag = adlorganization.fillOrg(node1, vector);
                organizationList.addElement(adlorganization);
            }
        }

        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting ADLOrganizations::fillOrgs() ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }

    public ADLOrganization getFirstOrg()
    {
        return (ADLOrganization)organizationList.elementAt(0);
    }

    public Vector getItemList()
    {
        Vector vector = new Vector();
        for(int i = 0; i < organizationList.size(); i++)
        {
            ADLOrganization adlorganization = (ADLOrganization)organizationList.elementAt(i);
            vector.addAll(adlorganization.getItemList());
        }

        return vector;
    }

    public boolean checkConformance(String s, String s1)
    {
        if(DebugIndicator.ON)
        {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("###   organizations");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        }
        boolean flag = true;
        min = OrganizationsRules.MIN;
        max = OrganizationsRules.MAX;
        spm = OrganizationsRules.SPM;
        int i = organizationList.size();
        int j = messageClass;
        int k = MessageType.INFO;
        String s2 = "";
        String s5 = messageLocation + "checkConformance()";
        String s6 = "";
        s2 = "Testing element <" + elemName + "> for minimum conformance";
        messageHandler.addMessage(j, k, s2, s5, s6);
        flag = checkMultiplicity(j, s5);
        flag = checkDefault(s) && flag;
        if(DebugIndicator.ON)
        {
            System.out.println("");
            System.out.println("Application Profile = " + s);
            System.out.println("numOrg = " + i);
        }
        if(s.equalsIgnoreCase("aggregation") && i < 1)
        {
            int l = MessageType.FAILED;
            String s3 = "Element <organization> was not found.";
            messageHandler.addMessage(j, l, s3, s5, s6);
            flag = false;
        } else
        if(s.equalsIgnoreCase("resource") && i > 0)
        {
            int i1 = MessageType.FAILED;
            String s4 = "Element <organizations> is not empty.";
            messageHandler.addMessage(j, i1, s4, s5, s6);
            flag = false;
        } else
        {
            for(int j1 = 0; j1 < i; j1++)
            {
                flag = ((ADLOrganization)organizationList.elementAt(j1)).checkConformance(s, s1) && flag;
                messageHandler.appendMessage(j, ((ADLOrganization)organizationList.elementAt(j1)).getMessage(j));
            }

        }
        return flag;
    }

    public boolean checkDefault(String s)
    {
        boolean flag = true;
        String s1 = new String("default");
        int i = organizationList.size();
        int j = messageClass;
        int k = MessageType.INFO;
        String s2 = "";
        String s6 = messageLocation + "checkDefault()";
        String s7 = "";
        int k1 = -1;
        int l1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            k1 = DefaultRules.AMIN;
            l1 = DefaultRules.AMAX;
        } else
        {
            k1 = DefaultRules.RMIN;
            l1 = DefaultRules.RMAX;
        }
        int i2 = organizationList.size();
        Vector vector = new Vector();
        for(int j2 = 0; j2 < i2; j2++)
            vector.add(((ADLOrganization)organizationList.elementAt(j2)).getIdentifier());

        s2 = "Testing attribute \"" + s1 + "\" for minimum " + "comformance";
        messageHandler.addMessage(j, k, s2, s6, s7);
        if(DebugIndicator.ON)
        {
            System.out.println("");
            System.out.println("defaultAttr = " + defaultAttr);
        }
        flag = checkMultiplicity(j, s6, s1, k1, l1, defaultAttr, true) && flag;
        if(s.equalsIgnoreCase("aggregation") && !defaultAttr.equals(""))
        {
            boolean flag1 = false;
            int k2 = vector.size();
            String s8 = "";
            for(int l2 = 0; l2 < k2; l2++)
            {
                String s9 = (String)vector.elementAt(l2);
                if(!s9.equalsIgnoreCase(defaultAttr))
                    continue;
                flag1 = true;
                break;
            }

            if(flag1)
            {
                int l = MessageType.PASSED;
                String s3 = "Organization identifier \"" + defaultAttr + "\" has " + "been found";
                messageHandler.addMessage(j, l, s3, s6, s7);
            } else
            {
                int i1 = MessageType.FAILED;
                String s4 = "Organization identifier \"" + defaultAttr + "\" could " + "not be found and failed the referenced identifier test";
                messageHandler.addMessage(j, i1, s4, s6, s7);
                flag = false;
            }
        } else
        if(s.equalsIgnoreCase("aggregation") && i2 > 1)
        {
            int j1 = MessageType.WARNING;
            String s5 = "Multiple <organization> elements have been detected and it is best practice to use and set the default attribute of the <organizations> element to reference an identifier attribute of one of the <organization> elements";
            messageHandler.addMessage(j, j1, s5, s6, s7);
        }
        return flag;
    }

    public Vector getMetadata()
    {
        Vector vector = new Vector();
        int i = organizationList.size();
        for(int j = 0; j < i; j++)
            vector.addAll(((ADLOrganization)organizationList.elementAt(j)).getMetadata());

        return vector;
    }
}
