package MegasusBOT;

import Music.GuildMusicManager;
import Music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class stop {
	public stop(GuildMessageReceivedEvent event) {
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

		musicManager.scheduler.getQueue().clear();
		musicManager.player.stopTrack();
		musicManager.player.setPaused(false);

		event.getChannel().sendMessage("Stopping the player and clearing the queue").queue();
	}
}
