package edu.feicui.app.phone.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.base.util.PhoneBookUtil;

public class PhoneBookListActivity extends BaseActivity {
    Context mCtx;
    int positionGrid = 1;
    PhoneBookUtil phoneBD;
    ListView mLst;

    SQLiteDatabase sqlDB;
    List<Map<String, String>> lists = null;

    protected void onCreate(Bundle savedInstanceState) {
        mCtx = this;
        phoneBD = new PhoneBookUtil(mCtx);
        super.onCreate(savedInstanceState);
        positionGrid = this.getIntent().getExtras().getInt("position");
        setContentView(R.layout.activity_phonebook_list);
        initActionBar(getIntent().getStringExtra("name"), getResources().getDrawable(R.mipmap.ic_arrows_bar_left), null, null);
        mLst = (ListView) findViewById(R.id.list_view);
        lists = phoneBD.readDirectorys(positionGrid);
        sqlDB = SQLiteDatabase.openOrCreateDatabase(mCtx.getFilesDir() + File.separator + "database" + File
                .separator + "commonnum.db", null);
        SimpleAdapter simAdp = new SimpleAdapter(this, lists, R.layout.phonebook_list_style, new
                String[]{"name",
                "number"}, new int[]{R.id.txt_name, R.id.txt_number});


        mLst.setAdapter(simAdp);
        mLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positionList, long id) {
                Intent intent = new Intent();
                Uri uri = Uri.parse("tel:" + lists.get(positionList).get("number"));
                intent.setAction(intent.ACTION_CALL);//设置操作为打电话
                intent.setData(uri);//通过Uri设置电话号码
                startActivity(intent);
//                intent.setAction(intent.ACTION_DIAL);//设置操作为打电话
//                intent.setData(Uri.parse("tel:110"));//通过Uri设置电话号码;
//                startActivity(intent);
            }
        });

    }

    @Override
    protected void onClickActionBar(int type) {
        switch (type) {
            case ACTION_LEFT_ICON:
                finish();
                break;
        }
    }
}
