import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    void whenNullIllegalArgumentException(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void whenEmptyIllegalArgumentException(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<Horse>()));

        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void isListCorrect(){
        List<Horse> horses = IntStream.range(1, 31)
                .mapToObj(i -> new Horse("Number_" + i, i, 0.1))
                .collect(Collectors.toList());

        List<Horse> expectedHorses = new ArrayList<>(horses);

        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(expectedHorses, hippodrome.getHorses());
    }

    @Test
    void isMoveAllHorses(){
        List<Horse> mockedHorses = Stream.generate(() -> Mockito.mock(Horse.class))
                .limit(50)
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(mockedHorses);

        hippodrome.move();
        mockedHorses.forEach( horse -> verify(horse, atLeastOnce()).move());
    }

    @Test
    void isLongestDistance(){
        Horse winner = null;

        List<Horse> horses = List.of(
                new Horse("Horse 0", 1.0, 20.0),
                new Horse("Horse 1", 1.0, 200.0),
                new Horse("Horse 2", 1.0, 10.0),
                new Horse("Horse 3", 1.0, 201.0),
                new Horse("Horse 4", 1.0, 145.0)
        );
        winner = horses.get(3);
        Hippodrome hippodrome = new Hippodrome(horses);

        assertSame(winner, hippodrome.getWinner());
    }

}