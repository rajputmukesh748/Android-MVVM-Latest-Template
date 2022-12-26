package com.mukesh.template.ui.viewModels

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter.GenericAdapter
import com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter.GenericLoadAdapter
import com.mukesh.template.dataStore.getAuthToken
import com.mukesh.template.databinding.DemoBinding
import com.mukesh.template.model.UserDataDC
import com.mukesh.template.networking.NetworkState
import com.mukesh.template.networking.repository.PagingHandlerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val pagingHandlerRepository: PagingHandlerRepository
) : ViewModel() {

    init {
        dummyData()
    }

    /**
     * Adapter With View Binding
     * */
    val adapter = object : GenericAdapter<DemoBinding, UserDataDC>() {
        override fun onCreateView(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = DemoBinding.inflate(layoutInflater, parent, false)

        override fun onBindHolder(holder: DemoBinding, dataClass: UserDataDC) {
            holder.btText.text = dataClass.title.orEmpty()
        }
    }.apply {
        withLoadStateFooter(footer = GenericLoadAdapter())
    }


    /**
     * Dummy Data Api Call
     * */
    private val _dummyDataResponse by lazy { MutableSharedFlow<NetworkState<PagingData<UserDataDC>>>() }
    val dummyData get() = _dummyDataResponse.asSharedFlow()

    private fun dummyData() = getAuthToken {
        viewModelScope.launch {
            pagingHandlerRepository.getPagerData().cachedIn(viewModelScope).onStart {
                _dummyDataResponse.emit(NetworkState.LOADING())
            }.catch { error ->
                _dummyDataResponse.emit(NetworkState.ERROR(error = error))
            }.collectLatest {
                _dummyDataResponse.emit(NetworkState.SUCCESS(it))
            }
        }
    }

}