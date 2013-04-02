package com.ulewo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ulewo.R;

public class BlogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.blog);
		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.blog);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_blog);
	}

}