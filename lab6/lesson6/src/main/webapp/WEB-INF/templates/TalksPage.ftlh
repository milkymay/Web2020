<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="event" type="ru.itmo.wp.model.domain.event.Event" -->
<#-- @ftlvariable name="talk" type="ru.itmo.wp.model.domain.talk.Talk" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.talk.Talk[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <form method="post" action="">
        <input type="hidden" name="action" value="send"/>
        <div class="field">
            <div class="name">
                <label for="message">Enter your message</label>
            </div>
            <div class="value">
                <input id="message" name="message" value="${message!}"/>
            </div>
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
                <#if  user?? && (user.id = talk.sourceUserId) || (user.id = talk.targetUserId)>
                <tr>
                    <td>${talk.id}</td>
                    <td>${talk.sourceUserId}</td>
                    <td>${talk.text}</td>
                    <td>${talk.targetUserId}</td>
                    <td>${talk.creationTime}</td>
                </tr>
                </#if>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>