package com.example.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Bundle extras = getIntent().getExtras();
        final DataBaseMemo db = new DataBaseMemo(this);
        final DataBaseMemo.Memos memo = db.getMemoById(Integer.parseInt(extras.getString("id")));

        TextView viewMemoName = findViewById(R.id.showMemoName);
        TextView viewMemoText = findViewById(R.id.showMemoText);

        viewMemoName.setText(memo.getmName());
        viewMemoText.setText(memo.getmText());

        Button edit = findViewById(R.id.editBtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("id",String.valueOf(memo.getMemoID()));
                Intent intent = new Intent(ViewActivity.this,AddMemo.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        Button delete = findViewById(R.id.deleteMemoBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewActivity.this,MainActivity.class);
                db.deletePost(memo.getMemoID());
                Toast.makeText(ViewActivity.this,"Uspesno ste obrisali memo.",Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
    }
}
