package MegasusBOT;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

public class role {

	public role(String[] args, GuildMessageReceivedEvent event) {
		if (args.length < 4) {
			EmbedBuilder usage = new EmbedBuilder();
			usage.setTitle(":sweat_smile: Need more arguments!");
			usage.setDescription("Usage: " + MegasusBOT.prefix + "role (member) give/remove <role>");
			event.getChannel().sendMessage(usage.build()).queue();
		} else {
			List<Role> r = event.getGuild().getRoles();
			List<Member> m = event.getGuild().getMembers();
			Member t = null;
			int ok = 0;
			for (int i = 0; i < m.size(); i++) {
				if (args[1].equals(m.get(i).getUser().getAsMention())
						|| args[1].equals(m.get(i).getUser().getAsTag())) {
					t = m.get(i);
					ok = 1;
				}
			}
			if (ok == 0) {
				EmbedBuilder usa = new EmbedBuilder();
				usa.setTitle(":sweat_smile: Member not found!");
				event.getChannel().sendMessage(usa.build()).queue();
			}
			String role = null;
			for (int i = 3; i < args.length; i++) {
				role = role + args[i];
			}
			int rol = 0;
			if (r.size() > 0) {
				for (int i = 0; i < r.size(); i++) {
					if (r.get(i).getName().equalsIgnoreCase(role)) {
						rol = i;
					}
				}
			}
			int has = 0;
			if (t.getRoles().size() > 0) {
				for (int i = 0; i < t.getRoles().size(); i++) {
					if (t.getRoles().get(i) == r.get(i)) {
						has = 1;
						return;
					}
				}
			}
			if (!event.getMember().isOwner()) {
				if (!event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
					EmbedBuilder usage = new EmbedBuilder();
					usage.setTitle(":x: You don't have acces to give/remove roles");
					event.getChannel().sendMessage(usage.build()).queue();
					return;
				}
				if (t.hasPermission(Permission.MANAGE_ROLES)) {
					EmbedBuilder usage = new EmbedBuilder();
					usage.setTitle(":x: You can't give/remove role to this member");
					event.getChannel().sendMessage(usage.build()).queue();
					return;
				}
				if (rol == 0) {
					EmbedBuilder error = new EmbedBuilder();
					error.setTitle(":x:Error");
					event.getChannel().sendMessage(error.build()).queue();
					return;
				}
			}
			if (args[2].equalsIgnoreCase("give")) {
				if (has == 1) {
					EmbedBuilder usage = new EmbedBuilder();
					usage.setTitle(":sweat_smile: The member have this role");
					event.getChannel().sendMessage(usage.build()).queue();
					return;
				}
				try {
					event.getGuild().addRoleToMember(t, r.get(rol)).queue();
					EmbedBuilder usage = new EmbedBuilder();
					usage.setTitle(":white_check_mark: You gived the role (" + role + ") to " + t.getAsMention());
					event.getChannel().sendMessage(usage.build()).queue();
				} catch (HierarchyException e) {
					EmbedBuilder usage = new EmbedBuilder();
					usage.setTitle(":x: You can't give/remove role to this member or your self");
					event.getChannel().sendMessage(usage.build()).queue();
				}
				return;
			}
			if (args[2].equalsIgnoreCase("remove")) {
				if (has == 0) {
					EmbedBuilder usage = new EmbedBuilder();
					usage.setTitle(":sweat_smile: The member don't have this role");
					event.getChannel().sendMessage(usage.build()).queue();
					return;
				}
				try {
					event.getGuild().removeRoleFromMember(t, r.get(rol));
					EmbedBuilder usag = new EmbedBuilder();
					usag.setTitle(":white_check_mark: You removed the role (" + role + ") at " + t.getAsMention());
					event.getChannel().sendMessage(usag.build()).queue();
				} catch (HierarchyException e) {
					EmbedBuilder usage = new EmbedBuilder();
					usage.setTitle(":x: You can't give/remove role to this member or your self");
					event.getChannel().sendMessage(usage.build()).queue();
				}
				return;
			}
		}
	}
}
