package com.mukesh.template.ui.views

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter.GenericLoadAdapter
import com.mukesh.template.databinding.ActivityMainBinding
import com.mukesh.template.networking.NetworkState
import com.mukesh.template.networking.getErrorMessage
import com.mukesh.template.socketSetUp.SocketInterface
import com.mukesh.template.socketSetUp.SocketSetup
import com.mukesh.template.ui.viewModels.MainActivityVM
import com.mukesh.template.utils.showSnackBar
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
        observeData()
    }


    /**
     * Observe Data
     * */
    private fun observeData() {
        lifecycleScope.launchWhenCreated {
            viewModel.dummyData.collectLatest { networkState ->
                when (networkState) {
                    is NetworkState.LOADING -> {
                        //Show Loader according to your requirement's
                    }
                    is NetworkState.ERROR -> {
                        this@MainActivity.showSnackBar(networkState.getErrorMessage())
                    }
                    is NetworkState.SUCCESS -> {
                        //Handle Data
                        viewModel.adapter.submitData(networkState.data!!)
                        println(networkState.data)
                    }
                }
            }
        }
    }
}