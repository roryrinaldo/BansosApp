package com.example.bansosapp.customer;

import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.bansosapp.R;
import com.example.bansosapp.database.DatabaseHelper;
import com.example.bansosapp.model.Customer;


public class HomeFragment extends Fragment  {
    private int customerId;
    private TextView textViewName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        textViewName = rootView.findViewById(R.id.user_name);

        // Mendapatkan ID pelanggan yang login dari SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        customerId = sharedPreferences.getInt("customerId",-1);

        // Mengakses database untuk mendapatkan data customer
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        Customer customer = databaseHelper.getCustomerById(customerId);
        databaseHelper.close();

        // Menampilkan data customer ke tampilan
        if (customer != null) {
            textViewName.setText(customer.getName());

        }


        return rootView;
    }




}
