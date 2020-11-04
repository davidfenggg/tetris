
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This is an example program that demonstrates how to play back an audio file
 * using the Clip in Java Sound API.
 * @author www.codejava.net
 *
 * Modified code to fit Tetris application better
 * @author David Feng
 *
 * @version 3.17.18
 */
public class AudioPlayer{

  Clip audioClip;

  /**
   * Play a given audio file.
   *
   * @param audioFilePath Path of the audio file.
   */
  void play(String audioFilePath) {
    File audioFile = new File(audioFilePath);

    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

      AudioFormat format = audioStream.getFormat();

      DataLine.Info info = new DataLine.Info(Clip.class, format);

      audioClip = (Clip) AudioSystem.getLine(info);

      audioClip.open(audioStream);

      audioClip.loop(10);


    } catch (UnsupportedAudioFileException ex) {
      System.out.println("The specified audio file is not supported.");
      ex.printStackTrace();
    } catch (LineUnavailableException ex) {
      System.out.println("Audio line for playing back is unavailable.");
      ex.printStackTrace();
    } catch (IOException ex) {
      System.out.println("Error playing the audio file.");
      ex.printStackTrace();
    }

  }

  public static void main(String[] args) {
    String audioFilePath = "rickroll.au";
    AudioPlayer player = new AudioPlayer();
    player.play(audioFilePath);
  }
}
