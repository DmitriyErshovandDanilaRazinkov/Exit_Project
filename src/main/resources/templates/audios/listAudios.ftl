<#import "../parts/common.ftl" as common>

<@common.page>

    <#list audios as audio>
        <div>
            <b>${audio.getId()}</b>
            <span>${audio.getName()}</span>
            <span>${audio.getTag()}</span>
            <span>${audio.getFileId()}</span>
        </div>
    </#list>

</@common.page>