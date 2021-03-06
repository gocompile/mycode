package com.lhl.entity;

public class User {
	private String userId; // 用户ID

	private String email; // 邮箱

	private String userName; // 用户名

	private String shortName; //名字简称

	private String password; // 密码

	private String userLittleIcon; // 用户小图像

	private String userBigIcon; //用户大图像

	private String age; // 年龄

	private String sex; // 性别

	private String characters; // 个性签名

	private int mark; // 积分

	private String address; // 籍贯

	private String work; // 职业

	private String registerTime; // 注册时间

	private String previsitTime; // 上次登陆时间

	private String activationCode; // 状态码

	private int articleNumber; // 发帖数量

	private int reArticleNumber; // 回帖数量

	private String isValid;

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getUserLittleIcon() {

		return userLittleIcon;
	}

	public void setUserLittleIcon(String userLittleIcon) {

		this.userLittleIcon = userLittleIcon;
	}

	public String getUserBigIcon() {

		return userBigIcon;
	}

	public void setUserBigIcon(String userBigIcon) {

		this.userBigIcon = userBigIcon;
	}

	public String getSex() {

		return sex;
	}

	public void setSex(String sex) {

		this.sex = sex;
	}

	public String getCharacters() {

		return characters;
	}

	public void setCharacters(String characters) {

		this.characters = characters;
	}

	public String getAge() {

		return age;
	}

	public void setAge(String age) {

		this.age = age;
	}

	public int getMark() {

		return mark;
	}

	public void setMark(int mark) {

		this.mark = mark;
	}

	public String getAddress() {

		return address;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public String getWork() {

		return work;
	}

	public void setWork(String work) {

		this.work = work;
	}

	public String getRegisterTime() {

		return registerTime;
	}

	public void setRegisterTime(String registerTime) {

		this.registerTime = registerTime;
	}

	public String getPrevisitTime() {

		return previsitTime;
	}

	public void setPrevisitTime(String previsitTime) {

		this.previsitTime = previsitTime;
	}

	public String getIsValid() {

		return isValid;
	}

	public void setIsValid(String isValid) {

		this.isValid = isValid;
	}

	public String getActivationCode() {

		return activationCode;
	}

	public void setActivationCode(String activationCode) {

		this.activationCode = activationCode;
	}

	public int getArticleNumber() {

		return articleNumber;
	}

	public void setArticleNumber(int articleNumber) {

		this.articleNumber = articleNumber;
	}

	public int getReArticleNumber() {

		return reArticleNumber;
	}

	public void setReArticleNumber(int reArticleNumber) {

		this.reArticleNumber = reArticleNumber;
	}

	public String getShortName() {

		return shortName;
	}

	public void setShortName(String shortName) {

		this.shortName = shortName;
	}

}
