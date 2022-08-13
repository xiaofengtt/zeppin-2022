/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.ITeacherDao;
import com.zeppin.entiey.Teacher;
import com.zeppin.service.ITeachereService;

/**
 * @author suijing
 * 
 */
public class TeacherService extends BaseService<Teacher, Integer> implements
	ITeachereService
{

    ITeacherDao itd;

    /**
     * @return itd
     */
    public ITeacherDao getItd()
    {
	return itd;
    }

    /**
     * @param itd
     *            要设置的 itd
     */
    public void setItd(ITeacherDao itd)
    {
	this.itd = itd;
    }

    /*
     * （非 Javadoc）
     * @see com.zeppin.service.ITeachereService#getITeacherDao()
     */
    @Override
    public ITeacherDao getITeacherDao()
    {
	// TODO 自动生成的方法存根
	return getItd();
    }

}
