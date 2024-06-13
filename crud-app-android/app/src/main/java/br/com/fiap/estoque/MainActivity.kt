@file:OptIn(ExperimentalPermissionsApi::class)

package br.com.fiap.estoque

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import br.com.fiap.moneyback.screens.CadastroAlunos
//import br.com.fiap.moneyback.screens.Produto
import br.com.fiap.estoque.screens.LoginScreen
import br.com.fiap.estoque.screens.MenuScreen
//import br.com.fiap.moneyback.screens.PesquisaAlunos
//import br.com.fiap.moneyback.screens.PesquisaMentores
//import br.com.fiap.moneyback.screens.RelAlunoMentor
//import br.com.fiap.moneyback.screens.RelMentorAluno
import br.com.fiap.estoque.ui.theme.MoneyBackTheme
//import br.com.fiap.moneyback.ui.theme.WaterNotificationService

//import android.Manifest
//import android.os.Bundle
//import androidx.activity.ComponentActivity
import br.com.fiap.estoque.screens.CadastroProdutos

//import com.ad_coding.notificationscourse.ui.theme.NotificationsCourseTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState




class  MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyBackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    val navController = rememberNavController()

                    val postNotificationPermission =
                        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

                    val waterNotificationService = WaterNotificationService(this)

                    LaunchedEffect(key1 = true) {
                        if (!postNotificationPermission.status.isGranted) {
                            postNotificationPermission.launchPermissionRequest()
                        }
                    }


                    waterNotificationService.showBasicNotification()

                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {

                        composable(route = "login") {
                            LoginScreen(navController)
                        }

                        composable(route = "menu/{OptMentorAluno}" ) {
                            val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
                            MenuScreen(navController, OptMentorAluno!!)
                        }

                        composable(route = "cadproduto") {
                            CadastroProdutos(navController)
                        }

//                        composable(route = "cadaluno") {
//                            CadastroAlunos(navController)
//                        }

                        //composable(route = "pesquisamentor/{OptMentorAluno}") {
//                        composable(route = "pesquisamentor") {
                            //val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
//                            PesquisaMentores(navController)
                            //    }
//                        }

//                        composable(route = "pesquisaaluno") {
                            //val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
//                            PesquisaAlunos(navController)
//                        }

//                        composable(route = "relmentoraluno/{OptMentorAluno}") {
//                            val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
//                            RelMentorAluno(navController)
//                        }

//                        composable(route = "relalunomentor/{OptMentorAluno}") {
//                            val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
//                            RelAlunoMentor(navController)
//                        }

                        /*
                        composable(route = "cadastro/{OptMentorAluno}") {
                            val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
                            Cadastro(navController, OptMentorAluno!!)
                        }

                        composable(route = "perfilmentor/{OptMentorAluno}") {
                            val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
                            PerfilMentor(navController, OptMentorAluno!!)

                        composable(route = "cadgames") {
                            CadGames(navController)
                        }
                        */

                    }


                }
            }
        }
    }


}


