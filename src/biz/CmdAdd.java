package biz;

import java.math.BigInteger;

import android.os.Bundle;
import android.util.Log;

public class CmdAdd extends iCmd {
	private static final String TAG = "test";
	private BigInteger a;
	private BigInteger b;

	public CmdAdd(BigInteger a, BigInteger b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public Bundle exe() throws InterruptedException {
		Log.i(TAG, this + ", exe, tid=" + Thread.currentThread().getId());
		Thread.sleep(5000);// demo for long operation
		Bundle bundle = new Bundle();
		bundle.putString("result", a.add(b).toString());
		return bundle;
	}

}
