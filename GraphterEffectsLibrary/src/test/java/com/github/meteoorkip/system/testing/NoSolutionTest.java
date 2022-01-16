package com.github.meteoorkip.system.testing;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.system.TestHelper;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;


public final class NoSolutionTest {

    @Test
    public void test1() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        assertFalse(new TestHelper().checkIfSolutionExists("regression/testing/nosolution.vis", "regression/testing/nosolution.dot"));
    }
}
