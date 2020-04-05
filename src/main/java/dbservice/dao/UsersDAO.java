package dbservice.dao;

import dbservice.dataSets.UserDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UserDataSet get(long id) throws HibernateException {
        return session.get(UserDataSet.class, id);
    }



    public long getUserId(String login) throws HibernateException {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> dataSetCriteriaQuery = criteriaBuilder.createQuery(UserDataSet.class );
        Root<UserDataSet> userDataSetRoot = dataSetCriteriaQuery.from(UserDataSet.class);
        dataSetCriteriaQuery.where(criteriaBuilder.equal(userDataSetRoot.get("login"), login));
        dataSetCriteriaQuery.select(userDataSetRoot);
        Query<UserDataSet> query = session.createQuery(dataSetCriteriaQuery);
        UserDataSet result = query.getSingleResult();
        return result.getId();
    }

    public long insertUser(String login, String password) throws HibernateException {
        return (long) session.save(new UserDataSet(login, password));
    }
}
