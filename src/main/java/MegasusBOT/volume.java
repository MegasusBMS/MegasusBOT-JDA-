package MegasusBOT;

import Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class volume {

	public volume(String[] args, GuildMessageReceivedEvent event) {
		PlayerManager mm = PlayerManager.getInstance();
		try{
			
		int volum = Integer.parseInt(args[1]);

		EmbedBuilder v = new EmbedBuilder();
		if(volum>20&&!event.getMember().hasPermission(Permission.ADMINISTRATOR)){
			v.setTitle(":cry: Maxim volume for you is 20");
			event.getChannel().sendMessage(v.build()).queue();
			return;
		}else{
			if(volum>100){
				event.getChannel().sendMessage("Maxim volume is 100").queue();
				v.setTitle(":ear: :boom: Maxim volume 100");
				v.setDescription("Trust me you don't want more then 100");
				event.getChannel().sendMessage(v.build()).queue();
				return;
			}
		}
		if(volum<0){
			v.setTitle(":x: Volume can be set from 1 to 20");
			event.getChannel().sendMessage(v.build()).queue();
			return;
		}
		mm.getGuildMusicManager(event.getGuild()).player.setVolume(volum);
		v.setTitle(":white_check_mark: Volume set to: "+ volum);
		event.getChannel().sendMessage(v.build()).queue();
		}catch(NumberFormatException e){
			EmbedBuilder v = new EmbedBuilder();
			v.setTitle(":cry: Something went rong!");
			v.setDescription(args[1]+ " is not a number");
			event.getChannel().sendMessage(v.build()).queue();
			return;
		}
	}
}
