package it.eng.unipa.projectwork.channel.telegram;

import it.eng.unipa.projectwork.channel.AbstractChannel;
import it.eng.unipa.projectwork.channel.event.AuctionEvent;
import it.eng.unipa.projectwork.telegram.MessageTelegram;
import it.eng.unipa.projectwork.telegram.SendTelegram;
import it.eng.unipa.projectwork.telegram.exception.TelegramNotSendException;


public class TelegramChannel extends AbstractChannel{

	
	private long chatID;
	
	public TelegramChannel(String username, long chatID) {
		super(TelegramChannelContainer.TELEGRAM, username);
		this.chatID = chatID;
	}

	private SendTelegram sendTelegram;
	
	protected void setTelegramSend(SendTelegram sendTelegram) {
		this.sendTelegram = sendTelegram;
	}
	
	@Override
	public void sendAuctionEvent(AuctionEvent message) {
		if(sendTelegram==null){
			throw new RuntimeException("sendTelegram is null");
		}
	
		
		String body = "Ciao " + getUsername() + "!!!\nNuova offerta di €" + message.getPrezzo() + " per l'asta " + message.getAuctionOid() +"\n";
		
		
		try {
			sendTelegram.sendTelegram(new MessageTelegram(body), this.chatID);
		} catch (TelegramNotSendException e) {
			e.printStackTrace();
		}
		
		System.out.println("-----> notifico TelegramBot a "+getUsername()+" "+message.toJson());
	}

	@Override
	public boolean isOpen() {
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj || ( obj instanceof TelegramChannel && this.chatID==(((TelegramChannel)obj).chatID));
	}
	
	@Override
	public int hashCode() {
		return new Long(this.chatID).hashCode();
	}

}
