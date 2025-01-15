package com.example.healthapp1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class CourseListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentLifecycle", "CourseListFragment: onCreate")
        Toast.makeText(requireContext(), "Fragment Created", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_course_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the UI components (if any)

    }

    override fun onStart() {
        super.onStart()
        Log.d("FragmentLifecycle", "CourseListFragment: onStart")
        Toast.makeText(requireContext(), "Fragment Started", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentLifecycle", "CourseListFragment: onResume")
        Toast.makeText(requireContext(), "Fragment Resumed", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.d("FragmentLifecycle", "CourseListFragment: onPause")
        Toast.makeText(requireContext(), "Fragment Paused", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.d("FragmentLifecycle", "CourseListFragment: onStop")
        Toast.makeText(requireContext(), "Fragment Stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FragmentLifecycle", "CourseListFragment: onDestroyView")
        Toast.makeText(requireContext(), "Fragment View Destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FragmentLifecycle", "CourseListFragment: onDestroy")
        Toast.makeText(requireContext(), "Fragment Destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("FragmentLifecycle", "CourseListFragment: onDetach")
        Toast.makeText(requireContext(), "Fragment Detached", Toast.LENGTH_SHORT).show()
    }
}
