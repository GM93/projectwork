package it.eng.unipa.projectwork.telegram;
import javax.ejb.Local;

import it.eng.unipa.projectwork.email.Message;
import it.eng.unipa.projectwork.telegram.exception.TelegramNotSendException;


@Local
public interface SendTelegram {
	public void sendTelegramAllUser(MessageTelegram message) throws TelegramNotSendException; // si pensa non utile 
	public void sendTelegram(MessageTelegram message,String destination) throws TelegramNotSendException;	
}