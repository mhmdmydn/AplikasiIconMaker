package com.ghodel.gicons.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.ghodel.gicons.model.IconModel;
import android.content.Context;
import java.util.List;
import android.view.LayoutInflater;
import android.text.Layout;
import com.ghodel.gicons.R;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.caverock.androidsvg.SVGImageView;
import java.util.ArrayList;
import android.widget.Filterable;
import android.widget.Filter;
import java.util.Collection;
import android.view.View.OnClickListener;
import com.ghodel.gicons.activity.CreateIconActivity;
import android.content.Intent;
import com.ghodel.gicons.util.MainUtils;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> implements Filterable {
    
    private Context con;
    private List<IconModel> list;
    private List<IconModel> listFull;
    
    public IconAdapter(Context con, List<IconModel> list){
        this.con = con;
        this.list = list;
        this.listFull = new ArrayList<>(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_icon, parent, false);
        
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final IconModel im = list.get(position);
        
        holder.icon.setSVG(im.getSVG());
        holder.iconName.setText(im.getFileName());
        
        holder.cardIcon.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent goToCreate = new Intent();
                    goToCreate.setAction(Intent.ACTION_VIEW);
                    goToCreate.putExtra("filePath", im.getFilePath());
                    goToCreate.putExtra("fileName", im.getFileName());
                    goToCreate.setClass(con, CreateIconActivity.class);
                    con.startActivity(goToCreate);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder{
        SVGImageView icon;
        TextView iconName;
        CardView cardIcon;
        
        public ViewHolder(View itemView){
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            iconName = itemView.findViewById(R.id.icon_title);
            cardIcon = itemView.findViewById(R.id.card_icon);
        }
    }
    
    @Override
    public Filter getFilter() {
        return filter;
    }
    
    Filter filter = new Filter(){

        @Override
        protected Filter.FilterResults performFiltering(CharSequence charSeq) {
            List<IconModel> listFilter = new ArrayList<>();
            
            if(charSeq == null || charSeq.length() == 0){
                listFilter.addAll(listFull);
            }else{
                String query = charSeq.toString().toLowerCase().trim();
                
                for(IconModel listIcon : listFull){
                    if(listIcon.getFileName().toLowerCase().contains(query)){
                        listFilter.add(listIcon);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = listFilter;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSeq, Filter.FilterResults result) {
            list.clear();
            list.addAll((Collection<? extends IconModel>) result.values);
            notifyDataSetChanged();
        }
        
    };
}
