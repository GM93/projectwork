package it.eng.unipa.projectwork.channel.telegram;


import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import it.eng.unipa.projectwork.channel.AbstractChannelContainer;
import it.eng.unipa.projectwork.telegram.SendTelegram;


@Singleton(name="TelegramChannelContainer")
@Startup
@DependsOn(value="MultiChannelContainer")
public class TelegramChannelContainer   extends AbstractChannelContainer<TelegramChannel> // da implementare 
{
	
	@EJB
	SendTelegram sendTelegram;
	
	/*sul telegram chnnel container sovraschiviamo questo metodo richiamando il metodo padre e mettendo il pezzo codice per avviare il bot e ci dobbiamo ricordare di e la dobbiamo riannotare come ON start. all avvio si registra sul multicanale e ci avvia il bot
	poi ci spiaega come inviare i mex sul canale
	
	sarebe fare un altra classe che si chiama singleton che si chiama recieve , al posto di telegramchannelcontainer e quando ci arrivano i messaggi facciamo sostanzialmente appendmessage sul topic, 
	poi ci spiega come poterlo isolare per condividere gli event via email via browser e cosi via, l idea è se faccio un offerta via bot teelgram deve mandare la notifica via email e via browser e questo si fa mettendo il messaggio nel topic listener che ascolta.
	*/
	
	
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
	
}
