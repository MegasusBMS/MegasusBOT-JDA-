package MegasusBOT;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Music.GuildMusicManager;
import Music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class skip {

	public skip(List<String> msg, GuildMessageReceivedEvent event) {
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		TextChannel channel = event.getChannel();
		AudioPlayer player = musicManager.player;

		if (player.getPlayingTrack() == null) {
			channel.sendMessage("Nothing to skip").queue();
			return;
		}
		if(repeat.repeatsong==true){
			channel.sendMessage("First stop the repeat!!!!!").queue();
			return;
		}
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();
		if (queue.size() == 0) {
			channel.sendMessage("Nothing to skip").queue();
			return;
		}
		player.getPlayingTrack().setPosition(player.getPlayingTrack().getDuration() - 1);
		channel.sendMessage("Skipping...").queue();
	}

}
