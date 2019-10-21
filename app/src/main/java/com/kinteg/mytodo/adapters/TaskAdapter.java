package com.kinteg.mytodo.adapters;

import android.graphics.Paint;
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

class TaskAdapter extends RecyclerView.Adapter {

    private Days day;

    TaskAdapter(Days day) {
        this.day = day;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BigTask bigTask = StoreBigTask.getStore().getAll(day).get(position);
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
            StoreBigTask.getStore().setCheck(checkBox.isChecked(), bigTask);
        });

        checkBox.setChecked(bigTask.isCompleted());
        name.setText(bigTask.getName());

        button.setOnClickListener(v -> {
            notifyItemRemoved(position);
            StoreBigTask.getStore().remove(bigTask);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return StoreBigTask.getStore().getSize(day);
    }


}
