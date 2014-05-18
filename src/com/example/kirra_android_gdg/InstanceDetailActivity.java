package com.example.kirra_android_gdg;

import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.abstratt.kirra.Instance;

public class InstanceDetailActivity extends Activity {

	public static final String ARG_INSTANCE_DATA = "instance_data";
	private Instance instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instance_detail);
		getActionBar().setDisplayHomeAsUpEnabled(true);

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
			fieldName.setTextSize(16);
			fieldName.setText(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(entry.getKey())), " ") + ":");
			LayoutParams labelParams = new LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			labelParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

			Integer belowAnchor = lastId;
			lastId = View.generateViewId();
			fieldName.setId(lastId);

			TextView value = new TextView(this);
			value.setTypeface(value.getTypeface(), Typeface.BOLD);
			value.setTextSize(16);
			value.setText(entry.getValue() == null ? "" : entry.getValue().toString());
			LayoutParams valueParams = new LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			valueParams.leftMargin = 5;
			valueParams.addRule(RelativeLayout.RIGHT_OF, lastId);
			if (belowAnchor != null) {
				labelParams.addRule(RelativeLayout.BELOW, belowAnchor);
				valueParams.addRule(RelativeLayout.BELOW, belowAnchor);
			}
			
			labelParams.topMargin = 8;
			valueParams.topMargin = 8;
			fieldName.setLayoutParams(labelParams);
			layout.addView(fieldName);
			value.setLayoutParams(valueParams);
			layout.addView(value);
		}
	}
}
