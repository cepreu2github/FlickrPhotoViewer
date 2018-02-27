package com.example.flickrphotoviewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListImagesFragment extends Fragment {
    FlickrService service;

    @Override
    public void onStart() {
        super.onStart();
        // set toolbar of Activity
        ((MainActivity) getActivity()).fillMenu();
    }

    private void showOriginal(Photo photo){
        final Photo oldPhoto = photo;
        Callback<OriginalPhotoData> callback = new Callback<OriginalPhotoData>(){

            @Override
            public void onResponse(Call<OriginalPhotoData> call, Response<OriginalPhotoData> response) {
                Photo result = response.body().getPhoto();
                ((MainActivity) getActivity()).showImageDetails(result.url());
            }

            @Override
            public void onFailure(Call<OriginalPhotoData> call, Throwable t) {
                Toast.makeText(getActivity(), "Can't get original info", Toast.LENGTH_SHORT).show();
                Log.w("ListImagesFragment", t.toString());
            }
        };
        service.getOriginalInfo(photo.getId(), photo.getSecret()).enqueue(callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        String searchString = getArguments().getString("searchString");
        final RecyclerView recyclerView = rootView.findViewById(R.id.photo_grid);
        recyclerView.setHasFixedSize(true);
        ListImagesAdapter.OnItemClickListener l = new ListImagesAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, Photo photo) {
                showOriginal(photo);
            }
        };
        final ListImagesAdapter adapter = new ListImagesAdapter(l);
        // FIXME: i think create this constant in values-w600, values-large and so on is better way
        // but i just don't want to waste time for that in testing app
        int windowWidth = getActivity().getResources().getDisplayMetrics().widthPixels;
        int columns = windowWidth / adapter.getElementWidth(getActivity());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        // make request
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FlickrService.class);
        Callback<PhotosData> callback = new Callback<PhotosData>() {
            @Override
            public void onResponse(Call<PhotosData> call, Response<PhotosData> response) {
                List<Photo> photos = response.body().getPhotos().getPhoto();
                adapter.setPhotos(photos);
            }

            @Override
            public void onFailure(Call<PhotosData> call, Throwable t) {
                Toast.makeText(getContext(), "Connection failure", Toast.LENGTH_SHORT).show();
            }
        };
        if (searchString.equals("")){
            service.latestImages().enqueue(callback);
        } else {
            service.searchImages(searchString).enqueue(callback);
        }
        return rootView;
    }
}
