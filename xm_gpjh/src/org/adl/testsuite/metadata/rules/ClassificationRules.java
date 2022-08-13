// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ClassificationRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import java.util.Vector;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.testsuite.metadata.rules:
//            TaxonpathRules

public class ClassificationRules extends MetadataUtil
{

    private String elemNum;
    private Node classificationNode;
    private boolean optionalNotUsed;

    public ClassificationRules(Node node, String s)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("ClassificationRules() called");
            System.out.println("/////////////////////////////////");
        }
        classificationNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        if(DebugIndicator.ON)
            System.out.println("element " + elemNum + " <classification> has no " + "mandatory sub-elements");
        return false;
    }

    public boolean verifyScoMandatory()
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = false;
        boolean flag4 = false;
        boolean flag5 = false;
        NodeList nodelist = classificationNode.getChildNodes();
        Node node = classificationNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("purpose"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyScoMandatory()", "Testing element " + elemNum + ".1 <purpose>...");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyScoMandatory()", "Testing element " + elemNum + ".1 <purpose> for multiplicity...");
                        int j = getMultiplicityUsed(classificationNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "AnnotationRules::verifyScoMandatory()", "More than 1 <purpose> element was found .. 0 or 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "ClassificationRules::verifyScoMandatory()", "0 or 1 <purpose> element was found");
                            if(!verifyPurpose(node1))
                                flag = false;
                        }
                        flag3 = true;
                    }
                } else
                if(s.equalsIgnoreCase("description"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyScoMandatory()", "Testing element " + elemNum + ".3 <description>...");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyScoMandatory()", "Testing element " + elemNum + ".3 <description> for multiplicity...");
                        int k = getMultiplicityUsed(classificationNode, s);
                        if(k > 1)
                        {
                            setMessage(MessageType.FAILED, "AnnotationRules::verifyScoMandatory()", "More than 1 <description> element was found .. 0 or 1 allowed");
                            flag1 = false;
                        } else
                        if(k == 1)
                        {
                            setMessage(MessageType.PASSED, "ClassificationRules::verifyScoMandatory()", "0 or 1 <description> element was found");
                            if(!verifyDescription(node1, elemNum + ".3"))
                                flag1 = false;
                        }
                        flag4 = true;
                    }
                } else
                if(s.equalsIgnoreCase("keyword"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyScoMandatory()", "Testing element " + elemNum + ".4 <keyword>...");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyScoMandatory()", "Testing element element " + elemNum + ".4 <keyword> for multiplicity...");
                        int l = getMultiplicityUsed(classificationNode, s);
                        if(l > 40)
                            setMessage(MessageType.WARNING, "ClassificationRules::verifyScoMandatory()", "More than 40 <keyword> elements were found");
                        else
                            setMessage(MessageType.PASSED, "ClassificationRules::verifyScoMandatory()", "40 or less <keyword> elements were found");
                        flag5 = true;
                    }
                    if(!verifyKeyword(node1, elemNum + ".4"))
                        flag2 = false;
                }
            }
        }

        if(!flag3)
        {
            setMessage(MessageType.INFO, "ClassificationRules::verifyScoMandatory()", "Testing element " + elemNum + ".1 <purpose> for multiplicity...");
            setMessage(MessageType.FAILED, "ClassificationRules::verifyScoMandatory()", "Element " + elemNum + ".1 <purpose> was not found and " + "must appear 1 and only 1 time");
        }
        if(!flag4)
        {
            setMessage(MessageType.INFO, "ClassificationRules::verifyScoMandatory()", "Testing element " + elemNum + ".3 <description> for multiplicity...");
            setMessage(MessageType.FAILED, "ClassificationRules::verifyScoMandatory()", "Element " + elemNum + ".3 <description> was not found and " + "must appear 1 and only 1 time");
        }
        if(!flag5)
        {
            setMessage(MessageType.INFO, "ClassificationRules::verifyScoMandatory()", "Testing element " + elemNum + ".4 <keyword> for " + "multiplicity...");
            setMessage(MessageType.FAILED, "ClassificationRules::verifyScoMandatory()", "Element " + elemNum + ".4 <keyword> was not found and " + "must appear 1 or more times");
        }
        if(DebugIndicator.ON)
        {
            boolean flag6 = flag && flag1 && flag2 && flag3 && flag4 && flag5;
            System.out.println("returning ->" + flag6);
        }
        return flag && flag1 && flag2 && flag3 && flag4 && flag5;
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
        boolean flag8 = true;
        NodeList nodelist = classificationNode.getChildNodes();
        Node node = classificationNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("purpose"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <purpose>...");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <purpose> for multiplicity...");
                        int j = getMultiplicityUsed(classificationNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "AnnotationRules::verifyAssetOptional()", "More than 1 <purpose> element was found .. 0 or 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "ClassificationRules::verifyAssetOptional()", "0 or 1 <purpose> element was found");
                            if(!verifyPurpose(node1))
                                flag = false;
                        }
                        flag4 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("taxonpath"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <taxonpath>...");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <taxonpath> for multiplicity...");
                        int k = getMultiplicityUsed(classificationNode, s);
                        if(k > 15)
                            setMessage(MessageType.WARNING, "ClassificationRules::verifyAssetOptional()", "More than 15 <taxonpath> elements were found");
                        else
                            setMessage(MessageType.PASSED, "ClassificationRules::verifyAssetOptional()", "15 or less <taxonpath> elements were found");
                        flag5 = true;
                    }
                    TaxonpathRules taxonpathrules = new TaxonpathRules(node1, elemNum + ".2");
                    boolean flag9 = taxonpathrules.verifyAssetOptional();
                    if(!flag9)
                        flag1 = false;
                    appendMessages(taxonpathrules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("description"))
                {
                    if(!flag6)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <description>...");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <description> for multiplicity...");
                        int l = getMultiplicityUsed(classificationNode, s);
                        if(l > 1)
                        {
                            setMessage(MessageType.FAILED, "AnnotationRules::verifyAssetOptional()", "More than 1 <description> element was found .. 0 or 1 allowed");
                            flag2 = false;
                        } else
                        if(l == 1)
                        {
                            setMessage(MessageType.PASSED, "ClassificationRules::verifyAssetOptional()", "0 or 1 <description> element was found");
                            if(!verifyDescription(node1, elemNum + ".3"))
                                flag2 = false;
                        }
                        flag6 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("keyword"))
                {
                    if(!flag7)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyAssetOptional()", "Testing element " + elemNum + ".4 <keyword>...");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyAssetOptional()", "Testing element element " + elemNum + ".4 <keyword> for multiplicity...");
                        int i1 = getMultiplicityUsed(classificationNode, s);
                        if(i1 > 40)
                            setMessage(MessageType.WARNING, "ClassificationRules::verifyAssetOptional()", "More than 40 <keyword> elements were found");
                        else
                            setMessage(MessageType.PASSED, "ClassificationRules::verifyAssetOptional()", "40 or less <keyword> elements were found");
                        flag7 = true;
                    }
                    if(!verifyKeyword(node1, elemNum + ".4"))
                        flag3 = false;
                    optionalNotUsed = false;
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag10 = flag && flag1 && flag2 && flag3;
            System.out.println("returning ->" + flag10);
        }
        return flag && flag1 && flag2 && flag3;
    }

    public boolean verifyScoOptional()
    {
        boolean flag = true;
        boolean flag1 = false;
        boolean flag2 = true;
        NodeList nodelist = classificationNode.getChildNodes();
        Node node = classificationNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("taxonpath"))
                {
                    if(!flag1)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyScoOptional()", "Testing element " + elemNum + ".2 <taxonpath>...");
                        setMessage(MessageType.INFO, "ClassificationRules::verifyScoOptional()", "Testing element " + elemNum + ".2 <taxonpath> for multiplicity...");
                        int j = getMultiplicityUsed(classificationNode, s);
                        if(j > 15)
                            setMessage(MessageType.WARNING, "ClassificationRules::verifyScoOptional()", "More than 15 <taxonpath> elements were found");
                        else
                            setMessage(MessageType.PASSED, "ClassificationRules::verifyScoOptional()", "15 or less <taxonpath> elements were found");
                        flag1 = true;
                    }
                    TaxonpathRules taxonpathrules = new TaxonpathRules(node1, elemNum + ".2");
                    boolean flag3 = taxonpathrules.verifyScoOptional();
                    if(!flag3)
                        flag = false;
                    appendMessages(taxonpathrules.getMessages());
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

    private boolean verifyPurpose(Node node)
    {
        boolean flag = true;
        boolean flag1 = false;
        Vector vector = buildVocabPurpose();
        flag = verifyVocabulary(node, vector, "purpose", flag1);
        return flag;
    }

    private boolean verifyDescription(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<description>", s, 2000);
        return flag;
    }

    private boolean verifyKeyword(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<keyword>", s, 1000);
        return flag;
    }

    private Vector buildVocabPurpose()
    {
        Vector vector = new Vector(8);
        vector.add("Discipline");
        vector.add("Idea");
        vector.add("Prerequisite");
        vector.add("Educational Objective");
        vector.add("Accessibility Restrictions");
        vector.add("Educational Level");
        vector.add("Skill Level");
        vector.add("Security Level");
        return vector;
    }

    public boolean getOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private ClassificationRules()
    {
        optionalNotUsed = true;
    }
}
