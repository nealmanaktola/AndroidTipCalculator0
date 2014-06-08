package com.example.tipcalculator;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TipCalculatorActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		setContentView(R.layout.activity_tipcalculator);

		
	}
	private void showEditDialog(String title, String message) {
	      FragmentManager fm = getSupportFragmentManager();
	      ErrorDialog editNameDialog = ErrorDialog.newInstance(title,message);
	      editNameDialog.show(fm, "fragment_edit_name");
	}
	public void hideSoftKeyboard(View view){
		  InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		  imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	public void onTipCalculate(View v) {
		//Hide Keyboard
		hideSoftKeyboard(v);
		BigDecimal tipPercent = new BigDecimal(((Button) v).getTag().toString());
		EditText etAmount = (EditText) findViewById(R.id.etAmount);
		
		TextView tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
		
		try {
			BigDecimal amount = new BigDecimal(etAmount.getText().toString());
			BigDecimal tipAmount = amount.multiply(tipPercent);
			
			//Formatting to US currency
			NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);
			usdCostFormat.setMinimumFractionDigits( 2 );
			usdCostFormat.setMaximumFractionDigits( 2 );
			
			tvTipAmount.setText("Tip is: " + usdCostFormat.format(tipAmount.doubleValue()) );
			
		}
		catch (NumberFormatException ne){
			showEditDialog("Error", "Amount is empty/incorrect. Please enter in a correct amount.");
			tvTipAmount.setText("");
			
			etAmount.setSelectAllOnFocus(true);
			
		}	
		

		
		
	}
}
