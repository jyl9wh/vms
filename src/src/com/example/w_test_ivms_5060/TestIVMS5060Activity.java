package com.example.w_test_ivms_5060;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class TestIVMS5060Activity extends Activity {
	
    private final String INTENT_ACTION = "com.android.iVMS_5060.CameraList.View";
    private final String USERNAME = "UserName";
    private final String PASSWORD= "Password";
    private final String SERVER_ADDRESS = "ServerAddress";	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_ivms5060);
		
		//test("dx", "12345", "www.3gvs.net:81");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test_ivms5060, menu);
		return true;
	}
	
    private void test(String u, String p, String s) {
        Intent intent = new Intent();
        intent.setAction(INTENT_ACTION);
        Bundle data = new Bundle();
        data.putString(USERNAME, u);
        data.putString(PASSWORD, p);
        data.putString(SERVER_ADDRESS, s);
        intent.putExtras(data);
        startActivity(intent);
    }
    
    public void btnClick(View v){
    	
    	String u = ((EditText)findViewById(R.id.editText1)).getText().toString();
    	String p = ((EditText)findViewById(R.id.editText2)).getText().toString();
    	String s = ((EditText)findViewById(R.id.editText3)).getText().toString();   	
    	    	
    	test(u, p, s);	
    }

}
