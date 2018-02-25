package org.projectwork.channel.telegram;


import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import it.eng.unipa.projectwork.channel.AbstractChannelContainer;
import it.eng.unipa.projectwork.email.SendMail;
import it.eng.unipa.projectwork.model.User;
import it.eng.unipa.projectwork.service.UserService;
import it.eng.unipa.projectwork.telegram.SendTelegram;

@Singleton
@Startup
@DependsOn(value="MultiChannelContainer")
public class TelegramChannelContainer   extends AbstractChannelContainer<TelegramChannel> // da implementare 
{
	
	@EJB
	SendTelegram sendTelegram;
	
	@EJB
	UserService userService;
	
	public static final String TELEGRAM = "TELEGRAM";

	
	public TelegramChannelContainer() {}
	
	@Override
	public String getType() {
		return "TELEGRAM";
	}
	
	@Override
	protected void beforeSend(TelegramChannel t) {
		t.setTelegramSend(this.sendTelegram);
	}
	
	@Override
	protected void afterSend(TelegramChannel t) {
		t.setTelegramSend(null);
	}
	
	@Override
	public void add(String username, Long auctionOid) {
		User user = userService.getUser(username);
		String chat_ID = user.getChat_ID();
		super.add(new TelegramChannel(username, chat_ID));
		super.add(username, auctionOid);
	}
}
