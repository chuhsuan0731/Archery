package tw.fgu.archery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class role extends AppCompatActivity {

    ImageButton im_add;
    ListView lv_role;

    private SQLiteDatabase mSQLiteDatabase = null;
    private static final String DATABASE_NAME = "archery.db";
    ArrayList<HashMap<String,Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        mSQLiteDatabase = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        list=new ArrayList<>();

        lv_role=findViewById(R.id.lv_role);

        im_add = findViewById(R.id.im_add);
        im_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent MainActivity = new Intent(role.this, choosebow.class);
                startActivity(MainActivity);

            }
        });
    }

    @Override
    protected  void onResume(){
        super.onResume();
        showlist();
    }

    public void showlist()
    {
        list.clear();
        Cursor cursor = mSQLiteDatabase.rawQuery("Select * from user where 1", null);
        cursor.moveToFirst();
        int balance=0;
        while (!cursor.isAfterLast())
        {
            Log.d("user",cursor.getString(1)+"--"+cursor.getString(2)+"--"+cursor.getString(3));
            HashMap<String,Object> user_profile=new HashMap<>();
            user_profile.put("u_id",cursor.getString(0));
            user_profile.put("U",cursor.getString(1));
            user_profile.put("BOW",cursor.getString(2));
            user_profile.put("ps",cursor.getString(3));
            balance=balance+cursor.getInt(3);

            list.add(user_profile);
            cursor.moveToNext();
        }
        SimpleAdapter adapter =new SimpleAdapter
                (role.this,
                list,
                R.layout.user_profile,
                new String[]{"U","BOW","ps"},
                new int[]{R.id.tv_name,R.id.tv_bow,R.id.tv_p});

        lv_role.setAdapter(adapter);
        lv_role.setTextFilterEnabled(true);
        lv_role.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                HashMap<String,Object> user=list.get(i);
                Intent intent = new Intent();
                intent.setClass(role.this,settingview.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",(String)user.get("u_id"));
                bundle.putString("name",(String) user.get("U"));
                bundle.putString("bow",(String) user.get("BOW"));
                bundle.putString("ps1",(String) user.get("ps"));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

}



