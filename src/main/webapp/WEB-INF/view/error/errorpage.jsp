<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>


    <jsp:attribute name="title">Страница не найдена</jsp:attribute>

    <jsp:body>

        <!-- Page Content -->
        <div class="container">

            <!-- Marketing Icons Section -->
            <div align="center" class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        Упс, страница не найдена
                    </h1>
                    <a href="/index.html">На главную</a>
                </div>
            </div>
        </div>



    </jsp:body>

</page:template>
