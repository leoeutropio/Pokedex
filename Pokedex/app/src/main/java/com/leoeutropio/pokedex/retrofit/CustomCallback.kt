package com.leoeutropio.pokedex.retrofit


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.leoeutropio.pokedex.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CustomCallback<T>(var context: Context, onResponse: OnResponse<T>) :
    Callback<T> {
    private val dialog: Dialog
    var onResponse: OnResponse<*>
    var viewLayout: View? = null
    override fun onResponse(call: Call<T>, response: Response<T>) {
        dialog.dismiss()


        if (response.isSuccessful) {
            onResponse.onResponse(response.body())
        } else {
            try {
                val erro = response.errorBody()!!.string()
                onResponse.onFailure(java.lang.Exception(erro))
            } catch (ex: java.lang.Exception) {
                onResponse.onFailure(java.lang.Exception("Ocorreu um erro"))
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        dialog.dismiss()
        if (viewLayout != null) {
            val snackbar = Snackbar
                .make(viewLayout!!, "Problema de conexao", Snackbar.LENGTH_LONG)
                .setAction(
                    "Tentar novamente"
                ) { onResponse.onRetry(Exception(t)) }
            snackbar.show()
        } else {
            //Cria o gerador do AlertDialog
            val builder = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
            //define o titulo
            builder.setTitle("Problema de conexao")
            //define a mensagem
            builder.setMessage("Gostaria de tentar novamente")
            //define um botão como positivo
            builder.setPositiveButton(
                "Sim"
            ) { arg0, arg1 ->
                arg0.dismiss()
                onResponse.onRetry(Exception(t))
            }
            //define um botão como negativo.
            builder.setNegativeButton(
                "Não"
            ) { arg0, arg1 ->
                arg0.dismiss()
                onResponse.onFailure(Exception(t))
            }
            //cria o AlertDialog
            val alerta = builder.create()
            //Exibe
            alerta.show()
        }
    }

    fun useSnackBar(view: View?) {
        viewLayout = view
    }

    interface OnResponse<T> {
        fun onResponse(response: Any?)
        fun onFailure(t: Exception?)
        fun onRetry(t: Exception?)
    }

    init {
        dialog = Dialog(context, R.style.FullscreenThemeMenu)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.setCancelable(false)
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.show()
        this.onResponse = onResponse
    }
}