package br.com.fiap.moneyback.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProdutoService {


    //@GET("")
    //fun getProduto(): Call<ProdutoLst>

    // ENDEREÇO BASE
    //https://viacep.com.br/ws/RS/Porto%20Alegre/Domingos+Jose/json/


    @GET("{id}/{nome}/json/")
    fun getProduto(
        //@Path("uf") uf: String,
        //@Path("cidade") cidade: String,
        //@Path("rua") rua: String


        //@Path("id") id: Long = 0,
        @Path("nome")  nome: String = ""
        //@Path("quantidade") quantidade: String = "",
        //@Path("preco")  preco: String = "",
        //@Path("produtonovo")  produtonovo: Boolean




    //): Call<List<ProdutoLst>>
    ): Call<List<ProdutoService>>

}