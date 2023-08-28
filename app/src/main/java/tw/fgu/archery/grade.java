package tw.fgu.archery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Attributes;

import static tw.fgu.archery.R.id.dialog_button;
import static tw.fgu.archery.R.id.ib_eig;
import static tw.fgu.archery.R.id.ib_fiv;
import static tw.fgu.archery.R.id.ib_fou;
import static tw.fgu.archery.R.id.ib_miss;
import static tw.fgu.archery.R.id.ib_nin;
import static tw.fgu.archery.R.id.ib_one;
import static tw.fgu.archery.R.id.ib_sev;
import static tw.fgu.archery.R.id.ib_six;
import static tw.fgu.archery.R.id.ib_ten;
import static tw.fgu.archery.R.id.ib_thr;
import static tw.fgu.archery.R.id.ib_two;
import static tw.fgu.archery.R.id.ib_x;

public class grade extends AppCompatActivity {

    TextView tv_role,tv_date,tv_b,tv_range,amount,tv_ps,tv_ps1,tv_ar,tv_all;
    Button bt_one,bt_two,bt_thr,bt_for,bt_fiv,bt_six,bt_total;
    String R_ID,name,range,date,ps,bow,ps1;
    ImageButton b_add;
    ListView lv_score;

    private SQLiteDatabase mSQLiteDatabase = null;
    private static final String DATABASE_NAME = "archery.db";
    ArrayList<HashMap<String,Object>> list;


