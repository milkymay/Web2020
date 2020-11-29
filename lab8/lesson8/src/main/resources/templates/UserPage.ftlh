<#-- @ftlvariable name="pageUser" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="user">
    <#if pageUser??>
        <ul>
            <li>${pageUser.id}</li>
            <li>${pageUser.login}</li>
            <li>${pageUser.creationTime}</li>
        </ul>
    <#else>
        <td class="NoSuchUser">No such user</td>
    </#if>
    </div>
</@c.page>
