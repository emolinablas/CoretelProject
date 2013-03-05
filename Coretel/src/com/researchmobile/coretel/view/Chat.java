package com.researchmobile.coretel.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.researchmobile.coretel.entity.PrimerPlano;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.ws.RequestWS;

public class Chat extends Activity implements OnClickListener, OnKeyListener{
	
	private ListView conversacionListView;
	private ArrayAdapter adapter;
	private BroadcastReceiver mReceiver;
	private List<String> list;
	private EditText agregarEditText;
	private Button enviarButton;
	private boolean enviado;
	private RespuestaWS respuesta;
	

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		setAgregarEditText((EditText)findViewById(R.id.chat_agregar_edittext));
		getAgregarEditText().setOnKeyListener(this);
		setEnviarButton((Button)findViewById(R.id.chat_enviar_button));
		getEnviarButton().setOnClickListener(this);
		setList(new ArrayList<String>());
		getList().add("Wuilder");
		IntentFilter intentFilter = new IntentFilter("android.intent.action.MAIN");
		mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String msg_for_me = intent.getStringExtra("mensaje");
		getAdapter().add(msg_for_me);
		int total = getConversacionListView().getCount();
		getConversacionListView().setSelection(total);
		}
		
		};
		this.registerReceiver(mReceiver, intentFilter);
		
		PrimerPlano.setEstado(true);
		setConversacionListView((ListView)findViewById(R.id.chat_conversacion_listview));
        setAdapter(new ArrayAdapter<String>(this, R.layout.window_chat, R.id.window_chat_item, getList()));
        getConversacionListView().setAdapter(getAdapter());
        getConversacionListView().setClickable(false);
    }
	
	//public void onPaused(){
		//this.unregisterReceiver(this.mReceiver);
	//}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
         {
			 setEnviado(EnviarMensaje());
             return true;
         }
         if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
         {
             //TODO: When the enter key is pressed
             return true;
         }
         return false;

	}
	private boolean EnviarMensaje() {
		String miMensaje = getAgregarEditText().getText().toString();
		if (!miMensaje.equalsIgnoreCase("")){
			try{
				ConnectState connect = new ConnectState();
				if (connect.isConnectedToInternet(Chat.this)){
					RequestWS request = new RequestWS();
					setRespuesta(request.EnviarMensajeChat(miMensaje));
					if (getRespuesta() != null || getRespuesta().isResultado()){
						LimpiaCampo();
						return true;
					}
				}
			}catch(Exception exception){
				
			}
		}else{
			return true;
		}
		return false;
	}

	private void LimpiaCampo() {
		getAgregarEditText().setText("");
		
	}

	@Override
	public void onResume(){
		PrimerPlano.setEstado(true);
		super.onResume();
	}
	
	@Override
	public void onStop(){
		PrimerPlano.setEstado(false);
		super.onStop();
	}
	
	@Override
	public void onPause(){
		PrimerPlano.setEstado(false);
		super.onPause();
	}
	
	private String[] MiLista() {
		String[] lista = {"Hola, Como estas", 
				"Muy bien, gracias", 
				"y tu...?"};
		return lista;
		
	}
	
	@Override
	public void onClick(View v) {
		setEnviado(EnviarMensaje());
		
	}
	public ListView getConversacionListView() {
		return conversacionListView;
	}
	public void setConversacionListView(ListView conversacionListView) {
		this.conversacionListView = conversacionListView;
	}
	public ArrayAdapter<String> getAdapter() {
		return adapter;
	}
	public void setAdapter(ArrayAdapter adapter) {
		this.adapter = adapter;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public EditText getAgregarEditText() {
		return agregarEditText;
	}

	public void setAgregarEditText(EditText agregarEditText) {
		this.agregarEditText = agregarEditText;
	}

	public boolean isEnviado() {
		return enviado;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	public RespuestaWS getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}

	public Button getEnviarButton() {
		return enviarButton;
	}

	public void setEnviarButton(Button enviarButton) {
		this.enviarButton = enviarButton;
	}
	
	
	
}
