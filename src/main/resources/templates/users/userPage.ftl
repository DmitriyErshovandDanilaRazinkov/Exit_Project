<#import "../parts/common.ftl" as common>

<@common.page>
    <div class = "container">
        <div class="row">
            <div class="col-sm">
                <h3 class = "my-3">
                    Плейлисты
                </h3>
                <table class="table table-borderless">
                    <#list pageTo.playLists as playList>
                        <tr>
                            <td>
                                <form action="/deletePlayList" method="post">
                                <td><input type="hidden" name="playListId" value="${playList.id}">
                                    <a href="/playLists/${playList.id}">${playList.name}</a></td>
                                <td><button class="btn btn-outline-danger ml-5" type="submit">Удалить</button></td>
                                </form>

                            </td>
                        </tr>
                    <#else>
                        У вас пока еще нет плэйлистов, но вы можете добавить их.
                    </#list>
                </table>
                <div>
                    <b>Добавить плэйлист</b>
                    <form method="post" action="/addPlayList" enctype="multipart/form-data" class = "my-3">
                        <input name="name" type="text" value="${(name)!""}">
                        <input name="isPrivate" type="checkbox"> сделать приватным
                        <br><button type="submit" class="btn btn-outline-primary my-2">Добавить</button>
                        </br><span>${(message)!""}</span>
                    </form>
                </div>
            </div>

            <div class = "col-sm mx-5">
                <h3 class="my-3">
                    Пользователь
                </h3>
                <div>
                    <b>id:</b> ${pageTo.user.id}
                </div>
                <div>
                    <b>username: </b>${pageTo.user.username}
                </div>
                <div>
                    <b>cash: </b>${pageTo.user.cash}
                </div>
                <#if pageTo.user.premium>
                    <div>
                        End premium: ${pageTo.user.endPremium}
                    </div>
                <#else>
                    <div>
                        <a href="/store/premium">Купить премиум</a>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</@common.page>