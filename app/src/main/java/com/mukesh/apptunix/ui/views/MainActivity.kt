package com.mukesh.apptunix.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.mukesh.apptunix.commonClasses.genericAdapter.recyclerAdapter.GenericAdapter
import com.mukesh.apptunix.databinding.ActivityMainBinding
import com.mukesh.apptunix.databinding.DemoBinding
import com.mukesh.apptunix.networking.NetworkState
import com.mukesh.apptunix.networking.getErrorMessage
import com.mukesh.apptunix.socketSetUp.SocketInterface
import com.mukesh.apptunix.socketSetUp.SocketSetup
import com.mukesh.apptunix.ui.viewModels.MainActivityVM
import com.mukesh.apptunix.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SocketInterface {

    private val activityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainActivityVM>()


    /**
     * On Create Method
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        SocketSetup.initializeInterface(this)
        activityMainBinding.rv.adapter = viewModel.adapter
        viewModel.adapter.submitList(listOf("", "", ""))
        observeData()
    }


    /**
     * Observe Data
     * */
    private fun observeData() {
        lifecycleScope.launchWhenCreated {
            viewModel.dummyData.collectLatest { networkState ->
                when(networkState){
                    is NetworkState.LOADING -> {
                        //Show Loader according to your requirement's
                    }
                    is NetworkState.ERROR -> {
                        this@MainActivity.showSnackBar(networkState.getErrorMessage())
                    }
                    is NetworkState.SUCCESS -> {
                        //Handle Data
                        val data = networkState.data
                    }
                }
            }
        }
    }
}