package com.demo.hsl.pad;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.andexert.expandablelayout.library.ExpandableLayoutListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String[] array = {"30153", "30154", "30156", "30157", "30159", "30160", "30161", "30162", "30158","30153", "30154", "30156", "30157", "30159", "30160", "30161", "30162", "30158"};
    List<ProductItem> productItemList;
    RelativeLayout itmel;
    GridView gridView;
    private List<String> groupArray;
    private List<List<String>> childArray;
    private VideoView videoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService("layout_inflater");
        gridView = (GridView) findViewById(R.id.gridView);
        setData();
        setGridView();

      /*  ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
        expandableListView.setAdapter(new  ExpandableAdapter(MainActivity.this));*/
      /*阵列接收器*/
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.view_row, R.id.header_text, array);
        final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.listview);

        expandableLayoutListView.setAdapter(arrayAdapter);

        videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(uri);
        /*videoView.start();*/
    }


    private void setData() {
        productItemList = new ArrayList<ProductItem>();
        ProductItem item = new ProductItem();
        item.setCode("FL2384789");
        item.setName("花纹扣");
        productItemList.add(item);
        item = new ProductItem();
        item.setCode("FL221934");
        item.setName("黑色圆扣");
        productItemList.add(item);
        item = new ProductItem();
        item.setCode("KR23948");
        item.setName("红色丝线");
        productItemList.add(item);
        item = new ProductItem();
        item.setCode("FL783326");
        item.setName("天蚕丝");
        productItemList.add(item);
        item = new ProductItem();
        item.setCode("FL327947");
        item.setName("袈裟");
        productItemList.add(item);
        item = new ProductItem();
        item.setCode("FL198246");
        item.setName("振奋铠甲");
        productItemList.add(item);
        productItemList.addAll(productItemList);
    }

    /**
     * 设置GirdView参数，绑定数据
     */
    private void setGridView() {
        int size = productItemList.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        /*int itemWidth = (int) (length * density);*/
        int itemWidth =161;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(0); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数

        GridViewAdapter adapter = new GridViewAdapter(getApplicationContext(), productItemList);
        gridView.setAdapter(adapter);
    }
    /**
     * GirdView 数据适配器
     */
    public class GridViewAdapter extends BaseAdapter {
        Context context;
        List<ProductItem> list;

        public GridViewAdapter(Context _context, List<ProductItem> _list) {
            this.list = _list;
            this.context = _context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_item1, null);
            TextView text1 = (TextView) convertView.findViewById(R.id.text1);
            TextView text2 = (TextView) convertView.findViewById(R.id.text2);
            ProductItem product = list.get(position);

            text1.setText(product.getCode());
            text2.setText(product.getName());
            return convertView;
        }
    }

    public class ProductItem {
        private String code;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class ExpandableAdapter extends BaseExpandableListAdapter {
        Activity activity;


        public ExpandableAdapter(Activity a) {
            activity = a;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return childArray.get(groupPosition).get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return childArray.get(groupPosition).size();
        }

        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            String string = childArray.get(groupPosition).get(childPosition);
            return getGenericView(string);
        }

        // group method stub
        public Object getGroup(int groupPosition) {
            return groupArray.get(groupPosition);
        }

        public int getGroupCount() {
            return groupArray.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String string = groupArray.get(groupPosition);
            return getGenericView(string);
        }

        // View stub to create Group/Children 's View
        public TextView getGenericView(String string) {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);
            TextView text = new TextView(activity);
            text.setLayoutParams(layoutParams);
            // Center the text vertically
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            text.setPadding(36, 0, 0, 0);
            text.setText(string);
            return text;
        }

        public boolean hasStableIds() {
            return false;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
