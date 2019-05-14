package kdmc_kumar.Initialization

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.*
import android.widget.*
import com.magnet.android.mms.MagnetMobileClient
import com.magnet.android.mms.exception.SchemaException
import displ.mobydocmarathi.com.R
import kdmc_kumar.Core_Modules.BaseConfig
import kdmc_kumar.Doctor_Modules.Login
import kdmc_kumar.Doctor_Modules.Splash_Screen
import kdmc_kumar.Utilities_Others.CustomKDMCDialog
import kdmc_kumar.Utilities_Others.LocalSharedPref
import kdmc_kumar.Utilities_Others.Tools
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.geAppMaster.controller.api.AppMaster
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.geAppMaster.controller.api.AppMasterFactory
import kotlinx.android.synthetic.main.kdmc_layout_initialization.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.util.concurrent.ExecutionException

class Initialization : CoreActivity() {

    private lateinit var myViewPagerAdapter: MyViewPagerAdapter

    var viewPager: ViewPager? = null
    var layoutDots: LinearLayout? = null
    var linearLayout2: LinearLayout? = null
    private var txtvwInit: TextView? = null
    var txtvwPleasewait: TextView? = null
    var button_go: Button? = null
    var progressBar1: ProgressBar? = null

    internal var maxstep = 7
    internal var Tittle = arrayOf("DOCTORS APP",
            "CLINICAL INFORMATION",
            "CASE NOTES",
            "INVESTIGATION",
            "PRESCRIPTION",
            "MY PATIENTS",
            "INPATIENTS")

    internal var Description = arrayOf("Enables doctors to easily and securely engage with their patients from their Smartphone.",
            "Is designed for Collecting, storing, manipulating and making available clinical information important to the healthcare delivery process",
            "Case notes include patient histories, diagnostic test results and temperature, blood Pressure and other charts, & other forms of Treatment.",
            "Investigation are maintained by doctors, test reports like SCAN, X-Ray, ECG, EEG and Angiogram.",
            "On the basis of inference & test reports, the doctor prescribes medicines in the \"Medicine Prescription\" page. The \"Medicine Intake Frequency\" field contains checkboxes where each value represents \"Morning, Afternoon, Night\" respectively. And the \"Medicine Intake Schedule\" contains options to specify whether the medicine is to be taken \"Before Food\" or \"After Food\" which is noted in the form of option.",
            "Each doctor is provided with a page named \"My Patients\". Under this menu, the patients consulted them earlier will be listed.",
            "Easy To Manage The Admitted Patients")

    internal var Images = intArrayOf(R.drawable.logo_white, R.drawable.dashboard_ic_diagnosis_details, R.drawable.dashboard_ic_case_book, R.drawable.dashboard_ic_medical_test, R.drawable.dashboard_ic_drug, R.drawable.dashboard_ic_my_patients, R.drawable.dashboard_ic_inpatient)



    //**********************************************************************************************
    private var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            BottomDots(position)

        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    //**********************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kdmc_layout_initialization)


        try {
            isStoragePermissionGranted()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    //**********************************************************************************************
    override fun onPermissionsGranted(requestCode: Int) {

        try {
            initComponent()
            Tools.setSystemBarTransparent(this)
        } catch (e: Exception) {

        }

    }

    //**********************************************************************************************
    private fun initComponent() {
        try {

            viewPager = findViewById<View>(R.id.view_pager) as ViewPager
            layoutDots = findViewById<View>(R.id.layoutDots) as LinearLayout
            linearLayout2 = findViewById<View>(R.id.linearLayout2) as LinearLayout
            txtvwInit = findViewById<View>(R.id.txtvw_init) as TextView
            txtvwPleasewait = findViewById<View>(R.id.txtvw_pleasewait) as TextView
            button_go = findViewById<View>(R.id.button_go) as Button
            progressBar1 = findViewById<View>(R.id.progressBar1) as ProgressBar

            BottomDots(0)

            myViewPagerAdapter = MyViewPagerAdapter()
            viewPager!!.adapter = myViewPagerAdapter
            viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)


            Tools.setSystemBarColor(this, R.color.overlay_light_80)
            Tools.setSystemBarLight(this)

            var init_status = LocalSharedPref(MainActivity@this).getBoolean("initialization_status")

            if (!init_status || init_status == null) {


                try {
                    CallInitiDialog()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {

                this.finish()
                startActivity(Intent(Initialization@ this, Splash_Screen::class.java))

            }


        } catch (e: Exception) {

            e.printStackTrace()
        }


    }

    //**********************************************************************************************
    var bc = BaseConfig()
    private fun CallInitiDialog() {

        val dialog = Dialog(this@Initialization)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_init)
        dialog.setCancelable(false)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        val ipaddress = dialog.findViewById<EditText>(R.id.edt_ipaddress)


        dialog.findViewById<View>(R.id.btn_yes).setOnClickListener {
            if (ipaddress.text.toString().isNotEmpty()) {

                dialog.dismiss()

                BaseConfig.AppNodeIP = "http://" + ipaddress.text.toString()



                if (BaseConfig.server_connectivity_status || BaseConfig.CheckNetwork(Initialization@ this)) {

                    //all mthds
                    //Call aysch get all info
                    Copydatabase().execute()


                } else {
                    //Message

                    CustomKDMCDialog(this)
                            .setLayoutColor(R.color.red_500)
                            .setImage(R.drawable.img_no_internet_satellite)
                            .setTitle("Information")
                            .setNegativeButtonVisible(View.GONE)
                            .setPossitiveButtonTitle("OK")
                            .setOnPossitiveListener { }


                }

            }
        }

        dialog.findViewById<View>(R.id.btn_no).setOnClickListener { this@Initialization.finishAffinity() }

        dialog.window!!.attributes = lp
        dialog.show()


    }


    //**********************************************************************************************
    override fun bindViews() {

    }

    override fun setListeners() {

    }

    //**********************************************************************************************
    /**
     * CREATING A SLIDE INTRO ABOUT APPLICATION
     * @param CurrentPosition
     */
    private fun BottomDots(CurrentPosition: Int) {

        val dots = arrayOfNulls<ImageView>(maxstep)

        layoutDots!!.removeAllViews()

        for (i in 0 until dots.size) {
            dots[i] = ImageView(this)
            val width_height = 15
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(width_height, width_height))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            dots[i]!!.setColorFilter(R.color.White, PorterDuff.Mode.SRC_IN)
            layoutDots!!.addView(dots[i])
        }

        if (dots.isNotEmpty()) {
            dots[CurrentPosition]!!.setImageResource(R.drawable.shape_circle)
            dots[CurrentPosition]!!.setColorFilter(resources.getColor(R.color.grey_10), PorterDuff.Mode.SRC_IN)
        }

    }

    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null
        private var btnNext: Button? = null

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater!!.inflate(R.layout.intro_item, container, false)
            (view.findViewById<View>(R.id.title) as TextView).text = Tittle[position]
            (view.findViewById<View>(R.id.description) as TextView).text = Description[position]
            (view.findViewById<View>(R.id.image) as ImageView).setImageResource(Images[position])

