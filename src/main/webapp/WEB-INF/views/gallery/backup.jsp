<!DOCTYPE html>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-12">

					<!-- Responsive images -->
					<h3>Responsive images</h3>
					<p>To make images "responsive" (they will change size as needed with the viewport) they be given the class of <code>img-responsive</code>.</p>
					<p><img class="img-responsive" src="http://placehold.it/1200x600" alt=""></p>
					<p>To make images into smaller thumbnail images, they can be given the classes of <code>img-responsive</code> and <code>thumbnail</code>.</p>
					<div class="row">
						<div class="col-sm-2"><img class="thumbnail img-responsive" src="http://placehold.it/1200x600" alt=""></div>
						<div class="col-sm-2"><img class="thumbnail img-responsive" src="http://placehold.it/200x200" alt=""></div>
						<div class="col-sm-2"><img class="thumbnail img-responsive" src="http://placehold.it/200x200" alt=""></div>
						<div class="col-sm-2"><img class="thumbnail img-responsive" src="http://placehold.it/200x200" alt=""></div>
						<div class="col-sm-2"><img class="thumbnail img-responsive" src="http://placehold.it/200x200" alt=""></div>
						<div class="col-sm-2"><img class="thumbnail img-responsive" src="http://placehold.it/1200x600" alt=""></div>
					</div>
					<hr>

					<!-- Pagination -->
					<h3>Pagination</h3>
					<p>Pagination can be applied to any unordered list using the <code>pagination</code> class.</p>
					<nav>
						<ul class="pagination">
							<li><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
						</ul>
					</nav>
					<hr>

					<!-- Pager -->
					<h3>Pager</h3>
					<p>The pager (which creates next and previous page links) can be applied to any unordered list using the <code>pager</code> class.</p>
					<nav>
						<ul class="pager">
							<li class="previous"><a href="#"><span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span> Older</a></li>
							<li class="next"><a href="#">Newer <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a></li>
						</ul>
					</nav>
					<hr>

				</div>
			</div>
			<!-- /.row -->
		</div>
		<!-- /.container-fluid -->
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>


			<!-- image divide -->
<!-- 			<div class="col mb-5">
				<div class="card h-100">
					Product image
					<img id="test1" class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." />
					Product details
					<div class="card-body p-4">
						<div class="text-center">
							Product name
							<h5 class="fw-bolder">Fancy Product</h5>
							Product price
						</div>
					</div>
					Product actions
					<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
						<div class="text-center">
							<a class="btn btn-outline-dark mt-auto" href="#">View options</a>
						</div>
					</div>
				</div>
			</div> -->
			<!-- image divide -->