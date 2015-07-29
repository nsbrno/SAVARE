package com.savare.funcoes.rotinas.receptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.savare.beans.PessoaBeans;
import com.savare.funcoes.rotinas.PessoaRotinas;
import com.savare.funcoes.rotinas.async.EnviarCadastroClienteFtpAsyncRotinas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faturamento on 29/07/2015.
 */
public class ReceptorAlarmeEnviarCadastroPessoaBroadcastRotinas extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        enviarOrcamentoFtpAsync();
    }


    private void enviarOrcamentoFtpAsync(){

        final PessoaRotinas pessoaRotinas = new PessoaRotinas(context);
        double quantidadeCadastroNovo = pessoaRotinas.quantidadeCadastroPessoaNovo();

        if (quantidadeCadastroNovo > 0){

            String where = " (CFACLIFO.ID_CFACLIFO < 0) AND (CFACLIFO.STATUS_CADASTRO_NOVO = N) ";

            List<PessoaBeans> listaPessoasCadastro = new ArrayList<PessoaBeans>();
            // Pega a lista de pessoa a serem enviadas os dados
            listaPessoasCadastro = pessoaRotinas.listaPessoaCompleta(PessoaRotinas.KEY_TIPO_CLIENTE, where);
            // Checa se retornou alguma lista
            if (listaPessoasCadastro != null && listaPessoasCadastro.size() > 0) {
                EnviarCadastroClienteFtpAsyncRotinas enviarCadastro = new EnviarCadastroClienteFtpAsyncRotinas(context, EnviarCadastroClienteFtpAsyncRotinas.TELA_RECEPTOR_ALARME);
                // Executa o envio do cadastro em segundo plano
                enviarCadastro.execute(listaPessoasCadastro);
            }
        }
    } // Fim enviarOrcamentoFtpAsync
}
