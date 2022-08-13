// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LomRules.java

package org.adl.testsuite.metadata.rules;

import java.io.PrintStream;
import org.adl.testsuite.metadata.MetadataUtil;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.testsuite.metadata.rules:
//            MetametadataRules, GeneralRules, TechnicalRules, RightsRules, 
//            LifecycleRules, ClassificationRules, EducationalRules, RelationRules, 
//            AnnotationRules

public class LomRules extends MetadataUtil
{

    private Node lomNode;
    private boolean optionalNotUsed;

    public LomRules(Node node)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("LomRules() called");
            System.out.println("/////////////////////////////////");
        }
        lomNode = node;
    }

    public boolean verifyAssetMandatory()
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean flag7 = false;
        NodeList nodelist = lomNode.getChildNodes();
        Node node = lomNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("metametadata"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 3 <metametadata>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 3 <metametadata> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyAssetMandatory()", "Element 3 <metametadata> existed 1 and only 1 time");
                        flag4 = true;
                    }
                    MetametadataRules metametadatarules = new MetametadataRules(node1, "3");
                    flag1 = metametadatarules.verifyAssetMandatory();
                    appendMessages(metametadatarules.getMessages());
                } else
                if(s.equalsIgnoreCase("general"))
                {
                    if(!flag5)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 1 <general>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 1 <general> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyAssetMandatory()", "Element 1 <general> existed 1 and only 1 time");
                        flag5 = true;
                    }
                    GeneralRules generalrules = new GeneralRules(node1, "1");
                    flag = generalrules.verifyAssetMandatory();
                    appendMessages(generalrules.getMessages());
                } else
                if(s.equalsIgnoreCase("technical"))
                {
                    if(!flag6)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 4 <technical>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 4 <technical> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyAssetMandatory()", "Element 4 <technical> existed 1 and only 1 time");
                        flag6 = true;
                    }
                    TechnicalRules technicalrules = new TechnicalRules(node1, "4");
                    flag2 = technicalrules.verifyAssetMandatory();
                    appendMessages(technicalrules.getMessages());
                } else
                if(s.equalsIgnoreCase("rights"))
                {
                    if(!flag7)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 6 <rights>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 6 <rights> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyAssetMandatory()", "Element 6 <rights> existed 1 and only 1 time");
                        flag7 = true;
                    }
                    RightsRules rightsrules = new RightsRules(node1, "6");
                    flag3 = rightsrules.verifyAssetMandatory();
                    appendMessages(rightsrules.getMessages());
                }
            }
        }

        if(!flag4)
        {
            setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 3 <metametadata> for multiplicity...");
            setMessage(MessageType.FAILED, "LomRules::verifyAssetMandatory()", "Element 3 <metametadata> was not found and must appear 1 and only 1 time");
        }
        if(!flag5)
        {
            setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 1 <general> for multiplicity...");
            setMessage(MessageType.FAILED, "LomRules::verifyAssetMandatory()", "Element 1 <general> was not found and must appear 1 and only 1 time");
        }
        if(!flag6)
        {
            setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 4 <technical> for multiplicity...");
            setMessage(MessageType.FAILED, "LomRules::verifyAssetMandatory()", "Element 4 <technical> was not found and must appear 1 and only 1 time");
        }
        if(!flag7)
        {
            setMessage(MessageType.INFO, "LomRules::verifyAssetMandatory()", "Testing element 6 <rights> for multiplicity...");
            setMessage(MessageType.FAILED, "LomRules::verifyAssetMandatory()", "Element 6 <rights> was not found and must appear 1 and only 1 time");
        }
        if(DebugIndicator.ON)
        {
            boolean flag8 = flag1 && flag && flag2 && flag3 && flag4 && flag5 && flag6 && flag7;
            System.out.println("returning ->" + flag8);
        }
        return flag1 && flag && flag2 && flag3 && flag4 && flag5 && flag6 && flag7;
    }

    public boolean verifyScoMandatory()
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
        NodeList nodelist = lomNode.getChildNodes();
        Node node = lomNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("metametadata"))
                {
                    if(!flag6)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 3 <metametadata>...");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 3 <metametadata> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyScoMandatory()", "Element 3 <metametadata> existed 1 and only 1 time");
                        flag6 = true;
                    }
                    MetametadataRules metametadatarules = new MetametadataRules(node1, "3");
                    flag = metametadatarules.verifyScoMandatory();
                    appendMessages(metametadatarules.getMessages());
                } else
                if(s.equalsIgnoreCase("general"))
                {
                    if(!flag7)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 1 <general>...");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 1 <general> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyScoMandatory()", "Element 1 <general> existed 1 and only 1 time");
                        flag7 = true;
                    }
                    GeneralRules generalrules = new GeneralRules(node1, "1");
                    flag1 = generalrules.verifyScoMandatory();
                    appendMessages(generalrules.getMessages());
                } else
                if(s.equalsIgnoreCase("lifecycle"))
                {
                    if(!flag8)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 2 <lifecycle>...");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 2 <lifecycle> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyScoMandatory()", "Element 2 <lifecycle> existed 1 and only 1 time");
                        flag8 = true;
                    }
                    LifecycleRules lifecyclerules = new LifecycleRules(node1, "2");
                    flag2 = lifecyclerules.verifyScoMandatory();
                    appendMessages(lifecyclerules.getMessages());
                } else
                if(s.equalsIgnoreCase("technical"))
                {
                    if(!flag9)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 4 <technical>...");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 4 <technical> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyScoMandatory()", "Element 4 <technical> existed 1 and only 1 time");
                        flag9 = true;
                    }
                    TechnicalRules technicalrules = new TechnicalRules(node1, "4");
                    flag3 = technicalrules.verifyAssetMandatory();
                    appendMessages(technicalrules.getMessages());
                } else
                if(s.equalsIgnoreCase("rights"))
                {
                    if(!flag10)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 6 <rights>...");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 6 <rights> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyScoMandatory()", "Element 6 <rights> existed 1 and only 1 time");
                        flag10 = true;
                    }
                    RightsRules rightsrules = new RightsRules(node1, "6");
                    flag4 = rightsrules.verifyAssetMandatory();
                    appendMessages(rightsrules.getMessages());
                } else
                if(s.equalsIgnoreCase("classification"))
                {
                    if(!flag11)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 9 <classification>...");
                        setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 9 <classification> for multiplicity...");
                        int j = getMultiplicityUsed(lomNode, s);
                        if(j > 40)
                        {
                            setMessage(MessageType.WARNING, "LomRules::verifyScoMandatory()", "More than 40 <classification> elements were found");
                            flag11 = true;
                        } else
                        {
                            setMessage(MessageType.PASSED, "LomRules::verifyScoMandatory()", "40 or less <classification> elements were found");
                            flag11 = true;
                        }
                    }
                    ClassificationRules classificationrules = new ClassificationRules(node1, "9");
                    flag5 = classificationrules.verifyScoMandatory();
                    appendMessages(classificationrules.getMessages());
                }
            }
        }

        if(!flag6)
        {
            setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 3 <metametadata> for multiplicity...");
            setMessage(MessageType.FAILED, "LomRules::verifyScoMandatory()", "Element 3 <metametadata> was not found and must appear 1 and only 1 time");
        }
        if(!flag7)
        {
            setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 1 <general> for multiplicity...");
            setMessage(MessageType.FAILED, "LomRules::verifyScoMandatory()", "Element 1 <general> was not found and must appear 1 and only 1 time");
        }
        if(!flag8)
        {
            setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 2 <lifecycle> for multiplicity...");
            setMessage(MessageType.FAILED, "LomRules::verifyScoMandatory()", "Element 2 <lifecycle> was not found and must appear 1 and only 1 time");
        }
        if(!flag9)
        {
            setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 4 <technical> for multiplicity...");
            setMessage(MessageType.FAILED, "LomRules::verifyScoMandatory()", "Element 4 <technical> was not found and must appear 1 and only 1 time");
        }
        if(!flag10)
        {
            setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 6 <rights> for multiplicity...");
            setMessage(MessageType.FAILED, "LomRules::verifyScoMandatory()", "Element 6 <rights> was not found and must appear 1 and only 1 time");
        }
        if(!flag11)
        {
            setMessage(MessageType.INFO, "LomRules::verifyScoMandatory()", "Testing element 9 <classification> for multiplicity...");
            setMessage(MessageType.FAILED, "LomRules::verifyScoMandatory()", "Element 9 <classification> was not found and must appear 1 or more times");
        }
        if(DebugIndicator.ON)
        {
            boolean flag12 = flag && flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9 && flag10 && flag11;
            System.out.println("returning ->" + flag12);
        }
        return flag && flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9 && flag10 && flag11;
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
        boolean flag9 = false;
        boolean flag10 = false;
        boolean flag11 = false;
        boolean flag12 = false;
        boolean flag13 = false;
        boolean flag14 = false;
        boolean flag15 = false;
        boolean flag16 = false;
        boolean flag17 = false;
        NodeList nodelist = lomNode.getChildNodes();
        Node node = lomNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("metametadata"))
                {
                    if(!flag9)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 3 <metametadata>...");
                        flag9 = true;
                    }
                    MetametadataRules metametadatarules = new MetametadataRules(node1, "3");
                    flag = metametadatarules.verifyAssetOptional();
                    appendMessages(metametadatarules.getMessages());
                    optionalNotUsed = optionalNotUsed && metametadatarules.isOptionalNotUsed();
                } else
                if(s.equalsIgnoreCase("general"))
                {
                    if(!flag10)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 1 <general>...");
                        flag10 = true;
                    }
                    GeneralRules generalrules = new GeneralRules(node1, "1");
                    flag1 = generalrules.verifyAssetOptional();
                    appendMessages(generalrules.getMessages());
                    optionalNotUsed = optionalNotUsed && generalrules.getOptionalNotUsed();
                } else
                if(s.equalsIgnoreCase("lifecycle"))
                {
                    if(!flag11)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 2 <lifecycle>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 2 <lifecycle> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyAssetOptional()", "Element 2 <lifecycle> existed 1 and only 1 time");
                        flag11 = true;
                    }
                    LifecycleRules lifecyclerules = new LifecycleRules(node1, "2");
                    flag2 = lifecyclerules.verifyAssetOptional();
                    appendMessages(lifecyclerules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("technical"))
                {
                    if(!flag12)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 4 <technical>...");
                        flag12 = true;
                    }
                    TechnicalRules technicalrules = new TechnicalRules(node1, "4");
                    flag3 = technicalrules.verifyAssetOptional();
                    appendMessages(technicalrules.getMessages());
                    optionalNotUsed = optionalNotUsed && technicalrules.getOptionalNotUsed();
                } else
                if(s.equalsIgnoreCase("educational"))
                {
                    if(!flag13)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 5 <educational>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 5 <educational> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyAssetOptional()", "Element 5 <educational> existed 1 and only 1 time");
                        flag13 = true;
                    }
                    EducationalRules educationalrules = new EducationalRules(node1, "5");
                    flag4 = educationalrules.verifyAssetOptional();
                    appendMessages(educationalrules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("rights"))
                {
                    if(!flag14)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 6 <rights>...");
                        flag14 = true;
                    }
                    RightsRules rightsrules = new RightsRules(node1, "6");
                    flag5 = rightsrules.verifyAssetOptional();
                    appendMessages(rightsrules.getMessages());
                    optionalNotUsed = optionalNotUsed && rightsrules.getOptionalNotUsed();
                } else
                if(s.equalsIgnoreCase("relation"))
                {
                    if(!flag15)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 7 <relation>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element element 7 <relation> for multiplicity...");
                        int j = getMultiplicityUsed(lomNode, s);
                        if(j > 100)
                            setMessage(MessageType.WARNING, "LomRules::verifyAssetOptional()", "More than 100 <relation> elements were found");
                        else
                            setMessage(MessageType.PASSED, "LomRules::verifyAssetOptional()", "100 or less <relation> elements were found");
                        flag15 = true;
                    }
                    RelationRules relationrules = new RelationRules(node1, "7");
                    flag6 = relationrules.verifyAssetOptional();
                    appendMessages(relationrules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("annotation"))
                {
                    if(!flag16)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 8 <annotation>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element element 8 <annotation> for multiplicity...");
                        int k = getMultiplicityUsed(lomNode, s);
                        if(k > 30)
                            setMessage(MessageType.WARNING, "LomRules::verifyAssetOptional()", "More than 30 <annotation> elements were found");
                        else
                            setMessage(MessageType.PASSED, "LomRules::verifyAssetOptional()", "30 or less <annotation> elements were found");
                        flag16 = true;
                    }
                    AnnotationRules annotationrules = new AnnotationRules(node1, "8");
                    flag7 = annotationrules.verifyAssetOptional();
                    appendMessages(annotationrules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("classification"))
                {
                    if(!flag17)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 9 <classification>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element 9 <classification> for multiplicity...");
                        int l = getMultiplicityUsed(lomNode, s);
                        if(l > 40)
                            setMessage(MessageType.WARNING, "LomRules::verifyAssetOptional()", "More than 40 <classification> elements were found");
                        else
                            setMessage(MessageType.PASSED, "LomRules::verifyAssetOptional()", "40 or less <classification> elements were found");
                        flag17 = true;
                    }
                    ClassificationRules classificationrules = new ClassificationRules(node1, "9");
                    flag8 = classificationrules.verifyAssetOptional();
                    appendMessages(classificationrules.getMessages());
                    optionalNotUsed = false;
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag18 = flag && flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8;
            System.out.println("returning ->" + flag18);
        }
        return flag && flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8;
    }

    public boolean verifyScoOptional()
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
        boolean flag9 = false;
        boolean flag10 = false;
        boolean flag11 = false;
        boolean flag12 = false;
        boolean flag13 = false;
        boolean flag14 = false;
        boolean flag15 = false;
        boolean flag16 = false;
        boolean flag17 = false;
        NodeList nodelist = lomNode.getChildNodes();
        Node node = lomNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("metametadata"))
                {
                    if(!flag9)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element 3 <metametadata>...");
                        flag9 = true;
                    }
                    MetametadataRules metametadatarules = new MetametadataRules(node1, "3");
                    flag2 = metametadatarules.verifyScoOptional();
                    appendMessages(metametadatarules.getMessages());
                    optionalNotUsed = optionalNotUsed && metametadatarules.isOptionalNotUsed();
                } else
                if(s.equalsIgnoreCase("general"))
                {
                    if(!flag10)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element 1 <general>...");
                        flag10 = true;
                    }
                    GeneralRules generalrules = new GeneralRules(node1, "1");
                    flag = generalrules.verifyScoOptional();
                    appendMessages(generalrules.getMessages());
                    optionalNotUsed = optionalNotUsed && generalrules.getOptionalNotUsed();
                } else
                if(s.equalsIgnoreCase("lifecycle"))
                {
                    if(!flag11)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element 2 <lifecycle>...");
                        flag11 = true;
                    }
                    LifecycleRules lifecyclerules = new LifecycleRules(node1, "2");
                    flag1 = lifecyclerules.verifyScoOptional();
                    appendMessages(lifecyclerules.getMessages());
                    optionalNotUsed = optionalNotUsed && lifecyclerules.getOptionalNotUsed();
                } else
                if(s.equalsIgnoreCase("technical"))
                {
                    if(!flag12)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element 4 <technical>...");
                        flag12 = true;
                    }
                    TechnicalRules technicalrules = new TechnicalRules(node1, "4");
                    flag3 = technicalrules.verifyScoOptional();
                    appendMessages(technicalrules.getMessages());
                    optionalNotUsed = optionalNotUsed && technicalrules.getOptionalNotUsed();
                } else
                if(s.equalsIgnoreCase("educational"))
                {
                    if(!flag13)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element 5 <educational>...");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element 5 <educational> for multiplicity...");
                        setMessage(MessageType.PASSED, "LomRules::verifyAssetOptional()", "Element 5 <educational> existed 1 and only 1 time");
                        flag13 = true;
                    }
                    EducationalRules educationalrules = new EducationalRules(node1, "5");
                    flag4 = educationalrules.verifyScoOptional();
                    appendMessages(educationalrules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("rights"))
                {
                    if(!flag14)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element 6 <rights>...");
                        flag14 = true;
                    }
                    RightsRules rightsrules = new RightsRules(node1, "6");
                    flag5 = rightsrules.verifyScoOptional();
                    appendMessages(rightsrules.getMessages());
                    optionalNotUsed = optionalNotUsed && rightsrules.getOptionalNotUsed();
                } else
                if(s.equalsIgnoreCase("relation"))
                {
                    if(!flag15)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element 7 <relation>...");
                        setMessage(MessageType.INFO, "LomRules::verifyAssetOptional()", "Testing element element 7 <relation> for multiplicity...");
                        int j = getMultiplicityUsed(lomNode, s);
                        if(j > 100)
                            setMessage(MessageType.WARNING, "LomRules::verifyScoOptional()", "More than 100 <relation> elements were found");
                        else
                            setMessage(MessageType.PASSED, "LomRules::verifyScoOptional()", "100 or less <relation> elements were found");
                        flag15 = true;
                    }
                    RelationRules relationrules = new RelationRules(node1, "7");
                    flag6 = relationrules.verifyAssetOptional();
                    appendMessages(relationrules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("annotation"))
                {
                    if(!flag16)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element 8 <annotation>...");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element element 8 <annotation> for multiplicity...");
                        int k = getMultiplicityUsed(lomNode, s);
                        if(k > 30)
                            setMessage(MessageType.WARNING, "LomRules::verifyScoOptional()", "More than 30 <annotation> elements were found");
                        else
                            setMessage(MessageType.PASSED, "LomRules::verifyScoOptional()", "30 or less <annotation> elements were found");
                        flag16 = true;
                    }
                    AnnotationRules annotationrules = new AnnotationRules(node1, "8");
                    flag7 = annotationrules.verifyScoOptional();
                    appendMessages(annotationrules.getMessages());
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("classification"))
                {
                    if(!flag17)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "LomRules::verifyScoOptional()", "Testing element 9 <classification>...");
                        flag17 = true;
                    }
                    ClassificationRules classificationrules = new ClassificationRules(node1, "9");
                    flag8 = classificationrules.verifyScoOptional();
                    appendMessages(classificationrules.getMessages());
                    optionalNotUsed = optionalNotUsed && classificationrules.getOptionalNotUsed();
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag18 = flag2 && flag && flag1 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8;
            System.out.println("returning ->" + flag18);
        }
        return flag2 && flag && flag1 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8;
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    public boolean isOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private LomRules()
    {
        optionalNotUsed = true;
    }
}
