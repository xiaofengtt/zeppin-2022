
package com.whaty.platform.training.skill;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;

/**
用来标识多个技能组成的技能链
 */
public abstract class SkillChain  implements Items
{
   private String id;
   private String name;
   private String note;
   private boolean isActive;
   
   public SkillChain() 
   {
    
   }
   
   /**
   Access method for the id property.
   
   @return   the current value of the id property
    */
   public String getId() 
   {
      return id;
   }
   
   /**
   Sets the value of the id property.
   
   @param aId the new value of the id property
    */
   public void setId(String aId) 
   {
      id = aId;
   }
   
   /**
   Access method for the name property.
   
   @return   the current value of the name property
    */
   public String getName() 
   {
      return name;
   }
   
   /**
   Sets the value of the name property.
   
   @param aName the new value of the name property
    */
   public void setName(String aName) 
   {
      name = aName;
   }
   
   /**
   Access method for the note property.
   
   @return   the current value of the note property
    */
   public String getNote() 
   {
      return note;
   }
   
   /**
   Sets the value of the note property.
   
   @param aNote the new value of the note property
    */
   public void setNote(String aNote) 
   {
      note = aNote;
   }
   
  
   public boolean getIsActive() {
	return isActive;
}

public void setActive(boolean isActive) {
	this.isActive = isActive;
}

/**
   @return java.util.List
    */
   public abstract List getSkillList() throws PlatformException;
   
   public abstract void addSkills(List skillIds) throws PlatformException;
   
   public abstract void removeSkills(List skillIds) throws PlatformException;
}
