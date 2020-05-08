<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        ${playList.name}
    </div>

    <div>
        <a href="/playLists/${playList.id}/users">Пользователи</a>
    </div>

    <table>
        <thead>
        <tr>
            <td>Name</td>
            <td>Tags</td>
            <td></td>
        </tr>
        </thead>
        <#list playList.listAudio as audio>
            <tr>
                <td><span>${audio.name}</span></td>
                <td><#list audio.tags as tag>${tag.name};</#list></td>
                <td>
                    <audio controls preload="metadata">
                        <source src="/download/${audio.fileId}">
                    </audio>
                </td>
                <td>
                    <#if (userRole.id >= 2)>
                        <form enctype="multipart/form-data" method="post">
                            <input type="hidden" name="audioId" value="${audio.id}"/>
                            <input type="hidden" name="action" value="delete"/>
                            <button type="submit">Delete</button>
                        </form>
                    </#if>
                </td>
            </tr>
        </#list>
    </table>

</@common.page>