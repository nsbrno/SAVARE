package com.savare.activity.material.designer;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.savare.R;
import com.savare.activity.ClienteListaActivity;
import com.savare.activity.ConfiguracoesActivity;
import com.savare.activity.ListaOrcamentoPedidoActivity;
import com.savare.activity.ListaTitulosActivity;
import com.savare.activity.LogActivity;
import com.savare.activity.ProdutoListaActivity;
import com.savare.activity.SincronizacaoActivity;
import com.savare.banco.funcoesSql.UsuarioSQL;
import com.savare.beans.EmpresaBeans;
import com.savare.beans.UsuarioBeans;
import com.savare.funcoes.FuncoesPersonalizadas;
import com.savare.funcoes.rotinas.EmpresaRotinas;
import com.savare.funcoes.rotinas.OrcamentoRotinas;
import com.savare.funcoes.rotinas.UsuarioRotinas;
import com.savare.funcoes.rotinas.async.ReceberDadosFtpAsyncRotinas;

/**
 * Created by Bruno Nogueira Silva on 04/12/2015.
 */
public class InicioMDActivity extends AppCompatActivity {

    private Toolbar toolbarInicio;
    private Drawer navegacaoDrawerEsquerdo;
    private AccountHeader cabecalhoDrawer;
    private MaterialListView mListView;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio_md);
        // Recupera o campo para manipular
        toolbarInicio = (Toolbar) findViewById(R.id.activity_inicio_md_toolbar_inicio);
        // Adiciona uma titulo para toolbar
        toolbarInicio.setTitle(this.getResources().getString(R.string.app_name));
        toolbarInicio.setTitleTextColor(getResources().getColor(R.color.branco));
        //toolbarInicio.setLogo(R.drawable.ic_launcher);
        // Seta uma toolBar para esta activiy(tela)
        setSupportActionBar(toolbarInicio);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FuncoesPersonalizadas funcoes = new FuncoesPersonalizadas(InicioMDActivity.this);

        UsuarioSQL usuarioSQL = new UsuarioSQL(InicioMDActivity.this);
        // Pega os dadosUsuario do usuario no banco de dadosUsuario
        Cursor dadosUsuario = usuarioSQL.query("ID_USUA = " + funcoes.getValorXml("CodigoUsuario"));

        // Pega o nome de login do usuario
        String nomeCompletoUsua = funcoes.getValorXml("Usuario");
        boolean enviaAutomatico = false;
        boolean recebeAutomatico = false;

        if (funcoes.getValorXml("EnviarAutomatico").equalsIgnoreCase("S")){
            enviaAutomatico = true;
        }

        if (funcoes.getValorXml("ReceberAutomatico").equalsIgnoreCase("S")){
            recebeAutomatico = true;
        }

        // Checa se retornou algum dados do usuario
        if (dadosUsuario != null && dadosUsuario.getCount() > 0){
            // move para o primeiro registro
            dadosUsuario.moveToFirst();
            // Pega o nome completo do usuario
            nomeCompletoUsua = dadosUsuario.getString(dadosUsuario.getColumnIndex("NOME_USUA"));
        }

        // Instancia o cabecalho(conta) do drawer
        cabecalhoDrawer = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorAccent)
                .addProfiles(
                        new ProfileDrawerItem().withName(nomeCompletoUsua).withEmail(funcoes.getValorXml("Email")).withIcon(getResources().getDrawable(R.mipmap.ic_account_circle)))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();

        //Instancia o drawer
        navegacaoDrawerEsquerdo = new DrawerBuilder()
                .withActivity(this)
            .withToolbar(toolbarInicio)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.clientes).withIcon(R.drawable.ic_action_person),
                        new PrimaryDrawerItem().withName(R.string.orcamentos).withIcon(R.drawable.ic_action_view_as_list),
                        new PrimaryDrawerItem().withName(R.string.produtos).withIcon(R.drawable.ic_action_box_produtct),
                        new PrimaryDrawerItem().withName(R.string.titulos).withIcon(R.drawable.ic_action_coins_pay),
                        new PrimaryDrawerItem().withName(R.string.pedidos_a_enviar).withIcon(R.drawable.ic_action_order),
                        new PrimaryDrawerItem().withName(R.string.pedidos_enviados).withIcon(R.drawable.ic_action_order),
                        new PrimaryDrawerItem().withName(R.string.lixeira).withIcon(R.drawable.ic_action_discard),
                        new PrimaryDrawerItem().withName(R.string.sincronizacao).withIcon(R.drawable.ic_action_cloud),
                        new PrimaryDrawerItem().withName(R.string.configuracoes).withIcon(R.drawable.ic_action_settings),
                        new SectionDrawerItem().withName(R.string.monitoramento),
                        new PrimaryDrawerItem().withName(R.string.logs).withIcon(R.drawable.ic_sim_alert),
                        new SwitchDrawerItem().withName(R.string.enviar_automatico).withIcon(R.mipmap.ic_upload).withChecked(enviaAutomatico).withOnCheckedChangeListener(mOnCheckedChangeListener).withTag("enviar"),
                        new SwitchDrawerItem().withName(R.string.receber_automatico).withIcon(R.mipmap.ic_download).withChecked(recebeAutomatico).withOnCheckedChangeListener(mOnCheckedChangeListener).withTag("receber")

                )
            .withDisplayBelowStatusBar(true)
            .withActionBarDrawerToggleAnimated(true)
            .withDrawerGravity(Gravity.LEFT)
            .withSavedInstance(savedInstanceState)
            .withSelectedItem(-1)
            .withActionBarDrawerToggle(true)
            .withAccountHeader(cabecalhoDrawer)

            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                    switch (position) {

                        case 1:
                            // Abre a tela de clientes
                            Intent intent = new Intent(InicioMDActivity.this, ClienteListaActivity.class);
                            startActivity(intent);
                            return true;
                        //break;

                        case 2:
                            // Abre a tela Lista de Orcamento
                            Intent intentListaOrcamentoPedido = new Intent(InicioMDActivity.this, ListaOrcamentoPedidoActivity.class);
                            // Salva um valor para transferir para outrao Activity(Tela)
                            intentListaOrcamentoPedido.putExtra("ORCAMENTO_PEDIDO", OrcamentoRotinas.ORCAMENTO);
                            // Abre outra tela
                            startActivity(intentListaOrcamentoPedido);
                            return true;

                        case 3:
                            // Tela de lista de produtos
                            // Cria um dialog para selecionar atacado ou varejo
                            AlertDialog.Builder mensagemAtacadoVarejo = new AlertDialog.Builder(InicioMDActivity.this);
                            // Atributo(variavel) para escolher o tipo da venda
                            final String[] opcao = {"Atacado", "Varejo"};
                            // Preenche o dialogo com o titulo e as opcoes
                            mensagemAtacadoVarejo.setTitle("Atacado ou Varejo").setItems(opcao, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // Cria uma intent para abrir uma nova activity
                                    Intent intentOrcamento = new Intent(InicioMDActivity.this, ProdutoListaActivity.class);
                                    intentOrcamento.putExtra("ATAC_VAREJO", String.valueOf(which).toString());
                                    startActivity(intentOrcamento);

                                }
                            });

                            // Faz a mensagem (dialog) aparecer
                            mensagemAtacadoVarejo.show();
                            return true;

                        case 4:
                            // Tela de Lista de Titulos
                            Intent intentListaTitulos = new Intent(InicioMDActivity.this, ListaTitulosActivity.class);
                            // Abre outra tela
                            startActivity(intentListaTitulos);
                            return true;

                        case 5:
                            // Tela de Lista de Pedidos nao enviados
                            Intent intentListaPedido = new Intent(InicioMDActivity.this, ListaOrcamentoPedidoActivity.class);
                            // Salva um valor para transferir para outrao Activity(Tela)
                            intentListaPedido.putExtra("ORCAMENTO_PEDIDO", OrcamentoRotinas.PEDIDO_NAO_ENVIADO);
                            // Abre outra tela
                            startActivity(intentListaPedido);
                            return true;

                        case 6:
                            // Tela de Lista de Pedidos nao enviados
                            Intent intentListaPedidoEnviado = new Intent(InicioMDActivity.this, ListaOrcamentoPedidoActivity.class);
                            // Salva um valor para transferir para outrao Activity(Tela)
                            intentListaPedidoEnviado.putExtra("ORCAMENTO_PEDIDO", OrcamentoRotinas.PEDIDO_ENVIADO);
                            // Abre outra tela
                            startActivity(intentListaPedidoEnviado);
                            return true;

                        case 7:
                            // Tela de orcamentos excluidos(lixeira)
                            Intent intentListaExcluido = new Intent(InicioMDActivity.this, ListaOrcamentoPedidoActivity.class);
                            // Salva um valor para transferir para outrao Activity(Tela)
                            intentListaExcluido.putExtra("ORCAMENTO_PEDIDO", OrcamentoRotinas.EXCLUIDO);
                            // Abre outra tela
                            startActivity(intentListaExcluido);
                            return true;

                        case 8:
                            // Tela de sincronização
                            Intent intentListaSincronizacao = new Intent(InicioMDActivity.this, SincronizacaoActivity.class);
                            // Abre outra tela
                            startActivity(intentListaSincronizacao);
                            return true;

                        case 9:
                            // Tela de sincronização
                            Intent intentListaConfiguracoes = new Intent(InicioMDActivity.this, ConfiguracoesActivity.class);
                            // Abre outra tela
                            startActivity(intentListaConfiguracoes);
                            return true;

                        case 11:
                            // Tela de sincronização
                            Intent intentLogs = new Intent(InicioMDActivity.this, LogActivity.class);
                            // Abre outra tela
                            startActivity(intentLogs);
                            return true;

                        default:
                            return false;
                    }
                }
            })
            .build();

        mListView = (MaterialListView) findViewById(R.id.material_listview);

        /*Card card = new Card.Builder(getApplicationContext())
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_buttons_card)
                .setTitle("1. Step: Declare a MaterialListView in your layout")
                .setDescription("<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                        "    android:layout_width=\"match_parent\"\n" +
                        "    android:layout_height=\"match_parent\"\n" +
                        "    android:paddingLeft=\"@dimen/activity_horizontal_margin\"\n" +
                        "    android:paddingRight=\"@dimen/activity_horizontal_margin\"\n" +
                        "    android:paddingTop=\"@dimen/activity_vertical_margin\"\n" +
                        "    android:paddingBottom=\"@dimen/activity_vertical_margin\">\n" +
                        "\n" +
                        "    <com.dexafree.materialList.view.MaterialListView\n" +
                        "        android:layout_width=\"fill_parent\"\n" +
                        "        android:layout_height=\"fill_parent\"\n" +
                        "        android:id=\"@+id/material_listview\"/>\n" +
                        "\n" +
                        "</RelativeLayout>")
                .addAction(R.id.right_text_button, new TextViewAction(getApplicationContext())
                        .setText("Action")
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                View v = view;
                                String s = card.toString();
                            }

                        }))
                .endConfig()
                .build();

        Card card2 = new Card.Builder(getApplicationContext())
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_welcome_card_layout)
                .setTitle("Welcome Card")
                .setTitleColor(getResources().getColor(R.color.branco))
                .setDescription("I am the description \n I am the description \n I am the description \n I am the description \n You're probably tired of writing code to display notifications in your applications, the library abstracts all the notifications construction process for you in a single line of code. Magic? Lie? I summarize in: productivity. To further improve productivity, pugnotification from release 1.2.0 now has support Android Wear.")
                .setDescriptionColor(getResources().getColor(R.color.branco))
                .setSubtitle("My subtitle!")
                .setSubtitleColor(getResources().getColor(R.color.branco))
                .setBackgroundColor(getResources().getColor(R.color.rosa_escuro))
                .setDividerVisible(true)
                .addAction(R.id.ok_button, new TextViewAction(getApplicationContext()).setTextColor(getResources().getColor(R.color.azul_medio_200))
                        .setText("Fazer alguma coisa")
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                String s = card.toString();
                            }
                        }))
                .endConfig()
                .build();

        Card card3 = new Card.Builder(getApplicationContext())
                .withProvider(new CardProvider())
                .setTitle("Card number 4")
                .setDescription("Lorem ipsum dolor sit amet")
                .setLayout(R.layout.material_basic_buttons_card)
                //.setLeftButtonText("Izquierda")
                //.setRightButtonText("Derecha")

                .addAction(R.id.right_text_button, new TextViewAction(getApplicationContext())
                        .setTextColor(getResources().getColor(R.color.azul_medio_200))
                        .setText("Action").setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                String s = card.toString();
                            }
                        }))

                .endConfig()
                .build();


        // Add card
        mListView.getAdapter().add(card);
        mListView.getAdapter().add(card2);
        mListView.getAdapter().add(card3);*/

        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener(){

            @Override
            public void onItemClick(Card card, int position) {
                String s = card.toString();
            }

            @Override
            public void onItemLongClick(Card card, int position) {
                String s = card.toString();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        navegacaoDrawerEsquerdo.setSelectionAtPosition(-1);

        FuncoesPersonalizadas funcoes = new FuncoesPersonalizadas(InicioMDActivity.this);

        if (!funcoes.getValorXml("RecebendoDados").equalsIgnoreCase("S")) {
            // Marca nos parametro internos que a aplicacao que esta recebendo os dados
            //funcoes.setValorXml("RecebendoDados", "S");

            // Desavia o recebimento automatico
            funcoes.criarAlarmeEnviarReceberDadosAutomatico(true, false);

            ReceberDadosFtpAsyncRotinas receberDadosFtpAsync = new ReceberDadosFtpAsyncRotinas(InicioMDActivity.this, ReceberDadosFtpAsyncRotinas.TELA_LOGIN);
            receberDadosFtpAsync.execute();

            Log.i("SAVARE", "Executou a rotina para receber os dados. - InicioActivity");
        }


        EmpresaRotinas empresaRotinas = new EmpresaRotinas(InicioMDActivity.this);
        // Pega os dados da empresa
        EmpresaBeans dadosEmpresa = empresaRotinas.empresa(funcoes.getValorXml("CodigoEmpresa"));

        UsuarioRotinas usuarioRotinas = new UsuarioRotinas(InicioMDActivity.this);
        // Pega os dados do usuario(vendedor)
        UsuarioBeans dadosUsuario = usuarioRotinas.usuarioCompleto("ID_USUA = " + funcoes.getValorXml("CodigoUsuario"));

        if (dadosEmpresa != null && dadosUsuario != null) {

            // Checa se esta liberado para vender no atacado
            if (dadosUsuario.getVendeAtacadoUsuario() == '1'){
                // Vareavel para salvar a descricao do card
                String descricaoCard = "";

                // Checa se o tipo de acumulo eh por valor para vendas no atacado
                if (dadosEmpresa.getTitpoAcumuloCreditoAtacado().equalsIgnoreCase("V")) {

                    descricaoCard += "Tpo de Acumulo de Crédito:  Por Valor. \n" +
                            "Valor Acumulado: " + getResources().getString(R.string.sigla_real) + " " + funcoes.arredondarValor(dadosUsuario.getValorCreditoAtacado() + "\n");

                    // Checa se o tipo eh por percentual para vendas no atacado
                } else if (dadosEmpresa.getTitpoAcumuloCreditoAtacado().equalsIgnoreCase("P")) {

                    descricaoCard += "Tpo de Acumulo de Crédito:  Por Percentual. \n" +
                            "Percentual Acumulado: " + funcoes.arredondarValor(dadosUsuario.getPercentualCreditoAtacado())+"% \n";

                }
                // Checa os periodo que vai ser acumulado os creditos para vendas no atacado
                if (dadosEmpresa.getPeriodocrceditoAtacado().equalsIgnoreCase("M")) {
                    descricaoCard += "Período do Crédito está Mensal. \n";

                } else if (dadosEmpresa.getPeriodocrceditoAtacado().equalsIgnoreCase("Q")) {
                    descricaoCard += "Período do Crédito está Quinzenal. \n";

                } else if (dadosEmpresa.getPeriodocrceditoAtacado().equalsIgnoreCase("S")) {
                    descricaoCard += "Período do Crédito está Semanal. \n";

                } else if (dadosEmpresa.getPeriodocrceditoAtacado().equalsIgnoreCase("T")) {
                    descricaoCard += "Período do Crédito está Semestral. \n";

                } else if (dadosEmpresa.getPeriodocrceditoAtacado().equalsIgnoreCase("A")) {
                    descricaoCard += "Período do Crédito está Anual. \n";
                }

                Card cardCreditoAtacado = new Card.Builder(getApplicationContext())
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_basic_buttons_card)
                        .setTitle("Resumo de Credito no Atacado")
                        .setDescription(descricaoCard)
                        .addAction(R.id.right_text_button, new TextViewAction(getApplicationContext())
                                .setText("Action")
                                .setListener(new OnActionClickListener() {
                                    @Override
                                    public void onActionClicked(View view, Card card) {
                                        String s = card.toString();
                                    }

                                }))
                        .endConfig()
                        .build();

                mListView.getAdapter().add(cardCreditoAtacado);

                // Checa se esta liberado para vender no varejo
            }

            if (dadosUsuario.getVendeVarejoUsuario() == '1'){
                String descricaoCard = "";
                // Checa se o tipo eh por valor para vendas no varejo
                if (dadosEmpresa.getTitpoAcumuloCreditoVarejo().equalsIgnoreCase("V")) {
                    descricaoCard += "Tpo de Acumulo de Crédito:  Por Valor. \n" +
                            "Valor Acumulado: " + getResources().getString(R.string.sigla_real) + " " + funcoes.arredondarValor(dadosUsuario.getValorCreditoVarejo() + "\n");

                    // Checa se o tipo eh por percentual para vendas no atacado
                } else if (dadosEmpresa.getTitpoAcumuloCreditoVarejo().equalsIgnoreCase("P")) {
                    descricaoCard += "Tpo de Acumulo de Crédito:  Por Percentual. \n" +
                            "Percentual Acumulado: " + funcoes.arredondarValor(dadosUsuario.getPercentualCreditoVarejo())+"% \n";
                }

                // Checa os periodo que vai ser acumulado os creditos para vendas no atacado
                if (dadosEmpresa.getPeriodocrceditoVarejo().equalsIgnoreCase("M")) {
                    descricaoCard += "Período do Crédito está Mensal. \n";

                } else if (dadosEmpresa.getPeriodocrceditoVarejo().equalsIgnoreCase("Q")) {
                    descricaoCard += "Período do Crédito está Quinzenal. \n";

                } else if (dadosEmpresa.getPeriodocrceditoVarejo().equalsIgnoreCase("S")) {
                    descricaoCard += "Período do Crédito está Semanal. \n";

                } else if (dadosEmpresa.getPeriodocrceditoVarejo().equalsIgnoreCase("T")) {
                    descricaoCard += "Período do Crédito está Semestral. \n";

                } else if (dadosEmpresa.getPeriodocrceditoVarejo().equalsIgnoreCase("A")) {
                    descricaoCard += "Período do Crédito está Anual. \n";
                }

                Card cardCreditoVarejo = new Card.Builder(getApplicationContext())
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_basic_buttons_card)
                        .setTitle("Resumo de Credito no Varejo")
                        .setDescription(descricaoCard)
                        .addAction(R.id.right_text_button, new TextViewAction(getApplicationContext())
                                .setText("Action")
                                .setListener(new OnActionClickListener() {
                                    @Override
                                    public void onActionClicked(View view, Card card) {
                                        String s = card.toString();
                                    }

                                }))
                        .endConfig()
                        .build();

                mListView.getAdapter().add(cardCreditoVarejo);
            }

            // Checa se o modo de sincronizacao esta SyncAccount
            if (dadosUsuario.getModoConexao().equalsIgnoreCase("S")) {
                // Cria a conta para o envio automatico do syncAdapter
                funcoes.CreateSyncAccount(InicioMDActivity.this);
            } else {
                funcoes.cancelarSincronizacaoSegundoPlano();
            }
        }
    } // Fim onResume

    /**
     * Pega os cliques feito no sweep do menu drawer.
     */
    private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(IDrawerItem iDrawerItem, CompoundButton compoundButton, boolean b) {

            FuncoesPersonalizadas funcoesP = new FuncoesPersonalizadas(InicioMDActivity.this);

            // Checa se a opcao selecionada eh para enviar
            if (iDrawerItem.getTag().toString().contains("enviar")){
                // Checa se foi escolhido verdadeiro ou false
                if (b){
                    funcoesP.setValorXml("EnviarAutomatico", "S");
                } else {
                    funcoesP.setValorXml("EnviarAutomatico", "N");
                }
            }
            // Checa se a opcao selecionada eh para receber
            if (iDrawerItem.getTag().toString().contains("receber")){
                // Checa se foi escolhido verdadeiro ou false
                if (b){
                    funcoesP.setValorXml("ReceberAutomatico", "S");
                } else {
                    funcoesP.setValorXml("ReceberAutomatico", "N");
                }
            }
            // Executa a funcao para criar os alarmes em background
            funcoesP.criarAlarmeEnviarReceberDadosAutomatico(true, true);
        }
    };
}