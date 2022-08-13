package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IQuestionDao;
import cn.zeppin.dao.IResultDao;
import cn.zeppin.entity.Result;
import cn.zeppin.entity.Submit;
import cn.zeppin.service.IResultService;

public class ResultServiceImpl extends BaseServiceImpl<Result, Integer> implements IResultService {

	private IResultDao resultDao;
	private IQuestionDao questionDao;
	

	public IResultDao getResultDao() {
		return resultDao;
	}

	public void setResultDao(IResultDao resultDao) {
		this.resultDao = resultDao;
	}

	public int getResultCount(int paperid, int pid, int tcid, int tsid, int qid, int aid) {
		return this.resultDao.getResultCount(paperid, pid, tcid, tsid, qid, aid);
	}

	public int getResultCount(int paperid, int pid, int tcid, int tsid, int qid, int aid, int classes) {
		return this.resultDao.getResultCount(paperid, pid, tcid, tsid, qid, aid, classes);
	}

	@Override
	public List getResultScore(int submit, int psq) {
		// TODO Auto-generated method stub
		return this.resultDao.getResultScore(submit, psq);
	}

//	@Override
//	public void addResult(String submitdata, int teacherTotal, String paper_id, Submit submit) {
//		// TODO Auto-generated method stub
//		String[] datas = submitdata.split("}");
//		
//		
//		
//		for (String data : datas) {
//			String[] ds = data.split("\\$");
//
//			String inx = (data.split("\\$"))[0];
//			String ans = "";
//			if (ds.length > 1) {
//				ans = (data.split("\\$"))[1];
//			}
//
//			String[] as = ans.split("\\|");
//			
//			StringBuilder hql = new StringBuilder();
//			hql.append("FROM Question q,Answer a,Psq p where 1=1 ");
//			hql.append(" and q.psq=p.id and a.question=q.id and a.psq=p.id ");
//			hql.append(" and q.psq="+paper_id);
//			hql.append(" and q.inx="+inx);
//			
//			if(as.length>1){
//				for(String a: as){
//					String contentString = "";
//					int score = 0;
//					try {
//						int temp = Integer.parseInt(a);// 转换成功,非填空题
//						
//						hql.append(" and a.inx="+temp);
//
//					} catch (Exception ex) {
//						// 否则为填空题
//						contentString = a;
//						hql.append(" and a.name='$$填空题$$'");
//					}
//					
//					List answerList = this.questionDao.findByHSQL(hql.toString());
//				}
//			}else{
//				
//			}
////			String qid = "";
////
////			String sql = "SELECT id FROM question WHERE psq = " + paper_id + " and inx = " + inx + " and type <> 0";
////			List qs = this.executeSQL(sql, null);
////
////			if (qs != null && qs.size() == 1) {
////				qid = qs.get(0) + "";
////				String[] as = ans.split("\\|");
////				for (String a : as) {
////					String contentString = "";
////					String aid = "";
////					int score = 0;
////					boolean isWord = false;
////
////					try {
////						int temp = Integer.parseInt(a);// 转换成功,非填空题
////
////						sql = "SELECT id, score FROM answer WHERE psq = " + paper_id + " and inx = " + a + " and question =" + qid;
////					} catch (Exception ex) {
////						// 否则为填空题
////						contentString = a;
////						isWord = true;
////						sql = "SELECT id, score FROM answer WHERE psq = " + paper_id + " and name='$$填空题$$'" + " and question =" + qid;
////					}
////
////					List as1 = this.executeSQL(sql, null);
////
////					if (as1 != null && as1.size() > 0) {
////						for (int i = 0; i < as1.size(); i++) {
////
////							Object[] objects = (Object[]) as1.get(i);
////							aid = objects[0] + "";
////							score = Integer.parseInt(objects[1] + "");
////							Result res = new Result();
////							int qqid = Integer.parseInt(qid);
////							res.setQuestion(this.getiQuestionService().get(qqid));
////							int aaid = Integer.parseInt(aid);
////							res.setAnswer(this.getiAnswerService().get(aaid));
////
////							res.setScore(score);
////							res.setSubmit(submit);
////							res.setContent(contentString);
////
////							this.getiResultService().add(res);
////							if (score > 0) {
////								teacherTotal += score;
////								anscount += 1;
////							}
////						}
////					}
////
////				}
////			}
//		}
//	}
}
