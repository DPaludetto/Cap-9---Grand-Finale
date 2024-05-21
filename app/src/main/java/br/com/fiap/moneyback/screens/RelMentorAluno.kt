package br.com.fiap.moneyback.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.moneyback.database.repository.AlunoRepository
import br.com.fiap.moneyback.model.Tbl_aluno

@Composable
fun RelMentorAluno(navController: NavController) {

    var nomeState = remember {
        mutableStateOf(value = "")
    }

    var telefoneState = remember {
        mutableStateOf(value =  "")
    }

    //var formacaoState = remember {
    //    mutableStateOf(value =  "")
    //}

    var area_interesseState = remember {
        mutableStateOf(value =  "")
    }

    var especializacaoState = remember {
        mutableStateOf(value =  "")
    }

    var tempo_experienciaState = remember {
        mutableStateOf(value =  "")
    }

    var disponibilidadeState = remember {
        mutableStateOf(value =  "")
    }

    //var amigoState = remember {
    //    mutableStateOf( value = false)
    //}





    val context = LocalContext.current
    val alunoRepository = AlunoRepository(context)

    // valor digitado na pesquisa
    var studioState by remember {
        mutableStateOf("")
    }

    var listaAlunoState = remember {
        mutableStateOf(alunoRepository.buscarRelMentorAluno())
        //mutableStateOf(contatoRepository.buscarRelMentorAluno(studioState))
    }

    var idState by remember {
        mutableStateOf(0)
    }


    Column {
        ContatoForm3(
            nome = nomeState.value,
            telefone = telefoneState.value,
//            formacao = formacaoState.value,
            area_interesse = area_interesseState.value,
            especializacao = especializacaoState.value,
            tempo_experiencia = tempo_experienciaState.value,
            disponibilidade = disponibilidadeState.value,
//            amigo = amigoState.value,

            onNomeChange = {
                nomeState.value = it
            },

            ontelefoneChange = {
                telefoneState.value = it
            },

            //onformacaoChange =  {
            //    formacaoState.value = it
            //},

            onarea_interesseChange =  {
                area_interesseState.value = it
            },

            onespecializacaoChange =  {
                especializacaoState.value = it
            },

            ontempo_experienciaChange =  {
                tempo_experienciaState.value = it
            },

            ondisponibilidadeChange =  {
                disponibilidadeState.value = it
            },

            //onAmigoChange = {
            //    amigoState.value = it
            //},



            atualizar = {
                listaAlunoState.value = alunoRepository.buscarRelMentorAluno()
                //listaContatosState.value = contatoRepository.buscarRelMentorAluno(studioState)
            }
        )



        // ######################## \\
        // ####### MAIN ########### \\
        // ######################## \\
        ContatoList3(
            // Certo
            listaAlunoState,
            atualizar = {listaAlunoState.value = alunoRepository.buscarRelMentorAluno()
            //atualizar = {listaContatosState.value = contatoRepository.buscarRelMentorAluno(studioState)
            }
        )

    }
}





// ######################## \\
// ####### FUNÇÕES ######## \\
// ######################## \\



// ############################# \\
// ####### ContatoForm3 ######## \\
// ############################# \\
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContatoForm3(
    nome: String,
    telefone: String,
    //formacao: String,
    area_interesse: String,
    especializacao: String,
    tempo_experiencia: String,
    disponibilidade: String,
    //amigo: Boolean,

    onNomeChange: (String) -> Unit,
    ontelefoneChange: (String) -> Unit,
    //onformacaoChange: (String) -> Unit,
    onarea_interesseChange: (String) -> Unit,
    onespecializacaoChange: (String) -> Unit,
    ontempo_experienciaChange: (String) -> Unit,
    ondisponibilidadeChange: (String) -> Unit,
    //onAmigoChange: (Boolean) -> Unit,
    atualizar: () -> Unit
){

    //Obter contexto
    val context = LocalContext.current
    val alunoRepository = AlunoRepository(context)

    // vaslor digitado na pesquisa
    var studioState by remember {
        mutableStateOf("")
    }

    var listaAlunoState = remember {
        mutableStateOf(alunoRepository.buscarRelMentorAluno())
        //mutableStateOf(contatoRepository.buscarRelMentorAluno(studioState))
    }

    Spacer(modifier = Modifier.height(15.dp))

    Column(
        modifier = Modifier.padding(14.dp),

    ){
        Text(text = "      Sujestão de Conexões",
            fontSize = 20.sp,
            textAlign = TextAlign.Justify,
            fontWeight = FontWeight.Bold,
            color = Color(color = 0xFF96A3EC)
        )

        Spacer(modifier = Modifier.height(2.dp))


    }
}




// ############################### \\
// ######## ContatoList3 ######### \\
// ############################### \\
@Composable
fun ContatoList3(ListaContatos: MutableState<List<Tbl_aluno>>,
                atualizar: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()) //SCROLL VERTICAL DA COLUMN
    ) {
        //for (i in 0 <=..<= 10){
        for (contato in ListaContatos.value){
            ContatoCard3(contato, atualizar)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

/*
@Composable
fun ContatoListPeloId3(ListaContatos: MutableState<List<Contato>>,
                 atualizar: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()) //SCROLL VERTICAL DA COLUMN
    ) {
        //for (i in 0 <=..<= 10){
        for (contato in ListaContatos.value){
            ContatoCard3(contato, atualizar)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
*/

// ############################### \\
// ######## ContatoCard3 ######### \\
// ############################### \\
@Composable
fun ContatoCard3(contato: Tbl_aluno, atualizar: () -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .padding(8.dp)
                .weight(2f)
            ) {
                Text(
                    text =  "Aluno(a): ${contato.nome}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Contato: ${contato.telefone}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Área: ${contato.area_interesse}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Especialidade: ${contato.especialidade_interesse}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Experiência: ${contato.experiencia_atual}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Disponibilidade: ${contato.disponibilidade}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            IconButton(onClick = {
             /*
                val alunoRepositoryRepository = ContatoRepository(context = context)
                alunoRepositoryRepository.excluir(contato = contato)
                atualizar()
*/
            }) {
                //Icon(
                    //imageVector = Icons.Default.Delete,
                    //contentDescription = ""
                //)
            }
        }
    }

}




