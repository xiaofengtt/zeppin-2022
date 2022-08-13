package com.whaty.platform.entity.service.imp.information;

import com.whaty.platform.entity.bean.PeDocument;
import com.whaty.platform.entity.bean.PrDocument;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.information.PeDocumentService;

public class PeDocumentServiceImp implements PeDocumentService {
	private GeneralDao generalDao;

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.information.PeDocumentService#saveforsave(com.whaty.platform.entity.bean.PeDocument, java.util.List)
	 */
	public PeDocument saveforsave(PeDocument peDocument,java.util.List list) throws EntityException{
		try{
			peDocument = (PeDocument)this.getGeneralDao().save(peDocument);
			for(Object o:list){
				PrDocument prDocument = new PrDocument();
				prDocument.setPeDocument(peDocument);
				prDocument.setSsoUser((SsoUser)o);
				prDocument.setEnumConstByFlagDel(this.getGeneralDao().getEnumConstByNamespaceCode("FlagDel", "0"));
				this.getGeneralDao().save(prDocument);
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("保存失败...");
		}
		return peDocument;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.information.PeDocumentService#saveforsend(com.whaty.platform.entity.bean.PeDocument, java.util.List)
	 */
	public PeDocument saveforsend(PeDocument peDocument,java.util.List list) throws EntityException{
		try{
			peDocument = (PeDocument)this.getGeneralDao().save(peDocument);
			for(Object o:list){
				PrDocument prDocument = new PrDocument();
				prDocument.setPeDocument(peDocument);
				prDocument.setSsoUser((SsoUser)o);
				prDocument.setEnumConstByFlagDel(this.getGeneralDao().getEnumConstByNamespaceCode("FlagDel", "0"));
				prDocument.setEnumConstByFlagRead(this.getGeneralDao().getEnumConstByNamespaceCode("FlagRead", "0"));
				this.getGeneralDao().save(prDocument);
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("发送失败...");
		}
		return peDocument;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.information.PeDocumentService#updateforsave(com.whaty.platform.entity.bean.PeDocument, java.util.List)
	 */
	public PeDocument updateforsave(PeDocument peDocument,java.util.List list) throws EntityException{
		try{
			peDocument = (PeDocument)this.getGeneralDao().save(peDocument);
			java.util.Set set = peDocument.getPrDocuments();
			for(Object o:set){
				PrDocument prDocument = (PrDocument)o;
				if(list==null||list.size()<=0){
					this.getGeneralDao().delete(prDocument);
				}else if(list.contains(prDocument.getSsoUser())){
					list.remove(prDocument.getSsoUser());
				}else{
					this.getGeneralDao().delete(prDocument);
				}
			}
			for(Object o:list){
				PrDocument prDocument = new PrDocument();
				prDocument.setPeDocument(peDocument);
				prDocument.setSsoUser((SsoUser)o);
				prDocument.setEnumConstByFlagDel(this.getGeneralDao().getEnumConstByNamespaceCode("FlagDel", "0"));
				this.getGeneralDao().save(prDocument);
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("发送失败...");
		}
		return peDocument;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.information.PeDocumentService#updateforsend(com.whaty.platform.entity.bean.PeDocument, java.util.List)
	 */
	public PeDocument updateforsend(PeDocument peDocument,java.util.List list) throws EntityException{
		try{
			peDocument = (PeDocument)this.getGeneralDao().save(peDocument);
			java.util.Set set = peDocument.getPrDocuments();
			for(Object o:set){
				PrDocument prDocument = (PrDocument)o;
				if(list.contains(prDocument.getSsoUser())){
					list.remove(prDocument.getSsoUser());
					prDocument.setEnumConstByFlagRead(this.getGeneralDao().getEnumConstByNamespaceCode("FlagRead", "0"));
					this.getGeneralDao().save(prDocument);
				}else{
					this.getGeneralDao().delete(prDocument);
				}
			}
			for(Object o:list){
				PrDocument prDocument = new PrDocument();
				prDocument.setPeDocument(peDocument);
				prDocument.setSsoUser((SsoUser)o);
				prDocument.setEnumConstByFlagDel(this.getGeneralDao().getEnumConstByNamespaceCode("FlagDel", "0"));
				prDocument.setEnumConstByFlagRead(this.getGeneralDao().getEnumConstByNamespaceCode("FlagRead", "0"));
				this.getGeneralDao().save(prDocument);
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("发送失败...");
		}
		return peDocument;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.information.PeDocumentService#updateforsend(java.util.List)
	 */
	public int updateforsend(java.util.List ids) throws EntityException{
		try{
			for(Object id:ids){
				PeDocument peDocument = (PeDocument)this.getGeneralDao().getById(id.toString());
				if(peDocument.getSendDate()!=null){
					throw new EntityException("所要发送的公文中"+peDocument.getTitle()+"已经发送成功，不必重新发送...");
				}
				peDocument.setSendDate(new java.util.Date());
				peDocument = (PeDocument)this.getGeneralDao().save(peDocument);
				java.util.Set set = peDocument.getPrDocuments();
				if(set==null||set.size()<=0){
					throw new EntityException("所要发送的公文中"+peDocument.getTitle()+"没有接收人，发送失败...");
				}
				for(Object o:set){
					PrDocument prDocument = (PrDocument)o;
					prDocument.setEnumConstByFlagRead(this.getGeneralDao().getEnumConstByNamespaceCode("FlagRead", "0"));
					this.getGeneralDao().save(prDocument);				
				}
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("发送失败...");
		}
		return ids.size();
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.information.PeDocumentService#deleteByIds(java.util.List)
	 */
	public int deleteByIds(java.util.List ids) throws EntityException{
		try{
			for(Object id:ids){
				PeDocument peDocument = (PeDocument)this.getGeneralDao().getById(id.toString());
				java.util.Set set = peDocument.getPrDocuments();
				if(peDocument.getSendDate()==null){
					for(Object o:set){
						PrDocument prDocument = (PrDocument)o;
						this.getGeneralDao().delete(prDocument);
					}
					this.getGeneralDao().delete(peDocument);
				}else{
					peDocument.setEnumConstByFlagDel(this.getGeneralDao().getEnumConstByNamespaceCode("FlagDel", "1"));
					this.getGeneralDao().save(peDocument);
				}
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("删除失败...");
		}
		return ids.size();
	}
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	
}
