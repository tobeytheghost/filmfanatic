//WatchedMovie extends the Movie Class as it has an added variable

public class WatchedMovie extends Movie {
    private double rating;
   
//no-arg constructor
    public WatchedMovie(){

    }

    public WatchedMovie(double rating){
        this.rating = rating;
    }

    public WatchedMovie(String title, String genre,String platform, double rating ) {
        super(title, genre, platform); //calling the parent class variables
        this.rating = rating;
        
    }
    
 //Getter and Setter for the rating variable
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
