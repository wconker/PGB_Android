package com.android.pinggubang.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pinggubang.R;


/**
 * 鑷畾涔塨ar鎺т欢锛� 鍏辨湁7涓睘鎬э紝 鍒嗗埆璁剧疆宸﹀彸Button鐨勬枃鏈紝鑳屾櫙锛屽彲瑙佹�э紝 涓�涓缃畉itle
 * 鍙傝values鏂囦欢澶逛腑attrs涓璫ustom_bar瀹氫箟鐨勫睘鎬�, 妗堜緥鍙傝activity_register涓�
 * 
 * 涔熻鐪嬬湅CBarView锛屽叾灏佽浜咰ustomBar鐨勭浉鍏冲姛鑳�
 * 
 * @author by 宕旀槑寮� at 2014骞�9鏈�19鏃�
 * 
 */
public class CustomBar extends RelativeLayout implements OnClickListener {
	public Button mLeft;
	public Button mRight;
	private TextView mTitle;
	private TextView mTop;
	private CBOnClickListener mListener;

	public CustomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(getContext()).inflate(R.layout.view_common_bar,
				this);
		mLeft = (Button) findViewById(R.id.left);
		mRight = (Button) findViewById(R.id.right);
		mTitle = (TextView) findViewById(R.id.title);
		mTop = (TextView) findViewById(R.id.top);

		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		// mTop.setVisibility(View.VISIBLE);
		// } else {
		// mTop.setVisibility(View.GONE);
		// }

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.custom_bar);
		if (array != null) {
			for (int i = 0; i < array.getIndexCount(); i++) {
				int id = array.getIndex(i);
				switch (id) {
				case R.styleable.custom_bar_left_name:
					String leftname = array.getString(id);
					mLeft.setText(leftname);
					
					break;
				case R.styleable.custom_bar_left_img:
					int leftid = array.getResourceId(id, 100);
					mLeft.setBackgroundResource(leftid);
					break;
				case R.styleable.custom_bar_left_visible:
					int leftvisible = array.getInteger(id, 0);
					mLeft.setVisibility(leftvisible);
					break;
				case R.styleable.custom_bar_right_name:
					String rightname = array.getString(id);
					mRight.setText(rightname);
					break;
				case R.styleable.custom_bar_right_img:
					int rightid = array.getResourceId(id, 100);
					mRight.setBackgroundResource(rightid);
					break;
				case R.styleable.custom_bar_right_visible:
					int rightvisible = array.getInteger(id, 0);
					mRight.setVisibility(rightvisible);
					break;
				case R.styleable.custom_bar_titlename:
					String title = array.getString(id);
					mTitle.setText(title);
					break;
				default:
					break;
				}
			}
		}
		array.recycle();
	}

	/**
	 * 娉ㄥ唽涓�涓洃鍚櫒锛岀洃鍚乏鍙矪utton鐨勭偣鍑讳簨浠�
	 * 
	 * @param listener
	 */
	public void setOnClickListener(CBOnClickListener listener) {
		mLeft.setOnClickListener(this);
		mRight.setOnClickListener(this);
		mListener = listener;
	}

	/**
	 * 璁剧疆鏄剧ず鐨勬爣棰�
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		mTitle.setText(title);
		mTitle.setTextSize(R.dimen.text_size_sobig);
	}

	/**
	 * 璁剧疆鍙宠竟鐨凚utton鏄惁鏄剧ず
	 * 
	 * @param visiblity
	 */
	public void setRightVisible(int visibility) {
		mRight.setVisibility(visibility);
	}

	/**
	 * 璁剧疆澶撮儴鐨勭┖闂存槸鍚︽樉绀�
	 * 
	 * @param visiblity
	 */
	public void setTopVisible(int visibility) {
		mTop.setVisibility(visibility);
	}

	/**
	 * 璁剧疆鍙宠竟Button鐨勬枃鏈�
	 * 
	 * @param name
	 */
	public void setRightText(String name) {
		mRight.setText(name);
	}

	/**
	 * 璁剧疆宸﹁竟鐨凚utton鏄惁鏄剧ず
	 * 
	 * @param visibility
	 */
	public void setLeftVisible(int visibility) {
		mLeft.setVisibility(visibility);
	}

	/**
	 * 璁剧疆宸﹁竟Button鐨勬枃鏈�
	 * 
	 * @param name
	 */
	public void setLeftText(String name) {
		mLeft.setText(name);
		
	}





	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			if (mListener != null) {
				mListener.onLeftClick();
			}
			break;
		case R.id.right:
			if (mListener != null) {
				mListener.onRightClick();
			}
			break;
		default:
			break;
		}

	}

	public interface CBOnClickListener {
		/**
		 * 褰撳乏杈圭殑鎸夐挳琚偣鍑绘椂
		 */
		void onLeftClick();

		/**
		 * 鍙宠竟鐨勬寜閽鐐瑰嚮鏃�
		 */
		void onRightClick();
	}
}
