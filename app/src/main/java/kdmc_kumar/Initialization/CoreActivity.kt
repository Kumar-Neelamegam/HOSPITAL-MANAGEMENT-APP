package kdmc_kumar.Initialization

import android.Manifest
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v4.app.ActivityCompat

import displ.mobydocmarathi.com.R
import kdmc_kumar.Utilities_Others.RuntimePermissionsActivity

/**
 * Created by ramraj on 10/3/17.
 */

abstract class CoreActivity : RuntimePermissionsActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    open var context: Context? = null

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)

        bindViews()
        context = this
        setListeners()
    }

    /**
     * method to bind all views related to resourceLayout
     */
    protected abstract fun bindViews()

    /**
     * called to set view listener for views
     */
    protected abstract fun setListeners()

    //***************************************************************************************************
    fun isStoragePermissionGranted() {

        super@CoreActivity.requestAppPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS), R.string
                .runtime_permissions_txt, REQUEST_PERMISSIONS)


    }

    companion object {
        private val REQUEST_PERMISSIONS = 20
    }
    //***************************************************************************************************
    //End
}
