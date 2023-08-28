package tw.fgu.archery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class choosebow extends AppCompatActivity {
    Spinner bow_spi;
    Image b_image;
    EditText r_name, r_ex;
    Button n_button;

    ArrayList<HashMap<String, Object>> list;

    private SQLiteDatabase mSQLiteDatabase = null;
    private static final String DATABASE_NAME = "archery.db";
    final String[] bow = {"弓種", "反曲弓", "複合弓", "傳統弓"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosebow);
        initialize();

        n_button = (Button) findViewById(R.id.n_button);
        n_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String U = r_name.getText().toString();
                String BOW = bow[bow_spi.getSelectedItemPosition()];
                String ps = r_ex.getText().toString();
                mSQLiteDatabase.execSQL("INSERT INTO user(U,BOW,ps) values ('" + U + "','" + BOW + "','" + ps + "')");

                finish();
            }
        });

    }

    void initialize()
    {
        bow_spi = findViewById(R.id.bow_spi);
        r_name = findViewById(R.id.r_name);
        bow_spi = findViewById(R.id.bow_spi);
        r_ex = findViewById(R.id.r_ex);

        list = new ArrayList<>();

        final Spinner bow_spi = (Spinner) findViewById(R.id.bow_spi);

        ArrayAdapter<String> bowList = new ArrayAdapter<>
                (choosebow.this, android.R.layout.simple_list_item_1, bow);
        bow_spi.setAdapter(bowList);
        mSQLiteDatabase = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

    }


}