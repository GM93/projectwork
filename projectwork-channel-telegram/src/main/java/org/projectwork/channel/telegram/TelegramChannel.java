package org.projectwork.channel.telegram;

import it.eng.unipa.projectwork.channel.AbstractChannel;
import it.eng.unipa.projectwork.channel.event.AuctionEvent;
import it.eng.unipa.projectwork.telegram.MessageTelegram;
import it.eng.unipa.projectwork.telegram.SendTelegram;
import it.eng.unipa.projectwork.telegram.exception.TelegramNotSendException;

public class TelegramChannel extends AbstractChannel{

	
	private String Chat_ID;
	
	public TelegramChannel(String username, String Chat_ID) {
		super(TelegramChannelContainer.TELEGRAM, username);
		this.Chat_ID = Chat_ID;
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
	
		
		String body ="New bid for Auction "+message.getAuctionOid() +"\n "
		+"Dear <b>"+getUsername()+"</b><br/>the auction <b>"+"</b> has a new event: <b style=\"color:red\">"+message.toJson()+"</b>";
		
		
		try {
			sendTelegram.sendTelegram(new MessageTelegram(body,this.Chat_ID), this.Chat_ID);
		} catch (TelegramNotSendException e) {
			e.printStackTrace();
		}
		
		System.out.println("-----> notifico TelegramBot a "+getUsername()+" "+message.toJson());
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj || ( obj instanceof TelegramChannel && this.Chat_ID.equals(((TelegramChannel)obj).Chat_ID));
	}
	
	@Override
	public int hashCode() {
		return this.Chat_ID.hashCode();
	}

}
