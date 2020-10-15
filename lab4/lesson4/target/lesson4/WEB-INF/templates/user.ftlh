<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle??>
        <#assign name = "handle", value = handle>
    <#else>
        <#if user_id?? && user_id?is_number>
            <#assign name = "id", value = user_id>
        </#if>
    </#if>

    <#assign u = c.findBy(users, name, value)!,
    next = c.findNext(users, name, value)!,
    prev = c.findPrev(users, name, value)!/>

    <#if u?has_content>
        <div class="user">
            <div class="name">
                <@c.userlink u/>
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
                <li>
                    posts: <a href="/posts?user_id=${u.id}">${posts?filter(x -> x.user_id == u.id)?size} </a>
                </li>
            </ul>
        </div>
        <div class="navigation">
            <#if prev?has_content> <a href="/user?handle=${prev.handle}" class="arrowActive">←</a>
            <#else> <a class="arrowPassive">←</a> </#if>
            <#if next?has_content> <a href="/user?handle=${next.handle}" class="arrowActive">→</a>
            <#else> <a class="arrowPassive">→</a> </#if>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
