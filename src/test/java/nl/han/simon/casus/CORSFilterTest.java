package nl.han.simon.casus;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CORSFilterTest {
    private CORSFilter sut;
    @Mock
    private ContainerRequestContext mockRequestCtx;
    @Mock
    private ContainerResponseContext mockResponseCtx;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new CORSFilter();
        when(mockResponseCtx.getHeaders()).thenReturn(new MultivaluedHashMap<>());
    }

    @Test
    public void filter() {
        // Arrange
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>();
        when(mockResponseCtx.getHeaders()).thenReturn(headersMap);

        // Act
        sut.filter(mockRequestCtx, mockResponseCtx);

        // Assert
        assertEquals("*", headersMap.getFirst("Access-Control-Allow-Origin"));
        assertEquals("origin, content-type, accept, authorization", headersMap.getFirst("Access-Control-Allow-Headers"));
        assertEquals("true", headersMap.getFirst("Access-Control-Allow-Credentials"));
        assertEquals("GET, POST, PUT, DELETE, OPTIONS, HEAD", headersMap.getFirst("Access-Control-Allow-Methods"));
        assertEquals("1209600", headersMap.getFirst("Access-Control-Max-Age"));
        Mockito.verify(mockResponseCtx, times(5)).getHeaders();
    }
}
