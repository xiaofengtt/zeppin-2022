// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CPDOMParser.java

package org.adl.parsers.dom;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import org.adl.parsers.util.MessageClassification;
import org.adl.parsers.util.MessageHandler;
import org.adl.testsuite.metadata.MetaDataDOMParser;
import org.adl.testsuite.metadata.MetaDataTester;
import org.adl.testsuite.util.AuditorIndicator;
import org.adl.util.Message;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.*;
import org.xml.sax.*;

// Referenced classes of package org.adl.parsers.dom:
//            ADLDOMParser, ADLManifest, ADLMetadata, ADLElement

public class CPDOMParser extends ADLDOMParser
{

    protected ADLManifest manifest;
    private boolean minimum;
    private boolean extensionsNotUsed;
    private int messageClass;
    private int messageType;
    private String messageText;
    private String messageLocation;
    private String messageTrace;
    private String tsPath;
    private String filePath;
    private String testType;
    private String tsParent;
    private String dateTime;

    public CPDOMParser()
    {
        minimum = true;
        extensionsNotUsed = true;
        messageText = "";
        messageLocation = "CPDOMParser::";
        messageTrace = "";
        tsPath = "";
        filePath = "";
        testType = "";
        tsParent = "";
        dateTime = "";
        manifest = new ADLManifest();
    }

    public CPDOMParser(boolean flag)
    {
        super(flag);
        minimum = true;
        extensionsNotUsed = true;
        messageText = "";
        messageLocation = "CPDOMParser::";
        messageTrace = "";
        tsPath = "";
        filePath = "";
        testType = "";
        tsParent = "";
        dateTime = "";
        manifest = new ADLManifest();
    }

    public boolean setControlDocs()
    {
        boolean flag = true;
        int i = MessageClassification.SYSTEM;
        int j = MessageType.FAILED;
        String s = "";
        String s2 = messageLocation + "setControlDocs()";
        String s3 = "";
        String s4 = "http://www.w3.org/2001/XMLSchema-instance";
        String s5 = "http://www.imsproject.org/xsd/imscp_rootv1p1p2";
        String s6 = "http://www.imsproject.org/xsd/imsmd_rootv1p2";
        String s7 = "http://www.adlnet.org/xsd/adl_cp_rootv1p1";
        NamedNodeMap namednodemap = rootNode.getAttributes();
        int k = namednodemap.getLength();
        for(int l = 0; l < k; l++)
        {
            Attr attr = (Attr)namednodemap.item(l);
            String s8 = attr.getNodeName();
            if(s8.equals("xsi:schemaLocation"))
                try
                {
                    attr.setNodeValue(s4 + " ..\\..\\..\\..\\..\\ims_xml.xsd " + s5 + " ..\\..\\..\\..\\..\\imscp_rootv1p1p2.xsd " + s6 + " ..\\..\\..\\..\\..\\imsmd_rootv1p2.xsd " + s7 + " ..\\..\\..\\..\\..\\adl_cp_rootv1p1.xsd");
                }
                catch(DOMException domexception)
                {
                    String s1 = "Error in setting the value for xsi.schemaLocation.";
                    messageHandler.addMessage(i, j, s1, s2, s3);
                    flag = false;
                }
        }

        return flag;
    }

