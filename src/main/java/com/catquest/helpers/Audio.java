package com.catquest.helpers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.net.URL;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Audio {
    private static final String MEDIA_PATH = "/com/catquest/medias/";
    private static final String AUDIO_FILE_PATH = "src/main/resources/com/catquest/audio.json";
    public static int musicVolume = 50;
    public static int effectsVolume = 50;
    private static final int defaultVolume = 50;
    private static MediaPlayer backgroundMediaPlayer = null;
    public static boolean EXECUTED_FROM_TESTS = false;

    public static void initialize() {
        File file = new File(AUDIO_FILE_PATH);
        if (!file.exists()) {
            Audio.save();
        }
        try {
            JSONObject audio = Json.parse(AUDIO_FILE_PATH);
            Audio.musicVolume = ((Long) audio.get("musicVolume")).intValue();
            Audio.effectsVolume = ((Long) audio.get("effectsVolume")).intValue();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!EXECUTED_FROM_TESTS) {
            startBackgroundMusic();
        }
    }

    private static void startBackgroundMusic() {
        URL file = Audio.class.getResource(MEDIA_PATH + "darker-cave-background.mp3");
        if (file != null) {
            Media media = new Media(file.toString());
            backgroundMediaPlayer = new MediaPlayer(media);
            backgroundMediaPlayer.play();
            backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            setMusicVolume();
        }
    }

    public static void buttonSoundEffect() {
        URL file = Audio.class.getResource(MEDIA_PATH + "button.mp3");
        if (file != null) {
            Media media = new Media(file.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(effectsVolume / 100.0);
            mediaPlayer.play();
        }
    }

    public static void startCatAttackSoundEffect() {
        URL file = Audio.class.getResource(MEDIA_PATH + "angry-cat.mp3");
        if (file != null) {
            Media media = new Media(file.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            double volume = effectsVolume / 100.0;
            if (effectsVolume > 0) {
                volume = volume / 2;
            }
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
        }
    }

    public static void startGameSoundEffect() {
        URL file = Audio.class.getResource(MEDIA_PATH + "start.mp3");
        if (file != null) {
            Media media = new Media(file.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(effectsVolume / 100.0);
            mediaPlayer.play();
        }
    }

    public static void errorSoundEffect() {
        URL file = Audio.class.getResource(MEDIA_PATH + "error.mp3");
        if (file != null) {
            Media media = new Media(file.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(effectsVolume / 100.0);
            mediaPlayer.play();
        }
    }

    public static void setMusicVolume() {
        if (backgroundMediaPlayer != null) {
            double volume = musicVolume / 100.0;
            backgroundMediaPlayer.setVolume(volume);
        }
    }

    public static void save() {
        HashMap<String, Object> json = new HashMap<>();
        json.put("musicVolume", musicVolume);
        json.put("effectsVolume", effectsVolume);
        setMusicVolume();
        try (FileWriter writer = new FileWriter(AUDIO_FILE_PATH)) {
            writer.write(new JSONObject(json).toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reset the audio settings to their default values
     */
    public static void reset() {
        Audio.musicVolume = defaultVolume;
        Audio.effectsVolume = defaultVolume;
        Audio.save();
    }
}
