// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLManifest.java

package org.adl.parsers.dom;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Vector;
import org.adl.parsers.util.MessageClassification;
import org.adl.parsers.util.MessageHandler;
import org.adl.parsers.util.adlrules.manifest.IdentifierRules;
import org.adl.parsers.util.adlrules.manifest.VersionRules;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement, ADLResources, ADLOrganizations, ADLMetadata, 
//            ADLItem, ADLResource

public class ADLManifest extends ADLElement
    implements Serializable
{

    private String parentElem;
    private ADLMetadata adlMetadata;
    private ADLOrganizations orgs;
    private ADLResources rscs;
    private Vector manifestList;
    private String identifier;
    private String version;
    private String xmlBase;
    private int messageClass;
    private String messageLocation;

    public ADLManifest()
    {
        super("manifest");
        parentElem = new String("");
        rscs = new ADLResources();
        orgs = new ADLOrganizations();
        manifestList = new Vector();
        identifier = new String("");
        version = new String("");
        xmlBase = new String("");
        adlMetadata = null;
        messageClass = MessageClassification.MINIMUM;
        messageLocation = "ADLManifest::";
    }

    public boolean fillManifest(Node node)
    {
        String s = messageLocation + "fillManifest(Node)";
        if(DebugIndicator.ON)
            System.out.println("******  " + s + "  *********");
        boolean flag = true;
        Vector vector = new Vector();
        parentElem = node.getParentNode().getNodeName();
        identifier = getAttribute(node, "identifier");
        version = getAttribute(node, "version");
        xmlBase = getAttribute(node, "base");
        if(xmlBase.length() > 0)
        {
            xmlBase = xmlBase.replace('\\', '/');
            if(xmlBase.charAt(xmlBase.length() - 1) != '/')
                xmlBase = xmlBase + '/';
        }
        NodeList nodelist = node.getChildNodes();
        Node node1 = node;
        String s1 = new String("");
        int i = nodelist.getLength();
        for(int j = 0; j < i; j++)
        {
            Node node2 = nodelist.item(j);
            if(node2.getNodeType() == 1)
            {
                String s2 = node2.getLocalName();
                if(s2.equalsIgnoreCase("manifest"))
                {
                    ADLManifest adlmanifest = new ADLManifest();
                    flag = adlmanifest.fillManifest(node2) && flag;
                    vector.addAll(adlmanifest.getIDList());
                    manifestList.addElement(adlmanifest);
                }
            }
        }

        for(int k = 0; k < i; k++)
        {
            Node node3 = nodelist.item(k);
            if(node3.getNodeType() == 1)
            {
                String s3 = node3.getLocalName();
                if(s3.equalsIgnoreCase("resources"))
                {
                    flag = rscs.fillResources(node3) && flag;
                    vector.addAll(rscs.getResourceID());
                    rscs.setIdExists(vector);
                }
            }
        }

        for(int l = 0; l < i; l++)
        {
            Node node4 = nodelist.item(l);
            if(node4.getNodeType() == 1)
            {
                String s4 = node4.getLocalName();
                if(s4.equalsIgnoreCase("organizations"))
                    flag = orgs.fillOrgs(node4, vector) && flag;
                else
                if(s4.equalsIgnoreCase("metadata"))
                {
                    adlMetadata = new ADLMetadata();
                    flag = adlMetadata.fillMetadata(node4) && flag;
                }
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
        boolean flag = true;
        String s2 = messageLocation + "checkConformance()";
        if(DebugIndicator.ON)
            System.out.println("******  " + s2 + "  *********");
        if(parentElem.equalsIgnoreCase("manifest"))
        {
            min = 0;
            max = -1;
        } else
        {
            min = 1;
            max = 1;
        }
        spm = -1;
        int i = messageClass;
        int j = MessageType.INFO;
        String s3 = "";
        String s4 = "";
        s3 = "Testing element <" + elemName + "> for minimum conformance";
        messageHandler.addMessage(i, j, s3, s2, s4);
        flag = checkMultiplicity(i, s2);
        flag = checkIdentifier() && flag;
        flag = checkVersion() && flag;
        flag = orgs.checkConformance(s, s1) && flag;
        messageHandler.appendMessage(i, orgs.getMessage(i));
        flag = rscs.checkConformance(s1, xmlBase) && flag;
        messageHandler.appendMessage(i, rscs.getMessage(i));
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
        flag = checkMultiplicity(i, s4, s, i1, j1, identifier, true) && flag;
        int l1 = identifier.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s2 = "The smallest permitted maximum for the value of attribute \"" + s + "\" is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s2, s4, s5);
        } else
        {
            int l = MessageType.PASSED;
            String s3 = "The value, \"" + identifier + "\", of attribute \"" + s + "\" passed the " + "smallest permitted maximum test";
            messageHandler.addMessage(i, l, s3, s4, s5);
        }
        return flag;
    }

    public boolean checkVersion()
    {
        boolean flag = true;
        String s = new String("version");
        int i = messageClass;
        int j = MessageType.INFO;
        String s1 = "";
        String s4 = messageLocation + "checkVersion()";
        String s5 = "";
        int i1 = VersionRules.MIN;
        int j1 = VersionRules.MAX;
        int k1 = IdentifierRules.VALUESPM;
        s1 = "Testing attribute \"" + s + "\" for minimum " + "comformance";
        messageHandler.addMessage(i, j, s1, s4, s5);
        flag = checkMultiplicity(i, s4, s, i1, j1, version, true) && flag;
        int l1 = version.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s2 = "The smallest permitted maximum for the value of attribute \"" + s + "\" is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s2, s4, s5);
        } else
        {
            int l = MessageType.PASSED;
            String s3 = "The value, \"" + version + "\", of attribute \"" + s + "\" passed the " + "smallest permitted maximum test";
            messageHandler.addMessage(i, l, s3, s4, s5);
        }
        return flag;
    }

    public Vector getIDList()
    {
        String s = messageLocation + "getIDList()";
        if(DebugIndicator.ON)
            System.out.println("******  " + s + "  *********");
        Vector vector = new Vector();
        vector.add(identifier);
        int i = manifestList.size();
        for(int j = 0; j < i; j++)
            vector.addAll(((ADLManifest)manifestList.elementAt(j)).getIDList());

        if(DebugIndicator.ON)
            System.out.println("*** Exiting " + s + " ***");
        return vector;
    }

    public ADLOrganizations getOrganizations()
    {
        return orgs;
    }

    public Vector getItemList()
    {
        String s = messageLocation + "ADLManifest::getItemList()";
        if(DebugIndicator.ON)
            System.out.println("*******    " + s + "  **************");
        Vector vector = new Vector();
        vector = orgs.getItemList();
        if(DebugIndicator.ON)
        {
            System.out.println("*******  " + s + "  **************");
            System.out.println("*******  Vector size is: " + vector.size() + "  **************");
        }
        return vector;
    }

    public Vector getMetadata()
    {
        Vector vector = new Vector();
        vector.add(adlMetadata);
        vector.addAll(rscs.getMetadata());
        vector.addAll(orgs.getMetadata());
        int i = manifestList.size();
        for(int j = 0; j < i; j++)
            vector.addAll(((ADLManifest)manifestList.elementAt(j)).getMetadata());

        return vector;
    }

    public HashSet getLaunchLines()
    {
        HashSet hashset = new HashSet();
        Vector vector = orgs.getItemList();
        String s = new String();
        String s2 = new String();
        String s4 = new String();
        Vector vector1 = rscs.getResourceList();
        int i = vector.size();
        if(i > 0)
        {
            for(int j = 0; j < i; j++)
            {
                ADLItem adlitem = (ADLItem)vector.elementAt(j);
                String s1 = adlitem.getIdentifierref();
                String s3 = adlitem.getParameterString();
                if(!s1.equals(""))
                {
                    String s5 = lookupResourceHref(s1);
                    if(!s5.equals(""))
                    {
                        s5 = s5 + s3;
                        hashset.add(s5);
                    }
                }
            }

        } else
        {
            int k = vector1.size();
            for(int l = 0; l < k; l++)
            {
                ADLResource adlresource = (ADLResource)vector1.elementAt(l);
                if(adlresource.getADLCPScormtype().equalsIgnoreCase("sco"))
                {
                    String s6 = adlresource.getFullLaunchLocation();
                    hashset.add(s6);
                }
            }

        }
        if(DebugIndicator.ON)
            System.out.println("RETURNING size" + hashset.size());
        return hashset;
    }

    public String lookupResourceHref(String s)
    {
        Vector vector = rscs.getResourceList();
        String s1 = new String();
        String s3 = new String();
        int i = vector.size();
        for(int j = 0; j < i; j++)
        {
            ADLResource adlresource = (ADLResource)vector.elementAt(j);
            String s2 = adlresource.getIdentifier();
            if(!s2.equals(s) || !adlresource.getADLCPScormtype().equalsIgnoreCase("sco"))
                continue;
            s3 = adlresource.getFullLaunchLocation();
            break;
        }

        return s3;
    }
}
