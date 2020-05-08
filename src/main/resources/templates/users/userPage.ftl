<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        ${user.id}
    </div>
    <div>
        ${user.username}
    </div>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input name="name" type="text">
            <input name="isPrivate" type="checkbox">
            <button type="submit">Добавить</button>
        </form>
    </div>

    <table>
        <#list user.playLists as playList>
            <div>
                <tr>
                    <td><a href="/playLists/${playList.id}">${playList.name}</a></td>
                </tr>
            </div>
        </#list>
    </table>

</@common.page>