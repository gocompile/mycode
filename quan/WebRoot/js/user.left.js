$(function() {
	loadItem();
	loadBaseInfo();
});

function loadBaseInfo() {
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : 'getUserInfoAjax.jspx?userId=' + userId,// 
		success : function(data) {
			if (data.result == "ok") {
				$("#imgcon").show();
				$("#imgcon").attr("src",
						"../upload/" + data.userBaesInfo.userLittleIcon);
				$("#user_name").html(data.userBaesInfo.userName);
				var resume = "这个人很懒，什么都没留下";
				if (data.userBaesInfo.characters != "") {
					resume = data.userBaesInfo.characters;
				}
				$("#resume").html(resume);
				var url = window.location.href;
				if (url.indexOf("blogdetail") == -1) {
					document.title = data.userBaesInfo.userName + "的空间";
				}
			} else {
				alert("获取用户信息失败");
				document.location.href = "../index.jspx";
			}
		}
	});
}

function loadItem(itemId) {
	$("#item").html("");
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : 'queryItem.jspx?userId=' + userId,// 
		success : function(data) {
			var item = {};
			item.id = "0";
			item.itemName = "全部文章";
			item.articleCount = data.allcount;
			var allItem = new ArticleItem(item);
			if (item.id == itemId || itemId == "") {
				allItem.getItemNamePanle().addClass("itemnameselect");
			}
			allItem.asHtml().appendTo($("#item"));
			var items = data.list;
			for ( var i = 0; i < items.length; i++) {
				var articleItem = new ArticleItem(items[i]);
				if (items[i].id == itemId) {
					articleItem.getItemNamePanle().addClass("itemnameselect");
				}
				articleItem.asHtml().appendTo($("#item"));
			}
		}
	});
}

function ArticleItem(item) {
	this.itemPanel = $("<div class='blog_item'></div>");
	this.index_itemname = $(
			"<div class='blog_itemname'><a href='blog.jspx?userId=" + userId
					+ "&itemId=" + item.id + "'>" + item.itemName
					+ "</a></div>").appendTo(this.itemPanel);
	this.countSpan = $(
			"<div class='blog_articlecount'>(" + item.articleCount + ")</div>")
			.appendTo(this.itemPanel);
	$("<div class='clear'></div>").appendTo(this.itemPanel);
}

ArticleItem.prototype = {
	asHtml : function() {
		return this.itemPanel;
	},
	getItemNamePanle : function() {
		return this.index_itemname;
	},
	setCount : function(count) {
		this.countSpan.html("(" + count + ")");
	}
}