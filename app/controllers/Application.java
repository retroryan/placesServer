package controllers;

import models.DisplayPlace;
import models.PlacesComparisonDTO;
import org.apache.commons.lang.StringUtils;
import org.folg.places.standardize.Place;
import org.folg.places.standardize.Standardizer;
import org.xml.sax.SAXException;
import play.mvc.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Application extends Controller {

   public static void index() {
      render();
   }

   public static void comparePlacesUpload() {
      render();
   }

   public static void comparePlaces(File placesFile) throws IOException {
      PlacesComparisonDTO placesComparisonDTO = comparePlacesFile(placesFile);
      render(placesComparisonDTO);
   }

   public static void searchForPlace(String place) throws IOException, SAXException {
      List<DisplayPlace> displayPlaces = getDisplayPlacesList(place);

      render(displayPlaces);
   }

   private static List<DisplayPlace> getDisplayPlacesList(String place) {
      List<Standardizer.PlaceScore> results = Standardizer.getInstance().standardize(place, 10);
      List<DisplayPlace> displayPlaces = new ArrayList<DisplayPlace>();
      for (Standardizer.PlaceScore nextPlaceScore : results) {
         DisplayPlace displayPlace = getDisplayPlace(nextPlaceScore.getPlace());
         displayPlaces.add(displayPlace);
      }
      return displayPlaces;
   }

   private static DisplayPlace getDisplayPlace(Place standardizedPlace) {
      Standardizer standardizer = Standardizer.getInstance();
      DisplayPlace displayPlace = new DisplayPlace();
      displayPlace.setFullName(standardizedPlace.getFullName());

      int[] alsoLocatedInIds = standardizedPlace.getAlsoLocatedInIds();
      if ((alsoLocatedInIds != null) && (alsoLocatedInIds.length > 0)) {
         StringBuffer alsoLocatedStrs = new StringBuffer();
         for (int indx = 0; indx < alsoLocatedInIds.length; indx++) {
            int alsoLocatedInId = alsoLocatedInIds[indx];
            Place alsoLocatedPlace = standardizer.getPlace(alsoLocatedInId);
            if (alsoLocatedStrs.length() > 0) {
               alsoLocatedStrs.append(", ");
            }
            alsoLocatedStrs.append(alsoLocatedPlace.getFullName());
         }
         displayPlace.setAlsoLocatedIn(alsoLocatedStrs.toString());
      }

      String altNames = StringUtils.join(standardizedPlace.getAltNames(), ",");
      displayPlace.setAltNames(altNames);

      String types = StringUtils.join(standardizedPlace.getTypes(), ",");
      displayPlace.setTypes(types);

      if (standardizedPlace.getLatitude() != 0 || standardizedPlace.getLongitude() != 0) {
        displayPlace.setLatitude(Double.toString(standardizedPlace.getLatitude()));
        displayPlace.setLongitude(Double.toString(standardizedPlace.getLongitude()));
      }
      return displayPlace;
   }

   public static PlacesComparisonDTO comparePlacesFile(File scrapeFile) throws IOException {
      Pattern compiledPattern = Pattern.compile("[, ]");

      PlacesComparisonDTO placesComparisonDTO = new PlacesComparisonDTO();
      placesComparisonDTO.displayPlaces = new ArrayList<DisplayPlace>();

      BufferedReader bufferedReader = new BufferedReader(new FileReader(scrapeFile));

      int lineCount = 0;
      while (bufferedReader.ready()) {

         String nextLine = bufferedReader.readLine();
         nextLine = nextLine.trim().toLowerCase();
         if (nextLine.length() == 0)
            continue;

         lineCount++;

         String[] split = nextLine.split("###");
         if (split.length != 3)
            continue;

         String place = split[0];
         String standard_text = split[1];
         int numDataRecords = Integer.parseInt(split[2]);

         List<DisplayPlace> placesSubList = getDisplayPlacesList(place);
         if (placesSubList.size() > 0) {
            DisplayPlace displayPlace = placesSubList.get(0);
            displayPlace.setInputPlace(place);
            displayPlace.setStandard_text(standard_text);
            displayPlace.setNumDataRecords(numDataRecords);

            String fullName = displayPlace.getFullName();
            if (standard_text.equalsIgnoreCase(fullName)) {
               placesComparisonDTO.numExactMatches++;
               displayPlace.setWasExactMatch("Yes!");
            } else {
               displayPlace.setWasExactMatch("no");
            }


            String modifedFolgName = compiledPattern.matcher(fullName).replaceAll("");
            String modifiedStandardText = compiledPattern.matcher(standard_text).replaceAll("");
            if (modifiedStandardText.equalsIgnoreCase(modifedFolgName)) {
               placesComparisonDTO.numModifiedatches++;
               displayPlace.setWasModifiedMatch("Yes!");
            } else {
               displayPlace.setWasModifiedMatch("no");
            }


            placesComparisonDTO.displayPlaces.add(displayPlace);

         }

      }

      placesComparisonDTO.numPlacesCompared = lineCount;
      return placesComparisonDTO;
   }


}

