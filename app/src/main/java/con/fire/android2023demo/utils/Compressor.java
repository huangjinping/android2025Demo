package con.fire.android2023demo.utils;

import android.content.Context;
import android.graphics.Bitmap;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.IOException;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Supplier;


public class Compressor {
    //max width and height values of the compressed image is taken as 612x816
    private int maxWidth = 400;
    private int maxHeight = 400;

    private int quality = 57;

    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private String destinationDirectoryPath;

    public Compressor(Context context) {
//        destinationDirectoryPath = context.getCacheDir().getPath() + File.separator + "images";
        destinationDirectoryPath = context.getExternalCacheDir().getPath() + File.separator + "images";

    }

    public Compressor setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public Compressor setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public Compressor setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
        return this;
    }

    public Compressor setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public Compressor setDestinationDirectoryPath(String destinationDirectoryPath) {
        this.destinationDirectoryPath = destinationDirectoryPath;
        return this;
    }

    public File compressToFile(File imageFile) throws IOException {
        return compressToFile(imageFile, imageFile.getName());
    }

    public File compressToFile(File imageFile, String compressedFileName) throws IOException {
        return ImageUtil.compressImage(imageFile, maxWidth, maxHeight, compressFormat, quality, destinationDirectoryPath + File.separator + compressedFileName);
    }

    public Bitmap compressToBitmap(File imageFile) throws IOException {
        return ImageUtil.decodeSampledBitmapFromFile(imageFile, maxWidth, maxHeight);
    }

    public Flowable<File> compressToFileAsFlowable(final File imageFile) {
        return compressToFileAsFlowable(imageFile, imageFile.getName());
    }

    public Flowable<File> compressToFileAsFlowable(final File imageFile, final String compressedFileName) {
        return Flowable.defer(new Supplier<Publisher<File>>() {
            @Override
            public Publisher<File> get() throws Throwable {
                try {
                    return Flowable.just(compressToFile(imageFile, compressedFileName));
                } catch (IOException e) {
                    return Flowable.error(e);
                }
            }
        });
    }

    public Flowable<Bitmap> compressToBitmapAsFlowable(final File imageFile) {
        return Flowable.defer(new Supplier<Publisher<Bitmap>>() {
            @Override
            public Publisher<Bitmap> get() throws Throwable {
                try {
                    return Flowable.just(compressToBitmap(imageFile));
                } catch (IOException e) {
                    return Flowable.error(e);
                }
            }
        });
    }
}