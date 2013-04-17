package com.ulewo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.adapter.ArticleListAdapter;
import com.ulewo.bean.Article;
import com.ulewo.bean.ArticleList;
import com.ulewo.bean.Task;
import com.ulewo.enums.TaskType;
import com.ulewo.logic.MainService;
import com.ulewo.util.Constants;

public class ArticleActivity extends BaseActivity implements IMainActivity {

	private LinearLayout progressBar = null;

	private ArticleListAdapter adapter = null;

	private View loadMoreView = null;

	private int page = 0;

	private TextView loadmoreTextView = null;

	private LinearLayout loadmore_prgressbar = null;

	ListView listView = null;

	private ImageButton refreshBtn = null;

	private static final int RESULTCODE_SUCCESS = 200;

	private static final int RESULTCODE_FAIL = 400;

	private Handler handler = null;

	private AppContext appContext;

	boolean isRefresh = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.article);
		ExitApplication.getInstance().addActivity(this);
		appContext = (AppContext) getApplication();
		initView();
		initData();
	}

	private void initView() {

		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_article);

		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);

		loadMoreView = View.inflate(this, R.layout.loadmore, null);

		listView = (ListView) findViewById(R.id.article_list_view_id);
		listView.addFooterView(loadMoreView);

		loadmore_prgressbar = (LinearLayout) findViewById(R.id.loadmore_progressbar);
		loadmoreTextView = (TextView) findViewById(R.id.loadmoretextview);
		loadmoreTextView.setVisibility(View.GONE);
		loadmoreTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				loadmoreTextView.setVisibility(View.GONE);
				loadmore_prgressbar.setVisibility(View.VISIBLE);
				Intent service = new Intent(ArticleActivity.this,
						MainService.class);
				startService(service);
				HashMap<String, Object> param = new HashMap<String, Object>(1);
				param.put("page", ++page);
				Task task = new Task(TaskType.QUERYARTICLES, param,
						ArticleActivity.this);
				MainService.newTask(task);
			}
		});

		refreshBtn = (ImageButton) findViewById(R.id.head_refresh);
		refreshBtn.setVisibility(View.VISIBLE);
		refreshBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				progressBar.setVisibility(View.VISIBLE);
				page = 1;
				isRefresh = true;
				initData();
			}
		});
	}

	private void initData() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				progressBar.setVisibility(View.GONE);
				if (msg.what != -1) {
					ArticleList list = (ArticleList) msg.obj;
					if (adapter == null || page == 1) {
						adapter = new ArticleListAdapter(ArticleActivity.this,
								list.getArticleList());
						listView.setAdapter(adapter);
						if (page < msg.arg1) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					} else {
						loadmore_prgressbar.setVisibility(View.GONE);
						adapter.loadMore(list.getArticleList());
						if (page < msg.arg1) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}
					listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								View view, int postion, long id) {

							String articleId = String.valueOf(adapter
									.getItemId(postion));
							if (!"0".equals(articleId)) {
								Intent intent = new Intent();
								intent.putExtra("articleId", articleId);
								intent.setClass(ArticleActivity.this,
										ShowArticleActivity.class);
								startActivity(intent);
							}
						}
					});
				} else {
					((AppException) msg.obj).makeToast(ArticleActivity.this);
					progressBar.setVisibility(View.GONE);
					loadmoreTextView.setVisibility(View.VISIBLE);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();
				try {
					ArticleList list = appContext.getArticleList(page,
							isRefresh);
					msg.what = 0;
					msg.obj = list;
				} catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... obj) {

		progressBar.setVisibility(View.GONE);
		refreshBtn.clearAnimation();
		HashMap<String, Object> myobj = (HashMap<String, Object>) obj[0];
		if (null != myobj.get("list")
				&& Constants.RESULTCODE_SUCCESS.equals(String.valueOf(myobj
						.get("result")))) {
			List<Article> list = (ArrayList<Article>) myobj.get("list");
			if (adapter == null || page == 1) {
				adapter = new ArticleListAdapter(this, list);
				listView.setAdapter(adapter);
				if (page < Integer.parseInt(myobj.get("pageTotal").toString())) {
					loadmoreTextView.setVisibility(View.VISIBLE);
				}
			} else {
				loadmore_prgressbar.setVisibility(View.GONE);
				adapter.loadMore(list);
				if (page < Integer.parseInt(myobj.get("pageTotal").toString())) {
					loadmoreTextView.setVisibility(View.VISIBLE);
				}
			}
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int postion, long id) {

					String articleId = String.valueOf(adapter
							.getItemId(postion));
					if (!"0".equals(articleId)) {
						Intent intent = new Intent();
						intent.putExtra("articleId", articleId);
						intent.setClass(ArticleActivity.this,
								ShowArticleActivity.class);
						startActivity(intent);
					}
				}
			});
		} else {
			Toast.makeText(ArticleActivity.this, R.string.request_timeout,
					Toast.LENGTH_LONG).show();
			progressBar.setVisibility(View.GONE);
			loadmoreTextView.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isExit();
		}
		return super.onKeyDown(keyCode, event);
	}

}
