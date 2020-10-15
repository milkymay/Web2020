package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;
import ru.itmo.web.lesson4.model.UserColor;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", UserColor.RED),
            new User(6, "pashka", "Pavel Mavrin", UserColor.BLUE),
            new User(9, "geranazarov555", "Georgiy Nazarov", UserColor.RED),
            new User(11, "tourist", "Gennady Korotkevich", UserColor.GREEN)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "POST1", "This round is held on the tasks of the school stage All-Russian Olympiad of Informatics 2018/2019\n" +
                    "                    year in city Saratov. The problems were prepared by PikMike, fcspartakm, Ne0n25, BledDest, Ajosteen\n" +
                    "                    and Vovuh. Great thanks to our coordinator _kun_ for the help with the round preparation! I also\n" +
                    "                    would like to thank our testers DavidDenisov, PrianishnikovaRina, Decibit and Vshining", 1),
            new Post(2, "POST2", "This round is held on the tasks of the school stage All-Russian Olympiad of Informatics 2018/2019\n" +
                    "                    year in city Saratov. The problems were prepared by PikMike, fcspartakm, Ne0n25, BledDest, Ajosteen\n" +
                    "                    and Vovuh. Great thanks to our coordinator _kun_ for the help with the round preparation! I also\n" +
                    "                    would like to thank our testers DavidDenisov, PrianishnikovaRina, Decibit and Vshining", 6),
            new Post(3, "POST3", "This round is held on the tasks of the school stage All-Russian Olympiad of Informatics 2018/2019\n" +
                    "                    year in city Saratov. The problems were prepared by PikMike, fcspartakm, Ne0n25, BledDest, Ajosteen\n" +
                    "                    and Vovuh. Great thanks to our coordinator _kun_ for the help with the round preparation! I also\n" +
                    "                    would like to thank our testers DavidDenisov, PrianishnikovaRina, Decibit and Vshining", 9),
            new Post(4, "POST4", "This round is held on the tasks of the school stage All-Russian Olympiad of Informatics 2018/2019\n" +
                    "                    year in city Saratov. The problems were prepared by PikMike, fcspartakm, Ne0n25, BledDest, Ajosteen\n" +
                    "                    and Vovuh. Great thanks to our coordinator _kun_ for the help with the round preparation! I also\n" +
                    "                    would like to thank our testers DavidDenisov, PrianishnikovaRina, Decibit and Vshining", 11)
    );


    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }

        data.put("menu", request.getRequestURI());
    }
}

