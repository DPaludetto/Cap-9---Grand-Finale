package br.com.fiap.moneyback.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.moneyback.R

@Composable
fun MenuScreen(navController: NavController, pOptMentorAluno: String) {
    /*Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFFFFFFF))
        .padding(32.dp)


    ){
    */
    // Espaço
    Spacer(modifier = Modifier.height(10.dp))

            Column (
                modifier = Modifier.padding(24.dp),

                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                //modifier = Modifier
                //    .fillMaxWidth()
                //.background(Color.White)

            ){

                Row(verticalAlignment = Alignment.CenterVertically
                    //horizontalArrangement = Arrangement.SpaceAround
                ) {


                    Text(
                        text = "Mentoria Acadêmica",
                        fontSize = 25.sp,
                        color = Color(color = 0xFF96A3EC),
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
                if (pOptMentorAluno == "M") {

                Button(
                    onClick = {
                        //navController.navigate("cadmentor/$pOptMentorAluno")
                        navController.navigate("cadmentor")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier.size(width = 250.dp, height = 48.dp)
                ) {
                    Text(
                        text = "Cadastro de Perfil",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

                //} else if (pOptMentorAluno == "A") {
                } else  {

                    Button(
                        onClick = {
                            //navController.navigate("cadaluno/$pOptMentorAluno")
                            navController.navigate("cadaluno")
                        },
                        colors = ButtonDefaults.buttonColors(Color.Gray),
                        modifier = Modifier.size(width = 250.dp, height = 48.dp)
                    ) {
                        Text(
                            text = "Cadastro de Perfil",
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }

                }


/*
                //
                //Botão "Exemplo Games"
                Button(
                    onClick = {
                        //navController.navigate("perfilmentor/$pOptMentorAluno")
                        navController.navigate("cadgames")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier.size(width = 250.dp, height = 48.dp)
                ) {
                    Text(
                        text = "Exemplo Games",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
 */

                //Botão "Pesquisa Mentores"

                // Se Opt = Mentor só mostra o botão de cadastro do mentor
                if (pOptMentorAluno == "M") {

                Button(
                    onClick = {
                        //navController.navigate("pesquisamentor/$pOptMentorAluno")
                        navController.navigate("pesquisaaluno")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier.size(width = 250.dp, height = 48.dp)
                ) {
                    Text(
                        text = "Encontre seu Aluno",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

                } else  {

                    Button(
                        onClick = {
                            //navController.navigate("pesquisaaluno/$pOptMentorAluno")
                            navController.navigate("pesquisamentor")
                        },
                        colors = ButtonDefaults.buttonColors(Color.Gray),
                        modifier = Modifier.size(width = 250.dp, height = 48.dp)
                    ) {
                        Text(
                            text = "Encontre seu Mentor",
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }

                }


                //Botão "Relacionamento Mentores x Alunos"

                // Se Opt = Mentor só mostra o botão de cadastro do mentor
                if (pOptMentorAluno == "M") {

                Button(
                    onClick = {
                        navController.navigate("relmentoraluno/$pOptMentorAluno")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier.size(width = 250.dp, height = 48.dp)
                ) {
                    Text(
                        text = "Conexões identificadas",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

                } else  {

                    Button(
                        onClick = {
                            navController.navigate("relalunomentor/$pOptMentorAluno")
                        },
                        colors = ButtonDefaults.buttonColors(Color.Gray),
                        modifier = Modifier.size(width = 250.dp, height = 48.dp)
                    ) {
                        Text(
                            text = "Conexões identificadas",
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }

                }


                /*
                // Se Opt = Mentor só mostra o botão de cadastro do mentor
                if (pOptMentorAluno == "M") {

                    //Botão "Cadastro de Mentores"
                    Button(
                        onClick = {
                            //navController.navigate("perfertil")
                            navController.navigate("cadmentor")
                        },
                        colors = ButtonDefaults.buttonColors(Color.Cyan),
                        modifier = Modifier.size(width = 250.dp, height = 48.dp)
                    ) {
                        Text(
                            text = "Cadastro Mentores",
                            //text = "Cadastro Mentores - $pOptMentorAluno",
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }

                } else if (pOptMentorAluno == "A") {

                        //Botão "Cadastro de Alunos"
                        Button(
                            onClick = {
                                //navController.navigate("bebaagua")
                                navController.navigate("cadaluno")
                            },
                            colors = ButtonDefaults.buttonColors(Color.Cyan),
                            modifier = Modifier.size(width = 250.dp, height = 48.dp)
                        ) {
                            Text(text = "Cadastro de Alunos", fontSize = 15.sp, color = Color.Black)
                        }

                } else {



                    //Botão "Cadastro de Mentores"
                    Button(
                        onClick = {
                            //navController.navigate("perfertil")
                            navController.navigate("cadmentor")
                        },
                        colors = ButtonDefaults.buttonColors(Color.Cyan),
                        modifier = Modifier.size(width = 250.dp, height = 48.dp)
                    ) {
                        Text(
                            text = "Cadastro Mentores",
                            //text = "Cadastro Mentores - $pOptMentorAluno",
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }


                    //Botão "Cadastro de Alunos"
                    Button(
                        onClick = {
                            //navController.navigate("bebaagua")
                            navController.navigate("cadaluno")
                        },
                        colors = ButtonDefaults.buttonColors(Color.Cyan),
                        modifier = Modifier.size(width = 250.dp, height = 48.dp)
                    ) {
                        Text(text = "Cadastro de Alunos", fontSize = 15.sp, color = Color.Black)
                    }

                }

                */

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
                        colors = ButtonDefaults.buttonColors(Color.Gray),
                        modifier = Modifier.size(width = 250.dp, height = 48.dp)
                    ) {
                        Text(text = "Voltar", fontSize = 15.sp, color = Color.Black)
                    }

                }


            }

    //}




}