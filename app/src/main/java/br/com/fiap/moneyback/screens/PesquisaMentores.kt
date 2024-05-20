package br.com.fiap.moneyback.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.moneyback.database.repository.ContatoRepository
import br.com.fiap.moneyback.model.Contato

@Composable
fun PesquisaMentores(navController: NavController) {

    var nomeState = remember {
        mutableStateOf(value = "")
    }

    var telefoneState = remember {
        mutableStateOf(value =  "")
    }

    var formacaoState = remember {
        mutableStateOf(value =  "")
    }

    var area_expertiseState = remember {
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

    var amigoState = remember {
        mutableStateOf( value = false)
    }

    val context = LocalContext.current
    val contatoRepository = ContatoRepository(context)

    // vaslor digitado na pesquisa
    var studioState by remember {
        mutableStateOf("")
    }

    var listaContatosState = remember {
        mutableStateOf(contatoRepository.listarContatos())
    }

    var idState by remember {
        mutableStateOf(0)
    }

    //var buscarMentorPeloIdState = remember {
    //    mutableStateOf(contatoRepository.buscarMentorPeloId(4) ) //idState
    //}




    Column {
        ContatoForm1(
            nome = nomeState.value,
            telefone = telefoneState.value,
            formacao = formacaoState.value,
            area_expertise = area_expertiseState.value,
            especializacao = especializacaoState.value,
            tempo_experiencia = tempo_experienciaState.value,
            disponibilidade = disponibilidadeState.value,
            amigo = amigoState.value,

            onNomeChange = {
                nomeState.value = it
            },

            ontelefoneChange = {
                telefoneState.value = it
            },

            onformacaoChange =  {
                formacaoState.value = it
            },

            onarea_expertiseChange =  {
                area_expertiseState.value = it
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

            onAmigoChange = {
                amigoState.value = it
            },


            atualizar = {
                listaContatosState.value = contatoRepository.listarContatos()
                //buscarMentorPeloIdState.value = contatoRepository.buscarMentorPeloId(4)

                //listaContatosState.value = contatoRepository.listarContatos().filter { it.nome.startsWith(prefix = studioState, ignoreCase = true)
            }
        )

        
        ContatoList1(
            // Certo
            listaContatosState,
            atualizar = {listaContatosState.value = contatoRepository.listarContatos()

        //ContatoListPeloId(
        //    buscarMentorPeloIdState,
        //    atualizar = {buscarMentorPeloIdState.value = contatoRepository.buscarMentorPeloId(4)

            //atualizar = {listaContatosState.value = contatoRepository.listarContatos().filter { it.nome .startsWith(prefix = studioState, ignoreCase = true) }

            }
        )

    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContatoForm1(
    nome: String,
    telefone: String,
    formacao: String,
    area_expertise: String,
    especializacao: String,
    tempo_experiencia: String,
    disponibilidade: String,
    amigo: Boolean,

    onNomeChange: (String) -> Unit,
    ontelefoneChange: (String) -> Unit,
    onformacaoChange: (String) -> Unit,
    onarea_expertiseChange: (String) -> Unit,
    onespecializacaoChange: (String) -> Unit,
    ontempo_experienciaChange: (String) -> Unit,
    ondisponibilidadeChange: (String) -> Unit,
    onAmigoChange: (Boolean) -> Unit,
    atualizar: () -> Unit
){

    //Obter contexto
    val context = LocalContext.current
    val contatoRepository = ContatoRepository(context)

    // vaslor digitado na pesquisa
    var studioState by remember {
        mutableStateOf("")
    }

    //var listaContatosState = remember {
//        mutableStateOf(contatoRepository.listarContatos())
//    }


    Column(
        modifier = Modifier.padding(8.dp)
    ){
        Text(text = "Pesquisa de Mentores",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(color = 0xFFE91E63
            )
        )
        Spacer(modifier = Modifier.height(2.dp))


                // ####################### \\
                // ####### CAMPOS ######## \\
                // ####################### \\
                OutlinedTextField(
                    value = studioState,
                    onValueChange = { studioState = it
                                        contatoRepository.buscarRelMentorAluno(studioState)},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Área Atuação" ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words),
                    trailingIcon = {
                        IconButton(onClick = {

                            contatoRepository.buscarRelMentorAluno(studioState)
                            atualizar()

                            /*
                            //atualizar = {
                            //    //listaContatosState.value = contatoRepository.buscarRelMentorAluno()
                            //    listaContatosState.value = contatoRepository.buscarRelMentorAluno(studioState)
                            //}

                            //val context = LocalContext.current
                            //val contatoRepository = ContatoRepository(context)

                            //val contatoRepository = ContatoRepository(context = context)
                            //contatoRepository.excluir(contato = contato)
                            //contatoRepository.buscarRelMentorAluno()
                             */

                        }) {
                            Icon(imageVector = Icons.Default.Search,
                                contentDescription = ""
                            )
                        }
                    }
                )


                OutlinedTextField(
                    value = studioState,
                    onValueChange = { studioState = it
                        contatoRepository.buscarRelMentorAluno(studioState)},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Especialidade" ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words),
                    trailingIcon = {
                        IconButton(onClick = {

                            contatoRepository.buscarRelMentorAluno(studioState)
                            atualizar()

                        }) {
                            Icon(imageVector = Icons.Default.Search,
                                contentDescription = ""
                            )
                        }
                    }
                )



                OutlinedTextField(
                    value = studioState,
                    onValueChange = { studioState = it
                        contatoRepository.buscarRelMentorAluno(studioState)},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Disponibilidade" ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words),
                    trailingIcon = {
                        IconButton(onClick = {

                            contatoRepository.buscarRelMentorAluno(studioState)
                            atualizar()

                        }) {
                            Icon(imageVector = Icons.Default.Search,
                                contentDescription = ""
                            )
                        }
                    }
                )

        //Spacer(modifier = Modifier.height(2.dp))


    }
}






// ####################### \\
// ######## LIST ######### \\
// ####################### \\
@Composable
fun ContatoList1(ListaContatos: MutableState<List<Contato>>,
                atualizar: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()) //SCROLL VERTICAL DA COLUMN
    ) {
        //for (i in 0 <=..<= 10){
        for (contato in ListaContatos.value){
            ContatoCard1(contato, atualizar)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun ContatoListPeloId(ListaContatos: MutableState<List<Contato>>,
                 atualizar: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()) //SCROLL VERTICAL DA COLUMN
    ) {
        //for (i in 0 <=..<= 10){
        for (contato in ListaContatos.value){
            ContatoCard1(contato, atualizar)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}


@Composable
fun ContatoCard1(contato: Contato, atualizar: () -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
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
                    //text =  "Nome:${contato.nome}",
                    text =  contato.nome,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = contato.telefone,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Formação:${contato.formacao}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = contato.area_expertise,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = contato.especializacao,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = contato.tempo_experiencia,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = contato.disponibilidade,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    //text = if(contato.isAmigo) "Amigo" else "Contato",
                    text = if(contato.aulaonline) "Formato de aula: on-line" else "Formato de aula: presencial",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = {
                val contatoRepository = ContatoRepository(context = context)
                contatoRepository.excluir(contato = contato)
                atualizar()

            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = ""
                )
            }
        }
    }
}



