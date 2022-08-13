// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ADLElement.java

package org.adl.parsers.dom;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import org.adl.parsers.util.MessageHandler;
import org.adl.util.MessageType;
import org.adl.util.debug.DebugIndicator;
import org.w3c.dom.*;

public class ADLElement
{

    protected MessageHandler messageHandler;
    protected String elemName;
    protected int multiplicity;
    protected int min;
    protected int max;
    protected int spm;

    public ADLElement()
    {
    }

    public ADLElement(String s)
    {
        messageHandler = new MessageHandler();
        elemName = s;
        multiplicity = -1;
        min = -1;
        max = -1;
        spm = -1;
    }

    protected String getAttribute(Node node, String s)
    {
        String s1 = new String();
        Attr aattr[] = sortAttributes(node.getAttributes());
        for(int i = 0; i < aattr.length; i++)
        {
            Attr attr = aattr[i];
            if(!attr.getLocalName().equals(s))
                continue;
            s1 = attr.getValue();
            break;
        }

        return s1;
    }

    protected Attr[] sortAttributes(NamedNodeMap namednodemap)
    {
        int i = namednodemap == null ? 0 : namednodemap.getLength();
        Attr aattr[] = new Attr[i];
        for(int j = 0; j < i; j++)
            aattr[j] = (Attr)namednodemap.item(j);

        for(int k = 0; k < i - 1; k++)
        {
            String s = aattr[k].getLocalName();
            int l = k;
            for(int i1 = k + 1; i1 < i; i1++)
            {
                String s1 = aattr[i1].getLocalName();
                if(s1.compareTo(s) < 0)
                {
                    s = s1;
                    l = i1;
                }
            }

            if(l != k)
            {
                Attr attr = aattr[k];
                aattr[k] = aattr[l];
                aattr[l] = attr;
            }
        }

        return aattr;
    }

    public String getSubElement(Node node, String s)
    {
        String s1 = new String();
        NodeList nodelist = node.getChildNodes();
        if(nodelist != null)
        {
            for(int i = 0; i < nodelist.getLength(); i++)
                if(nodelist.item(i).getNodeType() == 1 && nodelist.item(i).getLocalName().equalsIgnoreCase(s))
                {
                    s1 = getText(nodelist.item(i));
                    return s1;
                }

        } else
        if(DebugIndicator.ON)
            System.out.println("node has no kids");
        return s1;
    }

    public String getTypeAttribute(Node node, String s)
    {
        String s1 = new String();
        NodeList nodelist = node.getChildNodes();
        if(nodelist != null)
        {
            for(int i = 0; i < nodelist.getLength(); i++)
                if(nodelist.item(i).getNodeType() == 1 && nodelist.item(i).getLocalName().equalsIgnoreCase(s))
                {
                    s1 = getAttribute(nodelist.item(i), "type");
                    return s1;
                }

        } else
        if(DebugIndicator.ON)
            System.out.println("node has no kids");
        return s1;
    }

    public String getIdentifierrefAttribute(Node node, String s)
    {
        String s1 = new String();
        NodeList nodelist = node.getChildNodes();
        if(nodelist != null)
        {
            for(int i = 0; i < nodelist.getLength(); i++)
                if(nodelist.item(i).getNodeType() == 1 && nodelist.item(i).getLocalName().equalsIgnoreCase(s))
                {
                    s1 = getAttribute(nodelist.item(i), "identifierref");
                    return s1;
                }

        } else
        if(DebugIndicator.ON)
            System.out.println("node has no kids");
        return s1;
    }

    public String getText(Node node)
    {
        String s = new String();
        NodeList nodelist = node.getChildNodes();
//        try{
        if(nodelist != null)
        {
            for(int i = 0; i < nodelist.getLength(); i++)
                if(nodelist.item(i).getNodeType() == 3 || nodelist.item(i).getNodeType() == 4)
//                    s = s + new String(nodelist.item(i).getNodeValue().trim().getBytes(),"utf-8");
                	s = s + new String(nodelist.item(i).getNodeValue().trim().getBytes());
            System.out.println(s);

        } else
        if(DebugIndicator.ON)
            System.out.println("node has no kids");
//        }catch(UnsupportedEncodingException e){
//        	e.printStackTrace();
//        	return s;
//        }
        return s;
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

    public boolean checkMultiplicity(int i, String s)
    {
        boolean flag = false;
        int j = -1;
        String s1 = "";
        String s2 = "";
        if(multiplicity >= min || multiplicity <= max)
        {
            j = MessageType.PASSED;
            s1 = "Element <" + elemName + "> passed the multiplicity test";
            flag = true;
        } else
        if(min == max)
        {
            j = MessageType.FAILED;
            s1 = "The multiplicity for element <" + elemName + "> is " + min + " and only " + max + " and " + multiplicity + " were found.";
        } else
        if(spm > 0)
        {
            j = MessageType.WARNING;
            s1 = "The smallest permitted maximum for element <" + elemName + "> is " + spm + " and " + multiplicity + " were found.";
            flag = true;
        } else
        {
            j = MessageType.FAILED;
            s1 = "The multiplicity for element <" + elemName + "> is " + min + " or " + max + " and " + multiplicity + " were found.";
        }
        messageHandler.addMessage(i, j, s1, s, s2);
        return flag;
    }

    public boolean checkMultiplicity(int i, String s, String s1, int j, int k, String s2, boolean flag)
    {
        boolean flag1 = true;
        byte byte0 = -1;
        String s3 = "";
        String s6 = "";
        if(j > 0)
        {
            if(s2.equalsIgnoreCase(""))
            {
                int l = MessageType.FAILED;
                String s4;
                if(flag)
                    s4 = "Attribute \"" + s1 + "\" was not found or was " + "left blank.";
                else
                    s4 = "Element <" + s1 + "> was not found or was " + "left blank.";
                messageHandler.addMessage(i, l, s4, s, s6);
                if(j == k)
                {
                    l = MessageType.FAILED;
                    if(flag)
                        s4 = "The multiplicity for attribute \"" + s1 + "\"" + " is " + j + " and only " + k;
                    else
                        s4 = "The multiplicity for element <" + s1 + ">" + " is " + j + " and only " + k;
                } else
                {
                    l = MessageType.FAILED;
                    if(flag)
                        s4 = "The multiplicity for attribute \"" + s1 + "\"" + " is " + j + " or " + k;
                    else
                        s4 = "The multiplicity for element <" + s1 + ">" + " is " + j + " or " + k;
                }
                messageHandler.addMessage(i, l, s4, s, s6);
                flag1 = false;
            }
        } else
        {
            int i1 = MessageType.PASSED;
            String s5;
            if(flag)
                s5 = "Attribute \"" + s1 + "\" passed the " + "multiplicity test";
            else
                s5 = "Element <" + s1 + "> passed the " + "multiplicity test";
            messageHandler.addMessage(i, i1, s5, s, s6);
        }
        return flag1;
    }

    public Vector getMessage(int i)
    {
        return messageHandler.getMessage(i);
    }
}
