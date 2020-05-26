<#import "../parts/admincommon.ftl" as common>

<@common.page>

    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>Name</td>
        </tr>
        </thead>
        <#list playLists as playList>
            <tr>
                <td>${playList.id}</td>
                <td>${playList.name}</td>
                <td>
                    <form action="/admin/deletePlayList" method="post">
                        <input type="hidden" name="userId" value="${playList.id}"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>

</@common.page>