package com.mukesh.apptunix.ui.viewModels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.apptunix.commonClasses.genericAdapter.recyclerAdapter.GenericAdapter
import com.mukesh.apptunix.dataStore.getAuthToken
import com.mukesh.apptunix.databinding.DemoBinding
import com.mukesh.apptunix.model.UserDataDC
import com.mukesh.apptunix.networking.BaseResponse
import com.mukesh.apptunix.networking.NetworkState
import com.mukesh.apptunix.networking.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    /**
     * Adapter With View Binding
     * */
    val adapter = object : GenericAdapter<DemoBinding, String>() {
        override fun onCreateView(parent: ViewGroup, viewType: Int) =
            DemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        override fun onBindHolder(holder: DemoBinding, dataClass: String) = Unit
    }


    /**
     * Dummy Data Api Call
     * */
    private val _dummyDataResponse by lazy { MutableSharedFlow<NetworkState<BaseResponse<UserDataDC>>>() }
    val dummyData get() = _dummyDataResponse.asSharedFlow()

    fun dummyData() = getAuthToken { authToken ->
        viewModelScope.launch {
            repository.dummyData().onStart {
                _dummyDataResponse.emit(NetworkState.LOADING())
            }.catch { error ->
                _dummyDataResponse.emit(NetworkState.ERROR(error))
            }.collect { response ->
                if (response.isSuccessful)
                    _dummyDataResponse.emit(NetworkState.SUCCESS(response.body()))
                else
                    _dummyDataResponse.emit(
                        NetworkState.ERROR(
                            response.errorBody()?.string() ?: ""
                        )
                    )
            }
        }
    }

}