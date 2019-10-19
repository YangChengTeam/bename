package com.ydys.qmb.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ydys.qmb.ui.custom.CircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Created by cl on 2018/5/3.
 */

public class FunImageLoader {
    public static void load(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .into(iv);
    }

    public static void load(Context context, String url, ImageView iv, int placeholder) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .into(iv);
    }

    public static void load(Context context, int resId, ImageView iv) {
        Glide.with(context)
                .load(resId)
                .into(iv);
    }

    /**
     * 需要在子线程执行
     *
     * @param context
     * @param url
     * @return
     */
    public static Bitmap load(Context context, String url) {
        try {
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            return Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(options)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadCircle(Context context, int resId, ImageView iv) {
        RequestOptions options = new RequestOptions();
        options.transform(new CircleTransform(context));
        Glide.with(context)
                .load(resId)
                .apply(options)
                .into(iv);
    }
}
