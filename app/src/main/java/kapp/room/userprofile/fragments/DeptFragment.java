package kapp.room.userprofile.fragments;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kapp.room.userprofile.R;
import kapp.room.userprofile.adapters.DeptAdapter;
import kapp.room.userprofile.db.entity.Dept;
import kapp.room.userprofile.db.viewmodel.UIViewModel;
import kapp.room.userprofile.listener.RecyclerItemClickListener;

public class DeptFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnDeptFragmentInteractionListener mListener;
    private static final String TAG = "DeptFragment";

    EditText edDeptName;
    Button btnDeptAdd;
    RecyclerView mRecyclerView;
    List<Dept> list;
    LinearLayoutManager mLayoutManager;
    DeptAdapter mAdapter;
    private UIViewModel mUIViewModel;


    public DeptFragment() {
        // Required empty public constructor
    }

    public static DeptFragment newInstance(String param1, String param2) {
        DeptFragment fragment = new DeptFragment();
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
        return inflater.inflate(R.layout.fragment_dept, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edDeptName = view.findViewById(R.id.edDeptName);
        btnDeptAdd = view.findViewById(R.id.btnDeptAdd);
        btnDeptAdd.setOnClickListener(this);
        mRecyclerView = view.findViewById(R.id.mRecyclerView);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUIViewModel = ViewModelProviders.of(this).get(UIViewModel.class);
        list = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mAdapter = new DeptAdapter(list);
        mRecyclerView.setAdapter(mAdapter);


        mUIViewModel.getAllDept().observe(this, new Observer<List<Dept>>() {
            @Override
            public void onChanged(@Nullable List<Dept> deptList) {
                list.clear();
                list.addAll(deptList);
                mAdapter.addItems(list);
            }
        });


        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = list.get(position).getDeptId();
                String name = list.get(position).getDeptName();
                updateDialog(id, name);
                Log.d(TAG, "setOnUserItemClick: id : " + id + " name : " + name + " position : " + position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                showDeleteAlert(position);
            }
        }));
    }

    private void updateDialog(final int id, final String name) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add);

        final EditText edUser = dialog.findViewById(R.id.edUser);
        edUser.setText(name);
        edUser.setSelection(name.length());
        Button btnUpdate = dialog.findViewById(R.id.btnAdd);
        btnUpdate.setText("Update");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edUser.getText().toString();
                if (name != null && name.length() != 0) {

                    Dept dept = new Dept();
                    dept.setDeptId(id);
                    dept.setDeptName(name);
                    int result = mUIViewModel.updateDept(dept);
                    Toast.makeText(getActivity(), "" + result, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                } else
                    Toast.makeText(getActivity(), "Please Enter the Name", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void showDeleteAlert(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to delete");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete(position);
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void delete(int position) {
       int result = mUIViewModel.deleteDept(list.get(position));
        Toast.makeText(getActivity(), "" + result, Toast.LENGTH_SHORT).show();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onDeptFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDeptFragmentInteractionListener) {
            mListener = (OnDeptFragmentInteractionListener) context;
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

        if (v == btnDeptAdd) addDept();
    }


    private void addDept() {
        String deptName = edDeptName.getText().toString();
        if (TextUtils.isEmpty(deptName)) {
            Toast.makeText(getActivity(), "Please Enter the Dept Name", Toast.LENGTH_SHORT).show();
        } else {
            if (!mUIViewModel.isDeptExists(deptName)) {
                edDeptName.setText("");
                Dept dept = new Dept();
                dept.setDeptName(deptName);
                long insert = mUIViewModel.insertDept(dept);
                  Toast.makeText(getActivity(), ""
                    +insert, Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getActivity(), "Same data exists", Toast.LENGTH_SHORT).show();
        }
    }


    public interface OnDeptFragmentInteractionListener {
        void onDeptFragmentInteraction();
    }
}
