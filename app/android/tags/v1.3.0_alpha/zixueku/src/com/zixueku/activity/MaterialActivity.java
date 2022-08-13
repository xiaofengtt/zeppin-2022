package com.zixueku.activity;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.adapter.MaterialPagerAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Item;
import com.zixueku.entity.Material;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.fragment.MaterialCardFragment;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;

/**
 * 类说明 大题特训
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月17日 下午1:13:15
 */
public class MaterialActivity extends AbstractAsyncActivity implements OnClickListener {
	private ViewPager viewPager;
	private ImageButton goBack;
	private TextView title;
	private int position;
	private int subjectId;
	private int itemTypeId;
	private Material material;
	// 选项卡按钮
	private ImageButton cardButton;
	private User user;
	private ActionResult<List<Item>> actionResult;
	private Fragment fragment;
	// 选项卡开关标志
	private boolean showAssert = false;
	private String titleStr;

	public static HashMap<Integer, ViewPager> subViewPagers = new HashMap<Integer, ViewPager>();

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_material);
	}

	@Override
	protected void findViewById() {
		Intent intent = getIntent();
		viewPager = (ViewPager) findViewById(R.id.material_view_pager);
		user = ((App) getApplication()).getUserInfo();
		this.position = intent.getIntExtra("position", -1);
		this.subjectId = intent.getIntExtra("subjectId", -1);
		this.itemTypeId = intent.getIntExtra("itemTypeId", -1);
		this.titleStr = intent.getStringExtra("subjectName");
		this.cardButton = (ImageButton) findViewById(R.id.image_card);
		this.goBack = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		this.title = (TextView) findViewById(R.id.material_action_bar_title);
		actionResult = new ActionResult<List<Item>>() {
		};
	}

	@Override
	protected void setListener() {
		cardButton.setOnClickListener(this);
		goBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (showAssert) {
					hideTheAssert();
				} else {
					CommonTools.finishActivity(MaterialActivity.this);
				}
			}
		});
	}

	@Override
	protected void processLogic() {
		this.title.setText(titleStr);
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("user.id", user.getUserId());
		requestData.put("subject.id", subjectId);
		requestData.put("itemType.id", itemTypeId);
		Request request = new Request(R.string.UserTestItemList, requestData, this, actionResult);
		getDataFromServer(request, new ServerDataCallback<ActionResult<List<Item>>>() {
			@Override
			public void processData(ActionResult<List<Item>> paramObject, boolean paramBoolean) {
				material = new Material();
				material.setMaterialItems(paramObject.getRecords());
				createIndex();
				((App) getApplication()).setMaterial(material);
				viewPager.setAdapter(new MaterialPagerAdapter(getSupportFragmentManager(), paramObject.getRecords()));
			}
		});
	}

	private void createIndex() {
		int index = 0;
		for (Item item : material.getMaterialItems()) {
			if (item.getModelType() == 4) {
				for (Item child : item.getChildren()) {
					child.setIndex(index);
					index++;
				}
			} else {
				item.setIndex(index);
				index++;
			}
		}
		material.setTotalNum(index);
	}

	@Override
	public void onClick(View v) {
		if (showAssert) {
			hideTheAssert();
		} else {
			showCardFragment();
		}
	}

	private void showCardFragment() {
		fragment = new MaterialCardFragment(viewPager);
		getSupportFragmentManager().beginTransaction().add(R.id.root, fragment).commit();
		showAssert = true;
	}

	/**
	 * 
	 */
	public void hideTheAssert() {
		if (fragment != null) {
			getSupportFragmentManager().beginTransaction().remove(fragment).commit();
			fragment = null;
		}
		showAssert = false;
	}

	@Override
	public void onBackPressed() {
		if (showAssert) {
			hideTheAssert();
		} else {
			super.onBackPressed();
		}
	}

}