            btnNext = view.findViewById(R.id.btn_next)

            if (position == Tittle.size - 1) {
                btnNext!!.text = "Get Started"
                btnNext!!.visibility = View.GONE
            } else {
                btnNext!!.text = "Next"
                btnNext!!.visibility = View.VISIBLE
            }

            btnNext!!.setOnClickListener { v ->
                val current = viewPager!!.currentItem + 1
                if (current < maxstep) {
                    // move to next screen
                    viewPager!!.currentItem = current


                }
            }

            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return Tittle.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

    //**********************************************************************************************

    override fun onPause() {
        super.onPause()
        System.gc()
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }


    override fun onBackPressed() {

        System.gc()

    }

    //**********************************************************************************************

    private fun getApplicationUpdateNodeJs() {


        try {
            val appMaster: AppMaster
            // Instantiate a controller
            val magnetClient = MagnetMobileClient.getInstance(this@Initialization)
            val controllerFactory = AppMasterFactory(magnetClient)
            appMaster = controllerFactory.obtainInstance()

            val callObject = appMaster.getAppMaster(null)

            val result = callObject.get()


            val stringBuilder = StringBuilder(result.results)

            val jsonArray = JSONArray(stringBuilder.toString())

            for (i in 0 until jsonArray.length()) {

                val jsonObject = jsonArray.get(i) as JSONObject


                val Id = jsonObject.getString("Id")
                val Appname = jsonObject.getString("Appname")
                var AppLogo = jsonObject.getString("AppLogo")
                val AppLanguage = jsonObject.getString("AppLanguage")
                val AppWebServiceURL = jsonObject.getString("AppWebServiceURL")
                val AppWebServiceType = jsonObject.getString("AppWebServiceType")
                val AppNodeIP = jsonObject.getString("AppNodeIP")
                val AppDotNetIP = jsonObject.getString("AppDotNetIP")
                val AppImagephpURL = jsonObject.getString("AppImagephpURL")
                val AppDBFolderName = jsonObject.getString("AppDBFolderName")
                val InsertedDate = jsonObject.getString("InsertedDate")
                val UpdatedDate = jsonObject.getString("UpdatedDate")
                val IsActive = jsonObject.getString("IsActive")



                BaseConfig.Appname = Appname
                BaseConfig.AppLanguage = AppLanguage
                BaseConfig.AppWebServiceType = AppWebServiceType
                BaseConfig.AppNodeIP = AppNodeIP
                BaseConfig.AppImagephpURL = AppImagephpURL
                BaseConfig.AppDBFolderName = Environment.getExternalStorageDirectory().path + File.separator + AppDBFolderName
                BaseConfig.AppDatabaseName = BaseConfig.AppDBFolderName + File.separator + Appname + ".db"
                BaseConfig.AppDirectoryPictures = Appname


                val f = File(BaseConfig.AppDBFolderName)

                if (!f.exists()) {


                    val file = File(BaseConfig.AppDBFolderName)
                    file.mkdirs()

                }




                AppLogo = BaseConfig.saveURLImagetoSDcard(BaseConfig.Glide_GetBitmap(Initialization@ this, BaseConfig.AppNodeIP + AppLogo), "AppLogo")
                BaseConfig.AppLogo = AppLogo

                LocalSharedPref(Initialization@this).setValue("Appname",Appname)
                LocalSharedPref(Initialization@this).setValue("AppLanguage",BaseConfig.AppLanguage)
                LocalSharedPref(Initialization@this).setValue("AppWebServiceType",BaseConfig.AppWebServiceType)
                LocalSharedPref(Initialization@this).setValue("AppNodeIP",BaseConfig.AppNodeIP)
                LocalSharedPref(Initialization@this).setValue("AppImagephpURL",BaseConfig.AppImagephpURL)
                LocalSharedPref(Initialization@this).setValue("AppDBFolderName",BaseConfig.AppDBFolderName)
                LocalSharedPref(Initialization@this).setValue("AppDatabaseName",BaseConfig.AppDatabaseName)
                LocalSharedPref(Initialization@this).setValue("AppDirectoryPictures",BaseConfig.AppDirectoryPictures)
                LocalSharedPref(Initialization@this).setValue("AppLogo",BaseConfig.AppLogo)


                val contentValues = ContentValues()

                contentValues.put("Id", Id)
                contentValues.put("Appname", Appname)
                contentValues.put("AppLogo", AppLogo)
                contentValues.put("AppLanguage", AppLanguage)
                contentValues.put("AppWebServiceURL", AppWebServiceURL)
                contentValues.put("AppWebServiceType", AppWebServiceType)
                contentValues.put("AppNodeIP", AppNodeIP)
                contentValues.put("AppDotNetIP", AppDotNetIP)
                contentValues.put("AppImagephpURL", AppImagephpURL)
                contentValues.put("AppDBFolderName", AppDBFolderName)
                contentValues.put("InsertedDate", InsertedDate)
                contentValues.put("UpdatedDate", UpdatedDate)
                contentValues.put("IsActive", IsActive)


                // Check if the database exists before copying
                //  val initialiseDatabase = File(BaseConfig.AppDBFolderName).exists()

                shipdb()

                val db = BaseConfig.GetDb()

                db.insert("AppConfig", null, contentValues)

                Log.e("getApplicationUpdate: ", stringBuilder.toString())

             //   ExecuteCims();

                // save the data in Shared Preferences
                LocalSharedPref(Initialization@this).setBoolean("initialization_status",true)




            }


        } catch (e: SchemaException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun ExecuteCims() {

        val `is` = resources.assets.open("cims1.sql")

        val writer = StringWriter()
        val buffer = CharArray(2048)
        try {
            val reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
            var n: Int
            n = reader.read(buffer)
            while ((n) != -1) {
                writer.write(buffer, 0, n)
                n = reader.read(buffer)
            }

        } finally {
            `is`.close()
        }
        val text = writer.toString()

        val db = BaseConfig.GetDb()

        db.execSQL(text)


    }

    inner class Copydatabase : AsyncTask<Void, Void, Void>() {


        override fun onPostExecute(v: Void?) {

            progressBar1!!.visibility = View.GONE

            txtvw_init!!.text = "Initialization successful.."
            button_go!!.visibility = View.VISIBLE
            txtvwPleasewait!!.visibility = View.GONE

            button_go!!.setOnClickListener {
                finish()
                val intent = Intent(applicationContext, Login::class.java)
                startActivity(intent)
                Log.i("Initialization", "Initialization to registration")
            }


        }


        override fun onPreExecute() {

            super.onPreExecute()
            progressBar1!!.visibility = View.VISIBLE
            button_go!!.visibility = View.GONE
            txtvw_init!!.text = "Initialization Processing.."

        }


        override fun doInBackground(vararg params: Void): Void? {

            try {


                getApplicationUpdateNodeJs()


            } catch (e: Exception) {


                e.printStackTrace()

            }


            return null

        }


    }

    //***************************************************************************************************

    fun shipdb() {


        val DB_DESTINATION = BaseConfig.AppDatabaseName

        // Check if the database exists before copying
        val initialiseDatabase = File(DB_DESTINATION).exists()

        if (!initialiseDatabase) {

            Log.i("Processing...", "Copying Database")
            val file = applicationContext.assets.open("mobydoctor.db")

            val os = FileOutputStream(DB_DESTINATION)
            val buffer = ByteArray(1024)

            var length: Int = file.read(buffer)
            while ((length) > 0) {
                os.write(buffer, 0, length)
                length = file.read(buffer)
            }

            os.flush()

            os.close()
            file.close()


        }

        copyLogoFileAssets()

    }


    private fun copyLogoFileAssets() {

        try {
            val file = this@Initialization.applicationContext.assets.open("male_avatar.png")

            // Copy the database into the destination
            val os = FileOutputStream(BaseConfig.AppDBFolderName + "/male_avatar.jpg")
            val buffer = ByteArray(1024)

            var length: Int = file.read(buffer)
            while ((length) > 0) {
                os.write(buffer, 0, length)
                length = file.read(buffer)
            }

            os.flush()
            os.close()
            file.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //***************************************************************************************************


}//END
