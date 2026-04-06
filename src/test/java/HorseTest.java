import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void whenFirstArgNullIllegalArgumentException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, ()-> {
            new Horse(null, 1.0, 1.0);
        });

        assertEquals("Name cannot be null.", illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "  "})
    void whenFirstArgEmptyIllegalArgumentException(String str){
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, ()-> {
            new Horse(str, 1.0, 1.0);
        });

        assertEquals("Name cannot be blank.", illegalArgumentException.getMessage());
    }

    @Test
    void whenSpeedNegativeIllegalArgumentException(){
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, ()-> {
            new Horse("Name", -2.0, 1.0);
        });

        assertEquals("Speed cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    void whenDistanceNegativeIllegalArgumentException(){
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, ()-> {
            new Horse("Name", 1.0, -2.0);
        });

        assertEquals("Distance cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    void isReturnRightName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("TestName", 1.0, 1.0);

        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);

        assertEquals("TestName", (String)name.get(horse));
    }

    @Test
    void isReturnRightSpeed(){
        Horse horse = new Horse("TestName", 13.0, 1.0);
        assertEquals(13.0, horse.getSpeed());
    }

    @Test
    void isReturnRightDistance(){
        Horse horse = new Horse("TestName", 1.0, 13.0);
        assertEquals(13.0, horse.getDistance());

        Horse testHorse = new Horse("TestName", 1.0);
        assertEquals(0, testHorse.getDistance());
    }

    @Test
    void isGetRandomDouble_InMove(){
        Horse horse = new Horse("Name", 0.1, 0.1);

        try(MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)){
            horse.move();
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }

    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 0.7, 0.4})
    void returnRightDistance_whenMove(double mockedRandomDouble){
        double speed = 0.1;
        double distance = 0.2;

        Horse horse = new Horse("Name", speed, distance);

        try(MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)){
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(mockedRandomDouble);
            double expectedDistance = distance + speed * mockedRandomDouble;

            horse.move();

            assertEquals(expectedDistance, horse.getDistance(), 0.00001);
        }

    }

}