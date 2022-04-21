package edu.ntnu.idatt1002.sysdev_k1_05_ets.readersAndWriters;

import edu.ntnu.idatt1002.sysdev_k1_05_ets.tournament.Team;
import edu.ntnu.idatt1002.sysdev_k1_05_ets.utilities.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TournamentWriterRework {

    private static final String DELIMITER = "\n";
    private static final String COMMA_DELIMITER = ",";

    public TournamentWriterRework() {}

    /**
     * ifFileExistsAndFindLocation()
     * Used to check for duplicates,
     * and location
     * @param tournamentNameShortened
     * @return String of location
     */
    public static String ifFileExistsAndFindLocation(String tournamentNameShortened) {
        File file1 = new File("src/main/resources/edu/ntnu/idatt1002/" +
                "sysdev_k1_05_ets/tournamentFiles/ongoingTournaments/" + tournamentNameShortened + ".txt");
        File file2 = new File("src/main/resources/edu/ntnu/idatt1002/" +
                "sysdev_k1_05_ets/tournamentFiles/upcomingTournaments/" + tournamentNameShortened + ".txt");
        File file3 = new File("src/main/resources/edu/ntnu/idatt1002/" +
                "sysdev_k1_05_ets/tournamentFiles/previousTournaments/" + tournamentNameShortened + ".txt");

        if (file1.exists()) {
            return "Ongoing";
        } else if (file2.exists()) {
            return "Upcoming";
        } else if (file3.exists()) {
            return "Previous";
        } else {
            return "No";
        }
    }

    /**
     Takes in all basic tournament attributes as parameters (Everything except teams and matches).
     Uses shorten tournament name-method to get a file name for the new tournament.
     Replaces a potentially empty description with "No description",
     then checks if the tournament is either an ongoing or upcoming tournament.
     Then it writes the tournament to a file which is placed in either the ongoing or upcoming folder.
     It also uses the writeTournamentToOngoingOverview() or  writeTournamentToUpcomingOverview()
     to write the tournament to an overview file.
     * @param status status of the tournament, e.g. "Not finished"
     * @param tournamentName name ot the tournament, String
     * @param tournamentHost host of the tournament, only "Admin" for now, String
     * @param date date of the tournament, LocalDate
     * @param time time of the tournament, LocalTime
     * @param description description of the tournament, String
     * @param game game played at tournament, String
     * @param platform platform the game is played on, String
     * @param tournamentType type of tournament, only "Brackets" for now, String
     * @param numberOfTeams number of teams playing, only "4", "8" or "16" for now, String
     * @throws IOException
     */
    public static void writeNewTournamentToFileWithBasicInfo(
            String status, String tournamentName, String tournamentHost, LocalDate date, LocalTime time,
            String description, String game, String platform, String tournamentType,
            String numberOfTeams, String prizePool, String prizePoolCurrency, String entranceFee,
            String entranceFeeCurrency)
            throws IOException {
        String tournamentNameShortened = Utilities.shortenAndReplaceUnnecessarySymbolsInString(tournamentName);

        description = description.replaceAll("\n", " ");
        if (description.equals("")) {
            description += "No description";
        }
        String tournamentStringFormat = status + DELIMITER + tournamentName + DELIMITER + tournamentHost + DELIMITER +
                date + DELIMITER + time + DELIMITER + description + DELIMITER + game + DELIMITER + platform +
                DELIMITER + tournamentType + DELIMITER + numberOfTeams +
                DELIMITER + prizePool + COMMA_DELIMITER + prizePoolCurrency + DELIMITER +
                entranceFee + COMMA_DELIMITER + entranceFeeCurrency + DELIMITER;

        if (date.isEqual(LocalDate.now()) && time.equals(LocalTime.now()) && status.equals("Not finished") ||
                date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now()) && status.equals("Not finished") ||
                date.isBefore(LocalDate.now()) && status.equals("Not finished")) {

            File file = new File("src/main/resources/edu/ntnu/idatt1002/" +
                    "sysdev_k1_05_ets/tournamentFiles/ongoingTournaments/" + tournamentNameShortened + ".txt");

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(tournamentStringFormat);
                writeTournamentToOngoingOverview(tournamentNameShortened);
            } catch (IOException exception) {
                throw new IOException("Could not write tournament to file: " + exception.getMessage());
            }

        } else if (date.isAfter(LocalDate.now()) && status.equals("Not finished") ||
                date.isEqual(LocalDate.now()) && time.isAfter(LocalTime.now()) && status.equals("Not finished")) {

            File file = new File("src/main/resources/edu/ntnu/idatt1002/" +
                    "sysdev_k1_05_ets/tournamentFiles/upcomingTournaments/" + tournamentNameShortened + ".txt");

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(tournamentStringFormat);
                writeTournamentToUpcomingOverview(tournamentNameShortened);
            } catch (IOException exception) {
                throw new IOException("Could not write tournament to file: " + exception.getMessage());
            }
        } else {
            File file = new File("src/main/resources/edu/ntnu/idatt1002/" +
                    "sysdev_k1_05_ets/tournamentFiles/previousTournaments/" + tournamentNameShortened + ".txt");

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(tournamentStringFormat);
                writeTournamentToPreviousOverview(tournamentNameShortened);
            } catch (IOException exception) {
                throw new IOException("Could not write tournament to file: " + exception.getMessage());
            }
        }
    }


    /**
     Takes in the shortened tournament name and writes it into
     the ongoing overview file. This makes it easier to later
     locate the individual tournament files.
     * @param tournamentNameShortened shortened name of the tournament, String
     * @throws IOException
     */
    public static void writeTournamentToOngoingOverview(String tournamentNameShortened)
    throws IOException{
        String overviewStringFormat = tournamentNameShortened + DELIMITER;
        File file = new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                "tournamentFiles/ongoingTournaments/ongoingTournaments.txt");
        try (FileWriter fileWriter = new FileWriter(file,true)){
            fileWriter.write(overviewStringFormat);
        } catch (IOException exception){
            throw new IOException("Could not write tournament to ongoing overview");
        }
    }

    /**
    Takes in the shortened tournament name and writes it into
    the upcoming overview file. This makes it easier to later
    locate the individual tournament files.
     * @param tournamentNameShortened shortened name of the tournament, String
     * @throws IOException
     */
    public static void writeTournamentToUpcomingOverview(String tournamentNameShortened)
    throws IOException{
        String overviewStringFormat = tournamentNameShortened + DELIMITER;
        File file = new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                "tournamentFiles/upcomingTournaments/upcomingTournaments.txt");
        try(FileWriter fileWriter = new FileWriter(file,true)){
            fileWriter.write(overviewStringFormat);
        }catch (IOException exception){
            throw new IOException("Could not write tournament to upcoming overview");
        }
    }

    /**
     Takes in the shortened tournament name and writes it into
     the previous overview file. This makes it easier to later
     locate the individual tournament files.
     * @param tournamentNameShortened shortened name of the tournament, String
     * @throws IOException
     */
    public static void writeTournamentToPreviousOverview(String tournamentNameShortened)
            throws IOException{
        String overviewStringFormat = tournamentNameShortened + DELIMITER;
        File file = new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                "tournamentFiles/previousTournaments/previousTournaments.txt");
        try(FileWriter fileWriter = new FileWriter(file,true)){
            fileWriter.write(overviewStringFormat);
        }catch (IOException exception){
            throw new IOException("Could not write tournament to previous overview");
        }
    }

    /**
     Method takes in a shortened tournament name as parameter.
     It then uses the ifFileExistsAndFindLocation()-method
     to find which overview file the tournament is in.
     Then reads the overview file to an arraylist. In the arraylist,
     we then remove the tournament and convert the arraylist
     to a String builder. We then write over the overview file, now with 1 less tournament.
     * @param tournamentNameShortened shortened name of the tournament, String
     * @throws IOException
     */
    public static void removeTournamentFromOverviewWhenLocationNotKnown(String tournamentNameShortened)
    throws IOException{
        ArrayList<String> ongoingTournaments = TournamentReaderRework.readThroughOngoingTournaments();
        ArrayList<String> upcomingTournaments = TournamentReaderRework.readThroughUpcomingTournaments();
        ArrayList<String> previousTournaments = TournamentReaderRework.readThroughPreviousTournaments();
        String location = "";
        boolean locationFound = false;

        for (String str : ongoingTournaments){
            if (str.equals(tournamentNameShortened)) {
                location = "Ongoing";
                locationFound = true;
                break;
            }
        }
        if (!locationFound){
            for (String str : upcomingTournaments){
                if (str.equals(tournamentNameShortened)){
                    location = "Upcoming";
                    locationFound = true;
                    break;
                }
            }
        }
        if (!locationFound){
            for (String str : previousTournaments){
                if (str.equals(tournamentNameShortened)){
                    location = "Previous";
                    break;
                }
            }
        }
        File file = switch (location){
            case "Ongoing" -> new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                    "tournamentFiles/ongoingTournaments/ongoingTournaments.txt");
            case "Upcoming" -> new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                    "tournamentFiles/upcomingTournaments/upcomingTournaments.txt");
            case "Previous" -> new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                    "tournamentFiles/previousTournaments/previousTournaments.txt");
            default -> throw new IOException("File doesn't exist");

        };
        ArrayList<String> overview = GeneralReader.readFile(file);
        overview.removeIf(tournament -> tournament.equals(tournamentNameShortened));
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : overview){
            stringBuilder.append(string).append(DELIMITER);
        }

        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(stringBuilder.toString());
        } catch (IOException exception){
            throw new IOException("Could not write new overview to file: " + exception.getMessage());
        }
    }


    /**
     Handy method to find which folder (ongoing-, upcoming- or previous tournaments)
     the file of a tournament is located in.
     * @param tournamentNameShortened shortened name of the tournament, String
     * @return File path, String
     * @throws IOException
     */
    private static String getPathToTournamentFileAsString(String tournamentNameShortened)
    throws IOException {

        String location = ifFileExistsAndFindLocation(tournamentNameShortened);

        return switch (location){
            case "Ongoing" ->"src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                    "tournamentFiles/ongoingTournaments/" + tournamentNameShortened + ".txt";
            case "Upcoming" -> "src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                    "tournamentFiles/upcomingTournaments/" + tournamentNameShortened + ".txt";
            case "Previous" -> "src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                    "tournamentFiles/previousTournaments/" + tournamentNameShortened + ".txt";
            default -> throw new IOException("Could not find tournament file");
        };
    }


    /**
     Method takes in an arraylist of teams,
     and a shortened tournament name as parameter, for easily finding file location.
     It writes out the whole file to an arraylist, and adds or replaces index 11 (teams) with the new teams taken in
     as a parameter. It then writes the whole file back to the original file path.
     Can also be used to edit teams if matches has been set, but this functionality has not been implemented.
     * @param tournamentNameShortened shortened name of the tournament, String
     * @param teams teams to be added to tournament, ArrayList
     * @throws IOException
     */
    public static void writeTeamsToTournamentFile(String tournamentNameShortened, ArrayList<Team> teams)
    throws IOException{

        File file = new File(getPathToTournamentFileAsString(tournamentNameShortened));
        ArrayList<String> fileAsListOfStrings = GeneralReader.readFile(file);
        StringBuilder stringBuilder = new StringBuilder();

        for (Team team : teams){
            stringBuilder.append(team.getNameOfTeam()).append(COMMA_DELIMITER);
        }
        if (fileAsListOfStrings.size() <= 12){
            fileAsListOfStrings.add("");
        }
        fileAsListOfStrings.set(12,stringBuilder.toString());

        StringBuilder stringBuilder1 = new StringBuilder();
        for (String str : fileAsListOfStrings){
            stringBuilder1.append(str).append(DELIMITER);
        }
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(stringBuilder1.toString());
        } catch (IOException exception){
            throw new IOException("Could not write tournament back to file: " + exception.getMessage());
        }
    }

    public static void updateTournamentFileLocation()
    throws IOException{

        try {
            ArrayList<String> upcomingTournament = TournamentReaderRework.readThroughUpcomingTournaments();

            for (String tournamentNameShortened : upcomingTournament){
                File file = new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                        "tournamentFiles/upcomingTournaments/" + tournamentNameShortened + ".txt");
                //If tournament is no longer upcoming
                if (!TournamentReaderRework.isTournamentStillUpcoming(file)) {
                    ArrayList<String> tournament = GeneralReader.readFile(file);
                    //Remove tournament from upcoming Overview
                    removeTournamentFromUpcomingOverview(tournamentNameShortened);
                    //Write to ongoing overview
                    writeTournamentToOngoingOverview(tournamentNameShortened);
                    //Move entire tournament file to ongoing directory
                    if (file.delete()){
                        File newFile = new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                                "tournamentFiles/ongoingTournaments/" + tournamentNameShortened + ".txt");
                        try (FileWriter fileWriter = new FileWriter(newFile)){
                            StringBuilder stringBuilder = new StringBuilder();
                            for (String str : tournament){
                                stringBuilder.append(str).append("\n");
                            }
                            fileWriter.write(stringBuilder.toString());
                        } catch (IOException exception){
                            throw new IOException("Could not write to ongoing tournaments: " +
                                    exception.getMessage());
                        }
                    }
                }
            }
        } catch (IOException exception){
            throw new IOException("Could not write upcoming tournament to ongoing tournaments: " +
                    exception.getMessage());
        }
        try {
            ArrayList<String> ongoingTournament = TournamentReaderRework.readThroughOngoingTournaments();

            for (String tournamentNameShortened : ongoingTournament){
                File file = new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                        "tournamentFiles/ongoingTournaments/" + tournamentNameShortened + ".txt");

                if (!TournamentReaderRework.isTournamentStillOngoing(file)){
                    ArrayList<String> tournament = GeneralReader.readFile(file);
                    removeTournamentFromOngoingOverview(tournamentNameShortened);
                    writeTournamentToPreviousOverview(tournamentNameShortened);
                    if (file.delete()){
                        File newFile = new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                                "tournamentFiles/previousTournaments/" + tournamentNameShortened + ".txt");
                        try (FileWriter fileWriter = new FileWriter(newFile)){
                            StringBuilder stringBuilder = new StringBuilder();
                            for (String str : tournament){
                                stringBuilder.append(str).append("\n");
                            }
                            fileWriter.write(stringBuilder.toString());
                        } catch (IOException exception){
                            throw new IOException("Could not write to previous tournaments: " +
                                    exception.getMessage());
                        }
                    }
                }
            }
        } catch (IOException exception){
            throw new IOException("Could not write ongoing tournament to previous tournaments: " +
                    exception.getMessage());
        }

    }

    public static void removeTournamentFromUpcomingOverview(String tournamentNameShortened)
    throws IOException{
        try {
            ArrayList<String> upcomingTournaments = TournamentReaderRework.readThroughUpcomingTournaments();
            File file = new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                    "tournamentFiles/upcomingTournaments/upcomingTournaments.txt");
            removeTournamentFromOverview(tournamentNameShortened, upcomingTournaments, file);

        } catch (IOException exception){
            throw new IOException("Could not remove tournament from upcoming overview: "+ exception.getMessage());
        }
    }

    private static void removeTournamentFromOverview(String tournamentNameShortened,
                                                     ArrayList<String> tournaments, File file)
    throws IOException{
        tournaments.removeIf(s -> s.equals(tournamentNameShortened));
        StringBuilder stringBuilder = new StringBuilder();
        for (String tournament : tournaments){
            stringBuilder.append(tournament).append(DELIMITER);
        }
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(stringBuilder.toString());
        } catch (IOException exception){
            throw new IOException("Could not write overview back to file: " + exception.getMessage());
        }
    }

    public static void removeTournamentFromOngoingOverview(String tournamentNameShortened)
    throws IOException{
        try {
            ArrayList<String> ongoingTournaments = TournamentReaderRework.readThroughOngoingTournaments();
            File file = new File("src/main/resources/edu/ntnu/idatt1002/sysdev_k1_05_ets/" +
                    "tournamentFiles/ongoingTournaments/ongoingTournaments.txt");
            removeTournamentFromOverview(tournamentNameShortened, ongoingTournaments, file);

        } catch (IOException exception){
            throw new IOException("Could not remove tournament from upcoming overview: "+ exception.getMessage());
        }
    }

}
