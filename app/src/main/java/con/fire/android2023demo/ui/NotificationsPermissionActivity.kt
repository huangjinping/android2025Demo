package con.fire.android2023demo.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import con.fire.android2023demo.databinding.ActivityNotificationpermissionBinding

class NotificationsPermissionActivity : AppCompatActivity() {

    private var binding: ActivityNotificationpermissionBinding? = null

    private var activityResultSingle: ActivityResultLauncher<String>? = null

    var permissionsSingle: String = Manifest.permission.POST_NOTIFICATIONS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationpermissionBinding.inflate(getLayoutInflater())
        setContentView(binding?.root)
        activityResultSingle=registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            startActivity(
                Intent(
                    this@NotificationsPermissionActivity, NotificationsActivity::class.java
                )
            )
        }
        binding?.button16?.setOnClickListener {
            Toast.makeText(this@NotificationsPermissionActivity, "点击申请权限", Toast.LENGTH_SHORT).show()

            activityResultSingle?.launch(permissionsSingle)

        }
    }
}