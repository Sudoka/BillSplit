package billsplit.engine;

import java.util.*;


public class Event {
	private String creatorGID;
	private String name;
	private ArrayList<Participant> participants;
	private ArrayList<BalanceChange> txns;
	private String category;
	private Date dateCreated;
	private Date lastModified;
	public static Event current;
	/* 
	 * requires: creatorGID != null
	 *           name != null
	 */
	public Event(String creatorGID, String name){
		assert(creatorGID != null && name != null);
		this.creatorGID = creatorGID;
		this.name = name;
		participants = new ArrayList<Participant>();
		txns = new ArrayList<BalanceChange>();
		category = "None";
		dateCreated = new Date();
		lastModified = dateCreated;
	}
	
	/* 
	 * requires: creatorGID != null
	 *           name != null
	 *           category != null
	 */
	public Event(String creatorGID, String name, String category){
		assert(creatorGID != null && name != null && category != null);
		this.creatorGID = creatorGID;
		this.name = name;
		participants = new ArrayList<Participant>();
		txns = new ArrayList<BalanceChange>();
		this.category = category;
		dateCreated = new Date();
		lastModified = dateCreated;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCreatorGID(){
		return creatorGID;
	}
	
	//Changed from Collection to AbstractList by Kirill M. on 5/23/13 - should be changed back
	public AbstractList<Participant> getParticipants(){
		return (AbstractList<Participant>) participants;
	}
	
	/*
	 * requires name != null
	 */
	public boolean isParticipant(String name){
		assert(name != null);
		for(Participant p : participants){
			if(p.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * requires name != null
	 */
	public boolean isParticipantByGID(String gid){
		assert(gid != null);
		for(Participant p : participants){
			/*
			 * dacashman - getGID() missing from Account contract 
			 */
			if(p.getAccount().getGID().equals(gid)){
				return true;
			}
		}
		return false;
	}
	
	public Collection<BalanceChange> getBalanceChanges(){
		return (Collection<BalanceChange>) txns;
	}
	
	public String getCategory(){
		return category;
	}
	
	public void addBalanceChange(BalanceChange newBalanceChange){
		assert(newBalanceChange != null);
		txns.add(newBalanceChange);
		return;
	}
	
	/*
	 * dacashman - contract requires date to be non-null, but 
	 * 	this is wrong.  null should mean "infinity."  A null 
	 * start_date would mean "get all dates from beginning of event."
	 *  requires: beginDate be before endDate.
	 */
	public Collection<BalanceChange> getBalanceChangesByDate(Date beginDate, Date endDate){
		assert(beginDate.before(endDate));
		ArrayList<BalanceChange> matching = new ArrayList<BalanceChange>();
		if(beginDate == null){
			if(endDate == null){
				return (Collection<BalanceChange>) txns;
			}else{
				for(BalanceChange b : txns){
					if(endDate.after(b.getDate())){
						matching.add(b);
					}
				}
			}
		}else if(endDate == null){
			for(BalanceChange b : txns){
				if(beginDate.before(b.getDate())){
					matching.add(b);
				}
			}
		}else{
			for(BalanceChange b : txns){
				Date bDate = b.getDate();
				if(beginDate.before(bDate) && endDate.after(bDate)){
					matching.add(b);
				}
			}
		}
		return (Collection<BalanceChange>) matching;
	}
	
	/*
	 * require isParticipant(name).
	 */
	public Participant getParticipantByName(String name){
		assert(isParticipant(name));
		for(Participant p : participants){
			if(p.getName().equals(name)){
				return p;
			}
		}
		/* should never get here: complain if so.*/
		assert(false);
		return null;
	}
	
	/*
	 * require isParticipantByGID(gid).
	 */
	public Participant getParticipantByGID(String gid){
		assert(gid != null && isParticipantByGID(gid));
		for(Participant p : participants){
			if(p.getAccount().getGID().equals(gid)){
				return p;
			}
		}
		/* should never get here: complain if so.*/
		assert(false);
		return null;
	}
	
	/*
	 * requires: participant not already in event (by name and 
	 * thus GID).
	 */
	public void addParticipant(Participant newParticipant){
		assert(!isParticipant(newParticipant.getName()));
		participants.add(newParticipant);
		return;
	}
	
	public void removeParticipant(Participant ParticipantToRemove){
		assert(isParticipant(ParticipantToRemove.getName()));
		participants.remove(ParticipantToRemove);
		return;
	}
	
		
	/* dacashman - addBalanceChange, removeBalanceChange need to be 
	 * 	added to the contract.
	 */
		
	
	public void setCategory(String category){
		assert(category != null);
		this.category = category;
		return;
	}
	
	/*
	 * setCreatorGID should not exist or be in contract 
	 */
	
	public void setName(String name){
		assert(name != null);
		this.name = name;
		return;
	}
	
	/* update - recaclulates balances based on all transactions 
	 *  (shouldn't really be used) dacashman
	 */
	public void update(){
		System.out.print("Not yet implemented\n");
		return;
	}
	
}
