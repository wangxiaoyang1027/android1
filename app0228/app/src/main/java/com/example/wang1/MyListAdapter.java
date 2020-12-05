package com.example.wang1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class MyListAdapter extends android.widget.BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutinflater;

    public MyListAdapter(Context context){
        this.mContext = context;
        mLayoutinflater  = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        public ImageView imgIV;
        public TextView v_firstname ,v_lastname, v_email;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  holder = null;
        if (convertView == null){
            convertView = mLayoutinflater.inflate(R.layout.layout_list_item,null);

            holder = new ViewHolder();
            holder.imgIV = convertView.findViewById(R.id.imgIV);
            holder.v_firstname = convertView.findViewById(R.id.v_firstname);
            holder.v_lastname = convertView.findViewById(R.id.v_lastname);
            holder.v_email = convertView.findViewById(R.id.v_email);
            convertView.setTag(holder);

        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }


        holder.v_firstname.setText(((MusicListActivity)mContext).displaylist.get(position).first_name);
        holder.v_lastname.setText(((MusicListActivity)mContext).displaylist.get(position).last_name);
        Glide.with(mContext).load(((MusicListActivity)mContext).displaylist.get(position).img).into(holder.imgIV);



        return convertView;
    }
}
