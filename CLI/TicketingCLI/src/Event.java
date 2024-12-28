public class Event{
    private int eventId;
    private String eventName;
    private String location;
    private int noOfTickets;

    public Event(int eventId, String eventName, String location) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", location='" + location + '\''+"}";
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
