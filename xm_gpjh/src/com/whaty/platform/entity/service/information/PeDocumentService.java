package com.whaty.platform.entity.service.information;

import com.whaty.platform.entity.bean.PeDocument;
import com.whaty.platform.entity.exception.EntityException;

public interface PeDocumentService {
	
	/**
	 * 保存公文到发件箱（没有发送出去）
	 * @param peDocument
	 * 		要保存的公文
	 * @param list
	 * 		收件人列表(SsoUser)
	 * @return
	 * @throws EntityException
	 */
	public abstract PeDocument saveforsave(PeDocument peDocument,java.util.List list) throws EntityException;

	/**
	 * 直接发送公文
	 * @param peDocument
	 * 		要发送的公文
	 * @param list
	 * 		收件人列表(SsoUser)
	 * @return
	 * @throws EntityException
	 */
	public abstract PeDocument saveforsend(PeDocument peDocument,java.util.List list) throws EntityException;
	
	/**
	 * 更新公文（不发送）
	 * @param peDocument
	 * 		要更新的公文
	 * @param list
	 * 		更新后的收件人列表
	 * @return
	 * @throws EntityException
	 */
	public abstract PeDocument updateforsave(PeDocument peDocument,java.util.List list) throws EntityException;
	
	/**
	 * 更新并且发送公文
	 * @param peDocument
	 * 		要发送的公文
	 * @param list
	 * 		更新后的收件人列表
	 * @return
	 * @throws EntityException
	 */
	public abstract PeDocument updateforsend(PeDocument peDocument,java.util.List list) throws EntityException;
	
	/**
	 * 发送已经保存的公文
	 * @param ids
	 * 	要发送的公文的ID列表
	 * @return
	 * @throws EntityException
	 */
	public abstract int updateforsend(java.util.List ids) throws EntityException;
	
	/**
	 * 删除已经发送过的公文
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int deleteByIds(java.util.List ids) throws EntityException;
}
