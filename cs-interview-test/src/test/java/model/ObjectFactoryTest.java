package model;

import command.BucketFillCommand;
import command.CreateLineCommand;
import command.CreateRectangleCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectFactoryTest {

    private ObjectFactory objectFactory;

    @BeforeEach
    public void setUp()  {
        objectFactory = new ObjectFactory();
    }


    @Test
    public void Should_pass_the_test_when_get_entity_with_draw_line_and_they_should_be_equal()  {
        CreateLineCommand createLineCommand = new CreateLineCommand( "1", "2", "1", "4");
        assertEquals(objectFactory.getEntity(createLineCommand), new Line(1, 2, 1, 4));
    }

    @Test
    public void Should_pass_the_test_when_get_entity_with_rectangle_and_they_should_be_equal()  {
        CreateRectangleCommand drawLineCommand = new CreateRectangleCommand( "1", "2", "3", "4");
        assertEquals(objectFactory.getEntity(drawLineCommand), new Rectangle(1, 2, 3, 4));
    }

    @Test
    public void Should_pass_the_test_when_get_entity_with_area_fill_and_they_should_be_equal()  {
        BucketFillCommand drawLineCommand = new BucketFillCommand( "2", "3", "o");
        assertEquals(objectFactory.getEntity(drawLineCommand), new Area(2, 3, 'o'));
    }

    @Test
    public void Should_pass_the_test_when_get_entity_with_null_and_they_should_be_equal()  {
        assertEquals(objectFactory.getEntity(null), null);
    }
}