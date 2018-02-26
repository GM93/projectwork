package org.projectwork.channel.telegram;


import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import it.eng.unipa.projectwork.channel.AbstractChannelContainer;
import it.eng.unipa.projectwork.email.SendMail;
import it.eng.unipa.projectwork.model.User;
import it.eng.unipa.projectwork.service.UserService;
import it.eng.unipa.projectwork.telegram.SendTelegram;
import it.eng.unipa.projectwork.telegram.impl.SendTelegramImpl;


@Singleton
@Startup
@DependsOn(value="MultiChannelContainer")
public class TelegramChannelContainer   extends AbstractChannelContainer<TelegramChannel> // da implementare 
{
	
	@EJB
	SendTelegram sendTelegram;
	
	@EJB
	UserService userService;
	/*sul telegram chnnel container sovraschiviamo questo metodo richiamando il metodo padre e mettendo il pezzo codice per avviare il bot e ci dobbiamo ricordare di e la dobbiamo riannotare come ON start. all avvio si registra sul multicanale e ci avvia il bot
	poi ci spiaega come inviare i mex sul canale
	
	sarebe fare un altra classe che si chiama singleton che si chiama recieve , al posto di telegramchannelcontainer e quando ci arrivano i messaggi facciamo sostanzialmente appendmessage sul topic, 
	poi ci spiega come poterlo isolare per condividere gli event via email via browser e cosi via, l idea è se faccio un offerta via bot teelgram deve mandare la notifica via email e via browser e questo si fa mettendo il messaggio nel topic listener che ascolta.
	*/
	@Override
	
	public void register() {
		
		//inizializziamo le api
				ApiContextInitializer.init();
				//creazione dell oggetto telegrambotsapi
				TelegramBotsApi botsApi= new TelegramBotsApi();
				
				//registrazione bot
				try
				{
					botsApi.registerBot(new SendTelegramImpl());
				}
				catch(TelegramApiException e)
				{
					e.printStackTrace();
				}
		
		// TODO Auto-generated method stub
		super.register();
	}
	
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
		String chat_ID = user.getChatId();
		super.add(new TelegramChannel(username, chat_ID));
		super.add(username, auctionOid);
	}
}
