package com.example.cloudchat_from_panama;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView message;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        message=itemView.findViewById(R.id.message_item);

    }
}
