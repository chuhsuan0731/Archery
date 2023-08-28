package tw.fgu.archery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    Button ex_button;
    ImageButton target;
    Switch title;

    private SQLiteDatabase mSQLiteDatabase= null;
    private static final String DATABASE_NAME = "archery.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSQLiteDatabase = this.openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
        initialize();

        ImageButton target = (ImageButton) findViewById(R.id.target);
        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainActivity = new Intent(MainActivity.this,role.class);
                startActivity(MainActivity);
            }
        });

        Button ex_button = (Button) findViewById(R.id.ex_button);
        ex_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=zH7jvoWE4po"));
                startActivity(intent);
            }
        });
    }

    void initialize()
    {
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user (u_id INTEGER PRIMARY KEY AUTOINCREMENT, U TEXT,BOW TEXT,ps TEXT)";
        mSQLiteDatabase.execSQL(CREATE_USER_TABLE);

        String CREATE_RECORD_TABLE = "CREATE TABLE IF NOT EXISTS record (r_id INTEGER PRIMARY KEY AUTOINCREMENT,user  TEXT ,range INTEGER,DATE date,other TEXT)";
        mSQLiteDatabase.execSQL(CREATE_RECORD_TABLE);

        String CREATE_GRADE_TABLE = "CREATE TABLE IF NOT EXISTS grade (g_id INTEGER PRIMARY KEY AUTOINCREMENT,r_id INTEGER,one TEXT,two TEXT,thr TEXT,four TEXT,five TEXT,six TEXT)";
        mSQLiteDatabase.execSQL(CREATE_GRADE_TABLE);
    }

}