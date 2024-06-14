package br.com.fiap.estoque.screens

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import br.com.fiap.estoque.database.repository.ContatoRepository
//import br.com.fiap.moneyback.model.Contato
import br.com.fiap.estoque.model.Produto

    @Composable
    fun CadastroProdutos(navController: NavController) {

        var nomeState = remember {
            mutableStateOf(value = "")
        }

        var quantidadeState = remember {
            mutableStateOf(value = "")
        }

        var precoState = remember {
            mutableStateOf(value = "")
        }

        /*
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
    */

        var produtonovoState = remember {
            mutableStateOf(value = false)
        }

        val context = LocalContext.current
        val contatoRepository = ContatoRepository(context)

        var listaContatosState = remember {
            mutableStateOf(contatoRepository.listarContatos())
        }



        Column(modifier = Modifier
                            .fillMaxWidth()
                            //.background(Color(color = 0xFF0CB7E2))
                            .padding(24.dp))
        {
            ContatoForm(
                nome = nomeState.value,
                quantidade = quantidadeState.value,
                preco = precoState.value,
                //area_expertise = area_expertiseState.value,
                //especializacao = especializacaoState.value,
                //tempo_experiencia = tempo_experienciaState.value,
                //disponibilidade = disponibilidadeState.value,
                produtonovo = produtonovoState.value,

                onNomeChange = {
                    nomeState.value = it
                },

                onQuantidadeChange = {
                    quantidadeState.value = it
                },

                onPrecoChange = {
                    precoState.value = it
                },

                /*
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
            */

                onProdutoNovoChange = {
                    produtonovoState.value = it
                },

                atualizar = {
                    listaContatosState.value = contatoRepository.listarContatos()
                }
            )
            ContatoList(
                listaContatosState,
                atualizar = {
                    listaContatosState.value = contatoRepository.listarContatos()

                }
            )
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ContatoForm(
        nome: String,
        quantidade: String,
        preco: String,
        //area_expertise: String,
        //especializacao: String,
        //tempo_experiencia: String,
        //disponibilidade: String,
        produtonovo: Boolean,

        onNomeChange: (String) -> Unit,
        onQuantidadeChange: (String) -> Unit,
        onPrecoChange: (String) -> Unit,
        //onarea_expertiseChange: (String) -> Unit,
        //onespecializacaoChange: (String) -> Unit,
        //ontempo_experienciaChange: (String) -> Unit,
        //ondisponibilidadeChange: (String) -> Unit,
        onProdutoNovoChange: (Boolean) -> Unit,
        atualizar: () -> Unit
    ) {

        //Obter contexto
        val context = LocalContext.current
        val contatoRepository = ContatoRepository(context)

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Cadastro de Produtos",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(color = 0xFF96A3EC)
                //color = Color(color = 0xFF96A3EC)
                //color = Color.Black
            )
            Spacer(modifier = Modifier.height(2.dp))

            // ####################### \\
            // ####### CAMPOS ######## \\
            // ####################### \\
            OutlinedTextField(
                value = nome,
                onValueChange = { onNomeChange(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Produto") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
            )

            Spacer(modifier = Modifier.height(2.dp))

            OutlinedTextField(
                value = quantidade,
                onValueChange = { onQuantidadeChange(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Quantidade") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )

            Spacer(modifier = Modifier.height(2.dp))

            OutlinedTextField(
                value = preco,
                onValueChange = { onPrecoChange(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Preço") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )

            /*
        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = area_expertise,
            onValueChange = { onarea_expertiseChange  (it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Área expertise") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = especializacao,
            onValueChange = { onespecializacaoChange  (it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Especialização") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = tempo_experiencia,
            onValueChange = { ontempo_experienciaChange  (it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Tempo Experiência") },
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
        */

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = produtonovo, onCheckedChange = {
                    onProdutoNovoChange(it)
                })
                Text(text = "Produto novo ?")
            }


            // ####################### \\
            // ####### BOTÃO  ######## \\
            // ####################### \\
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val contato = Produto(
                        id = 0,
                        nome = nome,
                        quantidade = quantidade,
                        preco = preco,
                        //area_expertise = area_expertise,
                        //especializacao = especializacao,
                        //tempo_experiencia = tempo_experiencia,
                        //disponibilidade = disponibilidade,
                        produtonovo = produtonovo
                    )

                    contatoRepository.salvar(contato)
                    atualizar()

                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Gray),
                //colors = ButtonDefaults.buttonColors(Color(color = 0xFFD2E1E6)),
            )

            {
                Text(
                    text = "CADASTRAR",
                    modifier = Modifier.padding(4.dp),
                    color = Color.Black
                )
            }
        }
    }

    // ####################### \\
// ######## LIST ######### \\
// ####################### \\
    @Composable
    fun ContatoList(
        ListaContatos: MutableState<List<Produto>>,
        atualizar: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) //SCROLL VERTICAL DA COLUMN
        ) {
            //for (i in 0 <=..<= 10){
            for (contato in ListaContatos.value) {
                ContatoCard(contato, atualizar)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }

    @Composable
    fun ContatoCard(contato: Produto, atualizar: () -> Unit) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            //colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            val context = LocalContext.current
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(2f)
                        //.background(Color(color = 0xFF0CB7E2))
                ) {
                    Text(
                        text = "Produto: ${contato.nome}",
                        //text =  contato.nome,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Quantidade: ${contato.quantidade}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Preço: ${contato.preco}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )

                    /*
                Text(
                    text = "Área expertise: ${contato.area_expertise}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Especialização: ${contato.especializacao}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Tempo experiência: ${contato.tempo_experiencia}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Disponibilidade: ${contato.disponibilidade}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                */

                    Text(
                        //text = if(contato.isAmigo) "Amigo" else "Contato",
                        text = if (contato.produtonovo) "Produto novo: Sim" else "Produto novo: Não",
                        fontSize = 10.sp,
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

