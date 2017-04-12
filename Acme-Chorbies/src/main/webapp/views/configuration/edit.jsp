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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<fieldset>
		<legend>
			<spring:message code="configuration.info" />
		</legend>
		<div>
			<form:label path="hour">
				<spring:message code="configuration.hour" />
			</form:label>
			<form:input path="hour" />
			<form:errors path="hour" cssClass="error" />
		</div>
		<br>
		<div>
			<form:label path="minute">
				<spring:message code="configuration.minute" />
			</form:label>
			<form:input path="minute" />
			<form:errors path="minute" cssClass="error" />
		</div>
		<br>
		<div>
			<form:label path="second">
				<spring:message code="configuration.second" />
			</form:label>
			<form:input path="second" />
			<form:errors path="second" cssClass="error" />
		</div>
		<br>
	</fieldset>

	<acme:submit name="save" code="configuration.save" />

	<acme:cancel url="configuration/administrator/list.do" code="configuration.cancel" />

</form:form>