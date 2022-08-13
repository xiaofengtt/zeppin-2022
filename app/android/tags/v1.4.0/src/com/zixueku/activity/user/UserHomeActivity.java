package com.zixueku.activity.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.makeramen.segmented.SegmentedRadioGroup;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.base.BaseActivity;
import com.zixueku.base.ConstantValue;
import com.zixueku.customview.FileImageUpload;
import com.zixueku.customview.WheelView;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.net.URL;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;
import com.zixueku.util.HandlerUtils;
import com.zixueku.widget.CircleImageView;

@ContentView(R.layout.activity_user_home)
public class UserHomeActivity extends BaseActivity implements OnCheckedChangeListener {
	private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";/* 头像名称 */

	private Dialog dialog;
	private View dialogView;
	private File tempFile;

	@ViewInject(R.id.gender_button)
	private SegmentedRadioGroup segmentText;

	@ViewInject(R.id.user_icon)
	private CircleImageView mUser_icon;

	@ViewInject(R.id.iv_title_camera)
	private ImageView mTitle_camera_icon;

	@ViewInject(R.id.rl_nike_name)
	private RelativeLayout mRl_nike_name;

	@ViewInject(R.id.rl_age)
	private RelativeLayout mRl_age;

	@ViewInject(R.id.rl_profession)
	private RelativeLayout mRl_profession;

	@ViewInject(R.id.tv_user_name)
	private TextView tv_user_name;

	@ViewInject(R.id.tv_user_number)
	private TextView tv_user_phone;

	@ViewInject(R.id.tv_user_age)
	private TextView tv_user_age;

	@ViewInject(R.id.tv_user_pro)
	private TextView tv_user_pro;

	@ViewInject(R.id.ll_wheel)
	private LinearLayout ll_wheel;

	@ViewInject(R.id.wheel_view_wv)
	private WheelView wheel_view_wv;

	private Button photo_choose_dialog_paizhao;

	private Button photo_choose_dialog_xiangce;

	// private View ageDialogView;
	private static List<String> PLANETS;
	// private WheelView wv;
	private String phone;
	private User user;
	private ImageLoader imageLoader;
	private int pos;
	private int age;

	@Override
	protected void initHandler(Message msg) {
		switch (msg.what) {
		case ConstantValue.USERINFO_SUCCESS:
			user = App.getInstance().getUserInfo();
			userHomeSetData(user);
			break;
		case ConstantValue.USERINFO_ERROR:
			break;
		case ConstantValue.CHANGE_PHOTO_SUCCESS:
			ActionResult<StringMap<String>> actionResult = (ActionResult<StringMap<String>>) msg.obj;
			String icon = (String) actionResult.getRecords().get("url");
			user.setIcon(icon);
			updateLocalUserInfor("imageUrl", icon);
			if (icon != null) {
				imageLoader.displayImage(icon, mUser_icon);
			}
			break;
		case ConstantValue.CHANGE_PHOTO_ERROR:
			showToast("网络链接错误");
			break;
		}
	}

	@Override
	protected void initData() {
		User user = App.getInstance().getUserInfo();
		imageLoader = ImageLoader.getInstance();
		userHomeSetData(user);
		PLANETS = new ArrayList<String>();
		for (int i = 0; i <= 100; i++) {
			PLANETS.add(String.valueOf(i));
		}
		loadView();
		segmentText.setOnCheckedChangeListener(this);
		if (user.getGender() == 0) {
			segmentText.check(R.id.boy);
		} else if (user.getGender() == 1) {
			segmentText.check(R.id.gril);
		}

	}

	@OnClick(R.id.user_icon)
	private void titleImageClick(View view) {
		LogUtils.d("点击选择头像");
		if (dialog != null) {
			dialog.show();
		} else {
			showCameraDialog();
		}
	}

	@OnClick(R.id.go_back)
	private void goBackButtonClick(View view) {
		CommonTools.finishActivity(this);
	}

	private void userHomeSetData(User user) {
		String nikeName = user.getScreen_name();
		phone = user.getPhone();
		Short gender = user.getGender();
		String professional = user.getProfessional();
		String icon = user.getIcon();
		age = user.getAge();
		if (nikeName != null) {
			tv_user_name.setText(nikeName);
		}
		if (phone != null) {
			tv_user_phone.setText(phone);
		}
		if (professional != null) {
			tv_user_pro.setText(professional);
		}
		if (!"".equals(icon)) {
			imageLoader.displayImage(icon, mUser_icon);
		}
		if (age > 0) {
			tv_user_age.setText(age + "");
		}

	}

	private void loadView() {
		// 拍照对话框
		dialogView = View.inflate(UserHomeActivity.this, R.layout.dialog_photo_choose, null);
		// 年龄滚轮对话框
		initDialogId();
	}

	private void initDialogId() {
		wheel_view_wv.setOffset(2);
		wheel_view_wv.setItems(PLANETS);
		photo_choose_dialog_paizhao = (Button) dialogView.findViewById(R.id.photo_choose_dialog_paizhao);
		photo_choose_dialog_xiangce = (Button) dialogView.findViewById(R.id.photo_choose_dialog_xiangce);
		dialogClick();
	}

