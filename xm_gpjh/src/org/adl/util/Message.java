// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Message.java

package org.adl.util;


// Referenced classes of package org.adl.util:
//            MessageType

public class Message
{

    private int messageType;
    private String messageText;
    private String messageLocation;
    private String messageTrace;

    public Message()
    {
        messageText = new String("");
        messageLocation = new String("");
        messageTrace = new String("");
    }

    public Message(int i, String s, String s1, String s2)
    {
        messageType = i;
        messageText = s;
        messageLocation = s1;
        messageTrace = s2;
    }

    public Message(int i, String s, String s1)
    {
        messageType = i;
        messageText = s;
        messageLocation = s1;
        messageTrace = "";
    }

    public int getMessageType()
    {
        return messageType;
    }

    public String getMessageText()
    {
        return messageText;
    }

    public String getMessageLocation()
    {
        return messageLocation;
    }

    public String getMessageTrace()
    {
        return messageTrace;
    }

    public String toString()
    {
        String s = new String("");
        if(messageType == MessageType.INFO)
            s = "INFO";
        else
        if(messageType == MessageType.WARNING)
            s = "WARNING";
        else
        if(messageType == MessageType.PASSED)
            s = "PASSED";
        else
        if(messageType == MessageType.FAILED)
            s = "FAILED";
        else
            s = "OTHER";
        s = s + " : " + messageLocation + " : " + messageText;
        if(!messageTrace.equals(""))
            s = s + " : " + messageTrace;
        return s;
    }
}
