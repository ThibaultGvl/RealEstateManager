package com.openclassrooms.realestatemanager.ui.insert

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.provider.MediaStore.EXTRA_OUTPUT
import android.webkit.URLUtil
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.databinding.ActivityInsertBinding
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.MainActivity
import com.openclassrooms.realestatemanager.injection.Injection
import java.io.File
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue


class InsertActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityInsertBinding
    private lateinit var insertViewModel: InsertViewModel
    private var id: Long = 0
    private var mPhotos = ArrayList<Uri>()
    private lateinit var mEditType: EditText
    private lateinit var mEditAddress: EditText
    private lateinit var mEditAgent: EditText
    private lateinit var mEditDescription: EditText
    private lateinit var mEditInterest: EditText
    private lateinit var mEditPrice: EditText
    private lateinit var mEditPiece: EditText
    private lateinit var mEditSurface: EditText
    private lateinit var mEditDay: Spinner
    private lateinit var mEditMonth: Spinner
    private lateinit var mEditYear: Spinner
    private lateinit var mAutoCompleteTextView: Spinner
    private lateinit var mButtonGallery: Button
    private lateinit var mButtonTake: Button
    private lateinit var mButton: Button
    private var mPropertyId: Long = 0
    private val PERMISSION_ID = 0
    private val REQUEST_TAKE_PHOTO = 0
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    private lateinit var mUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityInsertBinding.inflate(layoutInflater)
        val view = mBinding.root
        val injection = Injection::class.java
        val mViewModelFactory = injection.newInstance()
                .provideViewModelFactory(applicationContext)
        insertViewModel = ViewModelProvider(this, mViewModelFactory)
                .get(InsertViewModel::class.java)
        createView()
        insertViewModel.getProperties().observe(this, { id = it.size.toLong() + 1 })
        setContentView(view)
        if (intent.extras != null) {
            mPropertyId = intent.extras?.getLong("id")!!
            insertViewModel.getPropertyById(mPropertyId)
                    .observe(this, this::getPropertyToModify)
        }
        mButtonGallery.setOnClickListener{
            selectImageInAlbum()
        }
        mButtonTake.setOnClickListener{
            takePhoto()
        }
    }

    private fun createView() {
        mEditType = mBinding.typeInput
        mEditAddress = mBinding.addressInput
        mEditAgent = mBinding.agentInput
        mEditDescription = mBinding.descriptionInput
        mEditInterest = mBinding.interestPointInput
        mEditPrice = mBinding.priceInput
        mEditPiece = mBinding.pieceInput
        mEditSurface = mBinding.surfaceInput
        mEditDay = mBinding.daySellInput
        mEditMonth = mBinding.monthSellInput
        mEditYear = mBinding.yearSellInput
        mAutoCompleteTextView = mBinding.statusInput
        val array = arrayOf("For Sale", "For Rent", "Sold")
        val days = arrayOf("01","02","03","04","05","06","07","08","09","10","11","12","13","14",
                "15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31")
        val month = arrayOf("01","02","03","04","05","06","07","08","09","10","11","12")
        val actualYear = Calendar.getInstance().get(Calendar.YEAR)
        val year = arrayOf((actualYear.absoluteValue -1).toString(),actualYear.absoluteValue.toString(),(actualYear.absoluteValue+1).toString())
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, array)
        val dayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, days)
        val monthAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, month)
        val yearAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, year)
        mEditDay.adapter = dayAdapter
        mEditMonth.adapter = monthAdapter
        mEditYear.adapter = yearAdapter
        mAutoCompleteTextView.adapter = adapter
        mButtonGallery = mBinding.gallery
        mButtonTake = mBinding.apn
        mButton = mBinding.createBtn
        mButton.setOnClickListener {
            if(inputComplete()) {
                createNewProperty()
            }
            else {
                Toast.makeText(baseContext, "All information must be registered", Toast.LENGTH_SHORT).show()
            }
        }
        val toolbar = mBinding.insertToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val returnButton = mBinding.returnInsert
        returnButton.setOnClickListener { onBackPressed() }
    }

    private fun getPropertyToModify(property: Property) {
        mEditType.setText(property.type)
        mEditPrice.setText(property.price.toString())
        mEditSurface.setText(property.surface.toString())
        mEditPiece.setText(property.piece.toString())
        mEditDescription.setText(property.description)
        mEditAddress.setText(property.address)
        mEditInterest.setText(property.interestPoint)
        mEditAgent.setText(property.agent)
        mButton.text = getString(R.string.update_property)
        val date = "$mEditDay/$mEditMonth/$mEditYear"
        mButton.setOnClickListener{
            val currentPhotosList = mPhotos.toString().split("\\s*,\\s*")
            val photoList = property.photos.split("\\s*,\\s*")
            val photos = ArrayList<String>()
            photos.addAll(photoList)
            photos.addAll(currentPhotosList)
            val updateProperty = Property(
                    property.id,
                    mEditType.text.toString(),
                    mEditPrice.text.toString().toFloat(),
                    mEditSurface.text.toString().toFloat(),
                    mEditPiece.text.toString().toFloat(),
                    mEditDescription.text.toString(),
                    mEditAddress.text.toString(),
                    mEditInterest.text.toString(),
                    mAutoCompleteTextView.toString(),
                    property.creationDate,
                    date,
                    photos.toString(),
                    mEditAgent.text.toString()
            )
            insertViewModel.updateProperty(updateProperty)
            sendVisualNotification(getString(R.string.update_notification_message))
            finish()
        }
    }

    private fun createNewProperty() {
        val photosList = mPhotos.toString().split("\\s*,\\s*")
        val photos = ArrayList<String>()
        for (photo in photosList) {
            var photoToAdd = photo
            for (char in photo) {
                photoToAdd = when {
                    char.toString() == "[" -> {
                        photoToAdd.replace("[", "")
                    }
                    char.toString() == "]" -> {
                        photoToAdd.replace("]", "")
                    }
                    else -> {
                        photoToAdd.replace(" ", "")
                    }
                }
            }
            if (photoToAdd != "") {
                photos.add(photoToAdd)
            }
        }
        val date = "$mEditDay/$mEditMonth/$mEditYear"
        val property = Property(id,
                mEditType.text.toString(),
                mEditPrice.text.toString().toFloat(),
                mEditSurface.text.toString().toFloat(),
                mEditPiece.text.toString().toFloat(),
                mEditDescription.text.toString(),
                mEditAddress.text.toString(),
                mEditInterest.text.toString(),
                mAutoCompleteTextView.toString(),
                Utils.getTodayDate(),
                date,
                photos.toString(),
                mEditAgent.text.toString())
        insertViewModel.createProperty(property)
        sendVisualNotification(getString(R.string.created_notification_message))
        finish()
    }

    private fun selectImageInAlbum() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_ID)
        }
        else {
            val i = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(i, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    private fun takePhoto() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_ID)
        }
        else {
            val m_intent = Intent(ACTION_IMAGE_CAPTURE)
            val file: File = File.createTempFile(
                    "IMG_", ".jpg",
                    applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
            mUri = FileProvider.getUriForFile(this, "com.openclassrooms.realestatemanager.provider", file)
            m_intent.putExtra(EXTRA_OUTPUT, mUri)
            startActivityForResult(m_intent, REQUEST_TAKE_PHOTO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM
                && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                val uri = data?.data
                val takeFlags = data?.flags?.and((Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION))
                if (uri != null && takeFlags != null) {
                    this.contentResolver.takePersistableUriPermission(uri, takeFlags)
                    updateWithPhoto(uri)
                }
            }
        if (resultCode == RESULT_OK && requestCode == REQUEST_TAKE_PHOTO && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                updateWithPhoto(mUri)
        }
     }

    private fun updateWithPhoto(uri: Uri) {
        mPhotos.add(uri)
    }

    private fun sendVisualNotification(message: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = "CREATION_NOTIFICATION"
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName: CharSequence = "Notification Creation"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun inputComplete(): Boolean {
        return (mEditType.text.isNotEmpty() && mEditPrice.text.isNotEmpty()
                && mEditSurface.text.isNotEmpty() && mEditPrice.text.isNotEmpty()
                && mEditDescription.text.isNotEmpty() && mEditAddress.text.isNotEmpty()
                && mEditInterest.text.isNotEmpty())
    }
}