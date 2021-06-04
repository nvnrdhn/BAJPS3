package com.nvnrdhn.bajps3

import android.app.Application
import com.nvnrdhn.bajps3.data.TMDBApiService
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application()