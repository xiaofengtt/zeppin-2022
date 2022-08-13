// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLFile.java

package org.adl.parsers.dom;

import java.io.File;
import java.io.PrintStream;
import java.net.*;
import org.adl.parsers.util.MessageClassification;
import org.adl.parsers.util.MessageHandler;
import org.adl.parsers.util.adlrules.manifest.FileRules;
import org.adl.parsers.util.adlrules.manifest.HrefRules;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement, ADLMetadata

public class ADLFile extends ADLElement
{

    private String href;
    private ADLMetadata adlMetadata;
    private int messageClass;
    private String messageLocation;

    public ADLFile()
    {
        super("file");
        href = new String();
        adlMetadata = null;
        messageClass = MessageClassification.MINIMUM;
        messageLocation = "ADLFile::";
    }

    public boolean fillFile(Node node)
    {
        boolean flag = true;
        multiplicity = getMultiplicityUsed(node.getParentNode(), elemName);
        String s = messageLocation + "fillFile(Node)";
        if(DebugIndicator.ON)
            System.out.println("******  " + s + "  *********");
        href = getAttribute(node, "href");
        NodeList nodelist = node.getChildNodes();
        int i = nodelist.getLength();
        for(int j = 0; j < i; j++)
        {
            Node node1 = nodelist.item(j);
            if(node1.getNodeType() == 1 && node1.getLocalName().equalsIgnoreCase("metadata"))
            {
                adlMetadata = new ADLMetadata();
                flag = adlMetadata.fillMetadata(node1) && flag;
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
        String s2 = messageLocation + "checkConformance(String)";
        if(DebugIndicator.ON)
            System.out.println("******  " + s2 + "  *********");
        boolean flag = true;
        min = FileRules.MIN;
        max = FileRules.MAX;
        int i = messageClass;
        int j = MessageType.INFO;
        String s3 = "";
        String s4 = "";
        s3 = "Testing element <" + elemName + "> for minimum conformance";
        messageHandler.addMessage(i, j, s3, s2, s4);
        flag = checkMultiplicity(i, s2);
        flag = checkHref(s, s1) && flag;
        if(adlMetadata != null)
        {
            flag = adlMetadata.checkConformance(s) && flag;
            messageHandler.appendMessage(i, adlMetadata.getMessage(i));
        }
        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting " + s2 + " ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }

    public boolean checkHref(String s, String s1)
    {
        String s2 = messageLocation + "checkHref(String)";
        if(DebugIndicator.ON)
            System.out.println("******  " + s2 + "  *********");
        boolean flag = true;
        String s3 = new String("href");
        int i = messageClass;
        int j = MessageType.INFO;
        String s4 = "";
        String s14 = "";
        int l2 = HrefRules.MIN;
        int i3 = HrefRules.MAX;
        int j3 = HrefRules.VALUESPM;
        s4 = "Testing attribute \"" + s3 + "\" for minimum " + "comformance";
        messageHandler.addMessage(i, j, s4, s2, s14);
        flag = checkMultiplicity(i, s2, s3, l2, i3, href, true) && flag;
        if(!href.equalsIgnoreCase(""))
        {
            int k3 = href.length();
            if(k3 > j3)
            {
                int k = MessageType.WARNING;
                String s5 = "The smallest permitted maximum for the value of attribute \"" + s3 + "\" is " + j3 + " and a length of " + k3 + " was found.";
                messageHandler.addMessage(i, k, s5, s2, s14);
            } else
            {
                int l = MessageType.PASSED;
                String s6 = "The value, \"" + href + "\", of attribute \"" + s3 + "\" passed the " + "smallest permitted maximum test";
                messageHandler.addMessage(i, l, s6, s2, s14);
            }
            try
            {
                if(DebugIndicator.ON)
                {
                    System.out.println("%%%  baseDir = " + s);
                    System.out.println("%%%  relative href = " + href);
                }
                String s15 = href;
                int l3 = s15.indexOf(63);
                if(l3 > 0)
                {
                    s15 = s15.substring(0, l3);
                    if(DebugIndicator.ON)
                        System.out.println("query temphref: " + s15);
                }
                int i4 = s15.indexOf(35);
                if(i4 > 0)
                {
                    s15 = s15.substring(0, i4);
                    if(DebugIndicator.ON)
                        System.out.println("fragment temphref: " + s15);
                }
                URLDecoder urldecoder = new URLDecoder();
                s15 = URLDecoder.decode(s15);
                String s16 = new String(s + s1 + s15);
                if(DebugIndicator.ON)
                    System.out.println("hrefPath = " + s16);
                File file = new File(s16);
                if(file.isFile())
                {
                    int i1 = MessageType.PASSED;
                    String s7 = "File \"" + href + "\" has been detected";
                    messageHandler.addMessage(i, i1, s7, s2, s14);
                } else
                {
                    boolean flag1 = true;
                    try
                    {
                        String s17 = href;
                        if(s1.length() > 6 && (s1.substring(0, 5).equals("http:") || s1.substring(0, 6).equals("https:")))
                            s17 = s1 + s17;
                        URL url = new URL(s17);
                        if(flag1)
                        {
                            String s18 = url.getProtocol();
                            if(s18.equalsIgnoreCase("file"))
                            {
                                int j1 = MessageType.FAILED;
                                String s8 = "File or URL \"" + href + "\" could not " + "be detected or verified for format";
                                messageHandler.addMessage(i, j1, s8, s2, s14);
                                flag = false;
                            } else
                            {
                                int k1 = MessageType.PASSED;
                                String s9 = "URL \"" + href + "\" has been verified for" + " correct format";
                                messageHandler.addMessage(i, k1, s9, s2, s14);
                            }
                        } else
                        {
                            int l1 = MessageType.FAILED;
                            String s10 = "File or URL \"" + href + "\" could not be " + "verified for correct format";
                            messageHandler.addMessage(i, l1, s10, s2, s14);
                            flag = false;
                        }
                    }
                    catch(MalformedURLException malformedurlexception)
                    {
                        int i2 = MessageType.FAILED;
                        String s11 = "URL \"" + href + "\" could not be verified for " + "correct format";
                        messageHandler.addMessage(i, i2, s11, s2, s14);
                        flag = false;
                    }
                }
            }
            catch(NullPointerException nullpointerexception)
            {
                if(DebugIndicator.ON)
                    System.out.println("NullPointerException thrown when accessing " + href);
                int j2 = MessageType.FAILED;
                String s12 = "File \"" + href + "\" could not be detected";
                messageHandler.addMessage(i, j2, s12, s2, s14);
                flag = false;
            }
            catch(SecurityException securityexception)
            {
                if(DebugIndicator.ON)
                    System.out.println("SecurityException thrown when accessing " + href);
                int k2 = MessageType.FAILED;
                String s13 = "File \"" + href + "\" could not be detected";
                messageHandler.addMessage(i, k2, s13, s2, s14);
                flag = false;
            }
        }
        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting " + s2 + " ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }

    public ADLMetadata getMetadata()
    {
        return adlMetadata;
    }
}
