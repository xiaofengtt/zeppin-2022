package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.ResultDao;
import com.gpjh.model.Result;
import com.gpjh.service.ResultService;
import com.gpjh.model.Answer;
import com.gpjh.model.LoginKey;
import com.gpjh.model.Question;
import com.gpjh.model.Submit;

@Service("resultService")
public class ResultServiceImpl extends BaseServiceImpl<Result, Integer> implements ResultService {
    
    @Autowired
	private ResultDao resultDao;

	@Override
	public Result add(Result t) {
		// TODO Auto-generated method stub
		return resultDao.add(t);
	}

	@Override
	public void delete(Result t) {
		// TODO Auto-generated method stub
		resultDao.delete(t);
	}

	@Override
	public Result load(Integer id) {
		// TODO Auto-generated method stub
		return resultDao.load(id);
	}

	@Override
	public List<Result> loadAll() {
		// TODO Auto-generated method stub
		return resultDao.loadAll();
	}

	@Override
	public Result update(Result t) {
		// TODO Auto-generated method stub
		return resultDao.update(t);
	}

	@Override
	public Result get(Integer id) {
		// TODO Auto-generated method stub
		return resultDao.get(id);
	}

    @Override
    public List<Object> findByHSQL(String querySql){
    	return resultDao.findByHSQL(querySql);
    }
    
	@Override
	public List<Result> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return resultDao.getListForPage(hql, pageId, pageSize);
	}
	
	@Override
	public List<Result> getListByHSQL(String hql) {
		return resultDao.getListByHSQL(hql);
	}

    @Override
    public List<Object> executeSQL(String querySql){
    	return resultDao.executeSQL(querySql);
    }
    
    public void addResult(String[] datas ,String paper_id ,LoginKey lk ,Submit submit){
		for (String data : datas) {
			String[] ds = data.split("\\$");

			String inx = (data.split("\\$"))[0];
			String ans = "";
			if (ds.length > 1) {
				ans = (data.split("\\$"))[1];
			}

			String qid = "";

			String sql = "SELECT id FROM question WHERE psq = " + paper_id + " and inx = " + inx + " and type <> 0";
			List qs = this.executeSQL(sql);

			if (qs != null && qs.size() == 1) {
				qid = qs.get(0) + "";
				String[] as = ans.split("\\|");
				for (String a : as) {
					String contentString = "";
					String aid = "";
					int score = 0;
					boolean isWord = false;

					try {
						int temp = Integer.parseInt(a);// 转换成功,非填空题

						sql = "SELECT id, score FROM answer WHERE psq = " + paper_id + " and inx = " + a + " and question =" + qid;
					} catch (Exception ex) {
						// 否则为填空题
						contentString = a;
						isWord = true;
						sql = "SELECT id, score FROM answer WHERE psq = " + paper_id + " and name='$$填空题$$'" + " and question =" + qid;
					}

					List as1 = this.executeSQL(sql);

					if (as1 != null && as1.size() > 0) {
						for (int i = 0; i < as1.size(); i++) {

							// for (Answer a1 : as1) {
							Object[] objects = (Object[]) as1.get(i);
							aid = objects[0] + "";
							score = Integer.parseInt(objects[1] + "");
							Result res = new Result();
							int qqid = Integer.parseInt(qid);
							res.setQuestion(new Question(qqid));
							int aaid = Integer.parseInt(aid);
							res.setAnswer(new Answer(aaid));
							res.setLoginkey(lk);
							res.setScore(score);
							res.setSubmit(submit);
							res.setContent(contentString);
							this.add(res);

						}
					}

				}
			}
		}
	}
}