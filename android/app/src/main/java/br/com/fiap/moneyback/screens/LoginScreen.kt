package br.com.fiap.moneyback.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.moneyback.R

@Composable
fun LoginScreen(navController: NavController) {

    var login by remember {
        mutableStateOf(value = "")
    }

    var pwd by remember {
        mutableStateOf(value = "")
    }

    // Delimitador de entrada de texto (login)
    val tamanhoMaximoLogin = 30

    // Delimitador de entrada de texto (PWD)
    val tamanhoMaximoPWD = 6

    // Valida erro email
    var erroLogin by remember {
        mutableStateOf(false)
    }

    // Valida erro email
    var erroPWD by remember {
        mutableStateOf(false)
    }

    var OptMentorAluno by remember {
        mutableStateOf(1) // -1
    }


    Column(
        //verticalArrangement = Arrangement.Center,

        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(color = 0xFF0CB7E2))

    ) {

        // Espaço
        Spacer(modifier = Modifier.height(20.dp))

        // Row da imagem
        Row(
            //horizontalArrangement = Arrangement.SpaceAround
        ) {


            Text(
                text = "Controle de Estoque",
                fontSize = 30.sp,
                //color = Color(color = 0xFF05BAF1),
                color = Color.Black,
                textAlign = TextAlign.Justify
            )
        }

        // imagem
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
                //.background(Color.Black)
                .background(Color(color = 0xFF0CB7E2)) //

        ) {
            Image(
                painter = painterResource(id = R.drawable.controleestoque), // coracao
                contentDescription = "controleestoque",//"chapeuformando",
                modifier = Modifier.size(300.dp)
            )

        }



        //Spacer(modifier = Modifier.height(5.dp))


        // ############################ \\
        // ####### CAMPO LOGIN ######## \\
        // ############################ \\
        OutlinedTextField(
            modifier = Modifier.width(300.dp),
            //TextField(
            value = login,
            //onValueChange = { letra ->
            //    login = letra
            onValueChange = {
                if (it.length < tamanhoMaximoLogin) login = it
            },
            singleLine = true,
            placeholder = {
                Text(text = "Entre com seu Login")
                Color(color = 0xFF0F0F0F)
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_supervised_user_circle_24),
                    contentDescription = "Botão de Login"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = erroLogin
        )

        Spacer(modifier = Modifier.height(25.dp))

        // ############################### \\
        // ####### CAMPO PASSWORD ######## \\
        // ############################### \\
        OutlinedTextField(
            //modifier = Modifier.fillMaxWidth(),
            modifier = Modifier.width(300.dp),
            value = pwd,
            onValueChange = { letra ->
                if (letra.length < tamanhoMaximoPWD) pwd = letra
                //pwd = letra
            },
            singleLine = true,
            placeholder = {
                Text(text = "Entre com sua Senha")
                Color(color = 0xFFD2E1E6)
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_login_24),
                    contentDescription = "Botão de Login"
                )
            }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = erroPWD
        )







        // Espaço
//        Spacer(modifier = Modifier.height(10.dp))




        // Espaço
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {


            // Botão Entrar
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    //.background(Color.Black)
                    .background(Color(color = 0xFF0CB7E2))
                    .fillMaxWidth()
                    .height(70.dp)
            ) {

                Button(
                    onClick = {

                        // Validação dos campos email e pwd vazios
                        //if (login.isEmpty()) erroLogin = true else  erroLogin = false
                        //if (pwd.isEmpty()) erroPWD = true else erroPWD = false

                        //if( erroLogin == false && erroPWD == false) navController.navigate("menu")
                        //OptMentorAluno == 0

                        // Variavel que guarda a opção selecionada (Mentor / Aluno)
                        var opt: String

                        // Verifica qual opção foi selecionada e guarda o valor na variável
                        //opt = if(OptMentorAluno == 0) "M" else "A"
                        if (OptMentorAluno == 0) { opt = "M"}  else if (OptMentorAluno == 1) { opt = "A"} else opt = "AM"

                        // Chama a tela de Menu passando o parametro
                        navController.navigate("menu/$opt")


                    },
                    //colors = ButtonDefaults.buttonColors(Color.White),
                    colors = ButtonDefaults.buttonColors(Color(color = 0xFFD2E1E6)),
                    modifier = Modifier.size(width = 150.dp, height = 48.dp)
                ) {
                    Text(text = "Entrar", fontSize = 15.sp, color = Color.Black)
                }
            }





            /* ##### ANTIGO
            // parte inferior
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(70.dp)
            ) {

                Button(
                    onClick = {
                        navController.navigate("menu")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Cyan),
                    modifier = Modifier.size(width = 250.dp, height = 48.dp)
                ) {
                    Text(text = "Voltar", fontSize = 15.sp, color = Color.Black)
                }

            }
            */


        }


    }


}
