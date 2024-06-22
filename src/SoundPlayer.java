import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private Clip clip;
    private boolean playing; // 재생 상태를 저장할 변수

    public SoundPlayer(String filePath) {
        try {
            File file = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        playing = false; // 초기에는 재생 상태가 아님
    }

    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            playing = true; // 재생 상태로 변경
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            playing = false; // 정지 상태로 변경
        }
    }

    public boolean isPlaying() {
        return playing; // 재생 상태 반환
    }
}
