package br.com.zup.projectfinal.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.zup.projectfinal.domain.RankingRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class RankingViewModel(application: Application) : AndroidViewModel(application) {

    private val rankingRepository = RankingRepository()

    private var _msgState = MutableLiveData<String>()
    val msgState: LiveData<String> = _msgState

    private var _usersState = MutableLiveData<List<String>>()
    val usersState: LiveData<List<String>> = _usersState

    private var _singleUserName = MutableLiveData<List<String>>()
    val singleUserName: LiveData<List<String>> = _singleUserName

    private var _singleUserPoints = MutableLiveData<List<String>>()
    val singleUserPoints: LiveData<List<String>> = _singleUserPoints

    //###########################################################################//

    //Aqui ele vai pegar todos os IDs dos usuários que estão salvos no Realtime do Firebase,
    // e vai inserir no liveData _userState para ser usado no observable depois
    fun getUsersDatabase() {
        rankingRepository.getUsers().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usersList = mutableListOf<String>()
                for (resultSnapshot in snapshot.children) {
                    val usersResponse = resultSnapshot.value.toString()
                    usersResponse.let {
                        usersList.add(it)
                    }
                }
                _usersState.value = usersList
            }

            override fun onCancelled(error: DatabaseError) {
                _msgState.value = error.message
            }
        })
    }


    //Aqui ele vai usar o ID do usuário como parâmetro para pegar o nome de um único usuário,
    // e vai inserir no liveData _singleUserName para ser usado no observable depois
    fun getSingleUserNameDatabase(pathUserId: String) {
        rankingRepository.getSingleUserName(pathUserId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val singleUserNameList = mutableListOf<String>()
                for (resultSnapshot in snapshot.children) {
                    val singlUserNameResponse = resultSnapshot.value.toString()
                    singlUserNameResponse.let {
                        singleUserNameList.add(it)
                    }
                }
                _singleUserName.value = singleUserNameList
            }

            override fun onCancelled(error: DatabaseError) {
                _msgState.value = error.message
            }
        })
    }


    //Aqui ele vai usar o ID do usuário como parâmetro para pegar a pontuação de um único usuário,
    // e vai inserir no liveData _singleUserPoints para ser usado no observable depois
    fun getSingleUserPointsDatabase(pathUserId: String) {
        rankingRepository.getSingleUserPoints(pathUserId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val singleUserPointsList = mutableListOf<String>()
                for (resultSnapshot in snapshot.children) {
                    val singlUserPointsResponse = resultSnapshot.value.toString()
                    singlUserPointsResponse.let {
                        singleUserPointsList.add(it)
                    }
                }
                _singleUserPoints.value = singleUserPointsList
            }

            override fun onCancelled(error: DatabaseError) {
                _msgState.value = error.message
            }
        })
    }
}