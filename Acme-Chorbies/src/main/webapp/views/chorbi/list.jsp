<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="5" class="displaytag" name="chorbies" requestURI="${requestURI}" id="row">
	
	<spring:message code="chorbi.picture" var="chorbiPicture" />
	<display:column title="${chorbiPicture}" sortable="false" >
		<img src="${row.picture}" width="200" height="100" />
	</display:column>
	
	<spring:message code="chorbi.name" var="chorbiName" />
	<display:column property="name" title="${chorbiName}" sortable="false" />
	
	<spring:message code="chorbi.surname" var="chorbiSurname" />
	<display:column property="surName" title="${chorbiSurname}" sortable="false" />
	
	<spring:message code="chorbi.genre" var="chorbiGenre" />
	<display:column property="genre" title="${chorbiGenre}" sortable="false" />
	
	<spring:message code="chorbi.birthDate" var="chorbiBirthDate" />
	<display:column property="birthDate" title="${chorbiBirthDate}" sortable="false" />
	
	<spring:message code="chorbi.relationship" var="chorbiRelationship" />
	<display:column property="relationship" title="${chorbiRelationship}" sortable="false" />
	
	<security:authorize access="isAuthenticated()">
		<display:column>
			<a
				href="chorbi/profile.do?chorbiId=${row.id}">
				<spring:message code="chorbi.profile" />
			</a>

		</display:column>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
		<display:column>
			<a
				href="chorbi/listWhoLikeThem.do?chorbiId=${row.id}">
				<spring:message code="chorbi.ListWhoLikeThem" />
			</a>

		</display:column>
	</security:authorize>
	
	

</display:table>