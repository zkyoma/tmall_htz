<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

	
<script>
$(function(){
	$("ul.pagination li.disabled a").click(function(){
		return false;
	});
});

</script>

<nav>
  <ul class="pagination">
    <li <c:if test="${!page.hasPrev}">class="disabled"</c:if>>
      <a  href="?pageNo=1${page.param}" aria-label="Previous" >
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>

    <li <c:if test="${!page.hasPrev}">class="disabled"</c:if>>
      <a  href="?pageNo=${page.pageNo}${page.param}" aria-label="Previous" >
        <span aria-hidden="true">&lsaquo;</span>
      </a>
    </li>

    <c:forEach begin="0" end="${page.totalPageNumber-1}" varStatus="status">


		    <li <c:if test="${page.pageNo == status.count}">class="disabled"</c:if>>
		    	<a
		    	href="?pageNo=${status.count}${page.param}"
		    	<c:if test="${page.pageNo == status.count}">class="current"</c:if>
		    	>${status.count}</a>
		    </li>

    </c:forEach>

    <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
      <a href="?pageNo=${page.pageNo + 1}${page.param}" aria-label="Next">
        <span aria-hidden="true">&rsaquo;</span>
      </a>
    </li>
    <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
      <a href="?pageNo=${page.totalPageNumber}${page.param}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>
