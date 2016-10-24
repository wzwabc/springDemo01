$(function($){
	//上传文件
    $("#uploadBtn").click(function() {
          ajaxFileUpload();
    });
    //初始化列表数据
	getList();
});

function getList(){
	var searchUrl=context+"/service/tax/bonus/query";
	$.ajax({
		type : "GET",
		url : searchUrl,
		contentType: "application/json;charset=utf-8",
		data : {},
		async : false,
		success : function(data) {
			displayData(data);
		},
		error:function(){
			alert("查询失败！");
		}
	});
}
//展现数据列表
function displayData(data){
	$("#taxList tr").remove();
	for(var i=0;i<data.length;i++){
	   var trDiv=$("<tr></tr>");
	   if(i%2==0){
		   var trDiv=$("<tr style='background-color:#FFCD40;'></tr>");
	   }
	   var td1=$("<td></td>");
	   var inputDiv = $("<input type='radio' name='group' />").val(data[i].id);
	   td1.append(inputDiv);
	   trDiv.append(td1);
	   var td2=$("<td></td>").text(data[i].wage);
	   trDiv.append(td2);
	   var td3=$("<td></td>").text(data[i].wage);
	   trDiv.append(td3);
	   var td4=$("<td></td>").text(data[i].optYearBonus);
	   trDiv.append(td4);
	   var td5=$("<td></td>").text(data[i].optMonth1Bonus);
	   trDiv.append(td5);
	   var td6=$("<td></td>").text(data[i].optMonth2Bonus);
	   trDiv.append(td6);
	   var td7=$("<td></td>").text(data[i].realTotalBonus);
	   trDiv.append(td7);
	   $("#taxList").append(trDiv);
	}
}
//上传文件
function ajaxFileUpload() {
    var elementIds=["file_url"];
    $.ajaxFileUpload({
        url: context + '/service/tax/bonus/upload', 
        type: 'post',
        secureuri: false,
        fileElementId: 'file',
        dataType: 'text',
        elementIds: elementIds,
        success: function(data, status){
        	if(data.indexOf("success")!=-1){
        		$("#file_url").val(data.substring(8,data.length));
        		alert("上传成功");
        		//刷新列表
        		getList();
        	} else if(data.indexOf("fail")!=-1){
        		alert(data.substring(5,data.length));
        	}
        },
        error: function(data, status, e){ 
            alert(e);
        }
    });
}