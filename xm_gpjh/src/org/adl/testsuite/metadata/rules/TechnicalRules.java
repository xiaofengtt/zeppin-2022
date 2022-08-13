// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   TechnicalRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.testsuite.metadata.rules:
//            RequirementRules, DatetypeRules

public class TechnicalRules extends MetadataUtil
{

    private String elemNum;
    private Node technicalNode;
    private boolean optionalNotUsed;

    public TechnicalRules(Node node, String s)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("TechnicalRules() called");
            System.out.println("/////////////////////////////////");
        }
        technicalNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = false;
        boolean flag3 = false;
        NodeList nodelist = technicalNode.getChildNodes();
        Node node = technicalNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("format"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetMandatory()", "Testing element " + elemNum + ".1 <format>...");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetMandatory()", "Testing element " + elemNum + ".1 <format> for multiplicity...");
                        int j = getMultiplicityUsed(technicalNode, s);
                        if(j > 40)
                        {
                            setMessage(MessageType.WARNING, "TechnicalRules::verifyAssetMandatory()", "More than 40 <format> elements were found");
                            if(!verifyFormat(node1, elemNum + ".1"))
                                flag = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "TechnicalRules::verifyAssetMandatory()", "40 or less <format> elements were found");
                            if(!verifyFormat(node1, elemNum + ".1"))
                                flag = false;
                        }
                        flag2 = true;
                    }
                } else
                if(s.equalsIgnoreCase("location"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetMandatory()", "Testing element " + elemNum + ".3 <location>...");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetMandatory()", "Testing element " + elemNum + ".3 <location> for multiplicity...");
                        int k = getMultiplicityUsed(technicalNode, s);
                        if(k > 10)
                            setMessage(MessageType.WARNING, "TechnicalRules::verifyAssetMandatory()", "More than 10 <location> elements were found");
                        else
                            setMessage(MessageType.PASSED, "TechnicalRules::verifyAssetMandatory()", "10 or less <location> elements were found");
                        flag3 = true;
                    }
                    if(!verifyLocation(node1, elemNum + ".3"))
                        flag1 = false;
                }
            }
        }

        if(!flag2)
        {
            setMessage(MessageType.INFO, "TechnicalRules::verifyAssetMandatory()", "Testing element " + elemNum + ".1 <format> for multiplicity...");
            setMessage(MessageType.FAILED, "TechnicalRules::verifyAssetMandatory()", "Element " + elemNum + ".1 <format> was not found and " + "must appear 1 or more times");
        }
        if(!flag3)
        {
            setMessage(MessageType.INFO, "TechnicalRules::verifyAssetMandatory()", "Testing element " + elemNum + ".3 <location> for multiplicity...");
            setMessage(MessageType.FAILED, "TechnicalRules::verifyAssetMandatory()", "element " + elemNum + ".3 <location> was not found and " + "must appear 1 or more times");
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
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean flag7 = false;
        boolean flag8 = false;
        boolean flag9 = false;
        boolean flag10 = true;
        NodeList nodelist = technicalNode.getChildNodes();
        Node node = technicalNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("size"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <size>...");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetOptional()", "Testing element 4.2 <size> for multiplicity...");
                        int j = getMultiplicityUsed(technicalNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "TechnicalRules::verifyAssetOptional()", "More than 1 <size> element was found...0 or 1 allowed");
                            flag = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "TechnicalRules::verifyAssetOptional()", "0 or 1 <size> element was found");
                            if(!verifySize(node1, elemNum + ".2"))
                                flag = false;
                        }
                        flag5 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("requirement"))
                {
                    if(!flag6)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetOptional()", "Testing element " + elemNum + ".4 <requirement>...");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetOptional()", "Testing element " + elemNum + ".4 <requirement> for multiplicity...");
                        int k = getMultiplicityUsed(technicalNode, s);
                        if(k > 40)
                            setMessage(MessageType.WARNING, "TechnicalRules::verifyAssetOptional()", "More than 40 <requirement> elements were found");
                        else
                            setMessage(MessageType.PASSED, "TechnicalRules::verifyAssetOptional()", "40 or less <requirement> elements were found");
                        flag6 = true;
                    }
                    RequirementRules requirementrules = new RequirementRules(node1, elemNum + ".4");
                    boolean flag11 = requirementrules.verifyAssetOptional();
                    if(!flag11)
                        flag1 = false;
                    appendMessages(requirementrules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("installationremarks"))
                {
                    if(!flag7)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetOptional()", "Testing element " + elemNum + ".5 <installationremarks>...");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetOptional()", "Testing element " + elemNum + ".5 <installationremarks> for multiplicity...");
                        int l = getMultiplicityUsed(technicalNode, s);
                        if(l > 1)
                        {
                            setMessage(MessageType.FAILED, "TechnicalRules::verifyAssetOptional()", "More than 1 <installationremarks> element was found...0 or 1 allowed");
                            flag2 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "TechnicalRules::verifyAssetOptional()", "0 or 1 <installationremarks> element was found");
                            if(!verifyInstallationremarks(node1, elemNum + ".5"))
                                flag2 = false;
                        }
                        flag7 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("otherplatformrequirements"))
                {
                    if(!flag8)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetOptional()", "Testing element " + elemNum + ".6 <otherplatformrequirements>...");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetOptional()", "Testing element " + elemNum + ".6 <otherplatformrequirements> for " + "multiplicity...");
                        int i1 = getMultiplicityUsed(technicalNode, s);
                        if(i1 > 1)
                        {
                            setMessage(MessageType.FAILED, "TechnicalRules::verifyAssetOptional()", "More than 1 <otherplatformrequirements> element was found...0 or 1 allowed");
                            flag3 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "TechnicalRules::verifyAssetOptional()", "0 or 1 <otherplatformrequirements> element was found");
                            if(!verifyOtherplatformrequirements(node1, elemNum + ".6"))
                                flag3 = false;
                        }
                        flag8 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("duration"))
                {
                    if(!flag9)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetOptional()", "Testing element " + elemNum + ".7 <duration>...");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyAssetOptional()", "Testing element " + elemNum + ".7 <duration> for multiplicity...");
                        int j1 = getMultiplicityUsed(technicalNode, s);
                        if(j1 > 1)
                        {
                            setMessage(MessageType.FAILED, "TechnicalRules::verifyAssetOptional()", "More than 1 <duration> element was found...0 or 1 allowed");
                            flag4 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "TechnicalRules::verifyAssetOptional()", "0 or 1 <duration> element was found");
                            if(!verifyDuration(node1, elemNum + ".7"))
                                flag4 = false;
                        }
                        flag9 = true;
                    }
                    optionalNotUsed = false;
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag12 = flag && flag1 && flag2 && flag3 && flag4;
            System.out.println("returning ->" + flag12);
        }
        return flag && flag1 && flag2 && flag3 && flag4;
    }

    public boolean verifyScoOptional()
    {
        return verifyAssetOptional();
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    private boolean verifyFormat(Node node, String s)
    {
        boolean flag = true;
        char c = '\u01F4';
        int i = 0;
        String s1 = new String();
        NodeList nodelist = node.getChildNodes();
        Node node1 = node;
        String s3 = new String();
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
                setMessage(MessageType.FAILED, "TechnicalRules::verifyFormat()", "No data was found in element <format> and fails the minimum character length test");
            } else
            if(i == 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        setMessage(MessageType.INFO, "TechnicalRules::verifyFormat()", "Testing element " + s + " <format> for " + "character length...");
                        String s2 = getTaggedData(node);
                        if(DebugIndicator.ON)
                            System.out.println("1<= " + s2.length() + " <= " + (int)c);
                        if(s2.length() > c)
                            setMessage(MessageType.WARNING, "TechnicalRules::verifyFormat()", "The text contained in element " + s + " <format> is greater than " + (int)c);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "TechnicalRules::verifyFormat()", "No text was found in element " + s + " <format> and fails the minimum " + "character length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "TechnicalRules::verifyFormat()", "Character length for element " + s + " <format> has been verified");
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

    private boolean verifySize(Node node, String s)
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
                setMessage(MessageType.FAILED, "TechnicalRules::verifySize()", "No data was found in element <size> and fails the minimum character length test");
            } else
            if(i == 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        setMessage(MessageType.INFO, "TechnicalRules::verifySize()", "Testing element " + s + " <size> for " + "digital resource in bytes...");
                        String s2 = getTaggedData(node);
                        try
                        {
                            Long long1 = new Long(s2);
                            if(long1.compareTo(new Long("0")) < 0)
                            {
                                setMessage(MessageType.FAILED, "TechnicalRules::verifySize()", "Text in element " + s + " <size> " + "was not a byte and fails the digital " + "resource in bytes test");
                                setMessage(MessageType.FAILED, "TechnicalRules::verifySize()", "value = " + s2);
                                flag = false;
                            }
                        }
                        catch(NumberFormatException numberformatexception)
                        {
                            setMessage(MessageType.FAILED, "TechnicalRules::verifySize()", "Text in element " + s + " <size> " + "was not a byte and fails the digital " + "resource in bytes test");
                            setMessage(MessageType.FAILED, "TechnicalRules::verifySize()", "value = " + s2);
                            flag = false;
                        }
                        if(flag)
                            setMessage(MessageType.PASSED, "TechnicalRules::verifySize()", "Digital resource in bytes for element " + s + " <size> has been verified");
                        setMessage(MessageType.INFO, "TechnicalRules::verifySize()", "Testing element " + s + " <size> for " + "character length...");
                        if(DebugIndicator.ON)
                            System.out.println("1>= " + s2.length() + " <= " + byte0);
                        if(s2.length() > byte0)
                            setMessage(MessageType.WARNING, "TechnicalRules::verifySize()", "The text contained in element " + s + " <size> is greater than " + byte0);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "TechnicalRules::verifySize()", "No text was found in element " + s + " <size> and fails the minimum character " + "length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "TechnicalRules::verifySize()", "Character length for element  <size> has been verified");
                        }
                    }
                }

            } else
            {
                flag = false;
                if(DebugIndicator.ON)
                    System.out.println("There were " + i + " TEXT_NODE elements found");
                setMessage(MessageType.FAILED, "TechnicalRules::verifySize()", "Too many text strings were found in <size> and fails the test");
            }
        }
        return flag;
    }

    private boolean verifyLocation(Node node, String s)
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
                setMessage(MessageType.FAILED, "TechnicalRules::verifyLocation()", "No data was found in element <location> and fails the minimum character length test");
            } else
            if(i == 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        String s2 = getTaggedData(node);
                        setMessage(MessageType.INFO, "TechnicalRules::verifyLocation()", "Testing text of element " + s + " <location> for character length...");
                        if(DebugIndicator.ON)
                            System.out.println("1<= " + s2.length() + " <= " + (int)c);
                        if(s2.length() > c)
                            setMessage(MessageType.WARNING, "TechnicalRules::verifyLocation()", "The text contained in element " + s + " <location> is greater than " + (int)c);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "TechnicalRules::verifyLocation()", "No text was found in element " + s + " <location> and fails the minimum " + "character length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "TechnicalRules::verifyLocation()", "Character length for element  <location> has been verified");
                        }
                    }
                }

            } else
            {
                flag = false;
                if(DebugIndicator.ON)
                    System.out.println("There were " + i + " TEXT_NODE elements found");
                setMessage(MessageType.FAILED, "TechnicalRules::verifyLocation()", "Too many text strings were found in <location> and fails the test");
            }
        }
        return flag;
    }

    private boolean verifyInstallationremarks(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<installationremarks>", "4.5", 1000);
        return flag;
    }

    private boolean verifyOtherplatformrequirements(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "otherplatformrequirements", "4.6", 1000);
        return flag;
    }

    private boolean verifyDuration(Node node, String s)
    {
        boolean flag = true;
        boolean flag3 = true;
        boolean flag4 = true;
        boolean flag5 = false;
        boolean flag6 = false;
        setMessage(MessageType.INFO, "TechnicalRules::verifyTypicallearningtime()", "Testing element " + s + " <duration> for " + "element <datetime>...");
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
                    if(!flag5)
                    {
                        setMessage(MessageType.INFO, "TechnicalRules::verifyTypicallearningtime()", "Testing element <datetime> for multiplicity...");
                        setMessage(MessageType.PASSED, "TechnicalRules::verifyTypicallearningtime()", "Element <datetime> existed 1 and only 1 time");
                        flag5 = true;
                    }
                    DatetypeRules datetyperules = new DatetypeRules(node2);
                    boolean flag1 = datetyperules.verifyDatetime();
                    if(!flag1)
                        flag3 = false;
                    appendMessages(datetyperules.getMessages());
                } else
                if(s1.equalsIgnoreCase("description"))
                {
                    if(!flag6)
                    {
                        setMessage(MessageType.INFO, "TechnicalRules::verifyTypicallearningtime()", "Testing element <description>...");
                        setMessage(MessageType.INFO, "TechnicalRules::verifyTypicallearningtime()", "Testing element <description> for multiplicity...");
                        setMessage(MessageType.PASSED, "TechnicalRules::verifyTypicallearningtime()", "Element <description> existed 0 or 1 time");
                        flag6 = true;
                    }
                    DatetypeRules datetyperules1 = new DatetypeRules(node2);
                    boolean flag2 = datetyperules1.verifyDescription();
                    if(!flag2)
                        flag4 = false;
                    appendMessages(datetyperules1.getMessages());
                }
            }
        }

        if(!flag5)
        {
            setMessage(MessageType.INFO, "TechnicalRules::verifyRole()", "Testing element <datetime> for multiplicity...");
            setMessage(MessageType.FAILED, "TechnicalRules::verifyRole()", "Element <datetime> was not found and must appear 1 and only 1 time");
        }
        return flag4 && flag3;
    }

    public boolean getOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private TechnicalRules()
    {
        optionalNotUsed = true;
    }
}
