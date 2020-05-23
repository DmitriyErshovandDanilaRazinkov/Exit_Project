<#import "../parts/common.ftl" as common>

<@common.page>

    <div><span>${audio.name}</span></div>
    <div>
        <#list audio.tags as tag>
            <div>
                ${tag.name}
            </div>
        </#list>
    </div>
    <td>
    <audio controls preload="metadata">
        <source src="/download/${audio.file.id}">
    </audio>

</@common.page>