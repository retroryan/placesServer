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
   private double latitude;
   private double longitude;


   //This is not used on the main display site.  It is only used for the
   // internal comparison site to compare the Church's standardizer
   private String inputPlace;
   private String standard_text;
   private int numDataRecords;


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

   public double getLatitude() {
      return latitude;
   }

   public void setLatitude(double latitude) {
      this.latitude = latitude;
   }

   public double getLongitude() {
      return longitude;
   }

   public void setLongitude(double longitude) {
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
}
