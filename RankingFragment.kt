package br.com.zup.projectfinal.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.zup.projectfinal.domain.model.User

class RankingFragment : Fragment() {

    //Aqui estão variáveis criadas VAZIAS para serem usadas nas funções abaixo, uma lista de todos os usuários que aparecerão no ranking.
    // E o nome e pontuação de cada um dos usuários, para ser inserido na lista dentro de um objeto User
    private val usersRankingList = mutableListOf<User>()
    var singleUserName = ""
    var singleUserPoints = ""


    //Criação do viewModel de Ranking
    private val viewModel: RankingViewModel by lazy {
        ViewModelProvider(this)[RankingViewModel::class.java]
    }


    //Nessa função você chama o viewmodel com o getUsersDatabase
    //E já inicia os observáveis
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.getUsersDatabase()
        initObserver()
    }


    //função de observáveis
    private fun initObserver() {

        viewModel.msgState.observe(this.viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        //Esse userState vai pegar uma lista com todos os IDs de usuários salvos no Realtime do Firebase
        // e vai mandar como parâmetro pra função extractUserData (na linha 64 tem a explicação do que essa função faz)
        viewModel.usersState.observe(this.viewLifecycleOwner) {
            extractUserData(it)
        }


        //O singleUserName vai apenas salvar o nome de um único usuário dentro da variável, para ser inserido no objeto User depois
        viewModel.singleUserName.observe(this.viewLifecycleOwner) {
            singleUserName = it.last()
        }

        //E esse vai salvar a pontuação de um único usuário dentro da variável,
        // para ser inserido no objeto User com a função saveAllUsers (na linha 76 tem a expliucação do que a função faz)
        viewModel.singleUserPoints.observe(this.viewLifecycleOwner) {
            singleUserPoints = it.last()
            saveAllUsers(username = singleUserName, userpoints = singleUserPoints)
        }
    }


    //Essa função recebe uma lista de IDs que veio do observável (linha 45),
    // e com o forEach passamos por cada ID da lista e o enviamos para o viewModel que recebe um único nome e uma única pontuação.
    // Ou seja, temos vários IDs, mas pegamos um por vez e selecionamos o nome e pontos de cada um deles,
    // e a partir daí ele vai para os observáveis (linhas 51 e 57)
    private fun extractUserData(usersIdsList: List<String>) {
        usersIdsList.forEach { userId ->
            viewModel.getSingleUserNameDatabase(userId)
            viewModel.getSingleUserPointsDatabase(userId)
        }
    }


    //Essa função adiciona um usuário à lista usersRankingList sempre que o observável for atualizado/chamado na linha 57),
    // assim é esperado que no final no loop do forEach da linha 69 esta lista esteja completa com todos os usuários salvos
    // em uma MutableList do tipo User, que pode ser adicionada ao adapter do recycler view
    private fun saveAllUsers(username: String, userpoints: String){
        usersRankingList.add(User(name = username, points = userpoints.toInt()))

        //A Partir daqui: Você deve adicionar a lista usersRankingList ao Adapter para aparecer os usuários no recycler view
    }

}