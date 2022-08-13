// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLDOMParser.java

package org.adl.parsers.dom;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import org.adl.parsers.util.MessageClassification;
import org.adl.parsers.util.MessageHandler;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.dom.TextImpl;
import org.apache.xerces.framework.XMLParser;
import org.apache.xml.serialize.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ADLDOMParser extends DOMParser
    implements ErrorHandler
{

	protected Document document;
    protected Node rootNode;
    protected MessageHandler messageHandler;
    protected boolean system;
    protected boolean wellFormed;
    protected boolean valid;
    protected boolean extensionFound;
    protected String baseDir;
    private boolean validation;
    private String messageLocation;
    private String tsPath;
    private String tsParent;
    private static String MDNamespace = "http://www.imsglobal.org/xsd/imsmd_rootv1p2p1";
    private static String CPNamespace = "http://www.imsproject.org/xsd/imscp_rootv1p1p2";
    private static String ADLNamespace = "http://www.adlnet.org/xsd/adlcp_rootv1p2";
    private static String XMLNamespace = "http://www.w3.org/XML/1998/namespace";
    private static String nsNamespace = "http://www.w3.org/2000/xmlns/";
    private static String xsiNamespace = "http://www.w3.org/2001/XMLSchema-instance";
    private static String MDSchema = "imsmd_rootv1p2p1.xsd";
    private static String CPSchema = "imscp_rootv1p1p2.xsd";
    private static String ADLSchema = "adlcp_rootv1p2.xsd";
    private static String XMLSchema = "ims_xml.xsd";

    public ADLDOMParser()
    {
        messageLocation = "ADLDOMParser::";
        int i = MessageClassification.SYSTEM;
        int j = MessageType.FAILED;
        String s = "";
        String s4 = messageLocation + "ADLDOMParser()";
        String s5 = "";
        validation = false;
        messageHandler = new MessageHandler();
        baseDir = new String("");
        system = true;
        wellFormed = true;
        valid = true;
        extensionFound = false;
        tsPath = "";
        tsParent = "";
        if(DebugIndicator.ON)
            System.out.println("\n\n*****  In ADLDOMParser()  ******\n");
        try
        {
            setFeature("http://xml.org/sax/features/validation", false);
            setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
            setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);
            setErrorHandler(this);
        }
        catch(SAXException saxexception)
        {
            String s1 = "Error in setting parser feature.  Parser not created.";
            messageHandler.addMessage(i, j, s1, s4, s5);
        }
       
        try
        {
            setIncludeIgnorableWhitespace(false);
        }
        catch(SAXNotRecognizedException saxnotrecognizedexception)
        {
            String s2 = "Error in setting ignoring whitespace. SAX Not Recognized!";
            messageHandler.addMessage(i, j, s2, s4, s5);
        }
        catch(SAXNotSupportedException saxnotsupportedexception)
        {
            String s3 = "Error in setting ignoring whitespace. SAX Not Supported!";
            messageHandler.addMessage(i, j, s3, s4, s5);
        }
    }

    public ADLDOMParser(boolean flag)
    {
        messageLocation = "ADLDOMParser::";
        int i = MessageClassification.SYSTEM;
        int j = MessageType.FAILED;
        String s = "";
        String s4 = messageLocation + "ADLDOMParser(boolean)";
        String s5 = "";
        validation = flag;
        messageHandler = new MessageHandler();
        baseDir = new String("");
        system = true;
        wellFormed = true;
        valid = true;
        extensionFound = false;
        tsPath = "";
        tsParent = "";
        if(DebugIndicator.ON)
            System.out.println("\n\n*****  In ADLDOMParser()  ******\n");
        try
        {
            setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
            setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);
            if(validation)
            {
                setValidationOn();
            } else
            {
                setFeature("http://xml.org/sax/features/validation", false);
                setErrorHandler(this);
            }
        }
        catch(SAXException saxexception)
        {
            String s1 = "Error in setting parser feature.  Parser not created.";
            messageHandler.addMessage(i, j, s1, s4, s5);
        }
        try
        {
            setIncludeIgnorableWhitespace(false);
        }
        catch(SAXNotRecognizedException saxnotrecognizedexception)
        {
            String s2 = "Error in setting ignoring whitespace. SAX Not Recognized!";
            messageHandler.addMessage(i, j, s2, s4, s5);
        }
        catch(SAXNotSupportedException saxnotsupportedexception)
        {
            String s3 = "Error in setting ignoring whitespace. SAX Not Supported!";
            messageHandler.addMessage(i, j, s3, s4, s5);
        }
    }

    /* public void setTSPath(String s)
    {
        StringTokenizer stringtokenizer;
        int i;
        int j;
        tsPath = s;
        stringtokenizer = new StringTokenizer(tsPath, "\\", false);
        i = stringtokenizer.countTokens();
        j = 0;
          goto _L1
_L3:
        new StringBuffer();
        this;
        JVM INSTR dup_x1 ;
        tsParent;
        append();
        stringtokenizer.nextToken();
        append();
        "\\";
        append();
        toString();
        tsParent;
        j++;
_L1:
        if(j < i - 1) goto _L3; else goto _L2
_L2:
    }*/

    public void setValidationOn()
    {
        int i = MessageClassification.SYSTEM;
        int j = MessageType.FAILED;
        String s = "";
        String s2 = messageLocation + "setValidationON()";
        String s3 = "";
        try
        {
            setFeature("http://xml.org/sax/features/validation", true);
            setFeature("http://xml.org/sax/features/namespaces", true);
            setFeature("http://apache.org/xml/features/validation/schema", true);
            setErrorHandler(this);
        }
        catch(SAXException saxexception)
        {
            String s1 = "Error in setting parser feature.  Parser not created.";
            messageHandler.addMessage(i, j, s1, s2, s3);
        }
        validation = true;
    }

    public boolean parseFile(String s)
    {
        boolean flag = false;
        if(DebugIndicator.ON)
            System.out.println("getting InputSource...");
        InputSource inputsource = getFileSource(s);
        String s3 = messageLocation + "parseFile(String)";
        String s4 = "";
        try
        {
            if(DebugIndicator.ON)
                System.out.println("calling parse...");
            super.parse(inputsource);
            if(DebugIndicator.ON)
                System.out.println("done with parse parse...");
        }
        catch(SAXException saxexception)
        {
            int i1 = MessageType.FAILED;
            String s1 = "Error in parsing new input source.  SAX Exception!";
            if(validation)
            {
                int i = MessageClassification.VALID;
                messageHandler.addMessage(i, i1, s1, s3, s4);
                valid = false;
            } else
            {
                int j = MessageClassification.WELLFORMED;
                messageHandler.addMessage(j, i1, s1, s3, s4);
                wellFormed = false;
                valid = false;
            }
        }
        catch(IOException ioexception)
        {
            int j1 = MessageType.FAILED;
            String s2 = "Error in parsing new input source.  IO Exception!";
            if(validation)
            {
                int k = MessageClassification.VALID;
                messageHandler.addMessage(k, j1, s2, s3, s4);
                valid = false;
            } else
            {
                int l = MessageClassification.WELLFORMED;
                messageHandler.addMessage(l, j1, s2, s3, s4);
                wellFormed = false;
                valid = false;
            }
        }
        catch(NullPointerException nullpointerexception1)
        {
            if(DebugIndicator.ON)
                System.out.println("caught NullPointerException");
            wellFormed = false;
            valid = false;
        }
        try
        {
            document = getDocument();
            rootNode = document.getDocumentElement();
            if(document.hasChildNodes())
                flag = true;
        }
        catch(NullPointerException nullpointerexception)
        {
            if(DebugIndicator.ON)
                System.out.println("caught NullPointerException");
            wellFormed = false;
            valid = false;
        }
        return flag;
    }

    public boolean parseDocumentNode(Document document1)
    {
        boolean flag = false;
        boolean flag1 = true;
        String s4 = messageLocation + "parseDocumentNode(Document)";
        String s5 = "";
        InputSource inputsource;
        try
        {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            OutputFormat outputformat = new OutputFormat(document);
            outputformat.setIndenting(false);
            XMLSerializer xmlserializer = new XMLSerializer(bytearrayoutputstream, outputformat);
            xmlserializer.asDOMSerializer();
            xmlserializer.serialize(document.getDocumentElement());
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(bytearrayoutputstream.toByteArray());
            inputsource = new InputSource(bytearrayinputstream);
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            inputsource = new InputSource();
            flag1 = false;
            int i2 = MessageType.FAILED;
            String s = "Error in creating new input source  File Not Found Exception!";
            if(validation)
            {
                int i = MessageClassification.VALID;
                messageHandler.addMessage(i, i2, s, s4, s5);
                valid = false;
            } else
            {
                int j = MessageClassification.WELLFORMED;
                messageHandler.addMessage(j, i2, s, s4, s5);
                wellFormed = false;
            }
        }
        catch(IOException ioexception)
        {
            inputsource = new InputSource();
            flag1 = false;
            int j2 = MessageType.FAILED;
            String s1 = "Error in creating new input source IO Exception!";
            if(validation)
            {
                int k = MessageClassification.VALID;
                messageHandler.addMessage(k, j2, s1, s4, s5);
                valid = false;
            } else
            {
                int l = MessageClassification.WELLFORMED;
                messageHandler.addMessage(l, j2, s1, s4, s5);
                wellFormed = false;
            }
        }
        if(flag1)
        {
            try
            {
                super.parse(inputsource);
            }
            catch(SAXException saxexception)
            {
                int k2 = MessageType.FAILED;
                String s2 = "Error in parsing new input source.  SAX Exception!";
                if(validation)
                {
                    int i1 = MessageClassification.VALID;
                    messageHandler.addMessage(i1, k2, s2, s4, s5);
                    valid = false;
                } else
                {
                    int j1 = MessageClassification.WELLFORMED;
                    messageHandler.addMessage(j1, k2, s2, s4, s5);
                    wellFormed = false;
                }
            }
            catch(IOException ioexception1)
            {
                int l2 = MessageType.FAILED;
                String s3 = "Error in parsing new input source.  IO Exception!";
                String s6 = "";
                if(validation)
                {
                    int k1 = MessageClassification.VALID;
                    messageHandler.addMessage(k1, l2, s3, s4, s6);
                    valid = false;
                } else
                {
                    int l1 = MessageClassification.WELLFORMED;
                    messageHandler.addMessage(l1, l2, s3, s4, s6);
                    wellFormed = false;
                }
            }
            document = super.getDocument();
            rootNode = document.getDocumentElement();
            if(document.hasChildNodes())
                flag = true;
        }
        return flag;
    }

    public Node getRootNode()
    {
        return rootNode;
    }

    private InputSource getFileSource(String s)
    {
        int i = MessageClassification.SYSTEM;
        int j = MessageType.FAILED;
        String s4 = messageLocation + "getFileSource(String)";
        String s5 = "";
        try
        {
            File file = new File(s);
            if(file.isFile())
            {
                String s6 = file.getAbsolutePath();
                if(DebugIndicator.ON)
                    System.out.println("XML File: " + s6);
                StringTokenizer stringtokenizer = new StringTokenizer(s6, new String("\\"), true);
                int k = stringtokenizer.countTokens();
                k--;
                for(int l = 0; l < k; l++)
                {
                    String s7 = stringtokenizer.nextToken();
                    baseDir = baseDir + s7;
                }

                baseDir = baseDir.replace('\\', '/');
                if(baseDir.charAt(baseDir.length() - 1) != '/')
                    baseDir = baseDir + '/';
                if(DebugIndicator.ON)
                    System.out.println("baseDir = " + baseDir);
                FileReader filereader = new FileReader(file);
                InputSource inputsource = new InputSource(filereader);
                return inputsource;
            }
        }
        catch(NullPointerException nullpointerexception)
        {
            String s1 = "Error in accessing the given file.  Null Pointer Exception!";
            messageHandler.addMessage(i, j, s1, s4, s5);
            system = false;
        }
        catch(SecurityException securityexception)
        {
            String s2 = "Error in accessing the given file.  Security Exception!";
            messageHandler.addMessage(i, j, s2, s4, s5);
            system = false;
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            String s3 = "Error in accessing the given file.  File Not Found Exception!";
            messageHandler.addMessage(i, j, s3, s4, s5);
            system = false;
        }
        return new InputSource();
    }

    protected void pruneTree(Node node)
    {
        boolean flag = false;
        if(node == null)
            return;
        short word0 = node.getNodeType();
        switch(word0)
        {
        case 2: // '\002'
        case 6: // '\006'
        default:
            break;

        case 7: // '\007'
            if(flag)
                printNodeInfo("PROCESSING_INSTRUCTION_NODE", ((Document)node).getDocumentElement());
            break;

        case 9: // '\t'
            if(flag)
                printNodeInfo("DOCUMENT_NODE", ((Document)node).getDocumentElement());
            pruneTree(((Node) (((Document)node).getDocumentElement())));
            break;

        case 1: // '\001'
            if(flag)
                printNodeInfo("ELEMENT_NODE", node);
            String s1 = node.getNodeName();
            NamedNodeMap namednodemap = node.getAttributes();
            int j = namednodemap.getLength();
            for(int l = 0; l < j; l++)
            {
                Attr attr = (Attr)namednodemap.item(l);
                if(flag)
                    printNodeInfo("ATTRIBUTE_NODE", attr);
            }

            NodeList nodelist1 = node.getChildNodes();
            if(nodelist1 == null)
                break;
            for(int i1 = 0; i1 < nodelist1.getLength(); i1++)
                if(nodelist1.item(i1) instanceof TextImpl)
                {
                    String s = new String(nodelist1.item(i1).getNodeValue().trim());
                    if(((TextImpl)nodelist1.item(i1)).isIgnorableWhitespace())
                    {
                        node.removeChild(nodelist1.item(i1));
                        i1--;
                    } else
                    if(s.length() == 0)
                    {
                        node.removeChild(nodelist1.item(i1));
                        i1--;
                    }
                } else
                if(nodelist1.item(i1).getNodeType() == 8)
                {
                    node.removeChild(nodelist1.item(i1));
                    i1--;
                }

            int j1 = nodelist1.getLength();
            for(int k1 = 0; k1 < j1; k1++)
                pruneTree(nodelist1.item(k1));

            break;

        case 5: // '\005'
            if(flag)
                printNodeInfo("ENTITY_REFERENCE_NODE", node);
            NodeList nodelist = node.getChildNodes();
            if(nodelist == null)
                break;
            int i = nodelist.getLength();
            for(int k = 0; k < i; k++)
                pruneTree(nodelist.item(k));

            break;

        case 8: // '\b'
            if(flag)
                printNodeInfo("COMMENT_NODE", node);
            break;

        case 4: // '\004'
            if(flag)
                printNodeInfo("CDATA_SECTION_NODE", node);
            break;

        case 3: // '\003'
            if(node instanceof TextImpl)
            {
                if(((TextImpl)node).isIgnorableWhitespace())
                {
                    if(flag)
                        printNodeInfo("TEXT_NODE (TextImpl:Ignorable Whitespace)", node);
                    break;
                }
                if(flag)
                    printNodeInfo("TEXT_NODE (TextImpl:Element Data)", node);
                break;
            }
            if(flag)
                printNodeInfo("TEXT_NODE", node);
            break;
        }
    }

    protected void pruneTree(Node node, boolean flag)
    {
        boolean flag1 = false;
        boolean flag2 = flag;
        if(node == null)
            return;
        short word0 = node.getNodeType();
        switch(word0)
        {
        case 2: // '\002'
        case 6: // '\006'
        default:
            break;

        case 7: // '\007'
            if(flag1)
                printNodeInfo("PROCESSING_INSTRUCTION_NODE", ((Document)node).getDocumentElement());
            break;

        case 9: // '\t'
            if(flag1)
                printNodeInfo("DOCUMENT_NODE", ((Document)node).getDocumentElement());
            pruneTree(((Node) (((Document)node).getDocumentElement())), flag2);
            break;

        case 1: // '\001'
            if(flag1)
                printNodeInfo("ELEMENT_NODE", node);
            String s1 = node.getLocalName();
            NamedNodeMap namednodemap = node.getAttributes();
            int j = namednodemap.getLength();
            for(int l = 0; l < j; l++)
            {
                Attr attr = (Attr)namednodemap.item(l);
                if(flag1)
                    printNodeInfo("ATTRIBUTE_NODE", attr);
                String s2 = attr.getNamespaceURI();
                String s3 = attr.getNodeName();
                if(s2 != null && !s2.equals(CPNamespace) && !s2.equals(MDNamespace) && !s2.equals(XMLNamespace) && !s2.equals(ADLNamespace) && !s2.equals(nsNamespace) && !s2.equals(xsiNamespace) && flag2)
                {
                    if(DebugIndicator.ON)
                    {
                        System.out.println("Just tossed an extension attribute");
                        System.out.println("Name: " + attr.getNodeName());
                        System.out.println("attr ns: " + s2);
                    }
                    extensionFound = true;
                    namednodemap.removeNamedItem(attr.getNodeName());
                }
            }

            NodeList nodelist1 = node.getChildNodes();
            if(nodelist1 == null)
                break;
            for(int i1 = 0; i1 < nodelist1.getLength(); i1++)
            {
                String s4 = nodelist1.item(i1).getNamespaceURI();
                if(s4 != null && !s4.equals(CPNamespace) && !s4.equals(MDNamespace) && !s4.equals(XMLNamespace) && !s4.equals(ADLNamespace) && !s4.equals(nsNamespace) && !s4.equals(xsiNamespace) && flag2)
                {
                    if(DebugIndicator.ON)
                    {
                        System.out.println("Just tossed a child element");
                        System.out.println("Name: " + nodelist1.item(i1).getNodeName());
                        System.out.println("child ns: " + s4);
                    }
                    extensionFound = true;
                    node.removeChild(nodelist1.item(i1));
                } else
                if(nodelist1.item(i1) instanceof TextImpl)
                {
                    String s = new String(nodelist1.item(i1).getNodeValue().trim());
                    if(((TextImpl)nodelist1.item(i1)).isIgnorableWhitespace())
                    {
                        node.removeChild(nodelist1.item(i1));
                        i1--;
                    } else
                    if(s.length() == 0)
                    {
                        node.removeChild(nodelist1.item(i1));
                        i1--;
                    }
                } else
                if(nodelist1.item(i1).getNodeType() == 8)
                {
                    node.removeChild(nodelist1.item(i1));
                    i1--;
                }
            }

            if(s1.equals("lom"))
            {
                if(DebugIndicator.ON)
                    System.out.println("found lom element");
                flag2 = false;
            }
            int j1 = nodelist1.getLength();
            for(int k1 = 0; k1 < j1; k1++)
                pruneTree(nodelist1.item(k1), flag2);

            break;

        case 5: // '\005'
            if(flag1)
                printNodeInfo("ENTITY_REFERENCE_NODE", node);
            NodeList nodelist = node.getChildNodes();
            if(nodelist == null)
                break;
            int i = nodelist.getLength();
            for(int k = 0; k < i; k++)
                pruneTree(nodelist.item(k), flag2);

            break;

        case 8: // '\b'
            if(flag1)
                printNodeInfo("COMMENT_NODE", node);
            break;

        case 4: // '\004'
            if(flag1)
                printNodeInfo("CDATA_SECTION_NODE", node);
            break;

        case 3: // '\003'
            if(node instanceof TextImpl)
            {
                if(((TextImpl)node).isIgnorableWhitespace())
                {
                    if(flag1)
                        printNodeInfo("TEXT_NODE (TextImpl:Ignorable Whitespace)", node);
                    break;
                }
                if(flag1)
                    printNodeInfo("TEXT_NODE (TextImpl:Element Data)", node);
                break;
            }
            if(flag1)
                printNodeInfo("TEXT_NODE", node);
            break;
        }
    }

    protected void printNodeInfo(String s, Node node)
    {
        StringBuffer stringbuffer = new StringBuffer("(null)");
        StringBuffer stringbuffer1 = new StringBuffer("(null)");
        StringBuffer stringbuffer2 = new StringBuffer("(null)");
        StringBuffer stringbuffer3 = new StringBuffer("(null)");
        String s1 = new String(" -- ");
        if(node != null)
        {
            if(s != null)
                stringbuffer = new StringBuffer(s);
            stringbuffer1 = new StringBuffer((new Integer(node.getNodeType())).toString());
            if(node.getNodeName() != null)
                stringbuffer2 = new StringBuffer(node.getNodeName());
            if(node.getNodeValue() != null)
                stringbuffer3 = new StringBuffer(node.getNodeValue());
        }
        for(; stringbuffer.length() < 42; stringbuffer.append(" "));
        for(; stringbuffer2.length() < 15; stringbuffer2.append(" "));
        for(; stringbuffer3.length() < 10; stringbuffer3.append(" "));
        if(DebugIndicator.ON)
            System.out.println(stringbuffer + s1 + stringbuffer1 + s1 + stringbuffer2 + s1 + stringbuffer3);
    }

    public Vector getMessage(int i)
    {
        return messageHandler.getMessage(i);
    }

    public boolean isValidationON()
    {
        return validation;
    }

    public boolean isSystem()
    {
        return system;
    }

    public boolean isWellFormed()
    {
        return wellFormed;
    }

    public boolean isValid()
    {
        return valid;
    }

    public void clearSystem()
    {
        messageHandler.clearMessage(MessageClassification.SYSTEM);
        system = true;
    }

    public void warning(SAXParseException saxparseexception)
    {
        int k = MessageType.FAILED;
        String s = saxparseexception.getMessage();
        String s1 = messageLocation + "warning(SAXParseException)";
        String s2 = saxparseexception.getSystemId() + " on line" + saxparseexception.getLineNumber();
        if(validation)
        {
            int i = MessageClassification.VALID;
            messageHandler.addMessage(i, k, s, s1, s2);
            valid = false;
        } else
        {
            int j = MessageClassification.WELLFORMED;
            messageHandler.addMessage(j, k, s, s1, s2);
            wellFormed = false;
        }
    }

    public void error(SAXParseException saxparseexception)
    {
        int k = MessageType.FAILED;
        String s = saxparseexception.getMessage();
        String s1 = messageLocation + "error(SAXParseException)";
        String s2 = "line = " + saxparseexception.getLineNumber();
        if(validation)
        {
            int i = MessageClassification.VALID;
            messageHandler.addMessage(i, k, s, s1, s2);
            valid = false;
        } else
        {
            int j = MessageClassification.WELLFORMED;
            messageHandler.addMessage(j, k, s, s1, s2);
            wellFormed = false;
        }
    }

    public void fatalError(SAXParseException saxparseexception)
    {
        int k = MessageType.FAILED;
        String s = saxparseexception.getMessage();
        String s1 = messageLocation + "fatalError(SAXParseException)";
        String s2 = "line = " + saxparseexception.getLineNumber();
        if(validation)
        {
            int i = MessageClassification.VALID;
            messageHandler.addMessage(i, k, s, s1, s2);
            valid = false;
        } else
        {
            int j = MessageClassification.WELLFORMED;
            messageHandler.addMessage(j, k, s, s1, s2);
            wellFormed = false;
        }
    }

}
