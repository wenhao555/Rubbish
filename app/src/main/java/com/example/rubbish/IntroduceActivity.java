package com.example.rubbish;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class IntroduceActivity extends AppCompatActivity
{
    private TextView textName, textContent;
    private int type = 0;
    private ImageView img;
    private ImageView logo_s;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        textName = findViewById(R.id.textName);
        img = findViewById(R.id.intro_img);
        mToolbar = findViewById(R.id.toolbar);


        logo_s = findViewById(R.id.logo_s);
        textContent = findViewById(R.id.textContent);
        type = getIntent().getIntExtra("name", 0);
        switch (type)
        {
            case 0://可回收
                textName.setText("可回收垃圾:");
                mToolbar.setTitle("可回收垃圾");
                Glide.with(this).load(R.mipmap.kehuishou).asBitmap().into(img);
                Glide.with(this).load(R.mipmap.logo).asBitmap().into(logo_s);
                textContent.setText("         可回收垃圾就是可以再生循环的垃圾。本身或材质可再利用的纸类、硬纸板、玻璃、塑料、金属、塑料包装，与这些材质有关的如：报纸、杂志、广告单及其它干净的纸类等皆可回收。");
                break;
            case 1://有害
                Glide.with(this).load(R.mipmap.youhailaji).asBitmap().into(img);
                Glide.with(this).load(R.mipmap.logo2).asBitmap().into(logo_s);
                textName.setText("有害垃圾:");
                mToolbar.setTitle("有害垃圾");
                textContent.setText("         可回收垃圾就是可以再生循环的垃圾。本身或材质可再利用的纸类、硬纸板、玻璃、塑料、金属、塑料包装，与这些材质有关的如：报纸、杂志、广告单及其它干净的纸类等皆可回收。");

                break;
            case 2://干
                Glide.with(this).load(R.mipmap.qitalaji).asBitmap().into(img);
                Glide.with(this).load(R.mipmap.logo3).asBitmap().into(logo_s);
                textName.setText("干垃圾:");
                mToolbar.setTitle("干垃圾");
                textContent.setText("         可回收垃圾就是可以再生循环的垃圾。本身或材质可再利用的纸类、硬纸板、玻璃、塑料、金属、塑料包装，与这些材质有关的如：报纸、杂志、广告单及其它干净的纸类等皆可回收。");

                break;
            case 3://湿
                Glide.with(this).load(R.mipmap.shilaji).asBitmap().into(img);
                Glide.with(this).load(R.mipmap.logo4).asBitmap().into(logo_s);
                textName.setText("湿垃圾:");
                mToolbar.setTitle("湿垃圾");
                textContent.setText("         可回收垃圾就是可以再生循环的垃圾。本身或材质可再利用的纸类、硬纸板、玻璃、塑料、金属、塑料包装，与这些材质有关的如：报纸、杂志、广告单及其它干净的纸类等皆可回收。");

                break;
        }
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
