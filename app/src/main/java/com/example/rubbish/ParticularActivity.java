package com.example.rubbish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.rubbish.adapter.BaseRecyclerAdapter;
import com.example.rubbish.adapter.OnItemClickListener;
import com.example.rubbish.model.Newslist;
import com.example.rubbish.views.SwipeRecycleView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ParticularActivity extends AppCompatActivity
{
    private final String TAG = "详情";
    private ProgressDialog progressDialog;
    private String name = "";
    private BaseRecyclerAdapter adapter;
    private List<Newslist> newslists = new ArrayList<>();
    private SwipeRecycleView recycler_View;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular);
        recycler_View = findViewById(R.id.recycler_View);
        mToolbar = findViewById(R.id.toolbar);
        name = getIntent().getStringExtra("name");
        mToolbar.setTitle("分类详情");
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        recycler_View.setLayoutManager(new LinearLayoutManager(ParticularActivity.this));
        request(name);
        adapter = new BaseRecyclerAdapter()
        {
            @Override
            protected void onBindView(@NonNull BaseViewHolder holder, @NonNull int position)
            {
                Newslist rooms = newslists.get(position);
                TextView textView = holder.getView(R.id.item_name);
                TextView classes = holder.getView(R.id.item_classes);
                TextView jieshi = holder.getView(R.id.jieshi);
                TextView toufangtishi = holder.getView(R.id.toufangtishi);
                ImageView item_icon = holder.getView(R.id.item_icon);
                textView.setText(rooms.getName());
                if (rooms.getExplain().equals(""))
                    jieshi.setVisibility(View.GONE);
                jieshi.setText("解释：" + rooms.getExplain());
                toufangtishi.setText("包含物品：" + rooms.getContain());
                switch (rooms.getType())
                {
                    case 0:
                        Glide.with(ParticularActivity.this).load(R.mipmap.kehuishou).asBitmap().into(item_icon);
                        classes.setText("可回收垃圾");
                        break;
                    case 1:
                        Glide.with(ParticularActivity.this).load(R.mipmap.youhailaji).asBitmap().into(item_icon);
                        classes.setText("有害垃圾");
                        break;
                    case 2:
                        Glide.with(ParticularActivity.this).load(R.mipmap.qitalaji).asBitmap().into(item_icon);
                        classes.setText("干垃圾");
                        break;
                    case 3:
                        Glide.with(ParticularActivity.this).load(R.mipmap.shilaji).asBitmap().into(item_icon);
                        classes.setText("湿垃圾");
                        break;
                }
            }

            @Override
            protected int getLayoutResId(int position)
            {
                return R.layout.item_classes;
            }

            @Override
            public int getItemCount()
            {
                return newslists.size();
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(@NonNull int position)
            {
                Newslist rooms = newslists.get(position);

            }
        });
    }

    /**
     * 0可回收
     * 1有害
     * 2湿垃圾
     * 3干垃圾
     *
     * @param string
     */

    private void request(String string)
    {
        Log.e(TAG, name);
        progressDialog = new ProgressDialog(ParticularActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在加载中...　");
        progressDialog.show();
        OkHttpUtils.get()
                .url("http://api.tianapi.com/txapi/lajifenlei/")
                .addParams("key",
                        "e1760fbad7f69845c317b4e77edd1f07")
                .addParams("word", string)
                .addParams("num", 10 + "")
                .build()
                .connTimeOut(5000)
                .writeTimeOut(5000)
                .readTimeOut(5000)
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Log.e(TAG, "连接失败");
                    }

                    @Override
                    public void onResponse(String response, int id)
                    {
                        JsonObject resultJson = new JsonParser().parse(response).getAsJsonObject();
                        int code = resultJson.get("code").getAsInt();
                        if (code == 200)
                        {
                            newslists = new Gson().fromJson(resultJson.getAsJsonArray("newslist"), new TypeToken<ArrayList<Newslist>>()
                            {

                            }.getType());
                            Log.e(TAG, newslists.size() + "");
                            recycler_View.setAdapter(adapter);
                        } else
                        {
                            Log.e(TAG, code + "");
                        }
                        progressDialog.dismiss();
                    }
                });
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
