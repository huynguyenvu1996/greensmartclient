package com.group07.greensmart.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.group07.greensmart.R;
import com.group07.greensmart.model.AgriculturalProduct;
import com.group07.greensmart.model.Notifications;

import java.util.ArrayList;

/**
 * Created by nguyenvuhuy on 4/3/18.
 */

public class AgriculturalProductAdapter extends RecyclerView.Adapter<AgriculturalProductAdapter.AgriculturalProductViewHolder> {

    private Context context;
    private ArrayList<AgriculturalProduct> listAGP;

    public AgriculturalProductAdapter(Context context, ArrayList<AgriculturalProduct> listAGP) {
        this.context = context;
        this.listAGP = listAGP;
    }


    @Override
    public AgriculturalProductAdapter.AgriculturalProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_agricultural_product, parent, false);
        return new AgriculturalProductAdapter.AgriculturalProductViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final AgriculturalProductAdapter.AgriculturalProductViewHolder holder, int position) {
        AgriculturalProduct agriculturalProduct = listAGP.get(position);
        holder.txtName.setText(agriculturalProduct.getName());
        holder.txtTemperature.setText(context.getString(R.string.item_agp_temperature,
                agriculturalProduct.getMinTemperature(), agriculturalProduct.getMinTemperature()));
        holder.txtHumidity.setText(context.getString(R.string.item_agp_humidity,
                agriculturalProduct.getMinHumidity(), agriculturalProduct.getMaxHumidity()));

        if (agriculturalProduct.isDetectRain()) {
            holder.txtDetectRain.setText(context.getString(R.string.item_agp_detect_rain));
        } else {
            holder.txtDetectRain.setText(context.getString(R.string.item_agp_no_detect_rain));
        }
        if (agriculturalProduct.isDrying()) {
            holder.txtDrying.setText(context.getString(R.string.item_agp_drying));
        } else {
            holder.txtDrying.setText(context.getString(R.string.item_agp_no_drying));
        }

        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder);
            }
        });

    }

    void showPopupMenu(AgriculturalProductAdapter.AgriculturalProductViewHolder holder) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, holder.imgMenu);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_item_agp);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_agp_view_forecast:
                        //handle menu1 click
                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();

    }

    @Override
    public int getItemCount() {
        return listAGP.size();
    }

    public class AgriculturalProductViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, txtTemperature, txtHumidity, txtDetectRain, txtDrying, txtNotification;
        public ImageView image;
        public ImageButton imgMenu;

        public AgriculturalProductViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txt_item_agp_name);
            txtTemperature = (TextView) view.findViewById(R.id.txt_item_agp_temperature);
            txtHumidity = (TextView) view.findViewById(R.id.txt_item_agp_humidity);
            txtDetectRain = (TextView) view.findViewById(R.id.txt_item_agp_detect_rain);
            txtDrying = (TextView) view.findViewById(R.id.txt_item_agp_drying);
            txtNotification = (TextView) view.findViewById(R.id.txt_item_agp_notification);
            image = view.findViewById(R.id.img_add_agp_image);
            imgMenu = view.findViewById(R.id.btn_item_agp_menu);
        }
    }
}
