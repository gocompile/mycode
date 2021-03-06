package com.lhl.upload;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.lhl.common.action.BaseAction;
import com.lhl.entity.User;
import com.lhl.util.DrowImage;

/**
 * 
 * 文件上传类
 * 
 * @author Administrator
 * 
 */
public class UserAvatarAction extends BaseAction {

	private Logger logger = Logger.getLogger(UserAvatarAction.class);

	private static final long serialVersionUID = 1L;

	private static final int BUFFER_SIZE = 20 * 1024;

	private final String SEPARATOR = File.separator;

	// 用File数组来封装多个上传文件域对象
	private File upload;

	// 用String数组来封装多个上传文件名
	private String uploadFileName;

	private String resultFileName = "";

	private String message;

	private static boolean copy(File src, File dst) {

		boolean result = false;
		InputStream in = null;
		OutputStream out = null;
		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			fin = new FileInputStream(src);
			fout = new FileOutputStream(dst);
			in = new BufferedInputStream(fin, BUFFER_SIZE);
			out = new BufferedOutputStream(fout, BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
			result = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		finally {
			if (null != fin) {
				try {
					fin.close();
					fin = null;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != fout) {
				try {
					fout.close();
					fout = null;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != in) {
				try {
					in.close();
					in = null;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
					out = null;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public String deleteFile() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		String filePath = request.getParameter("filePath");
		String fileName = request.getParameter("fileName");
		if (filePath != null && fileName != null) {
			File file = new File(ServletActionContext.getServletContext().getRealPath("/") + "/" + filePath);
			if (file.exists() && file.isFile()) {
				file.delete();
				ServletActionContext.getRequest().setAttribute("fileName", fileName);
				return SUCCESS;
			}
		}
		return ERROR;
	}

	public String imageUpload() throws Exception {

		File destFile = null;
		User sessionUser = getSessionUser();
		try {
			if (upload != null) {
				File srcFiles = upload;
				if (srcFiles.length() > 1024 * 1024) {
					message = "isBig";
					uploadFileName = null;
					return SUCCESS;
				}
				String srcName = uploadFileName.toLowerCase();
				// 判断文件类型是否符合(.jpg,.gif,.png,.bmp)
				if (!srcName.endsWith(".jpg") && !srcName.endsWith(".gif") && !srcName.endsWith(".png")
						&& !srcName.endsWith(".bmp")) {
					message = "error";
					uploadFileName = null;
					return SUCCESS;
				}
				String imagePath = ServletActionContext.getServletContext().getRealPath(SEPARATOR) + SEPARATOR
						+ "upload" + SEPARATOR + "avatartemp" + SEPARATOR;
				File imagePathFile = new File(imagePath);
				if (!imagePathFile.exists()) {
					imagePathFile.mkdirs();
				}
				destFile = new File(imagePath + sessionUser.getUserId() + ".jpg");
				if (!copy(srcFiles, destFile)) {
					message = "error";
					uploadFileName = null;
					return SUCCESS;
				}
				// 获取图片的长宽
				String destPath = imagePath + sessionUser.getUserId() + ".jpg";
				File fromFile = new File(destPath);
				BufferedImage srcImage = ImageIO.read(fromFile);
				int width = srcImage.getWidth();
				int height = srcImage.getHeight();
				int w = 650;
				int h = 650;
				if (destFile.length() > 200 * 1024 || width > 650 || height > 650) {
					if (destFile.length() > 200 * 1024 && width < 650 && height < 650) {//图片太大，长宽不大的情况
						w = width;
						h = height;
					}
					DrowImage.saveImageAsJpg(destPath, destPath, w, h, false);
				}
				resultFileName = "avatartemp/" + sessionUser.getUserId() + ".jpg";
			}
		}
		catch (Exception e) {
			destFile.delete();
			resultFileName = null;
			message = "error";
			logger.info(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getRealyPath(String path) {

		return ServletActionContext.getServletContext().getRealPath(path);
	}

	public String getUploadFileName() {

		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {

		this.uploadFileName = uploadFileName;
	}

	public void setUpload(File upload) {

		this.upload = upload;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public String getResultFileName() {

		return resultFileName;
	}
}