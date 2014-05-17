package com.example.kirra_android_gdg;

import com.abstratt.kirra.Entity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * An activity representing a list of Entities. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link EntityDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link EntityListFragment} and the item details (if present) is a
 * {@link EntityDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link EntityListFragment.Callbacks} interface to listen for item selections.
 */
public class EntityListActivity extends FragmentActivity implements
		EntityListFragment.Callbacks {

	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entity_list);

		if (findViewById(R.id.entity_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((EntityListFragment) getSupportFragmentManager().findFragmentById(
					R.id.entity_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link EntityListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Entity entity) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putSerializable(EntityDetailFragment.ARG_ITEM_ID, entity);
			EntityDetailFragment fragment = new EntityDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.entity_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, EntityDetailActivity.class);
			detailIntent.putExtra(EntityDetailFragment.ARG_ITEM_ID, entity);
			startActivity(detailIntent);
		}
	}
}
