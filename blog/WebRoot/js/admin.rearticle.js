function loadArticle(page){
	loadReArticle(page);
};
function loadReArticle(page){
	GlobalVar.page = page;
	$("#con").html("");
	$(".pageArea").html("");
	$("<div class='lodding'>正在加载中......</div>").appendTo($("#con"));
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : 'adminReArticle?method=admin_list&page='+page,
		success : function(data) {
			$(".lodding").remove();
			var articles = data.list;
			for(var i=0;i<articles.length;i++){
				var panle = new ReArticlePanel(articles[i]);
				var tr = $(panle.asHtml());
				if(i%2!=0){
					tr.css({"background":"#F5F5F5"});
				}
				tr.appendTo("#con");
			}
			new Pager(data.pageTotal, 10,page).asHtml().appendTo(
					$(".pageArea"));
		}
	});
}

function ReArticlePanel(note){
	this.id = note.id;
	this.notepanel = $("<div class='note_tr'></div>");
	$("<div class='note_con'>"+note.content+"</div>").appendTo(this.notepanel);
	$("<div class='note_username'>"+note.userName+"</div>").appendTo(this.notepanel);
	$("<div class='note_time'>"+note.postTime+"</div>").appendTo(this.notepanel);
	$("<div class='note_op'><a href='javascript:void(0)' class='btn'>删除</a></div>").appendTo(this.notepanel).bind("click",{id:this.id},this.delReArticle);
	$("<div class='clear_float'></div>").appendTo(this.notepanel);
}
ReArticlePanel.prototype={
	asHtml:function(){
		return this.notepanel;
	},
	delReArticle:function(event){
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : 'adminReArticle?method=admin_delete&id='+event.data.id,
			success : function(data) {
				loadReArticle(GlobalVar.page);
			}
		});
	}
}
