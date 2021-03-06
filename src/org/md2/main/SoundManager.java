package org.md2.main;

import org.md2.common.Sound;
import org.md2.gameobjects.WorldObject;
import org.md2.gameobjects.entity.Entity;

import java.io.*;
import javax.sound.sampled.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class SoundManager {
    private AudioInputStream audioIn;
    private HashMap<Sound, Clip> clips;
    private HashSet<Soundentry> toplay;
    public static final int SOUNDWALK = 1;
    public static final int SOUNDBOWEQUIP = 2;
    public static final int SOUNDBOWTENSION = 3;
    public static final int SOUNDBOWRELEASE = 4;
    public static final int SOUNDAUA = 5;
    public static final int SOUNDSWORDSLASH = 6;
    public static final int SOUNDMUSIC = 7;
    public static final int SOUNDFIREBALL = 8;
    public static final int SOUNDBOOMERANG = 9;
    public static final int SOUNDPICKUP = 10;

    public static final int SOUNDSETTINGMUSIC = 100;
    public static final int SOUNDSETTINGCOMBAT = 101;
    public static final int SOUNDSETTINGMISC = 102;

    private float musicvolume = 0.8f;     // SOUNDSETTINGMUSIC
    private float combatvolume = 1f;    // SOUNDSETTINGCOMBAT
    private float miscvolume = 0.3f;      // SOUNDSETTINGMISC

    public float getModuleVolume(int i)
    {
        switch (i)
        {
            case 1:{return musicvolume;}
            case 2:{return combatvolume;}
            case 3:{return miscvolume;}
            default:{return 0.0f;}
        }
    }

    public SoundManager()
    {
        createSounds();
    }

    public void tick(ArrayList<WorldObject> worldObjects)
    {
        addEntitysounds(worldObjects);
        playSoundID(SOUNDMUSIC);
        for(Soundentry s: toplay)
        {
            executeClip(s.getClip(),s.getVolume());
        }
        toplay.clear();
    }

    public void playSoundID(int i)
    {
        switch(i)
        {
            case SOUNDWALK:{playSound(Sound.WALK, 0.05f, getModuleVolume(SOUNDSETTINGMISC));break;}
            case SOUNDBOWEQUIP:{playSound(Sound.BOWEQUIP, 0.2f, getModuleVolume(SOUNDSETTINGMISC));break;}
            case SOUNDBOWTENSION:{playSound(Sound.BOWTENSION, 0.2f, getModuleVolume(SOUNDSETTINGCOMBAT));break;}
            case SOUNDBOWRELEASE:{playSound(Sound.BOWRELEASE, 0.2f, getModuleVolume(SOUNDSETTINGCOMBAT));break;}
            case SOUNDAUA:{playSound(Sound.AUA, 0.3f, getModuleVolume(SOUNDSETTINGCOMBAT));break;}
            case SOUNDSWORDSLASH:{playSound(Sound.SWORDSLASH, 0.2f, getModuleVolume(SOUNDSETTINGCOMBAT));break;}
            case SOUNDMUSIC:{playSound(Sound.MUSIC, 0.05f, getModuleVolume(SOUNDSETTINGMUSIC));break;}
            case SOUNDFIREBALL:{playSound(Sound.FIREBALL, 0.2f, getModuleVolume(SOUNDSETTINGCOMBAT));break;}
            case SOUNDBOOMERANG:{playSound(Sound.BOOMERANG, 0.2f, getModuleVolume(SOUNDSETTINGCOMBAT));break;}
            case SOUNDPICKUP:{playSound(Sound.PICKUP, 0.2f, getModuleVolume(SOUNDSETTINGMISC));break;}
        }
    }

    private boolean playSound(Sound sound) {return playClip(clips.get(sound));}

    private boolean playSound(Sound sound, float volume, float modulevolume) {return playClip(clips.get(sound), volume, modulevolume); }

    private boolean isPlaying(Clip c)
    {
        return c.isRunning();
    }

    private void createSounds()
    {
        clips = new HashMap<Sound, Clip>();
        for(Sound s: Sound.values())
        {
            loadSound(s);
        }
        toplay = new HashSet<Soundentry>();
    }

    private boolean playClip(Clip c)
    {
        return playClip(c, 1.0f, 1.0f);
    }

    private boolean playClip(Clip c, float volume, float modulevolume)
    {
        toplay.add(new Soundentry(c, volume, modulevolume));
        return true;
    }

    private void addEntitysounds(ArrayList<WorldObject> worldObjects)
    {
        for(WorldObject wo: worldObjects)
            if (wo instanceof Entity) {
                if (((Entity) wo).isMoving())
                    if (((Entity) wo).getWalkingSound() != null)
                        playSoundID(SOUNDWALK);
            }
    }

    private void loadSound(Sound s)
    {
        Clip clip = null;
        InputStream in = SoundManager.class.getResourceAsStream("/" + s.getSoundName() + ".wav");
        InputStream bufferedIn = new BufferedInputStream(in);
        try
        {
            audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            clip.open(audioIn);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clips.put(s, clip);
    }

    private boolean executeClip(Clip c)
    {
        return executeClip(c,1.0f);
    }

    private boolean executeClip(Clip c, float volume)
    {
        if(c != null)
        {
            if(!isPlaying(c))
            {
                c.setFramePosition(0);
                setVolume(c,volume);
                c.start();
            }

            return true;
        }
        else
            return false;
    }

    private void setVolume(Clip c, float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    private class Soundentry{
        private Clip clip;
        private float volume;
        private float modulevolume;

        public Soundentry(Clip clip, float volume, float modulevolume)
        {
            this.clip = clip;
            this.volume = volume;
            this.modulevolume = modulevolume;
        }

        public Clip getClip()
        {
            return clip;
        }

        public float getVolume()
        {
            return volume;
        }

        public float getModulevolume()
        {
            return modulevolume;
        }

        public void setVolume(float volume)
        {
            this.volume = volume;
        }
    }

}

