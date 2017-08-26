<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>


<page:template>
    <jsp:body>

        <!-- Page Content -->
        <div class="container">

            <!-- Page Heading/Breadcrumbs -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Директории и файлы
                        <small>Тестовое задание</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="index.html">Главная</a>
                        </li>
                        <li class="active">Dirs&Files</li>
                    </ol>
                </div>
            </div>
            <div class="row" align="center">
                <form  class="form-inline" >
                    <label for="folderPath">Новая директория</label>
                    <input class="form-control" id="folderPath">
                    <button type="submit" class="btn btn-default">Добавить в список</button>
                </form>
            </div>
            <hr>
            <div class="row">
                    <table class="table" align="center" width="90%">
                        <thead>
                        <tr>
                            <th>Дата</th>
                            <th>Базовая директория</th>
                            <th>Директорий</th>
                            <th>Файлов</th>
                            <th>Суммарный размер файлов</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${resultObject}" var="item">
                            <tr>
                                <td>${item.CREATED}</td>
                                <td>${item.PATH}</td>
                                <td>${item.DIRCNT}</td>
                                <td>${item.FILECNT}</td>
                                <td>${item.SUMMURYSIZE}</td>
                                <td><a href="" class="btn btn-default">Файлы</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
            </div>
            <hr>
        </div>
        <!-- /.container -->

    </jsp:body>
</page:template>

