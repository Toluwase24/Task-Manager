package com.example.mystudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageView newtask, btnDelete;
    TaskAdapter taskAdapter;
    RecyclerView recyclerView;
    List<TaskModel> taskList = new ArrayList<>();

    CoordinatorLayout coordinatorLayout;

    TaskDatabase taskDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);
        newtask = findViewById(R.id.createTask);
        btnDelete = findViewById(R.id.btnDelete);
        recyclerView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        taskDatabase = new TaskDatabase(this);

        fetchdata();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.task);


        newtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTask.class);
                startActivity(intent);
            }
        });

        taskAdapter = new TaskAdapter(this, MainActivity.this, taskList);
        recyclerView.setAdapter(taskAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);


    }


    private void fetchdata() {
        Cursor cursor = taskDatabase.readAllData();

        if(cursor == null || cursor.getCount() == 0){

        } else
        {
            while (cursor.moveToNext()) {
                taskList.add(new TaskModel(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btnDelete){
            deleteAllTasks();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllTasks() {
        TaskDatabase taskDatabase =  new TaskDatabase(MainActivity.this);
        taskDatabase.deleteAllTasks();
        recreate();
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            TaskModel item = taskAdapter.getTaskList().get(position);

            taskAdapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Item Deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            taskAdapter.restoreItem(item, position);
                            recyclerView.scrollToPosition(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            if (!(event == DISMISS_EVENT_ACTION)) {
                                TaskDatabase taskdb = new TaskDatabase(MainActivity.this);
                                taskdb.deleteSingleTask(item.getRecycler_title());
                            }
                        }
                    });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }

    };
}





