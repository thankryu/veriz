<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script>
$(document).ready(function () {	
	console.log('aaa');
    // 화면조회
	 var sendParam = new URLSearchParams();
	 const url = '/json/gallery/selectGalleryDetail';
	 sendParam.append('GALLERY_SEQ', ${GALLERY_SEQ});
	 axios.post(url, sendParam)
     .then((response) => {
    	 let resultList = response.data.resultList;
		    let tableContent = '';
		    for(var i =0; i< resultList.length ; i++){
		    	let src1 = '/img'+resultList[i].FILE_PATH + resultList[i].FILE_NAME;
		    	tableContent += '<div class="col-sm-12">';
		    	tableContent += '	<p><img class="img-responsive" src="'+src1+'" alt=""></p>';
		    	tableContent += '</div>';
		    }
		    document.getElementById('galleryDetailAreaTop').innerHTML = tableContent;
     })
     .catch((error) => {
         console.log(error.response)
     }).then(function () {
		    // always executed
     }); 
});
</script>
	<div class="container-fluid">
		<div class="row" id="galleryDetailAreaTop">
			<div class="col-sm-12">
				<!-- Responsive images -->
				<h3>Responsive images${GALLERY_SEQ}</h3>
				<p>To make images "responsive" (they will change size as needed with the viewport) they be given the class of <code>img-responsive</code>.</p>
				<p><img class="img-responsive" src="http://placehold.it/1200x600" alt=""></p>
				<hr>
			</div>
		</div>
		<!-- /.row -->
	</div>
		<!-- /.container-fluid -->
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
