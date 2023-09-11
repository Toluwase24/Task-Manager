package com.example.mystudy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> implements Filterable {
   private Context context;
   private Activity activity;
   private List<TaskModel> taskList;
   private List<TaskModel> newtaskList;

    public TaskAdapter(Context context, Activity activity, List<TaskModel> taskList) {
        this.context =  context;
        this.activity =  activity;
        this.taskList = taskList;
        newtaskList =  new ArrayList<>(taskList);
    }


    @NonNull
    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.task_container, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recycler_title.setText(taskList.get(position).getRecycler_title());
        holder.recycler_description.setText(taskList.get(position).getRecycler_description());
        holder.recycler_date.setText(taskList.get(position).getRecycler_date());
        holder.recycler_time.setText(taskList.get(position).getRecycler_time());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TaskModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(newtaskList);
            } else {
                String filterPattern =  constraint.toString().toLowerCase().trim();

                for(TaskModel item : newtaskList){
                    if(item.getRecycler_title().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            taskList.clear();
            taskList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView recycler_title, recycler_description, recycler_date, recycler_time;
        CardView layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recycler_title =  itemView.findViewById(R.id.recycler_title);
            recycler_description =  itemView.findViewById(R.id.recycler_description);
            recycler_date =  itemView.findViewById(R.id.recycler_date);
            recycler_time = itemView.findViewById(R.id.recycler_time);

            layout = itemView.findViewById(R.id.task_layout);
        }

    }

    public List<TaskModel> getTaskList(){
        return taskList;
    }

    public void removeItem(int position){
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(TaskModel item, int position){
        taskList.add(position, item);
        notifyItemInserted(position);
    }
}
