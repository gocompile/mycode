package com.ulewo.cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;

public class CallbackManager {
	private ConcurrentHashMap<String, List<ImageLoaderCallback>> callbackMap;

	public CallbackManager() {
		callbackMap = new ConcurrentHashMap<String, List<ImageLoaderCallback>>();
	}

	public void callback(String url, Bitmap bitmap) {
		List<ImageLoaderCallback> callbacks = callbackMap.get(url);
		if (null == callbacks) {
			return;
		}
		for (ImageLoaderCallback callback : callbacks) {
			if (null != callback) {
				callback.refresh(url, bitmap);
			}
		}
		callbacks.clear();
		callbackMap.remove(url);
	}
}