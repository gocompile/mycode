package com.lhl.entity;

/**
 * 
 * @Title:
 * @Description: 主题文章bean
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class Article {
	private int id; // id

	private String gid; // 组id

	private String type;// 类型 0:普通帖子 1:投票贴

	private int itemId; // 栏目ID

	private String title; // 标题

	private String titleStyle;// 标题样式

	private String content; // 内容

	private String keyWord; // 关键字

	private String authorId; // 作者

	private String authorName;

	private String postTime; // 发布时间

	private int readNumber; // 阅读次数

	private int grade; // 帖子等级

	private String essence; // 精华

	private String isValid; // 是否有效

	private String sysCode; // 系统代码

	private String subCode; // 模块代码

	private String image; // 图片 推荐的文章需要图片

	// 需要关联查询的信息
	private String groupName; // 群组名称 （文章所述群）

	private int reNumber; // 回复数量

	private User author; // 作者 信息

	private String itemName; // 栏目名称

	private String lastReAuthorId; // 最后回复人id

	private String lastReAuthorName; // 最后回复人

	private String lastReTime; // 最后回复时间

	private String summary;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getGid() {

		return gid;
	}

	public void setGid(String gid) {

		this.gid = gid;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public int getItemId() {

		return itemId;
	}

	public void setItemId(int itemId) {

		this.itemId = itemId;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getTitleStyle() {

		return titleStyle;
	}

	public void setTitleStyle(String titleStyle) {

		this.titleStyle = titleStyle;
	}

	public String getContent() {

		return content;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public String getKeyWord() {

		return keyWord;
	}

	public void setKeyWord(String keyWord) {

		this.keyWord = keyWord;
	}

	public String getAuthorId() {

		return authorId;
	}

	public void setAuthorId(String authorId) {

		this.authorId = authorId;
	}

	public String getPostTime() {

		return postTime;
	}

	public void setPostTime(String postTime) {

		this.postTime = postTime;
	}

	public int getReadNumber() {

		return readNumber;
	}

	public void setReadNumber(int readNumber) {

		this.readNumber = readNumber;
	}

	public int getGrade() {

		return grade;
	}

	public void setGrade(int grade) {

		this.grade = grade;
	}

	public String getEssence() {

		return essence;
	}

	public void setEssence(String essence) {

		this.essence = essence;
	}

	public String getIsValid() {

		return isValid;
	}

	public void setIsValid(String isValid) {

		this.isValid = isValid;
	}

	public String getSysCode() {

		return sysCode;
	}

	public void setSysCode(String sysCode) {

		this.sysCode = sysCode;
	}

	public String getSubCode() {

		return subCode;
	}

	public void setSubCode(String subCode) {

		this.subCode = subCode;
	}

	public String getImage() {

		return image;
	}

	public void setImage(String image) {

		this.image = image;
	}

	public String getGroupName() {

		return groupName;
	}

	public void setGroupName(String groupName) {

		this.groupName = groupName;
	}

	public int getReNumber() {

		return reNumber;
	}

	public void setReNumber(int reNumber) {

		this.reNumber = reNumber;
	}

	public User getAuthor() {

		return author;
	}

	public void setAuthor(User author) {

		this.author = author;
	}

	public String getLastReAuthorId() {

		return lastReAuthorId;
	}

	public void setLastReAuthorId(String lastReAuthorId) {

		this.lastReAuthorId = lastReAuthorId;
	}

	public String getLastReAuthorName() {

		return lastReAuthorName;
	}

	public void setLastReAuthorName(String lastReAuthorName) {

		this.lastReAuthorName = lastReAuthorName;
	}

	public String getLastReTime() {

		return lastReTime;
	}

	public void setLastReTime(String lastReTime) {

		this.lastReTime = lastReTime;
	}

	public String getItemName() {

		return itemName;
	}

	public void setItemName(String itemName) {

		this.itemName = itemName;
	}

	public String getAuthorName() {

		return authorName;
	}

	public void setAuthorName(String authorName) {

		this.authorName = authorName;
	}

	public String getSummary() {

		return summary;
	}

	public void setSummary(String summary) {

		this.summary = summary;
	}

}
