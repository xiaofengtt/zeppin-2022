// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MetadataUtil.java

package org.adl.testsuite.metadata;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Vector;
import org.adl.util.Message;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.*;

public class MetadataUtil
{

    protected Vector messages;
    private boolean notBest;
    private String valueOfType;
    private boolean typeNotUsed;
    private boolean validTypeValue;
    private boolean typeAndNameResult;
    private int taxonCount;

    public MetadataUtil()
    {
        notBest = false;
        valueOfType = "";
        typeNotUsed = true;
        validTypeValue = true;
        typeAndNameResult = true;
        taxonCount = 0;
        messages = new Vector(0, 5);
    }

    public int getMultiplicityUsed(Node node, String s)
    {
        NodeList nodelist = node.getChildNodes();
        int i = 0;
        for(int j = 0; j < nodelist.getLength(); j++)
            if(nodelist.item(j).getNodeType() == 1)
            {
                String s1 = nodelist.item(j).getLocalName();
                if(s1.equalsIgnoreCase(s))
                    i++;
            }

        return i;
    }

    public int getTaxonMultiplicityUsed(Node node, String s)
    {
        NodeList nodelist = node.getChildNodes();
        for(int i = 0; i < nodelist.getLength(); i++)
            if(nodelist.item(i).getNodeType() == 1)
            {
                String s1 = nodelist.item(i).getLocalName();
                if(s1.equalsIgnoreCase(s))
                {
                    taxonCount++;
                    getTaxonMultiplicityUsed(nodelist.item(i), s);
                }
            }

        return taxonCount;
    }

    public boolean allContainAttributes(Vector vector)
    {
        boolean flag = false;
        String s = new String();
        Vector vector1 = new Vector();
        int i = 0;
        if(DebugIndicator.ON)
            System.out.println("MetadataUtil::allContainAttributes()Testing for required xml:lang attribute in  in multiple <langstring> occurrences");
        for(int j = 0; j < vector.size(); j++)
        {
            Node node = (Node)vector.elementAt(j);
            if(node.getNodeType() == 1)
            {
                NamedNodeMap namednodemap = node.getAttributes();
                int k = namednodemap.getLength();
                for(int l = 0; l < k; l++)
                {
                    Attr attr = (Attr)namednodemap.item(l);
                    String s1 = attr.getLocalName();
                    if(s1.equalsIgnoreCase("lang"))
                        i++;
                }

            }
        }

        if(i == vector.size())
            flag = true;
        if(DebugIndicator.ON)
            System.out.println("Returning " + flag + " from allContainAttributes()");
        return flag;
    }

    public boolean verifyLangAttributes(Vector vector)
    {
        boolean flag = true;
        boolean flag1 = false;
        String s = new String();
        String s2 = new String();
        String s4 = new String();
        Vector vector1 = new Vector();
        setMessage(MessageType.INFO, "MetadataUtil::verifyLangAttributes()", "Testing xml:lang value for element <langstring>...");
        for(int i = 0; i < vector.size(); i++)
        {
            Node node = (Node)vector.elementAt(i);
            if(node.getNodeType() == 1)
            {
                NamedNodeMap namednodemap = node.getAttributes();
                int l = namednodemap.getLength();
                for(int j1 = 0; j1 < l; j1++)
                {
                    Attr attr = (Attr)namednodemap.item(j1);
                    String s5 = attr.getLocalName();
                    if(s5.equalsIgnoreCase("lang"))
                    {
                        String s6 = attr.getValue();
                        vector1.add(s6);
                    }
                }

            }
        }

        if(!vector1.isEmpty())
        {
            flag1 = true;
            int j = vector1.size();
            for(int k = 0; k < j - 1; k++)
            {
                for(int i1 = k + 1; i1 < j; i1++)
                {
                    String s1 = (String)vector1.elementAt(k);
                    String s3 = (String)vector1.elementAt(i1);
                    if(s1.equals(s3))
                        flag = false;
                }

            }

        }
        if(!flag1)
            setMessage(MessageType.INFO, "MetadataUtil::verifyLangAttribute()", "<langstring> element did not contain the optional xml:lang attribute");
        return flag;
    }

