<%-- 
    Document   : deposit
    Created on : Sep 18, 2018, 1:32:47 PM
    Author     : shalini_w
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Deposit</title>
               
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2">
                    <jsp:include page="resource/menu.jsp"></jsp:include>
                </div>
                <div class="col-md-10">
                    <div class="row back">
                        <jsp:include page="resource/header.jsp"></jsp:include>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-1"></div>
                        <div class="col-md-11">
                            <div class="row">
                                <h1>DEPOSIT</h1>
                            </div>
                    
                            <div class="row">
                                <c:forEach var="item" items="${functions}">
                                    <h3>    
                                        <input type="button" class="btn btn-primary" name="<c:url value="${item.getFunction_url()}?index=${item.getFunction_id()}"></c:url>" value="${item.getFunction_name()}"></input>  
                                    </h3>
                                </c:forEach>
                            </div>
                        </div>
                    </div>   
                </div>
            </div>
        </div>
    </body>
</html>
