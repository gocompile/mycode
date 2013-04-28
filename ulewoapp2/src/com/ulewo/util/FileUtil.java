package com.ulewo.util;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class FileUtil {
	public static File updateDir = null;
	public static File updateFile = null;

	/***
	 * 创建文件
	 */
	public static void createFile(String name) {
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			updateDir = new File(Environment.getExternalStorageDirectory()
					+ "/" + Constants.ULEWO);
			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			updateFile = new File(updateDir + "/" + name);
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
