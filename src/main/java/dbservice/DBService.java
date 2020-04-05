package dbservice;

import dbservice.dao.UsersDAO;
import dbservice.dataSets.UserDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class DBService {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    private final SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getMysqlConfiguration();
        this.sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry registry = builder.build();
        return configuration.buildSessionFactory(registry);
    }

    private Configuration getMysqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class","com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/mydb");
        configuration.setProperty("hibernate.connection.username","inginiir");
        configuration.setProperty("hibernate.connection.password","Qwerty!1");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);

        return configuration;
    }

    public UserDataSet getUser(long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UserDataSet dataSet = dao.get(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public UserDataSet getUser(String login) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UserDataSet dataSet = dao.get(dao.getUserId(login));
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }


    public long addUser(String login, String password) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.insertUser(login, password);
            transaction.commit();
            session.close();
            return id;
        }  catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void printConnectInfo() {
        try (Session session = sessionFactory.openSession()){
            session.doWork(connection ->  {
                System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
                System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
                System.out.println("Driver name: " + connection.getMetaData().getDriverName());
                System.out.println("Auto commit: " + connection.getAutoCommit());
            });
        }
    }


}
