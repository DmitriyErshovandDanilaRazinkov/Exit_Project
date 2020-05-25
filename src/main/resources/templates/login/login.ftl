<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        <body>

        <div align="center">
            <form method="POST" action="/login">
                <h2>Вход в систему</h2>
                <div>
                    <input name="username" type="text" placeholder="Username"
                           autofocus="true"/>
                    <input name="password" type="password" placeholder="Password"/>
                    <button type="submit">Log In</button>
                    <h4><a href="/registration">Зарегистрироваться</a></h4>
                </div>
            </form>
        </div>

</@common.page>