package com.android.pgb.Utils;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.pgb.R;


/**
 * 改善了原来的TabManager,第一次只加载第一个Fragment 当是找货选项卡时，不重新创建，其他选项卡时，重新创建。
 * 显示其他选项卡时，隐藏找货选项卡， 缓解启动时一起加载完所有Fragment的性能的不足
 * 
 * @author xiangzhenwang at 2014年12月5日
 * 
 */

public class TabManager implements OnCheckedChangeListener {
	private List<Fragment> mFragments;
	private FragmentActivity mActivity;
	private RadioGroup mGroup;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mTransaction;
	private Fragment mFindFragment;

	private int currentTab; // 当前Tab页面索引

	public TabManager(List<Fragment> list, FragmentActivity activity) {
		mFragments = list;
		mActivity = activity;
		mGroup = (RadioGroup) activity.findViewById(R.id.tab);
		mGroup.setOnCheckedChangeListener(this);
		mFragmentManager = mActivity.getSupportFragmentManager();
		mTransaction = mFragmentManager.beginTransaction();
		try {
			mFindFragment = (Fragment) mFragments.get(0);
			mTransaction.add(R.id.container, mFindFragment, "0_f");
			mTransaction.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public Fragment getCurrentFragment() {
		return mFragments.get(currentTab);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		for (int i = 0; i < mGroup.getChildCount(); i++) {
			if (mGroup.getChildAt(i).getId() == checkedId) {
				Fragment fragment = mFragments.get(i);
				FragmentTransaction ft = obtainFragmentTransaction(i);
				getCurrentFragment().onPause();
				if (fragment.isAdded()) {
					// fragment.onStart(); // 启动目标tab的onStart()
					fragment.onResume(); // 启动目标tab的onResume()
				} else {
					ft.add(R.id.container, fragment);
				}
				showTab(i); // 显示目标tab
				ft.commit();
			}
		}
	}

	/**
	 * 切换tab
	 * 
	 * @param idx
	 */
	private void showTab(int idx) {
		for (int i = 0; i < mFragments.size(); i++) {
			Fragment fragment = mFragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);

			if (idx == i) {
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			ft.commit();
		}
		currentTab = idx; // 更新目标tab为当前tab
	}

	/**
	 * 获取一个带动画的FragmentTransaction
	 * 
	 * @param index
	 * @return
	 */
	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = mActivity.getSupportFragmentManager()
				.beginTransaction();
		// 设置切换动画
		if (index > currentTab) {
			ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
		} else {
			ft.setCustomAnimations(R.anim.slide_right_in,
					R.anim.slide_right_out);
		}
		return ft;
	}
}
