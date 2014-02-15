package tetris.model;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private float volume=0;
    private Clip sound;
    private String soundPath;
    private boolean silence=false;

    public String getSoundPath() {
        return soundPath;
    }

    public Sound(String soundPath) {
        this.soundPath = soundPath;
        try {
            sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(new File(soundPath)));
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
        }
    }

    public void play() {
        if(!silence) sound.start();
    }

    public void loop() {
        if(!silence) sound.loop(Integer.MAX_VALUE);
    }

    public void pause() {
        sound.stop();
    }

    public void reload() {
        sound.close();
        try {
            sound.open(AudioSystem.getAudioInputStream(new File(soundPath)));
            if(!silence) sound.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
        }

    }

    public void changeSound(String soundPath) {
        if (!this.soundPath.equals(soundPath)) {
            sound.close();
            try {
                sound.open(AudioSystem.getAudioInputStream(new File(soundPath)));
                this.soundPath = soundPath;
                if(!silence) sound.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            }
        }
    }

    public boolean isSilence() {
        return silence;
    }
    
    public void silence(){
        if(silence){
            silence=false;
            sound.loop(5000);
        }else{
            silence =true;
            sound.stop();
        }
    }
    
    public void modifyVolume(float value){
        FloatControl gainControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
        if(volume < 0 || value < 0)volume += value;
        gainControl.setValue((float)volume);
    }
}
