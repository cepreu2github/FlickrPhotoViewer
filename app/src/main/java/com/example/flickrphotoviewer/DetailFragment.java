package com.example.flickrphotoviewer;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ImageView imageView;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).clearMenu();
    }

    public void adaptLayout(int imageWidth, int imageHeight) {
        // calculate layout options for 2/4 and 3/4 restrictions
        int viewWidth = imageView.getWidth();
        int viewHeight = imageView.getHeight();
        double viewAspect = ((double) viewWidth) / ((double) viewHeight);
        double imageAspect = ((double) imageWidth) / ((double) imageHeight);
        Log.d("viewAspect", Double.toString(viewAspect));
        Log.d("imageAspect", Double.toString(imageAspect));
        if (imageAspect < viewAspect){
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 3f));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        String url = getArguments().getString("url");
        imageView = rootView.findViewById(R.id.imageView);
        textView = rootView.findViewById(R.id.textView);
        GlideApp.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, Transition<? super File> transition){
                        Bitmap bitmap = BitmapFactory.decodeFile(resource.getAbsolutePath());
                        final int bitmapWidth = bitmap.getWidth();
                        final int bitmapHeight = bitmap.getHeight();
                        adaptLayout(bitmapWidth, bitmapHeight);
                        imageView.setImageBitmap(bitmap);
                        String data = "IO error, can't read image: <br>";
                        try {
                            ExifInterface exif = new ExifInterface(resource.getAbsolutePath());
                            String date = exif.getAttribute(exif.TAG_DATETIME);
                            if (date == null){
                                date = "unknown";
                            }
                            // FIXME: no better candidate to be user title in EXIF.
                            // Mistake in task description?
                            String title = exif.getAttribute(exif.TAG_USER_COMMENT);
                            if (title == null){
                                title = "not specified";
                            }
                            data = "<b> Creation date: </b> " + date;
                            data += "<br> <b> Title: </b> " + title;
                        } catch (IOException e) {
                            data += e.toString();
                        }
                        textView.setText(Html.fromHtml(data));
                    }
                });
        return rootView;
    }

}
