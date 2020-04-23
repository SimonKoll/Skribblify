package login_registration.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class User {


    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty user_img = new SimpleStringProperty();
    private final StringProperty user_status = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> last_login = new SimpleObjectProperty<LocalDate>();


    public User(){
        username.setValue(null);
        password.setValue(null);
        user_img.setValue(null);
        user_status.setValue(null);
        last_login.setValue(null);
    }

    private void killAndFill(){

        // Username Überprüfungen
        if(username.get() == null){
            throw new IllegalArgumentException("Username muss angegeben werden!");
        }

        if(password.get() == null){
            throw new IllegalArgumentException("Password muss angegeben werden.");
        }

    }


    public User checkExisting(String username, Statement statement) throws SQLException {
        String sql
                = " select user_id "
                + "       ,username "
                + "       ,status "
                + "       ,user_img "
                + "       ,last_login "
                + "       ,password "
                + " from users "
                + " where username = '" + username + "' ";


        ResultSet rSet = statement.executeQuery(sql);
        User user = null;

        if(rSet.next()){
            user = new User();

            user.setUsername(rSet.getString("username"));
            user.setPassword(rSet.getString("password"));
            user.setUser_img(rSet.getString("user_img"));
            user.setUser_status(Status.Y);
        }

        return user;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }


    public String getUser_img() {
        return user_img.get();
    }

    public StringProperty user_imgProperty() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img.set(user_img);
    }

    public String getUser_status() {
        return user_status.get();
    }

    public StringProperty user_statusProperty() {
        return user_status;
    }

    public void setUser_status(Status user_status) {
        this.user_status.set(String.valueOf(user_status));
    }

    public LocalDate getLast_login() {
        return last_login.get();
    }

    public ObjectProperty<LocalDate> last_loginProperty() {
        return last_login;
    }

    public void setLast_login(LocalDate last_login) {
        this.last_login.set(last_login);
    }
}
