package com.researchmobile.coretel.facebook;



import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.researchmobile.coretel.view.R;

public class Login extends Activity{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.sesion_facebook);
		
		List<String> permisos = new ArrayList<String>();
		permisos.add("email");
		
		// start Facebook Login
	    Session.openActiveSession(this, true, new Session.StatusCallback() {
	      // callback when session changes state
	    	
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			// TODO Auto-generated method stub
			  if (session.isOpened()) {

		          // make request to the /me API
		          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

		        	 
		            // callback after Graph API response with user object
		       

					@Override
					public void onCompleted(GraphUser user, Response response) {
						// TODO Auto-generated method stub
						 if (user != null) {
				                TextView welcome = (TextView) findViewById(R.id.lblWelcome);
				               welcome.setText("Hello " + user.getName() + "!" + user.getUsername());
				               JSONObject jsonObject = user.getInnerJSONObject();
				               System.out.println("OBJETOJSON : "+jsonObject.toString());
				              }
					}
		          });
		        }
		}
	    }); 
		
		NewPermissionsRequest perm = new Session.NewPermissionsRequest(this, permisos);
		perm.setCallback(new Session.StatusCallback() {
			
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				// TODO Auto-generated method stub
				if (session.isOpened()) {

			          // make request to the /me API
			          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

			        	 
			            // callback after Graph API response with user object
			       

						@Override
						public void onCompleted(GraphUser user, Response response) {
							// TODO Auto-generated method stub
							 if (user != null) {
					                TextView welcome = (TextView) findViewById(R.id.lblWelcome);
					               welcome.setText("Hello " + user.getName() + "!" + user.getUsername());
					               JSONObject jsonObject = user.getInnerJSONObject();
					               System.out.println("OBJETOJSON : "+jsonObject.toString());
					               System.out.println(user);
					              }
						}
			          });
			        }
				
			}
		});
	
	
	    
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

}



