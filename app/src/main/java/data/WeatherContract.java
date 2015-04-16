package data;

import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names as a contract for the weather database
 */
public class WeatherContract {
   //first normalise all dates going into the db to be the start of the Julian day at UTC

       public static long normaliseDate(long startDate){
           Time time = new Time();
           time.set(startDate);
           int julianDay = Time.getJulianDay(startDate,time.gmtoff);
           return time.setJulianDay(julianDay);
       }

    //inner class within WeatherContract to define contents of location table

       public static final class LocationEntry implements BaseColumns { //basecolumns implements _id as a primary key automatically

          public static final String TABLE_NAME = "location";

          //location setting string to send to the openweathermap as the location query
          public static final String COLUMN_LOCATION_SETTING = "location_settings";

          //human legible location provided by the API
          public static final String COLUMN_CITY_NAME = "city_name";

          //latitude and longitude for map intent
          public static final String COLUMN_COORD_LAT = "coord_lat";
          public static final String COLUMN_COORD_LONG = "coord_long";
       }

    //inner class within WeatherContract to define contents of weather table

       public static final class WeatherEntry implements BaseColumns {

           public static final String TABLE_NAME = "weather";

           // Column with the foreign key into the location table.
           public static final String COLUMN_LOC_KEY = "location_id";
           // Date, stored as long in milliseconds since the epoch
           public static final String COLUMN_DATE = "date";
           // Weather id as returned by API, to identify the icon to be used
           public static final String COLUMN_WEATHER_ID = "weather_id";

           // Short description and long description of the weather, as provided by API.
           // e.g "clear" vs "sky is clear".
           public static final String COLUMN_SHORT_DESC = "short_desc";

           // Min and max temperatures for the day (stored as floats)
           public static final String COLUMN_MIN_TEMP = "min";
           public static final String COLUMN_MAX_TEMP = "max";

           // Humidity is stored as a float representing percentage
           public static final String COLUMN_HUMIDITY = "humidity";

           // Humidity is stored as a float representing percentage
           public static final String COLUMN_PRESSURE = "pressure";

           // Windspeed is stored as a float representing windspeed  mph
           public static final String COLUMN_WIND_SPEED = "wind";

           // Degrees are meteorological degrees (e.g, 0 is north, 180 is south).  Stored as floats.
           public static final String COLUMN_DEGREES = "degrees";

      }

}
