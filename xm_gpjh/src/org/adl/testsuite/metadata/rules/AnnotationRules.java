// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AnnotationRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.testsuite.metadata.rules:
//            DatetypeRules

public class AnnotationRules extends MetadataUtil
{

    private String elemNum;
    private Node annotationNode;
    private boolean optionalNotUsed;

    public AnnotationRules(Node node, String s)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println();
            System.out.println("/////////////////////////////////");
            System.out.println("AnnotationRules() called");
            System.out.println("/////////////////////////////////");
            System.out.println();
        }
        annotationNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        if(DebugIndicator.ON)
            System.out.println("Element " + elemNum + " <annotation> has no " + "mandatory sub-elements");
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
        boolean flag3 = false;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = true;
        NodeList nodelist = annotationNode.getChildNodes();
        Node node = annotationNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("person"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "AnnotationRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <person>...");
                        setMessage(MessageType.INFO, "AnnotationRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <person> for multiplicity...");
                        int j = getMultiplicityUsed(annotationNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "AnnotationRules::verifyAssetOptional()", "More than 1 <person> element was found .. 0 or 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "AnnotationRules::verifyAssetOptional()", "0 or 1 <person> element was found");
                            if(!verifyPerson(node1, elemNum + ".1"))
                                flag = false;
                        }
                        flag3 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("date"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "AnnotationRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <date>...");
                        setMessage(MessageType.INFO, "AnnotationRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <date> for multiplicity...");
                        int k = getMultiplicityUsed(annotationNode, s);
                        if(k > 1)
                        {
                            setMessage(MessageType.FAILED, "AnnotationRules::verifyAssetOptional()", "More than 1 <date> element was found .. 0 or 1 allowed");
                            flag1 = false;
                        } else
                        if(k == 1)
                        {
                            setMessage(MessageType.PASSED, "AnnotationRules::verifyAssetOptional()", "0 or 1 <date> element was found");
                            if(!verifyDate(node1, elemNum + ".2"))
                                flag1 = false;
                        }
                        flag4 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("description"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "AnnotationRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <description>...");
                        setMessage(MessageType.INFO, "AnnotationRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <description> for multiplicity...");
                        int l = getMultiplicityUsed(annotationNode, s);
                        if(l > 1)
                        {
                            setMessage(MessageType.FAILED, "AnnotationRules::verifyAssetOptional()", "More than 1 <description> element was found .. 0 or 1 allowed");
                            flag2 = false;
                        } else
                        if(l == 1)
                        {
                            setMessage(MessageType.PASSED, "AnnotationRules::verifyAssetOptional()", "0 or 1 <description> element was found");
                            if(!verifyDescription(node1, elemNum + ".3"))
                                flag2 = false;
                        }
                        flag5 = true;
                    }
                    optionalNotUsed = false;
                }
            }
        }

        boolean flag7;
        if(DebugIndicator.ON)
            flag7 = flag && flag1 && flag2;
        return flag && flag1 && flag2;
    }

    public boolean verifyScoOptional()
    {
        return verifyAssetOptional();
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    private boolean verifyPerson(Node node, String s)
    {
        boolean flag = true;
        String s1 = new String();
        boolean flag1 = false;
        String s3 = new String();
        char c = '\u03E8';
        Node node1 = node;
        NodeList nodelist = node.getChildNodes();
        if(nodelist != null)
        {
            for(int i = 0; i < nodelist.getLength(); i++)
            {
                Node node2 = nodelist.item(i);
                if(node2.getNodeType() == 1)
                {
                    String s2 = node2.getLocalName();
                    if(s2.equalsIgnoreCase("vcard"))
                    {
                        if(!flag1)
                        {
                            setMessage(MessageType.INFO, "AnnotationRules::verifyPerson()", "Testing element <vcard>");
                            flag1 = true;
                        }
                        Node node3 = nodelist.item(i);
                        NodeList nodelist1 = node3.getChildNodes();
                        if(nodelist1 != null)
                        {
                            for(int j = 0; j < nodelist1.getLength(); j++)
                            {
                                Node node4 = nodelist1.item(j);
                                if(node4.getNodeType() == 3 || node4.getNodeType() == 4)
                                {
                                    setMessage(MessageType.INFO, "AnnotationRules()::verifyPerson()", "Testing element <vcard> for character length..");
                                    s3 = s3 + getTaggedData(node2);
                                }
                            }

                            if(DebugIndicator.ON)
                                System.out.println("1<= " + s3.length() + " <= " + (int)c);
                            if(s3.length() > c)
                                setMessage(MessageType.WARNING, "AnnotationRules::verifyPerson()", "The text contained in element " + s + "<person> is greater" + " than " + (int)c);
                            else
                            if(s3.length() < 1)
                            {
                                flag = false;
                                setMessage(MessageType.FAILED, "AnnotationRules::verifyPerson()", "No text was found in element " + s + " <person> and fails the minimum " + "character length test");
                            } else
                            {
                                setMessage(MessageType.PASSED, "AnnotationRules::verifyPerson()", "Character length for element " + s + " <person> has been verified");
                            }
                        }
                    }
                }
            }

            if(!flag1)
            {
                flag = false;
                setMessage(MessageType.FAILED, "AnnotationRules::verifyPerson()", "No vcard element was found in element " + s + " <person>");
            }
        }
        return flag;
    }

    private boolean verifyDate(Node node, String s)
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = false;
        boolean flag3 = false;
        setMessage(MessageType.INFO, "AnnotationRules::verifyDate()", "Testing element " + s + " <date> for " + "Element <datetime>...");
        NodeList nodelist = node.getChildNodes();
        Node node1 = node;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node2 = nodelist.item(i);
            if(node2.getNodeType() == 1)
            {
                String s1 = node2.getLocalName();
                if(s1.equalsIgnoreCase("datetime"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.INFO, "AnnotationRules::verifyDate()", "Testing element <datetime> for multiplicity...");
                        setMessage(MessageType.PASSED, "AnnotationRules::verifyDate()", "Element <datetime> existed 1 and only 1 time");
                        flag2 = true;
                    }
                    DatetypeRules datetyperules = new DatetypeRules(node2);
                    flag = datetyperules.verifyDatetime();
                    appendMessages(datetyperules.getMessages());
                } else
                if(s1.equalsIgnoreCase("description"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.INFO, "AnnotationRules::verifyDate()", "Testing element <description>...");
                        setMessage(MessageType.INFO, "AnnotationRules::verifyDate()", "Testing element <description> for multiplicity...");
                        setMessage(MessageType.PASSED, "AnnotationRules::verifyDate()", "Element <description> existed 0 or 1 time");
                        flag3 = true;
                    }
                    DatetypeRules datetyperules1 = new DatetypeRules(node2);
                    flag1 = datetyperules1.verifyDescription();
                    appendMessages(datetyperules1.getMessages());
                }
            }
        }

        if(!flag2)
        {
            setMessage(MessageType.INFO, "AnnotationRules::verifyRole()", "Testing element <datetime> for multiplicity...");
            setMessage(MessageType.FAILED, "AnnotationRules::verifyRole()", "Element <datetime> was not found and must appear 1 and only 1 time");
        }
        return flag1 && flag;
    }

    private boolean verifyDescription(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<description>", s, 1000);
        return flag;
    }

    public boolean getOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private AnnotationRules()
    {
        optionalNotUsed = true;
    }
}
