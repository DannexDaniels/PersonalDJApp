package com.dannextech.apps.personaldjapp;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DDannex" ;
    private static final int REQUEST_CODE = 1 ;

    ImageView ivDeck1,ivDeck2,ivPlay1,ivPlay2,ivStop1,ivStop2;
    Button btEffect1,btEffect2,btEffect3,btEffect4,btEffect5;
    TextView tvName1, tvName2;

    private volatile boolean rotate1 = true;
    private volatile boolean rotate2 = true;

    Timer timer = new Timer();

    Uri audioFileUri;

    MediaPlayer player1,player2,effects;

    @Override
    protected void onStart() {
        super.onStart();
        if (player2.isPlaying())
            rotate2=true;

        if(player1.isPlaying())
            rotate1=true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivDeck1 = (ImageView) findViewById(R.id.ivDeck1);
        ivDeck2 = (ImageView) findViewById(R.id.ivDeck2);
        ivPlay1 = (ImageView) findViewById(R.id.ivPlay1);
        ivPlay2 = (ImageView) findViewById(R.id.ivPlay2);
        ivStop1 = (ImageView) findViewById(R.id.ivStop1);
        ivStop2 = (ImageView) findViewById(R.id.ivStop2);

        btEffect1 = (Button) findViewById(R.id.btEffect1);
        btEffect2 = (Button) findViewById(R.id.btEffect2);
        btEffect3 = (Button) findViewById(R.id.btEffect3);
        btEffect4 = (Button) findViewById(R.id.btEffect4);
        btEffect5 = (Button) findViewById(R.id.btEffect5);

        tvName1 = (TextView) findViewById(R.id.tvName1);
        tvName2 = (TextView) findViewById(R.id.tvName2);

        player1 = new MediaPlayer();
        //depreciated
        player1.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player2 = new MediaPlayer();
        player2.setAudioStreamType(AudioManager.STREAM_MUSIC);

        ivDeck2.setOnClickListener(this);
        ivDeck1.setOnClickListener(this);
        ivStop1.setOnClickListener(this);
        ivStop2.setOnClickListener(this);
        ivPlay2.setOnClickListener(this);
        ivPlay1.setOnClickListener(this);
        btEffect1.setOnClickListener(this);
        btEffect2.setOnClickListener(this);
        btEffect3.setOnClickListener(this);
        btEffect4.setOnClickListener(this);
        btEffect5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivDeck1:
                selectMusic();
                //tvName1.setText(setMusicName());
                break;
            case R.id.ivDeck2:
                selectMusic();
                //Toast.makeText(v.getContext(),"Deck 1 Stopped",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivPlay1:
                rotate1 = true;
                rotateDeck();
                try {
                    playMusic1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(v.getContext(),"Deck 1 Playing",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivPlay2:
                rotate2 = true;
                rotateDeck2();
                try{
                    playMusic2();
                }catch (IOException e){
                    e.printStackTrace();
                }
                Toast.makeText(v.getContext(),"Deck 2 playing",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivStop1:
                rotate1 = false;
                try {
                    stopMusic1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(v.getContext(),"Deck 1 Stopped",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivStop2:
                rotate2 = false;
                try {
                    stopMusic2();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(v.getContext(),"Deck 2 Stopped",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btEffect1:
                effects = MediaPlayer.create(this,R.raw.effect2);
                effects.start();
                Toast.makeText(v.getContext(),"Effects Not Added",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btEffect2:
                effects = MediaPlayer.create(this,R.raw.effect3);
                effects.start();
                Toast.makeText(v.getContext(),"Effects Not Added",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btEffect3:
                effects = MediaPlayer.create(this,R.raw.effect4);
                effects.start();
                Toast.makeText(v.getContext(),"Effects Not Added",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btEffect4:
                effects = MediaPlayer.create(this,R.raw.effect1);
                effects.start();
                Toast.makeText(v.getContext(),"Effects Not Added",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btEffect5:
                Toast.makeText(v.getContext(),"Effects Not Added",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void stopMusic2() throws IOException {
        if (player2.isPlaying()){
            player2.reset();
//            player2.prepare();
            player2.stop();
            player2.release();
            player2 = null;
        }

    }

    private void playMusic2() throws IOException {
        player2.setDataSource(getApplicationContext(),audioFileUri);
        player2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player2.start();
            }
        });
        player2.prepareAsync();
    }

    private String setMusicName() {
        return audioFileUri.getPath().toString();
    }

    private void stopMusic1() throws IOException {
        if (player1.isPlaying()){
            player1.reset();
           // player1.prepare();
            player1.stop();
            player1.release();
           player1 = null;
        }

    }

    private void playMusic1() throws IOException {
        player1.setDataSource(getApplicationContext(),audioFileUri);
        player1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player1.start();
            }
        });
        player1.prepareAsync();
    }



    private void selectMusic() {
        rotate1 = false;
        rotate2 = false;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mpeg");
        startActivityForResult(Intent.createChooser(intent,"Select a Song"),REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data!= null && data.getData()!=null){
                audioFileUri = data.getData();
                tvName1.setText(audioFileUri.getPath().toString() );
            }
        }
    }

    private void rotateDeck() {
        final int[] degree = {0};
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (rotate1){
                    ivDeck1.setRotation(degree[0]);
                    Log.e(TAG, "run: Deck 1 running?" +rotate1);
                    degree[0]+=20;
                }
            }
        },150,150);
    }

    private void rotateDeck2() {
        final int[] degree = {0};
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (rotate2){
                    ivDeck2.setRotation(degree[0]);
                    Log.e(TAG, "run: Deck 2 running?" +rotate2);
                    degree[0]+=20;
                }
            }
        },150,150);
    }

}
