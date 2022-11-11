package com.mukesh.template.ui.viewModels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter.GenericAdapter
import com.mukesh.template.dataStore.getAuthToken
import com.mukesh.template.databinding.DemoBinding
import com.mukesh.template.model.UserDataDC
import com.mukesh.template.networking.BaseResponse
import com.mukesh.template.networking.NetworkState
import com.mukesh.template.networking.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
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
        override fun onCreateView(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = DemoBinding.inflate(layoutInflater, parent, false)

        override fun onBindHolder(holder: DemoBinding, dataClass: String) = Unit
    }


    /**
     * Dummy Data Api Call
     * */
    private val _dummyDataResponse by lazy { MutableSharedFlow<NetworkState<BaseResponse<UserDataDC>>>() }
    val dummyData get() = _dummyDataResponse.asSharedFlow()

    fun dummyData() = getAuthToken {
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