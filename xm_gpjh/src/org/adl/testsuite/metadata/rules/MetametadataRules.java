// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MetametadataRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.testsuite.metadata.rules:
//            CatalogentryRules, ContributeRules

public class MetametadataRules extends MetadataUtil
{

    private String elemNum;
    private Node metametadataNode;
    private boolean optionalNotUsed;

    public MetametadataRules(Node node, String s)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("MetametadataRules() called");
            System.out.println("/////////////////////////////////");
        }
        metametadataNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        boolean flag = true;
        boolean flag1 = false;
        boolean flag2 = true;
        NodeList nodelist = metametadataNode.getChildNodes();
        Node node = metametadataNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("metadatascheme"))
                {
                    if(!flag1)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "MetametadataRules::verifyAssetMandatory()", "Testing element " + elemNum + ".4 <metadatascheme>...");
                        setMessage(MessageType.INFO, "MetametadataRules::verifyAssetMandatory()", "Testing element " + elemNum + ".4 <metadatascheme> for " + "multiplicity...");
                        int j = getMultiplicityUsed(metametadataNode, s);
                        if(j > 10)
                            setMessage(MessageType.WARNING, "MetametadataRules::verifyAssetMandatory()", "More than 10 <metadatascheme> elements were found");
                        else
                            setMessage(MessageType.PASSED, "MetametadataRules::verifyAssetMandatory()", "10 or less <metadatascheme> elements were found");
                        flag1 = true;
                    }
                    if(!verifyMetadatascheme(node1, elemNum + ".4"))
                        flag = false;
                } else
                if(s.equalsIgnoreCase("identifier"))
                {
                    flag2 = false;
                    setMessage(MessageType.INFO, "MetametadataRules::verifyAssetMandatory()", "Testing element 3 <metametadata> for reserved element <identifier>..");
                    setMessage(MessageType.FAILED, "MetametadataRules::verifyAssetMandatory()", "Element 3.1 <identifier> was found and is a Reserved  element");
                }
            }
        }

        if(!flag1)
        {
            setMessage(MessageType.INFO, "MetametadataRules::verifyAssetMandatory()", "Testing element 3.4 <metadatascheme> for multiplicity...");
            setMessage(MessageType.FAILED, "MetametadataRules::verifyAssetMandatory()", "Element 3.4 <metadatascheme> was not found and must appear 1 or more times");
        }
        if(DebugIndicator.ON)
        {
            boolean flag3 = flag && flag1;
            System.out.println("returning ->" + flag3);
        }
        return flag && flag1 && flag2;
    }

    public boolean verifyScoMandatory()
    {
        return verifyAssetMandatory();
    }

    public boolean verifyCaMandatory()
    {
        return verifyScoMandatory();
    }

    public boolean verifyAssetOptional()
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = false;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = true;
        boolean flag7 = true;
        NodeList nodelist = metametadataNode.getChildNodes();
        Node node = metametadataNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("catalogentry"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "MetametadataRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <catalogentry>...");
                        setMessage(MessageType.INFO, "MetametadataRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <catalogentry> for multiplicity...");
                        int j = getMultiplicityUsed(metametadataNode, s);
                        if(j > 10)
                            setMessage(MessageType.WARNING, "MetametadataRules::verifyAssetOptional()", "More than 10 <catalogentry> elements were found");
                        else
                            setMessage(MessageType.PASSED, "MetametadataRules::verifyAssetOptional()", "10 or less <catalogentry> elements were found");
                        flag3 = true;
                    }
                    CatalogentryRules catalogentryrules = new CatalogentryRules(node1, elemNum + ".2");
                    boolean flag8 = catalogentryrules.verifyAssetOptional();
                    if(!flag8)
                        flag = false;
                    appendMessages(catalogentryrules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("contribute"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "MetametadataRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <contribute>...");
                        setMessage(MessageType.INFO, "MetametadataRules::verifyAssetOptional()", "Testing element 3.3 <contribute> for multiplicity...");
                        int k = getMultiplicityUsed(metametadataNode, s);
                        if(k > 10)
                            setMessage(MessageType.WARNING, "MetametadataRules::verifyAssetOptional()", "More than 10 <contribute> elements were found");
                        else
                            setMessage(MessageType.PASSED, "MetametadataRules::verifyAssetOptional()", "10 or less <contribute> elements were found");
                        flag4 = true;
                    }
                    ContributeRules contributerules = new ContributeRules(node1, elemNum + ".3");
                    boolean flag9 = contributerules.verifyAssetOptionalMetametadata();
                    if(!flag9)
                        flag1 = false;
                    appendMessages(contributerules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("language"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "MetametadataRules::verifyAssetOptional()", "Testing element " + elemNum + ".5 <language>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 3..5 <language> for multiplicity...");
                        int l = getMultiplicityUsed(metametadataNode, s);
                        if(l > 1)
                        {
                            setMessage(MessageType.FAILED, "MetametadataRules::verifyAssetOptional()", "More than 1 <languge> element was found...0 or 1 allowed");
                            flag2 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "MetametadataRules::verifyAssetOptional()", "0 or 1 <language> element was found");
                            if(!verifyLanguage(node1, elemNum + ".2"))
                                flag2 = false;
                        }
                        flag5 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("identifier"))
                {
                    flag6 = false;
                    setMessage(MessageType.INFO, "MetametadataRules::verifyAssetOptional()", "Testing element 3 <metametadata> for reserved  element <identifier>..");
                    setMessage(MessageType.FAILED, "MetametadataRules::verifyAssetOptional()", "Element 3.1 <identifier> was found and is a Reserved element");
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag10 = flag && flag1 && flag2;
            System.out.println("returning -> " + flag10);
        }
        return flag && flag1 && flag2 && flag6;
    }

    public boolean verifyScoOptional()
    {
        return verifyAssetOptional();
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    private boolean verifyMetadatascheme(Node node, String s)
    {
        boolean flag = true;
        String s1 = new String();
        String s3 = new String();
        int i = 0;
        byte byte0 = 30;
        Node node1 = node;
        NodeList nodelist = node.getChildNodes();
        if(nodelist != null)
        {
            for(int j = 0; j < nodelist.getLength(); j++)
            {
                Node node2 = nodelist.item(j);
                if(node2.getNodeType() == 3 || node2.getNodeType() == 4)
                    i++;
            }

            if(i == 0)
            {
                flag = false;
                setMessage(MessageType.FAILED, "MetametadataRules::verifyMetadatascheme()", "No data was found in element <metadatascheme> and fails the minimum character length test");
            } else
            if(i == 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        setMessage(MessageType.INFO, "MetametadataRules::verifyMetadatascheme()", "Testing text of element <metadatascheme> for character length...");
                        String s2 = getTaggedData(node);
                        if(DebugIndicator.ON)
                            System.out.println("1 <= " + s2.length() + " <= " + byte0);
                        if(s2.length() > byte0)
                            setMessage(MessageType.WARNING, "MetametadataRules::verifyMetadatascheme()", "The text contained in element <metadatascheme> is greater than " + byte0);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "MetametadataRules::verifyMetadatascheme()", "No text was found in element <metadatascheme> and fails the minimum character length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "MetametadataRules::verifyMetadatascheme()", "Character length for element <metadatascheme> has been verified");
                        }
                    }
                }

            } else
            {
                flag = false;
                if(DebugIndicator.ON)
                    System.out.println("There were " + i + " TEXT_NODE elements found");
                setMessage(MessageType.FAILED, "MetametadataRules::verifyMetadatascheme()", "Too many text strings were found in <metadatascheme> and fails the test");
            }
        }
        return flag;
    }

    private boolean verifyLanguage(Node node, String s)
    {
        boolean flag = true;
        String s1 = new String();
        String s3 = new String();
        int i = 0;
        byte byte0 = 100;
        Node node1 = node;
        NodeList nodelist = node.getChildNodes();
        if(nodelist != null)
        {
            for(int j = 0; j < nodelist.getLength(); j++)
            {
                Node node2 = nodelist.item(j);
                if(node2.getNodeType() == 3 || node2.getNodeType() == 4)
                    i++;
            }

            if(i == 0)
            {
                flag = false;
                setMessage(MessageType.FAILED, "MetametadataRules::verifyMetadatascheme()", "No data was found in element <language> and fails the minimum character length test");
            } else
            if(i <= 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        setMessage(MessageType.INFO, "MetametadataRules::verifyLanguage()", "Testing element " + s + " <language> for " + "character length...");
                        String s2 = getTaggedData(node);
                        if(DebugIndicator.ON)
                            System.out.println("1<= " + s2.length() + " <= " + byte0);
                        if(s2.length() > byte0)
                            setMessage(MessageType.WARNING, "MetametadataRules::verifyLanguage()", "The text contained in element " + s + " <language> is greater than " + byte0);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "MetametadataRules::verifyLanguage()", "No text was found in element " + s + " <language> and fails the minimum " + "character length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "MetametadataRules::verifyverifyLanguage()", "Character length for element " + s + " <language> has been verified");
                        }
                    }
                }

            } else
            {
                flag = false;
                if(DebugIndicator.ON)
                    System.out.println("There were " + i + " TEXT_NODE elements found");
                setMessage(MessageType.FAILED, "MetametadataRules::verifyLanguage()", "Too many text strings were found in <language> and fails the test");
            }
        }
        return flag;
    }

    public boolean isOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private MetametadataRules()
    {
        optionalNotUsed = true;
    }
}
