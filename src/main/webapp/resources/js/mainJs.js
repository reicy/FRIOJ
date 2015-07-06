function deleteSth(sth,name,url){
	$decision = confirm("Do your really want to remove "+sth+": "+name);
	
	if($decision===true)window.location.href = url;

}

