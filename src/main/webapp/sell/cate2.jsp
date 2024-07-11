<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<select class="form-control" id="category2" name="category">
	<option value="">=중분류 선택=</option>
	<c:forEach var="c" items="${ca_2 }">
		<option value="${c.caId}">${c.caName}</option>
	</c:forEach>
</select>