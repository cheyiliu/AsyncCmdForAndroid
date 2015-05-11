# AsyncCmdForAndroid
Async command pattern for android

# 引子
* 随着项目的发展，activity的代码变得越来越庞杂，不易维护。这便出现了诸如MVP等模式来分层实现以达到给activity减负的效果，从而降低业务逻辑的耦合，提高可维护性。本文从另外一个角度出发，利用命令模式来实现给activity瘦身的目标。采用本文的方案，同样可以将业务逻辑抽离，并于后台执行，同时提供了不错的取消机制。


# 接口
* iCmd接口，主要api是exe方法，返回值是bundle对象；其他mTag，mCanceled用于取消相关
```

public abstract class iCmd {
	private boolean mCanceled = false;
	private Object mTag;

	public void cancel() {
		mCanceled = true;
	}

	public boolean isCanceled() {
		return mCanceled;
	}

	public Object getTag() {
		return mTag;
	}

	public iCmd setTag(Object tag) {
		mTag = tag;
		return this;
	}

	public abstract Bundle exe() throws Exception;
}
```

* iCmdListener 命令监听接口，onPreExecute负责命令开始的通知；onPostExecute负责命令结束的通知并带回返回结果
```
public interface iCmdListener {
	public void onPreExecute();

//	public void onProgressUpdate(int progress);

	public void onPostExecute(Bundle resultBundle);

}
```

* iCmdExecutor命令执行器，下列代码仅列出主要api，具体的实现文后的github地址
```

public class iCmdExecutor {
//用于取消和具体tag相关的cmd
	public static void cancelAll(final Object tag)
	
//异步执行该命令，并在主线程执行回调通知
	public static void exeAsync(final iCmd cmd, final iCmdListener listener,
			Object tag)
			
//同步执行该命令，并在当前线程执行回调通知
	public static void exeSync(iCmd cmd, iCmdListener listener, Object tag)
	
}
```

# 用法
* 实现具体cmd

```

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
```

* 调用该命令

```
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
```

# 链接
* https://github.com/cheyiliu/AsyncCmdForAndroid
