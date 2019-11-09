<%--
  Created by IntelliJ IDEA.
  User: 88475
  Date: 2019-11-7
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>response return</title>
  <script src="js/jquery-3.4.1.min.js"></script>
  <script type="text/javascript">
    $(() => {
        $("#jsonBtn").click(() => {
            $.ajax({
                url: "resp/testJson",
                dataType: "JSON",
                success: (data) => {
                    console.log(data);
                }
            });
        });
    })
  </script>

</head>
<body>
<div><a href="resp/testRetVoid">return void</a></div>
<div><a href="resp/testRetString">return string(默认是转发)</a></div>
<div><a href="resp/testRetStringRedirect">return string redirect</a></div>
<div><a href="resp/testRetModelAndView">return modelAndView</a></div>
<div><button id="jsonBtn" type="button">Json</button></div>
</body>
</html>