package br.com.fiap.moneyback.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.moneyback.model.Contato
import br.com.fiap.moneyback.model.Tbl_aluno

@Dao
interface ContatoDao {


    @Insert
    fun salvar(contato: Contato): Long

    @Update
    fun atualizar(contato: Contato): Int

    @Delete
    fun excluir(contato: Contato): Int


    @Query("SELECT * FROM tbl_cadmentor WHERE id = :id")
    fun buscarMentorPeloId(id: Int): Contato

// WHERE nome = 'Theo'
    @Query("SELECT * FROM tbl_cadmentor ORDER BY nome ASC")
    fun listarContatos(): List<Contato>


    //@Query("SELECT * FROM tbl_cadmentor mentor ORDER BY nome ASC")
    //@Query("SELECT * FROM tbl_cadmentor mentor WHERE  mentor.nome = :pNome  ORDER BY nome ASC")
    //@Query("SELECT * FROM tbl_cadmentor mentor, tbl_aluno aluno WHERE mentor.area_expertise = aluno.area_interesse and mentor.id = :id  ORDER BY nome ASC")
    //@Query("SELECT mentor.nome || aluno.nome as nome, mentor.telefone, mentor.area_expertise, mentor.especializacao, mentor.disponibilidade FROM tbl_cadmentor mentor, tbl_aluno aluno WHERE mentor.area_expertise = aluno.area_interesse ORDER BY nome ASC")


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
            //"and mentor.nome like 'D%'" +
            "and mentor.nome != :pNome "+
            "ORDER BY nome ASC")

    fun buscarRelMentorAluno(pNome: String): List<Contato>
    //fun buscarRelMentorAluno(): List<Contato>

}


    @Dao
    interface Tbl_alunoDao {

        @Insert
        fun salvar(tbl_aluno: Tbl_aluno): Long

        @Update
        fun atualizar(tbl_aluno: Tbl_aluno): Int

        @Delete
        fun excluir(tbl_aluno: Tbl_aluno): Int

        //@Query("SELECT * FROM tbl_aluno WHERE id = :id")
        //fun buscarAlunoPeloId(id: Int): Tbl_aluno

        @Query("SELECT * FROM tbl_aluno ORDER BY nome ASC")
        fun listarAlunos(): List<Tbl_aluno>

    }

