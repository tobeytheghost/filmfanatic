//Create a Movie Parent class to grab for ArrayList and for the controllers to store information

public class Movie {
    //Both controllers have 3 variables in common, so it is added to the Parent Class
    private String title;
    private String genre;
    private String platform;

    //no-arg constructor
    public Movie(){

    }

    //Movie Constructor
    public Movie(String title, String genre, String platform) {
        this.title = title;
        this.genre = genre;
        this.platform = platform;
    }
    
//Getters and Setters for the Variables
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
