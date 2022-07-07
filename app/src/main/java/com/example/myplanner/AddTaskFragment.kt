package com.example.myplanner

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myplanner.databinding.AddTaskLayoutBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DateFormat
import java.util.*

class AddTaskFragment: Fragment() {
    lateinit var binding: AddTaskLayoutBinding
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddTaskLayoutBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, ModelFactory(Repo())).get(MainViewModel::class.java)
        createNotificationChannel()

//        binding.notificationTv.setOnClickListener {
//            var builder = NotificationCompat.Builder(context!!, "channel1")
//                .setSmallIcon(R.drawable.ic_baseline_notifications)
//                .setContentTitle("Task Deadline Approaching")
//                .setContentText("Your task is due in x days")
//                .setStyle(NotificationCompat.BigTextStyle()
//                    .bigText("Please complete your task "))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setCategory(NotificationCompat.CATEGORY_REMINDER)
//            with(NotificationManagerCompat.from(context!!)){
//                notify(1, builder.build())
//            }
//        }

        binding.datePicker.setOnDateChangedListener { datePicker, month, day, year ->

        }

        binding.timePicker.setOnTimeChangedListener { timePicker, hour, minutes ->

        }



        binding.addBtn.setOnClickListener {
            if(binding.taskNameEt.text.equals("")){
                Toast.makeText(context, "Please enter a valid name", Toast.LENGTH_SHORT).show()
            } else {
                val taskTitle = binding.taskNameEt.text.toString()
                scheduleNotification(taskTitle)
                Navigation.findNavController(binding.root).navigate(R.id.action_addTaskFragment_to_homeScreenFragment)
            }
        }

        binding.cancelBtn.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_addTaskFragment_to_homeScreenFragment)
        }
        return binding.root
    }

    private fun getTime(): Long{
        val minutes = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val month = binding.datePicker.month
        val day = binding.datePicker.dayOfMonth
        val year = binding.datePicker.year
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minutes)
        return calendar.timeInMillis
    }

    private fun scheduleNotification(taskTitle: String){
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra("title", taskTitle)
        intent.putExtra("description",
            "This task is due and hasn't been completed")
        intent.putExtra("desc", "Please complete this task")

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
//        showAlert(time, taskTitle)
        val task = Task(taskTitle, false, Date(time).toString())
        viewModel.addTask(task)
    }

    private fun showAlert(time: Long, title: String){
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(context)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(context)

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel1"
            val descriptionText = "Task Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(name, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context!!.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}