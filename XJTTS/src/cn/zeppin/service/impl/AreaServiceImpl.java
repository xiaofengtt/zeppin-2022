package cn.zeppin.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.dao.IAreaDao;
import cn.zeppin.entity.Area;
import cn.zeppin.service.IAreaService;

public class AreaServiceImpl extends BaseServiceImpl<Area, Integer> implements
	IAreaService
{

    private IAreaDao areaDao;
    private List<String> lstAreaStrings = new ArrayList<String>();

    public IAreaDao getAreaDao()
    {
    	return areaDao;
    }

    public void setAreaDao(IAreaDao areaDao)
    {
    	this.areaDao = areaDao;
    }

    public AreaServiceImpl() throws NullPointerException
    {
    }

    @Override
    public Area add(Area t)
    {
	// TODO Auto-generated method stub
	return areaDao.add(t);
    }

    @Override
    public Area update(Area t)
    {
	// TODO Auto-generated method stub
	return areaDao.update(t);
    }

    @Override
    public void delete(Area t)
    {
	// TODO Auto-generated method stub
	areaDao.delete(t);
    }

    @Override
    public Area load(Integer id)
    {
	// TODO Auto-generated method stub
	return areaDao.load(id);
    }

    @Override
    public Area get(Integer id)
    {
	// TODO Auto-generated method stub
	return areaDao.get(id);
    }

    @Override
    public List<Area> loadAll()
    {
	// TODO Auto-generated method stub
	return areaDao.loadAll();
    }

    @Override
    public List<Area> findAll()
    {
	// TODO Auto-generated method stub
	return areaDao.findAll();
    }

    @Override
    public List<Object> findByHSQL(String querySql)
    {
	// TODO Auto-generated method stub
	return areaDao.findByHSQL(querySql);
    }

    @Override
    public List<Area> getListForPage(String hql, int offset, int length)
    {
	// TODO Auto-generated method stub
	return areaDao.getListForPage(hql, offset, length);
    }

    @Override
    public void executeHSQL(String hql)
    {
	// TODO Auto-generated method stub
	areaDao.executeHSQL(hql);
    }

    @Override
    public List<Area> getListByHSQL(String hql)
    {
	// TODO Auto-generated method stub
	return areaDao.getListByHSQL(hql);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List executeSQL(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	return areaDao.getListBySQL(sql, objParas);
    }

    @Override
    public void executeSQLUpdate(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	areaDao.executeSQLUpdate(sql, objParas);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List getListPage(String sql, int offset, int length,
	    Object[] objParas)
    {
	// TODO Auto-generated method stub
	return areaDao.getListPage(sql, offset, length, objParas);
    }

    @Override
    public List<Area> getParentCodeArea(String code)
    {
	// TODO Auto-generated method stub
	String hql = "from Area t where t.parentcode='" + code
		+ "' order by weight desc";
	List<Area> li = this.areaDao.getListByHSQL(hql);

	return li;
    }

    @Override
    public List<Area> getLevelArea(short level)
    {
	// TODO Auto-generated method stub
	String hql = "from Area t where t.level=" + level
		+ " order by weight desc";
	List<Area> li = this.areaDao.getListByHSQL(hql);

	return li;
    }

    @Override
    public Area getAreaByCode(String code)
    {

	String hql = "from Area t where t.code='" + code + "'";
	List<Area> li = this.areaDao.getListByHSQL(hql);
	if (li.size() > 0)
	{
	    return li.get(0);
	}
	else
	{
	    return null;
	}
    }

    /*
     * (non-Javadoc)
     * @see cn.zeppin.service.IAreaService#getParentNodes(java.lang.String)
     */
    @Override
    public List<String> getParentNodes(String code)
    {
	lstAreaStrings.clear();
	getAreaName(code);
	return lstAreaStrings;
    }

    public void getAreaName(String code)
    {
	Area area = getAreaByCode(code);
	if (area != null)
	{
	    lstAreaStrings.add(area.getName());
	    if (area.getParentcode() != "0")
	    {
		getAreaName(area.getParentcode());
	    }
	}
    }

    /*
     * (non-Javadoc)
     * @see cn.zeppin.service.IAreaService#getAllChildByCode(java.lang.String)
     */
    @Override
    public List<Area> getAllChildByCode(String code)
    {
	// TODO Auto-generated method stub
	List<Area> lstAreas = new ArrayList<>();
	String hqlString = "from Area where code like %" + code + "%";
	lstAreas = this.areaDao.getListByHSQL(hqlString);
	return lstAreas;
    }
}
