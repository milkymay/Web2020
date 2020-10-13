<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign u = c.findBy(users, "handle", handle)!,
    next = c.findNext(users, "handle", handle)!,
    prev = c.findPrev(users, "handle", handle)!/>

    <#if u?has_content>
        <div class="user">
            <div class="name">
                <a href="${u.name}">${u.name}</a>
            </div>
            <#assign items=[
            {"name": "id", "key": "${u.id}"},
            {"name": "handle", "key": "${u.handle}"},
            {"name": "name", "key": "${u.name}"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <a>${item.name}: ${item.key}</a>
                    </li>
                </#list>
            </ul>
        </div>
        <div class="navigation">
            <#if prev?has_content> <a href="/user?handle=${prev.handle}" class="arrow">←</a>
                <#else> <a class="arrow" style="color: grey" >←</a> </#if>
            <#if next?has_content> <a href="/user?handle=${next.handle}" class="arrow">→</a>
                <#else> <a class="arrow" style="color: grey">→</a> </#if>
        </div>
<#--        <#list users as u>-->
<#--            <li>-->
<#--                <#if user?? && user.id == u.id>*</#if>-->
<#--                <@c.userlink u/>-->
<#--            </li>-->
<#--        </#list>-->
    <#else>
        No such user
    </#if>
</@c.page>

<#macro userlink user>
    <a href="/user?handle=${user.handle}">${user.name}</a>
</#macro>