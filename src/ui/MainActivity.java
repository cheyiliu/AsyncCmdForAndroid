package ui;

import java.math.BigDecimal;
import java.math.BigInteger;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import biz.CmdAdd;
import biz.CmdSubtract;
import biz.iCmdExecutor;
import biz.iCmdListener;

import async.cmd4android.R;

public class MainActivity extends Activity {

	protected static final String TAG = "test";

	private TextView tvAddResult;
	private TextView tvSubResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "onCreate, " + this);
		setContentView(R.layout.activity_main);
		tvAddResult = (TextView) findViewById(R.id.tv_result_add);
		tvSubResult = (TextView) findViewById(R.id.tv_result_sub);

		findViewById(R.id.btn_finish).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Log.e(TAG, "-------------------------------------------");
		iCmdExecutor.exeAsync(new CmdAdd(new BigInteger("123"), new BigInteger(
				"12345678901234567890123456789012345678901234567890")),
				new iCmdListener() {

					@Override
					public void onPreExecute() {
						Log.e(TAG, "onPreExecute, tid="
								+ Thread.currentThread().getId());
					}

					@Override
					public void onPostExecute(Bundle resultBundle) {
						Log.e(TAG, "onPostExecute, tid="
								+ Thread.currentThread().getId()
								+ ", resultBundle=" + resultBundle);
						if (null != resultBundle) {
							String res = resultBundle.getString("result");
							tvAddResult.setText(res);
						}
					}
				}, this);

		Log.e(TAG, "-------------------------------------------");
		iCmdExecutor
				.exeAsync(
						new CmdSubtract(
								new BigDecimal("123"),
								new BigDecimal(
										"12345678901234567890123456789012345678901234567890.1")),
						new iCmdListener() {

							@Override
							public void onPreExecute() {
								Log.e(TAG, "onPreExecute, tid="
										+ Thread.currentThread().getId());
							}

							@Override
							public void onPostExecute(Bundle resultBundle) {
								Log.e(TAG, "onPostExecute, tid="
										+ Thread.currentThread().getId()
										+ ", resultBundle=" + resultBundle);
								if (null != resultBundle) {
									String res = resultBundle
											.getString("result");
									tvSubResult.setText(res);
								}
							}
						}, this);
		Log.e(TAG, "-------------------------------------------");

	}

	@Override
	protected void onDestroy() {
		Log.e(TAG, "onDestroy, " + this);
		iCmdExecutor.cancelAll(this);
		super.onDestroy();
	}
}
