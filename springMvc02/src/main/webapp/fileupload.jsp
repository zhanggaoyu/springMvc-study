<%--
  Created by IntelliJ IDEA.
  User: 88475
  Date: 2019-11-7
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>文件上传</title>
</head>
<body>
<fieldset>
  <legend>springMvc文件上传</legend>
  <form action="testFileupload" method="post" enctype="multipart/form-data">
    file: <input type="file" name="upload"/><br>
    <button type="submit">提交</button>
  </form>
</fieldset>
<fieldset>
  <legend>跨服务器文件上传</legend>
  <form action="testFileupload2Server" method="post" enctype="multipart/form-data">
    file: <input type="file" name="upload"/><br>
    <button type="submit">提交</button>
  </form>
</fieldset>
</body>
</html>
