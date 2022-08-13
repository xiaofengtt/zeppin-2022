// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MetaDataTester.java

package org.adl.testsuite.metadata;

import java.io.PrintStream;
import org.adl.testsuite.metadata.rules.LomRules;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

// Referenced classes of package org.adl.testsuite.metadata:
//            MetaDataDOMParser, MetadataUtil

public class MetaDataTester extends MetaDataDOMParser
{

    private String metadataType;
    private boolean optionalNotUsed;
    private String parseType;
    private Node xmlNode;

    public MetaDataTester(String s, String s1, String s2)
    {
        super(s1, s2);
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("MetaDataTester() called");
            System.out.println("/////////////////////////////////");
        }
        metadataType = s;
        parseType = "file";
    }

    public MetaDataTester(String s, Node node, String s1)
    {
        super(node, s1);
        optionalNotUsed = true;
        if(DebugIndicator.ON)
        {
            System.out.println("/////////////////////////////////");
            System.out.println("MetaDataTester() called");
            System.out.println("/////////////////////////////////");
        }
        metadataType = s;
        parseType = "node";
        xmlNode = node;
    }

    public boolean verifyMandatory()
    {
        boolean flag = false;
        Object obj = null;
        if(parseType == "file")
            obj = document.getDocumentElement();
        else
        if(parseType == "node")
            obj = xmlNode;
        LomRules lomrules = new LomRules(((Node) (obj)));
        if(metadataType.equalsIgnoreCase("asset"))
            flag = lomrules.verifyAssetMandatory();
        else
        if(metadataType.equalsIgnoreCase("contentaggregation"))
            flag = lomrules.verifyCaMandatory();
        else
            flag = lomrules.verifyScoMandatory();
        appendMessages(lomrules.getMessages());
        return flag;
    }

    public boolean verifyOptional()
    {
        boolean flag = false;
        Object obj = null;
        if(parseType == "file")
            obj = document.getDocumentElement();
        else
        if(parseType == "node")
            obj = xmlNode;
        LomRules lomrules = new LomRules(((Node) (obj)));
        if(metadataType.equalsIgnoreCase("asset"))
            flag = lomrules.verifyAssetOptional();
        else
        if(metadataType.equalsIgnoreCase("contentaggregation"))
            flag = lomrules.verifyCaOptional();
        else
            flag = lomrules.verifyScoOptional();
        appendMessages(lomrules.getMessages());
        optionalNotUsed = lomrules.isOptionalNotUsed() && optionalNotUsed;
        return flag;
    }

    public boolean isOptionalNotUsed()
    {
        return optionalNotUsed;
    }

    private MetaDataTester()
    {
        super("", "");
        optionalNotUsed = true;
    }
}
