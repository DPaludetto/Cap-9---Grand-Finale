package br.com.fiap.moneyback.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.moneyback.model.Produto
//import br.com.fiap.moneyback.model.Tbl_aluno


//## Caso exista mais  classes acrecentar a virgula e colocar como no exemplo abaixo
@Database(
    //entities = [Contato::class,  Tbl_aluno:: class],
    entities = [Produto::class],
    version = 1
)

abstract class ContatoDb: RoomDatabase() {

    abstract fun contatoDao(): ContatoDao
    //abstract fun tbl_alunoDao(): Tbl_alunoDao

    companion object {

        private lateinit var instance: ContatoDb

        fun getDatabase(context: Context): ContatoDb {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        context,
                        ContatoDb::class.java,

                        name = "controleestoque_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

    }

}
