<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: aoliferov
  Date: 26.12.2018
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Start page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script type="text/javascript" src="plagin/form2js.js"></script>
    <script type="text/javascript" src='<c:url value="/plagin/form2js.js"/>'></script>
    <style type="text/css">
        input:focus:invalid {
            border: 2px solid red;
        }
    </style>
    <script>

        function updateClicked(){
            var country = $('#country').val();
            $.ajax({
                type: 'GET',
                url: '/user_app/rest/catalog/cities?country=' + country,
                complete: function(data) {
                    var cities = JSON.parse(data.responseText);
                    var result = "<option selected disabled value=''>choose city</option>";
                    for (var i = 0; i < cities.length; i++) {
                        result = result.concat('<option value=' + cities[i].id + '>' + cities[i].name + '</option>');
                    }
                    $('#city').html(result);
                }
            });
        }

        $.ajax({
            type: "GET",
            url: '/user_app/rest/catalog/countries',
            complete: function (data) {
                var countries = JSON.parse(data.responseText);
                var result = "";
                for (var i = 0; i < countries.length; i++) {
                    result = result.concat('<option value=' + countries[i].id + '>' + countries[i].name + '</option>');
                }
                $('#country option').after(result);
            }
        });

        var user = "${sessionScope.currentUser.id}";
        $.ajax({
            type: "GET",
            url: '/user_app/rest/catalog/clients?user=' + user,
            complete: function (data) {
                var clients = JSON.parse(data.responseText);
                var result = "";
                for (var i = 0; i < clients.length; i++) {
                    result = result.concat('<tr><td>' + clients[i].first_name + '</td><td>' + clients[i].second_name +'</td><td>' + clients[i].email + '</td></tr>');
                }
                $('#table thead').after('<tbody>' + result + '</tbody>');
            }
        });

        function summary() {
            if (validate()) {
                ajaxReq();
            }
        }

        function ajaxReq() {
            $.ajax({
                type: "POST",
                url: '/user_app/rest/catalog/clients',
                data: JSON.stringify(form2js('clientForm', '.', true)),
                contentType : "application/json",
                dataType: 'json',
                complete: function(data) {
                    if (data.responseText.indexOf("success") + 1) {
                        addRow();
                    }
                }
            });
        }

        function validate() {
            var result = true;
            var fields = document.getElementsByClassName('valide');
            for (var i = 0; i < fields.length; i++) {
                if (fields[i].value == "") {
                    alert("Write in all required fields");
                    fields[i].focus();
                    result = false;
                    break;
                }
            }
            return result;
        }

        function addRow() {
            var firstname = $('#first_name').val();
            var secondname = $('#second_name').val();
            var email = $('#email').val();
            $('#table tr:last').after('<tr><td>' + firstname + '</td><td>' + secondname +'</td><td>' + email + '</td></tr>');
        }

    </script>
</head>
<body>

<div class="container">
    <h1>Client list</h1>
    <h2>Bootstrap Page dinamic client page</h2>
    <p>This page is intended to dynamically add and display client without refreshing the page.</p>

    <form id="clientForm" action="/json">
        <div class="form-group">
            <label for="first_name">Name:</label>
            <input type="text" class="form-control valide" id="first_name" placeholder="Enter first name" name="first_name" required>
        </div>
        <div class="form-group">
            <label for="second_name">Second name:</label>
            <input type="text" class="form-control valide" id="second_name" placeholder="Enter second name" name="second_name" required>
        </div>
        <div class="form-group">
            <label>Contacts:</label>
            <input type="tel" class="form-control valide" id="phone" placeholder="Enter phone" name="phone" required>
            <input type="email" class="form-control valide" id="email" placeholder="Enter email" name="email" required>
        </div>
        <div class="form-group ">
            <label>Address:</label>
            <select class="form-control valide" id='country' name='address.country.id' onchange="updateClicked()" required>
                <option selected disabled value=''>choose country</option>
            </select>
            <select class="form-control valide" id='city' name='address.city.id' required>
                <option selected disabled value=''>choose city</option>
            </select>
            <input class="form-control valide" type='text' name='address.data' placeholder="Enter data" required/>
        </div>
        <label class="radio-inline">
            <input type="radio" name="sex" value="male" checked>
            Male
        </label>
        <label class="radio-inline">
            <input type="radio" name="sex" value="female" >
            Female
        </label>
        <input type="hidden" id="owner" name="owner" value="${sessionScope.currentUser.id}">
        <div>
            <input type="button" class="btn btn-default" onclick="return summary()" value="Add (ajax req)">
            <input type="submit" class="btn btn-default" onclick="return validate()" value="Submit">
        </div>
    </form>

    <table class="table my-lg-5" id="table">
        <thead>
        <tr>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>Email</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>
