package com.example.shreyastest.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.shreyastest.Activity.ImageActivity;
import com.example.shreyastest.BeanClasses.Images;
import com.example.shreyastest.R;

import java.util.List;
import java.util.stream.Collectors;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.GridViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<Images> lstImages;
    private Integer values = 0;
    public MutableLiveData<Integer> loadingProgress = new MutableLiveData<>();

    public RecyclerGridAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setLstImages(List<Images> lstImages){
        if(this.lstImages != null)
            this.lstImages.clear();

//        notifyDataSetChanged();
        values = 0;
        loadingProgress.postValue(values);
        this.lstImages = lstImages.stream()
                .filter(s -> s.getType().equals("image/png") || s.getType().equals("image/jpeg"))
                .collect(Collectors.toList());
        Log.d("List SIze",this.lstImages.size() + "");
        notifyDataSetChanged();
    }

    public List<Images> getLstImages() {
        return lstImages;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.image_layout,parent, false); // inflate the layout
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int i) {

//        icon.setImageResource(lstImages.get(i).getLink()); // set logo images


//        Log.d("Link", lstImages.get(i).getLink());

        String actualLink = lstImages.get(i).getLink();
        String Link1 = actualLink.substring(0, actualLink.length() - 4);
        String Link2 = actualLink.substring(actualLink.length() - 4);

        String finalLink =  Link1 + "t" + Link2;

//        Log.d("Link", finalLink);

        Glide.with(context)
                .load(finalLink)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {
                        values++;
                        loadingProgress.postValue(values);
                        return false;
                    }
                })
                .into(holder.icon);

        holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);

        holder.icon.setOnClickListener(v-> {
            Activity activity = (Activity) context;
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,
                    holder.icon, "image1");

            Intent secondActivity = new Intent(context, ImageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("imagelink", actualLink);
            bundle.putString("imageId", lstImages.get(i).getId());
            secondActivity.putExtras(bundle);
            context.startActivity(secondActivity, options.toBundle());

        });

    }

    @Override
    public int getItemCount() {
        return lstImages != null ? lstImages.size() : 0;
    }

    static class GridViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;

        GridViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.img1);

        }
    }
}
