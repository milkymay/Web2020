<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="event" type="ru.itmo.wp.model.domain.event.Event" -->
<#-- @ftlvariable name="talk" type="ru.itmo.wp.model.domain.talk.Talk" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.talk.Talk[]" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <form method="post" action="">
        <input type="hidden" name="action" value="send"/>
        <div class="field">
            <div class="name">
                <label for="text">Enter your message</label>
            </div>
            <div class="value">
                <input id="text" name="text" value="${text!}"/>
            </div>
            <#if error??>
                <div class="error">
                    ${error!}
                </div>
            </#if>
        </div>
        <div class="field">
            <div class="name">
                <label for="Receiver">To</label>
            </div>
            <div class="value">
                <label>
                    <select name="ReceiverLogin">
                        <#list users as u>
                            <option>${u.login}</option>
                        </#list>
                    </select>
                </label>
            </div>
        </div>
        <div class="button-field">
            <input type="submit" value="Send">
        </div>

    </form>

    <div class="datatable">
        <div class="caption">Message</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>From</th>
                <th>Message</th>
                <th>To</th>
                <th>When</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
                <tr>
                    <td>${talk.id}</td>
                    <td>${talk.sourceUserId}</td>
                    <td>${talk.text}</td>
                    <td>${talk.targetUserId}</td>
                    <td>${talk.creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>