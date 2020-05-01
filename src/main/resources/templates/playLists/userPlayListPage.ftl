<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        ${playList.name}
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
            </tr>
        </#list>
    </table>

    <a href="playLists/${playList.id}/users"></a>

</@common.page>