// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RelationRules.java

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

public class RelationRules extends MetadataUtil
{

    private String elemNum;
    private Node relationNode;
    private boolean optionalNotUsed;

    public RelationRules(Node node, String s)
    {
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("RelationRules() called");
            System.out.println("/////////////////////////////////");
        }
        relationNode = node;
        elemNum = s;
    }

    public boolean verifyAssetMandatory()
    {
        if(DebugIndicator.ON)
            System.out.println("element " + elemNum + " (relation) has no " + "mandatory sub-elements");
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
        boolean flag2 = false;
        boolean flag3 = false;
        NodeList nodelist = relationNode.getChildNodes();
        Node node = relationNode;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            if(node1.getNodeType() == 1)
            {
                String s = node1.getLocalName();
                if(s.equalsIgnoreCase("kind"))
                {
                    if(!flag2)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "RelationRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <kind>...");
                        setMessage(MessageType.INFO, "RelationRules::verifyAssetOptional()", "Testing element " + elemNum + ".1 <kind> for multiplicity...");
                        int j = getMultiplicityUsed(relationNode, s);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "RelationRules::verifyAssetOptional()", "More than 1 <kind> element was found .. 0 or 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "RelationRules::verifyAssetOptional()", "0 or 1 <kind> element was found");
                            if(!verifyKind(node1))
                                flag = false;
                        }
                        flag2 = true;
                    }
                    optionalNotUsed = false;
                } else
                if(s.equalsIgnoreCase("resource"))
                {
                    if(!flag3)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "RelationRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <resource>...");
                        setMessage(MessageType.INFO, "RelationRules::verifyAssetOptional()", "Testing element " + elemNum + ".2 <resource> for multiplicity...");
                        int k = getMultiplicityUsed(relationNode, s);
                        if(k > 1)
                        {
                            setMessage(MessageType.FAILED, "RelationRules::verifyAssetOptional()", "More than 1 <resource> element was found .. 0 or 1 allowed");
                            flag1 = false;
                        } else
                        if(k == 1)
                        {
                            setMessage(MessageType.PASSED, "RelationRules::verifyAssetOptional()", "0 or 1 <resource> element was found");
                            if(!verifyResource(node1, elemNum + ".2"))
                                flag1 = false;
                        }
                        flag3 = true;
                    }
                    optionalNotUsed = false;
                }
            }
        }

        if(DebugIndicator.ON)
        {
            boolean flag4 = flag && flag1;
            System.out.println("returning ->" + flag4);
        }
        return flag && flag1;
    }

    public boolean verifyScoOptional()
    {
        return verifyAssetOptional();
    }

    public boolean verifyCaOptional()
    {
        return verifyScoOptional();
    }

    private boolean verifyKind(Node node)
    {
        boolean flag = true;
        boolean flag1 = false;
        Vector vector = buildVocabKind();
        flag = verifyVocabulary(node, vector, "kind", flag1);
        return flag;
    }

    public boolean verifyResource(Node node, String s)
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag4 = false;
        boolean flag5 = true;
        boolean flag6 = false;
        NodeList nodelist = node.getChildNodes();
        Node node1 = node;
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node2 = nodelist.item(i);
            if(node2.getNodeType() == 1)
            {
                String s1 = node2.getLocalName();
                if(s1.equalsIgnoreCase("description"))
                {
                    if(!flag4)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "RelationRules::verifyResource()", "Testing element " + s + ".2 <description>...");
                        setMessage(MessageType.INFO, "RelationRules::verifyResource()", "Testing element " + s + ".2 <description> for multiplicity...");
                        int j = getMultiplicityUsed(node, s1);
                        if(j > 1)
                        {
                            setMessage(MessageType.FAILED, "RelationRules::verifyResource()", "More than 1 <description> element was found .. 0 or 1 allowed");
                            flag = false;
                        } else
                        if(j == 1)
                        {
                            setMessage(MessageType.PASSED, "RelationRules::verifyResource()", "0 or 1 <description> element was found");
                            if(!verifyDescription(node2, s + ".2"))
                                flag = false;
                        }
                        flag4 = true;
                    }
                } else
                if(s1.equalsIgnoreCase("identifier"))
                {
                    flag5 = false;
                    setMessage(MessageType.INFO, "RelationRules::verifyResource()", "Testing element 7.2 <resource> for reserved element <identifier>..");
                    setMessage(MessageType.FAILED, "RelationRules::verifyResource()", "Element 7.2.1 <identifier> was found and is a Reserved element");
                } else
                if(s1.equalsIgnoreCase("catalogentry"))
                {
                    if(!flag6)
                    {
                        setMessage(MessageType.OTHER, "", "");
                        setMessage(MessageType.INFO, "RelationRules::verifyResource()", "Testing element " + s + ".3 <catalogentry>...");
                        setMessage(MessageType.INFO, "RelationRules::verifyResource()", "Testing element " + s + ".3 <catalogentry> for multiplicity...");
                        int k = getMultiplicityUsed(node, s1);
                        if(k > 10)
                            setMessage(MessageType.WARNING, "RelationRules::verifyResource()", "More than 10 <catalogentry> elements were found");
                        else
                            setMessage(MessageType.PASSED, "RelationRules::verifyResource()", "10 or less <catalogentry> elements were found");
                        flag6 = true;
                    }
                    CatalogentryRules catalogentryrules = new CatalogentryRules(node2, s + ".3");
                    boolean flag3 = catalogentryrules.verifyAssetOptional();
                    if(!flag3)
                        flag1 = false;
                    appendMessages(catalogentryrules.getMessages());
                }
            }
        }

        return flag && flag5 && flag1;
    }

    private boolean verifyDescription(Node node, String s)
    {
        boolean flag = true;
        flag = verifyLangstring(node, "<description>", s, 1000);
        return flag;
    }

    private Vector buildVocabKind()
    {
        Vector vector = new Vector(12);
        vector.add("IsPartOf");
        vector.add("HasPart");
        vector.add("IsVersionOf");
        vector.add("HasVersion");
        vector.add("IsFormatOf");
        vector.add("HasFormat");
        vector.add("References");
        vector.add("IsReferencedBy");
        vector.add("IsBasedOn");
        vector.add("IsBasisFor");
        vector.add("Requires");
        vector.add("IsRequiredBy");
        return vector;
    }

    public boolean getOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private RelationRules()
    {
        optionalNotUsed = true;
    }
}
