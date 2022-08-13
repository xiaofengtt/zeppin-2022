// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   TaxonpathRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TaxonpathRules extends MetadataUtil
{

    private String elemNum;
    private Node taxonpathNode;
    private boolean optionalNotUsed;

    public TaxonpathRules(Node node, String s)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("TaxonpathRules() called");
            System.out.println("/////////////////////////////////");
        }
        taxonpathNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        if(DebugIndicator.ON)
            System.out.println("element " + elemNum + " <taxonpath> has no " + "mandatory sub-elements");
        return false;
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
        NodeList nodelist = taxonpathNode.getChildNodes();
        Node node = taxonpathNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("source"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TaxonpathRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <source>...");
                        setMessage(MessageType.INFO, "TaxonpathRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <source> for multiplicity...");
                        int j = getMultiplicityUsed(taxonpathNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "TaxonpathRules::verifyAssetOptional()", "More than 1 <source> element was found .. 0 or 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "TaxonpathRules::verifyAssetOptional()", "0 or 1 <source> element was found");
                            if(!verifySource(node1, elemNum + ".1"))
                                flag = false;
                        }
                        flag2 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("taxon"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TaxonpathRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <taxon>...");
                        setMessage(MessageType.INFO, "TaxonpathRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <taxon> for " + "multiplicity...");
                        int k = getTaxonMultiplicityUsed(taxonpathNode, s);
                        if(DebugIndicator.ON)
                            System.out.println("TAXONCOUNT value is " + k);
                        if(k > 15)
                            setMessage(MessageType.WARNING, "TaxonpathRules::verifyAssetOptional()", "More than 15 <taxon> elements were found");
                        else
                            setMessage(MessageType.PASSED, "TaxonpathRules::verifyAssetOptional()", "15 or less <taxon> elements were found");
                        flag3 = true;
                    }
                    if(!verifyTaxon(node1, elemNum + ".2"))
                        flag1 = false;
                    optionalNotUsed = false;
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

    private boolean verifySource(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<source>", s, 1000);
        return flag;
    }

    public boolean verifyTaxon(Node node, String s)
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        boolean flag5 = true;
        NodeList nodelist = node.getChildNodes();
        Node node1 = node;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node2 = nodelist.item(i);
            if(node2.getNodeType() == 1)
            {
                String s1 = node2.getLocalName();
                if(s1.equalsIgnoreCase("id"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TaxonpathRules::verifyTaxon()", "Testing element " + s + ".1 <id>...");
                        setMessage(MessageType.INFO, "TaxonpathRules::verifyTaxon()", "Testing element " + s + ".1 <id> for multiplicity...");
                        int j = getMultiplicityUsed(node, s1);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "TaxonpathRules::verifyTaxon()", "More than 1 <id> element was found .. 0 or 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "TaxonpathRules::verifyTaxon()", "0 or 1 <id> element was found");
                            if(!verifyId(node2, s + ".1"))
                                flag = false;
                        }
                        flag2 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s1.equalsIgnoreCase("entry"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TaxonpathRules::verifyTaxon()", "Testing element " + s + ".2 <entry>...");
                        setMessage(MessageType.INFO, "TaxonpathRules::verifyTaxon()", "Testing element " + s + ".2 <entry> for multiplicity...");
                        int k = getMultiplicityUsed(node, s1);
                        if(k > 1)
                        {
                            setMessage(MessageType.FAILED, "TaxonpathRules::verifyTaxon()", "More than 1 <entry> element was found .. 0 or 1 allowed");
                            flag1 = false;
                        } else
                        if(k == 1)
                        {
                            setMessage(MessageType.PASSED, "TaxonpathRules::verifyTaxon()", "0 or 1 <entry> element was found");
                            if(!verifyEntry(node2, s + ".2"))
                                flag1 = false;
                        }
                        flag3 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s1.equalsIgnoreCase("taxon"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TaxonpathRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <taxon>...");
                        flag4 = true;
                    }
                    if(!verifyTaxon(node2, elemNum + ".2"))
                        flag5 = false;
                    optionalNotUsed = false;
                }
            }
        }

        return flag && flag1 && flag5;
    }

    private boolean verifyId(Node node, String s)
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
                setMessage(MessageType.FAILED, "TaxonpathRules::verifyId()", "No data was found in element <id> and fails the minimum character length test");
            } else
            if(i == 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        setMessage(MessageType.INFO, "TaxonpathRules::verifyId()", "Testing text of element " + s + " <id> for character length...");
                        String s2 = getTaggedData(node);
                        if(DebugIndicator.ON)
                            System.out.println("1>= " + s2.length() + " <= " + byte0);
                        if(s2.length() > byte0)
                            setMessage(MessageType.WARNING, "TaxonpathRules::verifyId()", "The text contained in element " + s + " <id> is greater than " + byte0);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "TaxonpathRules::verifyId()", "No text was found in element " + s + " <id> and fails the minimum character " + "length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "TaxonpathRules::verifyId()", "Character length for element  <id> has been verified");
                        }
                    }
                }

            } else
            {
                flag = false;
                if(DebugIndicator.ON)
                    System.out.println("There were " + i + " TEXT_NODE elements found");
                setMessage(MessageType.FAILED, "TaxonpathRules::verifyId()", "Too many text strings were found in <id> and fails the test");
            }
        }
        return flag;
    }

    private boolean verifyEntry(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<entry>", s, 500);
        return flag;
    }

    public boolean getOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private TaxonpathRules()
    {
        optionalNotUsed = true;
    }
}
