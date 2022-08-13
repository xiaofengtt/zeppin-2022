// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLItem.java

package org.adl.parsers.dom;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.Vector;
import org.adl.parsers.util.MessageClassification;
import org.adl.parsers.util.MessageHandler;
import org.adl.parsers.util.adlrules.manifest.*;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package org.adl.parsers.dom:
//            ADLElement, ADLMetadata, ADLSequence

public class ADLItem extends ADLElement
    implements Serializable
{

    private ADLMetadata adlMetadata;
    private ADLSequence adlSequence;
    private String identifier;
    private String identifierref;
    private String title;
    private String prerequisites;
    private String scormType;
    private String type;
    private String launchLocation;
    private String maxTimeAllowed;
    private String timeLimitAction;
    private String parameterString;
    private String dataFromLMS;
    private String masteryScore;
    private String isVisible;
    private int level;
    private Vector itemList;
    private boolean idExists;
    private int messageClass;
    private String messageLocation;

    public ADLItem()
    {
        super("item");
        adlMetadata = null;
        adlSequence = null;
        identifier = new String();
        title = new String();
        prerequisites = new String();
        type = new String();
        maxTimeAllowed = new String();
        timeLimitAction = new String();
        launchLocation = new String();
        dataFromLMS = new String();
        masteryScore = new String();
        scormType = new String();
        isVisible = new String();
        itemList = new Vector();
        idExists = false;
        level = 0;
        messageClass = MessageClassification.MINIMUM;
        messageLocation = "ADLItem::";
    }

    public boolean fillItem(Node node, Vector vector)
    {
        boolean flag = true;
        level++;
        if(DebugIndicator.ON)
            System.out.println("******  ADLItem:fillItem  *********");
        int i = vector.size();
        for(int j = 0; j < i; j++)
        {
            if(!identifierref.equalsIgnoreCase((String)vector.elementAt(j)))
                continue;
            idExists = true;
            break;
        }

        NodeList nodelist = node.getChildNodes();
        if(DebugIndicator.ON)
        {
            System.out.println("*** NODE: " + node.getNodeName());
            System.out.println("*** Children - " + nodelist.getLength() + " ***");
        }
        for(int k = 0; k < nodelist.getLength(); k++)
        {
            Node node1 = nodelist.item(k);
            if(node1.getNodeType() != 1)
                continue;
            if(node1.getLocalName().equalsIgnoreCase("item"))
            {
                ADLItem adlitem = new ADLItem();
                String s = getAttribute(node1, "identifier");
                String s1 = getAttribute(node1, "identifierref");
                String s2 = getAttribute(node1, "isvisible");
                String s3 = getAttribute(node1, "parameters");
                String s4 = getSubElement(node1, "title");
                String s5 = getSubElement(node1, "prerequisites");
                String s6 = getSubElement(node1, "timelimitaction");
                String s7 = getSubElement(node1, "maxtimeallowed");
                String s8 = getSubElement(node1, "datafromlms");
                String s9 = getSubElement(node1, "masteryscore");
                int l = level + 1;
                String s10 = getTypeAttribute(node1, "prerequisite");
                adlitem.setIdentifier(s);
                if(!s1.equals(""))
                    adlitem.setIdentifierref(s1);
                else
                    adlitem.setIdentifierref("");
                adlitem.setIsVisible(s2);
                if(!s3.equals(""))
                    adlitem.setParameterString(s3);
                else
                    adlitem.setParameterString("");
                adlitem.setTitle(s4);
                adlitem.setPrerequisites(s5);
                adlitem.setTimeLimitAction(s6);
                adlitem.setMaxTimeAllowed(s7);
                adlitem.setDataFromLMS(s8);
                adlitem.setMasteryScore(s9);
                adlitem.setType(s10);
                adlitem.setLevel(level);
                flag = adlitem.fillItem(node1, vector);
                itemList.addElement(adlitem);
                if(!flag)
                    break;
            } else
            if(node1.getLocalName().equalsIgnoreCase("metadata"))
            {
                adlMetadata = new ADLMetadata();
                flag = adlMetadata.fillMetadata(node1) && flag;
            } else
            if(node1.getLocalName().equalsIgnoreCase("sequencing"))
            {
                adlSequence = new ADLSequence();
                flag = adlSequence.fillSequence(node1) && flag;
            }
        }

        if(DebugIndicator.ON)
        {
            System.out.println("*** Exiting Item::fillItem() ***");
            System.out.println("*** Returning: " + flag);
        }
        return flag;
    }

    public Vector getItemList()
    {
        if(DebugIndicator.ON)
            System.out.println("*******    Item::getItemList()  **************");
        Vector vector = new Vector();
        for(int i = 0; i < itemList.size(); i++)
        {
            ADLItem adlitem = (ADLItem)itemList.elementAt(i);
            vector.addElement(adlitem);
            Vector vector1 = adlitem.getItemList();
            vector.addAll(vector1);
        }

        if(DebugIndicator.ON)
        {
            System.out.println("*******  EXITING  ADLItem::getItemList()  **************");
            System.out.println("*******  Vector size is: " + vector.size() + "  **************");
        }
        return vector;
    }

    public void printItem()
    {
        System.out.println("identifier:      " + identifier);
        System.out.println("title:           " + title);
        System.out.println("prerequisites:   " + prerequisites);
        System.out.println("type:            " + type);
        System.out.println("maxTimeAllowed:  " + maxTimeAllowed);
        System.out.println("timeLimitAction: " + timeLimitAction);
        System.out.println("launchLocation:  " + launchLocation);
        System.out.println("parameterString: " + parameterString);
        System.out.println("dataFromLMS:     " + dataFromLMS);
        System.out.println("masteryScore:    " + masteryScore);
        System.out.println("level:           " + level);
    }

    public void setIdentifier(String s)
    {
        identifier = s;
    }

    public void setIdentifierref(String s)
    {
        identifierref = s;
    }

    public void setIsVisible(String s)
    {
        isVisible = s;
    }

    public void setTitle(String s)
    {
        title = s;
    }

    public void setPrerequisites(String s)
    {
        prerequisites = s;
    }

    public void setType(String s)
    {
        type = s;
    }

    public void setScormType(String s)
    {
        scormType = s;
    }

    public void setMaxTimeAllowed(String s)
    {
        maxTimeAllowed = s;
    }

    public void setTimeLimitAction(String s)
    {
        timeLimitAction = s;
    }

    public void setLaunchLocation(String s)
    {
        launchLocation = s;
    }

    public void setParameterString(String s)
    {
        parameterString = s;
    }

    public void setDataFromLMS(String s)
    {
        dataFromLMS = s;
    }

    public void setMasteryScore(String s)
    {
        masteryScore = s;
    }

    public void setLevel(int i)
    {
        level = i;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public String getIdentifierref()
    {
        return identifierref;
    }

    public String getIsVisible()
    {
        return isVisible;
    }

    public String getTitle()
    {
        return title;
    }

    public String getPrerequisites()
    {
        return prerequisites;
    }

    public String getType()
    {
        return type;
    }

    public String getScormType()
    {
        return scormType;
    }

    public String getMaxTimeAllowed()
    {
        return maxTimeAllowed;
    }

    public String getTimeLimitAction()
    {
        return timeLimitAction;
    }

    public String getLaunchLocation()
    {
        return launchLocation;
    }

    public String getParameterString()
    {
        return parameterString;
    }

    public String getDataFromLMS()
    {
        return dataFromLMS;
    }

    public String getMasteryScore()
    {
        return masteryScore;
    }

    public int getLevel()
    {
        return level;
    }

    public boolean checkConformance(String s, String s1)
    {
        boolean flag = true;
        if(s.equalsIgnoreCase("aggregation"))
        {
            min = ItemRules.AMIN;
            max = ItemRules.AMAX;
        } else
        {
            min = ItemRules.RMIN;
            max = ItemRules.RMAX;
        }
        spm = ItemRules.SPM;
        int j = messageClass;
        int k = MessageType.INFO;
        String s2 = "";
        String s3 = messageLocation + "checkConformance()";
        String s4 = "";
        s2 = "Testing element <" + elemName + "> for minimum conformance";
        messageHandler.addMessage(j, k, s2, s3, s4);
        flag = checkMultiplicity(j, s3);
        flag = checkIdentifier() && flag;
        flag = checkIdentifierref(s) && flag;
        flag = checkIsVisible(s) && flag;
        flag = checkParameters(s) && flag;
        flag = checkAdlcpprerequisites(s) && flag;
        flag = checkAdlcpmaxtimeallowed(s) && flag;
        flag = checkAdlcptimelimitaction(s) && flag;
        flag = checkAdlcpdatafromlms(s) && flag;
        flag = checkAdlcpmasteryscore(s) && flag;
        flag = checkTitle(s) && flag;
        int i = itemList.size();
        for(int l = 0; l < i; l++)
        {
            flag = ((ADLItem)itemList.elementAt(l)).checkConformance(s, s1) && flag;
            messageHandler.appendMessage(j, ((ADLItem)itemList.elementAt(l)).getMessage(j));
        }

        if(adlMetadata != null)
        {
            flag = adlMetadata.checkConformance(s1) && flag;
            messageHandler.appendMessage(j, adlMetadata.getMessage(j));
        }
        return flag;
    }

    public boolean checkIdentifier()
    {
        boolean flag = true;
        String s = new String("identifier");
        int i = messageClass;
        int j = MessageType.INFO;
        String s1 = "";
        String s4 = messageLocation + "checkIdentifier()";
        String s5 = "";
        int i1 = IdentifierRules.MIN;
        int j1 = IdentifierRules.MAX;
        int k1 = IdentifierRules.VALUESPM;
        s1 = "Testing attribute \"" + s + "\" for minimum " + "comformance";
        messageHandler.addMessage(i, j, s1, s4, s5);
        flag = checkMultiplicity(i, s4, s, i1, j1, identifier, true) && flag;
        int l1 = identifier.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s2 = "The smallest permitted maximum for the value of attribute \"" + s + "\" is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s2, s4, s5);
        } else
        {
            int l = MessageType.PASSED;
            String s3 = "The value, \"" + identifier + "\", of attribute \"" + s + "\" passed the " + "smallest permitted maximum test";
            messageHandler.addMessage(i, l, s3, s4, s5);
        }
        return flag;
    }

    public boolean checkIdentifierref(String s)
    {
        boolean flag = true;
        String s1 = new String("identitierref");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s8 = messageLocation + "checkIdentitierref()";
        String s9 = "";
        int l1 = -1;
        int i2 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            l1 = IdentifierrefRules.AMIN;
            i2 = IdentifierrefRules.AMAX;
        } else
        {
            l1 = IdentifierrefRules.RMIN;
            i2 = IdentifierrefRules.RMAX;
        }
        int j2 = IdentifierrefRules.VALUESPM;
        s2 = "Testing attribute \"" + s1 + "\" for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s8, s9);
        flag = checkMultiplicity(i, s8, s1, l1, i2, identifierref, true) && flag;
        int k2 = identifierref.length();
        if(k2 > j2)
        {
            int k = MessageType.WARNING;
            String s3 = "The smallest permitted maximum for the value of attribute \"" + s1 + "\" is " + j2 + " and a length of " + k2 + " was found.";
            messageHandler.addMessage(i, k, s3, s8, s9);
        } else
        {
            int l = MessageType.PASSED;
            String s4 = "The value, \"" + identifierref + "\", of attribute \"" + s1 + "\" passed the " + "smallest permitted maximum test";
            messageHandler.addMessage(i, l, s4, s8, s9);
        }
        if(identifierref.equalsIgnoreCase("") && s.equalsIgnoreCase("aggregation"))
        {
            int i1 = MessageType.PASSED;
            String s5 = "Attribute \"" + s1 + "\" was not found or was " + "left blank.  It is assumed that there is no content " + "associated with this entry in the organization";
            messageHandler.addMessage(i, i1, s5, s8, s9);
        } else
        if(idExists)
        {
            int j1 = MessageType.PASSED;
            String s6 = "Resource identifier \"" + identifierref + "\" has " + "been found";
            messageHandler.addMessage(i, j1, s6, s8, s9);
        } else
        {
            int k1 = MessageType.FAILED;
            String s7 = "Resource identifier \"" + identifierref + "\" could " + "not be found and failed the referenced identifier test";
            messageHandler.addMessage(i, k1, s7, s8, s9);
            flag = false;
        }
        return flag;
    }

    public boolean checkIsVisible(String s)
    {
        boolean flag = true;
        String s1 = new String("isvisible");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s7 = messageLocation + "checkStructure()";
        String s8 = "";
        int j1 = -1;
        int k1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            j1 = IsvisibleRules.AMIN;
            k1 = IsvisibleRules.AMAX;
        } else
        {
            j1 = IsvisibleRules.RMIN;
            k1 = IsvisibleRules.RMAX;
        }
        int l1 = IsvisibleRules.VOCABSIZE;
        Vector vector = new Vector(l1);
        for(int i2 = 0; i2 < l1; i2++)
            vector.add(IsvisibleRules.VOCAB[i2]);

        s2 = "Testing attribute \"" + s1 + "\" for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s7, s8);
        flag = checkMultiplicity(i, s7, s1, j1, k1, isVisible, true) && flag;
        if(isVisible.equalsIgnoreCase(""))
        {
            int k = MessageType.PASSED;
            String s3 = "Attribute \"" + s1 + "\" was not found or was " + "left blank.  It is assumed that the default value of " + "\"true\" will be used.";
            messageHandler.addMessage(i, k, s3, s7, s8);
        } else
        {
            boolean flag1 = false;
            int j2 = vector.size();
            for(int k2 = 0; k2 < j2 && !flag1; k2++)
                if(isVisible.equalsIgnoreCase((String)vector.elementAt(k2)))
                {
                    int l = MessageType.PASSED;
                    String s4 = "Attribute \"" + s1 + "\" passed the " + "vocabulary test";
                    messageHandler.addMessage(i, l, s4, s7, s8);
                    flag1 = true;
                }

            if(!flag1)
            {
                int i1 = MessageType.FAILED;
                String s5 = "Attribute \"" + s1 + "\" did not adhere to the " + "restricted vocabulary and failed the vocabulary test";
                messageHandler.addMessage(i, i1, s5, s7, s8);
                s5 = "Vocabulary list for attribute \"" + s1 + "\" is as follows:";
                messageHandler.addMessage(i, i1, s5, s7, s8);
                for(int l2 = 0; l2 < j2; l2++)
                {
                    String s6 = (String)vector.elementAt(l2);
                    messageHandler.addMessage(i, i1, s6, s7, s8);
                }

                flag = false;
            }
        }
        return flag;
    }

    public boolean checkParameters(String s)
    {
        boolean flag = true;
        String s1 = new String("parameters");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s5 = messageLocation + "checkParameters()";
        String s6 = "";
        int i1 = -1;
        int j1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            i1 = ParametersRules.AMIN;
            j1 = ParametersRules.AMAX;
        } else
        {
            i1 = ParametersRules.RMIN;
            j1 = ParametersRules.RMAX;
        }
        int k1 = ParametersRules.VALUESPM;
        s2 = "Testing attribute \"" + s1 + "\" for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s5, s6);
        flag = checkMultiplicity(i, s5, s1, i1, j1, parameterString, true) && flag;
        int l1 = parameterString.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s3 = "The smallest permitted maximum for the value of attribute \"" + s1 + "\" is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s3, s5, s6);
        } else
        {
            int l = MessageType.PASSED;
            String s4 = "The value, \"" + parameterString + "\", of attribute \"" + s1 + "\" passed the " + "smallest permitted maximum test";
            messageHandler.addMessage(i, l, s4, s5, s6);
        }
        return flag;
    }

    public boolean checkTitle(String s)
    {
        boolean flag = true;
        String s1 = new String("title");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s5 = messageLocation + "checkTitle()";
        String s6 = "";
        int i1 = -1;
        int j1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            i1 = TitleRules.AMIN;
            j1 = TitleRules.AMAX;
        } else
        {
            i1 = TitleRules.RMIN;
            j1 = TitleRules.RMAX;
        }
        int k1 = TitleRules.VALUESPM;
        s2 = "Testing element <" + s1 + "> for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s5, s6);
        flag = checkMultiplicity(i, s5, s1, i1, j1, title, false) && flag;
        int l1 = title.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s3 = "The smallest permitted maximum for the value of element <" + s1 + "> is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s3, s5, s6);
        } else
        {
            int l = MessageType.PASSED;
            String s4 = "The value, \"" + title + "\", of element <" + s1 + "> passed the smallest permitted maximum test";
            messageHandler.addMessage(i, l, s4, s5, s6);
        }
        return flag;
    }

    public boolean checkAdlcpprerequisites(String s)
    {
        boolean flag = true;
        String s1 = new String("prerequisites");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s5 = messageLocation + "checkAdlcpprerequisites()";
        String s6 = "";
        int i1 = -1;
        int j1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            i1 = ADLCPPrerequisitesRules.AMIN;
            j1 = ADLCPPrerequisitesRules.AMAX;
        } else
        {
            i1 = ADLCPPrerequisitesRules.RMIN;
            j1 = ADLCPPrerequisitesRules.RMAX;
        }
        int k1 = ADLCPPrerequisitesRules.VALUESPM;
        s2 = "Testing element <" + s1 + "> for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s5, s6);
        flag = checkMultiplicity(i, s5, s1, i1, j1, prerequisites, false) && flag;
        int l1 = prerequisites.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s3 = "The smallest permitted maximum for the value of element <" + s1 + "> is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s3, s5, s6);
        } else
        {
            int l = MessageType.PASSED;
            String s4 = "The value, \"" + title + "\", of element <" + s1 + "> passed the smallest permitted maximum test";
            messageHandler.addMessage(i, l, s4, s5, s6);
        }
        flag = checkType(s) && flag;
        return flag;
    }

    public boolean checkType(String s)
    {
        boolean flag = true;
        String s1 = new String("type");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s7 = messageLocation + "checkType()";
        String s8 = "";
        int j1 = -1;
        int k1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            j1 = ADLCPTypeRules.AMIN;
            k1 = ADLCPTypeRules.AMAX;
        } else
        {
            j1 = ADLCPTypeRules.RMIN;
            k1 = ADLCPTypeRules.RMAX;
        }
        int l1 = ADLCPTypeRules.VOCABSIZE;
        Vector vector = new Vector(l1);
        for(int i2 = 0; i2 < l1; i2++)
            vector.add(ADLCPTypeRules.VOCAB[i2]);

        s2 = "Testing attribute \"" + s1 + "\" for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s7, s8);
        flag = checkMultiplicity(i, s7, s1, j1, k1, type, true) && flag;
        boolean flag1 = false;
        int j2 = vector.size();
        if(type.equalsIgnoreCase(""))
        {
            int k = MessageType.PASSED;
            String s3 = "Element <" + elemName + "> passed the vocabulary test";
            messageHandler.addMessage(i, k, s3, s7, s8);
            flag1 = true;
        } else
        {
            for(int k2 = 0; k2 < j2 && !flag1; k2++)
                if(type.equalsIgnoreCase((String)vector.elementAt(k2)))
                {
                    int l = MessageType.PASSED;
                    String s4 = "Attribute \"" + s1 + "\" passed the " + "vocabulary test";
                    messageHandler.addMessage(i, l, s4, s7, s8);
                    flag1 = true;
                }

        }
        if(!flag1)
        {
            int i1 = MessageType.FAILED;
            String s5 = "Attribute \"" + s1 + "\" did not adhere to the " + "restricted vocabulary and failed the vocabulary test";
            messageHandler.addMessage(i, i1, s5, s7, s8);
            s5 = "Vocabulary list for attribute \"" + s1 + "\" is as follows:";
            messageHandler.addMessage(i, i1, s5, s7, s8);
            for(int l2 = 0; l2 < j2; l2++)
            {
                String s6 = (String)vector.elementAt(l2);
                messageHandler.addMessage(i, i1, s6, s7, s8);
            }

            flag = false;
        }
        return flag;
    }

    public boolean checkAdlcpmaxtimeallowed(String s)
    {
        boolean flag = true;
        String s1 = new String("maxtimeallowed");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s5 = messageLocation + "checkAdlcpmaxtimeallowed()";
        String s6 = "";
        int i1 = -1;
        int j1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            i1 = ADLCPMaxtimeallowedRules.AMIN;
            j1 = ADLCPMaxtimeallowedRules.AMAX;
        } else
        {
            i1 = ADLCPMaxtimeallowedRules.RMIN;
            j1 = ADLCPMaxtimeallowedRules.RMAX;
        }
        int k1 = ADLCPMaxtimeallowedRules.VALUESPM;
        s2 = "Testing element <" + s1 + "> for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s5, s6);
        flag = checkMultiplicity(i, s5, s1, i1, j1, maxTimeAllowed, false) && flag;
        int l1 = maxTimeAllowed.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s3 = "The smallest permitted maximum for the value of element <" + s1 + "> is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s3, s5, s6);
        } else
        {
            int l = MessageType.PASSED;
            String s4 = "The value, \"" + title + "\", of element <" + s1 + "> passed the smallest permitted maximum test";
            messageHandler.addMessage(i, l, s4, s5, s6);
        }
        if(!maxTimeAllowed.equalsIgnoreCase(""))
            flag = checkTimespan(maxTimeAllowed) && flag;
        return flag;
    }

    public boolean checkAdlcptimelimitaction(String s)
    {
        boolean flag = true;
        String s1 = new String("timelimitaction");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s6 = messageLocation + "checkAdlcptimelimitaction()";
        String s7 = "";
        int i1 = -1;
        int j1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            i1 = ADLCPTimelimitactionRules.AMIN;
            j1 = ADLCPTimelimitactionRules.AMAX;
        } else
        {
            i1 = ADLCPTimelimitactionRules.RMIN;
            j1 = ADLCPTimelimitactionRules.RMAX;
        }
        int k1 = ADLCPTimelimitactionRules.VALUESPM;
        int l1 = ADLCPTimelimitactionRules.VOCABSIZE;
        Vector vector = new Vector(l1);
        for(int i2 = 0; i2 < l1; i2++)
            vector.add(ADLCPTimelimitactionRules.VOCAB[i2]);

        s2 = "Testing element <" + s1 + "> for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s6, s7);
        flag = checkMultiplicity(i, s6, s1, i1, j1, timeLimitAction, false) && flag;
        if(!timeLimitAction.equalsIgnoreCase(""))
        {
            boolean flag1 = false;
            int j2 = vector.size();
            for(int k2 = 0; k2 < j2 && !flag1; k2++)
                if(timeLimitAction.equalsIgnoreCase((String)vector.elementAt(k2)))
                {
                    int k = MessageType.PASSED;
                    String s3 = "Element <" + s1 + "> passed the vocabulary test";
                    messageHandler.addMessage(i, k, s3, s6, s7);
                    flag1 = true;
                }

            if(!flag1)
            {
                int l = MessageType.FAILED;
                String s4 = "Element <" + s1 + "> did not adhere to the " + "restricted vocabulary and failed the vocabulary test";
                messageHandler.addMessage(i, l, s4, s6, s7);
                s4 = "Vocabulary list for element <" + s1 + "> is as follows:";
                messageHandler.addMessage(i, l, s4, s6, s7);
                for(int l2 = 0; l2 < j2; l2++)
                {
                    String s5 = (String)vector.elementAt(l2);
                    messageHandler.addMessage(i, l, s5, s6, s7);
                }

                flag = false;
            }
        }
        return flag;
    }

    public boolean checkAdlcpdatafromlms(String s)
    {
        boolean flag = true;
        String s1 = new String("datafromlms");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s5 = messageLocation + "checkAdlcpdatafromlms()";
        String s6 = "";
        int i1 = -1;
        int j1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            i1 = ADLCPDatafromlmsRules.AMIN;
            j1 = ADLCPDatafromlmsRules.AMAX;
        } else
        {
            i1 = ADLCPDatafromlmsRules.RMIN;
            j1 = ADLCPDatafromlmsRules.RMAX;
        }
        int k1 = ADLCPDatafromlmsRules.VALUESPM;
        s2 = "Testing element <" + s1 + "> for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s5, s6);
        flag = checkMultiplicity(i, s5, s1, i1, j1, dataFromLMS, false) && flag;
        int l1 = dataFromLMS.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s3 = "The smallest permitted maximum for the value of element <" + s1 + "> is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s3, s5, s6);
        } else
        {
            int l = MessageType.PASSED;
            String s4 = "The value, \"" + dataFromLMS + "\", of element <" + s1 + "> passed the smallest permitted maximum test";
            messageHandler.addMessage(i, l, s4, s5, s6);
        }
        return flag;
    }

    public boolean checkAdlcpmasteryscore(String s)
    {
        boolean flag = true;
        String s1 = new String("masteryscore");
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s5 = messageLocation + "checkAdlcpmasteryscore()";
        String s6 = "";
        int i1 = -1;
        int j1 = -1;
        if(s.equalsIgnoreCase("aggregation"))
        {
            i1 = ADLCPMasteryscoreRules.AMIN;
            j1 = ADLCPMasteryscoreRules.AMAX;
        } else
        {
            i1 = ADLCPMasteryscoreRules.RMIN;
            j1 = ADLCPMasteryscoreRules.RMAX;
        }
        int k1 = ADLCPMasteryscoreRules.VALUESPM;
        s2 = "Testing element <" + s1 + "> for minimum " + "comformance";
        messageHandler.addMessage(i, j, s2, s5, s6);
        flag = checkMultiplicity(i, s5, s1, i1, j1, masteryScore, false) && flag;
        int l1 = masteryScore.length();
        if(l1 > k1)
        {
            int k = MessageType.WARNING;
            String s3 = "The smallest permitted maximum for the value of element <" + s1 + "> is " + k1 + " and a length of " + l1 + " was found.";
            messageHandler.addMessage(i, k, s3, s5, s6);
        } else
        {
            int l = MessageType.PASSED;
            String s4 = "The value, \"" + title + "\", of element <" + s1 + "> passed the smallest permitted maximum test";
            messageHandler.addMessage(i, l, s4, s5, s6);
        }
        return flag;
    }

    public boolean checkTimespan(String s)
    {
        boolean flag = false;
        StringTokenizer stringtokenizer = new StringTokenizer(s, ":", false);
        String s1 = "maxtimeallowed";
        int i = messageClass;
        int j = MessageType.INFO;
        String s2 = "";
        String s5 = messageLocation + "checkTimespan()";
        String s6 = "";
        if(stringtokenizer.countTokens() == 3)
        {
            String s7 = stringtokenizer.nextToken();
            String s8 = stringtokenizer.nextToken();
            String s9 = stringtokenizer.nextToken();
            if(s7.length() >= 2 && s7.length() <= 4 && s8.length() == 2 && s9.length() <= 5)
                try
                {
                    Integer integer = new Integer(s7);
                    Integer integer1 = new Integer(s8);
                    if(integer.intValue() >= 0 && integer.intValue() <= 9999 && integer1.intValue() >= 0 && integer1.intValue() <= 99)
                        flag = true;
                    StringTokenizer stringtokenizer1 = new StringTokenizer(s9, ".", false);
                    int i1 = stringtokenizer1.countTokens();
                    String s10 = stringtokenizer1.nextToken();
                    if(DebugIndicator.ON)
                        System.out.println("secondsPart: " + s10);
                    if(s10.length() == 2)
                    {
                        Double double1 = new Double(s10);
                        flag = true;
                        if(DebugIndicator.ON)
                            System.out.println("seconds part had 2 chars and a valid decimal");
                    } else
                    {
                        flag = false;
                        if(DebugIndicator.ON)
                            System.out.println("Seconds Part did not have 2 chars");
                    }
                    if(i1 == 2 && flag)
                    {
                        String s11 = stringtokenizer1.nextToken();
                        if(s11.length() <= 2)
                        {
                            Double double2 = new Double(s11);
                            flag = true;
                        } else
                        {
                            flag = true;
                        }
                    }
                }
                catch(NumberFormatException numberformatexception)
                {
                    if(DebugIndicator.ON)
                        System.out.println("Value could not be converted to a time");
                    flag = false;
                }
        }
        if(!flag)
        {
            int k = MessageType.FAILED;
            String s3 = "The value, \"" + s + "\", of element <" + s1 + ">  did not adhere to the timespan data type format of " + "hhhh:mm:ss.ss and fails the data type format test";
            messageHandler.addMessage(i, k, s3, s5, s6);
        } else
        {
            int l = MessageType.PASSED;
            String s4 = "The value, \"" + s + "\", of element <" + s1 + "> passed the data type format test";
            messageHandler.addMessage(i, l, s4, s5, s6);
        }
        return flag;
    }

    public Vector getMetadata()
    {
        Vector vector = new Vector();
        vector.add(adlMetadata);
        int i = itemList.size();
        for(int j = 0; j < i; j++)
            vector.addAll(((ADLItem)itemList.elementAt(j)).getMetadata());

        return vector;
    }
}