    public boolean setControlDocsProperty()
    {
        boolean flag = true;
        int i = MessageClassification.SYSTEM;
        int j = MessageType.FAILED;
        String s = "";
        String s4 = messageLocation + "setControlDocsProperty()";
        String s5 = "";
        String s6 = "http://www.w3.org/2001/XMLSchema-instance";
        String s7 = "http://www.imsproject.org/xsd/imscp_rootv1p1p2";
        String s8 = "http://www.imsproject.org/xsd/imsmd_rootv1p2";
        String s9 = "http://www.adlnet.org/xsd/adl_cp_rootv1p1";
        try
        {
            setProperty("http://apache.org/xml/properties/external-schemaLocation", s6 + " ..\\..\\..\\..\\..\\ims_xml.xsd " + s7 + " ..\\..\\..\\..\\..\\imscp_rootv1p1p2.xsd " + s9 + " ..\\..\\..\\..\\..\\adl_cp_rootv1p1.xsd" + s8 + " ..\\..\\..\\..\\..\\imsmd_rootv1p2.xsd ");
        }
        catch(SAXNotRecognizedException saxnotrecognizedexception)
        {
            String s1 = "Error in setting parser property. SAX Not Recognized!";
            messageHandler.addMessage(i, j, s1, s4, s5);
            flag = false;
        }
        catch(SAXNotSupportedException saxnotsupportedexception)
        {
            String s2 = "Error in setting parser property. SAX Not Supported!";
            messageHandler.addMessage(i, j, s2, s4, s5);
            flag = false;
        }
        catch(SAXException saxexception)
        {
            String s3 = "Error in setting parser property.";
            messageHandler.addMessage(i, j, s3, s4, s5);
            flag = false;
        }
        return flag;
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
        super.setTSPath(tsParent);
        return;
    }*/

    public void setTestType(String s)
    {
        testType = s;
    }

    public void setFilePath(String s)
    {
        filePath = s;
    }

    public void setDateTime(String s)
    {
        dateTime = s;
    }

    public boolean checkManifest(String s, String s1)
    {
        String s2 = "";
        String s4 = messageLocation + "checkManifest(String)";
        String s5 = "";
        int i = MessageClassification.WELLFORMED;
        int j = MessageType.INFO;
        s2 = "*****************************************";
        messageHandler.addMessage(i, j, s2, s4, s5);
        s2 = "Testing the manifest for Well-Formedness";
        messageHandler.addMessage(i, j, s2, s4, s5);
        s2 = "*****************************************";
        messageHandler.addMessage(i, j, s2, s4, s5);
        boolean flag = parseFile(s);
        if(flag && rootNode != null)
            pruneTree(rootNode, true);
        i = MessageClassification.VALID;
        j = MessageType.INFO;
        s2 = "*****************************************";
        messageHandler.addMessage(i, j, s2, s4, s5);
        s2 = "Testing the manifest for validity against the controlling documents";
        messageHandler.addMessage(i, j, s2, s4, s5);
        s2 = "*****************************************";
        messageHandler.addMessage(i, j, s2, s4, s5);
        if(wellFormed)
        {
            setValidationOn();
            flag = parseDocumentNode(document);
        } else
        {
            int k = MessageType.FAILED;
            String s3 = "The manifest is NOT valid!";
            messageHandler.addMessage(i, k, s3, s4, s5);
            valid = false;
        }
        if(DebugIndicator.ON)
            System.out.println("extensionfound: " + extensionFound);
        if(valid)
        {
            fill();
            minimum = checkMinimum(s1) && minimum;
            extensionsNotUsed = !extensionFound;
        } else
        {
            extensionsNotUsed = !extensionFound;
            minimum = false;
        }
        return flag && wellFormed && valid && minimum;
    }

    public boolean checkMinimum(String s)
    {
        boolean flag = false;
        String s1 = "";
        String s2 = messageLocation + "checkMinimum()";
        String s3 = "";
        int i = MessageClassification.MINIMUM;
        int j = MessageType.INFO;
        s1 = "*****************************************";
        messageHandler.addMessage(i, j, s1, s2, s3);
        s1 = "Testing the manifest for minimum conformance";
        messageHandler.addMessage(i, j, s1, s2, s3);
        s1 = "*****************************************";
        messageHandler.addMessage(i, j, s1, s2, s3);
        flag = manifest.checkConformance(s, baseDir);
        messageHandler.appendMessage(i, manifest.getMessage(i));
        return flag;
    }

