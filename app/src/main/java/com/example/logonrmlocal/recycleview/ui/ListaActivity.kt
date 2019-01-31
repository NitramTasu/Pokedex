package com.example.logonrmlocal.recycleview.ui

import android.app.ListActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.logonrmlocal.recycleview.R
import com.example.logonrmlocal.recycleview.api.ClientApi
import com.example.logonrmlocal.recycleview.api.PokemonAPI
import com.example.logonrmlocal.recycleview.model.PokemonResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_lista.*


class ListaActivity : AppCompatActivity() {

    private var diposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        rvPokemons.layoutManager = LinearLayoutManager(this)
        carregarDados()
    }

    private fun exibeErro(erro: String){
        Toast.makeText(this, erro, Toast.LENGTH_LONG).show()
    }

    private fun carregarDados(){

        getPokemonAPI()
                .buscar(151)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<PokemonResponse> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        diposable = d
                    }

                    override fun onNext(t: PokemonResponse) {

                        rvPokemons.adapter = ListaPokemonAdapter( applicationContext , t.content, listener = {
                            Toast.makeText(applicationContext, it.nome, Toast.LENGTH_SHORT).show()
                        })
                    }

                    override fun onError(e: Throwable) {
                        exibeErro(e.message!!)
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        diposable?.dispose()
    }
}

fun getPokemonAPI(): PokemonAPI{
    return ClientApi<PokemonAPI>().getClient(PokemonAPI::class.java)
}
