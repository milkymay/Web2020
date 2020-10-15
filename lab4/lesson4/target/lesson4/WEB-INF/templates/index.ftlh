<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <@c.display_post post true/>
    </#list>
</@c.page>
