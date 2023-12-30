@file:Suppress("DEPRECATION")

package com.example.musik

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musik.compose.ModalBottomSheetContent
import com.example.musik.compose.SheetDragHandlePlayer
import com.example.musik.compose.ViewPlayer
import com.example.musik.compose.screens.HomeScreen
import com.example.musik.compose.screens.InfoProgramScreen
import com.example.musik.compose.screens.PlaylistScreen
import com.example.musik.compose.screens.SearchScreen
import com.example.musik.security.SecurityData
import com.example.musik.ui.theme.MusikTheme

class MainActivity : ComponentActivity() {

private lateinit var viewModel: MainViewModel

    @SuppressLint("MissingPermission", "SourceLockedOrientationActivity")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        checkLogIn(this)

        Dependencies.repository.initVkClient(this)
        viewModel =  MainViewModel(Dependencies.repository, this)

        viewModel.getUser()

        setContent {
            MusikTheme {
                var showBottomSheet by remember { mutableStateOf(false) }
                val listener = object : Observer {
                    override fun onEdit() {
                        showBottomSheet = !showBottomSheet
                    }
                }
                viewModel.listener = listener
                val sheetState = rememberModalBottomSheetState()


                val scaffoldState = rememberBottomSheetScaffoldState()
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable("main") {
                        HomeScreen(
                            mainActivity = this@MainActivity,
                            viewModel = viewModel,
                            playerManager = viewModel.playerManager,
                            navController = navController
                        )
                    }
                    composable("openPlaylist") {
                        PlaylistScreen(
                            mainActivity = this@MainActivity,
                            viewModel = viewModel,
                            playerManager = viewModel.playerManager,
                            navController = navController
                        )
                    }
                    composable("search"){
                        SearchScreen(
                            mainActivity = this@MainActivity,
                            viewModel = viewModel,
                            playerManager = viewModel.playerManager,
                            navController = navController
                        )
                    }
                    composable("info"){
                        InfoProgramScreen()
                    }
                }

                //нижний скролл список - плеер
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = { ViewPlayer(viewModel.playerManager) },
                    sheetPeekHeight = 50.dp,
                    sheetShape = RoundedCornerShape(0.dp),
                    sheetDragHandle = { SheetDragHandlePlayer(this, viewModel) }
                ) {}

                // нижний скролл список настроек для трека
                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        modifier = Modifier
                            .padding(5.dp),
                        sheetState = sheetState,
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        ModalBottomSheetContent()
                    }
                }
            }
        }
    }

    fun checkLogIn(context: Context){
        val securityData = SecurityData(this)
        if (!securityData.containsKey("token")){
            val intent = Intent(context, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if(keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
            viewModel.playerManager.switchPlaying()
            true
        } else
            super.onKeyDown(keyCode, event)
    }
}





