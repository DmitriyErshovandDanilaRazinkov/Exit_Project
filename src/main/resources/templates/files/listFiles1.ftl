<#import "../parts/common.ftl" as common>

<@common.page>

    <#list files as file>
        <div>
            <b>${file.getId()}</b>
            <span>${file.getName()}</span>
            <span>${file.getOriginalName()}</span>
        </div>
    </#list>

</@common.page>