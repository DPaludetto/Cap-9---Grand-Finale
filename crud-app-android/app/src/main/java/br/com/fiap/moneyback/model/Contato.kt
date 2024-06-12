package br.com.fiap.moneyback.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tb_produto")
data class Produto (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var nome: String = "",
    var quantidade: String = "",
    var preco: String = "",
    @ColumnInfo(name = "produto_novo") var produtonovo: Boolean = false
)

/*
@Entity(tableName = "tbl_cadmentor")
data class Contato (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var nome: String = "",
    var telefone: String = "",
    var formacao: String = "",
    var area_expertise: String = "",
    var especializacao: String = "",
    var tempo_experiencia: String = "",
    var disponibilidade: String = "",
    @ColumnInfo(name = "aula_online") var aulaonline: Boolean = false
)
*/
/*
@Entity(tableName = "tbl_aluno")
data class Tbl_aluno (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var nome: String = "",
    var telefone: String = "",
    var area_interesse: String = "",
    var especialidade_interesse: String = "",
    var experiencia_atual: String = "",
    var disponibilidade: String = ""

)
*/