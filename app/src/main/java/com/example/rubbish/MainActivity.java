package com.example.rubbish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView banner;
    private ImageView input_voice;
    private TextView text1, text2, text3;
    private Context context;
    private EditText edit_name;
    private CardView kehuishou, youdulaji, ganlaji, shilaji;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        kehuishou = findViewById(R.id.kehuishou);
        youdulaji = findViewById(R.id.youhailaji);
        ganlaji = findViewById(R.id.ganlaji);
        shilaji = findViewById(R.id.shilaji);
        banner = findViewById(R.id.banner);
        edit_name = findViewById(R.id.edit_name);
        input_voice = findViewById(R.id.input_voice);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        input_voice.setOnClickListener(this);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        youdulaji.setOnClickListener(this);
        kehuishou.setOnClickListener(this);
        ganlaji.setOnClickListener(this);
        shilaji.setOnClickListener(this);
        edit_name.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {//按下搜索
                    startActivity(new Intent(context, ParticularActivity.class).putExtra("name", edit_name.getText().toString()));
                }
                return false;//返回true，保留软键盘。false，隐藏软键盘
            }
        });
        Glide.with(this).load(R.mipmap.banner).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(banner);
    }

    private RecognizerDialog mIatDialog;
    private SpeechRecognizer mIat;
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    private void initVoice()
    {
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=5d27fdf3");

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {

            if (MainActivity.this.getCurrentFocus().getWindowToken() != null)
            {
                imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
        return super.onTouchEvent(event);

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.input_voice:
                break;
            case R.id.text1:
                startActivity(new Intent(context, ParticularActivity.class).putExtra("name", text1.getText().toString()));
                break;
            case R.id.text2:
                startActivity(new Intent(context, ParticularActivity.class).putExtra("name", text2.getText().toString()));
                break;
            case R.id.text3:
                startActivity(new Intent(context, ParticularActivity.class).putExtra("name", text3.getText().toString()));
                break;
            case R.id.kehuishou:
                startActivity(new Intent(context, IntroduceActivity.class).putExtra("name", 0));
                break;
            case R.id.youhailaji:
                startActivity(new Intent(context, IntroduceActivity.class).putExtra("name", 1));
                break;
            case R.id.ganlaji:
                startActivity(new Intent(context, IntroduceActivity.class).putExtra("name", 2));
                break;
            case R.id.shilaji:
                startActivity(new Intent(context, IntroduceActivity.class).putExtra("name", 3));
                break;
        }
    }

    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    public void onBackPressed()
    {
        if (!mBackKeyPressed)
        {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask()
            {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run()
                {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else
        {//退出程序
            this.finish();
            System.exit(0);
        }
    }
}
