<#-- @ftlvariable name="commentForm" type="ru.itmo.wp.form.CommentForm" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post/>
        <#if userId??>
            <div class="commentSection">
            <div class="header">Comment section</div>
            <div class="body">
                <form method="post" action="/post/${post.id}">
                    <div class="field">
                        <div class="name">
                            <label for="text">Type your comment here</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text">${commentForm.text!}</textarea>
                        </div>
                    </div>
                    <@c.error "commentForm.text"/>
                    <div class="button-field">
                        <input type="submit" value="Add">
                    </div>
                </form>
            </div>
        </#if>
        <#if post.comments?has_content>
            <#list post.comments as comment>
                <div>${comment.user.login}: ${comment.text}</div>
            </#list>
        </#if>
    <#else>
        <td class="NoSuchPost">No such post</td>
    </#if>
</@c.page>
