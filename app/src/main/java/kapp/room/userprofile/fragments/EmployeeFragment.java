package kapp.room.userprofile.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kapp.room.userprofile.R;
import kapp.room.userprofile.adapters.EmployeeAdapter;
import kapp.room.userprofile.db.entity.Employee;
import kapp.room.userprofile.db.entity.Employees;
import kapp.room.userprofile.db.viewmodel.UIViewModel;


public class EmployeeFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnEmployeeFragmentInteractionListener mListener;

    List<Employees> list;
    RecyclerView mRecyclerView;
    EmployeeAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    FloatingActionButton fabAdd;

    UIViewModel mUIViewModel;

    public EmployeeFragment() {
        // Required empty public constructor
    }

    public static EmployeeFragment newInstance(String param1, String param2) {
        EmployeeFragment fragment = new EmployeeFragment();
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
        return inflater.inflate(R.layout.fragment_employee, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        fabAdd = view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUIViewModel = ViewModelProviders.of(this).get(UIViewModel.class);
        list = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mAdapter = new EmployeeAdapter(getActivity(), list);
        mRecyclerView.setAdapter(mAdapter);

        mUIViewModel.getAllEmployees().observe(this, new Observer<List<Employees>>() {
            @Override
            public void onChanged(@Nullable List<Employees> employees) {
                mAdapter.addItems(employees);
            }
        });
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onEmployeeFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEmployeeFragmentInteractionListener) {
            mListener = (OnEmployeeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDeptFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (v == fabAdd) onButtonPressed();
    }

    public interface OnEmployeeFragmentInteractionListener {
        void onEmployeeFragmentInteraction();
    }
}
