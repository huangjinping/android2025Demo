package con.fire.android2023demo.photo;

import android.net.Uri;

public abstract class PhotoCallback {
    public abstract void getPath(Uri uri, String path);

    public void onFail(String message) {

    }

}
