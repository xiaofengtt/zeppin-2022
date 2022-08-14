/**
 * 
 */
package cn.product.treasuremall.vo.back;

import java.io.Serializable;

/**
 * 两群PK玩法抽奖号码
 * @author user
 *
 */
public class SharenumsPKVO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -412164764021943864L;
	
	
	private SharenumsVO lucky;
	private SharenumsVO raider;
	
	public SharenumsVO getLucky() {
		return lucky;
	}
	public void setLucky(SharenumsVO lucky) {
		this.lucky = lucky;
	}
	public SharenumsVO getRaider() {
		return raider;
	}
	public void setRaider(SharenumsVO raider) {
		this.raider = raider;
	}
	
	
}
