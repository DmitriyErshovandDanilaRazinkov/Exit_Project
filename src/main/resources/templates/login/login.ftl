<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        <div class="w-25 mx-auto mb-3">
            <h2 class="mx-auto">Вход в систему</h2>
            <form method="POST" action="/login">
                <div>
                    <div class="input-group input-group-prepend pt-1">
                        <input class="form-control" name="username" type="text" placeholder="Username"
                               autofocus="true"/></div>
                    <div align="center">
                        <div class="input-group input-group-prepend pt-1 pb-1 my-2">
                            <input class="form-control" name="password" type="password" placeholder="Password"/>
                        </div>
                        <button class="btn btn btn-success btn-lg btn-block my-2" type="submit" align="center">Log In</button>
                        <h4><a href="/registration" >Зарегистрироваться</a></h4>
                    </div>
                </div>
            </form>
        </div>

</@common.page>