package Music;

import java.util.HashMap;
import java.util.Map;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import MegasusBOT.Play;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayerManager {
	private static PlayerManager INSTANCE;
	private final AudioPlayerManager playerManager;
	private final Map<Long, GuildMusicManager> musicManagers;

	private PlayerManager() {
		this.musicManagers = new HashMap<>();

		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}

	public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
		long guildId = guild.getIdLong();
		GuildMusicManager musicManager = musicManagers.get(guildId);

		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			musicManagers.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
	}

	public void loadAndPlay(final TextChannel channel, final String trackUrl) {
		final GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

		playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
			public void trackLoaded(AudioTrack track) {
				if(Play.b){
				EmbedBuilder pm = new EmbedBuilder();
				pm.setTitle(":musical_note: Adding to queue: ");
				pm.setDescription(track.getInfo().title + "\n");
				channel.sendMessage(pm.build()).queue();
				}
				play(musicManager, track);
			}

			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();

				if (firstTrack == null) {
					firstTrack = playlist.getTracks().remove(0);
				}
				EmbedBuilder pm = new EmbedBuilder();
				pm.setTitle(":musical_note: Adding to queue: ");
				pm.setDescription(firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")");
				channel.sendMessage(pm.build()).queue();
				play(musicManager, firstTrack);

				playlist.getTracks().forEach(musicManager.scheduler::queue);
			}

			public void noMatches() {
				EmbedBuilder pm = new EmbedBuilder();
				pm.setTitle(":cry: Something went rong!");
				pm.setDescription("Nothing found");
				channel.sendMessage(pm.build()).queue();
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				EmbedBuilder pm = new EmbedBuilder();
				pm.setTitle(":cry: Something went rong!");
				pm.setDescription("Could not play: " + exception.getMessage());
				channel.sendMessage(pm.build()).queue();
				channel.sendMessage("Could not play: " + exception.getMessage()).queue();
			}
		});

	}

	private void play(GuildMusicManager musicManager, AudioTrack track) {
		musicManager.scheduler.queue(track);
	}

	public static synchronized PlayerManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PlayerManager();
		}

		return INSTANCE;
	}
}
