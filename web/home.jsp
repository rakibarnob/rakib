<%--
  Created by IntelliJ IDEA.
  User: Raihan
  Date: 23-Jan-16
  Time: 10:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Convex Hull</title>
</head>
<body bgcolor="#cccccc">
<h1><a href="/">Convex Hull</a></h1>

<p style="color: red;">
    <%
        out.write(
            request.getAttribute("errorMessage") == null ? "" : request.getAttribute("errorMessage").toString()
        );
    %>
</p>

<form action="" method="post" enctype="multipart/form-data">
    Image: <input type="file" name="imageFile"/>

    Select Color: <input type="text" name="color" value="${color}" />

    Color Tolerance: <input type="text" name="tolerance" value="${tolerance}" />

    <input type="submit" value="Update"/>
</form>

<br/>
<br/>

<table border="1" width="100%">
    <tr>
        <th>Input</th>
        <th>Output</th>
    </tr>

    <tr>
        <td>
            <img src="${inputImage}" style="max-width: 100%;" alt="Input"/>
        </td>
        <td>
            <img src="${outputImage}" style="max-width: 100%;" alt="Output"/>
        </td>
    </tr>
</table>

</body>
</html>
