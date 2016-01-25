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
    <title></title>
</head>
<body bgcolor="#cccccc">
<h1><a href="/">Convex Hull</a></h1>

<h3>Please upload your image:</h3>

<form action="" method="post" enctype="multipart/form-data">
    <input type="file" name="imageFile"/>

    <input type="submit" value="Upload"/>
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
            <img src="${inputImage}" alt="Input"/>
        </td>
        <td>
            <img src="${outputImage}" alt="Output"/>
        </td>
    </tr>
</table>

</body>
</html>
