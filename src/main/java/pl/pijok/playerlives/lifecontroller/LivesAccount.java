package pl.pijok.playerlives.lifecontroller;

public class LivesAccount {

    private int currentLives;
    private long deathTime;

    public LivesAccount(int currentLives, long deathTime){
        this.currentLives = currentLives;
        this.deathTime = deathTime;
    }

    public void addLives(int amount){
        this.currentLives += amount;
    }

    public void takeLives(int amount){
        this.currentLives -= amount;
    }

    public void setLives(int amount){
        this.currentLives = amount;
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    public long getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(long deathTime) {
        this.deathTime = deathTime;
    }
}
