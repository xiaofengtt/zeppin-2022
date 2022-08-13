package com.whaty.platform.entity.service.imp.basic;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeSubject;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeTraineeService;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class PeTraineeServiceImp
  implements PeTraineeService
{
  private GeneralDao generalDao;
  private MyListDAO myListDao;

  public AbstractBean save(AbstractBean instance)
    throws EntityException
  {
    PeTrainee peTrainee = (PeTrainee)instance;

    SsoUser user = new SsoUser();
    user.setLoginId(peTrainee.getLoginId());
    user.setPassword(Const.FIRST_PASSWORD);
    user.setLoginNum(Long.valueOf("0"));

    EnumConst isValid = getMyListDao().getEnumConstByNamespaceCode("FlagIsvalid", "0");

    user.setEnumConstByFlagIsvalid(isValid);

    DetachedCriteria dCriteria = DetachedCriteria.forClass(PePriRole.class);
    dCriteria.add(Restrictions.eq("name", "学员"));
    List roleList = getGeneralDao().getList(dCriteria);
    if ((roleList != null) && (roleList.size() != 0)) {
      PePriRole role = (PePriRole)roleList.get(0);
      user.setPePriRole(role);
    }
    getGeneralDao().save(user);
    peTrainee.setSsoUser(user);
    return getGeneralDao().save(peTrainee);
  }

  public int saveCertificateNo(File file)
    throws EntityException
  {
    StringBuffer msg = new StringBuffer();
    int count = 0;
    Workbook work = null;
    try {
      work = Workbook.getWorkbook(file);
    } catch (Exception e) {
      e.printStackTrace();
      msg.append("Excel表格读取异常！批量添加失败！<br/>");
      throw new EntityException(msg.toString());
    }
    Sheet sheet = work.getSheet(0);
    int rows = sheet.getRows();
    if (rows < 2) {
      msg.append("表格为空！<br/>");
    }
    String temp = "";

    UserSession userSession = (UserSession)ActionContext.getContext().getSession().get("user_session");
    if (userSession == null) {
      throw new EntityException("批量上传证书编号失败,无法取得您的身份信息！");
    }
    Set<PeTrainee> peTraineeSet = new HashSet();
    SsoUser ssoUser = userSession.getSsoUser();
    PeTrainee peTrainee = new PeTrainee();
    for (int i = 1; i < rows; i++) {
      try {
        temp = sheet.getCell(0, i).getContents().trim();
        if ((temp == null) || ("".equals(temp))) {
          msg.append("第" + (i + 1) + "行数据，姓名为空！<br/>");
        }
        else {
          peTrainee.setName(temp);

          temp = sheet.getCell(1, i).getContents().trim();
          if ((temp == null) && ("".equals(temp))) {
            msg.append("第" + (i + 1) + "行数据，培训项目为空！<br/>");
          }
          else {
            DetachedCriteria dcPeProApplyno = DetachedCriteria.forClass(PeProApplyno.class);
            dcPeProApplyno.add(Restrictions.eq("name", temp));
            List listProApplyno = getGeneralDao().getList(dcPeProApplyno);
            if ((listProApplyno == null) || (listProApplyno.isEmpty())) {
              msg.append("第" + (i + 1) + "行数据，培训项目不存在！<br/>");
            }
            else {
              peTrainee.setPeProApplyno((PeProApplyno)listProApplyno.get(0));

              temp = sheet.getCell(2, i).getContents().trim();
              if ((temp == null) && ("".equals(temp))) {
                msg.append("第" + (i + 1) + "行数据，学科为空！<br/>");
              }
              else {
                DetachedCriteria dcSubject = DetachedCriteria.forClass(PeSubject.class);
                dcSubject.add(Restrictions.eq("name", temp));
                List listSubject = getGeneralDao().getList(dcSubject);
                if ((listSubject == null) || (listSubject.isEmpty())) {
                  msg.append("第" + (i + 1) + "行数据，学科不存在！<br/>");
                }
                else {
                  peTrainee.setPeSubject((PeSubject)listSubject.get(0));

                  temp = sheet.getCell(3, i).getContents().trim();
                  if ((temp == null) || ("".equals(temp))) {
                    msg.append("第" + (i + 1) + "行数据，培训单位不能为空！<br/>");
                  }
                  else
                  {
                    DetachedCriteria dcPeUnit = DetachedCriteria.forClass(PeUnit.class);
                    dcPeUnit.add(Restrictions.eq("name", temp));
                    List peUnitList = getGeneralDao().getList(dcPeUnit);
                    if ((peUnitList == null) || (peUnitList.isEmpty())) {
                      msg.append("第" + (i + 1) + "行数据，培训单位不存在！<br/>");
                    }
                    else {
                      if (userSession.getRoleType().equals("2")) {
                        DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
                        dc.createAlias("ssoUser", "ssoUser");
                        dc.add(Restrictions.eq("ssoUser", ssoUser));
                        List listManager = getGeneralDao().getList(dc);
                        if (!temp.equals(((PeManager)listManager.get(0)).getPeUnit().getName())) {
                          msg.append("第" + (i + 1) + "行数据，培训单位填写错误！<br/>");
                          continue;
                        }
                      }
                      peTrainee.setPeUnitByFkTrainingUnit((PeUnit)peUnitList.get(0));

                      temp = sheet.getCell(4, i).getContents().trim();
                      if ((temp == null) || ("".equals(temp))) {
                        msg.append("第" + (i + 1) + "行数据，证书编号不能为空！<br/>");
                      }
                      else {
                        peTrainee.setCertificateNumber(temp);

                        Cell cell = sheet.getCell(5, i);

                        Date date = null;
                        try {
                          date = getCellDateValue(cell, "开始时间");
                        } catch (EntityException ee) {
                          msg.append("第" + (i + 1) + "行数据，" + ee.getMessage() + "<br/>");
                          continue;
                        }
                        peTrainee.setStartDate(date);

                        cell = sheet.getCell(6, i);
                        try {
                          date = getCellDateValue(cell, "结束时间");
                        } catch (EntityException ee) {
                          msg.append("第" + (i + 1) + "行数据，" + ee.getMessage() + "<br/>");
                          continue;
                        }
                        peTrainee.setEndDate(date);

                        temp = null;
                        cell = sheet.getCell(7, i);
                        try {
                          temp = getCellNumberValue(cell, "条件要求得分");
                        } catch (EntityException ee) {
                          msg.append("第" + (i + 1) + "行数据，" + ee.getMessage() + "<br/>");
                          continue;
                        }

                        peTrainee.setYaoqiudefen(temp);

                        temp = null;
                        cell = sheet.getCell(8, i);
                        try {
                          temp = getCellNumberValue(cell, "学习过程得分");
                        } catch (EntityException ee) {
                          msg.append("第" + (i + 1) + "行数据，" + ee.getMessage() + "<br/>");
                          continue;
                        }

                        peTrainee.setGuochengdefen(temp);

                        temp = null;
                        cell = sheet.getCell(9, i);
                        try {
                          temp = getCellNumberValue(cell, "学习成效得分");
                        } catch (EntityException ee) {
                          msg.append("第" + (i + 1) + "行数据，" + ee.getMessage() + "<br/>");
                          continue;
                        }

                        peTrainee.setChengxiaodefen(temp);

                        temp = sheet.getCell(10, i).getContents().trim();
                        if ((temp == null) || ("".equals(temp))) {
                          msg.append("第" + (i + 1) + "行数据，评价意见为空！<br/>");
                        }
                        else {
                          peTrainee.setYijian(temp);

                          DetachedCriteria dcPeTrainee = DetachedCriteria.forClass(PeTrainee.class);
                          dcPeTrainee.createAlias("peSubject", "peSubject");
                          dcPeTrainee.createAlias("peProApplyno", "peProApplyno");
                          dcPeTrainee.createAlias("peUnitByFkTrainingUnit", "peUnitByFkTrainingUnit");
                          dcPeTrainee.add(Restrictions.eq("name", peTrainee.getName()));
                          dcPeTrainee.add(Restrictions.eq("peProApplyno", peTrainee.getPeProApplyno()));
                          dcPeTrainee.add(Restrictions.eq("peSubject", peTrainee.getPeSubject()));
                          dcPeTrainee.add(Restrictions.eq("peUnitByFkTrainingUnit", peTrainee.getPeUnitByFkTrainingUnit()));
                          List peTraneeList = getGeneralDao().getList(dcPeTrainee);
                          if ((peTraneeList == null) || (peTraneeList.isEmpty())) {
                            msg.append("第" + (i + 1) + "行数据，没有找到该学员！<br/>");
                          }
                          else {
                            PeTrainee peTraineeOld = (PeTrainee)peTraneeList.get(0);

                            if ((peTraineeOld.getEnumConstByFkStatusTraining() != null) && (peTraineeOld.getEnumConstByFkStatusTraining().getCode().equals("002"))) {
                              msg.append("第" + (i + 1) + "行数据学员为“未报到”状态，无法导入证书！<br/>");
                            }
                            else {
                              peTraineeOld.setCertificateNumber(peTrainee.getCertificateNumber());
                              peTraineeOld.setStartDate(peTrainee.getStartDate());
                              peTraineeOld.setEndDate(peTrainee.getEndDate());

                              peTraineeOld.setYaoqiudefen(peTrainee.getYaoqiudefen());
                              peTraineeOld.setGuochengdefen(peTrainee.getGuochengdefen());
                              peTraineeOld.setChengxiaodefen(peTrainee.getChengxiaodefen());
                              peTraineeOld.setYijian(peTrainee.getYijian());

                              if (!peTraineeSet.add(peTraineeOld)) {
                                msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
                              }
                              else
                              {
                                count++; }  }  }  }  }  }  }  }  }  } 
          }
        } } catch (Exception e) { e.printStackTrace();
        msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
      }

    }

    if (msg.length() > 0) {
      msg.append("证书编号批量上传失败，请修改以上错误之后重新上传！<br/>");
      throw new EntityException(msg.toString());
    }

    for (PeTrainee peTraineeBean : peTraineeSet) {
      try {
        getGeneralDao().save(peTraineeBean);
      } catch (Exception e) {
        e.printStackTrace();
        throw new EntityException("批量上传证书编号失败。");
      }
    }
    return peTraineeSet.size();
  }
  public List saveList(List list) throws EntityException {
    for (int i = 0; i < list.size(); i++) {
      list.set(i, save((PeTrainee)list.get(i)));
    }
    return list;
  }
  public MyListDAO getMyListDao() {
    return this.myListDao;
  }

  public void setMyListDao(MyListDAO myListDao) {
    this.myListDao = myListDao;
  }

  public GeneralDao getGeneralDao() {
    return this.generalDao;
  }

  public void setGeneralDao(GeneralDao generalDao) {
    this.generalDao = generalDao;
  }

  public static Date getCellDateValue(Cell cell, String row) throws EntityException {
    if (cell.getType().equals(CellType.DATE))
      return ((DateCell)cell).getDate();
    if (cell.getType().equals(CellType.EMPTY)) {
      throw new EntityException(row + "不能为空！");
    }

    throw new EntityException(row + "单元格格式必须为日期类型！");
  }

  public static String getCellNumberValue(Cell cell, String row) throws EntityException
  {
    if (cell.getType().equals(CellType.NUMBER))
      return cell.getContents().trim();
    if (cell.getType().equals(CellType.EMPTY)) {
      throw new EntityException(row + "不能为空！");
    }

    throw new EntityException(row + "单元格格式必须为数值类型！");
  }
}