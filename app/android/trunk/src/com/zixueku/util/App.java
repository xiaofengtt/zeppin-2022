package com.zixueku.util;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Material;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.entity.User;
import com.zixueku.entity.WrongBook;
import com.zixueku.util.exception.UncaughtHandler;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-9 下午1:51:11
 */
public class App extends Application {
	public static final String VOLLEY_TAG = "VolleyPatterns";
	private RequestQueue mRequestQueue;
	private User userInfo;
	private TestAbilityChange testAbilityChange;
	private Exercise exercise;
	private PrepareExam prepareExam;
	private Material material;// 材料题
	private WrongBook wrongBookInfo; // 错题本信息
	private static App instance;

	public static App getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		ImageLoaderConfig.initImageLoader(getApplicationContext());
		userInfo = new User();
		mRequestQueue = Volley.newRequestQueue(this);
		initCrashHandler();
	}

	public User getUserInfo() {
		return userInfo;
	}

	public TestAbilityChange getTestAbilityChange() {
		return testAbilityChange;
	}

	public void setTestAbilityChange(TestAbilityChange testAbilityChange) {
		this.testAbilityChange = testAbilityChange;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public PrepareExam getPrepareExam() {
		return prepareExam;
	}

	public void setPrepareExam(PrepareExam prepareExam) {
		this.prepareExam = prepareExam;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public WrongBook getWrongBookInfo() {
		return wrongBookInfo;
	}

	public void setWrongBookInfo(WrongBook wrongBookInfo) {
		this.wrongBookInfo = wrongBookInfo;
	}

	public RequestQueue getmRequestQueue() {
		return mRequestQueue;
	}

	public void initCrashHandler() {
		// 设置该CrashHandler为程序的默认处理器
		UncaughtHandler catchExcep = new UncaughtHandler(this);
		Thread.setDefaultUncaughtExceptionHandler(catchExcep);
	}

}
