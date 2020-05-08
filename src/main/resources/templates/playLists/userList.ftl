<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        <a href="/playLists/${playListId}"><-Назад</a>
    </div>
    <div>
        Ссылка для приглашения : ${joinLink}
    </div>

    <#if (nowUserRole.id >=3) >
        <form action="/playLists/${playListId}/addUser" method="post" enctype="multipart/form-data">
            <input type="number" name="userId">
            <button type="submit">Добавить</button>
        </form>
        <#if (nowUserRole.id ==4) >
            <div><a href="/playLists/${playListId}/changeOwner">Поменять владельца</a></div>
        </#if>
        <div><a href="/playLists/${playListId}/admins">Aдминистраторы</a></div>
        <div><a href="/playLists/${playListId}/moderators">Модераторы</a></div>
    </#if>

    <table>
        <thead>
        <tr>
            <td>UserName</td>
            <td>Role</td>
        </tr>
        </thead>
        <#list listUserWithRole as userWithRole>
            <tr>
                <td>${userWithRole.user.username}</td>
                <td>${userWithRole.playListRole.getName()}</td>
                <td>
                    <#if (nowUserRole.id >=3)>
                        <form action="/playLists/${playListId}/deleteUser" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="userId" value="${userWithRole.user.id}">
                            <button type="submit">Удалить</button>
                        </form>
                    </#if>
                </td>
            </tr>
        </#list>
    </table>


</@common.page>