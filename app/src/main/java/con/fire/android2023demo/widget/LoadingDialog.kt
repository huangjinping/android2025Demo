package con.fire.android2023demo.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import con.fire.android2023demo.databinding.DialogLoadingBinding

/**
 * 美观的加载对话框
 */
class LoadingDialog(
    context: Context, private val message: String = "加载中..."
) : Dialog(context) {

    private lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 去除默认标题和背景
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 默认不可取消，但可以通过setCancelable(true)设置为可取消
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        // 设置内容视图
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置消息文本
        binding.tvLoadingMessage.text = message

        // 设置窗口属性
        window?.attributes = window?.attributes?.apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
    }

    /**
     * 更新加载消息
     */
    fun updateMessage(message: String) {
        if (::binding.isInitialized) {
            binding.tvLoadingMessage.text = message
        }
    }

    companion object {
        /**
         * 创建并显示加载对话框
         */
        @JvmStatic
        fun show(context: Context, message: String = "加载中..."): LoadingDialog {
            return LoadingDialog(context, message).apply {
                show()
            }
        }
    }
}