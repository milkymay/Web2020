<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if post_id??>
        <#assign p = c.findBy(posts, "id", post_id)!/>
        <#if p?has_content>
            <@c.display_post p false/>
        <#else>
            No such post
        </#if>
    </#if>
</@c.page>
