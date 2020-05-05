package game_ui.server.entities;

public class GameSettings {
    private int roundSize;
    private int roundDuration;
    private char language;

    public GameSettings(int roundSize, int roundDuration, char language){
        this.roundSize = roundSize;
        this.roundDuration = roundDuration;
        this.language = language;
    }

    public int getRoundSize() {
        return roundSize;
    }

    public void setRoundSize(int roundSize) {
        this.roundSize = roundSize;
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public void setRoundDuration(int roundDuration) {
        this.roundDuration = roundDuration;
    }

    public char getLanguage() {
        return language;
    }

    public void setLanguage(char language) {
        this.language = language;
    }
}
