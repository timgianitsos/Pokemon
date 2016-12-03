import java.io.File; 
import java.io.IOException; 
import javax.sound.sampled.AudioFormat; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.DataLine; 
import javax.sound.sampled.FloatControl; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.SourceDataLine; 
import javax.sound.sampled.UnsupportedAudioFileException; 
 
public class AePlayWave extends Thread { 
    
    public static void main(String[] args) {
        new AePlayWave("cries/CHARIZARD.wav").start();
    }

    public static final int DEFAULT_BUFFER_SIZE = 524288; // 128Kb 
    private String filename;
    private Position curPosition;
    private final int EXTERNAL_BUFFER_SIZE; 
    private volatile boolean exit = false;
 
    enum Position { 
        LEFT, RIGHT, NORMAL
    };
 
    public AePlayWave(String wavfile) { 
        filename = wavfile;
        curPosition = Position.NORMAL;
        EXTERNAL_BUFFER_SIZE = DEFAULT_BUFFER_SIZE;
    } 
 
    public AePlayWave(String wavfile, Position p) { 
        filename = wavfile;
        curPosition = p;
        EXTERNAL_BUFFER_SIZE = DEFAULT_BUFFER_SIZE;
    }

    public AePlayWave(String wavfile, int size) { 
        filename = wavfile;
        curPosition = Position.NORMAL;
        EXTERNAL_BUFFER_SIZE = size;
    }
 
    public void run() { 
 
        File soundFile = new File(filename);
        if (!soundFile.exists()) { 
            System.err.println("Wave file not found: " + filename);
            return;
        } 
 
        AudioInputStream audioInputStream = null;
        try { 
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e1) { 
            e1.printStackTrace();
            return;
        } catch (IOException e1) { 
            e1.printStackTrace();
            return;
        } 
 
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
        try { 
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) { 
            e.printStackTrace();
            return;
        } catch (Exception e) { 
            e.printStackTrace();
            return;
        } 
 
        if (auline.isControlSupported(FloatControl.Type.PAN)) { 
            FloatControl pan = (FloatControl) auline
                    .getControl(FloatControl.Type.PAN);
            if (curPosition == Position.RIGHT) 
                pan.setValue(1.0f);
            else if (curPosition == Position.LEFT) 
                pan.setValue(-1.0f);
        } 
 
        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
        try { 
            int startRead = 0;
            while (nBytesRead != -1 && !exit) { 
                nBytesRead = audioInputStream.read(abData, startRead, startRead + 2048);
                if (nBytesRead >= 0) 
                    auline.write(abData, startRead, nBytesRead);
                startRead += nBytesRead;
            } 
        } catch (IOException e) { 
            e.printStackTrace();
            return;
        } finally { 
            auline.drain();
            auline.close();
        } 
 
    }

    public void quit() {
        exit = true;
    }
} 
