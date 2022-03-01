import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    switch (option)
    {
      case "t" -> searchTitles();
      case "c" -> searchCast();
      case "k" -> searchKeywords();
      case "g" -> listGenres();
      case "r" -> listHighestRated();
      case "h" -> listHighestRevenue();
      default -> System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (Movie movie : movies)
    {
      String movieTitle = movie.getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.contains(searchTerm)) {
        //add the Movie objest to the results list
        results.add(movie);
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
    ArrayList<String> actors = new ArrayList<>();
    for(Movie movie : movies)
    {
      String[] actor = movie.getCast().split("\\|");
      for(String act : actor)
      {
        if(!actors.contains(act))
        {
          actors.add(act);
        }
      }
    }
    for (int j = 1; j < actors.size(); j++)
    {
      String temp = actors.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(actors.get(possibleIndex - 1)) < 0)
      {
        actors.set(possibleIndex, actors.get(possibleIndex - 1));
        possibleIndex--;
      }
      actors.set(possibleIndex, temp);
    }
    System.out.println("Enter a person to search for (first or last name)");
    String input = scanner.nextLine().toLowerCase();
    int i = 1;
    ArrayList<String> found = new ArrayList<>();
    for(String actor : actors)
    {
      if(actor.toLowerCase().contains(input))
      {
        System.out.println(i + ". " + actor);
        found.add(actor);
        i++;
      }
    }
    System.out.println("Which actor would you like to learn more about?");
    String selectedActor = found.get(scanner.nextInt() - 1);
    System.out.println("All movies " + selectedActor + " are in: ");
    ArrayList<Movie> found1 = new ArrayList<>();
    for(Movie movie : movies)
    {
      if(movie.getCast().contains(selectedActor))
      {
        found1.add(movie);
      }
    }
    int k = 1;
    for(Movie movie : found1)
    {
      System.out.println(k + ". " + movie.getTitle());
      k++;
    }
    System.out.println("Which movie would you like to learn more about?");
    int input1 = scanner.nextInt();
    displayMovieInfo(found1.get(input1 - 1));
  }

  private void searchKeywords()
  {
    System.out.println("Enter a search keyword.");
    String input = scanner.nextLine();
    ArrayList<Movie> found = new ArrayList<>();
    for(Movie movie : movies)
    {
      if(movie.getKeywords().contains(input.toLowerCase()))
      {
        found.add(movie);
      }
    }
    int i = 1;
    for(Movie movie : found)
    {
      System.out.println(i + ". " + movie.getTitle());
      i++;
    }
    System.out.println("Which movie would you like to learn more about?");
    int input1 = scanner.nextInt();
    displayMovieInfo(found.get(input1 - 1));
  }
  
  private void listGenres()
  {
    ArrayList<String> genres = new ArrayList<>();
    for(Movie movie : movies)
    {
      String[] genre = movie.getGenres().split("\\|");
      for(String gen : genre)
      {
        if(!genres.contains(gen))
        {
          genres.add(gen);
        }
      }
    }
    for (int j = 1; j < genres.size(); j++)
    {
      String temp = genres.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(genres.get(possibleIndex - 1)) < 0)
      {
        genres.set(possibleIndex, genres.get(possibleIndex - 1));
        possibleIndex--;
      }
      genres.set(possibleIndex, temp);
    }
    System.out.println("For which genre would you like to see all movies from?");
    int i = 1;
    for(String genre : genres)
    {
      System.out.println(i + ". " + genre);
      i++;
    }
    int input = scanner.nextInt();
    String selectedGenre = genres.get(input - 1);
    sortResults(movies);
    int j = 1;
    ArrayList<Movie> found = new ArrayList<>();
    for(Movie movie : movies)
    {
      if(movie.getGenres().contains(selectedGenre))
      {
        System.out.println(j + ". " + movie.getTitle());
        found.add(movie);
        j++;
      }
    }
    System.out.println("Which movie would you like to learn more about?");
    int input1 = scanner.nextInt();
    displayMovieInfo(found.get(input1 - 1));
  }
  
  private void listHighestRated()
  {
    ArrayList<Movie> list = new ArrayList<>();
    list.addAll(movies);
    ArrayList<Movie> fin = new ArrayList<>();
    for (int j = 1; j < list.size(); j++)
    {
      //double temp = list.get(j).getUserRating();
      Movie temp = list.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.getUserRating() < list.get(possibleIndex - 1).getUserRating())
      {
        list.set(possibleIndex, list.get(possibleIndex - 1));
        possibleIndex--;
      }
      list.set(possibleIndex, temp);
    }
    for(int i = list.size() - 1; i > list.size() - 51; i--)
    {
      fin.add(list.get(i));
    }
    int i = 1;
    for(Movie movie : fin)
    {
      System.out.println(i + ". " + movie.getTitle() + " : " + movie.getUserRating());
      i++;
    }
  }
  
  private void listHighestRevenue()
  {
    ArrayList<Movie> list = new ArrayList<>();
    list.addAll(movies);
    ArrayList<Movie> fin = new ArrayList<>();
    for (int j = 1; j < list.size(); j++)
    {
      //double temp = list.get(j).getUserRating();
      Movie temp = list.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.getRevenue() < list.get(possibleIndex - 1).getRevenue())
      {
        list.set(possibleIndex, list.get(possibleIndex - 1));
        possibleIndex--;
      }
      list.set(possibleIndex, temp);
    }
    for(int i = list.size() - 1; i > list.size() - 51; i--)
    {
      fin.add(list.get(i));
    }
    int i = 1;
    for(Movie movie : fin)
    {
      System.out.println(i + ". " + movie.getTitle() + " : $" + movie.getRevenue());
      i++;
    }
  }

  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null)
      {
        // import all cells for a single row as an array of Strings,
        // then convert to ints as needed
        String[] thing = line.split(",");

        // pull out the data for this cereal
        String title = thing[0];
        String cast = thing[1];
        String director = thing[2];
        String tagline = thing[3];
        String keywords = thing[4] ;
        String overview = thing[5] ;
        int runtime = Integer.parseInt(thing[6]);
        String genres = thing[7] ;
        double userRating = Double.parseDouble((thing[8]));
        int year = Integer.parseInt(thing[9]);
        int revenue = Integer.parseInt(thing[10]);

        // create Cereal object to store values
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        // adding Cereal object to the arraylist
        movies.add(nextMovie);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());
    }
  }
  
  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}