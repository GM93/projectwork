package it.eng.unipa.projectwork.channel.event;

import java.io.Serializable;

public abstract class AuctionEvent implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long auctionOid;
	private String type;
	
	
	public AuctionEvent(Long auctionOid,String type) {
		this.auctionOid = auctionOid;
		this.type = type;
	}
	
	
	public Long getAuctionOid() {
		return auctionOid;
	}
	
	public String getType() {
		return type;
	}
	
	
	public String toJson() {
		return "{type:\""+getType()+",\"auctionOid\":"+getAuctionOid()+"}";
	}
	
	
	
	public String getPrezzo() {
		return " ";
	}

}
