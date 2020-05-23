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
                <td>${userWithRole.idComp.user.username}</td>
                <td>${userWithRole.playListRole.getName()}</td>
                <td>
                    <#if userWithRole.playListRole.id==2>
                        <form action="/playLists/${pageTo.playListId}/deleteModerator" method="post"
                              enctype="multipart/form-data">
                            <input type="hidden" name="userId" value="${userWithRole.idComp.user.id}">
                            <button type="submit">Удалить модератора</button>
                        </form>
                    </#if>
                    <#if userWithRole.playListRole.id < 2>
                        <form action="/playLists/${pageTo.playListId}/addModerator" method="post"
                              enctype="multipart/form-data">
                            <input type="hidden" name="userId" value="${userWithRole.idComp.user.id}">
                            <button type="submit">Добавить модератора</button>
                        </form>
                    </#if>
                </td>
            </tr>
        </#list>
    </table>

</@common.page>