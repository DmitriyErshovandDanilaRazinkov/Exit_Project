<#import "parts/common.ftl" as common>
<#import "parts/navigation.ftl" as navigation>
<@common.page>

    <@navigation.navig/>

    <#list audios as audio>
        <div>
            <b>${audio.getId()}</b>
            <span>${audio.getName()}</span>
            <span>${audio.getTag()}</span>
            <span>${audio.getFileName()}</span>
        </div>
    </#list>
</@common.page>