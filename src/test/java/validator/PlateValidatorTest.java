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
        plateValidator = new PlateValidator();
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

//    @Test
//    public void test_checkRegexMatch(){
//        String reg1 = "BO0OBS1";
//        String reg2 = "T3RR0R";
//        String reg3 = "CR4CKX";
//        String reg4 = "N1GG4R";
//        String reg5 = "AB23 UYI";
//        String reg6 = "ALI1";
//        String test7 = "A123BCD";
//
//
//
//        boolean actual1 = plateValidator.checkRegexMatch(reg1);
//        boolean actual2 = plateValidator.checkRegexMatch(reg2);
//        boolean actual3 = plateValidator.checkRegexMatch(reg3);
//        boolean actual4 = plateValidator.checkRegexMatch(reg4);
//        boolean actual5 = plateValidator.checkRegexMatch(reg5);
//        boolean actual6 = plateValidator.checkRegexMatch(reg6);
//        boolean actual7 = plateValidator.checkRegexMatch(test7);
//
//
//        assertTrue(actual1);
//        assertTrue(actual2);
//        assertTrue(actual3);
//        assertTrue(actual4);
//
//        assertFalse(actual5);
//        assertFalse(actual6);
//        assertFalse(actual7);
//
//    }
//
//


}
