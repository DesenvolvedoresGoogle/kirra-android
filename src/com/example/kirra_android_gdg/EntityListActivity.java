package com.example.kirra_android_gdg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.abstratt.kirra.Entity;

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
 * {@link EntityListFragment.EntitySelectedCallback} interface to listen for item selections.
 */
public class EntityListActivity extends FragmentActivity implements
		EntityListFragment.EntitySelectedCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entity_list);
	}

	/**
	 * Callback method from {@link EntityListFragment.EntitySelectedCallback} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Entity entity) {
		// In single-pane mode, simply start the detail activity
		// for the selected item ID.
		Intent detailIntent = new Intent(this, EntityDetailActivity.class);
		detailIntent.putExtra(EntityDetailFragment.ARG_ITEM_ID, entity);
		startActivity(detailIntent);
	}
}
