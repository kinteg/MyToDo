package com.kinteg.mytodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kinteg.mytodo.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AddAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<String> options;

    private Context context;

    public AddAdapter(Context context) {
        this.context = context;
        options = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.tasks)));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.add);
        EditText editText = ((AppCompatActivity) context).findViewById(R.id.add_name);

        textView.setText(options.get(position));
        textView.setOnClickListener(v -> {
            editText.setText(options.get(position));
            editText.setSelection(editText.length());
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }
}
