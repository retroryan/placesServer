package models;

/**
 * User: Ryan K.
 * Date: 1/12/12
 */
public class DisplayPlace {
   
   private String fullName;
   private String altNames;
   private String types;
   private String alsoLocatedIn;
   private String latitude;
   private String longitude;


   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public String getAltNames() {
      return altNames;
   }

   public void setAltNames(String altNames) {
      this.altNames = altNames;
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
}
