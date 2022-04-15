package com.example.trio1.ui.Fav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trio1.R;
import com.example.trio1.TrioLogin;
import com.example.trio1.databinding.FavFragmentBinding;

public class FavFragment extends Fragment {


    private FavFragmentBinding binding;

    public static FavFragment newInstance() {
            return new FavFragment();
        }

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            FavViewModel favViewModel = new ViewModelProvider(this).get(FavViewModel.class);

            binding = FavFragmentBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            setHasOptionsMenu(true);
            final TextView textView = binding.textFav;
            favViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            return root;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }