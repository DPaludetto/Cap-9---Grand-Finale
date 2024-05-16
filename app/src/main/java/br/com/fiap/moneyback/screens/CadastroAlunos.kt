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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import br.com.fiap.moneyback.database.repository.AlunoRepository
import br.com.fiap.moneyback.model.Tbl_aluno

@Composable
fun CadastroAlunos(navController: NavController) {



    var nomeState = remember {
        mutableStateOf(value = "")
    }

    var telefoneState = remember {
        mutableStateOf(value =  "")
    }

    var area_interesseState = remember {
        mutableStateOf(value =  "")
    }

    var especialidade_interesseState = remember {
        mutableStateOf(value =  "")
    }

    var experiencia_atualState = remember {
        mutableStateOf(value =  "")
    }

    var disponibilidadeState = remember {
        mutableStateOf(value =  "")
    }

    //var amigoState = remember {
    //    mutableStateOf( value = false)
    //}

    val context = LocalContext.current
    val tbl_alunoRepository = AlunoRepository(context)

    var listaTbl_alunosState = remember {
        mutableStateOf(tbl_alunoRepository.listarAlunos())
    }



    Column {
        AlunoForm(
            nome = nomeState.value,
            telefone = telefoneState.value,
            area_interesse = area_interesseState.value,
            especialidade_interesse = especialidade_interesseState.value,
            experiencia_atual = experiencia_atualState.value,
            disponibilidade = disponibilidadeState.value,
            //amigo = amigoState.value,

            onNomeChange = {
                //nomeState.value = it
                nomeState.value = it
            },

            ontelefoneChange = {
                telefoneState.value = it
            },

            onarea_interesseChange =  {
                area_interesseState.value = it
            },

            onespecialidade_intertesseChange =  {
                especialidade_interesseState.value = it
            },

            onexperiencia_atualChange =  {
                experiencia_atualState.value = it
            },

            ondisponibilidadeChange =  {
                disponibilidadeState.value = it
            },


            atualizar = {
                listaTbl_alunosState.value = tbl_alunoRepository.listarAlunos()
            }
        )
        ContatoList(
            listaTbl_alunosState,
            atualizar = { listaTbl_alunosState.value = tbl_alunoRepository.listarAlunos() }

        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlunoForm(
    nome: String,
    telefone: String,
    area_interesse: String,
    especialidade_interesse: String,
    experiencia_atual: String,
    disponibilidade: String,
    //amigo: Boolean,

    onNomeChange: (String) -> Unit,
    ontelefoneChange: (String) -> Unit,
    onarea_interesseChange: (String) -> Unit,
    onespecialidade_intertesseChange: (String) -> Unit,
    onexperiencia_atualChange: (String) -> Unit,
    ondisponibilidadeChange: (String) -> Unit,
    //onAmigoChange: (Boolean) -> Unit,
    atualizar: () -> Unit
){

    //Obter contexto
    val context = LocalContext.current
    val tbl_alunoRepository = AlunoRepository(context)

    Column(
        modifier = Modifier.padding(8.dp)
    ){
        Text(text = "Cadastro de Alunos",
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
            value = nome,
            onValueChange = { onNomeChange(it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nome" ) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words),
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = telefone,
            onValueChange = { ontelefoneChange  (it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Contato") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = area_interesse,
            onValueChange = { onarea_interesseChange  (it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Área interesse") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value =  especialidade_interesse ,
            onValueChange = { onespecialidade_intertesseChange  (it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Especialidade interesse") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value =experiencia_atual,
            onValueChange = { onexperiencia_atualChange  (it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Experiência atual") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = disponibilidade,
            onValueChange = { ondisponibilidadeChange  (it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Disponibilidade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        /*
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = amigo, onCheckedChange = {
                onAmigoChange(it)
            })
            Text(text = "Aula on-line ?")
        }
        */

        // ####################### \\
        // ####### BOTÃO  ######## \\
        // ####################### \\
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val tbl_aluno = Tbl_aluno(
                    id = 0,
                    nome = nome,
                    telefone = telefone,
                    area_interesse = area_interesse,
                    especialidade_interesse = especialidade_interesse,
                    experiencia_atual = experiencia_atual,
                    disponibilidade = disponibilidade
                )

                tbl_alunoRepository.salvarAluno(tbl_aluno)
                atualizar()

            },
            modifier = Modifier.fillMaxWidth()
        )

        {
            Text(
                text = "CADASTRAR",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

// ####################### \\
// ######## LIST ######### \\
// ####################### \\
@Composable
fun ContatoList(ListaContatos: MutableState<List<Tbl_aluno>>,
                atualizar: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()) //SCROLL VERTICAL DA COLUMN
    ) {
        //for (i in 0 <=..<= 10){
        for (tbl_aluno in ListaContatos.value){
            AlunoCard(tbl_aluno, atualizar)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun AlunoCard(tblAluno: Tbl_aluno, atualizar: () -> Unit){
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
                    text =  tblAluno.nome,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = tblAluno.telefone,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = tblAluno.area_interesse,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = tblAluno.especialidade_interesse,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = tblAluno.experiencia_atual,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = tblAluno.disponibilidade,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                /*
                Text(
                    //text = if(contato.isAmigo) "Amigo" else "Contato",
                    text = if(tblAluno.aulaonline) "Formato de aula: on-line" else "Formato de aula: presencial",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                */

            }
            IconButton(onClick = {
                val contatoRepository = AlunoRepository(context = context)
                contatoRepository.excluirAluno  (tblAluno = tblAluno)
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
