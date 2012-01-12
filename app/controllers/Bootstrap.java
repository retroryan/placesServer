package controllers;

import org.folg.places.standardize.Standardizer;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

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
