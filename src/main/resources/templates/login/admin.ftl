<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>
</head>

<body>
<div>
    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>UserName</td>
            <td>Password</td>
            <td>Roles</td>
        </tr>
        </thead>
        <#list user as user>
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>
                    <#list user.roles as role> ${role.name}; </#list>
                </td>
                <td>
                    <form action="/admin" method="post">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>
    <a href="/">Главная</a>
</div>
</body>
</html>
