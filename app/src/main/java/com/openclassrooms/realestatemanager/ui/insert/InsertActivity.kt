package com.openclassrooms.realestatemanager.ui.insert

import android.R.attr
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityInsertBinding
import com.openclassrooms.realestatemanager.model.Property


class InsertActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityInsertBinding

    private lateinit var insertViewModel: InsertViewModel

    private var PICK_IMAGE = 1

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

    private lateinit var uri: Uri

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
        mButtonGallery.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Pick Image"), PICK_IMAGE)
        }
        mButtonTake.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent(ACTION_IMAGE_CAPTURE).toString()
            startActivityForResult(Intent.createChooser(intent, "Pick Image"), PICK_IMAGE)
        }
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
            val photos = mPhotos.toString().split("\\s*,\\s*")
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
            finish()
        }
    }

    private fun createNewProperty() {
        val photos = mPhotos.toString().split("\\s*,\\s*")
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
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == requestCode) {
                val selectedImageUri: Uri? = data?.data!!
                if (null != selectedImageUri) {
                    data.data?.let { uri = it }
                    Toast.makeText(baseContext, "This photo has been had with success",
                            Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun addUriToList(uri: Uri) {
        mPhotos.add(uri)
    }

    private fun inputComplete(): Boolean {
        return (mEditType.text.isNotEmpty() && mEditPrice.text.isNotEmpty()
                && mEditSurface.text.isNotEmpty() && mEditPrice.text.isNotEmpty()
                && mEditDescription.text.isNotEmpty() && mEditAddress.text.isNotEmpty()
                && mEditInterest.text.isNotEmpty() && mEditCreationDate.text.isNotEmpty())
    }
}