    public boolean verifyLangstring(Node node, String s, String s1, int i)
    {
        boolean flag = true;
        boolean flag1 = false;
        boolean flag3 = true;
        boolean flag4 = false;
        int j = i;
        setMessage(MessageType.INFO, "MetadataUtil::verifyLangstring()", "Testing element " + s1 + " " + s + " for element <langstring>...");
        String s2 = new String();
        NodeList nodelist = node.getChildNodes();
        Node node1 = node;
        String s4 = new String();
        Vector vector = new Vector();
        for(int k = 0; k < nodelist.getLength(); k++)
        {
            Node node2 = nodelist.item(k);
            int l = getMultiplicityUsed(node, "langstring");
            if(node2.getNodeType() == 1)
            {
                String s5 = node2.getLocalName();
                if(s5.equalsIgnoreCase("langstring"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.INFO, "MetadataUtil::verifyLangstring()", "Testing element <langstring> for multiplicity...");
                        if(l > 10)
                            setMessage(MessageType.WARNING, "MetadataUtil::verifyLangstring()", "More than 10 <langstring> elements  were found ");
                        else
                            setMessage(MessageType.PASSED, "MetadataUtil::verifyLangstring()", "10 or less <langstring> elements  were found ");
                        flag4 = true;
                    }
                    vector.add(node2);
                }
            }
        }

        if(!vector.isEmpty())
        {
            int i1 = vector.size();
            if(i1 > 1)
            {
                boolean flag2 = allContainAttributes(vector);
                if(flag2)
                {
                    flag3 = verifyLangAttributes(vector);
                    if(!flag3)
                    {
                        setMessage(MessageType.FAILED, "MetadataUtil::verifyLangstring()", "Multiple <langstring> elements are required to contain a different xml:lang attribute values");
                        flag = false;
                    } else
                    {
                        setMessage(MessageType.PASSED, "MetadataUtil::verifyLangstring()", "Multiple <langstring> elements contained different xml:lang attribute values");
                    }
                } else
                {
                    setMessage(MessageType.FAILED, "MetadataUtil::verifyLangstring()", "Multiple <langstring> elements are required to contain the xml:lang attribute");
                    flag = false;
                }
            }
        }
        setMessage(MessageType.INFO, "MetadataUtil::verifyLangstring()", "Testing text of element <langstring> for character length...");
        boolean flag5 = false;
        for(int k1 = 0; k1 < vector.size(); k1++)
        {
            int j1 = 0;
            Node node3 = (Node)vector.elementAt(k1);
            NodeList nodelist1 = node3.getChildNodes();
            if(nodelist1 != null)
            {
                for(int l1 = 0; l1 < nodelist1.getLength(); l1++)
                {
                    Node node4 = nodelist1.item(l1);
                    if(node4.getNodeType() == 3 || node4.getNodeType() == 4)
                        j1++;
                }

                if(j1 == 0)
                {
                    flag = false;
                    setMessage(MessageType.FAILED, "MetadataUtil::verifyLangstring()", "No data was found in element " + s + " and fails the minimum " + "character length test");
                } else
                if(j1 == 1)
                {
                    for(int i2 = 0; i2 < nodelist1.getLength(); i2++)
                    {
                        Node node5 = nodelist1.item(i2);
                        if(node5.getNodeType() == 3 || node5.getNodeType() == 4)
                        {
                            String s3 = getTaggedData((Node)vector.elementAt(k1));
                            if(DebugIndicator.ON)
                                System.out.println("1<= " + s3.length() + " <= " + j);
                            if(s3.length() > j)
                                setMessage(MessageType.WARNING, "MetadataUtil::verifyLangstring()", "The text contained in element <langstring> is greater than " + j);
                            else
                            if(s3.length() < 1)
                            {
                                flag = false;
                                setMessage(MessageType.FAILED, "MetadataUtil::verifyLangstring()", "No text was found in element <langstring> and fails the minimum character length test");
                            } else
                            {
                                setMessage(MessageType.PASSED, "MetadataUtil::verifyLangstring()", "Character length for element <langstring> has been verified");
                            }
                        }
                    }

                } else
                {
                    flag = false;
                    if(DebugIndicator.ON)
                        System.out.println("There were " + j1 + " TEXT_NODE elements found");
                    setMessage(MessageType.FAILED, "MetadataUtil::verifyLangstring()", "Too many text strings were found in <langstring> and fails the test");
                    flag = false;
                }
            }
        }

