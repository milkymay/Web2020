<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-vs-coronavirus-65.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if menu?? && menu == item.href>
                            <a href="${item.href}" class="current">${item.name}</a>
                            <#else>
                                <a href="${item.href}">${item.name}</a>
                        </#if>

                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
    <#list posts?reverse as post>
        <section>
            <div class="header">Post #${post.id}</div>
            <div class="body"><@post_text post true/></div>
            <div class="footer">
                <a href="/post?post_id=${post.id}">View all</a>
            </div>
        </section>
    </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2020 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly = false>
    <#if nameOnly>
        <a href="/user?handle=${user.handle}">${user.handle}</a>
    <#else>
        <a style="font-weight: bold; text-decoration: none; color: ${user.color}" href="/user?handle=${user.handle}">${user.handle}</a>
    </#if>
</#macro>

<#macro post_text post cut>
    <#if cut && post.text?length &gt; 250>
        <#assign text = post.text[0..250]+"...">
    <#else>
        <#assign text = post.text>
    </#if>
    ${text}
</#macro>

<#macro display_post post cut>
    <#assign user = findBy(users, "id", post.user_id)>
    <article>
        <div class="title">${post.title} #${post.id}</div>
        <div class="information">By <@userlink user/></div>
        <div class="body">
            <@post_text post cut/>
        </div>
    </article>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findNext items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return items[item?index + 1]/>
        </#if>
    </#list>
</#function>

<#function findPrev items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return items[item?index - 1]/>
        </#if>
    </#list>
</#function>