package br.com.fiap.estoque.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



data class Produto (
    var name: String,
    var brand: String,
    var type: String,
    var description: String,
    var price: Double,
    var stockQuantity: Integer
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