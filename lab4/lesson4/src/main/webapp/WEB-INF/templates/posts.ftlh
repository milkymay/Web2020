<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id??>
        <#list posts?filter(x -> x.user_id == user_id)?reverse as post>
            <#assign p = c.findBy(posts, "id", post.id)!/>
            <@c.display_post p true/>
        </#list>
    </#if>
</@c.page>
