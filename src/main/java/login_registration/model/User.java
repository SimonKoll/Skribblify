package login_registration.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class User {

    private StringProperty user_id = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty user_img = new SimpleStringProperty();
    private final StringProperty user_status = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> last_login = new SimpleObjectProperty<LocalDate>();


    public User() {
        username.setValue(null);
        password.setValue(null);
        user_img.setValue(null);
        user_status.setValue(null);
        last_login.setValue(null);
    }

    private static void killAndFill( StringProperty username, StringProperty password) {

        // Überprüfungen
        if (username.get() == null) {
            throw new IllegalArgumentException("Username muss angegeben werden!");
        }
        if (username.get().length() < 5) {
            throw new IllegalArgumentException("Username muss mindestens 5 Zeichen lang sein!");
        }

        if (password.get() == null) {
            throw new IllegalArgumentException("Password muss angegeben werden.");
        }
        if (password.get().length() < 6) {
            throw new IllegalArgumentException("Password muss mindestens 6 Zeichen lang sein!");
        }


    }


    public static User checkExisting(String username, Statement statement) throws SQLException {
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

        if (rSet.next()) {
            user = new User();

            user.setUsername(rSet.getString("username"));
            user.setPassword(rSet.getString("password"));
            user.setUser_img(rSet.getString("user_img"));
            user.setUser_status(Status.Y);

        }
        return user;

    }

    public static void newToDB(String username, String password, Statement statement) throws SQLException, ParseException {

        User user = checkExisting(username, statement);

//killAndFill();
        if (user == null) {
            //     DateFormat formatter = new SimpleDateFormat("YYY-DD-MM");
            //   java.sql.Date now = new java.sql.Date();
            // Date myDate = formatter.parse(String.valueOf(LocalDate.now()));
            //Date sqlDate = new java.sql.Date(myDate.getTime());
            //System.out.println(myDate);
            //System.out.println(sqlDate);
            Date now = new Date();
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            String sqlDate = formatter.format(now);
            String date = sqlDate.toString();
            System.out.println(date);
            String sql
                    = " insert into users (user_id, "
                    + "username "
                    + ",status "
                    + ",last_login "
                    + ",password) values ('"
                    + createCode(statement) + "', '"
                    + username + "', '"
                    + Status.Y + "', '"
                    + date + "', '"
                    + password + "') ";

            System.out.println(sql);
            statement.executeUpdate(sql);
    user = new User();

            user.setUsername(username);
            user.setPassword(password);

            user.setUser_status(Status.Y);
            System.out.println("Registration sucessfull!!!");

        }
        else{
        }

    }

    public static String createCode(Statement statement) throws SQLException {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz0123456789";
        String userID = "#";
        for (int i = 0; i < 8; i++) {
            userID += CHAR_LOWER.toUpperCase().charAt((int) Math.floor(Math.random() * CHAR_LOWER.length()));
        }

        String sql
                = " select user_id "
                + "       ,username "
                + "       ,status "
                + "       ,user_img "
                + "       ,last_login "
                + "       ,password "
                + " from users "
                + " where user_id = '" + userID + "' ";


        ResultSet rSet = statement.executeQuery(sql);


        if (rSet.next()) {
            userID = createCode(statement);

        }
        return userID;

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

    public String getUser_id() {
        return user_id.get();
    }

    public StringProperty user_idProperty() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id.set(user_id);
    }

    public void setUser_status(String user_status) {
        this.user_status.set(user_status);
    }


    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username=" + username +
                ", password=" + password +
                ", user_img=" + user_img +
                ", user_status=" + user_status +
                ", last_login=" + last_login +
                '}';
    }
}
