package com.example.demo;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.databinding.RecycleTitleBinding;
import com.example.demo.databinding.ViewPager2Binding;

import java.util.List;

public class ViewPager2Adapter extends RecyclerView.Adapter {
    private List<Integer> data;
    public ViewPager2Adapter(List<Integer> data){
        this.data=data;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewPager2Binding binding = ViewPager2Binding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        return new mViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int v=data.get(position);
        mViewHolder h =(mViewHolder) holder;
        h.binding.one.setImageResource(v);
        h.itemView.setOnClickListener(v1 -> Toast.makeText(v1.getContext(), "点击："+v,Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class mViewHolder extends RecyclerView.ViewHolder {
        ViewPager2Binding binding;
        public mViewHolder(ViewPager2Binding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
