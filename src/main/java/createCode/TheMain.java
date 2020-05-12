package createCode;

public class TheMain
{
    public String createIdCode() {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz0123456789";
        String id = "#";
        for (int i = 0; i < 8; i++) {
            id += CHAR_LOWER.toUpperCase().charAt((int) Math.floor(Math.random() * CHAR_LOWER.length()));
        }
        return id;
    }
}
