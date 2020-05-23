<#import "../parts/common.ftl" as common>

<@common.page>

    <table>
        <thead>
        <tr>
            <td>Name</td>
            <td>Tags</td>
            <td>Add in PlayList</td>
        </tr>
        </thead>
        <#list listAudio as audio>
            <tr>
                <td><a href="/user/audioPage/${audio.id}"><span>${audio.name}</span></td>
                <td><#list audio.tags as tag>${tag.name};</#list></td>
                <td>
                    <a href="/audio/addInPlayList/${audio.id}">Add in PlayList</a>
                </td>
            </tr>
        </#list>
    </table>

</@common.page>