	/**
	 * 按钮点击事件
	 */
	private void dialogClick() {
		photo_choose_dialog_xiangce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 激活系统图库，选择一张图片
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
				startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
				dialog.dismiss();
			}
		});
		photo_choose_dialog_paizhao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 激活相机
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				tempFile = new File(GetDefaultStorageFolder(context).getAbsolutePath(), PHOTO_FILE_NAME);
				// 从文件中创建uri
				Uri uri = Uri.fromFile(tempFile);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
				startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
				dialog.dismiss();
			}
		});
		wheel_view_wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
			@Override
			public void onSelected(int selectedIndex, String item) {
				pos = selectedIndex;
				tv_user_age.setText(item);
				user.setAge(Integer.valueOf(item));
			}
		});
	}

	@OnClick(R.id.rl_nike_name)
	private void nikeNameClick(View view) {
		startedActivity(ReNikeNameActivity.class);
	}

	@Override
	protected void onResume() {
		super.onResume();
		user = ((App) getApplication()).getUserInfo();
		userHomeSetData(user);
	}

	@OnClick(R.id.rl_age)
	private void userAgeClick(View view) {
		ll_wheel.setVisibility(View.VISIBLE);
		if (age < 0) {
			pos = 20;
		} else {
			pos = user.getAge();
		}
		wheel_view_wv.setSeletion(pos);
	}

	@OnClick(R.id.rl_ok)
	private void ageOkClick(View view) {
		ll_wheel.setVisibility(View.GONE);
		upDataUserInfo("age", user.getAge());
	}

	/**
	 * 若绑定过 启动 ReUserChangeBindPhoneActivity
	 * 
	 * 若未绑定过 启动 ReUserBindPhoneActivity
	 */
	@OnClick(R.id.rl_phone_number)
	private void userPhoneNumberClick(View view) {
		// TODO 需要进行判断 是否绑定过手机
		if (phone == null) {
			startedActivity(ReUserBindPhoneActivity.class);
		} else {
			startedActivity(ReUserChangeBindPhoneActivity.class);
		}

	}

	@OnClick(R.id.rl_profession)
	private void userProClick(View view) {
		startedActivity(ReUserProActivity.class);
	}

	/**
	 * 需要开启的activity的 字节码文件
	 * 
	 * @param clazz
	 */
	private void startedActivity(Class<? extends android.app.Activity> clazz) {
		Intent intent = new Intent(UserHomeActivity.this, clazz);
		startActivity(intent);
	}

	@OnClick(R.id.iv_title_camera)
	public void imageOnClick(View view) {
		LogUtils.d("点击选择头像");
		if (dialog != null) {
			dialog.show();
		} else {
			showCameraDialog();
		}
	}

	private void showCameraDialog() {
		dialog = new Dialog(UserHomeActivity.this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(dialogView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		final StringBuilder sb = new StringBuilder();
		sb.append(URL.ServerPath);
		sb.append("ssoUserUploadImage?user.id=");
		sb.append(user.getUserId());

		if (requestCode == PHOTO_REQUEST_GALLERY) {
			// 从相册返回的数据
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				crop(uri);
			}

		} else if (requestCode == PHOTO_REQUEST_CAREMA) {
			// 从相机返回的数据
			crop(Uri.fromFile(tempFile));
		} else if (requestCode == PHOTO_REQUEST_CUT) {
			// 从剪切图片返回的数据
			if (data != null) {
				Bitmap bitmap = data.getParcelableExtra("data");
				// this.iv_image.setImageBitmap(bitmap);
				final File file = saveBitmap(PHOTO_FILE_NAME, bitmap);
				new Thread(new Runnable() {
					@Override
					public void run() {
						String uploadFile = FileImageUpload.uploadFile(file, sb.toString());
						Gson gson = new Gson();
						ActionResult<StringMap<String>> actionResult = gson.fromJson(uploadFile, new TypeToken<ActionResult<StringMap<String>>>() {
						}.getType());
						if (actionResult.getStatus().equals("success")) {
							HandlerUtils.sendMessage(UserHomeActivity.this, ConstantValue.CHANGE_PHOTO_SUCCESS, actionResult);
						} else {
							HandlerUtils.sendMessage(UserHomeActivity.this, ConstantValue.CHANGE_PHOTO_ERROR, actionResult);
						}
						try {
							// 将临时文件删除
							tempFile.delete();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void upDataUserInfo(final String name, final int value) {
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		ActionResult actionResult = new ActionResult() {
		};
		requestData.put(name, value);
		Request req = new Request(R.string.UserUpdate, requestData, UserHomeActivity.this, actionResult);
		netDataConnationNoProgressDialog(req, new ServerDataCallback<ActionResult>() {
			@Override
			public void processData(ActionResult paramObject, boolean paramBoolean) {
				String status = paramObject.getStatus();
				if (status.equals("success")) {
					updateLocalUserInfor(name, value);
				} else if (status.equals("error")) {

				}
			}
		});
	}

	/*
	 * 剪切图片
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);

		intent.putExtra("outputFormat", "JPEG");// 图片格式
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	public File saveBitmap(String fileName, Bitmap mBitmap) {
		File f = new File(GetDefaultStorageFolder(context).getAbsolutePath(), fileName);
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

	public File GetDefaultStorageFolder(Context context) {
		File storageFolder = context.getExternalFilesDir(null);
		if (storageFolder == null) {
			storageFolder = context.getFilesDir();
		}
		return storageFolder;
	}

	public void updateLocalUserInfor(String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.USER_FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void updateLocalUserInfor(String key, int value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.USER_FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// gender(0男1女)
		short gender = 0;
		if (group == segmentText) {
			if (checkedId == R.id.boy) {
				gender = 0;
			} else if (checkedId == R.id.gril) {
				gender = 1;
			}
			if (user != null) {
				upDataUserInfo("gender", gender);
				user.setGender(gender);
			}
		}
	}

	@Override
	public void onBackPressed() {
		if (ll_wheel.getVisibility() == View.VISIBLE) {
			ll_wheel.setVisibility(View.GONE);
		} else {
			super.onBackPressed();
		}
	}
}
