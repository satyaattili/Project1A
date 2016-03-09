package in.mobileappdev.moviesdb.utils;

import com.squareup.otto.Bus;

/**
 * Created by satyanarayana.avv on 09-03-2016.
 */

  public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
      return BUS;
    }

    private BusProvider() {
      // No instances.
    }
}
