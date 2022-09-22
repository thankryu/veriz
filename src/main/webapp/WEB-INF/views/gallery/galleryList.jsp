<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<script type="text/javascript">
function selectGalleryList(page, order){
	console.log('');
    // 화면조회
	axios.get('/json/gallery/selectGalleryList', {
		params :{
			"SEARCH_KEYWORD" : $('#searchGalleryWord').val(),
			"PAGE_NUM" : page,
			"pageScript": 'selectGalleryList',
			"ORDER" : $('#ORDER').val(),
			"ORDER_FLAG" :$('#ORDER_FLAG').val()
			
		}
	  }).then(function (response) {
		console.log('response', response);
	    let resultList = response.data.resultList;
	    let tableContent = '';
	    for(var i =0; i< resultList.length ; i++){
	    	let src1 = '/img'+resultList[i].FILE_PATH + resultList[i].FILE_NAME;
	    	tableContent += '<div class="col mb-5">';
	    	tableContent += '	<div class="card h-100">';
	    	tableContent += '		<img class="card-img-top" src="'+src1+'" onclick="galleryDetail('+resultList[i].GALLERY_SEQ+')" />';
	    	tableContent += '		<div class="card-body p-4">';
	    	tableContent += '			<div class="text-center">';
	    	tableContent += '				<h5 class="fw-bolder">'+resultList[i].AUTHOR+'</h5>';
	    	tableContent += '			</div>';
	    	tableContent += '		</div>';
	    	tableContent += '		<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">';
	    	tableContent += '			<div class="text-center">';
	    	tableContent += '				<a class="btn btn-outline-dark mt-auto" href="#">View options</a>';
	    	tableContent += '			</div>';
	    	tableContent += '		</div>';
	    	tableContent += '	</div>';
	    	tableContent += '</div>';
	    }
	    
	    $('#galleryListPagination').html(response.data.pagination);
	    
	    document.getElementById('galleryListArea').innerHTML = tableContent;
	  }).catch(function (error) {
	    console.log(error);
	  }).then(function () {
	    // always executed
	  });
}
function startList() {
	selectGalleryList();
}
$(document).ready(function () {
	startList();
});



</script>
<script type="text/css">
/* Pagination */
.pagenation {
    overflow: hidden;
    text-align: center;
    margin-top: 30px;
}
.pagenation a {
    display: inline-block;
    width: 35px;
    height: 35px;
    line-height: 35px;
    border: 1px solid #e8e9ec;
    border-radius: 3px;
    font-size: 12px;
    color: #818181;
    text-decoration: none;
    -webkit-transition: background-color .3s;
    transition: background-color .3s;
}
.pagenation a.active {
    background-color: #5b6b8a;
    color: #fff;
}
.pagenation a:hover:not(.active) {
    background-color: #ddd;
}
</script>
<!-- Section-->
<section class="py-5">
	<div>
		<input type="text" class="form-control" id="searchGalleryWord" placeholder="Recipient's username" aria-label="Recipient's username" aria-describedby="button-addon2">
		<button class="btn btn-outline-secondary" type="button" onclick="selectGalleryList()"> Button</button>
		<select class="form-select" id="ORDER" onchange="selectGalleryList()">
		  <option value="" selected>정렬</option>
		  <option value="GALLERY_SEQ">이름</option>
		  <option value="AUTHOR">타이틀</option>
		  <option value="TYPE">타입</option>
		  <option value="REG_DATE">등록일</option>
		  <option value="CNT">페이지수</option>
		</select>
		<select class="form-select" id="ORDER_FLAG" onchange="selectGalleryList()">
		  <option value="" selected>정렬</option>
		  <option value="DESC">내림차순</option>
		  <option value="ASC">오름차순</option>
		</select>
	</div>
	<div class="container px-4 px-lg-5 mt-5" id="galleryListShow">
		<div id="galleryListArea" class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
			<div class="input-group mb-3">
			</div>
		</div>
	</div>
	<div class="container-fluid" id="galleryDetailHide" style="display:none;">
		<div class="row" id="galleryDetailArea">
			<div class="col-sm-12">
				<!-- Responsive images -->
				<h3>Responsive images</h3>
				<p>To make images "responsive" (they will change size as needed with the viewport) they be given the class of <code>img-responsive</code>.</p>
				<p><img class="img-responsive" src="http://placehold.it/1200x600" alt=""></p>
				<hr>
			</div>
		</div>
		<!-- /.row -->
	</div>
	<div class="pagenation" id="galleryListPagination">
	</div>
</section>
<!-- Footer-->
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>
