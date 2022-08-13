// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLMetadata.java

package org.adl.parsers.dom;

import java.io.*;
import java.net.*;
import java.util.Vector;
import org.adl.parsers.util.MessageClassification;
import org.adl.parsers.util.MessageHandler;
import org.adl.parsers.util.adlrules.manifest.*;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement

public class ADLMetadata extends ADLElement
    implements Serializable
{

    private Node metadataRoot;
    private String parentElementName;
    private String parentElementID;
    private String metadataType;
    private String schema;
    private String schemaversion;
    private String adlcpLocation;
    private int schemaMult;
    private int schemaversionMult;
    private int adlcpLocationMult;
    private int messageClass;
    private String messageLocation;

    public ADLMetadata()
    {
        super("metadata");
        metadataRoot = null;
        parentElementName = "";
        parentElementID = "";
        metadataType = "";
        schema = "";
        schemaversion = "";
        adlcpLocation = "";
        schemaMult = -1;
        schemaversionMult = -1;
        adlcpLocationMult = -1;
        messageClass = MessageClassification.MINIMUM;
        messageLocation = "ADLMetadata::";
    }

    public boolean fillMetadata(Node node)
    {
        boolean flag = true;
        URLDecoder urldecoder = new URLDecoder();
        multiplicity = getMultiplicityUsed(node.getParentNode(), elemName);
        Node node1 = node.getParentNode();
        parentElementName = node1.getLocalName();
        if(parentElementName.equalsIgnoreCase("manifest"))
            return flag;
        if(parentElementName.equalsIgnoreCase("manifest") || parentElementName.equalsIgnoreCase("organization") || parentElementName.equalsIgnoreCase("item") || parentElementName.equalsIgnoreCase("resource"))
            parentElementID = getAttribute(node1, "identifier");
        else
            parentElementID = getAttribute(node1, "href");
        if(parentElementName.equalsIgnoreCase("organization") || parentElementName.equalsIgnoreCase("item"))
            metadataType = "contentaggregation";
        else
        if(parentElementName.equalsIgnoreCase("resource"))
        {
            String s = getAttribute(node1, "scormtype");
            if(s.equalsIgnoreCase("sco"))
                metadataType = "sco";
            else
                metadataType = "asset";
        } else
        {
            metadataType = "asset";
        }
        String s1 = messageLocation + "fillMetadata(Node)";
        if(DebugIndicator.ON)
            System.out.println("******  " + s1 + "  *********");
        NodeList nodelist = node.getChildNodes();
        int i = nodelist.getLength();
        for(int j = 0; j < i; j++)
        {
            Node node2 = nodelist.item(j);
            if(node2.getNodeType() == 1)
                if(node2.getLocalName().equalsIgnoreCase("schema"))
                {
                    schemaMult = getMultiplicityUsed(node2, "schema");
                    schema = getText(node2);
                } else
                if(node2.getLocalName().equalsIgnoreCase("schemaversion"))
                {
                    schemaversionMult = getMultiplicityUsed(node2, "schemaversion");
                    schemaversion = getText(node2);
                } else
                if(node2.getLocalName().equalsIgnoreCase("location"))
                {
                    adlcpLocationMult = getMultiplicityUsed(node, "location");
                    adlcpLocation = getText(node2);
                    adlcpLocation = URLDecoder.decode(adlcpLocation);
                } else
                if(node2.getLocalName().equalsIgnoreCase("lom"))
                    metadataRoot = node2;
        }

        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting " + s1 + " ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }

    public boolean checkConformance(String s)
    {
        boolean flag = true;
        int i = messageClass;
        int j = MessageType.INFO;
        String s1 = "";
        String s3 = messageLocation + "checkConformance()";
        String s4 = "";
        if(parentElementName.equalsIgnoreCase("manifest") || parentElementName.equalsIgnoreCase("resource") || parentElementName.equalsIgnoreCase("file"))
        {
            min = MetadataRules.MIN;
            max = MetadataRules.MAX;
        } else
        if(parentElementName.equalsIgnoreCase("organization") || parentElementName.equalsIgnoreCase("item"))
        {
            min = MetadataRules.AMIN;
            max = MetadataRules.AMAX;
        }
        s1 = "Testing element <" + elemName + "> for minimum conformance";
        messageHandler.addMessage(i, j, s1, s3, s4);
        flag = checkMultiplicity(i, s3);
        flag = checkSchema() && flag;
        flag = checkSchemaversion() && flag;
        if(metadataRoot == null)
            flag = checkADLCPLocation(s) && flag;
        else
        if(adlcpLocationMult > 0)
        {
            int k = MessageType.FAILED;
            String s2 = "Element <location> can not be used in conjunction with in-line meta-data";
            messageHandler.addMessage(i, k, s2, s3, s4);
            flag = false;
        }
        return flag;
    }

    public boolean checkSchema()
    {
        boolean flag = true;
        String s = "schema";
        int i = messageClass;
        int j = MessageType.INFO;
        String s1 = "";
        String s8 = messageLocation + "checkSchema()";
        String s9 = "";
        int l1 = -1;
        int i2 = -1;
        if(parentElementName.equalsIgnoreCase("manifest") || parentElementName.equalsIgnoreCase("resource") || parentElementName.equalsIgnoreCase("file"))
        {
            l1 = SchemaRules.MIN;
            i2 = SchemaRules.MAX;
        } else
        if(parentElementName.equalsIgnoreCase("organization") || parentElementName.equalsIgnoreCase("item"))
        {
            l1 = SchemaRules.AMIN;
            i2 = SchemaRules.AMAX;
        }
        int j2 = SchemaRules.VALUESPM;
        int k2 = SchemaRules.VOCABSIZE;
        Vector vector = new Vector(k2);
        for(int l2 = 0; l2 < k2; l2++)
            vector.add(SchemaRules.VOCAB[l2]);

        s1 = "Testing element <" + s + "> for minimum " + "conformance";
        messageHandler.addMessage(i, j, s1, s8, s9);
        if(schemaMult >= l1 && schemaMult <= i2)
        {
            j = MessageType.PASSED;
            s1 = "Element <" + s + "> passed the multiplicity " + "test";
            flag = true;
        } else
        if(l1 == i2)
        {
            j = MessageType.FAILED;
            s1 = "The multiplicity for element <" + s + "> is " + l1 + " and only " + i2 + " and " + multiplicity + " were found.";
        } else
        {
            j = MessageType.FAILED;
            s1 = "The multiplicity for element <" + s + "> is " + l1 + " or " + i2 + " and " + multiplicity + " were found.";
        }
        messageHandler.addMessage(i, j, s1, s8, s9);
        int i3 = schema.length();
        if(i3 > j2)
        {
            int k = MessageType.WARNING;
            String s2 = "The smallest permitted maximum for the value of element <" + elemName + "> is " + j2 + " and a length of " + i3 + " was found.";
            messageHandler.addMessage(i, k, s2, s8, s9);
        } else
        {
            int l = MessageType.PASSED;
            String s3 = "The value, \"" + schema + "\"element <" + elemName + "> passed the smallest permitted maximum test";
            messageHandler.addMessage(i, l, s3, s8, s9);
        }
        if(!schema.equalsIgnoreCase(""))
        {
            boolean flag1 = true;
            int j3 = vector.size();
            if(schemaversion.equalsIgnoreCase(""))
            {
                int i1 = MessageType.PASSED;
                String s4 = "Element <" + s + "> passed the vocabulary test";
                messageHandler.addMessage(i, i1, s4, s8, s9);
            } else
            {
                for(int k3 = 0; k3 < j3 && !flag1; k3++)
                    if(schemaversion.equalsIgnoreCase((String)vector.elementAt(k3)))
                    {
                        int j1 = MessageType.PASSED;
                        String s5 = "Element <" + s + "> passed the vocabulary test";
                        messageHandler.addMessage(i, j1, s5, s8, s9);
                        flag1 = false;
                    }

            }
            if(!flag1)
            {
                int k1 = MessageType.WARNING;
                String s6 = "Element <" + s + "> did not adhere to the " + "best practice vocabulary signifying that this is not " + "ADL SCORM metadata";
                messageHandler.addMessage(i, k1, s6, s8, s9);
                s6 = "Vocabulary list for element <" + s + "> is as follows:";
                messageHandler.addMessage(i, k1, s6, s8, s9);
                for(int l3 = 0; l3 < j3; l3++)
                {
                    String s7 = (String)vector.elementAt(l3);
                    messageHandler.addMessage(i, k1, s7, s8, s9);
                }

                flag = true;
            }
        }
        return flag;
    }

    public boolean checkSchemaversion()
    {
        boolean flag = true;
        String s = "schemaversion";
        int i = messageClass;
        int j = MessageType.INFO;
        String s1 = "";
        String s8 = messageLocation + "checkSchemaversion()";
        String s9 = "";
        int l1 = -1;
        int i2 = -1;
        if(parentElementName.equalsIgnoreCase("manifest") || parentElementName.equalsIgnoreCase("resource") || parentElementName.equalsIgnoreCase("file"))
        {
            l1 = SchemaversionRules.MIN;
            i2 = SchemaversionRules.MAX;
        } else
        if(parentElementName.equalsIgnoreCase("organization") || parentElementName.equalsIgnoreCase("item"))
        {
            l1 = SchemaversionRules.AMIN;
            i2 = SchemaversionRules.AMAX;
        }
        int j2 = SchemaversionRules.VALUESPM;
        int k2 = SchemaversionRules.VOCABSIZE;
        Vector vector = new Vector(k2);
        for(int l2 = 0; l2 < k2; l2++)
            vector.add(SchemaversionRules.VOCAB[l2]);

        s1 = "Testing element <" + s + "> for minimum " + "conformance";
        messageHandler.addMessage(i, j, s1, s8, s9);
        if(schemaversionMult >= l1 && schemaversionMult <= i2)
        {
            j = MessageType.PASSED;
            s1 = "Element <" + s + "> passed the multiplicity " + "test";
            flag = true;
        } else
        if(l1 == i2)
        {
            j = MessageType.FAILED;
            s1 = "The multiplicity for element <" + s + "> is " + l1 + " and only " + i2 + " and " + multiplicity + " were found.";
        } else
        {
            j = MessageType.FAILED;
            s1 = "The multiplicity for element <" + s + "> is " + l1 + " or " + i2 + " and " + multiplicity + " were found.";
        }
        messageHandler.addMessage(i, j, s1, s8, s9);
        int i3 = schemaversion.length();
        if(i3 > j2)
        {
            int k = MessageType.WARNING;
            String s2 = "The smallest permitted maximum for the value of element <" + elemName + "> is " + j2 + " and a length of " + i3 + " was found.";
            messageHandler.addMessage(i, k, s2, s8, s9);
        } else
        {
            int l = MessageType.PASSED;
            String s3 = "The value, \"" + schemaversion + "\" element <" + elemName + "> passed the smallest permitted maximum test";
            messageHandler.addMessage(i, l, s3, s8, s9);
        }
        if(schema.equalsIgnoreCase("ADL SCORM"))
        {
            boolean flag1 = false;
            int j3 = vector.size();
            if(schemaversion.equalsIgnoreCase(""))
            {
                int i1 = MessageType.PASSED;
                String s4 = "Element <" + s + "> passed the vocabulary test";
                messageHandler.addMessage(i, i1, s4, s8, s9);
                flag1 = true;
            } else
            {
                for(int k3 = 0; k3 < j3 && !flag1; k3++)
                    if(schemaversion.equalsIgnoreCase((String)vector.elementAt(k3)))
                    {
                        int j1 = MessageType.PASSED;
                        String s5 = "Element <" + s + "> passed the " + "vocabulary test";
                        messageHandler.addMessage(i, j1, s5, s8, s9);
                        flag1 = true;
                    }

            }
            if(!flag1)
            {
                int k1 = MessageType.FAILED;
                String s6 = "Element <" + s + "> did not adhere to the " + "restricted vocabulary and failed the vocabulary test";
                messageHandler.addMessage(i, k1, s6, s8, s9);
                s6 = "Vocabulary list for element <" + s + "> is as follows:";
                messageHandler.addMessage(i, k1, s6, s8, s9);
                for(int l3 = 0; l3 < j3; l3++)
                {
                    String s7 = (String)vector.elementAt(l3);
                    messageHandler.addMessage(i, k1, s7, s8, s9);
                }

                flag = false;
            }
        }
        return flag;
    }

    public boolean checkADLCPLocation(String s)
    {
        String s1 = messageLocation + "checkADLCPLocation()";
        if(DebugIndicator.ON)
            System.out.println("******  " + s1 + "  *********");
        boolean flag = true;
        String s2 = new String("location");
        int i = messageClass;
        int j = MessageType.INFO;
        String s3 = "";
        String s14 = "";
        int i3 = ADLCPLocationRules.MIN;
        int j3 = ADLCPLocationRules.MAX;
        int k3 = ADLCPLocationRules.VALUESPM;
        s3 = "Testing element <" + s2 + "> for minimum " + "comformance";
        messageHandler.addMessage(i, j, s3, s1, s14);
        if(adlcpLocationMult >= i3 && adlcpLocationMult <= j3)
        {
            j = MessageType.PASSED;
            s3 = "Element <" + s2 + "> passed the multiplicity " + "test";
        } else
        {
            if(i3 == j3)
            {
                j = MessageType.FAILED;
                s3 = "The multiplicity for element <" + s2 + "> is " + i3 + " and only " + j3 + " and " + adlcpLocationMult + " were found.";
            } else
            {
                j = MessageType.FAILED;
                s3 = "The multiplicity for element <" + s2 + "> is " + i3 + " or " + j3 + " and " + adlcpLocationMult + " were found.";
            }
            flag = false;
        }
        messageHandler.addMessage(i, j, s3, s1, s14);
        if(!adlcpLocation.equalsIgnoreCase(""))
        {
            int l3 = adlcpLocation.length();
            if(l3 > k3)
            {
                int k = MessageType.WARNING;
                String s4 = "The smallest permitted maximum for the value of element <" + s2 + "> is " + k3 + " and a length of " + l3 + " was found.";
                messageHandler.addMessage(i, k, s4, s1, s14);
            } else
            {
                int l = MessageType.PASSED;
                String s5 = "The value, \"" + adlcpLocation + "\"element <" + s2 + "> passed the smallest permitted maximum test";
                messageHandler.addMessage(i, l, s5, s1, s14);
            }
            try
            {
                if(DebugIndicator.ON)
                {
                    System.out.println("%%%  baseDir = " + s);
                    System.out.println("%%%  relative adlcpLocation = " + adlcpLocation);
                }
                String s15 = new String(s + adlcpLocation);
                if(DebugIndicator.ON)
                    System.out.println("locationPath = " + s15);
                File file = new File(s15);
                if(file.isFile())
                {
                    int i1 = MessageType.PASSED;
                    String s6 = "File \"" + adlcpLocation + "\" has been detected";
                    messageHandler.addMessage(i, i1, s6, s1, s14);
                } else
                {
                    boolean flag1 = true;
                    try
                    {
                        URL url = new URL(adlcpLocation);
                        if(flag1)
                        {
                            String s16 = url.getProtocol();
                            if(s16.equalsIgnoreCase("file"))
                            {
                                int j1 = MessageType.FAILED;
                                String s7 = "File or URL \"" + adlcpLocation + "\" could not be detected";
                                messageHandler.addMessage(i, j1, s7, s1, s14);
                                flag = false;
                            } else
                            {
                                int k1 = MessageType.PASSED;
                                String s8 = "URL \"" + adlcpLocation + "\" has been detected";
                                messageHandler.addMessage(i, k1, s8, s1, s14);
                            }
                        } else
                        {
                            int l1 = MessageType.FAILED;
                            String s9 = "File or URL \"" + adlcpLocation + "\" could not be detected";
                            messageHandler.addMessage(i, l1, s9, s1, s14);
                            flag = false;
                        }
                    }
                    catch(MalformedURLException malformedurlexception)
                    {
                        int i2 = MessageType.FAILED;
                        String s10 = "URL \"" + adlcpLocation + "\" could not be detected";
                        messageHandler.addMessage(i, i2, s10, s1, s14);
                        flag = false;
                    }
                }
            }
            catch(NullPointerException nullpointerexception)
            {
                if(DebugIndicator.ON)
                    System.out.println("NullPointerException thrown when accessing " + adlcpLocation);
                int j2 = MessageType.FAILED;
                String s11 = "File \"" + adlcpLocation + "\" could not be detected";
                messageHandler.addMessage(i, j2, s11, s1, s14);
                flag = false;
            }
            catch(SecurityException securityexception)
            {
                if(DebugIndicator.ON)
                    System.out.println("SecurityException thrown when accessing " + adlcpLocation);
                int k2 = MessageType.FAILED;
                String s12 = "File \"" + adlcpLocation + "\" could not be detected";
                messageHandler.addMessage(i, k2, s12, s1, s14);
                flag = false;
            }
            if(metadataRoot != null)
            {
                int l2 = MessageType.FAILED;
                String s13 = "Both the <adlcp:location> element and in-line methods  were found to express metadata.  Only one or the other is allowed, but not both";
                messageHandler.addMessage(i, l2, s13, s1, s14);
                flag = false;
            }
        }
        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting " + s1 + " ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }

    public Node getMetadataRoot()
    {
        return metadataRoot;
    }

    public String getMetadataLocation()
    {
        return adlcpLocation;
    }

    public String getMetadataType()
    {
        return metadataType;
    }

    public String getParentName()
    {
        return parentElementName;
    }

    public String getParentID()
    {
        return parentElementID;
    }
}
