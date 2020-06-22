package createLobby;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class MusicPlayer  {
    private String path = "./src/main/java/createLobby/music.mp3";
    private Media hit = new Media(Paths.get(path).toUri().toString());
    private AudioClip mediaPlayer = new AudioClip(hit.getSource());

    public MusicPlayer() {

    }

    public void play() {

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }


    public void stop() {
        mediaPlayer.stop();
    }


    public void mute() {
        mediaPlayer.setVolume(0);
        mediaPlayer.stop();
    }

    public void unmute()  {
        mediaPlayer.setVolume(1);
        mediaPlayer.play();
    }

}
