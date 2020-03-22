<#import "../parts/common.ftl" as common>

<@common.page>


    <#list users as user>
        <div>
            <b>${user.getId()}</b>
            <span>${user.getName()}</span>
            <span>${user.getUserType}</span>
        </div>
    </#list>

</@common.page>