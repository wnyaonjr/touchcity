package com.neugent.touchcity.list;

import java.util.ArrayList;

import com.neugent.touchcity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CompanyListAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> companyName;
    ArrayList<String> companyAdd;
    ArrayList<String> phoneNumber;
    private LayoutInflater mInflater;
    
    public CompanyListAdapter(Context c, ArrayList<String> name, ArrayList<String> addr, ArrayList<String> phone) {
        mContext = c;
        this.companyName = name;
        this.companyAdd = addr;
        this.phoneNumber = phone;
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
            convertView = mInflater.inflate(R.layout.gridview_items, null);

            holder = new ViewHolder();
            holder.compName = (TextView) convertView.findViewById(R.id.compName);
            holder.compAdd = (TextView) convertView.findViewById(R.id.compAdd);
            holder.phoneNum = (TextView) convertView.findViewById(R.id.phoneNum);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.compName.setText(companyName.get(position).toString());
        holder.compAdd.setText(companyAdd.get(position).toString());
        if(phoneNumber.get(position).toString().equals("")) holder.phoneNum.setText("");
        else holder.phoneNum.setText("Contact Number: " + phoneNumber.get(position).toString() + "\n ");

        return convertView;
    }

    class ViewHolder {
        TextView compName;
        TextView compAdd;
        TextView phoneNum;
    }

	
	public int getCount() {
		return companyName.size();
	}

}