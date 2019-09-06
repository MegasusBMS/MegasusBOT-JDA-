package MegasusBOT;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class leave {
	public leave(GuildMessageReceivedEvent event) {
		AudioManager am = event.getGuild().getAudioManager();
		TextChannel tc = event.getChannel();

		if (!am.isConnected()) {
			EmbedBuilder j = new EmbedBuilder();
			j.setTitle(":smiley: I'm not connected to a channel");
			j.setDescription("First i need to join one");
			tc.sendMessage(j.build()).queue();
			return;
		}

		VoiceChannel vc1 = am.getConnectedChannel();

		if (!vc1.getMembers().contains(event.getMember())) {
			EmbedBuilder j = new EmbedBuilder();
			j.setTitle(":sweat_smile: You have to be in the same voicechannel as me to use this");
			j.setDescription("Come to me if you can! :smiling_imp: ");
			tc.sendMessage(j.build()).queue();
			return;
		}
		am.closeAudioConnection();
		EmbedBuilder j = new EmbedBuilder();
		j.setTitle(":wave: Disconnected from your channel");
		j.setDescription("Bye !!!");
		tc.sendMessage(j.build()).queue();
	}
}
