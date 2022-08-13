// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RequirementRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import java.util.Vector;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RequirementRules extends MetadataUtil
{

    private String elemNum;
    private Node requirementNode;
    private String valueOfType;
    private boolean typeNotUsed;

    public RequirementRules(Node node, String s)
    {
        typeNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("RequirementRules() called");
            System.out.println("/////////////////////////////////");
        }
        requirementNode = node;
        elemNum = s;
        valueOfType = "";
    }

    public boolean verifyAssetMandatory()
    {
        if(DebugIndicator.ON)
            System.out.println("element " + elemNum + " (requirement) has no " + "mandatory sub-elements");
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
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean flag7 = false;
        NodeList nodelist = requirementNode.getChildNodes();
        Node node = requirementNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("type"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "RequirementRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <type>...");
                        setMessage(MessageType.INFO, "RequirementRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <type> for " + "multiplicity...");
                        int j = getMultiplicityUsed(requirementNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "RequirementRules::verifyAssetOptional()", "More than 1 <type> element was found...0 or 1 allowed");
                            flag = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "RequirementRules::verifyAssetOptional()", "0 or 1 <type> element was found");
                            if(!verifyType(node1))
                                flag = false;
                        }
                    }
                    flag4 = true;
                    typeNotUsed = false;
                } else
                if(s.equalsIgnoreCase("name"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "RequirementRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <name>...");
                        setMessage(MessageType.INFO, "RequirementRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <name> for " + "multiplicity...");
                        int k = getMultiplicityUsed(requirementNode, s);
                        if(k > 1)
                        {
                            setMessage(MessageType.FAILED, "RequirementRules::verifyAssetOptional()", "More than 1 <name> element was found...0 or 1 allowed");
                            flag1 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "RequirementRules::verifyAssetOptional()", "0 or 1 <name> element was found");
                            if(!verifyName(node1))
                                flag1 = false;
                        }
                    }
                    flag5 = true;
                } else
                if(s.equalsIgnoreCase("minimumversion"))
                {
                    if(!flag6)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "RequirementRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <minimumversion>...");
                        setMessage(MessageType.INFO, "RequirementRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <minimumversion> for multiplicity...");
                        int l = getMultiplicityUsed(requirementNode, s);
                        if(l > 1)
                        {
                            setMessage(MessageType.FAILED, "RequirementRules::verifyAssetOptional()", "More than 1 <minimumversion> element was found...0 or 1 allowed");
                            flag2 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "RequirementRules::verifyAssetOptional()", "0 or 1 <minimumversion> element was found");
                            if(!verifyMinimumversion(node1, elemNum + ".3"))
                                flag2 = false;
                        }
                    }
                    flag6 = true;
                } else
                if(s.equalsIgnoreCase("maximumversion"))
                {
                    if(!flag7)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "RequirementRules::verifyAssetOptional()", "Testing element " + elemNum + ".4 <maximumversion>...");
                        setMessage(MessageType.INFO, "RequirementRules::verifyAssetOptional()", "Testing element " + elemNum + ".4 <maximumversion> for multiplicity...");
                        int i1 = getMultiplicityUsed(requirementNode, s);
                        if(i1 > 1)
                        {
                            setMessage(MessageType.FAILED, "RequirementRules::verifyAssetOptional()", "More than 1 <maximumversion> element was found...0 or 1 allowed");
                            flag3 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "RequirementRules::verifyAssetOptional()", "0 or 1 <maximumversion> element was found");
                            if(!verifyMaximumversion(node1, elemNum + ".4"))
                                flag3 = false;
                        }
                    }
                    flag7 = true;
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag8 = flag && flag1 && flag2 && flag3;
            System.out.println("returning ->" + flag8);
        }
        return flag && flag1 && flag2 && flag3;
    }

    public boolean verifyScoOptional()
    {
        return verifyAssetOptional();
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    private boolean verifyType(Node node)
    {
        boolean flag = true;
        boolean flag1 = false;
        Vector vector = buildVocabType();
        flag = verifyVocabulary(node, vector, "type", flag1);
        return flag;
    }

    private boolean verifyName(Node node)
    {
        boolean flag = true;
        boolean flag1 = false;
        Vector vector = new Vector();
        String s = getvalueOfType();
        if(s.equalsIgnoreCase("Operating System"))
            vector = buildVocabNameOS();
        else
        if(s.equalsIgnoreCase("Browser"))
            vector = buildVocabNameBrowser();
        else
            vector = buildVocabNameOther();
        flag = verifyVocabulary(node, vector, "name", flag1);
        return flag;
    }

    private boolean verifyMinimumversion(Node node, String s)
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
                setMessage(MessageType.FAILED, "RequirementRules::verifyMinimumversion()", "No data was found in element <minimumversion> and fails the minimum character length test");
            } else
            if(i == 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        setMessage(MessageType.INFO, "RequirementRules::verifyMinimumversion()", "Testing text of element " + s + " <minimumversion> for character length...");
                        String s2 = getTaggedData(node);
                        if(DebugIndicator.ON)
                            System.out.println("1<= " + s2.length() + " <= " + byte0);
                        if(s2.length() > byte0)
                            setMessage(MessageType.WARNING, "RequirementRules::verifyMinimumversion()", "The text contained in element " + s + " <minimumversion> is greater than " + byte0);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "RequirementRules::verifyMinimumversion()", "No text was found in element " + s + " <minimumversion> and fails the minimum " + "character length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "RequirementRules::verifyMinimumversion()", "Character length for element " + s + " <minimumversion> has been verified");
                        }
                    }
                }

            } else
            {
                flag = false;
                if(DebugIndicator.ON)
                    System.out.println("There were " + i + " TEXT_NODE elements found");
                setMessage(MessageType.FAILED, "RequirementRules::verifyMinimumversion()", "Too many text strings were found in <minimumversion> and fails the test");
            }
        }
        return flag;
    }

    private boolean verifyMaximumversion(Node node, String s)
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
                setMessage(MessageType.FAILED, "RequirementRules::verifyMaximumversion()", "No data was found in element <maximumversion> and fails the minimum character length test");
            } else
            if(i == 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        setMessage(MessageType.INFO, "RequirementRules::verifyMaximumversion()", "Testing text of element " + s + " <maximumversion> for character length...");
                        String s2 = getTaggedData(node);
                        if(DebugIndicator.ON)
                            System.out.println("1<= " + s2.length() + " <= " + byte0);
                        if(s2.length() > byte0)
                            setMessage(MessageType.WARNING, "RequirementRules::verifyMaximumversion()", "The text contained in element " + s + " <maximumversion> is greater than " + byte0);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "RequirementRules::verifyMaximumversion()", "No text was found in element " + s + " <maximumversion> and fails the minimum " + "character length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "RequirementRules::verifyMaximumversion()", "Character length for element  <maximumversion> has been verified");
                        }
                    }
                }

            } else
            {
                flag = false;
                if(DebugIndicator.ON)
                    System.out.println("There were " + i + " TEXT_NODE elements found");
                setMessage(MessageType.FAILED, "RequirementRules::verifyMaximumversion()", "Too many text strings were found in <maximumversion> and fails the test");
            }
        }
        return flag;
    }

    private Vector buildVocabType()
    {
        Vector vector = new Vector(2);
        vector.add("Operating System");
        vector.add("Browser");
        return vector;
    }

    private Vector buildVocabNameOS()
    {
        Vector vector = new Vector(7);
        vector.add("PC-DOS");
        vector.add("MS-Windows");
        vector.add("MacOS");
        vector.add("Unix");
        vector.add("Multi-OS");
        vector.add("Other");
        vector.add("None");
        return vector;
    }

    private Vector buildVocabNameBrowser()
    {
        Vector vector = new Vector(4);
        vector.add("Any");
        vector.add("Netscape Communicator");
        vector.add("Microsoft Internet Explorer");
        vector.add("Opera");
        return vector;
    }

    private Vector buildVocabNameOther()
    {
        Vector vector = new Vector(1);
        vector.add(" ");
        return vector;
    }

    private RequirementRules()
    {
        typeNotUsed = true;
    }
}
