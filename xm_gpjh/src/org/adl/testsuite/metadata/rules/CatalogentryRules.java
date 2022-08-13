// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CatalogentryRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CatalogentryRules extends MetadataUtil
{

    private String elemNum;
    private Node catalogentryNode;

    public CatalogentryRules(Node node, String s)
    {
        if(DebugIndicator.ON)
        {
            System.out.println();
            System.out.println("/////////////////////////////////");
            System.out.println("CatalogentryRules() called");
            System.out.println("/////////////////////////////////");
            System.out.println();
        }
        catalogentryNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = false;
        boolean flag3 = false;
        NodeList nodelist = catalogentryNode.getChildNodes();
        Node node = catalogentryNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("catalog"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "CatalogentryRules::verifyAssetMandatory()", "Testing element " + elemNum + ".1 <catalog>...");
                        setMessage(MessageType.INFO, "CatalogentryRules::verifyAssetMandatory()", "Testing element " + elemNum + ".1 <catalog> for multiplicity...");
                        int j = getMultiplicityUsed(catalogentryNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "CatalogentryRules::verifyAssetMandatory()", "More than 1 <catalog> element was found .. 1 and only 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "CatalogentyRules::verifyAssetMandatory()", "1 and only 1 <catalog> element was found");
                            if(!verifyCatalog(node1, elemNum + ".1"))
                                flag = false;
                        }
                        flag2 = true;
                    }
                } else
                if(s.equalsIgnoreCase("entry") && !flag3)
                {
                    setMessage(MessageType.OTHER, "", "");
                    setMessage(MessageType.INFO, "CatalogentryRules::verifyAssetMandatory()", "Testing element " + elemNum + ".2 <entry>...");
                    setMessage(MessageType.INFO, "CatalogentryRules::verifyAssetMandatory()", "Testing element " + elemNum + ".2 <entry> for multiplicity...");
                    int k = getMultiplicityUsed(catalogentryNode, s);
                    if(k > 1)
                    {
                        setMessage(MessageType.FAILED, "CatalogentryRules::verifyAssetMandatory()", "More than 1 <entry> element was found .. 1 and only 1 allowed");
                        flag1 = false;
                    } else
                    if(k == 1)
                    {
                        setMessage(MessageType.PASSED, "CatalogentyRules::verifyAssetMandatory()", "1 and only 1 <entry> element was found");
                        if(!verifyEntry(node1, elemNum + ".2"))
                            flag1 = false;
                    }
                    flag3 = true;
                }
            }
        }

        if(!flag2)
        {
            setMessage(MessageType.INFO, "CatalogentryRules::verifyAssetMandatory()", "Testing element " + elemNum + ".1 <catalog> for multiplicity...");
            setMessage(MessageType.FAILED, "GeneralRules::verifyAssetMandatory()", "Element " + elemNum + ".1 <catalog> was not found and " + "must appear 1 and only 1 time");
        }
        if(!flag3)
        {
            setMessage(MessageType.INFO, "CatalogentryRules::verifyAssetMandatory()", "Testing element " + elemNum + ".2 <entry> for multiplicity...");
            setMessage(MessageType.FAILED, "GeneralRules::verifyAssetMandatory()", "Element " + elemNum + ".2 <entry> was not found and " + "must appear 1 and only 1 time");
        }
        if(DebugIndicator.ON)
        {
            boolean flag4 = flag && flag1 && flag2 && flag3;
            System.out.println("returning ->" + flag4);
        }
        return flag && flag1 && flag2 && flag3;
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
        boolean flag2 = false;
        boolean flag3 = false;
        NodeList nodelist = catalogentryNode.getChildNodes();
        Node node = catalogentryNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("catalog"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "CatalogentryRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <catalog>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <catalog> for multiplicity...");
                        int j = getMultiplicityUsed(catalogentryNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "CatalogentryRules::verifyAssetOptional()", "More than 1 <catalog> element was found .. 1 and only 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "CatalogentyRules::verifyAssetOptional()", "1 and only 1 <catalog> element was found");
                            if(!verifyCatalog(node1, elemNum + ".1"))
                                flag = false;
                        }
                    }
                } else
                if(s.equalsIgnoreCase("entry") && !flag3)
                {
                    setMessage(MessageType.OTHER, "", "");
                    setMessage(MessageType.INFO, "CatalogentryRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <entry>...");
                    setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <entry> for multiplicity...");
                    int k = getMultiplicityUsed(catalogentryNode, s);
                    if(k > 1)
                    {
                        setMessage(MessageType.FAILED, "CatalogentryRules::verifyAssetOptional()", "More than 1 <entry> element was found .. 1 and only 1 allowed");
                        flag1 = false;
                    } else
                    if(k == 1)
                    {
                        setMessage(MessageType.PASSED, "CatalogentyRules::verifyAssetOptional()", "1 and only 1 <entry> element was found");
                        if(!verifyEntry(node1, elemNum + ".2"))
                            flag1 = false;
                    }
                    flag3 = true;
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag4 = flag && flag1;
            System.out.println("returning ->" + flag4);
        }
        return flag && flag1;
    }

    public boolean verifyScoOptional()
    {
        return verifyAssetOptional();
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    private boolean verifyCatalog(Node node, String s)
    {
        boolean flag = true;
        String s1 = new String();
        String s3 = new String();
        int i = 0;
        char c = '\u03E8';
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
                setMessage(MessageType.FAILED, "CatalogentryRules::verifyCatalog()", "No data was found in element <catalog> and fails the minimum character length test");
            } else
            if(i == 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        setMessage(MessageType.INFO, "CatalogentryRules::verifyCatalog()", "Testing element " + s + " <catalog> " + "for character length...");
                        String s2 = getTaggedData(node);
                        if(DebugIndicator.ON)
                            System.out.println("1 <= " + s2.length() + " <= " + (int)c);
                        if(s2.length() > c)
                            setMessage(MessageType.WARNING, "CatalogentryRules::verifyCatalog()", "The text contained in element " + s + " <catalog> is greater than " + (int)c);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "CatalogentryRules::verifyCatalog()", "No text was found in element " + s + " <catalog> and fails the minimum " + "character length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "CatalogentryRules::verifyCatalog()", "Character length for element  <catalog> has been verified");
                        }
                    }
                }

            } else
            {
                flag = false;
                if(DebugIndicator.ON)
                    System.out.println("There were " + i + " TEXT_NODE elements found");
                setMessage(MessageType.FAILED, "CatalogentryRules::verifyCatalog()", "Too many text strings were found in <catalog> and fails the test");
            }
        }
        return flag;
    }

    private boolean verifyEntry(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<entry>", s, 1000);
        return flag;
    }

    private CatalogentryRules()
    {
    }
}
