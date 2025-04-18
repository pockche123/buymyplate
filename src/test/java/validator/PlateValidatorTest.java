package validator;

import org.example.validator.PlateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlateValidatorTest {

    PlateValidator plateValidator;

    @BeforeEach
    public void setUp() throws IOException {
        plateValidator = new PlateValidator("/banned_words.json");
    }

    @Test
    public void test_normalise_testValidator(){
        String reg = "AB23\n      GUY!!?";
        String expected = "ab23guy";

        String actual = plateValidator.normalisePlate(reg);
        assertEquals(expected, actual);

    }

    @Test
    public void test_checkContainsBannedWords(){
        String reg1 = "FAT1 GUY";
        String reg2 = "GAY2 BOI";

        String reg3 = "AB23 UYI";
        String reg4 = "ALI1";

        boolean actualTrue = plateValidator.checkContainsBannedWords(reg1);
        boolean actualTrue2 = plateValidator.checkContainsBannedWords(reg2);

        boolean actualFalse = plateValidator.checkContainsBannedWords(reg3);
        boolean actualFalse2 = plateValidator.checkContainsBannedWords(reg4);

        assertTrue(actualTrue);
        assertTrue(actualTrue2);

        assertFalse(actualFalse);
        assertFalse(actualFalse2);

    }




}
