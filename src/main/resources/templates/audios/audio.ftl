<#import "../parts/admincommon.ftl" as common>

<@common.page>
    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>Name</td>
            <td>Tags</td>
            <td>FileId</td>
        </tr>
        </thead>
        <tr>
            <td>${audio.id}</td>
            <td>${audio.name}</td>
            <td><#list audio.tags as tag>${tag.id}:${tag.name};</#list></td>
            <td>${audio.file.id}</td>
        </tr>
    </table>

    <div>
        <h2>
            Добавить тэг
        </h2>
        <#list tagList as tag>
                <a href="/admin/audios/${audio.id}/${tag.id}">
                    ${tag.name}
                </a>
        </#list>
    </div>
</@common.page>