package com.group07.greensmart.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.group07.greensmart.R;
import com.group07.greensmart.listener.OnMenuItemAGPClickListener;
import com.group07.greensmart.listener.RecycleViewOnItemClickListener;
import com.group07.greensmart.model.AgriculturalProduct;
import com.group07.greensmart.utils.ApplicationUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenvuhuy on 4/3/18.
 */

public class AgriculturalProductAdapter extends RecyclerView.Adapter<AgriculturalProductAdapter.AgriculturalProductViewHolder> {

    private Context context;
    private ArrayList<AgriculturalProduct> listAGP;
    private RecycleViewOnItemClickListener recycleViewOnItemClickListener;
    private OnMenuItemAGPClickListener onMenuItemAGPClickListener;

    public AgriculturalProductAdapter(Context context, ArrayList<AgriculturalProduct> listAGP) {
        this.context = context;
        this.listAGP = listAGP;
    }

    public void setRecycleViewOnItemClickListener(RecycleViewOnItemClickListener recycleViewOnItemClickListener) {
        this.recycleViewOnItemClickListener = recycleViewOnItemClickListener;
    }

    public void setOnMenuItemAGPClickListener(OnMenuItemAGPClickListener onMenuItemAGPClickListener) {
        this.onMenuItemAGPClickListener = onMenuItemAGPClickListener;
    }

    @Override
    public AgriculturalProductAdapter.AgriculturalProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_agricultural_product, parent, false);
        return new AgriculturalProductAdapter.AgriculturalProductViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final AgriculturalProductAdapter.AgriculturalProductViewHolder holder, final int position) {

        holder.setRecycleViewOnItemClickListener(new RecycleViewOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (recycleViewOnItemClickListener != null) {
                    recycleViewOnItemClickListener.onClick(view, position);
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                if (recycleViewOnItemClickListener != null) {
                    recycleViewOnItemClickListener.onLongClick(view, position);
                }
            }
        });

        AgriculturalProduct agriculturalProduct = listAGP.get(position);
        holder.txtName.setText(agriculturalProduct.getName());
        holder.txtTemperature.setText(context.getString(R.string.item_agp_temperature,
                String.valueOf(agriculturalProduct.getMinTemperature()), String.valueOf(agriculturalProduct.getMaxTemperature())));
        holder.txtHumidity.setText(context.getString(R.string.item_agp_humidity,
                String.valueOf(agriculturalProduct.getMinHumidity()), String.valueOf(agriculturalProduct.getMaxHumidity())));

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

        if (agriculturalProduct.isNotification()) {
            holder.txtNotification.setText(context.getString(R.string.item_agp_notification));
        } else {
            holder.txtNotification.setVisibility(View.INVISIBLE);
        }

        Picasso.get().load(ApplicationUtils.getImageUrl(context, agriculturalProduct.getImage()))
                .resize(90, 90)
                .into(holder.image);

        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder, position);
            }
        });

    }

    void showPopupMenu(final AgriculturalProductAdapter.AgriculturalProductViewHolder holder, final int position) {
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

                        if (onMenuItemAGPClickListener != null) {
                            onMenuItemAGPClickListener.onViewWeatherForecastClick(holder.imgMenu, position);
                        }

                        break;
                    case R.id.menu_item_agp_delete:

                        if (onMenuItemAGPClickListener != null) {
                            onMenuItemAGPClickListener.onDeleteAGPClick(holder.imgMenu, position);
                        }


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

    public class AgriculturalProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView txtName, txtTemperature, txtHumidity, txtDetectRain, txtDrying, txtNotification;
        public CircleImageView image;
        public ImageButton imgMenu;
        private RecycleViewOnItemClickListener recycleViewOnItemClickListener;

        public AgriculturalProductViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnClickListener(this);
            txtName = (TextView) view.findViewById(R.id.txt_item_agp_name);
            txtTemperature = (TextView) view.findViewById(R.id.txt_item_agp_temperature);
            txtHumidity = (TextView) view.findViewById(R.id.txt_item_agp_humidity);
            txtDetectRain = (TextView) view.findViewById(R.id.txt_item_agp_detect_rain);
            txtDrying = (TextView) view.findViewById(R.id.txt_item_agp_drying);
            txtNotification = (TextView) view.findViewById(R.id.txt_item_agp_notification);
            image = view.findViewById(R.id.img_item_agp_image);
            imgMenu = view.findViewById(R.id.btn_item_agp_menu);
        }

        public void setRecycleViewOnItemClickListener(RecycleViewOnItemClickListener recycleViewOnItemClickListener) {
            this.recycleViewOnItemClickListener = recycleViewOnItemClickListener;
        }


        @Override
        public void onClick(View view) {
            if (recycleViewOnItemClickListener != null) {
                recycleViewOnItemClickListener.onClick(view, getAdapterPosition());
            }

        }

        @Override
        public boolean onLongClick(View view) {
            if (recycleViewOnItemClickListener != null) {
                recycleViewOnItemClickListener.onLongClick(view, getAdapterPosition());
                return true;
            }
            return false;
        }
    }
}
