// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LifecycleRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import java.util.Vector;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.testsuite.metadata.rules:
//            ContributeRules

public class LifecycleRules extends MetadataUtil
{

    private String elemNum;
    private Node lifecycleNode;
    private boolean optionalNotUsed;

    public LifecycleRules(Node node, String s)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("LifecycleRules() called");
            System.out.println("/////////////////////////////////");
        }
        lifecycleNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        if(DebugIndicator.ON)
            System.out.println("element " + elemNum + " <lifecycle> has no " + "mandatory sub-elements");
        return false;
    }

    public boolean verifyScoMandatory()
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = false;
        boolean flag3 = false;
        NodeList nodelist = lifecycleNode.getChildNodes();
        Node node = lifecycleNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("version"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyScoMandatory()", "Testing element " + elemNum + ".1 <version>...");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyScoMandatory()", "Testing element " + elemNum + ".1 <version> for multiplicity...");
                        int j = getMultiplicityUsed(lifecycleNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "LifecycleRules::verifyAssetOptional()", "More than 1 <version> element was found .. 0 or 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "LifecycleRules::verifyAssetOptional()", "0 or 1 <version> element was found");
                            if(!verifyVersion(node1, elemNum + ".1"))
                                flag = false;
                        }
                    }
                    flag2 = true;
                } else
                if(s.equalsIgnoreCase("status"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyScoMandatory()", "Testing element " + elemNum + ".2 <status>...");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyScoMandatory()", "Testing element " + elemNum + ".2 <status> for multiplicity...");
                        setMessage(MessageType.PASSED, "LifecycleRules::verifyScoMandatory()", "Element " + elemNum + ".2 <status> existed 1 and only 1 time");
                        flag3 = true;
                    }
                    if(!verifyStatus(node1))
                        flag1 = false;
                }
            }
        }

        if(!flag2)
        {
            setMessage(MessageType.INFO, "LifecycleRules::verifyScoMandatory()", "Testing element " + elemNum + ".1 <version> for multiplicity...");
            setMessage(MessageType.FAILED, "LifecycleRules::verifyScoMandatory()", "Element " + elemNum + ".1 <version> was not found and " + "must appear 1 and only 1 time");
        }
        if(!flag3)
        {
            setMessage(MessageType.INFO, "LifecycleRules::verifyScoMandatory()", "Testing element " + elemNum + ".2 <status> for multiplicity...");
            setMessage(MessageType.FAILED, "LifecycleRules::verifyScoMandatory()", "Element " + elemNum + ".2 <status> was not found and " + "must appear 1 and only 1 time");
        }
        if(DebugIndicator.ON)
        {
            boolean flag4 = flag && flag1 && flag2 && flag3;
            System.out.println("returning ->" + flag4);
        }
        return flag && flag1 && flag2 && flag3;
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
        NodeList nodelist = lifecycleNode.getChildNodes();
        Node node = lifecycleNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("version"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <version>...");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <version> for multiplicity...");
                        int j = getMultiplicityUsed(lifecycleNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "LifecycleRules::verifyAssetOptional()", "More than 1 <version> element was found .. 0 or 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "LifecycleRules::verifyAssetOptional()", "0 or 1 <version> element was found");
                            if(!verifyVersion(node1, elemNum + ".1"))
                                flag = false;
                        }
                        flag3 = true;
                    }
                } else
                if(s.equalsIgnoreCase("status"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <status>...");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <status> for multiplicity...");
                        setMessage(MessageType.PASSED, "LifecycleRules::verifyAssetOptional()", "Element " + elemNum + ".2 <status> existed 1 and only 1 time");
                        flag4 = true;
                    }
                    if(!verifyStatus(node1))
                        flag1 = false;
                } else
                if(s.equalsIgnoreCase("contribute"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <contribute>...");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <contribute> for multiplicity...");
                        int k = getMultiplicityUsed(lifecycleNode, s);
                        if(k > 30)
                            setMessage(MessageType.WARNING, "LifecycleRules::verifyAssetOptional()", "More than 30 <contribute> elements were found");
                        else
                            setMessage(MessageType.PASSED, "LifecycleRules::verifyAssetOptional()", "30 or less <contribute> elements were found");
                        flag5 = true;
                    }
                    ContributeRules contributerules = new ContributeRules(node1, elemNum + ".3");
                    boolean flag7 = contributerules.verifyAssetOptionalLifecycle();
                    if(!flag7)
                        flag2 = false;
                    appendMessages(contributerules.getMessages());
                    optionalNotUsed = false;
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag8 = flag && flag1 && flag2;
            System.out.println("returning ->" + flag8);
        }
        return flag && flag1 && flag2;
    }

    public boolean verifyScoOptional()
    {
        boolean flag = true;
        boolean flag1 = false;
        boolean flag2 = true;
        NodeList nodelist = lifecycleNode.getChildNodes();
        Node node = lifecycleNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("contribute"))
                {
                    if(!flag1)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyScoOptional()", "Testing element " + elemNum + ".3 <contribute>...");
                        setMessage(MessageType.INFO, "LifecycleRules::verifyScoOptional()", "Testing element " + elemNum + ".3 <contribute> for multiplicity...");
                        int j = getMultiplicityUsed(lifecycleNode, s);
                        if(j > 30)
                            setMessage(MessageType.WARNING, "LifecycleRules::verifyScoOptional()", "More than 30 <contribute> elements were found");
                        else
                            setMessage(MessageType.PASSED, "LifecycleRules::verifyScoOptional()", "30 or less <contribute> elements were found");
                        flag1 = true;
                    }
                    ContributeRules contributerules = new ContributeRules(node1, elemNum + ".3");
                    boolean flag3 = contributerules.verifyAssetOptionalLifecycle();
                    if(!flag3)
                        flag = false;
                    appendMessages(contributerules.getMessages());
                    optionalNotUsed = false;
                }
            }
        }

        if(DebugIndicator.ON)
            System.out.println("returning ->" + flag);
        return flag;
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    private boolean verifyVersion(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<version>", s, 50);
        return flag;
    }

    private boolean verifyStatus(Node node)
    {
        boolean flag = true;
        boolean flag1 = true;
        Vector vector = buildVocabStatus();
        flag = verifyVocabulary(node, vector, "status", flag1);
        return flag;
    }

    private Vector buildVocabStatus()
    {
        Vector vector = new Vector(4);
        vector.add("Draft");
        vector.add("Final");
        vector.add("Revised");
        vector.add("Unavailable");
        return vector;
    }

    public boolean getOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private LifecycleRules()
    {
        optionalNotUsed = true;
    }
}
