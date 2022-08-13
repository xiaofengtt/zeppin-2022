// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RightsRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import java.util.Vector;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RightsRules extends MetadataUtil
{

    private String elemNum;
    private Node rightsNode;
    private boolean optionalNotUsed;

    public RightsRules(Node node, String s)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("RightsRules() called");
            System.out.println("/////////////////////////////////");
        }
        rightsNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = false;
        boolean flag3 = false;
        NodeList nodelist = rightsNode.getChildNodes();
        Node node = rightsNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("cost"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "RightsRules::verifyAssetMandatory()", "Testing element " + elemNum + ".1 <cost>...");
                        setMessage(MessageType.INFO, "RightsRules::verifyAssetMandatory()", "Testing element " + elemNum + ".1 <cost> for multiplicity...");
                        int j = getMultiplicityUsed(rightsNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "RightsRules::verifyAssetMandatory()", "More than 1 <cost> element was found .. 1 and only 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "RightsRules::verifyAssetMandatory()", "1 and only 1 <cost> element was found");
                            if(!verifyCost(node1))
                                flag = false;
                        }
                        flag2 = true;
                    }
                } else
                if(s.equalsIgnoreCase("copyrightandotherrestrictions") && !flag3)
                {
                    setMessage(MessageType.OTHER, "", "");
                    setMessage(MessageType.INFO, "RightsRules::verifyAssetMandatory()", "Testing element " + elemNum + ".2 <copyrightandotherrestrictions>...");
                    setMessage(MessageType.INFO, "RightsRules::verifyAssetMandatory()", "Testing element " + elemNum + ".2 <copyrightandotherrestrictions> for " + "multiplicity...");
                    int k = getMultiplicityUsed(rightsNode, s);
                    if(k > 1)
                    {
                        setMessage(MessageType.FAILED, "RightsRules::verifyAssetMandatory()", "More than 1 <copyrightandotherrestrictions> element was found .. 1 and only 1 allowed");
                        flag1 = false;
                    } else
                    if(k == 1)
                    {
                        setMessage(MessageType.PASSED, "RightsRules::verifyAssetMandatory()", "1 and only 1 <copyrightandotherrestrictions> element was found");
                        if(!verifyCopyrightandotherrestrictions(node1))
                            flag1 = false;
                    }
                    flag3 = true;
                }
            }
        }

        if(!flag2)
        {
            setMessage(MessageType.INFO, "RightsRules::verifyAssetMandatory()", "Testing element " + elemNum + ".1 <cost> for " + "multiplicity...");
            setMessage(MessageType.FAILED, "RightsRules::verifyAssetMandatory()", "Element " + elemNum + ".1 <cost> was not found and " + "must appear 1 and only 1 time");
        }
        if(!flag3)
        {
            setMessage(MessageType.INFO, "RightsRules::verifyAssetMandatory()", "Testing element " + elemNum + ".2 <copyrightandotherrestrictions> for multiplicity...");
            setMessage(MessageType.FAILED, "RightsRules::verifyAssetMandatory()", "Element " + elemNum + ".2 <copyrightandotherrestrictions> was not found and " + "must appear 1 and only 1 time");
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
        boolean flag1 = false;
        NodeList nodelist = rightsNode.getChildNodes();
        Node node = rightsNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("description"))
                {
                    if(!flag1)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "RightsRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <description>...");
                        setMessage(MessageType.INFO, "RightsRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <description> for multiplicity...");
                        int j = getMultiplicityUsed(rightsNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "GeneralRulesRules::verifyAssetOptional()", "More than 1 <description> element was found .. 0 or 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "RightsRules::verifyAssetOptional()", "0 or 1 <description> element was found");
                            if(!verifyDescription(node1, elemNum + ".3"))
                                flag = false;
                        }
                        flag1 = true;
                    }
                    optionalNotUsed = false;
                }
            }
        }

        if(DebugIndicator.ON)
            System.out.println("returning ->" + flag);
        return flag;
    }

    public boolean verifyScoOptional()
    {
        return verifyAssetOptional();
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    private boolean verifyCost(Node node)
    {
        boolean flag = true;
        boolean flag1 = true;
        Vector vector = buildVocabCost();
        flag = verifyVocabulary(node, vector, "cost", flag1);
        return flag;
    }

    private boolean verifyCopyrightandotherrestrictions(Node node)
    {
        boolean flag = true;
        boolean flag1 = true;
        Vector vector = buildVocabCopyrightandotherrestrictions();
        flag = verifyVocabulary(node, vector, "copyrightandotherrestrictions", flag1);
        return flag;
    }

    private boolean verifyDescription(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<description>", s, 1000);
        return flag;
    }

    private Vector buildVocabCost()
    {
        Vector vector = new Vector(2);
        vector.add("yes");
        vector.add("no");
        return vector;
    }

    private Vector buildVocabCopyrightandotherrestrictions()
    {
        Vector vector = new Vector(2);
        vector.add("yes");
        vector.add("no");
        return vector;
    }

    public boolean getOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private RightsRules()
    {
        optionalNotUsed = true;
    }
}
