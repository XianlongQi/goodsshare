
function toIndex(url){
   window.location.href=url+"/mybook.action";
}

//判断是不是整形
function isInteger(str){
	var regu = /^[-]{0,1}[0-9]{1,}$/;
	return regu.test(str);	
		
}