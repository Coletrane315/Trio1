package com.example.trio1.ui.Fav;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.trio1.databinding.FavFragmentBinding;

import com.example.trio1.R;

public class FavFragment extends Fragment {


        private FavViewModel favViewModel;
        private FavFragmentBinding binding;

        public static FavFragment newInstance() {
            return new FavFragment();
        }

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            favViewModel =
                    new ViewModelProvider(this).get(FavViewModel.class);

            binding = FavFragmentBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            final TextView textView = binding.textFav;
            favViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    textView.setText(s);
                }
            });
            return root;
        }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }