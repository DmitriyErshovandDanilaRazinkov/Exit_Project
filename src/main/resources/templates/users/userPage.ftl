<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        ${pageTo.user.id}
    </div>
    <div>
        ${pageTo.user.username}
    </div>
    <div>
        Cash: ${pageTo.user.cash}
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
    <div>
        <form method="post" action="/addPlayList" enctype="multipart/form-data">
            <input name="name" type="text">
            <input name="isPrivate" type="checkbox">
            <button type="submit">Добавить</button>
        </form>
    </div>

    <h3>
        Плейлисты
    </h3>
    <table>
        <#list pageTo.playLists as playList>
            <tr>
                <td><a href="/playLists/${playList.id}">${playList.name}</a></td>
            </tr>
        </#list>
    </table>

</@common.page>