    private static final int[] id = {R.id.bt_one,R.id.bt_two,R.id.bt_thr,R.id.bt_for,R.id.bt_fiv,R.id.bt_six};
    private Button[] button=new Button[id.length];
    private static final int[] ids = {R.id.ib_miss,R.id.ib_one,R.id.ib_two,R.id.ib_thr,R.id.ib_fou,R.id.ib_fiv,
                                      R.id.ib_six,R.id.ib_sev,R.id.ib_eig,R.id.ib_nin,R.id.ib_ten,R.id.ib_x};
    private ImageButton[] b=new ImageButton[ids.length];
    int i,j;
    String[] score= {"0","0","0","0","0","0"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        listener();
        mSQLiteDatabase = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        list=new ArrayList<>();

        Bundle bundle=getIntent().getExtras();
        R_ID=bundle.getString("R_ID");
        date=bundle.getString("date");
        range=bundle.getString("range");
        ps=bundle.getString("ps");
        bow=bundle.getString("bow");
        name=bundle.getString("name");
        ps1=bundle.getString("ps1");

        showlist();

        tv_date.setText(date);
        tv_range.setText(range);
        tv_ps.setText(ps);
        tv_b.setText(bow);
        tv_role.setText(name);
        tv_ps1.setText(ps1);

        for(i =0 ; i<id.length;i++)
        {
            final int m=i;
            button[i]=findViewById(id[i]);
            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final LayoutInflater inflater=LayoutInflater.from(grade.this);
                    final View v=inflater.inflate(R.layout.score,null);
                    AlertDialog.Builder alert= new AlertDialog.Builder(grade.this);
                    final AlertDialog ad=alert.setTitle("請選分數").setView(v).show();
                    for(j =0; j<ids.length ; j++)
                    {
                        b[j]=v.findViewById(ids[j]);
                        b[j].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.d("Click", i+"");
                                switch (view.getId())
                                {
                                    case ib_miss:
                                        button[m].setText("M");
                                        score[m]="M";
                                        break;
                                    case ib_one:
                                        button[m].setText("1");
                                        score[m]="1";
                                        break;
                                    case ib_two:
                                        button[m].setText("2");
                                        score[m]="2";
                                        break;
                                    case ib_thr:
                                        button[m].setText("3");
                                        score[m]="3";
                                         break;
                                    case ib_fou:
                                        button[m].setText("4");
                                        score[m]="4";
                                        break;
                                    case ib_fiv:
                                        button[m].setText("5");
                                        score[m]="5";
                                        break;
                                    case ib_six:
                                        button[m].setText("6");
                                        score[m]="6";
                                        break;
                                    case ib_sev:
                                        button[m].setText("7");
                                        score[m]="7";
                                        break;
                                    case ib_eig:
                                        button[m].setText("8");
                                        score[m]="8";
                                        break;
                                    case ib_nin:
                                        button[m].setText("9");
                                        score[m]="9";
                                        break;
                                    case ib_ten:
                                        button[m].setText("10");
                                        score[m]="10";
                                        break;
                                    case ib_x:
                                        button[m].setText("X");
                                        score[m]="X";
                                        break;
                                }
                                int t=totalScore();
                                ad.dismiss();

                            }
                        });
                    }
                }
            });
        }

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String one = button[0].getText().toString();
                String two = button[1].getText().toString();
                String thr = button[2].getText().toString();
                String four = button[3].getText().toString();
                String five = button[4].getText().toString();
                String six = button[5].getText().toString();
                mSQLiteDatabase.execSQL("INSERT INTO grade(r_id,one,two,thr,four,five,six) values ('"+R_ID+"','"+one+"','"+two+"','"+thr+"','"+four+"','"+five+"','"+six+"')");
                showlist();
                for(int i=0;i<6;i++)
                    button[i].setText("");
                bt_total.setText("0");
            }
        });

    }

    int totalScore()
    {
        int total=0;
        for(int i=0;i<6;i++)
        {
            switch(score[i])
            {
                case "M":
                case "0":
                    total=total+0;
                    break;
                case "1":
                    total=total+1;
                    break;
                case "2":
                    total=total+2;
                    break;
                case "3":
                    total=total+3;
                    break;
                case "4":
                    total=total+4;
                    break;
                case "5":
                    total=total+5;
                    break;
                case "6":
                    total=total+6;
                    break;
                case "7":
                    total=total+7;
                    break;
                case "8":
                    total=total+8;
                    break;
                case "9":
                    total=total+9;
                    break;
                case "10":
                case "X":
                    total=total+10;
                    break;
            }
        }
        bt_total.setText(Integer.toString(total));
        return total;
    }

    void showlist()
    {
        list.clear();
        Cursor cursor = mSQLiteDatabase.rawQuery("Select * from grade where r_id="+R_ID+" order by g_id DESC", null);
        cursor.moveToFirst();
        int balance = 0;
        int allSum=0;

        while (!cursor.isAfterLast()) {
            Log.d("grade", cursor.getString(2) + "--" + cursor.getString(3) + "--" + cursor.getString(4)+"--"+cursor.getString(5) + "--" + cursor.getString(6) + "--" + cursor.getString(7));
            HashMap<String, Object> grade_score = new HashMap<>();
            int total=0;
            grade_score.put("one", cursor.getString(2));
            total=total+scoreValue(cursor.getString(2));
            grade_score.put("two", cursor.getString(3));
            total=total+scoreValue(cursor.getString(3));
            grade_score.put("thr", cursor.getString(4));
            total=total+scoreValue(cursor.getString(4));
            grade_score.put("four", cursor.getString(5));
            total=total+scoreValue(cursor.getString(5));
            grade_score.put("five", cursor.getString(6));
            total=total+scoreValue(cursor.getString(6));
            grade_score.put("six", cursor.getString(7));
            total=total+scoreValue(cursor.getString(7));

            grade_score.put("total",Integer.toString(total));
            allSum=allSum+total;

            balance = balance + cursor.getInt(6);

            list.add(grade_score);
            cursor.moveToNext();
        }
        int count=cursor.getCount();
        double average=Math.round((double)allSum/count*100)/100.0;
        tv_ar.setText(Double.toString(average));
        tv_all.setText(Integer.toString(allSum));

        SimpleAdapter adapter = new SimpleAdapter
                (grade.this,
                        list,
                        R.layout.grade_score,
                        new String[]{"one", "two", "thr","four","five","six","total"},
                        new int[]{R.id.tv1, R.id.tv2, R.id.tv3,R.id.tv4,R.id.tv5, R.id.tv6,R.id.tvtot});
        lv_score.setAdapter(adapter);


    }

    int scoreValue(String v)
    {
        if(v.equals("M") || v.equals("0"))
            return 0;
        else if(v.equals("1"))
                return 1;
        else if(v.equals("2"))
            return 2;
        else if(v.equals("3"))
            return 3;
        else if(v.equals("4"))
            return 4;
        else if(v.equals("5"))
            return  5;
        else if(v.equals("6"))
            return 6;
        else if(v.equals("7"))
            return 7;
        else if(v.equals("8"))
            return 8;
        else if(v.equals("9"))
            return 9;
        else if(v.equals("10") ||v.equals("X"))
            return 10;
        else
            return 0;
    }

    void listener()
    {
        tv_role=findViewById(R.id.tv_role);
        tv_date=findViewById(R.id.tv_date);
        tv_b=findViewById(R.id.tv_b);
        tv_range=findViewById(R.id.tv_range);
        tv_ps=findViewById(R.id.tv_ps);
        tv_ps1=findViewById(R.id.tv_ps1);
        bt_total=findViewById(R.id.bt_total);
        b_add=findViewById(R.id.b_add);
        lv_score=findViewById(R.id.lv_score);
        tv_ar=findViewById(R.id.tv_ar);
        tv_all=findViewById(R.id.tv_all);
    }

}