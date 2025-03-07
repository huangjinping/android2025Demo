package con.fire.android2023demo.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import con.fire.android2023demo.R;

public class DemoPopWindow extends PopupWindow {

    public DemoPopWindow(Context context) {
        super(context);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //加载的PopupWindow 的样式布局
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_test,
                null, false);
        setContentView(contentView);
    }

}