    public boolean checkMetadata()
    {
        boolean flag = true;
        String s = "";
        String s3 = messageLocation + "checkMetadata()";
        String s4 = "";
        int i = MessageClassification.METADATA;
        int j = MessageType.OTHER;
        s = "";
        messageHandler.addMessage(i, j, s, s3, s4);
        j = MessageType.OTHER;
        s = "=============== Meta-data Testing ==============";
        messageHandler.addMessage(i, j, s, s3, s4);
        j = MessageType.OTHER;
        s = "Testing meta-data found within the package";
        messageHandler.addMessage(i, j, s, s3, s4);
        j = MessageType.OTHER;
        s = "=============== Meta-data Testing ==============";
        messageHandler.addMessage(i, j, s, s3, s4);
        j = MessageType.OTHER;
        s = "";
        messageHandler.addMessage(i, j, s, s3, s4);
        Vector vector = new Vector(10, 10);
        vector = manifest.getMetadata();
        Object obj = null;
        String s5 = "";
        String s7 = "";
        String s9 = "";
        Object obj1 = null;
        boolean flag1 = false;
        int i1 = vector.size();
        if(DebugIndicator.ON)
            System.out.println("Number of MD recs found: " + i1);
        for(int j1 = 0; j1 < i1; j1++)
        {
            ADLMetadata adlmetadata = (ADLMetadata)vector.elementAt(j1);
            if(adlmetadata != null)
            {
                String s10 = adlmetadata.getMetadataLocation();
                Node node = adlmetadata.getMetadataRoot();
                if(node != null || !s10.equalsIgnoreCase(""))
                {
                    String s11 = "MD_" + j1 + "_Log.htm";
                    s11 = tsPath + "Log_" + dateTime + "\\" + s11;
                    File file = new File(s11);
                    String s12 = file.getParent();
                    if(s12 != null)
                    {
                        File file1 = new File(s12);
                        if(!file1.exists())
                            file1.mkdirs();
                    }
                    try
                    {
                        FileWriter filewriter = new FileWriter(file);
                        prepareMDFile(filewriter);
                        int k = MessageType.OTHER;
                        String s1 = "<br><br>***************************************************";
                        messageHandler.addMessage(i, k, s1, s3, s4);
                        messageHandler.addMessage(i, k, s1, s3, s4, filewriter);
                        k = MessageType.INFO;
                        s1 = "Testing next meta-data file:";
                        messageHandler.addMessage(i, k, s1, s3, s4);
                        messageHandler.addMessage(i, k, s1, s3, s4, filewriter);
                        s1 = "---for parent element: " + adlmetadata.getParentName();
                        messageHandler.addMessage(i, k, s1, s3, s4);
                        messageHandler.addMessage(i, k, s1, s3, s4, filewriter);
                        s1 = "---with ID: " + adlmetadata.getParentID() + "<br><br>";
                        messageHandler.addMessage(i, k, s1, s3, s4);
                        messageHandler.addMessage(i, k, s1, s3, s4, filewriter);
                        String s6 = adlmetadata.getMetadataType();
                        String s8 = adlmetadata.getMetadataLocation();
                        Node node1 = adlmetadata.getMetadataRoot();
                        boolean flag3 = false;
                        if(s8 != "")
                        {
                            if(s8.length() > 6 && (s8.substring(0, 5).equals("http:") || s8.substring(0, 6).equals("https:")))
                            {
                                flag3 = true;
                                if(DebugIndicator.ON)
                                    System.out.println("got external md");
                            }
                            if(testType.equals("pif") && !flag3)
                                s8 = tsPath + "PackageImport\\" + s8;
                            else
                            if(testType.equals("media") && !flag3)
                                s8 = filePath + s8;
                        }
                        if(DebugIndicator.ON)
                        {
                            System.out.println("Meta-data record: " + j1);
                            System.out.println("Meta-data type: " + s6);
                            System.out.println("Meta-data file: " + s8);
                        }
                        if(s8 != "" || node1 != null)
                        {
                            boolean flag2 = startValidateTest(s6, s8, node1, filewriter);
                            if(!flag2 && flag)
                                flag = false;
                        }
                        filewriter.write("</body>\n");
                        filewriter.close();
                    }
                    catch(Exception exception) { }
                    int l = MessageType.OTHER;
                    String s13 = replace(s11, "\\", "/");
                    String s2 = "<a href=\\\"" + s13 + "\\\" target=\\\"_blank\\\">Click here to view complete meta-data test log</a><br>";
                    messageHandler.addMessage(i, l, s2, s3, s4);
                }
            }
        }

        minimum = flag && minimum;
        return flag;
    }

