package com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter

import android.util.Log
import androidx.paging.*
import com.mukesh.template.model.UserDataDC
import kotlinx.coroutines.flow.Flow


/**
 * Abstract Paging Source
 * */
abstract class GenericPagingSource<T : Any> : PagingSource<Int, T>() {

    /**
     * Refresh key
     * */
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


    /**
     * Load Data
     * */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            val response = callApiData(page, params.loadSize)
            LoadResult.Page(
                data = response ?: emptyList(),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isNullOrEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


    /**
     * Call Api Call Back and get data from remote
     * */
    abstract suspend fun callApiData(
        pageNumber: Int,
        loadSize: Int
    ): List<T>?


    /**
     * Get Paging Source Factory
     * */
    abstract fun getPagingSourceFactory(): PagingSource<Int, UserDataDC>


    /**
     * Get Pager Data
     * */
    fun getPagerData(): Flow<PagingData<UserDataDC>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { getPagingSourceFactory() },
        ).flow
    }

}