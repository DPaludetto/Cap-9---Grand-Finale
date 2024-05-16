package br.com.fiap.moneyback.screens

import androidx.compose.animation.expandHorizontally
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
        mutableStateOf(-1)
    }


    Column(
        //verticalArrangement = Arrangement.Center,

        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)

    ) {

        // Espaço
        Spacer(modifier = Modifier.height(20.dp))

        // Row da imagem
        Row(
            //horizontalArrangement = Arrangement.SpaceAround
        ) {


            Text(
                text = "Mentoria Acadêmica",
                fontSize = 30.sp,
                color = Color.Gray,
                textAlign = TextAlign.Justify
            )
        }

        // imagem
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.chapeuformando), // coracao
                contentDescription = "chapeuformando",//"coracao",
                modifier = Modifier.size(200.dp),
            )

        }


        // Espaço
        Spacer(modifier = Modifier.height(20.dp))


        // Campo Login
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

        // Espaço
        Spacer(modifier = Modifier.height(25.dp))

        // Campo Password
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
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_login_24),
                    contentDescription = "Botão de Login"
                )
            }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = erroPWD
        )


   // }  /// teste



        // Espaço
        Spacer(modifier = Modifier.height(10.dp))

        // Radio button Mentor
        Row(verticalAlignment = Alignment.CenterVertically,    ){
            RadioButton(
                selected = OptMentorAluno == 0,
                onClick = { OptMentorAluno = 0 },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Black,
                    unselectedColor = Color.Gray
                )
            )
            Text(text = "Mentor")
        }

        // Radio button Aluno
        Row(verticalAlignment = Alignment.CenterVertically){
            RadioButton(
                selected = OptMentorAluno == 1,
                onClick = { OptMentorAluno = 1 },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Black,
                    unselectedColor = Color.Gray
                )

            )
            Text(text = "Aluno")
        }

        // Radio button Ambos
        Row(verticalAlignment = Alignment.CenterVertically){
            RadioButton(
                selected = OptMentorAluno == 2,
                onClick = { OptMentorAluno = 2 },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Black,
                    unselectedColor = Color.Gray
                )

            )
            Text(text = "Ambos")
        }



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
                    .background(Color.White)
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
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier.size(width = 150.dp, height = 48.dp)
                ) {
                    Text(text = "Entrar", fontSize = 15.sp, color = Color.Black)
                }
            }





            /*
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


/*Text(
    text = "Hello $name!",
    color = Color.Red,
    modifier = modifier
)*/
