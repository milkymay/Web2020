<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign u = c.findBy(users, "handle", handle)!/>

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
            <a href="/ser?handle?" class="arrow">←</a>
            <a href="/ser?handle?" class="arrow">→</a>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>

<#macro userlink user>
    <a href="/user?handle=${user.handle}">${user.name}</a>
</#macro>