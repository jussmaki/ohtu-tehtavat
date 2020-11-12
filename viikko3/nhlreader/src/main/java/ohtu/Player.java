package ohtu;

public class Player implements Comparable<Player>{
    private String name;
    private String nationality;
    private Integer assists;
    private Integer goals;
    private String team;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
    
    @Override
    public String toString() {
        return this.name + " team " + this.team + " goals " + this.goals + " assists " +this.assists;
    }

    @Override
    public int compareTo(Player t) {
        return t.getAssists()+t.getGoals()-this.getAssists()-this.getGoals();
    }
      
}
