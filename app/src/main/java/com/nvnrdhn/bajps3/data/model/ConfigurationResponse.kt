package com.nvnrdhn.bajps3.data.model

import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(

	@field:SerializedName("images")
	val images: Images = Images(),

	@field:SerializedName("change_keys")
	val changeKeys: List<String> = listOf()
)

data class Images(

	@field:SerializedName("poster_sizes")
	val posterSizes: List<String> = listOf(),

	@field:SerializedName("secure_base_url")
	val secureBaseUrl: String = "",

	@field:SerializedName("backdrop_sizes")
	val backdropSizes: List<String> = listOf(),

	@field:SerializedName("base_url")
	val baseUrl: String = "",

	@field:SerializedName("logo_sizes")
	val logoSizes: List<String> = listOf(),

	@field:SerializedName("still_sizes")
	val stillSizes: List<String> = listOf(),

	@field:SerializedName("profile_sizes")
	val profileSizes: List<String> = listOf()
)
