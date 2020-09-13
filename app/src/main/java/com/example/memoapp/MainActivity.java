package com.example.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.example.memoapp.DataBaseMemo.Memos;

public class MainActivity extends AppCompatActivity {
    LinearLayout mainLayout;
    LinearLayout memoLayout;
    List<Memos> memoVal;
    List<Memos> memoValCopy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

        ImageView img = (ImageView) findViewById(R.id.btnAddNewMemo);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMemoIntent = new Intent(MainActivity.this,AddMemo.class);
                startActivity(addMemoIntent);
            }
        });

        ImageView imgLogout = (ImageView)findViewById(R.id.imageView_logout);
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(logoutIntent);
                Toast.makeText(MainActivity.this,"Uspesno ste odjavljeni.",Toast.LENGTH_SHORT).show();
            }
        });




    }
    public List<Memos> memoGet(){
        DataBaseMemo db = new DataBaseMemo(this);

        memoVal = db.getAllMemos();

        if(memoVal.size() == 0){
            db.addMemo("Prva beleska","Ovo je neki test tekst");
            db.addMemo("Druga beleska","Ovo je drugi test");
        }
        return memoVal;
    }

    private void getData(){
        LayoutInflater inflater = LayoutInflater.from(this);
        mainLayout = findViewById(R.id.mainView);

        memoVal = memoGet();
        memoValCopy = memoGet();

        for(final Memos m:memoVal){
            memoLayout = (LinearLayout) inflater.inflate(R.layout.memo,mainLayout,false);

            TextView name = memoLayout.findViewById(R.id.textViewMemoName);

            name.setTextColor(Color.BLACK);
            name.setTextSize(18);

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putString("id",String.valueOf(m.getMemoID()));
                    Intent i = new Intent(MainActivity.this,ViewActivity.class);
                    i.putExtras(extras);
                    startActivity(i);

                }
            });
            name.setText(m.getmName());

            mainLayout.addView(memoLayout);

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
