package com.zixueku.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.special.ResideMenu.ResideMenu;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.activity.MainActivity;
import com.zixueku.adapter.AdapterCycle;
import com.zixueku.adapter.PrepareExamsAdapter;
import com.zixueku.entity.ADInfo;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.ParserType;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.Request;
import com.zixueku.listerner.PrepareExamsItemClickListener;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.NetThreadUtil;
import com.zixueku.widget.AbScrollView;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年8月28日 上午11:32:51
 */
public class HomeFragment extends BaseFragment {
	// viewpager auto play
	private static final long ANIM_VIEWPAGER_DELAY = 3000;
	private View parentView;
	private ResideMenu resideMenu;
	private SwipeMenuListView prepareExamsList;
	private List<PrepareExam> prepareExamsListData;
	private AbScrollView noFouceLayout;
	private ActionResult<ArrayList<PrepareExam>> actionResult;
	private PrepareExamsAdapter prepareExamsAdapter;
	private ViewPager mViewpage;
	private CircleIndicator viewPagerIndicator;
	private List<ADInfo> listAc;
	
	public HomeFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.home, container, false);
		prepareExamsList = (SwipeMenuListView) parentView.findViewById(R.id.prepare_exams_list);
		noFouceLayout = (AbScrollView) parentView.findViewById(R.id.no_focus_layout);
		mViewpage = (ViewPager) parentView.findViewById(R.id.viewpage);
		viewPagerIndicator = (CircleIndicator) parentView.findViewById(R.id.indicator_default);
		actionResult = new ActionResult<ArrayList<PrepareExam>>() {
		};
		initPager();
		setUpViews();
		return parentView;
	}

	private void setUpViews() {
		MainActivity parentActivity = (MainActivity) mActivity;
		resideMenu = parentActivity.getResideMenu();
		// add gesture operation's ignored views
		RelativeLayout ignored_view = (RelativeLayout) parentView.findViewById(R.id.ad_layout);
		resideMenu.addIgnoredView(ignored_view);
		resideMenu.addIgnoredView(prepareExamsList);
		processLogic();
	}

	protected void processLogic() {
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		Request request = new Request(R.string.UserSubjectList, requestData, mActivity, actionResult);
		NetThreadUtil.sendDataToServerWithProgressDialog(request, new ServerDataCallback<ActionResult<ArrayList<PrepareExam>>>() {
			@Override
			public void processData(ActionResult<ArrayList<PrepareExam>> paramObject, boolean paramBoolean) {
				prepareExamsListData = paramObject.getRecords();
				if (prepareExamsListData != null && !prepareExamsListData.isEmpty()) {
					noFouceLayout.setVisibility(View.GONE);
					prepareExamsAdapter = new PrepareExamsAdapter(mActivity, prepareExamsListData);
					prepareExamsList.setAdapter(prepareExamsAdapter);
					prepareExamsList.setOnItemClickListener(new PrepareExamsItemClickListener(mActivity));
					createSideMunu();
				} else {
					noFouceLayout.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	protected void createSideMunu() {
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(mActivity);
				// set item background
				// deleteItem.setBackground(new
				// StateListDrawable(R.drawable.holder_bg));
				StateListDrawable states = new StateListDrawable();
				states.addState(new int[] { android.R.attr.state_pressed }, getResources().getDrawable(R.color.darkred));
				states.addState(new int[] { android.R.attr.state_focused }, getResources().getDrawable(R.color.darkred));
				states.addState(new int[] {}, getResources().getDrawable(R.color.red));
				deleteItem.setBackground(states);
				// set item width
				deleteItem.setWidth(CommonTools.dip2px(mActivity, 75));
				// set a icon
				deleteItem.setIcon(R.drawable.del_icon);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};

		// set creator
		prepareExamsList.setMenuCreator(creator);

		// step 2. listener item click event
		prepareExamsList.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					// delete
					HashMap<String, Object> requestData = new HashMap<String, Object>();
					requestData.put("subject.id", prepareExamsListData.get(position).getSubjectid());
					Request request = new Request(R.string.UserSubjectDelete, requestData, mActivity, ParserType.USER_DEFINED, ActionResult.class);
					NetThreadUtil.sendDataToServerNoProgressDialog(request, new ServerDataCallback<ActionResult<?>>() {
						@Override
						public void processData(ActionResult<?> paramObject, boolean paramBoolean) {
							if ("success".equals(paramObject.getStatus())) {
								prepareExamsListData.remove(position);
								prepareExamsAdapter.notifyDataSetChanged();
								CommonTools.showShortToastDefaultStyle(mActivity, paramObject.getMessage());
								if (prepareExamsListData.size() == 0) {
									noFouceLayout.setVisibility(View.VISIBLE);
								}
								App.getInstance().setPrepareExam(null);
							}
						}
					});
					break;
				}
				return false;
			}
		});
	}

	private Handler h = new Handler();
	private Runnable animateViewPager = new Runnable() {
		public void run() {
			if (listAc != null && listAc.size() > 1) {
				mViewpage.setCurrentItem((mViewpage.getCurrentItem() + 1) % listAc.size(), true);
				h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
			}
		}
	};

	private void initPager() {
		ActionResult<ArrayList<ADInfo>> activityList = new ActionResult<ArrayList<ADInfo>>() {
		};
		Request request = new Request(R.string.ActivityGetList, mActivity, activityList);
		NetThreadUtil.sendDataToServerNoProgressDialogIgnoError(request, new ServerDataCallback<ActionResult<ArrayList<ADInfo>>>() {
			@Override
			public void processData(ActionResult<ArrayList<ADInfo>> paramObject, boolean paramBoolean) {
				if (paramObject == null) {
					return;
				}
				listAc = paramObject.getRecords();
				if (listAc != null && !listAc.isEmpty()) {
					AdapterCycle mAdapter = new AdapterCycle(((FragmentActivity) mActivity).getSupportFragmentManager(), listAc);
					mViewpage.setAdapter(mAdapter);
					viewPagerIndicator.setViewPager(mViewpage);
					// h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
				} else {
				}
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		if (h != null) {
			h.removeCallbacks(animateViewPager);
		}
	}

	public void updateList() {
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		Request request = new Request(R.string.UserSubjectList, requestData, mActivity, actionResult);
		NetThreadUtil.sendDataToServerWithProgressDialog(request, new ServerDataCallback<ActionResult<ArrayList<PrepareExam>>>() {
			@Override
			public void processData(ActionResult<ArrayList<PrepareExam>> paramObject, boolean paramBoolean) {
				if (paramObject == null) {
					return;
				}
				prepareExamsListData = paramObject.getRecords();
				if (prepareExamsListData != null && !prepareExamsListData.isEmpty()) {
					noFouceLayout.setVisibility(View.GONE);
					prepareExamsAdapter = new PrepareExamsAdapter(mActivity, prepareExamsListData);
					prepareExamsList.setAdapter(prepareExamsAdapter);
					prepareExamsList.setOnItemClickListener(new PrepareExamsItemClickListener(mActivity));
					createSideMunu();
				} else {
					noFouceLayout.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
	}

	public List<PrepareExam> getPrepareExamsListData() {
		return prepareExamsListData;
	}

	public PrepareExamsAdapter getPrepareExamsAdapter() {
		return prepareExamsAdapter;
	}

}
