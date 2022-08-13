// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLOrganization.java

package org.adl.parsers.dom;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Vector;
import org.adl.parsers.util.MessageClassification;
import org.adl.parsers.util.MessageHandler;
import org.adl.parsers.util.adlrules.manifest.*;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement, ADLItem, ADLMetadata, ADLSequence

public class ADLOrganization extends ADLElement
    implements Serializable
{

    private Vector itemList;
    private ADLMetadata adlMetadata;
    private ADLSequence adlSequence;
    private String titleElem;
    private String identifierAttr;
    private String structureAttr;
    private int messageClass;
    private String messageLocation;

    public ADLOrganization()
    {
        super("organization");
        itemList = new Vector();
        titleElem = new String();
        identifierAttr = new String();
        structureAttr = new String();
        adlMetadata = null;
        adlSequence = null;
        messageClass = MessageClassification.MINIMUM;
        messageLocation = "ADLOrganization::";
    }

    public boolean fillOrg(Node node, Vector vector)
    {
        boolean flag = true;
        byte byte0 = 2;
        multiplicity = getMultiplicityUsed(node.getParentNode(), elemName);
        if(DebugIndicator.ON)
            System.out.println("******  ADLOrganization:fillOrg()  *********");
        identifierAttr = getAttribute(node, "identifier");
        structureAttr = getAttribute(node, "structure");
        NodeList nodelist = node.getChildNodes();
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
                if(node1.getLocalName().equalsIgnoreCase("item"))
                {
                    ADLItem adlitem = new ADLItem();
                    String s = getAttribute(node1, "identifier");
                    String s1 = getAttribute(node1, "identifierref");
                    String s2 = getAttribute(node1, "parameters");
                    String s3 = getSubElement(node1, "title");
                    String s4 = getSubElement(node1, "prerequisites");
                    String s5 = getSubElement(node1, "timelimitaction");
                    String s6 = getSubElement(node1, "maxtimeallowed");
                    String s7 = getSubElement(node1, "datafromlms");
                    String s8 = getSubElement(node1, "masteryscore");
                    adlitem.setIdentifier(s);
                    if(!s1.equals(""))
                        adlitem.setIdentifierref(s1);
                    else
                        adlitem.setIdentifierref("");
                    if(!s2.equals(""))
                        adlitem.setParameterString(s2);
                    else
                        adlitem.setParameterString("");
                    adlitem.setTitle(s3);
                    adlitem.setPrerequisites(s4);
                    adlitem.setTimeLimitAction(s5);
                    adlitem.setMaxTimeAllowed(s6);
                    adlitem.setDataFromLMS(s7);
                    adlitem.setMasteryScore(s8);
                    adlitem.setLevel(0);
                    flag = adlitem.fillItem(node1, vector);
                    itemList.addElement(adlitem);
                } else
                if(node1.getLocalName().equalsIgnoreCase("title"))
                    titleElem = getText(node1);
                else
                if(node1.getLocalName().equalsIgnoreCase("metadata"))
                {
                    adlMetadata = new ADLMetadata();
                    flag = adlMetadata.fillMetadata(node1) && flag;
                } else
                if(node1.getLocalName().equalsIgnoreCase("sequencing"))
                {
                    adlSequence = new ADLSequence();
                    flag = adlSequence.fillSequence(node1) && flag;
                }
        }

        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting ADLOrganization::fillOrg() ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }

    public Vector getItemList()
    {
        if(DebugIndicator.ON)
            System.out.println("*******    ADLOrganization::getItemList()  **************");
        Vector vector = new Vector();
        Vector vector1 = new Vector();
        for(int i = 0; i < itemList.size(); i++)
        {
            ADLItem adlitem = (ADLItem)itemList.elementAt(i);
            vector1.addElement(adlitem);
            Vector vector2 = adlitem.getItemList();
            vector1.addAll(vector2);
        }

        vector.addAll(vector1);
        if(DebugIndicator.ON)
        {
            System.out.println("*******    EXITING  ADLOrganization::getItemList()  **************");
            System.out.println("*******  Vector size is: " + vector.size() + "  **************");
        }
        return vector;
    }

    public boolean checkConformance(String s, String s1)
    {
        boolean flag = true;
        if(s.equalsIgnoreCase("aggregation"))
        {
            min = OrganizationRules.AMIN;
            max = OrganizationRules.AMAX;
        } else
        {
            min = OrganizationRules.RMIN;
            max = OrganizationRules.RMAX;
        }
        spm = OrganizationRules.SPM;
        int j = messageClass;
        int k = MessageType.INFO;
        String s2 = "";
        String s3 = messageLocation + "checkConformance()";
        String s4 = "";
        s2 = "Testing element <" + elemName + "> for minimum conformance";
        messageHandler.addMessage(j, k, s2, s3, s4);
        flag = checkMultiplicity(j, s3);
        flag = checkIdentifier() && flag;
        flag = checkStructure(s) && flag;
        flag = checkTitle(s) && flag;
        int i = itemList.size();
        for(int l = 0; l < i; l++)
        {
            flag = ((ADLItem)itemList.elementAt(l)).checkConformance(s, s1) && flag;
            messageHandler.appendMessage(j, ((ADLItem)itemList.elementAt(l)).getMessage(j));
        }

        if(adlMetadata != null)
        {
            flag = adlMetadata.checkConformance(s1) && flag;
            messageHandler.appendMessage(j, adlMetadata.getMessage(j));
        }
        return flag;
    }

    public boolean checkIdentifier()
    {
        boolean flag = true;
        String s = new String("identifier");
        int i = messageClass;
        int j = MessageType.INFO;
        String s1 = "";
        String s4 = messageLocation + "checkIdentifier()";
        String s5 = "";
        int i1 = IdentifierRules.MIN;
        int j1 = IdentifierRules.MAX;
        int k1 = IdentifierRules.VALUESPM;
        s1 = "Testing attribute \"" + s + "\" for minimum " + "comformance";
        messageHandler.addMessage(i, j, s1, s4, s5);
        flag = checkMultiplicity(i, s4, s, i1, j1, identifierAttr, true) && flag;
        int l1 = identifierAttr.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s2 = "The smallest permitted maximum for the value of attribute \"" + s + "\" is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s2, s4, s5);
        } else
        {
            int l = MessageType.PASSED;
            String s3 = "The value, \"" + identifierAttr + "\", of attribute \"" + s + "\" passed the " + "smallest permitted maximum test";
            messageHandler.addMessage(i, l, s3, s4, s5);
        }
        return flag;
    }

    public boolean checkStructure(String s)
    {
        boolean flag = true;
        String s1 = new String("structure");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s5 = messageLocation + "checkStructure()";
        String s6 = "";
        int i1 = -1;
        int j1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            i1 = StructureRules.AMIN;
            j1 = StructureRules.AMAX;
        } else
        {
            i1 = StructureRules.RMIN;
            j1 = StructureRules.RMAX;
        }
        int k1 = StructureRules.VALUESPM;
        s2 = "Testing attribute \"" + s1 + "\" for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s5, s6);
        flag = checkMultiplicity(i, s5, s1, i1, j1, structureAttr, true) && flag;
        int l1 = structureAttr.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s3 = "The smallest permitted maximum for the value of attribute \"" + s1 + "\" is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s3, s5, s6);
        } else
        {
            int l = MessageType.PASSED;
            String s4 = "The value, \"" + structureAttr + "\", of attribute \"" + s1 + "\" passed the " + "smallest permitted maximum test";
            messageHandler.addMessage(i, l, s4, s5, s6);
        }
        return flag;
    }

    public boolean checkTitle(String s)
    {
        boolean flag = true;
        String s1 = new String("title");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s5 = messageLocation + "checkTitle()";
        String s6 = "";
        int i1 = -1;
        int j1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            i1 = TitleRules.AMIN;
            j1 = TitleRules.AMAX;
        } else
        {
            i1 = TitleRules.RMIN;
            j1 = TitleRules.RMAX;
        }
        int k1 = TitleRules.VALUESPM;
        s2 = "Testing element <" + s1 + "> for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s5, s6);
        flag = checkMultiplicity(i, s5, s1, i1, j1, titleElem, false) && flag;
        int l1 = titleElem.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s3 = "The smallest permitted maximum for the value of element <" + s1 + "> is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s3, s5, s6);
        } else
        {
            int l = MessageType.PASSED;
            String s4 = "The value, \"" + titleElem + "\", of element <" + s1 + "> passed the " + "smallest permitted maximum test";
            messageHandler.addMessage(i, l, s4, s5, s6);
        }
        return flag;
    }

    public ADLSequence getSequence()
    {
        ADLSequence adlsequence = new ADLSequence();
        ADLSequence adlsequence1 = new ADLSequence();
        if(adlSequence == null)
            adlsequence = adlsequence1;
        else
            adlsequence = adlSequence;
        return adlsequence;
    }

    public String getIdentifier()
    {
        return identifierAttr;
    }

    public Vector getMetadata()
    {
        Vector vector = new Vector();
        vector.add(adlMetadata);
        int i = itemList.size();
        for(int j = 0; j < i; j++)
            vector.addAll(((ADLItem)itemList.elementAt(j)).getMetadata());

        return vector;
    }
}
