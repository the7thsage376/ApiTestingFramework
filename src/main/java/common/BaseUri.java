package common;

import io.restassured.config.Config;

public class BaseUri {
   public static String baseURL ="https://www.ndosiautomation.co.za";

   protected static final String Admin_Email = ConfigLoader.getProperty("admin.email");
   protected static final String Admin_Password =ConfigLoader.getProperty("admin.password");

}
// C:\Users\moloi\Desktop\allure-2.38.1\allure-2.38.1\bin\allure serve allure-results