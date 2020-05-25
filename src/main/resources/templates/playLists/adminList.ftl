<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        <a href="/playLists/${pageTo.playListId}/users"><-Назад</a>
    </div>

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
                    <#if userWithRole.playListRole.id==3>
                        <form action="/playLists/${pageTo.playListId}/deleteAdmin" method="post"
                              enctype="multipart/form-data">
                            <input type="hidden" name="userId" value="${userWithRole.user.id}">
                            <button type="submit">Удалить админа</button>
                        </form>
                    </#if>
                    <#if userWithRole.playListRole.id < 3>
                        <form action="/playLists/${pageTo.playListId}/addAdmin" method="post"
                              enctype="multipart/form-data">
                            <input type="hidden" name="userId" value="${userWithRole.user.id}">
                            <button type="submit">Добавить админа</button>
                        </form>
                    </#if>
                </td>
            </tr>
        </#list>
    </table>

</@common.page>