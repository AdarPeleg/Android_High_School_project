package com.example.oneapptorulethemall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


class MyAdapter extends CursorAdapter {
    public MyAdapter(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.row_item, null);
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvAge = view.findViewById(R.id.tvAge);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        TextView tvPass = view.findViewById(R.id.tvPass);


        tvName.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.COLUMN_NAME)));
        tvAge.setText(cursor.getInt(cursor.getColumnIndex(SQLHelper.COLUMN_AGE)) +"");
        tvEmail.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.COLUMN_EMAIL)));
        tvPass.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.COLUMN_PASSWORD)));

    }



}
