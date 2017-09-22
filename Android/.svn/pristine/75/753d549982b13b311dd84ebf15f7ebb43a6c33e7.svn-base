package com.android.pgb.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.android.pgb.R;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param text
	 * @return
	 */
	public static ImageView getImageView(Context context, String url, @Nullable int drawable) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		ImageLoader.getInstance().displayImage(url, imageView);

		//ImageLoaderUtil.getInstance().displayFromDrawable(drawable,imageView);
		return imageView;
	}
}
