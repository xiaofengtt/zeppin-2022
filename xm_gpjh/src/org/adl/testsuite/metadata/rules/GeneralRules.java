// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GeneralRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import java.util.Vector;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.testsuite.metadata.rules:
//            CatalogentryRules

public class GeneralRules extends MetadataUtil
{

    private String elemNum;
    private Node generalNode;
    private boolean optionalNotUsed;

    public GeneralRules(Node node, String s)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("GeneralRules() called");
            System.out.println("/////////////////////////////////");
        }
        generalNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = true;
        NodeList nodelist = generalNode.getChildNodes();
        Node node = generalNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(DebugIndicator.ON)
            {
                System.out.println("Node name: " + node1.getLocalName());
                System.out.println("namespace name: " + node1.getNamespaceURI());
                System.out.println("prefix name: " + node1.getPrefix());
            }
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("title"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetMandatory()", "Testing element " + elemNum + ".2 <title>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetMandatory()", "Testing element " + elemNum + ".2 <title> for multiplicity...");
                        int j = getMultiplicityUsed(generalNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "GeneralRules::verifyAssetMandatory()", "More than 1 <title> element was found .. 1 and only 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "GeneralRules::verifyAssetMandatory()", "1 and only 1 <title> element was found");
                            if(!verifyTitle(node1, elemNum + ".2"))
                                flag = false;
                        }
                        flag2 = true;
                    }
                } else
                if(s.equalsIgnoreCase("description"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetMandatory()", "Testing element " + elemNum + ".5 <description>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetMandatory()", "Testing element 1.5 <description> for multiplicity...");
                        int k = getMultiplicityUsed(generalNode, s);
                        if(k > 10)
                            setMessage(MessageType.WARNING, "GeneralRules::verifyAssetMandatory()", "More than 10 <description> elements were found");
                        else
                            setMessage(MessageType.PASSED, "GeneralRules::verifyAssetMandatory()", "10 or less <description> elements were found");
                        flag3 = true;
                    }
                    if(!verifyDescription(node1, elemNum + ".5"))
                        flag1 = false;
                } else
                if(s.equalsIgnoreCase("identifier"))
                {
                    flag4 = false;
                    setMessage(MessageType.INFO, "GeneralRules::verifyAssetMandatory()", "Testing element 1 <general> for reserved element <identifier>..");
                    setMessage(MessageType.FAILED, "GeneralRules::verifyAssetMandatory()", "Element 1.1 <identifier> was found and is a Reserved element");
                }
            }
        }

        if(!flag2)
        {
            setMessage(MessageType.INFO, "GeneralRules::verifyAssetMandatory()", "Testing element 1.2 <title> for multiplicity...");
            setMessage(MessageType.FAILED, "GeneralRules::verifyAssetMandatory()", "Element 1.2 <title> was not found and must appear 1 and only 1 time");
        }
        if(!flag3)
        {
            setMessage(MessageType.INFO, "GeneralRules::verifyAssetMandatory()", "Testing element 1.5 <description> for multiplicity...");
            setMessage(MessageType.FAILED, "GeneralRules::verifyAssetMandatory()", "Element 1.5 <description> was not found and must appear 1 or more times");
        }
        if(DebugIndicator.ON)
        {
            boolean flag5 = flag && flag1 && flag2 && flag3;
            System.out.println("returning ->" + flag5);
        }
        return flag && flag1 && flag2 && flag3 && flag4;
    }

    public boolean verifyScoMandatory()
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean flag7 = false;
        boolean flag8 = true;
        boolean flag9 = true;
        NodeList nodelist = generalNode.getChildNodes();
        Node node = generalNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("title"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + ".2 <title>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + ".2 <title> for multiplicity...");
                        int j = getMultiplicityUsed(generalNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "GeneralRules::verifyScoMandatory()", "More than 1 <title> element was found...1 and only 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "GeneralRules::verifyScoMandatory()", "1 and only 1 <title> element was found");
                            if(!verifyTitle(node1, elemNum + ".2"))
                                flag = false;
                        }
                    }
                    flag4 = true;
                } else
                if(s.equalsIgnoreCase("catalogentry"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + ".3 <catalogentry>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + ".3 <catalogentry> for multiplicity...");
                        int k = getMultiplicityUsed(generalNode, s);
                        if(k > 10)
                            setMessage(MessageType.WARNING, "GeneralRules::verifyScoMandatory()", "More than 10 <catalogentry> elements were found");
                        else
                            setMessage(MessageType.PASSED, "GeneralRules::verifyScoMandatory()", "10 or less <catalogentry> elements were found");
                        flag5 = true;
                    }
                    CatalogentryRules catalogentryrules = new CatalogentryRules(node1, elemNum + ".3");
                    boolean flag10 = catalogentryrules.verifyScoMandatory();
                    if(!flag10)
                        flag1 = false;
                    appendMessages(catalogentryrules.getMessages());
                } else
                if(s.equalsIgnoreCase("description"))
                {
                    if(!flag6)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + ".5 <description>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element element " + elemNum + ".5 <description> for multiplicity...");
                        int l = getMultiplicityUsed(generalNode, s);
                        if(l > 10)
                            setMessage(MessageType.WARNING, "verifyScoMandatory()", "More than 10 <description> elements were found");
                        else
                            setMessage(MessageType.PASSED, "verifyScoMandatory()", "10 or less <description> elements were found");
                        flag6 = true;
                    }
                    if(!verifyDescription(node1, elemNum + ".5"))
                        flag2 = false;
                } else
                if(s.equalsIgnoreCase("keyword"))
                {
                    if(!flag7)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + ".6 <keyword>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + ".6 <keyword> for multiplicity...");
                        int i1 = getMultiplicityUsed(generalNode, s);
                        if(i1 > 10)
                            setMessage(MessageType.WARNING, "verifyScoMandatory()", "More than 10 <keyword> elements were found");
                        else
                            setMessage(MessageType.PASSED, "verifyScoMandatory()", "10 or less <keyword> elements were found");
                        flag7 = true;
                    }
                    if(!verifyKeyword(node1, elemNum + ".6"))
                        flag3 = false;
                } else
                if(s.equalsIgnoreCase("identifier"))
                {
                    flag8 = false;
                    setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element 1 <general> for reserved element <identifier>..");
                    setMessage(MessageType.FAILED, "GeneralRules::verifyScoMandatory()", "Element 1.1 <identifier> was found and is a Reserved element");
                }
            }
        }

        if(!flag4)
        {
            setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + " .2 <title> for " + "multiplicity...");
            setMessage(MessageType.FAILED, "GeneralRules::verifyScoMandatory()", "Element " + elemNum + ".2 <title> was not found and " + "must appear 1 and only 1 time");
        }
        if(!flag5)
        {
            setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + ".3 <catalogentry> for " + "multiplicity...");
            setMessage(MessageType.FAILED, "GeneralRules::verifyScoMandatory()", "Element " + elemNum + ".3 <catalogentry> was not found and " + "must appear 1 or more times");
        }
        if(!flag6)
        {
            setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + ".5 <description> for " + "multiplicity...");
            setMessage(MessageType.FAILED, "GeneralRules::verifyScoMandatory()", "Element " + elemNum + ".5 <description> was not found and " + "must appear 1 or more times");
        }
        if(!flag7)
        {
            setMessage(MessageType.INFO, "GeneralRules::verifyScoMandatory()", "Testing element " + elemNum + ".6 <keyword> for " + "multiplicity...");
            setMessage(MessageType.FAILED, "GeneralRules::verifyScoMandatory()", "Element " + elemNum + ".6 <keyword> was not found and " + "must appear 1 or more times");
        }
        if(DebugIndicator.ON)
        {
            boolean flag11 = flag && flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7;
            System.out.println("returning ->" + flag11);
        }
        return flag && flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8;
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
        boolean flag4 = true;
        boolean flag5 = true;
        boolean flag6 = false;
        boolean flag7 = false;
        boolean flag8 = false;
        boolean flag9 = false;
        boolean flag10 = false;
        boolean flag11 = false;
        boolean flag12 = true;
        boolean flag13 = true;
        NodeList nodelist = generalNode.getChildNodes();
        Node node = generalNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("catalogentry"))
                {
                    if(!flag6)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <catalogentry>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <catalogentry> for multiplicity...");
                        int j = getMultiplicityUsed(generalNode, s);
                        if(j > 10)
                            setMessage(MessageType.WARNING, "GeneralRules::verifyAssetOptional()", "More than 10 <catalogentry> elements were found");
                        else
                            setMessage(MessageType.PASSED, "GeneralRules::verifyAssetOptional()", "10 or less <catalogentry> elements were found");
                        flag6 = true;
                    }
                    CatalogentryRules catalogentryrules = new CatalogentryRules(node1, elemNum + ".3");
                    boolean flag14 = catalogentryrules.verifyAssetOptional();
                    if(!flag14)
                        flag = false;
                    appendMessages(catalogentryrules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("language"))
                {
                    if(!flag7)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".4 <language>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".4 <language> for multiplicity...");
                        int k = getMultiplicityUsed(generalNode, s);
                        if(k > 10)
                            setMessage(MessageType.WARNING, "GeneralRules::verifyAssetOptional()", "More than 10 <language> elements were found");
                        else
                            setMessage(MessageType.PASSED, "GeneralRules::verifyAssetOptional()", "10 or less <language> elements were found");
                        flag7 = true;
                    }
                    if(!verifyLanguage(node1, elemNum + ".4"))
                        flag1 = false;
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("keyword"))
                {
                    if(!flag8)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".6 <keyword>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".6 <keyword> for multiplicity...");
                        int l = getMultiplicityUsed(generalNode, s);
                        if(l > 10)
                            setMessage(MessageType.WARNING, "GeneralRules::verifyAssetOptional()", "More than 10 <keyword> elements were found");
                        else
                            setMessage(MessageType.PASSED, "GeneralRules::verifyAssetOptional()", "10 or less <keyword> elements were found");
                        flag8 = true;
                    }
                    if(!verifyKeyword(node1, elemNum + ".6"))
                        flag2 = false;
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("coverage"))
                {
                    if(!flag9)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".7 <coverage>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".7 <coverage> for multiplicity...");
                        int i1 = getMultiplicityUsed(generalNode, s);
                        if(i1 > 10)
                            setMessage(MessageType.WARNING, "GeneralRules::verifyAssetOptional()", "More than 10 <coverage> elements were found");
                        else
                            setMessage(MessageType.PASSED, "GeneralRules::verifyAssetOptional()", "10 or less <coverage> elements were found");
                        flag9 = true;
                    }
                    if(!verifyCoverage(node1, elemNum + ".7"))
                        flag3 = false;
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("structure"))
                {
                    if(!flag10)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".8 <structure>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".8 <structure> for multiplicity...");
                        int j1 = getMultiplicityUsed(generalNode, s);
                        if(j1 > 1)
                        {
                            setMessage(MessageType.FAILED, "GeneralRulesRules::verifyAssetOptional()", "More than 1 <structure> element was found .. 0 or 1 allowed");
                            flag4 = false;
                        } else
                        if(j1 == 1)
                        {
                            setMessage(MessageType.PASSED, "GeneralRules::verifyAssetOptional()", "0 or 1 <structure> element was found");
                            if(!verifyStructure(node1))
                                flag4 = false;
                        }
                        flag10 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("aggregationlevel"))
                {
                    if(!flag11)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".9 <aggregationlevel>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element " + elemNum + ".9 <aggregationlevel> for multiplicity...");
                        int k1 = getMultiplicityUsed(generalNode, s);
                        if(k1 > 1)
                        {
                            setMessage(MessageType.FAILED, "GeneralRules::verifyAssetOptional()", "More than 1 <aggregationlevel> element was found .. 0 or 1 allowed");
                            flag5 = false;
                        } else
                        if(k1 == 1)
                        {
                            setMessage(MessageType.PASSED, "GeneralRules::verifyAssetOptional()", "0 or 1 <aggregationlevel> element was found");
                            if(!verifyAggregationlevel(node1))
                                flag5 = false;
                        }
                        flag11 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("identifier"))
                {
                    flag12 = false;
                    setMessage(MessageType.INFO, "GeneralRules::verifyAssetOptional()", "Testing element 1 <general> for reserved element <identifier>..");
                    setMessage(MessageType.FAILED, "GeneralRules::verifyAssetOptional()", "Element 1.1 <identifier> was found and is a Reserved element");
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag15 = flag && flag1 && flag2 && flag3 && flag4 && flag5;
            System.out.println("returning ->" + flag15);
        }
        return flag && flag1 && flag2 && flag3 && flag4 && flag5 && flag12;
    }

    public boolean verifyScoOptional()
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean flag7 = false;
        boolean flag8 = true;
        boolean flag9 = true;
        NodeList nodelist = generalNode.getChildNodes();
        Node node = generalNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("language"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoOptional()", "Testing element " + elemNum + ".5 <language>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoOptional()", "Testing element " + elemNum + ".4 <language> for multiplicity...");
                        int j = getMultiplicityUsed(generalNode, s);
                        if(j > 10)
                            setMessage(MessageType.WARNING, "GeneralRules::verifyScoOptional()", "More than 10 <language> elements were found");
                        else
                            setMessage(MessageType.PASSED, "GeneralRules::verifyScoOptional()", "10 or less <language> elements were found");
                        flag4 = true;
                    }
                    if(!verifyLanguage(node1, elemNum + ".4"))
                        flag = false;
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("coverage"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoOptional()", "Testing element " + elemNum + ".7 <coverage>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoOptional()", "Testing element " + elemNum + ".7 <coverage> for multiplicity...");
                        int k = getMultiplicityUsed(generalNode, s);
                        if(k > 10)
                            setMessage(MessageType.WARNING, "GeneralRules::verifyScoOptional()", "More than 10 <coverage> elements were found");
                        else
                            setMessage(MessageType.PASSED, "GeneralRules::verifyScoOptional()", "10 or less <coverage> elements were found");
                        flag5 = true;
                    }
                    if(!verifyCoverage(node1, elemNum + ".7"))
                        flag1 = false;
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("structure"))
                {
                    if(!flag6)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoOptional()", "Testing element " + elemNum + ".8 <structure>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoOptional()", "Testing element " + elemNum + ".8 <structure> for multiplicity...");
                        int l = getMultiplicityUsed(generalNode, s);
                        if(l > 1)
                        {
                            setMessage(MessageType.FAILED, "GeneralRulesRules::verifyScoOptional()", "More than 1 <structure> element was found .. 0 or 1 allowed");
                            flag2 = false;
                        } else
                        if(l == 1)
                        {
                            setMessage(MessageType.PASSED, "GeneralRules::verifyScoOptional()", "0 or 1 <structure> element was found");
                            if(!verifyStructure(node1))
                                flag2 = false;
                        }
                        flag6 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("aggregationlevel"))
                {
                    if(!flag7)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoOptional()", "Testing element " + elemNum + ".9 <aggregationlevel>...");
                        setMessage(MessageType.INFO, "GeneralRules::verifyScoOptional()", "Testing element " + elemNum + ".9 <aggregationlevel> for multiplicity...");
                        int i1 = getMultiplicityUsed(generalNode, s);
                        if(i1 > 1)
                        {
                            setMessage(MessageType.FAILED, "GeneralRules::verifyScoOptional()", "More than 1 <aggregationlevel> element was found .. 0 or 1 allowed");
                            flag3 = false;
                        } else
                        if(i1 == 1)
                        {
                            setMessage(MessageType.PASSED, "GeneralRules::verifyScoOptional()", "0 or 1 <aggregationlevel> element was found");
                            if(!verifyAggregationlevel(node1))
                                flag3 = false;
                        }
                        flag7 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("identifier"))
                {
                    flag8 = false;
                    setMessage(MessageType.INFO, "GeneralRules::verifyScoOptional()", "Testing element 1 <general> for reserved element <identifier>..");
                    setMessage(MessageType.FAILED, "GeneralRules::verifyScoOptional()", "Element 1.1 <identifier> was found and is a Reserved element");
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag10 = flag && flag1 && flag2 && flag3;
            System.out.println("returning ->" + flag10);
        }
        return flag && flag1 && flag2 && flag3 && flag8;
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    private boolean verifyTitle(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<title>", s, 1000);
        return flag;
    }

    private boolean verifyDescription(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<description>", s, 2000);
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
                setMessage(MessageType.FAILED, "GeneralRules::verifyLanguage()", "No data was found in element <language> and fails the minimum character length test");
            } else
            if(i == 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        setMessage(MessageType.INFO, "GeneralRules::verifyLanguage()", "Testing element " + s + " <language> for " + "character length...");
                        String s2 = getTaggedData(node);
                        if(DebugIndicator.ON)
                            System.out.println("1<= " + s2.length() + " <= " + byte0);
                        if(s2.length() > byte0)
                            setMessage(MessageType.WARNING, "GeneralRules::verifyLanguage()", "The text contained in element " + s + " <language> is greater than " + byte0);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "GeneralRules::verifyLanguage()", "No text was found in element " + s + " <language> and fails the minimum " + "character length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "GeneralRules::verifyLanguage()", "Character length for element " + s + " <language> has been verified");
                        }
                    }
                }

            } else
            {
                flag = false;
                if(DebugIndicator.ON)
                    System.out.println("There were " + i + " TEXT_NODE elements found");
                setMessage(MessageType.FAILED, "GeneralRules::verifyLanguage()", "Too many text strings were found in <language> and fails the test");
            }
        }
        return flag;
    }

    private boolean verifyKeyword(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<keyword>", s, 1000);
        return flag;
    }

    private boolean verifyCoverage(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<coverage>", s, 1000);
        return flag;
    }

    private boolean verifyStructure(Node node)
    {
        boolean flag = true;
        boolean flag1 = true;
        Vector vector = buildVocabStructure();
        flag = verifyVocabulary(node, vector, "structure", flag1);
        return flag;
    }

    private boolean verifyAggregationlevel(Node node)
    {
        boolean flag = true;
        boolean flag1 = true;
        Vector vector = buildVocabAggregationlevel();
        flag = verifyVocabulary(node, vector, "aggregationlevel", flag1);
        return flag;
    }

    private Vector buildVocabStructure()
    {
        Vector vector = new Vector(8);
        vector.add("Collection");
        vector.add("Mixed");
        vector.add("Linear");
        vector.add("Hierarchical");
        vector.add("Networked");
        vector.add("Branched");
        vector.add("Parceled");
        vector.add("Atomic");
        return vector;
    }

    private Vector buildVocabAggregationlevel()
    {
        Vector vector = new Vector(4);
        vector.add("1");
        vector.add("2");
        vector.add("3");
        vector.add("4");
        return vector;
    }

    public boolean getOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private GeneralRules()
    {
        optionalNotUsed = true;
    }
}
