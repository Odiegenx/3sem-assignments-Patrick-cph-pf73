package excerciseWithJavalinAndCrud.controllers;

import excerciseWithJavalinAndCrud.DTO.RoomDTO;
import excerciseWithJavalinAndCrud.config.ApplicationConfig;
import excerciseWithJavalinAndCrud.config.HibernateConfig;
import excerciseWithJavalinAndCrud.config.Routes;
import excerciseWithJavalinAndCrud.model.Hotel;
import excerciseWithJavalinAndCrud.model.Room;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RestAssuredRoomTest {

    @BeforeAll
    static void setUpAll(){
        RestAssured.baseURI = "http://localhost:7777/api";
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.initiateServer()
                .startServer(7777)
                .setExceptionOverallHandling()
                .setRoute(Routes.setRoutes());
    }

    @BeforeEach
    void setUp() {
        RoomController roomController = new RoomController(true);
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

    @Test
    @DisplayName("Get all rooms")
    void getRooms() {
        RestAssured.given().log().all()
                .when()
                .get("/hotels/rooms")
                .then().log().all()
                .statusCode(200)
                .body("[0].hotelId",equalTo(1));
    }

    @Test
    @DisplayName("Get room by id")
    void getRoomById() {
        RestAssured.given().log().all()
                .when()
                .get("/hotels/rooms/room/1")
                .then().log().all()
                .statusCode(200)
                .body("hotelId",equalTo(1),"id", equalTo(1));
    }

    @Test
    @DisplayName("Create new room")
    void createRoom() {
        Room room2 = new Room(1,2,0);
        RestAssured.given().log().all()
                .when().body(room2)
                .when()
                .post("/hotels/rooms/room")
                .then().log().all()
                .statusCode(200);
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        Room toFind;
        try(EntityManager em = emf.createEntityManager()){
            toFind = em.find(Room.class,3);
        }
        assertEquals(1,toFind.getHotelId());
        assertEquals(3,toFind.getId());
    }

    @Test
    @DisplayName("Update room")
    void updateRoom() {
        RestAssured.given().log().all()
                .with().body(new RoomDTO(99999))
                .when()
                .put("/hotels/rooms/room/1")
                .then()
                .statusCode(200)
                .assertThat().body("price",  equalTo(99999))
                .log().all();
    }

    @Test
    @DisplayName("Delete room" )
    void deleteRoom() {
        RestAssured.given().log().all()
                .when()
                .delete("/hotels/rooms/room/1")
                .then()
                .statusCode(200).log().all();
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        Room toFind = null;
        try(EntityManager em = emf.createEntityManager()){
            toFind = em.find(Room.class,1);
        }
        assertEquals(toFind,null);
    }

}