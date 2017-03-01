package edu.feicui.app.phone.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.base.util.PhoneBookIoUtil;
import edu.feicui.app.phone.base.util.PhoneBookUtil;

public class PhoneBookGridActivity extends BaseActivity {
    Context mCtx;
    GridView mGrd;
    PhoneBookUtil phoneBU;
    PhoneBookIoUtil phoneBIO;
    LayoutInflater layoutInflater;
    PhoneBookGridAdapter phoneBookGridAdapter;
    List<Map<String, String>> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCtx = this;
        phoneBU = new PhoneBookUtil(mCtx);
        phoneBIO = new PhoneBookIoUtil(mCtx);
        phoneBIO.PhoneBookRW();
        setContentView(R.layout.activity_phonebook_grid);
        initActionBar(this.getString(R.string.home_addressList), getResources().getDrawable(R.mipmap.ic_arrows_bar_left), null, null);
        mGrd = (GridView) findViewById(R.id.gv_list);
        lists = phoneBU.readDirectorys();
        phoneBookGridAdapter = new PhoneBookGridAdapter(mCtx);
        mGrd.setAdapter(phoneBookGridAdapter);
        mGrd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putString("name", lists.get(position).get("name"));
                bundle.putInt("position", position);
                startActivity(PhoneBookListActivity.class,bundle);

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

    class PhoneBookGridAdapter extends BaseAdapter {
        protected Context mCtx;

        public PhoneBookGridAdapter(Context context) {
            mCtx = context;
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return lists == null ? 0 : lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.phonebook_grid_style, null);
            switch (position % 3) {
                case 0:
                    convertView.setBackgroundResource(R.drawable.notification_information_progress_red);
                    break;
                case 1:
                    convertView.setBackgroundResource(R.drawable.notification_information_progress_green);
                    break;
                case 2:
                    convertView.setBackgroundResource(R.drawable.notification_information_progress_yellow);
                    break;
            }
            TextView tv_title = (TextView) convertView.findViewById(R.id.grid_txt);
            tv_title.setText(lists.get(position).get("name"));
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }
}