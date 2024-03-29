package com.kinteg.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kinteg.mytodo.adapters.AddAdapter;
import com.kinteg.mytodo.store.Days;
import com.kinteg.mytodo.store.StoreBigTask;

import java.text.ParseException;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private final static String TYPE = "type";
    private EditText name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.add_name);
        name.setOnKeyListener(this::enterName);

        final RecyclerView.Adapter adapter = new AddAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.adds);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public boolean enterName(View v, int keyCode, KeyEvent event) {
        Date date;
        if (getIntent().getSerializableExtra(TYPE).getClass() == Date.class) {
            date = (Date) getIntent().getSerializableExtra(TYPE);
        } else {
            Days days = (Days) getIntent().getSerializableExtra(TYPE);
            date = days.equals(Days.WITHOUT_DATE) ? null : days.getStartTime();
        }

        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            if (name.length() == 0) {
                name.setError(getResources().getString(R.string.error));
            } else {
                name.setError(null);
                StoreBigTask.getSTORE_BIG_TASK().add(name.getText().toString(), date, null, false);

                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }
        return true;
    }

}