    private void prepareMDFile(FileWriter filewriter)
    {
        try
        {
            filewriter.write("<head>\n");
            filewriter.write("<meta http-equiv=\"expires\" content=\"Tue, 20 Aug 1999 01:00:00 GMT\">\n");
            filewriter.write("<meta http-equiv=\"Pragma\" content=\"no-cache\">\n");
            filewriter.write("<title>Meta-data Test Log</title>\n");
            filewriter.write("</head>\n");
            filewriter.write("<body>\n");
        }
        catch(Exception exception) { }
    }

    public boolean startValidateTest(String s, String s1, Node node, FileWriter filewriter)
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = false;
        boolean flag4 = true;
        String s3 = "";
        String s10 = messageLocation + "startValidateTest()";
        String s11 = "";
        int i = MessageClassification.METADATA;
        if(DebugIndicator.ON)
            System.out.println("Beginning startValidateTest function");
        if(s1 != "")
        {
            String s2 = makeReadyForPrint(s1);
            int j = MessageType.INFO;
            s3 = "Processing Meta-data: " + s2 + "<br>";
            messageHandler.addMessage(i, j, s3, s10, s11, filewriter);
            if(DebugIndicator.ON)
                System.out.println("Meta-data document being tested: " + s1);
        }
        int k = MessageType.OTHER;
        s3 = "***************************************************";
        messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
        k = MessageType.INFO;
        s3 = "Meta-Data Test Conformance Summary";
        messageHandler.addMessage(i, k, s3, s10, s11);
        k = MessageType.OTHER;
        s3 = "***************************************";
        messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
        k = MessageType.INFO;
        s3 = "Checking Meta-data for Minimum Conformance";
        messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
        k = MessageType.INFO;
        s3 = "Checking Meta-data for well-formedness...";
        messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
        k = MessageType.OTHER;
        s3 = "***************************************";
        messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
        if(DebugIndicator.ON)
            System.out.println("Checking Meta-data for well-formedness...");
        MetaDataTester metadatatester = null;
        if(s1 != "")
            metadatatester = new MetaDataTester(s, s1, tsParent);
        else
        if(node != null)
            metadatatester = new MetaDataTester(s, node, tsParent);
        Message amessage[] = metadatatester.getMessages(false);
        if(amessage.length > 0)
        {
            if(DebugIndicator.ON)
                System.out.println("Meta-data has errors with well-formedness");
            k = MessageType.FAILED;
            s3 = "Meta-data has errors with well-formedness";
            messageHandler.addMessage(i, k, s3, s10, s11);
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            s3 = "The following errors were encountered:";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            reportMessages(amessage, true, filewriter);
        } else
        {
            k = MessageType.PASSED;
            s3 = "Meta-data is well-formed";
            messageHandler.addMessage(i, k, s3, s10, s11);
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            if(DebugIndicator.ON)
                System.out.println("Meta-data is well-formed");
        }
        metadatatester.clearMessages();
        boolean flag5 = false;
        if(DebugIndicator.ON)
            System.out.println("metadataTester.isWellFormed() " + metadatatester.isWellFormed());
        if(metadatatester.isWellFormed())
        {
            k = MessageType.OTHER;
            s3 = "***************************************";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            k = MessageType.INFO;
            s3 = "Validating Meta-data against Schema...";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            k = MessageType.OTHER;
            s3 = "***************************************";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            if(DebugIndicator.ON)
                System.out.println("Validating Meta-data against the Schema");
            if(node == null)
                flag5 = metadatatester.validate();
            else
                flag5 = true;
        }
        if(flag5)
        {
            k = MessageType.PASSED;
            s3 = "Meta-data is valid against the Schema";
            messageHandler.addMessage(i, k, s3, s10, s11);
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            metadatatester.clearMessages();
            k = MessageType.OTHER;
            s3 = "***************************************";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            k = MessageType.INFO;
            s3 = "Checking Meta-data for Mandatory Elements...";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            k = MessageType.OTHER;
            s3 = "***************************************";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            if(DebugIndicator.ON)
                System.out.println("Checking the Meta-data for mandatory elements");
            flag = metadatatester.verifyMandatory();
            if(flag)
            {
                Message amessage1[] = metadatatester.getMessages(false);
                reportMessages(amessage1, true, filewriter);
                k = MessageType.PASSED;
                s3 = "Meta-data contains all mandatory elements";
                messageHandler.addMessage(i, k, s3, s10, s11);
                messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            } else
            {
                flag = false;
                Message amessage2[] = metadatatester.getMessages(false);
                reportMessages(amessage2, true, filewriter);
                k = MessageType.FAILED;
                s3 = "Meta-data contains errors with mandatory elements";
                messageHandler.addMessage(i, k, s3, s10, s11);
                messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            }
            metadatatester.clearMessages();
            k = MessageType.OTHER;
            s3 = "***************************************";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            k = MessageType.INFO;
            s3 = "Checking Meta-data for Optional Elements...";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            k = MessageType.OTHER;
            s3 = "***************************************";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            if(DebugIndicator.ON)
                System.out.println("Checking the Meta-data for optional elements");
            flag1 = metadatatester.verifyOptional();
            flag2 = metadatatester.isOptionalNotUsed();
            if(flag2)
            {
                k = MessageType.PASSED;
                s3 = "Meta-data did not use optional elements";
                messageHandler.addMessage(i, k, s3, s10, s11);
                messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            } else
            if(flag1)
            {
                Message amessage3[] = metadatatester.getMessages(false);
                reportMessages(amessage3, true, filewriter);
                k = MessageType.PASSED;
                s3 = "Meta-data used optional elements correctly";
                messageHandler.addMessage(i, k, s3, s10, s11);
                messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            } else
            {
                flag1 = false;
                Message amessage4[] = metadatatester.getMessages(false);
                reportMessages(amessage4, true, filewriter);
                k = MessageType.FAILED;
                s3 = "Meta-data has errors with optional elements";
                messageHandler.addMessage(i, k, s3, s10, s11);
                messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            }
            metadatatester.clearMessages();
            k = MessageType.OTHER;
            s3 = "***************************************";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            k = MessageType.INFO;
            s3 = "Checking Meta-data for Extension Elements...";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            k = MessageType.OTHER;
            s3 = "***************************************";
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
            flag3 = metadatatester.isExtensionsUsed();
            if(DebugIndicator.ON)
                System.out.println("extensionsUsed = " + flag3);
            if(!flag3)
            {
                k = MessageType.PASSED;
                s3 = "Extension element(s) have not been used";
                messageHandler.addMessage(i, k, s3, s10, s11);
                messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
                if(DebugIndicator.ON)
                    System.out.println("Extension element(s) have not been used");
            } else
            {
                k = MessageType.PASSED;
                s3 = "Extension element(s) have been used";
                messageHandler.addMessage(i, k, s3, s10, s11);
                messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
                if(DebugIndicator.ON)
                    System.out.println("Extension element(s) have been used");
            }
        } else
        {
            flag = false;
            flag1 = false;
            Message amessage5[] = metadatatester.getMessages(false);
            reportMessages(amessage5, true, filewriter);
            k = MessageType.FAILED;
            s3 = "Meta-data is not valid against the Schema";
            messageHandler.addMessage(i, k, s3, s10, s11);
            messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
        }
        k = MessageType.OTHER;
        s3 = "*******************************************";
        messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
        messageHandler.addMessage(i, k, s3, s10, s11, filewriter);
        if(DebugIndicator.ON)
        {
            System.out.println("Mandatory = " + flag);
            System.out.println("Optional = " + flag1);
            System.out.println("Extension = " + flag3);
        }
        if(!flag2 && !flag1)
            flag = false;
        if(flag2)
            flag1 = false;
        if(flag && flag1 && flag3)
        {
            int l = MessageType.CONFORMANT;
            String s4 = "Meta-data is SCORM Version 1.2 MD-XML1 + Optional + Extensions Conformant<br>";
            messageHandler.addMessage(i, l, s4, s10, s11);
            messageHandler.addMessage(i, l, s4, s10, s11, filewriter);
            if(DebugIndicator.ON)
                System.out.println("Meta-data is SCORM Version 1.2 MD-XML1 + Optional Extensions Conformant");
            flag4 = true;
        } else
        if(flag && flag1)
        {
            int i1 = MessageType.CONFORMANT;
            String s5 = "Meta-data is SCORM Version 1.2 MD-XML1 + Optional Conformant<br>";
            messageHandler.addMessage(i, i1, s5, s10, s11);
            messageHandler.addMessage(i, i1, s5, s10, s11, filewriter);
            if(DebugIndicator.ON)
                System.out.println("The Meta-data is SCORM Version 1.2 MD-XML1 + Optional Conformant");
            flag4 = true;
        } else
        if(flag && flag3)
        {
            int j1 = MessageType.CONFORMANT;
            String s6 = "Meta-data is SCORM Version 1.2 MD-XML1 + Extensions Conformant<br>";
            messageHandler.addMessage(i, j1, s6, s10, s11);
            messageHandler.addMessage(i, j1, s6, s10, s11, filewriter);
            if(DebugIndicator.ON)
                System.out.println("The Meta-data is SCORM Version 1.2 MD-XML1 + Extensions Conformant");
            flag4 = true;
        } else
        if(flag)
        {
            int k1 = MessageType.CONFORMANT;
            String s7 = "Meta-data is SCORM Version 1.2 MD-XML1 Conformant<br>";
            messageHandler.addMessage(i, k1, s7, s10, s11);
            messageHandler.addMessage(i, k1, s7, s10, s11, filewriter);
            if(DebugIndicator.ON)
                System.out.println("The Meta-data is SCORM Version 1.2 MD-XML1 Conformant");
            flag4 = true;
        } else
        {
            int l1 = MessageType.TERMINATE;
            String s8 = "The Meta-data is Non-Conformant<br>";
            messageHandler.addMessage(i, l1, s8, s10, s11);
            messageHandler.addMessage(i, l1, s8, s10, s11, filewriter);
            if(DebugIndicator.ON)
                System.out.println("The Meta-data is Non-Conformant");
            flag4 = false;
        }
        if(AuditorIndicator.OFF)
        {
            int i2 = MessageType.OTHER;
            String s9 = "";
            messageHandler.addMessage(i, i2, s9, s10, s11, filewriter);
            i2 = MessageType.INFO;
            s9 = "Successful outcome of this test does not constitute ADL Certification unless the test was conducted by an ADL Accredited Auditor.";
            messageHandler.addMessage(i, i2, s9, s10, s11, filewriter);
        }
        return flag4;
    }

    private void reportMessages(Message amessage[], boolean flag, FileWriter filewriter)
    {
        String s = "";
        String s6 = messageLocation + "startValidateTest()";
        String s7 = "";
        int i = MessageClassification.METADATA;
        for(int k1 = 0; k1 < amessage.length; k1++)
        {
            int l1 = amessage[k1].getMessageType();
            String s8 = amessage[k1].getMessageText();
            s8 = makeReadyForPrint(s8);
            messageHandler.logMessage(l1, s8, filewriter);
            if(l1 == MessageType.FAILED && !flag)
            {
                int j = MessageType.FAILED;
                String s1 = s8;
                messageHandler.addMessage(i, j, s1, s6, s7);
            } else
            if(l1 == MessageType.INFO && !flag)
            {
                int k = MessageType.INFO;
                String s2 = s8;
                messageHandler.addMessage(i, k, s2, s6, s7);
            } else
            if(l1 == MessageType.WARNING && !flag)
            {
                int l = MessageType.WARNING;
                String s3 = s8;
                messageHandler.addMessage(i, l, s3, s6, s7);
            } else
            if(l1 == MessageType.PASSED && !flag)
            {
                int i1 = MessageType.PASSED;
                String s4 = s8;
                messageHandler.addMessage(i, i1, s4, s6, s7);
            } else
            if(!flag)
            {
                int j1 = MessageType.OTHER;
                String s5 = s8;
                messageHandler.addMessage(i, j1, s5, s6, s7);
            }
        }

    }

    private String makeReadyForPrint(String s)
    {
        String s1 = replace(s, "&", "&amp;");
        s1 = replace(s1, "\"", "&quot;");
        s1 = replace(s1, "<", "&lt;");
        s1 = replace(s1, ">", "&gt;");
        s1 = replace(s1, "[", "&#91;");
        s1 = replace(s1, "]", "&#93;");
        s1 = replace(s1, "'", "&#39;");
        s1 = replace(s1, "\\", "\\\\");
        return s1;
    }

    private String replace(String s, String s1, String s2)
    {
        int i = 0;
        int j = s.length();
        for(int k = -1; (k = s.indexOf(s1, i)) != -1;)
        {
            String s3 = s.substring(0, k);
            s3 = s3 + s2;
            s3 = s3 + s.substring(k + s1.length());
            s = s3;
            i = k + s2.length();
        }

        return s;
    }

    public boolean checkSCO()
    {
        boolean flag = false;
        String s = "";
        String s1 = messageLocation + "checkSCO()";
        String s2 = "";
        int i = MessageClassification.MINIMUM;
        int j = MessageType.INFO;
        s = "*****************************************";
        messageHandler.addMessage(i, j, s, s1, s2);
        s = "Testing the manifest for SCO RTE conformance";
        messageHandler.addMessage(i, j, s, s1, s2);
        s = "*****************************************";
        messageHandler.addMessage(i, j, s, s1, s2);
        j = MessageType.FAILED;
        s = "SCO RTE conformance test is not yet implemented!!!";
        messageHandler.addMessage(i, j, s, s1, s2);
        minimum = flag && minimum;
        return flag;
    }

    public boolean isMinimum()
    {
        return minimum;
    }

    public boolean isExtention()
    {
        if(DebugIndicator.ON)
            System.out.println("extensionsNotUsed: " + extensionsNotUsed);
        return !extensionsNotUsed;
    }

    public Vector getItemList()
    {
        String s = messageLocation + "getItemList()";
        if(DebugIndicator.ON)
        {
            System.out.println("*******    " + s + "  **************");
            System.out.println("*******    returning...         **************");
        }
        return manifest.getItemList();
    }

    public boolean fill()
    {
        String s = messageLocation + "fill()";
        if(DebugIndicator.ON)
            System.out.println("******  " + s + "  *********");
        boolean flag = false;
        flag = manifest.fillManifest(rootNode);
        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting " + s + " ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }

    public Vector getLaunchLines()
    {
        URLDecoder urldecoder = new URLDecoder();
        HashSet hashset = manifest.getLaunchLines();
        Vector vector = new Vector();
        Iterator iterator = hashset.iterator();
        String s = new String("");
        String s1;
        for(; iterator.hasNext(); vector.add(s1))
        {
            s1 = (String)iterator.next();
            String s2 = replace(s1, "\\", "/");
            s1 = s2;
        }

        return vector;
    }
}
