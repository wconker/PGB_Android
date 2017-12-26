package com.android.pinggubang.View.InnerListview.adapt;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.pinggubang.Bean.XJBean;
import com.android.pinggubang.R;
import com.android.pinggubang.View.InnerListview.base.BaseObjectListAdapter;

/**
 * ��listview������
 * @author mmsx
 *
 */
public class ChildAdapt extends BaseObjectListAdapter {

	private List<XJBean> mDatas = new ArrayList<XJBean>();
	public ChildAdapt(Context context, List<XJBean > datas) {
		super(context, datas);
		mDatas=datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = null;
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_main_list_item_1, null);
			vHolder.name = (TextView)convertView.findViewById(R.id.textView_1);
			vHolder.pay= (TextView)convertView.findViewById(R.id.textView_2);
			convertView.setTag(vHolder);
		}else {
			vHolder = (ViewHolder) convertView.getTag();
		}
		XJBean tempEntity =  mDatas.get(position);
		vHolder.name.setText(tempEntity.getNc());
//		Log.e("我是报酬"+tempEntity.getBc());
		vHolder.pay.setText("￥"+tempEntity.getBc());
		return convertView;
	}
	
	class ViewHolder{
		TextView name;
		TextView pay;
	}
}
