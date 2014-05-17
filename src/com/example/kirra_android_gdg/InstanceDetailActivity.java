package com.example.kirra_android_gdg;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abstratt.kirra.Entity;
import com.abstratt.kirra.Instance;

public class InstanceDetailActivity extends Activity {

	public static final String ARG_INSTANCE_DATA = "instance_data";
	private Instance instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instance_detail);

		if (savedInstanceState != null) {
			Instance serializable = (Instance) savedInstanceState
					.getSerializable(ARG_INSTANCE_DATA);
			this.instance = serializable;
		} else {
			Intent intent = getIntent();
			if (intent != null) {
				Instance serializable = (Instance) intent
						.getSerializableExtra(ARG_INSTANCE_DATA);
				this.instance = serializable;
			}
		}

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.instance_layout);

		Set<Entry<String, Object>> values = instance.getValues().entrySet();
		Integer lastId = null;
		for (Entry<String, Object> entry : values) {
			TextView fieldName = new TextView(this);
			fieldName.setText(entry.getKey());
			LayoutParams params = new LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

			if (lastId != null) {
				params.addRule(RelativeLayout.BELOW, lastId);
			}
			lastId = View.generateViewId();
			fieldName.setId(lastId);

			fieldName.setLayoutParams(params);
			layout.addView(fieldName);

			if (entry.getValue() != null) {
				TextView value = new TextView(this);
				value.setText(entry.getValue().toString());
				LayoutParams valueParams = new LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				value.setLayoutParams(valueParams);
				valueParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				valueParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				valueParams.addRule(RelativeLayout.RIGHT_OF, fieldName.getId());
				if (lastId != null) {
					valueParams.addRule(RelativeLayout.BELOW, lastId);

				}
				lastId = View.generateViewId();
				value.setId(lastId);
				layout.addView(value);
			}
		}
	}
}
