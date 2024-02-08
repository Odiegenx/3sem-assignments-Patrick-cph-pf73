package threadExcercise.DTOs;

public class GetActivityDTO implements DTOInterface{
    String activity;
    String type;
    String participants;
    String price;
    String link;
    @Override
    public Object getResults() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bored? Heres an Activity: \n"+
                activity+"!\n"+
                "Type: "+type+"\n"+
                "Participants: "+participants+"\n"+
                "Expected price: "+price+"\n"+
                "Link for reference: "+link);
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bored? Heres an Activity: \n"+
                activity+"!\n"+
                "Type: "+type+"\n"+
                "Participants: "+participants+"\n"+
                "Expected price: "+price+"\n"+
                "Link for reference: "+link);
        return stringBuilder.toString();
    }
}
