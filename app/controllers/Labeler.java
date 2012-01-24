package controllers;

import models.AmbigPlace;
import models.AmbigResults;
import models.DisplayPlace;
import models.NextAmbigDTO;
import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.folg.places.standardize.Place;
import org.folg.places.standardize.Standardizer;
import play.db.jpa.GenericModel;
import play.mvc.Controller;

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

   public static void storeLabelChoice(int ambigId, String ambigPlace, int placeId, String placeName, String placeFullName) {
      System.out.println("ambigId = " + ambigId);
      System.out.println("ambigPlace = " + ambigPlace);
      System.out.println("placeName = " + placeName);
      System.out.println("placeFullName = " + placeFullName);

      labelNextPlace(placeId);
   }

   public static void labelNextPlace(int ambigId) {
      Integer nextInt = randomData.nextInt(0, maxID);

      GenericModel.JPAQuery byIdGreaterThan = AmbigPlace.find("byIdGreaterThan", nextInt);
      List<Object> objectList = byIdGreaterThan.fetch(1);

      AmbigPlace ambigPlace = null;
      if (objectList.size() > 0) {
         ambigPlace = (AmbigPlace) objectList.get(0);
      } else {
         System.out.println("oops! no ambig place found");
         error("oops! no ambig place found");
      }
      System.out.println("random long was: " + nextInt + " ambigPlace = " + ambigPlace.getId() + " " + ambigPlace.getPlace());


      List<Standardizer.PlaceScore> placeScoreList = Standardizer.getInstance().standardize(ambigPlace.getPlace(), 10);
      List<DisplayPlace> ambigPlaceDTOList = new ArrayList<DisplayPlace>();
      for (Standardizer.PlaceScore nextPlaceScore : placeScoreList) {

         Place standardizedPlace = nextPlaceScore.getPlace();
         DisplayPlace displayPlace = PlacesUtil.getDisplayPlace(standardizedPlace);
         ambigPlaceDTOList.add(displayPlace);
      }

      NextAmbigDTO nextAmbigDTO = new NextAmbigDTO(ambigPlace.id, ambigPlace.place, ambigPlaceDTOList);

      render(nextAmbigDTO);
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


         if (lineCount % 1000 == 0) {
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

      return;
   }
}

