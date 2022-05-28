package com.kajanthan.android_app.userdetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kajanthan.android_app.userdetails.api.UserAPIService
import com.ragul.android.userdetails.databinding.FragmentFirstBinding
import com.kajanthan.android_app.userdetails.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val userAPIService = UserAPIService.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonView.setOnClickListener {
            val id = binding.editText.text.toString()
            val user = userAPIService.getUser(id)
            user.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val body = response.body()
                    body?.let {
                        Log.i("FirstFragment", it.name)
                        binding.textviewFirst.setText(
                            "ID: ${it.id} \n\nName: ${it.name}\n\nUserName: ${it.username}\n\nEmail: ${it.email}")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i("Error",t.message!!)
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}