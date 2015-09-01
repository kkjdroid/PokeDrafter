public class player {
    private String[] pokes;
    private int[] money;
    private String[] names;
    private int players;
    public player() {
        this(0);
    }

    public player(int x) {
        this.pokes=new String[x];
        this.money=new int[x];
        this.names=new String[x];
        for (int i=0; i<x; i++) {
            this.pokes[i]=""; 
            this.money[i]=1000;
            this.names[i]="";
        }
        this.players=x;
    }

    public void addPoke(int player, String poke) {
        this.pokes[player-1]=(this.pokes[player-1] + poke + " ");
    }

    public String getPokes(int player) {
        return this.pokes[player-1];
    }

    public int getMoney(int player) {
        return this.money[player-1];
    }

    public void subtract(int player, int cost) {
        this.money[player-1]-=cost;
    }
    
    public void setName(int player, String name) {
        this.names[player]=name;
    }
    
    public String getName(int player) {
        return this.names[player-1];
    }
    
    public int getPlayers() {
        return this.players;
    }
}