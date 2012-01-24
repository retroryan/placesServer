package models;

/**
 * User: ryan
 * Date: 1/23/12
 */
public class AmbigResults {

   public int ambigId;
   public String ambigPlace;
   public String fullname;
   
   public String msg;

   public AmbigResults(int ambigId, String ambigPlace, String fullname) {
      this.ambigId = ambigId;
      this.ambigPlace = ambigPlace;
      this.fullname = fullname;
   }

   public AmbigResults() {
   }
}
