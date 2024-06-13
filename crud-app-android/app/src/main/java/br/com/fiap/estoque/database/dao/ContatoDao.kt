package br.com.fiap.estoque.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.estoque.model.Produto
//import br.com.fiap.moneyback.model.Tbl_aluno

@Dao
interface ContatoDao {

    @Insert
    fun salvar(contato: Produto): Long

    @Update
    fun atualizar(contato: Produto): Int

    @Delete
    fun excluir(contato: Produto): Int


    //@Query("SELECT * FROM tbl_cadmentor WHERE id = :id")
    //fun buscarMentorPeloId(id: Int): Contato

// WHERE nome = 'Theo'
    @Query("SELECT * FROM tb_produto ORDER BY nome ASC")
    fun listarContatos(): List<Produto>


/*
    //Relação de conexões de Mentores por Aluno
    @Query("SELECT " +
            "mentor.id, " +
            "mentor.nome as nome, " +
            //"'Mentor:' || mentor.nome || ' - Aluno:' || aluno.nome as nome, " +
            "mentor.telefone, " +
            "mentor.formacao," +
            "mentor.area_expertise, " +
            "mentor.especializacao, " +
            "mentor.disponibilidade, " +
            "mentor.tempo_experiencia, " +
            "mentor.aula_online " +
            "FROM " +
            "tbl_cadmentor mentor, " +
            "tbl_aluno aluno " +
            "WHERE " +
            "mentor.area_expertise = aluno.area_interesse " +
            //"and mentor.area_expertise like :pExp "+
            "group by" +
            " mentor.nome, " +
            " mentor.telefone " +
            "ORDER BY nome ASC")

    //fun buscarRelMentorAluno(pExp: String): List<Contato>
    fun buscarRelAlunoMentor(): List<Contato>
*/
}

