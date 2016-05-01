package idm.tpf.sinai.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import idm.tpf.sinai.R;
import idm.tpf.sinai.activity.MainActivity;


public class HomeFragment extends Fragment {

	final static  String TAG = "HomeFragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		    Log.d("TAG", "onCreate");
		    super.onCreate(savedInstanceState);
		    setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		//inflater.inflate(R.menu.home,menu);
		//super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_main,menu);

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
			
		   //super.onActivityResult(requestCode, resultCode, data);
		 	Log.d(TAG, "Imagen capturada");
		
			 if (requestCode == MainActivity.CAMERA_PIC_REQUEST && resultCode == getActivity().RESULT_OK) {
				 Log.d("HomeFragment", "Imagen Capturada Exitosamente");
			 }

	}
	

}
