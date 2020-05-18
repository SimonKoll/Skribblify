package createCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateCode {
    public static String createIdCode(String type, Statement statement) throws SQLException {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz0123456789";
        String id = "#";
        for (int i = 0; i < 8; i++) {
            id += CHAR_LOWER.toUpperCase().charAt((int) Math.floor(Math.random() * CHAR_LOWER.length()));
        }

        switch (type) {
            case "friendship":
                String sqlFriendship = "select FRIENDSHIP_ID from FRIENDSHIP";
                ResultSet rSetFriendship = statement.executeQuery(sqlFriendship);
                while (rSetFriendship.next()) {
                    String checkId = rSetFriendship.getString("FRIENDSHIP_ID");
                    if (id.equals(checkId)) {
                        createIdCode("friendship", statement);
                    }
                    else {
                        id = checkId;
                    }
                }
                break;
            case "user":
                String sqlUser = "select USER_ID from USERS";
                ResultSet rSetUser = statement.executeQuery(sqlUser);
                while (rSetUser.next()) {
                    String checkId = rSetUser.getString("USER_ID");
                    if (id.equals(checkId)) {
                        createIdCode("user", statement);
                    }
                    else{
                        id = checkId;
                    }
                }
                break;


            case "lobby":

                break;

        }
        return id;
    }
}
