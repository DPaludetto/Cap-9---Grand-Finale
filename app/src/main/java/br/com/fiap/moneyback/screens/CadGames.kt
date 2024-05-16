package br.com.fiap.moneyback.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.moneyback.database.repository.getAllGames
import br.com.fiap.moneyback.database.repository.getGamesByStudio
import br.com.fiap.moneyback.model.Game

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//fun CadGames(navController: NavController, pOptMentorAluno: String) {
fun CadGames(navController: NavController) {

    // vaslor digitado na pesquisa
    var studioState by remember {
        mutableStateOf("")
    }

    var listGamesByStudio by remember {
        mutableStateOf(getGamesByStudio(studioState))
    }

    Column (modifier = Modifier.padding(16.dp)) {
        Text(text = "Meus jogos favoritos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = studioState,
            onValueChange = {
                studioState = it
                listGamesByStudio = getGamesByStudio(studioState)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Nome do jogo")

            },
            trailingIcon = {
                IconButton(onClick = {
                    listGamesByStudio = getGamesByStudio(studioState)
                }) {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow() {
            items(listGamesByStudio) {
                StudioCard(game = it)
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(){
            items(listGamesByStudio){
                //Text(text = "${it.title}  ")
                GameCard(game = it)
            }
        }


    }
}


@Composable
fun StudioCard(game: Game) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .padding(end = 4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = game.studio)
        }
    }
}

@Composable
fun GameCard(game: Game) {

    Card(modifier = Modifier.padding(bottom = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(3f)) {
                Text(
                    text = game.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = game.studio,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Text(
                text = game.releaseYear.toString() ,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )



        }
    }









}