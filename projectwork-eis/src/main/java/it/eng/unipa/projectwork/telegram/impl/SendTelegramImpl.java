package it.eng.unipa.projectwork.telegram.impl;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import it.eng.unipa.projectwork.telegram.MessageTelegram;
import it.eng.unipa.projectwork.telegram.SendTelegram;
import it.eng.unipa.projectwork.telegram.exception.TelegramNotSendException;


@Stateless
public class SendTelegramImpl extends TelegramLongPollingBot implements SendTelegram {
	
	@Override
	public void sendTelegram(MessageTelegram message, String destination) throws TelegramNotSendException {
		try{
			
			 SendMessage sendMessageTelegram = new SendMessage(); //c'era mailsession
			 sendMessageTelegram.setChatId(destination)// destination = Chat_ID
	    	 .setText(message.getTextTelegram());
	    	 sendMessage(sendMessageTelegram); 
			
		}catch (Exception e) {
			throw new TelegramNotSendException(e);
		}
	}
	
	@Override
	public void sendTelegramAllUser(MessageTelegram message) throws TelegramNotSendException {
		/*try{
			QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
			QueueSession queueSession = queueConnection.createQueueSession(true, javax.jms.Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = queueSession.createProducer(queue);
			MapMessage mapMessage = queueSession.createMapMessage();
			mapMessage.setString("SUBJECT", message.getSubject());
			mapMessage.setString("BODY", message.getBody());
			mapMessage.setString("TYPE", message.getType().name());
			producer.send(mapMessage);
			queueSession.commit();
			queueSession.close();
			queueConnection.close();
		}catch (Exception e) {
			throw new MailNotSendException(e);
		}*/
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "AsteOnlineEngBot";
	}

	@Override
	public void onUpdateReceived(Update update) {
		 if (update.getMessage().getText().equals("/start")) 
			{
		    	 long chatIdTelegram= update.getMessage().getChat().getId();
		    	 String telegramUsername = update.getMessage().getChat().getUserName();
		 
		    	 System.out.println("Username: " + telegramUsername);
		    	 SendMessage welcomeMessage = new SendMessage();
		    	 welcomeMessage.setChatId(chatIdTelegram)
		    	 .setText("Benvenuto su AsteOnline Spa ");
		    	 
		    	 try
		    	 {
		    		 sendMessage(welcomeMessage);
		    	 }
		    	 catch(TelegramApiException e)
		    	 {
		    		 e.printStackTrace();
		    	 }
		    }
		
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "540859545:AAG79hoC16EZbYD4imqErquAiqM5Ih2kKuU";
	}
}

