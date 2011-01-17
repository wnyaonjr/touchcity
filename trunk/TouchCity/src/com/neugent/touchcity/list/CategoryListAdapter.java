package com.neugent.touchcity.list;

import com.neugent.touchcity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryListAdapter extends BaseAdapter {
    private Context mContext;
    String[] categories;
    private LayoutInflater mInflater;
    Integer[] cat_icon = {
            R.drawable.dining_n, R.drawable.travel_n,
            R.drawable.shopping_n, R.drawable.learn_n,
            R.drawable.nightlife_n, R.drawable.beauty_n,
    };
	
    public CategoryListAdapter(Context c, String[] categories) {
        mContext = c;
        this.categories = categories;
        mInflater = LayoutInflater.from(mContext);
	}

	public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.imageview, null);

            holder = new ViewHolder();
            holder.categories = (ImageView) convertView.findViewById(R.id.cat);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.categories.setImageResource(cat_icon[position]);

        return convertView;
    }

    class ViewHolder {
        ImageView categories;
    }

	
	public int getCount() {
		return categories.length;
	}

}