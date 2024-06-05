package br.com.fiap.moneyback.database.repository

import android.content.Context
import br.com.fiap.moneyback.database.dao.ContatoDb
import br.com.fiap.moneyback.model.Produto
//import br.com.fiap.moneyback.model.Tbl_aluno

class ContatoRepository(context: Context) {

    var db = ContatoDb.getDatabase(context).contatoDao()

    fun salvar(contato: Produto): Long {
        return db.salvar(contato = contato)
    }

    fun atualizar(contato: Produto): Int {
        return db.atualizar(contato = contato)
    }

    fun excluir(contato: Produto): Int {
        return db.excluir(contato = contato)
    }

    //fun buscarMentorPeloId(id: Int): Contato {
    //   return db.buscarMentorPeloId(id = id)
    //}

    fun listarContatos(): List<Produto> {
        return db.listarContatos()
    }

    //fun buscarRelMentorAluno(pExp: String): List<Contato> {
    //    return db.buscarRelMentorAluno(pExp = pExp)
    //}

    //fun buscarRelAlunoMentor(): List<Contato> {
    //    return db.buscarRelAlunoMentor()
    //}


}
