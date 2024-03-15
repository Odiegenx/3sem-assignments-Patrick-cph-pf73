package excerciseWithJavalinAndCrud.controllers;

import excerciseWithJavalinAndCrud.config.ApplicationConfig;
import excerciseWithJavalinAndCrud.config.HibernateConfig;
import excerciseWithJavalinAndCrud.config.Routes;
import excerciseWithJavalinAndCrud.model.Hotel;
import excerciseWithJavalinAndCrud.model.Room;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class RestAssuredHotelTest {


  /*
  Basic Setup

  Include Rest Assured dependency in your project.
  and in our case, start javalin.
  */

    @BeforeAll
    static void setUpAll(){
        RestAssured.baseURI = "http://localhost:7777/api";
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.initiateServer()
                .startServer(7777)
                .setExceptionOverallHandling()
                .setRoute(Routes.setRoutes());
    }
    /*
    set up the testcontainers for each test, so that each test is done on
    the same data. Which is done to insure consistency in out tests.
     */
    @BeforeEach
    void setUp() {
        HotelController hotelController = new HotelController(true);
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(true);

        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("delete from Room ").executeUpdate();
            em.createQuery("delete from Hotel ").executeUpdate();

            em.createNativeQuery("ALTER SEQUENCE room_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE hotel_id_seq RESTART WITH 1").executeUpdate();
            Hotel hotel1 = new Hotel("Charmander","Ildvej 5");
            Room room1 = new Room(hotel1.getId(),1,5000);
            Room room2 = new Room(hotel1.getId(),2,3000);

            hotel1.addRoom(room1);
            hotel1.addRoom(room2);
            em.persist(hotel1);
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDown() {
    }


    /*
    Testing CRUD Functionality - end point response.

    Using rest assured we are able to write "simple" and easy to read tests.
    Below tests show how you could write tests for CRUD operations
    (Create, Read, Update, Delete) using rest assured and hamcrest matchers.
     */
    @Test
    @DisplayName("Getting all hotels")
    void getHotels() {
       RestAssured.given().log().all()
               .when()
               .get("/hotels")
               .then().log().all()
               .statusCode(200)
               .body("[0].name",equalTo("Charmander"));
    }
    /*
    Response Validation

     To tests the information coming from the response we
     use Hamcrest matchers.
     equalTo(Object expected) - Checks if the actual value is equal to the expected value.
     isA(Class<?> type) - Checks if the actual value is an instance of the specified class.
     containsString(String substring) - Checks if the actual value contains the specified substring.
     startsWith(String prefix) - Checks if the actual value starts with the specified prefix.
     endsWith(String suffix) - Checks if the actual value ends with the specified suffix.
     hasSize(int size) - Checks if the actual value has the specified size.
     hasItem(T item) - Checks if the actual value contains the specified item.
     hasItems(T... items) - Checks if the actual value contains all of the specified items.
     */
    @Test
    @DisplayName("Get hotel by ID")
    void getHotelById() {
        RestAssured.given().log().all()
                .when()
                .get("/hotels/hotel/1")
                .then().log().all()
                .statusCode(200)
                .body("name",equalTo("Charmander"))
                .body("name",containsString("Char"))
                .body("rooms",hasSize(2));
    }

    @Test
    @DisplayName("Get hotel rooms by hotel ID")
    void getHotelRoomsByHotelId() {
        RestAssured.given().log().all()
                .when()
                .get("/hotels/hotel/1/rooms")
                .then().log().all()
                .statusCode(200)
                .body("[0].id",equalTo(1),"[1].id",equalTo(2));
    }

    @Test
    @DisplayName("Create a hotel")
    void createHotel() {
        Hotel hotel1 = new Hotel("Charmander's Hideout","Ildvej 65");
        Room room1 = new Room(hotel1.getId(),1,0);
        Room room2 = new Room(hotel1.getId(),2,0);
        hotel1.addRoom(room1);
        hotel1.addRoom(room2);
        RestAssured.given().log().all()
                .when().body(hotel1)
                .when()
                .post("/hotels/hotel")
                .then()
                .statusCode(200);
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        Hotel toFind;
        try(EntityManager em = emf.createEntityManager()){
            toFind = em.find(Hotel.class,2);
        }
        assertEquals("Charmander's Hideout",toFind.getName());
    }

    @Test
    @DisplayName("Update Hotel")
    void updateHotel() {
        RestAssured.given().log().all()
                .with().body(new Hotel("Tessy","hej"))
                .when()
                .put("/hotels/hotel/1")
                .then()
                .statusCode(200)
                .assertThat().body("name",  equalTo("Tessy"))
                .log().all();
    }


    @Test
    @DisplayName("Delete hotel")
    void deleteHotel() {
        RestAssured.given().log().all()
                .when()
                .delete("/hotels/hotel/1")
                .then()
                .statusCode(200);
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        Hotel toFind = null;
        try(EntityManager em = emf.createEntityManager()){
            toFind = em.find(Hotel.class,1);
        }
        assertEquals(toFind,null);
    }
}