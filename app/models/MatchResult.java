package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * User: ryan
 * Date: 1/23/12
 */
@Entity(name = "matchresults")
public class MatchResult extends Model {

   private int ambigId;
   private String ambigPlace;

   private int standardizedId;
   private String standardizedName;
   private String standardizedFullName;


   public MatchResult(int ambigId, String ambigPlace, int standardizedId, String standardizedName, String standardizedFullName) {
      this.ambigId = ambigId;
      this.ambigPlace = ambigPlace;
      this.standardizedId = standardizedId;
      this.standardizedName = standardizedName;
      this.standardizedFullName = standardizedFullName;
   }

   public int getAmbigId() {
      return ambigId;
   }

   public void setAmbigId(int ambigId) {
      this.ambigId = ambigId;
   }

   public String getAmbigPlace() {
      return ambigPlace;
   }

   public void setAmbigPlace(String ambigPlace) {
      this.ambigPlace = ambigPlace;
   }

   public int getStandardizedId() {
      return standardizedId;
   }

   public void setStandardizedId(int standardizedId) {
      this.standardizedId = standardizedId;
   }

   public String getStandardizedName() {
      return standardizedName;
   }

   public void setStandardizedName(String standardizedName) {
      this.standardizedName = standardizedName;
   }

   public String getStandardizedFullName() {
      return standardizedFullName;
   }

   public void setStandardizedFullName(String standardizedFullName) {
      this.standardizedFullName = standardizedFullName;
   }

   @Override
   public String toString() {
      return "ambig place =" + ambigId +
              ":" + ambigPlace +
              "      standardized place = " + standardizedId +
              ":" + standardizedName + "   "  + standardizedFullName;
   }
}
