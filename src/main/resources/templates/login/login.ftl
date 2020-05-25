<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>
</head>

<body>
<@sec.authorize access="isAuthenticated()">
    <% response.sendRedirect("/"); %>
</@sec.authorize>
<div align="center">
    <form method="POST" action="/login">
        <h2 >Вход в систему</h2>
        <div class = "container my-5" >
            <div >
                <input name="username" type="text" placeholder="Username" autofocus="true"/><br>
                <input name="password" type="password"  placeholder="Password" ><br>
                <button type="submit" class="btn btn-dark">Log In</button>
                <h4><a href="/registration" align="center">Зарегистрироваться</a></h4>
            </div>
        </div>
    </form>
</div>

</body>
</html>