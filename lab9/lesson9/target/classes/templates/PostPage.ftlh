<#-- @ftlvariable name="commentForm" type="ru.itmo.wp.form.CommentForm" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post/>
        <div class="commentSection">
            <div class="header" style="font-weight: bold; color: cornflowerblue">Comment section</div>
            <#if userId??>
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
                    <div><a style="font-style: italic">${comment.user.login}:</a> ${comment.text}</div>
                </#list>
            </#if>
        </div>
    <#else>
        <td class="NoSuchPost">No such post</td>
    </#if>
</@c.page>
