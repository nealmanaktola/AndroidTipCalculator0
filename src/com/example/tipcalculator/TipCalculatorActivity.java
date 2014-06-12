package com.example.tipcalculator;

import java.awt.font.NumericShaper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TextView;

public class TipCalculatorActivity extends FragmentActivity {
	
	double tipPercentage;
	int splitNum; 
	private EditText etAmount;
	private TextView tvTipAmount;
	private TextView tvTipPercent;
	private TextView tvSplitNum;
	private TextView tvTipPP; 
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tipcalculator);
		tipPercentage = 0.1;
		splitNum = 1;
		etAmount = (EditText) findViewById(R.id.etAmount);
		etAmount.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				calculateTip();
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	    tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
	    tvTipPercent = (TextView) findViewById(R.id.tvTipPercent);  
		tvTipPercent.setText(Integer.toString((int) (tipPercentage * 100))	);	
		tvSplitNum = (TextView) findViewById(R.id.tvSplit);
		tvTipPP = (TextView) findViewById(R.id.tvTipPP);
		
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
	public void onTipUpClick(View v) {
		tipPercentage+= 0.01;
		tvTipPercent.setText(Integer.toString((int) (tipPercentage * 100)));
		calculateTip();
	}

	public void onTipDownClick(View v) {
		tipPercentage-= 0.01;
		tvTipPercent.setText(Integer.toString((int) (tipPercentage * 100)));
		calculateTip();
	}
	public void onSplitUpClick(View v) {
		if (splitNum < 99)
		{
			splitNum++;
			//Format to two strings
			tvSplitNum.setText(String.format("%02d",splitNum));
			calculateTip();
		}
		else
			showEditDialog("Error", "Sorry! Maximum split is 99");
		
	}
	public void onSplitDownClick(View v) {
		if (splitNum > 1)
		{
			splitNum--;
			tvSplitNum.setText(String.format("%02d",splitNum));
			calculateTip();
		}
		else
		{
			showEditDialog("Error", "Sorry! Minimum split is 1");
		}	
	}
	private void calculateTip() {
		NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);
		usdCostFormat.setMinimumFractionDigits( 2 );
		usdCostFormat.setMaximumFractionDigits( 2 );
		try
		{
			BigDecimal amount = new BigDecimal(etAmount.getText().toString());
			BigDecimal percentage = new BigDecimal(tipPercentage);
			BigDecimal split = new BigDecimal(splitNum);
			BigDecimal tipAmount = amount.multiply(percentage);
			BigDecimal tipPerPerson = tipAmount.divide(split,2, RoundingMode.HALF_UP);
			tvTipAmount.setText("Tip is: " + usdCostFormat.format(tipAmount.doubleValue()) );
			tvTipPP.setText("Tip per person: " + usdCostFormat.format(tipPerPerson.doubleValue()) );
		
		}
		catch (NumberFormatException ne)
		{
			Log.e("value not valid", "amount");
			tvTipAmount.setText("Tip is: ");

		}
	}
	
	public void onTipPresetClick(View v) {
		//Hide Keyboard
		hideSoftKeyboard(v);
		tipPercentage = Double.parseDouble((((ImageButton) v).getTag().toString()));
		tvTipPercent.setText(Integer.toString((int) (tipPercentage * 100)));
		calculateTip();		
	}
}
