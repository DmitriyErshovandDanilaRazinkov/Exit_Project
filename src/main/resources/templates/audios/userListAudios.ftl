<#import "../parts/common.ftl" as common>

<@common.page>

    <table>
        <thead>
        <tr>
            <td></td>
            <td>Name</td>
            <td>Tags</td>
            <td>Add in PlayList</td>
        </tr>
        </thead>
        <#list listAudio as audio>
            <tr>
                <td><span>${audio.name}</span></td>
                <td><#list audio.tags as tag>${tag.name};</#list></td>
                <td><span><a href="/admin/files/${audio.getFileId()}">${audio.getFileId()}</a></span></td>
                <td>
                    <a href="/audio/addInPlayList/${audio.id}">Add in PlayList</a>
                </td>
            </tr>
        </#list>
    </table>

</@common.page>