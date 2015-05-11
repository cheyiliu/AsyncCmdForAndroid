package biz;

import java.math.BigDecimal;

import android.os.Bundle;
import android.util.Log;

public class CmdSubtract extends iCmd {
	private static final String TAG = "test";
	private BigDecimal a;
	private BigDecimal b;

	public CmdSubtract(BigDecimal a, BigDecimal b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public Bundle exe() throws Exception {
		Log.i(TAG, this + ", exe, tid=" + Thread.currentThread().getId());
		Thread.sleep(10000);// demo for long operation
		Bundle bundle = new Bundle();
		bundle.putString("result", a.subtract(b).toString());
		// throw new Exception("");// for test
		return bundle;
	}

}
