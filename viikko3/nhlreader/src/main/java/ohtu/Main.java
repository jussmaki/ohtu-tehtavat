package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.client.fluent.Request;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players";
        
        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        Player[] players = mapper.fromJson(bodyText, Player[].class);

        System.out.print("Players from FIN ");
        System.out.println(new SimpleDateFormat("E MMM d HH:mm:ss z y").format(new Date()));
        System.out.println("");

        for (Player player : players) {
            if (player.getNationality().equals("FIN")) {
                System.out.println(player);
            }
        }   
    }
  
}
