package com.example.pr24shterk; // Убедитесь, что здесь ваш пакет

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCatSound, mChickenSound, mCowSound, mDogSound, mDuckSound, mSheepSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация кнопок и установка слушателя
        ImageButton cowImageButton = findViewById(R.id.image_cow);
        cowImageButton.setOnClickListener(this);

        ImageButton chickenImageButton = findViewById(R.id.image_chicken);
        chickenImageButton.setOnClickListener(this);

        ImageButton catImageButton = findViewById(R.id.image_cat);
        catImageButton.setOnClickListener(this);

        ImageButton duckImageButton = findViewById(R.id.image_duck);
        duckImageButton.setOnClickListener(this);

        ImageButton sheepImageButton = findViewById(R.id.image_sheep);
        sheepImageButton.setOnClickListener(this);

        ImageButton dogImageButton = findViewById(R.id.image_dog);
        dogImageButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Создаем SoundPool при возобновлении работы приложения
        createNewSoundPool();
        mAssetManager = getAssets();

        // Загружаем звуки
        mCatSound = loadSound("cat.mp3");
        mChickenSound = loadSound("chicken.mp3");
        mCowSound = loadSound("cow.mp3");
        mDogSound = loadSound("dog.mp3");
        mDuckSound = loadSound("duck.mp3");
        mSheepSound = loadSound("sheep.mp3");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Освобождаем ресурсы SoundPool, чтобы не забивать память
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_cow) {
            playSound(mCowSound);
        } else if (id == R.id.image_chicken) {
            playSound(mChickenSound);
        } else if (id == R.id.image_cat) {
            playSound(mCatSound);
        } else if (id == R.id.image_duck) {
            playSound(mDuckSound);
        } else if (id == R.id.image_sheep) {
            playSound(mSheepSound);
        } else if (id == R.id.image_dog) {
            playSound(mDogSound);
        }
    }

    private void createNewSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        } else {
            createOldSoundPool();
        }
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }
}