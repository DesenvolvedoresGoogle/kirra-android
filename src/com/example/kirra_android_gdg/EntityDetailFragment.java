package com.example.kirra_android_gdg;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.abstratt.kirra.Entity;
import com.abstratt.kirra.Instance;
import com.abstratt.kirra.Property;
import com.abstratt.kirra.rest.client.InstanceManagementOnREST;
import com.abstratt.kirra.rest.client.SchemaManagementOnREST;

/**
 * A fragment representing a single Entity detail screen. This fragment is
 * either contained in a {@link EntityListActivity} in two-pane mode (on
 * tablets) or a {@link EntityDetailActivity} on handsets.
 */
public class EntityDetailFragment extends ListFragment {

	private static final String STATE_ACTIVATED_POSITION = "activated_position";
	private int mActivatedPosition = ListView.INVALID_POSITION;
	
	private static class InstanceData {
		String label;
		String objectId;

		public InstanceData(String objectId, String label) {
			this.objectId = objectId;
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}
	}

	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	private List<Instance> instances;
	private List<InstanceData> instanceInfo;

	private Instance selectedInstance;
	/**
	 * The dummy content this fragment is presenting.
	 */
	private Entity entity;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public EntityDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			Entity serializable = (Entity) savedInstanceState
					.getSerializable(ARG_ITEM_ID);
			this.entity = serializable;
		} else {
			Intent intent = getActivity().getIntent();
			if (intent != null) {
				Entity serializable = (Entity) intent
						.getSerializableExtra(ARG_ITEM_ID);
				this.entity = serializable;
			}
		}
		if (this.entity != null) {
			new AsyncTask<Void, Void, List<Instance>>() {
				@Override
				protected List<Instance> doInBackground(Void... params) {
					URI restUri = URI
							.create("http://develop.cloudfier.com/services/api/demo-cloudfier-examples-");
					InstanceManagementOnREST instanceManagement = new InstanceManagementOnREST(
							restUri);
					return instances = instanceManagement.getInstances(
							entity.getEntityNamespace(), entity.getName(), false);
				}

				@Override
				protected void onPostExecute(List<Instance> result) {
					List<InstanceData> instanceInfo = new ArrayList<InstanceData>(
							result.size());
					
						for (Instance instance : result)
							instanceInfo.add(new InstanceData(instance.getObjectId(), (String) instance.getShorthand()));
						setListAdapter(new ArrayAdapter<InstanceData>(
						getActivity(),
						android.R.layout.simple_list_item_activated_1,
						android.R.id.text1, instanceInfo));
					
				}
			}.execute();
		}

	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}	
}
