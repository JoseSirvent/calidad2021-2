package com.anahuac.calidad.doubles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockTest {

    Dependency dependency;

    @Before
    public void setUp() throws Exception {
        dependency = Mockito.mock(Dependency.class);
    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void test() {
        assertThat(dependency.getClassName(), is(nullValue()));
    }

    private void setComportamiento() {
        when(dependency.getClassName()).thenReturn("Nombre de la clase");
    }

    @Test
    public void mockValueTest(){
        String resultadoEsperado = "Nombre de la clase";
        setComportamiento();
        String resultadoEjecucion = dependency.getClassName();
        assertThat(resultadoEsperado, is(resultadoEjecucion));
    }

    @Test(expected = IllegalArgumentException.class)
    public void mockExceptionTest(){
        when(dependency.getClassName()).thenThrow(IllegalArgumentException.class);

        dependency.getClassName();
    }

    @Test
    public void mockRealTest(){
        when(dependency.getClassName()).thenCallRealMethod();

        assertThat(dependency.getClassName(), is(dependency.getClass().getSimpleName()));
    }

    @Test
    public void mockArgumentoTest(){
        int resultadoEsperado = 10;
        when(dependency.addTwo(anyInt())).thenReturn(10);
        
        int resultadoEjecucion = dependency.addTwo(0);
        assertThat(10, is(dependency.addTwo(50)));
    }

    @Test
    public void mockAnswerTest(){
        when(dependency.addTwo(anyInt())).thenAnswer(new Answer<Integer>(){

            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                int arg = (Integer) invocation.getArguments()[0];
                return arg + 10;
            }
        });
        assertEquals(60, dependency.addTwo(50));
        assertThat(39, is(dependency.addTwo(29)));
    }
}
