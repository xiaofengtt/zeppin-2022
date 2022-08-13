import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Excel {

	private static Configuration configuration;
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	private static Session session;
	private static Transaction transaction;
	private static final String fileName="";

	public static void befor() {
		configuration = new Configuration().configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.getCurrentSession();
		transaction = session.beginTransaction();
	}

	public static void after() {
		transaction.commit();
		sessionFactory.close();
	}

	public static void main(String[] args) {
		befor();
		read();
		after();
	}
	
	
	
	public static void read(){
		
	}

}
