package com.example.demo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.databinding.RecycleTitleBinding;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter {
    private List<Integer> data;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private int SelectedPosition=0;
    public RecycleAdapter(List<Integer> data){
        this.data=data;
    }

    public interface OnItemClickListener{void onItemClick(int position);}
    public interface OnItemLongClickListener{void onItemLongClick(int position);}
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }

    public void setSelectedPosition(int position){this.SelectedPosition=position;}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleTitleBinding binding = RecycleTitleBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        return new mViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int v=data.get(position);
        mViewHolder h =(mViewHolder) holder;
        h.binding.first.setImageResource(v);
        h.itemView.setOnClickListener(v1 -> {if(onItemClickListener!=null)
        {onItemClickListener.onItemClick(position);}});
        h.itemView.setOnLongClickListener(v2->{
            onItemLongClickListener.onItemLongClick(position);return true;});}

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class mViewHolder extends RecyclerView.ViewHolder {
        RecycleTitleBinding binding;
        public mViewHolder(RecycleTitleBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
