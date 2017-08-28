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
                <form id="theForm" class="form-inline" method="POST">
                    <label for="path">Новая директория</label>
                    <input class="form-control" id="path" name="path">
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
                    <c:forEach items="${dbLogs}" var="item">
                        <tr>
                            <td>${item.CREATED}</td>
                            <td>${item.PATH}</td>
                            <td>${item.DIRCNT}</td>
                            <td>${item.FILECNT}</td>
                            <td>${item.SUMMURYSIZE}</td>
                            <td>
                                <div class="btn btn-default ch" id="${item.IDLOG}">Файлы</div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <hr>
        </div>


       <div class="modal fade" id="myModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 class="modal-title" id="modalTitle">Big Title</h3>
                    </div>
                    <div class="modal-body">
                        <table class="table table-striped" id="tblGrid">
                            <thead id="tblHead">
                            <tr>
                                <th>Файл</th>
                                <th>Размер</th>
                            </tr>
                            </thead>
                            <tbody id="insertRow">

                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default " data-dismiss="modal">Close</button>
                    </div>

                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->


        <!-- /.container -->
        <script type="text/JavaScript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

        <script type="text/JavaScript">
            $(document).ready(function () {
                $(".ch").click(function (event) {
                    $.ajax({
                        url: 'getFiles',
                        type: 'POST',
                        data: {'id': String(event.target.id)}, // An object with the key 'submit' and value 'true;
                        success: function (results) {
                            $('#insertRow').empty();
                            $.each(results, function (k, v) {
                                $('<tr>').append(
                                    $('<td>').text(v.NAME),
                                    $('<td>').text(v.SIZE)
                                ).appendTo('#insertRow');
                            });
                            $('#myModal').modal('show');
                        }
                    });

                    // alert(String(event.target.id));
                });
            });
        </script>


    </jsp:body>
</page:template>

