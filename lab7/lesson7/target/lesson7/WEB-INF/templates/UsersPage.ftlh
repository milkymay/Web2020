<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#assign isAdmin = false>
    <#if user?? && user.isAdmin()>
        <#assign isAdmin = true>
    </#if>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if isAdmin>
                    <th>Change admin status</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="userId">Loading..</td>
                    <td class="userLogin">Loading..</td>
                    <#if isAdmin>
                        <td class="userAdmin">
                            <div class="userAdminValue">Loading..</div>
                            <a href="#" class="changeUserAdmin">
                                Loading..
                            </a>
                        </td>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <td colspan=3>No Data</td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax({
                    action: "findAll"
                },
                function (response) {
                    response["users"].forEach(function (user) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find("tr").attr("userId", user["id"]);
                        $tr.find(".userId").text(user["id"]);
                        $tr.find(".userLogin").text(user["login"]);
                        $tr.find(".userAdminValue").text(user["admin"] ? "admin" : "usual");
                        $tr.find(".changeUserAdmin").text(user["admin"] ? "disable" : "enable");
                        $tr.find(".changeUserAdmin").attr("isAdmin", user["admin"]);
                        $tr.find(".changeUserAdmin").attr("id", user["id"]);
                        $(".users tbody").append($tr);
                    });
                    $(".userAdmin .changeUserAdmin").click(function () {
                        $(this).text("Changing..");
                        $(this).parent().find(".userAdminValue").text("Changing..");
                        ajax({
                                action: "changeStatus",
                                userId: $(this).attr("id"),
                                isAdmin: $(this).attr("isAdmin")
                            }, function (response) {
                                let user = response["changedUser"];
                                let isAdmin = user["admin"];
                                let id = user["id"];
                                let userAdminValue = isAdmin ? "admin" : "usual";
                                let changedUserAdmin = isAdmin ? "disable" : "enable";
                                let changedUser = $("tr[userId=" + id + "]");
                                changedUser.find(".userAdminValue").text(userAdminValue);
                                changedUser.find(".changeUserAdmin").text(changedUserAdmin);
                                changedUser.find(".changeUserAdmin").attr("isAdmin", isAdmin);
                            }, "/users"
                        );

                    })

                }, "/users");

        })
    </script>
</@c.page>
