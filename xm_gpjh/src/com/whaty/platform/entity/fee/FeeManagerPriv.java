/**
 * 
 */
package com.whaty.platform.entity.fee;

/**
 * @author wangqiang
 * 
 */
public abstract class FeeManagerPriv {
	/***************************************************************************
	 * ���ù���Ȩ�� *
	 **************************************************************************/

	public int addFeeStandard = 1; // �Ƿ�������ѧ�ѱ�׼

	public int getFeeStandard = 1; // �Ƿ���Բ鿴ѧ�ѱ�׼

	public int addFee = 1; // �Ƿ�������ѧ�ѱ�׼

	public int getFee = 1; // �Ƿ���Բ鿴ѧ�ѱ�׼

	public int getFeeByTime = 1; // �Ƿ���԰���ʱ��ϲ�ѯѧ��

	public int getStuOtherFee = 1; // �Ƿ���Բ鿴ѧ���ӷ�

	public int getStuOtherFeeByTime = 1; // �Ƿ���԰���ʱ��β�ѯѧ���ӷ�

	public int getStuFeeReturnApply = 1; // �Ƿ���Բ鿴ѧ���˷�����

	public int getConfirmOrder = 1; // �Ƿ����ȷ�϶���

	public int getReConfirmOrder = 1; // �Ƿ��������ȷ�϶���

	public int getSiteFeeStat = 1; // �Ƿ���Բ鿴��ѧվѧ��ͳ��

	/***************************************************************************
	 * �ӷѹ���Ȩ�� *
	 **************************************************************************/

	public int addOtherFeeType = 1; // �Ƿ��������ӷ�����

	public int deleteOtherFeeType = 1; // �Ƿ����ɾ���ӷ�����

	public int getOtherFeeType = 1; // �Ƿ���Բ鿴�ӷ�����

	public int updateOtherFeeType = 1; // �Ƿ�����޸��ӷ�����

	public int addOtherFeeStandard = 1; // �Ƿ��������ӷѱ�׼

	public int deleteOtherFeeStandard = 1; // �Ƿ����ɾ���ӷѱ�׼

	public int getOtherFeeStandard = 1; // �Ƿ���Բ鿴�ӷѱ�׼

	public int updateOtherFeeStandard = 1; // �Ƿ�����޸��ӷѱ�׼

}