        return flag && flag4 && flag3;
    }

    public boolean vocabLangAttributeExist(Vector vector)
    {
        boolean flag = true;
        String s = new String();
        setMessage(MessageType.INFO, "MetadataUtil::vocabLangAttributeExist()", "Testing xml:lang value...");
        for(int i = 0; i < vector.size(); i++)
        {
            Node node = (Node)vector.elementAt(i);
            if(node.getNodeType() == 1)
            {
                NamedNodeMap namednodemap = node.getAttributes();
                int j = namednodemap.getLength();
                for(int k = 0; k < j; k++)
                {
                    Attr attr = (Attr)namednodemap.item(k);
                    String s1 = attr.getLocalName();
                    if(s1.equalsIgnoreCase("lang"))
                    {
                        String s2 = attr.getValue();
                        if(s2.equals("x-none"))
                        {
                            setMessage(MessageType.PASSED, "MetadataUtil::vocabLangAttributeExist()", "<langstring> attribute value 'x-none'  existed");
                        } else
                        {
                            setMessage(MessageType.FAILED, "MetadataUtil::vocabLangAttributeExist()", "<langstring> attribute value did not equal 'x-none' ");
                            flag = false;
                        }
                    }
                }

            }
        }

        return flag;
    }

    public boolean verifyRestrictedLang(Node node, String s, Vector vector)
    {
        boolean flag = true;
        boolean flag1 = false;
        boolean flag3 = true;
        boolean flag5 = false;
        boolean flag6 = false;
        setMessage(MessageType.INFO, "MetadataUtil::verifyRestrictedLang()", "Testing element <" + s + "> for element" + " <langstring>...");
        String s1 = new String();
        NodeList nodelist = node.getChildNodes();
        Node node1 = node;
        String s3 = new String();
        Vector vector1 = new Vector();
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node2 = nodelist.item(i);
            int j = getMultiplicityUsed(node, "langstring");
            if(node2.getNodeType() == 1)
            {
                String s4 = node2.getLocalName();
                if(s4.equalsIgnoreCase("langstring"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.INFO, "MetadataUtil::verifyRestrictedLang()", "Testing element <langstring> for multiplicity...");
                        setMessage(MessageType.PASSED, "MetadataUtil::verifyRestrictedLang()", "1 and only 1 <langstring> element was found");
                        flag5 = true;
                    }
                    vector1.add(node2);
                }
            }
        }

        if(!vector1.isEmpty())
        {
            boolean flag2 = allContainAttributes(vector1);
            if(flag2)
            {
                boolean flag4 = vocabLangAttributeExist(vector1);
                if(!flag4)
                {
                    setMessage(MessageType.FAILED, "MetadataUtil::verifyRestrictedLang()", "<langstring> element in vocabulary type is required to contain the xml:lang attribute with value 'x-none'");
                    flag = false;
                }
            } else
            {
                setMessage(MessageType.FAILED, "MetadataUtil::verifyRestrictedLang()", "<langstring> element in vocabulary type is required to contain the xml:lang attribute with value 'x-none'");
                flag = false;
            }
        }
        for(int k = 0; k < vector1.size(); k++)
        {
            int l = 0;
            Node node3 = (Node)vector1.elementAt(k);
            NodeList nodelist1 = node3.getChildNodes();
            if(nodelist1 != null)
            {
                for(int i1 = 0; i1 < nodelist1.getLength(); i1++)
                {
                    Node node4 = nodelist1.item(i1);
                    if(node4.getNodeType() == 3 || node4.getNodeType() == 4)
                        l++;
                }

                if(l == 0)
                {
                    flag = false;
                    setMessage(MessageType.FAILED, "MetadataUtil::verifyRestrictedLang()", "No data was found in element <" + s + "> and fails the minimum character length " + "test");
                } else
                if(l == 1)
                {
                    for(int j1 = 0; j1 < nodelist1.getLength(); j1++)
                    {
                        Node node5 = nodelist1.item(j1);
                        if(node5.getNodeType() == 3 || node5.getNodeType() == 4)
                        {
                            String s2 = getTaggedData((Node)vector1.elementAt(k));
                            if(s.equalsIgnoreCase("source"))
                            {
                                setMessage(MessageType.INFO, "MetadataUtil::verifyRestrictedLang()", "Testing the value of <source>");
                                if(!s2.equals("LOMv1.0"))
                                {
                                    setMessage(MessageType.FAILED, "MetadataUtil::verifyRestrictedLang()", "<source> value found - '" + s2 + "' ");
                                    setMessage(MessageType.FAILED, "MetadataUtil::verifyRestrictedLang()", "Must equal 'LOMv1.0' for Restricted Vocabulary");
                                    flag = false;
                                } else
                                {
                                    setMessage(MessageType.PASSED, "MetadataUtil::verifyRestrictedLang()", "<source> value equals 'LOMv1.0'");
                                }
                            } else
                            if(s.equalsIgnoreCase("value"))
                            {
                                for(int k1 = 0; k1 < vector.size(); k1++)
                                {
                                    String s5 = (String)vector.elementAt(k1);
                                    if(s2.equals(s5))
                                        flag6 = true;
                                }

                                setMessage(MessageType.INFO, "MetadataUtil::verifyRestrictedLang()", "Testing the value of <value>");
                                if(!flag6)
                                {
                                    setMessage(MessageType.FAILED, "MetadataUtil::verifyRestrictedLang()", "Found '" + s2 + "' " + "- Not a valid Restricted Vocabulary token");
                                    flag = false;
                                } else
                                {
                                    setMessage(MessageType.PASSED, "MetadataUtil::verifyRestrictedLang()", "Found '" + s2 + "' " + "- A valid Restricted Vocabulary token");
                                }
                            }
                        }
                    }

                } else
                {
                    flag = false;
                    if(DebugIndicator.ON)
                        System.out.println("There were " + l + " TEXT_NODE elements found");
                    setMessage(MessageType.FAILED, "MetadataUtil::verifyRestrictedLang()", "Too many text strings were found in <langstring> and fails the test");
                    flag = false;
                }
            }
        }

        return flag;
    }

    public boolean verifyBestPracticeLang(Node node, String s, String s1, Vector vector)
    {
        boolean flag = true;
        boolean flag1 = false;
        boolean flag3 = true;
        boolean flag5 = false;
        boolean flag6 = false;
        char c = '\u03E8';
        if(DebugIndicator.ON)
            System.out.println("The VALUE OF typeAndNameResult is " + typeAndNameResult);
        setMessage(MessageType.INFO, "MetadataUtil::verifyBestPracticeLang()", "Testing element <" + s1 + "> for element" + " <langstring>...");
        String s2 = new String();
        NodeList nodelist = node.getChildNodes();
        Node node1 = node;
        String s4 = new String();
        Vector vector1 = new Vector();
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node2 = nodelist.item(i);
            int j = getMultiplicityUsed(node, "langstring");
            if(node2.getNodeType() == 1)
            {
                String s5 = node2.getLocalName();
                if(s5.equalsIgnoreCase("langstring"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.INFO, "MetadataUtil::verifyBestPracticeLang()", "Testing element <langstring> for multiplicity...");
                        setMessage(MessageType.PASSED, "MetadataUtil::verifyBestPracticeLang()", "1 and only 1 <langstring> element was found");
                        flag5 = true;
                    }
                    vector1.add(node2);
                }
            }
        }

        if(!vector1.isEmpty())
        {
            boolean flag2 = allContainAttributes(vector1);
            if(flag2)
            {
                boolean flag4 = vocabLangAttributeExist(vector1);
                if(!flag4)
                {
                    setMessage(MessageType.FAILED, "MetadataUtil::verifyBestPracticeLang()", "<langstring> element in vocabulary type is required to contain the xml:lang attribute with value 'x-none'");
                    flag = false;
                }
            } else
            {
                setMessage(MessageType.FAILED, "MetadataUtil::verifyBestPracticeLang()", "<langstring> element in vocabulary type is required to contain the xml:lang attribute with value 'x-none'");
                flag = false;
            }
        }
        for(int k = 0; k < vector1.size(); k++)
        {
            int l = 0;
            Node node3 = (Node)vector1.elementAt(k);
            NodeList nodelist1 = node3.getChildNodes();
            if(nodelist1 != null)
            {
                for(int i1 = 0; i1 < nodelist1.getLength(); i1++)
                {
                    Node node4 = nodelist1.item(i1);
                    if(node4.getNodeType() == 3 || node4.getNodeType() == 4)
                        l++;
                }

                if(l == 0)
                {
                    flag = false;
                    setMessage(MessageType.FAILED, "MetadataUtil::verifyBestPracticeLang()", "No data was found in element <" + s1 + "> and fails the minimum character length " + "test");
                } else
                if(l == 1)
                {
                    for(int j1 = 0; j1 < nodelist1.getLength(); j1++)
                    {
                        Node node5 = nodelist1.item(j1);
                        if(node5.getNodeType() == 3 || node5.getNodeType() == 4)
                        {
                            String s3 = getTaggedData((Node)vector1.elementAt(k));
                            if(s1.equalsIgnoreCase("source"))
                            {
                                setMessage(MessageType.INFO, "MetadataUtil::verifyBestPracticeLang()", "Testing the value of <source>");
                                if(!s3.equals("LOMv1.0"))
                                {
                                    if(typeAndNameResult && validTypeValue)
                                    {
                                        setMessage(MessageType.WARNING, "MetadataUtil::verifyBestPracticeLang()", "<source> value found - '" + s3 + "' ");
                                        setMessage(MessageType.WARNING, "MetadataUtil::verifyBestPracticeLang()", "Must equal 'LOMv1.0' in order to adhear to Best Practice Vocabulary");
                                        notBest = true;
                                    }
                                    if(s3.length() > c)
                                        setMessage(MessageType.WARNING, "MetadataUtil::verifyBestPracticeLang()", "The text contained in source <langstring> is greater than " + (int)c);
                                    else
                                    if(s3.length() < 1)
                                    {
                                        flag = false;
                                        setMessage(MessageType.FAILED, "MetadataUtil::verifyBestPracticeLang()", "No text was found in source <langstring> and fails the minimum character length test");
                                    } else
                                    {
                                        setMessage(MessageType.PASSED, "MetadataUtil::verifyBestPracticeLang()", "Found '" + s3 + "' Character " + " length for this value has been " + "verified");
                                    }
                                } else
                                if(validTypeValue && typeAndNameResult)
                                {
                                    setMessage(MessageType.PASSED, "MetadataUtil::verifyBestPracticeLang()", "<source> value equals 'LOMv1.0'");
                                    setMessage(MessageType.PASSED, "MetadataUtil::verifyBestPracticeLang()", "<value> must now contain a valid Best Practice Vocabulary token");
                                    notBest = false;
                                } else
                                if(s3.length() > c)
                                    setMessage(MessageType.WARNING, "MetadataUtil::verifyBestPracticeLang()", "The text contained in source <langstring> is greater than " + (int)c);
                                else
                                if(s3.length() < 1)
                                {
                                    flag = false;
                                    setMessage(MessageType.FAILED, "MetadataUtil::verifyBestPracticeLang()", "No text was found in source <langstring> and fails the minimum character length test");
                                } else
                                {
                                    setMessage(MessageType.PASSED, "MetadataUtil::verifyBestPracticeLang()", "Found '" + s3 + "' Character " + " length for this value has been " + "verified");
                                }
                            } else
                            if(s1.equals("value"))
                                if(!notBest)
                                {
                                    for(int k1 = 0; k1 < vector.size(); k1++)
                                    {
                                        String s6 = (String)vector.elementAt(k1);
                                        if(s3.equals(s6))
                                        {
                                            flag6 = true;
                                            if(s.equalsIgnoreCase("type"))
                                                valueOfType = s3;
                                        }
                                    }

                                    setMessage(MessageType.INFO, "MetadataUtil::verifyBestPracticeLang()", "Testing the value of <value>");
                                    if(!flag6)
                                    {
                                        if(!notBest)
                                        {
                                            setMessage(MessageType.FAILED, "MetadataUtil::verifyBestPracticeLang()", "Found '" + s3 + "' " + " - Not a valid Best Practice " + "Vocabulary token");
                                            flag = false;
                                        } else
                                        {
                                            setMessage(MessageType.WARNING, "MetadataUtil::verifyBestPracticeLang()", "Found '" + s3 + "' " + " - Not a valid Best Practice " + "Vocabulary token");
                                        }
                                        if(s.equalsIgnoreCase("type"))
                                            validTypeValue = false;
                                    } else
                                    {
                                        setMessage(MessageType.PASSED, "MetadataUtil::verifyBestPracticeLang()", "Found '" + s3 + "' " + "- A Valid Best Practice Vocabulary token");
                                    }
                                } else
                                {
                                    if(s.equalsIgnoreCase("type"))
                                        validTypeValue = false;
                                    if(s3.length() > c)
                                        setMessage(MessageType.WARNING, "MetadataUtil::verifyBestPracticeLang()", "The text contained in value <langstring> is greater than " + (int)c);
                                    else
                                    if(s3.length() < 1)
                                    {
                                        setMessage(MessageType.FAILED, "MetadataUtil::verifyBestPracticeLang()", "No text was found in value <langstring> and fails the minimum character length test");
                                        flag = false;
                                    } else
                                    {
                                        setMessage(MessageType.PASSED, "MetadataUtil::verifyBestPracticeLang()", "Found '" + s3 + "' Character " + " length for this value has been " + "verified");
                                    }
                                }
                        }
                    }

                } else
                {
                    flag = false;
                    if(DebugIndicator.ON)
                        System.out.println("There were " + l + " TEXT_NODE elements found");
                    setMessage(MessageType.FAILED, "MetadataUtil::verifyBestPracticeLang()", "Too many text strings were found in <langstring> and fails the test");
                    flag = false;
                }
            }
        }

        return flag;
    }

    public boolean verifyVocabulary(Node node, Vector vector, String s, boolean flag)
    {
        boolean flag1 = true;
        boolean flag2 = false;
        boolean flag3 = true;
        boolean flag4 = false;
        boolean flag5 = true;
        if(s.equalsIgnoreCase("type"))
        {
            typeNotUsed = false;
            setMessage(MessageType.INFO, "MetadataUtil::verifyVocabulary()", "Testing for element <name> to accompany  <type>");
            if(!peek(node.getParentNode(), "name"))
            {
                setMessage(MessageType.WARNING, "MetadataUtil::verifyVocabulary()", "No <name> element was detected.  Best Practice vocabulary will NOT be tested for element <type>.  It is recommended that the <type> and <name> elements accompany each other.");
                typeAndNameResult = false;
                notBest = true;
            } else
            {
                setMessage(MessageType.PASSED, "MetadataUtil::verifyVocabulary()", "<name> element was detected.  Best Practice vocabulary will be tested for element <type>.  It is recommended that the <type> and <name> elements accompany each other.");
            }
        } else
        if(s.equalsIgnoreCase("name"))
        {
            setMessage(MessageType.INFO, "MetadataUtil::verifyVocabulary()", "Testing for element <type> to accompany  <name>");
            if(typeNotUsed)
            {
                setMessage(MessageType.WARNING, "MetadataUtil::verifyVocabulary()", "No <type> element was detected.  Best Practice vocabulary will NOT be tested for element <name>.  It is recommended that the <type> and <name> elements accompany each other.");
                typeAndNameResult = false;
                notBest = true;
                validTypeValue = false;
            } else
            {
                setMessage(MessageType.PASSED, "MetadataUtil::verifyVocabulary()", "<type> element was detected.  Best Practice vocabulary will be tested for element <name>.  It is recommended that the <type> and <name> elements accompany each other.");
            }
            if(!validTypeValue && !typeNotUsed)
            {
                setMessage(MessageType.INFO, "MetadataUtil::verifyVocabulary()", "Testing if a best practice vocabulary list exists for element <name>");
                setMessage(MessageType.WARNING, "MetadataUtil::verifyVocabulary()", "Element 4.4.1 <type> did not adhere to the best practice vocabulary list, so there is no best practice vocabulary list for <name>.");
                notBest = true;
            }
        }
        setMessage(MessageType.INFO, "MetadataUtil::verifyVocabulary()", "Testing element <" + s + ">" + " for element <source> and <value>...");
        NodeList nodelist = node.getChildNodes();
        Node node1 = node;
        String s1 = new String();
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node2 = nodelist.item(i);
            if(node2.getNodeType() == 1)
            {
                String s2 = node2.getLocalName();
                if(s2.equalsIgnoreCase("source"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.INFO, "MetaDataUtil::verifyVocabulary()", "Testing element <source> for multiplicity...");
                        setMessage(MessageType.PASSED, "MetaDataUtil::verifyVocabulary()", "Element <source> existed 1 and only 1 time");
                        flag2 = true;
                    }
                    if(flag)
                        flag3 = verifyRestrictedLang(node2, "source", vector);
                    else
                        flag3 = verifyBestPracticeLang(node2, s, "source", vector);
                } else
                if(s2.equalsIgnoreCase("value"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.INFO, "MetaDataUtil::verifyVocabulary()", "Testing element <value> for multiplicity...");
                        setMessage(MessageType.PASSED, "MetaDataUtil::verifyVocabulary()", "Element <value> existed 1 and only 1 time");
                        flag4 = true;
                    }
                    if(flag)
                        flag5 = verifyRestrictedLang(node2, "value", vector);
                    else
                        flag5 = verifyBestPracticeLang(node2, s, "value", vector);
                }
            }
        }

        return flag3 && flag5;
    }

    public String getvalueOfType()
    {
        return valueOfType;
    }

    public int getNumMessages()
    {
        return messages.size();
    }

    public void clearMessages()
    {
        messages.clear();
    }

    public Message[] getMessages(boolean flag)
    {
        Message amessage[] = new Message[messages.size()];
        for(int i = 0; i < messages.size(); i++)
            amessage[i] = (Message)messages.elementAt(i);

        if(flag)
            clearMessages();
        return amessage;
    }

    public Collection getMessages()
    {
        return messages;
    }

    public void appendMessages(Collection collection)
    {
        messages.addAll(collection);
    }

    public void setMessage(int i, String s, String s1)
    {
        Message message = new Message(i, s1, s);
        messages.add(message);
        if(DebugIndicator.ON)
            System.out.println(message.toString());
    }

    public String getTaggedData(Node node)
    {
        String s = new String();
        NodeList nodelist = node.getChildNodes();
        if(nodelist != null)
        {
            for(int i = 0; i < nodelist.getLength(); i++)
                if(nodelist.item(i).getNodeType() == 3 || nodelist.item(i).getNodeType() == 4)
                    s = s + nodelist.item(i).getNodeValue().trim();

        } else
        if(DebugIndicator.ON)
            System.out.println("%%% no kids for value %%%");
        return s;
    }

    public boolean peek(Node node, String s)
    {
        boolean flag = false;
        Node node1 = node;
        String s1 = new String();
        NodeList nodelist = node.getChildNodes();
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node2 = nodelist.item(i);
            if(node2.getNodeType() != 1)
                continue;
            String s2 = node2.getLocalName();
            if(!s2.equals(s))
                continue;
            flag = true;
            break;
        }

        return flag;
    }
}
