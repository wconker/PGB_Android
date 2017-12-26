package com.android.pinggubang.Activity.Core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.View.CBarView;

import java.util.ArrayList;
import java.util.List;

public class Select_DX extends Activity {

    private List<String> list;
    private ListView listView;
    private Select_DX.ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__dx);

        final String s = getIntent().getStringExtra("value");

        String[] digitGroup = s.split("\\|");

        list = new ArrayList<>();
        for (int t = 0; t < digitGroup.length; t++)
            list.add(digitGroup[t]);


        final int Index = getIntent().getIntExtra("index", 0);
        adapter = new Select_DX.ListViewAdapter(this, list, R.layout.new_items_list);
        listView = (ListView) this.findViewById(R.id.selectdx);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("Conker", list.get(i));
                Intent data = new Intent();
                data.putExtra("StrValue", list.get(i).toString());
                data.putExtra("index", Index);
                setResult(Index, data); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
                finish();//此处一定要调用finish()方法
            }
        });

    }


    class ListViewAdapter extends CommonAdapter<String> {


        public ListViewAdapter(Context context, List<String> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, String s) {

            viewHolder.setText(R.id.newsitem, s);
        }
    }


}
