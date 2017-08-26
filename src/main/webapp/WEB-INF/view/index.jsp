<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="style">

            .carousel .item {
                height: 600px;
            }

            .item img {
                position: absolute;
                top: 0;
                left: 0;
                min-height: 600px;
            }

    </jsp:attribute>
    <jsp:attribute name="title">Тестовое задание</jsp:attribute>

    <jsp:body>



        <header id="myCarousel" class="carousel slide">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class=""></li>
                <li data-target="#myCarousel" data-slide-to="1" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="2"></li>
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner">
                <div class="item">
                    <div class="fill"
                         style="background-image:url('${pageContext.request.contextPath}/resources/images/indexImage1.png');"></div>
                    <div class="carousel-caption">

                    </div>
                </div>
                <div class="item active">
                    <div class="fill"
                         style="background-image:url('${pageContext.request.contextPath}/resources/images/indexImage1.png');"></div>
                    <div class="carousel-caption">

                    </div>
                </div>
                <div class="item">
                    <div class="fill"
                         style="background-image:url('${pageContext.request.contextPath}/resources/images/indexImage1.png');"></div>
                    <div class="carousel-caption">

                    </div>
                </div>
            </div>

            <!-- Controls -->
            <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                <span class="icon-prev"></span>
            </a>
            <a class="right carousel-control" href="#myCarousel" data-slide="next">
                <span class="icon-next"></span>
            </a>
        </header>
        <!-- Page Content -->
        <div class="container">

            <!-- Marketing Icons Section -->
            <div align="center" class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        <a href="/dirs_and_files">Тестовое задание на Spring</a>
                    </h1>
                </div>

            </div>
        </div>


        <!-- Script to Activate the Carousel -->
        <script>
            $('.carousel').carousel({
                interval: 5000 //changes the speed
            })
        </script>
    </jsp:body>

</page:template>
