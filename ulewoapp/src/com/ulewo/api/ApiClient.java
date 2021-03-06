package com.ulewo.api;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.bean.Article;
import com.ulewo.bean.ArticleList;
import com.ulewo.bean.Blog;
import com.ulewo.bean.BlogList;
import com.ulewo.bean.GroupList;
import com.ulewo.bean.LoginUser;
import com.ulewo.bean.ReArticleList;
import com.ulewo.bean.ReArticleResult;
import com.ulewo.util.Constants;

public class ApiClient {
	private static final int HTTP_200 = 200;

	private static final String SUCCESS = "success";

	private static final String REQUESTTIMEOUT = "requesttimeout";

	private static final String SEPARATOR = File.separator;

	private static final String ULEWO = "ulewo";

	private final static int TIMEOUT_CONNECTION = 5000;

	private final static int TIMEOUT_SOCKET = 5000;

	private final static String UTF_8 = "utf-8";

	private final static int RETRY_TIME = 3;

	private static final String BASEURL = "http://192.168.2.224:8080/ulewo";

	private static final String HOST = BASEURL;

	private static final String BASEURL_ARTICLELIST = BASEURL
			+ "/android/fetchArticle.jspx";

	private static final String BASEUR_SHOWARTICLE = BASEURL
			+ "/android/showArticle.jspx";

	private static final String BASEUR_BLOGLIST = BASEURL
			+ "/android/fetchBlog.jspx";

	private static final String BASEUR_SHOWBLOG = BASEURL
			+ "/android/showBlog.jspx";

	private static final String BASEUR_GROUPLIST = BASEURL
			+ "/android/fetchWoWo.jspx";

	private static final String BASEUR_GROUPARTICLELIST = BASEURL
			+ "/android/fetchArticleByGid.jspx";

	private static final String BASEUR_RECOMMENT = BASEURL
			+ "/android/fetchReComment.jspx";

	private static final String BASEUR_SUBRECOMMENT = BASEURL
			+ "/android/addArticleComment.jspx";

	private static final String BASEUR_LOGIN = BASEURL + "/android/login.jspx";

	public static final int RESULTCODE_SUCCESS = 200;

	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";

	public static final int RESULTCODE_FAIL = 400;

	private static final int NoPage = 0;

