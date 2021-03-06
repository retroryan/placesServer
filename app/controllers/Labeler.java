package controllers;

import models.*;
import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.folg.places.standardize.Place;
import org.folg.places.standardize.Standardizer;
import play.mvc.Controller;
import play.mvc.Util;

import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ryan
 * Date: 1/23/12
 */
public class Labeler extends Controller {

   public static int maxID = 0;
   public static RandomData randomData = new RandomDataImpl();


   public static void storeLabelChoice(MatchResult matchResult) {
      matchResult.save();

      AmbigPlace ambig = AmbigPlace.findById(matchResult.getAmbigId());
      ambig.setMatched(true);
      ambig.save();

      labelNextPlace();
   }

   public static void labelNextPlace() {
      render();
   }

   public static void getNextPlace() {

      if (maxID == 0) {
         setMaxId();
      }

      AmbigPlace ambigPlace = null;
      do {
         Integer nextInt = randomData.nextInt(0, maxID);
         ambigPlace = AmbigPlace.find("byIdGreaterThan", nextInt).first();
         //System.out.println("random long was: " + nextInt + " ambigPlace = " + ambigPlace.getId() + " " + ambigPlace.getPlace());
      } while (ambigPlace.isMatched());

      List<Standardizer.PlaceScore> placeScoreList = Standardizer.getInstance().standardize(ambigPlace.getPlace(), 10);
      List<DisplayPlace> ambigPlaceDTOList = new ArrayList<DisplayPlace>();
      for (Standardizer.PlaceScore nextPlaceScore : placeScoreList) {

         Place standardizedPlace = nextPlaceScore.getPlace();
         DisplayPlace displayPlace = PlacesUtil.getDisplayPlace(standardizedPlace);
         ambigPlaceDTOList.add(displayPlace);
      }


      DisplayPlace d1 = new DisplayPlace();
      d1.setFullName("This text is not a place.");
      d1.setId(-1);
      ambigPlaceDTOList.add(d1);

      DisplayPlace d2 = new DisplayPlace();
      d2.setFullName("I can't tell which place this should be.");
      d2.setId(-2);
      ambigPlaceDTOList.add(d2);

      DisplayPlace d3 = new DisplayPlace();
      d3.setFullName("Correct place is not listed.");
      d3.setId(-3);
      ambigPlaceDTOList.add(d3);

      NextAmbigDTO nextAmbigDTO = new NextAmbigDTO(ambigPlace.id, ambigPlace.place, ambigPlaceDTOList);

      renderJSON(nextAmbigDTO);
   }

   public static void uploadAmbigPlaces() {
      render();
   }

   public static void uploadAmbigResults(File ambigFile) throws IOException {

      if (AmbigPlace.count() > 0) {
         AmbigPlace.deleteAll();
      }

      BufferedReader bufferedReader = new BufferedReader(new FileReader(ambigFile));

      int lineCount = 0;
      while (bufferedReader.ready()) {

         String nextLine = bufferedReader.readLine();
         nextLine = nextLine.trim().toLowerCase();
         if (nextLine.length() == 0)
            continue;

         lineCount++;

         String[] split = nextLine.split("\\|");
         if (split.length != 2)
            continue;


         if (lineCount % 5000 == 0) {
            System.out.println("lineCount = " + lineCount);
            break;
         }

         Integer id = Integer.valueOf(split[0]);
         String place = split[1];
         AmbigPlace ambigPlace = new AmbigPlace(id, place);
         ambigPlace.save();
      }

      setMaxId();

      AmbigResults ambigResults = new AmbigResults();
      ambigResults.msg = "uploaded " + AmbigPlace.count() + " records";
      render(ambigResults);
   }

   @Util
   @SuppressWarnings("JpaQlInspection")
   public static void setMaxId() {
      Query query = AmbigPlace.em().createQuery("select MAX(a.id) from ambigplaces a ");
      List resultList = query.getResultList();

      if (resultList.size() > 0) {
         Labeler.maxID = (Integer) resultList.get(0);
      } else {
         System.out.println("Could not find largest id from db, setting manually");
         Labeler.maxID = 143357;
      }
   }
}

