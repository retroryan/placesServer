package controllers;

import models.AmbigPlace;
import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.folg.places.standardize.Standardizer;
import play.db.jpa.GenericModel;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import javax.persistence.Query;
import java.util.List;

/**
 * User: Ryan K
 * Date: 1/12/12
 */
@OnApplicationStart
public class Bootstrap extends Job {

   public void doJob() {
      //get the standardizer to force initialization
      Standardizer standardizer = Standardizer.getInstance();
      System.out.println("standardizer = " + standardizer);

   }
}
