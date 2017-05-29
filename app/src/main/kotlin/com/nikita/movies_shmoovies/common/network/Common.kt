package com.nikita.movies_shmoovies.common.network

data class ListResponse<out T>(val page: Int,
                               val results: List<T>,
                               val total_pages: Int,
                               val total_results: Int)