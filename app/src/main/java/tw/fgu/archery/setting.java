package tw.fgu.archery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class setting extends AppCompatActivity {

    Button bu_date,bt_finish;
    SeekBar sb_m;
    TextView tv_m,st_user;
    EditText et_ps;
    String id,name;

    private SQLiteDatabase mSQLiteDatabase= null;
    private static final String DATABASE_NAME = "archery.db";
    ArrayList<HashMap<String,Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id");
        name=bundle.getString("name");

        mSQLiteDatabase = this.openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
        sb_m=findViewById(R.id.sb_m);
        tv_m=findViewById(R.id.tv_m);
        et_ps=findViewById(R.id.et_ps);
        st_user=findViewById(R.id.st_user);

        st_user.setText(name);

        meter();
        date();


        bt_finish=findViewById(R.id.bt_finish);
        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer range = sb_m.getProgress();
                String DATE = bu_date.getText().toString();
                String other = et_ps.getText().toString();
                mSQLiteDatabase.execSQL("INSERT INTO record(user, range,DATE,other) values ('"+id+"','"+range+"','"+DATE+"','"+other+"')");

                finish();

            }
        });

    }



    void date(){
        bu_date=findViewById(R.id.bu_date);
        bu_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(setting.this);
                final DatePicker picker=new DatePicker(setting.this);
                picker.setCalendarViewShown(false);

                builder.setTitle("Create Year");
                builder.setView(picker);
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year=picker.getYear();
                        int month=picker.getMonth()+1;
                        int day=picker.getDayOfMonth();

                        String mString= Integer.toString(month);
                        if(month<10)
                            mString="0"+mString;

                        String dString=Integer.toString(day);
                        if(day<10)
                            dString="0"+dString;
                        bu_date.setText(year+"-"+mString+"-"+dString);
                    }
                });
                builder.show();

            }
        });


    }
    void meter()
    {
        sb_m.setMax(130);
        sb_m.setProgress(1);
        tv_m.setText("目前距離(公尺)：" +sb_m.getProgress() + "  /  最大距離(公尺)："+sb_m.getMax());
        sb_m.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_m.setText("目前距離(公尺)：" + progress + "  /  最大距離(公尺)："+sb_m.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(setting.this, "距離選擇" , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(setting.this, "結束"  , Toast.LENGTH_SHORT).show();

            }
        });
    }


}

