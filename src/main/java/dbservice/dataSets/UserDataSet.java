package dbservice.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserDataSet implements Serializable {

    private static final long serialVersionUID = -8706689714326132798L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "login", unique = true, updatable = false)
    private  String login;

    @Column(name = "password")
    private  String password;

    @SuppressWarnings("UnusedDeclaration")
    public UserDataSet() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public UserDataSet(long id, String login, String password) {
        this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
    }

    public UserDataSet(String login, String password) {
        this.setId(-1);
        this.setLogin(login);
        this.setPassword(password);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDataSet{" + "id=" + id + ", login=" + login + ", password=" + password + "}";
    }
}
