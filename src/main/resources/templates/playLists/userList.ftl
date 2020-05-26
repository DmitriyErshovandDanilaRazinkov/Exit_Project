<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        <a href="/playLists/${pageTo.playListId}"><-Назад</a>
    </div>
    <div>
        Ссылка для приглашения : ${pageTo.joinCode}
    </div>

    <#if (pageTo.nowUserRole >=3) >
        <#if (pageTo.nowUserRole == 4) >
            <div><a href="/playLists/${pageTo.playListId}/changeOwner">Поменять владельца</a></div>
        </#if>
        <div><a href="/playLists/${pageTo.playListId}/admins">Aдминистраторы</a></div>
        <div><a href="/playLists/${pageTo.playListId}/moderators">Модераторы</a></div>
    </#if>

    <table>
        <thead>
        <tr>
            <td>UserName</td>
            <td>Role</td>
        </tr>
        </thead>
        <#list pageTo.roleInPlayListList as userWithRole>
            <tr>
                <td>${userWithRole.user.username}</td>
                <td>${userWithRole.playListRole.getName()}</td>
                <td>
                    <#if (pageTo.nowUserRole >=3)>
                        <form action="/playLists/${pageTo.playListId}/deleteUser" method="post"
                              enctype="multipart/form-data">
                            <input type="hidden" name="userId" value="${userWithRole.user.id}">
                            <button type="submit">Удалить</button>
                        </form>
                    </#if>
                </td>
            </tr>
        </#list>
    </table>


</@common.page>