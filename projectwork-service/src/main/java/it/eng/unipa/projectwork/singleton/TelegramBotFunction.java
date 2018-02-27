package it.eng.unipa.projectwork.singleton;

import javax.ejb.Singleton;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class TelegramBotFunction extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		
		String[] TestoRicevuto=update.getMessage().getText().split(" ");
		
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
		 
		 
		 if (TestoRicevuto[0].equals("/rilancia")) 
			{
		    	 long chatIdTelegram= update.getMessage().getChat().getId();
		    	 String telegramUsername = update.getMessage().getChat().getUserName();
		 
		    	 
		    	 System.out.println("Username: " + telegramUsername);
		    	 SendMessage BidMessage = new SendMessage();
		    	 BidMessage.setChatId(chatIdTelegram)
		    	 .setText("Hai rilanciato: €"+TestoRicevuto[2] + " per l'asta: "+ TestoRicevuto[1]);
		    	 
		    	 
		    	 try
		    	 {
		    		 sendMessage(BidMessage);
		    	 }
		    	 catch(TelegramApiException e)
		    	 {
		    		 e.printStackTrace();
		    	 }
		    }
		 
		 
		 
	}
		
	@Override
	public String getBotUsername() {
		return  "AsteOnlineEngBot";
	}

	@Override
	public String getBotToken() {
		return "540859545:AAG79hoC16EZbYD4imqErquAiqM5Ih2kKuU";
	}

}
