package MegasusBOT;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Play{
    private final YouTube YouTube;
    public static String input;
    public static boolean b;
    public Play(String[] args, GuildMessageReceivedEvent event, boolean bo) {
    	b=bo;
        YouTube temp = null;
        try {
            temp = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    null
            )
                    .setApplicationName("MegasusBOT JDA bodt")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        YouTube = temp;

        TextChannel channel = event.getChannel();

        if (args.length<2) {
        	EmbedBuilder play=new EmbedBuilder();
        	play.setTitle(":upside_down: Please provide some arguments");
        	play.setDescription(MegasusBOT.prefix+"play [url/yt_id/title/thumbnails]");
            channel.sendMessage(play.build()).queue();

            return;
        }
        String imput = "";
        for(int i=1;i<args.length;i++){
        	imput= imput + args[i] + " ";
        }
        input = imput;

        if (!isUrl(input)) {
            String ytSearched = searchYoutube(input);

            if (ytSearched == null) {
            	EmbedBuilder play=new EmbedBuilder();
            	play.setTitle(":thinking: I don't find this input");
            	play.setDescription("Are you sure , this is what you want to play?");
                channel.sendMessage(play.build()).queue();

                return;
            }

            input = ytSearched;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(event.getChannel(), input);
    }

	private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Nullable
    private String searchYoutube(String input) {
        try {
            List<SearchResult> results = YouTube.search()
                    .list("id,snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey("AIzaSyBXqUY-vhlfNBzKTsu9UTMH7QvX2ErNSmQ")
                    .execute()
                    .getItems();

            if (!results.isEmpty()) {
                String videoId = results.get(0).getId().getVideoId();

                return "https://www.youtube.com/watch?v=" + videoId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}