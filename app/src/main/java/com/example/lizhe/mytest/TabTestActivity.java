package com.example.lizhe.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class TabTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override public int getItemCount() {
            return 0;
        }

//        class MyViewHolder extends RecyclerView.ViewHolder {
//            MyViewHolder(TextView view){//
//            }
//        }
    }
}
