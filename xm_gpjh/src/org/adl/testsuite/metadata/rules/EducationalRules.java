// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   EducationalRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import java.util.Vector;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.testsuite.metadata.rules:
//            DatetypeRules

public class EducationalRules extends MetadataUtil
{

    private String elemNum;
    private Node educationalNode;
    private boolean optionalNotUsed;

    public EducationalRules(Node node, String s)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("EducationalRules() called");
            System.out.println("/////////////////////////////////");
        }
        educationalNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        if(DebugIndicator.ON)
            System.out.println("element " + elemNum + " <educational> has no " + "mandatory sub-elements");
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
        boolean flag4 = true;
        boolean flag5 = true;
        boolean flag6 = true;
        boolean flag7 = true;
        boolean flag8 = true;
        boolean flag9 = true;
        boolean flag10 = true;
        boolean flag11 = false;
        boolean flag12 = false;
        boolean flag13 = false;
        boolean flag14 = false;
        boolean flag15 = false;
        boolean flag16 = false;
        boolean flag17 = false;
        boolean flag18 = false;
        boolean flag19 = false;
        boolean flag20 = false;
        boolean flag21 = false;
        NodeList nodelist = educationalNode.getChildNodes();
        Node node = educationalNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("interactivitytype"))
                {
                    if(!flag11)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <interactivitytype>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <interactivitytype> for multiplicity...");
                        int j = getMultiplicityUsed(educationalNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "EducationalRules::verifyAssetOptional()", "More than 1 <interactivitytype> element was found...0 or 1 allowed");
                            flag = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "0 or 1 <interactivitytype> element was found");
                            if(!verifyInteractivitytype(node1))
                                flag = false;
                        }
                        flag11 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("learningresourcetype"))
                {
                    if(!flag12)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <learningresourcetype>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <learningresourcetype> for multiplicity...");
                        int k = getMultiplicityUsed(educationalNode, s);
                        if(k > 10)
                            setMessage(MessageType.WARNING, "EducationalRules::verifyAssetOptional()", "More than 10 <learningresourcetype> elements were found");
                        else
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "10 or less <learningresourcetype> elements were found");
                        flag12 = true;
                    }
                    if(!verifyLearningresourcetype(node1))
                        flag1 = false;
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("interactivitylevel"))
                {
                    if(!flag13)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <interactivitylevel>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".3 <interactivitylevel> for multiplicity...");
                        int l = getMultiplicityUsed(educationalNode, s);
                        if(l > 1)
                        {
                            setMessage(MessageType.FAILED, "EducationalRules::verifyAssetOptional()", "More than 1 <interactivitylevel> element was found...0 or 1 allowed");
                            flag2 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "0 or 1 <interactivitylevel> element was found");
                            if(!verifyInteractivitylevel(node1))
                                flag2 = false;
                        }
                        flag13 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("semanticdensity"))
                {
                    if(!flag14)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".4 <semanticdensity>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".4 <semanticdensity> for multiplicity...");
                        int i1 = getMultiplicityUsed(educationalNode, s);
                        if(i1 > 1)
                        {
                            setMessage(MessageType.FAILED, "EducationalRules::verifyAssetOptional()", "More than 1 <semanticdensity> element was found...0 or 1 allowed");
                            flag3 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "0 or 1 <semanticdensity> element was found");
                            if(!verifySemanticdensity(node1))
                                flag3 = false;
                        }
                        flag14 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("intendedenduserrole"))
                {
                    if(!flag15)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".5 <intendedenduserrole>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".5 <intendedenduserrole> for multiplicity...");
                        int j1 = getMultiplicityUsed(educationalNode, s);
                        if(j1 > 10)
                            setMessage(MessageType.WARNING, "EducationalRules::verifyAssetOptional()", "More than 10 <intendedenduserrole> elements were found");
                        else
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "10 or less <intendedenduserrole> elements were found");
                        flag15 = true;
                    }
                    if(!verifyIntendedenduserrole(node1))
                        flag4 = false;
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("context"))
                {
                    if(!flag16)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".6 <context>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".6 <context> for multiplicity...");
                        int k1 = getMultiplicityUsed(educationalNode, s);
                        if(k1 > 10)
                            setMessage(MessageType.WARNING, "EducationalRules::verifyAssetOptional()", "More than 10 <context> elements were found");
                        else
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "10 or less <context> elements were found");
                        flag16 = true;
                    }
                    if(!verifyContext(node1))
                        flag5 = false;
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("typicalagerange"))
                {
                    if(!flag17)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".7 <typicalagerange>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".7 <typicalagerange> for multiplicity...");
                        int l1 = getMultiplicityUsed(educationalNode, s);
                        if(l1 > 5)
                            setMessage(MessageType.WARNING, "EducationalRules::verifyAssetOptional()", "More than 5 <typicalagerange> elements were found");
                        else
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "5 or less <typicalagerange> elements were found");
                        flag17 = true;
                    }
                    if(!verifyTypicalagerange(node1, elemNum + ".7"))
                        flag6 = false;
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("difficulty"))
                {
                    if(!flag18)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".8 <difficulty>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".8 <difficulty> for multiplicity...");
                        int i2 = getMultiplicityUsed(educationalNode, s);
                        if(i2 > 1)
                        {
                            setMessage(MessageType.FAILED, "EducationalRules::verifyAssetOptional()", "More than 1 <difficulty> element was found...0 or 1 allowed");
                            flag7 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "0 or 1 <difficulty> element was found");
                            if(!verifyDifficulty(node1))
                                flag7 = false;
                        }
                        flag18 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("typicallearningtime"))
                {
                    if(!flag19)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".9 <typicallearningtime>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".9 <typicallearningtime> for multiplicity...");
                        int j2 = getMultiplicityUsed(educationalNode, s);
                        if(j2 > 1)
                        {
                            setMessage(MessageType.FAILED, "EducationalRules::verifyAssetOptional()", "More than 1 <typicallearningtime> element was found...0 or 1 allowed");
                            flag8 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "0 or 1 <typicallearningtime> element was found");
                            if(!verifyTypicallearningtime(node1, elemNum + ".9"))
                                flag8 = false;
                        }
                        flag19 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("description"))
                {
                    if(!flag20)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".10 <description>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".10 <description> for multiplicity...");
                        int k2 = getMultiplicityUsed(educationalNode, s);
                        if(k2 > 1)
                        {
                            setMessage(MessageType.FAILED, "EducationalRules::verifyAssetOptional()", "More than 1 <description> element was found...0 or 1 allowed");
                            flag9 = false;
                        } else
                        {
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "0 or 1 <description> element was found");
                            if(!verifyDescription(node1, elemNum + ".10"))
                                flag9 = false;
                        }
                        flag20 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("language"))
                {
                    if(!flag21)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".11 <language>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyAssetOptional()", "Testing element " + elemNum + ".11 <language> for multiplicity...");
                        int l2 = getMultiplicityUsed(educationalNode, s);
                        if(l2 > 10)
                            setMessage(MessageType.WARNING, "EducationalRules::verifyAssetOptional()", "More than 10 <language> elements were found");
                        else
                            setMessage(MessageType.PASSED, "EducationalRules::verifyAssetOptional()", "10 or less <language> elements were found");
                        flag21 = true;
                    }
                    if(!verifyLanguage(node1, elemNum + ".11"))
                        flag10 = false;
                    optionalNotUsed = false;
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag22 = flag && flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9 && flag10;
            System.out.println("returning ->" + flag22);
        }
        return flag && flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9 && flag10;
    }

    public boolean verifyScoOptional()
    {
        return verifyAssetOptional();
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    private boolean verifyInteractivitytype(Node node)
    {
        boolean flag = true;
        boolean flag1 = true;
        Vector vector = buildVocabInteractivitytype();
        flag = verifyVocabulary(node, vector, "interactivitytype", flag1);
        return flag;
    }

    private boolean verifyLearningresourcetype(Node node)
    {
        boolean flag = true;
        boolean flag1 = false;
        Vector vector = buildVocabLearningresourcetype();
        flag = verifyVocabulary(node, vector, "learningresourcetype", flag1);
        return flag;
    }

    private boolean verifyInteractivitylevel(Node node)
    {
        boolean flag = true;
        boolean flag1 = true;
        Vector vector = buildVocabInteractivitylevel();
        flag = verifyVocabulary(node, vector, "interactivitylevel", flag1);
        return flag;
    }

    private boolean verifySemanticdensity(Node node)
    {
        boolean flag = true;
        boolean flag1 = true;
        Vector vector = buildVocabSemanticdensity();
        flag = verifyVocabulary(node, vector, "semanticdensity", flag1);
        return flag;
    }

    private boolean verifyIntendedenduserrole(Node node)
    {
        boolean flag = true;
        boolean flag1 = true;
        Vector vector = buildVocabIntendedenduserrole();
        flag = verifyVocabulary(node, vector, "intendedenduserrole", flag1);
        return flag;
    }

    private boolean verifyContext(Node node)
    {
        boolean flag = true;
        boolean flag1 = false;
        Vector vector = buildVocabContext();
        flag = verifyVocabulary(node, vector, "context", flag1);
        return flag;
    }

    private boolean verifyTypicalagerange(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<typicalagerange>", s, 1000);
        return flag;
    }

    private boolean verifyDifficulty(Node node)
    {
        boolean flag = true;
        boolean flag1 = true;
        Vector vector = buildVocabDifficulty();
        flag = verifyVocabulary(node, vector, "difficulty", flag1);
        return flag;
    }

    private boolean verifyTypicallearningtime(Node node, String s)
    {
        boolean flag = true;
        boolean flag3 = true;
        boolean flag5 = true;
        boolean flag6 = false;
        boolean flag7 = false;
        setMessage(MessageType.INFO, "EducationalRules::verifyTypicallearningtime()", "Testing element " + s + " <typicallearningtime> for " + "Element <datetime>...");
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
                    if(!flag6)
                    {
                        setMessage(MessageType.INFO, "EducationalRules::verifyTypicallearningtime()", "Testing element <datetime> for multiplicity...");
                        setMessage(MessageType.PASSED, "EducationalRules::verifyTypicallearningtime()", "Element <datetime> existed 1 and only 1 time");
                        flag6 = true;
                    }
                    DatetypeRules datetyperules = new DatetypeRules(node2);
                    boolean flag1 = datetyperules.verifyDatetime();
                    boolean flag4;
                    if(!flag1)
                        flag4 = false;
                    appendMessages(datetyperules.getMessages());
                } else
                if(s1.equalsIgnoreCase("description"))
                {
                    if(!flag7)
                    {
                        setMessage(MessageType.INFO, "EducationalRules::verifyTypicallearningtime()", "Testing element <description>...");
                        setMessage(MessageType.INFO, "EducationalRules::verifyTypicallearningtime()", "Testing element <description> for multiplicity...");
                        setMessage(MessageType.PASSED, "EducationalRules::verifyTypicallearningtime()", "Element <description> existed 0 or 1 time");
                        flag7 = true;
                    }
                    DatetypeRules datetyperules1 = new DatetypeRules(node2);
                    boolean flag2 = datetyperules1.verifyDescription();
                    if(!flag2)
                        flag5 = false;
                    appendMessages(datetyperules1.getMessages());
                }
            }
        }

        if(!flag6)
        {
            setMessage(MessageType.INFO, "EducationalRules::verifyRole()", "Testing element <datetime> for multiplicity...");
            setMessage(MessageType.FAILED, "EducationalRules::verifyRole()", "Element <datetime> was not found and must appear 1 and only 1 time");
        }
        return flag5 && flag6;
    }

    private boolean verifyDescription(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<description>", s, 1000);
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
                setMessage(MessageType.FAILED, "EducationalRules::verifyLanguage()", "No data was found in element <language> and fails the minimum character length test");
            } else
            if(i == 1)
            {
                for(int k = 0; k < nodelist.getLength(); k++)
                {
                    Node node3 = nodelist.item(k);
                    if(node3.getNodeType() == 3 || node3.getNodeType() == 4)
                    {
                        setMessage(MessageType.INFO, "EducationalRules::verifyLanguage()", "Testing element " + s + " <language> for " + "character length...");
                        String s2 = getTaggedData(node);
                        if(DebugIndicator.ON)
                            System.out.println("1<= " + s2.length() + " <= " + byte0);
                        if(s2.length() > byte0)
                            setMessage(MessageType.WARNING, "EducationalRules::verifyLanguage()", "The text contained in element " + s + " <language> is greater than " + byte0);
                        else
                        if(s2.length() < 1)
                        {
                            flag = false;
                            setMessage(MessageType.FAILED, "EducationalRules::verifyLanguage()", "No text was found in element " + s + " <language> and fails the minimum " + "character length test");
                        } else
                        {
                            setMessage(MessageType.PASSED, "EducationalRules::verifyverifyLanguage()", "Character length for element " + s + " <language> has been verified");
                        }
                    }
                }

            } else
            {
                flag = false;
                if(DebugIndicator.ON)
                    System.out.println("There were " + i + " TEXT_NODE elements found");
                setMessage(MessageType.FAILED, "EducationalRules::verifyLanguage()", "Too many text strings were found in <language> and fails the test");
            }
        }
        return flag;
    }

    private Vector buildVocabInteractivitytype()
    {
        Vector vector = new Vector(4);
        vector.add("Active");
        vector.add("Expositive");
        vector.add("Mixed");
        vector.add("Undefined");
        return vector;
    }

    private Vector buildVocabLearningresourcetype()
    {
        Vector vector = new Vector(14);
        vector.add("Exercise");
        vector.add("Simulation");
        vector.add("Questionnaire");
        vector.add("Diagram");
        vector.add("Figure");
        vector.add("Graph");
        vector.add("Index");
        vector.add("Slide");
        vector.add("Table");
        vector.add("Narrative Text");
        vector.add("Exam");
        vector.add("Experiment");
        vector.add("Problem Statement");
        vector.add("Self Assesment");
        return vector;
    }

    private Vector buildVocabInteractivitylevel()
    {
        Vector vector = new Vector(5);
        vector.add("very low");
        vector.add("low");
        vector.add("medium");
        vector.add("high");
        vector.add("very high");
        return vector;
    }

    private Vector buildVocabSemanticdensity()
    {
        Vector vector = new Vector(5);
        vector.add("very low");
        vector.add("low");
        vector.add("medium");
        vector.add("high");
        vector.add("very high");
        return vector;
    }

    private Vector buildVocabIntendedenduserrole()
    {
        Vector vector = new Vector(4);
        vector.add("Teacher");
        vector.add("Author");
        vector.add("Learner");
        vector.add("Manager");
        return vector;
    }

    private Vector buildVocabContext()
    {
        Vector vector = new Vector(11);
        vector.add("Primary Education");
        vector.add("Secondary Education");
        vector.add("Higher Education");
        vector.add("University First Cycle");
        vector.add("University Second Cycle");
        vector.add("University Postgrade");
        vector.add("Technical School First Cycle");
        vector.add("Technical School Second Cycle");
        vector.add("Professional Formation");
        vector.add("Continuous Formation");
        vector.add("Vocational Training");
        return vector;
    }

    private Vector buildVocabDifficulty()
    {
        Vector vector = new Vector(5);
        vector.add("very easy");
        vector.add("easy");
        vector.add("medium");
        vector.add("difficult");
        vector.add("very difficult");
        return vector;
    }

    public boolean getOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private EducationalRules()
    {
        optionalNotUsed = true;
    }
}
