package com.saurabh.synerzipdemo.utils.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

/**
 * Created by SaurabhA on 03,October,2020
 *
 */
public class GlideImageLoader implements ModelLoader<GlideImageModel, InputStream> {

    private final ModelCache<GlideImageModel, GlideImageModel> mModelCache;

    private GlideImageLoader(ModelCache<GlideImageModel, GlideImageModel> mModelCache) {
        this.mModelCache = mModelCache;
    }

    @Nullable
    @Override
    public ModelLoader.LoadData<InputStream> buildLoadData(@NonNull GlideImageModel model, int width, int height, Options options) {
        GlideImageModel glideImageModel = model;
        if (mModelCache != null) {
            glideImageModel = mModelCache.get(model, 0, 0);
            if (glideImageModel == null) {
                mModelCache.put(model, 0, 0, model);
                glideImageModel = model;
            }
        }
        return null;
    }

    @Override
    public boolean handles(@NonNull GlideImageModel glideImageModel) {
        return true;
    }

    public static class Factory implements ModelLoaderFactory<GlideImageModel, InputStream> {

        private final ModelCache<GlideImageModel, GlideImageModel> mModelCache = new ModelCache<>(500);

        @Override
        public ModelLoader<GlideImageModel, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new GlideImageLoader(mModelCache);
        }

        @Override
        public void teardown() {

        }
    }

}
