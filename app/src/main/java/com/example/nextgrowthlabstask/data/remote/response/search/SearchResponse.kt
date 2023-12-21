package com.example.nextgrowthlabstask.data.remote.response.search

import com.google.gson.annotations.SerializedName
import com.example.nextgrowthlabstask.data.remote.response.user.UserResponse

data class SearchResponse(

	@field:SerializedName("items")
	val items: List<UserResponse>
)
