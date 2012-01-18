package controllers;

import models.DisplayPlace;
import org.apache.commons.lang.StringUtils;
import org.folg.places.standardize.Place;
import org.folg.places.standardize.Standardizer;
import org.xml.sax.SAXException;
import play.mvc.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

   public static void index() {
      render();
   }

   public static void searchForPlace(String place) throws IOException, SAXException {
      List<Standardizer.PlaceScore> results = Standardizer.getInstance().standardize(place, 10);
      List<DisplayPlace> displayPlaces = new ArrayList<DisplayPlace>();
      for (Standardizer.PlaceScore nextPlaceScore : results) {
         DisplayPlace displayPlace = getDisplayPlace(nextPlaceScore.getPlace());
         displayPlaces.add(displayPlace);
      }

      render(displayPlaces);
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

      displayPlace.setLatitude(standardizedPlace.getLatitude());
      displayPlace.setLongitude(standardizedPlace.getLongitude());
      return displayPlace;
   }

}