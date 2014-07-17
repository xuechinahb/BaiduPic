package com.jerry.baidupic;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ItemAdapter extends BaseAdapter {

	private List<String> mDatas;
	private LayoutInflater mInflater;
	DisplayImageOptions mDisplayOption;
	ImageLoader mImageLoader;
	
	public ItemAdapter(Context context, List<String> mDatas) {
		this.mDatas = mDatas;
		mInflater = LayoutInflater.from(context);
		mDisplayOption = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher).showImageForEmptyUri(R.drawable.ic_launcher).showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(true).considerExifParams(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		mImageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public String getItem(int arg0) {
		return mDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item, null);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		mImageLoader.displayImage(getItem(arg0), holder.image, mDisplayOption);
		return convertView;
	}

	
	static class ViewHolder{
		ImageView image;
	}
}
