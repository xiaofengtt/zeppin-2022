package com.whaty.platform.vote.bean;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * VotePaper generated by MyEclipse - Hibernate Tools
 */

public class VotePaper  implements java.io.Serializable {


    // Fields    

     private String id;
     private String title;
     private String pictitle;
     private Long active;
     private Date foundDate;
     private Date startDate;
     private Date endDate;
     private String note;
     private Long viewSuggest;
     private Long limitDiffip;
     private Long limitDiffsession;
     private Long canSuggest;
     private String type;
     private String keywords;
     
     private String disStartDate;


    // Constructors

    /** default constructor */
    public VotePaper() {
    }

    
    /** full constructor */
    public VotePaper(String title, String pictitle, Long active, Date foundDate, Date startDate, Date endDate, String note, Long viewSuggest, Long limitDiffip, Long limitDiffsession, Long canSuggest, String type, String keywords) {
        this.title = title;
        this.pictitle = pictitle;
        this.active = active;
        this.foundDate = foundDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
        this.viewSuggest = viewSuggest;
        this.limitDiffip = limitDiffip;
        this.limitDiffsession = limitDiffsession;
        this.canSuggest = canSuggest;
        this.type = type;
        this.keywords = keywords;
    }

   
    // Property accessors
    
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictitle() {
        return this.pictitle;
    }
    
    public void setPictitle(String pictitle) {
        this.pictitle = pictitle;
    }

    public Long getActive() {
        return this.active;
    }
    
    public void setActive(Long active) {
        this.active = active;
    }

    public Date getFoundDate() {
        return this.foundDate;
    }
    
    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }

    public Long getViewSuggest() {
        return this.viewSuggest;
    }
    
    public void setViewSuggest(Long viewSuggest) {
        this.viewSuggest = viewSuggest;
    }

    public Long getLimitDiffip() {
        return this.limitDiffip;
    }
    
    public void setLimitDiffip(Long limitDiffip) {
        this.limitDiffip = limitDiffip;
    }

    public Long getLimitDiffsession() {
        return this.limitDiffsession;
    }
    
    public void setLimitDiffsession(Long limitDiffsession) {
        this.limitDiffsession = limitDiffsession;
    }

    public Long getCanSuggest() {
        return this.canSuggest;
    }
    
    public void setCanSuggest(Long canSuggest) {
        this.canSuggest = canSuggest;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getKeywords() {
        return this.keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }


	public String getDisStartDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		disStartDate = sf.format(getStartDate());
		return  disStartDate;
	}
   








}