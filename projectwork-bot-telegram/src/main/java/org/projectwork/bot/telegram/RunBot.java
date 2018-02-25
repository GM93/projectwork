package org.projectwork.bot.telegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import it.eng.unipa.projectwork.telegram.impl.SendTelegramImpl;

public class RunBot  
{
    public static void main( String[] args )
    {
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
    }
}
