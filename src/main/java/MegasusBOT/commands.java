package MegasusBOT;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class commands extends ListenerAdapter{
	public String arg;
	public static GuildMessageReceivedEvent e;
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		e = event;
		String [] args = event.getMessage().getContentRaw().split("\\s+");
		List<String> msg = new ArrayList<String>();
		for(int i = 0; i<args.length; i++){
			msg.add(args[i]);
		}
		if(event.getGuild().getSelfMember()!=event.getMember())
		if(args[0].contains(MegasusBOT.prefix)){
			String command = args[0].substring(1);
			switch (command){
			case "clear":
				new clear(args,event);
				break;
			case "status":
				new status(event);
				break;
			case "ban":
				new ban(msg,event);
				break;
			case "leave":
				new leave(event);
				break;
			case "join":
				new join(event);
				break;
			case "volume":
				new volume(args,event);
				break;
			case "queue":
				new queue(event);
				break;
			case "stop":
				new stop(event);
				break;
			case "play":
				new Play(args,event,true);
				break;
			case "skip":
				new skip(msg,event);
				break;
			case "unban":
				new unban(msg,event);
				break;
			case "kick":
				new kick(msg,event);
				break;
			case "repeat":
				new repeat(args,event);
				break;
			case "np":
				new NowPlay(msg,event);
				break;
			case "nowplay":
				new NowPlay(msg,event);
				break;
			case "support":
				EmbedBuilder support = new EmbedBuilder();
				support.setTitle(":hugging: Support");
				event.getChannel().sendMessage(support.build()).queue();
				event.getChannel().sendMessage("https://discord.gg/B76kauM").queue();
				break;
			default :
				new help(event);
				break;
			}
		}
	}
}