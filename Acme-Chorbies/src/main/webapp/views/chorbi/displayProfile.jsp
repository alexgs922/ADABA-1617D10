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

<spring:message code="chorbi.personalInfo" var="chorbiPersonalInfo" />
<h2>
	<jstl:out value="${chorbiPersonalInfo}" />
</h2>
<display:table pagesize="5" class="displaytag" name="chorbi"
	requestURI="${requestURI}" id="row">

	<spring:message code="chorbi.picture" var="chorbiPicture" />
	<display:column title="${chorbiPicture}" sortable="false">
		<img src="${row.picture}" width="200" height="100" />
	</display:column>

	<spring:message code="chorbi.name" var="chorbiName" />
	<display:column property="name" title="${chorbiName}" sortable="false" />

	<spring:message code="chorbi.surname" var="chorbiSurname" />
	<display:column property="surName" title="${chorbiSurname}"
		sortable="false" />

	<spring:message code="chorbi.genre" var="chorbiGenre" />
	<display:column property="genre" title="${chorbiGenre}"
		sortable="false" />

	<spring:message code="chorbi.birthDate" var="chorbiBirthDate" />
	<display:column property="birthDate" title="${chorbiBirthDate}"
		sortable="false" />

	<spring:message code="chorbi.relationship" var="chorbiRelationship" />
	<display:column property="relationship" title="${chorbiRelationship}"
		sortable="false" />


</display:table>

<security:authorize access="hasRole('CHORBI')">
	<div>

		<jstl:choose>
			<jstl:when test="${toLike==true and principal.id ne row.id}">
				<a href="chorbi/chorbi/like.do?chorbiId=${row.id}"><img
					src="images/like.jpg" width="120" height="120" /></a>
			</jstl:when>
			<jstl:when test="${toLike==false and principal.id ne row.id}">
				<a href="chorbi/chorbi/cancelLike.do?chorbiId=${row.id}"><img
					src="images/cancelLike.jpg" width="80" height="80" /></a>
			</jstl:when>

		</jstl:choose>


	</div>
</security:authorize>


<spring:message code="chorbi.locationInfo" var="chorbiLocationInfo" />
<h2>
	<jstl:out value="${chorbiLocationInfo}" />
</h2>
<display:table pagesize="5" class="displaytag" name="chorbi"
	requestURI="${requestURI}" id="coordinate">

	<spring:message code="chorbi.country" var="chorbiCountry" />
	<display:column property="coordinate.country" title="${chorbiCountry}"
		sortable="false" />

	<spring:message code="chorbi.state" var="chorbiState" />
	<display:column property="coordinate.state" title="${chorbiState}"
		sortable="false" />

	<spring:message code="chorbi.province" var="chorbiProvince" />
	<display:column property="coordinate.province"
		title="${chorbiProvince}" sortable="false" />

	<spring:message code="chorbi.city" var="chorbiCity" />
	<display:column property="coordinate.city" title="${chorbiCity}"
		sortable="false" />


</display:table>

<spring:message code="chorbi.contactInfo" var="chorbiContactInfo" />
<h2>
	<jstl:out value="${chorbiContactInfo}" />
</h2>
<display:table pagesize="5" class="displaytag" name="chorbi"
	requestURI="${requestURI}" id="row">

	<spring:message code="chorbi.email" var="chorbiEmail" />
	<display:column title="${chorbiEmail}" sortable="false">
		<jstl:out value="***"></jstl:out>
	</display:column>

	<spring:message code="chorbi.phone" var="chorbiPhone" />
	<display:column title="${chorbiPhone}" sortable="false">
		<jstl:out value="***"></jstl:out>
	</display:column>

</display:table>