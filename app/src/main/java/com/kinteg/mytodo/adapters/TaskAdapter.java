package com.kinteg.mytodo.adapters;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kinteg.mytodo.R;
import com.kinteg.mytodo.realm.BigTask;
import com.kinteg.mytodo.store.Days;
import com.kinteg.mytodo.store.StoreBigTask;

import java.util.Date;

public class TaskAdapter extends RecyclerView.Adapter {

    private Days day;
    private Date date;
    private static StoreBigTask store_big_task = StoreBigTask.getSTORE_BIG_TASK();

    public TaskAdapter(Days day) {
        this.day = day;
    }

    public TaskAdapter(Date date) {
        this.date = date;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BigTask bigTask;
        if (day == null) {
            bigTask = store_big_task.getAll(date).get(position);
            Log.wtf("111", date.toString() + "qwe");
        } else {
            bigTask = store_big_task.getAll(day).get(position);
        }

        if (bigTask == null) return;
        CheckBox checkBox = holder.itemView.findViewById(R.id.check);
        Button button = holder.itemView.findViewById(R.id.delete_task);
        TextView name = holder.itemView.findViewById(R.id.task_name);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (checkBox.isChecked()) {
                button.setVisibility(View.VISIBLE);
                checkBox.setPaintFlags(checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                button.setVisibility(View.INVISIBLE);
                checkBox.setPaintFlags(checkBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
            store_big_task.setCheck(checkBox.isChecked(), bigTask);
        });

        checkBox.setChecked(bigTask.isCompleted());
        name.setText(bigTask.getName());

        button.setOnClickListener(v -> {
            notifyItemRemoved(position);
            store_big_task.remove(bigTask);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if (day == null) {
            Log.wtf("qqqq1", store_big_task.getSize(date) + "");
            return store_big_task.getSize(date);
        }
        Log.wtf("qqqq2", store_big_task.getSize(day) + "");
        return store_big_task.getSize(day);
    }


}
