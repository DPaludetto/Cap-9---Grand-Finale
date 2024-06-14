package br.com.fiap.estoque.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController, pOptMentorAluno: String) {
    /*Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFFFFFFF))
        .padding(32.dp)


    ){
    */
    // Espaço

    //val context = LocalContext.current

    Spacer(modifier = Modifier.height(10.dp))

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(color = 0xFF0CB7E2))
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,

                //modifier = Modifier
                //    .fillMaxWidth()
                //.background(Color.White)
                //.background(Color(color = 0xFF0CB7E2))

            ){

                Row(verticalAlignment = Alignment.CenterVertically
                    //horizontalArrangement = Arrangement.SpaceAround
                ) {


                    Text(
                        text = "Controle de Estoque",
                        fontSize = 25.sp,
                        //color = Color(color = 0xFF96A3EC),
                        color = Color.Black,
                        textAlign = TextAlign.Justify
                    )


                    /*
                    //
                    // imagem
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,

                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.chapeuformando),
                            contentDescription = "chapeu",
                            modifier = Modifier.size(75.dp),
                        )

                    }
                    */


                }




                //Botão "Cadastro de perfil"

                // Se Opt = Mentor só mostra o botão de cadastro do mentor
                //if (pOptMentorAluno == "M") {

                Button(
                    onClick = {
                        //navController.navigate("cadmentor/$pOptMentorAluno")
                        navController.navigate("cadproduto")
                    },
                    //colors = ButtonDefaults.buttonColors(Color.Gray),
                    colors = ButtonDefaults.buttonColors(Color(color = 0xFFD2E1E6)),
                    modifier = Modifier.size(width = 250.dp, height = 48.dp)
                ) {
                    Text(
                        text = "Cadastro de Produtos",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }


                Spacer(modifier = Modifier.height(32.dp))

                // parte inferior
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        //.background(Color.White)
                        .fillMaxWidth()
                        .height(70.dp)
                ) {

                    Button(
                        onClick = {
                            navController.navigate("login")
                        },
                        //colors = ButtonDefaults.buttonColors(Color.Gray),
                        colors = ButtonDefaults.buttonColors(Color(color = 0xFFD2E1E6)),
                        modifier = Modifier.size(width = 250.dp, height = 48.dp)
                    ) {
                        Text(text = "Voltar", fontSize = 15.sp, color = Color.Black)
                    }

                }


            }

    //}




}