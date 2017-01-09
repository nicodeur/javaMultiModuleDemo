<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="includes/header.jsp" />

    <div class="col-sm-5">
    	<h2>Hello World !</h2>
    	
    	<c:if test="${not empty successMsg}">
    		<div class="alert alert-success" role="alert">${successMsg}</div>
    	</c:if>
	    <form:form  method="POST" commandName="customer" class="form-horizontal">
	    	<form:hidden path="id"/>
	    	<div class="form-group">
	    		<label for="firstName" class="col-sm-2 control-label">FirstName :</label> 
	    		<div class="col-sm-5">
	    			<form:input path="firstName" class="form-control"/><br />
	    		</div>
	    		<div class="col-sm-5">
	    			<form:errors path="firstName" class="alert alert-danger" element="div"/>
	    		</div>
	    	</div>
	    	<div class="form-group">
		    	<label for="lastName" class="col-sm-2 control-label">LastName :</label> 
		    	<div class="col-sm-5">
		    		<form:input path="lastName" class="form-control" />
		    	</div>
		    	<div class="col-sm-5">
	    			<form:errors path="lastName" class="alert alert-danger" element="div"/>
	    		</div>
		    </div>
		    <div class="form-group">
		    	<label for="birthDate" class="col-sm-2 control-label">Birth date :</label> 
		    	<div class="col-sm-5">
		    		<form:input type="date" path="birthDate" class="form-control"/>
		    	</div>
		    	<div class="col-sm-5">
	    			<form:errors path="birthDate" class="alert alert-danger" element="div"/>
	    		</div>
		    </div>
	    	<div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button type="submit" class="btn btn-default">Submit</button>
			    </div>
			</div>
	    </form:form>
    </div>

<jsp:include page="includes/footer.jsp" />