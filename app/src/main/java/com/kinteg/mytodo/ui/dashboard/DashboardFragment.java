package com.kinteg.mytodo.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kinteg.mytodo.AddActivity;
import com.kinteg.mytodo.R;
import com.kinteg.mytodo.adapters.TaskAdapter;

import java.util.Calendar;
import java.util.Date;

public class DashboardFragment extends Fragment {

    private CalendarView calendarView;
    private View root;
    private Button button;
    private final static String TYPE = "type";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.calendarRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        button = root.findViewById(R.id.button_add);
        calendarView = root.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth, 0, 0, 0);
            calendarView.setDate(calendar.getTimeInMillis());
            Date date = calendar.getTime();
            RecyclerView.Adapter adapter = new TaskAdapter(date);
            recyclerView.removeAllViewsInLayout();
            recyclerView.swapAdapter(adapter, true);
            calendarView.setDate(calendar.getTimeInMillis());
        });

        button.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), AddActivity.class);
            intent.putExtra(TYPE, new Date(calendarView.getDate()));
            Log.wtf("WTF1", new Date(calendarView.getDate()).toString());
            root.getContext().startActivity(intent);
        });
        return root;
    }

}