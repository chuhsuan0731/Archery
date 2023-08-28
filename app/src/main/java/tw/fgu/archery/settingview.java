package tw.fgu.archery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class settingview extends AppCompatActivity {
    TextView tv_n;
    ImageButton ib_ad;
    ListView lv_set;
    String id,name,bow,ps1;

    private SQLiteDatabase mSQLiteDatabase = null;
    private static final String DATABASE_NAME = "archery.db";
    ArrayList<HashMap<String,Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingview);

        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id");
        name=bundle.getString("name");
        bow=bundle.getString("bow");
        ps1=bundle.getString("ps1");

        mSQLiteDatabase = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        list=new ArrayList<>();
        tv_n=findViewById(R.id.tv_n);
        ib_ad = findViewById(R.id.ib_ad);
        lv_set=findViewById(R.id.lv_set);
        tv_n.setText(name);

        ib_ad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(settingview.this, setting.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                bundle.putString("name",name);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
    }

    @Override
    protected  void onResume(){
        super.onResume();
        showlist();
    }
    void showlist() {
        list.clear();
        Cursor cursor = mSQLiteDatabase.rawQuery("Select * from record where user="+id, null);
        cursor.moveToFirst();
        int balance = 0;
        while (!cursor.isAfterLast()) {
            Log.d("record", cursor.getString(2) + "--" + cursor.getString(3) + "--" + cursor.getString(4));
            HashMap<String, Object> user_setting = new HashMap<>();

            user_setting.put("r_id", cursor.getString(0));
            user_setting.put("user", cursor.getString(1));
            user_setting.put("range", cursor.getString(2));
            user_setting.put("DATE", cursor.getString(3));
            user_setting.put("other", cursor.getString(4));
            balance = balance + cursor.getInt(3);

            list.add(user_setting);
            cursor.moveToNext();
        }
        SimpleAdapter adapter = new SimpleAdapter
                (settingview.this,
                        list,
                        R.layout.user_setting,
                        new String[]{"range", "DATE", "other"},
                        new int[]{R.id.tv_range, R.id.tv_date, R.id.tv_ps});

        lv_set.setAdapter(adapter);
        lv_set.setTextFilterEnabled(true);
        lv_set.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, Object> record = list.get(i);
                Intent intent = new Intent();
                intent.setClass(settingview.this, grade.class);
                Bundle bundle = new Bundle();
                bundle.putString("R_ID", (String) record.get("r_id"));
                bundle.putString("name", name);
                bundle.putString("range", (String) record.get("range"));
                bundle.putString("date", (String) record.get("DATE"));
                bundle.putString("ps", (String) record.get("other"));
                bundle.putString("bow",bow);
                bundle.putString("ps1",ps1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}