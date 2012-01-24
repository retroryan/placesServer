package models;

import java.util.List;

/**
 * User: ryan
 * Date: 1/23/12
 */
public class NextAmbigDTO {

   public int ambigId;
   public String ambigPlace;

   public List<DisplayPlace> ambigPlaceList;

   public NextAmbigDTO(int ambigId, String ambigPlace, List<DisplayPlace> ambigPlaceList) {
      this.ambigId = ambigId;
      this.ambigPlace = ambigPlace;
      this.ambigPlaceList = ambigPlaceList;
   }
}
