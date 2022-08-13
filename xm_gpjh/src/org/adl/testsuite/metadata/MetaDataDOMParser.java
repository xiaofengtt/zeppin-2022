// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MetaDataDOMParser.java

package org.adl.testsuite.metadata;

import java.io.*;
import java.net.URL;
import java.util.Collection;
import java.util.Vector;
import org.adl.util.Message;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.TextImpl;
import org.apache.xerces.framework.XMLParser;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class MetaDataDOMParser
    implements ErrorHandler
{

    protected Vector messages;
    protected String fileName;
    protected DOMParser parser;
    protected Document document;
    protected Node metadataNode;
    private static final int parserMessageCeiling = 50;
    private boolean warningNotCaught;
    private boolean errorNotCaught;
    private boolean fatalErrorNotCaught;
    private boolean wellFormed;
    private String mySchemaLocation;
    private boolean extensionsUsed;
    private static String MDNamespace = "http://www.imsglobal.org/xsd/imsmd_rootv1p2p1";
    private static String XMLNamespace = "http://www.w3.org/XML/1998/namespace";
    private static String nsNamespace = "http://www.w3.org/2000/xmlns/";
    private static String xsiNamespace = "http://www.w3.org/2001/XMLSchema-instance";

    public MetaDataDOMParser(String s, String s1)
    {
        warningNotCaught = true;
        errorNotCaught = true;
        fatalErrorNotCaught = true;
        mySchemaLocation = "";
        extensionsUsed = false;
        if(DebugIndicator.ON)
            System.out.println("\n\n*****  In MetaDataDOMParser( file )  ******\n");
        messages = new Vector(0, 100);
        fileName = searchFile(s, "xml");
        parser = null;
        document = null;
        wellFormed = preProcess();
    }

    public MetaDataDOMParser(Node node, String s)
    {
        warningNotCaught = true;
        errorNotCaught = true;
        fatalErrorNotCaught = true;
        mySchemaLocation = "";
        extensionsUsed = false;
        if(DebugIndicator.ON)
            System.out.println("\n\n*****  In MetaDataDOMParser( node )  ******\n");
        messages = new Vector(0, 100);
        parser = null;
        wellFormed = true;
    }

    public String getXMLFileName()
    {
        if(DebugIndicator.ON)
            System.out.println("Entering MetaDataDOMParser::getXMLFileName()");
        if(fileName != null)
            return fileName;
        else
            return new String("");
    }

    public boolean preProcess()
    {
        boolean flag = true;
        if(DebugIndicator.ON)
            System.out.println("Entering MetaDataDOMParser::preProcess()");
        if(parser == null)
            createParser();
        if(parser != null)
        {
            try
            {
                parser.setFeature("http://xml.org/sax/features/validation", false);
                int i = messages.size();
                if(fileName != null)
                {
                    InputSource inputsource = setUpInputSource(fileName);
                    parser.parse(inputsource);
                    if(DebugIndicator.ON)
                        System.out.println("Non-validated Document parsing complete.");
                }
                if(messages.size() == i)
                    document = parser.getDocument();
            }
            catch(SAXException saxexception)
            {
                setMessage(MessageType.FAILED, "MetaDataDOMParser::preProcess()", "Parser threw a SAXException.");
                flag = false;
            }
            catch(IOException ioexception)
            {
                setMessage(MessageType.FAILED, "MetaDataDOMParser::preProcess()", "Parser threw a IOException.");
                flag = false;
            }
        } else
        {
            flag = false;
            if(DebugIndicator.ON)
                System.out.println("Parser must equal null");
        }
        if(flag && document != null)
        {
            try
            {
                org.w3c.dom.Element element = document.getDocumentElement();
                pruneTree(element);
            }
            catch(NullPointerException nullpointerexception)
            {
                setMessage(MessageType.FAILED, "MetaDataDOMParser::preProcess()", "There was a problem parsing the XML with validation off");
                flag = false;
            }
            if(DebugIndicator.ON)
                System.out.println("###   Pre-processing is complete!!  ###");
        } else
        {
            setMessage(MessageType.FAILED, "MetaDataDOMParser::preProcess()", "There was a problem parsing the XML with validation off");
            flag = false;
        }
        if(DebugIndicator.ON)
            System.out.println("Exiting MetaDataDOMParser::preProcess()");
        return flag;
    }

    public boolean validate()
    {
        boolean flag = false;
        boolean flag1 = false;
        if(DebugIndicator.ON && DebugIndicator.ON)
            System.out.println("Entering MetaDataDOMParser::validate()");
        if(parser == null)
            preProcess();
        try
        {
            parser.setFeature("http://xml.org/sax/features/validation", true);
        }
        catch(SAXException saxexception)
        {
            setMessage(MessageType.FAILED, "MetaDataDOMParser::validate()", "Parser threw a SAXException - could not reset validation.");
        }
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
            flag1 = true;
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            setMessage(MessageType.FAILED, "MetaDataDOMParser::validate()", "FileNotFoundException caught");
            inputsource = new InputSource();
        }
        catch(IOException ioexception)
        {
            setMessage(MessageType.FAILED, "MetaDataDOMParser::validate()", "IOException caught: could not serialize");
            inputsource = new InputSource();
        }
        if(parser != null && wellFormed && flag1)
        {
            try
            {
                parser.parse(inputsource);
            }
            catch(SAXException saxexception1)
            {
                setMessage(MessageType.FAILED, "MetaDataDOMParser::validate()", "SAXException thrown: could not parse");
            }
            catch(IOException ioexception1)
            {
                setMessage(MessageType.FAILED, "MetaDataDOMParser::validate()", "IOException thrown: could not parse");
            }
            boolean flag2 = errorNotCaught && warningNotCaught && fatalErrorNotCaught;
            if(flag2)
            {
                document = (DocumentImpl)parser.getDocument();
                flag = true;
            } else
            {
                flag = false;
            }
        }
        if(DebugIndicator.ON)
            System.out.println("Exiting MetaDataDOMParser::validate()");
        return flag;
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

    public void warning(SAXParseException saxparseexception)
    {
        warningNotCaught = false;
        if(messages.size() < 50)
            setMessage(MessageType.WARNING, "XMLDocTester::warning()", "Parser warning on line " + saxparseexception.getLineNumber() + ":  " + saxparseexception.getMessage());
        else
        if(messages.size() == 50)
            setMessage(MessageType.INFO, "", "Message limit reached.  Additional parser messages will not be logged.");
    }

    public void error(SAXParseException saxparseexception)
    {
        errorNotCaught = false;
        if(messages.size() < 50)
            setMessage(MessageType.FAILED, "XMLDocTester::error()", "Parser error on line " + saxparseexception.getLineNumber() + ":  " + saxparseexception.getMessage());
        else
        if(messages.size() == 50)
            setMessage(MessageType.INFO, "", "Message limit reached.  Additional parser messages will not be logged.");
    }

    public void fatalError(SAXParseException saxparseexception)
    {
        fatalErrorNotCaught = false;
        if(messages.size() < 50)
            setMessage(MessageType.FAILED, "XMLDocTester::fatalError()", "Parser fatal error on line " + saxparseexception.getLineNumber() + ":  " + saxparseexception.getMessage());
        else
        if(messages.size() == 50)
            setMessage(MessageType.INFO, "", "Message limit reached.  Additional parser messages will not be logged.");
    }

    protected boolean createParser()
    {
        boolean flag;
        try
        {
            parser = new DOMParser();
            parser.setFeature("http://xml.org/sax/features/validation", false);
            parser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
            parser.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);
            parser.setErrorHandler(this);
            flag = true;
        }
        catch(SAXException saxexception)
        {
            parser = null;
            flag = false;
            setMessage(MessageType.FAILED, "XMLDocTester::CreateParser()", "Error in setting parser feature.  Parser not created.");
        }
        return flag;
    }

    protected void pruneTree(Node node)
    {
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
            if(DebugIndicator.ON)
                printNodeInfo("PROCESSING_INSTRUCTION_NODE", ((Document)node).getDocumentElement());
            break;

        case 9: // '\t'
            if(DebugIndicator.ON)
                printNodeInfo("DOCUMENT_NODE", ((Document)node).getDocumentElement());
            pruneTree(((Node) (((Document)node).getDocumentElement())));
            break;

        case 1: // '\001'
            if(DebugIndicator.ON)
                printNodeInfo("ELEMENT_NODE", node);
            String s1 = node.getLocalName();
            NamedNodeMap namednodemap = node.getAttributes();
            int j = namednodemap.getLength();
            for(int l = 0; l < j; l++)
            {
                Attr attr = (Attr)namednodemap.item(l);
                if(DebugIndicator.ON)
                    printNodeInfo("ATTRIBUTE_NODE", attr);
                String s2 = attr.getNamespaceURI();
                String s3 = attr.getNodeName();
                if(s2 != null && !s2.equals(MDNamespace) && !s2.equals(XMLNamespace) && !s2.equals(nsNamespace) && !s2.equals(xsiNamespace))
                {
                    System.out.println("Just tossed an extension attribute");
                    System.out.println("Name: " + attr.getNodeName());
                    System.out.println("attr ns: " + s2);
                    extensionsUsed = true;
                    node.removeChild(attr);
                }
            }

            NodeList nodelist1 = node.getChildNodes();
            if(nodelist1 == null)
                break;
            for(int i1 = 0; i1 < nodelist1.getLength(); i1++)
            {
                String s4 = nodelist1.item(i1).getNamespaceURI();
                if(s4 != null && !s4.equals(MDNamespace) && !s4.equals(XMLNamespace) && !s4.equals(nsNamespace) && !s4.equals(xsiNamespace))
                {
                    if(DebugIndicator.ON)
                    {
                        System.out.println("Just tossed a child element");
                        System.out.println("Name: " + nodelist1.item(i1).getNodeName());
                        System.out.println("child ns: " + s4);
                    }
                    extensionsUsed = true;
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

            int j1 = nodelist1.getLength();
            for(int k1 = 0; k1 < j1; k1++)
                pruneTree(nodelist1.item(k1));

            break;

        case 5: // '\005'
            if(DebugIndicator.ON)
                printNodeInfo("ENTITY_REFERENCE_NODE", node);
            NodeList nodelist = node.getChildNodes();
            if(nodelist == null)
                break;
            int i = nodelist.getLength();
            for(int k = 0; k < i; k++)
                pruneTree(nodelist.item(k));

            break;

        case 8: // '\b'
            if(DebugIndicator.ON)
                printNodeInfo("COMMENT_NODE", node);
            break;

        case 4: // '\004'
            if(DebugIndicator.ON)
                printNodeInfo("CDATA_SECTION_NODE", node);
            break;

        case 3: // '\003'
            if(node instanceof TextImpl)
            {
                if(((TextImpl)node).isIgnorableWhitespace())
                {
                    if(DebugIndicator.ON)
                        printNodeInfo("TEXT_NODE (TextImpl:Ignorable Whitespace)", node);
                    break;
                }
                if(DebugIndicator.ON)
                    printNodeInfo("TEXT_NODE (TextImpl:Element Data)", node);
                break;
            }
            if(DebugIndicator.ON)
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

    private InputSource setUpInputSource(String s)
    {
        InputSource inputsource = new InputSource();
        inputsource = setupFileSource(s);
        return inputsource;
    }

    private InputSource setupFileSource(String s)
    {
        try
        {
            File file = new File(s);
            if(DebugIndicator.ON)
                System.out.println(file.getAbsolutePath());
            if(file.isFile())
            {
                FileReader filereader = new FileReader(file);
                InputSource inputsource = new InputSource(filereader);
                return inputsource;
            }
            if(s.length() > 6 && (s.substring(0, 5).equals("http:") || s.substring(0, 6).equals("https:")))
            {
                URL url = new URL(s);
                java.io.InputStream inputstream = url.openStream();
                InputSource inputsource1 = new InputSource(inputstream);
                return inputsource1;
            }
            System.out.println("XML File: " + s + " is not a file or URL");
        }
        catch(NullPointerException nullpointerexception)
        {
            System.out.println("Null pointer exception" + nullpointerexception);
        }
        catch(SecurityException securityexception)
        {
            System.out.println("Security Exception" + securityexception);
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            System.out.println("File Not Found Exception" + filenotfoundexception);
        }
        catch(Exception exception)
        {
            System.out.println("General Exception" + exception);
        }
        return new InputSource();
    }

    private MetaDataDOMParser()
    {
        warningNotCaught = true;
        errorNotCaught = true;
        fatalErrorNotCaught = true;
        mySchemaLocation = "";
        extensionsUsed = false;
    }

    private String searchFile(String s, String s1)
    {
        boolean flag = false;
        String s2 = new String("");
        try
        {
            if(s.length() > 6 && (s.substring(0, 5).equals("http:") || s.substring(0, 6).equals("https:")))
            {
                s2 = s;
            } else
            {
                File file = new File(s);
                if(file.isFile())
                {
                    boolean flag1 = true;
                    s2 = file.getAbsolutePath();
                    if(DebugIndicator.ON)
                        System.out.println(s2 + " was found!!");
                } else
                {
                    setMessage(MessageType.FAILED, "MetaDataDOMParser::searchFile()", s1 + " File Not A Normal File.  Verify the" + " specified " + s1 + " file, " + s + ", exists.");
                }
            }
        }
        catch(NullPointerException nullpointerexception)
        {
            setMessage(MessageType.FAILED, "MetaDataDOMParser::searchFile()", "Empty " + s1 + " Filename Specified.");
        }
        catch(SecurityException securityexception)
        {
            setMessage(MessageType.FAILED, "MetaDataDOMParser::searchFile()", s1 + " File Not Accessible.");
        }
        return s2;
    }

    public boolean isWellFormed()
    {
        return wellFormed;
    }

    public boolean isExtensionsUsed()
    {
        return extensionsUsed;
    }

}
