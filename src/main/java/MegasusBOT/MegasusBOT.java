package MegasusBOT;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class MegasusBOT{
	
	public static JDA jda;
	public static String prefix = "$";
	
	public static void main(String[] args) throws LoginException {
		jda = new JDABuilder(AccountType.BOT).setToken("NjAzNDc2MzI0MTk1MjM3OTA4.XTgM-g.KiLoXDUzbPFllagtFkLOxm6ScU8").build();
		CommandClientBuilder b = new CommandClientBuilder();
		b.setPrefix("$");
		b.setOwnerId("305359668061011968");
		b.setStatus(OnlineStatus.ONLINE);
		CommandClient c = b.build();
		
		jda.addEventListener(c);
		jda.addEventListener(new commands());
	}
}