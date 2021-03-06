package com.savare.activity.material.designer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.savare.R;
import com.savare.funcoes.rotinas.async.ReceberDadosWebserviceAsyncRotinas;
import com.savare.webservice.WSSisinfoWebservice;

/**
 * Created by Bruno Nogueira Silva on 08/09/2016.
 */
public class RecebeDadosWebserviceFragment extends SlideFragment {

    private ProgressBar progressBarStatus;
    private TextView textStatus;

    public RecebeDadosWebserviceFragment(){

    }

    public static RecebeDadosWebserviceFragment newInstance() {
        return new RecebeDadosWebserviceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_recebe_dados_webservice, container, false);

        progressBarStatus = (ProgressBar) root.findViewById(R.id.fragment_recebe_dados_webservice_progressBar_status);
        textStatus = (TextView) root.findViewById(R.id.fragment_recebe_dados_webservice_text_status);

        String[] tabelaRecebeDados = {  WSSisinfoWebservice.FUNCTION_SELECT_USUARIO_USUA,
                                        WSSisinfoWebservice.FUNCTION_SELECT_SMAEMPRE,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFAAREAS,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFAATIVI,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFASTATU,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFATPDOC,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFACCRED,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFAPORTA,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFAPROFI,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFATPCLI,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFATPCOB,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFAESTAD,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFACIDAD,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFACLIFO,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFAENDER,
                                        WSSisinfoWebservice.FUNCTION_SELECT_CFAPARAM,
                                        //WSSisinfoWebservice.FUNCTION_SELECT_CFAFOTOS,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAPLPGT,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEACLASE,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAUNVEN,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAGRADE,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAMARCA,
                                        //WSSisinfoWebservice.FUNCTION_SELECT_AEACODST,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAPRODU,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAEMBAL,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAPLOJA,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEALOCES,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAESTOQ,
                                        //WSSisinfoWebservice.FUNCTION_SELECT_AEAORCAM,
                                        //WSSisinfoWebservice.FUNCTION_SELECT_AEAITORC,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAPERCE,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAFATOR,
                                        WSSisinfoWebservice.FUNCTION_SELECT_AEAPRREC,
                                        WSSisinfoWebservice.FUNCTION_SELECT_RPAPARCE};

        ReceberDadosWebserviceAsyncRotinas receberDadosWebservice = new ReceberDadosWebserviceAsyncRotinas(new ReceberDadosWebserviceAsyncRotinas.OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {

            }
        },
        getContext());

        receberDadosWebservice.setProgressBarStatus(progressBarStatus);
        receberDadosWebservice.setTextStatus(textStatus);
        receberDadosWebservice.setTabelaRecebeDados(tabelaRecebeDados);
        receberDadosWebservice.execute();

        return root;
    }
}
