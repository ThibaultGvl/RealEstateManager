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
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityInsertBinding
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.MainActivity


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
    private lateinit var mEditCreationDate: EditText
    private lateinit var mEditSaleDate: EditText
    private lateinit var mAutoCompleteTextView: Spinner
    private lateinit var mButtonGallery: Button
    private lateinit var mButtonTake: Button
    private lateinit var mButton: Button
    private var mPropertyId: Long = 0
    private val REQUEST_TAKE_PHOTO = 0
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityInsertBinding.inflate(layoutInflater)
        val view = mBinding.root
        val insertInjection = InsertInjection::class.java
        val mViewModelFactory = insertInjection.newInstance()
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
        mEditCreationDate = mBinding.dateEnterInput
        mEditSaleDate = mBinding.dateSellInput
        mAutoCompleteTextView = mBinding.statusInput
        val array = arrayOf("For Sale", "For Rent", "Sold")
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, array)
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
    }

    private fun getPropertyToModify(property: Property) {
        mEditType.setText(property.type)
        mEditPrice.setText(property.price.toString())
        mEditSurface.setText(property.surface.toString())
        mEditPiece.setText(property.piece.toString())
        mEditDescription.setText(property.description)
        mEditAddress.setText(property.address)
        mEditInterest.setText(property.interestPoint)
        mEditCreationDate.setText(property.creationDate)
        mEditSaleDate.setText(property.sellDate)
        mEditAgent.setText(property.agent)
        mButton.text = getString(R.string.update_property)
        mButton.setOnClickListener{
            val photosList = mPhotos.toString().split("\\s*,\\s*")
            val photos = ArrayList<String>()
            for (photo in photosList) {
                photos.add(photo)
            }
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
                    mEditCreationDate.text.toString(),
                    mEditSaleDate.text.toString(),
                    photos,
                    mEditAgent.text.toString()
            )
            insertViewModel.updateProperty(updateProperty)
            sendVisualNotification(getString(R.string.update_notification_message))
            finish()
        }
    }

    private fun createNewProperty() {
        val photosList = mPhotos.toString().split("\\s*,\\s*") as ArrayList
        val photos = ArrayList<String>()
        for (photo in photosList) {
            photos.add(photo)
        }
        val property = Property(id,
                mEditType.text.toString(),
                mEditPrice.text.toString().toFloat(),
                mEditSurface.text.toString().toFloat(),
                mEditPiece.text.toString().toFloat(),
                mEditDescription.text.toString(),
                mEditAddress.text.toString(),
                mEditInterest.text.toString(),
                mAutoCompleteTextView.toString(),
                mEditCreationDate.text.toString(),
                mEditSaleDate.text.toString(),
                photos,
                mEditAgent.text.toString())
        insertViewModel.createProperty(property)
        sendVisualNotification(getString(R.string.created_notification_message))
        finish()
    }

    private fun selectImageInAlbum() {
        val i = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(i, REQUEST_SELECT_IMAGE_IN_ALBUM)
    }

    private fun takePhoto() {
        val callCameraIntent = Intent(ACTION_IMAGE_CAPTURE)
        if (callCameraIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(callCameraIntent, REQUEST_TAKE_PHOTO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            val uri = data?.data
            val takeFlags = data?.flags?.and((Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION))
            if (uri != null && takeFlags != null) {
                    this.contentResolver.takePersistableUriPermission(uri, takeFlags)
                    updateWithPhoto(uri)
            }
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_TAKE_PHOTO && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val imageToked: Uri? = data?.data
            if (imageToked != null) {
                updateWithPhoto(imageToked)
            }
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
                && mEditInterest.text.isNotEmpty() && mEditCreationDate.text.isNotEmpty())
    }
}