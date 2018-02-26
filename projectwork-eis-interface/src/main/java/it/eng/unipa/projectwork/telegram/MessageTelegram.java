package it.eng.unipa.projectwork.telegram;

public class MessageTelegram {

	private String chatTelegramID;// possibilemtne long
	private String TextTelegram;
	
	
	public MessageTelegram(String textTelegram,String chatTelegramID) {
		super();
		this.chatTelegramID = chatTelegramID;
		TextTelegram = textTelegram;
	}
	

	public String getTextTelegram() {
		return TextTelegram;
	}
	public void setTextTelegram(String textTelegram) {
		TextTelegram = textTelegram;
	}
	
	
}
