package br.com.fiap.moneyback

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.moneyback.screens.CadastroAlunos
import br.com.fiap.moneyback.screens.CadastroMentores
import br.com.fiap.moneyback.screens.LoginScreen
import br.com.fiap.moneyback.screens.MenuScreen
import br.com.fiap.moneyback.screens.PesquisaAlunos
import br.com.fiap.moneyback.screens.PesquisaMentores
import br.com.fiap.moneyback.screens.RelAlunoMentor
import br.com.fiap.moneyback.screens.RelMentorAluno
import br.com.fiap.moneyback.ui.theme.MoneyBackTheme

class  MainActivity : ComponentActivity() {
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

                        composable(route = "cadmentor") {
                            CadastroMentores(navController)
                        }

                        composable(route = "cadaluno") {
                            CadastroAlunos(navController)
                        }

                        //composable(route = "pesquisamentor/{OptMentorAluno}") {
                        composable(route = "pesquisamentor") {
                            //val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
                            PesquisaMentores(navController)
                            //    }
                        }

                        composable(route = "pesquisaaluno") {
                            //val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
                            PesquisaAlunos(navController)
                        }

                        composable(route = "relmentoraluno/{OptMentorAluno}") {
                            val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
                            RelMentorAluno(navController)
                        }

                        composable(route = "relalunomentor/{OptMentorAluno}") {
                            val OptMentorAluno = it.arguments?.getString("OptMentorAluno")
                            RelAlunoMentor(navController)
                        }

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




/*

@Composable
fun ContatosScreen() {

    var nomeState = remember {
        mutableStateOf(value = "")
    }

    var telefoneState = remember {
        mutableStateOf(value =  "")
    }

    var amigoState = remember {
        mutableStateOf( value = false)
    }

    val context = LocalContext.current
    val contatoRepository = ContatoRepository(context)

    var listaContatosState = remember {
        mutableStateOf(contatoRepository.listarContatos())
    }



    Column {
        ContatoForm(
            nome = nomeState.value,
            telefone = telefoneState.value,
            amigo = amigoState.value,

            onNomeChange = {
                nomeState.value = it
            },

            ontelefoneChange = {
                telefoneState.value = it
            },

            onAmigoChange = {
                amigoState.value = it
            },

            atualizar = {
                listaContatosState.value = contatoRepository.listarContatos()
            }
        )
        ContatoList(
            listaContatosState,
            atualizar = {listaContatosState.value = contatoRepository.listarContatos()}
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContatoForm(
    nome: String,
    telefone: String,
    amigo: Boolean,
    onNomeChange: (String) -> Unit,
    ontelefoneChange: (String) -> Unit,
    onAmigoChange: (Boolean) -> Unit,
    atualizar: () -> Unit
){

    //Obter contexto
    val context = LocalContext.current
    val contatoRepository = ContatoRepository(context)

    Column(
        modifier = Modifier.padding(16.dp)
    ){
        Text(text = "Cadastro de contatos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(color = 0xFFE91E63
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = { onNomeChange(it)},
            modifier = Modifier.fillMaxWidth(),
            label = {Text(text = "Nome do contato" )},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                                                capitalization = KeyboardCapitalization.Words),
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = telefone,
            onValueChange = { ontelefoneChange  (it)},
            modifier = Modifier.fillMaxWidth(),
            label = {Text(text = "Telefone do contato")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = amigo, onCheckedChange = {
                onAmigoChange(it)
            })
            Text(text = "Amigo")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val contato = Contato(
                    id = 0,
                    nome = nome,
                    telefone = telefone,
                    isAmigo = amigo
                )

                contatoRepository.salvar(contato)
                atualizar()

            },
            modifier = Modifier.fillMaxWidth()
        )

        {
            Text(
                text = "CADASTRAR",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun ContatoList(ListaContatos: MutableState<List<Contato>>,
                atualizar: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()) //SCROLL VERTICAL DA COLUMN
    ) {
        //for (i in 0 <=..<= 10){
        for (contato in ListaContatos.value){
            ContatoCard(contato, atualizar)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun ContatoCard(contato: Contato, atualizar: () -> Unit){
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
                    text = contato.nome,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = contato.telefone,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if(contato.isAmigo) "Amigo" else "Contato",
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



*/