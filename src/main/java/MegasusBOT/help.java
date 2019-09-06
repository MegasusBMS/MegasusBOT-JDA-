package MegasusBOT;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class help extends ListenerAdapter {
	public help(GuildMessageReceivedEvent event) {
		EmbedBuilder help = new EmbedBuilder();
		help.setTitle(":sos: Help!");
		help.setDescription(
				  "Channel: clear .\n"
				+ "Administrative: status , role , unban , ban .\n"
				+ "Music: join , leave , play , stop , volume , skip .\n"
				+ "Support: support ."
				);
		event.getChannel().sendMessage(help.build()).queue();
	}
}
