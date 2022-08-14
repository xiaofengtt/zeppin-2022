/**
 * 
 */
package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.util.List;

public class SharenumsVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7232429294437331173L;
	
	private List<Integer> currentNums;
	private List<Integer> usedNums;
	
	
	public List<Integer> getCurrentNums() {
		return currentNums;
	}
	public void setCurrentNums(List<Integer> currentNums) {
		this.currentNums = currentNums;
	}
	public List<Integer> getUsedNums() {
		return usedNums;
	}
	public void setUsedNums(List<Integer> usedNums) {
		this.usedNums = usedNums;
	}
}
