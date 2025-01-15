package com.example.healthapp1

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var sharedPreferences: SharedPreferences
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_GALLERY = 2
    private val REQUEST_DOCUMENT_UPLOAD = 3
    private lateinit var profileImageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var uploadDocumentButton: Button
    private lateinit var documentsListView: ListView
    private var selectedImageUri: Uri? = null
    private val documentsList = mutableListOf<Pair<String, Uri>>() // List to hold document names and URIs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val nameTextView = view.findViewById<TextView>(R.id.name_textview)
        val ageTextView = view.findViewById<TextView>(R.id.age_textview)
        val dobTextView = view.findViewById<TextView>(R.id.dob_textview)
        val genderTextView = view.findViewById<TextView>(R.id.gender_textview)
        val cameraButton = view.findViewById<Button>(R.id.camera_button)
        profileImageView = view.findViewById(R.id.profile_image)
        saveButton = view.findViewById(R.id.save_button)
        uploadDocumentButton = view.findViewById(R.id.upload_document_button)
        documentsListView = view.findViewById(R.id.documents_list_view)

        // Shared Preferences initialization
        sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Load saved data
        nameTextView.text = sharedPreferences.getString("name", "")
        ageTextView.text = sharedPreferences.getString("age", "")
        dobTextView.text = sharedPreferences.getString("dob", "")
        genderTextView.text = sharedPreferences.getString("gender", "")

        // Load saved profile image
        loadProfileImage()

        // Load saved documents
        loadDocuments()

        // Camera/Gallery button click
        cameraButton.setOnClickListener {
            showImagePickerDialog()
        }

        // Upload document button click
        uploadDocumentButton.setOnClickListener {
            openDocumentPicker()
        }

        // Save button click
        saveButton.setOnClickListener {
            saveProfileData()
        }

        return view
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("Choose Image Source")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> dispatchTakePictureIntent()  // Camera option
                    1 -> openGallery()  // Gallery option
                }
            }.show()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    private fun openDocumentPicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Allow any file type
        startActivityForResult(intent, REQUEST_DOCUMENT_UPLOAD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    profileImageView.setImageBitmap(imageBitmap)
                    saveImageToSharedPreferences(imageBitmap)
                }
                REQUEST_IMAGE_GALLERY -> {
                    selectedImageUri = data?.data
                    profileImageView.setImageURI(selectedImageUri)
                    selectedImageUri?.let { uri ->
                        val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                        saveImageToSharedPreferences(bitmap)
                    }
                }
                REQUEST_DOCUMENT_UPLOAD -> {
                    data?.data?.let { uri ->
                        val documentName = uri.lastPathSegment ?: "Document"
                        documentsList.add(Pair(documentName, uri))  // Store name and URI
                        saveDocuments()
                        updateDocumentsListView()
                    }
                }
            }
        }
    }

    private fun saveImageToSharedPreferences(bitmap: Bitmap?) {
        bitmap?.let {
            val baos = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val imageBytes = baos.toByteArray()
            val encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            sharedPreferences.edit().putString("profile_image", encodedImage).apply()
        }
    }

    private fun loadProfileImage() {
        val savedImage = sharedPreferences.getString("profile_image", null)
        if (savedImage != null) {
            val imageBytes = Base64.decode(savedImage, Base64.DEFAULT)
            val decodedBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            profileImageView.setImageBitmap(decodedBitmap)
        }
    }

    private fun saveProfileData() {
        sharedPreferences.edit()
            .putString("name", view?.findViewById<TextView>(R.id.name_textview)?.text.toString())
            .putString("age", view?.findViewById<TextView>(R.id.age_textview)?.text.toString())
            .putString("dob", view?.findViewById<TextView>(R.id.dob_textview)?.text.toString())
            .putString("gender", view?.findViewById<TextView>(R.id.gender_textview)?.text.toString())
            .apply()
        Toast.makeText(requireContext(), "Profile saved successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun loadDocuments() {
        val savedDocumentUris = sharedPreferences.getStringSet("documents", emptySet())
        savedDocumentUris?.let {
            for (uriString in it) {
                val uri = Uri.parse(uriString)
                val documentName = uri.lastPathSegment ?: "Document"
                documentsList.add(Pair(documentName, uri)) // Add document name and URI
            }
        }
        updateDocumentsListView()
    }

    private fun saveDocuments() {
        val documentUrisSet = documentsList.map { it.second.toString() }.toSet() // Get set of URIs
        sharedPreferences.edit()
            .putStringSet("documents", documentUrisSet)
            .apply()
    }

    private fun updateDocumentsListView() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, documentsList.map { it.first })
        documentsListView.adapter = adapter

        // Handle item clicks
        documentsListView.setOnItemClickListener { _, _, position, _ ->
            val documentName = documentsList[position].first
            showDocumentOptions(documentName, position)
        }
    }

    private fun showDocumentOptions(documentName: String, position: Int) {
        val documentUri = documentsList[position].second  // Get URI
        val options = arrayOf("Open Document", "Delete Document")
        AlertDialog.Builder(requireContext())
            .setTitle("Document Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openDocument(documentUri)  // Open the document
                    1 -> deleteDocument(position)
                }
            }.show()
    }

    private fun openDocument(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "*/*")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
    }

    private fun deleteDocument(position: Int) {
        documentsList.removeAt(position)
        saveDocuments()
        updateDocumentsListView()
        Toast.makeText(requireContext(), "Document deleted", Toast.LENGTH_SHORT).show()
    }

    private fun clearProfileImage() {
        sharedPreferences.edit().remove("profile_image").apply()
    }

}
