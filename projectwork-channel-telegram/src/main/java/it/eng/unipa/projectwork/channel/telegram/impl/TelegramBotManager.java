package it.eng.unipa.projectwork.channel.telegram.impl;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import it.eng.unipa.projectwork.channel.telegram.TelegramChannelContainer;
import it.eng.unipa.projectwork.service.AuctionService;
import it.eng.unipa.projectwork.service.UserService;

@Singleton
@Startup
@DependsOn("TelegramChannelContainer")
public class TelegramBotManager  {
	
	
	@EJB
	AuctionService auctionService;
	
	@EJB
	UserService userService;
	
	@EJB
	TelegramChannelContainer telegramChannelContainer;
	
	private TelegramBotFunction t;
	
	@PostConstruct
	public void init() {
		ApiContextInitializer.init();
		//creazione dell oggetto telegrambotsapi
		TelegramBotsApi botsApi= new TelegramBotsApi();
		
		//registrazione bot
		try
		{
			botsApi.registerBot(new TelegramBotFunction(auctionService, userService, telegramChannelContainer));
		}
		catch(TelegramApiException e)
		{
			e.printStackTrace();
		}
	}


}
