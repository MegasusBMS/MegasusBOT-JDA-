package MegasusBOT;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class clear{
		public clear(String[] args, GuildMessageReceivedEvent event) {

		if (args[0].equalsIgnoreCase(MegasusBOT.prefix + "clear")) {
			if (args.length < 2) {
				EmbedBuilder usage = new EmbedBuilder();
				usage.setTitle(":sweat_smile: Specify amount to delete");
				usage.setDescription("Usage: " + MegasusBOT.prefix + "clear [# of messages]");
				event.getChannel().sendMessage(usage.build()).queue();
			} else {
				if (Integer.parseInt(args[1]) > 1) {
					try {
						List<Message> Messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1]))
								.complete();
						event.getChannel().deleteMessages(Messages).queue();
						EmbedBuilder succes = new EmbedBuilder();
						succes.setTitle(":white_check_mark: Successfully deleted " + args[1] + " messages.");
						event.getChannel().sendMessage(succes.build()).queue();
					} catch (IllegalArgumentException e) {
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xB10308);
						if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
							error.setTitle(":red_circle:  Too many messages selected!");
							error.setDescription("Between: 2-100 messages can be deleted at one time!!!");
							event.getChannel().sendMessage(error.build()).queue();
						} else {
							error.setTitle(":red_circle: Selected messages are older than 2 weeks!");
							error.setDescription("Messages are older than 2 weeks can't be deleted!!!");
							event.getChannel().sendMessage(error.build()).queue();
						}
					}
				} else {
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle(":red_circle:  Minimum 2 messages can be deleted!");
					error.setDescription("Between: 2-100 messages can be deleted at one time!!!");
					event.getChannel().sendMessage(error.build()).queue();
				}
			}
		}
	}
}
