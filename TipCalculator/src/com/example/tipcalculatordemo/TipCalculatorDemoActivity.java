package com.example.tipcalculatordemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.*;


public class TipCalculatorDemoActivity extends Activity {

	public EditText enteredAmount;
	public TextView tipAmount;
	public TextView totalAmount;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator_demo);
        enteredAmount=(EditText) findViewById(R.id.editText1);
        tipAmount=(TextView) findViewById(R.id.textView2);
        totalAmount=(TextView) findViewById(R.id.textView3);
		
        Button button_10=(Button) findViewById(R.id.button1);
		button_10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					tipAmount.setText("$" + calculateTip(Integer.parseInt(enteredAmount.getText().toString()),10));
					totalAmount.setText("$" + totalBill(Integer.parseInt(enteredAmount.getText().toString()),10));
				}
			});
		
		Button button_15=(Button) findViewById(R.id.button2);
		button_15.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				tipAmount.setText("$" + calculateTip(Integer.parseInt(enteredAmount.getText().toString()),15));
				totalAmount.setText("$" + totalBill(Integer.parseInt(enteredAmount.getText().toString()),15));
				}
			});
		
		Button button_20=(Button) findViewById(R.id.button3);
		button_20.setOnClickListener(new View.OnClickListener() {
	
		@Override
		public void onClick(View v) {
			tipAmount.setText("$" + calculateTip(Integer.parseInt(enteredAmount.getText().toString()),20));
			totalAmount.setText("$" + totalBill(Integer.parseInt(enteredAmount.getText().toString()),20));
			}
		});
    }
		
		private int calculateTip(int transactionAmount, int percentage) {
			return (transactionAmount * percentage)/100;
			}
		
		private int totalBill(int transactionAmount, int percentage) {
			return (transactionAmount + (transactionAmount * percentage)/100);
			}
}


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//       getMenuInflater().inflate(R.menu.tip_calculator_demo, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//       if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
