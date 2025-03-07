package con.fire.android2023demo.photo;

/**
 * Created by li on 2018/5/18.
 * 图片加载类
 */

public class ImageUtil131 {


    /**
     //     * 压缩图片
     //     *
     //     * @param path
     //     * @return
     //     */
//    public static void openCompress(final Uri path, final ImgCompressLinster linster) {
//        Luban.with(App.application).load(path).ignoreBy(100)//不压缩的阈值，单位为K
//                .filter(new CompressionPredicate() {//设置开启压缩条件(图片为空或gif格式不压缩)
//                    @Override
//                    public boolean apply(String path) {
//                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif") || path.toLowerCase().endsWith(".mp4"));
//                    }
//                }).setCompressListener(new OnCompressListener() {
//                    @Override
//                    public void onStart() {
//                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                        //ToastUtil.setToast("压缩图片");
//                    }
//
//                    @Override
//                    public void onSuccess(File file) {
//                        File imageFile = new File(String.valueOf(path));
//                        linster.success(imageFile);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                }).launch();
//    }
//
//
//    /**
//     * 压缩图片
//     *
//     * @param path
//     * @return
//     */
//    public static void openCompress(final String path, final ImgCompressLinster linster) {
//        long maxLength = 100 * 1024;
//
//        Luban.with(App.application).load(path).ignoreBy(100)//不压缩的阈值，单位为K
//                .filter(new CompressionPredicate() {//设置开启压缩条件(图片为空或gif格式不压缩)
//                    @Override
//                    public boolean apply(String path) {
//                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif") || path.toLowerCase().endsWith(".mp4"));
//                    }
//                }).setCompressListener(new OnCompressListener() {
//                    @Override
//                    public void onStart() {
//                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                        //ToastUtil.setToast("压缩图片");
//                    }
//
//                    @Override
//                    public void onSuccess(File file) {
//                        Log.d("okhttps", "====000=33333==>>>>" + file.getAbsolutePath());
//
//                        if (file.length() > maxLength) {
//                            openCompress(file.getAbsolutePath(), linster);
//                        } else {
//                            if (linster != null) {
//                                linster.success(file);
//                            }
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//
//                }).launch();
//    }
}