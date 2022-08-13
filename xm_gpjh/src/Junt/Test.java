package Junt;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.OrderedSetType;
import org.springframework.beans.PropertyValues;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzCourseFeedback;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.util.OrderProperty;

public class Test extends HibernateDaoSupport {

	private static String configFile = "./hibernate.cfg.xml";
	public static SessionFactory sessionFactory;
	private Set checkcomnum = new HashSet();
	private Vector  vector = new Vector();
	int k = 0;

	private static final String str_num = "0123456789";
	private static final String str_char = "abcdefghijklmnopqrstuvwxyz";
	private static final String str_Upperchar = str_char.toUpperCase();
	static {
		try {
			Configuration config = new Configuration();
			config.configure(configFile);
			sessionFactory = config.buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public int getsame(String num){
		String tempnum = "";
		int result =0;
		if(checkcomnum.add(num)){
			System.out.println("num================>"+num+"========记录1次");
			vector.addElement(num+"1");
			tempnum ="1";
		}else{
			System.out.println("num ============>"+num);
		for(int m = 0 ; m<vector.size();m++){
			
			if((vector.get(m).toString()).contains(num)){
				System.out.println("vector.get(m) =================>"+vector.get(m).toString());
				tempnum =	vector.get(m).toString().substring(3);
				System.out.println("tempnum==============>"+tempnum);
				int k =Integer.parseInt(tempnum)+1;
				System.out.println("k=================>"+k);
					vector.setElementAt(num+k, m);
					result = k;
				}
			}
		}
		
		return result;
	}

	public void checkBulltin() throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
				System.out.println("");
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}

	}

	private static String getRandomchar(String str) {
		if (str.length() < 1 || str == "" || str == null) {
			return "";
		}
		return String.valueOf(str.charAt((int) (Math.random() * str.length())));
	}

	public static void main(String[] args) throws Exception {
		Test test = new Test();
		test.checkBulltin();
	}

}
