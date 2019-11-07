package com.kinteg.mytodo.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kinteg.mytodo.AddActivity;
import com.kinteg.mytodo.R;
import com.kinteg.mytodo.store.Days;
import com.kinteg.mytodo.store.StoreBigTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> days;
    private Context context;
    private final static String TYPE = "type";
    private static final StoreBigTask store_big_task = StoreBigTask.getSTORE_BIG_TASK();

    public DayAdapter(Context context) {
        this.context = context;
        days = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.days)));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.days, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Days typeDay = Days.values()[position];
        Log.wtf("WTF", new Date(typeDay.getStart() + new Date().getTime()).toString());
//        Toast.makeText(context, , Toast.LENGTH_LONG).show();
        final RecyclerView.Adapter adapter = new TaskAdapter(typeDay);
        final RecyclerView tasks = holder.itemView.findViewById(R.id.tasks);
        TextView day = holder.itemView.findViewById(R.id.day);
        Button button = holder.itemView.findViewById(R.id.add_task);

        day.setText(days.get(position));
        day.setOnClickListener(v -> tasks.setVisibility(tasks.getVisibility() == View.VISIBLE ?
                View.GONE : store_big_task.getSize(typeDay) != 0 ? View.VISIBLE : View.GONE));

        if (!typeDay.equals(Days.PAST))
        button.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddActivity.class);
            intent.putExtra(TYPE, typeDay);

            context.startActivity(intent);
        });
        else button.setVisibility(View.INVISIBLE);

        tasks.setLayoutManager(new LinearLayoutManager(context));
        tasks.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }
}
