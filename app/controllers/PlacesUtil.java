package controllers;

import models.DisplayPlace;
import org.apache.commons.lang.StringUtils;
import org.folg.places.standardize.Place;
import org.folg.places.standardize.Standardizer;

/**
 * User: ryan
 * Date: 1/23/12
 */
public class PlacesUtil {

   public static DisplayPlace getDisplayPlace(Place standardizedPlace) {
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

      displayPlace.setId(standardizedPlace.getId());
      displayPlace.setPlaceName(standardizedPlace.getName());

      return displayPlace;
   }
}
