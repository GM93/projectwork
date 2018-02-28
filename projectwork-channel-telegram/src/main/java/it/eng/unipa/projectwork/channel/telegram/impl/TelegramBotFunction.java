package it.eng.unipa.projectwork.channel.telegram.impl;

import java.math.BigDecimal;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import it.eng.unipa.projectwork.channel.telegram.TelegramChannel;
import it.eng.unipa.projectwork.channel.telegram.TelegramChannelContainer;
import it.eng.unipa.projectwork.model.User;
import it.eng.unipa.projectwork.service.AuctionService;
import it.eng.unipa.projectwork.service.UserService;

public class TelegramBotFunction extends TelegramLongPollingBot {
	
	
	private AuctionService auctionService;
	
	private UserService userService;
	
	private TelegramChannelContainer telegramChannelContainer;

	
	public TelegramBotFunction(AuctionService auctionService,UserService userService,TelegramChannelContainer telegramChannelContainer) {
		this.auctionService = auctionService;
		this.userService = userService;
		this.telegramChannelContainer = telegramChannelContainer;
	}
	
	
	
	@Override
	public void onUpdateReceived(Update update) {
		
		String[] testoRicevuto=update.getMessage().getText().split(" ");
		
		if (update.getMessage().getText().equals("/start")) 
			{
		    	 
			
				
			
				long chatIdTelegram= update.getMessage().getChat().getId();
		    	String telegramUsername = update.getMessage().getChat().getUserName();
		    	
		    	User user = userService.getUserFromUsernameTelegram(telegramUsername);
		    	
		    	telegramChannelContainer.add(new TelegramChannel(user.getUsername(), chatIdTelegram));
		    	
		    	 System.out.println("Username: " + telegramUsername);
		    	 SendMessage welcomeMessage = new SendMessage();
		    	 welcomeMessage.setChatId(chatIdTelegram)
		    	 .setText("Benvenuto su AsteOnline Spa\nConnessione avviata con successo!");

		    	 try
		    	 {
		    		 sendMessage(welcomeMessage);
		    	 }
		    	 catch(TelegramApiException e)
		    	 {
		    		 e.printStackTrace();
		    	 }
		    }
		else if (testoRicevuto[0].equals("/osserva")) {
			long chatIdTelegram= update.getMessage().getChat().getId();
	    	 String usernameTelegram = update.getMessage().getChat().getUserName();
	    	 User user = userService.getUserFromUsernameTelegram(usernameTelegram);
		    telegramChannelContainer.add(user.getUsername(),new Long(testoRicevuto[1]));
			
			 SendMessage replyMessage = new SendMessage();
			 replyMessage.setChatId(chatIdTelegram)
	    	 .setText("Stai osservando le offerte sull'asta con ID "+ testoRicevuto[1]);
			
		   	 try
	    	 {
	    		 sendMessage(replyMessage);
	    	 }
	    	 catch(TelegramApiException e)
	    	 {
	    		 e.printStackTrace();
	    	 }
			
			
		}
		 
		else if (testoRicevuto[0].equals("/rilancia")) 
			{
			 
			 
		    	 long chatIdTelegram= update.getMessage().getChat().getId();
		    	 String telegramUsername = update.getMessage().getChat().getUserName();
		 
		    
		    	 //TODO implementare una politica dicontrollo, se il prezzo inserito è minore di quello corrente (anche il Version), deve inviare un messaggio di errore
		    	 System.out.println("Username: " + telegramUsername);
		    	 SendMessage bidMessage = new SendMessage();
		    	 String bid = testoRicevuto[2];
				 String oidAuction = testoRicevuto[1];
				 
				boolean esito = addBid(oidAuction, telegramUsername, bid);
				 if(esito) {
					 bidMessage.setChatId(chatIdTelegram).setText("Hai rilanciato €"+bid + " per l'asta con ID  "+ oidAuction);
				 }else {
					 bidMessage.setChatId(chatIdTelegram).setText("Errore nell'offerta");
				 }
		    	 
		    	 
		    	 try
		    	 {
		    		 sendMessage(bidMessage);
		    		 
		    	 }
		    	 catch(TelegramApiException e)
		    	 {
		    		 e.printStackTrace();
		    	 }
		    }
		else if (testoRicevuto[0].equals("/stop")) 
		{
	    	 
		
			
		
			long chatIdTelegram= update.getMessage().getChat().getId();
	    	String telegramUsername = update.getMessage().getChat().getUserName();
	    	
	    	User user = userService.getUserFromUsernameTelegram(telegramUsername);
	    	
	    	telegramChannelContainer.remove(new TelegramChannel(user.getUsername(), chatIdTelegram));
	    	
	    	 System.out.println("Username: " + telegramUsername);
	    	 SendMessage stopMessage = new SendMessage();
	    	 stopMessage.setChatId(chatIdTelegram)
	    	 .setText("Hai interrotto la comunicazione con successo!  ");
	    	 
	    	 try
	    	 {
	    		 sendMessage(stopMessage);
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
	
	
	private boolean addBid(String oidAuction,String usernameTelegram,String bidS) {
		try {
			User user = userService.getUserFromUsernameTelegram(usernameTelegram);
			if(user!=null) {
				auctionService.addBid(Long.parseLong(oidAuction), user.getUsername(), new BigDecimal(bidS));
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public String getBotToken() {
		return "540859545:AAG79hoC16EZbYD4imqErquAiqM5Ih2kKuU";
	}

}
