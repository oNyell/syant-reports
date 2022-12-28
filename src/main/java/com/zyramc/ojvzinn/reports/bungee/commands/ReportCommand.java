package com.zyramc.ojvzinn.reports.bungee.commands;

import com.zyramc.ojvzinn.reports.bungee.reports.ReportManager;
import dev.syantmc.pewd.player.role.Role;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ReportCommand extends Commands {

    private static final SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yy 'às' hh:mm");

    public ReportCommand() {
        super("report", "reportar");
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length < 1) {
            player.sendMessage(TextComponent.fromLegacyText("§cUse \"/reportar <jogador>\" para denunciar um hacker."));
            return;
        }
        String target = args[0];

        if (args.length > 1) {
            String motivo = args[1];
            Date date = new Date();
            sf.setTimeZone(TimeZone.getTimeZone("GMT-3"));
            String data = sf.format(date);
            ReportManager.createReport(Role.getColored(target), Role.getColored(player.getName()), data, motivo);
        } else {
            Date date = new Date();
            sf.setTimeZone(TimeZone.getTimeZone("GMT-3"));
            String data = sf.format(date);
            ReportManager.createReport(Role.getColored(target), Role.getColored(player.getName()), data, "Não informado");
        }

        player.sendMessage(TextComponent.fromLegacyText(" "));
        player.sendMessage(TextComponent.fromLegacyText("§a* Você reportou o jogador " + Role.getPrefixed(target) + "§a. Um membro de nossa equipe §afoi notificado e o comportamento deste jogador §aserá analisado em breve.\n\n §a* O uso abusivo deste comando poderá §aresultar em punição."));
        player.sendMessage(TextComponent.fromLegacyText(" "));
    }
}
