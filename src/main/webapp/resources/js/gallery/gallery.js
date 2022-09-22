function galleryDetail(seq) {
//	 document.getElementById("galleryListArea").style.display = "none";
//	 document.getElementById("galleryDetailHide").style.display = "block";
//	 
//	 var sendParam = new URLSearchParams();
//	 const url = '/json/gallery/selectGalleryDetail';
//	 sendParam.append('GALLERY_SEQ', seq);
//	 axios.post(url, sendParam)
//     .then((response) => {
//    	 let resultList = response.data.resultList;
//    	 console.log('resultList', resultList);
//		    let tableContent = '';
//		    for(var i =0; i< resultList.length ; i++){
//		    	let src1 = '/img'+resultList[i].FILE_PATH + resultList[i].FILE_NAME;
//		    	tableContent += '<div class="col-sm-12">';
//		    	tableContent += '	<p><img class="img-responsive" src="'+src1+'" alt=""></p>';
//		    	tableContent += '</div>';
//		    }
//		    document.getElementById('galleryDetailArea').innerHTML = tableContent;
//     })
//     .catch((error) => {
//         console.log(error.response)
//     }).then(function () {
//		    // always executed
//     }); 
	window.open("/detail/gallery/galleryDetail/"+seq);
	// location.href="/detail/gallery/galleryDetail/"+seq;
 }
