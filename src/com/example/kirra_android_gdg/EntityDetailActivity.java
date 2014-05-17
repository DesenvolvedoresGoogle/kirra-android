package com.example.kirra_android_gdg;

import java.io.Serializable;

import com.abstratt.kirra.Instance;
import com.example.kirra_android_gdg.EntityDetailFragment.Callbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * An activity representing a single Entity detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link EntityListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link EntityDetailFragment}.
 */
public class EntityDetailActivity extends FragmentActivity implements Callbacks {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entity_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			Serializable serializableExtra = getIntent().getSerializableExtra(EntityDetailFragment.ARG_ITEM_ID);
			arguments.putSerializable(EntityDetailFragment.ARG_ITEM_ID, serializableExtra);
			EntityDetailFragment fragment = new EntityDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction().add(R.id.entity_detail_container, fragment).commit();
			
		}
	}
	
	@Override
	public void onItemSelected(Instance instance) {
		// In single-pane mode, simply start the detail activity
		// for the selected item ID.
		Intent detailIntent = new Intent(this, InstanceDetailActivity.class);
		detailIntent.putExtra(InstanceDetailActivity.ARG_INSTANCE_DATA, instance);
		startActivity(detailIntent);
	}
}
