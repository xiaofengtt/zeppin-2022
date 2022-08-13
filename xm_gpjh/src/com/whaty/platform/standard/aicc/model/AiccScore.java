package com.whaty.platform.standard.aicc.model;

public class AiccScore {
	private String rawScore="0";
	private String maxScore;
	private String minScore;
	public AiccScore() {
		
	}
	public AiccScore(String score) {
		if(score==null || score.trim().length()<1)
			rawScore="0";
		else
		{
			String[] values=score.split(",");
			if(values.length>0)
				rawScore=values[0].trim();
			if(values.length>1)
				maxScore=values[1].trim();
			if(values.length>2)
				minScore=values[2].trim();
		}
		
	}
	public String getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}
	public String getMinScore() {
		return minScore;
	}
	public void setMinScore(String minScore) {
		this.minScore = minScore;
	}
	public String getRawScore() {
		if(rawScore==null || rawScore.equals(""))
			return "0";
		else
			return rawScore;
	}
	public void setRawScore(String rawScore) {
		this.rawScore = rawScore;
	}
	public String toStrData()
	{
		String str=this.rawScore;
		if(this.minScore!=null)
		{
			str+=","+this.maxScore+","+this.minScore;
		}
		return str;
	}

}
