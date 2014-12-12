package com.lqg.youtube.support.util;

import android.content.Context;

import com.lqg.youtube.R;
import com.lqg.youtube.support.GlobalApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

public class ImageLoaderUtil {

    private static String CACHEPATH = GlobalApplication.getInstance().getExternalCacheDir() + "/ImageCache/";

    public static DisplayImageOptions createListPicDisplayImageOptions() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.wb_pic_loading_large)
                .showImageForEmptyUri(R.drawable.wb_pic_loading_large)
                .showImageOnFail(R.drawable.wb_pic_loading_large)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                        // .displayer(new FadeInBitmapDisplayer(100)).build();
                .build();

        return options;
    }

    public static ImageLoader getImageLoader() {
        ImageLoader loader = ImageLoader.getInstance();
        if (!loader.isInited())
            initImageLoader(GlobalApplication.getInstance(), loader);
        return loader;
    }

    private static void initImageLoader(Context context, ImageLoader imageLoader) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .diskCache(new UnlimitedDiscCache(new File(CACHEPATH)))
                .diskCacheFileCount(500)
                .diskCacheSize(500 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        imageLoader.init(config);
    }
}
