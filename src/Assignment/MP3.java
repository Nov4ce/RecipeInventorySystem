package Assignment;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

abstract class Playlist {
    abstract void play();

    abstract void stop();
}

class Music1 extends Playlist {
    private Clip clip;

    void play() {
        File file = new File("Eiffel 65 - Blue (Da Ba Dee).wav");
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}

class Music2 extends Playlist {
    private Clip clip;

    void play() {
        File file = new File("Pokemon RubySapphireEmerald- Littleroot Town.wav");
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}

class Music3 extends Playlist {
    private Clip clip;

    void play() {
        File file = new File("Rick Astley - Never Gonna Give You Up (Official Music Video).wav");
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}

public class MP3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Playlist currentSong = null;

        do {
            System.out.println("PLAYLIST \n" + "1. Blues - Eiffel 65\n" + "2. Littleroot Town \n" + "3. ???? ????? \n" + "4. Exit \n" + "Enter your choice: ");
            choice = scanner.nextInt();

            if (currentSong != null) {
                currentSong.stop();
            }

            switch (choice) {
                case 1:
                    currentSong = new Music1();
                    currentSong.play();
                    break;
                case 2:
                    currentSong = new Music2();
                    currentSong.play();
                    break;
                case 3:
                    currentSong = new Music3();
                    currentSong.play();
                    break;
                case 4:
                    System.exit(0);
                    break;
            }
        } while (choice < 4);
    }
}
