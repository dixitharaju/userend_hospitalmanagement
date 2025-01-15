package com.example.healthapp1

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.healthapp1.R
import com.google.android.material.switchmaterial.SwitchMaterial

class NotificationSettingsFragment : Fragment(R.layout.fragment_notification_settings) {

    private lateinit var enableNotificationsSwitch: SwitchMaterial
    private lateinit var soundSwitch: SwitchMaterial
    private lateinit var vibrationSwitch: SwitchMaterial

    private val CHANNEL_ID = "pluspoint_notifications"

    // Register the permission request callback
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            showNotification()
        } else {
            Toast.makeText(requireContext(), "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification_settings, container, false)

        // Initialize switches
        enableNotificationsSwitch = view.findViewById(R.id.switch_enable_notifications)
        soundSwitch = view.findViewById(R.id.switch_sound)
        vibrationSwitch = view.findViewById(R.id.switch_vibration)

        // Create notification channel
        createNotificationChannel()

        // Set listeners
        enableNotificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Notifications: ${if (isChecked) "Enabled" else "Disabled"}", Toast.LENGTH_SHORT).show()

            if (isChecked) {
                checkAndRequestNotificationPermission()
            }
        }

        soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Sound: ${if (isChecked) "On" else "Off"}", Toast.LENGTH_SHORT).show()
        }

        vibrationSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Vibration: ${if (isChecked) "On" else "Off"}", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED
            ) {
                showNotification()  // Permission granted; show notification
            } else {
                // Request the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            // For devices below Android 13, directly show the notification
            showNotification()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "PlusPoint Notifications"
            val descriptionText = "Channel for PlusPoint notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, so do not show the notification
            return
        }

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)  // Replace with your app's icon drawable
            .setContentTitle("PlusPoint Notification")
            .setContentText("Notifications are now enabled for PlusPoint.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(1001, builder.build())  // 1001 is the notification ID
        }
    }


}