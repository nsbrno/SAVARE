package com.savare.activity;

import com.savare.R;
import com.savare.funcoes.FuncoesPersonalizadas;
import com.savare.funcoes.rotinas.ImportarDadosTxtRotinas;
import com.savare.funcoes.rotinas.UsuarioRotinas;
import com.savare.funcoes.rotinas.async.ReceberDadosFtpAsyncRotinas;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SincronizacaoActivity extends Activity {

	private ProgressBar progressReceberDados,
						progressReceberDadosEmpresa,
						progressReceberDadosClientes,
						progressReceberDadosProdutos,
						progressReceberDadosTitulos;
	private TextView textDataUltimoEnvio,
                     textDataUltimoRecebimento,
			 		 textReceberDados,
			 		 textReceberDadosEmpresa,
			 		 textReceberDadosClientes,
			 		 textReceberDadosProdutos,
			 		 textReceberDadosTitulos;
	private Button buttonReceberDados,
				   buttonReceberDadosEmpresa,
				   buttonReceberDadosClientes,
				   buttonReceberDadosProdutos,
				   buttonReceberDadosTitulos;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sincronizacao);
		
		FuncoesPersonalizadas funcoes = new FuncoesPersonalizadas(SincronizacaoActivity.this);
		
		funcoes.bloqueiaOrientacaoTela();
		
		// Ativa a action bar com o simbolo de voltar
		actionBar = getActionBar();
		// 
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		recuperaCampos();
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Instancia a classe de rotinas da tabela usuario
		UsuarioRotinas usuarioRotinas = new UsuarioRotinas(SincronizacaoActivity.this);

		// Pega a data do ultimo envio de pedido
		textDataUltimoEnvio.setText(usuarioRotinas.dataUltimoEnvio(funcoes.getValorXml("CodigoUsuario")));
        // Pega a data do ultimo recebimento de dados
        textDataUltimoRecebimento.setText(usuarioRotinas.dataUltimoRecebimento(funcoes.getValorXml("CodigoUsuario")));

		
		buttonReceberDados.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ReceberDadosFtpAsyncRotinas receberDadosFtpAsync = new ReceberDadosFtpAsyncRotinas(SincronizacaoActivity.this, progressReceberDados, textReceberDados);
				receberDadosFtpAsync.execute();
			}
		});
		
		buttonReceberDadosEmpresa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ReceberDadosFtpAsyncRotinas receberDadosFtpAsync = new ReceberDadosFtpAsyncRotinas(SincronizacaoActivity.this, progressReceberDadosEmpresa, textReceberDadosEmpresa);
				receberDadosFtpAsync.execute(ImportarDadosTxtRotinas.BLOCO_S);
			}
		});
		
		buttonReceberDadosClientes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ReceberDadosFtpAsyncRotinas receberDadosFtpAsync = new ReceberDadosFtpAsyncRotinas(SincronizacaoActivity.this, progressReceberDadosClientes, textReceberDadosClientes);
				receberDadosFtpAsync.execute(ImportarDadosTxtRotinas.BLOCO_C);
			}
		});
		
		buttonReceberDadosProdutos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ReceberDadosFtpAsyncRotinas receberDadosFtpAsync = new ReceberDadosFtpAsyncRotinas(SincronizacaoActivity.this, progressReceberDadosProdutos, textReceberDadosProdutos);
				receberDadosFtpAsync.execute(ImportarDadosTxtRotinas.BLOCO_A);
			}
		});
		
		buttonReceberDadosTitulos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ReceberDadosFtpAsyncRotinas receberDadosFtpAsync = new ReceberDadosFtpAsyncRotinas(SincronizacaoActivity.this, progressReceberDadosTitulos, textReceberDadosTitulos);
				receberDadosFtpAsync.execute(ImportarDadosTxtRotinas.BLOCO_R);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		FuncoesPersonalizadas funcoes = new FuncoesPersonalizadas(SincronizacaoActivity.this);
		funcoes.desbloqueiaOrientacaoTela();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			break;
			
		default:
			break;
		}
			return true;
	} // Fim do onOptionsItemSelected
	
	private void recuperaCampos(){
		textDataUltimoEnvio = (TextView) findViewById(R.id.activity_sincronizacao_text_data_ultimo_envio);
        textDataUltimoRecebimento = (TextView) findViewById(R.id.activity_sincronizacao_text_data_ultimo_recebimento);
		textReceberDados = (TextView) findViewById(R.id.activity_sincronizacao_textView_receber_dados);
		textReceberDadosEmpresa = (TextView) findViewById(R.id.activity_sincronizacao_textView_receber_dados_bloco_s);
		textReceberDadosClientes = (TextView) findViewById(R.id.activity_sincronizacao_textView_receber_dados_bloco_c);
		textReceberDadosProdutos = (TextView) findViewById(R.id.activity_sincronizacao_textView_receber_dados_bloco_a);
		textReceberDadosTitulos = (TextView) findViewById(R.id.activity_sincronizacao_textView_receber_dados_bloco_r);
		buttonReceberDados = (Button) findViewById(R.id.activity_sincronizacao_button_receber_dados);
		buttonReceberDadosEmpresa = (Button) findViewById(R.id.activity_sincronizacao_button_receber_dados_bloco_s);
		buttonReceberDadosClientes = (Button) findViewById(R.id.activity_sincronizacao_button_receber_dados_bloco_c);
		buttonReceberDadosProdutos = (Button) findViewById(R.id.activity_sincronizacao_button_receber_dados_bloco_a);
		buttonReceberDadosTitulos = (Button) findViewById(R.id.activity_sincronizacao_button_receber_dados_bloco_r);
		progressReceberDados = (ProgressBar) findViewById(R.id.activity_sincronizacao_progressBar_receber_dados);
		progressReceberDadosEmpresa = (ProgressBar) findViewById(R.id.activity_sincronizacao_progressBar_receber_dados_bloco_s);
		progressReceberDadosClientes = (ProgressBar) findViewById(R.id.activity_sincronizacao_progressBar_receber_dados_bloco_c);
		progressReceberDadosProdutos = (ProgressBar) findViewById(R.id.activity_sincronizacao_progressBar_receber_dados_bloco_a);
		progressReceberDadosTitulos = (ProgressBar) findViewById(R.id.activity_sincronizacao_progressBar_receber_dados_bloco_r);
	}
}
