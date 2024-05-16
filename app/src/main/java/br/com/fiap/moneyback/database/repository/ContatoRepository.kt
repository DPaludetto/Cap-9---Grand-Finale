package br.com.fiap.moneyback.database.repository

import android.content.Context
import br.com.fiap.moneyback.database.dao.ContatoDb
import br.com.fiap.moneyback.model.Contato
import br.com.fiap.moneyback.model.Tbl_aluno

class ContatoRepository(context: Context) {

    var db = ContatoDb.getDatabase(context).contatoDao()

    fun salvar(contato: Contato): Long {
        return db.salvar(contato = contato)
    }

    fun atualizar(contato: Contato): Int {
        return db.atualizar(contato = contato)
    }

    fun excluir(contato: Contato): Int {
        return db.excluir(contato = contato)
    }

    fun buscarMentorPeloId(id: Int): Contato {
        return db.buscarMentorPeloId(id = id)
    }

    fun listarContatos(): List<Contato> {
        return db.listarContatos()
    }

}






    class AlunoRepository(context: Context) {

        var db = ContatoDb.getDatabase(context).tbl_alunoDao()


    fun salvarAluno(tblAluno: Tbl_aluno): Long {
        return db.salvar(tbl_aluno = tblAluno)
    }

    fun atualizarAluno(tblAluno: Tbl_aluno): Int {
        return db.atualizar(tbl_aluno = tblAluno)
    }

    fun excluirAluno(tblAluno: Tbl_aluno): Int {
        return db.excluir(tbl_aluno = tblAluno)
    }

    fun buscarAlunoPeloId(id: Long): Tbl_aluno {
        return db.buscarAlunoPeloId (id = id)
    }

    fun listarAlunos(): List<Tbl_aluno> {
        return db.listarAlunos()
    }

}


