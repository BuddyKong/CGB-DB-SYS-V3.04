$(function(){
 		$("#pageId").load("doPageUI",doGetObjects);
 		$(".input-group-btn").on("click",".btn-search",doQueryObjects);
 		$(".input-group-btn").on("click",".btn-delete",doDeleteObjects);
 		$("#checkAll").change(doChangeTBodyCheckState);
 		$("#tbodyId").on("change","input:checkbox",doChangeTHeadCheckState);
 	});
   
function doChangeTHeadCheckState(){
 		var flag=true;
 		$("#tbodyId input:checkbox").each(function(){
 			flag=flag&&$(this).prop("checked");
 		});
 		$("#checkAll").prop("checked",flag)
 	}
   	function doChangeTBodyCheckState(){
   		var flag=$(this).prop("checked");
   		$("#tbodyId input:checkbox").prop("checked",flag);
   	} 
 	function doDeleteObjects(){
 		var url="log/doDeletePageObjects"
 		var idArrays=[];
 		$("#tbodyId input[type=checkbox]").each(function(){
 			if($(this).prop("checked"))idArrays.push($(this).val())
 			});
 		if(!confirm("确认删除吗"))return;
 		var params={"ids":idArrays.toString()};
 		$.post(url,params,function(result){
            if(result.state==1){
                  alert(result.message);
                  doRefreshAfterDelete();
            }else{
            	alert(result.message);
            }
    });
 	}
 	
 	function doRefreshAfterDelete(){
   		var checkAll=$("#checkAll").prop("checked");
   		var pageCurrent=$("#pageId").data("pageCurrent");
   		var pageCount=$("#pageId").data("pageCount");
   		if(checkAll&&pageCurrent==pageCount&&pageCurrent>1){
   			pageCurrent--;
   			$("#pageId").data("pageCurrent",pageCurrent);
   		}
   		doGetObjects();
   	}
 
    function doQueryObjects(){
        //为什么要在此位置初始化pageCurrent的值为1?
        //数据查询时页码的初始位置也应该是第一页
        $("#pageId").data("pageCurrent",1);
        //为什么要调用doGetObjects函数？
        //重用js代码，简化jS代码编写。
        doGetObjects();
}
 	function doGetObjects(){
        //debugger;//断点调试
        //1.定义url和参数
        var url="log/doFindPageObjects"
        //? 请问data函数的含义是什么？(从指定元素上获取绑定的数据)
        //此数据会在何时进行绑定？(setPagination,doQueryObjects)
        var pageCurrent=$("#pageId").data("pageCurrent");
        //为什么要执行如下语句的判定，然后初始化pageCurrent的值为1
        //pageCurrent参数在没有赋值的情况下，默认初始值应该为1.
        if(!pageCurrent) pageCurrent=1;
        var params={"pageCurrent":pageCurrent};//pageCurrent=2
        var username=$("#searchNameId").val();
        if(username) params.username=username;
        //2.发起异步请求
        //请问如下ajax请求的回调函数参数名可以是任意吗？可以,必须符合标识符的规范
    $.getJSON(url,params,function(result){
    	 console.log("result",result);
                //请问result是一个字符串还是json格式的js对象？对象
                 doHandleResponseResult(result);
              });//特殊的ajax函数
              }
   function doSetTableBodyRows(records){
	   //获取tbody并清空内容
	   var tBody=$("tbody");
		tBody.empty();
	   //遍历records并将结果更新到页面上
		for (var i = 0; i < records.length; i++) {
			tBody.append(doCreateRow(records[i]));
		}	
   }
   function doCreateRow(obj){
			return `<tr>
			<td><input type="checkbox" id="check" class='cBox' name='cItem' value=${obj.id} ></td>
			<td>${obj.id}</td>  
			<td>${obj.username}</td>
			<td>${obj.operation}</td>
			<td>${obj.method}</td>
			<td>${obj.params}</td>
			<td>${obj.ip}</td>
			<td>${obj.time}</td>
			<td>${obj.createdTime}</td>			
			</tr>`;
   }
   