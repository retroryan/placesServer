package models;

import org.folg.places.standardize.Place;

/**
 * User: Ryan K.
 * Date: 1/12/12
 */
public class DisplayPlace {
   public static class Source {
      public String source;
      public String link;
      public Source(String source, String link) {
         this.source = source;
         this.link = link;
      }
   }
   
   private String fullName;
   private Place.AltName[] altNames;
   private String types;
   private String alsoLocatedIn;
   private String latitude;
   private String longitude;
   private DisplayPlace.Source[] sources;


   //This is not used on the main display site.  It is only used for the
   // internal comparison site to compare the Church's standardizer
   private String inputPlace;
   private String standard_text;
   private int numDataRecords;
   private String wasExactMatch;
   private String wasModifiedMatch;


   //This is only used for the ambig labeler
   private int id;
   private String placeName;

   public String getFullName() {
      return fullName;
   }

   public String getLink() {
      return "http://www.werelate.org/wiki/Place:"+fullName.replace(' ','_');
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public Place.AltName[] getAltNames() {
      return altNames;
   }

   public void setAltNames(Place.AltName[] altNames) {
      this.altNames = altNames;
   }

   public DisplayPlace.Source[] getSources() {
      return sources;
   }

   public void setSources(Place.Source[] sources) {
      if (sources == null) {
         this.sources = new Source[0];
      }
      else {
         this.sources = new Source[sources.length];
         for (int i = 0; i < sources.length; i++) {
            Place.Source src = sources[i];
            String link = "";
            if (src.source.equals("fhlc")) {
               link = "http://www.familysearch.org/eng/library/fhlcatalog/supermainframeset.asp?display=localitydetails&subject="+src.id+"&columns=*,0,0";
            }
            else if (src.source.equals("getty")) {
               link = "http://www.getty.edu/vow/TGNFullDisplay?find=&place=&nation=&english=Y&subjectid="+src.id;
            }
            else if (src.source.equals("wikipedia")) {
               link = "http://en.wikipedia.org/wiki/"+src.id.replace(' ','_');
            }
            this.sources[i] = new Source(src.source, link);
         }
      }
   }

   public String getTypes() {
      return types;
   }

   public void setTypes(String types) {
      this.types = types;
   }

   public String getAlsoLocatedIn() {
      return alsoLocatedIn;
   }

   public void setAlsoLocatedIn(String alsoLocatedIn) {
      this.alsoLocatedIn = alsoLocatedIn;
   }

   public String getLatitude() {
      return latitude;
   }

   public void setLatitude(String latitude) {
      this.latitude = latitude;
   }

   public String getLongitude() {
      return longitude;
   }

   public void setLongitude(String longitude) {
      this.longitude = longitude;
   }

   public String getInputPlace() {
      return inputPlace;
   }

   public void setInputPlace(String inputPlace) {
      this.inputPlace = inputPlace;
   }

   public String getStandard_text() {
      return standard_text;
   }

   public void setStandard_text(String standard_text) {
      this.standard_text = standard_text;
   }

   public int getNumDataRecords() {
      return numDataRecords;
   }

   public void setNumDataRecords(int numDataRecords) {
      this.numDataRecords = numDataRecords;
   }

   public String getWasExactMatch() {
      return wasExactMatch;
   }

   public void setWasExactMatch(String wasExactMatch) {
      this.wasExactMatch = wasExactMatch;
   }

   public String getWasModifiedMatch() {
      return wasModifiedMatch;
   }

   public void setWasModifiedMatch(String wasModifiedMatch) {
      this.wasModifiedMatch = wasModifiedMatch;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getPlaceName() {
      return placeName;
   }

   public void setPlaceName(String placeName) {
      this.placeName = placeName;
   }
}
