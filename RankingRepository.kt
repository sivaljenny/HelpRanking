package br.com.zup.projectfinal.domain

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class RankingRepository {

    private val database = FirebaseDatabase.getInstance()
    private val referenceUsers = database.getReference("bemEstarZupper")

    //Aqui ele vai pegar todos os IDs dos usuários que estão salvos no Realtime do Firebase
    fun getUsers(): Query {
        return referenceUsers
    }


    //Aqui ele vai usar o ID do usuário como parâmetro para pegar o nome de um único usuário
    fun getSingleUserName(pathUserId: String): Query{
        return database.getReference("bemEstarZupper/$pathUserId/NomeUsuario")
    }


    //Aqui ele vai usar o ID do usuário como parâmetro para pegar a pontuação de um único usuário
    fun getSingleUserPoints(pathUserId: String): Query{
        return database.getReference("bemEstarZupper/$pathUserId/Pontuacao").orderByValue()
    }
}