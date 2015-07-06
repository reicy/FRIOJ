<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<h2>Articles</h2>


<c:forEach items="${articleList}" var="article">
		
		
			<div class="articlePreview">
			
				<div class="articleUpperHalf">
					<a  href="/frioj/article/<c:out value="${article.articleId}"/>" class="articleLink">
						
						<security:authorize access="hasAnyRole('Teacher,Admin')">
								
							<c:if test="${article.status.num==1}">
								<span class="red">
									<c:out value="${article.status.toString()}"></c:out>
								</span>
							</c:if>	
						</security:authorize>
						
						<span>
							<c:out value="${article.name }"/>
							
						</span>
						
					</a>
				</div>
				
				<div class="articleLowerHalf">
					<div class="articleNote">
						<c:out value="${article.note}"/>
					</div>
				</div>
	
			
		
		
			<security:authorize access="hasAnyRole('Teacher,Admin')">
				
				<div class="UDbuttonsArticles">
					
					<div onclick="deleteSth('article','<c:out value="${article.name}"/>','/frioj/admin/deleteArticle/<c:out value="${article.articleId}"/>')" class="greyButtonSizeLess right  grayBack roundedBorders">
						delete
					</div>
					
					<a class="greyButtonSizeLess right grayBack roundedBorders"  href="/frioj/admin/updateArticleForm/<c:out value="${article.articleId}"/>">
						update
					</a>	
					
					
				</div>
			</security:authorize>
		</div>
		
	
</c:forEach>


<div class="pageNumbers">
	
		<%@ page import="java.lang.Math" %>
		<%
			int currentPage = 1;
			int pages = 1;
			if(request.getAttribute("currentPage")!=null){
				currentPage = (Integer)(request.getAttribute("currentPage"));
			}
			
			if(request.getAttribute("pages")!=null){
				pages = (Integer)(request.getAttribute("pages"));
				
			}
			
			if(currentPage!=1 || pages!=1){
			
				if(currentPage==1){
				
					for(int i = 1; i <= Math.min( 3 , pages); i++){
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllArticles?page="+i+"\">"+i+"</a>");
					}
					if(pages!=0)
					out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllArticles?page="+Math.min(currentPage+1, pages)+"\">"+"next"+"</a>");
				
				}else{
					if(currentPage==pages){
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllArticles?page="+Math.max(currentPage-1, 1)+"\">"+"prev"+"</a>");
						
						for(int i = Math.max(currentPage-2, 1); i <= currentPage; i++){
							out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllArticles?page="+i+"\">"+i+"</a>");
						}
						
					}else{
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllArticles?page="+Math.max(currentPage-1, 1)+"\">"+"prev"+"</a>");
	
						for(int i = currentPage-1; i <= Math.min( currentPage+1 , pages); i++){
							out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllArticles?page="+i+"\">"+i+"</a>");
						}
						
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllArticles?page="+Math.min(currentPage+1, pages)+"\">"+"next"+"</a>");
						
					}
				}
			}
			
		%>
	</div>
 
