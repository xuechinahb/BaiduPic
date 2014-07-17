package com.jerry.baidupic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

	/**
	 * 主要参数： word，查询关键词 rn，每页显示图片数量 pn，图片显示的页码
	 * 其它参数照抄上述api地址中的就行。
	 */
	private String JSONDataUrl = "http://image.baidu.com/i?tn=baiduimagejson&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr="
			+ "&sf=1&fmq=1349413075627_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&word=gaoqing&rn=";
	
	private ListView mListView ;
	private List<String> mDatas;
	private ItemAdapter mAdapter;
	private final int PAGE_SIZE = 10;
	private int mPage = 1;
	private int count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		mListView = (ListView) findViewById(R.id.listView);
		mListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && view.getLastVisiblePosition() == view.getCount() - 1) {
					if (count != view.getCount()) {
						mPage++;				
						System.out.println("page===" + mPage);
						load();
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			}
		});
		
		mDatas = new ArrayList<String>();
		mAdapter = new ItemAdapter(this, mDatas);
		mListView.setAdapter(mAdapter);
		load();
	}
	
	 private void load() { 
		 	count = mListView.getCount();
		 	RequestQueue requestQueue = Volley.newRequestQueue(this); 
	        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(  
	                Request.Method.GET,   
	                JSONDataUrl + PAGE_SIZE + "&pn=" + mPage ,  
	                null,  
	                new Response.Listener<JSONObject>() {  
	                    @Override  
	                    public void onResponse(JSONObject response) {  
	                    	try {
								JSONArray jArray = response.getJSONArray("data");
								for (int i = 0; i < jArray.length(); i++) {
									System.out.println("image url===" + jArray.getJSONObject(i).getString("objURL"));
									mDatas.add(jArray.getJSONObject(i).getString("objURL"));
								}
								
							} catch (JSONException e) {
								e.printStackTrace();
							}
	                    	mAdapter.notifyDataSetChanged();
	                    }  
	                },   
	                new Response.ErrorListener() {  
	                    @Override  
	                    public void onErrorResponse(VolleyError arg0) {  
	                           System.out.println("sorry,Error");  
	                    }  
	                });  
	        requestQueue.add(jsonObjectRequest);  
	    }  

}
