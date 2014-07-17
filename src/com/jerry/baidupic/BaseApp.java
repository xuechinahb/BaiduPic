package com.jerry.baidupic;

import java.io.File;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class BaseApp extends Application {

	
	@Override
	public void onCreate() {
		super.onCreate();
		
//		final int memClass = ((ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
//		int memCache = memClass / 3;
		
		File cacheDir = StorageUtils.getCacheDirectory(this);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//		        .diskCacheExtraOptions(480, 800, null)
		        .threadPoolSize(3) // default
		        .threadPriority(Thread.NORM_PRIORITY - 1) // default
		        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
		        .denyCacheImageMultipleSizesInMemory()
//		        .memoryCache(new LruMemoryCache(memCache * 1024 * 1024))
//		        .memoryCacheSize(memCache * 1024 * 1024)
		        .memoryCacheSizePercentage(30) 
		        .diskCache(new UnlimitedDiscCache(cacheDir)) // default
		        .diskCacheSize(60 * 1024 * 1024)
		        .diskCacheFileCount(1000)
		        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
		        .imageDownloader(new BaseImageDownloader(this)) // default
		        .imageDecoder(new BaseImageDecoder(false)) // default
		        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
		        .writeDebugLogs()
		        .build();
		
		ImageLoader.getInstance().init(config);
	}
}
