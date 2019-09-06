package MegasusBOT;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class status extends ListenerAdapter {
	public status(GuildMessageReceivedEvent event) {
	int u = event.getGuild().getMembers().size();
	int c = event.getGuild().getChannels().size();
	int v = event.getGuild().getVoiceChannels().size();
	int b = event.getGuild().retrieveBanList().complete().size();
	Region r = event.getGuild().getRegion();
	int o = 0;
	List<Member> m = new ArrayList<>();m=event.getGuild().getMembers();for(
	int i = 0;i<u;i++)
	{
		if (((m.get(i).getOnlineStatus()) == OnlineStatus.ONLINE)
				|| ((m.get(i).getOnlineStatus()) == OnlineStatus.DO_NOT_DISTURB)
				|| ((m.get(i).getOnlineStatus()) == OnlineStatus.IDLE)) {
			o++;
		}
	}
	EmbedBuilder status = new EmbedBuilder();status.setColor(0xff3923);status.setTitle(":thinking: Status");status.setDescription("Members: "+u+"          Online: "+o+"\n\nChannels: "+c+"          VoiceChannels: "+v+"\n\nBanned: "+b+"          Region: "+r);event.getChannel().sendMessage(status.build()).queue();
}
}
