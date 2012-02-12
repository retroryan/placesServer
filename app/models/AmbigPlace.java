package models;

import play.db.jpa.GenericModel;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: ryan
 * Date: 1/23/12
 */
@Entity(name = "ambigplaces")
public class AmbigPlace extends GenericModel {

   @Id
   public int id;

   public String place;

   public boolean matched = false;

   public AmbigPlace(int id, String place) {
      this.id = id;
      this.place = place;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getPlace() {
      return place;
   }

   public void setPlace(String place) {
      this.place = place;
   }

   public boolean isMatched() {
      return matched;
   }

   public void setMatched(boolean matched) {
      this.matched = matched;
   }
}