	/**
	 * 
	 * description: 文章列表
	 * 
	 * @param pageIndex
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public static ArticleList getArticleList(final int pageIndex)
			throws AppException {

		String newUrl = BASEURL_ARTICLELIST + "?page=" + pageIndex;
		try {
			return ArticleList
					.parse(convertInputStream2JSONObject(http_get(newUrl)));
		} catch (AppException e) {
			throw e;
		}
	}

	/**
	 * 
	 * description: 获取文章详情
	 * 
	 * @param articleId
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public static Article getArticle(final int articleId) throws AppException {

		String newUrl = BASEUR_SHOWARTICLE + "?articleId=" + articleId;
		try {
			return Article
					.parse(convertInputStream2JSONObject(http_get(newUrl)));
		} catch (AppException e) {
			throw e;
		}
	}

	/**
	 * 
	 * description:获取评论
	 * 
	 * @param articleId
	 * @param pageIndex
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public static ReArticleList getReArticleList(int articleId, int pageIndex)
			throws AppException {

		String newUrl = BASEUR_RECOMMENT + "?page=" + pageIndex + "&articleId="
				+ articleId;
		try {
			return ReArticleList
					.parse(convertInputStream2JSONObject(http_get(newUrl)));
		} catch (AppException e) {
			throw e;
		}
	}

	public static ReArticleResult addReArticle(String content, int articleId,
			String sessionId, String userName, String password)
			throws AppException {

		String newUrl = BASEUR_SUBRECOMMENT;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("content", content);
		params.put("articleId", articleId);
		params.put(Constants.SESSIONID, sessionId);
		params.put(Constants.USERNAME, userName);
		params.put(Constants.PASSWORD, password);

		try {
			ReArticleResult result = ReArticleResult
					.parse(convertInputStream2JSONObject(http_post(newUrl,
							params, null)));
			if (result.isLogin()) {
				AppContext.putUserInfo(Constants.SESSIONID,
						result.getSessionId());
			} else {
				AppContext.removeUserInfo(Constants.SESSIONID);
			}
			return result;
		} catch (AppException e) {
			throw e;
		}
	}

	public static ReArticleResult addSubReArticle(String content,
			int articleId, String atUserId, String pid, String sessionId,
			String userName, String password) throws AppException {

		String newUrl = BASEUR_SUBRECOMMENT;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("content", content);
		params.put("articleId", articleId);
		params.put("atUserId", atUserId);
		params.put("pid", pid);
		params.put(Constants.SESSIONID, sessionId);
		params.put(Constants.USERNAME, userName);
		params.put(Constants.PASSWORD, password);
		try {
			ReArticleResult result = ReArticleResult
					.parse(convertInputStream2JSONObject(http_post(newUrl,
							params, null)));
			if (result.isLogin()) {
				AppContext.putUserInfo(Constants.SESSIONID,
						result.getSessionId());
			} else {
				AppContext.removeUserInfo(Constants.SESSIONID);
			}
			return result;
		} catch (AppException e) {
			throw e;
		}
	}

	/**
	 * 
	 * description: 博客列表
	 * 
	 * @param pageIndex
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public static BlogList getBlogList(final int pageIndex) throws AppException {

		String newUrl = BASEUR_BLOGLIST + "?page=" + pageIndex;
		try {
			return BlogList
					.parse(convertInputStream2JSONObject(http_get(newUrl)));
		} catch (AppException e) {
			throw e;
		}
	}

	/**
	 * 
	 * description: 博客详情
	 * 
	 * @param articleId
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public static Blog getBlog(final int articleId) throws AppException {

		String newUrl = BASEUR_SHOWBLOG + "?articleId=" + articleId;
		try {
			return Blog.parse(convertInputStream2JSONObject(http_get(newUrl)));
		} catch (AppException e) {
			throw e;
		}
	}

	/**
	 * 
	 * description: 群组列表
	 * 
	 * @param pageIdex
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public static GroupList getGroupList(final int pageIdex)
			throws AppException {

		String newUrl = BASEUR_GROUPLIST + "?page=" + pageIdex;
		try {
			return GroupList
					.parse(convertInputStream2JSONObject(http_get(newUrl)));
		} catch (AppException e) {
			throw e;
		}
	}

	/**
	 * 
	 * description: 群组文章
	 * 
	 * @param pageIndex
	 * @param gid
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public static ArticleList getGroupArticleList(final int pageIndex,
			final String gid) throws AppException {

		String newUrl = BASEUR_GROUPARTICLELIST + "?page=" + pageIndex
				+ "&gid=" + gid;
		try {
			return ArticleList
					.parse(convertInputStream2JSONObject(http_get(newUrl)));
		} catch (AppException e) {
			throw e;
		}
	}

	public static LoginUser login(String userName, String password)
			throws AppException {

		String newUrl = BASEUR_LOGIN;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("password", password);
		try {
			return LoginUser.parse(convertInputStream2JSONObject(http_post(
					newUrl, params, null)));
		} catch (AppException e) {
			throw e;
		}
	}

	private static JSONObject convertInputStream2JSONObject(InputStream in)
			throws AppException {

		JSONObject jsonObj = null;
		BufferedInputStream bis = null;
		try {
			// 用ByteArrayBuffer缓存
			bis = new BufferedInputStream(in);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			String myString = EncodingUtils.getString(baf.toByteArray(),
					"UTF-8");
			jsonObj = new JSONObject(myString);
		} catch (IOException e) {
			e.printStackTrace();
			AppException.io(e);
		} catch (JSONException e) {
			e.printStackTrace();
			AppException.josn(e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != bis) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return jsonObj;
	}

	/**
	 * get请求URL
	 * 
	 * @param url
	 * @throws AppException
	 */
	private static InputStream http_get(String url) throws AppException {

		HttpClient httpClient = null;
		GetMethod httpGet = null;

		String responseBody = "";
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpGet = getHttpGet(url);
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(new HttpException());
				}
				responseBody = httpGet.getResponseBodyAsString();
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);
		return new ByteArrayInputStream(responseBody.getBytes());
	}

	private static GetMethod getHttpGet(String url) {

		GetMethod httpGet = new GetMethod(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpGet.setRequestHeader("Host", HOST);
		httpGet.setRequestHeader("Connection", "Keep-Alive");
		return httpGet;
	}

	private static PostMethod getHttpPost(String url) {

		PostMethod httpPost = new PostMethod(url);
		// 设置 请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpPost.setRequestHeader("Host", HOST);
		httpPost.setRequestHeader("Connection", "Keep-Alive");
		return httpPost;
	}

	/**
	 * 公用post方法
	 * 
	 * @param url
	 * @param params
	 * @param files
	 * @throws AppException
	 */
	private static InputStream http_post(String url,
			HashMap<String, Object> params, HashMap<String, File> files)
			throws AppException {

		// System.out.println("post_url==> "+url);
		// String cookie = getCookie(appContext);
		// String userAgent = getUserAgent(appContext);

		HttpClient httpClient = null;
		PostMethod httpPost = null;

		// post表单参数处理
		int length = (params == null ? 0 : params.size())
				+ (files == null ? 0 : files.size());
		Part[] parts = new Part[length];
		int i = 0;
		if (params != null)
			for (String name : params.keySet()) {
				parts[i++] = new StringPart(name, String.valueOf(params
						.get(name)), UTF_8);
				// System.out.println("post_key==> "+name+"    value==>"+String.valueOf(params.get(name)));
			}
		if (files != null)
			for (String file : files.keySet()) {
				try {
					parts[i++] = new FilePart(file, files.get(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				// System.out.println("post_key_file==> "+file);
			}

		String responseBody = "";
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpPost = getHttpPost(url);
				httpPost.setRequestEntity(new MultipartRequestEntity(parts,
						httpPost.getParams()));
				int statusCode = httpClient.executeMethod(httpPost);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(new HttpException());
				}
				responseBody = httpPost.getResponseBodyAsString();
				// System.out.println("XMLDATA=====>"+responseBody);
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		return new ByteArrayInputStream(responseBody.getBytes());
	}

	private static HttpClient getHttpClient() {

		HttpClient httpClient = new HttpClient();
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 设置 默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置 连接超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(TIMEOUT_CONNECTION);
		// 设置 读数据超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(TIMEOUT_SOCKET);
		// 设置 字符集
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}

}
