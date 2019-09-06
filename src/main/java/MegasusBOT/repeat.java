package MegasusBOT;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import Music.GuildMusicManager;
import Music.PlayerManager;
import Music.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class repeat {
	public static boolean repeatsong;
	public boolean problem;
	public static String[] song;

	public repeat(String[] args, GuildMessageReceivedEvent event) {
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		TrackScheduler scheduler = musicManager.scheduler;
		AudioPlayer player = musicManager.player;
		EmbedBuilder r = new EmbedBuilder();
		if (args.length == 1) {
			r.setTitle(":upside_down: Please provide some arguments");
			r.setDescription(MegasusBOT.prefix + "repeat [queue/song] (on/off)");
			event.getChannel().sendMessage(r.build()).queue();
			return;
		}
		if (args.length == 2) {
			if (args[1].equals("song")) {
				if (player.getPlayingTrack() == null) {
					commands.e.getChannel().sendMessage("The player is not playing any song.").queue();

					return;
				}
				repeatsong = repeatsong ? false : true;
			}
		}
		if (args.length > 2) {
			if (args[1].equals("song")) {
				if (player.getPlayingTrack() == null) {
					commands.e.getChannel().sendMessage("The player is not playing any song.").queue();

					return;
				}
				repeatsong = args[2].equalsIgnoreCase("on") ? true
						: args[2].equalsIgnoreCase("off") ? false : wrong(event, repeatsong);
				if (problem) {
					problem = false;
					return;
				}
			}

			if (repeatsong)
				r.setTitle(":repeat_one: Repeat song : ON");
			else
				r.setTitle(":repeat_one: Repeat song : OFF");
			event.getChannel().sendMessage(r.build()).queue();
		} else {
			if (args[1] == "song")
				if (args[2] == "on") {
					repeatsong = true;
				} else {
					repeatsong = false;
				}
		}
		if (repeatsong) {
			scheduler.queueclear();
			song = (MegasusBOT.prefix + "play " + player.getPlayingTrack().getInfo().title).split(" ");
			new Play(song, commands.e, false);
		} else {
			scheduler.reloadqueue();
		}
	}

	private boolean wrong(GuildMessageReceivedEvent event, boolean returned) {
		EmbedBuilder r = new EmbedBuilder();
		r.setTitle(":rolling_eyes: Pls insert ON or OFF");
		r.setDescription(MegasusBOT.prefix + "repeat [queue/song] (on/off)");
		event.getChannel().sendMessage(r.build()).queue();
		problem = true;
		return returned;
	}

}
