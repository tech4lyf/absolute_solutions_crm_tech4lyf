package com.tech4lyf.absolutesolutionscrm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.absolutesolutionscrm.Models.Customer;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomerView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomerView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String key;

    private OnFragmentInteractionListener mListener;

    public CustomerView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerView.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerView newInstance(String param1, String param2) {
        CustomerView fragment = new CustomerView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        databaseReference = FirebaseDatabase.getInstance().getReference();


        View root= inflater.inflate(R.layout.fragment_customer_view, container, false);



        return root;
    }

    private void getCustfromDB() {
        databaseReference.child("Customers").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Customer> adsList = new ArrayList<Customer>();
                for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {
                    adsList.add(adSnapshot.getValue(Customer.class));
                }
                Log.d(TAG, "no of ads for search is "+adsList.size());

                RVAdapterCust recyclerViewAdapter = new
                        RVAdapterCust(adsList, getActivity());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
               /*Log.d(TAG, "Error trying to get classified ads for " +category+
                        " "+databaseError);
                Toast.makeText(getActivity(),
                        "Error trying to get classified ads for " +category,
                        Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
