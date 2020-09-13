package com.example.memoapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;

public class AddMemo extends AppCompatActivity {
    DataBaseMemo db;
    EditText mName;
    EditText mText;
    Button mBtnAdd;
    Button mBtnCncl;
    Bundle extras;
    DataBaseMemo.Memos memos;
    Boolean editTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        db = new DataBaseMemo(this);
        mName = (EditText)findViewById(R.id.mName);
        mText = (EditText)findViewById(R.id.mText);
        mBtnAdd = (Button)findViewById(R.id.btnAdd);
        mBtnCncl = (Button)findViewById(R.id.btnCancel);
        extras = getIntent().getExtras();
        editTxt = false;

        if(extras != null){
            editTxt = true;
            memos = db.getMemoById(Integer.parseInt(extras.getString("id")));
            mName.setText(memos.getmName());
            mText.setText(memos.getmText());

        }
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTxt == false){
                    addMemo();
                    backToFirstIntent();
                }else {
                    editMemo(memos);
                }
            }
        });

        mBtnCncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToFirstIntent();
            }
        });

    }


    private void editMemo(DataBaseMemo.Memos memos){
        DataBaseMemo db = new DataBaseMemo(this);

        String memoName = mName.getText().toString().trim();
        String memoText = mText.getText().toString().trim();

        if(memoName.isEmpty() != true && memoText.isEmpty() != true){
            db.editMemo(memos.getMemoID(),memoName,memoText);
            Toast.makeText(AddMemo.this,"Upsesno ste izmenili belesku.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddMemo.this,ViewActivity.class);
            intent.putExtras(extras);
            startActivity(intent);


        }else {
            Toast.makeText(AddMemo.this,"Morate da popunite sva navedena polja!",Toast.LENGTH_SHORT).show();
        }
    }

    private  void addMemo(){
        String memoName = mName.getText().toString().trim();
        String memoText = mText.getText().toString().trim();

        if(memoName.isEmpty() != true && memoText.isEmpty() != true){
            long val = db.addMemo(memoName,memoText);
            if (val > 0){
                Toast.makeText(AddMemo.this,"Uspesno ste dodali belesku!",Toast.LENGTH_SHORT).show();}

            else {
                Toast.makeText(AddMemo.this,"Greska prilikom postavljanja beleske",Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(AddMemo.this,"Morate da popunite sva navedena polja!",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddMemo.this, MainActivity.class));
    }

    private void backToFirstIntent() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }





}
