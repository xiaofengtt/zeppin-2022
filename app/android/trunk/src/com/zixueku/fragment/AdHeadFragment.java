package com.zixueku.fragment;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zixueku.R;
import com.zixueku.activity.ADDetialActivity;
import com.zixueku.listerner.IntentActivityListener;
import com.zixueku.net.URL;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年9月8日 下午4:39:33
 */
public class AdHeadFragment extends Fragment {
	private static final String ARG_IMAGE_URL = "imageUrl";
	private static final String ARG_TITLE = "title";
	private static final String ARG_URL = "url";
	private ImageLoader imageLoader = ImageLoader.getInstance();

	private String mImageUrl;
	private String mTitle;
	private String mUrl;

	public AdHeadFragment() {
	}

	public static AdHeadFragment newInstance(String imageUrl, String title, String url) {
		AdHeadFragment fragment = new AdHeadFragment();
		Bundle args = new Bundle();
		args.putString(ARG_IMAGE_URL, imageUrl);
		args.putString(ARG_TITLE, title);
		args.putString(ARG_URL, url);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mImageUrl = bundle.getString(ARG_IMAGE_URL);
			mTitle = bundle.getString(ARG_TITLE);
			mUrl = bundle.getString(ARG_URL);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.ad_head_fragment, container, false);
		ImageView imageView = (ImageView) v.findViewById(R.id.ad_head_image);
		imageLoader.displayImage(URL.ServerImagePath + mImageUrl, imageView);
		Map<String, String> params = new HashMap<String, String>();
		params.put(ARG_URL, mUrl);
		params.put(ARG_TITLE, mTitle);
		v.setOnClickListener(new IntentActivityListener(getActivity(), ADDetialActivity.class, params));
		return v;
	}
}
